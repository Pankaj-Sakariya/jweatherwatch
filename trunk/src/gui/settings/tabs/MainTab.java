package gui.settings.tabs;

import gui.Gui;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import net.NotificationConnector;
import net.SettingsReader;
import net.Utils;
import net.Notifer.NotiferTypes;

public class MainTab extends SettingsTab {

	private static final long serialVersionUID = 1L;

	private JComboBox jComboBox_Notifer = null;

	private JTextField jTextField_NotificationInterval = null;

	private JToggleButton jToggleButton_SystemStart = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextField jTextField_Host = null;

	private JLabel jLabel3 = null;

	/**
	 * This is the default constructor
	 */
	public MainTab() {
		super("General");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel3 = new JLabel();
		jLabel3.setBounds(new Rectangle(75, 60, 151, 16));
		jLabel3.setText("Notification Host");
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(135, 120, 91, 16));
		jLabel2.setText("min");
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(75, 105, 151, 16));
		jLabel1.setText("Notification Interval");
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(75, 15, 151, 16));
		jLabel.setText("Select Notifer");
		this.setSize(300, 200);
		this.setLayout(null);
		this.add(getJComboBox_Notifer(), null);
		this.add(jLabel, null);
		this.add(getJTextField_NotificationInterval(), null);
		this.add(jLabel1, null);
		this.add(jLabel2, null);
		this.add(getJToggleButton_SystemStart(), null);
		this.add(getJTextField_Host(), null);
		this.add(jLabel3, null);
	}

	private JComboBox getJComboBox_Notifer() {
		if (jComboBox_Notifer == null) {
			jComboBox_Notifer = new JComboBox();
			jComboBox_Notifer.setBounds(new Rectangle(75, 30, 151, 16));
			jComboBox_Notifer.setPreferredSize(new Dimension(100, 16));
			for (NotiferTypes n : NotiferTypes.values())
				jComboBox_Notifer.addItem(n);

		}
		return jComboBox_Notifer;
	}

	/**
	 * This method initializes jTextField_NotificationInterval
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_NotificationInterval() {
		if (jTextField_NotificationInterval == null) {
			jTextField_NotificationInterval = new JTextField();
			jTextField_NotificationInterval.setBounds(new Rectangle(75, 120,
					60, 16));
			jTextField_NotificationInterval
					.setHorizontalAlignment(JTextField.TRAILING);
			jTextField_NotificationInterval.setText("30");
			jTextField_NotificationInterval.setToolTipText("0 to deactivate");
		}
		return jTextField_NotificationInterval;
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
					.setBounds(new Rectangle(75, 150, 151, 16));
			jToggleButton_SystemStart.setText("Start with System");
			jToggleButton_SystemStart.setSelected(SettingsReader.getInstance().isAutostart());
			jToggleButton_SystemStart
					.setEnabled(Utils.getOS() == Utils.OS.WINDOWS);

		}
		return jToggleButton_SystemStart;
	}

	/**
	 * This method initializes jTextField_Host
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_Host() {
		if (jTextField_Host == null) {
			jTextField_Host = new JTextField();
			jTextField_Host.setBounds(new Rectangle(75, 75, 151, 16));
		}
		return jTextField_Host;
	}

	@Override
	public void load() {
		jComboBox_Notifer.setSelectedItem(NotificationConnector.getNotifer()
				.getName());
		jTextField_NotificationInterval.setText(String
				.valueOf(SettingsReader.getInstance().notificationInterval));
		jTextField_Host.setText(NotificationConnector.getHost());

	}

	@Override
	public void save(Gui gui) {
		NotificationConnector.setNotifer(NotiferTypes.getNotifer(
				(NotiferTypes) jComboBox_Notifer.getSelectedItem(), gui
						.getTrayIcon()));

		// autostart
		if (SettingsReader.getInstance().isAutostart() != jToggleButton_SystemStart
				.isSelected()) {
			if (jToggleButton_SystemStart.isSelected())
				SettingsReader.getInstance().addtoAutostart();
			else
				SettingsReader.getInstance().removeAutostart();
		}

		NotificationConnector.setHost(jTextField_Host.getText());

	}
}
