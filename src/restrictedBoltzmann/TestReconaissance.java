package restrictedBoltzmann;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

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


		DeepBeliefNetwork dBN = new DeepBeliefNetwork(Paths.get("DBNsaveFiles","saveFile5Layers_2016-05-14T11-19-37.txt"));

		String sc = new String("");

		Scanner read = new Scanner(System.in);
		System.out.println("Pour quitter tapez exit sinon entrée");
		sc = read.nextLine();

		while(!sc.equals("exit")){

			
			double[] weights = bmp.BMPtograyscale("image-test.bmp");
			dBN.setInputs(weights, 1);
			dBN.fire();

			int label = -1;
			double maxProba = 0.;
			double sumProba = 0.;

			for(int i = 0; i<10;i++){
				sumProba = dBN.entityValues[dBN.totalLayerNumber - 1][i] + sumProba;
				if(dBN.entityValues[dBN.totalLayerNumber - 1][i] >= maxProba){
					label = i;
					
					maxProba = dBN.entityValues[dBN.totalLayerNumber - 1][i];
				}
			}

			System.out.println("Nous reconnaisson un " + dBN.getMnistMostProbableLabel() + " avec un probabilité de " + maxProba/sumProba);
			System.out.println("Pour quitter tapez exit");
			sc = read.nextLine();
			
		}
	}

}
