/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.unassignedprescription.api;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.unassignedprescription.ServiceResponse;
import org.openmrs.module.unassignedprescription.UnassignedOb;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface UnassignedPrescriptionService {
	
	/**
	 * Returns an item by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	
	public List<UnassignedOb> getUnassignedPrescriptions(String uuid) throws APIException;
	
	public List<UnassignedOb> getAllObs() throws APIException;
	
	public ServiceResponse getObsByLocationUuid(String locationUuid) throws APIException;
	
	public ServiceResponse assignPrescriptionById(int unassignedObsId, String patientUuid) throws APIException;
	
	public ServiceResponse createUnassignedPrescription(String locationUuid, String comment, MultipartFile multipartFile)
	        throws APIException;
	
}
