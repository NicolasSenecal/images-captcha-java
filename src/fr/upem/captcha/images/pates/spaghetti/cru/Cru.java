/**
 * @authors : Olivier MEYER - Nicolas SENECAL
 * @date : 2019/05/18
 * @file : Cru.java
 * @package : fr.upem.captcha.images.pates.spaghetti.cru
 */
package fr.upem.captcha.images.pates.spaghetti.cru;

import fr.upem.captcha.images.pates.spaghetti.Spaghetti;

/**
 * "Spaghetti Cru" category, sub-category of Spaghetti
 */
public class Cru extends Spaghetti {

  /**
   * Constructor
   */
  public Cru() {
    super();
  }

  /**
   * Get the name description of the category
   *
   * @return the name description of the category
   */
  @Override
  public String getName() {
    return "des Spaghetti Cru";
  }
}
