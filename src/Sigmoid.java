public class Sigmoid extends ActivationFunction {
	
	/* Attributes */
	Double lambda;
	
	
	/* Constructor */
	
	//Default Constructor
	public Sigmoid() {
		
		this.lambda = 1.;
		this.activationFunction = x -> 1 / (1 + Math.exp(x)) ;
		this.derivativeFunction = x -> lambda * x * (1-x) ;
		
	}
	
	//Custom Constructor : you can use any value for lambda
	public Sigmoid(Double lambda) {
		
		this.lambda = lambda;
		this.activationFunction = x -> 1 / (1 + Math.exp(-lambda*x)) ;
		this.derivativeFunction = x -> lambda * x * (1-x) ;
		
	}

}
