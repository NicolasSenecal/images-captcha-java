package fr.upem.captcha;

import java.io.IOException;

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
	
  // difficulty
	private static int motherDepth = 0; // depth of all images to be displayed
	private static int trueCategoryDepth = 1; // depth of the true images

	private static Category motherCategory = null;
	private static Category trueCategory = null;

	private static ArrayList<URL> trueImages = new ArrayList<URL>();
  private static ArrayList<URL> falseImages = new ArrayList<URL>();
	private static ArrayList<URL> images = new ArrayList<URL>(); // Images to display

  // SETTERS - GETTERS
  /**
   * set a random category of images to display (motherCategory)
   * and a sub-category of images to pick (trueCategory)
   */
	public static void setRandomCategories () {
    // Random categories
    do {
      motherCategory = allCategory.getRandomCategory(motherDepth);
      trueCategory = motherCategory.getRandomCategory(trueCategoryDepth - motherDepth);
    } while (motherCategory == trueCategory);
	}

  /**
   * set the array of images to display in function of the current categories
   */
	public static void setRandomImages () {
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
    
    // Verification 
    /*
    for (URL image : images) {
      if (trueCategory.hasImage(image)) {
        System.out.println("=> OK :\n   " + image);
      } else {
        System.out.println("=> PAS OK :\n   " + image);
      }
    }
    */
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
  	trueImagesNb = (int)(Math.random() * 3 + 2);
  	trueImages.clear();
  	falseImages.clear();
  	images.clear();
  	setRandomCategories();
  	setRandomImages();    
  }
  
  /**
   * get the instruction message
   */
  public static String getMessage() {
    return ("Vous devez trouvez\n" 
    + trueCategory.getName() + " parmis\n" 
    + motherCategory.getName());
  }

  /**
   * compare the given image list with the true category images
   */
  public static Boolean checkImages(ArrayList<URL> selected) {
  	if (selected.size() != trueImages.size()) return false;
  	for (URL tmp : selected) {
      if (!trueImages.contains(tmp)) {
          return false;
      }
  	}
  	return true;
  }

  /**
   * increase the difficulty of the image identification
   */
  public static void increaseDifficulty() {
  	motherDepth++;
  	trueCategoryDepth++;
  }

  /**
   * reset the difficulty of the image identification
   */
  public static void resetDifficulty() {
  	motherDepth = 0;
  	trueCategoryDepth = 1;
  }

}
