package group.rxcloud.vrml.cloudruntimes.log;

import group.rxcloud.vrml.log.config.LogsConfiguration;
import lombok.Data;

import java.util.List;

public interface Settings {

    String CONFIG_FILE_NAME = "vrml-log-config.json";

    @Data
    class LogConfigs {

        private List<LogsConfiguration.LogsConfig> entities;
    }
}
