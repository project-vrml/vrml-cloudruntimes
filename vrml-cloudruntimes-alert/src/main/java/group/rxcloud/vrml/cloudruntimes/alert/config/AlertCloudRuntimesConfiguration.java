package group.rxcloud.vrml.cloudruntimes.alert.config;

import group.rxcloud.vrml.alert.actor.AlertMessage;
import group.rxcloud.vrml.alert.config.AlertConfiguration;
import group.rxcloud.vrml.cloudruntimes.infrastructure.CloudRuntimesAndResourcesConfiguration;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static group.rxcloud.vrml.cloudruntimes.alert.config.Settings.ALERT_ASYNC;
import static group.rxcloud.vrml.cloudruntimes.alert.config.Settings.CONFIG_FILE_NAME;

@Component
public class AlertCloudRuntimesConfiguration extends CloudRuntimesAndResourcesConfiguration<Properties>
        implements AlertConfiguration {

    public AlertCloudRuntimesConfiguration() {
        super(CONFIG_FILE_NAME);
    }

    @Override
    public boolean isAlertAsync() {
        String value = config.getProperty(ALERT_ASYNC);
        return "true".equalsIgnoreCase(value);
    }

    @Override
    public boolean isAlertEnable(AlertMessage message) {
        String simpleName = message.getClass().getSimpleName();
        String value = config.getProperty(simpleName);
        return "true".equalsIgnoreCase(value);
    }
}
