package restrictedBoltzmann;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class OutputData {



	
	/* Attributes */
	ArrayList<Double> TestEnergySum;
	ArrayList<Double> LearningEnergySum;
	ArrayList<Integer> nbex;
	
	
	/* Methods */
	public OutputData(
			ArrayList<Integer> nbex,
			ArrayList<Double> TestEnergySum,
			ArrayList<Double> LearningEnergySum) {
		this.nbex = nbex;
		this.TestEnergySum = TestEnergySum;
		this.LearningEnergySum = LearningEnergySum;
	}
	
	
	public void toCSV(Path p) throws IOException {
		FileWriter file = new FileWriter(p.toString());
		
		java.util.ListIterator<Integer> nbexIter = this.nbex.listIterator();
		java.util.ListIterator<Double> TestEnergySumIter = this.TestEnergySum.listIterator();
		java.util.ListIterator<Double> LearningEnergySumIter = this.LearningEnergySum.listIterator();
		
		file.write("nbex; TestEnergySum; LearningEnergySum;\n");
		
		boolean cont = true;
		while(cont) {
			String line =
					((nbexIter.hasNext()) ? (nbexIter.next()+";") : (";"))
					+ ((TestEnergySumIter.hasNext()) ? (TestEnergySumIter.next()+";") : (";"))
					+ ((LearningEnergySumIter.hasNext()) ? (LearningEnergySumIter.next()+";") : (";"))
					+ "\n";
			file.write(line);
			cont =
					nbexIter.hasNext()
					|| TestEnergySumIter.hasNext()
					|| LearningEnergySumIter.hasNext();

		}
		file.close();
	}
	
	public void addData(double TestEnergySum, double LearningEnergySum, int nbex){
		this.TestEnergySum.add(TestEnergySum);
		this.LearningEnergySum.add(LearningEnergySum);
		this.nbex.add(nbex);
	}
	
	public void addData(double TestEnergySum, double LearningEnergySum, int nbex, Path p) throws IOException{
		
		FileWriter file = new FileWriter(p.toString(), true);
		
		this.TestEnergySum.add(TestEnergySum);
		this.LearningEnergySum.add(LearningEnergySum);
		this.nbex.add(nbex);
		
		String line =
				nbex + ";" +
				TestEnergySum + ";"+
				LearningEnergySum + "\n";
		
		file.write(line);
		file.close();
	}
}


