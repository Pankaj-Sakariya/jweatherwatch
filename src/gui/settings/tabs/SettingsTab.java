package gui.settings.tabs;

import gui.Gui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.Utils;

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

	public void restart(){
		 
				int result = JOptionPane
						.showConfirmDialog(
								null,
								"The changes you made will be availible afte restarting jWeatherWatch",
								"Restart Needed", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.NO_OPTION)
					return;

				Utils.restart();
	}
	public abstract void save(Gui gui);

	public abstract void load();

}
