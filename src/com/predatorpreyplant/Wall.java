package com.predatorpreyplant;

public class Wall extends Sprite {


    public Wall(int x, int y, int w, int h) {
        super(x, y);

        initWall(w, h);
    }
    
    private void initWall(int w, int h) {
        width = w;
        height = h;
        loadImage("src/resources/brick.png");
    }
}