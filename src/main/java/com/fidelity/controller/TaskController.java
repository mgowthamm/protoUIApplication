package com.fidelity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.model.TaskDTO;
import com.fidelity.service.CamundaTaskService;

@RestController
@RequestMapping("/api")
public class TaskController {
	
	 @Autowired
	    private CamundaTaskService camundaTaskService;

	    @GetMapping("/tasks")
	    public List<TaskDTO> getTasks() {
	        return camundaTaskService.getTasks();
	    }
	    
	    

	    @GetMapping("/{taskId}/variables")
	    public Map<String, Object> getTaskVariables(@PathVariable String taskId) {
	    	
	    	Map<String, Object> receivedVariables = camundaTaskService.getTaskVariables(taskId);
	    	Map<String, Object> receivedMetadata = camundaTaskService.getTaskMetaData(taskId);

	    	Map<String, Object> sortedVariables = new HashMap<>();

	    	Map<String, Object> dataContext = new HashMap<>();

	    	for(Entry<String, Object> entry : receivedVariables.entrySet()) {
	    		
	    	    String key = entry.getKey();
	    	       	    
	    	    Object value = ((Map<String, Object>) entry.getValue()).get("value");
	    	    
	    	    dataContext.put(key, value);
	    	    

	      	}	    		    	    

	    	
	    	sortedVariables.put("taskId", receivedMetadata.get("id"));
	    	sortedVariables.put("taskName", receivedMetadata.get("name"));
	    	sortedVariables.put("taskAssignee", receivedMetadata.get("assignee"));
	    	sortedVariables.put("taskCreatedTime", receivedMetadata.get("created"));
	    	sortedVariables.put("taskDueDate", receivedMetadata.get("due"));
	    	sortedVariables.put("taskFollowUpDate", receivedMetadata.get("followUp"));
	    	sortedVariables.put("tasklastUpdatedDate", receivedMetadata.get("lastUpdated"));
	    	sortedVariables.put("processInstanceId", receivedMetadata.get("processInstanceId"));
	    	sortedVariables.put("formKey", receivedMetadata.get("formKey"));
	    	sortedVariables.put("camundaFormRef", receivedMetadata.get("camundaFormRef"));
	    	sortedVariables.put("dataContext", dataContext);
  	
	    	
	    	return sortedVariables;    
	    }
	    
	    @GetMapping("/{taskId}/metaData")
	    public Map<String, Object> getTaskMetaData(@PathVariable String taskId) {
	    	
	    	Map<String, Object> finalMap = camundaTaskService.getTaskMetaData(taskId);

	    	finalMap.remove("tenantId");
	    	finalMap.remove("suspended");
	    	finalMap.remove("caseDefinitionId");
	    	finalMap.remove("caseInstanceId");
	    	finalMap.remove("caseExecutionId");
	    	finalMap.remove("taskDefinitionKey");
	    	finalMap.remove("processDefinitionId");
	    	finalMap.remove("parentTaskId");
	    	finalMap.remove("owner");
	    	finalMap.remove("executionId");
	    	finalMap.remove("description");
	    	finalMap.remove("delegationState");	    	
	        return finalMap;
	    }
	    
	    @PostMapping("/{taskId}/claim/{userId}")
	    public void claimTask(@PathVariable String taskId, @PathVariable String userId) {
	        camundaTaskService.claimTask(taskId, userId);
	    }

	    @PostMapping("/{taskId}/unclaim")
	    public void unclaimTask(@PathVariable String taskId) {
	        camundaTaskService.unclaimTask(taskId);
	    }
	    
	    @PutMapping("/{taskId}/variables")
	    public void updateTaskVariables(@PathVariable String taskId, @RequestBody Map<String, Object> variables) {
	        camundaTaskService.updateTaskVariables(taskId, variables);
	    }
	    
	    @PostMapping("/{taskId}/complete")
	    public void completeTask(@PathVariable String taskId, @RequestBody Map<String, Object> variables) {
	        camundaTaskService.completeTask(taskId, variables);
	    }
	    
//	    @PostMapping("/{taskId}/complete")
//	    public void completeTask(@PathVariable String taskId, @RequestBody TaskDTO task) {
//	        task.setId(taskId);
//	        camundaTaskService.completeTask(task);
//	    }

}
