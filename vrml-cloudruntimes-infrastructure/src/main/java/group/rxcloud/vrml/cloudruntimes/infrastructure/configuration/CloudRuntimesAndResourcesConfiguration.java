package group.rxcloud.vrml.cloudruntimes.infrastructure.configuration;

import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

/**
 * The Cloud runtimes and resources configuration.
 *
 * @param <T> the type parameter
 */
@Slf4j
@Component
public abstract class CloudRuntimesAndResourcesConfiguration<T> {

    @Autowired
    protected CloudRuntimesConfigurationSubscriber cloudRuntimesConfigurationSubscriber;
    @Autowired
    protected ResourcesConfigurationLoader resourcesConfigurationLoader;

    /**
     * The Config name.
     */
    protected String configName;
    /**
     * The Config type.
     */
    protected Class<T> configType;

    /**
     * The Config.
     */
    protected T config;

    /**
     * Instantiates a new Cloud runtimes and resources configuration.
     *
     * @param configName the config name
     */
    public CloudRuntimesAndResourcesConfiguration(String configName) {
        this.configName = configName;
        this.configType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        // load resources file firstly
        Optional<T> loadResourcesConfiguration =
                this.resourcesConfigurationLoader.loadResourcesConfiguration(configName, configType);
        loadResourcesConfiguration.ifPresent(value -> this.config = value);

        // subscribe configuration secondly
        Optional<Tuple2<T, Flux<T>>> subscribeConfiguration =
                this.cloudRuntimesConfigurationSubscriber.subscribeConfiguration(configName, configType);
        subscribeConfiguration.ifPresent(tuple2 -> {
            this.config = tuple2._1();
            tuple2._2().subscribe(newProperties -> this.config = newProperties);
        });

        // check configuration
        if (this.getConfig() == null) {
            this.doHookWhenConfigEmpty();
        }
    }

    /**
     * Spring will interrupt when config not found default.
     * But you can rewrite this hook(), do your interrupt logic.
     */
    protected void doHookWhenConfigEmpty() {
        final String ERROR = "[Vrml.CloudRuntimes][CloudRuntimesConfigurationSubscriber] " +
                "[" + this.getClass().getSimpleName() + "] " +
                "load configuration [" + configName + "] empty!";
        if (log.isErrorEnabled()) {
            log.error(ERROR);
        }
        throw new IllegalStateException(ERROR);
    }

    /**
     * Gets config.
     *
     * @return the config
     */
    protected T getConfig() {
        return config;
    }
}
