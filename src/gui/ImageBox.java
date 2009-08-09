package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class ImageBox extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3840358180384205226L;
	private Image image;
	private int width = 0, hight = 0, xoff = 0, yoff = 0;
	private boolean isInitialized=false;

	public ImageBox() {
		// TODO Auto-generated constructor stub
	}

	public ImageBox(Image image) {
		setImage(image);
	}

	public void setImage(Image image) {
		this.image = image;
		sizeUpdate();
		repaint();
	}


	private void sizeUpdate() {
		if (image == null)
			return;
		if (this.getHeight() <= 0 || this.getWidth() <= 0)
			return;
		width = image.getWidth(this);
		hight = image.getHeight(this);
		xoff = 0;
		yoff = 0;
		float resize = 1;
		if (width > this.getWidth()) {
			resize = (float) this.getWidth() / width;
			width = this.getWidth();
			hight = (int) (hight * resize);
			yoff = (this.getHeight() - hight) / 2;
		}
		if (hight > this.getHeight()) {
			resize = (float) this.getHeight() / hight;
			hight = this.getHeight();
			width = (int) (width * resize);
			xoff = (this.getWidth() - width) / 2;
		}
		if (xoff == 0 && yoff == 0) {
			xoff = (this.getWidth() - width) / 2;
			yoff = (this.getHeight() - hight) / 2;
		}
		// System.out.println("Whidth: " + width + "\nXoff: " + xoff +
		// "\nHight: "
		// + hight + "\nYoff: " + yoff);
		
		isInitialized=true;

	}

	public Image getImage() {
		return image;
	}

	@Override
	public void paint(Graphics g) {
		if(!isInitialized)return;
		g.setColor(getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(image, xoff, yoff, width , hight , this);
	}

	@Override
	public void setSize(Dimension d) {
		// TODO Auto-generated method stub
		super.setSize(d);
		
		sizeUpdate();
	}

	@Override
	public void setBounds(Rectangle r) {
		// TODO Auto-generated method stub
		super.setBounds(r);
		sizeUpdate();
	}

}
