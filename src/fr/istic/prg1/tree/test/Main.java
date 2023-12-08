package fr.istic.prg1.tree.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fr.istic.prg1.tree.Image;

public abstract class Main {

	public static final String DIR = "";
	private static final String ARB1 = DIR + "a1.arb";
	private static final String ARB2 = DIR + "a2.arb";
	private static final String ARB3 = DIR + "a3.arb";
	private static final String ARB5 = DIR + "a5.arb";
	private static final String ARB6 = DIR + "a6.arb";
	private static final String CARTOON = DIR + "cartoon.arb";

	/**
	 * @param fileName nom du fichier à lire
	 * @return Image créée à partir du fichier
	 */
	public static Image readFile(String fileName) {
		Image image = new Image();
		try {
			image.xCreateTreeFromFile(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return image;
	}

	public static void main(String[] args) {
		Image image1 = readFile(CARTOON);
		System.out.print(image1.height());
	}

}
