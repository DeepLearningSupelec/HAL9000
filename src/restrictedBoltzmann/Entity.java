package restrictedBoltzmann;
import java.util.ArrayList;

public class Entity {
	


	//Attributes
	
	protected int state;
	
	//private double neuronDiff;
	
	private double bias;
	
	//protected ActivationFunction activationFunction;
	
	protected boolean fired;
	
	private int id;
	
	
	//Constructor
	
	public Entity(int id){
		this.state = 0;
		this.bias = 0;
		this.id = id;
		//this.neuronDiff = 0;
		//this.activationFunction = Sigmoid.getINSTANCE();
		//this.fired = false;
	}
	
	public Entity(int id, double bias){
		this.state = 0;
		this.bias = bias;
		this.id = id;
		//this.neuronDiff = 0;
		//this.activationFunction = Sigmoid.getINSTANCE();
		//this.fired = false;
		
	}
	
	
	//Methods
	
	/*public void fire(){
		
	};
	
	public void setUnfired(){
		this.fired = false;
	}*/
	
	public int getState(){
		return this.state;
	}
	
	public void setState(int s){
		this.state = s;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setBias(double b){
		this.bias = b;
	}
	
	public double getBias(){
		return this.bias;
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





