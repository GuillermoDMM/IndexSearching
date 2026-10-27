# EPED — Práctica 2025-2026

**Estrategias de Programación y Estructuras de Datos (EPED)**  
Grado en Ingeniería Informática — UNED

## Descripción

Este repositorio contiene la implementación de la práctica de la asignatura *Estrategias de Programación y Estructuras de Datos* correspondiente al curso 2025-2026.

El objetivo del proyecto es desarrollar un **índice invertido para la búsqueda de documentos de texto**, utilizando dos implementaciones alternativas basadas en estructuras de datos diferentes. El índice permite asociar cada palabra con los documentos en los que aparece y almacenar su frecuencia de aparición en cada uno de ellos.

## Objetivos

- Implementar un tipo de dato abstracto (`IndexIF`) mediante dos estructuras de datos diferentes.
- Trabajar con secuencias, iteradores y árboles generales.
- Desarrollar operaciones de inserción, consulta y búsqueda por prefijos.
- Analizar la complejidad temporal de los algoritmos.
- Comparar experimentalmente el rendimiento de ambas implementaciones.

## Implementaciones

### 1. Índice basado en secuencias

La clase `IndexSequence` almacena los pares palabra-secuencia en una secuencia ordenada alfabéticamente.

Cada palabra se asocia con una secuencia de pares que contiene los identificadores de los documentos y sus respectivas frecuencias.

### 2. Índice basado en árboles generales (Trie)

La clase `IndexTree` utiliza un árbol general para representar las palabras mediante caminos formados por caracteres. Cada nodo hoja almacena la información asociada a una palabra.

Esta estructura permite localizar palabras recorriendo el árbol carácter a carácter y consultar las palabras que comparten un prefijo.

## Operaciones principales

Las dos implementaciones ofrecen las operaciones definidas en `IndexIF`:

| Operación | Descripción |
|---|---|
| `insertIndex(p, doc_id, freq)` | Inserta una asociación entre una palabra, un documento y su frecuencia. |
| `retrieveIndex(p)` | Recupera los documentos asociados a una palabra y sus frecuencias. |
| `prefixIterator(prefix)` | Obtiene las palabras que comienzan por un prefijo, en orden alfabético. |

Además, la clase `ProcessDoc` contabiliza las apariciones de cada palabra dentro de un documento mediante `addWord(String w)`.

## Tecnologías y conceptos

- **Lenguaje:** Java
- **Estructuras de datos:** secuencias, listas enlazadas, iteradores y árboles generales.
- **Algoritmos:** búsqueda, inserción, recorrido de árboles y análisis de complejidad.
- **Complejidad algorítmica:** análisis del caso peor y comparación de costes temporales.

Las implementaciones utilizan las interfaces y estructuras de datos proporcionadas por el equipo docente de la asignatura.

## Estructura del proyecto

Las clases principales del proyecto son:

| Clase | Responsabilidad |
|---|---|
| `IndexIF` | Define la interfaz del índice. |
| `Seq_PSF` | Gestiona secuencias de pares cadena-frecuencia. |
| `Pair_S_F` | Representa una cadena y su frecuencia. |
| `Pair_W_SeqPSF` | Asocia una palabra con su secuencia de documentos. |
| `ProcessDoc` | Procesa las palabras de un documento. |
| `IndexSequence` | Implementa el índice mediante secuencias. |
| `IndexTree` | Implementa el índice mediante un Trie. |
| `Main` | Ejecuta las operaciones indicadas en el fichero de entrada. |

## Aprendizajes

Este proyecto permite profundizar en el diseño e implementación de tipos de datos abstractos, la elección de estructuras de datos según las operaciones requeridas y el análisis de la eficiencia de los algoritmos.

También permite comparar cómo una misma funcionalidad puede implementarse mediante estructuras diferentes y cómo esa elección afecta al coste de las operaciones.

---

**Asignatura:** Estrategias de Programación y Estructuras de Datos  
**Universidad:** Universidad Nacional de Educación a Distancia (UNED)  
**Curso académico:** 2025-2026
