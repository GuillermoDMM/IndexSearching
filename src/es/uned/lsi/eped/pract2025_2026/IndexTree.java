package es.uned.lsi.eped.pract2025_2026;

import es.uned.lsi.eped.DataStructures.GTreeIF;
import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.GTree;
import es.uned.lsi.eped.DataStructures.List;
import es.uned.lsi.eped.DataStructures.ListIF;

public class IndexTree implements IndexIF {

    protected GTreeIF<Node> index;
    
    public IndexTree() {
    	this.index = new GTree<Node>();
    	this.index.setRoot(new NodeRoot());
    }
    
    /* Método auxiliar */
    /* Para todos los métodos se va a necesitar buscar un hijo por carácter*/
    private GTreeIF<Node> findChild(GTreeIF<Node> node, char c) {
    	IteratorIF<GTreeIF<Node>> it = node.getChildren().iterator();
    	
    	while(it.hasNext()) {
    		GTreeIF<Node> child = it.getNext();
    		Node content = child.getRoot();
    		
    		if(content instanceof NodeInner) {
    			if(((NodeInner) content).getLetter() == c) {
    				return child;
    			}
    		}
    	}
    	
    	return null;
    }

	public Seq_PSF retrieveIndex(String p) {
		
		// Se empieza desde la raiz del arbol
		GTreeIF<Node> current = this.index;
				
		// Se recorre la palabra caracter a caracter
		for(int i = 0; i < p.length(); i++) {
			char c = p.charAt(i);
			
			// Se busca si existe el hijo correspondiente al caracter c
			GTreeIF<Node> child = findChild(current, c);
			
			// Si no existe se devuelve una secuencia vacia
			if(child == null) {
				return new Seq_PSF();
			}
			
			// Se avanza al siguiente nivel del arbol
			current = child;
		}
		
		// Como ya es el final de la plabara, se busca el nodo INFO
		IteratorIF<GTreeIF<Node>> it = current.getChildren().iterator();
		
		while(it.hasNext()) {
			GTreeIF<Node> child = it.getNext();
			
			// Si se encuentra el NodeInfo se devuelve su secuencia
			if(child.getRoot() instanceof NodeInfo) {
				return((NodeInfo) child.getRoot()).getSeqPSR();
			}
		}
		
		// Si no hay NodeInfo, la palabra no está indexada
		return new Seq_PSF();
	}

	public void insertIndex(String p, String doc_id, int freq) {
		// Se empieza desde la raiz del arbol
		GTreeIF<Node> current = this.index;
		
		// Se recorre la palabra caracter a caracter
		for(int i = 0; i < p.length(); i++) {
			char c = p.charAt(i);
			
			// Se busca si existe el hijo correspondiente al caracter c
			GTreeIF<Node> child = findChild(current, c);
			
			// Si no existe, se crea
			if(child == null) {
				child = new GTree<Node>();
				child.setRoot(new NodeInner(c));
				
				int pos = 1;
				IteratorIF<GTreeIF<Node>> it = current.getChildren().iterator();
				
				while(it.hasNext()) {
					GTreeIF<Node> aux = it.getNext();
					Node content = aux.getRoot();
					
					if(content instanceof NodeInfo) {
						break;
					}
					
					// Solo se compara con nodos internos (letras)
					if(content instanceof NodeInner) {
						char existing = ((NodeInner) content).getLetter();
						
						// Si se encuentra uno mayor se inserta antes
						if(c < existing) {
							break;
						}
					}
					
					pos++;
				}
				
				// Se inserta en la psoicion correcta
				current.addChild(pos,  child);
			}
			
			// Se avanza al siguiente nivel del arbol
			current = child;
		}
		
		// Como ya es el final de la plabara, se busca el nodo INFO
		GTreeIF<Node> infoNode = null;
		
		IteratorIF<GTreeIF<Node>> it = current.getChildren().iterator();
		
		while(it.hasNext()) {
			GTreeIF<Node> child = it.getNext();
			
			if(child.getRoot() instanceof NodeInfo) {
				infoNode = child;
			}
		}
		
		// Si no existe, se crea el nodo con el par
		if(infoNode == null) {
			infoNode = new GTree<Node>();
			NodeInfo info = new NodeInfo(new Pair_S_F(doc_id, freq));
			infoNode.setRoot(info);
			// Se añade el nodeInfo como hijo
			current.addChild(current.getChildren().size() + 1, infoNode);
		} else {
			NodeInfo info = (NodeInfo) infoNode.getRoot();
			info.add(new Pair_S_F(doc_id, freq));
		}
	}

