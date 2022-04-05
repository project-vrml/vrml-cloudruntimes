package group.rxcloud.vrml.cloudruntimes.infrastructure;

import group.rxcloud.cloudruntimes.domain.core.ConfigurationRuntimes;

public interface CloudRuntimesProvider {

    String getConfigurationStoreName();
    String getConfigurationAppId();

    ConfigurationRuntimes configurationRuntimes();
}
