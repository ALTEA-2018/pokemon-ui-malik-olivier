package com.miage.altea.tp.pokemon_ui.pokemonTypes.bo;

import java.io.Serializable;

public class Sprites implements Serializable {
    private String back_default;
    private String front_default;

    public String getBack_default() {
        return back_default;
    }

    public void setBack_default(String back_default) {
        this.back_default = back_default;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }
}
