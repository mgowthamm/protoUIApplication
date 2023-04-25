package com.fidelity.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.fidelity.model.TaskDTO;

@Service
public class CamundaTaskService {
	
	 @Value("${camunda.rest.api.url}")
	    private String camundaRestApiUrl;

	    public List<TaskDTO> getTasks() {
	        RestTemplate restTemplate = new RestTemplate();
	        TaskDTO[] tasks = restTemplate.getForObject(camundaRestApiUrl + "/task", TaskDTO[].class);
	        return Arrays.asList(tasks);
	    }
	    public Map<String, Object> getTaskVariables(String taskId) {
	        RestTemplate restTemplate = new RestTemplate();
	        String url = camundaRestApiUrl + "/task/" + taskId + "/variables";
	        Map<String, Object> variables = restTemplate.getForObject(url, Map.class);
	        
	        
	        return variables;
	    }
	    
	    public Map<String, Object> getTaskMetaData(String taskId) {
	        RestTemplate restTemplate = new RestTemplate();
	        String url = camundaRestApiUrl + "/task/" + taskId;
	        Map<String, Object> metaData = restTemplate.getForObject(url, Map.class);	             
	        return metaData;
	    }
	    
	    public void claimTask(String taskId, String userId) {
	        RestTemplate restTemplate = new RestTemplate();
	        String url = camundaRestApiUrl + "/task/" + taskId + "/claim";
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        String requestBody = "{\"userId\":\"" + userId + "\"}";
	        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
	        restTemplate.exchange(url, HttpMethod.POST, request, String.class);
	    }
	    
	    public void unclaimTask(String taskId) {
	        RestTemplate restTemplate = new RestTemplate();
	        String url = camundaRestApiUrl + "/task/" + taskId + "/unclaim";
	        restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class);
	    }
	    
	    public void updateTaskVariables(String taskId, Map<String, Object> variables) {
	        RestTemplate restTemplate = new RestTemplate();
	        String url = camundaRestApiUrl + "/task/" + taskId + "/variables";
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        try {
	            for (Map.Entry<String, Object> entry : variables.entrySet()) {
	                String variableName = entry.getKey();
	                Object variableValue = entry.getValue();
	                String requestBody = "{\"value\":" + new ObjectMapper().writeValueAsString(variableValue) + "}";
	                HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
	                restTemplate.put(url + "/" + variableName, request);
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Error updating task variables", e);
	        }
	    }
	    
	    public void completeTask(String taskId, Map<String, Object> variables) {
	        RestTemplate restTemplate = new RestTemplate();
	        String url = camundaRestApiUrl + "/task/" + taskId + "/complete";
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        try {
	            String requestBody = "{\"variables\":" + new ObjectMapper().writeValueAsString(variables) + "}";
	            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
	            restTemplate.exchange(url, HttpMethod.POST, request, String.class);
	        } catch (Exception e) {
	            throw new RuntimeException("Error completing task", e);
	        }
	    }
	    
//	    public void completeTask(TaskDTO task) {
//	        RestTemplate restTemplate = new RestTemplate();
//	        String taskId = task.getId();
//	        String url = camundaRestApiUrl + "/task/" + taskId + "/complete";
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_JSON);
//	        try {
//	            Map<String, Object> requestBodyMap = new HashMap<>();
//	            requestBodyMap.put("variables", task.getVariables());
//
//	            String requestBody = new ObjectMapper().writeValueAsString(requestBodyMap);
//	            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
//	            restTemplate.exchange(url, HttpMethod.POST, request, String.class);
//	        } catch (Exception e) {
//	            throw new RuntimeException("Error completing task", e);
//	        }
//	    }
	    

}
