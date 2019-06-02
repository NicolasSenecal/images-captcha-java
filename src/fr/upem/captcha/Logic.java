/**
 * @authors : Olivier MEYER - Nicolas SENECAL
 * @date : 2019/05/18
 * @file : Logic.java
 * @package : fr.upem.captcha
 */
package fr.upem.captcha;

import fr.upem.captcha.images.Category;
import fr.upem.captcha.images.AllCategory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
/**
 * Class which manages the overall logic of the application
 * ie moving in the images tree hierarchy, and setting up images to display
 */
public class Logic {

  // MEMBERS
  private static Category allCategory = new AllCategory();

  private static final int IMAGES_NB = 12; // all images to display
  private static int trueImagesNb = 4; // minimum numbers of images to find

  private static Category motherCategory = null;
  private static Category trueCategory = null;

  private static List<URL> images = new ArrayList<URL>(); // Images to display

  // SETTERS - GETTERS
  /**
   * set a random category of images to display (motherCategory) and a
   * sub-category of images to pick (trueCategory)
   */
  public static void setInitialCategories() {
    // Random categories
    motherCategory = allCategory.getRandomCategory(0);
    trueCategory = motherCategory.getRandomCategory(1);
  }

  /**
   * set the array of images to display in function of the current categories
   */
  public static void setRandomImages() {
    // Images
    List<URL> trueImages = trueCategory.getRandomPhotosURL(trueImagesNb);
    trueImagesNb = trueImages.size();
    List<URL> falseImages = motherCategory
            .getRandomPhotosURL(IMAGES_NB - trueImagesNb, trueCategory);
    // we take into account the case where trueImages.size() is lower than the one expected
    // and we excluded the trueCategory

    images.addAll(trueImages);
    images.addAll(falseImages);
    Collections.shuffle(images); // random order
	}

  /**
   * get all the images
   *
   * @return all the stored images
   */
  public static List<URL> getImages() {
    return images;
  }

  // METHODS
  /**
   * initiate the categories and images
   */
  public static void init() {
    resetDifficulty();
  }

  /**
   * reset the images
   */
  public static void resetImages() {
    trueImagesNb = (int) (Math.random() * 3 + 2);
    images.clear();
    setRandomImages();
  }

  /**
   * get the instruction message
   * 
   * @return the instruction message to display on the UI
   */
  public static String getMessage() {
    return ("Vous devez trouvez\n"
            + trueCategory.getName() + " parmi\n"
            + motherCategory.getName());
  }

  /**
   * compare the given image list with the true category images
   */
  public static Boolean checkImages(List<URL> selected) {
    if (selected.size() != trueImagesNb) {
      return false;
    }
    for (URL tmp : selected) {
      if (!trueCategory.isPhotoCorrect(tmp)) {
        return false;
      }
    }
    return true;
  }

  /**
   * increase the difficulty of the image identification -> go 1 category deeper
   *
   * @throws ClassNotFoundException when reaching maximum tree depth
   */
  public static void increaseDifficulty() throws ClassNotFoundException {
    motherCategory = trueCategory;
    trueCategory = motherCategory.getRandomCategory(1);
    if (motherCategory == trueCategory) {
      throw new ClassNotFoundException();
    }
    resetImages();
  }

  /**
   * reset the difficulty of the image identification -> go back to top category
   */
  public static void resetDifficulty() {
    // Random categories
    motherCategory = allCategory.getRandomCategory(0);
    trueCategory = motherCategory.getRandomCategory(1);
    resetImages();
  }

}
