package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ChangePasswordTests extends TestBase {

    //закомментить (//@BeforeMethod) для james, раскомментить для встроенного
//    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException, MessagingException {
        app.changePassword().login(new UserData().withUsername("administrator").withPassword("root"));
        UserData user = app.db().users().stream().filter((d) -> d.getId() > 1).collect(Collectors.toList()).iterator().next();
        int id = user.getId();
        String username = user.getUsername();
        String email = user.getEmail();
        app.changePassword().change(id);
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        String newPassword = "newPassword";
        app.changePassword().finish(confirmationLink, newPassword);
        Assert.assertTrue(app.newSession().login(username, newPassword));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    //закомментить (//@AfterMethod) для james, раскомментить для встроенного
//    @AfterMethod(alwaysRun = true) //чтобы тестовый почтовый сервер останавливался даже когда тест упал
    public void stopMailServer() {
        app.mail().stop();
    }
}
