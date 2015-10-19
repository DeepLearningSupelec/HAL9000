
public class Synapse {
	
	//Attributes
	
	private double weight;
	
	private double weightDiff;
	
	private AbstractNeuron inputNeuron;
	
	private AbstractNeuron outputNeuron;
	
	
	
	//Constructor
	

	public Synapse(AbstractNeuron inputNeuron, AbstractNeuron outputNeuron){
		this.weight = 0.1;
		this.weightDiff = 0;
		this.inputNeuron = inputNeuron;
		this.outputNeuron = outputNeuron;
		
		inputNeuron.addOutputSynapse(this);
		outputNeuron.addInputSynapse(this);
		
	}
	
	public Synapse(double w, AbstractNeuron inputNeuron, AbstractNeuron outputNeuron){
		this.weight = w;
		this.weightDiff = 0;
		this.inputNeuron = inputNeuron;
		this.outputNeuron = outputNeuron;
		
		inputNeuron.addOutputSynapse(this);
		outputNeuron.addInputSynapse(this);
	}
	
	
	//Methods
	
	public double getWeight(){
		return this.weight;
	}
	
	public void setWeight(double w){
		this.weight = w;
	}
	
	public void setWeightDiff(double w){
		this.weightDiff = w;
	}
	
	public AbstractNeuron getInputNeuron(){
		return this.inputNeuron;
	}
	
	public AbstractNeuron getOutputNeuron(){
		return this.outputNeuron;
	}
	
	public double getWeightDiff(){
		return this.weightDiff;
	}
	
	public void getWeightDiff(double d){
		this.weightDiff = d;
	}
	
	
	
}
