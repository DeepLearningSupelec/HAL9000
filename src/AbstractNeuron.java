import java.util.ArrayList;

public abstract class AbstractNeuron {

	//Attributes
	
	protected double output;
	
	private double neuronDiff;
	
	private ActivationFunction activationFunction;
	
	private ArrayList<Synapse> inputSynapses;

	private ArrayList<Synapse> outputSynapses;
	
	
	//Constructor
	
	public void AbstractNeuron(){
		this.output = 0;
		this.neuronDiff = 0;
		this.inputSynapses = new ArrayList<Synapse>();
		this.outputSynapses = new ArrayList<Synapse>();
		this.activationFunction = new Sigmoid();
		/*
		 * 
		 * Attention, remplacer ce constructeur pour garder une seule 
		 * instance de Sigmoid !!
		 * 
		 * 
		 */
	}
	
	public void AbstractNeuron(ActivationFunction f){
		this.output = 0;
		this.neuronDiff = 0;
		this.inputSynapses = new ArrayList<Synapse>();
		this.outputSynapses = new ArrayList<Synapse>();
		this.activationFunction = f;
	}
	
	
	
	
	
	//Methods
	
	abstract public void fire();
	
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
