import java.io.FileNotFoundException;
import java.io.IOException;

import mnistReader.MnistManager;


public class Input {
	
	//Attributes
	private int label;
	
	//Constructor
	
		public Input(int a) throws IOException{
			MnistManager m = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
			m.setCurrent(a);
			this.label = m.readLabel();
		}
	
		
	//Methods
	public int getLabel(){
		return this.label;
	}
	
	//Creates a list of value that are the expected output of the Perceptron for MNIST
		public int[] expectedOutput(){
			int[] expectedOutput = new int[10] ;
			for (int j=0 ; j<this.getLabel() ; j=j+1 ){
				expectedOutput[j] = 0;
			}
			expectedOutput [this.getLabel()] = 1;
			for (int j=this.getLabel()+1 ; j<=9 ; j=j+1 ){
				expectedOutput[j] = 0;
			}
			return expectedOutput;
		}
		
}
