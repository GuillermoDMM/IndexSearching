package es.uned.lsi.eped.pract2025_2026;

import es.uned.lsi.eped.DataStructures.IteratorIF;

/* Un objeto de esta clase permite contabilizar la frecuencia
 * de las palabras de un documento.
 */
public class ProcessDoc {
    String doc_id;
    Seq_PSF words;

    /* Constructor */
    public ProcessDoc(String did) {
        this.doc_id = did;
        this.words = new Seq_PSF();
    }

    /* Devuelve la secuencia de pares <palabra,frecuencia> */
    public Seq_PSF getSequence() {
        return this.words;
    }

    /* Incrementa en 1 la frecuencia de la palabra w en el documento */    
    public void addWord(String w) {
    	boolean found = false; // Variable auxiliar para saber si se ha encontrado la palabra
    	IteratorIF<Pair_S_F> it = this.words.iterator(); // Iterador sobre la secuencia <palabra, frecuencia>
    	
    	// Se recorre la secuencia completa
    	while(it.hasNext() && !found) {
    		Pair_S_F pair = it.getNext();
    		
    		// Se comprueba si la palabra del par coincide con w
    		if(pair.getString().equals(w)) {
    			pair.incFrequency(); // Si existe, se incrementa en 1
    			found = true; // Como se ha encontrado la variable booleana es true
    		}
    	}
    	
    	// Si no se encuentra la palabra se añade un par <w, 1> a la secuencia
    	if(!found) this.words.add(new Pair_S_F(w, 1));
    }
}