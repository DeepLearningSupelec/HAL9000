package restrictedBoltzmann;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;


public class RestrictedBoltzmannMachine {


	private static final Exception IOException = null;

	//Attributes

	Entity[][] layers;

	double energy;

	double weightWide;

	double biasWide;

	double[][] connections;

	double learningRate;

	static String image_adress = "Images_ppm//";

	double[][] connectionsGradient;
	double[][] biasGradient;



	//Constructor

	/**
	 * <p>Restricted Boltzmann Machine COnstructor</p>
	 * <p>
	 *  Connections between each entity of each table are made. Learning rate and Weight Wide are set.
	 *  </p>
	 *  
	 * @param visibleEntities
	 * 			Table of entity which correspond to the visible units.
	 * @param hiddenEntities
	 * 			Table of entity which correspond to the hidden units.
	 * @param weightWide
	 * 			Range of the weight.
	 * @param learningRate
	 * 			The learning rate of the network is set.
	 */
	public RestrictedBoltzmannMachine(Entity[] visibleEntities, Entity[] hiddenEntities, double weightWide, double learningRate){
		this.weightWide = weightWide;
		this.learningRate = learningRate;
		Random rand = new Random();
		this.layers = new Entity[2][];
		this.layers[0] = visibleEntities;
		this.layers[1] = hiddenEntities;
		this.connections = new double[visibleEntities.length][hiddenEntities.length];
		for(int i = 0; i < this.connections.length; i++){
			for(int j = 0; j < this.connections[0].length; j++){
				this.connections[i][j] = (rand.nextDouble()- 0.5)*this.weightWide;
			}
		}
		this.connectionsGradient = new double[this.connections.length][this.connections[0].length];

		this.biasGradient = new double[2][];
		this.biasGradient[0] = new double[this.layers[0].length];
		this.biasGradient[1] = new double[this.layers[1].length];
	}

	/**
	 * <p>Restricted Boltzmann Machine COnstructor</p>
	 * <p>
	 *  Connections between each entity of each table are made. Learning rate and Weight Wide are set.
	 *  </p>
	 *  
	 * @param inputData
	 * 			Table of two integers, the first one is the nulber of visible unit, the second one is the numbr of hidden units.
	 * @param weightWide
	 * 			Range of the weight.
	 * @param biasWide
	 * 			Range of the bias.
	 * 
	 */

	public RestrictedBoltzmannMachine(int[] inputData, double weightWide, double biasWide, double learningRate) {
		/*
		 * inputData : {visibleUnitsNumber, hiddenunitsNumber}
		 * 
		 * 
		 */
		this.learningRate = learningRate;
		this.weightWide = weightWide;
		this.biasWide = biasWide;
		Random rand = new Random();
		this.layers = new Entity[2][];
		for(int i = 0; i < 2; i++){
			this.layers[i] = new Entity[inputData[i]];
			for(int j = 0; j < inputData[i]; j++){
				this.layers[i][j] = new Entity(j, (rand.nextDouble()- 0.5)*this.biasWide);
			}
		}
		this.connections = new double[inputData[0]][inputData[1]];
		for(int i = 0; i < this.connections.length; i++){
			for(int j = 0; j < this.connections[0].length; j++){
				this.connections[i][j] = (rand.nextDouble()- 0.5)*this.weightWide;
			}
		}

		this.connectionsGradient = new double[this.connections.length][this.connections[0].length];

		this.biasGradient = new double[2][];
		this.biasGradient[0] = new double[this.layers[0].length];
		this.biasGradient[1] = new double[this.layers[1].length];
	}

	public RestrictedBoltzmannMachine(int[] inputData, double weightWide, double biasWide){
		/*
		 * inputData : {visibleUnitsNumber, hiddenunitsNumber}
		 * 
		 * 
		 */
		this.learningRate = 0.005;
		this.weightWide = weightWide;
		this.biasWide = biasWide;
		Random rand = new Random();
		this.layers = new Entity[2][];
		for(int i = 0; i < 2; i++){
			this.layers[i] = new Entity[inputData[i]];
			for(int j = 0; j < inputData[i]; j++){
				this.layers[i][j] = new Entity(j, (rand.nextDouble()- 0.5)*this.biasWide);
			}
		}
		this.connections = new double[inputData[0]][inputData[1]];
		for(int i = 0; i < this.connections.length; i++){
			for(int j = 0; j < this.connections[0].length; j++){
				this.connections[i][j] = (rand.nextDouble()- 0.5)*this.weightWide;
			}
		}
		this.connectionsGradient = new double[this.connections.length][this.connections[0].length];

		this.biasGradient = new double[2][];
		this.biasGradient[0] = new double[this.layers[0].length];
		this.biasGradient[1] = new double[this.layers[1].length];

	}

