package gui;

import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.SettingsReader;
import net.Utils;


public class Splash extends JPanel {

	private static final long serialVersionUID = 1L;
	private ImageBox imageBox = null;
	Utils utils=new Utils();
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	/**
	 * This is the default constructor
	 */
	public Splash() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(150, 90, 631, 31));
		jLabel1.setFont(new Font("Dialog", Font.BOLD, 18));
		jLabel1.setText("Loading Stations");
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(45, 15, 391, 31));
		jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		jLabel.setText("Starting: "+SettingsReader.name);
		this.setSize(817, 448);
		this.setLayout(null);
		this.add(getImageBox(), null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
	}

	/**
	 * This method initializes imageBox	
	 * 	
	 * @return gui.ImageBox	
	 */
	private ImageBox getImageBox() {
		if (imageBox == null) {
			imageBox = new ImageBox();
			imageBox.setBounds(new Rectangle(60, 75, 76, 76));
			imageBox.setImage(utils
				.imageLodaer("other/bigrotation2.gif"));
		}
		return imageBox;
	}

	public void setLoadingText(String s){
		jLabel1.setText(s);
	}

}
