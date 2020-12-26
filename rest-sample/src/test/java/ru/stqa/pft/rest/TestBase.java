package ru.stqa.pft.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import org.testng.SkipException;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestBase {

    public String getIssueStatus(int issueId) {
        String json = RestAssured.get("https://bugify.stqa.ru/api/issues/" + issueId + ".json").asString();
        JsonElement issues = JsonParser.parseString(json).getAsJsonObject().get("issues");
        String stataName = issues.getAsJsonArray().iterator().next()
                .getAsJsonObject().get("state_name").getAsString();
        return stataName;
    }

    public boolean isIssueOpen(int issueId) throws MalformedURLException, RemoteException {
        String issueStatus = getIssueStatus(issueId);
        if (issueStatus.equals("Resolved") || issueStatus.equals("Closed")) {
            return false; //true
        }
        return true; //false
    }

    public void skipIfNotFixed(int issueId) throws MalformedURLException, RemoteException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}
