
package Controlador;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
        private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();

        } catch (HibernateException ex) {
            System.err.println("Error al crear la conf de hibernate: " + ex.getMessage());
            throw new ExceptionInInitializerError();
        }
    }

    public static SessionFactory getSession() {
        return sessionFactory;
    }
}
