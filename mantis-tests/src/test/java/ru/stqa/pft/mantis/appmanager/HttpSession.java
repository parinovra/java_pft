package ru.stqa.pft.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
    private CloseableHttpClient httpclient;
    private ApplicationManager app;

    public HttpSession(ApplicationManager app) {
        this.app = app;
        httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build(); //здесь создается новая сессия, новый клиент
    }

    public boolean login(String username, String password) throws IOException {
        HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php");
        List<NameValuePair> params = new ArrayList<>(); //<~>
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("secure_session", "on"));
        params.add(new BasicNameValuePair("return", "index.php"));
        post.setEntity(new UrlEncodedFormEntity(params)); //упаковка параметров и помещение в post
        CloseableHttpResponse response = httpclient.execute(post);
        String body = geTextForm(response);
        //проверка, действительно ли пользователь вошел, содержит ли код страницы такую строку
        return body.contains(String.format("<span class=\"user-info\">%s</span>", username));
    }

    private String geTextForm(CloseableHttpResponse response) throws IOException {
        try {
            return EntityUtils.toString(response.getEntity()); //анализ response
        } finally {
            response.close();
        }
    }

    //проверка, какой юзер залогинен в системе
    public boolean isLoggedInAs(String username) throws IOException {
        HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php");
        CloseableHttpResponse response = httpclient.execute(get);
        String body = geTextForm(response);
        return body.contains(String.format("<span class=\"user-info\">%s</span>", username));
    }
}