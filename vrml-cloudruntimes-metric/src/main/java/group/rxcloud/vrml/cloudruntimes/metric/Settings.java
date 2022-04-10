package group.rxcloud.vrml.cloudruntimes.metric;

import lombok.Data;

public interface Settings {

    String CONFIG_FILE_NAME = "vrml-metric-config.json";

    @Data
    class MetricConfig {

        private String topic;
        private boolean metric;
        private boolean debug;
    }
}
