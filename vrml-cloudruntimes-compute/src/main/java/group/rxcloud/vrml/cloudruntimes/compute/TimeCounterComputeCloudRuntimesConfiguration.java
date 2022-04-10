package group.rxcloud.vrml.cloudruntimes.compute;

import group.rxcloud.vrml.cloudruntimes.infrastructure.configuration.CloudRuntimesAndResourcesConfiguration;
import group.rxcloud.vrml.compute.TimeCounterComputes;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static group.rxcloud.vrml.cloudruntimes.compute.Settings.CONFIG_FILE_NAME;

/**
 * The Time counter compute cloud runtimes configuration.
 */
@Component
public class TimeCounterComputeCloudRuntimesConfiguration extends CloudRuntimesAndResourcesConfiguration<Settings.TimeCounterComputeConfigs>
        implements TimeCounterComputes.TimeCounterComputeConfiguration {

    /**
     * Instantiates a new Time counter compute cloud runtimes configuration.
     */
    public TimeCounterComputeCloudRuntimesConfiguration() {
        super(CONFIG_FILE_NAME);
    }

    @Override
    public TimeCounterComputes.TimeCounterComputeConfig getComputeConfiguration(String key) {
        if (key == null) {
            return null;
        }
        List<TimeCounterComputes.TimeCounterComputeConfig> entities = config.getEntities();
        if (CollectionUtils.isEmpty(entities)) {
            return null;
        }
        return entities.stream()
                .filter(computeConfig -> key.equalsIgnoreCase(computeConfig.getKey()))
                .findFirst()
                .orElse(null);
    }
}
