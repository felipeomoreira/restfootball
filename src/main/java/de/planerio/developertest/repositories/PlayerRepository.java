package de.planerio.developertest.repositories;

import org.springframework.data.repository.CrudRepository;

import de.planerio.developertest.entities.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {
}
