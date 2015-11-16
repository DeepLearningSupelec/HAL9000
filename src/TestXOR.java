
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class TestXOR {

	public static void main(String[] args) throws IOException {
		
		
		//Input DataBase creation
		
		int[] valuesA = {1, 1};
		Input a = new Input(valuesA, 0);
		int[] valuesB = {1, 0};
		Input b = new Input(valuesB, 1);
		int[] valuesC = {0, 1};
		Input c = new Input(valuesC, 1);
		int[] valuesD = {0, 0};
		Input d = new Input(valuesD, 0);
		
		Input[] inputs = {a, b, c, d};
		
		//Learning
		
		/*Path p1 = Paths.get(System.getProperty("user.home"),"desktop", "XORPerceptron.txt");
		
		Perceptron test = new Perceptron(p1);
		System.out.println("pouet");*/
		int[] tabneuron = {2, 2, 1};
		Perceptron testPerceptron = new Perceptron(tabneuron, false);
		testPerceptron.synapses.get(0).setWeight(0);
		BackPropagation algorithm = new BackPropagation();
		Input currentInput;
		double learningRate = 0.1;
		
		
	
	}
}
