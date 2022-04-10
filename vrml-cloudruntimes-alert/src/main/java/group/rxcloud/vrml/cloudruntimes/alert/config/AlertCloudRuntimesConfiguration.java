package group.rxcloud.vrml.cloudruntimes.alert.config;

import group.rxcloud.vrml.alert.actor.AlertMessage;
import group.rxcloud.vrml.alert.config.AlertConfiguration;
import group.rxcloud.vrml.cloudruntimes.infrastructure.configuration.CloudRuntimesAndResourcesConfiguration;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static group.rxcloud.vrml.cloudruntimes.alert.config.Settings.ALERT_ASYNC;
import static group.rxcloud.vrml.cloudruntimes.alert.config.Settings.CONFIG_FILE_NAME;

/**
 * The Alert cloud runtimes configuration.
 */
@Component
public class AlertCloudRuntimesConfiguration extends CloudRuntimesAndResourcesConfiguration<Properties>
        implements AlertConfiguration {

    /**
     * Alert only open when config is 'true'.
     */
    public static final String ALERT_OPEN_TRUE = "true";

    /**
     * Instantiates a new Alert cloud runtimes configuration.
     */
    public AlertCloudRuntimesConfiguration() {
        super(CONFIG_FILE_NAME);
    }

    @Override
    public boolean isAlertAsync() {
        String value = config.getProperty(ALERT_ASYNC, "");
        return ALERT_OPEN_TRUE.equalsIgnoreCase(value);
    }

    @Override
    public boolean isAlertEnable(AlertMessage message) {
        String simpleName = message.getClass().getSimpleName();
        String value = config.getProperty(simpleName, "");
        return ALERT_OPEN_TRUE.equalsIgnoreCase(value);
    }
}
