package gui;

import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.Utils;


public class Splash extends JPanel {

	private static final long serialVersionUID = 1L;
	private ImageBox imageBox = null;
	Utils utils=new Utils();
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private ImageBox imageBox1 = null;

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
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(150, 150, 226, 31));
		jLabel2.setText("Snarl WeatherWatcher is powered by:");
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(150, 90, 631, 31));
		jLabel1.setFont(new Font("Dialog", Font.BOLD, 18));
		jLabel1.setText("Loading Stations");
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(45, 15, 391, 31));
		jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		jLabel.setText("Starting: "+Gui.name);
		this.setSize(817, 448);
		this.setLayout(null);
		this.add(getImageBox(), null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(jLabel2, null);
		this.add(getImageBox1(), null);
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

	/**
	 * This method initializes imageBox1	
	 * 	
	 * @return gui.ImageBox	
	 */
	private ImageBox getImageBox1() {
		if (imageBox1 == null) {
			imageBox1 = new ImageBox();
			imageBox1.setBounds(new Rectangle(375, 150, 226, 31));
			imageBox1.setImage(utils
				.imageLodaer("logo/accuweather_logotype_color.png"));
		}
		return imageBox1;
	}
	public void setLoadingText(String s){
		jLabel1.setText(s);
	}

}
