package restrictedBoltzmann;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;

import mnistReader.MnistManager;

public class TestVideo {

	public static void main(String[] args) throws IOException, ParseException {
		
		int[] inputData = {784, 3};
		double biasWide = 0.;
		double weightWide = 0.035;
		double learningRate = 0.005;

		String date = "_" + LocalDateTime.now();
		date = date.substring(0, 20);
		date = date.replace(':', '-');

		RestrictedBoltzmannMachine Rbm = new RestrictedBoltzmannMachine(inputData, weightWide, biasWide, learningRate);

		MnistManager m = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");

		m.setCurrent(1);
		double[] image1D;
		double trainingErrors = 0.;
		double[] visibleVector;

		double[] probabilityOutputs;
		double sumProbability = 0.0;
		double learningEnergy = 0.;
		
		Path p;
		OutputWeights output = null;

		for(int i = 0; i < 500000; i++){
			m.setCurrent((i % 60000) + 1);
			image1D = m.readImage1D();
			Rbm.getLogProbabilityDerivativeSum(Rbm.unsupervisedLearning(2, image1D));
			Rbm.applyLearningGradients();
			probabilityOutputs=Rbm.getProbabilityOutputs();
			learningEnergy += Rbm.getEnergy();


			if(i%1000 == 0){

				if(i==0){
					p = Rbm.visualizeAllFilters();
					int month = LocalDateTime.now().getMonthValue();
					int year = LocalDateTime.now().getYear();
					int day = LocalDateTime.now().getDayOfMonth();
					int hour = LocalDateTime.now().getHour();
					int minute = LocalDateTime.now().getMinute();
					date = day + "-" + month + "-" + year + "_" + hour + "h" + minute;
					
					String dir = "RBM_Filters/Video_" + date + "/";
					
					File f = new File(dir);
					f.mkdirs();
					
					int nombreFiltres = (int) Math.ceil(Math.sqrt(Rbm.layers[1].length));
					int pixelParFiltre = (int) Math.sqrt(Rbm.layers[0].length);
					int ecart = (nombreFiltres-1)*3;
					int taille = (nombreFiltres*pixelParFiltre)+ecart;
					
					output = new OutputWeights(taille);
					output.toVidInit(Paths.get(dir, "filters.avi"), taille);
					output.toVid(p);
					
				}
				
				m.setCurrent(1);
				image1D = m.readImage1D(); 
				sumProbability = 0.0;
				learningEnergy = 0.;
				System.out.println(i);
				p = Rbm.visualizeAllFilters();
				output.toVid(p);
				
			}

		}
		
		
		output.toVidRelease();
		System.out.println("Done");


	}

}
