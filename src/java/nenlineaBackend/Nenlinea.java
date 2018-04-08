/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nenlineaBackend;

/**
 *
 * @author ubau-
 */
public class Nenlinea {
    
    public String descrip;
    public String id;
    public String jugador1;
    public String jugador2;
    public String tam;
    public Ficha[][] matriz;
    public int jugadaX;
    public int jugadaY;
    public int gana;
    public String[] chat;

    public Nenlinea() {
    }
        
    
    public Nenlinea(String descrip, String id, String jugador1, String jugador2, String tam, Ficha[][] matriz, int jugadaX, int jugadaY, int gana, String[] chat) {
        this.descrip = descrip;
        this.id = id;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.tam = tam;
        this.matriz = matriz;
        this.jugadaX = jugadaX;
        this.jugadaY = jugadaY;
        this.gana = gana;
        this.chat = chat;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJugador1(String jugador1) {
        this.jugador1 = jugador1;
    }

    public void setJugador2(String jugador2) {
        this.jugador2 = jugador2;
    }

    public void setTam(String tam) {
        this.tam = tam;
    }

    public void setMatriz(Ficha[][] matriz) {
        this.matriz = matriz;
    }

    public void setJugadaX(int jugadaX) {
        this.jugadaX = jugadaX;
    }

    public void setJugadaY(int jugadaY) {
        this.jugadaY = jugadaY;
    }

    public void setGana(int gana) {
        this.gana = gana;
    }

    public void setChat(String[] chat) {
        this.chat = chat;
    }

    public String getDescrip() {
        return descrip;
    }

    public String getId() {
        return id;
    }

    public String getJugador1() {
        return jugador1;
    }

    public String getJugador2() {
        return jugador2;
    }

    public String getTam() {
        return tam;
    }

    public Ficha[][] getMatriz() {
        return matriz;
    }

    public int getJugadaX() {
        return jugadaX;
    }

    public int getJugadaY() {
        return jugadaY;
    }

    public int getGana() {
        return gana;
    }

    public String[] getChat() {
        return chat;
    }
    
    

    
        
        
        
    
    
    
    
}
