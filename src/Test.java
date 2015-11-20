
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
		Path p = Paths.get(System.getProperty("user.home"),"desktop", "perceptron.csv");
		FileWriter file = output.toCSV(p);
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
			
			if (i%epoch == 0) {
				if (i==0){
					output.addData(0., 0., 0., 0., 0,p);
				} else {
					output.addData(errorQuadTest/10000, errorQuad/epoch, pErrorTest/10000, pError/epoch, i, p);
				}
				System.out.println(i+ "     " +errorQuadTest/10000 +"  "+    errorQuad/1000      + "  "+ pError/epoch +"   " + pErrorTest/10000);
				pError = 0.;
				pErrorTest =0.;
				errorQuad = 0.;
				errorQuadTest =0.;  
			}
			
			if (learn){
				currentInput = new Input(Math.abs((i%50000)+1));
				learningDataManager.setCurrent(Math.abs((i%50000)+1));
				testPerceptron.setNormalizedInputs(learningDataManager.readImage1D(), 256);
				testPerceptron.fire();
				algorithm.launch(testPerceptron, learningRate , currentInput);
				i++;
				if (i%1000 == 0){
					learn=!learn;
				}
			}
			if(!learn){
				for(int j = i - epoch; j<i ;j++){
					currentInput = new Input(Math.abs((j%50000)+1));
					learningDataManager.setCurrent(Math.abs((j%50000)+1));
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
				/*for(int k = 50001 ; k<=60000; k++){
					currentInput = new Input(Math.abs(k));
					learningDataManager.setCurrent(Math.abs(k));
					testPerceptron.setNormalizedInputs(learningDataManager.readImage1D(), 256);
					testPerceptron.fire();
					outputs=testPerceptron.getOutputs();
					for (int index =0 ; index<10; index++){
						errorQuadTest += Math.pow(outputs[index]- currentInput.expectedOutput()[index], 2)/2;
					}
					if(currentInput.getLabel() != testPerceptron.mostProbableAnswer()){
						pErrorTest +=  1.;
					}
				}*/
				learn=!learn;
			}
		/*	for(int j =0; j<10;j++){
				String s = outputs[j] + " ";
				if(outputs[j] < 0.001){ s = "~0 ";}
				System.out.print(s);
				instantError += Math.abs(currentInput.expectedOutput()[j]-outputs[j])/10;
			}
			System.out.println("Max absolute Weight : " + testPerceptron.wideWeight());
			System.out.println("Expected : " + currentInput.getLabel() + " Output : " + testPerceptron.mostProbableAnswer());
			
			System.out.println("Erreur : " + instantError);
			*/
		/*	accuError += instantError;
			instantError = 0.;
			*/

		} while(true) ;


	}

}