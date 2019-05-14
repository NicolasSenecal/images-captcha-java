package fr.upem.captcha;

import java.io.IOException;

import fr.upem.captcha.images.Category;

public class Main {

	public static void main(String[] args) throws IOException {
		Category cat = new Category();
		cat.populateCurrentImages();
		System.out.println(cat);
	}
}
