package com.fidelity.model;

import java.util.Map;

public class TaskDTO {

	 	private String id;
	    private String name;
	    private String assignee;
	    private String created;
	    private String due;
	    private String followUp;
	    private String description;
	    private String processInstanceId;
	    private Map<String, Object> metaData; 
	    public Map<String, Object> getMetaData() {
			return metaData;
		}
		public void setMetaData(Map<String, Object> metaData) {
			this.metaData = metaData;
		}
		private Map<String, Object> variables;

	    public Map<String, Object> getVariables() {
			return variables;
		}
		public void setVariables(Map<String, Object> variables) {
			this.variables = variables;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAssignee() {
			return assignee;
		}
		public void setAssignee(String assignee) {
			this.assignee = assignee;
		}
		public String getCreated() {
			return created;
		}
		public void setCreated(String created) {
			this.created = created;
		}
		public String getDue() {
			return due;
		}
		public void setDue(String due) {
			this.due = due;
		}
		public String getFollowUp() {
			return followUp;
		}
		public void setFollowUp(String followUp) {
			this.followUp = followUp;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getProcessInstanceId() {
			return processInstanceId;
		}
		public void setProcessInstanceId(String processInstanceId) {
			this.processInstanceId = processInstanceId;
		}
		public String getSuspended() {
			return suspended;
		}
		public void setSuspended(String suspended) {
			this.suspended = suspended;
		}
		private String suspended;
}
