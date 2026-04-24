/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author josue
 */
public class DibujoArbol extends JPanel {
    private ArbolABB arbol;
    private static final int RADIO = 20;
    private static final int ESPACIADO_V = 50;

    public DibujoArbol(ArbolABB arbol) {
        this.arbol = arbol;
        setBackground(Color.WHITE); // Fondo blanco para que se vea el dibujo
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (arbol.getRaiz() != null) {
            dibujarNodo(g, arbol.getRaiz(), getWidth() / 2, 30, getWidth() / 4);
        }
    }

    private void dibujarNodo(Graphics g, Nodo nodo, int x, int y, int ancho) {
        if (nodo == null) return;

        // 1. Dibujar las líneas hacia los hijos primero (para que queden detrás del círculo)
        g.setColor(Color.BLACK);
        if (nodo.getIzquierdo() != null) {
            g.drawLine(x, y, x - ancho, y + ESPACIADO_V);
            dibujarNodo(g, nodo.getIzquierdo(), x - ancho, y + ESPACIADO_V, ancho / 2);
        }
        if (nodo.getDerecho() != null) {
            g.drawLine(x, y, x + ancho, y + ESPACIADO_V);
            dibujarNodo(g, nodo.getDerecho(), x + ancho, y + ESPACIADO_V, ancho / 2);
        }

        // 2. Dibujar el círculo del nodo
        g.setColor(new Color(100, 149, 237)); // Azul suave
        g.fillOval(x - RADIO, y - RADIO, RADIO * 2, RADIO * 2);
        g.setColor(Color.BLACK);
        g.drawOval(x - RADIO, y - RADIO, RADIO * 2, RADIO * 2);

        // 3. Dibujar el ID del nodo
        String texto = String.valueOf(nodo.getDato().getId());
        g.drawString(texto, x - 6, y + 4);
    }
}
