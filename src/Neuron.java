
public abstract class Neuron extends AbstractNeuron {

	
	//Attributes
	
	protected double bias;
	
	protected double biasDiff;
	
	
	
	
	//Constructor
	
	public Neuron(){
		super();
		this.bias = 0.1;
		this.biasDiff = 0;
		
	}
	
	public Neuron(ActivationFunction f){
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

	public void fire() {
		if(this.fired){return;}
		double scalarProduct = -this.bias;
		for (int i = 0; i < this.inputSynapses.size(); i++) {
			Synapse s = this.inputSynapses.get(i);
			s.getInputNeuron().fire();
			scalarProduct += s.getWeight() * s.getInputNeuron().getOutput();
		}
		this.output = this.activationFunction.apply(scalarProduct);
		this.fired = true;
		return;
	}
	
}
