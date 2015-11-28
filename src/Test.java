
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
		boolean isMnist = true;
		BackPropagation algorithm = new BackPropagation(isMnist);
		Input currentInput;
		double learningRate = 0.1;
		
		OutputData output = new OutputData(
				new ArrayList<Integer>(),
				new ArrayList<Double>(),
				new ArrayList<Double>(),
				new ArrayList<Double>(),
				new ArrayList<Double>());
		Path p = Paths.get(System.getProperty("user.home"),"desktop", "perceptron3.csv");
		output.toCSV(p);
		Double instantError = 0.;
		Double accuError = 0.;
		Double pError = 0.;
		Double pErrorTest = 0.;
		Double errorQuad = 0.;
		Double errorQuadTest = 0.;
		boolean learn = true;
		double[] outputs;
		int i = 0;
		int epoch = 1000;
		do{
			if (learn){
				currentInput = new Input(Math.abs((i%60000)+1),learningDataManager);
				learningDataManager.setCurrent(Math.abs((i%60000)+1));
				testPerceptron.setNormalizedInputs(learningDataManager.readImage1D(), 256);
				testPerceptron.fire();
				algorithm.launch(testPerceptron, learningRate , currentInput);
				i++;
				if (i%1000 == 0){
					learn=!learn;
				}
			}
			if(!learn){
				for(int j = 1; j<=60000 ;j++){
					currentInput = new Input(j,learningDataManager);
					learningDataManager.setCurrent(j);
					testPerceptron.setNormalizedInputs(learningDataManager.readImage1D(), 256);
					testPerceptron.fire();				
					outputs=testPerceptron.getOutputs();
					for (int index =0 ; index<10; index++){
						errorQuad += Math.pow(outputs[index]- currentInput.expectedOutput(isMnist)[index], 2)/2;
					}
					if(currentInput.getLabel() != testPerceptron.mostProbableAnswer()){
						pError +=  1.;
					}
				}
				for(int k = 1 ; k<=10000; k++){
					currentInput = new Input(Math.abs(k), testDataManager);
					testDataManager.setCurrent(Math.abs(k));
					testPerceptron.setNormalizedInputs(testDataManager.readImage1D(), 256);
					testPerceptron.fire();
					outputs=testPerceptron.getOutputs();
					for (int index =0 ; index<10; index++){
						errorQuadTest += Math.pow(outputs[index]- currentInput.expectedOutput(isMnist)[index], 2)/2;
					}
					if(currentInput.getLabel() != testPerceptron.mostProbableAnswer()){
						pErrorTest +=  1.;
					}
				}
				learn=!learn;
			}
			if (i%epoch == 0) {
				output.addData(errorQuadTest/10000, errorQuad/60000, pErrorTest/10000, pError/60000, i, p);
				System.out.println(i+ "     " +errorQuadTest/10000 +"  "+    errorQuad/60000      + "  "+ pError/60000 +"   " + pErrorTest/10000);
				pError = 0.;
				pErrorTest =0.;
				errorQuad = 0.;
				errorQuadTest =0.;  
			}
		} while(i<300000) ;


	}

}