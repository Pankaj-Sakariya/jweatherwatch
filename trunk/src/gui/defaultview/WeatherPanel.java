package gui.defaultview;

import gui.ImageBox;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import net.SettingsReader;
import net.Utils;
import net.ACCUWeather.CurrentWeather;
import net.ACCUWeather.ForecastWeather;
import net.ACCUWeather.Location;
import net.ACCUWeather.Weather;

public class WeatherPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ImageBox imageBox = null;
	private JLabel jLabel_Location = null;
	private JLabel jLabel_text = null;
	private JLabel jLabel_Temp = null;
	private JLabel jLabel_RealFeal = null;
	private JLabel jLabel_Wind = null;
	Utils utils = new Utils(); // @jve:decl-index=0:
	private JLabel jLabel_other = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel_Other_Lable = null;
	private boolean timeIsRunning = false;
	private String url = null; // @jve:decl-index=0:
	private Location weatherLocation = null;

	/**
	 * This is the default constructor
	 */

	public WeatherPanel() {
		super();
		initialize();

	}

	public void updateWeather(final Location location, Weather weather) {
		url = weather.getUrl();
		jLabel_Location.setText(location.toString());
		jLabel_Location.setToolTipText(location.toString());
		imageBox.setImage(utils.imageLodaer(SettingsReader.getInstance().getIconpPath()
				+ weather.getWeathericon() + ".png"));
		jLabel_text.setText(weather.getWeathertext());
		jLabel_text.setToolTipText(weather.getWeathertext());
		jLabel.setText("Temprature:");
		jLabel_Temp.setText(weather.getTemperature().toString());
		jLabel1.setText("RealFeal\u00AE:");
		jLabel_RealFeal.setText(weather.getRealfeel().toString());
		jLabel2.setText("Wind:");
		jLabel_Wind.setText(weather.getWinddirection() + " "
				+ weather.getWindspeed());
		if (weather instanceof CurrentWeather) {
			jLabel_Other_Lable.setText("Current Time:");
			this.weatherLocation = location;
			if (!timeIsRunning)
				new Thread("Time" + location.getCity()) {
					public void run() {
						timeIsRunning = true;
						while (timeIsRunning) {
							updateTime();
							try {
								Thread.sleep(60000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				}.start();
			else
				updateTime();

		}
		if (weather instanceof ForecastWeather) {
			jLabel_text.setText(((ForecastWeather) weather).getTxtshort()
					.toString());
			jLabel_Location.setText("Day: "
					+ ((ForecastWeather) weather).getDay().getDayNR() + " "
					+ ((ForecastWeather) weather).getDay().getDayCode() + " "
					+ ((ForecastWeather) weather).getDay().getDate());
			jLabel_Location.setToolTipText("Day: "
					+ ((ForecastWeather) weather).getDay().getDayNR() + " "
					+ ((ForecastWeather) weather).getDay().getDayCode() + " "
					+ ((ForecastWeather) weather).getDay().getDate());
			jLabel_Other_Lable.setText("Precipitation: ");
			String tooltip = "Rain: " + ((ForecastWeather) weather).getRain()
					+ ", Thunderstorm Probability: "
					+ ((ForecastWeather) weather).getThunderstormProbability()
					+ "%" + ", Snow: " + ((ForecastWeather) weather).getSnow()
					+ ", Ice: " + ((ForecastWeather) weather).getIce();
			jLabel_Other_Lable.setToolTipText(tooltip);
			jLabel_other.setText(((ForecastWeather) weather).getPrecipitation()
					.toString());
			jLabel_other.setToolTipText(tooltip);

		}
		if (!imageBox.isVisible())
			for (Component c : getComponents())
				c.setVisible(true);

	}

	private void updateTime() {
		jLabel_other.setText(weatherLocation.getCurrentTime());

	};

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */

	private void initialize() {
		jLabel_Other_Lable = new JLabel();
		jLabel_Other_Lable.setBounds(new Rectangle(105, 105, 91, 16));
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(105, 90, 91, 16));
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(105, 75, 91, 16));
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(105, 60, 91, 16));
		jLabel_other = new JLabel();
		jLabel_other.setBounds(new Rectangle(195, 105, 91, 16));
		jLabel_Wind = new JLabel();
		jLabel_Wind.setBounds(new Rectangle(195, 90, 91, 16));
		jLabel_RealFeal = new JLabel();
		jLabel_RealFeal.setBounds(new Rectangle(195, 75, 91, 16));
		jLabel_Temp = new JLabel();
		jLabel_Temp.setBounds(new Rectangle(195, 60, 91, 16));
		jLabel_text = new JLabel();
		jLabel_text.setBounds(new Rectangle(105, 45, 181, 16));
		jLabel_Location = new JLabel();
		jLabel_Location.setBounds(new Rectangle(15, 15, 271, 16));
		jLabel_Location.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_Location.setFont(new Font("Dialog", Font.BOLD, 14));
		this.setLayout(null);
		this.add(getImageBox(), null);
		this.add(jLabel_Location, null);
		this.add(jLabel_text, null);
		this.add(jLabel_Temp, null);
		this.add(jLabel_RealFeal, null);
		this.add(jLabel_Wind, null);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.setPreferredSize(new Dimension(300, 136));
		this.setSize(new Dimension(300, 136));
		this.setVisible(true);
		this.add(jLabel_other, null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(jLabel2, null);
		this.add(jLabel_Other_Lable, null);
	}

	/**
	 * This method initializes imageBox
	 * 
	 * @return lib.ImageBox
	 */
	public ImageBox getImageBox() {
		if (imageBox == null) {
			imageBox = new ImageBox();
			imageBox.setBounds(new Rectangle(15, 45, 76, 76));
			imageBox.setToolTipText("Visit AccuWeather Forecast");
			imageBox.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Utils.visitURL(url);
				}
			});

		}
		return imageBox;
	}

	public void clear() {
		for (Component c : getComponents())
			c.setVisible(false);
	}

	public void setTimeIsRunning(boolean timeIsRunning) {
		this.timeIsRunning = timeIsRunning;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
