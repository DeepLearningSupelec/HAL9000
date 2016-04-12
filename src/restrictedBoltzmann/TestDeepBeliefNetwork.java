package restrictedBoltzmann;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import mnistReader.MnistManager;

public class TestDeepBeliefNetwork {

	public static void main(String[] args) throws IOException {
		

	



		int[] inputData = {784, 90, 36, 10};
		double biasWide = 0;
		double weightWide = 0.035;
		double learningRate = 0.051;
		
		String date = "_" + LocalDateTime.now();
		date = date.substring(0, 20);
		date = date.replace(':', '-');
		System.out.println(date);
		
		
		
		DeepBeliefNetwork dBN = new DeepBeliefNetwork(inputData, weightWide, biasWide, learningRate);
		dBN.machines[0].setMnistParameters();
		
		
		
		int gibbsSteps = 3;
		
		//MnistManager learningManager = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		MnistManager testManager = new MnistManager("src/t10k-images.idx3-ubyte","src/t10k-labels.idx1-ubyte");
		
		BatchManager batchManager = new BatchManager();
		
		OutputData output = new OutputData(new ArrayList<Integer>(), new ArrayList<Double>(), new ArrayList<Double>());
		Path p = Paths.get(/*System.getProperty("user.home"),*/"DBN_Erros", "Test" /*+ gibbsSteps +  "GibbsSteps"*/  + date + ".csv");
		output.toCSV(p);
		
		double[] image1D;
		double trainingErrors = 0.;
		double[] visibleVector;
		
		double[] errorRates = new double[10];
		double totalErrors = 0.;
		
		
		//unsupervised learning
	
		for(int i = 0; i < 300000; i++){

			batchManager.setCurrent((i % batchManager.dataSize) + 1/*tempLabel*/);
			image1D = batchManager.readImage1D();
			
			dBN.singleUnsupervisedLearning(gibbsSteps * 2, image1D, true);
			//discriminationRbm[batchManager.readLabel()].unsupervisedLearning(gibbsSteps * 2, image1D);
			
			/*if(i%10 == 0){
				// applying batch cumulative gradients
				
				for(int k = 0; k < 10; k++){
					dBN.applyLearningGradients();
				}
				
			}*/
			
			if(i%1000 == 0){
				// Somme sur l'ensemble test
				double testErrors = 0.;
				/*
				for(int j = 1; j < 1000; j++){
					testManager.setCurrent(j);
					image1D = testManager.readImage1D();
					
					double minEnergy = 0.;
					int label = 0;
					for(int k = 0; k < 10; k++){
						visibleVector = testManager.readImage1D();
						discriminationRbm[k].setBinaryInputs(visibleVector);
						double temp = discriminationRbm[k].getFreeEnergy();
						if(temp < minEnergy){
							label = k;
							minEnergy = temp;
						}
					}
					if(label != testManager.readLabel()){
						testErrors ++;
					}
					
				}
				*/

				output.addData(testErrors/1000, trainingErrors/1000, i/1000, p);
				System.out.println(trainingErrors/1000.);
				trainingErrors = 0.;
			}
			if(i%100 == 0){
				System.out.println(i);
			}
		}
		
		//supervised learning
	
		for(int i = 0; i < 300000; i++){

			batchManager.setCurrent((i % batchManager.dataSize) + 1/*tempLabel*/);
			image1D = batchManager.readImage1D();
			
			dBN.singleSupervisedLearning(image1D, batchManager.readLabel());

			if(i%10 == 0){
				// applying batch cumulative gradients
				
				for(int k = 0; k < 10; k++){
					dBN.applyLearningGradients();
				}
				
			}
			
			if(i%1000 == 0){
				// Somme sur l'ensemble test
				double testErrors = 0.;
				
				for(int j = 1; j < 1000; j++){
					testManager.setCurrent(j);
					image1D = testManager.readImage1D();
					dBN.setInputs(image1D, 255.);
					dBN.fire();
					if(dBN.getMnistMostProbableLabel() != testManager.readLabel()){
						testErrors ++;
					}
					
				}
				

				output.addData(testErrors/1000, trainingErrors/1000, i/1000, p);
				System.out.println(trainingErrors/1000.);
				trainingErrors = 0.;
			}
			if(i%100 == 0){
				System.out.println(i);
			}
		}
	
		
		
		/*
		testManager.setCurrent(1);
		System.out.println("Current label : " + testManager.readLabel());
		image1D = testManager.readImage1D();
		
		for(int i = 1; i < 10; i++){
			discriminationRbm[i].setBinaryInputs(image1D);
			discriminationRbm[i].constrastiveDivergence(2);
			System.out.println(i + " RBM energy : " + discriminationRbm[i].getEnergy());
			
		}
		*/
		
		/*
		
		//Test image one at a time
		
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		int selectLabel = 1;
		while(selectLabel != 0){
			testManager.setCurrent(selectLabel);
			System.out.println("Current label : " + testManager.readLabel());
			image1D = testManager.readImage1D();
			for(int i = 0; i < 10; i++){
				visibleVector = testManager.readImage1D();
				discriminationRbm[i].setBinaryInputs(visibleVector);
				System.out.println(i + " RBM free energy : " + discriminationRbm[i].getFreeEnergy());
				
			}
			
			System.out.println("Enter a testImage number (0 to exit): ");
			selectLabel = reader.nextInt();
		}
		
		*/
		
		/*
		// Errors repartition 
		
		String[] labelLines = new String[10];
		for(int i = 0; i < 10; i++){
			labelLines[i] = "label " + i + " percentage: ";
		}
		
		
		for(int i = 0; i < 10; i++){
			labelLines[i] = labelLines[i] + (errorRates[i] / totalErrors);
			
			
		}
		
		List<String> lines = Arrays.asList(
				labelLines[0],labelLines[1],
				labelLines[2],labelLines[3],
				labelLines[4],labelLines[5],
				labelLines[6],labelLines[7],
				labelLines[8],labelLines[9]);
		Path file = Paths.get("RBM_discrimationErrorRepartition", "repartitionError" + gibbsSteps +  "GibbsSteps"  + date + ".txt");
		//Files.write(file, lines, Charset.forName("UTF-8"));
		
		double tempd = 0.;
		for(int i = 0; i <10; i++){
			tempd+= errorRates[i];
		}
		System.out.println("error sum " + tempd + " on " + totalErrors + " errors");
			
		*/
		
		

	}

}
