
public class OutputNeuron extends AbstractNeuron {

	
	//Attributes
	
	private double bias;
	
	private double biasDiff;
	

	
	
	//Constructor
	
	public void OutputNeuron(){
		super.AbstractNeuron();
		this.bias = 0.1;
		this.biasDiff = 0;
		
	}
	
	public void OutputNeuron(ActivationFunction f){
		super.AbstractNeuron(f);
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
	

	
	
	
	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}


}
