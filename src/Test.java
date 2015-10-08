
public class Test {

	public static void main(String[] args) {
		
		
		/* Test Sigmoid */
		Sigmoid f1 = new Sigmoid();
		Sigmoid f2 = new Sigmoid(2.);
		System.out.println(f1.apply(1.));
		System.out.println(f2.apply(1.));
		System.out.println(f1.applyDerivative(2.));
		System.out.println(f2.applyDerivative(2.));

	}

}
