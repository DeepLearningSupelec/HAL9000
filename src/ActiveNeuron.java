
public abstract class ActiveNeuron extends AbstractNeuron {

	
	//Attributes
	
	protected double bias;
	
	protected double biasDiff;
	
	
	
	
	//Constructor
	
	public ActiveNeuron(){
		super();
		this.bias = 0.1;
		this.biasDiff = 0;
		
	}
	
	public ActiveNeuron(ActivationFunction f){
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
			if(s.getWeight() != s.getWeight()){System.out.println("weight NaN");}
			scalarProduct += s.getWeight() * s.getInputNeuron().getOutput();
		}
		System.out.println("scalar product: " + scalarProduct);
		if(scalarProduct != scalarProduct){System.out.println("NaN !!!");}
		this.output = this.activationFunction.apply(scalarProduct);
		System.out.println("output: " + this.output);

		this.fired = true;
		return;
	}
	
}
