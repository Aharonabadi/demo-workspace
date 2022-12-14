package com.example.zipkinservice3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ZipkinService3Application {

	public static void main(String[] args) {
		SpringApplication.run(ZipkinService3Application.class, args);
	}
}
@RestController
class ZipkinController{
	
	@Autowired
	RestTemplate restTemplate;
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	@Bean
	public AlwaysSampler alwaysSampler() {
		return new AlwaysSampler();
	}
	private static final Logger LOG = Logger.getLogger(ZipkinController.class.getName());
	
	@GetMapping(value="/zipkin3")
	public String zipkinService1(@RequestParam(value = "fileName") String fileNmae) throws IOException {
		LOG.info("Inside zipkinService 3..");

		File file = new File(".");
		String content = Files.readString(new File(".",fileNmae).toPath());

		return "Hi service3..." + content;
	}
}
