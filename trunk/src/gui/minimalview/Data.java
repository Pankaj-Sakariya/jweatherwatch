package gui.minimalview;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.ACCUWeather.CurrentWeather;
import net.ACCUWeather.ForecastWeather;
import net.ACCUWeather.Weather;

public class Data extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel_Day = null;
	private JLabel jLabel_Text = null;
	private JLabel jLabel_Temp = null;
	private JLabel jLabel_Precipitation = null;
	private JLabel jLabel_Wind = null;
	private int size = 100;
	private JLabel jLabel_Humidity = null;
	/**
	 * This is the default constructor
	 */
	public Data(int size) {
		super();
		this.size = size;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel_Humidity = new JLabel();
		jLabel_Precipitation = new JLabel();
		jLabel_Wind = new JLabel();
		jLabel_Temp = new JLabel();
		jLabel_Temp.setToolTipText("Teprature");
		jLabel_Text = new JLabel();
		jLabel_Day = new JLabel();
		this.setSize(100, 100);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(jLabel_Day, null);
		this.add(jLabel_Text, null);
		this.add(jLabel_Temp, null);
		this.add(jLabel_Precipitation, null);
		this.add(jLabel_Wind, null);
		this.add(jLabel_Humidity, null);
	}

	public void updateWeather(Weather weather) {
		if (weather instanceof ForecastWeather) {
			
			jLabel_Day.setText(((ForecastWeather) weather).getDay()
					.getDayCode());
			jLabel_Humidity.setText(((ForecastWeather) weather).getThunderstormProbability()+"%");
			jLabel_Humidity.setToolTipText("Thunder Storm Probability");
		} else {
			jLabel_Day.setText("Current");
	
			jLabel_Humidity.setText(((CurrentWeather) weather)
					.getHumidity());
			jLabel_Humidity.setToolTipText("Humidity");
			
		}
		jLabel_Precipitation.setText( weather
				.getPrecipitation().toString());
		jLabel_Precipitation.setToolTipText("Precipitation");

		
		// Temp
		jLabel_Temp.setText(weather.getTemperature().toString());
		// tooltips
		jLabel_Day.setToolTipText(jLabel_Day.getText());
	
		
		
		jLabel_Text.setText(weather.getWeathertext());
		jLabel_Text.setToolTipText(jLabel_Text.getText());
		jLabel_Wind
				.setText(weather.getWinddirection() + weather.getWindspeed());
		jLabel_Wind.setToolTipText("Wind");
	}

	public void setBoxSize(int i) {
		this.size = i;
	}

	public int getBoxSize() {
		return this.size;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
