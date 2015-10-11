import java.io.IOException;

public class Perceptron extends NeuralNetwork {
	
	//Constructor
	
	public Perceptron(String inputFilePath) throws IOException{
		super(inputFilePath);
	}
	
	
	
	
	
	
	//Methods
	
	public void fire(){
		
		// Unfire each neuron of the network
		for (int i = 0; i < this.inputNeurons.size(); i++) {
			this.inputNeurons.get(i).setUnfired();
		}
		for (int i = 0; i < this.intermediateNeurons.size(); i++) {
			this.intermediateNeurons.get(i).setUnfired();
		}
		for (int i = 0; i < this.outputNeurons.size(); i++) {
			this.outputNeurons.get(i).setUnfired();
		}
		
		//Launch fire() on each OutputNeuron
		for (int i = 0; i < this.outputNeurons.size(); i++) {
			this.outputNeurons.get(i).fire();
		}
	}
	public double getOutputs(){
		
		return 0.0;
	}
	public void setInputs(double x){
		
	}
	public void linkNetwork(){
		
	}
	
	
	
	
}
