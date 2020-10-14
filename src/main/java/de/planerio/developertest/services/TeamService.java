package de.planerio.developertest.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import de.planerio.developertest.entities.League;
import de.planerio.developertest.entities.Player;
import de.planerio.developertest.entities.Team;
import de.planerio.developertest.repositories.CountryRepository;
import de.planerio.developertest.repositories.LeagueRepository;
import de.planerio.developertest.repositories.TeamRepository;

@Service
public class TeamService {

	public Iterable<Team> getTeams(TeamRepository tRepo) {
		return tRepo.findAll();
	}

	public Team createTeam(TeamRepository tRepo, Team t) {
		int leagueTeamsCount = 0;
		for(Team team : tRepo.findAll()) {
			if (team.getLeague() != null && team.getLeague() == t.getLeague()) {
				leagueTeamsCount++;
				if(leagueTeamsCount >= 20) {
					// maximum number of teams for this league was reached. entity will not be saved.
					// TODO send a proper message
					return null;
				}
			}
		}
		return tRepo.save(t);
	}

	//Only Teams validated by createTeam method will be saved.
	public Iterable<Team> createTeams(TeamRepository tRepo, Iterable<Team> t) {
		ArrayList<Team> returnTeams = new ArrayList<Team>();
		for (Team team : t) {
			Team addTeam = createTeam(tRepo, team);
			if (addTeam != null) {
				returnTeams.add(createTeam(tRepo, team));
			}
		}
		Iterable<Team> it = returnTeams;
		return it;
		//return tRepo.saveAll(t);
	}

	public void deleteTeam(TeamRepository tRepo, long teamId, LeagueRepository lRepo, CountryRepository cRepo) {
		LeagueService lService = new LeagueService();
		Team team = getTeamById(tRepo, teamId);
		League league = team.getLeague();
		
		league.getTeams().remove(team);
		lService.updateLeague(lRepo, league, league.getId(), cRepo);
		
		tRepo.deleteById(teamId);
	}

	public void updateTeam(TeamRepository tRepo, Team updatedTeam, long teamId) {
		Team team = tRepo.findById(teamId).orElseThrow();
		copyTeam(team, updatedTeam, tRepo);
		tRepo.save(team);
	}
	
	private void copyTeam(Team team1, Team team2, TeamRepository tRepo) {
		if(team2.getName() != null) {
			team1.setName(team2.getName());
		}
		if(team2.getLeague() != null) {
			int leagueTeamsCount = 0;
			for(Team team : tRepo.findAll()) {
				if (team.getLeague() != null && team.getLeague() == team2.getLeague()) {
					leagueTeamsCount++;
					if(leagueTeamsCount >= 20) {
						// maximum number of teams for this league was reached. league will not be updated.
						// TODO send a proper message
						break;
					}
				}
			}
			team1.setLeague(team2.getLeague());
		}
		if (team2.getPlayers() != null && team2.getPlayers().size() >= 1 && team2.getPlayers().size() <= 25) {
			team1.setPlayers(team2.getPlayers());
		}
	}
	
	public Team getTeamById(TeamRepository tRepo, long teamId) {
		return tRepo.findById(teamId).orElseThrow();
	}
	
	public Team createTeam(TeamRepository tRepo, Team t, LeagueRepository lRepo, long leagueId) {
		LeagueService lService = new LeagueService();
		League league = lService.getLeagueById(lRepo, leagueId);
		lService.addTeam(lRepo, leagueId, t);
		
		t.setLeague(league);
		return createTeam(tRepo, t);
	}
	
	public Iterable<Team> createTeams(TeamRepository tRepo, Iterable<Team> t, LeagueRepository lRepo, long leagueId) {
		ArrayList<Team> returnTeams = new ArrayList<Team>();
		for (Team team : t) {
			returnTeams.add(createTeam(tRepo, team, lRepo, leagueId));
		}
		Iterable<Team> teams = returnTeams;
		return teams;
	}
	
	public Team addPlayer(TeamRepository tRepo, long teamId, Player p) {
		Team t = getTeamById(tRepo, teamId);
		t.getPlayers().add(p);
		return t;
	}
}
