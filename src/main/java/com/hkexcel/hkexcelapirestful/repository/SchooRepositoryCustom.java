package com.hkexcel.hkexcelapirestful.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.hkexcel.hkexcelapirestful.model.School;

public interface SchooRepositoryCustom {

	School updateOneSchoolById(School school);
	List<School> getListOfSchools(School school, String[] sort, int[] range);
	
}
