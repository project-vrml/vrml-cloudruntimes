package group.rxcloud.vrml.cloudruntimes.infrastructure;

import group.rxcloud.cloudruntimes.domain.core.ConfigurationRuntimes;
import group.rxcloud.cloudruntimes.domain.core.configuration.ConfigurationItem;
import group.rxcloud.cloudruntimes.domain.core.configuration.SubConfigurationResp;
import group.rxcloud.cloudruntimes.utils.TypeRef;
import group.rxcloud.vrml.core.serialization.Serialization;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class CloudRuntimesConfigurationSubscriber {

    private final CloudRuntimesProvider cloudRuntimesProvider;

    public CloudRuntimesConfigurationSubscriber(CloudRuntimesProvider cloudRuntimesProvider) {
        this.cloudRuntimesProvider = cloudRuntimesProvider;
    }

    public <T> Optional<Tuple2<T, Flux<T>>> subscribeConfiguration(String configName, Class<T> configType) {
        return subscribeConfiguration(configName, configType, null);
    }

    public <T> Optional<Tuple2<T, Flux<T>>> subscribeConfiguration(String configName, Class<T> configType, Map<String, String> metadata) {
        final ConfigurationRuntimes configurationRuntimes = cloudRuntimesProvider.configurationRuntimes();
        final String configurationStoreName = cloudRuntimesProvider.getConfigurationStoreName();
        final String configurationAppId = cloudRuntimesProvider.getConfigurationAppId();
        List<String> keys = Collections.singletonList(configName);
        TypeRef<T> type = TypeRef.get(configType);
        try {
            // subscribe config flux
            Flux<SubConfigurationResp<T>> flux = configurationRuntimes.subscribeConfiguration(
                    configurationStoreName,
                    configurationAppId,
                    keys,
                    metadata,
                    type);

            // subscribe config
            Flux<T> subscribeConfigurationFlux = flux.map(this::getConfigurationEntity);
            // get first config
            T firstConfiguration = subscribeConfigurationFlux.blockFirst();

            Tuple2<T, Flux<T>> tuple2 = Tuple.of(firstConfiguration, subscribeConfigurationFlux);
            if (log.isInfoEnabled()) {
                log.info("[Vrml.CloudRuntimes][CloudRuntimesConfigurationSubscriber] subscribe configuration [{}] success, first response is [{}]",
                        configName, Serialization.toJsonSafe(firstConfiguration));
            }
            return Optional.of(tuple2);
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("[Vrml.CloudRuntimes][CloudRuntimesConfigurationSubscriber] subscribe configuration [{}] error",
                        configName, e);
            }
            return Optional.empty();
        }
    }

    private <T> T getConfigurationEntity(SubConfigurationResp<T> subConfigurationResp) {
        if (subConfigurationResp != null) {
            List<ConfigurationItem<T>> items = subConfigurationResp.getItems();
            if (!CollectionUtils.isEmpty(items)) {
                ConfigurationItem<T> configurationItem = items.get(0);
                if (configurationItem != null) {
                    return configurationItem.getContent();
                }
            }
        }
        return null;
    }
}
