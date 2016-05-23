package restrictedBoltzmann;

import java.io.IOException;
import java.nio.file.Paths;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class TestDBNAnalyse {

	public static void main(String[] args) throws IOException, ParseException {

		/*
		 * DBN parameters :
		 * 
		 * int[] inputData = {784, 500, 500, 2000, 10};
		 * int rbmLayerNumber = 4;
		 * double biasWide = 0;
		 * double weightWide = 1;
		 * double learningRate = 0.1;
		 * double backPropLearningRate = 0.1;
		 * 
		 */
		
		DeepBeliefNetwork dBN = new DeepBeliefNetwork(Paths.get("DBNsaveFiles","saveFile5Layers_2016-05-14T11-19-37.txt"));
		
		for(int i = 0; i < 4; i++){
			try {
				dBN.machines[i].visualizeAllFilters();
			} catch (java.text.ParseException e) {
				System.out.println("Exception");
				e.printStackTrace();
			}
			System.out.println("Couche " + i);
		}
		
		
		
		
		
	}

}
