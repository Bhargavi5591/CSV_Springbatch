package com.example.demo.config;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.entity.Person;

public class Processor implements ItemProcessor<Person,Person>{

	@Override
	public Person process(Person person) throws Exception {
		return person;
	}

}
