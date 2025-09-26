package com.project.apigateway.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "services")
@Data
public class ServiceRoleMappingProperties {
    private List<Endpoint> endpoints;

    @Data
    public static class Endpoint {
        private String path;
        private String permission;
        private String method;
    }
}
