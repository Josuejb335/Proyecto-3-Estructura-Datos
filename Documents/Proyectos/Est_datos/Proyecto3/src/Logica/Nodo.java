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
public class Nodo {
    Tarjeta dato;
    Nodo izquierdo, derecho;

    public Nodo(Tarjeta tarjeta) {
        this.dato = tarjeta;
        this.izquierdo = null;
        this.derecho = null;
    }
    
    // Getters para facilitar el graficado posterior
    public Tarjeta getDato() { return dato; }
    public Nodo getIzquierdo() { return izquierdo; }
    public Nodo getDerecho() { return derecho; }
}
