package restrictedBoltzmann;

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
 */
public class Entity {
	
	
	//Attributes
	
	protected int state;
	protected double entityDiff;
	private double bias;
	protected boolean fired;
	private int id;
	
	
	//Constructor
	
	/**
     * <p>Entity constructor.</p>
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
     * <p>Entity constructor.<p>
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
     * <p>Allows to get the value of the state</p>
     * @see Entity#state
     */
	public int getState(){
		return this.state;
	}
	
	/**
     * <p>Allows to set the value of the state to s</p>
     * @param s
     * 		The new value of the neuron state
     * @see Entity#state
     */
	public void setState(int s){
		this.state = s;
	}
	
	/**
     * <p>Allows to get the id of the neuron</p>
     * @see Entity#neuron
     */
	public int getId(){
		return this.id;
	}
	
	/**
     * <p>Allows to set the value of the bias to b</p>
     * @param b
     * 		The new value of the biais
     * @see Entity#biais
     */
	public void setBias(double b){
		this.bias = b;
	}
	
	/**
     * <p>Allows to get the value of the bias</p>
     * @see Entity#biais
     */
	public double getBias(){
		return this.bias;
	}
	
	/**
     * <p>???</p>
     * @param e
     * 		???
     * @see Entity#entityDiff
     */
	public void setEntityDiff(double e){
		this.entityDiff = e;
	}
	
	public double getEntityDiff(){
		return this.entityDiff;
	}
	
}





