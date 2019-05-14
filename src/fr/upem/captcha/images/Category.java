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
   * @return Return the relative path of the current directory
   */
  private Path getCurrentPath() {
    String packageName = "../src/" + this.getClass().getPackage().getName();
    String currentPath = packageName.replace('.', '/');
    // return Paths.get(currentPath);
    return Paths.get("./");
  }

  /**
   * Populate "currentImages" array with all the image files present in the current folder
   */
  public void populateCurrentImages() {
    List<String> images = new ArrayList<String>();
    Path currentPath = this.getCurrentPath();
    currentImages.add(this.getClass().getResource("./riz/1.jpg"));
    try {
      //System.out.println( classPath);
      //System.out.println(Files.walk(currentPath, 1));
  
  //  images = Files.walk(classPath, 1)
  //          .map(Path::getFileName)
  //          .map(Path::toString)
  //          .filter(n -> n.contains(".jpg") || n.contains(".jpeg") || n.contains(".png"))
  //          .collect(Collectors.toList());

      images = Files.walk(currentPath, 1)
              .map(Path::getFileName)
              .map(Path::toString)
              .collect(Collectors.toList());

      System.out.println(images);
      
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
    return "Category [currentImages=" + currentImages.toString() + ", categories=" + categories + "]";
  }
  
  
  
}
