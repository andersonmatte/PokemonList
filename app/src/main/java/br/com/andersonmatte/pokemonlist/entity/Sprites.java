package br.com.andersonmatte.pokemonlist.entity;

import java.io.Serializable;

public class Sprites implements Serializable {

    public Other other;

    public Other getOther() {
        return other;
    }

    public void setOther(Other other) {
        this.other = other;
    }

}
