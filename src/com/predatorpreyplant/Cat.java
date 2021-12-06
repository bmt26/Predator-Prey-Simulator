package com.predatorpreyplant;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

public class Cat extends Sprite {

    private int dx;
    private int dy;
    private int miceEaten = 0;
	  					 //0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19
    //private int[] genes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    //private int[] genes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] genes;
    private final int B_WIDTH;
    private final int B_HEIGHT;
    private final int[] MOUSEHOLE;
    private boolean canSwat = true;
    private boolean swatting = false;
    
    public Cat(int x, int y, int bW, int bH, int[] mH) {
        super(x, y);
        B_WIDTH = bW;
        B_HEIGHT = bH;
        MOUSEHOLE = mH;
        initCat();
    }
    
    public Cat(int x, int y, int bW, int bH, int[] mH, int[] g) {
        super(x, y);
        B_WIDTH = bW;
        B_HEIGHT = bH;
        MOUSEHOLE = mH;
        genes = g;
        initCat();
    }

    private void initCat() {
        width = 80;
        height = 80;
        loadImage("src/resources/cat.png");
    }

    public int[] getGenes() { return genes; }
    public boolean Swat() { 
    	if(canSwat&&swatting) {
    		canSwat=false;
    		return true;
    	}
    	return false;
    }
    
    public void incrementMiceEaten() { miceEaten+=1; }
    
    public int getMiceEaten() { return miceEaten; }
    
    public void think(List<Plant> plants, List<Mouse> mice, boolean fart) {
        Rectangle recMouseHole = new Rectangle(MOUSEHOLE[0], MOUSEHOLE[1], MOUSEHOLE[2], MOUSEHOLE[3]);
        Rectangle recMouse = mice.get(0).getBounds();

		int mouseProxSafeX = B_WIDTH-(mice.get(0).x+mice.get(0).width/2)-(MOUSEHOLE[0]+MOUSEHOLE[2]/2);
		int mouseProxSafeY = B_HEIGHT-(mice.get(0).y+mice.get(0).height/2)-(MOUSEHOLE[1]+MOUSEHOLE[3]-50);
    	int mouseProxFruitX = B_WIDTH-(mice.get(0).x+mice.get(0).width/2)-(plants.get(0).getFruit().get(0).x+plants.get(0).getFruit().get(0).width/2);
    	int mouseProxFruitY = B_HEIGHT-(mice.get(0).y+mice.get(0).height/2)-(plants.get(0).getFruit().get(0).y+plants.get(0).getFruit().get(0).height/2);
    	int proxLBorder = -x;
    	int proxTBorder = -y;
    	int proxRBorder = B_WIDTH-x+width;
    	int proxBBorder = B_HEIGHT-x+height;
    	int proxMouseX = mice.get(0).x+mice.get(0).width/2-x-width/2;
    	int proxMouseY = mice.get(0).y+mice.get(0).height/2-y-height/2;
    	int proxFruitX = plants.get(0).getFruit().get(0).x+plants.get(0).getFruit().get(0).width/2-x-width/2;
    	int proxFruitY = plants.get(0).getFruit().get(0).y+plants.get(0).getFruit().get(0).height/2-y-height/2;
    	int proxSafeX = MOUSEHOLE[0]+MOUSEHOLE[2]/2-x-width/2;
    	int proxSafeY = MOUSEHOLE[1]-50-y-height/2;
    	
    	int state;
    	
    	int toX = 0;
    	int toY = 0;
    	
    	if (recMouseHole.intersects(recMouse)) { 
    		if (canSwat) 							{ state = 5; } // inside safe can repel
    		else 									{ state = 4; } // inside safe can't repel
    	} else if (mice.get(0).getHasFruit()) {
		if (canSwat) 								{ state = 3; } // outside safe with fruit can repel
		else 										{ state = 2; } // outside safe with fruit can't repel
    	} else {
    		if (canSwat) 							{ state = 1; } // outside safe without fruit can repel
    		else 									{ state = 0; } // outside safe without fruit can't repel
    	}
    	
		toX = mouseProxSafeX		* genes[state * genes.length / 6 + 0]
			+ mouseProxFruitX		* genes[state * genes.length / 6 + 1]
			+ proxLBorder			* genes[state * genes.length / 6 + 2]
			+ proxRBorder			* genes[state * genes.length / 6 + 3]
			+ proxMouseX			* genes[state * genes.length / 6 + 4]
			+ proxFruitX			* genes[state * genes.length / 6 + 5]
			+ proxSafeX				* genes[state * genes.length / 6 + 6];
		
		toY = mouseProxSafeY		* genes[state * genes.length / 6 + 7]
			+ mouseProxFruitY		* genes[state * genes.length / 6 + 8]
			+ proxTBorder			* genes[state * genes.length / 6 + 9]
			+ proxBBorder			* genes[state * genes.length / 6 + 10]
			+ proxMouseY			* genes[state * genes.length / 6 + 11]
			+ proxFruitY			* genes[state * genes.length / 6 + 12]
			+ proxSafeY				* genes[state * genes.length / 6 + 13];
		
		swatting = (Math.abs(mouseProxSafeX)		+ Math.abs(mouseProxSafeY))		/300	< genes[state * genes.length / 6 + 14]
				 ||(Math.abs(mouseProxFruitX)		+ Math.abs(mouseProxFruitY))	/300	< genes[state * genes.length / 6 + 15]
				 ||(Math.abs(proxTBorder)			+ Math.abs(proxTBorder))		/300	< genes[state * genes.length / 6 + 16]
				 ||(Math.abs(proxBBorder)			+ Math.abs(proxTBorder))		/300	< genes[state * genes.length / 6 + 17]
				 ||(Math.abs(proxMouseX)			+ Math.abs(proxMouseX))			/300	< genes[state * genes.length / 6 + 18]
				 ||(Math.abs(proxFruitX)			+ Math.abs(proxFruitY))			/300	< genes[state * genes.length / 6 + 19]
				 ||(Math.abs(proxSafeX)				+ Math.abs(proxSafeY))			/300	< genes[state * genes.length / 6 + 20];
		if (swatting&&canSwat) {
			System.out.println("Cat used swat");
		}
		
		if(fart) {
    		dx=-2*Integer.signum(mice.get(0).x+mice.get(0).width/2-x-width/2);
    		dy=-2*Integer.signum(mice.get(0).y+mice.get(0).height/2-y-height/2);
		}
		else {
	    	dx = 2*Integer.signum(toX);
	    	dy = 2*Integer.signum(toY);
		}
    	move();
    }
    
