package fr.upem.captcha;

import java.io.IOException;

import fr.upem.captcha.images.Category;
import fr.upem.captcha.images.AllCategory;

public class Main {

	public static void main(String[] args) {
		Category cat = new AllCategory();
		System.out.println(cat);
	}
}
