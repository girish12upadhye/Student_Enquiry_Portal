package org.girishTechUniverse.service;

import java.util.List;

import org.girishTechUniverse.binding.SearchCriteria;
import org.girishTechUniverse.entity.StudentEnq;

public interface EnquiryService {

	public boolean addEnq(StudentEnq se);
	
	public List<StudentEnq> getEnquiries(Integer cid, SearchCriteria s);
}
