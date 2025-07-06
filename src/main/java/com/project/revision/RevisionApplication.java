package com.project.revision;

import com.project.revision.Config.JWT.TokenHandler;
import com.project.revision.sitting.TokenConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(TokenConfig.class)
public class RevisionApplication {

    public static void main(String[] args) {
        SpringApplication.run(RevisionApplication.class, args);
    }

}
