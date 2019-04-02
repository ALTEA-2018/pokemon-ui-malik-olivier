package com.miage.altea.tp.pokemon_ui.pokemonTypes.service.impl;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.TrainerDetails;
import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.TrainerDetailsService;
import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TrainerDetailsServiceImpl implements TrainerDetailsService {
    private TrainerService trainerService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var trainer = trainerService.getTrainer(s);
        if(trainer == null) throw  new BadCredentialsException("No such user");
        return new TrainerDetails(trainer);
    }

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }
}
