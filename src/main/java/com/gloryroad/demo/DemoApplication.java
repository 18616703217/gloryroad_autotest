package com.gloryroad.demo;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLOutput;


@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        System.out.println("The service to start.");
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("The service has started.");
    }

}
