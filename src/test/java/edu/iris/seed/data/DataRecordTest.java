package edu.iris.seed.data;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.DataBufferInt;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import edu.iris.seed.io.DataRecordIterator;
import edu.iris.seed.io.SeedIOUtils;
import edu.iris.seed.record.DataRecord;
import edu.iris.seed.record.DecompressedDataRecord;
import edu.iris.seed.timeseries.TBufferedImage;

import edu.iris.seedcodec.CodecException;
import edu.iris.seedcodec.DecompressedData;
import edu.iris.timeseries.Segment;
import edu.iris.timeseries.Timeseries;

public class DataRecordTest {

	public BufferedImage bufferedImage() throws Exception {
		try (InputStream is = new FileInputStream(
				new File(DataRecordTest.class.getClassLoader().getResource("mseed/ADK.IU.2020.001").getFile()));) {

			int width = 600;
			int height = 300;
			double xscale = 0;
			double yscale = 0;

			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D imgGraphics2D = img.createGraphics();
			imgGraphics2D.setColor(Color.white);
			imgGraphics2D.fillRect(0, 0, width, height);
			imgGraphics2D.dispose();

			List<Timeseries> l = SeedIOUtils.toTimeseries(is, false);
			Timeseries ts = l.get(0);
			ts.getActualNumberOfSamples();
			ts.getStartTime();
			ts.getEndTime();
			ts.getMin();
			ts.getMax();

			xscale = (width * 1.0) / (ts.getActualNumberOfSamples() * 1.0);
			yscale = (height * 1.0) / (Math.abs(ts.getMin()) - Math.abs(ts.getMax()) * 1.0);

			DataBuffer dataBuffer = img.getRaster().getDataBuffer();
			int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
			for (Segment s : ts.getSegments()) {
				for (DecompressedDataRecord ddr : s.getData()) {
					float[] data = ddr.getRecord().getData();
					int sizew=data.length;
					for (int j = 0; j < sizew; j++) {
						int x = (int) (xscale * j);
						int y = (int) (yscale * data[j]);
						System.out.println(x+"     "+y);
						int pixelindex = (y * width + x) * 3;
						pixels[pixelindex + 1] = 255; // set the green byte
					}
				}
			}

			imgGraphics2D.setComposite(AlphaComposite.Src);
			imgGraphics2D.drawImage(img, 0, 0, null);
			return img;
		}
		
	}

	public static void main(String[] args) throws Exception{
		DataRecordTest r=new DataRecordTest();
		int width = 600;
		int height = 300;
		DataRecordTest t = new DataRecordTest();
		BufferedImage image = r.bufferedImage();

		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setSize(width, height);
		X panel = t.new X(image);
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	class X extends JPanel {
		BufferedImage image;

		X(BufferedImage image) {
			this.image = image;
			setBackground(Color.green);
		}

		/*
		 * @Override protected void paintComponent(Graphics g) {
		 * super.paintComponent(g); Graphics2D imageG =
		 * image.paintComponent(this.getWidth(), this.getHeight());
		 * g.drawImage(this.image, 0, 0, this); // draw onto component }
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this);
		}
	}

}