	/**
	 * <p>Restricted Boltzmann Machine COnstructor</p>
	 * <p>
	 *  Connections between each entity of each table are made. Learning rate and Weight Wide are set.
	 *  </p>
	 *  
	 * @param p
	 * 			Path to the file in which the paramaters of the RBM are.
	 * <p>inputFile format:
	 * [
	 * A B C D
	 * x b1
	 * y b2
	 * .
	 * .
	 * .
	 * x0 y0 w0
	 * x1 y1 w1
	 * x2 y2 w2
	 * .
	 * .
	 * .
	 * ]
	 *
	 * A : entitiesNumber;
	 * B : visibleEntitiesNumber;
	 * C : hiddenEntitiesNumber;
	 * D : connectionsDescribed
	 *
	 * x bi : bias bi on entity x
	 * 	
	 * xi yi wi : connection between visible entity xi and hidden entity yi weighted by wi
	 * </p>
	 * 
	 * @throws IOException
	 * 			If the file at the path p does not exist.
	 */

	public RestrictedBoltzmannMachine(Path p) throws IOException{

		/*
		inputFile format:
		[
		A B C D
		x b1
		y b2
		.
		.
		.
		x0 y0 w0
		x1 y1 w1
		x2 y2 w2
		.
		.
		.
		]

		A : entitiesNumber;
		B : visibleEntitiesNumber;
		C : hiddenEntitiesNumber;
		D : connectionsDescribed

		x bi : bias bi on entity x

		xi yi wi : connection between visible entity xi and hidden entity yi weighted by wi

		 */

		int[] informations = new int[4];
		/*
		 * informations:
		 * 
		 * 0 : entitiesNumber;
		 * 1 : visibleEntitiesNumber;
		 * 2 : hiddenEntitiesNumber;
		 * 3 : connectionsDescribed
		 */

		int lineCptTemp = 0;

		for(String line : Files.readAllLines(p)) {
			if(lineCptTemp ==0){
				int partCpt = 0;
				for (String part : line.split("\\s+")) {
					int i = Integer.valueOf(part);
					informations[partCpt] = i;
					partCpt++;
				}

			}
			lineCptTemp++;
		}

		this.layers = new Entity[2][];
		for(int i = 0; i < 2; i++){
			this.layers[i] = new Entity[informations[i + 1]];
		}

		this.connections = new double[informations[1]][informations[2]];

		for(int i = 0; i < this.connections.length; i++){
			for(int j = 0; j < this.connections[0].length; j++){
				this.connections[i][j] = 0;
			}
		}

		int lineCpt = 0; // line indicator

		for (String line : Files.readAllLines(p)) {

			if(lineCpt ==0){
				int partCpt = 0;
				for (String part : line.split("\\s+")) {
					int i = Integer.valueOf(part);
					informations[partCpt] = i;
					partCpt++;
				}

			} else if(lineCpt < informations[0] + 1){

				// Entity Creation

				int partCpt = 0;
				double bias = 0;
				boolean setBias = false;
				int entityNumber = 0;

				for (String part : line.split("\\s+")) {
					if(partCpt == 0){
						Integer i = Integer.valueOf(part);
						entityNumber = i;

					}
					if(partCpt == 1){
						setBias = true;
						Integer i = Integer.valueOf(part);
						bias = i;
					}
					partCpt++;
				}

				int layer = 0;
				if(entityNumber >= informations[1]){ //if the entity is hidden
					layer = 1;
					entityNumber -= informations[1];
				}
				this.layers[layer][entityNumber] = new Entity(entityNumber, bias);		

			} else {	
				int partCpt = 0;
				double weight = 0;
				boolean setWeight = false;

				int[] connectedEntities = new int[2];

				for (String part : line.split("\\s+")) {
					if(partCpt < 2){
						Integer i = Integer.valueOf(part);
						connectedEntities[partCpt] = i;

					}
					if(partCpt == 3){
						setWeight = true;
						Integer i = Integer.valueOf(part);
						weight = i;
					}
					partCpt++;
				}

				//Connection creation

				this.connections[connectedEntities[0]][connectedEntities[1]] = weight;
			}
			lineCpt++;
		}
		this.connectionsGradient = new double[this.connections.length][this.connections[0].length];

		this.biasGradient = new double[2][];
		this.biasGradient[0] = new double[this.layers[0].length];
		this.biasGradient[1] = new double[this.layers[1].length];

	}



