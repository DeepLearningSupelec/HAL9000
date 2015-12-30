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
	
	double weightWide;
	
	double biasWide;
	
	
	//Constructor
	
	public DeepBeliefNetwork(int[] inputData, double weightWide, double biasWide){
		/*
		 * inputData : {layer0UnitsNumber, layer1UnitsNumber, layer2UnitsNumber, ... }
		 * 
		 */
		this.weightWide = weightWide;
		this.biasWide = biasWide;
		Random rand = new Random();
		int layerNumber = inputData.length;
		this.layers = new Entity[layerNumber][];
		for(int i = 0; i < layerNumber; i++){
			this.layers[i] = new Entity[inputData[i]];
			for(int j = 0; j < inputData[i]; j++){
				this.layers[i][j] = new Entity(j, (rand.nextDouble()- 0.5)*this.biasWide);
			}
		}
		
		this.machines = new RestrictedBoltzmannMachine[layerNumber - 1];
		for(int i = 0; i < layerNumber - 1; i++){
			this.machines[i] = new RestrictedBoltzmannMachine(this.layers[i], this.layers[i + 1], weightWide);
		}
		
		
	}
	
	
	
	
	
	
	
}
