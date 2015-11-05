

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import mnistReader.MnistManager;

public class Test {


	public static void main(String[] args) throws IOException {
		MnistManager learningDataManager = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		MnistManager testDataManager = new MnistManager("src/t10k-images.idx3-ubyte","src/t10k-labels.idx1-ubyte");
		int[] tabneuron = {784, 90 ,10};
		Perceptron testPerceptron = new Perceptron(tabneuron, true);
		BackPropagation algorithm = new BackPropagation();
		Input currentInput;
		double learningRate = 0.1;
		
		OutputData output = new OutputData(
				new ArrayList<Integer>(),
				new ArrayList<Double>(),
				new ArrayList<Double>(),
				new ArrayList<Double>(),
				new ArrayList<Double>());
		Path p = Paths.get(System.getProperty("user.home"),"desktop", "perceptron.csv");
		FileWriter file = output.toCSV(p);
		
		Double instantError = 0.;
		Double accuError = 0.;
		
		int i = 0;
		
		do{
			
			if (i%1000 == 1) {
				if (i==1){
					output.addData(0., 0., 0., accuError, i, p);
				} else {
					output.addData(0., 0., 0., accuError/1000, i, p);
				}
				accuError = 0.;
			}
			
			i++;
			
			currentInput = new Input(Math.abs(i%60001));
			learningDataManager.setCurrent(Math.abs(i%60001));
			//testPerceptron.setInputs(learningDataManager.readImage1D());
			testPerceptron.setNormalizedInputs(learningDataManager.readImage1D(), 256);
			testPerceptron.fire();
			algorithm.launch(testPerceptron, learningRate , currentInput);			
			double[] outputs=testPerceptron.getOutputs();
			for(int j =0; j<10;j++){
				String s = outputs[j] + " ";
				if(outputs[j] < 0.001){ s = "~0 ";}
				System.out.print(s);
				instantError += Math.abs(currentInput.expectedOutput()[j]-outputs[j])/10;
			}
			System.out.println("Max absolute Weight : " + testPerceptron.wideWeight());
			System.out.println("Expected : " + currentInput.getLabel() + " Output : " + testPerceptron.mostProbableAnswer());
			
			System.out.println("Erreur : " + instantError);
			
			accuError += instantError;
			instantError = 0.;
			

		} while (accuError < 0.01) ;
		/*
		OutputData output = new OutputData(
				nbex,
				quadTest,
				quadLearning,
				errTest,
				errLearning);
		Path p = Paths.get(System.getProperty("user.home"),"desktop", "perceptron.csv");
		output.toCSV(p);

		//} while (accuError > 0.001) ;*/


	}

}