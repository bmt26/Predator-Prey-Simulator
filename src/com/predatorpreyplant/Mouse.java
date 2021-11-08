package com.predatorpreyplant;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Mouse extends Sprite {

    private int dx;
    private int dy;
    private int fruitEaten = 0;
    					 //0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19
    private int[] genes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private boolean hasFruit;
    private Fruit fruit;
    private final int B_WIDTH;
    private final int B_HEIGHT;
    private final int[] MOUSEHOLE;

    public Mouse(int x, int y, int bW, int bH, int[] mH, int[] g) {
        super(x, y);
        B_WIDTH = bW;
        B_HEIGHT = bH;
        MOUSEHOLE = mH;
        genes = g;
        initMouse();
    }

    private void initMouse() {
        width = 50;
        height = 50;
        loadImage("src/resources/mouse.png");
    }

    public int[] getGenes() { return genes; }
    
    public void setHasFruit(boolean hF) { hasFruit = hF; }
    
    public boolean getHasFruit() { return hasFruit; }

    public void setFruit(Fruit f) { fruit = f; }
    
    public Fruit getFruit() { return fruit; }
    
    public void incrementFruitEaten() { fruitEaten+=1; }
    
    public int getFruitEaten() { return fruitEaten; }

    public void think(List<Plant> plants, List<Cat> cats) {
        Rectangle recMouseHole = new Rectangle(MOUSEHOLE[0], MOUSEHOLE[1], MOUSEHOLE[2], MOUSEHOLE[3]);
        Rectangle recMouse = getBounds();

    	int catProxSafeX;
    	int catProxSafeY;
    	int mouseSafe;
    	int catProxFruitX;
    	int catProxFruitY;
    	int mouseHasFruit;
    	
    	mouseSafe = recMouseHole.intersects(recMouse) ? 1 : 0;
		catProxSafeX = (B_WIDTH-((cats.get(0).x+cats.get(0).width/2)-(MOUSEHOLE[0]+MOUSEHOLE[2]/2)));
		catProxSafeY = (B_HEIGHT-((cats.get(0).y+cats.get(0).height/2)-(MOUSEHOLE[1]+MOUSEHOLE[3]-50)));
	
    	if ( getHasFruit() ) {
    		catProxFruitX = 0;
    		catProxFruitY = 0;
    		mouseHasFruit = 1;
    	} else {
    		catProxFruitX = (B_WIDTH-((cats.get(0).x+cats.get(0).width/2)-(plants.get(0).getFruit().get(0).x+plants.get(0).getFruit().get(0).width/2)));
    		catProxFruitY = (B_HEIGHT-((cats.get(0).y+cats.get(0).height/2)-(plants.get(0).getFruit().get(0).y+plants.get(0).getFruit().get(0).height/2)));
    		mouseHasFruit = 0;
    	}

    	int proxLeftBorder = (0-x)*genes[0]*(1+catProxSafeX*genes[1])*(1+mouseSafe*genes[2])*(1+catProxFruitX*genes[3])*(1+mouseHasFruit*genes[4]);
    	int proxTopBorder = (0-y)*genes[5]*(1+catProxSafeX*genes[6])*(1+mouseSafe*genes[7])*(1+catProxFruitX*genes[8])*(1+mouseHasFruit*genes[9]);
    	
    	int proxRightBorder = (B_WIDTH-x+width)*genes[10]*(1+catProxSafeX*genes[11])*(1+mouseSafe*genes[12])*(1+catProxFruitX*genes[13])*(1+mouseHasFruit*genes[14]);
    	int proxBottomBorder = (B_HEIGHT-x+height)*genes[15]*(1+catProxSafeX*genes[16])*(1+mouseSafe*genes[17])*(1+catProxFruitX*genes[18])*(1+mouseHasFruit*genes[19]);
    	
    	int proxCatX = (cats.get(0).x+cats.get(0).width/2-x-width/2)*genes[20]*(1+catProxSafeX*genes[21])*(1+mouseSafe*genes[22])*(1+catProxFruitX*genes[23])*(1+mouseHasFruit*genes[24]);
    	int proxCatY = (cats.get(0).y+cats.get(0).height/2-y-height/2)*genes[25]*(1+catProxSafeY*genes[26])*(1+mouseSafe*genes[27])*(1+catProxFruitY*genes[28])*(1+mouseHasFruit*genes[29]);
    	
    	int proxPlantX = (plants.get(0).x+plants.get(0).width/2-x-width/2)*genes[30]*(1+catProxSafeX*genes[31])*(1+mouseSafe*genes[32])*(1+catProxFruitX*genes[33])*(1+mouseHasFruit*genes[34]);
    	int proxPlantY = (plants.get(0).y+plants.get(0).height/2-y-height/2)*genes[35]*(1+catProxSafeY*genes[36])*(1+mouseSafe*genes[37])*(1+catProxFruitY*genes[38])*(1+mouseHasFruit*genes[39]);
    	
    	int proxFruitX = (plants.get(0).getFruit().get(0).x+plants.get(0).getFruit().get(0).width/2-x-width/2)*genes[40]*(1+catProxSafeX*genes[41])*(1+mouseSafe*genes[42])*(1+catProxFruitX*genes[43])*(1+mouseHasFruit*genes[44]);
    	int proxFruitY = (plants.get(0).getFruit().get(0).y+plants.get(0).getFruit().get(0).height/2-y-height/2)*genes[45]*(1+catProxSafeY*genes[46])*(1+mouseSafe*genes[47])*(1+catProxFruitY*genes[48])*(1+mouseHasFruit*genes[49]);
    	
    	int proxSafeX = (MOUSEHOLE[0]+MOUSEHOLE[2]/2-x-width/2)*genes[50]*(1+catProxSafeX*genes[51])*(1+mouseSafe*genes[52])*(1+catProxFruitX*genes[53])*(1+mouseHasFruit*genes[54]);
    	int proxSafeY = (MOUSEHOLE[1]-50-y-height/2)*genes[55]*(1+catProxSafeY*genes[56])*(1+mouseSafe*genes[57])*(1+catProxFruitY*genes[58])*(1+mouseHasFruit*genes[59]);
    	
    	dx = 8*Integer.signum(proxLeftBorder+proxRightBorder+proxCatX+proxPlantX+proxFruitX+proxSafeX);
    	dy = 8*Integer.signum(proxTopBorder+proxBottomBorder+proxCatY+proxPlantY+proxFruitY+proxSafeY);
    	move();
    }
    
    public void move() {

        x += dx;
        y += dy;

        // Top
        if (x < 0) {
            x = 0;
        }
        // Bottom
        else if (x > B_WIDTH-width) {
            x = B_WIDTH-width;
        }
        // Left Wall Left Side
        else if (y > B_HEIGHT*3/4-height && x > B_WIDTH*1/3-width && x < B_WIDTH*1/3-width+10) {
        	x = B_WIDTH*1/3-width;
        }
        // Left Wall Right Side
        else if (y > B_HEIGHT*3/4-height+30  && x > B_WIDTH*1/3+20 && x < B_WIDTH*1/3+30) {
        	x = B_WIDTH*1/3+30;
        }
        // Right Wall Left Side
        else if (y > B_HEIGHT*3/4-height+30 && x > B_WIDTH*2/3-width-30 && x < B_WIDTH*2/3-width-20) {
        	x = B_WIDTH*2/3-width-30;
        }
        // Right Wall Right Side
        else if (y > B_HEIGHT*3/4-height && x > B_WIDTH*2/3-10 && x < B_WIDTH*2/3) {
        	x = B_WIDTH*2/3;
        }
        //Top Left Wall Right Side
        else if (y > B_HEIGHT*3/4-height && y < B_HEIGHT*3/4+30 && x > B_WIDTH/2-40 && x < B_WIDTH/2-30) {
        	x = B_WIDTH/2-30;
        }
        //Top Right Wall Left Side
        else if (y > B_HEIGHT*3/4-height && y < B_HEIGHT*3/4+30 && x > B_WIDTH/2-width+30 && x < B_WIDTH/2-width+40) {
        	x = B_WIDTH/2-width+30;
        }

        //Left
        if (y < 0) {
            y = 0;
        }
        // Right
        else  if (y > B_HEIGHT-height-1) {
            y = B_HEIGHT-height-1;
        }
        // Top Left Wall Top Side
        else if (y > B_HEIGHT*3/4-height && y < B_HEIGHT*3/4-height+10 && x > B_WIDTH*1/3-width && x < B_WIDTH/2-30) {
        	y = B_HEIGHT*3/4-height;
        }
        // Top Left Wall Bottom Side
        else if (y > B_HEIGHT*3/4+20 && y < B_HEIGHT*3/4+30 && x > B_WIDTH*1/3-width+30 && x < B_WIDTH/2-30) {
        	y = B_HEIGHT*3/4+30;
        }
        // Top Right Wall Top Side
        else if (y > B_HEIGHT*3/4-height && y < B_HEIGHT*3/4-height+10 && x > B_WIDTH/2-width+30 && x < B_WIDTH*2/3) {
        	y = B_HEIGHT*3/4-height;
        }
        // Top Right Wall Bottom Side
        else if (y > B_HEIGHT*3/4+20 && y < B_HEIGHT*3/4+30 && x > B_WIDTH*1/2-width+30 && x < B_WIDTH*2/3-30) {
        	y = B_HEIGHT*3/4+30;
        }
        
        if (hasFruit) {
        	fruit.move(x+width/2, y);
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -4;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 4;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -4;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 4;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

	public int getSafeProximity() {
		return B_WIDTH/2+B_HEIGHT-(Math.abs(MOUSEHOLE[0]+MOUSEHOLE[2]/2-x-width/2))-(Math.abs(MOUSEHOLE[1]-50-y-height/2));
	}
	public int getFruitProximity(Plant plant) {
		return B_WIDTH/2+B_HEIGHT-(Math.abs(plant.getFruit().get(0).x+plant.getFruit().get(0).width/2-x-width/2))-(Math.abs(plant.getFruit().get(0).y+plant.getFruit().get(0).height/2-y-height/2));
	}
}