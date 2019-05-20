package fr.upem.captcha;

import fr.upem.captcha.images.Category;
import fr.upem.captcha.images.AllCategory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Logic {

  // MEMBERS
  private static Category allCategory = new AllCategory();

  private static int imagesNb = 12; // all images to display
  private static int trueImagesNb = 4; // minimum numbers of images to find

  private static Category motherCategory = null;
  private static Category trueCategory = null;

  private static ArrayList<URL> trueImages = new ArrayList<URL>();
  private static ArrayList<URL> falseImages = new ArrayList<URL>();
  private static ArrayList<URL> images = new ArrayList<URL>(); // Images to display

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
    trueImages = trueCategory.getRandomImages(trueImagesNb);
    falseImages = motherCategory
            .getRandomImages(imagesNb - trueImages.size(), trueCategory);
    // we take into account the case where trueImages.size() is lower than the one expected
    // and we excluded the trueCategory

    images.addAll(trueImages);
    images.addAll(falseImages);
    Collections.shuffle(images); // random order
    // System.out.println("images to display = " + images + "\n");
  }

  /**
   * get all the images
   */
  public static ArrayList<URL> getImages() {
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
    trueImages.clear();
    falseImages.clear();
    images.clear();
    setRandomImages();
  }

  /**
   * get the instruction message
   */
  public static String getMessage() {
    return ("Vous devez trouvez\n"
            + trueCategory.getName() + " parmi\n"
            + motherCategory.getName());
  }

  /**
   * compare the given image list with the true category images
   */
  public static Boolean checkImages(ArrayList<URL> selected) {
    if (selected.size() != trueImages.size()) {
      return false;
    }
    for (URL tmp : selected) {
      if (!trueImages.contains(tmp)) {
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
