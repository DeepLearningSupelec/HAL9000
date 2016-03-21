package restrictedBoltzmann;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * <p> This class allows to print a weight matrix into a bitmap file.</p>
 * 
 * @see BMP
 */
public class OutputWeights {
	
	/**
	 * The weight matrix which will be converted to a bitmap file.
	 */
	private double[][] weights;
	/**
	 * The minimal weight value in the weights matrix
	 */
	private double min;
	
	private int[][] weightsInt;
	/**
	 * The maximal weight value in the weights matrix
	 */
	private double max;
	
	
	/* Constructors */
	
	/**
	 * Allows to create a new OutputWeights instance with all values defined to zero.
	 * @param l
	 * 		The width of the grid
	 */
	public OutputWeights(int l){
		this.weights = new double[l][l];
		this.min = 0;
		this.max = 0;
	}
	
	/**
	 * Allows to create a new OutputWeights instance with all values defined to zero.
	 * @param lines
	 * 		The number of lines in the grid
	 * @param columns
	 * 		The number of columns in the grid
	 */
	public OutputWeights(int lines, int columns){
		this.weights = new double[lines][columns];
		this.min = 0;
		this.max = 0;
	}
	
	/**
	 * Allows to create a new OutputWeights instance with the weights defined by the argument.
	 * @param weights
	 * 		Value of the weights in the new instance.
	 */
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
	
	public OutputWeights(int[][] weights){
		this.weightsInt = weights;

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
	
	/* Accessors */
	
	/**
	 * Allows to get the value of weights.
	 * @return weights
	 * @see #weights
	 */
	public double[][] getWeights(){
		return this.weights;
	}
	
	/**
	 * Returns the max weight value
	 * @return max
	 * @see #max
	 */
	public double getMax(){
		return this.max;
	}
	
	/**
	 * Returns the min weight value
	 * @return min
	 * @see #min
	 */
	public double getMin(){
		return this.min;
	}
	
	/**
	 * Allows to set the weight to the value of the argument
	 * @param weights
	 * @see #weights
	 */
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
	
	/**
	 * Allows to set the value of the weight at a given line and number to a given value
	 * @param line
	 * @param column
	 * @param weight
	 */
	public void set(int line, int column, double weight){
		this.weights[line][column] = weight;
		
		if(weights[line][column] < min){
			min = weights[line][column];
		}
		if(weights[line][column] > max){
			max = weights[line][column];
		}
	}
	
	/**
	 * Converts the weights matrix to a bitmap file.
	 * It returns a picture in grey levels with the minimal weight value at #000 and the maximal weight value at #FFF.
	 * @param p
	 * 		The Path where the bitmap file will be saved.
	 */
	public void toBmp(Path p) throws IOException {
		
		if (this.weights!=null){
			int line = this.weights.length;
			int column = this.weights[0].length;
			int red, green, blue;

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
		
		
		else{
			int line = this.weightsInt.length;
			int column = this.weightsInt[0].length;
			int red, green, blue;

			int[][] rgbValues = new int[line][column];
			BMP bmp = new BMP();
			
			for(int i=0; i<line; i++){
				for(int j=0; j<column; j++){
					red = (int) Math.ceil(255*(this.weightsInt[i][j] - this.min)/(this.max - this.min));
					blue = (int) Math.ceil(255*(this.weightsInt[i][j] - this.min)/(this.max - this.min));
					green = (int) Math.ceil(255*(this.weightsInt[i][j] - this.min)/(this.max - this.min));
					rgbValues[i][j]=red|green<<8|blue<<16;
				}
			}
			
			bmp.saveBMP(p.toString(), rgbValues);
		}
	}
	
}
