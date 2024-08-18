package org.girishTechUniverse.service;

import java.util.List;

import org.girishTechUniverse.binding.DashboardResponse;
import org.girishTechUniverse.entity.Counsellor;
import org.girishTechUniverse.entity.StudentEnq;
import org.girishTechUniverse.repository.CounsellorRepository;
import org.girishTechUniverse.repository.StudentEnqRepository;
import org.girishTechUniverse.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	@Autowired
	private CounsellorRepository counsellorRepository;
	
	@Autowired
	private StudentEnqRepository studentEnqRepository;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Override
	public String saveCounseller(Counsellor c) {
		
//		Varify duplicate email
		Counsellor obj = counsellorRepository.findByEmail(c.getEmail());
		if(obj != null) {
			return "Record already exist with this email !";
		}
		Counsellor savedObj = counsellorRepository.save(c);
		if(savedObj.getCid() != null) {
			return "Registration Success!";
		}
		return "Registration Fail";
	}

	@Override
	public Counsellor loginCheck(String email, String pwd) {
		return counsellorRepository.findByEmailAndPwd(email, pwd);
	}

	@Override
	public boolean recoverPwd(String email) {
		Counsellor counsellor = counsellorRepository.findByEmail(email);
		
		if(counsellor == null) {
			return false;
		}
		String subject = "Recover Password - GirishTechUniverse";
		
		String body = "<h1>Your Password is : "+counsellor.getPwd()+"</h1>";
		
		return emailUtils.sendEmail(subject, body, email);
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer cid) {
		
		List<StudentEnq> findByCid = studentEnqRepository.findByCid(cid);
		
		int enrolledEnq = (int) findByCid.stream()
										 .filter(e -> e.getEnqStatus().equalsIgnoreCase("enrolled"))
										 .count();
		
		int lostEnq = (int) findByCid.stream()
				                     .filter( e -> e.getEnqStatus().equalsIgnoreCase("lost"))
				                     .count();
		
		DashboardResponse resp = new DashboardResponse();
		
		resp.setTotalEnq(findByCid.size());
		resp.setEnrolledEnq(enrolledEnq);
		resp.setLostEnq(lostEnq);
		
		return resp;
	}

}
