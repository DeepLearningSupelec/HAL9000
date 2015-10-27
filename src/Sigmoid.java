public class Sigmoid extends ActivationFunction {
	
	/* Constructor */
	
	//Default Constructor
	private Sigmoid() {}
	
	
	/* Methods */
	@Override
	public Double apply(Double x) {
		return 1/(1+Math.exp(-x));
	}

	@Override
	public Double applyDerivative(Double x) {
		return x*(1-x);
	}
	
	
	/*Gestion du singleton*/
	
	//Instance UNIQUE préinitialisée
	private static Sigmoid INSTANCE = new Sigmoid();
	
	//Récupération de l'instance
	public static Sigmoid getINSTANCE() {
		return INSTANCE;
	}
	
	//Changement d'instance
	public static void setINSTANCE(Sigmoid iNSTANCE) {
		INSTANCE = iNSTANCE;
	}

}
