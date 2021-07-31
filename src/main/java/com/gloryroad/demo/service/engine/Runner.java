package com.gloryroad.demo.service.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private TaskActionService taskActionService;

    @Override
    public void run(String... args) throws Exception {
        taskActionService.actionMain();
        System.out.println("The Runner start to initialize ...");

    }

}
