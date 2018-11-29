package com.hkexcel.hkexcelapirestful.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hkexcel.hkexcelapirestful.model.School;
import com.hkexcel.hkexcelapirestful.repository.SchoolRepository;
import com.hkexcel.hkexcelapirestful.service.SchoolService;

@RestController
@RequestMapping(value = "/api/v1/schools")
@CrossOrigin(origins = "http://localhost:3000")
public class SchoolController {
	
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private SchoolRepository schoolRepository;
	private ObjectMapper mapper = new ObjectMapper();
	HttpHeaders responseHeaders = new HttpHeaders();
	
	public SchoolController() {
		responseHeaders.set("Access-Control-Expose-Headers", "Content-Range");
	}
	
	@GetMapping(value = "")
	public List<School> listAllSchools()
	{
		return schoolRepository.findAll();
	}
	
	//https://stackoverflow.com/questions/21577782/json-parameter-in-spring-mvc-controller/21689084#21689084
	@GetMapping(value = "", params = {"filter", "sort", "range"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getListSchools(String filter, String sort, String range) throws JsonMappingException, JsonParseException, IOException
	{
		School school = mapper.readValue(filter, School.class);
		List<School> schools = new ArrayList<School>();
		String result;	
		String sortArray[] = convertQueryArrayToStringArray(sort);
		int[] rangeArray = convertQueryArrayToIntArray(range);
		int total = 0;
		try {
			total = schoolRepository.findAll().size();
			schools = schoolRepository.getListOfSchools(school, sortArray, rangeArray);			
			result = mapper.writeValueAsString(schools);
			
		} catch (IOException e) {
			result = e.getMessage();
		}
		responseHeaders.set("Content-Range", String.format("schools 0-%d/%d", rangeArray[1], total)); //"schools 0-24/319"
		return ResponseEntity.ok()
				.headers(responseHeaders)
				.body(result);
	}
	
	/*@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	School findById(@PathVariable String id) {
		return schoolRepository.findById(id)
				.orElseThrow(() -> new SchoolNotFoundException(id));
	}*/
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	School findById(@PathVariable String id) {
		return schoolRepository.findOneSchoolById(id);
	}
	
	@PostMapping("")
	School newSchool(@RequestBody School newSchool) {
		return schoolRepository.updateOneSchoolById(newSchool);
	}
	
	@DeleteMapping("/{id}")
	void deleteSchool(@PathVariable String id) {
		schoolRepository.deleteOneById(id);
	}
	
	@PutMapping("/{id}")
	School replaceSchool(@RequestBody School newSchool, @PathVariable String id) {
		return schoolRepository.updateOneSchoolById(newSchool);
	}
	
	private String[] convertQueryArrayToStringArray(String queryArray) {
		try {
			return queryArray.replace("[", "").replace("]", "").replace("\"", "").split(",");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	private int[] convertQueryArrayToIntArray(String queryArray) {
		try {
			return Stream.of(queryArray.replace("[", "").replace("]", "").split(","))
					.mapToInt(Integer::parseInt)
					.toArray();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	
}




