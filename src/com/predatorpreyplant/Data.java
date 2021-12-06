package com.predatorpreyplant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Data {
	int oldGen=0;
	int currGen=1;
	int[] oldPlantsScore;
	int[] oldMiceScore;
	int[] oldCatsScore;
	int[][] oldPlantsGenes;
	int[][] oldMiceGenes;
	int[][] oldCatsGenes;
	int[] currPlantsScore;
	int[] currMiceScore;
	int[] currCatsScore;
	int[][] currPlantsGenes;
	int[][] currMiceGenes;
	int[][] currCatsGenes;
	
	public Data(int gen) {
		oldGen = gen;
		currGen = oldGen+1;
		oldPlantsGenes= new int[52][];
		oldMiceGenes= new int[52][];
		oldCatsGenes= new int[52][];
	    readFile();
	    Reproduce();
	}
	
	public void setOldGen(int g) { oldGen=g; currGen=g+1; }
	
	public void readFile() {
		try {
		      File myObj = new File("trials/Gen" + oldGen + ".txt");
		      Scanner myReader = new Scanner(myObj);
		      int p = 0;
		      int m = 0;
		      int c = 0;
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        if(data.contains("Plants Score: ")) {
		        	oldPlantsScore = Arrays.stream(data.substring(14).split(" ")).mapToInt(Integer::parseInt).toArray();
		        }
		        else if(data.contains("Mice Score: ")) {
		        	oldMiceScore = Arrays.stream(data.substring(12).split(" ")).mapToInt(Integer::parseInt).toArray();
		        }
		        else if(data.contains("Cats Score: ")) {
		        	oldCatsScore = Arrays.stream(data.substring(12).split(" ")).mapToInt(Integer::parseInt).toArray();
		        }
		        else if(data.contains("Plants Genes: ")) {
		        	oldPlantsGenes[p] = Arrays.stream(data.substring(14).split(" ")).mapToInt(Integer::parseInt).toArray();
		        	p++;
		        }
		        else if(data.contains("Mice Genes: ")) {
		        	oldMiceGenes[m] = Arrays.stream(data.substring(12).split(" ")).mapToInt(Integer::parseInt).toArray();
		        	m++;
		        }
		        else if(data.contains("Cats Genes: ")) {
		        	oldCatsGenes[c] = Arrays.stream(data.substring(12).split(" ")).mapToInt(Integer::parseInt).toArray();
		        	c++;
		        }
		      }
		      myReader.close();
	    } catch (FileNotFoundException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
	}

	public int[][] getOldPlantsGenes() { return oldPlantsGenes; }
	public int[][] getOldMiceGenes() { return oldMiceGenes; }
	public int[][] getOldCatsGenes() { return oldCatsGenes; }
	
	public int[][] getPlantsGenes() { return currPlantsGenes; }
	public int[][] getMiceGenes() { return currMiceGenes; }
	public int[][] getCatsGenes() { return currCatsGenes; }
	
	public void setPlantsScore(int[] pS) { currPlantsScore = pS; }
	public void setMiceScore(int[] mS) { currMiceScore = mS; }
	public void setCatsScore(int[] cS) { currCatsScore = cS;}
	
	public void setPlantsGenes(int[][] pG) { currPlantsGenes = pG; }
	public void setMiceGenes(int[][] mG) { currMiceGenes = mG; }
	public void setCatsGenes(int[][] cG) { currCatsGenes = cG;}
	
	public void Reproduce() {
		//System.out.println("Plant: ");
		int[][] plantsParents = ChooseParent(oldPlantsScore);
		//System.out.println("Mice: ");
		int[][] miceParents = ChooseParent(oldMiceScore);
		//System.out.println("Cat: ");
		int[][] catsParents = ChooseParent(oldCatsScore);

		//System.out.println();
		//System.out.println("Plant: ");
		currPlantsGenes = OffspringGenes(plantsParents, oldPlantsGenes);
		//System.out.println();
		//System.out.println("Mice: ");
		currMiceGenes = OffspringGenes(miceParents, oldMiceGenes);
		//System.out.println();
		//System.out.println("Cat: ");
		currCatsGenes = OffspringGenes(catsParents, oldCatsGenes);
		//System.out.println();
	}
	
	public int[][] ChooseParent(int[] scores) {
		Random rand = new Random();
		int[][] retParents = new int[scores.length/4][2];
		
		for (int i = 0; i < scores.length/4; i++) {
			int totalChance=0;
			for (int j = 0; j < scores.length; j++) {
				totalChance+=scores[j];
		    }

			int prevCumChance=0;
			int cumChance=0;

			int parent1 = rand.nextInt(totalChance);
			for(int j = 0; j < scores.length; j++) {
				cumChance+=scores[j];
				if (parent1<cumChance) {
					parent1=j;
					prevCumChance=cumChance-scores[j];
					break;
				}
			}
			
			int parent2=rand.nextInt(totalChance);
			while (parent2<cumChance && parent2>=prevCumChance) {
				parent2=rand.nextInt(totalChance);

			}
			for(int j = 0; j < scores.length; j++) {
				cumChance+=scores[j];
				if (parent2<cumChance) {
					parent2=j;
					break;
				}
			}
			retParents[i][0] = parent1;
			retParents[i][1] = parent2;
			//System.out.println("Parent[" + i + "][0]: " + parent1);
			//System.out.println("Parent[" + i + "][1]: " + parent2);
		}
		return retParents;
	}
	
	public int[][] OffspringGenes(int[][] parents, int[][] genes) {
		int[][] retGenes = new int[parents.length*4][genes[0].length];
		for (int i = 0; i < parents.length; i++) {
			int parent1=parents[i][0];
			int parent2=parents[i][1];
			//System.out.println(i);
			retGenes[i*4]=Mutate(genes[parent1], genes[parent2]);
			//System.out.println();
			retGenes[i*4+1]=Mutate(genes[parent1], genes[parent2]);
			//System.out.println();
			retGenes[i*4+2]=Mutate(genes[parent2], genes[parent1]);
			//System.out.println();
			retGenes[i*4+3]=Mutate(genes[parent2], genes[parent1]);
			//System.out.println();
		}
		return retGenes;
	}
	
	public int[] Mutate(int[] parent1Genes, int[] parent2Genes) {
		Random rand = new Random();
		int[] retGenes = new int[parent1Genes.length];
		for (int i = 0; i < parent1Genes.length; i++) {
			//System.out.print("| " + parent1Genes[i] + " ");
			if(rand.nextFloat()<0.6) {
				retGenes[i]= parent1Genes[i];
			}
			else {
				retGenes[i]= parent2Genes[i];
			}
			
			
			if(rand.nextInt(1000)==0) {
				retGenes[i]+= rand.nextInt(2)==1 ? 1 : -1;
			}
		}
		return retGenes;
	}
	
	public void Save() {
	    
	    try {
	    	String filename = "trials/Gen" + currGen + ".txt";
	    	FileWriter myWriter = new FileWriter(filename);
	    	String text = "Plants Score: ";
		    for (int i = 0; i < currPlantsScore.length; i++) {
		    	text+=currPlantsScore[i] + " ";
		    }
	    	text+="\nMice Score: ";
	    	for (int i = 0; i < currMiceScore.length; i++) {
	    		text+=currMiceScore[i] + " ";
	    	}
	    	text+="\nCats Score: ";
	    	for (int i = 0; i < currCatsScore.length; i++) {
	    		text+=currCatsScore[i] + " ";
	    	}
	    	for (int i = 0; i< 52; i++) {
		    	text += "\nPlants Genes: ";
		    	for (int j = 0; j < currPlantsGenes[i].length; j++) {
			    	text+=currPlantsGenes[i][j] + " ";
			    }
		    	text+="\nMice Genes: ";
		    	for (int j = 0; j < currMiceGenes[i].length; j++) {
		    		text+=currMiceGenes[i][j] + " ";
		    	}
		    	text+="\nCats Genes: ";
		    	for (int j = 0; j < currCatsGenes[i].length; j++) {
		    		text+=currCatsGenes[i][j] + " ";
		    	}
	    		
	    	}
	    	
	    	myWriter.write(text);
	    	myWriter.close();
	    	System.out.println("Successfully wrote to file: " + filename);
	    } catch (IOException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
	}
}
