package app.general.common.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;


@Component
public class Validators {

    private static final Pattern NIN_SAUDI_PATTERN = Pattern.compile("^[1][\\d]{9}$");
    private static final Pattern NIN_ALIEN_SAUDI_PATTERN = Pattern.compile("^[2][\\d]{9}$");
    private static final Pattern MOI_NUMBER_PATTERN = Pattern.compile("^(1|7)[\\d]{9}$");
    private static final Pattern BIRTH_DATE_PATTERN = Pattern.compile("^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-([0-9]{4})$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{6,20}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^9665[0-9]{8}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,7}$");



    public boolean isValidUsername(String username) {
        if(username == null || username.equals("anonymousUser") || username.equals("anonymous")){
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }

    public boolean isValidMobileNumber(String mobileNumber) {
        if(mobileNumber == null){
            return false;
        }
        return MOBILE_PATTERN.matcher(mobileNumber).matches();
    }

    public boolean isValidEmail(String email) {
        if(email == null){
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isNotEmpty(String input) {
        return input != null && !input.isEmpty();
    }

    public boolean isNumber(String input) {
        if(input == null){
            return false;
        }
        try{
            Integer.parseInt(input);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
