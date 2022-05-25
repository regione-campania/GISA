/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.hibernate;

import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.apache.commons.logging.*;

public class HibernateFactory {
    private static SessionFactory sessionFactory;
    private static SessionFactory sessionFactoryCanina;
    private static SessionFactory sessionFactoryFelina;
    private static SessionFactory sessionFactoryDocumentale;
    private static Log log = LogFactory.getLog(HibernateFactory.class);

    /**
     * Constructs a new Singleton SessionFactory
     * @return
     * @throws HibernateException
     */
    public static SessionFactory buildSessionFactory() throws HibernateException {
        if (sessionFactory != null) {
            closeFactory();
        }
        return configureSessionFactory();
    }

    /**
     * Builds a SessionFactory, if it hasn't been already.
     */
    public static SessionFactory buildIfNeeded() throws DataAccessLayerException{
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            return sessionFactory;
        }
        try {
            return configureSessionFactory();
        } catch (HibernateException e) {
            throw new DataAccessLayerException(e);
        }
    }
    
    public static SessionFactory buildIfNeededCanina() throws DataAccessLayerException{
        if (sessionFactoryCanina != null && !sessionFactory.isClosed()) {
            return sessionFactoryCanina;
        }
        try {
            return configureSessionFactoryCanina();
        } catch (HibernateException e) {
            throw new DataAccessLayerException(e);
        }
    }
    
    public static SessionFactory buildIfNeededFelina() throws DataAccessLayerException{
        if (sessionFactoryFelina != null && !sessionFactory.isClosed()) {
            return sessionFactoryFelina;
        }
        try {
            return configureSessionFactoryFelina();
        } catch (HibernateException e) {
            throw new DataAccessLayerException(e);
        }
    }
    
    public static SessionFactory buildIfNeededDocumentale() throws DataAccessLayerException{
        if (sessionFactoryDocumentale != null && !sessionFactory.isClosed()) {
            return sessionFactoryDocumentale;
        }
        try {
            return configureSessionFactoryDocumentale();
        } catch (HibernateException e) {
            throw new DataAccessLayerException(e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    

    public static Session openSession() throws HibernateException {
        buildIfNeeded();
        return sessionFactory.openSession();
    }
    
    public static Session openSessionCanina() throws HibernateException {
        buildIfNeededCanina();
        return sessionFactoryCanina.openSession();
    }
    
    public static Session openSessionFelina() throws HibernateException {
        buildIfNeededFelina();
        return sessionFactoryFelina.openSession();
    }
    
    public static Session openSessionDocumentale() throws HibernateException {
        buildIfNeededDocumentale();
        return sessionFactoryDocumentale.openSession();
    }

    public static void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (HibernateException ignored) {
                log.error("Couldn't close SessionFactory", ignored);
            }
        }
    }

    public static void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException ignored) {
                log.error("Couldn't close Session", ignored);
            }
        }
    }

    public static void rollback(Transaction tx) {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (HibernateException ignored) {
            log.error("Couldn't rollback Transaction", ignored);
        }
    }
    /**
     *
     * @return
     * @throws HibernateException
     */
    private static SessionFactory configureSessionFactory() throws HibernateException {
        
    	
    	AnnotationConfiguration	annotationConfiguration	= new AnnotationConfiguration().configure();
    	sessionFactory			= annotationConfiguration.buildSessionFactory();
    	
    	
//    	Configuration configuration = new Configuration();
//        configuration.configure();
//        sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }
    
    private static SessionFactory configureSessionFactoryCanina() throws HibernateException {
        
    	
    	AnnotationConfiguration	annotationConfiguration	= new AnnotationConfiguration().configure("hibernateCanina.cfg.xml");
    	sessionFactoryCanina			= annotationConfiguration.buildSessionFactory();
    	
    	
//    	Configuration configuration = new Configuration();
//        configuration.configure();
//        sessionFactory = configuration.buildSessionFactory();
        return sessionFactoryCanina;
    }
    
    private static SessionFactory configureSessionFactoryFelina() throws HibernateException {
        
    	
    	AnnotationConfiguration	annotationConfiguration	= new AnnotationConfiguration().configure("hibernateFelina.cfg.xml");
    	sessionFactoryFelina			= annotationConfiguration.buildSessionFactory();
    	
    	
//    	Configuration configuration = new Configuration();
//        configuration.configure();
//        sessionFactory = configuration.buildSessionFactory();
        return sessionFactoryFelina;
    }
    
    private static SessionFactory configureSessionFactoryDocumentale() throws HibernateException {
        
    	
    	AnnotationConfiguration	annotationConfiguration	= new AnnotationConfiguration().configure("hibernateDocumentale.cfg.xml");
    	sessionFactoryDocumentale			= annotationConfiguration.buildSessionFactory();
    	
    	
//    	Configuration configuration = new Configuration();
//        configuration.configure();
//        sessionFactory = configuration.buildSessionFactory();
        return sessionFactoryDocumentale;
    }
}