    public void think2(List<Plant> plants, List<Mouse> mice, boolean fart) {
        Rectangle recMouseHole = new Rectangle(MOUSEHOLE[0], MOUSEHOLE[1], MOUSEHOLE[2], MOUSEHOLE[3]);
        Rectangle recMouse = mice.get(0).getBounds();

    	int mouseProxSafeX;
    	int mouseProxSafeY;
    	int mouseSafe;
    	int mouseProxFruitX;
    	int mouseProxFruitY;
    	int mouseHasFruit;
    	
    	if ( recMouseHole.intersects(recMouse) ) {
    		mouseProxSafeX = 0;
    		mouseProxSafeY = 0;
    		mouseSafe = 1;
    	} else {
    		mouseProxSafeX = (B_WIDTH-(mice.get(0).x+mice.get(0).width/2)-(MOUSEHOLE[0]+MOUSEHOLE[2]/2));
    		mouseProxSafeY = (B_HEIGHT-(mice.get(0).y+mice.get(0).height/2)-(MOUSEHOLE[1]));
    		mouseSafe = 0;
    	}
    	
    	if ( mice.get(0).getHasFruit() ) {
    		mouseProxFruitX = 0;
    		mouseProxFruitY = 0;
    		mouseHasFruit = 1;
    	} else {
    		mouseProxFruitX = (B_WIDTH-((mice.get(0).x+mice.get(0).width/2)-(plants.get(0).getFruit().get(0).x+plants.get(0).getFruit().get(0).width/2)));
    		mouseProxFruitY = (B_HEIGHT-((mice.get(0).y+mice.get(0).height/2)-(plants.get(0).getFruit().get(0).y+plants.get(0).getFruit().get(0).height/2)));
    		mouseHasFruit = 0;
    	}

    	int proxLeftBorder = (0-x)*genes[0]*(1+mouseProxSafeX*genes[1])*(1+mouseSafe*genes[2])*(1+mouseProxFruitX*genes[3])*(1+mouseHasFruit*genes[4]);
    	int proxTopBorder = (0-y)*genes[5]*(1+mouseProxSafeX*genes[6])*(1+mouseSafe*genes[7])*(1+mouseProxFruitX*genes[8])*(1+mouseHasFruit*genes[9]);
    	
    	int proxRightBorder = (B_WIDTH-x+width)*genes[10]*(1+mouseProxSafeX*genes[11])*(1+mouseSafe*genes[12])*(1+mouseProxFruitX*genes[13])*(1+mouseHasFruit*genes[14]);
    	int proxBottomBorder = (B_HEIGHT-x+height)*genes[15]*(1+mouseProxSafeX*genes[16])*(1+mouseSafe*genes[17])*(1+mouseProxFruitX*genes[18])*(1+mouseHasFruit*genes[19]);
    	
    	int proxMouseX = (mice.get(0).x+mice.get(0).width/2-x-width/2)*genes[20]*(1+mouseProxSafeX*genes[21])*(1+mouseSafe*genes[22])*(1+mouseProxFruitX*genes[23])*(1+mouseHasFruit*genes[24]);
    	int proxMouseY = (mice.get(0).y+mice.get(0).height/2-y-height/2)*genes[25]*(1+mouseProxSafeY*genes[26])*(1+mouseSafe*genes[27])*(1+mouseProxFruitY*genes[28])*(1+mouseHasFruit*genes[29]);
    	
    	int proxPlantX = (plants.get(0).x+plants.get(0).width/2-x-width/2)*genes[30]*(1+mouseProxSafeX*genes[31])*(1+mouseSafe*genes[32])*(1+mouseProxFruitX*genes[33])*(1+mouseHasFruit*genes[34]);
    	int proxPlantY = (plants.get(0).y+plants.get(0).height/2-y-height/2)*genes[35]*(1+mouseProxSafeY*genes[36])*(1+mouseSafe*genes[37])*(1+mouseProxFruitY*genes[38])*(1+mouseHasFruit*genes[39]);
    	
    	int proxFruitX = (plants.get(0).getFruit().get(0).x+plants.get(0).getFruit().get(0).width/2-x-width/2)*genes[40]*(1+mouseProxSafeX*genes[41])*(1+mouseSafe*genes[42])*(1+mouseProxFruitX*genes[43])*(1+mouseHasFruit*genes[44]);
    	int proxFruitY = (plants.get(0).getFruit().get(0).y+plants.get(0).getFruit().get(0).height/2-y-height/2)*genes[45]*(1+mouseProxSafeY*genes[46])*(1+mouseSafe*genes[47])*(1+mouseProxFruitY*genes[48])*(1+mouseHasFruit*genes[49]);
    	
    	int proxSafeX = (MOUSEHOLE[0]+MOUSEHOLE[2]/2-x-width/2)*genes[50]*(1+mouseProxSafeX*genes[51])*(1+mouseSafe*genes[52])*(1+mouseProxFruitX*genes[53])*(1+mouseHasFruit*genes[54]);
    	int proxSafeY = (MOUSEHOLE[1]+MOUSEHOLE[3]/2-y-height/2)*genes[55]*(1+mouseProxSafeY*genes[56])*(1+mouseSafe*genes[57])*(1+mouseProxFruitY*genes[58])*(1+mouseHasFruit*genes[59]);
    	
    	dx = 2*Integer.signum(proxLeftBorder+proxRightBorder+proxMouseX+proxPlantX+proxFruitX+proxSafeX);
    	dy = 2*Integer.signum(proxTopBorder+proxBottomBorder+proxMouseY+proxPlantY+proxFruitY+proxSafeY);
    	if(fart) {
    		dx=-2*Integer.signum(mice.get(0).x+mice.get(0).width/2-x-width/2);
    		dy=-2*Integer.signum(mice.get(0).y+mice.get(0).height/2-y-height/2);
    		
    	}
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

	public int getMouseProximity(Mouse mouse) {
		return B_WIDTH+B_HEIGHT-(Math.abs(mouse.x+mouse.width/2-x-width/2))-(Math.abs(mouse.y+mouse.height/2-y-height/2));
	}
}