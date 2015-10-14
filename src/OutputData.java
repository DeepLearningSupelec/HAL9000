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
	public void toCSV(Path p) throws IOException {
		FileWriter file = new FileWriter(p.toString());
		java.util.ListIterator<Integer> nbexIter = this.nbex.listIterator();
		java.util.ListIterator<Double> quadTestIter = this.quadTest.listIterator();
		java.util.ListIterator<Double> quadLearningIter = this.quadLearning.listIterator();
		java.util.ListIterator<Double> errTestIter = this.errTest.listIterator();
		java.util.ListIterator<Double> errLearningIter = this.quadLearning.listIterator();
		
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
}
