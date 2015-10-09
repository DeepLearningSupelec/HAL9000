import java.io.IOException;;

public class Test {

	private static Double d[];

	public static void main(String[] args) throws IOException {
		
		
		/* Test Sigmoid */
		/*Sigmoid f1 = new Sigmoid();
		Sigmoid f2 = new Sigmoid(2.);
		System.out.println(f1.apply(1.));
		System.out.println(f2.apply(1.));
		System.out.println(f1.applyDerivative(2.));
		System.out.println(f2.applyDerivative(2.));*/
		
		
		/* Test ActivationFunction*/
		/*ActivationFunction f = new ActivationFunction(x->x*x, x->2*x);
		System.out.println(f.apply(3.));
		System.out.println(f.applyDerivative(3.));*/
		
		
		/* Test Analytics */
		Analytics test = new Analytics("C:\\Users\\Fabien\\Desktop\\","essai","csv");
		for(int i=0; i<11; i++){
			d[i] = (double) i;
		};
		test.newLine("bite", d);
		test.close();

	}

}
