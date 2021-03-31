package org.openmrs.module.unassignedprescription.api.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.unassignedprescription.GlobalUtils;
import org.openmrs.module.unassignedprescription.ServiceResponse;
import org.openmrs.module.unassignedprescription.UnassignedOb;
import org.openmrs.module.unassignedprescription.UtilConstants;
import org.openmrs.module.unassignedprescription.api.UnassignedPrescriptionService;
import org.openmrs.module.unassignedprescription.api.dao.UnassignedPrescriptionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UnassignedPrescriptionServiceImpl extends BaseOpenmrsService implements UnassignedPrescriptionService {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UnassignedPrescriptionDao unassignedDao;
	
	@Override
	public List<UnassignedOb> getUnassignedPrescriptions(String uuid) throws APIException {
		return null;
	}
	
	@Override
	public List<UnassignedOb> getAllObs() throws APIException {
		return unassignedDao.getAllObs();
	}
	
	@Override
	public ServiceResponse getObsByLocationUuid(String locationUuid) throws APIException {
		
		List<UnassignedOb> obsByLocation = unassignedDao.getObsByLocation(locationUuid,
		    UtilConstants.PRESCRIPTION_UNASSIGNED);
		if (!GlobalUtils.isNull(obsByLocation) && (!obsByLocation.isEmpty())) {
			return new ServiceResponse(true, obsByLocation);
		} else {
			return new ServiceResponse(false, UtilConstants.NOT_FOUND);
		}
	}
	
	@Override
	public ServiceResponse assignPrescriptionById(int unassignedObsId, String patientUuid) throws APIException {
		System.out.println("unassignedobsid in service================" + unassignedObsId);
		System.out.println("patientUuid in service================" + patientUuid);
		int updateUnassignedObs = unassignedDao.updateUnassignedObs(unassignedObsId, UtilConstants.PRESCRIPTION_ASSIGNED,
		    patientUuid);
		if (!GlobalUtils.isNull(updateUnassignedObs) && updateUnassignedObs != 0) {
			return new ServiceResponse(true, UtilConstants.UPDATE_RECORD_SUCCESS);
		} else {
			return new ServiceResponse(false, UtilConstants.UPDATE_RECORD_FAIL);
		}
	}
	
	@Override
	public ServiceResponse createUnassignedPrescription(String locationUuid, String comment, MultipartFile multipartFile)
	        throws APIException {
		
		String imageStorageLocation = UtilConstants.LOCAL_SYSTEM_IMAGE_PATH;
		String imageName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		UUID uuid = UUID.randomUUID();
		
		try {
			byte[] bytes = multipartFile.getBytes();
			File fileForDirectory = new File(imageStorageLocation);
			System.out.println("image file path name =======================================" + fileForDirectory);
			if (!fileForDirectory.exists()) {
				fileForDirectory.mkdirs();
				Path path = Paths.get(imageStorageLocation + File.separator + imageName);
				Files.write(path, bytes);
			} else {
				Path path = Paths.get(imageStorageLocation + File.separator + imageName);
				Files.write(path, bytes);
			}
		}
		catch (IOException exception) {
			log.error(exception);
			exception.printStackTrace();
			return new ServiceResponse(false, exception.getMessage());
		}
		
		int updateUnassignedObs = unassignedDao.createdUnassignedObs(locationUuid, comment, dtf.format(now),
		    uuid.toString(), imageName, UtilConstants.PRESCRIPTION_UNASSIGNED);
		if (!GlobalUtils.isNull(updateUnassignedObs) && updateUnassignedObs != 0) {
			return new ServiceResponse(true, UtilConstants.PRESCRIPTION_CREATED);
		} else {
			return new ServiceResponse(false, UtilConstants.PRESCRIPTION_NOT_CREATED);
		}
	}
	
}
