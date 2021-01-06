package ccbus.system.query;
import org.hibernate.cfg.AvailableSettings;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.TimeZone;

public class JPAUtil {
    private static String PERSISTENCE_UNIT_NAME = "ccbus.system.query.jpa";
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }

    public static void setPersistenceUnitName(String persistenceUnitName)
    {
        PERSISTENCE_UNIT_NAME = persistenceUnitName;
    }
}