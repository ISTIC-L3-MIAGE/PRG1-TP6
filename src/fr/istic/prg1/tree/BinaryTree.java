package fr.istic.prg1.tree;

import java.util.ArrayDeque;
import java.util.Deque;

import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Antonella Atterey <antonella.atterey@etudiant.univ-rennes1.fr>
 * @author Ezan Tahi <ezan.tahi@etudiant.univ-rennes1.fr>
 * @class L3 MIAGE 2023/2024
 * @param <T> type formel d'objet pour la classe
 *
 *            Les arbres binaires sont construits par chaînage par références
 *            pour les fils et une pile de pères.
 */
public class BinaryTree<T> {

	/**
	 * Type représentant les noeuds.
	 */
	private class Element {
		public T value;
		public Element left, right;

		public Element() {
			value = null;
			left = null;
			right = null;
		}

		public boolean isEmpty() {
			return left == null && right == null;
		}
	}

	private Element root;

	public BinaryTree() {
		root = new Element();
	}

	/**
	 * @return Un nouvel iterateur sur l'arbre this. Le noeud courant de l'itérateur
	 *         est positionné sur la racine de l'arbre.
	 */
	public TreeIterator iterator() {
		return new TreeIterator();
	}

	/**
	 * @return true si l'arbre this est vide, false sinon
	 */
	public boolean isEmpty() {
		return root.isEmpty();
	}

	/**
	 * Classe représentant les itérateurs sur les arbres binaires.
	 */
	public class TreeIterator implements Iterator<T> {
		private Element currentNode;
		private Deque<Element> stack;

		private TreeIterator() {
			stack = new ArrayDeque<>();
			currentNode = root;
		}

		/**
		 * L'itérateur se positionnne sur le fils gauche du noeud courant.
		 * 
		 * @pre Le noeud courant n'est pas un butoir.
		 */
		@Override
		public void goLeft() {
			try {
				assert !this.isEmpty() : "le butoir n'a pas de fils";
				stack.push(currentNode);
				currentNode = currentNode.left;
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * L'itÃ©rateur se positionnne sur le fils droit du noeud courant.
		 * 
		 * @pre Le noeud courant nâ€™est pas un butoir.
		 */
		@Override
		public void goRight() {
			try {
				assert !this.isEmpty() : "le butoir n'a pas de fils";
				stack.push(currentNode);
				currentNode = currentNode.right;
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * L'itÃ©rateur se positionnne sur le pÃ¨re du noeud courant.
		 * 
		 * @pre Le noeud courant nâ€™est pas la racine.
		 */
		@Override
		public void goUp() {
			try {
				assert !stack.isEmpty() : " la racine n'a pas de pere";
				currentNode = stack.pop();
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * L'itÃ©rateur se positionne sur la racine de l'arbre.
		 */
		@Override
		public void goRoot() {
			currentNode = root;
			stack.clear();
		}

		/**
		 * @return true si l'iterateur est sur un sous-arbre vide, false sinon
		 */
		@Override
		public boolean isEmpty() {
			return currentNode.isEmpty();
		}

		/**
		 * @return Le genre du noeud courant.
		 */
		@Override
		public NodeType nodeType() {
			if (this.isEmpty()) {
				return NodeType.SENTINEL;
			}

			this.goLeft();
			boolean isLeftEmpty = this.isEmpty();
			this.goUp();
			this.goRight();
			boolean isRightEmpty = this.isEmpty();
			this.goUp();

			if (isLeftEmpty && !isRightEmpty) {
				return NodeType.SIMPLE_RIGHT;
			} else if (!isLeftEmpty && isRightEmpty) {
				return NodeType.SIMPLE_LEFT;
			} else if (isLeftEmpty && isRightEmpty) {
				return NodeType.LEAF;
			} else {
				return NodeType.DOUBLE;
			}
		}

		/**
		 * Supprimer le noeud courant de l'arbre.
		 * 
		 * @pre Le noeud courant n'est pas un noeud double.
		 */
		@Override
		public void remove() {
			try {
				assert nodeType() != NodeType.DOUBLE : "retirer : retrait d'un noeud double non permis";
				Element newCurrentNode = null;

				switch (this.nodeType()) {
				case SENTINEL:
					return;
				case LEAF:
					newCurrentNode = new Element();
					break;
				case SIMPLE_LEFT:
					newCurrentNode = currentNode.left;
					break;
				case SIMPLE_RIGHT:
					newCurrentNode = currentNode.right;
					break;
				default:
					break;
				}

				currentNode.value = newCurrentNode.value;
				currentNode.left = newCurrentNode.left;
				currentNode.right = newCurrentNode.right;

			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * Vider le sousâ€“arbre rÃ©fÃ©rencÃ© par le noeud courant, qui devient butoir.
		 */
		@Override
		public void clear() {
			currentNode.value = null;
			currentNode.left = null;
			currentNode.right = null;
		}

		/**
		 * @return La valeur du noeud courant.
		 */
		@Override
		public T getValue() {
			return currentNode.value;
		}

		/**
		 * CrÃ©er un nouveau noeud de valeur v Ã cet endroit.
		 * 
		 * @pre Le noeud courant est un butoir.
		 * 
		 * @param v Valeur Ã ajouter.
		 */

		@Override
		public void addValue(T v) {
			try {
				assert isEmpty() : "Ajouter : on n'est pas sur un butoir";
				currentNode.value = v;
				currentNode.left = new Element();
				currentNode.right = new Element();
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * Affecter la valeur v au noeud courant.
		 * 
		 * @param v La nouvelle valeur du noeud courant.
		 */
		@Override
		public void setValue(T v) {
			currentNode.value = v;
		}

		private void ancestor(int i, int j) {
			try {
				assert !stack.isEmpty() : "switchValue : argument trop grand";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			Element x = stack.pop();
			if (j < i) {
				ancestor(i, j + 1);
			} else {
				T v = x.value;
				x.value = currentNode.value;
				currentNode.value = v;
			}
			stack.push(x);
		}

		/**
		 * Ã‰changer les valeurs associÃ©es au noeud courant et Ã son pÃ¨re dâ€™ordre i
		 * (le noeud courant reste inchangÃ©).
		 * 
		 * @pre i>= 0 et racine est pÃ¨re du noeud courant dâ€™ordre >= i.
		 * 
		 * @param i ordre du pÃ¨re
		 */
		@Override
		public void switchValue(int i) {
			if (i < 0) {
				throw new IllegalArgumentException("switchValue : argument negatif");
			}
			if (i > 0) {
				ancestor(i, 1);
			}
		}
	}
}