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

    @Override
    public boolean checkSwitches(List<String> switchKeys) {
        if (CollectionUtils.isEmpty(switchKeys)) {
            return false;
        }
        JsonObject params = this.getParams();
        try {
            for (int i = 0; i < switchKeys.size(); i++) {
                String key = switchKeys.get(i);
                params = params.getAsJsonObject(key);
                if (params == null) {
                    return false;
                }
                if (i == switchKeys.size() - 1) {
                    return params.getAsBoolean();
                }
            }
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("[Vrml.CloudRuntimes][SwitchCloudRuntimesConfiguration] check switches[{}] error",
                        Serialization.GSON.toJson(switchKeys), e);
            }
        }
        return false;
    }
}
