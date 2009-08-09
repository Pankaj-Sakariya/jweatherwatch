package gui;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemTray;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EtchedBorder;

import net.ACCUWeatherFetcher;
import net.NotificationConnector;
import net.NotificationThread;
import net.Utils;
import net.ACCUWeather.Location;
import net.ACCUWeather.LocationList;
import net.ACCUWeather.WeatherSubtypes.Day;
import net.ACCUWeather.WeatherSubtypes.UnitCode;

public class Gui extends JFrame {
	public static final  String name="JWeatherWatch";
	public static final String version="v1.1";
	

	private NotificationThread notificationthread=null;  //  @jve:decl-index=0:
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JScrollBar jScrollBar = null;
	private LocationList locations = new LocationList(); // @jve:decl-index=0:
	private JTextField jTextField_Serch = null;
	private JButton jButton_Search = null;
	private JComboBox jComboBox_Results = null;
	private JButton jButton_Add = null;
	private WeatherPanel weatherPanel = null;

	private JLabel jLabel = null;
	private WeatherPanel weatherPanel_Day1 = null;
	private WeatherPanel weatherPanel_Day2 = null;
	private JPanel jPanel_Forecast = null;
	private JScrollBar jScrollBar1_Forecast = null;
	private ImageBox imageBox_Logo = null;

	Utils utils = new Utils(); // @jve:decl-index=0:
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JPanel jPanel_Units = null;
	private JRadioButton jRadioButton_English = null;
	private JRadioButton jRadioButton_Metric = null;
	private JPanel jPanel = null;
	private JLabel jLabel4 = null;
	private JComboBox jComboBox_QuickSwitch = null;
	private JButton jButton_RemoveLocation = null;
	private JToggleButton jToggleButton_Day = null;
	private JToggleButton jToggleButton_Night = null;
	private ImageBox imageBox_Loading = null;
	private Splash splash = null; // @jve:decl-index=0:visual-constraint="277,470"
	private boolean adding;
	private WeatherTrayIcon trayIcon=null;
	private Settings settings = null;  //  @jve:decl-index=0:visual-constraint="-3,68"

	/**
	 * This is the default constructor
	 */
	public Gui() {
		super();	
		initialize();
		NotificationConnector.initialize(getTryIcon());
		NotificationConnector.sendNotification(null, "Snarl Weather Watch",
		"Snarl Weather Watch succsessfully registered","");
		load();
		setNotificationthread(new NotificationThread(locations,30));

	}

