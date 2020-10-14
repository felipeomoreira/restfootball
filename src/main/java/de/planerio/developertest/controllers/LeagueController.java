package de.planerio.developertest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.planerio.developertest.entities.League;
import de.planerio.developertest.services.LeagueService;

@RestController
public class LeagueController extends ApplicationController{
	
	private LeagueService lService = new LeagueService();

	@GetMapping("/league")
	public Iterable<League> getLeagues() {
		return lService.getLeagues(lRepo);
	}
	
	@GetMapping("/league/{leagueId}")
	public League getLeagueById(@PathVariable long leagueId) {
		return lService.getLeagueById(lRepo, leagueId);
	}
	
	@GetMapping("/league/language/{language}")
	public Iterable<League> getLeagues(@PathVariable String language) {
		return lService.getLeagues(lRepo, language);
	}

	@PostMapping("/league")
	public League createLeague(@RequestBody League l) {
		return lService.createLeague(lRepo, l);
	}

	@PostMapping("/leagues")
	public Iterable<League> createLeagues(@RequestBody Iterable<League> l) {
		return lService.createLeagues(lRepo, l);
	}

	@PostMapping("/league/delete/{leagueId}")
	public void deleteLeague(@PathVariable long leagueId) {
		lService.deleteLeague(lRepo, leagueId);
	}

	@PostMapping("/league/update/{leagueId}")
	public void updateLeague(@RequestBody League updatedLeague, @PathVariable long leagueId) {
		lService.updateLeague(lRepo, updatedLeague, leagueId, cRepo);
	}
	
	@PostMapping("/league/create/{countryId}")
	public League createLeague(@RequestBody League l, @PathVariable long countryId) {
		return lService.createLeague(lRepo, l, cRepo, countryId);
	}

}
