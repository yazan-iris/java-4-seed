package edu.iris.seed.timeseries;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.iris.seed.io.SeedIOUtils;
import edu.iris.timeseries.TimeseriesBufferedImage;

public class TimeseriesBufferedImageTest {

	public static void main(String[] args) throws Exception {

		int width = 500;
		int height = 300;
		try (InputStream is = new FileInputStream(new File(
				TimeseriesBufferedImageTest.class.getClassLoader().getResource("mseed/ADK.IU.2020.001").getFile()));) {
			List<TimeseriesBufferedImage> l = SeedIOUtils.toTimeseriesBufferedImage(is, false, width, height);

			TimeseriesBufferedImage bi = l.get(0);
			TimeseriesPlotPanel panel = new TimeseriesPlotPanel(bi);

			JFrame frame = new JFrame();
			frame.setLayout(new BorderLayout());
			frame.setSize(width, height);
			frame.getContentPane().add(panel, BorderLayout.CENTER);

			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			/*
			 * BufferedImage bi = new BufferedImage(panel.getWidth(), panel.getHeight(),
			 * BufferedImage.TYPE_INT_ARGB); Graphics2D g2d = bi.createGraphics();
			 * panel.paintAll(g2d); g2d.dispose();
			 * 
			 * try { ImageIO.write(tbi, "png", new File("/Users/Suleiman/images/111.png"));
			 * } catch (IOException e) { e.printStackTrace(); }
			 */
		}
	}
}
