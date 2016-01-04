package restrictedBoltzmann;

import java.util.Random;

public class DeepBeliefNetwork {
	/*
	 * The Network is modeled by a composition of RestrictedBoltzmannMachine connected one with another
	 * 
	 * 
	 */
	
	
	
	
	//Attributes
	
	Entity[][] layers;
	
	RestrictedBoltzmannMachine[] machines;
	
	private int layerNumber;
	
	double weightWide;
	
	double biasWide;
	
	double learningRate;
	
	
	//Constructor
	
	public DeepBeliefNetwork(int[] inputData, double weightWide, double biasWide, double learningRate){
		/*
		 * inputData : {layer0UnitsNumber, layer1UnitsNumber, layer2UnitsNumber, ... }
		 * 
		 */
		this.weightWide = weightWide;
		this.biasWide = biasWide;
		this.learningRate = learningRate;
		Random rand = new Random();
		this.layerNumber = inputData.length;
		this.layers = new Entity[this.layerNumber][];
		for(int i = 0; i < this.layerNumber; i++){
			this.layers[i] = new Entity[inputData[i]];
			for(int j = 0; j < inputData[i]; j++){
				this.layers[i][j] = new Entity(j, (rand.nextDouble()- 0.5)*this.biasWide);
			}
		}
		
		this.machines = new RestrictedBoltzmannMachine[this.layerNumber - 1];
		for(int i = 0; i < this.layerNumber - 1; i++){
			this.machines[i] = new RestrictedBoltzmannMachine(this.layers[i], this.layers[i + 1], weightWide, this.learningRate);
		}
		
		
	}
	
	
	
	// Methods
	
	public void setBinaryInputs(int[] x){
		this.machines[0].setBinaryInputs(x);
	}
	
	public int[] getBinaryOutputs(){
		int[] outputs = this.machines[this.layerNumber - 2].getBinaryOutputs(); //there is (this.layerNumber -1) Boltzmann machines in the network
		return outputs;
	}
	
	public double[] getProbabilityOutputs(){
		double[] outputs = this.machines[this.layerNumber - 2].getProbabilityOutputs(); 
		return outputs;
	}
	
	public int[] getLayerState(int l){
		int[] states = new int[this.layers[l].length];
		for(int i = 0; i < states.length; i++){
			states[i] = this.layers[l][i].getState();
		}
		return states;
	}
	
	
	public void singleUnsupervisedLearning(int cdIteraions, int[] exemple){
		
		
		for(int i = 0; i < this.layerNumber - 1; i++){
			
			
			
		}
		
	}
	
	
	
	
	
}
