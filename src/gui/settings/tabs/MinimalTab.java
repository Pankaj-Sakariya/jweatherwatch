package gui.settings.tabs;

import gui.Gui;

import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

import net.SettingsReader;

public class MinimalTab extends SettingsTab{

	private static final long serialVersionUID = 1L;

	private JToggleButton jToggleButton_MinimalShifted = null;

	private JSlider jSlider_RowCount = null; // @jve:decl-index=0:visual-constraint="0,14"

	private JLabel jLabel = null;

	private JSlider jSlider_Size = null;

	private JLabel jLabel1 = null;

	/**
	 * This is the default constructor
	 */
	public MinimalTab() {
		super("MinimalView");
	
	
		initialize();
}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(60, 120, 151, 16));
		jLabel1.setText("Set size of a row "+SettingsReader.minimumViewSize);
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(60, 60, 151, 16));
		jLabel.setText("Number of Rows "+SettingsReader.mininimalViewRows);
		this.setSize(300,200);
		this.setLayout(null);
		this.add(getJSlider_RowCount(), null);
		this.add(getJToggleButton_MinimalShifted(), null);
		this.add(jLabel, null);
		this.add(getJSlider_Size(), null);
		this.add(jLabel1, null);
	}

	/**
	 * This method initializes jToggleButton_MinimalShifted
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getJToggleButton_MinimalShifted() {
		if (jToggleButton_MinimalShifted == null) {
			jToggleButton_MinimalShifted = new JToggleButton();
			jToggleButton_MinimalShifted.setBounds(new Rectangle(75, 30, 151, 16));
			jToggleButton_MinimalShifted.setText("Shifted View");

		}
		return jToggleButton_MinimalShifted;
	}

	public void load() {
		jToggleButton_MinimalShifted
		.setSelected(SettingsReader.minimalView_Shifted);
		jSlider_RowCount.setValue(SettingsReader.mininimalViewRows);
		jSlider_Size.setValue(SettingsReader.minimumViewSize);
	}

	public void save(Gui gui) {
	SettingsReader.minimalView_Shifted = jToggleButton_MinimalShifted
				.isSelected();
		SettingsReader.mininimalViewRows=jSlider_RowCount.getValue();
		SettingsReader.minimumViewSize=jSlider_Size.getValue();
	}

	/**
	 * This method initializes jSlider_RowCount
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getJSlider_RowCount() {
		if (jSlider_RowCount == null) {
			jSlider_RowCount = new JSlider();
			jSlider_RowCount.setBounds(new Rectangle(75, 75, 151, 31));
			jSlider_RowCount.setMinimum(1);
			jSlider_RowCount.setMaximum(20);
			jSlider_RowCount.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					jLabel.setText("Number of Rows "+jSlider_RowCount.getValue());
				}
			});
		}
		return jSlider_RowCount;
	}

	/**
	 * This method initializes jSlider_Size	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider_Size() {
		if (jSlider_Size == null) {
			jSlider_Size = new JSlider();
			jSlider_Size.setBounds(new Rectangle(75, 135, 151, 31));
			jSlider_Size.setMinimum(65);
			jSlider_Size.setMaximum(145);
			jSlider_Size.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					jLabel1.setText("Set size of a row "+jSlider_Size.getValue());
					}
			});
		}
		return jSlider_Size;
	}

}  //  @jve:decl-index=0:visual-constraint="23,13"
