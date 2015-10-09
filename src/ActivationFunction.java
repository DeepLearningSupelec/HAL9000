import java.util.function.UnaryOperator;

public class ActivationFunction {
	
	/* Attributes */
	UnaryOperator <Double> activationFunction;
	UnaryOperator <Double> derivativeFunction;
	
	
	/* Constructors */
	//Allows the creation of any kind of activation function
	public ActivationFunction
	(UnaryOperator <Double> activationFunction, UnaryOperator <Double> derivativeFunction) {
		
		this.activationFunction = activationFunction;
		this.derivativeFunction = derivativeFunction;
		
	}
	
	
	/* Methods */
	public Double apply(Double x) {
		return activationFunction.apply(x);
	}
	
	public Double applyDerivative(Double x) {
		return derivativeFunction.apply(x);
	}
}
