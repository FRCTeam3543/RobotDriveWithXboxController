package team3543.vision;

// https://docs.opencv.org/3.4.4/javadoc/index.html

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.stream.*;

import org.opencv.core.*;
import org.opencv.core.Core.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.*;
import org.opencv.objdetect.*;

import team3543.vision.CenterDropDetection.DetectionResult;


public class CenterDropDetectionTest
{
    // straight-on file with the ball drop
    static final String DROP_FILE = "./GRIP/images/drop.jpeg";
    static final String DROP_FILE2 = "./GRIP/images/drop2.jpeg";
    static final String DROP_FILE3 = "./GRIP/images/perspective1.jpeg";
    static final String DROP_FILE_BAD = "./GRIP/images/drop_bad.jpeg";

    CenterDropDetection detector;

    @Before
    public void setUp()
    {
        detector = new CenterDropDetection();
    }

    @Test
    public void testDetection()
    {
        tryDetect(DROP_FILE, true);
        tryDetect(DROP_FILE2, true);
        tryDetect(DROP_FILE3, true);
        tryDetect(DROP_FILE_BAD, false);
    }

    void tryDetect(String filename, boolean shouldDetect)
    {
        // System.out.println("USER DIR = "+System.getProperty("user.dir"));
        Mat img = readImage(filename);
        assertNotNull(img);
        assertTrue(!img.empty());

        assertEquals("Width not 320: "+img.width(), 320, img.width());
        assertEquals("Height not 240: "+img.height(), 240, img.height());

        DetectionResult result = detector.detect(img);
        if (shouldDetect) {
            assertTrue("Detection failed when should have succeeded", result.detected);
        }
        else {
            assertTrue("Detection succeeded when should have failed", !result.detected);
        }
    }

    Mat readImage(String imageFile)
    {
        Mat img = Imgcodecs.imread(imageFile, Imgcodecs.IMREAD_COLOR | Imgcodecs.IMREAD_UNCHANGED);
        return img;
    }
}