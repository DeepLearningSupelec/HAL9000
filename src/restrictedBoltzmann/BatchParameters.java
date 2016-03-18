package restrictedBoltzmann;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import mnistReader.MnistManager;

public class BatchParameters {

	public static void main(String[] args) throws IOException {
		/*
		 * 
		 * The purpose of this class is to create mini batches that 
		 * contain each of the 10 classes of the Mnist learning database
		 * 
		 */
		
		
		int[][] labelIndexs = new int[10][7000]; // 7000 is enough to contain the number of labels
		int[] cursors = new int[10]; // gives the current number of each figure found
		
		MnistManager learningManager = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		int label;
		
		for(int i = 1; i <= 60000; i++){
			learningManager.setCurrent(i);
			label = learningManager.readLabel();
			labelIndexs[label][cursors[label]] = i;
			cursors[label]++;
			
		}
		
		//Mini batches creation
		
		int batchNbr = 7000;
		for(int i = 0; i < 10; i++){
			if(cursors[i] < batchNbr){
				batchNbr = cursors[i];
			}
		}
		System.out.println("Batch number : " + batchNbr);
		
		String batchesLine = "";
		
		
		for(int i = 0; i < batchNbr; i++){
			for(int j = 0; j < 10; j++){
				
				batchesLine += labelIndexs[j][i] + " ";
				
			}
			
		}
		
		
		List<String> lines = Arrays.asList(batchesLine);
		Path file = Paths.get("batchParametersTemp.txt");
		Files.write(file, lines, Charset.forName("UTF-8"));
		
		
		
	}

}
