
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class TestXOR {

	public static void main(String[] args) throws IOException {
		
		
		//Input DataBase creation
		
		double[] valuesA = {1, 1};
		Input a = new Input(valuesA, 0);
		double[] valuesB = {1, 0};
		Input b = new Input(valuesB, 1);
		double[] valuesC = {0, 1};
		Input c = new Input(valuesC, 1);
		double[] valuesD = {0, 0};
		Input d = new Input(valuesD, 0);
		
		Input[] inputs = {a, b, c, d};
		
		//Learning
		
		/*Path p1 = Paths.get(System.getProperty("user.home"),"desktop", "XORPerceptron.txt");
		
		Perceptron test = new Perceptron(p1);
		System.out.println("pouet");*/
		int[] tabneuron = {2, 2, 1};
		Perceptron testPerceptron = new Perceptron(tabneuron, false);
		testPerceptron.synapses.get(0).setWeight(0.8);
		testPerceptron.synapses.get(1).setWeight(0.5);
		testPerceptron.synapses.get(2).setWeight(-0.3);
		testPerceptron.synapses.get(3).setWeight(0.2);
		testPerceptron.synapses.get(4).setWeight(0.6);
		testPerceptron.synapses.get(5).setWeight(-0.5);
		
		boolean isMnist = false;
		BackPropagation algorithm = new BackPropagation(isMnist);
		Input currentInput;
		double learningRate = 0.1;
		
		OutputData output = new OutputData(
				new ArrayList<Integer>(),
				new ArrayList<Double>(),
				new ArrayList<Double>(),
				new ArrayList<Double>(),
				new ArrayList<Double>());
		Path p = Paths.get(System.getProperty("user.home"),"desktop", "perceptronXOR.csv");
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

		

		
		/*
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
				currentInput = inputs[i%4];
				//learningDataManager.setCurrent(Math.abs((i%50000)+1));
				testPerceptron.setInputs(currentInput.getInputValues());
				testPerceptron.fire();
				algorithm.launch(testPerceptron, learningRate , currentInput);
				i++;
				if (i%1000 == 0){
					learn=!learn;
				}
			}
			if(!learn){
				for(int j = i - epoch; j<i ;j++){
					currentInput = inputs[j%4];
					testPerceptron.setInputs(currentInput.getInputValues());
					testPerceptron.fire();
					outputs=testPerceptron.getOutputs();
					errorQuad += Math.pow(outputs[0]- currentInput.expectedOutput()[0], 2)/2;
					if(currentInput.getLabel() != Math.round(testPerceptron.getOutputs()[0])){
						pError +=  1.;
					}
				}
				learn=!learn;
			}
		

		} while(true) ;*/
		
		//int k = 0;
		System.out.println("begin");
		System.out.println(((ActiveNeuron)testPerceptron.outputNeurons.get(0)).getBias());
		for(int k =0; k < 100; k++){
			currentInput = inputs[k%4];
			testPerceptron.setInputs(currentInput.getInputValues());
			testPerceptron.fire();
			//System.out.println("Expected : " + currentInput.getLabel() + " Output : "+ testPerceptron.outputNeurons.get(0).getOutput());
			//System.out.println(testPerceptron.wideWeight());
			algorithm.launch(testPerceptron, learningRate , currentInput);
			i++;
			System.out.println(k + " " + testPerceptron.outputNeurons.get(0).getNeuronDiff());
		}
		
		
		
	
	}
}
