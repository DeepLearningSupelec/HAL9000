import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

public class TestsOpenCV
{
   public static void main( String[] args )
   {	
	  int dim = 200;
	   
      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
      Mat mat = Mat.eye( dim, dim, CvType.CV_8UC1 );
      System.out.println( "mat = " + mat.dump() );
      Size size = new Size(dim,dim);
      
      VideoWriter vid = new VideoWriter();
      vid.open("D:/MyVideo.avi", -1, 1, size, false);
      System.out.println("Vidéo créée");
      
      Mat img = imread("D:\test.png");
      
      for(int i=0; i<60; i++){
    	  vid.write(img);
    	  System.out.println("Image "+i);
      }
      
      vid.release();
      System.out.println("Fin");
      
      /*http://opencv-srf.blogspot.fr/2011/09/saving-images-videos_16.html*/
      /*fourcc = choix du codec. quel code pour h264?*/
      /*http://docs.opencv.org/java/3.1.0/org/opencv/videoio/VideoWriter.html#write-org.opencv.core.Mat-*/
     /* //Path p = Paths.get(System.getProperty("user.home"),"Desktop", "im0" + i + ".png");
      V
      if ( !vid.isOpened() ) //if not initialize the VideoWriter successfully, exit the program
      {
           System.out.println("fuck");
      }


      */
      
   }
}