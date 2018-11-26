 package com.hkexcel.hkexcelapirestful.repository;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.hkexcel.hkexcelapirestful.model.School;

@Repository
public class SchoolRepositoryImpl implements SchooRepositoryCustom{
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public School updateOneSchoolBySchoolCode(School school) {
		Query query = new Query(
				Criteria.where("schoolCode").is(school.getSchoolCode())
				);
		Update update = new Update();
		update.set("schoolCode", school.getSchoolCode());
		update.set("fullName", school.getFullName());
		update.set("city", school.getCity());
		update.set("country", school.getCountry());
		update.set("address", school.getAddress());
		update.set("updatedAt", new Date());
		update.setOnInsert("createdAt", new Date());
		
		//mongoTemplate.upsert(query, update, School.class);
		
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		options.upsert(true);
		
		return mongoTemplate.findAndModify(query, update, options, School.class);						
	}

}
