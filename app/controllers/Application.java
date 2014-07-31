package controllers;

import models.Notification;

import models.dao.NotificationDao;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;


import views.html.*;


import static play.data.Form.form;

public class Application extends Controller {

    final static Form<Notification> notForm = form(Notification.class);

    private static NotificationDao noteDao;

    private static NotificationDao getNotificationDao (){

        if(noteDao == null)
            return new NotificationDao();

        return noteDao;
    }

    public static void setNotificationDao(NotificationDao dao){
        Application.noteDao = dao;
    }

    public static Result index() {

        return ok(index.render(notForm));
    }

    @Transactional
    public static Result add() {
        Form<Notification> filledForm = notForm.bindFromRequest();

        if(filledForm.hasErrors()){
            return badRequest(index.render(filledForm));
        }

        Notification created = filledForm.get();

        getNotificationDao().persist(created);

        return ok(notes.render(created));
    }

}
