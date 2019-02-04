package team3543.vision;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import edu.wpi.first.wpilibj.vision.VisionPipeline;

import org.opencv.core.*;
import org.opencv.core.Core.*;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.*;
import org.opencv.objdetect.*;

/**
* GripPipeline class.
*
* <p>An OpenCV pipeline generated by GRIP.
*
* @author GRIP
*/
public class GripPipeline1 implements VisionPipeline {
	private static final Logger LOGGER = Logger.getLogger("GRIP Pipeline 1");

	//Settings, instead of having them inline
	double[] rgbThresholdRed = {126.12410071942446, 255.0};
	double[] rgbThresholdGreen = {0.0, 255.0};
	double[] rgbThresholdBlue = {233.90287769784172, 255.0};

	// blur
	BlurType blurType = BlurType.get("Box Blur");
	double blurRadius = 0.0;

	// Filter lines
	double filterLinesMinLength = 5.0;
	double[] filterLinesAngle = {197, 339};

	int imageHeight = 240;

	//Outputs
	private Mat rgbThresholdOutput = new Mat();
	private Mat blurOutput = new Mat();
	private ArrayList<Line> findLinesOutput = new ArrayList<Line>();
	private ArrayList<Line> filterLinesOutput = new ArrayList<Line>();

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * This is the primary method that runs the entire pipeline and updates the outputs.
	 */
	@Override	public void process(Mat source0) {
		// Step RGB_Threshold0:
		Mat rgbThresholdInput = source0;
		rgbThreshold(rgbThresholdInput, rgbThresholdRed, rgbThresholdGreen, rgbThresholdBlue, rgbThresholdOutput);

		// Step Blur0:
		Mat blurInput = rgbThresholdOutput;
		blur(blurInput, blurType, blurRadius, blurOutput);

		// Step Find_Lines0:
		Mat findLinesInput = blurOutput;
		findLines(findLinesInput, findLinesOutput);

		// Step Filter_Lines0:
		ArrayList<Line> filterLinesLines = findLinesOutput;

		filterLines(filterLinesLines, filterLinesMinLength, filterLinesAngle, filterLinesOutput);

	}

	/**
	 * This method is a generated getter for the output of a RGB_Threshold.
	 * @return Mat output from RGB_Threshold.
	 */
	public Mat rgbThresholdOutput() {
		return rgbThresholdOutput;
	}

	/**
	 * This method is a generated getter for the output of a Blur.
	 * @return Mat output from Blur.
	 */
	public Mat blurOutput() {
		return blurOutput;
	}

	/**
	 * This method is a generated getter for the output of a Find_Lines.
	 * @return ArrayList<Line> output from Find_Lines.
	 */
	public ArrayList<Line> findLinesOutput() {
		return findLinesOutput;
	}

	/**
	 * This method is a generated getter for the output of a Filter_Lines.
	 * @return ArrayList<Line> output from Filter_Lines.
	 */
	public ArrayList<Line> filterLinesOutput() {
		return filterLinesOutput;
	}


