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
		it1.addValue(Node.valueOf(it2.getValue().state));
		// Seuls les noeud de state = 2 ont des fils
		if (it2.getValue().state == 2) {
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
		Iterator<Node> it = this.iterator();
		videoInverseAux(it);
	}

	private void videoInverseAux(Iterator<Node> it) {
		Node n = it.getValue();

		if (n.state != 2) {
			it.setValue(Node.valueOf(n.state == 1 ? 0 : 1));
		} else {
			it.goLeft();
			videoInverseAux(it);
			it.goUp();

			it.goRight();
			videoInverseAux(it);
			it.goUp();
		}
	}

	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2 image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();

		it1.clear(); // On vide l'arbre avant de faire le mirroir
		mirroVAux(it1, it2);
	}

	private void mirroVAux(Iterator<Node> it1, Iterator<Node> it2) {
		// On traite d'abord la racine
		it1.addValue(Node.valueOf(it2.getValue().state));
		// Ensuite on continue le parcours
		if (it2.getValue().state == 2) {
			it2.goLeft();
			it1.goRight();
			mirroVAux(it1, it2);
			it2.goUp();
			it1.goUp();

			it2.goRight();
			it1.goLeft();
			mirroVAux(it1, it2);
			it2.goUp();
			it1.goUp();
		}
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
		if (this == image1 && this == image2) {
			return;
		}

		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();

		it.clear(); // On vide l'arbre avant de faire l'intersection
		intersectionAux(it, it1, it2);
	}

	private void intersectionAux(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		if (!it1.isEmpty() && !it2.isEmpty()) {
			// On traite d'abord la racine
			Node n1 = it1.getValue();
			Node n2 = it2.getValue();
			if (n1.state == 2 && n2.state == 1) {
				copyWithPreOrderTraversal(it, it1);
			} else if (n1.state == 1 && n2.state == 2) {
				copyWithPreOrderTraversal(it, it2);
			} else if (n1.state == 1 && n2.state == 1) {
				it.addValue(Node.valueOf(1));
			} else {
				it.addValue(Node.valueOf(0));
			}
			// Ensuite on continue le parcours
			it.goLeft();
			it1.goLeft();
			it2.goLeft();
			intersectionAux(it, it1, it2);
			it.goUp();
			it1.goUp();
			it2.goUp();

			it.goRight();
			it1.goRight();
			it2.goRight();
			intersectionAux(it, it1, it2);
			it.goUp();
			it1.goUp();
			it2.goUp();
		}
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
			return; // Rien à faire
		}

		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();

		it.clear(); // On vide l'arbre avant de faire l'union
		unionAux(it, it1, it2);
	}

	private void unionAux(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		Node n1 = it1.getValue();
		Node n2 = it2.getValue();
		// On traite d'abord la racine
		if (n1.state == 1 || n2.state == 1) {
			it.addValue(Node.valueOf(1));
		} else if (n1.state == 2 && n2.state == 0) {
			copyWithPreOrderTraversal(it, it1);
		} else if (n1.state == 0 && n2.state == 2) {
			copyWithPreOrderTraversal(it, it2);
		} else {
			it.addValue(Node.valueOf(0));
		}

		if (it1.getValue().state == 2 && it2.getValue().state == 2) {
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
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255) sont
	 *         allumés dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		// Opérationnel
		Iterator<Node> it = this.iterator();
		int x = testDiagonalAux(0, 255, 0, it);
		return x == 256;
	}

	private int testDiagonalAux(int debut, int fin, int x, Iterator<Node> it) {
		// Opérationnel
		Node n = it.getValue();

		if (n.state == 1 && x <= 255) {
			// Si pixel de coords (x,x) est allumé, on test le suivant sur la diagonale
			// (x+1,x+1)
			System.out.println(x + " est allumé -> goRoot()"); // debug code
			it.goRoot();
			x = testDiagonalAux(0, 255, x + 1, it);

		} else if (n.state == 2) {
			// Si on a pas encore trouvé le noeud correspondant aux coords (x,x), on
			// continue le parcours
			int middle = (debut + fin) / 2;
			if (x <= middle) {
				it.goLeft();
				fin = middle;
			} else {
				it.goRight();
				debut = middle + 1;
			}

			n = it.getValue();
			if (n.state == 2) {
				if (x <= middle) {
					it.goLeft();
					fin = middle;
				} else {
					it.goRight();
					debut = middle + 1;
				}
			}
			x = testDiagonalAux(debut, fin, x, it);
		}

		return x;
	}

	/**
	 * @param x abscisse du point
	 * @param y ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) {
		// Opérationnel
		int debutX = 0, finX = 255;
		int middleX = (debutX + finX) / 2;
		int debutY = 0, finY = 255;
		int middleY = (debutY + finY) / 2;

		Iterator<Node> it = this.iterator();

		while (it.getValue().state == 2) {
			// Découpage sur y
			if (y <= middleY) {
				it.goLeft();
				finY = middleY;
			} else {
				it.goRight();
				debutY = middleY + 1;
			}
			middleY = (debutY + finY) / 2;

			// Découpage sur x
			if (it.getValue().state == 2) {
				if (x <= middleX) {
					it.goLeft();
					finX = middleX;
				} else {
					it.goRight();
					debutX = middleX + 1;
				}
				middleX = (debutX + finX) / 2;
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
		// Opérationnel
		if (x1 == x2 && y1 == y2) {
			return true;
		}

		int debutX = 0, finX = 255;
		int middleX = (debutX + finX) / 2;
		int debutY = 0, finY = 255;
		int middleY = (debutY + finY) / 2;

		Iterator<Node> it = this.iterator();

		while (it.getValue().state == 2) {
			// Découpage sur y
			if (y1 <= middleY && y2 <= middleY) {
				it.goLeft();
				finY = middleY;
			} else if (y1 > middleY && y2 > middleY) {
				it.goRight();
				debutY = middleY + 1;
			} else {
				return false;
			}
			middleY = (debutY + finY) / 2;

			// Découpage sur x
			if (it.getValue().state == 2) {
				if (x1 <= middleX && x2 <= middleX) {
					it.goLeft();
					finX = middleX;
				} else if (x1 > middleX && x2 > middleX) {
					it.goRight();
					debutX = middleX + 1;
				} else {
					return false;
				}
				middleX = (debutX + finX) / 2;
			}
		}
		return true;
	}

	/**
	 * @param image2 autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumés false
	 *         sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {
		if (this == image2) {
			return true;
		}

		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();

		return isIncludedInAux(it1, it2);
	}

	private boolean isIncludedInAux(Iterator<Node> it1, Iterator<Node> it2) {
		// Passe une partie des tests
		Node n1 = it1.getValue();
		Node n2 = it2.getValue();
		boolean inclusion = true;
		// Si tout est allumé dans this alors que image2 est partiellement éteint ou
		// totalement éteint, on renvoi false
		if (n1.state == 1 && n2.state != 1) {
			return false;
		}

		if (n1.state == 2 && n2.state == 2) {
			it1.goLeft();
			it2.goLeft();
			inclusion &= isIncludedInAux(it1, it2);
			it1.goUp();
			it2.goUp();

			it1.goRight();
			it2.goRight();
			inclusion &= isIncludedInAux(it1, it2);
			it1.goUp();
			it2.goUp();
		}

		return inclusion;
	}
}