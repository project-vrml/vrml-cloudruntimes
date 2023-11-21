package group.rxcloud.vrml.cloudruntimes.switchs;

import com.google.gson.JsonObject;
import group.rxcloud.vrml.cloudruntimes.infrastructure.configuration.CloudRuntimesAndResourcesConfiguration;
import group.rxcloud.vrml.core.serialization.Serialization;
import group.rxcloud.vrml.switches.SwitchesConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static group.rxcloud.vrml.cloudruntimes.switchs.Settings.CONFIG_FILE_NAME;

/**
 * The Switch cloud runtimes configuration.
 */
@Slf4j
@Component
public class SwitchCloudRuntimesConfiguration extends CloudRuntimesAndResourcesConfiguration<JsonObject>
        implements SwitchesConfiguration {

    /**
     * Instantiates a new Switch cloud runtimes configuration.
     */
    public SwitchCloudRuntimesConfiguration() {
        super(CONFIG_FILE_NAME);
    }

    @Override
    public JsonObject getParams() {
        return this.config;
    }
}
