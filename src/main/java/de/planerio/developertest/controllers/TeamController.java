package de.planerio.developertest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.planerio.developertest.entities.Team;
import de.planerio.developertest.repositories.TeamRepository;
import de.planerio.developertest.services.TeamService;

@RestController
public class TeamController extends ApplicationController{

	@Autowired
	private TeamRepository tRepo;

	private TeamService tService = new TeamService();

	@GetMapping("/team")
	public Iterable<Team> getTeams() {
		return tService.getTeams(tRepo);
	}
	
	@GetMapping("/team/{teamId}")
	public Team getTeamById(@PathVariable long teamId) {
		return tService.getTeamById(tRepo, teamId);
	}
	
	@PostMapping("/team")
	public Team createTeam(@RequestBody Team t) {
		return tService.createTeam(tRepo, t);
	}

	@PostMapping("/teams")
	public Iterable<Team> createTeams(@RequestBody Iterable<Team> t) {
		return tService.createTeams(tRepo, t);
	}

	@PostMapping("/team/delete/{teamId}")
	public void deleteTeam(@PathVariable long teamId) {
		tService.deleteTeam(tRepo, teamId, lRepo, cRepo);
	}

	@PostMapping("/team/update/{teamId}")
	public void updateTeam(@RequestBody Team updatedTeam, @PathVariable long teamId) {
		tService.updateTeam(tRepo, updatedTeam, teamId);
	}
	
	@PostMapping("/team/create/{leagueId}")
	public Team createTeam(@RequestBody Team t, @PathVariable long leagueId) {
		return tService.createTeam(tRepo, t, lRepo, leagueId);
	}
	
	@PostMapping("/teams/create/{leagueId}")
	public Iterable<Team> createTeams(@RequestBody Iterable<Team> t, @PathVariable long leagueId) {
		return tService.createTeams(tRepo, t, lRepo, leagueId);
	}

}
