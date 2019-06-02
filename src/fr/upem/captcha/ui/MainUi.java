/**
 * @authors : Olivier MEYER - Nicolas SENECAL
 * @date : 2019/05/18
 * @file : MainUi.java
 * @package : fr.upem.captcha.ui
 */
package fr.upem.captcha.ui;

import fr.upem.captcha.Logic;

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
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Class which manages the UI of the app : displaying the interface, reseting
 * the program
 */
public class MainUi {

  private static List<URL> allImages = new ArrayList<URL>();
  private static List<URL> selectedImages = new ArrayList<URL>();
  private static JFrame frame;

  public static void main(String[] args) throws IOException {
    init();
  }

  /**
   * initiate the application
   */
  private static void init() throws IOException {
    Logic.init();
    resetDisplay();
  }

  /**
   * kill the application
   */
  private static void killDisplay() {
    if (frame != null) {
      frame.dispose();
    }
  }

  /**
   * reset the application display (without reseting the logic)
   */
  private static void resetDisplay() {
    if (frame != null) {
      frame.dispose();
    }
    selectedImages.clear();
    frame = new JFrame("Capcha"); // Crï¿½ation de la fenï¿½tre principale
    GridLayout layout = createLayout();  // Crï¿½ation d'un layout de type Grille avec 4 lignes et 3 colonnes
    JButton okButton = createOkButton();

    frame.setLayout(layout);  // affection du layout dans la fenï¿½tre.
    frame.setSize(1024, 768); // dï¿½finition de la taille
    frame.setResizable(false);  // On dï¿½finit la fenï¿½tre comme non redimentionnable
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenï¿½tre on quitte le programme. 

    allImages = Logic.getImages();

    for (URL url : allImages) {
      try {
        frame.add(createLabelImage(url));
      } catch (IOException e) {
        System.err.println("err : tried to load an invalid image");
        e.printStackTrace();
      }
    }

    frame.add(new JTextArea(Logic.getMessage()));
    frame.add(okButton);
    frame.setVisible(true);
  }

  /**
   * creates a grid layout
   *
   * @return a 4x3 grid layout
   */
  private static GridLayout createLayout() {
    return new GridLayout(4, 3);
  }

  /**
   * creates a ok button
   *
   * @return a ok JButton
   */
  private static JButton createOkButton() {
    return new JButton(new AbstractAction("Vérifier") { //ajouter l'action du bouton

      @Override
      public void actionPerformed(ActionEvent arg0) {
        EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des Ã©vÃ¨nements

          @Override
          public void run() { // c'est un runnable
            validateSelection();
          }
        });
      }
    });
  }

  /**
   * creates a label image
   *
   * @param URL of the image
   * @return a JLabel with the given image
   */
  private static JLabel createLabelImage(URL url) throws IOException {
    BufferedImage img = ImageIO.read(url); //lire l'image
    Image sImage = img.getScaledInstance(1024 / 3, 768 / 4, Image.SCALE_SMOOTH); //redimentionner l'image

    final JLabel label = new JLabel(new ImageIcon(sImage)); // crÃ©er le composant pour ajouter l'image dans la fenÃªtre

    label.addMouseListener(new MouseListener() { //Ajouter le listener d'Ã©venement de souris
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
      public void mouseClicked(MouseEvent arg0) { //ce qui nous intÃ©resse c'est lorsqu'on clique sur une image, il y a donc des choses Ã  faire ici
        EventQueue.invokeLater(new Runnable() {

          @Override
          public void run() {
            if (!isSelected) {
              label.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
              isSelected = true;
              selectedImages.add(url);
            } else {
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

  /**
   * routine launched when the validation button is pressed
   */
  public static void validateSelection() {
    if (Logic.checkImages(selectedImages)) {
      JOptionPane.showMessageDialog(null, "c'est validé !");
      killDisplay();
      return;
    } else {
      JOptionPane.showMessageDialog(null, "c'est raté... le prochain sera plus difficile !");
      try {
        Logic.increaseDifficulty();
      } catch (ClassNotFoundException e) {
        JOptionPane.showMessageDialog(null, "profondeur maximale atteinte : vous êtes un robot. Si ce n'est pas le cas veuillez contacter l'administrateur de votre service.");
        killDisplay();
        return;
      }
    }
    resetDisplay();
  }
}
