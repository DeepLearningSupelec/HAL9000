package restrictedBoltzmann;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import mnistReader.MnistManager;
public class Test_Filters {

	/*This Class purpose is to train one RBM on
	 * many examples in order to visualize the filters
	 * to compare with the Fischer and Igel results
	 */

	public static void main(String[] args) throws IOException, ParseException {
	
		int[] inputData = {784, 3};
		double biasWide = 0.;
		double weightWide = 0.035;
		double learningRate = 0.005;

		String date = "_" + LocalDateTime.now();
		date = date.substring(0, 20);
		date = date.replace(':', '-');

		RestrictedBoltzmannMachine Rbm = new RestrictedBoltzmannMachine(inputData, weightWide, biasWide, learningRate);

		MnistManager m = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		MnistManager testManager = new MnistManager("src/t10k-images.idx3-ubyte","src/t10k-labels.idx1-ubyte");

		m.setCurrent(1);
		double[] image1D;
		double trainingErrors = 0.;
		double[] visibleVector;

		double[] probabilityOutputs;
		double sumProbability = 0.0;
		double learningEnergy = 0.;

		for(int i = 0; i < 1000; i++){
			m.setCurrent((i % 60000) + 1);
			image1D = m.readImage1D();
			Rbm.getLogProbabilityDerivativeSum(Rbm.unsupervisedLearning(2, image1D));
			Rbm.applyLearningGradients();
			probabilityOutputs=Rbm.getProbabilityOutputs();
			learningEnergy += Rbm.getEnergy();


			if(i%1000 == 0){

				m.setCurrent(1);
				image1D = m.readImage1D(); 
				sumProbability = 0.0;
				learningEnergy = 0.;
				System.out.println(i);
			}

		}
		
		Rbm.visualizeAllFilters();
		
		System.out.println("Done");


	}

}
