package restrictedBoltzmann;

import java.io.File;
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

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class DeepBeliefNetwork {
	/**
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

	//load deepbeliefmachine
	public DeepBeliefNetwork(Path p) throws IOException{

		/*
		 * Save file content:
		 * line 0: (informations) total number of layers, number of rbm layers, learningRate, backPropLearningRate
		 * line 1: layers composition (int[] inputData)
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

		int lineCpt = 0;
		int currentEntity = 0;
		int currentLayer = 0;

		double[] informations = new double[4];
		int[] inputData = null;


		for(String line : Files.readAllLines(p)) {
			if(lineCpt == 0){
				int partCpt = 0;
				for (String part : line.split("\\s+")) {
					informations[partCpt] = Double.valueOf(part);
					//System.out.println();
					partCpt++;
				}
				inputData = new int[(int)informations[0]];
			} else if(lineCpt == 1){
				int partCpt = 0;
				for (String part : line.split("\\s+")) {
					inputData[partCpt] = (int)Math.floor(Double.valueOf(part));
					//System.out.println();
					partCpt++;
				}

				// DBN construction
				//call impossible : this(inputData, (int)informations[1], 0, 0, informations[2], informations[3]);

				this.weightWide = 0;
				this.biasWide = 0;
				this.learningRate = informations[2];
				this.backPropLearningRate = informations[3];
				this.totalLayerNumber = inputData.length;
				this.rbmLayerNumber = (int)informations[1];
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
						this.layers[i][j] = new Entity(j, 0);
						this.entityDiffs[i][j] = 0;
						this.entityWeightedSums[i][j] = 0;
						this.entityValues[i][j] = 0;
						this.entityIntermediateValues[i][j] = 0;
					}
				}

				this.machines = new RestrictedBoltzmannMachine[this.totalLayerNumber - 1];
				for(int i = 0; i < this.totalLayerNumber - 1; i++){
					this.machines[i] = new RestrictedBoltzmannMachine(this.layers[i], this.layers[i + 1], 0, this.learningRate);
				}




			} else if(lineCpt < 2 + informations[0]) {
				//bias line
				currentLayer = lineCpt - 2;
				int partCpt = 0;
				for (String part : line.split("\\s+")) {
					this.layers[currentLayer][partCpt].setBias(Double.valueOf(part));
					//System.out.println();
					partCpt++;
				}
				currentLayer = 0;
			} else {
				//weight line


				int partCpt = 0;
				for (String part : line.split("\\s+")) {
					//System.out.println("Line " + lineCpt);
					//System.out.println(currentLayer +" "+ currentEntity +" "+ partCpt);
					this.machines[currentLayer].connections[currentEntity][partCpt] = (Double.valueOf(part));
					//System.out.println();
					partCpt++;
				}

				currentEntity++;
				if(currentEntity == this.layers[currentLayer].length){
					currentLayer++;
					currentEntity = 0;
				}

			}
			lineCpt++;
		}

		System.out.println("Machine pr�te");

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


	public void singleBackPropagation(double[] expectedOutput, int layerNumberToBackPropagate){

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

		for(int i = this.totalLayerNumber - 2; i >= this.totalLayerNumber - layerNumberToBackPropagate; i--){
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

	public void singleSupervisedLearning(double[] example, int label, double wide, int backPropLayers){
		this.setInputs(example, wide);
		this.fire();
		double[] expectedOutput = this.getMnistExpectedOutput(label);
		this.singleBackPropagation(expectedOutput, backPropLayers);
	}

	public void singleSupervisedLearning(double[] example, int label, int backPropLayers){
		this.setNormalizedInputs(example);
		this.fire();
		double[] expectedOutput = this.getMnistExpectedOutput(label);
		this.singleBackPropagation(expectedOutput, backPropLayers);
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
		 * line 0: (informations) total number of layers, number of rbm layers, learningRate, backPropLearningRate
		 * line 1: layers composition (int[] inputData)
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


		int lineNumber = 2;

		//line 0 and 1:
		String composition = "";
		for(int i = 0; i < this.totalLayerNumber; i++){
			composition += this.layers[i].length + " "; 
			lineNumber ++; //bias lines
		}
		for(int i = 0; i < this.totalLayerNumber - 1; i++){
			lineNumber += this.layers[i].length; // connections lines
		}

		String[] lines = new String[lineNumber];
		lines[0] = this.totalLayerNumber + " " + this.rbmLayerNumber + " " + this.learningRate + " " + this.backPropLearningRate + " ";
		lines[1] = composition;

		for(int i = 2; i < lineNumber; i++){
			lines[i] = "";
		}

		//bias lines
		for(int i = 2; i <= this.totalLayerNumber+1; i++){
			for(int j = 0; j < this.layers[i - 2].length; j++){
				lines[i] += this.layers[i-2][j].getBias() + " ";
			}
		}

		//weights lines
		int lineIndex = this.totalLayerNumber+2;
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
		/*System.out.println("Do you want to save a copy of the machine ? (1 yes; 0 no");
		selectAnswer = reader.nextInt();
		 */
		if(selectAnswer == 1){
			List<String> linesList = Arrays.asList(lines);
			Path file = Paths.get("DBNsaveFiles", "saveFile" + this.totalLayerNumber +  "Layers"  + date + ".txt");
			Files.write(file, linesList, Charset.forName("UTF-8"));
		}



	}


	public void visualizeAllFilters() throws ParseException{

		String date = "_" + LocalDateTime.now();
		int month = LocalDateTime.now().getMonthValue();
		int year = LocalDateTime.now().getYear();
		int day = LocalDateTime.now().getDayOfMonth();
		int hour = LocalDateTime.now().getHour();
		int minute = LocalDateTime.now().getMinute();
		int seconde = LocalDateTime.now().getSecond();

		date = day + "-" + month + "-" + year + "_" + hour + "h" + minute + "m" + seconde;


		/*
		 * For all machine we initialize the i layer of the machine,
		 * then we go back through the layers. 
		 */

		//double[][] valueNeuron = new double[this.layers.length][]; 

		double[] image1DFilter = new double[this.layers[0].length];


		// i : number of layers

		for(int i = 1; i<this.layers.length; i++){


			/*
			 * Whole net intialized to 0
			 */

			for(int j=0; j< this.layers.length; j++){
				for(int k =0; k< this.layers[j].length; k ++){
					this.layers[j][k].setState(0);
				}

			}

			/*
			 * Size + nbr of filters
			 *init of filter file
			 */

			Random rand = new Random();
			int nombreFiltres = (int) Math.ceil(Math.sqrt(this.layers[i].length));
			int pixelParFiltre = (int) Math.sqrt(this.layers[0].length);
			int ecart = (nombreFiltres-1);
			int taille = (nombreFiltres*pixelParFiltre)+ecart;
			double[][] allFilters = new double[taille][taille];
			for(int k=0; k<taille; k++){
				for(int j = 0; j<taille; j++){
					allFilters[k][j] = 1; 
				}
			}


			// j : neurons in layer i = filter we want to show

			for(int j=0; j < this.layers[i].length; j++){


				/*
				 * For each layer i we put node j to 1 the others to 0
				 */


				for(int k=0; k<this.layers[i].length; k++){
					if (k==j){
						this.layers[i][k].setState(1);
					}
					else{
						this.layers[i][k].setState(0);
					}
				}


				/*We update the layers given the distribution
				 * of the previous layers to the first one and store in image1DFilter.
				 */

				//g : layers to update

				if(i!=1){
					for(int o = 0; o <5; o++){
						this.machines[i-1].layerUpdate(0);
						this.machines[i-1].layerUpdate(1);

					}
				}



				double weightedSum = 0;

				for(int g = i-1; g>=0; g--){

					if(g!=0){
						this.machines[g].layerUpdate(0);
					}
					else{
						for(int k = 0; k<this.layers[0].length; k++){
							double x = this.layers[0][k].getBias();

							for(int l = 0; l<this.layers[1].length; l++){

								x += this.machines[0].connections[k][l]*this.machines[0].layers[1][l].getState();

							}

							image1DFilter[k]=Sigmoid.getINSTANCE().apply(x);
						}


					}

				}






				double [][]image2DFilter = Tools.image1Dto2Ddouble(image1DFilter, pixelParFiltre, pixelParFiltre);

				int rang = j % nombreFiltres;
				int ligne =(int) Math.floor(j/nombreFiltres);

				for(int k = 0; k< image2DFilter[0].length; k++){
					for(int l = 0; l<image2DFilter[0].length; l++){
						allFilters[ligne*(pixelParFiltre+1)+k][rang*(pixelParFiltre+1)+l]=image2DFilter[k][l];

						//System.out.println(image2DFilter[k][l]);

					}
				}

			}

			/*
			 * File info
			 */

			OutputWeights output = new OutputWeights(allFilters);


			Path path = Paths.get("DBN_Filters/Filtre_" + date + "couche" + i, date + "_AllFilters" + ".bmp");
			File f = new File("DBN_Filters/Filtre_" + date + "couche" +i);
			f.mkdirs();
			try {
				output.toBmp(path);

			} catch (java.io.IOException e) {

				System.out.println("Exception");
				e.printStackTrace();
			}

		}

	}
}


