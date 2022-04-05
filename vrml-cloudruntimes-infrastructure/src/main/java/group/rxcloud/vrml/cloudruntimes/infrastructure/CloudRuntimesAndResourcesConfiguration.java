package group.rxcloud.vrml.cloudruntimes.infrastructure;

import io.vavr.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

@Component
public abstract class CloudRuntimesAndResourcesConfiguration<T> {

    @Autowired
    protected CloudRuntimesConfigurationSubscriber configurationSubscriber;
    @Autowired
    protected ResourcesConfigurationLoader configurationLoader;

    protected String configName;
    protected Class<T> configType;

    protected T config;

    public CloudRuntimesAndResourcesConfiguration(String configName) {
        this.configName = configName;
        this.configType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @PostConstruct
    public void init() {
        // load resources file firstly
        Optional<T> loadResourcesConfiguration =
                this.configurationLoader.loadResourcesConfiguration(configName, configType);
        loadResourcesConfiguration.ifPresent(value -> this.config = value);

        // subscribe configuration secondly
        Optional<Tuple2<T, Flux<T>>> subscribeConfiguration =
                this.configurationSubscriber.subscribeConfiguration(configName, configType);
        subscribeConfiguration.ifPresent(tuple2 -> {
            this.config = tuple2._1();
            tuple2._2().subscribe(newProperties -> this.config = newProperties);
        });
    }

    protected T getConfig() {
        return config;
    }
}
