public class Sigmoid extends ActivationFunction {
	
	/* Attributes */
	Double lambda;
	
	
	/* Constructor */
	
	//Default Constructor
	public Sigmoid() {
		
		super(
				x -> 1 / (1 + Math.exp(x)),
				x -> x * (1-x)
		);
		this.lambda = 1.;
		
	}
	
	//Custom Constructor : you can use any value for lambda
	public Sigmoid(Double lambda) {
		
		super(
				x -> 1 / (1 + Math.exp(-lambda*x)),
				x -> lambda * x * (1-x)
		);
		this.lambda = lambda;
		
	}

}
