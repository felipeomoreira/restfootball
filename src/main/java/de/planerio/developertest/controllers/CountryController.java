package de.planerio.developertest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.planerio.developertest.entities.Country;
import de.planerio.developertest.services.CountryService;

@RestController
public class CountryController extends ApplicationController{

	private CountryService cService = new CountryService();

	@GetMapping("/country")
	public Iterable<Country> getCountries() {
		return cService.getCountries(cRepo);
	}
	
	@GetMapping("/country/{countryId}")
	public Country getCountryById(@PathVariable long countryId) {
		return cService.getCountryById(cRepo, countryId);
	}

	@PostMapping("/country")
	public Country createCountry(@RequestBody Country c) {
		return cService.createCountry(cRepo, c);
	}

	@PostMapping("/countries")
	public Iterable<Country> createCountries(@RequestBody Iterable<Country> c) {
		return cService.createCountries(cRepo, c);
	}

	@PostMapping("/country/delete/{countryId}")
	public void deleteCountry(@PathVariable long countryId) {
		cService.deleteCountry(cRepo, countryId);
	}

	@PostMapping("/country/update/{countryId}")
	public void updateCountry(@RequestBody Country updatedCountry, @PathVariable long countryId) {
		try {
			cService.updateCountry(cRepo, updatedCountry, countryId);
		} catch (Exception e) {
			// TODO treat exception properly
			e.printStackTrace();
		}
	}
}
