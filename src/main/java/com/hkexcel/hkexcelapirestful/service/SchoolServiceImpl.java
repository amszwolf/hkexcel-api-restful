package com.hkexcel.hkexcelapirestful.service;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hkexcel.hkexcelapirestful.model.School;
import com.hkexcel.hkexcelapirestful.repository.SchoolRepository;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class SchoolServiceImpl implements SchoolService{
	
	@Autowired
	private SchoolRepository schoolRepository;
	private ObjectMapper mapper = new ObjectMapper();
	
	public SchoolServiceImpl() {
		//this.mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		//this.mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
	}
	
	String AddParentAttribute(String fieldName, List<School> dataList) {
		try {
			return mapper.writer().withRootName(fieldName).writeValueAsString(dataList);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
		
	}
	
	String AddJsonAttribute(String json, String fieldName, String fieldVaue) {
		JsonNode jsonNode;
		try {
			jsonNode = mapper.readTree(json);
			//append "total":numeric property to the end of json
			((ObjectNode) jsonNode).put(fieldName, fieldVaue);
			
			//https://stackoverflow.com/questions/23271699/adding-property-to-json-using-jackson
			//JsonNode elem0 = ((ArrayNode) jsonNode).get(0);
			//((ObjectNode) elem0).put("total", total);
			return  mapper.writer().withRootName("").writeValueAsString(jsonNode);		
		} catch (IOException e) {
			return e.getMessage();
		}		
	}
	
	
	

}
