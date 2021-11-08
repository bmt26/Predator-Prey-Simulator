package com.predatorpreyplant;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class PredatorPreyPlant extends JFrame {

    public PredatorPreyPlant() {
        
        initUI();
    }
    
    private void initUI() {
    	add(new Board(1866));
		
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