import java.util.ArrayList;

public class InputNeuron extends AbstractNeuron {

	//Attributes
	
	private double input;
	
	
	
	//Constructor
	public InputNeuron(){
		super();
		this.input = 0;
	}
	
	/*
	public void InputNeuron(ActivationFunction f){
		super.AbstractNeuron(f);
		this.input = 0;
	}
	
	Pas utile par déf d'un InputNeuron ?
	*/
	
	
	//Methods
	
	public void setInput(double x){
		this.input = x;
	}

	@Override
	public void fire() {
		this.output = this.input;
	}
	
	public double getOutput(){
		return this.output;
	}

	
}
