/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Entidades.Tarjeta;

/**
 *
 * @author josue
 */
public class ArbolABB {
    private Nodo raiz;

    public ArbolABB() {
        this.raiz = null;
    }

    // --- INSERCIÓN ---
    public void insertar(Tarjeta nueva) throws Exception {
        if (nueva.getId() <= 0) throw new Exception("El ID debe ser un entero positivo."); 
        if (buscarNodo(raiz, nueva.getId()) != null) throw new Exception("El ID ya existe en el árbol."); 
        raiz = insertarRecursivo(raiz, nueva);
    }

    private Nodo insertarRecursivo(Nodo actual, Tarjeta tarjeta) {
        if (actual == null) return new Nodo(tarjeta); 
        if (tarjeta.getId() < actual.dato.getId()) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, tarjeta); 
        } else {
            actual.derecho = insertarRecursivo(actual.derecho, tarjeta); 
        }
        return actual;
    }

    // --- BÚSQUEDA ---
    public Tarjeta buscar(int id) {
        Nodo resultado = buscarNodo(raiz, id);
        return (resultado != null) ? resultado.dato : null; 
    }

    private Nodo buscarNodo(Nodo actual, int id) {
        if (actual == null || actual.dato.getId() == id) return actual;
        if (id < actual.dato.getId()) return buscarNodo(actual.izquierdo, id);
        return buscarNodo(actual.derecho, id);
    }

    // --- ELIMINACIÓN (Según Reglas Específicas) ---
    public void eliminar(int id) throws Exception {
        Nodo nodoAEliminar = buscarNodo(raiz, id);
        if (nodoAEliminar == null) throw new Exception("El ID no existe.");
        
        // Regla 5: Categoría Civiles no se elimina 
        if (nodoAEliminar.dato.getCategoria().equalsIgnoreCase("Civiles")) {
            throw new Exception("No se permite eliminar tarjetas de la categoría 'Civiles'.");
        }
        
        raiz = eliminarRecursivo(raiz, id);
    }

    private Nodo eliminarRecursivo(Nodo actual, int id) throws Exception {
        if (actual == null) return null;

        if (id < actual.dato.getId()) {
            actual.izquierdo = eliminarRecursivo(actual.izquierdo, id);
        } else if (id > actual.dato.getId()) {
            actual.derecho = eliminarRecursivo(actual.derecho, id);
        } else {
            // Regla 1: Es hoja 
            if (actual.izquierdo == null && actual.derecho == null) return null;

            // Regla 2: Solo subárbol derecho (Bloqueado) 
            if (actual.izquierdo == null && actual.derecho != null) {
                throw new Exception("No se permite eliminar un nodo que solo tiene subárbol derecho.");
            }

            // Regla 3: Solo subárbol izquierdo 
            if (actual.derecho == null) return actual.izquierdo;

            // Regla 4: Dos subárboles (Bloqueado) 
            throw new Exception("No se permite eliminar un nodo con dos hijos.");
        }
        return actual;
    }

    // --- RECORRIDOS (Retornan String para la GUI) ---
    public String preOrden() { return recorrerPre(raiz).replaceAll("-$", ""); } 
    private String recorrerPre(Nodo n) {
        if (n == null) return "";
        return n.dato.getId() + "-" + recorrerPre(n.izquierdo) + recorrerPre(n.derecho);
    }

    public String inOrden() { return recorrerIn(raiz).replaceAll("-$", ""); } 
    private String recorrerIn(Nodo n) {
        if (n == null) return "";
        return recorrerIn(n.izquierdo) + n.dato.getId() + "-" + recorrerIn(n.derecho);
    }

    public String postOrden() { return recorrerPost(raiz).replaceAll("-$", ""); } 
    private String recorrerPost(Nodo n) {
        if (n == null) return "";
        return recorrerPost(n.izquierdo) + recorrerPost(n.derecho) + n.dato.getId() + "-";
    }

    // --- CONSULTAS ADICIONALES ---
    // Total Súper héroes o Súper villanos 
    public int contarHeroesVillanos() { return contarHV(raiz); }
    private int contarHV(Nodo n) {
        if (n == null) return 0;
        int count = (n.dato.getCategoria().equals("Súper héroes") || 
                     n.dato.getCategoria().equals("Súper villanos")) ? 1 : 0;
        return count + contarHV(n.izquierdo) + contarHV(n.derecho);
    }

    // Frases icónicas que son hojas 
   public String listarFrasesIconicasHojas() {
        // Es vital empezar con una cadena vacía y acumular
        return recorrerFrases(this.raiz);
    }

    private String recorrerFrases(Nodo n) {
        // Caso base: si el nodo es nulo, no devuelve nada
        if (n == null) {
            return "";
        }

        // 1. Ir a la izquierda y recoger lo que haya
        String frasesIzquierda = recorrerFrases(n.izquierdo);

        // 2. Revisar el nodo actual
        String fraseActual = "";

        // Verificamos que sea HOJA (sin hijos)
        if (n.izquierdo == null && n.derecho == null) {
            // Usamos equalsIgnoreCase y trim para ser más flexibles con el texto
            String categoria = n.dato.getCategoria().trim();
            if (categoria.equalsIgnoreCase("Frases icónicas") || categoria.equalsIgnoreCase("Frases iconicas")) {
                fraseActual = "- " + n.dato.getDescripcion() + "\n";
            }
        }

        // 3. Ir a la derecha y recoger lo que haya
        String frasesDerecha = recorrerFrases(n.derecho);

        // UNIR TODO: Izquierda + Actual + Derecha
        // El orden de esta suma asegura que se mantenga una estructura de lista
        return frasesIzquierda + fraseActual + frasesDerecha;
    }

    // Mayor y Menor ID 
    public int obtenerMenor() throws Exception {
        if (raiz == null) throw new Exception("Árbol vacío.");
        Nodo temp = raiz;
        while (temp.izquierdo != null) temp = temp.izquierdo;
        return temp.dato.getId();
    }

    public int obtenerMayor() throws Exception {
        if (raiz == null) throw new Exception("Árbol vacío.");
        Nodo temp = raiz;
        while (temp.derecho != null) temp = temp.derecho;
        return temp.dato.getId();
    }
    
    // Getter para la raíz (útil para graficar)
    public Nodo getRaiz() { return raiz; }
}
