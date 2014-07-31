import com.google.common.collect.ImmutableMap;
import controllers.Application;
import controllers.routes;
import models.Notification;
import models.dao.NotificationDao;
import org.junit.*;


import play.data.Form;
import play.mvc.*;
import play.test.*;

import play.twirl.api.Content;

import static play.data.Form.form;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.mockito.Mockito.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {


    String name = "tester";
    String surname = "kowalski";
    String email = "test@kowalski.com";
    String yob = "2014-11-11";
    String favDb = "1";
    String notes = "I like it";

    String wrongEmail = "asd@";

    NotificationDao noteDao = mock(NotificationDao.class);


    @Before
    public void setUp(){

        doNothing().when(noteDao).persist(any());

        Application.setNotificationDao(noteDao);

        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
    }

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    // view
    @Test
    public void renderTemplate() {

        final Form<Notification> notForm = form(Notification.class);

        Content html = views.html.index.render(notForm);
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Name:");
        assertThat(contentAsString(html)).contains("Surname:");
        assertThat(contentAsString(html)).contains("Email:");
        assertThat(contentAsString(html)).contains("Year of birth:");
        assertThat(contentAsString(html)).contains("Your favourite database:");
        assertThat(contentAsString(html)).contains("Notes:");
    }


    // routes
    @Test
    public void rootRoute() {

        Result result = route(fakeRequest(GET, "/"));
        assertThat(result).isNotNull();

    }

    @Test
    public void badRoute() {

        Result result = route(fakeRequest(GET, "/bad"));
        assertThat(result).isNull();

    }

    // controller
    @Test
    public void callIndex() {
        Result result = controllers.Application.index();
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void testSubmitValidationPass() {

        Result result = callAction(routes.ref.Application.add(), new FakeRequest().withFormUrlEncodedBody(ImmutableMap.of("name", name, "favDb", favDb)));
        assertThat(status(result)).isEqualTo(OK);

    }

    @Test
    public void testSubmitValidationErrors() {

        Result result = callAction(routes.ref.Application.add(), new FakeRequest().withFormUrlEncodedBody(ImmutableMap.of("name", name, "email", wrongEmail)));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);

    }
}
