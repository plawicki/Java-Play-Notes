package models.dao;

import models.Notification;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;

/**
 * Created by patrykl on 2014-07-31.
 */
public class NotificationDao {

    private EntityManager getEntityManager(){
        return JPA.em();
    }

    public void persist(Notification note){

        getEntityManager().persist(note);
    }

}
