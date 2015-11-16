import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Perceptron extends NeuralNetwork {
	
	//Attributes
	
	AbstractNeuron[][] layers;
	
	final double defaultSynapseWeight = 0.01;

	
	
	//Constructor
	
	public Perceptron(Path inputFilePath) throws IOException{
		super(inputFilePath);
	}
	
	public Perceptron(int[] inputData){
		this(inputData, false);
	}
	
	public Perceptron(int[] inputData, boolean randomWeight){
		super();
		int dataLength = inputData.length;
		this.layers = new AbstractNeuron[dataLength][];
		Random rand = new Random();
		
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
					double w = defaultSynapseWeight;
					if(randomWeight){
						w = rand.nextDouble()*2. - 1.;
					}
					if(i == 1){
						this.synapses.add(new Synapse(w, this.inputNeurons.get(k), neuron));
						
					} else {
						int a = k + interNeuronCpt;
						//System.out.println("i: " + i + " j: " +j);
						//System.out.println("taille: " + this.intermediateNeurons.size() + " case : " + a + " k: " + k );
						this.synapses.add(new Synapse(w, this.intermediateNeurons.get(k + interNeuronCpt), neuron));
					}
				}
			}	
			if(i != 1){
				interNeuronCpt += inputData[i - 1];
			}
			//interNeuronCpt += inputData[i];
		}
		interNeuronCpt += inputData[dataLength - 2];
		
		//Adding and Connecting Output Neurons
		this.layers[dataLength - 1] = new AbstractNeuron[inputData[dataLength - 1]];
		for(int j = 0; j < inputData[dataLength - 1]; j++){
			OutputNeuron neuron = new OutputNeuron();
			this.outputNeurons.add(neuron);
			this.layers[dataLength - 1][j] = neuron;
			for(int k = 1; k <= inputData[dataLength - 2]; k++){
				double w = defaultSynapseWeight;
				if(randomWeight){
					w = rand.nextDouble()*2. - 1.;
				}
				//Synapse s = new Synapse(w, this.intermediateNeurons.get(interNeuronCpt-k), neuron);
				this.synapses.add(new Synapse(w, this.intermediateNeurons.get(interNeuronCpt-k), neuron));
				
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
	
	public void setNormalizedInputs(double[] x, double max){
		int i = 0;
		for (AbstractNeuron neuron : inputNeurons){
			((InputNeuron)neuron).setInput(((double)x[i]) / (max - 1.0));
			i++;
		}	
	}
	
	public void setInputs(double[] x){
		int i = 0;
		for (AbstractNeuron neuron : inputNeurons){
			double d = x[i];
			//if(d >= 127){ d -= 255;}
			((InputNeuron)neuron).setInput(d);
			i++;
		}		
	}
	
	
	public void linkNetwork(){
		
	}
	
	//Debug Methods
	
	public double wideWeight(){
		double wide = 0;
		for(Synapse s : this.synapses){
			if(Math.abs(s.getWeight()) > wide){ wide = Math.abs(s.getWeight());}
		}
		return wide;
	}
	
	public int mostProbableAnswer(){
		double maxProba = 0;
		int number = -1;
		for(int i = 0; i < 10; i++){
			if(this.getOutputs()[i] > maxProba){
				maxProba = this.getOutputs()[i];
				number = i;
			}	
		}
		return number;
	}
	
	
}
