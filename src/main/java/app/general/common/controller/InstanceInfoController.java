package app.general.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/instance-info")
public class InstanceInfoController {
    private final String NODE_UUID = UUID.randomUUID().toString();

    @GetMapping("/node-id")
    public String getNodeId(@RequestParam("s") String s){
        if(!s.equals("fX4wW7wO4nT6vL5pK4uM1pI9hJ4dR1jU6dH3kX0oJ2mQ1wB1lS1dR2pJ5vD7nAkK2qI2cU5kV8jI7bB1cE7hY1oAoA3vT8rI")){
           return null;
        }
        try{
            return InetAddress.getLocalHost().getHostName();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/instance-id")
    public String getInstanceId(){
        return NODE_UUID;
    }



}