	/*public RestrictedBoltzmannMachine (int[] inputData, boolean randomWeight){
		int dataLength = inputData.length;
		this.layers = new Entity[dataLength][];
		Random rand = new Random();
		double neuronQuantity = 0;
		for(int i = 0; i < dataLength; i++){
			neuronQuantity += inputData[i];
		}
		this.weightWide = 2.38*2./(Math.sqrt(neuronQuantity));

		//must add the neurons
	}*/




	//Methods
	/**
	 * <p>Update Energy</p>
	 * 
	 * <p>The energy of the network is updated after the Gibbs' samplig is done.</p>
	 * 
	 */
	public void updateEnergy(){
		double e = 0;

		for(int i = 0; i < 2; i++){
			for(int j = 0; j < this.layers[i].length; j++){
				e -= this.layers[i][j].getState()*this.layers[i][j].getBias();
			}
		}

		for(int i = 0; i < this.connections.length; i++){
			for(int j = 0; j < this.connections[0].length; j++){
				e -= this.layers[0][i].getState()*this.layers[1][j].getState()
						*this.connections[i][j];
			}
		}

		this.energy = e;
	}
	/**
	 * <p>Sum of the Log Likelihood of the network</p>
	 * 
	 * @param logProbabilityDerivatives
	 * 			Table with the derivative of the log pribability of the unit. First column is visible units. Second one is hidden units.
	 * 
	 * @return
	 * 			Sum of the network's log likelyhood.
	 */
	public double getLogProbabilityDerivativeSum(double[][] logProbabilityDerivatives){

		int xLength = logProbabilityDerivatives.length;
		int yLength = logProbabilityDerivatives[0].length;
		double sum = 0.0;
		for(int i = 0; i < xLength; i++){
			for(int j = 0; j < yLength; j++){
				sum += logProbabilityDerivatives[i][j];
			}
		}

		return sum;
	}

	/**
	 * <p>Update of the state of the layer</p>
	 * <p>Each unit state is set according to it's probability p of being activated. A random number A between 0 and 1 is generated according to a uniform distribution. If p > A unit's state is set to 1</p>
	 *  
	 * @param layerToBeUpdated
	 * 			0 for the visible layer, 1 for the hidden layer.
	 */
	public void layerUpdate(int layerToBeUpdated ){
		Random rand = new Random();

		for(int i = 0; i < this.layers[layerToBeUpdated].length; i++){
			double x = this.layers[layerToBeUpdated][i].getBias();
			for(int j = 0; j < this.layers[(layerToBeUpdated + 1) % 2].length; j++){

				if(layerToBeUpdated == 0){
					x += this.connections[i][j]*this.layers[1][j].getState();
				} else {
					x += this.connections[j][i]*this.layers[0][j].getState();
				}
			}

			if(Sigmoid.getINSTANCE().apply(x) >= rand.nextDouble()){
				this.layers[layerToBeUpdated][i].setState(1);
			} else {
				this.layers[layerToBeUpdated][i].setState(0);
			}

		}
	}

	/**<p>Reach Equilibrium</p>
	 * 
	 * <p>Each layer are updated as long as they don't reach a stable state</p>
	 * 
	 */
	public void reachEquilibrium(){

		boolean[] Equilibriums = {false, false};
		int iterCpt = 0;
		int[][] states =  new int[2][];
		states[0] = this.getLayerState(0);
		states[1] = this.getLayerState(1);

		while((!Equilibriums[0])||(!Equilibriums[1])){

			int layerToUpdate = (iterCpt + 1) % 2;
			states[layerToUpdate] = this.getLayerState(layerToUpdate);
			this.layerUpdate(layerToUpdate);
			Equilibriums[layerToUpdate] = this.isLayerConstant(layerToUpdate, states[layerToUpdate]);
			iterCpt++;
		}
	}

