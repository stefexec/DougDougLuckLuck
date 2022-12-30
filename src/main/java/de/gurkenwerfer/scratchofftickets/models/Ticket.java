package de.gurkenwerfer.scratchofftickets.models;

public class Ticket {
  private String name;
  private Rarity rarity;
  private int price;
  private int id;
  private int amount;


public Ticket(String name, Rarity rarity, int price, int id, int amount) {
    this.name = name;
    this.rarity = rarity;
    this.price = price;
    this.id = id;
    this.amount = amount;
  }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Rarity getRarity() {
        return rarity;
    }
    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

}
