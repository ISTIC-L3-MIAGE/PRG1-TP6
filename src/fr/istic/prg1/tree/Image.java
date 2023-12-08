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

//import fr.istic.prg1.tree_util.AbstractImage;
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
		// On traite d'abord la racine
		Node n2 = it2.getValue();
		it1.addValue(n2);
		// Seuls les noeuds de state = 2 ont des fils
		if (n2.state == 2) {
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
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();

		it1.clear(); // On vide l'arbre avant de faire la rotation
		rotate180Aux(it1, it2);
	}

	/**
	 * Fonction auxiliaire utile pour réaliser rotate180
	 *
	 * @param it1 itérateur de this
	 * @param it2 itérateur de l’image dont il faut faire la rotation
	 */
	private void rotate180Aux(Iterator<Node> it1, Iterator<Node> it2) {
		Node n2 = it2.getValue();
		it1.addValue(n2);

		if (n2.state == 2) {
			it2.goLeft();
			it1.goRight();
			rotate180Aux(it1, it2);
			it2.goUp();
			it1.goUp();

			it2.goRight();
			it1.goLeft();
			rotate180Aux(it1, it2);
			it2.goUp();
			it1.goUp();
		}
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

	/**
	 * Fonction auxiliaire utile pour réaliser videoInverse
	 *
	 * @param it itérateur de this
	 */
	private void videoInverseAux(Iterator<Node> it) {
		Node n = it.getValue();

		if (n.state != 2) {
			// Chaque noeud != 2 reçoit l'inverse de son état actuel
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
		mirrorAux(it1, it2, false);
	}

	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2 image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();

		it1.clear(); // On vide l'arbre avant de faire le mirroir
		mirrorAux(it1, it2, true);
	}

	/**
	 * Fonction auxiliaire utile pour réaliser mirrorV et mirrorH
	 *
	 * @param it1    itérateur de this
	 * @param it2    itérateur de l’image dont il faut faire le mirroir
	 * @param follow doit prendre false au départ pour réaliser mirrorV et true au
	 *               départ pour réaliser mirrorH
	 */
	private void mirrorAux(Iterator<Node> it1, Iterator<Node> it2, boolean follow) {
		Node n2 = it2.getValue();
		it1.addValue(n2);

		if (n2.state == 2) {
			it2.goLeft();
			if (follow) {
				it1.goLeft();
			} else {
				it1.goRight();
			}
			mirrorAux(it1, it2, !follow);
			it2.goUp();
			it1.goUp();

			it2.goRight();
			if (follow) {
				it1.goRight();
			} else {
				it1.goLeft();
			}
			mirrorAux(it1, it2, !follow);
			it2.goUp();
			it1.goUp();
		}
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
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		int compteur = 0;

		it1.clear();
		// On descends deux fois à gauche dans la mesure du possible pour tomber dans le
		// sous arbre représentant le quart supérieur gauche de image2
		while (it2.getValue().state == 2 && compteur < 2) {
			it2.goLeft();
			compteur++;
		}
		// Ensuite on copie ce sous arbre dans this
		copyWithPreOrderTraversal(it1, it2);
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

		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		int counter = 0;
		int level = 0;

		it1.clear();
		if (it2.getValue().state == 0) {
			it1.addValue(Node.valueOf(0));
		} else {
			while (counter < 2) {
				it1.addValue(Node.valueOf(2));
				it1.goRight();
				it1.addValue(Node.valueOf(0));
				it1.goUp();
				it1.goLeft();
				counter++;
			}
		}

		zoomOutAux(it1, it2, level);

		if (it1.getValue().state == 0) {
			it1.goRoot();
			it1.clear();
			it1.addValue(Node.valueOf(0));
		}
	}

	/**
	 * Fonction auxiliaire utile pour réaliser zoomOut
	 *
	 * @param it1   itérateur de this
	 * @param it2   itérateur de l’image 2
	 * @param level le niveau courant dans l'arbre de l’image 2
	 */
	private void zoomOutAux(Iterator<Node> it1, Iterator<Node> it2, int level) {
		Node n2 = it2.getValue();
		it1.addValue(n2);
		level++;

		if (n2.state == 2) {
			if (level < 15) {
				it1.goLeft();
				it2.goLeft();

				zoomOutAux(it1, it2, level);
				int leftState = it1.getValue().state;

				it1.goUp();
				it2.goUp();

				it1.goRight();
				it2.goRight();

				zoomOutAux(it1, it2, level);
				int rightState = it1.getValue().state;

				it1.goUp();
				it2.goUp();

				// On gère les cas (2,1,1) et (2,0,0)
				if (leftState == rightState && leftState != 2) {
					it1.clear();
					it1.addValue(Node.valueOf(leftState));
				}
			} else { // Correction des feuilles de l'arbre
				it2.goLeft();
				int leftState = it2.getValue().state;
				it2.goUp();

				it2.goRight();
				int rightState = it2.getValue().state;
				it2.goUp();

				it1.clear();
				if (leftState == 1 || rightState == 1) {
					it1.addValue(Node.valueOf(1));
				} else if (leftState == 2 && rightState == 2) {
					it1.addValue(Node.valueOf(1));
				} else {
					it1.addValue(Node.valueOf(0));
				}
			}
		}
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
			return; // Rien à faire
		}

		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();

		it.clear(); // On vide l'arbre avant de faire l'intersection
		intersectionAux(it, it1, it2);
	}

	/**
	 * Fonction auxiliaire utile pour réaliser intersection
	 *
	 * @param it  itérateur de this
	 * @param it1 itérateur de l’image 1
	 * @param it2 itérateur de l’image 2
	 */
	private void intersectionAux(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		// On fait d'abord l'intersection des racines
		Node n1 = it1.getValue();
		Node n2 = it2.getValue();

		if (n1.state == 0 || n2.state == 0) {
			it.addValue(Node.valueOf(0));
		} else if (n1.state == 2 && n2.state == 1) {
			copyWithPreOrderTraversal(it, it1);
		} else if (n1.state == 1 && n2.state == 2) {
			copyWithPreOrderTraversal(it, it2);
		} else if (n1.state == 1 && n2.state == 1) {
			it.addValue(Node.valueOf(1));
		}

		// Ensuite on continue le parcours
		if (n1.state == 2 && n2.state == 2) {
			it.addValue(Node.valueOf(2));

			it.goLeft();
			it1.goLeft();
			it2.goLeft();

			intersectionAux(it, it1, it2);
			int leftState = it.getValue().state;

			it.goUp();
			it1.goUp();
			it2.goUp();

			it.goRight();
			it1.goRight();
			it2.goRight();

			intersectionAux(it, it1, it2);
			int rightState = it.getValue().state;

			it.goUp();
			it1.goUp();
			it2.goUp();

			// On gère les cas (2,1,1) et (2,0,0)
			if (leftState == rightState && leftState != 2) {
				it.clear();
				it.addValue(Node.valueOf(leftState));
			}
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

	/**
	 * Fonction auxiliaire utile pour réaliser union
	 *
	 * @param it  itérateur de this
	 * @param it1 itérateur de l’image 1
	 * @param it2 itérateur de l’image 2
	 */
	private void unionAux(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		// On fait d'abord l'union des racines
		Node n1 = it1.getValue();
		Node n2 = it2.getValue();

		if (n1.state == 1 || n2.state == 1) {
			it.addValue(Node.valueOf(1));
		} else if (n1.state == 2 && n2.state == 0) {
			copyWithPreOrderTraversal(it, it1);
		} else if (n1.state == 0 && n2.state == 2) {
			copyWithPreOrderTraversal(it, it2);
		} else if (n1.state == 0 && n2.state == 0) {
			it.addValue(Node.valueOf(0));
		}

		// Ensuite on continue le parcours
		if (n1.state == 2 && n2.state == 2) {
			it.addValue(Node.valueOf(2));

			it.goLeft();
			it1.goLeft();
			it2.goLeft();

			unionAux(it, it1, it2);
			int leftState = it.getValue().state;

			it.goUp();
			it1.goUp();
			it2.goUp();

			it.goRight();
			it1.goRight();
			it2.goRight();

			unionAux(it, it1, it2);
			int rightState = it.getValue().state;

			it.goUp();
			it1.goUp();
			it2.goUp();

			// On gère les cas (2,1,1) et (2,0,0)
			if (leftState == rightState && leftState != 2) {
				it.clear();
				it.addValue(Node.valueOf(leftState));
			}
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
		Iterator<Node> it = this.iterator();
		return testDiagonalAux(it, true);
	}

	/**
	 * Fonction auxiliaire utile pour réaliser testDiagonal
	 *
	 * @param it         itérateur de this
	 * @param isDiagonal doit prendre true lorsque la diagonale est allumée, false
	 *                   sinon
	 * @return true si la portion de diagonale actuellement visitée est allumée,
	 *         false sinon
	 */
	private boolean testDiagonalAux(Iterator<Node> it, boolean isDiagonal) {
		Node n = it.getValue();
		boolean twoStepUnder = false;

		// Les pixels sur la diagonale sont placé à chaque deux fois à gauche et deux
		// fois à droite
		if (n.state != 2) {
			return n.state == 1 && isDiagonal;
		} else if (isDiagonal) {
			it.goLeft();
			if (it.getValue().state == 2) {
				it.goLeft();
				twoStepUnder = true;
			}

			isDiagonal = testDiagonalAux(it, isDiagonal);

			it.goUp();
			if (twoStepUnder) {
				it.goUp();
				twoStepUnder = false;
			}

			it.goRight();
			if (it.getValue().state == 2) {
				it.goRight();
				twoStepUnder = true;
			}

			isDiagonal = testDiagonalAux(it, isDiagonal);

			it.goUp();
			if (twoStepUnder) {
				it.goUp();
				twoStepUnder = false;
			}
		}

		return isDiagonal;
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
			// Découpage horizontal
			if (y <= middleY) {
				it.goLeft();
				finY = middleY;
			} else {
				it.goRight();
				debutY = middleY + 1;
			}
			middleY = (debutY + finY) / 2;

			// Découpage vertical
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
		if (x1 == x2 && y1 == y2) {
			return true;
		}

		int debutX = 0, finX = 255;
		int middleX = (debutX + finX) / 2;
		int debutY = 0, finY = 255;
		int middleY = (debutY + finY) / 2;

		Iterator<Node> it = this.iterator();
		boolean same = true;

		while (it.getValue().state == 2 && same) {
			// Découpage horizontal
			if (y1 <= middleY && y2 <= middleY) {
				it.goLeft();
				finY = middleY;
			} else if (y1 > middleY && y2 > middleY) {
				it.goRight();
				debutY = middleY + 1;
			} else {
				same = false;
			}
			middleY = (debutY + finY) / 2;

			// Découpage vertical
			if (it.getValue().state == 2 && same) {
				if (x1 <= middleX && x2 <= middleX) {
					it.goLeft();
					finX = middleX;
				} else if (x1 > middleX && x2 > middleX) {
					it.goRight();
					debutX = middleX + 1;
				} else {
					same = false;
				}
				middleX = (debutX + finX) / 2;
			}
		}
		return same;
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

		return isIncludedInAux(it1, it2, true);
	}

	/**
	 * Fonction auxiliaire utile pour réaliser isIncludedIn
	 *
	 * @param it1       itérateur de this
	 * @param it2       itérateur de l’image 2
	 * @param inclusion doit prendre true si this est inclus dans image 2, false
	 *                  sinon
	 */
	private boolean isIncludedInAux(Iterator<Node> it1, Iterator<Node> it2, boolean inclusion) {
		Node n1 = it1.getValue();
		Node n2 = it2.getValue();
		// Cas d'arrêt 1: this est totallement allumé et image2 ne l'est pas
		// Cas d'arrêt 2: this est partiellement allumé et image2 est totallement éteint
		if ((n1.state == 1 && n2.state != 1) || (n1.state == 2 && n2.state == 0)) {
			return false;
		}

		if (n1.state == 2 && n2.state == 2 && inclusion) {
			it1.goLeft();
			it2.goLeft();

			inclusion = isIncludedInAux(it1, it2, inclusion);

			it1.goUp();
			it2.goUp();

			it1.goRight();
			it2.goRight();

			inclusion = isIncludedInAux(it1, it2, inclusion);

			it1.goUp();
			it2.goUp();
		}

		return inclusion;
	}
}