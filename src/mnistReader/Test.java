package mnistReader;
import java.util.Scanner;
import java.io.IOException;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		testParser();
	}

	public static void testParser() throws IOException {
		MnistManager m = new MnistManager("train-images.idx3-ubyte","train-labels.idx1-ubyte");
		Scanner saisieUtilisateur = new Scanner(System.in); 

		for(int i = 0; i<=3; i=i+1){
			System.out.println("Veuillez saisir un entier :"); 
			int ent = saisieUtilisateur.nextInt();


			m.setCurrent(ent);
			int[][] image = m.readImage();
			System.out.println("Label:" + m.readLabel());

			System.out.println("Image length: " + m.getImages().getEntryLength());
			System.out.println("Current Index: " + m.getImages().getCurrentIndex());

			System.out.println("Label length: " + m.getLabels().getEntryLength());
			System.out.println("Label Index: " + m.getLabels().getCurrentIndex());
			
		}

		//MnistManager.writeImageToPpm(image, "10000.ppm");
	}

}