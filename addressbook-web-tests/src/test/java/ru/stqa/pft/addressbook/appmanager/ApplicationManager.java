package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    private final GroupHelper groupHelper = new GroupHelper();

    public void init() {
        groupHelper.wd = new FirefoxDriver();
        groupHelper.wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        groupHelper.wd.get("http://localhost/addressbook/");
        login("admin", "secret");
    }

    private void login(String username, String password) {
      groupHelper.wd.findElement(By.name("user")).click();
      groupHelper.wd.findElement(By.name("user")).clear();
      groupHelper.wd.findElement(By.name("user")).sendKeys(username);
      groupHelper.wd.findElement(By.name("pass")).click();
      groupHelper.wd.findElement(By.name("pass")).clear();
      groupHelper.wd.findElement(By.name("pass")).sendKeys(password);
      groupHelper.wd.findElement(By.xpath("//input[@value='Login']")).click();
    }

    //группы
    public void gotoGroupPage() {
        groupHelper.wd.findElement(By.linkText("groups")).click();
    }

    public void stop() {
        groupHelper.wd.findElement(By.linkText("Logout")).click();
        groupHelper.wd.quit();
    }

    public GroupHelper getGroupHelper() {
        return groupHelper;
    }
}
