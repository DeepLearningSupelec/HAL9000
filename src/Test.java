

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
		
		ArrayList<Double> quadTest = new ArrayList<Double>();
		ArrayList<Double> quadLearning = new ArrayList<Double>();
		ArrayList<Double> errTest = new ArrayList<Double>();
		ArrayList<Double> errLearning = new ArrayList<Double>();
		Double error = 0.;
		ArrayList<Integer> nbex = new ArrayList<Integer>();
		
		
		int i =1;
		do{
			currentInput = new Input(Math.abs(i%600001));
			learningDataManager.setCurrent(Math.abs(i%600001));
			//testPerceptron.setInputs(learningDataManager.readImage1D());
			testPerceptron.setNormalizedInputs(learningDataManager.readImage1D(), 256);
			testPerceptron.fire();
			algorithm.launch(testPerceptron, learningRate , currentInput);			
			double[] outputs=testPerceptron.getOutputs();
			for(int j =0; j<10;j++){
				String s = outputs[j] + " ";
				if(outputs[j] < 0.001){ s = "~0 ";}
				System.out.print(s);
			}
			System.out.println("Max absolute Weight : " + testPerceptron.wideWeight());
			System.out.println("Expected : " + currentInput.getLabel() + " Output : " + testPerceptron.mostProbableAnswer());
			if (currentInput.getLabel() != testPerceptron.mostProbableAnswer()) {
				error++;
			}
			
			System.out.println("Taux d'erreur : " + error/i);
			
			if (i%1000 == 0) {
				nbex.add(i);
				errLearning.add(error/i);
			}
			
			i++;
		} while (error/i>0.1) ;
		
		OutputData output = new OutputData(
				nbex,
				quadTest,
				quadLearning,
				errTest,
				errLearning);
		Path p = Paths.get(System.getProperty("user.home"),"desktop", "perceptron.csv");
		output.toCSV(p);

	}

}