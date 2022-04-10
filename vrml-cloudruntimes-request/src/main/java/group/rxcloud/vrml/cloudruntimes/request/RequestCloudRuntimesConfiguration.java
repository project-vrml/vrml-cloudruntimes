package group.rxcloud.vrml.cloudruntimes.request;

import group.rxcloud.vrml.cloudruntimes.infrastructure.configuration.CloudRuntimesAndResourcesConfiguration;
import group.rxcloud.vrml.request.config.RequestConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static group.rxcloud.vrml.cloudruntimes.request.Settings.CONFIG_FILE_NAME;

/**
 * The Request cloud runtimes configuration.
 */
@Component
public class RequestCloudRuntimesConfiguration extends CloudRuntimesAndResourcesConfiguration<Settings.RequestReportConfigs>
        implements RequestConfiguration {

    /**
     * Instantiates a new Request cloud runtimes configuration.
     */
    public RequestCloudRuntimesConfiguration() {
        super(CONFIG_FILE_NAME);
    }

    @Override
    public RequestReportConfig getRequestReportConfig(String requestName) {
        if (requestName == null) {
            return null;
        }
        List<RequestReportConfig> entities = config.getEntities();
        if (CollectionUtils.isEmpty(entities)) {
            return null;
        }
        return entities.stream()
                .filter(requestReportConfig -> requestName.equalsIgnoreCase(requestReportConfig.requestReportName()))
                .findFirst()
                .orElse(null);
    }
}
