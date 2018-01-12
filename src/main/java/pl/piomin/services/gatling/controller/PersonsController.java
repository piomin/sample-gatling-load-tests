package pl.piomin.services.gatling.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.gatling.model.Person;
import pl.piomin.services.gatling.repository.PersonsRepository;

@RestController
@RequestMapping("/persons")
public class PersonsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonsController.class);
	
	@Autowired
	PersonsRepository repository;
	
	@GetMapping
	public List<Person> findAll() {
		return (List<Person>) repository.findAll();
	}
	
	@PostMapping
	public Person add(@RequestBody Person person) {
		Person p = repository.save(person);
		LOGGER.info("add: {}", p.toString());
		return p;
	}
	
	@GetMapping("/{id}")
	public Person findById(@PathVariable("id") Long id) {
		LOGGER.info("findById: id={}", id);
		return repository.findOne(id);
	}
	
}
