package edu.iris.seed.timeseries;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.io.SeedIOUtils;
import edu.iris.seed.record.DecompressedDataRecord;
import edu.iris.timeseries.Segment;
import edu.iris.timeseries.TimeseriesBufferedImage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * An example of a time series chart. For the most part, default settings are
 * used, except that the renderer is modified to show filled shapes (as well as
 * lines) at each data point.
 */
// public class TimeSeriesChartDemo1 extends ApplicationFrame {

@SuppressWarnings("restriction")
public class TimeSeriesChartDemo extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Line Chart Sample");

		String fileName = "/users/Suleiman/seed-files/fdsnws-dataselect_2020-01-31t05_45_12z.mseed";
		try (FileInputStream fis = new FileInputStream(new File(fileName));) {
			List<TimeseriesBufferedImage> l = SeedIOUtils.toTimeseriesBufferedImage(fis, true,600,300);
			TimeseriesBufferedImage i = l.get(0);
			i.showRecordLine(true);

			final NumberAxis xAxis = new NumberAxis();
			final NumberAxis yAxis = new NumberAxis();
			xAxis.setLabel("Time");

			final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
			lineChart.setTitle(fileName);
			lineChart.setCreateSymbols(false);
			lineChart.setAnimated(false);
			lineChart.getStyleClass().add("thin-chart");

			

			XYChart.Series<Number,Number> series = new XYChart.Series<>();

			List<XYChart.Data<Number,Number>> chartDataList = new ArrayList<>();
			int cnt = 0;
			for (Segment segment : i.getSegments()) {
				List<DecompressedDataRecord> dl = segment.getData();
				for (DecompressedDataRecord d : dl) {
					d.getStartTime();
				
					float[] floats = d.getRecord().getData();
					for (float f : floats) {
						chartDataList.add(new XYChart.Data<>(cnt++, f));
					}
				}

			}
			series.getData().addAll(chartDataList);
			Scene scene = new Scene(lineChart, 800, 600);
			lineChart.getData().add(series);
			stage.setScene(scene);

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		javafx.scene.image.Image ii=null;
	}

	public static void main(String[] args) {
		launch(args);
	}

}