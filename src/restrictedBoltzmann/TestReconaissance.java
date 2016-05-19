package restrictedBoltzmann;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import mnistReader.MnistManager;

/*
 * Pour ce test l'idée est d'utiliser le réseau une fois entrainé correctement pour
 * lui mettre en entrée une image bitmap que l'on aura tracée à la main pour la présentation.
 * 
 */

public class TestReconaissance {

	public static void main(String[] args) throws IOException, ParseException {


		BMP bmp = new BMP();
		double[][] weights = bmp.BMPtograyscale("image-test.bmp");

		OutputWeights output = new OutputWeights(weights);

		try {
			Path p = Paths.get("src/restrictedBoltzmann/test-retour.bmp");
			output.toBmp(p);
			System.out.println("Images Crées");
		} catch (java.io.IOException e) {

			System.out.println("Exception");
			e.printStackTrace();
		}

	}

}
