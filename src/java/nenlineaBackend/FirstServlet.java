/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nenlineaBackend;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ubau-
 */
public class FirstServlet extends HttpServlet {
    
    static ArrayList<Nenlinea> juegos = new ArrayList();
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        verificarJuego();
        System.out.println(" //////////////////////////////////////////////////////////////////");
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
            System.out.println("========================================");
            System.out.println(Arrays.toString(juegos.toArray()));
            System.out.println("========================================");

            try (BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                String line;
                line = input.readLine();
                System.out.println("? "+line);
                

                if(line == null){
                    System.out.println("request is null!!");
                    
                }
                
                else if(line.equals("listaJuegos")){
                    
                    System.out.println(" SOLICITUD DE LISTA DE JUEGOS!!");
                    out.println(Arrays.toString(postListaJuegos()));
                }
                //verificarJugador2EnJuego
                
               
                
                else{
                    
                    Gson gson = new Gson();
                    Nenlinea obj = gson.fromJson(line, Nenlinea.class);//pasar el string json a objeto de la clase Nenlinea 
                    
                    if(obj.descrip.equals("aceptar")){
                        
                        System.out.println("  ACEPTAR JUEGO");
                        
                        out.println(postBuscarJuego(obj));
                        String json = gson.toJson(postBuscarJuego(obj));
                        System.out.println("RESPONSE ACEPTAR: "+json);
                        
                    }
                    
                    else if(obj.descrip.equals("verificar")){
                        
                        System.out.println("  VERIFICAR JUEGO");
                        
                        out.println(verificarJugador2EnJuego(obj));
                        String json = gson.toJson(verificarJugador2EnJuego(obj));
                        System.out.println("RESPONSE VERIFICAR: "+json);
                    }
                    
                    else if(obj.descrip.equals("actualizar")){
                        
                        System.out.println("  ACTUALIZAR JUEGO");
                        
                        out.println(actualizarDash(obj));
                        String json = gson.toJson(actualizarDash(obj));
                        System.out.println("RESPONSE ACTUALIZAR: "+json);
                    }
                    
                    else if(obj.descrip.equals("actualizarChat")){
                        
                        System.out.println("  ACTUALIZAR CHAT");
                        
                        out.println(actualizarChat(obj));
                        //String json = gson.toJson(actualizarChat(obj));
                        //System.out.println("RESPONSE ACTUALIZARCHAT: "+json);
                    }
                    
                    else if(obj.matriz == null){
                        
                        //obj.setMatriz(generarMatrizInicialPost(obj.tam));
                        
                        String id = String.valueOf(juegos.size()+1);
                        String jugador1 = obj.jugador1;                        
                        String tam = obj.tam;
                        ArrayList<String> chatPrueba = new ArrayList();// {"Server: Bienvenidos!"};
                        chatPrueba.add("Server: Bienvenidos!");
                        String tipoJuego= "usuario";
                        String jugador2 = "";
                        String dificultad="";
                        
                        if(!obj.tipoJuego.equals("usuario")){
                            
                            tipoJuego=obj.tipoJuego;
                            jugador2 = "PC";
                            dificultad= obj.dificultad;
                        
                        }
                        
                        Nenlinea juego = new Nenlinea("nenlinea",  id, jugador1, jugador2, tam, null, 0, 0, 0, chatPrueba, tipoJuego, dificultad, 1);
                        
                        juego.setMatriz(generarMatrizInicialPost(obj.tam));
                        juego.setChat(chatPrueba);
                        juegos.add(juego);
                        
                        String json = gson.toJson(juego);
                        System.out.println("RESPONSE CREAR: "+json);
                        out.println(json);
                        
                        /*System.out.println("parsead "+obj.jugador1);
                        System.out.println("parsead "+obj.jugador2);
                        System.out.println("parsead "+obj.matriz);
                        System.out.println("parsead "+obj.tam);
                        System.out.println("parsead "+obj.jugadaX);
                        System.out.println("parsead "+obj.jugadaY);*/
                    }
                    else{
                        
                        out.println(validarPOST(line));
                    }
                    
                 
                    
                }
    
                /*System.out.println("linea final: "+line);
                System.out.println("linea final: "+request.getRemoteAddr());
                System.out.println("linea final: "+request.getRemoteHost());
                System.out.println("linea final: "+request.getRequestedSessionId());*/
                
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Solicitud GET");
        setAccessControlHeaders(response);
        PrintWriter writer = response.getWriter();
        writer.write("test response from myServlet");
        processRequest(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Solicitud POST");
        setAccessControlHeaders(response);
        //PrintWriter writer = response.getWriter();
        //writer.write("test response from myServlet");
        
        processRequest(request, response);
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    private void setAccessControlHeaders(HttpServletResponse resp) {
      resp.setHeader("Access-Control-Allow-Origin", "*");
      resp.setHeader("Access-Control-Allow-Methods", "POST, GET");
    }
    
    public Ficha[][] generarMatrizInicialPost(String n) {//Generar matriz inicial
        int a = Integer.parseInt(n);
        
        Ficha matriz[][] = new Ficha[a][a];

        for (int i = 0; i < a; i++) {
            
            for (int j = 0; j < a; j++) {

                Ficha ficha = new Ficha(0, i, j);
                matriz[i][j] = ficha;
                

            }   
        }
            /*String[] chatPrueba = {"hola"};
        Nenlinea juego = new Nenlinea("", "", "", "", "", matriz, -1, -1, 0, chatPrueba);
        
        Gson gson = new Gson();
        String json = gson.toJson(juego);
        return json;*/
        return matriz;

    }
    
    public boolean leerRequest(String n){
        System.out.println("LEER REQUEST");
        
        try {
            
            int a = Integer.parseInt(n);
            
            
        } catch (NumberFormatException e) {
            System.out.println("No se pudo "+n);
            return false;
        }
        
        return true;
    }
    
    public String postBuscarJuego(Nenlinea req){
        
        //postListaJuegos();//llena dos juegos default
        Gson gson = new Gson();
        String json = "";
        
        for(Nenlinea juego : juegos) {
            
            if(juego.id.equals(req.id)){
                
                
                juego.jugador2 = req.jugador2; 
                
                json = gson.toJson(juego);
                return json;
                
            }
            

        }
        return json;
        
      
    } 
    
    public String actualizarChat(Nenlinea req){
        
        //postListaJuegos();//llena dos juegos default
        Gson gson = new Gson();
        String json = "";
        
        for(Nenlinea juego : juegos) {
            
            if(juego.id.equals(req.id)){
                
                
                juego.chat.add(req.chat.get(req.chat.size()-1));
                //System.out.println("========================================");
                //System.out.println(Arrays.toString(juegos.toArray()));
                //System.out.println("========================================");
                json = gson.toJson(juego);
                
                return json;
                
            }
            
            

        }
        return json;
        
      
    } 
    public int nivelFacil(int tam){
        System.out.println("");
        int numero1 = (int) (Math.random() * (tam-1));  //para ingresar ficha en una posicion random   
        System.out.println("numero 1 random: "+ numero1);
       
        return numero1;
    }
    
    public String actualizarDash(Nenlinea req){
        
        //postListaJuegos();//llena dos juegos default
        Gson gson = new Gson();
        String json = "";
        
        for(Nenlinea juego : juegos) {
            
            if(juego.id.equals(req.id)){
                
                
                
                json = gson.toJson(juego);
                return json;
                
            }
            
            

        }
        return json;
        
      
    } 
    
    public String verificarJugador2EnJuego(Nenlinea req){
        
        //postListaJuegos();//llena dos juegos default
        Gson gson = new Gson();
        String json = "";
        
        for(Nenlinea juego : juegos) {
            
            if(juego.id == req.id){// && !"".equals(juego.jugador2)){
                
                
                juego.jugador2="prueba";
                json = gson.toJson(juego);
                return json;
                
            }
            json = gson.toJson(juego);
            

        }
        return json;
        
      
    } 
    
    public String[] postListaJuegos(){
        
        //juegos.removeAll(juegos);
        /*String[] chatPrueba = {"hola"};
        String id1 = String.valueOf(juegos.size());
        String id2 = String.valueOf(juegos.size()+1);
        Nenlinea j1 = new Nenlinea("1", id1, "kevin ubau", "", "5", null, 0, 0, 0, chatPrueba);
        Nenlinea j2 = new Nenlinea("2", id2, "Juana papalotes", "", "6", null, 0, 0, 0, chatPrueba);
        juegos.add(j1);
        juegos.add(j2);
        */
        int cont = 0;
        for(Nenlinea juego : juegos) {
            if("".equals(juego.jugador2)){
                cont++;
            }
        }
        String[] array = new String[cont];
        
        
        Gson gson = new Gson();
        boolean flag = false;
        cont =0;
        System.out.println(Arrays.toString(array)+" ARRAY1 ");
        for (Nenlinea juego : juegos) {
            System.out.println("Lo que hay: "+juego.tipoJuego);
            if("".equals(juego.jugador2)){
                flag = true;
                System.out.println("IF post lista juegos");
                String json = gson.toJson(juego);
                array[cont] = json;
                cont++;
            }
            
        }
        
        
        System.out.println(Arrays.toString(array)+" ARRAY2 ");
        
        return array;
    } 
    public Ficha [][]turno2(Nenlinea mat){
        System.out.println("ENtra a turno 2");
        if (mat.jugador2.equals("PC")&&mat.turno==2){
            System.out.println("Entra a if de turno 2");
            mat.jugadaY=nivelFacil(Integer.parseInt(mat.tam));
            return mat.matriz;
        }
        return mat.matriz;
    }
    public void verificarJuego(){
        System.out.println("ENtra a verificarJuego");
        Gson gson = new Gson();
        String json="";
        for(Nenlinea juego : juegos) {
            if (juego.jugador2.equals("PC") &&juego.turno==2){
                juego.matriz=turno2(juego);
                json = gson.toJson(juego);
                validarPOST(json);
                //String json = gson.toJson(obj);
        
            }
            
        }
       
    }
    
    public String validarPOST(String n) {//validar clic con POST
    
        Gson gson = new Gson();
        Nenlinea obj = gson.fromJson(n, Nenlinea.class);//pasar el string json a objeto de la clase Nenlinea 
        System.out.println("Tipo juego   "+obj.tipoJuego);
        System.out.println("dificultad "+ obj.dificultad);
        int tam = obj.matriz.length;
        //obj.matriz= turno2(obj, tam);
        //if (obj.tipoJuego.equals("PC") && obj.turno==2){ //&& (obj.dificultad.equals("pcFacil"))){
        //    obj.jugadaY=nivelFacil(tam);
        //}
        obj.matriz = votearMatrizHaciaOriginal(tam, colocarFichasAlFondo(girarMatriz(tam, obj.matriz), obj.jugadaY,obj.turno));//para colocar fichas al fondo
        
        obj.matriz = votearMatrizHaciaOriginal(tam, vertical(tam, girarMatriz(tam, obj.matriz)));//para verificar si hay gane en vertical
        
        
        
        //HORIZONTAL **
        obj.matriz = vertical(tam, obj.matriz);//para verificar si hay gane en horizontal last,08:20AM 19/03


        obj.matriz = diagonalSuperior(3, obj.matriz);//validar diagonal superior normal /
        obj.matriz = diagonalInferior(3, obj.matriz);//validar diagonal inferior normal /
       
        obj.matriz = votearMatrizHaciaOriginal(tam, diagonalSuperior(3, girarMatriz(tam, obj.matriz))); //validar diagonal superior inversa \
        obj.matriz = votearMatrizHaciaOriginal(tam, diagonalInferior(3, girarMatriz(tam, obj.matriz))); //validar diagonal inferior inversa \
        
        obj= verificarGane(obj);// si encuentra al menos una ficha con el color de gane, entonces cambiara a 1 el atributo .gana en el json
        
        if (obj.turno==1){
            
            obj.turno=2;
        }else{
            obj.turno=1;
        }
        for(Nenlinea juego : juegos) {
            
            if(juego.id.equals(obj.id)){
                
                
                juego.matriz = obj.matriz;
                juego.turno=obj.turno;
                juego.jugadaY=obj.jugadaY;
                //juego.chat=obj.chat;
                break;
                
            }
        }
        
        String json = gson.toJson(obj);
        
        return json;
    }
    
    
    public Ficha[][] girarMatriz(int a, Ficha matriz[][]){
        
        Ficha matrizAux[][] = new Ficha[a][a];
        int c1 = a-1;
        int c2 = a-1;
        int c3 = 0;
        int c4 = a-1;
        
        
        for (int i = 0; i < a; i++) {
            
            
            c1=a-1;
            c4=a-1;
            for (int j = 0; j < a; j++) {
                matrizAux[c1][c2] = matriz[c3][c4];
                c1--;
                c4--;
            }
            c2--;
            c3++;
            
            
            
        }
        
        

        return matrizAux;
    
    }
    
    public Ficha[][] colocarFichasAlFondo(Ficha matrizGirada[][], int posicionY, int turno){
 
        int a = matrizGirada.length;
        for (int i = 0; i < a; i++) {
            if(i == posicionY){
                
                for (int j = 0; j < a; j++) {
                    if(matrizGirada[i][j].status == 0){
                        matrizGirada[i][j].status = turno;
                        return matrizGirada;
                    }
                
                

                }
            }
            
            
        }
        
        return matrizGirada;
    
    }
    
    public Ficha[][] votearMatrizHaciaOriginal(int a, Ficha[][] matriz){
        Ficha[][] matrizAux = new Ficha[a][a];
        int c1 = a-1;
        int c2 = a-1;
        int c3 = 0;
        int c4 = a-1;
        
        
        
        for (int i = 0; i < a; i++) {
            
            
            c1=a-1;
            c4=a-1;
            for (int j = 0; j < a; j++) {
                matrizAux[c2][c1] = matriz[c4][c3];
                c1--;
                c4--;
            }
            c2--;
            c3++;
            
            
            
        }
        
        
        return matrizAux;
    
    
    }  
    
    public Ficha[][] vertical(int tam, Ficha[][] matriz){
//        System.out.println(" VERTICAL XXXXXXXXXXXXXXXXXX");
        int cont = 0;
        ArrayList<Ficha> fichasGanadoras = new ArrayList();
        for(Ficha[] fila:matriz){
            fichasGanadoras.clear();
         

            for(Ficha ficha:fila){
                if(ficha.status==2){
                    cont++;
                    
                    fichasGanadoras.add(ficha);
                    
                    
                }
                else{
                    cont=0;
                    fichasGanadoras.clear();
                    
                }
                
                if(cont == 4){
                    for(Ficha fich:fichasGanadoras){
                        fich.status = 4;
                        for(Ficha[] fi:matriz){
                            
                            for(Ficha f:fi){
                               
                                if(fich.posicionX == f.posicionX && fich.posicionY == f.posicionY){
                                    f.status = fich.status;
                                }
                            }
                        }
                    }
                    return matriz;
                    
                }




            }
        }
        return matriz;
    }
    
    public Ficha[][] auxDiagonal(int contGane, int fichasParaGanar, ArrayList<Ficha> listaFichas, Ficha[][] matriz){
        
        if(contGane == fichasParaGanar){
            
                 
                    for(Ficha fich:listaFichas){
                        fich.status = 4;
                        for(Ficha[] fi:matriz){
                            
                            for(Ficha f:fi){
                                
                                if(fich.posicionX == f.posicionX && fich.posicionY == f.posicionY){
                                    f.status = fich.status;
                                }
                            }
                        }
                    }
                    return matriz;
             
             }
        return matriz;
    }
        
    public Ficha[][] diagonalSuperior(int fichasParaGanar, Ficha[][] matriz){
        int tam = matriz.length;
        int posX = 0;
        int posY;
        int contFichas;
        int auxPosX;
        ArrayList<Ficha> listaFichas = new ArrayList();
        
        for (int i = 0; i < tam; i++) {
            
            posY = 0;
            auxPosX = posX;
            contFichas = 0;
            listaFichas.clear();
            
            while (auxPosX >= 0) {
                
                if(matriz[auxPosX][posY].status == 2)
                {
                    contFichas++;
                    listaFichas.add(matriz[auxPosX][posY]);
                }
                
                
                else
                {
                    contFichas = 0;
                    listaFichas.clear();
                            
                }
                
                if(contFichas == 3)
                {
                 

                    return auxDiagonal(contFichas, fichasParaGanar, listaFichas, matriz);
                    
                }
                posY++;
                auxPosX--;
                
            }
            posX++;                              
            
        }
        
        return matriz;
        
    }
    
    public Ficha[][] diagonalInferior(int fichasParaGanar, Ficha[][] matriz ){
        int tam = matriz.length;
        ArrayList<Ficha> listaFichas = new ArrayList();
        int posX = tam-1;
        int posY = 0;
        int contFichas;
        int auxPosX;
        int auxPosY;
        
        
        for (int i = 0; i < tam; i++) {
            
            posY++;
            auxPosX = posX;
            auxPosY = posY;
            contFichas = 0;
            listaFichas.clear();

            
            while (auxPosY <= tam-1) {
                
                if(matriz[auxPosX][auxPosY].status == 2)
                {
                    listaFichas.add(matriz[auxPosX][auxPosY]);
                    contFichas++;
                }
                
                
                else
                {
                    contFichas = 0;
                    listaFichas.clear();
                }
                
                if(contFichas == 3)
                {
                   
                    return auxDiagonal(contFichas, fichasParaGanar, listaFichas, matriz);
                    
                }
                auxPosY++;
                auxPosX--;
                
            }
      
        }
        return matriz;
      
    }    
   
    public Nenlinea verificarGane(Nenlinea json){
        
        for(Ficha[] fila: json.matriz){
          

            for(Ficha ficha:fila){
                if(ficha.status==3 || ficha.status==4){
                    json.gana=2;//esto debe ser una variable y no una constante
                    return json;
                    
                    
                    
                    
                }
            }
        }
        return json;
    
    }
    
   

}
