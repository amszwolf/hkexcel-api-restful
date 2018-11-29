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
		//this.mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		//this.mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
	}
	
	@GetMapping(value = "")
	public List<School> listAllSchools()
	{
	return schoolRepository.findAll();
	}
	
	//http://localhost:8080/schools?filter={"schoolCodes"=["CDNIS","LGS","KGS"]}
	//https://stackoverflow.com/questions/21577782/json-parameter-in-spring-mvc-controller/21689084#21689084
	@GetMapping(value = "", params = "filter" , produces = 	MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findManyBySchoolCode( String[] filter1, String filter) throws JsonMappingException, JsonParseException, IOException
	{
		School school = mapper.readValue(filter, School.class);
		
		String result;
		
		int total;
		try {
			List<School> schools = schoolRepository.findByIdIn(filter1);
			
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
	return ResponseEntity.ok()
			.body(result);
	}
	
	@GetMapping(value = "", params = {"filter", "sort", "range"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getListSchools(String filter, String sort, String range) throws JsonMappingException, JsonParseException, IOException
	{
		School school = mapper.readValue(filter, School.class);
		List<School> schools = new ArrayList<School>();
		String result;	
		String sortArray[] = sort.replace("[", "").replace("]", "").replace("\"", "").split(",");
		int[] rangeArray = Stream.of(range.replace("[", "").replace("]", "").split(","))
				.mapToInt(Integer::parseInt)
				.toArray();
		int total = 0;
		try {
			total = schoolRepository.findAll().size();
			schools = schoolRepository.getListOfSchools(school, sortArray, rangeArray);
			//schools = schoolRepository.findBySchoolCodeIn(new String[] {school.getSchoolCode()});

			/*result = mapper.writer().withRootName("data").writeValueAsString(schools);
			JsonNode jsonNode = mapper.readTree(result);
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
		
		return schoolRepository.findById(id)
				.map(school -> {
					school.setFullName(newSchool.getFullName());
					school.setId(newSchool.get_id());
					school.setAddress(newSchool.getAddress());
					return schoolRepository.updateOneSchoolById(school);
				})
				.orElseGet(() -> {
					newSchool.setId(id);
					return schoolRepository.updateOneSchoolById(newSchool);
				});
	}
}




