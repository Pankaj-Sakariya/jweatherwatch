package gui;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.ACCUWeatherFetcher;
import net.NotificationConnector;
import net.NotificationThread;
import net.Utils;
import net.Notifer.NetNotifer;
import net.Notifer.Notifer;
import net.Notifer.NotiferTypes;
import net.Notifer.Notifers.KNotify;
import net.Notifer.Notifers.NetGrowl;
import net.Notifer.Notifers.NetSnarl;
import net.Notifer.Notifers.Snarl;
import net.Notifer.Notifers.TrayNotification;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Settings extends JFrame {

	private static final long serialVersionUID = 1L;

	private Gui parent;

	private JPanel jContentPane = null;
	private JTextField jTextField_NotificationInterval = null;
	private JLabel jLabel = null;
	private JButton jButton_NotificationInterval = null;
	private JLabel jLabel1 = null;
	private JToggleButton jToggleButton_SnarlNetbridge = null;
	private JTextField jTextField_Host = null;
	private JLabel jLabel3 = null;
	private JButton jButton_Host = null;

	private JLabel jLabel4 = null;

	private JToggleButton jToggleButton_Snarl = null;

	private JToggleButton jToggleButton_NetGrowl = null;

	private JToggleButton jToggleButton_Trayicon = null;
	private JToggleButton jToggleButton_KNotify =null;

	/**
	 * This is the default constructor
	 */
	public Settings(Gui parent) {
		super();
		this.parent = parent;
		initialize();
		loadFromFile();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 245);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("Settings");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				loadSettings();
			}

		});
	}

	private void loadSettings() {
		if (NotificationConnector.getNotifer() instanceof NetNotifer) {
			jTextField_Host.setText(((NetNotifer) NotificationConnector
					.getNotifer()).getHost());
		}
		if (parent.getNotificationthread() != null)
			jTextField_NotificationInterval.setText(String.valueOf(parent
					.getNotificationthread().getInterval()));
		else
			parent.setNotificationthread(new NotificationThread(parent
					.getLocations(), 30));
		switch (NotificationConnector.getNotifer().getName()) {
		case Snarl:
			jToggleButton_Snarl.setSelected(true);
			break;
		case NetSnarl:
			jToggleButton_SnarlNetbridge.setSelected(true);
			break;
		case NetGrowl:
			jToggleButton_NetGrowl.setSelected(true);
			break;
		case TrayIcon:
			jToggleButton_Trayicon.setSelected(true);
			break;
		case KNotify:
			jToggleButton_KNotify.setSelected(true);
			break;
		default:
			break;
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(30, 15, 226, 13));
			jLabel4.setText("Your Notification System:");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(30, 180, 151, 16));
			jLabel3.setText("NetSnarl Host:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(60, 135, 61, 16));
			jLabel1.setText("minutes");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(30, 120, 151, 16));
			jLabel.setText("Notification Interval:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTextField_NotificationInterval(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJButton_NotificationInterval(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField_Host(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJButton_Host(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJToggleButton_Snarl(), null);
			jContentPane.add(getJToggleButton_NetGrowl(), null);
			jContentPane.add(getJToggleButton_Trayicon(), null);
			jContentPane.add(getJToggleButton_SnarlNetbridge(), null);
			jContentPane.add(getjToggleButton_KNotify(),null);
			ButtonGroup bg = new ButtonGroup();
			bg.add(jToggleButton_NetGrowl);
			bg.add(jToggleButton_SnarlNetbridge);
			bg.add(jToggleButton_Snarl);
			bg.add(jToggleButton_Trayicon);
			bg.add(jToggleButton_KNotify);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField_NotificationInterval
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_NotificationInterval() {
		if (jTextField_NotificationInterval == null) {
			jTextField_NotificationInterval = new JTextField();
			jTextField_NotificationInterval.setBounds(new Rectangle(30, 135,
					31, 16));
			jTextField_NotificationInterval
					.setHorizontalAlignment(JTextField.TRAILING);
			jTextField_NotificationInterval.setText("30");
		}
		return jTextField_NotificationInterval;
	}

	/**
	 * This method initializes jButton_NotificationInterval
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_NotificationInterval() {
		if (jButton_NotificationInterval == null) {
			jButton_NotificationInterval = new JButton();
			jButton_NotificationInterval.setBounds(new Rectangle(120, 135, 61,
					16));
			jButton_NotificationInterval.setText("Set");
			jButton_NotificationInterval
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							parent.getNotificationthread().shutdown();
							parent
									.setNotificationthread(new NotificationThread(
											parent.getLocations(),
											Integer
													.valueOf(jTextField_NotificationInterval
															.getText())));
						}
					});
		}
		return jButton_NotificationInterval;
	}

	/**
	 * This method initializes jToggleButton_SnarlNetbridge
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getJToggleButton_SnarlNetbridge() {
		if (jToggleButton_SnarlNetbridge == null) {
			jToggleButton_SnarlNetbridge = new JToggleButton();
			jToggleButton_SnarlNetbridge
					.setBounds(new Rectangle(75, 45, 91, 16));
			jToggleButton_SnarlNetbridge.setText("NetSnarl");
			jToggleButton_SnarlNetbridge
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (jToggleButton_SnarlNetbridge.isSelected()) {
								NotificationConnector
										.setNotifer(new NetSnarl());
							}
						}
					});

		}
		return jToggleButton_SnarlNetbridge;
	}

	/**
	 * This method initializes jTextField_Host
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_Host() {
		if (jTextField_Host == null) {
			jTextField_Host = new JTextField();
			jTextField_Host.setBounds(new Rectangle(30, 195, 91, 16));
		}
		return jTextField_Host;
	}

	/**
	 * This method initializes jButton_Host
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Host() {
		if (jButton_Host == null) {
			jButton_Host = new JButton();
			jButton_Host.setBounds(new Rectangle(120, 195, 61, 16));
			jButton_Host.setText("Set");
			jButton_Host.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					NotificationConnector.setHost(jTextField_Host.getText());
				}
			});
		}
		return jButton_Host;
	}

	/**
	 * This method initializes jToggleButton_Snarl
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getJToggleButton_Snarl() {
		if (jToggleButton_Snarl == null) {
			jToggleButton_Snarl = new JToggleButton();
			jToggleButton_Snarl.setBounds(new Rectangle(75, 30, 91, 16));
			jToggleButton_Snarl.setText("Snarl");
			jToggleButton_Snarl
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (jToggleButton_Snarl.isSelected()) {
								NotificationConnector.setNotifer(new Snarl());
							}
						}
					});

		}
		return jToggleButton_Snarl;
	}

	/**
	 * This method initializes jToggleButton_NetGrowl
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getJToggleButton_NetGrowl() {
		if (jToggleButton_NetGrowl == null) {
			jToggleButton_NetGrowl = new JToggleButton();
			jToggleButton_NetGrowl.setBounds(new Rectangle(75, 60, 91, 16));
			jToggleButton_NetGrowl.setText("NetGrowl");
			jToggleButton_NetGrowl
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (jToggleButton_NetGrowl.isSelected()) {
								NotificationConnector
										.setNotifer(new NetGrowl());
							}
						}
					});

		}
		return jToggleButton_NetGrowl;
	}

	/**
	 * This method initializes jToggleButton_Trayicon
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getJToggleButton_Trayicon() {
		if (jToggleButton_Trayicon == null) {
			jToggleButton_Trayicon = new JToggleButton();
			jToggleButton_Trayicon.setBounds(new Rectangle(75, 90, 91, 16));
			jToggleButton_Trayicon.setText("Trayicon");
			jToggleButton_Trayicon
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (jToggleButton_Trayicon.isSelected()) {
								NotificationConnector
										.setNotifer(new TrayNotification(parent
												.getTrayIcon()));
							}
						}
					});

		}
		return jToggleButton_Trayicon;
	}
	
	private JToggleButton getjToggleButton_KNotify() {
		if (jToggleButton_KNotify == null) {
			jToggleButton_KNotify= new JToggleButton();
			jToggleButton_KNotify.setBounds(new Rectangle(75, 75, 91, 16));
			jToggleButton_KNotify.setText("KNotify");
			jToggleButton_KNotify
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (jToggleButton_KNotify.isSelected()) {
								NotificationConnector
										.setNotifer(new KNotify());
							}
						}
					});

		}
		return jToggleButton_KNotify;
	}

	private boolean loadFromFile() {
		if (!new File(ACCUWeatherFetcher.getHomeDirectory() + "/." + Gui.name
				+ "/settings.xml").exists())
			return false;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		Document doc = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(ACCUWeatherFetcher.getHomeDirectory() + "/."
					+ Gui.name + "/settings.xml");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Element element = ((Element) (doc.getElementsByTagName("" + Gui.name
				+ "Settings").item(0)));

		NotificationConnector.setHost(Utils.getXMLValue(element, "host"));
		Notifer notifer2 = null;

		switch (NotiferTypes.valueOf(Utils.getXMLValue(element, "notifer"))) {
		case Snarl:
			notifer2 = new Snarl();
			break;
		case NetSnarl:
			notifer2 = new NetSnarl();
			break;
		case NetGrowl:
			notifer2 = new NetGrowl();
			break;
		case TrayIcon:
			notifer2 = new TrayNotification(parent.getTrayIcon());
			break;
		case KNotify:
			notifer2 = new KNotify();
			break;
		}

		NotificationConnector.setNotifer(notifer2);
		// setting up notification thread
		if (parent.getNotificationthread() != null)
			parent.getNotificationthread().shutdown();
		parent.setNotificationthread(new NotificationThread(parent
				.getLocations(), Integer.valueOf(Utils.getXMLValue(element,
				"notificationIterval"))));

		return true;
	}

	public void save() {
		PrintWriter out = null;
		try {
			if (!new File(ACCUWeatherFetcher.getHomeDirectory() + "/."
					+ Gui.name).exists())
				new File(ACCUWeatherFetcher.getHomeDirectory() + "/."
						+ Gui.name).mkdir();
			out = new PrintWriter(new FileOutputStream(ACCUWeatherFetcher
					.getHomeDirectory()
					+ "/." + Gui.name + "/settings.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("<" + Gui.name + "Settings>");
		out.println("<host>" + NotificationConnector.getHost() + "</host>");
		out.println("<notificationIterval>"
				+ parent.getNotificationthread().getInterval()
				+ "</notificationIterval>");
		out.println("<notifer>" + NotificationConnector.getNotifer().getName()
				+ "</notifer>");
		out.println("</" + Gui.name + "Settings>");
		out.close();

	}

} // @jve:decl-index=0:visual-constraint="10,10"
