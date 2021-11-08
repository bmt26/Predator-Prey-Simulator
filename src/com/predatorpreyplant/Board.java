package com.predatorpreyplant;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private long startTime;
    private List<Wall> walls;
    private List<Plant> plants;
    private List<Mouse> mice;
    private List<Cat> cats;
    private int startingGen;
    private int currPlay=0;
    private Data data;
    private boolean ingame;
    private final int B_WIDTH = 1080;
    private final int B_HEIGHT = 720;
    private final int DELAY = 15;
    private final int NUM_PLAYS = 19;

    private final int[] MOUSEHOLE = {B_WIDTH/3, B_HEIGHT*3/4, B_WIDTH/3, B_HEIGHT/4};;
    private final int[][] wallsPos = {
        {MOUSEHOLE[0], MOUSEHOLE[1], 30, MOUSEHOLE[3]},
        {MOUSEHOLE[0], MOUSEHOLE[1], MOUSEHOLE[2]/2-30, 30},
        {MOUSEHOLE[0]+MOUSEHOLE[2]-30, MOUSEHOLE[1], 30, MOUSEHOLE[3]},
        {MOUSEHOLE[0]+MOUSEHOLE[2]/2+30, MOUSEHOLE[1], MOUSEHOLE[2]/2-30, 30},
    	};
    private int[][] plantsGenes;
    private final int[][] plantsPos = {
        {B_WIDTH/2-18, 80},
        };
    private int[][] miceGenes;
    private final int[][] micePos = {
        {B_WIDTH/2-25, B_HEIGHT-80},
    	};
    private int[][] catsGenes;
    private final int[][] catsPos = {
        {B_WIDTH/2-40, 160},
    	};

    private int[] plantsScore = new int[NUM_PLAYS+1];
    private int[] miceScore = new int[NUM_PLAYS+1];
    private int[] catsScore = new int[NUM_PLAYS+1];
    
    public Board(int gen) {
    	startingGen = gen;
    	data = new Data(startingGen);
    	data.Reproduce();
    	plantsGenes=data.getPlantsGenes();
    	miceGenes=data.getMiceGenes();
    	catsGenes=data.getCatsGenes();
        initBoard();
    }

    private void initBoard() {
    	Color grass = new Color(160, 255, 120);
        addKeyListener(new TAdapter());
        setBackground(grass);
        setFocusable(true);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        initWalls();
        initPlants();;
        initMice();
        initCats();
        

        timer = new Timer(DELAY, this);
        timer.start();
        startTime = System.currentTimeMillis();
    }

    public void initWalls() {
        
        walls = new ArrayList<>();

        for (int[] p : wallsPos) {
            walls.add(new Wall(p[0], p[1], p[2], p[3]));
        }
    }
    public void initPlants() {
        
        plants = new ArrayList<>();

        for (int[] p : plantsPos) {
            plants.add(new Plant(p[0], p[1]));
        }
    }
    public void initMice() {

        mice = new ArrayList<>();

        
        for (int i = 0; i<micePos.length; i++) {
            mice.add(new Mouse(micePos[i][0], micePos[i][1], B_WIDTH, B_HEIGHT, MOUSEHOLE, miceGenes[currPlay]));
        }
    }
    public void initCats() {
        
        cats = new ArrayList<>();

        for (int i = 0; i<catsPos.length; i++) {
            cats.add(new Cat(catsPos[i][0], catsPos[i][1], B_WIDTH, B_HEIGHT, MOUSEHOLE, catsGenes[currPlay]));
        }
    }

    public void calcScores() {
    	for (int i = 0; i < plants.size(); i++) {
    		plantsScore[currPlay]=plants.get(i).getSeedDistance()+1;
    	}
    	for (int i = 0; i < mice.size(); i++) {
    		miceScore[currPlay]=mice.get(i).getFruitEaten()*(B_WIDTH+B_HEIGHT)*10;
    		if(miceScore[currPlay]==0) {
                Rectangle recMouseHole = new Rectangle(MOUSEHOLE[0], MOUSEHOLE[1], MOUSEHOLE[2], MOUSEHOLE[3]);
                Rectangle recMouse = mice.get(i).getBounds();
    			if(recMouseHole.intersects(recMouse))
    			{
            		miceScore[currPlay]=(int) (mice.get(i).getFruitProximity(plants.get(i))/80);
    			}
    			else {
            		miceScore[currPlay]=(int) (mice.get(i).getHasFruit() ? (B_WIDTH+B_HEIGHT) + mice.get(i).getSafeProximity()*5 : Math.pow(mice.get(i).getFruitProximity(plants.get(i)), 3)/1000000);
    			}
    		}
    		if(cats.get(i).getMiceEaten()==1)
    		miceScore[currPlay]*=9/10;

    		if(miceScore[currPlay]<1)
    		miceScore[currPlay]=1;
    	}
    	for (int i = 0; i < cats.size(); i++) {
    		catsScore[currPlay]=cats.get(i).getMiceEaten()*(B_WIDTH+B_HEIGHT);
    		if (catsScore[currPlay]==0) {
        		catsScore[currPlay]=(int) Math.pow(cats.get(i).getMouseProximity(mice.get(i)), 2)/10000;
    		}
    	}
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {

            drawObjects(g);

        } else if (currPlay<NUM_PLAYS) {
        	calcScores();
        	currPlay++;
        	initBoard();
        } else {
        	calcScores();
            saveData();
            
            startingGen++;
        	data = new Data(startingGen);
        	data.Reproduce();
        	plantsGenes=data.getPlantsGenes();
        	miceGenes=data.getMiceGenes();
        	catsGenes=data.getCatsGenes();
            currPlay=0;
            initBoard();
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void saveData() {
    	data.setPlantsScore(plantsScore);
    	data.setMiceScore(miceScore);
    	data.setCatsScore(catsScore);
    	data.setPlantsGenes(plantsGenes);
    	data.setMiceGenes(miceGenes);
    	data.setCatsGenes(catsGenes);
    	data.Save();
        timer.stop();
		
	}

	private void drawObjects(Graphics g) {


	    for (Wall wall : walls) {
	        if (wall.isVisible()) {
	            g.drawImage(wall.getImage(), wall.getX(), wall.getY(), this);
	        }
	    }
		for (int i = 0; i < plants.size(); i++) {
			 Plant plant = plants.get(i);
			 g.drawImage(plant.getImage(), plant.getX(), plant.getY(), this);
	         List<Fruit> fruits = plant.getFruit();
	         for (Fruit fruit : fruits) {
	             if (fruit.isVisible()) {
	                 g.drawImage(fruit.getImage(), fruit.getX(), fruit.getY(), this);
	             }
	         }
		}
		for (int i = 0; i < mice.size(); i++) {
			 Mouse mouse = mice.get(i);
	    	 if (mouse.isVisible()) {
	             g.drawImage(mouse.getImage(), mouse.getX(), mouse.getY(), this);
	         }
	    }
		for (int i = 0; i < cats.size(); i++) {
			Cat cat = cats.get(i);
	        if (cat.isVisible()) {
	            g.drawImage(cat.getImage(), cat.getX(), cat.getY(), this);
	        }
	    }
        g.setColor(Color.BLACK);
        for (int i = 0; i < currPlay; i++) {
            g.drawString("Plant " + (i+1) + " Seed Distance: " + plantsScore[i], 5, 15*(i+1));
            g.drawString("Mouse " + (i+1) + " Fruit Eaten: " + miceScore[i], B_WIDTH/3+5, 15*(i+1));
            g.drawString("Cat " + (i+1) + " Mice Eaten: " + catsScore[i], B_WIDTH*2/3+5, 15*(i+1));
        }
        g.drawString("Time Left " + (20-(System.currentTimeMillis()-startTime)/1000), B_WIDTH-200, 15);
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateFruits();
        updateMice();
        updateCats();

        checkCollisions();

        repaint();
    }

    private void inGame() {
    	if ((6000-(System.currentTimeMillis()-startTime))<0) {
    		ingame=false;
    	}
        if (!ingame) {
            timer.stop();
        }
    }

    private void updateFruits() {
		for (Plant plant : plants) {
			List<Fruit> fruits = plant.getFruit();

		    for (int i = 0; i < fruits.size(); i++) {
		
		        Fruit fruit = fruits.get(i);
		
		        if (!fruit.isVisible()) {
		        	plant.addSeedDistance(fruit.getSeedDistance());
		            fruits.remove(i);
		        }
		    }
		    
		    if (fruits.isEmpty()) {
		    	plant.grow();
		    }
		}
    }
    
    private void updateMice() {

        if (mice.isEmpty()) {
        }

        for (int i = 0; i < mice.size(); i++) {

            Mouse mouse = mice.get(i);
            
            if (mouse.isVisible()) {
            	mouse.think(plants, cats);
            } else {
            	ingame = false;
            }
        }
    }

    private void updateCats() {

        if (cats.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < cats.size(); i++) {

            Cat cat = cats.get(i);
            
            if (cat.isVisible()) {
                cat.think(plants, mice);
            }
        }
    }

    public void checkCollisions() {

        for (Mouse mouse : mice) {
        	if(mouse.isVisible()) {
        		Rectangle recMouse = mouse.getBounds();
                
                for (Cat cat : cats) {
                    
                    Rectangle recCat = cat.getBounds();

                    if (recMouse.intersects(recCat)) {
                    	cat.incrementMiceEaten();
                    	mouse.setFruit(null);
                    	mouse.setHasFruit(false);
                        mouse.setVisible(false);
                    }
                }
                
                if (!mouse.getHasFruit()) {
                	for (Plant plant : plants) {
    		
    		        	List<Fruit> fruits = plant.getFruit();
    		
    		            for (Fruit fruit : fruits) {
    		
    		                Rectangle recFruit = fruit.getBounds();
    		
    		                if (recMouse.intersects(recFruit)) {
    		                	mouse.setFruit(fruit);
    		                	mouse.setHasFruit(true);
    		                }
    		            }
    		        }
                }else {
                	Fruit fruit = mouse.getFruit();
                    Rectangle recMouseHole = new Rectangle(MOUSEHOLE[0], MOUSEHOLE[1], MOUSEHOLE[2], MOUSEHOLE[3]);
                    Rectangle recFruit = fruit.getBounds();
                	
                    if (recMouseHole.intersects(recFruit)) {
                    	fruit.setVisible(false);
                    	mouse.setFruit(null);
                    	mouse.setHasFruit(false);
                    	mouse.incrementFruitEaten();
                    }
                }
        	}
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            for (Mouse mouse : mice) {
                mouse.keyReleased(e);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            for (Mouse mouse : mice) {
                mouse.keyPressed(e);
            }
        }
    }
}