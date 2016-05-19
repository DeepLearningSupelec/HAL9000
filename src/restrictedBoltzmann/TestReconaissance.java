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
 * A faire : 
 * - DBN dans laquelle on insere les valeurs des poids et des biais d'une machine déja entrainée.
 * 
 *
 * 
 */

public class TestReconaissance {

	public static void main(String[] args) throws IOException, ParseException {


		BMP bmp = new BMP();
		double[] weights = bmp.BMPtograyscale("image-test.bmp");
		
		for(int i=0; i < weights.length ; i++){
			System.out.println(weights[i]);
		}


	}

}
