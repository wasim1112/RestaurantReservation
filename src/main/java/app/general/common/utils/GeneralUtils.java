package app.general.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;


@Component
public class GeneralUtils {

    @Autowired
    private ObjectMapper objectMapper;

    public String reverseDateParts(String date) {
        String[] dateParts = date.split("(\\-)");
        if (dateParts.length != 3) {
            throw new IllegalArgumentException("date format is invalid");
        }
        return dateParts[2]+"-"+dateParts[1]+"-"+dateParts[0];
    }

    public String serialiseObject(Object object){
        if(object == null){
            return null;
        }
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            objectMapper.writeValue(os, object);
            return os.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getContentTypeByFileNameExtension(String path){
        if(path.endsWith(".jpeg") || path.endsWith(".jpg")){
            return "image/jpeg";
        }else if(path.endsWith(".png")){
            return "image/png";
        }else if(path.endsWith(".mpeg") || path.endsWith(".mpg")){
            return "video/mpeg";
        }else if(path.endsWith(".mp4")){
            return "video/mp4";
        }
        return null;
    }


}
