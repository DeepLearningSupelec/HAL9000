package restrictedBoltzmann;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import mnistReader.MnistManager;

public class TestTemp {

	public static void main(String[] args) throws IOException {
		
		String line = "";
		String[] labelLines = new String[10];
		for(int j=0;j<10;j++){labelLines[j] = "";}
		
		for(int i = 0; i < 784; i++){
			line +=  " ";
			for(int j = 0; j < 10; j++){
				labelLines[j] +=   " ";
			}
		}
		
		String line12 = "";
		for(int j = 0; j < 10; j++){
			line12 +=  " ";
		}
		
		
		List<String> lines = Arrays.asList(line12,
				line, 
				labelLines[0],labelLines[1],
				labelLines[2],labelLines[3],
				labelLines[4],labelLines[5],
				labelLines[6],labelLines[7],
				labelLines[8],labelLines[9],
				line12);
		
		List<String> lines2 = Arrays.asList(labelLines);
		
	}

}
