package restrictedBoltzmann;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestBMP {

	public static void main(String[] args) {
		
		double[][] weights = new double[28][28];
		for(int i=0; i<28; i++){
			for(int j=0; j<28; j++){
				weights[i][j] = (i*j)-1000;
			}
		}
		OutputWeights output = new OutputWeights(weights);
		Path p = Paths.get(System.getProperty("user.home"),"desktop", "img.bmp");
		output.toBmp(p);
	}
}
