package org.girishTechUniverse.repository;

import java.util.List;

import org.girishTechUniverse.entity.StudentEnq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEnqRepository extends JpaRepository<StudentEnq, Integer> {

	public List<StudentEnq> findByCid(Integer cid);
}