	/**
	 * Segment an image based on color ranges.
	 * @param input The image on which to perform the RGB threshold.
	 * @param red The min and max red.
	 * @param green The min and max green.
	 * @param blue The min and max blue.
	 * @param output The image in which to store the output.
	 */
	private void rgbThreshold(Mat input, double[] red, double[] green, double[] blue,
		Mat out) {
		Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2RGB);
		Core.inRange(out, new Scalar(red[0], green[0], blue[0]),
			new Scalar(red[1], green[1], blue[1]), out);
	}

	/**
	 * An indication of which type of filter to use for a blur.
	 * Choices are BOX, GAUSSIAN, MEDIAN, and BILATERAL
	 */
	enum BlurType{
		BOX("Box Blur"), GAUSSIAN("Gaussian Blur"), MEDIAN("Median Filter"),
			BILATERAL("Bilateral Filter");

		private final String label;

		BlurType(String label) {
			this.label = label;
		}

		public static BlurType get(String type) {
			if (BILATERAL.label.equals(type)) {
				return BILATERAL;
			}
			else if (GAUSSIAN.label.equals(type)) {
			return GAUSSIAN;
			}
			else if (MEDIAN.label.equals(type)) {
				return MEDIAN;
			}
			else {
				return BOX;
			}
		}

		@Override
		public String toString() {
			return this.label;
		}
	}

	/**
	 * Softens an image using one of several filters.
	 * @param input The image on which to perform the blur.
	 * @param type The blurType to perform.
	 * @param doubleRadius The radius for the blur.
	 * @param output The image in which to store the output.
	 */
	private void blur(Mat input, BlurType type, double doubleRadius,
		Mat output) {
		int radius = (int)(doubleRadius + 0.5);
		int kernelSize;
		switch(type){
			case BOX:
				kernelSize = 2 * radius + 1;
				Imgproc.blur(input, output, new Size(kernelSize, kernelSize));
				break;
			case GAUSSIAN:
				kernelSize = 6 * radius + 1;
				Imgproc.GaussianBlur(input,output, new Size(kernelSize, kernelSize), radius);
				break;
			case MEDIAN:
				kernelSize = 2 * radius + 1;
				Imgproc.medianBlur(input, output, kernelSize);
				break;
			case BILATERAL:
				Imgproc.bilateralFilter(input, output, -1, radius, radius);
				break;
		}
	}

	public static class Line {
		public final double x1, y1, x2, y2;
		public Line(double x1, double y1, double x2, double y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		public double lengthSquared() {
			return Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
		}
		public double length() {
			return Math.sqrt(lengthSquared());
		}
		public double angle() {
			return Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
		}

		@Override
		public String toString()
		{
			return String.format("Line (%.2f, %.2f) -> (%.2f, %.2f) [L=%.2f A=%.2f]",
								x1,y1,x2,y2, length(), angle());
		}
	}
	/**
	 * Finds all line segments in an image.
	 * @param input The image on which to perform the find lines.
	 * @param lineList The output where the lines are stored.
	 */
	private void findLines(Mat input, ArrayList<Line> lineList) {
		int upperHalf = input.height() / 2;
		int middleHalfStart = input.width() / 4;
		int middleHalfEnd = input.width() * 3 / 4;

		Line line;
		final LineSegmentDetector lsd = Imgproc.createLineSegmentDetector();
		final Mat lines = new Mat();
		lineList.clear();
		if (input.channels() == 1) {
			lsd.detect(input, lines);
		} else {
			final Mat tmp = new Mat();
			Imgproc.cvtColor(input, tmp, Imgproc.COLOR_BGR2GRAY);
			lsd.detect(tmp, lines);
		}
		if (!lines.empty()) {
			for (int i = 0; i < lines.rows(); i++) {
				//SPECIAL - only add if y's are both in upper half of screen
				line = new Line(lines.get(i, 0)[0], lines.get(i, 0)[1],
									lines.get(i, 0)[2], lines.get(i, 0)[3]);
				if (line.y1 < upperHalf && line.y2 < upperHalf
					&& line.x1 > middleHalfStart && line.x1 < middleHalfEnd
					&& line.x2 > middleHalfStart && line.x2 < middleHalfEnd
				) {
					lineList.add(line);
				}
				else {
					LOGGER.info("Skip "+line);
				}
			}
		}
	}

	/**
	 * Filters out lines that do not meet certain criteria.
	 * @param inputs The lines that will be filtered.
	 * @param minLength The minimum length of a line to be kept.
	 * @param angle The minimum and maximum angle of a line to be kept.
	 * @param outputs The output lines after the filter.
	 */
	private void filterLines(List<Line> inputs,double minLength,double[] angle,
		List<Line> outputs) {
			double minLengthSquared = Math.pow(minLength,2); // perf
		LOGGER.info(String.format("minLength = %f, angle = [%.2f to %.2f]", minLength, angle[0], angle[1])
		);
		outputs.clear();
		outputs.addAll(inputs.stream()
				.map(line -> {
					boolean exceedmax = line.lengthSquared() > minLengthSquared;
					boolean inAngle = (line.angle() >= angle[0] && line.angle() <= angle[1])
							|| (line.angle()+180 >= angle[0] && line.angle()+180 <= angle[1]);

					LOGGER.info(String.format("\nLine LSQ=%.2f (vs. %.2f)\nangle (%.2f or %.2f) (vs. %.2f to %.2f)\n[%f.2f, %.2f] to [%.2f, %.2f]\nlength=%b angle=%b\n\n",
					line.lengthSquared(), minLengthSquared,
					line.angle(), line.angle()+180.0, angle[0], angle[1],
					line.x1, line.y1, line.x2, line.y2,
					exceedmax, inAngle
				));
					return line;
				})
				.filter(line -> line.lengthSquared() >= minLengthSquared)
				.filter(line -> (line.angle() >= angle[0] && line.angle() <= angle[1])
				|| (line.angle() + 180.0 >= angle[0] && line.angle() + 180.0 <= angle[1]))
				.collect(Collectors.toList()));
	}

}

