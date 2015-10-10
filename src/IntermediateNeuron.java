
public class IntermediateNeuron extends AbstractNeuron {

	//Attributes
	
	private double bias;
	
	private double biasDiff;
	
	
	
	
	//Constructor
	
	public IntermediateNeuron(){
		super();
		this.bias = 0.1;
		this.biasDiff = 0;
		
	}
	
	public IntermediateNeuron(ActivationFunction f){
		super(f);
		this.bias = 0.1;
		this.biasDiff = 0;
	}
	
	
	
	
	//Methods
	
	public void setBias(double x){
		this.bias = x;
	}
	
	public void setBiasDiff(double x){
		this.biasDiff = x;
	}

	@Override
	public void fire() {
		/*double scalarProduct = -this.bias;
		for (int i = 0; i < this.inputSynapses.size(); i++) {
			Synapse s = this.inputSynapses.get(i);
			s.getInputNeuron().fire();
			scalarProduct += s.getWeight() * s.getInputNeuron().getOutput();
			
		}*/
		
	}


}
