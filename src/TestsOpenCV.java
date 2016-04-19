
import java.nio.file.Path;
import java.nio.file.Paths;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

public class TestsOpenCV
{
   public static void main( String[] args )
   {
      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
      Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
      System.out.println( "mat = " + mat.dump() );
      
      /*http://opencv-srf.blogspot.fr/2011/09/saving-images-videos_16.html*/
      /*fourcc = choix du codec. quel code pour h264?*/
      /*http://docs.opencv.org/java/3.1.0/org/opencv/videoio/VideoWriter.html#write-org.opencv.core.Mat-*/
      Size size = new Size(2,2);
      VideoWriter vid = new VideoWriter("C:/MyVideo.avi", CV_FOURCC('P','I','M','1'), 20, size);
      for(int i=0; i<4; i++){
    	  Path p = Paths.get(System.getProperty("user.home"),"Desktop", "im0" + i + ".png");
    	  Mat im = /* conversion image en Mat */;
    	  vid.write(im);
    	  
      }
      
   }
}