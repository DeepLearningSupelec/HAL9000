public class Sigmoid extends ActivationFunction {
	
	/* Attributes */
	Double lambda;
	
	
	/* Constructor */
	
	//Default Constructor
	public Sigmoid() {
		this.lambda = 1.;
	}
	
	//Custom Constructor : you can use any value for lambda
	public Sigmoid(Double lambda) {
		this.lambda = lambda;
	}

	@Override
	public Double apply(Double x) {
		return 1/(1+Math.exp(lambda*x));
	}

	@Override
	public Double applyDerivative(Double x) {
		return lambda*x*(1-x);
	}

}
