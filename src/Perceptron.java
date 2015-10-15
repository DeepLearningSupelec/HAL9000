import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

public class Perceptron extends NeuralNetwork {
	
	//Constructor
	
	public Perceptron(Path inputFilePath) throws IOException{
		super(inputFilePath);
	}
	
	public Perceptron(int[] inputData){
		super();
		int dataLength = inputData.length;
		
		//Adding Input Neurons
		
		for(int i = 0; i < inputData[0]; i++){
			this.inputNeurons.add(new InputNeuron());
		}
		
		//Adding Intermediate Neurons
		
		int interNeuronCpt = 0;
		
		for(int i = 1; i < dataLength - 1; i++){
			
			//For each Neuron of the Layer i
			for(int j = 0; j < inputData[i]; j++){
				IntermediateNeuron neuron = new IntermediateNeuron();
				this.intermediateNeurons.add(neuron);
				
				//Connecting Synapses between neuron and Layer (i - 1)
				for(int k = 0; k < inputData[i - 1]; k++){
					if(i == 1){
						this.synapses.add(new Synapse(this.inputNeurons.get(k), neuron));
						
					} else {
						this.synapses.add(new Synapse(this.intermediateNeurons.get(k + interNeuronCpt), neuron));
					}
				}
			}	
			interNeuronCpt += inputData[i];
		}
		
		//Adding and Connecting Output Neurons
		for(int j = 0; j < inputData[dataLength - 1]; j++){
			OutputNeuron neuron = new OutputNeuron();
			this.outputNeurons.add(neuron);
			for(int k = 0; k < inputData[dataLength - 2]; k++){
				this.synapses.add(new Synapse(this.intermediateNeurons.get(k + interNeuronCpt), neuron));
			}
		}
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
		double[] outputs = {};
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
