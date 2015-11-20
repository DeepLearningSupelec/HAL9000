
public abstract class ActiveNeuron extends AbstractNeuron {

	
	//Attributes
	
	protected double bias;
	
	protected double biasDiff;
	
	protected double intermediateValue;
	
	
	//Constructor
	
	public ActiveNeuron(){
		super();
		this.bias = 0;
		this.biasDiff = 0;
		
	}
	
	public ActiveNeuron(ActivationFunction f){
		super(f);
		this.bias = 0;
		this.biasDiff = 0;
	}
	
	
	
	
	//Methods
	
	public void setBias(double x){
		this.bias = x;
	}
	
	public double getBias(){
		return this.bias;
	}
	
	public void setBiasDiff(double x){
		this.biasDiff = x;
	}
	
	public double getBiasDiff(){
		return this.biasDiff;
	}
	
	public double getIntermediateValue(){
		return this.intermediateValue;
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
		this.intermediateValue = scalarProduct;
		//System.out.println("scalar product: " + scalarProduct);
		this.output = this.activationFunction.apply(scalarProduct);
		//System.out.println("output: " + this.output);

		this.fired = true;
		return;
	}
	
}
