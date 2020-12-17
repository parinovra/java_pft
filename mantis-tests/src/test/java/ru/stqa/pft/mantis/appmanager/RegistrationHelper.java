package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(ApplicationManager app) {
        super(app);
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[value='Зарегистрироваться']")); //Signup
    }

    public void finish(String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);

        click(By.cssSelector("button[type='Update User']"));

//        click(By.tagName("button"));
//        wd.findElement(By.tagName("button")).click();
//        click(By.cssSelector("input[value='Зарегистрироваться']")); //Signup
//        click(By.cssSelector("button[type='submit']"));
    }
}
