/**
 * @authors : Olivier MEYER - Nicolas SENECAL
 * @date : 2019/05/06
 * @file : Category.java
 * @package : fr.upem.captcha.images
 */
package fr.upem.captcha.images;

import java.io.IOException;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Category {

  private ArrayList<URL> currentImages;
  private ArrayList<Category> categories;

  /**
   * Constructor
   */
  public Category() {
    this.currentImages = new ArrayList<URL>();
    //this.setCurrentImages();
  }

  //Getters / Setters
  public ArrayList<URL> getCurrentImages() {
    return currentImages;
  }

  /**
   * Get the current directory path
   *
   * @return Return the absolute path of the current directory
   */
  public Path getCurrentPath() {
    String className = this.getClass().getSimpleName() + ".class";
    URL url = this.getClass().getResource(className); // URL of the actual class file
    File file = new File(url.getPath()); // Actual class file
    return Paths.get(file.getParent()); // Path of the parent
  }

  /**
   * Populate "currentImages" array with all the image files present in the
   * current folder
   */
  public void populateCurrentImages() {
    List<String> images = new ArrayList<String>();
    Path currentPath = this.getCurrentPath();
    
    try {
      images = Files.walk(currentPath, 1)
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

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("-- ");
    str.append(this.getClass().getSimpleName());
    str.append("\n  -- currentImages = ");
    str.append(currentImages.toString());
    return str.toString();
  }

}