	/**<p> Constrative Divergence</p>
	 * 
	 * <p>The equilibrium is not reached, the higher the number of iterations the better the approximation. Usually iteration = 2.</p>
	 *  
	 * @param iterations
	 * 			Integer, must be an even number if Gibb's sampling is wanted.
	 */
	public void constrastiveDivergence(int iterations){
		//the iterations start from visible to hidden (layers[1] -> layers [2])

		int iterCpt = 0;
		int[][] states =  new int[2][];
		states[0] = this.getLayerState(0);
		states[1] = this.getLayerState(1);

		while(iterCpt < iterations){

			int layerToUpdate = (iterCpt + 1) % 2;
			states[layerToUpdate] = this.getLayerState(layerToUpdate);
			this.layerUpdate(layerToUpdate);
			iterCpt++;
		}
	}

	public void applyLearningGradients(){

		// applying weight modifications

		for(int i = 0; i < this.layers[0].length; i++){
			for(int j = 0; j < this.layers[1].length; j++){

				this.connections[i][j] += this.learningRate*this.connectionsGradient[i][j];
			}
		}

		// applying bias modifications

		for(int i = 0; i < 2; i++){
			for(int j = 0; j < this.layers[i].length; j++){
				this.layers[i][j].setBias(this.layers[i][j].getBias() + this.learningRate*this.biasGradient[i][j]);
			}
		}

		// reseting gradients

		this.connectionsGradient = new double[this.connections.length][this.connections[0].length];
		this.biasGradient = new double[2][];
		this.biasGradient[0] = new double[this.layers[0].length];
		this.biasGradient[1] = new double[this.layers[1].length];

	}



	/**
	 * 
	 * @param cdIterations
	 * @param exemple
	 * @return
	 */
	public double[][] unsupervisedLearning(int cdIterations, int[] exemple){

		double[][] logProbabilityDerivatives = new double[this.connections.length][this.connections[0].length];
		/*
		 * logProbabilityDerivative of weight w(i,j) is given by
		 * <v(i)*h(j)>(data) - <v(i)*h(j)>(model)
		 * where the first term is obtained by a single iteration of contrastive divergence
		 * and the second one by a full step contrastive divergence
		 */
		double[][] biasModificationAttributes = new double[2][];
		biasModificationAttributes[0] = new double[this.layers[0].length];
		biasModificationAttributes[1] = new double[this.layers[1].length];

		// data informations

		this.setBinaryInputs(exemple);
		this.constrastiveDivergence(2);
		/*for(int i = 0; i < this.layers[0].length; i++){
			for(int j = 0; j < this.layers[1].length; j++){
				logProbabilityDerivatives[i][j] = this.layers[0][i].getState()*this.layers[1][j].getState();
				biasModificationAttributes[0][i] = this.layers[0][i].getState();
				biasModificationAttributes[1][j] = this.layers[1][j].getState();
			}
		}*/
		double[] probabilityInputs = this.getProbabilityInputs();
		double[] probabilityOutputs = this.getProbabilityOutputs();
		for(int i = 0; i < this.layers[0].length; i++){
			for(int j = 0; j < this.layers[1].length; j++){
				logProbabilityDerivatives[i][j] = probabilityInputs[i]*probabilityOutputs[j];
			}
			biasModificationAttributes[0][i] = probabilityInputs[i];
		}
		for(int j = 0; j < this.layers[1].length; j++){
			biasModificationAttributes[1][j] = probabilityOutputs[j];
		}



		// model informations

		this.setBinaryInputs(exemple);
		this.constrastiveDivergence(cdIterations);
		/*for(int i = 0; i < this.layers[0].length; i++){
			for(int j = 0; j < this.layers[1].length; j++){
				logProbabilityDerivatives[i][j] -= this.layers[0][i].getState()*this.layers[1][j].getState();
				biasModificationAttributes[0][i] -= this.layers[0][i].getState();
				biasModificationAttributes[1][j] -= this.layers[1][j].getState();
			}
		}*/
		probabilityInputs = this.getProbabilityInputs();
		probabilityOutputs = this.getProbabilityOutputs();
		for(int i = 0; i < this.layers[0].length; i++){
			for(int j = 0; j < this.layers[1].length; j++){
				logProbabilityDerivatives[i][j] -= probabilityInputs[i]*probabilityOutputs[j];
			}
			biasModificationAttributes[0][i] -= probabilityInputs[i];
		}
		for(int j = 0; j < this.layers[1].length; j++){
			biasModificationAttributes[1][j] -= probabilityOutputs[j];
		}

		// updating gradients

		for(int i = 0; i < this.layers[0].length; i++){
			for(int j = 0; j < this.layers[1].length; j++){
				this.connectionsGradient[i][j] += logProbabilityDerivatives[i][j];
			}
			this.biasGradient[0][i] += biasModificationAttributes[0][i];
		}
		for(int j = 0; j < this.layers[1].length; j++){
			this.biasGradient[1][j] += biasModificationAttributes[1][j];
		}


		return logProbabilityDerivatives;
	}


