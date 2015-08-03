package org.webonise.multihostpoc.daoimpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jdbc.ReturningWork;
import org.slf4j.LoggerFactory;
import org.webonise.multihostpoc.dao.GenericDao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Webonise on 31/07/15.
 */
public class GenericDaoImpl<T,PK extends Serializable> implements GenericDao<T> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(GenericDaoImpl.class);
    private Class<T> type;
    private final SessionFactory sessionFactory;
    private Session session = null;

    public GenericDaoImpl(Class<T>type,SessionFactory factory){
        this.type = type;
        this.sessionFactory = factory;
    }


    @Override
    public void save(T object) {
        Session currentSession = getSession();
        prepSession(currentSession,false,false);
        currentSession.getTransaction().begin();
        currentSession.saveOrUpdate(object);
        currentSession.getTransaction().commit();
    }

    @Override
    public List<T> readAll() {
        Session currentSession = getSession();
        prepSession(currentSession,true,true);
        Criteria criteria = currentSession.createCriteria(type);
        List<T> result = (List<T>) criteria.list();
        return result;
    }

    private Session getSession(){
        if(this.session == null ){
            this.session = sessionFactory.openSession();
        }
        return  this.session;
    }

    private void prepSession(Session session,boolean readOnlyFlag,boolean autoCommitFlag) {
        try {
            SessionImpl sessionImpl = (SessionImpl) this.getSession();
            sessionImpl.connection().setReadOnly(readOnlyFlag);
            sessionImpl.connection().setAutoCommit(autoCommitFlag);
        }
        catch (Exception e){
            LOG.error("Error occured in prepping the session.");
        }
    }

}
