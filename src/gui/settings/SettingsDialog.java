package gui.settings;

import gui.Gui;
import gui.WeatherView;
import gui.settings.tabs.AdvancedTab;
import gui.settings.tabs.MainTab;
import gui.settings.tabs.MinimalTab;
import gui.settings.tabs.SettingsTab;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.SettingsReader;

public class SettingsDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gui parent = null;
	private SettingsTab tabs[] = new SettingsTab[3];

	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel = null;
	private JButton jButton_Ok = null;
	private JButton jButton_Aply = null;
	private JButton jButton_Cancel = null;

	public SettingsDialog(Gui gui) {
		super("Settings");
		parent = gui;
		initialize();
	
	}

	

	private void save() {
		for (SettingsTab t : tabs) {
			t.save(parent);
		}
		parent.setView(WeatherView.getViewByName(SettingsReader.getInstance().view,parent));

	}

	private void initialize() {
		this.setLayout(null);
		this.setSize(new Dimension(305, 257));
		this.setResizable(false);
		this.setContentPane(getJPanel());
		this.setIconImage(parent.getIconImages().get(0));
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setSize(300, 200);
			tabs[0] = new MainTab();
			tabs[1] = new MinimalTab();
			tabs[2]=new AdvancedTab();
			for (SettingsTab t : tabs)
				jTabbedPane.addTab(t.getTabName(), null, t, t.getTabName());
		}
		jTabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				((SettingsTab) jTabbedPane.getSelectedComponent()).load();				
			}
		});
		((SettingsTab) jTabbedPane.getSelectedComponent()).load();		
		return jTabbedPane;
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
			jPanel.add(getJTabbedPane(), null);
			jPanel.add(getJButton_Ok(), null);
			jPanel.add(getJButton_Aply(), null);
			jPanel.add(getJButton_Cancel(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton_Ok
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Ok() {
		if (jButton_Ok == null) {
			jButton_Ok = new JButton();
			jButton_Ok.setBounds(new Rectangle(210, 210, 76, 16));
			jButton_Ok.setText("Ok");
			jButton_Ok.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					save();
					setVisible(false);
				}
			});
		}
		return jButton_Ok;
	}

	/**
	 * This method initializes jButton_Aply
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Aply() {
		if (jButton_Aply == null) {
			jButton_Aply = new JButton();
			jButton_Aply.setBounds(new Rectangle(120, 210, 76, 16));
			jButton_Aply.setText("Aply");
			jButton_Aply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					save();
				}
			});
		}
		return jButton_Aply;
	}

	/**
	 * This method initializes jButton_Cancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Cancel() {
		if (jButton_Cancel == null) {
			jButton_Cancel = new JButton();
			jButton_Cancel.setBounds(new Rectangle(30, 210, 76, 16));
			jButton_Cancel.setText("Cancel");
			jButton_Cancel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setVisible(false);
						}
					});
		}
		return jButton_Cancel;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
