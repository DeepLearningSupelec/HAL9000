package restrictedBoltzmann;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DeepBeliefNetwork {
	/*
	 * The Network is modeled by a composition of RestrictedBoltzmannMachine connected one with another
	 * 
	 * 
	 */
	
	
	
	
	//Attributes
	
	Entity[][] layers;
	
	RestrictedBoltzmannMachine[] machines;
	
	private int rbmLayerNumber;
	
	int totalLayerNumber;
	
	double weightWide;
	
	double biasWide;
	
	double learningRate;
	
	
	
	// BackPropagation Attributes
	
	double backPropLearningRate;
	
	final double momentumFactor = 0.0;
	
	double[][] entityValues;
	
	double[][] entityDiffs;
		
	double[][] entityWeightedSums;
	
	double[][] entityIntermediateValues;
		
	//Constructor
	
	public DeepBeliefNetwork(int[] inputData, int rbmLayerNumber, double weightWide, double biasWide, double learningRate, double backPropLearningRate){
		/*
		 * inputData : {layer0UnitsNumber, layer1UnitsNumber, layer2UnitsNumber, ... }
		 * 
		 */
		this.weightWide = weightWide;
		this.biasWide = biasWide;
		this.learningRate = learningRate;
		this.backPropLearningRate = backPropLearningRate;
		Random rand = new Random();
		this.totalLayerNumber = inputData.length;
		this.rbmLayerNumber = rbmLayerNumber;
		this.layers = new Entity[this.totalLayerNumber][];
		this.entityDiffs = new double[this.totalLayerNumber][];
		this.entityWeightedSums = new double[this.totalLayerNumber][];
		this.entityValues = new double[this.totalLayerNumber][];
		this.entityIntermediateValues = new double[this.totalLayerNumber][];
		for(int i = 0; i < this.totalLayerNumber; i++){
			this.layers[i] = new Entity[inputData[i]];
			this.entityDiffs[i] = new double[inputData[i]];
			this.entityWeightedSums[i] = new double[inputData[i]];
			this.entityValues[i] = new double[inputData[i]];
			this.entityIntermediateValues[i] = new double[inputData[i]];
			
			for(int j = 0; j < inputData[i]; j++){
				this.layers[i][j] = new Entity(j, (rand.nextDouble())*this.biasWide);
				this.entityDiffs[i][j] = 0;
				this.entityWeightedSums[i][j] = 0;
				this.entityValues[i][j] = 0;
				this.entityIntermediateValues[i][j] = 0;
			}
		}
		
		this.machines = new RestrictedBoltzmannMachine[this.totalLayerNumber - 1];
		for(int i = 0; i < this.totalLayerNumber - 1; i++){
			this.machines[i] = new RestrictedBoltzmannMachine(this.layers[i], this.layers[i + 1], weightWide, this.learningRate);
		}
		
		
	}
	
	
	
	// Methods
	
	public void setBinaryInputs(int[] x){
		this.machines[0].setBinaryInputs(x);
	}
	
	public int[] getRBMBinaryOutputs(){
		int[] outputs = this.machines[this.rbmLayerNumber - 2].getBinaryOutputs(); //there is (this.rbmLayerNumber -1) Boltzmann machines in the network
		return outputs;
	}
	
	public double[] getRBMProbabilityOutputs(){
		double[] outputs = this.machines[this.rbmLayerNumber - 2].getProbabilityOutputs(); 
		return outputs;
	}
	
	public int[] getLayerState(int l){
		int[] states = new int[this.layers[l].length];
		for(int i = 0; i < states.length; i++){
			states[i] = this.layers[l][i].getState();
		}
		return states;
	}
	
	
	
	public void singleUnsupervisedLearning(int cdIterations, int[] exemple, boolean instantLearning){
		
		int[] currentExemple = exemple;
		
		// the machines are trained one after another
		for(int i = 0; i < this.rbmLayerNumber - 1; i++){
			this.machines[i].unsupervisedLearning(cdIterations, currentExemple);
			if(instantLearning){
				this.machines[i].applyLearningGradients();
			}
			this.machines[i].setBinaryInputs(currentExemple);
			this.machines[i].layerUpdate(1);
			currentExemple = this.machines[i].getBinaryOutputs();
		}
	}
	
	public void singleUnsupervisedLearning(int cdIterations, double[] exemple, boolean instantLearning){
		// instantLearning means that bias and weights are updated after each step
		double[] currentExemple = exemple;
		
		// the machines are trained one after another
		for(int i = 0; i < this.rbmLayerNumber - 1; i++){
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
		for(int i = 0; i < this.totalLayerNumber - 1; i++){
			this.machines[i].applyLearningGradients();
		}
	}
	
	
	public void singleBackPropagation(double[] expectedOutput){
		
		// Entity Diff computing
		
		//sub step : output layer
		for(int i = 0; i < this.layers[this.totalLayerNumber - 1].length; i++){
			double error = expectedOutput[i] - this.entityValues[this.totalLayerNumber - 1][i];
			// delta = f'(input)*e
			this.entityDiffs[this.totalLayerNumber - 1][i] = Sigmoid.getINSTANCE().applyDerivative(this.entityWeightedSums[this.totalLayerNumber - 1][i])*error;
			
			//bias diff 
			this.machines[this.totalLayerNumber - 2].biasGradient[1][i] += this.entityDiffs[this.totalLayerNumber - 1][i]*this.backPropLearningRate;
			//weight diff
			for(int m = 0; m < this.layers[this.totalLayerNumber - 2].length; m++){
				this.machines[this.totalLayerNumber - 2].connectionsGradient[m][i] += 
						this.backPropLearningRate*this.entityDiffs[this.totalLayerNumber - 1][i]*Sigmoid.getINSTANCE().apply(this.entityWeightedSums[this.totalLayerNumber - 2][m]);
			}
		}
		
		// sub step : others layers
		
		for(int i = this.totalLayerNumber - 2; i >= 0; i--){
			for(int j = 0; j < this.layers[i].length; j++){
				double weightedErrorOutput = 0;
				for(int k = 0; k < this.layers[i + 1].length; k++){
					weightedErrorOutput += this.entityDiffs[i + 1][k]*this.machines[i].connections[j][k];
				}
				// delta = f'(input)* sum ( gradient next Neuron * weight linked synapse) 
				this.entityDiffs[i][j] = Sigmoid.getINSTANCE().applyDerivative(this.entityWeightedSums[i][j])*weightedErrorOutput;
				
				if(i > 0){
					//bias diff 
					this.machines[i - 1].biasGradient[1][j] += this.entityDiffs[i][j]*this.backPropLearningRate;
					//weight diff
					for(int m = 0; m < this.layers[i - 1].length; m++){
						this.machines[i - 1].connectionsGradient[m][j] += 
								this.backPropLearningRate*this.entityDiffs[i][j]*Sigmoid.getINSTANCE().apply(this.entityWeightedSums[i - 1][m]);
					}
				} else {
					//input bias diff
					this.machines[0].biasGradient[0][j] += this.entityDiffs[0][j]*this.backPropLearningRate;
				}
			}
		}	
	}
	
	
	public void setIntegerInputs(int[] inputs, int inputWide){
		for(int i = 0; i < this.layers[0].length; i++){
			this.entityValues[0][i] = ((double)inputs[i]) / ((double)inputWide);
			this.entityWeightedSums[0][i] = ((double)inputs[i]) / ((double)inputWide);
            /*
             * 
             * TODO
             * tests
             * 
             */
		}
	}
	
	public void setInputs(double[] inputs, double inputWide){
		for(int i = 0; i < this.layers[0].length; i++){
			this.entityValues[0][i] = ((double)inputs[i]) / ((double)inputWide);
			this.entityWeightedSums[0][i] = ((double)inputs[i]) / ((double)inputWide);
            /*
             * 
             * TODO
             * tests
             * 
             */
		}
	}
	
	public void setNormalizedInputs(double[] inputs){
		for(int i = 0; i < this.layers[0].length; i++){
			this.entityValues[0][i] = inputs[i];
			this.entityWeightedSums[0][i] = inputs[i];
		}
	}
	
	public void fire(){
		
		for(int i = 1; i < this.totalLayerNumber; i++){
			for(int j = 0; j < this.layers[i].length; j++){
				
				double x = this.machines[i - 1].layers[1][j].getBias();
				for(int k = 0; k < this.layers[i - 1].length; k++){
					x += this.machines[i - 1].connections[k][j]*this.entityValues[i - 1][k];
				}
				this.entityWeightedSums[i][j] = x;
				this.entityValues[i][j] = Sigmoid.getINSTANCE().apply(x);
				
			}
		}
	}
	
	public void singleSupervisedLearning(double[] example, int label, double wide){
		this.setInputs(example, wide);
		this.fire();
		double[] expectedOutput = this.getMnistExpectedOutput(label);
		this.singleBackPropagation(expectedOutput);
	}
	
	public void singleSupervisedLearning(double[] example, int label){
		this.setNormalizedInputs(example);
		this.fire();
		double[] expectedOutput = this.getMnistExpectedOutput(label);
		this.singleBackPropagation(expectedOutput);
	}
	
	public double[] getMnistExpectedOutput(int label){
		double[] output = new double[10];
		output[label] = 1.;
		return output;
	}
	
	public int getMnistMostProbableLabel(){
		int label = -1;
		double maxProba = 0.;
		for(int i = 0; i<10;i++){
			if(this.entityValues[this.totalLayerNumber - 1][i] >= maxProba){
				label = i;
				maxProba = this.entityValues[this.totalLayerNumber - 1][i];
			}
		}
		return label;
	}
	
	public void saveMachineState() throws IOException{
		
		/*
		 * Save file content:
		 * 1st line: layers composition (int[] inputData)
		 * line 2 to line x : bias of each layer
		 * line x+1 : weight of synapses from 1st layer 1st entity with 2nd layer
		 * line x+2 : weight of synapses from 1st layer 2nd entity with 2nd layer
		 * .
		 * .
		 * .
		 * line y+1 : weight of synapses from 2nd layer 1st entity with 3rd layer
		 * .
		 * .
		 * .
		 */
		
		
		int lineNumber = 1;
		
		//line 1:
		String composition = "";
		for(int i = 0; i < this.totalLayerNumber; i++){
			composition += this.layers[i].length + " "; 
			lineNumber ++; //bias lines
		}
		for(int i = 0; i < this.totalLayerNumber - 1; i++){
			lineNumber += this.layers[i].length; // connections lines
		}
		
		String[] lines = new String[lineNumber];
		lines[0] = composition;
		
		//bias lines
		for(int i = 1; i <= this.totalLayerNumber; i++){
			for(int j = 0; j < this.layers[i - 1].length; j++){
				lines[i] += this.layers[i-1][j].getBias() + " ";
			}
		}
		
		//weights lines
		int lineIndex = this.totalLayerNumber+1;
		for(int i = 0; i < this.totalLayerNumber - 1; i++){
			for(int j = 0; j < this.layers[i].length; j++){
				for(int k = 0; k < this.layers[i+1].length; k++){
					lines[lineIndex] += this.machines[i].connections[j][k] + " ";
				}
				lineIndex++;
			}
		}
		
		String date = "_" + LocalDateTime.now();
		date = date.substring(0, 20);
		date = date.replace(':', '-');
		System.out.println("date" + date);
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		int selectAnswer = 1;
		System.out.println("Do you want to save a copy of the machine ? (1 yes; 0 no");
		selectAnswer = reader.nextInt();
	
		if(selectAnswer == 1){
			List<String> linesList = Arrays.asList(lines);
			Path file = Paths.get("DBNsaveFiles", "saveFile" + this.totalLayerNumber +  "Layers"  + date + ".txt");
			Files.write(file, linesList, Charset.forName("UTF-8"));
		}
		
		
		
	}
	
}
