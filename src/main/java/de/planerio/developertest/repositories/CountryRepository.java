package de.planerio.developertest.repositories;

import org.springframework.data.repository.CrudRepository;

import de.planerio.developertest.entities.Country;

public interface CountryRepository extends CrudRepository<Country, Long> {
}