	private void load() {
		LocationList loca = ACCUWeatherFetcher.load();
		for (Location l : loca) {
			splash.setLoadingText("Loading Station: " + l);
			addStation(l);
		}
		if (ACCUWeatherFetcher.getUnit_code() == UnitCode.Metric)
			jRadioButton_Metric.setSelected(true);
		else
			jRadioButton_English.setSelected(true);

		this.setContentPane(getJContentPane());
		validate();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(817, 448);
		this.setResizable(false);
		getJContentPane();
		this.setContentPane(getSplash());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(name);
		this.setIconImage(utils
				.imageLodaer("logo/accuweather_logomark_color.png"));
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowIconified(java.awt.event.WindowEvent e) {
				setVisible(false);
			}

			public void windowClosing(java.awt.event.WindowEvent e) {
				close();
			}

		});
		try {
			SystemTray.getSystemTray().add(getTryIcon());

		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setVisible(true);

	}


	public WeatherTrayIcon getTryIcon() {
		if(trayIcon==null){
			trayIcon=new WeatherTrayIcon(this, this.getIconImage());			
		}
		
		return trayIcon;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(465, 390, 76, 16));
			jLabel4.setText("Povered by:");
			jLabel3 = new JLabel();
			jLabel3.setText("Metric:");
			jLabel3.setBounds(new Rectangle(105, 15, 61, 16));
			jLabel2 = new JLabel();
			jLabel2.setText("English:");
			jLabel2.setBounds(new Rectangle(15, 15, 61, 16));
			jLabel1 = new JLabel();
			jLabel1.setText("Units:");
			jLabel1.setBounds(new Rectangle(15, 75, 61, 16));
			jLabel = new JLabel();
			jLabel.setText("Current Location:");
			jLabel.setBounds(new Rectangle(15, 150, 106, 16));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel_Forecast(), null);
			jContentPane.add(getImageBox_Logo(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.setVisible(true);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollBar
	 * 
	 * @return javax.swing.JScrollBar
	 */
	private JScrollBar getJScrollBar() {
		if (jScrollBar == null) {
			jScrollBar = new JScrollBar();
			jScrollBar.setMaximum(0);
			jScrollBar.setBounds(new Rectangle(315, 165, 16, 136));

			jScrollBar
					.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
						public void adjustmentValueChanged(
								java.awt.event.AdjustmentEvent e) {
							updateWeather(locations.get(jScrollBar.getValue()));
							jComboBox_QuickSwitch.setSelectedItem(locations
									.get(jScrollBar.getValue()));
						}
					});
		}
		return jScrollBar;
	}

	/**
	 * This method initializes jTextField_Serch
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_Serch() {
		if (jTextField_Serch == null) {
			jTextField_Serch = new JTextField();
			jTextField_Serch.setBounds(new Rectangle(15, 15, 226, 16));
			jTextField_Serch.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == '\n')
						search();
				}
			});
		}
		return jTextField_Serch;
	}

	/**
	 * This method initializes jButton_Search
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Search() {
		if (jButton_Search == null) {
			jButton_Search = new JButton();
			jButton_Search.setText("Search");
			jButton_Search.setBounds(new Rectangle(255, 15, 76, 16));
			jButton_Search
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							search();
						}
					});
		}
		return jButton_Search;
	}

	/**
	 * This method initializes jComboBox_Results
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox_Results() {
		if (jComboBox_Results == null) {
			jComboBox_Results = new JComboBox();
			jComboBox_Results.setBounds(new Rectangle(15, 45, 226, 16));
			LocationList loc = ACCUWeatherFetcher.search("new york");
			jComboBox_Results.removeAllItems();
			for (Location l : loc) {
				jComboBox_Results.addItem(l);
			}
		}
		return jComboBox_Results;
	}

	/**
	 * This method initializes jButton_Add
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Add() {
		if (jButton_Add == null) {
			jButton_Add = new JButton();
			jButton_Add.setText("Add");
			jButton_Add.setBounds(new Rectangle(255, 45, 76, 16));
			jButton_Add.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new Thread() {
						@Override
						public void run() {
							addStation((Location) jComboBox_Results
									.getSelectedItem());
						}
					}.start();

				}
			});
		}
		return jButton_Add;
	}

	/**
	 * This method initializes weatherPanel
	 * 
	 * @return gui.WeatherPanel
	 */
	private WeatherPanel getWeatherPanel() {
		if (weatherPanel == null) {
			weatherPanel = new WeatherPanel();
			weatherPanel.setBounds(new Rectangle(15, 165, 300, 136));
		}
		return weatherPanel;
	}

	/**
	 * This method initializes weatherPanel_Day1
	 * 
	 * @return gui.WeatherPanel
	 */
	private WeatherPanel getWeatherPanel_Day1() {
		if (weatherPanel_Day1 == null) {
			weatherPanel_Day1 = new WeatherPanel();
			weatherPanel_Day1.setLocation(new Point(15, 15));
			weatherPanel_Day1.setSize(new Dimension(300, 136));
		}
		return weatherPanel_Day1;
	}

	/**
	 * This method initializes weatherPanel_Day2
	 * 
	 * @return gui.WeatherPanel
	 */
	private WeatherPanel getWeatherPanel_Day2() {
		if (weatherPanel_Day2 == null) {
			weatherPanel_Day2 = new WeatherPanel();
			weatherPanel_Day2.setBounds(new Rectangle(15, 165, 300, 136));
		}
		return weatherPanel_Day2;
	}

	/**
	 * This method initializes jPanel_Forecast
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Forecast() {
		if (jPanel_Forecast == null) {
			jPanel_Forecast = new JPanel();
			jPanel_Forecast.setLayout(null);
			jPanel_Forecast.setBounds(new Rectangle(405, 30, 346, 346));
			jPanel_Forecast.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel_Forecast.add(getWeatherPanel_Day2(), null);
			jPanel_Forecast.add(getWeatherPanel_Day1(), null);
			jPanel_Forecast.add(getJScrollBar1_Forecast(), null);
			jPanel_Forecast.add(getJToggleButton_Day(), null);
			jPanel_Forecast.add(getJToggleButton_Night(), null);
			ButtonGroup bg = new ButtonGroup();
			bg.add(jToggleButton_Day);
			bg.add(jToggleButton_Night);
		}
		return jPanel_Forecast;
	}

	/**
	 * This method initializes jScrollBar1_Forecast
	 * 
	 * @return javax.swing.JScrollBar
	 */
	private JScrollBar getJScrollBar1_Forecast() {
		if (jScrollBar1_Forecast == null) {
			jScrollBar1_Forecast = new JScrollBar();
			jScrollBar1_Forecast.setBounds(new Rectangle(315, 15, 16, 286));
			jScrollBar1_Forecast.setValues(1, 1, 1, 5);
			jScrollBar1_Forecast
					.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
						public void adjustmentValueChanged(
								java.awt.event.AdjustmentEvent e) {
							updateWeather(locations.get(jScrollBar.getValue()));

						}
					});
		}
		return jScrollBar1_Forecast;
	}

	/**
	 * This method initializes imageBox_Logo
	 * 
	 * @return lib.ImageBox
	 */
	private ImageBox getImageBox_Logo() {
		if (imageBox_Logo == null) {
			imageBox_Logo = new ImageBox();
			imageBox_Logo.setBounds(new Rectangle(540, 390, 241, 16));
			imageBox_Logo.setImage(utils
					.imageLodaer("logo/accuweather_logotype_color.png"));
			imageBox_Logo.setToolTipText("Visit AccuWeather");
			imageBox_Logo.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Utils.visitURL("http://www.accuweather.com/");
				}
			});

		}
		return imageBox_Logo;
	}

	/**
	 * This method initializes jPanel_Units
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Units() {
		if (jPanel_Units == null) {
			jPanel_Units = new JPanel();
			jPanel_Units.setLayout(null);
			jPanel_Units.setBounds(new Rectangle(15, 90, 196, 46));
			jPanel_Units.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel_Units.add(jLabel2, null);
			jPanel_Units.add(jLabel3, null);
			jPanel_Units.add(getJRadioButton_English(), null);
			jPanel_Units.add(getJRadioButton_Metric(), null);
			ButtonGroup bg = new ButtonGroup();
			bg.add(jRadioButton_English);
			bg.add(jRadioButton_Metric);
		}
		return jPanel_Units;
	}

	/**
	 * This method initializes jRadioButton_English
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton_English() {
		if (jRadioButton_English == null) {
			jRadioButton_English = new JRadioButton();
			jRadioButton_English.setBounds(new Rectangle(75, 15, 16, 16));
			jRadioButton_English
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (jRadioButton_English.isSelected())
								ACCUWeatherFetcher
										.setUnitCode(UnitCode.English);
							else
								ACCUWeatherFetcher.setUnitCode(UnitCode.Metric);
							for (Location l : locations) {
								l.update();
							}
							if (locations.size() != 0)
								updateWeather(locations.get(jScrollBar
										.getValue()));

						}

					});
		}
		return jRadioButton_English;
	}

	/**
	 * This method initializes jRadioButton_Metric
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton_Metric() {
		if (jRadioButton_Metric == null) {
			jRadioButton_Metric = new JRadioButton();
			jRadioButton_Metric.setBounds(new Rectangle(165, 15, 16, 16));
		}
		return jRadioButton_Metric;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(45, 30, 346, 346));
			jPanel.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel.add(getWeatherPanel(), null);
			jPanel.add(getJButton_Add(), null);
			jPanel.add(getJButton_Search(), null);
			jPanel.add(getJScrollBar(), null);
			jPanel.add(getJTextField_Serch(), null);
			jPanel.add(getJComboBox_Results(), null);
			jPanel.add(getJPanel_Units(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel, null);
			jPanel.add(getJComboBox_QuickSwitch(), null);
			jPanel.add(getJButton_RemoveLocation(), null);
			jPanel.add(getImageBox_Loading(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jComboBox_QuickSwitch
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox_QuickSwitch() {
		if (jComboBox_QuickSwitch == null) {
			jComboBox_QuickSwitch = new JComboBox();
			jComboBox_QuickSwitch.setBounds(new Rectangle(120, 150, 211, 16));
			jComboBox_QuickSwitch
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							updateWeather((Location) jComboBox_QuickSwitch
									.getSelectedItem());
							jScrollBar.setValue(jComboBox_QuickSwitch
									.getSelectedIndex());
						}
					});
		}
		return jComboBox_QuickSwitch;
	}

	/**
	 * This method initializes jButton_RemoveLocation
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_RemoveLocation() {
		if (jButton_RemoveLocation == null) {
			jButton_RemoveLocation = new JButton();
			jButton_RemoveLocation.setBounds(new Rectangle(240, 315, 91, 16));
			jButton_RemoveLocation.setText("Remove");
			jButton_RemoveLocation
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jComboBox_QuickSwitch.removeItem(locations
									.remove(jScrollBar.getValue()));
							updateWeather(locations.get(locations.size() - 1));
							jComboBox_QuickSwitch.setSelectedIndex(locations
									.size() - 1);
							jScrollBar.setValues(locations.size() - 1, 1, 0,
									locations.size() - 1);

						}
					});
		}
		return jButton_RemoveLocation;
	}

	/**
	 * This method initializes jToggleButton_Day
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getJToggleButton_Day() {
		if (jToggleButton_Day == null) {
			jToggleButton_Day = new JToggleButton();
			jToggleButton_Day.setBounds(new Rectangle(75, 315, 106, 16));
			jToggleButton_Day.setText("Day");
			jToggleButton_Day.setSelected(true);

			jToggleButton_Day
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (locations.size() != 0)
								updateWeather(locations.get(jScrollBar
										.getValue()));
						}
					});
		}
		return jToggleButton_Day;
	}

	/**
	 * This method initializes jToggleButton_Night
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getJToggleButton_Night() {
		if (jToggleButton_Night == null) {
			jToggleButton_Night = new JToggleButton();
			jToggleButton_Night.setBounds(new Rectangle(180, 315, 91, 16));
			jToggleButton_Night.setText("Night");
			jToggleButton_Night
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (locations.size() != 0)
								updateWeather(locations.get(jScrollBar
										.getValue()));
						}
					});
		}
		return jToggleButton_Night;
	}

	/**
	 * This method initializes imageBox_Loading
	 * 
	 * @return gui.ImageBox
	 */
	private ImageBox getImageBox_Loading() {
		if (imageBox_Loading == null) {
			imageBox_Loading = new ImageBox();
			imageBox_Loading.setBounds(new Rectangle(240, 87, 61, 54));
			imageBox_Loading.setImage(utils
					.imageLodaer("other/bigrotation2.gif"));
			imageBox_Loading.setVisible(false);
		}
		return imageBox_Loading;
	}

	/**
	 * This method initializes splash
	 * 
	 * @return gui.Splash
	 */
	private Splash getSplash() {
		if (splash == null) {
			splash = new Splash();
		}
		return splash;
	}

	public void close() {
		NotificationConnector.exit();
		SystemTray.getSystemTray().remove(trayIcon);
		ACCUWeatherFetcher.save(locations);

	}

	void addStation(Location location) {
		if (adding)
			return;
		adding = true;
		imageBox_Loading.setVisible(true);
		locations.put(locations.size(), location);
		jComboBox_QuickSwitch.addItem(location);
		jComboBox_QuickSwitch.setSelectedItem(location);
		location.getCurrentWeather();
		location.getFiveDayForecast();
		updateWeather(location);
		jScrollBar.setValues(locations.size() - 1, 1, 0, locations.size() - 1);
		imageBox_Loading.setVisible(false);
		adding = false;
	
	}

	void updateWeather(Location location) {
		Day d = Day.Day;
		if (jToggleButton_Night.isSelected())
			d = Day.Night;
		weatherPanel.updateWeather(location, location.getCurrentWeather());
		this.setIconImage(weatherPanel.getImageBox().getImage());
		trayIcon.setImage(getIconImage());
		weatherPanel_Day1.updateWeather(location, location.getFiveDayForecast()
				.getDay(jScrollBar1_Forecast.getValue()).getDayNight(d));
		weatherPanel_Day2.updateWeather(location, location.getFiveDayForecast()
				.getDay(jScrollBar1_Forecast.getValue() + 1).getDayNight(d));
	
	}

	void search() {
		LocationList loc = ACCUWeatherFetcher
				.search(jTextField_Serch.getText());
		jComboBox_Results.removeAllItems();
		for (Location l : loc) {
			jComboBox_Results.addItem(l);
		}
	
	}
	public LocationList getLocations() {
		return locations;
	}

	/**
	 * This method initializes settings	
	 * 	
	 * @return gui.Settings	
	 */
	Settings getSettings() {
		if (settings == null) {
			settings = new Settings(this);
		}
		return settings;
	}

	public static void main(String[] args) {
		new Gui();

	}

	public void setNotificationthread(NotificationThread notificationthread) {
		this.notificationthread = notificationthread;
	}

	public NotificationThread getNotificationthread() {
		return notificationthread;
	}
	

} // @jve:decl-index=0:visual-constraint="10,10"
