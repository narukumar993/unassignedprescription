package org.openmrs.module.unassignedprescription;

import org.springframework.web.multipart.MultipartFile;

public class AttachmentDTO {
	
	private String patient;
	
	private String fileCaption;
	
	private MultipartFile file;
	
	public AttachmentDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public AttachmentDTO(String patient, String fileCaption, MultipartFile file) {
		this.patient = patient;
		this.fileCaption = fileCaption;
		this.file = file;
	}
	
	public String getPatient() {
		return patient;
	}
	
	public void setPatient(String patient) {
		this.patient = patient;
	}
	
	public String getFileCaption() {
		return fileCaption;
	}
	
	public void setFileCaption(String fileCaption) {
		this.fileCaption = fileCaption;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	@Override
	public String toString() {
		return "AttachmentDTO [patient=" + patient + ", fileCaption=" + fileCaption + ", file=" + file + "]";
	}
	
}
