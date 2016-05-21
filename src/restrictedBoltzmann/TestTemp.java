package restrictedBoltzmann;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import mnistReader.MnistManager;

public class TestTemp {

	public static void main(String[] args) throws IOException {
		
		int[] inputData = {784, 10, 10, 10};
		int rbmLayerNumber = 3;
		double biasWide = 0;
		double weightWide = 1;
		double learningRate = 0.1;
		double backPropLearningRate = 0.1;
		
		
		int[] p = inputData.clone();
		p[0] = 0;
		System.out.println(p[0] + " " + inputData[0]);

		
		
		DeepBeliefNetwork dBN = new DeepBeliefNetwork(inputData, rbmLayerNumber, weightWide, biasWide, learningRate, backPropLearningRate);
		dBN.machines[0].setMnistParameters();
		
		//dBN.saveMachineState();
		
		dBN = new DeepBeliefNetwork(Paths.get("DBNsaveFiles","saveFile4Layers_2016-05-10T11-20-41.txt"));
		
		dBN.saveMachineState();
	}

}
