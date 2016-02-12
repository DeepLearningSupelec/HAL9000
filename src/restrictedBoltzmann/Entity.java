package restrictedBoltzmann;

public class Entity {
	
	/**
     * <p> This class represents a single Boltzmann neuron.</p>
     * <p>Its characteristics are:
     * <ul>
     * 	<li>
     * 		Its state : it represents the <b>output</b> of the neuron. It can take any integer value.
     * 	</li>
     * 	<li>
     * 		Its <b>bias</b>. It can take any float value.
     * 	</li>
     * 	<li>
     * 		Its <b>id</b>. It's an integer that can not be mofified.
     * 	</li>
     * </ul>
     * <p>
     * 
     * @see RestrictedBoltzmannMachine
     * 
     */
	
	
	//Attributes
	
	protected int state;
	protected double entityDiff;
	private double bias;
	protected boolean fired;
	private int id;
	
	
	//Constructor
	
	/**
     * <b>Entity constructor.</b>
     * <p>
     * 	The state and biais are set to 0.
     * </p>
     * 
     * @param id
     *            The id of the created neuron
     * 
     * @see Entity#id
     */
	public Entity(int id){
		this.state = 0;
		this.bias = 0;
		this.id = id;
		this.entityDiff = 0;
	}
	
	/**
     * <b>Entity constructor.</b>
     * <p>
     * 	The state is set to 0.
     * </p>
     * 
     * @param id
     *            The id of the created neuron
     * @param biais
     *            The biais of the created neuron
     * 
     * @see Entity#id
     * @see Entity#biais
     */
	public Entity(int id, double bias){
		this.state = 0;
		this.bias = bias;
		this.id = id;
		this.entityDiff = 0;
	}
	
	
	//Methods
	
	/**
     * <b>Allows to get the value of the state</b>
     * <p>
     * 	The state and biais are set to 0.
     * </p>
     * 
     * @see Entity#state
     */
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
	
	public void setEntityDiff(double e){
		this.entityDiff = e;
	}
	
	public double getEntityDiff(){
		return this.entityDiff;
	}
	
	
}





