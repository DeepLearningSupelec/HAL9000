package restrictedBoltzmann;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import mnistReader.MnistManager;

public class TestParameters {

	public static void main(String[] args) throws IOException {
		/*
		 * 
		 * The purpose of this class is to set the most accurate initial value
		 * of the parameters of RBMs (visible entities bias)
		 * 
		 */
		
		int[] activationCpt = new int[784];
		int[][] labelActivationCpt = new int[10][784];
		MnistManager learningManager = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		double[] image1D; 
		int[] incrArray;
		int label;
		
		for(int i = 1; i <= 60000; i++){
			learningManager.setCurrent(i);
			image1D = learningManager.readImage1D();
			incrArray = RestrictedBoltzmannMachine.InputToBinaryEntity(image1D);
			label = learningManager.readLabel();
			for(int j = 0; j < 784; j++){
				activationCpt[j] += incrArray[j];
				labelActivationCpt[label][j] += incrArray[j];
			}
		}
		
		
		List<String> lines = Arrays.asList("The first line", "The second line");
		Path file = Paths.get("the-file-name.txt");
		Files.write(file, lines, Charset.forName("UTF-8"));
		
		
		
	}

}
