package org.openmrs.module.unassignedprescription;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the unassigned_obs database table.
 */
@Entity
@Table(name = "unassigned_obs")
@NamedQuery(name = "UnassignedOb.findAll", query = "SELECT u FROM UnassignedOb u")
public class UnassignedOb implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "unassigned_obs_id")
	private int unassignedObsId;
	
	@Column(name = "comment")
	private String comment;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Column(name = "location_uuid")
	private String locationUuid;
	
	@Lob
	@Column(name = "obs_image")
	private String obsImage;
	
	@Column(name = "patient_uuid")
	private String patientUuid;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "uuid")
	private String uuid;
	
	public UnassignedOb() {
	}
	
	public UnassignedOb(int unassignedObsId, String comment, Date dateCreated, String locationUuid, String obsImage,
	    String patientUuid, String status, String uuid) {
		this.unassignedObsId = unassignedObsId;
		this.comment = comment;
		this.dateCreated = dateCreated;
		this.locationUuid = locationUuid;
		this.obsImage = obsImage;
		this.patientUuid = patientUuid;
		this.status = status;
		this.uuid = uuid;
	}
	
	public int getUnassignedObsId() {
		return this.unassignedObsId;
	}
	
	public void setUnassignedObsId(int unassignedObsId) {
		this.unassignedObsId = unassignedObsId;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Date getDateCreated() {
		return this.dateCreated;
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
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUuid() {
		return this.uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public String toString() {
		return "UnassignedOb [unassignedObsId=" + unassignedObsId + ", comment=" + comment + ", dateCreated=" + dateCreated
		        + ", locationUuid=" + locationUuid + ", obsImage=" + obsImage + ", patientUuid=" + patientUuid + ", status="
		        + status + ", uuid=" + uuid + "]";
	}
	
}
