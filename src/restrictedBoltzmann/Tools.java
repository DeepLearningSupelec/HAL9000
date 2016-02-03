package restrictedBoltzmann;

public class Tools {

	
	
	static public int[][] image1Dto2D(double[] image1D, int xSize, int ySize){
		//int size = (int)(Math.sqrt(image1D.length));
		int[][] image2D = new int[xSize][ySize];
		for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                image2D[i][j] = (int)Math.floor(image1D[i*ySize + j]);
            }
        }
		return image2D;
	}
	
	
}
