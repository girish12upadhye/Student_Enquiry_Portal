package org.girishTechUniverse.service;

import org.girishTechUniverse.binding.DashboardResponse;
import org.girishTechUniverse.entity.Counsellor;

public interface CounsellorService {

	public String saveCounseller(Counsellor c);
	
	public Counsellor loginCheck(String email, String pwd);
	
	public boolean recoverPwd(String email);
	
	public DashboardResponse getDashboardInfo(Integer cid);
}
