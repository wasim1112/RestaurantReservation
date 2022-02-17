package app.general.common.utils;

import org.springframework.security.access.AccessDeniedException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class BasicAuthUtils {
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();
    private static final Base64.Decoder base64Decoder = Base64.getDecoder();

    public static void checkAuthorization(String authorizationHeader, String username, String password){
        if(!isAuthorized(authorizationHeader, username, password)){
            throw new AccessDeniedException("Not authorized");
        }
    }

    public static boolean isAuthorized(String authorizationHeader, String username, String password){
        if(username == null || password == null){
            throw new IllegalArgumentException("username and password cannot be null");
        }
        String[] credentials = getCredentials(authorizationHeader);
        return username.equals(credentials[0]) && password.equals(credentials[1]);
    }

    public static String[] getCredentials(String authorizationHeader){
        try{
            String encoded = authorizationHeader.split("Basic ")[1];
            String decoded = new String(base64Decoder.decode(encoded), StandardCharsets.UTF_8);
            String[] credentials = decoded.split(":");
            assert credentials.length == 2;
            return credentials;
        }catch(Exception e){
            return new String[]{null, null};
        }
    }

    /**
     * Returns the authorization header value including the keyword Basic
     * @param username Username
     * @param password Password
     * @return authorization header including the keyword Basic
     */
    public static String encodeAuthorizationHeader(String username, String password){
        return "Basic " + base64Encoder.encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

}
