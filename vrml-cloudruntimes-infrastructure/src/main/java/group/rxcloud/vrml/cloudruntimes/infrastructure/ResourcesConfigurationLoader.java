package group.rxcloud.vrml.cloudruntimes.infrastructure;

import group.rxcloud.vrml.core.serialization.Serialization;
import group.rxcloud.vrml.resource.Resources;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ResourcesConfigurationLoader {

    public <T> Optional<T> loadResourcesConfiguration(String configName, Class<T> configType) {
        Try<T> aTry = Resources.loadResources(configName, configType);
        T result = aTry
                .map(file -> {
                    if (log.isInfoEnabled()) {
                        log.info("[Vrml.CloudRuntimes][ResourcesConfigurationLoader] load configuration [{}] success, file is [{}]",
                                configName, Serialization.toJsonSafe(file));
                    }
                    return file;
                })
                .recover(throwable -> {
                    if (log.isWarnEnabled()) {
                        log.warn("[Vrml.CloudRuntimes][ResourcesConfigurationLoader] load configuration [{}] error",
                                configName, throwable);
                    }
                    return null;
                })
                .get();
        return Optional.ofNullable(result);
    }
}
