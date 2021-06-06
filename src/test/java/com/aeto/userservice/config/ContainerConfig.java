package com.aeto.userservice.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class ContainerConfig extends PostgreSQLContainer<ContainerConfig> {
    private static final String IMAGE_VERSION = "postgres:11.1";
    private static ContainerConfig container;

    private ContainerConfig() {
        super(IMAGE_VERSION);
    }

    public static ContainerConfig getInstance() {
        if (container == null) {
            container = new ContainerConfig();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
