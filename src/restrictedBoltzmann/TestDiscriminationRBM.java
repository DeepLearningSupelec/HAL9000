package restrictedBoltzmann;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import mnistReader.MnistManager;

public class TestDiscriminationRBM {
	/*
	 * This class purpose is to train one RBM for each class 
	 * of the sample (here: 10 figures)
	 * 
	 * A RBM is only trained with its class' examples
	 * 
	 */

	public static void main(String[] args) throws IOException {
		
		
		int[] inputData = {784, 36};
		double biasWide = 0;
		double weightWide = 0.02;
		
		String date = "_" + LocalDateTime.now();
		date = date.substring(0, 20);
		date = date.replace(':', '-');
		System.out.println(date);
		
		
		RestrictedBoltzmannMachine[] discriminationRbm = new RestrictedBoltzmannMachine[10];
		
		for(int i = 0; i < 10; i++){
			discriminationRbm[i] = new RestrictedBoltzmannMachine(inputData, weightWide, biasWide);
		}
		
		
		MnistManager learningManager = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		MnistManager testManager = new MnistManager("src/t10k-images.idx3-ubyte","src/t10k-labels.idx1-ubyte");
		
		
		//OutputData output = new OutputData(new ArrayList<Integer>(), new ArrayList<Double>(), new ArrayList<Double>());
		//Path p = Paths.get(/*System.getProperty("user.home"),*/"RBM_EnergyData", "boltzmannEnergy" + date + ".csv");
		//output.toCSV(p);
		
		double[] image1D;
		
	
		for(int i = 0; i < 300000; i++){
			learningManager.setCurrent((i % 60000) + 1);
			image1D = learningManager.readImage1D();
			
			discriminationRbm[learningManager.readLabel()].unsupervisedLearning(3, image1D);


			
			if(i%1000 == 0){
				
				
				/*
				 * 
				 * Test on each RBM ?
				 * 
				 * 
				 */
			}
			System.out.println(i);
		}
		
		
		testManager.setCurrent(1);
		System.out.println("Current label : " + testManager.readLabel());
		image1D = testManager.readImage1D();
		
		for(int i = 1; i < 10; i++){
			discriminationRbm[i].setBinaryInputs(image1D);
			discriminationRbm[i].constrastiveDivergence(2);
			System.out.println(i + " RBM energy : " + discriminationRbm[i].getEnergy());
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
