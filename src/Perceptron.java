import java.io.IOException;
import java.util.Iterator;

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
	public double[] getOutputs(){
		double[] outputs;
		int i=0;
		for (Iterator<AbstractNeuron> it= this.outputNeurons.iterator(); it.hasNext(); i++){
				outputs[i]=((OutputNeuron)it.next()).getOutput();
		}
		return outputs;
		
	}
	public void setInputs(double[] x){
		int i = 0;
		for (Iterator<AbstractNeuron> it= this.inputNeurons.iterator(); it.hasNext();i++){
			((InputNeuron)it.next()).setInput(x[i]);
		}		
	}
	
	
	public void linkNetwork(){
		
	}
	
	
	
	
}
