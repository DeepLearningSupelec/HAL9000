package restrictedBoltzmann;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class RestrictedBoltzmannMachine {

	
	//Attributes
	
	Entity[][] layers;
	
	double energy;
	
	double weightWide;
	
	double[][] connexions;
	
	//Constructor
	
	/*public RestrictedBoltzmannMachine(Path inputFilePath) throws IOException{
		super(inputFilePath);
	}
	
	public RestrictedBoltzmannMachine(int[] inputData){
		this(inputData, false);
	}*/
	
	public RestrictedBoltzmannMachine (int[] inputData, boolean randomWeight){
		int dataLength = inputData.length;
		this.layers = new Entity[dataLength][];
		Random rand = new Random();
		double neuronQuantity = 0;
		for(int i = 0; i < dataLength; i++){
			neuronQuantity += inputData[i];
		}
		this.weightWide = 2.38*2./(Math.sqrt(neuronQuantity));
		
		//must add the neurons
	}
	
	
	//Methods
	
	public void fire(){
		
	}
	
	
	public double[] getOutputs(){
		return null;
	}
	
	public void setInputs (double[]x){
		
	}

	
	
}