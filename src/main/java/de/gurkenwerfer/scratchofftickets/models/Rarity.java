package de.gurkenwerfer.scratchofftickets.models;


public enum Rarity {
    COMMON("Common"),
    RARE("Rare"),
    EPIC("Epic"),
    LEGENDARY("Legandary");

    private final String name;
    Rarity(String name){
        this.name = name;
    }
    @Override
    public String toString(){
        return name;
    }
}
