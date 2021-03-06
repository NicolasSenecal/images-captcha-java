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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Collections;

/**
 * Abstract class for all possible and unimaginable categories of images, as
 * well as their subcategories
 */
public abstract class Category implements Images {

  /**
   * URL list of all images present directly in the current folder
   */
  private ArrayList<URL> currentImages;
  /**
   * List of instances of each subcategories
   */
  private ArrayList<Category> categories;

  /**
   * Constructor, populate all subcategories and current images Catch and show
   * IOException if there are errors
   */
  public Category() {
    this.currentImages = new ArrayList<URL>();
    this.categories = new ArrayList<Category>();
    try {
      this.populateCurrentImages();
      this.populateCategories();
    } catch (IOException ex) {
      Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Get currentImages copy
   *
   * @return Return the URL list of all images present directly in the current
   * folder
   */
  public ArrayList<URL> getCurrentImages() {
    return (ArrayList<URL>) currentImages.clone();
  }

  /**
   * Get all images of the category
   *
   * @return the URL list of all images of the category, child included
   */
  public ArrayList<URL> getPhotos() {
    return this.getPhotos(new ArrayList<Category>());
  }

  /**
   * Get all images of the category, ignoring a sub category instance
   *
   * @param excluded category instance to exclude
   * @return the URL list of all images of the category, child included
   */
  public ArrayList<URL> getPhotos(Category excluded) {
    ArrayList<Category> excludedList = new ArrayList<Category>();
    excludedList.add(excluded);
    return this.getPhotos(excludedList);
  }

  /**
   * Get all images of the category, ignoring a sub category instance
   *
   * @param excludedList list of categories instance to exclude
   * @return a list of random images
   */
  public ArrayList<URL> getPhotos(ArrayList<Category> excludedList) {
    ArrayList<URL> images = new ArrayList<URL>();

    // add sub categories images
    for (Category category : categories) {
      if (!excludedList.contains(category)) {
        images.addAll(category.getPhotos(excludedList));
      }
    }

    // add current images
    images.addAll(this.getCurrentImages());

    return images;
  }

  /**
   * Get a list of random images, sub categories included
   *
   * @param count number of images to get
   * @return a list of random images
   */
  public List<URL> getRandomPhotosURL(int count) {
    return this.getRandomPhotosURL(count, new ArrayList<Category>());
  }

  /**
   * Get an random images, sub categories included
   *
   * @return URL of the random images
   */
  public URL getRandomPhotoURL() {
    return this.getRandomPhotosURL(1, new ArrayList<Category>()).get(0);
  }

  /**
   * Get a list of random images, ignoring a category instance
   *
   * @param count number of images to get
   * @param excluded category instance to exclude
   * @return a list of random images
   */
  public List<URL> getRandomPhotosURL(int count, Category excluded) {
    ArrayList<Category> excludedList = new ArrayList<Category>();
    excludedList.add(excluded);
    return this.getRandomPhotosURL(count, excludedList);
  }

  /**
   * Get a list of random images, ignoring some categories instances
   *
   * @param count number of images to get
   * @param excludedList list of categories instance to exclude
   * @return a list of random images
   */
  public List<URL> getRandomPhotosURL(int count, ArrayList<Category> excludedList) {
    ArrayList<URL> allImages = this.getPhotos(excludedList);
    Collections.shuffle(allImages); // change the order of images randomly
    int min = Math.min(count, allImages.size()); // if there are not enough images
    return new ArrayList<URL>(allImages.subList(0, min));
  }

  /**
   * Get the current directory path
   *
   * @return Return the relative path of the current directory
   */
  private Path getCurrentPath() {
    String packageName = this.getClass().getPackage().getName();
    String currentPath = System.getProperty("java.class.path") + "/" + packageName.replace('.', '/');
    return Paths.get(currentPath);
  }

  /**
   * Get a random sub category by depth.If there is no subcategory relative to
   * this depth, the deepest category would be returned
   *
   * @param depth depth of category to get
   * @return a sub category relative to the depth
   */
  public Category getRandomCategory(int depth) {
    if (this.categories.isEmpty() || depth <= 0) {
      return this;
    }
    ArrayList<Category> allCategories = new ArrayList<Category>(this.categories);
    Collections.shuffle(allCategories); // change the order of categories randomly
    return allCategories.get(0).getRandomCategory(depth - 1);
  }

  /**
   * Get a random subcategory, only takes into account direct children If there
   * is no subcategory, the current category is returned
   *
   * @return a sub category of the current category
   */
  public Category getRandomCategory() {
    return this.getRandomCategory(1);
  }

  /**
   * Test if an image is present in the list, including children
   *
   * @param image URL of the image to test
   * @return true if it is, false otherwise
   */
  public boolean isPhotoCorrect(URL image) {
    return this.getPhotos().contains(image);
  }

  /**
   * Get the name of the current class file
   *
   * @return Return the current class file
   */
  private String getClassFileName() {
    return this.getClass().getSimpleName() + ".class";
  }

  /**
   * Populate "currentImages" array with all the image files present in the
   * current folder
   */
  private void populateCurrentImages() throws IOException {
    List<String> images = new ArrayList<String>();
    Path currentPath = this.getCurrentPath();

    images = Files.walk(currentPath, 1)
            .map(Path::getFileName)
            .map(Path::toString)
            .filter(n -> n.contains(".jpg") || n.contains(".jpeg") || n.contains(".png"))
            .collect(Collectors.toList());

    for (String image : images) {
      currentImages.add(this.getClass().getResource(image));
    }
  }

  /**
   * Get the list of name of subFolders of current path
   *
   */
  private List<String> getSubFolders() throws IOException {
    List<String> directories = new ArrayList<String>();
    Path currentPath = this.getCurrentPath();

    directories = Files.walk(currentPath, 1)
            .map(Path::getFileName)
            .map(Path::toString)
            .filter(n -> !n.contains("."))
            .collect(Collectors.toList());
    directories.remove(0); // We remove the current folder
    return directories;
  }

  /**
   * Populate "categories" array with all the Categories in sub folders
   *
   */
  private void populateCategories() throws IOException {
    List<String> subFolders = this.getSubFolders();
    Path currentPath = this.getCurrentPath();

    for (String subFolder : subFolders) {
      Path subFolderPath = Paths.get(currentPath + "/" + subFolder);
      String packageName = this.getClass().getPackage().getName() + "." + subFolderPath.getFileName();

      List<String> classNames = Files.walk(subFolderPath, 1)
              .map(Path::getFileName)
              .map(Path::toString)
              .filter(n -> n.contains(".class")) // get class file
              .map(n -> packageName + "." + n.replace(".class", "")) // get class full name (with package)
              .collect(Collectors.toList());

      for (String className : classNames) {
        Object classObject = null;
        try {
          classObject = Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException ex) {
          Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
          Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
          Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
          Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
          Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
          Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }

        // If classObject is a child of the current class
        if (this.getClass().isInstance(classObject)) {
          categories.add((Category) classObject); // add to child categories
        }
      }
    }
  }

  /**
   * returns true if this category has a sub category
   */
  public boolean hasSubCategory() {
    return categories.size() > 0;
  }

  /**
   * Get the name description of the category
   *
   * @return the name description of the category
   */
  abstract public String getName();

  /**
   * Override equals function
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Category other = (Category) obj;
    if (getName() == null) {
      if (other.getName() != null) {
        return false;
      }
    } else if (!getName().equals(other.getName())) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 58;
    int result = 22;
    result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
    return result;
  }

  /**
   * Override toString method for display category informations
   *
   * @return the string description of the category
   */
  @Override
  public String toString() {
    return this.toString(0);
  }

  /**
   * ToString method for display category informations with tabulation for
   * clarity
   *
   * @param nbTab tab number before display category informations
   * @return the string description of the category
   */
  public String toString(int nbTab) {
    StringBuilder str = new StringBuilder();
    str.append("\n");

    // Category name and description 
    str.append(CategoryTools.repeat("  ", nbTab));
    str.append("=== ");
    str.append(this.getClass().getSimpleName());
    str.append(" - ");
    str.append(this.getName());
    str.append(" ===");

    // Category current images
    str.append("\n");
    str.append(CategoryTools.repeat("  ", nbTab));
    str.append("  Images numbers = ");
    str.append(currentImages.size());

    // Category sub cattegories
    str.append("\n");
    str.append(CategoryTools.repeat("  ", nbTab));
    str.append("  sub categories numbers = ");
    str.append(categories.size());
    str.append("\n");
    if (categories.size() > 0) {
      str.append(CategoryTools.repeat("  ", nbTab));
      str.append("  sub categories {");
      for (Category category : categories) {
        str.append("\n");
        str.append(category.toString(nbTab + 2));
      }
      str.append("\n");
      str.append(CategoryTools.repeat("  ", nbTab));
      str.append("  }");
    }
    return str.toString();
  }
}
