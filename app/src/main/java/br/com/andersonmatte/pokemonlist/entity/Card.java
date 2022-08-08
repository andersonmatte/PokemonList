package br.com.andersonmatte.pokemonlist.entity;

public class Card {

    private String nome;
    private int imagemgid;

    public Card(String nome, int imagemgid) {
        this.nome = nome;
        this.imagemgid = imagemgid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getImagemgid() {
        return imagemgid;
    }

    public void setImagemgid(int imagemgid) {
        this.imagemgid = imagemgid;
    }

}
