package gui.minimalview;

import gui.ImageBox;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.SettingsReader;
import net.Utils;
import net.ACCUWeather.Location;

public class MininmalPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ImageBox[] boxes = new ImageBox[6];
	private Data data = null;

	private Utils utils = new Utils();
	private JLabel jLabel_Title = null;

	private int size = -1;
	private Location weatherLocation = null;
	private boolean timeIsRunning = false;
	private boolean shifted = false;
	private int selected = -1;

	/**
	 * This is the default constructor
	 * 
	 * @param location
	 */
	public MininmalPanel(final Location location, int size) {
		super();
		this.weatherLocation = location;
		this.size = size;
		initialize();

		this.setSize((size + 5) * 7, size + 30);
		this.setVisible(true);

		new Thread("Time" + location.getCity()) {
			@Override
			public void run() {
				timeIsRunning = true;
				while (timeIsRunning) {
					jLabel_Title.setText(location.getCity() + " "
							+ location.getCurrentTime());
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("TimeThread stopped");
			}
		}.start();
		arange(0);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel_Title = new JLabel();
		jLabel_Title.setBounds(new Rectangle(0, 0, 241, 16));
		this.setSize(420, 92);
		this.setLayout(null);
		for (int i = 0; i < 6; ++i) {
			this.add(getImageBox(i), null);

		}
		this.add(getData(0), null);
		this.add(jLabel_Title, null);

	}

	/**
	 * This method initializes imageBox
	 * 
	 * @return gui.ImageBox
	 */
	private ImageBox getImageBox(final int i) {
		if (boxes[i] == null) {
			boxes[i] = new ImageBox();
			if (i == 0) {
				boxes[i].setImage(utils.imageLodaer(SettingsReader.getIconpPath()
						+ weatherLocation.getCurrentWeather().getWeathericon()
						+ ".png"));
				boxes[i].setToolTipText("Current");
			} else {
				boxes[i].setImage(utils.imageLodaer("iconset/"
						+ weatherLocation.getFiveDayForecast().getDay(i)
								.getDay().getWeathericon() + ".png"));
				boxes[i].setToolTipText(weatherLocation.getFiveDayForecast()
						.getDay(i).getDayCode());
			}
			boxes[i].addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					arange(i);
				}
			});
		}
		return boxes[i];
	}

	/**
	 * This method initializes data
	 * 
	 * @return gui.simple.Data
	 */
	private Data getData(int i) {
		if (data == null) {
			data = new Data(size);
			data.setLocation(new Point(60, 30));
			data.setSize(new Dimension(50, 46));
		}
		return data;
	}

	private void arange(int nr) {
		if (selected == nr) {
	
			 if (nr == 0) {
			 Utils.visitURL(weatherLocation.getCurrentWeather().getUrl());
			 return;
			 }
			 Utils.visitURL(weatherLocation.getFiveDayForecast().getDay(nr)
			 .getUrl());
			 return;
		}
		selected = nr;
		int x = size + 5;
		for (int i = 0; i <= nr; ++i) {
			boxes[i].setBounds(x * i, 30, size, size);
		}
		if (nr == 0)
			data.updateWeather(weatherLocation.getCurrentWeather());
		else
			data.updateWeather(weatherLocation.getFiveDayForecast().getDay(nr)
					.getDay());
		int y = 30;
		if (shifted)
			y = 15;
		data.setBounds(x * (nr + 1), y, size, size + 30 - y);
		for (int i = nr + 1; i < boxes.length; ++i) {
			boxes[i].setBounds(x * (i + 1), 30, size, size);
		}
	}

	public void stop() {
		this.timeIsRunning = false;
	}

	public void setShifted(boolean b) {
		if (b != shifted) {
			this.shifted = b;
			if (b)
				data.setBoxSize(size + 15);
			arange(0);
		}
	}

	public Location getWeatherLocation() {
		return weatherLocation;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
