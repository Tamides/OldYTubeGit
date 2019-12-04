package pl.tamides.ytube.helpers;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;

import pl.tamides.ytube.App;

public class GoogleAutorizationHelper {

    private static final String CLIENT_ID = "284339421370-d4dn7lj15k9b5gtd6k0g772kmnn1inm1.apps.googleusercontent.com";
    private static final String AUTHORIZATION_ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String TOKEN_ENDPOINT = "https://www.googleapis.com/oauth2/v4/token";
    private static final String REDIRECT_URI_STRING = "pl.tamides.ytube:/oauth2callback";
    private static final String AUTORIZATION_SCOPE = "https://www.googleapis.com/auth/youtube";

    private static volatile GoogleAutorizationHelper instance = null;

    private AuthorizationRequest authorizationRequest;

    public GoogleAutorizationHelper() {
        authorizationRequest = new AuthorizationRequest.Builder(
                new AuthorizationServiceConfiguration(
                        Uri.parse(AUTHORIZATION_ENDPOINT),
                        Uri.parse(TOKEN_ENDPOINT)
                ),
                CLIENT_ID,
                AuthorizationRequest.RESPONSE_TYPE_CODE,
                Uri.parse(REDIRECT_URI_STRING)
        )
                .setScopes(AUTORIZATION_SCOPE)
                .build();
    }

    public static GoogleAutorizationHelper getInstance() {
        if (instance == null) {
            synchronized (GoogleAutorizationHelper.class) {
                if (instance == null) {
                    instance = new GoogleAutorizationHelper();
                }
            }
        }

        return instance;
    }

    public void launchAutorization(String actionForResult) {
        new AuthorizationService(App.getCurrentActivity())
                .performAuthorizationRequest(
                        authorizationRequest,
                        PendingIntent.getActivity(
                                App.getAppContext(),
                                authorizationRequest.hashCode(),
                                new Intent(actionForResult),
                                0
                        )
                );
    }

    public void saveAuthState(AuthState authState) {
        SharedPreferenceHelper.getInstance().putString(SharedPreferenceHelper.Key.AUTH_STATE, authState.toJsonString());
    }

    public AuthState getAuthState() {
        try {
            return AuthState.fromJson(SharedPreferenceHelper.getInstance().getString(SharedPreferenceHelper.Key.AUTH_STATE, null));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isAuthorized() {
        return getAuthState() != null;
    }

    public void handleResponse(Intent intent, HandleResponseListener handleResponseListener) {
        try {
            AuthorizationResponse authorizationResponse = AuthorizationResponse.fromIntent(intent);

            if (authorizationResponse == null) {
                handleResponseListener.onFinish();
                return;
            }

            AuthState authState = new AuthState(
                    authorizationResponse,
                    AuthorizationException.fromIntent(intent)
            );

            new AuthorizationService(App.getCurrentActivity())
                    .performTokenRequest(
                            authorizationResponse.createTokenExchangeRequest(),
                            (tokenResponse, exception) -> {
                                authState.update(tokenResponse, exception);
                                saveAuthState(authState);
                                handleResponseListener.onFinish();
                            }
                    );
        } catch (Exception e) {
            e.printStackTrace();
            App.showMessage(e.getMessage());
            handleResponseListener.onFinish();
        }
    }

    public void executeWithToken(RunnableWithToken runnable) {
        getAuthState().performActionWithFreshTokens(
                new AuthorizationService(App.getCurrentActivity()),
                (accessToken, idToken, authorizationException) -> runnable.execute(accessToken)
        );
    }

    public interface HandleResponseListener {
        void onFinish();
    }

    public interface RunnableWithToken {
        void execute(String token);
    }
}
