package com.predatorpreyplant;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class PredatorPreyPlant extends JFrame {

    public PredatorPreyPlant() {
        
        initUI();
    }
    
    // startup glitch, first 2-3 trials times are messed up
    // 25 mouse goes up and down
    // 150 simple techniques for both
    // 288 Cat learns good time to swat to prevent mouse from leaving hole
    // 514 Mouse learns to repel, everything is jankie again
    // 728 Mouse learns really good time to repel
    private void initUI() {
    	add(new Board(728, 50, false, false, false));
		
		setResizable(false);
		pack();
		
		setTitle("Predator Prey Plant Simulator");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            PredatorPreyPlant ex = new PredatorPreyPlant();
            ex.setVisible(true);
        });
    }
}