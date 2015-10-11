import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class NeuralNetwork {

	//Attributes
	
	protected ArrayList<AbstractNeuron> inputNeurons;
	protected ArrayList<AbstractNeuron> intermediateNeurons;
	protected ArrayList<AbstractNeuron> outputNeurons;
	protected ArrayList<Synapse> synapses;
	
	
	
	
	//Constructor
	
	public NeuralNetwork(String inputFilePath) throws IOException{
		
		this.inputNeurons = new ArrayList<AbstractNeuron>();
		this.intermediateNeurons = new ArrayList<AbstractNeuron>();
		this.outputNeurons = new ArrayList<AbstractNeuron>();
		this.synapses = new ArrayList<Synapse>();
		
		
		/*
		inputFile format:
		[
		A B C D E
		x0 y0 
		x1 y1
		x2 y2
		.
		.
		.
		]
		
		A : neuronsNumber;
		B : inputNeuronsNumber;
		C : intermediateNeuronsNumber;
		D : outputNeuronsNumber;
		E : synapsesNumber;
		
		xi yi : synapse between neuron number xi and neuron number yi
		
		*/
		
		
		int[] informations = new int[5];
		/*
		 * informations:
		 * 
		 * 0 : neuronsNumber;
		 * 1 : inputNeuronsNumber;
		 * 2 : intermediateNeuronsNumber;
		 * 3 : outputNeuronsNumber;
		 * 4 : synapsesNumber;
		 */
		
		int lineCpt = 0; // line indicator
		
		for (String line : Files.readAllLines(Paths.get(inputFilePath))) {
		
			if(lineCpt ==0){
				int partCpt = 0;
				for (String part : line.split("\\s+")) {
			        int i = Integer.valueOf(part);
			        informations[partCpt] = i;
			        partCpt++;
			    }
				
				//Neurons creation
				
				for(int i = 0; i < informations[1]; i++){
					this.inputNeurons.add(new InputNeuron());
				}
				for(int i = 0; i < informations[2]; i++){
					this.intermediateNeurons.add(new InputNeuron());
				}
				for(int i =0; i < informations[3]; i++){
					this.outputNeurons.add(new InputNeuron());
				}
				
				
			}
			
			int partCpt = 0;
			int[] connectedNeurons = new int[2];
			
		    for (String part : line.split("\\s+")) {
		    	if(partCpt < 2){
			        Integer i = Integer.valueOf(part);
			        connectedNeurons[partCpt] = i;
			        
		    	}
		    }
		    
		    //Synapse creation
		    
		    AbstractNeuron beginNeuron;
		    AbstractNeuron endNeuron;
		    
	    	if(connectedNeurons[0] < informations[1]){ 												// InputNeuron case
	    		beginNeuron = this.inputNeurons.get(connectedNeurons[0]);
	    	} else if(connectedNeurons[0] - informations[1] < informations[2]){ 					// IntermediateNeuron case
	    		beginNeuron = this.intermediateNeurons.get(connectedNeurons[0] - informations[1]);
	    	} else { 																				// OutputNeuron case
	    		beginNeuron = this.outputNeurons.get(connectedNeurons[0] - informations[2]);
	    	}

	    	if(connectedNeurons[1] < informations[1]){ 												// InputNeuron case
	    		endNeuron = this.inputNeurons.get(connectedNeurons[1]);
	    	} else if(connectedNeurons[1] - informations[1] < informations[2]){ 					// IntermediateNeuron case
	    		endNeuron = this.intermediateNeurons.get(connectedNeurons[1] - informations[1]);
	    	} else {																				// OutputNeuron case
	    		endNeuron = this.outputNeurons.get(connectedNeurons[1] - informations[2]);
	    	}
		    
	    	
		    this.synapses.add(new Synapse(beginNeuron, endNeuron));
		    
		    lineCpt++;
		}
		
		
	}
	
	
	
	
	
	//Methods
	
	abstract public void fire();
	abstract public double getOutputs();
	abstract public void setInputs(double x);
	abstract public void linkNetwork();
}