	public double getFreeEnergy(){
		/*
		 * Gives the free Energy of the visible layer of the RBM
		 * 
		 * 
		 * the free energy of visible vector v
			is the energy that a single configuration would need to have in
			order to have the same probability as all of the configurations that contain v
		 */

		double freeEnergy = 0.;

		for(int i = 0; i < this.layers[0].length; i++){
			freeEnergy -= this.layers[0][i].getState()*this.layers[0][i].getBias();
		}


		for(int j = 0; j < this.layers[1].length; j++){
			double x = this.layers[1][j].getBias();
			//System.out.println("x = " + x);
			for(int i = 0; i < this.layers[0].length; i++){
				x += this.layers[0][i].getState()*this.connections[i][j];
				/*System.out.println("1 = " + this.layers[0][i].getState());
				System.out.println("2 = " + this.connections[i][j]);
				System.out.println("x = " + x);
				if(x != x){ this.layers[3] = null;}*/
			}
			//System.out.println("x = " + x);
			freeEnergy -= Math.log(1 + Math.exp(x));
		}

		/*
		for(int j = 0; j < this.layers[1].length; j++){
			double x = this.layers[1][j].getBias();
			for(int i = 0; i < this.layers[0].length; i++){
				x += this.layers[0][i].getState()*this.connections[i][j];
			}
			freeEnergy -= x*Sigmoid.getINSTANCE().apply(x);
		}

		for(int j = 0; j < this.layers[1].length; j++){
			double x = this.layers[1][j].getBias();
			for(int i = 0; i < this.layers[0].length; i++){
				x += this.layers[0][i].getState()*this.connections[i][j];
			}
			double p = Sigmoid.getINSTANCE().apply(x);
			freeEnergy += p*Math.log10(p) + (1. - p)*Math.log10(1. - p);
		}
		 */

		return freeEnergy;
	}




	public double[][] unsupervisedLearning(int cdIterations, double[] exemple){

		double[][] logProbabilityDerivatives = new double[this.connections.length][this.connections[0].length];
		/*
		 * logProbabilityDerivative of weight w(i,j) is given by
		 * <v(i)*h(j)>(data) - <v(i)*h(j)>(model)
		 * where the first term is obtained by a single iteration of contrastive divergence
		 * and the second one by a full step contrastive divergence
		 */
		double[][] biasModificationAttributes = new double[2][];
		biasModificationAttributes[0] = new double[this.layers[0].length];
		biasModificationAttributes[1] = new double[this.layers[1].length];

		// data informations

		this.setBinaryInputs(exemple);
		this.constrastiveDivergence(2);

		double[] probabilityInputs = this.getProbabilityInputs();
		double[] probabilityOutputs = this.getProbabilityOutputs();
		for(int i = 0; i < this.layers[0].length; i++){
			for(int j = 0; j < this.layers[1].length; j++){
				logProbabilityDerivatives[i][j] = probabilityInputs[i]*probabilityOutputs[j];
			}
			biasModificationAttributes[0][i] = probabilityInputs[i];
		}
		for(int j = 0; j < this.layers[1].length; j++){
			biasModificationAttributes[1][j] = probabilityOutputs[j];
		}



		// model informations

		this.setBinaryInputs(exemple);
		this.constrastiveDivergence(cdIterations + 1);
		/*for(int i = 0; i < this.layers[0].length; i++){
			for(int j = 0; j < this.layers[1].length; j++){
				logProbabilityDerivatives[i][j] -= this.layers[0][i].getState()*this.layers[1][j].getState();
				biasModificationAttributes[0][i] -= this.layers[0][i].getState();
				biasModificationAttributes[1][j] -= this.layers[1][j].getState();
			}
		}*/
		probabilityInputs = this.getProbabilityInputs();
		probabilityOutputs = this.getProbabilityOutputs();
		for(int i = 0; i < this.layers[0].length; i++){
			for(int j = 0; j < this.layers[1].length; j++){
				logProbabilityDerivatives[i][j] -= probabilityInputs[i]*probabilityOutputs[j];
			}
			biasModificationAttributes[0][i] -= probabilityInputs[i];
		}
		for(int j = 0; j < this.layers[1].length; j++){
			biasModificationAttributes[1][j] -= probabilityOutputs[j];
		}

		// updating gradients

		for(int i = 0; i < this.layers[0].length; i++){
			for(int j = 0; j < this.layers[1].length; j++){
				this.connectionsGradient[i][j] += logProbabilityDerivatives[i][j];
			}
			this.biasGradient[0][i] += biasModificationAttributes[0][i];
		}
		for(int j = 0; j < this.layers[1].length; j++){
			this.biasGradient[1][j] += biasModificationAttributes[1][j];
		}



		return logProbabilityDerivatives;
	}

