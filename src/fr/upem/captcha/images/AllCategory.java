/**
 * @authors : Olivier MEYER - Nicolas SENECAL
 * @date : 2019/05/18
 * @file : All.java
 * @package : fr.upem.captcha.images
 */
package fr.upem.captcha.images;

import fr.upem.captcha.images.Category;

public class AllCategory extends Category {

  /**
   * Constructor
   */
  public AllCategory() {
    super();
  }

  /**
   * Get the name description of the category
   *
   * @return the name description of the category
   */
  @Override
  public String getName() {
    return "tous ces bons plats";
  }

}
