package com.desale.visitorsManagementSystem.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.desale.visitorsManagementSystem.service.req.ResponseUser;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
@Configuration
public class UserCallerUtil {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private EurekaClient eurekaClient;

	@Value("${user-service}") // value is coming from the application.properties file
	private String remoteServiceName;
	
	private List<ResponseUser> cachedUsers = new ArrayList<>();
//	For some reason if the remote service fails then the value returned by the defaultGetAllUsers() will  be returned as default
	@HystrixCommand(fallbackMethod = "defaultGetAllUsers")
	public List<ResponseUser> getUsers() {
		String url = lookupUrlFor(remoteServiceName) + "/users";
		cachedUsers = Arrays.asList(restTemplate.exchange(url, HttpMethod.GET, createHttpEntity(), ResponseUser[].class).getBody());
		return cachedUsers;
	}
	
	private HttpEntity<Object> createHttpEntity() {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.setBasicAuth(username, password);
//		headers.add("Authorization", (String) servletContext.getAttribute("userToken"));
		return new HttpEntity<>(headers);
	}

	private String lookupUrlFor(String appName) {
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
		return instanceInfo.getHomePageUrl();
	}
	
	//default return list
	private List<ResponseUser> defaultGetAllUsers() {
		System.out.println("I have been here");
		return cachedUsers;
	}
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
