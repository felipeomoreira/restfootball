package de.planerio.developertest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.planerio.developertest.entities.Player;
import de.planerio.developertest.services.PlayerService;

@RestController
public class PlayerController extends ApplicationController{

	private PlayerService pService = new PlayerService();
	
	@GetMapping("/player")
    public Iterable<Player> getPlayers() {
		return pService.getPlayers(pRepo);
    }
	
	@GetMapping("/player/{playerId}")
    public Player getPlayerById(@PathVariable long playerId) {
		return pService.getPlayerById(pRepo, playerId);
    }
	
	@GetMapping("/player/position/{position}")
    public Iterable<Player> getPlayersByPosition(@PathVariable String position) {
		return pService.getPlayersByPosition(pRepo, position);
    }
	
	@GetMapping("/player/defense/{order}")
    public Iterable<Player> getDefensePlayersSortedByNameOrdered(@PathVariable String order) {
		return pService.getDefensePlayersSortedByNameOrdered(pRepo, order);
    }
    
    @PostMapping("/player")
    public Player createPlayer(@RequestBody Player p) {
    	return pService.createPlayer(pRepo, p);
    }
    
    @PostMapping("/players")
    public Iterable<Player> createPlayers(@RequestBody Iterable<Player> p) {
    	return pService.createPlayers(pRepo, p);
    }

    @PostMapping("/player/delete/{playerId}")
    public void deletePlayer(@PathVariable long playerId) {
    	pService.deletePlayer(pRepo, playerId, tRepo);
    }
    
    @PostMapping("/player/update/{playerId}")
    public void updatePlayer(@RequestBody Player updatedPlayer, @PathVariable long playerId) {
    	pService.updatePlayer(pRepo, updatedPlayer, playerId);
    }

	@PostMapping("/player/create/{teamId}")
	public Player createPlayer(@RequestBody Player p, @PathVariable long teamId) {
		return pService.createPlayer(pRepo, p, tRepo, teamId);
	}
	
	@PostMapping("/players/create/{teamId}")
	public Iterable<Player> createPlayers(@RequestBody Iterable<Player> p, @PathVariable long teamId) {
		return pService.createPlayers(pRepo, p, tRepo, teamId);
	}
}
