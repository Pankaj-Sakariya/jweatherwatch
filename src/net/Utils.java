package net;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.w3c.dom.Element;

public class Utils {
	public Utils() {
		// TODO Auto-generated constructor stub
	}

	public static String TimeStamp() {
		return "["
				+ new SimpleDateFormat("HH:mm:ss").format(new Date(System
						.currentTimeMillis())) + "] ";
	}

	public Image[] imageLodaer(String[] src) {
		Image img[] = new Image[src.length];
		for (int i = 0; i < img.length; ++i) {
			URL u = this.getClass().getResource("/" + src[i].trim());
			if (u != null)
				img[i] = new ImageIcon(u).getImage();
			else
				img[i] = new ImageIcon(src[i]).getImage();
		}
		return img;
	}

	public Image imageLodaer(String src) {
	
		if(new File(src).exists())
			return new ImageIcon(src).getImage();
		else{
		URL u = this.getClass().getResource("/" + src.trim());
			if(u!=null)
				return new ImageIcon(u).getImage();
		}
		return null;
			
	}

	static public void saveImage(Image img) {
		BufferedImage bufferedImage = new BufferedImage(100, 100,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();

		try {
			// Save as PNG
			File file = new File(System.currentTimeMillis() + ".png");
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e) {
		}
	}

	public static String getXMLValue(Element ele, String tag) {
		if (ele.getElementsByTagName(tag).item(0).getChildNodes().item(0) == null)
			return null;
		String s = ele.getElementsByTagName(tag).item(0).getChildNodes()
				.item(0).getNodeValue();
		// System.out.println(tag + ": " + s);
		return s;
	}


	public static void visitURL(String url) {
		try {System.out.println("Visiting: "+url);
		String os=System.getProperty("os.name").toLowerCase();
		if(os.contains("windows"))					
			Runtime.getRuntime().exec("rundll32 url.dll , FileProtocolHandler "+ url);
		if(os.contains("linux"))
			Runtime.getRuntime().exec("firefox "+url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static enum OS{
		WINDOWS,LINUX,MAC,ERROR
	}
	public static OS getOS(){
		String o=System.getProperty("os.name").toLowerCase();
		if(o.contains("windows"))
			return OS.WINDOWS;
		if(o.contains("mac"))
			return OS.MAC;
		if(o.contains("linux"))
			return OS.LINUX;
		
		return OS.ERROR;
		
		
	}
}