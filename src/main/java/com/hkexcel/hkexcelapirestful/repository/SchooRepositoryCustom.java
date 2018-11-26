package com.hkexcel.hkexcelapirestful.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.hkexcel.hkexcelapirestful.model.School;

public interface SchooRepositoryCustom {

	School updateOneSchoolBySchoolCode(School school);
	
}
