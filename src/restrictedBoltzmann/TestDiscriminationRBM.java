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
		double weightWide = 0.035;
		double learningRate = 0.051;
		
		String date = "_" + LocalDateTime.now();
		date = date.substring(0, 20);
		date = date.replace(':', '-');
		System.out.println("date " + date);
		
		
		RestrictedBoltzmannMachine[] discriminationRbm = new RestrictedBoltzmannMachine[10];
		
		for(int i = 0; i < 10; i++){
			discriminationRbm[i] = new RestrictedBoltzmannMachine(inputData, weightWide, biasWide, learningRate);
			discriminationRbm[i].setMnistLabelParameters(i);
		}
		
		int gibbsSteps = 3;
		
		//MnistManager learningManager = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		MnistManager testManager = new MnistManager("src/t10k-images.idx3-ubyte","src/t10k-labels.idx1-ubyte");
		
		BatchManager batchManager = new BatchManager();
		
		OutputData output = new OutputData(new ArrayList<Integer>(), new ArrayList<Double>(), new ArrayList<Double>());
		Path p = Paths.get(/*System.getProperty("user.home"),*/"RBM_EnergyData", "visibleEnergyTest" + gibbsSteps +  "GibbsSteps"  + date + ".csv");
		output.toCSV(p);
		
		double[] image1D;
		double trainingErrors = 0.;
		double[] visibleVector;
		
		double[] errorRates = new double[10];
		double totalErrors = 0.;
		
		
		
	
		for(int i = 0; i < 300000; i++){
			/*int tempInt = i % 10;
			int tempLabel = 0;
			switch (tempInt) {
			case 0: tempLabel = 2; break;
			case 1: tempLabel = 4; break;
			case 2: tempLabel = 6; break;
			case 3: tempLabel = 8; break;
			case 4: tempLabel = 3; break;
			case 5: tempLabel = 1; break;
			case 6: tempLabel = 14; break;
			case 7: tempLabel = 16; break;
			case 8: tempLabel = 18; break;
			case 9: tempLabel = 5; break;
			}*/
			
			
			
			batchManager.setCurrent((i % batchManager.dataSize) + 1/*tempLabel*/);
			image1D = batchManager.readImage1D();
			
			
			
			
			discriminationRbm[batchManager.readLabel()].unsupervisedLearning(gibbsSteps * 2, image1D);
			
			double min = 0.;
			int labl = 0;
			visibleVector = batchManager.readImage1D();
			for(int k = 0; k < 10; k++){
				discriminationRbm[k].setBinaryInputs(visibleVector);
				double temp = discriminationRbm[k].getFreeEnergy();
				if(temp < min){
					labl = k;
					min = temp;
				}
			}
			if(labl != batchManager.readLabel()){
				trainingErrors ++;
				totalErrors++;
				errorRates[batchManager.readLabel()]++;
				//System.out.println("error !");
			}
			
			if(i%10 == 0){
				// applying batch cumulative gradients
				
				for(int k = 0; k < 10; k++){
					discriminationRbm[k].applyLearningGradients();
				}
				
			}
			
			
			
			
			
			
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
		Files.write(file, lines, Charset.forName("UTF-8"));
		
		double tempd = 0.;
		for(int i = 0; i <10; i++){
			tempd+= errorRates[i];
		}
		System.out.println("error sum " + tempd + " on " + totalErrors + " errors");
	}
	
}
