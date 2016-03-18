package restrictedBoltzmann;

import java.io.IOException;

import mnistReader.MnistManager;

public class TestTemp {

	public static void main(String[] args) throws IOException {
		

		BatchManager batchManager = new BatchManager();
		System.out.println("size " + batchManager.dataSize);
		
		for(int i = 1; i <= batchManager.getSize(); i++){
			batchManager.setCurrent(i);
			System.out.println("i = " + i + " label = " + batchManager.readLabel());
		}
		

	}

}
