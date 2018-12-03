package com.hkexcel.hkexcelapirestful.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hkexcel.hkexcelapirestful.model.School;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SchoolRepositoryTest {
	
	@Autowired
	private SchoolRepository schoolRepository;

	@Test
	public void whenFindAll() {
		// given
		School firstSchool = new School();
		firstSchool.setId("FIS");

		School secondSchool = new School();
		secondSchool.setId("AMIS");

		// when
		List<School> schools = schoolRepository.findAll();

		// then
		assertThat(schools.size()).isGreaterThan(0);
		//assertThat(schools.get(7)).isEqualTo(firstArrival);
		//assertThat(schools.get(8)).isEqualTo(secondArrival);
	}
}
