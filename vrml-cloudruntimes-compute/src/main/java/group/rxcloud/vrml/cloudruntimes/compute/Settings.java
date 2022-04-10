package group.rxcloud.vrml.cloudruntimes.compute;

import group.rxcloud.vrml.compute.TimeCounterComputes;
import lombok.Data;

import java.util.List;

public interface Settings {

    String CONFIG_FILE_NAME = "vrml-compute-timecounter-config.json";

    @Data
    class TimeCounterComputeConfigs {

        private List<TimeCounterComputes.TimeCounterComputeConfig> entities;
    }
}
