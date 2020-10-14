package de.planerio.developertest.repositories;

import org.springframework.data.repository.CrudRepository;

import de.planerio.developertest.entities.League;

public interface LeagueRepository extends CrudRepository<League, Long> {
}
