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
	
	double[][] entityValues;
	
	double[][] entityDiffs;
		
	double[][] entityWeightedSums;
	
	double[][] entityIntermediateValues;
		
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
		this.entityDiffs = new double[this.layerNumber][];
		this.entityWeightedSums = new double[this.layerNumber][];
		this.entityValues = new double[this.layerNumber][];
		this.entityIntermediateValues = new double[this.layerNumber][];
		for(int i = 0; i < this.layerNumber; i++){
			this.layers[i] = new Entity[inputData[i]];
			this.entityDiffs[i] = new double[inputData[i]];
			this.entityWeightedSums[i] = new double[inputData[i]];
			this.entityValues[i] = new double[inputData[i]];
			this.entityIntermediateValues[i] = new double[inputData[i]];
			
			for(int j = 0; j < inputData[i]; j++){
				this.layers[i][j] = new Entity(j, (rand.nextDouble()- 0.5)*this.biasWide);
				this.entityDiffs[i][j] = 0;
				this.entityWeightedSums[i][j] = 0;
				this.entityValues[i][j] = 0;
				this.entityIntermediateValues[i][j] = 0;
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
	
	public void singleUnsupervisedLearning(int cdIterations, double[] exemple, boolean instantLearning){
		// instantLearning means that bias and weights are updated after each step
		double[] currentExemple = exemple;
		
		// the machines are trained one after another
		for(int i = 0; i < this.layerNumber - 1; i++){
			this.machines[i].unsupervisedLearning(cdIterations, currentExemple);
			if(instantLearning){
				this.machines[i].applyLearningGradients();
			}
			this.machines[i].setBinaryInputs(currentExemple);
			this.machines[i].layerUpdate(1);
			int[] temp = this.machines[i].getBinaryOutputs();
			currentExemple = new double[temp.length];
			for(int j = 0; j < temp.length; j++){
				currentExemple[j] = (double)temp[j];
			}
		}
	}
	
	
	
	public void applyLearningGradients(){
		for(int i = 0; i < this.layerNumber - 1; i++){
			this.machines[i].applyLearningGradients();
		}
	}
	
	
	public void singleBackPropagation(int[] exemple, int[] expectedOutput){
		
		// Entity Diff computing
		
		//sub step : output layer
		for(int i = 0; i < this.layers[this.layerNumber - 1].length; i++){
			double error = expectedOutput[i] - this.entityValues[this.layerNumber - 1][i];
			// delta = f'(input)*e
			this.entityDiffs[this.layerNumber - 1][i] = Sigmoid.getINSTANCE().applyDerivative(this.entityWeightedSums[this.layerNumber - 1][i])*error;
			
			//bias diff 
			this.machines[this.layerNumber - 2].biasGradient[1][i] += this.entityDiffs[this.layerNumber - 1][i]*this.learningRate;

		}
		
		// sub step : others layers
		
		for(int i = this.layerNumber - 2; i > 0; i--){
			for(int j = 0; j < this.layers[i].length; j++){
				double weightedErrorOutput = 0;
				for(int k = 0; k < this.layers[i + 1].length; k++){
					weightedErrorOutput += this.entityDiffs[i + 1][k]*this.machines[i].connections[j][k];
				}
				// delta = f'(input)* sum ( gradient next Neuron * weight linked synapse) 
				this.entityDiffs[i][j] = Sigmoid.getINSTANCE().applyDerivative(this.entityWeightedSums[i][j])*weightedErrorOutput;
				
				//bias diff 
				this.machines[i - 1].biasGradient[1][i] += this.entityDiffs[this.layerNumber - 1][i]*this.learningRate;
				//weight diff
				//this.machines[this.layerNumber - 2].connectionsGradient[]
			}
		}
		
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * TODO
		 * 
		 * 
		 * 
		 * 
		 */
		
	}
	
	
	
}
