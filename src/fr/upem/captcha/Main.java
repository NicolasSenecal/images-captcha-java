package fr.upem.captcha;

import java.io.IOException;

import fr.upem.captcha.images.Category;
import fr.upem.captcha.images.AllCategory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

  public static void main(String[] args) {
    // we insanticie all the categories tree
    Category allCategory = new AllCategory();
    int imagesNb = 12; // all images to display
    int trueImagesNb = 4; // minimum numbers of images to find

    // difficulty
    int motherDepth = 1; // depth of all images to be displayed
    int trueCategoryDepth = 3; // depth of the true images

    Category motherCategory = null;
    Category trueCategory = null;
    ArrayList<URL> images = new ArrayList<URL>(); // Images to display

    // Random categories
    do {
      motherCategory = allCategory.getRandomCategory(motherDepth);
      trueCategory = motherCategory.getRandomCategory(trueCategoryDepth - motherDepth);
    } while (motherCategory == trueCategory);

    System.out.print("\n === Vous devez trouvez " + trueCategory.getName());
    System.out.println(" parmis " + motherCategory.getName() + " ===\n");

    // Images
    ArrayList<URL> trueImages = trueCategory.getRandomImages(trueImagesNb);
    ArrayList<URL> falseImages = motherCategory.
            getRandomImages(imagesNb - trueImages.size(), trueCategory);
    // we take into account the case where trueImages.size() is lower than the one expected
    // and we excluded the trueCategory

    images.addAll(trueImages);
    images.addAll(falseImages);
    Collections.shuffle(images); // random order
    // System.out.println("images to display = " + images + "\n");
    
    // Verification 
    for (URL image : images) {
      if (trueCategory.hasImage(image)) {
        System.out.println("=> OK :\n   " + image);
      } else {
        System.out.println("=> PAS OK :\n   " + image);
      }

    }
  }
}
