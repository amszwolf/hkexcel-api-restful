package com.hkexcel.hkexcelapirestful.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.hkexcel.hkexcelapirestful.model.School;

@RunWith(SpringRunner.class)
@WebMvcTest(SchoolController.class)
public class SchoolControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SchoolController schoolController;
	
	@Test
    public void schoolControllerInitializedCorrectly() {
        assertThat(schoolController).isNotNull();
    }
	
	@Test
	public void getAllArrivals() throws Exception {

	}

	@Test
	public void getSchoolById() throws Exception {
		School school = new School();
		school.setId("AMIS");

		given(schoolController.getSchoolById(school.getId())).willReturn(school);

		mvc.perform(get("/" + school.getId())
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id", is(school.getId())));

	}
}
