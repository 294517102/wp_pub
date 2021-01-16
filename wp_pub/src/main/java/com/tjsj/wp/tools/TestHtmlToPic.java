/**
 * 
 */
package com.tjsj.wp.tools;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**
 * @author andrew-silence
 *
 */
public class TestHtmlToPic {
 private static void ont() throws MalformedURLException, IOException, InterruptedException {
	
	  //load the webpage into the editor
     //JEditorPane ed = new JEditorPane(new URL("http://www.google.com"));
     JEditorPane ed = new JEditorPane(new URL("https://t.xsz.tjsjnet.com/"));
     System.out.println("10");
     Thread.sleep(10000);
     ed.setSize(1000,1000);

     //create a new image
     BufferedImage image = new BufferedImage(ed.getWidth(), ed.getHeight(),
                                             BufferedImage.TYPE_INT_ARGB);

     //paint the editor onto the image
     SwingUtilities.paintComponent(image.createGraphics(), 
                                   ed, 
                                   new JPanel(), 
                                   0, 0, image.getWidth(), image.getHeight());
     //save the image to file
     ImageIO.write((RenderedImage)image, "png", new File("C:\\Users\\andrew-silence\\Desktop\\html.png"));
         System.out.println("ok");
 }
 public static void main(String[] args) {
	 try {
		ont();
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
