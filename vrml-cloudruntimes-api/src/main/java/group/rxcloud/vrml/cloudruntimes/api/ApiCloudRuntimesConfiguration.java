package group.rxcloud.vrml.cloudruntimes.api;

import group.rxcloud.vrml.api.intercept.config.ApiLogConfiguration;
import group.rxcloud.vrml.cloudruntimes.infrastructure.configuration.CloudRuntimesAndResourcesConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;

import static group.rxcloud.vrml.cloudruntimes.api.Settings.CONFIG_FILE_NAME;

/**
 * The Api cloud runtimes configuration.
 */
@Component
public class ApiCloudRuntimesConfiguration extends CloudRuntimesAndResourcesConfiguration<Settings.ApiConfig>
        implements ApiLogConfiguration {

    /**
     * Instantiates a new Api cloud runtimes configuration.
     */
    public ApiCloudRuntimesConfiguration() {
        super(CONFIG_FILE_NAME);
    }

    @Override
    public boolean isOpenRequestLog(String logsKey) {
        return this.isOpen(logsKey, Settings.ApiConfigEntity::isOpenRequestLog);
    }

    @Override
    public boolean isOpenResponseLog(String logsKey) {
        return this.isOpen(logsKey, Settings.ApiConfigEntity::isOpenResponseLog);
    }

    @Override
    public boolean isOpenErrorLog(String logsKey) {
        return this.isOpen(logsKey, Settings.ApiConfigEntity::isOpenErrorLog);
    }

    private boolean isOpen(String key, Function<Settings.ApiConfigEntity, Boolean> func) {
        if (key == null) {
            return false;
        }
        List<Settings.ApiConfigEntity> entities = config.getEntities();
        if (CollectionUtils.isEmpty(entities)) {
            return false;
        }
        return entities.stream()
                .filter(apiConfigEntity -> key.equalsIgnoreCase(apiConfigEntity.getLogsKey()))
                .findFirst()
                .map(func)
                .orElse(false);
    }
}
