package com.miage.altea.tp.pokemon_ui.pokemonTypes.service.impl;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.PokemonType;
import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.PokemonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PokemonTypeServiceImpl implements PokemonTypeService {
    private RestTemplate restTemplate;
    private String pokemonServiceUrl;

    @Cacheable("pokemon-types")
    @Retryable
    @Override
    public List<PokemonType> listPokemonsTypes() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptLanguageAsLocales(List.of(LocaleContextHolder.getLocale()));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        PokemonType[] pokemons = restTemplate.exchange(pokemonServiceUrl + "/pokemon-types/", HttpMethod.GET, entity, PokemonType[].class).getBody();
        return Arrays.asList(pokemons);
    }

    @Override
    @Retryable
    @Cacheable("pokemon-types")
    public PokemonType getPokemonType(int id) {
        var pokemon = restTemplate.getForObject(pokemonServiceUrl+"/pokemon-types/{id}",PokemonType.class,id);
        return pokemon;
    }

    @Autowired
    @Override
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${pokemonType.service.url}")
    public void setPokemonTypeServiceUrl(String pokemonServiceUrl) {
        this.pokemonServiceUrl = pokemonServiceUrl;
    }
}
