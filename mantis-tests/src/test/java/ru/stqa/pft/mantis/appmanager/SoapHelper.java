package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {
    private final String admLgn; //добавил
    private final String admPswd; //добавил

    private ApplicationManager app;
    private String soapUrl; //добавил

    public SoapHelper(ApplicationManager app) {
        this.app = app;
        this.soapUrl = app.getProperty("web.baseUrl") + "api/soap/mantisconnect.php"; //добавил
        this.admLgn = app.getProperty("web.adminLogin"); //добавил
        this.admPswd = app.getProperty("web.adminPassword"); //добавил
    }

    public Set<Project> getProjects() throws MalformedURLException, RemoteException, ServiceException {
        MantisConnectPortType mc = getMantisConnect();
        //массив проектов
//        ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root"); //ДЗ: ПЕРЕНЕСТИ СТРОКИ В КОНФИГ. ФАЙЛ
        ProjectData[] projects = mc.mc_projects_get_user_accessible(admLgn, admPswd); //ДЗ: ПЕРЕНЕСЛИ СТРОКИ В КОНФИГ. ФАЙЛ

        //преобразуем полученные данные в модельные объекты
        return Arrays.asList(projects).stream()
                .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName())) //.intValue() для преобразования BigInteger в Int
                .collect(Collectors.toSet());
    }

    private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
//        MantisConnectPortType mc = new MantisConnectLocator()
//                .getMantisConnectPort(new URL("http://localhost/mantisbt-2.24.2/api/soap/mantisconnect.php")); //ДЗ: ПЕРЕНЕСТИ СТРОКИ В КОНФИГ. ФАЙЛ
        MantisConnectPortType mc = new MantisConnectLocator()
                .getMantisConnectPort(new URL(soapUrl)); //ДЗ: ПЕРЕНЕСЛИ СТРОКИ В КОНФИГ. ФАЙЛ
        return mc;
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
//        String[] categories = mc.mc_project_get_categories("administrator", "root", BigInteger
//                .valueOf(issue.getProject().getId()));
        String[] categories = mc.mc_project_get_categories(admLgn, admPswd, BigInteger
                .valueOf(issue.getProject().getId()));
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(new ObjectRef(BigInteger
                .valueOf(issue.getProject().getId()), issue.getProject().getName()));
        issueData.setCategory(categories[0]); //выбрать первую поавшуюсь категорию
//        BigInteger issueId = mc.mc_issue_add("administrator", "root", issueData);
        BigInteger issueId = mc.mc_issue_add(admLgn, admPswd, issueData);
//        IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId);
        IssueData createdIssueData = mc.mc_issue_get(admLgn, admPswd, issueId);

        return new Issue().withId(createdIssueData.getId().intValue())
                .withSummary(createdIssueData.getSummary()).withDescription(createdIssueData.getDescription())
                .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
                                          .withName(createdIssueData.getProject().getName()));
    }

    public String getIssueStatus(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
//        IssueData issueData = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
        IssueData issueData = mc.mc_issue_get(admLgn, admPswd, BigInteger.valueOf(issueId));
        return issueData.getStatus().getName();
    }
}
