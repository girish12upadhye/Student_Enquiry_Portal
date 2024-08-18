package org.girishTechUniverse.repository;

import org.girishTechUniverse.entity.Counsellor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounsellorRepository extends JpaRepository<Counsellor, Integer> {

	public Counsellor findByEmailAndPwd(String email, String pwd);
	public Counsellor findByEmail(String email);
}
