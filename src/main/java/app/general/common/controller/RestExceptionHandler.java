package app.general.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import app.general.common.utils.ErrorMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Locale EN = Locale.ENGLISH;
    private static final Locale AR = new Locale("ar");

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler
    public ResponseEntity<Object> handleException(final HttpServletRequest request, final Exception ex) throws Exception {
        if(ex instanceof AccessDeniedException){
            throw ex;
        }
        if(ex instanceof HttpMediaTypeNotSupportedException){
            log.error("HttpMediaTypeNotSupportedException: url = {}", request.getRequestURI());
        }
        try{
            String ar = messageSource.getMessage(ex.getMessage(), null, AR);
            String en = messageSource.getMessage(ex.getMessage(), null, EN);
            return ResponseEntity.badRequest().body(new ErrorMessage(ar, en));
        }catch(Exception e){
            ex.printStackTrace();
            String ar = messageSource.getMessage("errors.general.error", null, AR);
            String en = messageSource.getMessage("errors.general.error", null, EN);
            return ResponseEntity.badRequest().body(new ErrorMessage(ar, en));
        }
    }
}
