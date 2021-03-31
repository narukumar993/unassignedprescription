package org.openmrs.module.unassignedprescription;

import java.util.Date;

public class UnassignedObsDTO {
	
	private int unassignedObsId;
	
	private String comment;
	
	private Date dateCreated;
	
	private String locationUuid;
	
	private String obsImage;
	
	private String patientUuid;
	
	private String status;
	
	private String uuid;
	
	private String imageName;
	
	public UnassignedObsDTO() {
		
	}
	
	public UnassignedObsDTO(int unassignedObsId, String comment, Date dateCreated, String locationUuid, String obsImage,
	    String patientUuid, String status, String uuid, String imageName) {
		this.unassignedObsId = unassignedObsId;
		this.comment = comment;
		this.dateCreated = dateCreated;
		this.locationUuid = locationUuid;
		this.obsImage = obsImage;
		this.patientUuid = patientUuid;
		this.status = status;
		this.uuid = uuid;
		this.imageName = imageName;
	}
	
	public int getUnassignedObsId() {
		return unassignedObsId;
	}
	
	public void setUnassignedObsId(int unassignedObsId) {
		this.unassignedObsId = unassignedObsId;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getLocationUuid() {
		return locationUuid;
	}
	
	public void setLocationUuid(String locationUuid) {
		this.locationUuid = locationUuid;
	}
	
	public String getObsImage() {
		return obsImage;
	}
	
	public void setObsImage(String obsImage) {
		this.obsImage = obsImage;
	}
	
	public String getPatientUuid() {
		return patientUuid;
	}
	
	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	@Override
	public String toString() {
		return "UnassignedObsDTO [unassignedObsId=" + unassignedObsId + ", comment=" + comment + ", dateCreated="
		        + dateCreated + ", locationUuid=" + locationUuid + ", obsImage=" + obsImage + ", patientUuid=" + patientUuid
		        + ", status=" + status + ", uuid=" + uuid + ", imageName=" + imageName + "]";
	}
	
}
