package restrictedBoltzmann;

import java.util.ArrayList;
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
	
	// BackPropagation Attributes
	
	final double momentumFactor = 0.0;
	
	double[][] EntityDiffs;
		
	double[][] EntityWeightedSums;
		
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
		this.EntityDiffs = new double[this.layerNumber][];
		this.EntityWeightedSums = new double[this.layerNumber][];
		for(int i = 0; i < this.layerNumber; i++){
			this.layers[i] = new Entity[inputData[i]];
			this.EntityDiffs[i] = new double[inputData[i]];
			this.EntityWeightedSums[i] = new double[inputData[i]];
			
			for(int j = 0; j < inputData[i]; j++){
				this.layers[i][j] = new Entity(j, (rand.nextDouble()- 0.5)*this.biasWide);
				this.EntityDiffs[i][j] = 0;
				this.EntityWeightedSums[i][j] = 0;
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
	
	
	
	public void singleUnsupervisedLearning(int cdIterations, int[] exemple){
		
		int[] currentExemple = exemple;
		
		// the machines are trained one after another
		for(int i = 0; i < this.layerNumber - 1; i++){
			this.machines[i].unsupervisedLearning(cdIterations, currentExemple);
			this.machines[i].setBinaryInputs(currentExemple);
			this.machines[i].layerUpdate(1);
			currentExemple = this.machines[i].getBinaryOutputs();
		}
	}
	
//	public void singleBackPropagation(int[] exemple, int[] expectedOutput){

	
	//}
	
	
	
}
