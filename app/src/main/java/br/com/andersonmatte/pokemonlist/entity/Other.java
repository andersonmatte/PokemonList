package br.com.andersonmatte.pokemonlist.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Other implements Serializable {

    @JsonProperty("official-artwork")
    public OfficialArtwork officialArtwork;

    public OfficialArtwork getOfficialArtwork() {
        return officialArtwork;
    }

    public void setOfficialArtwork(OfficialArtwork officialArtwork) {
        this.officialArtwork = officialArtwork;
    }

}
