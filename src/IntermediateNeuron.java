
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
		this.bias = x;
	}
	
	public void setBiasDiff(double x){
		this.biasDiff = x;
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getOutput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNeuronDiff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getNeuronDiff() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ActivationFunction getActivationFunction() {
		// TODO Auto-generated method stub
		return null;
	}
}
