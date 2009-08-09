package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import lib.Utils;
import net.ACCUWeather.CurrentWeather;
import net.ACCUWeather.ForecastWeather;
import net.ACCUWeather.Location;
import net.ACCUWeather.Weather;

public class WeatherPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	private ImageBox imageBox = null;
	private JLabel jLabel_Location = null;
	private JLabel jLabel_text = null;
	private JLabel jLabel_Temp = null;
	private JLabel jLabel_RealFeal = null;
	private JLabel jLabel_Wind = null;
	Utils utils = new Utils();  //  @jve:decl-index=0:
	private JLabel jLabel_other = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel_Other_Lable = null;
	private boolean timeIsRunning = false;
	private String url=null;  //  @jve:decl-index=0:
	private String iconEnding=null;  //  @jve:decl-index=0:
	private Location location=null;

	/**
	 * This is the default constructor
	 */	

	public WeatherPanel() {
		super();
		initialize();
	
	}
	private String getIconEnding() {
		if(iconEnding==null){	
			if(new File("iconset/01.gif").exists())
				iconEnding=".gif";
			else if(new File("iconset/01.jpg").exists())
				iconEnding=".jpg";	
			else
				iconEnding=".png";
		}
		return iconEnding;
	}
	public void updateWeather(Location location,Weather weather) {
		this.location=location;
		url = weather.getUrl();
		jLabel_Location.setText(location.toString());
		jLabel_Location.setToolTipText(location.toString());
		imageBox.setImage(utils.imageLodaer("iconset/"
				+ weather.getWeathericon() + getIconEnding()));
		jLabel_text.setText(weather.getWeathertext());
		jLabel_text.setToolTipText(weather.getWeathertext());
		jLabel.setText("Temprature:");
		jLabel_Temp.setText(weather.getTemperature().toString());
		jLabel1.setText("RealFeal®:");
		jLabel_RealFeal.setText(weather.getRealfeel().toString());
		jLabel2.setText("Wind:");		
		jLabel_Wind.setText(weather.getWinddirection() + " "
				+ weather.getWindspeed());
		if (weather instanceof CurrentWeather) {
			jLabel_Other_Lable.setText("Current Time:");
			if (!timeIsRunning)
				new Thread(this).start();
			timeIsRunning = true;
			updateTime();
		}
		if (weather instanceof ForecastWeather) {
			jLabel_text.setText(( (ForecastWeather)weather).getTxtshort().toString()); 
			jLabel_Location.setText("Day: "+((ForecastWeather) weather).getDay().getDayNR()+" "+((ForecastWeather) weather).getDay()
					.getDayCode()
					+ " " + ((ForecastWeather) weather).getDay().getDate());
			jLabel_Location.setToolTipText("Day: "+((ForecastWeather) weather).getDay().getDayNR()+" "+((ForecastWeather) weather).getDay()
					.getDayCode()
					+ " " + ((ForecastWeather) weather).getDay().getDate());
			jLabel_Other_Lable.setText("Precipitation: ");
			jLabel_Other_Lable.setToolTipText("Rain: "
					+ ((ForecastWeather) weather).getRain() + ", Snow: "
					+ ((ForecastWeather) weather).getSnow() + ", Ice: "
					+ ((ForecastWeather) weather).getIce()
					+ ", Thunderstorm Probability: "
					+ ((ForecastWeather) weather).getThunderstormProbability()
					+ "%");
			jLabel_other.setText(((ForecastWeather) weather).getPrecipitation()
					.toString());
			jLabel_other.setToolTipText("Rain: "
					+ ((ForecastWeather) weather).getRain() + ", Snow: "
					+ ((ForecastWeather) weather).getSnow() + ", Ice: "
					+ ((ForecastWeather) weather).getIce()
					+ ", Thunderstorm Probability: "
					+ ((ForecastWeather) weather).getThunderstormProbability()
					+ "%");
			
		}
		

	}

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

	public void run() {
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

	private void updateTime() {
		Calendar c=location.getCurrentTime();
		jLabel_other.setText(c.get(Calendar.HOUR_OF_DAY) + ":"
				+ c.get(Calendar.MINUTE));
	}

} // @jve:decl-index=0:visual-constraint="10,10"
