package com.chatbot.util;
 
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class HibernateUtil {
 
    private static SessionFactory sessionFactoryObj = buildSessionFactoryObj();
 
    // Create The SessionFactory Object From Standard (Hibernate.cfg.xml) Configuration File
    public static SessionFactory buildSessionFactoryObj() {
        try {
            sessionFactoryObj = new Configuration().configure().buildSessionFactory();
        } catch (ExceptionInInitializerError exceptionObj) {
            exceptionObj.printStackTrace();
        }
        return sessionFactoryObj;
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactoryObj;
    }
}
