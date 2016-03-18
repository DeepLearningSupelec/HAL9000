package restrictedBoltzmann;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import mnistReader.MnistManager;

public class BatchManager {

	MnistManager learningManager;
	int batchSize = 10;
	int currentIndex; //As in MnistManager class, index starts at 1
	int dataSize;
	
	ArrayList<Integer> indexs;
	
	
	public BatchManager() throws IOException{
		
		this.learningManager = new MnistManager("src/train-images.idx3-ubyte","src/train-labels.idx1-ubyte");
		this.currentIndex = 1;
		this.indexs = new ArrayList<Integer>();
		this.loadBatchParameters();
	}
	
	public void setCurrent(int index){
		this.currentIndex = index;
		this.learningManager.setCurrent(indexs.get(index - 1));
	}
	
	public int readLabel() throws IOException{
		this.learningManager.setCurrent(this.indexs.get(this.currentIndex - 1));
		return this.learningManager.readLabel();
	}
	
	public double[] readImage1D() throws IOException{
		this.learningManager.setCurrent(this.indexs.get(this.currentIndex - 1));
		return learningManager.readImage1D();
	}
	
	public void loadBatchParameters() throws IOException{
		Path p = Paths.get("batchParameters.txt");
		
		int lineCpt = 0;
		for(String line : Files.readAllLines(p)) {
			System.out.println(lineCpt);
			if(lineCpt == 0){
				int partCpt =0;
				for (String part : line.split("\\s+")) {
					int temp = (Integer.valueOf(part) );
					this.indexs.add(temp);
					System.out.println(temp);
			        partCpt++;
			        
			    }
				this.dataSize = partCpt;
			}
			lineCpt++;
		}
		System.out.println("Batch Parameters loaded");
	}
	
	public int getSize(){
		return this.dataSize;
	}

}
