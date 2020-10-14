package de.planerio.developertest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import de.planerio.developertest.repositories.CountryRepository;
import de.planerio.developertest.repositories.LeagueRepository;
import de.planerio.developertest.repositories.PlayerRepository;
import de.planerio.developertest.repositories.TeamRepository;

@RestController
public class ApplicationController {

	@Autowired
	protected TeamRepository 	tRepo; // team repository
	@Autowired
	protected LeagueRepository 	lRepo; // league repository
	@Autowired
	protected CountryRepository cRepo; // country repository
	@Autowired
	protected PlayerRepository 	pRepo; // player repository
}
