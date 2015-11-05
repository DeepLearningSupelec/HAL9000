import java.util.ArrayList;

public abstract class AbstractNeuron {

	//Attributes
	
	protected double output;
	
	protected double bias;
	
	private double neuronDiff;
	
	protected ActivationFunction activationFunction;
	
	protected ArrayList<Synapse> inputSynapses;

	protected ArrayList<Synapse> outputSynapses;
	
	protected boolean fired;
	
	//Constructor
	
	public AbstractNeuron(){
		this.output = 0;
		this.neuronDiff = 0;
		this.inputSynapses = new ArrayList<Synapse>();
		this.outputSynapses = new ArrayList<Synapse>();
		this.activationFunction = Sigmoid.getINSTANCE();
		this.fired = false;
	}
	
	public AbstractNeuron(ActivationFunction f){
		this.output = 0;
		this.neuronDiff = 0;
		this.inputSynapses = new ArrayList<Synapse>();
		this.outputSynapses = new ArrayList<Synapse>();
		this.activationFunction = f;
		this.fired = false;
	}
	
	
	
	
	
	//Methods
	
	abstract public void fire();
	
	public void setUnfired(){
		this.fired = false;
	}
	
	public double getOutput(){
		return this.output;
	}
	
	public void setNeuronDiff(double x){
		this.neuronDiff = x;
	}
	
	public double getNeuronDiff(){
		return this.neuronDiff;
	}
	
	public ActivationFunction getActivationFunction(){
		return this.activationFunction;
	}
	
	public void addInputSynapse(Synapse s){
		this.inputSynapses.add(s);
	}
	
	public void addOutputSynapse(Synapse s){
		this.outputSynapses.add(s);
	}
	
	public ArrayList<Synapse> getInputSynapses(){
		return this.inputSynapses;
	}
	
	public ArrayList<Synapse> getOutputSynapses(){
		return this.outputSynapses;
	}
	
	
}
