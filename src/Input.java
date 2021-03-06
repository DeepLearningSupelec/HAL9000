import java.io.FileNotFoundException;
import java.io.IOException;

import mnistReader.MnistManager;


public class Input {
	
	//Attributes
	private int label;
	
	private double[] inputValues;
	
	//Constructor
	
	public Input(double[] inputValues, int label){
		this.inputValues = inputValues;
		this.label = label;
		
	}
	
	
	
	// this input is necessary a training input
	public Input(int a) throws IOException{
		MnistManager m = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		m.setCurrent(a);
		this.label = m.readLabel();
	}
	
	
	public Input(int a, MnistManager m) throws IOException{
		m.setCurrent(a);
		this.label = m.readLabel();
	}
	
	// input for whether training or testing
	public Input(int a, boolean isTraining) throws IOException{
		
		MnistManager m;
		if(isTraining){m = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");}
		else {m = new MnistManager("src/t10k-images.idx3-ubyte","src/t10k-labels.idx1-ubyte");}
		m.setCurrent(a);
		this.label = m.readLabel();
	} 
		
	
	//Methods
	public int getLabel(){
		return this.label;
	}
	
	public double[] getInputValues(){
		return this.inputValues;
	}
	
	
	
	//Creates a list of value that are the expected output of the Perceptron for MNIST
	public int[] expectedOutput(boolean mnist){
		if(mnist){
			int[] expectedOutput = new int[10];
			for (int j=0 ; j<10 ; j=j+1 ){
				expectedOutput[j] = 0;
			}
			expectedOutput [this.getLabel()] = 1;
			return expectedOutput;
		} else {
			int[] a = {this.getLabel()};
			return a;
		}
	}
	
}
