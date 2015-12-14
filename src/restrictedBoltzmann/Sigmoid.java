package restrictedBoltzmann;

public class Sigmoid {
	
	/* Constructor */
	
	//Default Constructor
	private Sigmoid() {}
	
	
	/* Methods */
	
	public Double apply(Double x) {
		return 1/(1+Math.exp(-x));
	}

	
	public Double applyDerivative(Double x) {
		return this.apply(x)*(1 - this.apply(x));
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
