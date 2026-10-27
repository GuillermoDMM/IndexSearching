package es.uned.lsi.eped.pract2025_2026;

import es.uned.lsi.eped.DataStructures.ListIF;
import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.List;

public class Seq_PSF {
    private ListIF<Pair_S_F> seqStringFreq;
    
    /* Constructor */
    public Seq_PSF() {
        // Inicializamos la secuencia como una lista vacía
    	this.seqStringFreq = new List<>();
    }

    /* Devuelve un iterador de la secuencia */
    public IteratorIF<Pair_S_F> iterator() {
        return this.seqStringFreq.iterator();
    }
    
    /* Añade un nuevo par <cadena,frecuencia> a la secuencia */
    public void add(Pair_S_F pair) {
    	this.seqStringFreq.insert(this.seqStringFreq.size() + 1, pair); // Insertamos el elemento al final de la secuencia
    }
}