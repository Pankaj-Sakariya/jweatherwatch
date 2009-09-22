package gui.settings.tabs;

import gui.Gui;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.SettingsReader;
import javax.swing.JCheckBox;

public class AdvancedTab extends SettingsTab{

	private static final long serialVersionUID = 1L;
	private JTextField jTextField_IconPath = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JCheckBox jCheckBox_Dev = null;
	private JLabel jLabel2 = null;
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
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(75, 75, 76, 16));
		jLabel2.setText("updates:");
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(75, 60, 151, 16));
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
	jTextField_IconPath.setText(SettingsReader.getIconpPath());
	jCheckBox_Dev.setSelected(SettingsReader.devChannel);
	}

	@Override
	public void save(Gui gui) {
		SettingsReader.setIconpPath(jTextField_IconPath.getText());
		SettingsReader.devChannel=jCheckBox_Dev.isSelected();
	}

	/**
	 * This method initializes jCheckBox_Dev	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Dev() {
		if (jCheckBox_Dev == null) {
			jCheckBox_Dev = new JCheckBox();
			jCheckBox_Dev.setBounds(new Rectangle(165, 75, 31, 16));
		}
		return jCheckBox_Dev;
	}

}  //  @jve:decl-index=0:visual-constraint="27,11"
