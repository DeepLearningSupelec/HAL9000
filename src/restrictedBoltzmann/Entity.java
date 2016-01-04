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
	}
	
	public Entity(int id, double bias){
		this.state = 0;
		this.bias = bias;
		this.id = id;
		
	}
	
	
	//Methods
	
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
	
	
	
}





