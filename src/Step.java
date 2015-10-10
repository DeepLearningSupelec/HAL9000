
public class Step extends ActivationFunction {

	@Override
	public Double apply(Double x) {
		return (double) ((x<0)?0:1);
	}

	@Override
	public Double applyDerivative(Double x) {
		return null;
	}

}
