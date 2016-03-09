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
		
		int[] imageCpt = new int[10];
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
			learningManager.setCurrent(i);
			label = learningManager.readLabel();
			imageCpt[label]++;
			for(int j = 0; j < 784; j++){
				activationCpt[j] += incrArray[j];
				labelActivationCpt[label][j] += incrArray[j];
			}
		}
		
		/*
		
		1st Line: number of activation of each entity (with full training)
		
		2nd to 12th lines : number of activation of each entity (with one-label-only training)
		
		*/
		String line = "";
		String[] labelLines = new String[10];
		for(int j=0;j<10;j++){labelLines[j] = "";}
		
		for(int i = 0; i < 784; i++){
			line += activationCpt[i] + " ";
			for(int j = 0; j < 10; j++){
				labelLines[j] += labelActivationCpt[j][i] + " ";
			}
		}
		
		String line12 = "";
		for(int j = 0; j < 10; j++){
			line12 += imageCpt[j] + " ";
		}
		
		
		List<String> lines = Arrays.asList(line12,
				line, 
				labelLines[0],labelLines[1],
				labelLines[2],labelLines[3],
				labelLines[4],labelLines[5],
				labelLines[6],labelLines[7],
				labelLines[8],labelLines[9],
				line12);
		Path file = Paths.get("trainingParametersTemp.txt");
		Files.write(file, lines, Charset.forName("UTF-8"));
		
		
		
	}

}
