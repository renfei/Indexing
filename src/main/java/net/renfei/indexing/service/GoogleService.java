package net.renfei.indexing.service;

import com.alibaba.fastjson.JSON;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.*;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import net.renfei.indexing.entity.GoogleIndexingEntity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Google提供的服务
 *
 * @author RenFei
 */
public class GoogleService {
    private final String googleJsonPath;
    private HttpTransport httpTransport;

    public GoogleService(String googleJsonPath) {
        this.googleJsonPath = googleJsonPath;
    }

    public HttpResponse update(String url) throws IOException, GeneralSecurityException {
        GoogleCredential googleCredential = getGoogleCredential("https://www.googleapis.com/auth/indexing");
        GoogleIndexingEntity googleIndexingEntity = new GoogleIndexingEntity();
        googleIndexingEntity.setUrl(url);
        googleIndexingEntity.setType("URL_UPDATED");
        return this.publish(googleCredential, googleIndexingEntity);
    }

    public HttpResponse publish(GoogleCredential googleCredential, GoogleIndexingEntity googleIndexingEntity) throws IOException {
        String endPoint = "https://indexing.googleapis.com/v3/urlNotifications:publish";
        GenericUrl genericUrl = new GenericUrl(endPoint);
        String content = JSON.toJSONString(googleIndexingEntity);
        HttpRequestFactory requestFactory = this.httpTransport.createRequestFactory();
        HttpRequest request =
                requestFactory.buildPostRequest(genericUrl, ByteArrayContent.fromString("application/json", content));
        googleCredential.initialize(request);
        return request.execute();
    }

    public GoogleCredential getGoogleCredential(String scopes) throws IOException, GeneralSecurityException {
        return getGoogleCredential(googleJsonPath, scopes);
    }

    public GoogleCredential getGoogleCredential(String path, String scopes) throws IOException, GeneralSecurityException {
        return getGoogleCredential(new FileInputStream(path), scopes);
    }

    public GoogleCredential getGoogleCredential(InputStream in, String scopes) throws IOException, GeneralSecurityException {
        JsonFactory jsonFactory = new JacksonFactory();
        initHttpTransport();
        return GoogleCredential.fromStream(in, this.httpTransport, jsonFactory).createScoped(Collections.singleton(scopes));
    }

    private void initHttpTransport() throws GeneralSecurityException, IOException {
        if (this.httpTransport == null) {
            this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        }
    }
}
