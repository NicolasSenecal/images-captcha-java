/**
 * @authors : Olivier MEYER - Nicolas SENECAL
 * @date : 2019/05/06
 * @file : Category.java
 * @package : fr.upem.captcha.images
 */
package fr.upem.captcha.images;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Category {

  private ArrayList<URL> currentImages;
  private ArrayList<Category> categories;

  /**
   * Constructor
   */
  protected Category() {
    this.currentImages = new ArrayList<URL>();
    //this.setCurrentImages();
  }

  /**
   * Get the current directory path
   *
   * @return Return the relatif path of the current directory
   */
  private Path getCurrentClassPath() {
    String packageName = "../src/" + this.getClass().getPackage().getName();
    String currentPath = packageName.replace('.', '/');
    return Paths.get(currentPath);
  }

  /**
   * Set currentImages with all images in the actual path
   */
  private void setCurrentImages() {
    List<String> images = null;
    Path classPath = this.getCurrentClassPath();
    try {
      images = Files.walk(classPath, 1)
              .map(Path::getFileName)
              .map(Path::toString)
              .filter(n -> n.contains(".jpg") || n.contains(".jpeg") || n.contains(".png"))
              .collect(Collectors.toList());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    for (String image : images) {
      currentImages.add(this.getClass().getResource(image));
    }
  }
}
