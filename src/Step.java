
public class Step extends ActivationFunction {

	@Override
	public Double apply(Double x) {
		return (double) ((x<0)?0:1);
	}

	@Override
	public Double applyDerivative(Double x) {
		return null;
	}
	
	/*Gestion du singleton*/
	
	//Instance UNIQUE préinitialisée
	private static Step INSTANCE = new Step();
	
	//Récupération de l'instance
	public static Step getINSTANCE() {
		return INSTANCE;
	}
	
	//Changement d'instance
	public static void setINSTANCE(Step iNSTANCE) {
		INSTANCE = iNSTANCE;
	}

}
