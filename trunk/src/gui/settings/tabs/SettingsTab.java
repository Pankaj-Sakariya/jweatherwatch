package gui.settings.tabs;

import gui.Gui;

import javax.swing.JPanel;

public abstract class SettingsTab extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tabName = null;

	public SettingsTab(String tabName) {
		super();
		this.tabName = tabName;
//		this.setSize(new Dimension(300, 200));
//		this.setLayout(null);
	}

	public String getTabName() {
		return this.tabName;
	}

	public abstract void save(Gui gui);

	public abstract void load();

}
