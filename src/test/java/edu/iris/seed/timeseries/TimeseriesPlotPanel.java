package edu.iris.seed.timeseries;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import edu.iris.timeseries.TimeseriesBufferedImage;

public class TimeseriesPlotPanel extends JPanel {

	private TimeseriesBufferedImage image;

	private double zoom;

	public TimeseriesPlotPanel(TimeseriesBufferedImage image) {
		this.image = image;
		/*
		 * this.addMouseWheelListener(new MouseWheelListener() { public void
		 * mouseWheelMoved(MouseWheelEvent e) { int notches = e.getWheelRotation();
		 * double temp = zoom - (notches * 0.2); // minimum zoom factor is 1.0 temp =
		 * Math.max(temp, 1.0); if (temp != zoom) { zoom = temp; try { zoom(temp, temp);
		 * } catch (Exception e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } } } });
		 */
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	public static BufferedImage imageToBufferedImage(Image img) {
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB); // TYPE_4BYTE_ABGR
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, null, null);

		return bi;
	}

	@Override
	protected void paintComponent(Graphics g) {
		System.out.println("????????????????????");
		super.paintComponent(g); 
		
		Graphics2D imageG=image.paintComponent(this.getWidth(),this.getHeight());
	   // Graphics2D g2 = (Graphics2D) g;
	    //Graphics imageG2 = this.image.createGraphics();
	    
	    g.drawImage(this.image, 0, 0, this);  // draw onto component
	    
		System.out.println(image.getNetworkCode());
	}

}
