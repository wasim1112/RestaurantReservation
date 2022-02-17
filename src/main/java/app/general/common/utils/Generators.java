package app.general.common.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


@Component
public class Generators {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$&*()_+-=[]";


    public String randomPassword(int length, boolean includeSpecialCharacters){
        String alphabet = (CHAR_LOWER + CHAR_UPPER + NUMBER);
        if(includeSpecialCharacters){
            alphabet += OTHER_CHAR;
        }
        List<String> letters = Arrays.asList(alphabet.split(""));
        Collections.shuffle(letters);
        String shuffledSpace = String.join("", letters);
        Random random = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(shuffledSpace.charAt(random.nextInt(shuffledSpace.length())));
        }
        return sb.toString();
    }

}
