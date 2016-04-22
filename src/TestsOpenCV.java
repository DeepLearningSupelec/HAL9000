import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoWriter;

public class TestsOpenCV
{
   public static void main( String[] args )
   {	
	  System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	  
	  Mat img = Imgcodecs.imread("D:\test.png");
	  if(img==null){
		  System.out.println("Fuck!-1");
	  }
	  int dim = img.dims();
      Size size = new Size(dim,dim);
      
      VideoWriter vid = new VideoWriter();
      vid.open("D:\\MyVideo.avi", -1, 60, size, true);
      System.out.println("Vidéo créée");
      
      for(int i=0; i<120; i++){
    	  img = Imgcodecs.imread("D:\\test.png");
    	  vid.write(img);
      }
      
      vid.release();
      System.out.println("Fin");
      
      /*http://opencv-srf.blogspot.fr/2011/09/saving-images-videos_16.html*/
      /*fourcc = choix du codec. quel code pour h264?*/
      /*http://docs.opencv.org/java/3.1.0/org/opencv/videoio/VideoWriter.html#write-org.opencv.core.Mat-*/
     /* //Path p = Paths.get(System.getProperty("user.home"),"Desktop", "im0" + i + ".png");


      */
      
   }
}