	public boolean isLayerConstant(int l, int[] previousLayerState){
		boolean bool = true;
		for(int i = 0; i < previousLayerState.length; i++){
			bool = bool && (previousLayerState[i] == this.layers[l][i].getState());
		}
		return bool;
	}


	public int[] getLayerState(int l){
		int[] states = new int[this.layers[l].length];
		for(int i = 0; i < states.length; i++){
			states[i] = this.layers[l][i].getState();
		}
		return states;
	}

	public int[] getBinaryOutputs(){
		int[] outputs = new int[this.layers[1].length];
		for(int i = 0; i < this.layers[1].length; i++){
			outputs[i] = this.layers[1][i].getState();
		}
		return outputs;
	}

	public int[] getBinaryInputs(){
		int[] outputs = new int[this.layers[0].length];
		for(int i = 0; i < this.layers[0].length; i++){
			outputs[i] = this.layers[0][i].getState();
		}
		return outputs;
	}

	public void displayBinaryOutputs(){
		for(int i = 0; i < this.layers[1].length; i++){
			System.out.println(this.layers[1][i].getState());
		}
	}

	public double[] getProbabilityOutputs(){
		double[] outputs = new double[this.layers[1].length];
		for(int i = 0; i < this.layers[1].length; i++){

			double x = this.layers[1][i].getBias();
			for(int j = 0; j < this.layers[0].length; j++){
				x += this.connections[j][i]*this.layers[0][j].getState();
			}
			outputs[i] = Sigmoid.getINSTANCE().apply(x);
		}
		return outputs;
	}

	public double[] getProbabilityInputs(){
		double[] inputs = new double[this.layers[0].length];
		for(int i = 0; i < this.layers[0].length; i++){


			double x = this.layers[0][i].getBias();
			/*System.out.println("x = " + x);
			if(x != x){ 
				System.out.println("getbias = " + this.layers[0][i].getBias());
				this.layers[3] = null;
			}*/
			for(int j = 0; j < this.layers[1].length; j++){
				/*System.out.println("this.layers[1][j].getState() = " + this.layers[1][j].getState());
				if(this.layers[1][j].getState() != this.layers[1][j].getState()){ this.layers[3] = null;}
				System.out.println("this.connections[i][j] = " + this.connections[i][j]);
				if(this.connections[i][j] != this.connections[i][j]){ this.layers[3] = null;}*/
				x += this.connections[i][j]*this.layers[1][j].getState();
			}

			inputs[i] = Sigmoid.getINSTANCE().apply(x);
		}
		return inputs;
	}

	public double  getEnergy(){
		this.updateEnergy();
		return this.energy;
	}

	public void displayProbabilityOutputs(){

		for(int i = 0; i < this.layers[1].length; i++){

			double x = this.layers[1][i].getBias();
			for(int j = 0; j < this.layers[0].length; j++){
				x += this.connections[j][i]*this.layers[0][j].getState();
			}
			System.out.println(Sigmoid.getINSTANCE().apply(x));
		}
	}

	public void setBinaryInputs(int[] x){
		for(int i = 0; i < this.layers[0].length; i++){
			if(x[i] != 0){
				this.layers[0][i].setState(1);
			} else {
				this.layers[0][i].setState(0);
			}

		}
	}

	public void setBinaryInputs(double[] x){
		for(int i = 0; i < this.layers[0].length; i++){
			if(x[i] != 0.){
				this.layers[0][i].setState(1);
			} else {
				this.layers[0][i].setState(0);
			}

		}
	}

