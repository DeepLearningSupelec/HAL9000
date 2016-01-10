package restrictedBoltzmann;

public class TestRBM {

	public static void main(String[] args) {
		
		int[] inputData = {2, 2};
		double biasWide = 1;
		double weightWide = 0.7;
		
		RestrictedBoltzmannMachine rbm = new RestrictedBoltzmannMachine(inputData, weightWide, biasWide);
		
		int[] exemple = {0, 1};
		
		
		rbm.setBinaryInputs(exemple);
		rbm.reachEquilibrium();
		rbm.displayBinaryOutputs();
		
		rbm.displayProbabilityOutputs();
		//rbm.constrastiveDivergence(2);
		rbm.unsupervisedLearning(2, exemple);
		
		rbm.displayProbabilityOutputs();
		
		System.out.println("Done");
		

	}

}
