package app.general.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        int status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return ResponseEntity.status(status).body("error "+status);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
