package site.nomoreparties.stellarburgers.model;

public class Bun {
    String name;
    String hash;

    public Bun(String name, String hash) {
        this.name = name;
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }
}