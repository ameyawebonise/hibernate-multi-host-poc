package org.webonise.multihostpoc.session;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager
{
    private static final Logger LOG = LoggerFactory.getLogger(SessionManager.class);
    private final SessionFactory sessionFactory;

    public SessionManager() {
        sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration = configuration.addResource("hibernate.cfg.xml");
            configuration = configuration.configure();
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            LOG.error("Exception thrown while creating session factory {}", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void shutdown() {
        // Close caches and connection pools
        sessionFactory.close();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
