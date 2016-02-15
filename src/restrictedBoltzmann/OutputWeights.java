package restrictedBoltzmann;

import java.nio.file.Path;
import java.nio.file.Paths;

public class OutputWeights {
	
	private double[][] weights;
	private double min;
	private double max;
	
	public OutputWeights(int l){
		this.weights = new double[l][l];
		this.min = 0;
		this.max = 0;
	}
	
	public OutputWeights(int lines, int columns){
		this.weights = new double[lines][columns];
		this.min = 0;
		this.max = 0;
	}
	
	public OutputWeights(double[][] weights){
		this.weights = weights;

		double min = weights[0][0];
		double max = weights[0][0];
		for(int i = 0; i < weights.length; i++){
			for(int j = 0; j < weights[i].length; j++){
				if(weights[i][j] < min){
					min = weights[i][j];
				}
				if(weights[i][j] > max){
					max = weights[i][j];
				}
			}
		}
		this.min = min;
		this.max = max;
	}
	
	public double[][] getWeights(){
		return this.weights;
	}
	
	public double getMax(){
		return this.max;
	}
	
	public double getMin(){
		return this.min;
	}
	
	public void set(double[][] weights){
		this.weights = weights;
		
		double min = weights[0][0];
		double max = weights[0][0];
		for(int i = 0; i < weights.length; i++){
			for(int j = 0; j < weights[i].length; j++){
				if(weights[i][j] < min){
					min = weights[i][j];
				}
				if(weights[i][j] > max){
					max = weights[i][j];
				}
			}
		}
		this.min = min;
		this.max = max;
	}
	
	public void set(int line, int column, double weight){
		this.weights[line][column] = weight;
		
		if(weights[line][column] < min){
			min = weights[line][column];
		}
		if(weights[line][column] > max){
			max = weights[line][column];
		}
	}
	
	public void toBmp(Path p){
		
		int red, green, blue;
		int line = this.weights.length;
		int column = this.weights[0].length;
		int[][] rgbValues = new int[line][column];
		BMP bmp = new BMP();
		
		for(int i=0; i<line; i++){
			for(int j=0; j<column; j++){
				red = (int) Math.ceil(255*(this.weights[i][j] - this.min)/(this.max - this.min));
				blue = (int) Math.ceil(255*(this.weights[i][j] - this.min)/(this.max - this.min));
				green = (int) Math.ceil(255*(this.weights[i][j] - this.min)/(this.max - this.min));
				rgbValues[i][j]=red|green<<8|blue<<16;
			}
		}
		
		bmp.saveBMP(p.toString(), rgbValues);
	}
	
}
