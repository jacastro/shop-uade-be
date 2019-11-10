package ar.edu.uade.integracion.shop.service;

import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SSOClient {
    private static final String URL = "https://uade-sso-users-api.herokuapp.com/api/login";

    private String user;
    private String password;
    private String tenant;
    private String token;
    private RestTemplate restTemplate;

    public SSOClient(String user, String password, String tenant) {
        this.password = password;
        this.user = user;
        this.tenant = tenant;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity get(String serviceUrl, Class clazz, Object... objects) {
        return doExchange(serviceUrl, clazz, HttpMethod.GET, objects);
    }

    private ResponseEntity doExchange(String serviceUrl, Class clazz, HttpMethod method, Object... objects) {
        login();
        return restTemplate.exchange(serviceUrl, method, new HttpEntity<>(getHeaders()), clazz, objects);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private HttpHeaders getLoginHeaders(){
        HttpHeaders headers = getHeaders();
        headers.add("TENANT-ID", tenant);
        return  headers;
    }

    private void login(){
        Map<String, String> map = new HashMap<>();
        map.put("email", user);
        map.put("password", password);
        HttpEntity entity = new HttpEntity(map, getLoginHeaders());

        ResponseEntity<String> ssoResponse = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);

        if(HttpStatus.OK.equals(ssoResponse.getStatusCode())){
            this.token = ssoResponse.getBody();
        }else{
            throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "Issues when trying to login with SSO");
        }
    }
}
