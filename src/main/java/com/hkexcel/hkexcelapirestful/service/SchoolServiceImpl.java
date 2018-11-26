package com.hkexcel.hkexcelapirestful.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hkexcel.hkexcelapirestful.repository.SchoolRepository;
import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class SchoolServiceImpl implements SchoolService{
	
	@Autowired
	private SchoolRepository schoolRepository;

}
