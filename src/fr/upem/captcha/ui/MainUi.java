package fr.upem.captcha.ui;

import fr.upem.captcha.Logic;
import fr.upem.captcha.images.Category;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MainUi {

	private static ArrayList<URL> allImages = new ArrayList<URL>();
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
	private static JFrame frame; 
	
	public static void main(String[] args) throws IOException {
		init();
	}
	
	private static void init() throws IOException {
		// init display
		if (frame != null) frame.dispose();
		frame = new JFrame("Capcha"); // Création de la fenêtre principale
		GridLayout layout = createLayout();  // Création d'un layout de type Grille avec 4 lignes et 3 colonnes
		JButton okButton = createOkButton();
		
		frame.setLayout(layout);  // affection du layout dans la fenêtre.
		frame.setSize(1024, 768); // définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme. 
		
		Logic.init();
		
		allImages = Logic.getImages();

    for (URL url : allImages) {
//    	System.out.println(url.getFile());
    	frame.add(createLabelImage(url));
    }
			
		frame.add(new JTextArea(Logic.getMessage()));
		frame.add(okButton);
		frame.setVisible(true);
	}
	
	private static GridLayout createLayout(){
		return new GridLayout(4,3);
	}
	
	private static JButton createOkButton(){
		return new JButton(new AbstractAction("Vérifier") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des évènements
					
					@Override
					public void run() { // c'est un runnable
						if (Logic.checkImages(selectedImages)) {
							System.out.println("c'est validey");
							Logic.resetDifficulty();
						} else {
							System.out.println("c'est pabon");
							Logic.increaseDifficulty();
						}
						try {
							TimeUnit.SECONDS.sleep(1);
							init();
						} catch (Exception e) {
							System.err.println("error while reloading");
							e.printStackTrace();
						}
					}
				});
			}
		});
	}
	
	private static JLabel createLabelImage(URL url) throws IOException {
		BufferedImage img = ImageIO.read(url); //lire l'image
		Image sImage = img.getScaledInstance(1024/3,768/4, Image.SCALE_SMOOTH); //redimentionner l'image
		
		final JLabel label = new JLabel(new ImageIcon(sImage)); // créer le composant pour ajouter l'image dans la fenêtre
		
		label.addMouseListener(new MouseListener() { //Ajouter le listener d'évenement de souris
			private boolean isSelected = false;
			
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
		
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) { //ce qui nous intéresse c'est lorsqu'on clique sur une image, il y a donc des choses à faire ici
				EventQueue.invokeLater(new Runnable() { 
					
					@Override
					public void run() {
						if(!isSelected){
							label.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
							isSelected = true;
							selectedImages.add(url);
						}
						else {
							label.setBorder(BorderFactory.createEmptyBorder());
							isSelected = false;
							selectedImages.remove(url);
						}
						
					}
				});
				
			}
		});
		
		return label;
	}
}
