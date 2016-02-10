package restrictedBoltzmann;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;


public class OutputsProb {
	/* Attributes */
	
	ArrayList<Double> logProbabilities;
	ArrayList<Integer> nbex;
	public OutputsProb( ArrayList<Double> logProbabilities, ArrayList<Integer> nbex){
			this.nbex = nbex; 
			this.logProbabilities = logProbabilities;
	}

	
	
	/* Methods */

	
	public void toCSV(Path p) throws IOException {
		FileWriter file = new FileWriter(p.toString());
		java.util.ListIterator<Integer> nbexIter = this.nbex.listIterator();
		java.util.ListIterator<Double> logProbIter = this.logProbabilities.listIterator();

		file.write("nbex; logProbabilities;\n");
		
		boolean cont = true;
		while(cont) {
			String line =
					((nbexIter.hasNext()) ? (nbexIter.next()+";") : (";"))
					+ ((logProbIter.hasNext()) ? (logProbIter.next()+";") : (";"))
					+ "\n";

			file.write(line);
			cont =
					nbexIter.hasNext()
					|| logProbIter.hasNext();
		}
		file.close();
	}
	
	public void addData(double logProbability, int nbex){
		this.logProbabilities.add(logProbability);
		this.nbex.add(nbex);
	}
	
	public void addData(double logProbability, int nbex, Path p) throws IOException{
		
		FileWriter file = new FileWriter(p.toString(), true);
		
		this.logProbabilities.add(logProbability);
		this.nbex.add(nbex);
		
		String line =
				nbex + ";" +
				logProbability + ";"
				+ "\n";
		
		file.write(line);
		file.close();
	}
}
