package restrictedBoltzmann;

import java.io.IOException;
import java.time.LocalDateTime;

import mnistReader.MnistManager;

public class TestRBM {

	public static void main(String[] args) throws IOException {
		
		int[] inputData = {784, 784};
		double biasWide = 1;
		double weightWide = 0.7;
		
		RestrictedBoltzmannMachine rbm = new RestrictedBoltzmannMachine(inputData, weightWide, biasWide);
		
		MnistManager m = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		m.setCurrent(2);
		double[] image1D = m.readImage1D();
		
		
		
		//rbm.setBinaryInputs(exemple);
		//rbm.reachEquilibrium();
		//rbm.displayBinaryOutputs();
		
		//rbm.displayProbabilityOutputs();
		
		rbm.unsupervisedLearning(2, image1D);
		
		// //rbm.displayBinaryOutputs();
		
		int[] input1D = rbm.getBinaryInputs();
		for(int i = 0; i < 784; i++){
			System.out.println(input1D[i]);
		}
		
		
		String date = "_" + LocalDateTime.now();
		date = date.substring(0, 20);
		date = date.replace(':', '-');
		System.out.println(date);
		String adress = "Images_ppm//";
		String extension = ".ppm";
		int[][] image2D = Tools.image1Dto2D(rbm.getBinaryInputs(), 28, 28);
		MnistManager.writeImageToPpm(image2D, adress + "image1" + date + extension);
		int[][] image2Dexit = Tools.image1Dto2D(rbm.getBinaryOutputs(), 28, 28);
		MnistManager.writeImageToPpm(image2Dexit, adress + "image1ExitRBM" + date + extension);
		
		//rbm.displayProbabilityOutputs();
		
		System.out.println("Done");
		
		/*MnistManager m = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		m.setCurrent(1);
		int[][] image = m.readImage();
		m.setCurrent(1);
		double[] image1D = m.readImage1D();
		String date = "_" + LocalDateTime.now();
		date = date.substring(0, 20);
		date = date.replace(':', '-');
		System.out.println(date);
		String adress = "Images_ppm//";
		String extension = ".ppm";
		MnistManager.writeImageToPpm(image, adress + "image0" + date + extension);
		int[][] image2D = Tools.image1Dto2D(image1D, 28, 28);
		MnistManager.writeImageToPpm(image2D, adress + "imageto2D" + date + extension);
		*/
		
	}

}
