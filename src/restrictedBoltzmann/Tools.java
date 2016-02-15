package restrictedBoltzmann;

public class Tools {

	/**
	 * <p>image1Dto2D</p>
	 * <p>Changes a one dimension table of double into a two dimension table with values that can produce a grey-scale image </p>   
	 * 
	 * 
	 * 
	 * @param image1D
	 * 			The table of double to transform
	 * @param xSize
	 * 			Size of x dimension in pixel	
	 * @param ySize
	 * 			Size of y dimension in pixel
	 * @return
	 */
	
	
	static public int[][] image1Dto2D(double[] image1D, int xSize, int ySize){
		//int size = (int)(Math.sqrt(image1D.length));
		int[][] image2D = new int[xSize][ySize];
		for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                image2D[i][j] = (int)Math.floor(image1D[i*ySize + j])*255;
            }
        }
		return image2D;
	}
	
	/**
	 * <p>image1Dto2D</p>
	 * <p>Changes a one dimension table of int into a two dimension table with values that can produce a grey-scale image </p>   
	 * 
	 * 
	 * 
	 * @param image1D
	 * 			The table of int to transform
	 * @param xSize
	 * 			Size of x dimension in pixel	
	 * @param ySize
	 * 			Size of y dimension in pixel
	 * @return
	 */
	static public int[][] image1Dto2D(int[] image1D, int xSize, int ySize){
		//int size = (int)(Math.sqrt(image1D.length));
		int[][] image2D = new int[xSize][ySize];
		for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                image2D[i][j] = image1D[i*ySize + j]*255;
            }
        }
		return image2D;
	}
	
	
}
