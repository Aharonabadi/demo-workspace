package com.example.zipkinservice2;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@SpringBootApplication
public class ZipkinService2Application {

  public static void main(String[] args) {
    SpringApplication.run(ZipkinService2Application.class, args);
  }
}

@RestController
class ZipkinController {

  private static final Logger LOG = Logger.getLogger(ZipkinController.class.getName());
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

  @GetMapping(value = "/zipkin2")
  public String zipkinService1(@RequestParam(value = "name") String name) {

    LOG.info("Inside zipkinService 2.." + name);
    LOG.info("Now let's create some intentional delay...");
    String result = null;

    if ("myfile".equals(name)) {
      String fileName = name + ".txt";

      UriComponentsBuilder builder = UriComponentsBuilder
          .fromHttpUrl("http://localhost:8083/zipkin3")
          .queryParam("fileName", fileName);

      HttpHeaders headers = new HttpHeaders();
      headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
      HttpEntity<?> entity = new HttpEntity<>(headers);
      try {
        Thread.sleep(5 * 1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      LOG.info("returning afte delay..");
      result = (String) restTemplate
          .exchange(builder.toUriString(), HttpMethod.GET, entity, String.class).getBody();
    } else {
      result = "file name not correct";
    }

    return "Hi service2..." + result;
  }
}