	public static int[] InputToBinaryEntity(double[] x){
		int[] binaryEntity = new int[x.length];
		for(int i = 0; i < x.length; i++){
			if(x[i] != 0.){
				binaryEntity[i] = 1;
			} else {
				binaryEntity[i] = 0;
			}	
		}
		return binaryEntity;
	}

	public void setMnistParameters() throws IOException{
		System.out.println("loading file...");
		Path p = Paths.get("mnistTrainingParameters.txt");
		System.out.println("loaded");
		int lineCpt = 0;

		for(String line : Files.readAllLines(p)) {
			if(lineCpt == 1){
				int partCpt = 0;
				for (String part : line.split("\\s+")) {
					double temp = (Double.valueOf(part) / 60000.);
					if(temp == 0.){
						temp = 1. / 6000.;
					}
					double bias = Math.log(temp / (1. - temp));
					this.layers[0][partCpt].setBias(bias);
					System.out.println(bias);
					partCpt++;
				}

			}
			lineCpt++;
		}


	}

	public void setMnistLabelParameters(int label) throws IOException{
		System.out.println("loading file...");
		Path p = Paths.get("mnistTrainingParameters.txt");
		System.out.println("loaded");

		//TODO: imageCpt
		double[] imageCpt = new double[10];
		/*imageCpt[0] = 5923.;
		imageCpt[1] = 6742.;
		imageCpt[2] = 5958.;
		imageCpt[3] = 6131.;
		imageCpt[4] = 5842.;
		imageCpt[5] = 5421.;
		imageCpt[6] = 5918.;
		imageCpt[7] = 6265.;
		imageCpt[8] = 5851.;
		imageCpt[9] = 5949.;
		 */


		int lineCpt = 0;
		for(String line : Files.readAllLines(p)) {
			System.out.println(lineCpt);
			if(lineCpt == 0){
				int partCpt =0;
				for (String part : line.split("\\s+")) {
					int temp = (Integer.valueOf(part) );
					imageCpt[partCpt] = temp;
					partCpt++;
				}
			}


			if(lineCpt - 2 == label){
				System.out.println("setting");
				int partCpt = 0;
				for (String part : line.split("\\s+")) {
					double temp = (Double.valueOf(part) / imageCpt[label]);
					if(temp == 0.){
						temp = 1. / 10000.;
					}
					if(temp != temp){
						System.out.println("temp = " + temp);
					}
					if(temp >= 1.){
						System.out.println("pouet");

					}
					double bias = Math.log(temp / (1. - temp));
					//System.out.println("bias = " + bias);
					if(bias != bias){
						System.out.println("temp = " + temp);
					}
					this.layers[0][partCpt].setBias(bias);
					partCpt++;
				}

			}
			lineCpt++;
		}


	}

	public void visualizeFilters(){

		for(int i=0; i < this.layers[1].length; i++){

			int[] image1DFilter = new int[this.layers[0].length];

			/*For each filter we put one output 
			 * node to 1 the others to 0
			 */

			for(int j=0; j<this.layers[1].length; j++){
				if (j==i){
					this.layers[1][j].setState(1);
				}
				else{
					this.layers[1][j].setState(0);
				}
			}

			/*We update the visible layer given the distribution
			 * of the hidden layer we set and store it in image1DFilter
			 * 
			 */
			for(int k = 0; k < this.layers[0].length; k++){
				double x = this.layers[0][i].getBias();
				for(int j = 0; j < this.layers[(0 + 1) % 2].length; j++){
					x += this.connections[k][j]*this.layers[1][j].getState();
				}
				image1DFilter[k]=(int) (Sigmoid.getINSTANCE().apply(x)*255.0);
			}

			int [][]image2DFilter = Tools.image1Dto2D(image1DFilter, 28, 28);

			OutputWeights output = new OutputWeights(image2DFilter);
			String date = "_" + LocalDateTime.now();
			date = date.substring(1, 17);
			date = date.replace(':', '-');
			date = date.replace('T', '_');
			
			Path path = Paths.get("RBM_Filters", date + "_filter_"  + i + ".bmp");
			try {
				output.toBmp(path);
				System.out.println("Images Crées");
			} catch (java.io.IOException e) {
			
				System.out.println("Exception");
				e.printStackTrace();
			}
			
		
		}

	}
}