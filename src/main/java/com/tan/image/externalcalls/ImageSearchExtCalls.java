package com.tan.image.externalcalls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tan.image.utils.ImageSearchConstants;

@Component
public class ImageSearchExtCalls {

	@Autowired
	RestTemplate restTemplate;

	public String extCalls(String url, String method) {

		ResponseEntity<String> respEnt = null;

		String resp = "";

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> entity = new HttpEntity<>(headers);

		if (method.equalsIgnoreCase(ImageSearchConstants.METHOD)) {

			respEnt = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		}

		if (respEnt.getStatusCodeValue() == 200) {
			resp = respEnt.getBody();
		}

		System.out.println("resp -------->  " + resp);

		return resp;

	}

	public String urlBuilder(String name, String qty) {

		StringBuilder urlBuild = new StringBuilder();
		urlBuild.append(ImageSearchConstants.SERVICEURL);
		urlBuild.append(ImageSearchConstants.IJN);
		urlBuild.append(Integer.parseInt(qty) - 1);
		urlBuild.append(ImageSearchConstants.QUERY);
		urlBuild.append(name);
		System.out.println("url --------> " + urlBuild);

		return urlBuild.toString();

	}
	
	
	public byte[] extCallsForImage(String url, String method) {

		ResponseEntity<byte[]> respEnt = null;

		byte[] resp = null;

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> entity = new HttpEntity<>(headers);

		if (method.equalsIgnoreCase(ImageSearchConstants.METHOD)) {

			respEnt = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
		}


		if (respEnt.getStatusCodeValue() == 200) {
			resp = respEnt.getBody();
		}

		System.out.println("resp -------->  " + resp);

		return resp;

	}
	
	

}
