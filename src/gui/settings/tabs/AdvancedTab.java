package gui.settings.tabs;

import gui.Gui;

import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JTextField;

import net.SettingsReader;

public class AdvancedTab extends SettingsTab{

	private static final long serialVersionUID = 1L;
	private JTextField jTextField_IconPath = null;
	private JLabel jLabel = null;

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
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(75, 15, 151, 16));
		jLabel.setText("IconPath");
		this.setSize(300, 200);
		this.setLayout(null);
		this.add(getJTextField_IconPath(), null);
		this.add(jLabel, null);
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
	}

	@Override
	public void save(Gui gui) {
		SettingsReader.setIconpPath(jTextField_IconPath.getText());
	}

}
