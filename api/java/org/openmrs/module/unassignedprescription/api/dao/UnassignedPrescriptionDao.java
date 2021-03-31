package org.openmrs.module.unassignedprescription.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.unassignedprescription.UnassignedOb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UnassignedPrescriptionDao {
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<UnassignedOb> getAllObs() {
		List<UnassignedOb> allObs = new ArrayList<UnassignedOb>();
		
		DbSession session = sessionFactory.getCurrentSession();
		try {
			String hql = "FROM UnassignedOb";
			Query query = session.createQuery(hql);
			allObs = query.list();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		return allObs;
	}
	
	@SuppressWarnings("unchecked")
	public List<UnassignedOb> getObsByLocation(String locationUuid, String status) {
		List<UnassignedOb> allObs = new ArrayList<UnassignedOb>();
		DbSession session = sessionFactory.getCurrentSession();
		try {
			String hql = "FROM UnassignedOb UO WHERE UO.locationUuid = :locationUuid AND UO.status = :status AND UO.patientUuid IS NULL";
			Query query = session.createQuery(hql);
			query.setParameter("locationUuid", locationUuid);
			query.setParameter("status", status);
			allObs = query.list();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		return allObs;
	}
	
	public int updateUnassignedObs(int unassignedObsId, String status, String patientUuid) {
		int executeUpdate = 0;
		DbSession session = sessionFactory.getCurrentSession();
		try {
			String hql = "UPDATE UnassignedOb UO SET UO.status = :status, UO.patientUuid = :patientUuid WHERE UO.unassignedObsId = :unassignedObsId";
			Query query = session.createQuery(hql);
			query.setParameter("status", status);
			query.setParameter("patientUuid", patientUuid);
			query.setParameter("unassignedObsId", unassignedObsId);
			executeUpdate = query.executeUpdate();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		return executeUpdate;
	}
	
	public int createdUnassignedObs(String locationUuid, String comment, String dateCreated, String uuid, String obsImage,
	        String status) {
		int executeUpdate = 0;
		DbSession session = sessionFactory.getCurrentSession();
		try {
			String sql = "INSERT INTO openmrs.unassigned_obs (location_uuid, comment, date_created, uuid, obs_image, status) VALUES (:locationUuid, :comment, :dateCreated, :uuid, :obsImage, :status)";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("locationUuid", locationUuid);
			query.setParameter("comment", comment);
			query.setParameter("dateCreated", dateCreated);
			query.setParameter("uuid", uuid);
			query.setParameter("obsImage", obsImage);
			query.setParameter("status", status);
			
			executeUpdate = query.executeUpdate();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		return executeUpdate;
	}
	
}
