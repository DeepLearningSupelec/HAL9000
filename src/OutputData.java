import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class OutputData {
	
	/* Attributes */
	ArrayList<Double> quadTest;
	ArrayList<Double> quadLearning;
	ArrayList<Double> errTest;
	ArrayList<Double> errLearning;
	ArrayList<Integer> nbex;
	
	
	/* Methods */
	public OutputData(
			ArrayList<Integer> nbex,
			ArrayList<Double> quadTest,
			ArrayList<Double> quadLearning,
			ArrayList<Double> errTest,
			ArrayList<Double> errLearning) {
		this.nbex = nbex;
		this.quadTest = quadTest;
		this.quadLearning = quadLearning;
		this.errTest = errTest;
		this.errLearning = errLearning;
	}
	
	
	public void toCSV(Path p) throws IOException {
		FileWriter file = new FileWriter(p.toString());
		
		java.util.ListIterator<Integer> nbexIter = this.nbex.listIterator();
		java.util.ListIterator<Double> quadTestIter = this.quadTest.listIterator();
		java.util.ListIterator<Double> quadLearningIter = this.quadLearning.listIterator();
		java.util.ListIterator<Double> errTestIter = this.errTest.listIterator();
		java.util.ListIterator<Double> errLearningIter = this.errLearning.listIterator();
		
		file.write("nbex; quadLearning; errLearning; quadTest; errTest;\n");
		
		boolean cont = true;
		while(cont) {
			String line =
					((nbexIter.hasNext()) ? (nbexIter.next()+";") : (";"))
					+ ((quadLearningIter.hasNext()) ? (quadLearningIter.next()+";") : (";"))
					+ ((errLearningIter.hasNext()) ? (errLearningIter.next()+";") : (";"))
					+ ((quadTestIter.hasNext()) ? (quadTestIter.next()+";") : (";"))
					+ ((errTestIter.hasNext()) ? (errTestIter.next()+";") : (";"))
					+ "\n";
			file.write(line);
			cont =
					nbexIter.hasNext()
					|| quadLearningIter.hasNext()
					|| errLearningIter.hasNext()
					|| quadTestIter.hasNext()
					|| errTestIter.hasNext();
		}
		file.close();
	}
	
	public void addData(double quadTest, double quadLearning,double errTest, double errLearning, int nbex){
		this.quadTest.add(quadTest);
		this.quadLearning.add(quadLearning);
		this.errTest.add(errTest);
		this.errLearning.add(errLearning);
		this.nbex.add(nbex);
	}
	
	public void addData(double quadTest, double quadLearning,double errTest, double errLearning, int nbex, Path p) throws IOException{
		
		FileWriter file = new FileWriter(p.toString(), true);
		
		this.quadTest.add(quadTest);
		this.quadLearning.add(quadLearning);
		this.errTest.add(errTest);
		this.errLearning.add(errLearning);
		this.nbex.add(nbex);
		
		String line =
				nbex + ";" +
				quadLearning + ";" +
				errLearning + ";" +
				quadTest + ";" +
				errTest + "\n";
		
		file.write(line);
		file.close();
	}
}
