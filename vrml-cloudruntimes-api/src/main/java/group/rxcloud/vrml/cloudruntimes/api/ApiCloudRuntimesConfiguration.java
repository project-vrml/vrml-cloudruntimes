package group.rxcloud.vrml.cloudruntimes.api;

import group.rxcloud.vrml.api.intercept.config.ApiLogConfiguration;
import group.rxcloud.vrml.cloudruntimes.infrastructure.CloudRuntimesAndResourcesConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static group.rxcloud.vrml.cloudruntimes.api.Settings.CONFIG_FILE_NAME;

@Component
public class ApiCloudRuntimesConfiguration extends CloudRuntimesAndResourcesConfiguration<Settings.ApiConfig>
        implements ApiLogConfiguration {

    public ApiCloudRuntimesConfiguration() {
        super(CONFIG_FILE_NAME);
    }

    @Override
    public boolean isOpenRequestLog(String logsKey) {
        List<Settings.ApiConfigEntity> entities = config.getEntities();
        if (CollectionUtils.isEmpty(entities)) {
            return false;
        }
        return entities.stream().filter(apiConfigEntity -> logsKey.equalsIgnoreCase(apiConfigEntity.getLogsKey()))
                .findFirst()
                .map(Settings.ApiConfigEntity::isOpenRequestLog)
                .orElse(false);
    }

    @Override
    public boolean isOpenResponseLog(String logsKey) {
        List<Settings.ApiConfigEntity> entities = config.getEntities();
        if (CollectionUtils.isEmpty(entities)) {
            return false;
        }
        return entities.stream().filter(apiConfigEntity -> logsKey.equalsIgnoreCase(apiConfigEntity.getLogsKey()))
                .findFirst()
                .map(Settings.ApiConfigEntity::isOpenResponseLog)
                .orElse(false);
    }

    @Override
    public boolean isOpenErrorLog(String logsKey) {
        List<Settings.ApiConfigEntity> entities = config.getEntities();
        if (CollectionUtils.isEmpty(entities)) {
            return false;
        }
        return entities.stream().filter(apiConfigEntity -> logsKey.equalsIgnoreCase(apiConfigEntity.getLogsKey()))
                .findFirst()
                .map(Settings.ApiConfigEntity::isOpenErrorLog)
                .orElse(false);
    }
}
