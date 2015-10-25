
import java.io.IOException;

import mnistReader.MnistManager;

public class Test {


	public static void main(String[] args) throws IOException {
		MnistManager learningDataManager = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		MnistManager testDataManager = new MnistManager("src/t10k-images.idx3-ubyte","src/t10k-labels.idx1-ubyte");
		int[] tabneuron = {784, 90 ,10};
		Perceptron testPerceptron = new Perceptron(tabneuron, true);
		BackPropagation algorithm = new BackPropagation();
		Input currentInput;
		double learningRate = 0.01;
		for(int i =1; i <100 ; i++){
			currentInput = new Input(i);
			learningDataManager.setCurrent(i);
			//testPerceptron.setInputs(learningDataManager.readImage1D());
			testPerceptron.setNormalizedInputs(learningDataManager.readImage1D(), 256);
			testPerceptron.fire();
			algorithm.launch(testPerceptron, learningRate , currentInput);			
			double[] outputs=testPerceptron.getOutputs();
			for(int j =0; j<10;j++){
				System.out.print(outputs[j]+" ");
			}
			System.out.println();
			
			
		}
		
		
		/* Test Sigmoid */
		/*Sigmoid f1 = new Sigmoid();
		Sigmoid f2 = new Sigmoid(2.);
		System.out.println(f1.apply(1.));
		System.out.println(f2.apply(1.));
		System.out.println(f1.applyDerivative(2.));
		System.out.println(f2.applyDerivative(2.));*/
		
		
		/* Test ActivationFunction*/
		/*ActivationFunction f = new ActivationFunction(x->x*x, x->2*x);
		System.out.println(f.apply(3.));
		System.out.println(f.applyDerivative(3.));*/

	}

}
