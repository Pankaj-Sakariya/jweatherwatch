package gui.settings.tabs;

import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;
import gui.Gui;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.Main;
import net.SettingsReader;
import net.Version;
import updater.net.Updater;

public class AdvancedTab extends SettingsTab {

	private static final long serialVersionUID = 1L;
	private JTextField jTextField_IconPath = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JCheckBox jCheckBox_Dev = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JComboBox jComboBox_Broswer = null;

	/**
	 * This is the default constructor
	 */
	public AdvancedTab() {
		super("Advanced");
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
		jLabel3.setText("Browser");
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(75, 120, 76, 16));
		jLabel2.setText("updates:");
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(75, 105, 151, 16));
		jLabel1.setText("Recive developement");
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(75, 15, 151, 16));
		jLabel.setText("IconPath");
		this.setSize(300, 200);
		this.setLayout(null);
		this.add(getJTextField_IconPath(), null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(getJCheckBox_Dev(), null);
		this.add(jLabel2, null);
		this.add(jLabel3, null);
		this.add(getJComboBox_Broswer(), null);
	}

	/**
	 * This method initializes jTextField_IconPath
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_IconPath() {
		if (jTextField_IconPath == null) {
			jTextField_IconPath = new JTextField();
			jTextField_IconPath.setBounds(new Rectangle(75, 30, 151, 16));
		}
		return jTextField_IconPath;
	}

	@Override
	public void load() {
		jTextField_IconPath.setText(SettingsReader.getInstance().getIconpPath());
		jCheckBox_Dev.setSelected(SettingsReader.getInstance().devChannel);
		jComboBox_Broswer.setSelectedItem(SettingsReader.getInstance().webBrowser);
	}

	@Override
	public void save(Gui gui) {
		SettingsReader.getInstance().webBrowser=jComboBox_Broswer.getSelectedItem().toString();
		SettingsReader.getInstance().setIconpPath(jTextField_IconPath.getText());
		if (SettingsReader.getInstance().devChannel != jCheckBox_Dev.isSelected()) {
			SettingsReader.getInstance().devChannel = jCheckBox_Dev.isSelected();
			Version compare = new Version(SettingsReader.getInstance().devChannel ? Updater
					.getDevVersion() : Updater.getVersion());
			if (SettingsReader.getInstance().getVersion().compareTo(compare) != 0)
				Main.update();
		}
	}

	/**
	 * This method initializes jCheckBox_Dev
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox_Dev() {
		if (jCheckBox_Dev == null) {
			jCheckBox_Dev = new JCheckBox();
			jCheckBox_Dev.setBounds(new Rectangle(165, 120, 31, 16));
		}
		return jCheckBox_Dev;
	}

	/**
	 * This method initializes jComboBox_Broswer
	 * 
	 * @return javax.swing.JComboBox
	 */	
	private JComboBox getJComboBox_Broswer() {
		if (jComboBox_Broswer == null) {
			BrowserLauncher b;
			List<String> l = new ArrayList<String>();
			l.add("No Browser Found");

			try {
				b = new BrowserLauncher();

				l = b.getBrowserList();

			} catch (BrowserLaunchingInitializingException e) {
			} catch (UnsupportedOperatingSystemException e) {
			}
			jComboBox_Broswer = new JComboBox(l.toArray());
			jComboBox_Broswer.setBounds(new Rectangle(75, 75, 151, 16));

		}
		return jComboBox_Broswer;
	}

} // @jve:decl-index=0:visual-constraint="27,11"
