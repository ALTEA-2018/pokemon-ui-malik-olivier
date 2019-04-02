package com.miage.altea.tp.pokemon_ui.pokemonTypes.service.impl;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.Trainer;
import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.PokemonTypeService;
import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {
    private RestTemplate restTemplate;
    private String trainerServiceUrl;
    private PokemonTypeService pokemonTypeService;

    @Cacheable(value="getTrainers")
    @Retryable
    @Override
    public List<Trainer> getTrainers() {
        var trainers = restTemplate.getForObject(trainerServiceUrl+"/trainers/",Trainer[].class);
        var trainersList = Arrays.asList(trainers);
        trainersList.parallelStream().forEach(trainer -> {
            trainer.getTeam().parallelStream().forEach(pokemon -> {
                pokemon.setType(pokemonTypeService.getPokemonType(pokemon.getPokemonType()));
            });
        });
        return trainersList;
    }

    @Override
    public Trainer getTrainer(String name) {
        var trainer = restTemplate.getForObject(trainerServiceUrl+"/trainers/{name}",Trainer.class,name);
        return trainer;
    }

    @Autowired
    @Qualifier("trainerApiRestTemplate")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${trainers.service.url}")
    public void setTrainerServiceUrl(String pokemonServiceUrl) {
        this.trainerServiceUrl = pokemonServiceUrl;
    }

    public PokemonTypeService getPokemonTypeService() {
        return pokemonTypeService;
    }

    @Autowired
    public void setPokemonTypeService(PokemonTypeService pokemonTypeService) {
        this.pokemonTypeService = pokemonTypeService;
    }
}
