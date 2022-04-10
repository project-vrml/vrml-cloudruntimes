package group.rxcloud.vrml.cloudruntimes.infrastructure;

import group.rxcloud.cloudruntimes.domain.core.ConfigurationRuntimes;

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
     * Configuration runtimes configuration runtimes.
     *
     * @return the configuration runtimes
     */
    ConfigurationRuntimes configurationRuntimes();

    // -- Other Cloud Runtimes
}
