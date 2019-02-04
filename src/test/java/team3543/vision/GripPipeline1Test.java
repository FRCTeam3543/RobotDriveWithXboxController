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


public class GripPipeline1Test
{
    // straight-on file with the ball drop
    static final String DROP_FILE = "./GRIP/images/drop.jpeg";
    GripPipeline1 pipeline;

    @Before
    public void setUp()
    {
        pipeline = new GripPipeline1();
    }

    @Test
    public void testLineDetection()
    {
        // System.out.println("USER DIR = "+System.getProperty("user.dir"));
        Mat img = readImage(DROP_FILE);
        assertNotNull(img);
        assertTrue(!img.empty());

        assertEquals("Width not 320: "+img.width(), 320, img.width());
        assertEquals("Height not 240: "+img.height(), 240, img.height());

        pipeline.process(img);

        // this is to check that things read in correctly
        // Imgcodecs.imwrite("out.jpeg", img);
        // This baselines things - the image we're testing has 11 lines in the GRIP GUI
        assertEquals(String.format("Find lines only found %d", pipeline.findLinesOutput().size()),
                8, // there are 8 lines in the top-middle
                pipeline.findLinesOutput().size());

        System.out.println(pipeline.filterLinesOutput().stream().map(line ->
            String.format("[%f, %f] -> [%f, %f]", line.x1, line.y1, line.x2, line.y2))
                .collect(Collectors.joining("\n"))
        );

        assertEquals("Didn't find 2 lines in "+DROP_FILE, 2, pipeline.filterLinesOutput().size());
    }

    Mat readImage(String imageFile)
    {
        Mat img = Imgcodecs.imread(imageFile, Imgcodecs.IMREAD_COLOR | Imgcodecs.IMREAD_UNCHANGED);
        return img;
    }
}