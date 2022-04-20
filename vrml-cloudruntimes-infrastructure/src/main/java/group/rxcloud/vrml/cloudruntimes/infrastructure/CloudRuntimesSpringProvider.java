package group.rxcloud.vrml.cloudruntimes.infrastructure;

import group.rxcloud.cloudruntimes.domain.core.ConfigurationRuntimes;
import group.rxcloud.cloudruntimes.domain.core.InvocationRuntimes;

/**
 * The Cloud runtimes provider.
 */
public interface CloudRuntimesSpringProvider {

    // -- Cloud Runtimes Configuration

    /**
     * Gets configuration store name.
     *
     * @return the configuration store name
     */
    String getConfigurationStoreName();

    /**
     * Gets configuration app id.
     *
     * @return the configuration app id
     */
    String getConfigurationAppId();

    /**
     * Get configuration runtimes from Spring.
     *
     * @return the configuration runtimes
     */
    ConfigurationRuntimes configurationRuntimes();

    // -- Cloud Runtimes RPC

    /**
     * Get invocation runtimes from Spring.
     *
     * @return the invocation runtimes
     */
    InvocationRuntimes invocationRuntimes();

    // -- Other Cloud Runtimes
}
