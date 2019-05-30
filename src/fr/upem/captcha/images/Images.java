/**
 * @authors : Olivier MEYER - Nicolas SENECAL
 * @date : 2019/05/30
 * @file : Images.java
 * @package : fr.upem.captcha.images
 */
package fr.upem.captcha.images;

import java.net.URL;
import java.util.List;

public interface Images {

  /**
   * Get all images of the category
   *
   * @return the URL list of all images of the category, child included
   */
  public List<URL> getPhotos();

  /**
   * Get a list of random images
   *
   * @param count number of images to get
   * @return a list of random images
   */
  public List<URL> getRandomPhotosURL(int count);

  /**
   * Get an random images
   *
   * @return URL of the random images
   */
  public URL getRandomPhotoURL();

  /**
   * Test if an image is present in the list, including children
   *
   * @param image URL of the image to test
   * @return true if it is, false otherwise
   */
  public boolean isPhotoCorrect(URL image);

}
