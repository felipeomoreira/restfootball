package de.planerio.developertest.services;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import de.planerio.developertest.entities.Country;
import de.planerio.developertest.repositories.CountryRepository;

@Service
public class CountryService {

	//Constants defined in project specification. 
	private final Set<String> languages = Set.of("de", "fr", "en", "es", "it");

	public Iterable<Country> getCountries(CountryRepository cRepo) {
		return cRepo.findAll();
	}

	public Country createCountry(CountryRepository cRepo, Country c) {
		if (c.getLanguage() != null && languages.contains(c.getLanguage()))
			return cRepo.save(c);
		else
			// an invalid language was sent. entity will not be saved.
			// TODO send a proper message
			return null;
	}

	/*
	 * Only countries with a valid language will be saved and returned as response.
	 */
	public Iterable<Country> createCountries(CountryRepository cRepo, Iterable<Country> c) {
		ArrayList<Country> returnCountries = new ArrayList<Country>();
		for (Country country : c) {
			Country addCountry = createCountry(cRepo, country);
			if (addCountry != null) {
				returnCountries.add(createCountry(cRepo, country));
			}
		}
		Iterable<Country> it = returnCountries;
		return it;

		// return cRepo.saveAll(c);
	}

	public void deleteCountry(CountryRepository cRepo, long countryId) {
		cRepo.deleteById(countryId);
	}

	//The fields are validated before update
	public void updateCountry(CountryRepository cRepo, @RequestBody Country updatedCountry,
			@PathVariable long countryId) throws Exception {
		Country country = cRepo.findById(countryId).orElseThrow();
		copyCountry(country, updatedCountry);
		cRepo.save(country);
	}
	
	//Copy data from object country2 to object country1 validating the fields properly
	private void copyCountry(Country country1, Country country2) throws Exception {
		if(country2.getLanguage() != null) {
			if(languages.contains(country2.getLanguage())){
				country1.setLanguage(country2.getLanguage());
			} else {
				//if an invalid language is set for update nothing is changed.
				throw new Exception("An invalid language was set for update.");
			}
		}	
		
		if(country2.getName() != null) 
				country1.setName(country2.getName());
	}
	
	public Country getCountryById(CountryRepository cRepo, long countryId) {
		return cRepo.findById(countryId).orElseThrow();
	}
}
