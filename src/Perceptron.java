import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class Perceptron extends NeuralNetwork {
	
	//Attributes
	
	AbstractNeuron[][] layers;

	
	
	//Constructor
	
	public Perceptron(Path inputFilePath) throws IOException{
		super(inputFilePath);
	}
	
	public Perceptron(int[] inputData){
		super();
		int dataLength = inputData.length;
		this.layers = new AbstractNeuron[dataLength][];
		
		//Adding Input Neurons
		this.layers[0] = new AbstractNeuron[inputData[0]];
		for(int i = 0; i < inputData[0]; i++){
			InputNeuron n = new InputNeuron();
			this.inputNeurons.add(n);
			this.layers[0][i] = n;
		}
		
		//Adding Intermediate Neurons
		
		int interNeuronCpt = 0;
		
		for(int i = 1; i < dataLength - 1; i++){
			
			this.layers[i] = new AbstractNeuron[inputData[i]];
			//For each Neuron of the Layer i
			for(int j = 0; j < inputData[i]; j++){
				IntermediateNeuron neuron = new IntermediateNeuron();
				this.intermediateNeurons.add(neuron);
				this.layers[i][j] = neuron;
				
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
		this.layers[dataLength - 1] = new AbstractNeuron[inputData[dataLength - 1]];
		for(int j = 0; j < inputData[dataLength - 1]; j++){
			OutputNeuron neuron = new OutputNeuron();
			this.outputNeurons.add(neuron);
			this.layers[dataLength - 1][j] = neuron;
			for(int k = 1; k <= inputData[dataLength - 2]; k++){
				this.synapses.add(new Synapse(this.intermediateNeurons.get(interNeuronCpt-k), neuron));
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
		int i=0;
		double outputs[] = new double[outputNeurons.size()];
		for (AbstractNeuron neuron : outputNeurons ){
				outputs[i]=((OutputNeuron)neuron).getOutput();
				i++;
		}
		return outputs;
		
	}
	public void setInputs(double[] x){
		int i = 0;
		for (AbstractNeuron neuron : inputNeurons){
			((InputNeuron)neuron).setInput(x[i]);
			i++;
		}		
	}
	
	
	public void linkNetwork(){
		
	}
	
	
	
	
}
