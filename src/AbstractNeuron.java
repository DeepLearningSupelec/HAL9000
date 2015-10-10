
public abstract class AbstractNeuron {

	//Attributes
	
	private double output;
	
	private double neuronDiff;
	
	//Methods
	
	abstract public void fire();
	abstract public void getOutput();
	abstract public void setNeuronDiff();
	abstract public double getNeuronDiff();
	abstract public ActivationFunction getActivationFunction();
	
	
	
	
}
