package com.leshka_and_friends.lgvb.core.app;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final ServiceLocator INSTANCE = new ServiceLocator();
    private final Map<Class<?>, Object> services = new HashMap<>();

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        return INSTANCE;
    }

    public <T> void registerService(Class<T> type, T service) {
        services.put(type, service);
    }

    public <T> T getService(Class<T> type) {
        T service = type.cast(services.get(type));
        if (service == null) {
            throw new IllegalStateException("Service not found: " + type.getName());
        }
        return service;
    }
}
