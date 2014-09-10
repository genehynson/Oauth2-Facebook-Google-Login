package client;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.api.gwt.oauth2.client.Callback;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusRequestInitializer;
import com.google.api.services.plus.model.Person;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Gene on 7/7/2014.
 */
public class Oauthfbtest implements EntryPoint {

    private static final String FACEBOOK_AUTH_URL = "https://www.facebook.com/dialog/oauth";
    private static final String FACEBOOK_CLIENT_ID = "675910869150098";
    private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    //https://accounts.google.com/o/oauth2/auth?scope=email&profile&state=%2Fprofile&redirect_uri=http://127.0.0.1:8888/Oauthfbtest/callback&response_type=token&client_id=482524296107-ij08oi0uhptc6t6pr84ughcb5pvgi5i5.apps.googleusercontent.com
    private static final String GOOGLE_CLIENT_ID = "482524296107-ij08oi0uhptc6t6pr84ughcb5pvgi5i5.apps.googleusercontent.com";
    private static final String APPLICATION_NAME = "Oauthfbtest";

    private HttpTransport httpTransport;
    private JsonFactory jsonFactory;

    private Button fbLogin, gLogin, getMe;
    private Label status;
    private TextArea resultText;

    public void onModuleLoad() {
        status = new Label();
        resultText = new TextArea();
        resultText.setText("Result goes here...");
        resultText.setWidth("600px");
        resultText.setHeight("600px");

        fbLogin = new Button("Login with Facebook");
        fbLogin.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                status.setText("...");
                resultText.setText("Result goes here...");
                loginWithFacebook();
            }
        });
        gLogin = new Button("Login with Google");
        gLogin.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                status.setText("...");
                resultText.setText("Result goes here...");
                loginWithGoogle();
            }
        });
        getMe = new Button("Get Me");
        getMe.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getMe();
            }
        });

        RootPanel.get().add(fbLogin);
        RootPanel.get().add(gLogin);
        RootPanel.get().add(getMe);
        RootPanel.get().add(status);
        RootPanel.get().add(resultText);
    }

    public void loginWithGoogle() {
        final AuthRequest req = new AuthRequest(GOOGLE_AUTH_URL,
                GOOGLE_CLIENT_ID)
                .withScopes("https://www.googleapis.com/auth/plus.login");
        Auth.get().clearAllTokens();
        Auth.get().login(req, new Callback<String, Throwable>() {
            @Override
            public void onSuccess(String token) {
                try {
                    requestGoogleInformation(token);
                    status.setText("Logged in with google successfully");
                } catch (RequestException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                resultText.setText("Declined or failed :(");
                GWT.log("Log in declined or failed");
            }
        });

    }

    public void loginWithFacebook() {
        final AuthRequest req = new AuthRequest(FACEBOOK_AUTH_URL,
                FACEBOOK_CLIENT_ID).withScopes("email", "user_birthday", "user_friends").withScopeDelimiter(",");
        Auth.get().clearAllTokens();
        Auth.get().login(req, new Callback<String, Throwable>() {
            @Override
            public void onSuccess(String token) {
                try {
                    requestFacebookInformation(token);
                    status.setText("Logged in with facebook successfully");
                } catch (RequestException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                resultText.setText("Declined or failed :(");
                GWT.log("Log in declined or failed");
            }
        });
    }

    public void requestFacebookInformation(String token) throws RequestException {

        String url = "https://graph.facebook.com/me?access_token=" + token;
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
                URL.encode(url));
        try {
            Request request = builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
//                    -- Call failed
                    GWT.log("There was an error...no response");
                }

                public void onResponseReceived(Request request, Response response) {
                    if (Response.SC_OK == response.getStatusCode()) {
//                        --- Retrieve details from a JSON string.
                        GWT.log("We got a valid string!");
                       resultText.setText(response.getText());
                    } else if (Response.SC_BAD_REQUEST == response.getStatusCode()) {
                        GWT.log("bad request");
                    } else {
                        GWT.log("idk what happened lol");
                    }
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    public void requestGoogleInformation(String token) throws RequestException {

        String url = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + token;
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
                URL.encode(url));
        try {
            Request request = builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
//                    -- Call failed
                    GWT.log("There was an error...no response");
                }

                public void onResponseReceived(Request request, Response response) {
                    if (Response.SC_OK == response.getStatusCode()) {
//                        --- Retrieve details from a JSON string.
                        GWT.log("We got a valid string!");
                        resultText.setText(response.getText());
                    } else if (Response.SC_BAD_REQUEST == response.getStatusCode()) {
                        GWT.log("bad request");
                    } else {
                        GWT.log("idk what happened lol");
                    }
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    public void getMe() {
        // Set up the HTTP transport and JSON factory
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            jsonFactory = JacksonFactory.getDefaultInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Plus plus = new Plus.Builder(httpTransport, jsonFactory, null).setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new PlusRequestInitializer(GOOGLE_CLIENT_ID)).build();

        try {
            Person profile = plus.people().get("me").execute();
            System.out.println("ID: " + profile.getId());
            System.out.println("Name: " + profile.getDisplayName());
            System.out.println("Image URL: " + profile.getImage().getUrl());
            System.out.println("Profile URL: " + profile.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
