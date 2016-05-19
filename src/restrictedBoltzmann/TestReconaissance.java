package restrictedBoltzmann;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import mnistReader.MnistManager;

/*
 * Pour ce test l'id�e est d'utiliser le r�seau une fois entrain� correctement pour
 * lui mettre en entr�e une image bitmap que l'on aura trac�e � la main pour la pr�sentation.
 * 
 * A faire : 
 * - DBN dans laquelle on insere les valeurs des poids et des biais d'une machine d�ja entrain�e.
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
