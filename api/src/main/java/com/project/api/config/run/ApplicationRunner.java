package com.project.api.config.run;



import org.springframework.boot.ApplicationArguments;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {
    private final Environment environment;

    // TODO :: profile yml 적용 테스트
    public ApplicationRunner(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("===================다중 프로파일 테스트===================");
        System.out.println("Active profiles : "+ Arrays.toString(environment.getActiveProfiles()));
        System.out.println("Datasource driver : " + environment.getProperty("spring.datasource.driver-class-name"));
        System.out.println("Datasource url : " + environment.getProperty("spring.datasource.url"));
        System.out.println("Datasource username : " + environment.getProperty("spring.datasource.username"));
        System.out.println("Datasource password : " + environment.getProperty("spring.datasource.password"));
        System.out.println("Server Port : " + environment.getProperty("server.port"));
        System.out.println("====================================================");
    }
}
