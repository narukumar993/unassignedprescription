/**This Source Code Form is subject to the terms of the Mozilla Public License,*v.2.0.If a copy of the MPL was not distributed with this file,You can*obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 *the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 **Copyright(C)OpenMRS Inc.OpenMRS is a registered trademark and the OpenMRS*graphic logo is a trademark of OpenMRS Inc.*/
package org.openmrs.module.unassignedprescription.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.api.context.Context;
import org.openmrs.module.unassignedprescription.ServiceResponse;
import org.openmrs.module.unassignedprescription.UnassignedObsDTO;
import org.openmrs.module.unassignedprescription.api.UnassignedPrescriptionService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/unassignedprescription")
public class UnassignedPrescriptionController extends BaseRestController {
	
	@Autowired
	public UnassignedPrescriptionService unassignedPrescriptionService;
	
	@Autowired
	public ServletContext servletContext;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getNamespace() {
		return "Unassigned Prescription Resource";
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UnassignedObsDTO>> getAllUnassignedPrescriptions() {
		List<UnassignedObsDTO> allObs = unassignedPrescriptionService.getAllObs();
		return new ResponseEntity<List<UnassignedObsDTO>>(allObs, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{locationUuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity<ServiceResponse> getUnassignedPrescriptions(@PathVariable("locationUuid") String locationUuid) {
		ServiceResponse unassignedPres = unassignedPrescriptionService.getObsByLocationUuid(locationUuid);
		return new ResponseEntity<ServiceResponse>(unassignedPres, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity<ServiceResponse> assignPrescriptions(@RequestParam("unassignedObsId") int unassignedObsId,
	        @RequestParam("patientUuid") String patientUuid) {
		System.out.println("unassignedobsid in controller================" + unassignedObsId);
		System.out.println("patientUuid in controller================" + patientUuid);
		ServiceResponse assignPres = unassignedPrescriptionService.assignPrescriptionById(unassignedObsId, patientUuid);
		return new ResponseEntity<ServiceResponse>(assignPres, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/demo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity<ServiceResponse> assignAndAllocatePrescriptions(@RequestParam String patientUuid,
	        @RequestParam("image") MultipartFile multipartFile) {
		System.out.println("demo controller ================");
		System.out.println("controller================");
		ServiceResponse assignPres = unassignedPrescriptionService.restTemplateDemo(patientUuid, multipartFile);
		return new ResponseEntity<ServiceResponse>(assignPres, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity<ServiceResponse> createdUnassignedPres(@RequestParam String locationUuid, @RequestParam String comment,
	        @RequestParam("image") MultipartFile multipartFile) {
		ServiceResponse createUnassignedPres = unassignedPrescriptionService.createUnassignedPrescription(locationUuid,
		    comment, multipartFile);
		return new ResponseEntity<ServiceResponse>(createUnassignedPres, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/image/{imageName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadImage(HttpServletRequest request, HttpServletResponse response,
	        @PathVariable("imageName") String imageName) throws IOException {
		try {
			File fileForDirectory = OpenmrsUtil.getDirectoryInApplicationDataDirectory(Context.getAdministrationService()
			        .getGlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_COMPLEX_OBS_DIR));
			System.out.println("image path from where we are getting images " + fileForDirectory + File.separator
			        + imageName);
			Path file = Paths.get(fileForDirectory + File.separator, imageName);
			
			if (!Files.exists(file)) {
				String errorMessage = "File you are trying to download does not exist on the server.";
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
				return;
			}
			
			String mimeType = servletContext.getMimeType(file.getFileName().toString());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			
			response.setContentType(mimeType);
			response.addHeader("Content-Disposition", "attachment; filename=" + file.getFileName().toString());
			Files.copy(file, response.getOutputStream());
			
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}
