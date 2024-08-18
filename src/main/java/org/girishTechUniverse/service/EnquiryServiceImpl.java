package org.girishTechUniverse.service;

import java.util.List;

import org.girishTechUniverse.binding.SearchCriteria;
import org.girishTechUniverse.entity.StudentEnq;
import org.girishTechUniverse.repository.StudentEnqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private StudentEnqRepository studentEnqRepository;

	@Override
	public boolean addEnq(StudentEnq se) {
		StudentEnq savedEnq = studentEnqRepository.save(se);
		return savedEnq.getEnqId() != null;
	}

	@Override
	public List<StudentEnq> getEnquiries(Integer cid, SearchCriteria sc) {
		
		StudentEnq enq = new StudentEnq();
		enq.setCid(cid);
		
		if(sc.getCourseName() != null && !sc.getCourseName().equals("")) {
			enq.setCourseName(sc.getCourseName());
		}
		if(sc.getEnqStatus() != null && !sc.getEnqStatus().equals("")) {
			enq.setEnqStatus(sc.getEnqStatus());
		}
		if(sc.getClassMode() != null && !sc.getClassMode().equals("")) {
			enq.setClassMode(sc.getClassMode());
		}
		
		Example<StudentEnq> of = Example.of(enq);
		
		List<StudentEnq> enquiries = studentEnqRepository.findAll(of);
		
		return enquiries;
	}

}
