package com.hkexcel.hkexcelapirestful.controller;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping(value = "/schools")
public class SchoolController {
	
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private SchoolRepository schoolRepository;
	private ObjectMapper mapper = new ObjectMapper();
	HttpHeaders responseHeaders = new HttpHeaders();
	
	public SchoolController() {
		responseHeaders.set("Access-Control-Expose-Headers", "Content-Range");
		//this.mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		//this.mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
	}
	//private final ObjectWriter writer = mapper.writer().withRootName("data");
	
	@GetMapping(value = "")
	public List<School> listAllSchools()
	{
	return schoolRepository.findAll();
	}
	
	//http://localhost:8080/schools?filter={"schoolCodes"=["CDNIS","LGS","KGS"]}
	//https://stackoverflow.com/questions/21577782/json-parameter-in-spring-mvc-controller/21689084#21689084
	@GetMapping(value = "", params = "filter" , produces = 	MediaType.APPLICATION_JSON_VALUE)
	public String /*List<School>*/ findManyBySchoolCode( String[] filter1, String filter) throws JsonMappingException, JsonParseException, IOException
	{
		School school = mapper.readValue(filter, School.class);
		
		String result;
		
		int total;
		try {
			List<School> schools = schoolRepository.findBySchoolCodeIn(filter1);
			
			result = mapper.writer().withRootName("data").writeValueAsString(schools);
			JsonNode jsonNode = mapper.readTree(result);
			total = schools.size(); 
			
			//append "total":numeric property to the end of json
			((ObjectNode) jsonNode).put("total", total);
			//https://stackoverflow.com/questions/23271699/adding-property-to-json-using-jackson
			//JsonNode elem0 = ((ArrayNode) jsonNode).get(0);
			//((ObjectNode) elem0).put("total", total);
			result = mapper.writer().withRootName("").writeValueAsString(jsonNode); 
			
		} catch (/*JsonProcessingException |*/ IOException e) {
			result = e.getMessage();
		}
	return result;
	}
	
	//@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "", params = {"filter", "sort", "range"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String>/*List<School>*/ getListSchools( String[] filter1, String filter) throws JsonMappingException, JsonParseException, IOException
	{
		School school = mapper.readValue(filter, School.class);
		String result;
		
		int total;
		try {
			//List<School> schools = schoolRepository.findBySchoolCodeIn(filter1);
			List<School> schools = schoolRepository.findAll();
			
			/*result = mapper.writer().withRootName("data").writeValueAsString(schools);
			JsonNode jsonNode = mapper.readTree(result);
			total = schools.size(); 
			
			//append "total":numeric property to the end of json
			((ObjectNode) jsonNode).put("total", total);
			
			//https://stackoverflow.com/questions/23271699/adding-property-to-json-using-jackson
				//JsonNode elem0 = ((ArrayNode) jsonNode).get(0);
				//((ObjectNode) elem0).put("total", total);
			result = mapper.writer().withRootName("").writeValueAsString(jsonNode);*/
			
			result = mapper.writeValueAsString(schools);
			
		} catch (/*JsonProcessingException |*/ IOException e) {
			result = e.getMessage();
		}
		responseHeaders.set("Content-Range", "schools 0-24/319");
	return ResponseEntity.ok()
			.headers(responseHeaders)
			.body(result);
	}
	
	
	/*@GetMapping(value = "/{id}")
	Optional<School> findById(@PathVariable String id) {
		return schoolRepository.findById(id);
			//.orElseThrow(() -> new SchoolNotFoundException(id));
	}*/
	
	@GetMapping(value = "/{schoolCode}"/*, produces = MediaType.APPLICATION_JSON_VALUE*/)
	School findBySchoolCode(@PathVariable String schoolCode) {
		return schoolRepository.findOneBySchoolCode(schoolCode);
	}
	
	
	@PostMapping("")
	School newSchool(@RequestBody School newSchool) {
		return schoolRepository.updateOneSchoolBySchoolCode(newSchool);
	}
	
	@DeleteMapping("/{id}")
	void deleteSchool(@PathVariable String id) {
		schoolRepository.deleteById(id);
	}
	
}




