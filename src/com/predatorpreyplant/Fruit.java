package com.predatorpreyplant;

public class Fruit extends Sprite {

	private int seedDistance = 0;
	
    public Fruit(int x, int y) {
        super(x, y);

        initFruit();
    }
    
    private void initFruit() {
        width = 20;
        height = 20;
        loadImage("src/resources/fruit.png");        
    }
    
    public int getSeedDistance() {
        return seedDistance/10;
    }
    
    public int addSeedDistance(int sD) {
        return seedDistance+=sD;
    }
    
    public void move(int newX, int newY) {
    	addSeedDistance(Math.abs(x-newX)+Math.abs(y-newY));
    	x = newX-width/2;
    	y = newY-height;
    }
}