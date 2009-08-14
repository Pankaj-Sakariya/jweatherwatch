package gui;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import net.NotificationConnector;
import net.NotificationThread;
import net.Settings;
import net.Utils;
import net.Notifer.NotiferTypes;
import net.Notifer.Notifers.KNotify;
import net.Notifer.Notifers.NetGrowl;
import net.Notifer.Notifers.NetSnarl;
import net.Notifer.Notifers.Snarl;
import net.Notifer.Notifers.TrayNotification;

public class SettingsDialog extends JFrame {

	private static final long serialVersionUID = 1L;

	private Gui parent;

	private JPanel jContentPane = null;
	private JTextField jTextField_NotificationInterval = null;
	private JLabel jLabel = null;
	private JButton jButton_NotificationInterval = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField_Host = null;
	private JLabel jLabel3 = null;
	private JButton jButton_Host = null;

	private JLabel jLabel4 = null;

	private JToggleButton jToggleButton_SystemStart = null;

	private JComboBox jComboBox_Notifer = null;

	/**
	 * This is the default constructor
	 */
	public SettingsDialog(Gui parent) {
		super();
		this.parent = parent;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(213, 208);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("Settings");
		this.setIconImage(parent.getIconImage());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				loadSettings();
			}

		});
	}

	private void loadSettings() {
		jTextField_Host.setText(NotificationConnector.getHost());

		if (parent.getNotificationthread() != null)
			jTextField_NotificationInterval.setText(String.valueOf(parent
					.getNotificationthread().getInterval()));
		else
			parent.setNotificationthread(new NotificationThread(parent
					.getLocations(), 30));
		jComboBox_Notifer.setSelectedItem(NotificationConnector.getNotifer()
				.getName());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(30, 15, 151, 13));
			jLabel4.setText("Your Notification System:");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(30, 105, 151, 16));
			jLabel3.setText("NetSnarl Host:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(60, 75, 61, 16));
			jLabel1.setText("minutes");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(30, 60, 151, 16));
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
			jContentPane.add(getJToggleButton_SystemStart(), null);
			jContentPane.add(getJComboBox_Notifer(), null);
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
			jTextField_NotificationInterval.setBounds(new Rectangle(30, 75, 31,
					16));
			jTextField_NotificationInterval
					.setHorizontalAlignment(JTextField.TRAILING);
			jTextField_NotificationInterval.setText("30");
			jTextField_NotificationInterval.setToolTipText("0 to deactivate");
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
			jButton_NotificationInterval.setBounds(new Rectangle(120, 75, 61,
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
							Settings.notificationInterval = parent
									.getNotificationthread().getInterval();
						}
					});
		}
		return jButton_NotificationInterval;
	}

	/**
	 * This method initializes jTextField_Host
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_Host() {
		if (jTextField_Host == null) {
			jTextField_Host = new JTextField();
			jTextField_Host.setBounds(new Rectangle(30, 120, 91, 16));
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
			jButton_Host.setBounds(new Rectangle(120, 120, 61, 16));
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
	 * This method initializes jToggleButton_SystemStart
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getJToggleButton_SystemStart() {
		if (jToggleButton_SystemStart == null) {
			jToggleButton_SystemStart = new JToggleButton();
			jToggleButton_SystemStart
					.setBounds(new Rectangle(30, 150, 151, 16));
			jToggleButton_SystemStart.setText("Start with System");
			jToggleButton_SystemStart.setSelected(Settings.isAutostart());
			if (Utils.getOS() != Utils.OS.WINDOWS)
				jToggleButton_SystemStart.setEnabled(false);
			jToggleButton_SystemStart
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (jToggleButton_SystemStart.isSelected())
								Settings.addtoAutostart();
							else
								Settings.removeAutostart();
						}
					});
		}
		return jToggleButton_SystemStart;
	}

	/**
	 * This method initializes jComboBox_Notifer
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox_Notifer() {
		if (jComboBox_Notifer == null) {
			jComboBox_Notifer = new JComboBox();
			jComboBox_Notifer.setBounds(new Rectangle(30, 30, 151, 16));
			jComboBox_Notifer.addItem(NotiferTypes.Snarl);
			jComboBox_Notifer.addItem(NotiferTypes.KNotify);
			jComboBox_Notifer.addItem(NotiferTypes.NetGrowl);
			jComboBox_Notifer.addItem(NotiferTypes.NetSnarl);
			jComboBox_Notifer.addItem(NotiferTypes.TrayIcon);

			jComboBox_Notifer
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							boolean changed = false;
							switch ((NotiferTypes) jComboBox_Notifer
									.getSelectedItem()) {
							case Snarl:
								changed = NotificationConnector
										.setNotifer(new Snarl());
								break;
							case KNotify:
								changed = NotificationConnector
										.setNotifer(new KNotify());
								break;
							case NetGrowl:
								changed = NotificationConnector
										.setNotifer(new NetGrowl());
								break;
							case NetSnarl:
								changed = NotificationConnector
										.setNotifer(new NetSnarl());
								break;
							case TrayIcon:
								changed = NotificationConnector
										.setNotifer(new TrayNotification(parent
												.getTrayIcon()));
								break;
							}
							if (changed)
								jComboBox_Notifer
										.setSelectedItem(NotificationConnector
												.getNotifer().getName());
						}
					});

		}
		return jComboBox_Notifer;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
