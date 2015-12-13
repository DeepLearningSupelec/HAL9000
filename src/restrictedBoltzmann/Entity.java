package restrictedBoltzmann;
import java.util.ArrayList;

public class Entity {
	


	//Attributes
	
	protected double output;
	
	private double neuronDiff;
	
	//protected ActivationFunction activationFunction;
	
	protected boolean fired;
	
	//Constructor
	
	public Entity(){
		this.output = 0;
		this.neuronDiff = 0;
		//this.activationFunction = Sigmoid.getINSTANCE();
		this.fired = false;
	}
	
	
	
	//Methods
	
	public void fire(){
		
	};
	
	public void setUnfired(){
		this.fired = false;
	}
	
	public double getOutput(){
		return this.output;
	}
	
	/*
	public void setNeuronDiff(double x){
		this.neuronDiff = x;
	}
	
	public double getNeuronDiff(){
		return this.neuronDiff;
	}*/
	
	/*public void addInputSynapse(Synapse s){
		this.inputSynapses.add(s);
	}
	
	public void addOutputSynapse(Synapse s){
		this.outputSynapses.add(s);
	}*/
	
	/*public ArrayList<Synapse> getInputSynapses(){
		return this.inputSynapses;
	}
	
	public ArrayList<Synapse> getOutputSynapses(){
		return this.outputSynapses;
	}*/
	
	
}





