package restrictedBoltzmann;

import java.io.IOException;
import java.nio.file.Paths;

public class TestDBNAnalyse {

	public static void main(String[] args) throws IOException {

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
			dBN.machines[i].visualizeFilters();
		}
		
		
		
		
		
	}

}
