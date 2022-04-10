package group.rxcloud.vrml.cloudruntimes.log;

import group.rxcloud.vrml.cloudruntimes.infrastructure.configuration.CloudRuntimesAndResourcesConfiguration;
import group.rxcloud.vrml.core.tags.Important;
import group.rxcloud.vrml.log.config.LogsConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static group.rxcloud.vrml.cloudruntimes.log.Settings.CONFIG_FILE_NAME;

/**
 * The Log cloud runtimes configuration.
 */
@Component
public class LogCloudRuntimesConfiguration extends CloudRuntimesAndResourcesConfiguration<Settings.LogConfigs>
        implements LogsConfiguration {

    /**
     * Use 'default' config when key not found.
     */
    @Important
    public static final String DEFAULT_LOG_KEY = "default";

    /**
     * Instantiates a new Log cloud runtimes configuration.
     */
    public LogCloudRuntimesConfiguration() {
        super(CONFIG_FILE_NAME);
    }

    @Override
    public boolean isTraceEnabled(String key) {
        return this.isEnabled(key, LogsConfig::isTrace);
    }

    @Override
    public boolean isDebugEnabled(String key) {
        return this.isEnabled(key, LogsConfig::isDebug);
    }

    @Override
    public boolean isInfoEnabled(String key) {
        return this.isEnabled(key, LogsConfig::isInfo);
    }

    @Override
    public boolean isWarnEnabled(String key) {
        return this.isEnabled(key, LogsConfig::isWarn);
    }

    @Override
    public boolean isErrorEnabled(String key) {
        return this.isEnabled(key, LogsConfig::isError);
    }

    private boolean isEnabled(String key, Function<LogsConfig, Boolean> func) {
        if (key == null) {
            return false;
        }
        List<LogsConfig> entities = config.getEntities();
        if (CollectionUtils.isEmpty(entities)) {
            return false;
        }
        // search config
        Optional<LogsConfig> configOptional = entities.stream()
                .filter(logsConfig -> key.equalsIgnoreCase(logsConfig.getKey()))
                .findFirst();
        if (configOptional.isPresent()) {
            LogsConfig logsConfig = configOptional.get();
            return func.apply(logsConfig);
        }
        // config not found, use default config
        else {
            if (DEFAULT_LOG_KEY.equalsIgnoreCase(key)) {
                return false;
            }
            return this.isEnabled(DEFAULT_LOG_KEY, func);
        }
    }
}
