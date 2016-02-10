package restrictedBoltzmann;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class OutputData {



	
	/* Attributes */
	ArrayList<Double> logDerivativeSum;
	ArrayList<Integer> nbex;
	
	
	/* Methods */
	public OutputData(
			ArrayList<Integer> nbex,
			ArrayList<Double> logDerivativeSum) {
		this.nbex = nbex;
		this.logDerivativeSum = logDerivativeSum;
	}
	
	
	public void toCSV(Path p) throws IOException {
		FileWriter file = new FileWriter(p.toString());
		
		java.util.ListIterator<Integer> nbexIter = this.nbex.listIterator();
		java.util.ListIterator<Double> logDerivativeSumIter = this.logDerivativeSum.listIterator();
		
		file.write("nbex; logDerivativeSum;\n");
		
		boolean cont = true;
		while(cont) {
			String line =
					((nbexIter.hasNext()) ? (nbexIter.next()+";") : (";"))
					+ ((logDerivativeSumIter.hasNext()) ? (logDerivativeSumIter.next()+";") : (";"))
					+ "\n";
			file.write(line);
			cont =
					nbexIter.hasNext()
					|| logDerivativeSumIter.hasNext();

		}
		file.close();
	}
	
	public void addData(double logDerivativeSum, int nbex){
		this.logDerivativeSum.add(logDerivativeSum);
		this.nbex.add(nbex);
	}
	
	public void addData(double logDerivativeSum, int nbex, Path p) throws IOException{
		
		FileWriter file = new FileWriter(p.toString(), true);
		
		this.logDerivativeSum.add(logDerivativeSum);
		this.nbex.add(nbex);
		
		String line =
				nbex + ";" +
				logDerivativeSum + "\n";
		
		file.write(line);
		file.close();
	}
}


