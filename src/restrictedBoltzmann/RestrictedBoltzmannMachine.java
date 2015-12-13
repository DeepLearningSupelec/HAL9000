package restrictedBoltzmann;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class RestrictedBoltzmannMachine {

	
	//Attributes
	
	Entity[][] layers;
	
	double energy;
	
	double weightWide;
	
	double biasWide;
	
	double[][] connections;
	
	//Constructor
	
	
	public RestrictedBoltzmannMachine(int[] inputData, double weightWide, double biasWide){
		this.weightWide = weightWide;
		this.biasWide = biasWide;
		Random rand = new Random();
		this.layers = new Entity[2][];
		for(int i = 0; i < 2; i++){
			this.layers[i] = new Entity[inputData[i]];
			for(int j = 0; j < inputData[i]; j++){
				this.layers[i][j] = new Entity((rand.nextDouble()- 0.5)*this.biasWide);
			}
		}
		this.connections = new double[inputData[0]][inputData[1]];
		for(int i = 0; i < this.connections.length; i++){
			for(int j = 0; j < this.connections[0].length; j++){
				this.connections[i][j] = (rand.nextDouble()- 0.5)*this.weightWide;
			}
		}
		
	}
	
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
			    this.layers[layer][entityNumber] = new Entity(bias);		
				
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
	
	public void fire(){
		
	}
	
	
	public double[] getOutputs(){
		return null;
	}
	
	public void setInputs (double[]x){
		
	}

	
	
}