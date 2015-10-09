import java.io.*;

public class Analytics {
	
	/* Attributes */
	java.lang.String location;
	java.lang.String name;
	java.lang.String extension;
	FileWriter file;
	
	
	/* Methods */
	//Initialisation des attributs
	public Analytics(java.lang.String location, java.lang.String name, java.lang.String extension) throws IOException {
		
		this.location = location;
		this.name = name;
		this.extension = extension;
		this.file = new FileWriter(location + '\\' + name + '.' + extension);
		
	}
	
	//Relacher la memoire
	public void close() throws IOException {
		this.file.close();
	}
	
	//Ecrire une ligne complete et passer à la ligne
	public void newLine(java.lang.String name, Double[] data) throws IOException {
		
		this.file.write(name + ";");
		
		for(int i=0; i<data.length+1; i++) {
			this.file.write(data[i] + ";");
		};
		this.file.write("\n");
		
	}	
}
