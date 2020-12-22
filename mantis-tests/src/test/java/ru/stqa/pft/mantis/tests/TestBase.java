package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestBase {

    protected static ApplicationManager app; //первый тянется из конфига, а второй дефолтный на случай отсутствия в конфиге

    static {
        try {
            app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
        app.ftp().upload(
                new File("src/test/resources/config_inc.php"),
                "config_inc.php",
                "config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.ftp().restore(
                "config_inc.php.bak",
                "config_inc.php");
        app.stop();
    }

    public boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        String issueStatus = app.soap().getIssueStatus(issueId);
        if (issueStatus.equals("resolved") || issueStatus.equals("closed")) {
            return false; //true
        }
        return true; //false
    }


    public void skipIfNotFixed(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}
