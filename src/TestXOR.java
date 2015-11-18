
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
		testPerceptron.synapses.get(1).setWeight(-0.3);
		testPerceptron.synapses.get(2).setWeight(0.5);
		testPerceptron.synapses.get(3).setWeight(0.2);
		testPerceptron.synapses.get(4).setWeight(-0.5);
		testPerceptron.synapses.get(5).setWeight(0.6);
		
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
		Double pError = 0.;
		Double pErrorTest = 0.;
		Double errorQuad = 0.;
		Double errorQuadTest = 0.;
		boolean learn = true;
		double[] outputs;
		int i = 0;
		
		
		int k = 0;
		
		currentInput = inputs[k];
		//testPerceptron.setNormalizedInputs(learningDataManager.readImage1D(), 256);
		testPerceptron.setInputs(currentInput.getInputValues());
		testPerceptron.fire();
		System.out.println("Expected : " + currentInput.getLabel() + " Output : "+ testPerceptron.outputNeurons.get(0).getOutput());
		algorithm.launch(testPerceptron, learningRate , currentInput);
		i++;
		
	
	}
}
