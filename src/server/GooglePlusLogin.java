package server;

import client.Oauthfbtest;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.Person;

import java.io.InputStreamReader;
import java.util.Collections;

/**
 * Created by Gene on 7/8/2014.
 */
public class GooglePlusLogin {

    final private String APPLICATION_NAME = "Oauthfbtest";
    private GoogleClientSecrets clientSecrets;
    private HttpTransport httpTransport;
    private JsonFactory jsonFactory;

    public void g() {
        // Set up the HTTP transport and JSON factory
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            jsonFactory = JacksonFactory.getDefaultInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

// Load client secrets
        try {
            clientSecrets = GoogleClientSecrets.load(jsonFactory,
                    new InputStreamReader(Oauthfbtest.class.getResourceAsStream("/client_secrets.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

// Set up authorization code flow
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                httpTransport, jsonFactory, clientSecrets,
//                Collections.singleton(PlusScopes.PLUS_ME)).setDataStoreFactory(new D).build();

// Authorize
//        Credential credential =
//                new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//
//// Set up the main Google+ class
//        Plus plus = new Plus.Builder(httpTransport, jsonFactory, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();

// Make a request to access your profile and display it to console
//        try {
//            Person profile = plus.people().get("me").execute();
//            System.out.println("ID: " + profile.getId());
//            System.out.println("Name: " + profile.getDisplayName());
//            System.out.println("Image URL: " + profile.getImage().getUrl());
//            System.out.println("Profile URL: " + profile.getUrl());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
