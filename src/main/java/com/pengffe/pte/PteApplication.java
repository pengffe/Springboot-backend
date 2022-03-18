package com.pengffe.pte;


import com.pengffe.pte.utils.email.EmailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
@EnableSwagger2
@RestController
@EnableNeo4jRepositories
public class PteApplication {

    @Autowired
    EmailServiceImp emailServiceImp;

	public static void main(String[] args) {

	    SpringApplication.run(PteApplication.class, args);
	}


	@GetMapping("/good")
	public void good() {
        emailServiceImp.sendEmail("limindeng92@gmail.com", "sharkdeng");
    }


}


