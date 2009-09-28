package updater.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JLabel;

import updater.net.Updater;
import javax.swing.JCheckBox;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jButton_Install = null;
	private JTextField jTextField_Path = null;
	private JButton jButton_Browse = null;
	private JButton jButton_Cancell1 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JCheckBox jCheckBox_Dev = null;

	/**
	 * This is the default constructor
	 */
	public Gui() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(316, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("jWeatherWatch Installer");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(15, 105, 120, 16));
			jLabel2.setText("Developement build?");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(15, 60, 62, 18));
			jLabel1.setText("Install to:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(19, 15, 177, 16));
			jLabel.setText("Install " + Updater.getVersion());
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJButton_Install(), null);
			jContentPane.add(getJTextField_Path(), null);
			jContentPane.add(getJButton_Browse(), null);
			jContentPane.add(getJButton_Cancell1(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJCheckBox_Dev(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton_Install
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Install() {
		if (jButton_Install == null) {
			jButton_Install = new JButton();
			jButton_Install.setText("Install");
			jButton_Install.setLocation(new Point(180, 135));
			jButton_Install.setPreferredSize(new Dimension(78, 26));
			jButton_Install.setSize(new Dimension(100, 16));
			jButton_Install
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							install();
						}
					});
			;
		}
		return jButton_Install;
	}

	/**
	 * This method initializes jTextField_Path
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_Path() {
		if (jTextField_Path == null) {
			jTextField_Path = new JTextField();
			jTextField_Path.setBounds(new Rectangle(15, 75, 271, 16));
		}
		return jTextField_Path;
	}

	/**
	 * This method initializes jButton_Browse
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Browse() {
		if (jButton_Browse == null) {
			jButton_Browse = new JButton();
			jButton_Browse.setText("Browse");
			jButton_Browse.setBounds(new Rectangle(180, 90, 106, 16));
			jButton_Browse
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JFileChooser fc = new JFileChooser();
							fc
									.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

							int result = fc.showOpenDialog(null);
							if (result == JFileChooser.APPROVE_OPTION)
								jTextField_Path.setText(fc.getSelectedFile()
										.getAbsolutePath());

						}
					});
		}
		return jButton_Browse;
	}

	/**
	 * This method initializes jButton_Cancell1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Cancell1() {
		if (jButton_Cancell1 == null) {
			jButton_Cancell1 = new JButton();
			jButton_Cancell1.setText("Cancell");
			jButton_Cancell1.setSize(new Dimension(100, 16));
			jButton_Cancell1.setLocation(new Point(75, 135));
			jButton_Cancell1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.exit(0);
						}
					});
		}
		return jButton_Cancell1;
	}

	private void install() {
		if (jTextField_Path.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please set Installation Path",
					"Installation Path not set", JOptionPane.ERROR_MESSAGE);
			return;
		}

			JOptionPane.showMessageDialog(null, "this may take some time",
					"Installation will start now", JOptionPane.INFORMATION_MESSAGE);
	

		if (!Updater.update(jTextField_Path.getText(), jCheckBox_Dev.isSelected())) {
			JOptionPane.showMessageDialog(null,
					"Installation of jWeatherWatcher Failed",
					"Installation failed!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Updater.creatLnk(jTextField_Path.getText());
		JOptionPane.showMessageDialog(null,
				"Instalaltion of jWeatherWatcher was succsessfull",
				"Installation complete", JOptionPane.INFORMATION_MESSAGE);

		try {
			Runtime.getRuntime().exec(
					new String[] { "java", "-jar",
							jTextField_Path.getText() + "/JWeatherWatch.jar" });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	/**
	 * This method initializes jCheckBox_Dev
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox_Dev() {
		if (jCheckBox_Dev == null) {
			jCheckBox_Dev = new JCheckBox();
			jCheckBox_Dev.setBounds(new Rectangle(150, 105, 31, 16));
			jCheckBox_Dev
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (jCheckBox_Dev.isSelected())
								jLabel.setText("Install "
										+ Updater.getDevVersion());
							else
								jLabel.setText("Install "
										+ Updater.getVersion());
						}
					});
		}
		return jCheckBox_Dev;
	}

	public static void main(String[] args) {
		Gui f = new Gui();
		f.setVisible(true);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
