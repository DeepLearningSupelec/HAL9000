
public class IntermediateNeuron extends AbstractNeuron {

	//Attributes
	
	private double bias;
	private double biasDiff;
	
	private Synapse[] inputSynapses;
	private Synapse[] outputSynapses;
	
	
	//Methods
	
	public Synapse[] getInputSynapses(){
		return this.inputSynapses.clone();
	}
	
	public Synapse[] getOutputSynapses(){
		return this.outputSynapses.clone();
	}
	
	public void setBias(double x){
		
	}
	
}
