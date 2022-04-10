package group.rxcloud.vrml.cloudruntimes.metric;

import group.rxcloud.vrml.cloudruntimes.infrastructure.configuration.CloudRuntimesAndResourcesConfiguration;
import group.rxcloud.vrml.metric.config.MetricConfiguration;
import io.vavr.Function3;

import java.util.Map;

import static group.rxcloud.vrml.cloudruntimes.metric.Settings.CONFIG_FILE_NAME;

/**
 * The Abstract metric cloud runtimes configuration.
 */
public abstract class AbstractMetricCloudRuntimesConfiguration extends CloudRuntimesAndResourcesConfiguration<Settings.MetricConfig>
        implements MetricConfiguration {

    /**
     * Instantiates a new Abstract metric cloud runtimes configuration.
     */
    public AbstractMetricCloudRuntimesConfiguration() {
        super(CONFIG_FILE_NAME);
    }

    @Override
    public String topic() {
        return this.config.getTopic();
    }

    @Override
    public Function3<Throwable, Map<String, String>, Map<String, String>, Void> metricException() {
        return (throwable, indexes, stores) -> {
            this.doMetricException(throwable, indexes, stores);
            return null;
        };
    }

    /**
     * Do metric exception.
     *
     * @param throwable the throwable
     * @param indexes   the indexes
     * @param stores    the stores
     */
    protected abstract void doMetricException(Throwable throwable, Map<String, String> indexes, Map<String, String> stores);

    @Override
    public Function3<String, Map<String, String>, Map<String, String>, Void> metricFinally() {
        return (topic, indexes, stores) -> {
            this.doMetricFinally(topic, indexes, stores);
            return null;
        };
    }

    /**
     * Do metric finally.
     *
     * @param topic   the topic
     * @param indexes the indexes
     * @param stores  the stores
     */
    protected abstract void doMetricFinally(String topic, Map<String, String> indexes, Map<String, String> stores);

    @Override
    public boolean metricSwitch() {
        return this.config.isMetric();
    }

    @Override
    public boolean debugSwitch() {
        return this.config.isDebug();
    }
}
