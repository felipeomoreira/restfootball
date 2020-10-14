package de.planerio.developertest.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import de.planerio.developertest.entities.Country;
import de.planerio.developertest.entities.League;
import de.planerio.developertest.entities.Team;
import de.planerio.developertest.repositories.CountryRepository;
import de.planerio.developertest.repositories.LeagueRepository;

@Service
public class LeagueService {
	
	public Iterable<League> getLeagues(LeagueRepository lRepo) {
		return lRepo.findAll();
	}
	
	//returns only the leagues with specified language
	public Iterable<League> getLeagues(LeagueRepository lRepo, String language) {
		ArrayList<League> returnLeagues = new ArrayList<League>();
		for (League league : getLeagues(lRepo)) {
			if(league.getCountry().getLanguage().equals(language))
				returnLeagues.add(league);			
		}
		Iterable<League> it = returnLeagues;
		return it;
	}

	public League createLeague(LeagueRepository lRepo, League l) {
		return lRepo.save(l);
	}

	public Iterable<League> createLeagues(LeagueRepository lRepo, Iterable<League> l) {
		return lRepo.saveAll(l);
	}

	public void deleteLeague(LeagueRepository lRepo, long leagueId) {
		lRepo.deleteById(leagueId);
	}

	//fields are validated before update
	public void updateLeague(LeagueRepository lRepo, League updatedLeague, long leagueId, CountryRepository cRepo) {
		League league =  lRepo.findById(leagueId).orElseThrow();
		copyLeague(league, updatedLeague, cRepo);
		lRepo.save(league);
	}
	
	//Copy data from object league2 to league1 validating the fields properly
	private void copyLeague(League league1, League league2, CountryRepository cRepo) {
		if(league2.getName() != null) {
			league1.setName(league2.getName());
		}
		if(league2.getCountry() != null) {
			for (Country country : cRepo.findAll()) {
				if (country.getLanguage().equals(league2.getCountry().getLanguage())){
					break;
				}
			}
			league1.setCountry(league2.getCountry());
		}
		if(league2.getTeams() != null && league2.getTeams().size() >= 1 && league2.getTeams().size() <= 20) {
			league1.setTeams(league2.getTeams());
		}
	}
	
	public League createLeague(LeagueRepository lRepo, League l, long countryId) {
		return lRepo.save(l);
	}
	
	public League getLeagueById(LeagueRepository lRepo, long leagueId) {
		return lRepo.findById(leagueId).orElseThrow();
	}
	
	//create a league with the specified country
	public League createLeague(LeagueRepository lRepo, League l, CountryRepository cRepo, long countryId) {
		CountryService cService = new CountryService();
		Country country = cService.getCountryById(cRepo, countryId);
		l.setCountry(country);
		return createLeague(lRepo, l);
	}
	
	public League addTeam(LeagueRepository lRepo, long leagueId, Team t) {
		League l = getLeagueById(lRepo, leagueId);
		l.getTeams().add(t);
		return l;
	}
}
