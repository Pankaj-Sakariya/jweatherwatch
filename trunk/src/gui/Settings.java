package gui;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import net.NotificationConnector;
import net.Notifer.Notifers.NetSnarl;
import net.Notifer.Notifers.Snarl;

public class Settings extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField jTextField_NotificationInterval = null;
	private JLabel jLabel = null;
	private JButton jButton_NotificationInterval = null;
	private JLabel jLabel1 = null;
	private JToggleButton jToggleButton_Netbridge = null;
	private JLabel jLabel2 = null;
	private JTextField jTextField_Host = null;
	private JLabel jLabel3 = null;
	private JButton jButton_Host = null;

	/**
	 * This is the default constructor
	 */
	public Settings() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("Settings");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (NotificationConnector.getNotifer() instanceof NetSnarl) {
					jTextField_Host.setText(((NetSnarl) NotificationConnector
							.getNotifer()).getSnarl().getHost());
				}

			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(30, 105, 151, 16));
			jLabel3.setText("NetSnarl Host:");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(30, 60, 151, 16));
			jLabel2.setText("Using SnarlNetwork:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(60, 30, 61, 16));
			jLabel1.setText("minutes");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(30, 15, 151, 16));
			jLabel.setText("Notification Interval:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTextField_NotificationInterval(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJButton_NotificationInterval(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJToggleButton_Netbridge(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField_Host(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJButton_Host(), null);
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
			jTextField_NotificationInterval.setBounds(new Rectangle(30, 30, 31,
					16));
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
			jButton_NotificationInterval.setBounds(new Rectangle(120, 30, 61,
					16));
			jButton_NotificationInterval.setText("Set");
		}
		return jButton_NotificationInterval;
	}

	/**
	 * This method initializes jToggleButton_Netbridge
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getJToggleButton_Netbridge() {
		if (jToggleButton_Netbridge == null) {
			jToggleButton_Netbridge = new JToggleButton();
			jToggleButton_Netbridge.setBounds(new Rectangle(120, 75, 61, 16));
			jToggleButton_Netbridge.setText("No");
			jToggleButton_Netbridge
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (jToggleButton_Netbridge.isSelected()) {

								if (NotificationConnector
										.setNotifer(new NetSnarl())) {
									jToggleButton_Netbridge.setText("Yes");
									jTextField_Host
											.setText(((NetSnarl) NotificationConnector
													.getNotifer()).getSnarl()
													.getHost());
								} else
									jToggleButton_Netbridge.setText("No");
							} else {

								if (NotificationConnector
										.setNotifer(new Snarl()))
									jToggleButton_Netbridge.setText("No");
								else {
									jToggleButton_Netbridge.setText("Yes");
									jTextField_Host
											.setText(((NetSnarl) NotificationConnector
													.getNotifer()).getSnarl()
													.getHost());
								}
							}
							jToggleButton_Netbridge
									.setSelected(jToggleButton_Netbridge
											.getText().equals("Yes"));

						}
					});
		}
		return jToggleButton_Netbridge;
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
					if (NotificationConnector.getNotifer() instanceof NetSnarl) {
						((NetSnarl) NotificationConnector.getNotifer())
								.getSnarl().setHost(jTextField_Host.getText());
					}
				}
			});
		}
		return jButton_Host;
	}

}
