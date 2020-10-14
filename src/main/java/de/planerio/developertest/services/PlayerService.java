package de.planerio.developertest.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import de.planerio.developertest.entities.Player;
import de.planerio.developertest.entities.Team;
import de.planerio.developertest.repositories.PlayerRepository;
import de.planerio.developertest.repositories.TeamRepository;

public class PlayerService {
	
	//Constants defined in project specification. 
	private final Set<String> positions = Set.of("GK", "CB", "RB", "LB", "LWB", "RWB", "CDM", "CM", "LM", "RM", "CAM", "ST", "CF");

	public Iterable<Player> getPlayers(PlayerRepository pRepo) {
		return pRepo.findAll();
	}
	
	//returns players with specified position
	public Iterable<Player> getPlayersByPosition(PlayerRepository pRepo, String position) {
		ArrayList<Player> returnPlayers = new ArrayList<Player>();
		for (Player player : getPlayers(pRepo)) {
			if(player.getPosition().equals(position))
				returnPlayers.add(player);			
		}
		Iterable<Player> it = returnPlayers;
		return it;
	}
	
	//returns all defense players (position ending with 'B' and 'GK') sorted by their names.
	//Descending order is defined by setting "desc" as order
	public Iterable<Player> getDefensePlayersSortedByNameOrdered(PlayerRepository pRepo, String order) {
		ArrayList<Player> returnPlayers = new ArrayList<Player>();
		for (Player player : getPlayers(pRepo)) {
			if(player.getPosition().charAt(player.getPosition().length() - 1) == 'B' || player.getPosition().equals("GK")){
				returnPlayers.add(player);		
			}
		}
		Collections.sort(returnPlayers, new Comparator<Player>() {
			public int compare(Player p1, Player p2) {
				if(order.equals("desc")) {
					return (p2.getName().compareTo(p1.getName()));
				} else { // consider ascending order whenever DESC is not requested
					return (p1.getName().compareTo(p2.getName()));
				}
			}
		});
		
			
		Iterable<Player> it = returnPlayers;
		return it;
	}

	//creates a player validating some project definitions
	//position cannot be different from constant defined
	//shirt number must be a value between 1 and 99 and
	//there cannot be more than one player with the same shit number in the team
	public Player createPlayer(PlayerRepository pRepo, Player p) {
		if (p.getPosition() != null && positions.contains(p.getPosition()) //validate position
				&& p.getShirtNumber() >= 1 && p.getShirtNumber() <= 99) {  //validate shirt number
			
			// validate non duplicate shirt number in the same team
			long teamId = p.getTeam().getId();
			
			int teamPlayersCount = 0;
			for (Player player : getPlayers(pRepo)) {
				if(player.getTeam().getId() == teamId) {
					teamPlayersCount++;
					if(teamPlayersCount >= 25) {
						// maximum number of players for this team was reached. entity will not be saved.
						// TODO send a proper message
						return null;
					}
					if(player.getShirtNumber() == p.getShirtNumber()) {
						// there is a player in the same team with this shirt number. entity will not be saved.
						// TODO send a proper message
						return null;
					}
				}
			}
			
			return pRepo.save(p);
		}
		else
			// an invalid condition was reached. entity will not be saved.
			// TODO send a proper message
			return null;
	}

	//Only Players validated by createPlayer method will be saved.
	public Iterable<Player> createPlayers(PlayerRepository pRepo, Iterable<Player> p) {
		ArrayList<Player> returnPlayers = new ArrayList<Player>();
		for (Player player : p) {
			Player addPlayer = createPlayer(pRepo, player);
			if (addPlayer != null) {
				returnPlayers.add(createPlayer(pRepo, player));
			}
		}
		Iterable<Player> it = returnPlayers;
		return it;
		//return pRepo.saveAll(p);
	}

	public void deletePlayer(PlayerRepository pRepo, long playerId, TeamRepository tRepo) {
		TeamService tService = new TeamService();
		Player player = getPlayerById(pRepo, playerId);
		Team team = player.getTeam();
		
		team.getPlayers().remove(player);
		tService.updateTeam(tRepo, team, team.getId());
		
		pRepo.deleteById(playerId);
	}

	public void updatePlayer(PlayerRepository pRepo, Player updatedPlayer, long playerId) {
		Player player = pRepo.findById(playerId).orElseThrow();
		if(updatedPlayer.getTeam() == null) {
			updatedPlayer.setTeam(player.getTeam());
		}
		copyPlayer(player, updatedPlayer, pRepo);
		pRepo.save(player);
	}
	
	private void copyPlayer(Player player1, Player player2, PlayerRepository pRepo) {
		if(player2.getName() != null) {
			player1.setName(player2.getName());
		}
		if(player2.getPosition() != null) {
			if(positions.contains(player2.getPosition()))
				player1.setPosition(player2.getPosition());
			else {
				//invalid position. it will not be copied.
			}
		}
		if(player2.getShirtNumber() >= 1 && player2.getShirtNumber() <= 99) {
			// validate non duplicate shirt number in the same team
			long teamId = player2.getTeam().getId();
			for (Player player : getPlayers(pRepo)) {
				if (player.getTeam().getId() == teamId) {
					if (player.getShirtNumber() == player2.getShirtNumber()) {
						// there is a player in the same team with this shirt number. shirt number will not be updated.
						// TODO send a proper message
						break;
					}
				}
			}
			player1.setShirtNumber(player2.getShirtNumber());
		}
		if(player2.getTeam() != null) {
			long teamId = player2.getTeam().getId();
			int teamPlayersCount = 0;
			for (Player player : getPlayers(pRepo)) {
				if(player.getTeam().getId() == teamId) {
					teamPlayersCount++;
					if(teamPlayersCount >= 25) {
						// maximum number of players for this team was reached. team will not be updated.
						// TODO send a proper message
						break;
					}
				}
			}
		}
	}
	
	public Player createPlayer(PlayerRepository pRepo, Player p, TeamRepository tRepo, long teamId) {
		TeamService tService = new TeamService();
		Team team = tService.getTeamById(tRepo, teamId);
		tService.addPlayer(tRepo, teamId, p);
		
		p.setTeam(team);
		return createPlayer(pRepo, p);
	}
	
	public Iterable<Player> createPlayers(PlayerRepository pRepo, Iterable<Player> p, TeamRepository tRepo, long teamId) {
		ArrayList<Player> returnPlayers = new ArrayList<Player>();
		for (Player player : p) {
			returnPlayers.add(createPlayer(pRepo, player, tRepo, teamId));
		}
		Iterable<Player> players = returnPlayers;
		return players;
	}
	
	public Player getPlayerById(PlayerRepository pRepo, long playerId) {
		return pRepo.findById(playerId).orElseThrow();
	}
}
