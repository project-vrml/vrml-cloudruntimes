package group.rxcloud.vrml.cloudruntimes.request;

import group.rxcloud.vrml.request.config.RequestConfiguration;
import lombok.Data;

import java.util.List;

public interface Settings {

    String CONFIG_FILE_NAME = "vrml-request-report-config.json";

    @Data
    class RequestReportConfigs {

        private List<RequestConfiguration.RequestReportConfig> entities;
    }
}
