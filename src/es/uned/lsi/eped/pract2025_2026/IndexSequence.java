package es.uned.lsi.eped.pract2025_2026;

import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.ListIF;
import es.uned.lsi.eped.DataStructures.List;

public class IndexSequence implements IndexIF {

    protected ListIF<Pair_W_SeqPSF> index;
    
    public IndexSequence() {
    	this.index = new List<Pair_W_SeqPSF>();
    }
    
    public Seq_PSF retrieveIndex(String p) {
    	IteratorIF<Pair_W_SeqPSF> it = this.index.iterator(); // Iterador sobre la secuencia del índice
    	
    	// Se recorre la secuencia buscando la palabra p
    	while(it.hasNext()) {
    		Pair_W_SeqPSF pair = it.getNext();
    		
    		// Se comprueba si la palabra coincide con p
    		// Si la encontramos, devolvemos su secuencia asociada
    		if(pair.getWord().equals(p)) return pair.getSeqPSR();
    	}
    	
    	// Si no se encuentra la palabra, devolvemos una secuencia vacía
        return new Seq_PSF();
    }
    
    public void insertIndex(String p, String doc_id, int freq) {
    	IteratorIF<Pair_W_SeqPSF> it = this.index.iterator(); // Iterador sobre la secuencia del índice
    	int pos = 1; // Posición donde insertar
    	boolean found = false; // Variable auxiliar para saber si ya se ha insertado o encontrado la palabra
    	
    	// Se recorre la secuencia
    	while(it.hasNext() && !found) {
    		Pair_W_SeqPSF pair = it.getNext();
    		
    		// esta variable indica el orden alfabético entre p y la palabra actual
    		// < 0: va antes | 0: igual | > 0: va después
    		int cmp = p.compareTo(pair.getWord());
    		
    		// Caso en que la palabra ya existe
    		if(cmp == 0) {
    			pair.add(doc_id, freq); // Se añade el nuevo par al final
    			found = true;
    		}
    		
    		//caso en el que la nueva palabra debe ir antes
    		else if(cmp < 0) {
    			Pair_W_SeqPSF newPair = new Pair_W_SeqPSF(p); // Se crea un nuevo par
    			newPair.add(doc_id, freq); // Se añade el nuevo par
    			this.index.insert(pos, newPair); // Se inserta en la posición correcta
    			found = true;
    		}
    		
    		// Caso en el que hay que seguir buscando
    		else pos++;
    	}
    	
    	// Caso en el que no se ha encontrado
    	if(!found) {
    		Pair_W_SeqPSF newPair = new Pair_W_SeqPSF(p); // Se crea un par con la palabra p
    		newPair.add(doc_id, freq); // Se añade al par el documento y frecuencia
    		
    		this.index.insert(this.index.size() + 1, newPair); // se inserta al final de la lista
    	}
    }

	public IteratorIF<Pair_W_SeqPSF> prefixIterator(String prefix) {
		ListIF<Pair_W_SeqPSF> result = new List<Pair_W_SeqPSF>(); // Lista donde almacenamos los resultados
		IteratorIF<Pair_W_SeqPSF> it = this.index.iterator(); // Iterador sobre el índice
		int pos = 1; // Posición donde insertar
		
		// Se recorre todo el índice
		while(it.hasNext()) {
			Pair_W_SeqPSF pair = it.getNext();
			
			// La palabra empieza por el prefijo
			if(pair.getWord().startsWith(prefix)) {
				result.insert(pos, pair); // Se añade a la lista resultado (al final)
				pos++;
			}
		}
		
		return result.iterator(); // Se devuelve el iterador de la lista resultado
	}
}
