package gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JToggleButton;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.xml.internal.txw2.Document;

public class Installer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jButton_Install = null;
	private JTextField jTextField_Path = null;
	private JButton jButton_Browse = null;
	private JButton jButton_Cancell1 = null;

	/**
	 * This is the default constructor
	 */
	public Installer() {
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
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJButton_Install(), null);
			jContentPane.add(getJTextField_Path(), null);
			jContentPane.add(getJButton_Browse(), null);
			jContentPane.add(getJButton_Cancell1(), null);
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
			jButton_Install.setSize(new Dimension(100, 16));
			jButton_Install.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
			install();}
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
			jTextField_Path.setBounds(new Rectangle(15, 45, 271, 16));
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
			jButton_Browse.setBounds(new Rectangle(180, 60, 106, 16));
			jButton_Browse
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JFileChooser fc = new JFileChooser();
							fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

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
			jButton_Cancell1.setBounds(new Rectangle(75, 135, 96, 16));
			jButton_Cancell1.setText("Cancell");
			jButton_Cancell1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.exit(0);
						}
					});
		}
		return jButton_Cancell1;
	}

	private void install(){
		org.w3c.dom.Document doc =null;
		try {
			doc= DocumentBuilderFactory.newInstance().newDocumentBuilder()
			.parse("http://jweatherwatch.googlecode.com/svn/installer/CurrentVersion.xml");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String version=doc.getElementsByTagName("version").item(0).getNodeValue(); 
		System.out.println(version);
		
		
	}
	public static void main(String[] args) {
		Installer f = new Installer();
		f.setVisible(true);
	}
} // @jve:decl-index=0:visual-constraint="10,10"