package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.UserData;

public class ChangePasswordHelper extends HelperBase {

    public ChangePasswordHelper(ApplicationManager app) {
        super(app);
    }

    public void login(UserData user) {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"), user.getUsername());
        click(By.cssSelector("input[value='Войти']"));
        type(By.name("password"), user.getPassword());
        click(By.cssSelector("input[value='Войти']"));
    }
    public void change(int id) {
        click(By.id("menu-toggler"));
        click(By.xpath("//div[@id='sidebar']/ul/li[6]/a/span"));
        click(By.linkText("Управление пользователями"));
        click(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", id)));
        click(By.cssSelector("input[value='Сбросить пароль']"));
    }

    public void finish(String confirmationLink, String newPassword) {
        wd.get(confirmationLink);
        type(By.name("password"), newPassword);
        type(By.name("password_confirm"), newPassword);
        click(By.tagName("button"));
    }
}
