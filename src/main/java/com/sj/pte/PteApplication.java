package com.sj.pte;


import com.sj.pte.utils.email.EmailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
@EnableSwagger2
@RestController
public class PteApplication {

    @Autowired
    EmailServiceImp emailServiceImp;

	public static void main(String[] args) {

	    SpringApplication.run(PteApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}


	@GetMapping("/good")
	public void good() {
        emailServiceImp.sendEmail("limindeng92@gmail.com", "sharkdeng");
    }


}


