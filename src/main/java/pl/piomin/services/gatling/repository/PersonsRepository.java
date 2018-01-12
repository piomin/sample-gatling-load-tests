package pl.piomin.services.gatling.repository;

import org.springframework.data.repository.CrudRepository;

import pl.piomin.services.gatling.model.Person;

public interface PersonsRepository extends CrudRepository<Person, Long> {
	
}
