package com.hkexcel.hkexcelapirestful.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hkexcel.hkexcelapirestful.model.School;

@Repository
public interface SchoolRepository extends MongoRepository<School, String>, SchooRepositoryCustom{

	School findOneBySchoolCode(@Param("schoolCode") String schoolCode);
	
	//@Query("{ 'firstname' : ?0 }")
	List<School> findBySchoolCodeIn(String[] schoolCodes);
	
}
