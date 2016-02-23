package restrictedBoltzmann;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import mnistReader.MnistManager;

public class TestRBM {

	public static void main(String[] args) throws IOException {
		
		int[] inputData = {784, 36};
		double biasWide = 0;
		double weightWide = 0.02;
		
		String date = "_" + LocalDateTime.now();
		date = date.substring(0, 20);
		date = date.replace(':', '-');
		System.out.println(date);
		
		RestrictedBoltzmannMachine rbm = new RestrictedBoltzmannMachine(inputData, weightWide, biasWide);
		
		MnistManager m = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		MnistManager testManager = new MnistManager("src/t10k-images.idx3-ubyte","src/t10k-labels.idx1-ubyte");
		
		m.setCurrent(1);
		OutputData output = new OutputData(new ArrayList<Integer>(), new ArrayList<Double>(), new ArrayList<Double>());
		Path p = Paths.get(/*System.getProperty("user.home"),*/"RBM_EnergyData", "boltzmannEnergy" + date + ".csv");
		output.toCSV(p);
		double[] image1D = m.readImage1D();
		
		
		
		
		rbm.setBinaryInputs(image1D);
		int[] input1D = rbm.getBinaryInputs();
		for(int i = 0; i < 784; i++){
			System.out.println(input1D[i]);
		}
		
		//rbm.reachEquilibrium();
		//rbm.displayBinaryOutputs();
		
		//rbm.displayProbabilityOutputs();
		
		
		
		
		String adress = "Images_ppm//";
		String extension = ".ppm";
		int[][] image2D = Tools.image1Dto2D(input1D, 28, 28);
		MnistManager.writeImageToPpm(image2D, adress + "image1" + date + extension);
		double[] probabilityOutputs;
		double sumProbability = 0.0;
		double learningEnergy = 0.;
		for(int i = 0; i < 250000; i++){
			m.setCurrent((i % 60000) + 1);
			image1D = m.readImage1D();
	//		rbm.unsupervisedLearning(2, image1D);
	//		sumProbability += Math.pow(rbm.getLogProbabilityDerivativeSum(rbm.unsupervisedLearning(3, image1D)), 2);
			rbm.getLogProbabilityDerivativeSum(rbm.unsupervisedLearning(3, image1D));
			probabilityOutputs=rbm.getProbabilityOutputs();
			learningEnergy += rbm.getEnergy();
			
			System.out.println(i);
			
			if(i%1000 == 0){
				// Somme sur l'ensemble test
				double testEnergy = 0.;
				
				for(int j = 1; j < 1000; j++){
					testManager.setCurrent(j);
					image1D = testManager.readImage1D();
					rbm.setBinaryInputs(image1D);
					rbm.constrastiveDivergence(3);
					testEnergy += rbm.getEnergy();
					
				}
				m.setCurrent(1);
				image1D = m.readImage1D(); 
				//double sum = rbm.getLogProbabilityDerivativeSum(rbm.unsupervisedLearning(2, image1D));
				/* 
				 * 
				 * 
				 * int[][] image2Dexit = Tools.image1Dto2D(rbm.getBinaryOutputs(), 6, 6);
				 * MnistManager.writeImageToPpm(image2Dexit, adress + "imageExitRBMAfter" + (i/1000) + "Epochs" + date + extension);*/
				//output.addData(sumProbability,i/1000, p);
				sumProbability = 0.0;
				output.addData(testEnergy, learningEnergy, i/1000, p);
				learningEnergy = 0.;
			}
			
			
			
			
			
			
		}
		m.setCurrent(1);
		image1D = m.readImage1D();
		rbm.setBinaryInputs(image1D);
		rbm.constrastiveDivergence(2);
		
		// //rbm.displayBinaryOutputs();
		
		
		
		
		
		/*int[][] image2Dexit = Tools.image1Dto2D(rbm.getBinaryOutputs(), 28, 28);
		MnistManager.writeImageToPpm(image2Dexit, adress + "image1ExitRBM" + date + extension);*/
		
		//rbm.displayProbabilityOutputs();
		
		System.out.println("Done");
		
		/*MnistManager m = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		m.setCurrent(1);
		int[][] image = m.readImage();
		m.setCurrent(1);
		double[] image1D = m.readImage1D();
		String date = "_" + LocalDateTime.now();
		date = date.substring(0, 20);
		date = date.replace(':', '-');
		System.out.println(date);
		String adress = "Images_ppm//";
		String extension = ".ppm";
		MnistManager.writeImageToPpm(image, adress + "image0" + date + extension);
		int[][] image2D = Tools.image1Dto2D(image1D, 28, 28);
		MnistManager.writeImageToPpm(image2D, adress + "imageto2D" + date + extension);
		*/
		
	}

}
