/**
 * @authors : Olivier MEYER - Nicolas SENECAL
 * @date : 2019/05/18
 * @file : CategoryTools.java
 * @package : fr.upem.captcha.images
 */
package fr.upem.captcha.images;

/**
 * Useful toolbox for categories
 */
public class CategoryTools {

  /**
   * Repeat a string
   * 
   * @param count Number of the repetition
   * @param with String to repeat
   * @return The repeated string
   */
  public static String repeat(int count, String with) {
    return new String(new char[count]).replace("\0", with);
  }
}
