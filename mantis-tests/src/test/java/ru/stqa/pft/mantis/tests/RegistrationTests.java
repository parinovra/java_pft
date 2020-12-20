package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

    //закомментить (//@BeforeMethod) для james, раскомментить для встроенного
    //@BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testRegistration() throws IOException, MessagingException {
        long now = System.currentTimeMillis(); //возвращает текущее время в мс от 01.01.1970
        String user = String.format("user%s", now);
        String password = "password";
        String email = String.format("user%s@localhost", now); //первый параметр - шаблон, второй - переменная
        app.james().createUser(user, password);
        app.registration().start(user, email);

        //переключить на встроенный? 1 письмо, а не 2?
        //здесь получаем почту встроенным почтовым клиентом
        //List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);

        //здесь получаем почту внешним почтовым клиентом
        List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, password);
        assertTrue(app.newSession().login(user, password));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    //закомментить (//@AfterMethod) для james, раскомментить для встроенного
    //@AfterMethod(alwaysRun = true) //чтобы тестовый почтовый сервер останавливался даже когда тест упал
    public void stopMailServer() {
        app.mail().stop();
    }
}
