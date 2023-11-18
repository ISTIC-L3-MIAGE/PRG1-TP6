package fr.istic.prg1.tree;

public abstract class BinaryTree {
	enum NodeType {SENTINEL, LEAF, SIMPLE_LEFT, SIMPLE_RIGHT, DOUBLE};
	static final int NODE_EMPTY_VALUE = -1;
	// Classe interne représentant un noeud de l'arbre
	private class Node {
		
		int value;
		Node father;
		Node left;
		Node right;
		
		Node() {
			this.value = NODE_EMPTY_VALUE;
			this.father = null;
			this.left = null;
			this.right = null;
		}
		
		Node(int value, Node father, Node left, Node right) {
			this.value = value;
			this.father = father;
			this.left = left;
			this.right = right;
		}
	}
	
	// Classe interne représentant l'itérateur de l'arbre
	public class Iterator {
		
		Node current;
		
		Iterator() {
			this.current = root;
		}
		
		public boolean isEmpty() {
			return current.left == null && current.right == null;
		}
		
		public void goRoot() {
			current = root;
		}
		
		public void goUp() {
			assert current == root: "La racine n'a pas de père";
			current = current.father;
		}
		
		public void goLeft() {
			assert !this.isEmpty(): "Le butoir n'a pas de fils gauche";
			current = current.left;
		}
		
		public void goRight() {
			assert !this.isEmpty(): "Le butoir n'a pas de fils droit";
			current = current.right;
		}
		
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
		
		public void clear() {
			current.left = null;
			current.right = null;
		}
		
		public void remove() {
			assert this.nodeType() != NodeType.DOUBLE: "Impossible de supprimer un noeud double";
			switch (this.nodeType()) {
				case LEAF:
					this.clear();
					break;
				case SIMPLE_LEFT:
					current = current.left;
					break;
				case SIMPLE_RIGHT:
					current = current.right;
					break;
				default: break;
			}
			this.goUp();
		}
		
		public int getValue() {
			return current.value;
		}
		
		public void addValue(int value) {
			assert this.isEmpty(): "Impossible d'ajouter car le noeud courrant n'est pas un butoir";
			current.value = value;
			current.left = new Node();
			current.right = new Node();
		}
		
		public void setValue(int value) {
			current.value = value;
		}
		
		public void switchValue(int i) {
			int temp = current.value;
			current.value = root.value;
			while (i != 0) {
				this.goUp();
			}
			root.value = temp;
		}
		
	}
	
	// Racine de l'arbre
	private Node root;
	
	public BinaryTree() {
		root = new Node();
	}
	
	public boolean isEmpty() {
		return root.left == null && root.right == null;
	}
	
	public Iterator iterator() {
		return new Iterator();
	}
	
}
