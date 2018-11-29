 package com.hkexcel.hkexcelapirestful.repository;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hkexcel.hkexcelapirestful.model.School;

@Repository
public class SchoolRepositoryImpl implements SchooRepositoryCustom{
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public School updateOneSchoolById(School school) {
		Query query = new Query(
				Criteria.where("id").is(school.getId())
				);
		Update update = new Update();
		update.set("id", school.getId());
		update.set("fullName", school.getFullName());
		if(!StringUtils.isEmpty(school.getAddress())) {
			update.set("address.$.city", school.getAddress().getCity());
			update.set("address.$.country", school.getAddress().getCountry());
			update.set("address.$.streetHouse", school.getAddress().getStreetHouse());
			update.set("address.$.zipCode", school.getAddress().getZipCode());
		}	
		update.set("updatedAt", new Date());
		update.setOnInsert("createdAt", new Date());
		
		//mongoTemplate.upsert(query, update, School.class);
		
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		options.upsert(true);
		
		return mongoTemplate.findAndModify(query, update, options, School.class);						
	}

	@Override
	public List<School> getListOfSchools(School school, String[] sortArray, int[] range) {
		
		//https://stackoverflow.com/questions/3333974/how-to-loop-over-a-class-attributes-in-java
		/*try {
			BeanInfo schoolInfo = Introspector.getBeanInfo(School.class);
		} catch (IntrospectionException e) {
			System.out.print(e.getMessage());
		}*/
		Query query = new Query();
		if(!StringUtils.isEmpty(school.getFullName())) {
			query.addCriteria(Criteria.where("fullName").regex(Pattern.compile(Pattern.quote(school.getFullName()), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		}
		if(!StringUtils.isEmpty(school.getId())) {
			query.addCriteria(Criteria.where("id").regex(Pattern.compile(Pattern.quote(school.getId()), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		}
		if(!StringUtils.isEmpty(school.getAddress())){
			if(!StringUtils.isEmpty(school.getAddress().getCountry())) {
				query.addCriteria(Criteria.where("address.country").is(school.getAddress().getCountry()));
			}
			if(!StringUtils.isEmpty(school.getAddress().getCity())) {
				query.addCriteria(Criteria.where("address.city").is(school.getAddress().getCity()));
			}
		}
				
		Sort sort = (sortArray[1].equals("ASC") ? new Sort(Sort.Direction.ASC, sortArray[0]) : new Sort(Sort.Direction.DESC, sortArray[0]));
		query.with(PageRequest.of(range[0]/(range[1]-range[0]+1), range[1]-range[0]+1, sort));
		
		return mongoTemplate.find(query, School.class);
		
	}
	
}
