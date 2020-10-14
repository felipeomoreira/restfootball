package de.planerio.developertest.repositories;

import org.springframework.data.repository.CrudRepository;

import de.planerio.developertest.entities.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {
}
