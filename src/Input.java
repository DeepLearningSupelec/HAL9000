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
}
