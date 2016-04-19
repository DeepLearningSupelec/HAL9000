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

public class Test2classes {
	
	public static void main(String[] args) throws IOException {		
			int[] inputData = {784, 36};
			double biasWide = 0;
			double weightWide = 0.035;
			double learningRate = 0.051;
			
			String date = "_" + LocalDateTime.now();
			date = date.substring(0, 20);
			date = date.replace(':', '-');
			System.out.println(date);
			
			
			RestrictedBoltzmannMachine[] discriminationRbm = new RestrictedBoltzmannMachine[10];
			
			for(int i = 0; i < 2; i++){
				discriminationRbm[i] = new RestrictedBoltzmannMachine(inputData, weightWide, biasWide, learningRate);
				discriminationRbm[i].setMnistLabelParameters(i);
			}
			
			int gibbsSteps = 3;
			
			BatchManager testManager = new BatchManager();
			OutputData output = new OutputData(new ArrayList<Integer>(), new ArrayList<Double>(), new ArrayList<Double>());
			Path p = Paths.get(/*System.getProperty("user.home"),*/"RBM_EnergyData", "visibleEnergyTest" + gibbsSteps +  "GibbsSteps"  + date + ".csv");
			output.toCSV(p);
			
			double[] image1D;
			double trainingErrors = 0.; 
			double[] visibleVector;
			
			double[] errorRates = new double[10];
			double totalErrors = 0.;
			
			int number1 = 6;
			int number2 = 8;
			ArrayList <Integer> index1 = new ArrayList<Integer>();
			ArrayList <Integer> index2 = new ArrayList<Integer>();
			
			for(int i = 1; i<= 50000;i++){
				testManager.setCurrent(i);
				if(testManager.readLabel() == number1){
					index1.add(i);
				}
				if(testManager.readLabel() == number2){
					index2.add(i);
				}
			}
			boolean order =true;
			for(int i = 0; i < 100000;){
				if(order){
					testManager.setCurrent(index1.get(i % index1.size()));
				}
				if(!order){
					testManager.setCurrent(index2.get(i % index2.size()));
					i++;
				}
				order=!order;
				image1D = testManager.readImage1D();
				if(testManager.readLabel()== number1){
					discriminationRbm[0].unsupervisedLearning(gibbsSteps * 2, image1D);
					discriminationRbm[0].applyLearningGradients();
				}
				else{
					discriminationRbm[1].unsupervisedLearning(gibbsSteps * 2, image1D);
					discriminationRbm[1].applyLearningGradients();
				}
				double min = 0.;
				int labl = 0;
				visibleVector = testManager.readImage1D();
				for(int k = 0; k < 2; k++){
					discriminationRbm[k].setBinaryInputs(visibleVector);
					double temp = discriminationRbm[k].getFreeEnergy();
					if(temp < min){
						if(k==0)
							labl = number1;
						else
							labl = number2;
						min = temp;
					}
				}
				
				if(labl != testManager.readLabel()){
					trainingErrors ++;
					totalErrors++;
					errorRates[testManager. readLabel()]++;
				}
				
				if(i%10 == 0 && !order) {
					// applying batch cumulative gradients
					
					for(int k = 0; k < 2; k++){
						discriminationRbm[k].applyLearningGradients();
					}
					
				}
				
				
				
				
				
				
				if(i%1000 == 0 && !order){
					double testErrors = 0.;
					output.addData(testErrors/2000, trainingErrors/2000, i/2000, p);
					System.out.println(trainingErrors/2000.);
					trainingErrors = 0.;
				}
				if(i%100 == 0 && !order){
					System.out.println(i);
				}
			}
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