	public IteratorIF<Pair_W_SeqPSF> prefixIterator(String prefix) {
		// Lista donde se acumularan los resultados
		ListIF<Pair_W_SeqPSF> result = new List<Pair_W_SeqPSF>();
		
		// SE baja hasta el nodo que representa el prefijo
		GTreeIF<Node> current = this.index;
		
		for(int i = 0; i < prefix.length(); i++) {
			char c = prefix.charAt(i);
			
			// Se busca si existe el hijo correspondiente al caracter c
			GTreeIF<Node> child = findChild(current, c);
			
			// Sino hay camino no hay resultado
			if(child == null) {
				return result.iterator();
			}
			
			// Se avanza al siguiente nivel del arbol
			current = child;
		}
		
		// Se recorre el subarbol de ese nodo
		// pathBuilder contendra la palabra construida, empezando por el prefijo
		StringBuilder pathBuilder = new StringBuilder(prefix);
		
		// Llamada recursiva para explorar el subarbol
		collect(current, pathBuilder, result);
		
		return result.iterator();
	}
    
	/* Metodo auxiliar recursivo */
	/* Recorre el subarbol y construye palabras completas */
	private void collect(GTreeIF<Node> node, StringBuilder path, ListIF<Pair_W_SeqPSF> result) {
		// Recorre todos los hijos del nodo actual
		IteratorIF<GTreeIF<Node>> it = node.getChildren().iterator();
		
		while(it.hasNext()) {
			GTreeIF<Node> child = it.getNext();
			
			if(child.getRoot() instanceof NodeInfo) {
				NodeInfo info = (NodeInfo) child.getRoot();
				
				// Se crea el par con la palabra actual
				Pair_W_SeqPSF pair = new Pair_W_SeqPSF(path.toString());
				
				// Se copia la secuencia
				IteratorIF<Pair_S_F> itSeq = info.getSeqPSR().iterator();
				
				while(itSeq.hasNext()) {
					Pair_S_F p = itSeq.getNext();
					pair.add(p.getString(), p.getFrequency());
				}
				
				// Se añade al resultado
				result.insert(result.size() + 1, pair);
			}
			
		}
		
		// Recoger solo los NodeInner
		ListIF<GTreeIF<Node>> children = new List<GTreeIF<Node>>();
		IteratorIF<GTreeIF<Node>> it2 = node.getChildren().iterator();
		
		while(it2.hasNext()) {
			GTreeIF<Node> child = it2.getNext();
			
			if(child.getRoot() instanceof NodeInner) {
				children.insert(children.size() + 1, child);
			}
		}
		
		// Ordenar hijos por caracter
		ListIF<GTreeIF<Node>> sorted = new List<GTreeIF<Node>>();
		IteratorIF<GTreeIF<Node>> it3 = children.iterator();
		
		while(it3.hasNext()) {
			GTreeIF<Node> child = it3.getNext();
			char c = ((NodeInner) child.getRoot()).getLetter();
			
			int pos = 1;
			IteratorIF<GTreeIF<Node>> itSorted = sorted.iterator();
			
			while(itSorted.hasNext()) {
				GTreeIF<Node> aux = itSorted.getNext();
				char existing = ((NodeInner) aux.getRoot()).getLetter();
				
				// Insercion ordenada
				if(c < existing) {
					break;
				}
				
				pos++;
			}
			
			sorted.insert(pos,  child);
		}
		
		// Recorrer en orden
		IteratorIF<GTreeIF<Node>> itFinal = sorted.iterator();
		
		while(itFinal.hasNext()) {
			GTreeIF<Node> child = itFinal.getNext();
			char c = ((NodeInner) child.getRoot()).getLetter();
			
			// Se añade el caracter al camino
			path.append(c);
			
			// Recursion
			collect(child, path, result);
			
			path.deleteCharAt(path.length() - 1);
		}
	}
}