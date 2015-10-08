import java.util.function.UnaryOperator;

public abstract class ActivationFunction {
	
	/* Attributes */
	UnaryOperator <Double> activationFunction;
	UnaryOperator <Double> derivativeFunction;
	
	
	/* Methods */
	public Double apply(Double x) {
		return activationFunction.apply(x);
	}
	
	public Double applyDerivative(Double x) {
		return derivativeFunction.apply(x);
	}
}
