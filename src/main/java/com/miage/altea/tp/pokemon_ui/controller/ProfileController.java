package com.miage.altea.tp.pokemon_ui.controller;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.PokemonTypeService;
import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller

public class ProfileController {
    @Autowired
    private TrainerService trainerService;

    @Autowired
    private PokemonTypeService pokemonService;

    @GetMapping("/profile")
    public ModelAndView profile(Principal principal) {
        var modelAndView = new ModelAndView("profile");
        var trainer = trainerService.getTrainer(principal.getName());
        trainer.getTeam().stream().forEach(pokemon -> pokemon.setType(pokemonService.getPokemonType(pokemon.getPokemonType())));
//        trainer.setTeam(pokemons);
        modelAndView.addObject("trainer", trainer);
        return modelAndView;
    }
}
