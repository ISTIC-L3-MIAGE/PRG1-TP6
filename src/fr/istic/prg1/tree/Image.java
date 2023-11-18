package fr.istic.prg1.tree;

/**
 * @author Antonella Atterey <antonella.atterey@etudiant.univ-rennes1.fr>
 * @author Ezan Tahi <ezan.tahi@etudiant.univ-rennes1.fr>
 * @class L3 MIAGE 2023/2024
 * 
 *        Classe décrivant les images en noir et blanc de 256 sur 256 pixels
 *        sous forme d'arbres binaires.
 * 
 */

import java.util.Scanner;

import fr.istic.prg1.tree_util.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;

public class Image extends AbstractImage {
	private static final Scanner standardInput = new Scanner(System.in);

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}

	/**
	 * Réalise un parcours préfixe pour copier tous les noeuds de l’image dont
	 * l’itérateur est it2 dans this
	 *
	 * @param it1 itérateur de this
	 * @param it2 itérateur de l’image à copier
	 */
	public void copyWithPreOrderTraversal(Iterator<Node> it1, Iterator<Node> it2) {
		if (!it2.isEmpty()) {
			it1.addValue(it2.getValue());

			it1.goLeft();
			it2.goLeft();
			copyWithPreOrderTraversal(it1, it2);
			it1.goUp();
			it2.goUp();

			it1.goRight();
			it2.goRight();
			copyWithPreOrderTraversal(it1, it2);
			it1.goUp();
			it2.goUp();
		}
	}

	/**
	 * this devient identique à image2.
	 *
	 * @param image2 image à copier
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void affect(AbstractImage image2) {
		if (this == image2) {
			return; // Rien à affecter
		}

		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();

		it1.clear(); // On vide l'arbre avant de faire l'affectation
		copyWithPreOrderTraversal(it1, it2);
	}

	/**
	 * this devient rotation de image2 à 180 degrés.
	 *
	 * @param image2 image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction a ecrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient inverse vidéo de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction a ecrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2 image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction a ecrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2 image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction a ecrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient quart supérieur gauche de image2.
	 *
	 * @param image2 image à agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction a ecrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * Le quart supérieur gauche de this devient image2, le reste de this devient
	 * éteint.
	 * 
	 * @param image2 image à réduire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction a ecrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void intersection(AbstractImage image1, AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction a ecrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void union(AbstractImage image1, AbstractImage image2) {
		if (this == image1 && this == image2) {
			return; // Rien à unir
		}

		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();

		it.clear(); // On vide l'arbre avant de faire l'union
		unionAux(it, it1, it2);
	}

	private void unionAux(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		if (!it1.isEmpty() || !it2.isEmpty()) {
			// On traite d'abord la racine
			if (!it1.isEmpty() && it1.getValue().state == 1) {
				System.out.println("image1 est totalement allumé");
				it.addValue(it1.getValue());
			} else if (!it2.isEmpty() && it2.getValue().state == 1) {
				System.out.println("image2 est totalement allumé");
				it.addValue(it2.getValue());
			} else if (!it1.isEmpty() && !it2.isEmpty()) {
				Node n1 = it1.getValue();
				Node n2 = it2.getValue();
				if (n1.state == 2 && n2.state == 0) {
					System.out.println("image1 est partiellement allumé");
					copyWithPreOrderTraversal(it, it1);
				} else if (n1.state == 0 && n2.state == 2) {
					System.out.println("image2 est partiellement allumé");
					copyWithPreOrderTraversal(it, it2);
				} else {
					it.addValue(Node.valueOf(0));
					System.out.println("image1 et image 2 sont éteints");
				}
			}
			System.out.println(toString());
			// Ensuite on continue le parcours
			it.goLeft();
			it1.goLeft();
			it2.goLeft();
			unionAux(it, it1, it2);
			it.goUp();
			it1.goUp();
			it2.goUp();

			it.goRight();
			it1.goRight();
			it2.goRight();
			unionAux(it, it1, it2);
			it.goUp();
			it1.goUp();
			it2.goUp();
		}
	}

	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumés dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction a ecrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
	}

	/**
	 * @param x abscisse du point
	 * @param y ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) {
		int debutX = 0, finX = 255;
		int middleX = (debutX + finX) / 2;
		int debutY = 0, finY = 255;
		int middleY = (debutY + finY) / 2;

		Iterator<Node> it = this.iterator();

		while (it.getValue().state == 2) {
			// Découpage sur x
			if (x <= middleX) {
				it.goLeft();
				finX = middleX;
				middleX = (debutX + finX) / 2;
			} else {
				it.goRight();
				debutX = middleX;
				middleX = (debutX + finX) / 2;
			}
			// Découpage sur y
			if (it.getValue().state == 2) {
				if (y <= middleY) {
					it.goLeft();
					finY = middleY;
					middleY = (debutY + finY) / 2;
				} else {
					it.goRight();
					debutY = middleY;
					middleY = (debutY + finY) / 2;
				}
			} else {
				return it.getValue().state == 1;
			}
		}
		return it.getValue().state == 1;
	}

	/**
	 * @param x1 abscisse du premier point
	 * @param y1 ordonnée du premier point
	 * @param x2 abscisse du deuxième point
	 * @param y2 ordonnée du deuxième point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont représentés par la
	 *         même feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction a ecrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
	}

	/**
	 * @param image2 autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumés false
	 *         sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction a ecrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
	}
}