
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
		double learningRate = 0.1;
		for(int i =1; i <5000 ; i++){
			currentInput = new Input(i);
			learningDataManager.setCurrent(i);
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
			
			
			
		}
		//System.out.println(testPerceptron.layers[6][1]);
		
		/* Test Sigmoid */
		/*System.out.println("Test Sigmoid");
		Sigmoid f1 = Sigmoid.getINSTANCE();
		double x = 2.;
		System.out.println("f(" + x + ") = " + f1.apply(x));
		x = -10000; 
		System.out.println("f(" + x + ") = " + f1.apply(x));
		x = 10000; 
		System.out.println("f(" + x + ") = " + f1.apply(x));
		double y = 10000;
		System.out.println("f'(" + y + ") = " + f1.applyDerivative(y));*/
		
		
		/* Test ActivationFunction*/
		/*ActivationFunction f = new ActivationFunction(x->x*x, x->2*x);
		System.out.println(f.apply(3.));
		System.out.println(f.applyDerivative(3.));*/

	}

}
