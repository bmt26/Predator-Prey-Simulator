package com.predatorpreyplant;

import java.util.ArrayList;
import java.util.List;

public class Plant extends Sprite {

	private int seedDistance = 0;
    private List<Fruit> fruits;
    
    public Plant(int x, int y) {
        super(x, y);

        initPlant();
    }
    
    private void initPlant() {
        fruits = new ArrayList<>();
        width = 36;
        height = 36;
        loadImage("src/resources/bush.png");
    }
    
    public List<Fruit> getFruit() {
        return fruits;
    }
    
    public int getSeedDistance() {
        return seedDistance/10;
    }
    
    public int addSeedDistance(int sD) {
        return seedDistance+=sD;
    }
    
    public void grow() {
        fruits.add(new Fruit(x + width / 2 -10, y + height / 2 - 10));
    }
}