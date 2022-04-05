package group.rxcloud.vrml.cloudruntimes.api;

import lombok.Data;

import java.util.List;

public interface Settings {

    String CONFIG_FILE_NAME = "vrml-api-config.json";

    @Data
    class ApiConfig {

        private List<ApiConfigEntity> entities;
    }

    @Data
    class ApiConfigEntity {

        private String logsKey;
        private boolean openRequestLog;
        private boolean openResponseLog;
        private boolean openErrorLog;
    }
}
