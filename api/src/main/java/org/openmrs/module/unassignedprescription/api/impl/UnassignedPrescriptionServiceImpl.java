package org.openmrs.module.unassignedprescription.api.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.unassignedprescription.AttachmentDTO;
import org.openmrs.module.unassignedprescription.GlobalUtils;
import org.openmrs.module.unassignedprescription.ServiceResponse;
import org.openmrs.module.unassignedprescription.UnassignedOb;
import org.openmrs.module.unassignedprescription.UnassignedObsDTO;
import org.openmrs.module.unassignedprescription.UtilConstants;
import org.openmrs.module.unassignedprescription.api.UnassignedPrescriptionService;
import org.openmrs.module.unassignedprescription.api.dao.UnassignedPrescriptionDao;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UnassignedPrescriptionServiceImpl extends BaseOpenmrsService implements UnassignedPrescriptionService {
	
	@Autowired
	private UnassignedPrescriptionDao unassignedDao;
	
	@Override
	public List<UnassignedOb> getUnassignedPrescriptions(String uuid) throws APIException {
		return null;
	}
	
	@Override
	public List<UnassignedObsDTO> getAllObs() throws APIException {
		List<UnassignedOb> allObs = unassignedDao.getAllObs();
		List<UnassignedObsDTO> allObsDTO = new ArrayList<UnassignedObsDTO>();
		
		for (UnassignedOb unassignedOb : allObs) {
			UnassignedObsDTO dto = new UnassignedObsDTO();
			dto.setUnassignedObsId(unassignedOb.getUnassignedObsId());
			dto.setComment(unassignedOb.getComment());
			dto.setDateCreated(unassignedOb.getDateCreated());
			dto.setLocationUuid(unassignedOb.getLocationUuid());
			String imageName = unassignedOb.getObsImage();
			// dto.setObsImage(UtilConstants.IMAGE_API_PATH_LOCAL + imageName);
			dto.setObsImage(UtilConstants.IMAGE_API_PATH_SERVER + imageName);
			dto.setPatientUuid(unassignedOb.getPatientUuid());
			dto.setStatus(unassignedOb.getStatus());
			dto.setUuid(unassignedOb.getUuid());
			dto.setImageName(imageName);
			
			allObsDTO.add(dto);
		}
		return allObsDTO;
	}
	
	@Override
	public ServiceResponse getObsByLocationUuid(String locationUuid) throws APIException {
		
		List<UnassignedOb> obsByLocation = unassignedDao.getObsByLocation(locationUuid,
		    UtilConstants.PRESCRIPTION_UNASSIGNED);
		List<UnassignedObsDTO> allObsDTO = new ArrayList<UnassignedObsDTO>();
		
		for (UnassignedOb unassignedOb : obsByLocation) {
			UnassignedObsDTO dto = new UnassignedObsDTO();
			dto.setUnassignedObsId(unassignedOb.getUnassignedObsId());
			dto.setComment(unassignedOb.getComment());
			dto.setDateCreated(unassignedOb.getDateCreated());
			dto.setLocationUuid(unassignedOb.getLocationUuid());
			String imageName = unassignedOb.getObsImage();
			// dto.setObsImage(UtilConstants.IMAGE_API_PATH_LOCAL + imageName);
			dto.setObsImage(UtilConstants.IMAGE_API_PATH_SERVER + imageName);
			dto.setPatientUuid(unassignedOb.getPatientUuid());
			dto.setStatus(unassignedOb.getStatus());
			dto.setUuid(unassignedOb.getUuid());
			dto.setImageName(imageName);
			
			allObsDTO.add(dto);
		}
		if (!GlobalUtils.isNull(allObsDTO) && (!allObsDTO.isEmpty())) {
			return new ServiceResponse(true, allObsDTO);
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
	public ServiceResponse restTemplateDemo(String patientUuid, MultipartFile multipartFile) throws APIException {
		
		String url = "http://digiprescribe.com:8081/openmrs/ws/rest/v1/attachment";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		
		String imageName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		AttachmentDTO attachmentDTO = new AttachmentDTO();
		attachmentDTO.setFile(multipartFile);
		attachmentDTO.setFileCaption(imageName);
		attachmentDTO.setPatient(patientUuid);
		
		Object object = restTemplate.postForObject(url, attachmentDTO, Object.class);
		
		if (!GlobalUtils.isNull(object)) {
			return new ServiceResponse(true, object);
		} else {
			return new ServiceResponse(false, UtilConstants.UPDATE_RECORD_FAIL);
		}
		
	}
	
	@Override
	public ServiceResponse createUnassignedPrescription(String locationUuid, String comment, MultipartFile multipartFile)
	        throws APIException {
		String imageName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		System.out.println("================================= imageName " + imageName);
		String imageLocation = Context.getAdministrationService().getGlobalProperty(
		    OpenmrsConstants.GLOBAL_PROPERTY_COMPLEX_OBS_DIR);
		System.out.println("======================== image location " + imageLocation);
		try {
			
			File fileForDirectory = OpenmrsUtil.getDirectoryInApplicationDataDirectory(Context.getAdministrationService()
			        .getGlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_COMPLEX_OBS_DIR));
			System.out.println("file for directory == " + fileForDirectory);
			byte[] bytes = multipartFile.getBytes();
			// File fileForDirectory = new File(imageLocation);
			if (!fileForDirectory.exists()) {
				fileForDirectory.mkdirs();
				Path path = Paths.get(fileForDirectory + File.separator + imageName);
				System.out.println("=============================== path " + path);
				Files.write(path, bytes);
			} else {
				Path path = Paths.get(fileForDirectory + File.separator + imageName);
				System.out.println("=============================== path " + path);
				Files.write(path, bytes);
			}
			
		}
		
		catch (IOException exception) {
			exception.printStackTrace();
			return new ServiceResponse(false, exception.getMessage());
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		UUID uuid = UUID.randomUUID();
		int updateUnassignedObs = unassignedDao.createdUnassignedObs(locationUuid, comment, dtf.format(now),
		    uuid.toString(), imageName, UtilConstants.PRESCRIPTION_UNASSIGNED);
		System.out.println("==================== After create unassigned prescriptions");
		if (!GlobalUtils.isNull(updateUnassignedObs) && updateUnassignedObs != 0) {
			return new ServiceResponse(true, UtilConstants.PRESCRIPTION_CREATED);
		} else {
			return new ServiceResponse(false, UtilConstants.PRESCRIPTION_NOT_CREATED);
		}
	}
	
}
