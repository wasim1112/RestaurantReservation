package app.general.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller // must be @Controller not @RestController because "forward:/" works only on @Controller
public class WebappController {

    @GetMapping(value = {
            "/", "/login", "/forgotPassword", "/success",
            "/role/list", "/role/view", "/role/create", "/role/edit", // iam
            "/user/list", "/user/view", "/user/create", "/user/edit", "/my-profile", // iam
            "/settings", "/key-value-store", "/endpoint-execution-log", // system
            ////////////////////////////////////////////////////////////////////
            "/order/list", "/order/view", // business: order
    })
    public String forward404() {
        log.info("forwarding to /");
        return "forward:/";
    }

}
