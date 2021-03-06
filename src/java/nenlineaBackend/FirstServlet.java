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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            throws ServletException, IOException, SQLException {
        verificarJuego();
        
        response.setContentType("text/plain;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
           
            

            try (BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                String line;
                line = input.readLine();
                
                
                System.out.println(" REQUEST: "+line);
                //System.out.println(convertUTF8toISO(line));
                line = convertUTF8toISO(line);
                //System.out.println("REQUEST AFTER: "+line);
                if(line == null){
                    System.out.println("request is null!!");
                    
                }
                
                else if(line.equals("listaJuegos")){
                    
                    //System.out.println("SOLICITUD DE LISTA DE JUEGOS!!");
                    out.println(Arrays.toString(postListaJuegos()));
                }             
               
                
                else{
                    
                    Gson gson = new Gson();
                    Nenlinea obj = gson.fromJson(line, Nenlinea.class);//pasar el string json a objeto de la clase Nenlinea 
                    
                    if(obj.descrip.equals("aceptar")){
                        
                        //System.out.println("ACEPTAR JUEGO");
                        
                        out.println(postBuscarJuego(obj));
                        String json = gson.toJson(postBuscarJuego(obj));
                        //System.out.println("RESPONSE ACEPTAR: "+json);
                        
                    }
                    
                    else if(obj.descrip.equals("guardar")){

                        //buscar juego y enviarlo por parametro a funcion insert
                        System.out.println(" GUARDAR JUEGO");
                        String juego = verificarJugador2EnJuego(obj);
                        Nenlinea juegoOBJ = retornarJuego(obj);
                        enviarInsert(juegoOBJ.id, juegoOBJ.jugador1, juegoOBJ.jugador2, juego);
                        out.println(verificarJugador2EnJuego(obj));
                        
                    }
                    
                    else if(obj.descrip.equals("verificar")){
                        
                        //System.out.println("VERIFICAR JUEGO");
                        
                        out.println(verificarJugador2EnJuego(obj));
                        String json = gson.toJson(verificarJugador2EnJuego(obj));
                        //System.out.println("RESPONSE VERIFICAR: "+json);
                    }
                    
                    else if(obj.descrip.equals("actualizar")){ 
                        
                        //System.out.println("ACTUALIZAR JUEGO");
                        
                        out.println(actualizarDash(obj));
                        String json = gson.toJson(actualizarDash(obj));
                        //System.out.println("RESPONSE ACTUALIZAR: "+json);
                    }
                    
                    else if(obj.descrip.equals("actualizarChat")){
                        
                        //System.out.println("ACTUALIZAR CHAT");
                        
                        out.println(actualizarChat(obj));
                        
                    }
                    
                    else if(obj.matriz == null){

                        String id = String.valueOf(juegos.size()+1);
                        String jugador1 = obj.jugador1;                        
                        String tam = obj.tam;
                        ArrayList<String> chatPrueba = new ArrayList();// {"Server: Bienvenidos!"};
                        chatPrueba.add("Server: Bienvenidos!");
                        String tipoJuego= "usuario";
                        String jugador2 = "";
                        String dificultad="";
                        int cantFichasParaGanar = obj.cantFichasParaGanar;
                        
                        if(!obj.tipoJuego.equals("usuario")){
                            
                            tipoJuego=obj.tipoJuego;
                            jugador2 = "PC";
                            dificultad= obj.dificultad;
                        
                        }
                        
                        Nenlinea juego = new Nenlinea("nenlinea",  id, jugador1, jugador2, tam, null, 0, 0, 0, chatPrueba, tipoJuego, dificultad, 1, cantFichasParaGanar);
                        
                        juego.setMatriz(generarMatrizInicialPost(obj.tam));
                        juego.setChat(chatPrueba);
                        juegos.add(juego);
                        
                        String json = gson.toJson(juego);
                        System.out.println("RESPONSE CREAR: "+json);
                        out.println(json);
                     
                    }
                    else{
                        
                        out.println(validarPOST(line)); //Validar una jugada.
                    }
                    
                 
                    
                }
    
               
                
            }
            
        }
    }
    
    public void enviarInsert(String id, String j1, String j2, String juego) throws SQLException{

            Conector con=new Conector();
            con.Conectar();
            con.insertarBD(id, j1, j2, juego);
            Conector.viewTable();
            
   }
    
    public Nenlinea retornarJuego(Nenlinea req){
      
        for(Nenlinea juego : juegos) {
            
            if(juego.id.equals(req.id)){
  
                return juego;
                
            }
            

        }
        return req;
        
      
    } 
   
    public static String convertUTF8toISO(String str) {
	String ret;
	try {
		ret = new String(str.getBytes("ISO-8859-1"), "UTF-8");
	}
	catch (java.io.UnsupportedEncodingException e) {
		return null;
	}
	return ret;
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FirstServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        
        
        setAccessControlHeaders(response);
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FirstServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
            
        return matriz;

    }
    

    
    public String postBuscarJuego(Nenlinea req){
        
        
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
        
        
        Gson gson = new Gson();
        String json = "";
        
        for(Nenlinea juego : juegos) {
            
            if(juego.id.equals(req.id)){
                
                
                juego.chat.add(req.chat.get(req.chat.size()-1));
                json = gson.toJson(juego);
                
                return json;
                
            }
            
            

        }
        return json;
        
      
    } 
    public int nivelFacil(int tam){
        
        int numero1 = (int) (Math.random() * (tam-1));  //para ingresar ficha en una posicion random   
              
        return numero1;
    }
    
    public int nivelDificil(int jugadaY, int tamaño){
        int contRest1=0;
        int contRest2=0;
        int contRest3=0;
        int numeroX;
        int numero1;
        int mitadTamaño=tamaño/2;
        numeroX=jugadaY;
        
        
        
        if(jugadaY<mitadTamaño-1){
            numero1=jugadaY;
            contRest1++;
            if(contRest1>=2&&jugadaY<tamaño){
            numero1=jugadaY+1;
            }
            
        }
        else if(jugadaY>mitadTamaño-1 && jugadaY<tamaño-1){
            numero1=jugadaY+1;
            contRest2++;
            if(contRest2>=3){
                numero1=jugadaY-1;
            }
        }
        else{
            //contRojas++;
            numero1=jugadaY-1;
            contRest3++;
            if(contRest3>=2){
                numero1=jugadaY;
            }   
        }
        System.out.print("cont1: "+contRest1);
        System.out.print("cont2: "+contRest2);
        System.out.print("cont3: "+contRest3);
        System.out.print("JugadaY: "+jugadaY);
        System.out.println("NúmeroX: "+numeroX);
        System.out.println("Número: "+numero1);
        System.out.println("mitadTamaño: "+mitadTamaño);
        System.out.println("Nivel dificil en uso");
        return numero1;
    }
           
    public String actualizarDash(Nenlinea req){
        
        
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
        
        
        Gson gson = new Gson();
        String json = "";
        
        for(Nenlinea juego : juegos) {
            
            if(juego.id.equals(req.id)){
                
                
                
                json = gson.toJson(juego);
                return json;
                
            }
            json = gson.toJson(juego);
            

        }
        return json;
        
      
    } 
    
    public String[] postListaJuegos(){
        
      
        int cont = 0;
        for(Nenlinea juego : juegos) {
            if("".equals(juego.jugador2)){
                cont++;
            }
        }
        String[] array = new String[cont];
        
        
        Gson gson = new Gson();
        
        cont =0;
        //System.out.println(Arrays.toString(array)+" ARRAY1 ");
        for (Nenlinea juego : juegos) {
            
            if("".equals(juego.jugador2)){
                
                
                String json = gson.toJson(juego);
                array[cont] = json;
                cont++;
            }
            
        }
        
        
        //System.out.println(Arrays.toString(array)+" ARRAY2 ");
        
        return array;
    } 
    public Ficha [][]turno2(Nenlinea obj){
        
        if (obj.jugador2.equals("PC")&&obj.turno==2&&obj.dificultad.equals("pcFacil")){
            System.out.println("Entra a if de turno 2");
            obj.jugadaY=nivelFacil(Integer.parseInt(obj.tam));
            return obj.matriz;
        }
        else if (obj.jugador2.equals("PC")&&obj.turno==2&&obj.dificultad.equals("pcDificil")){
            System.out.println("Entra a dificil");
            obj.jugadaY=nivelDificil(obj.jugadaY, Integer.parseInt(obj.tam));
            //int tam, Ficha[][] matriz, int cantFichasParaGanar, int turno
            //mat.jugadaY=nivelDificil(Integer.parseInt(mat.tam));
            return obj.matriz;
        }
        else if(obj.jugador2.equals("PC")&&obj.turno==2&&obj.dificultad.equals("pcIntermedio")){
            int num = (int) (Math.random() * (2));
            System.out.println("random :"+ num);
            if (num==0){
                obj.jugadaY=nivelFacil(Integer.parseInt(obj.tam));
                return obj.matriz;
            }
            else{
               // obj.jugadaY=nivelDificil(obj.jugadaX, obj.jugadaY, Integer.parseInt(obj.tam));
                return obj.matriz;
            }
        
        }
        return obj.matriz;
    }
    
    
    public void verificarJuego(){//verificar si el jugado2 es la PC
        
        Gson gson = new Gson();
        String json;
        for(Nenlinea juego : juegos) {
            
            if (juego.jugador2.equals("PC") && juego.turno==2){
                juego.matriz=turno2(juego);
                json = gson.toJson(juego);
                validarPOST(json);
                
        
            }
            
        }
       
    }
    
    public String validarPOST(String n) {//validar clic con POST
    
        Gson gson = new Gson();
        Nenlinea obj = gson.fromJson(n, Nenlinea.class);//pasar el string json a objeto de la clase Nenlinea 
        //System.out.println("Tipo juego   "+obj.tipoJuego);
        //System.out.println("dificultad "+ obj.dificultad);
        int tam = obj.matriz.length;     
        obj.matriz = votearMatrizHaciaOriginal(tam, colocarFichasAlFondo(girarMatriz(tam, obj.matriz), obj.jugadaY,obj.turno));//para colocar fichas al fondo      
        

        //VERTICAL **
        obj.matriz = votearMatrizHaciaOriginal(tam, vertical(tam, girarMatriz(tam, obj.matriz), obj.cantFichasParaGanar, obj.turno));//para verificar si hay gane en vertical

        //HORIZONTAL **
        obj.matriz = vertical(tam, obj.matriz, obj.cantFichasParaGanar, obj.turno);//para verificar si hay gane en horizontal last,08:20AM 19/03

        //DIAGONALES **
        obj.matriz = diagonalSuperior(obj.cantFichasParaGanar, obj.matriz, obj.turno);//validar diagonal superior normal /
        obj.matriz = diagonalInferior(obj.cantFichasParaGanar, obj.matriz, obj.turno);//validar diagonal inferior normal /
        obj.matriz = votearMatrizHaciaOriginal(tam, diagonalSuperior(obj.cantFichasParaGanar, girarMatriz(tam, obj.matriz), obj.turno)); //validar diagonal superior inversa \
        obj.matriz = votearMatrizHaciaOriginal(tam, diagonalInferior(obj.cantFichasParaGanar, girarMatriz(tam, obj.matriz), obj.turno)); //validar diagonal inferior inversa \
        
        //VALIDAR GANE **
        obj= verificarGane(obj);// si encuentra al menos una ficha con el color de gane, entonces cambiara a 1 el atributo .gana en el json
        
        //SWITCH TURNOS
        if (obj.turno==1){
            
            obj.turno=2;
        }else{
            obj.turno=1;
        }
        
        //ACTUALIZAR JUEGO
        for(Nenlinea juego : juegos) {
            
            if(juego.id.equals(obj.id)){
                
                
                juego.matriz = obj.matriz;
                juego.turno=obj.turno;
                juego.jugadaY=obj.jugadaY;
                
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
    
    public Ficha[][] vertical(int tam, Ficha[][] matriz, int cantFichasParaGanar, int turno){
        int cont = 0;
        ArrayList<Ficha> fichasGanadoras = new ArrayList();
        for(Ficha[] fila:matriz){
            fichasGanadoras.clear();
         

            for(Ficha ficha:fila){
                if(ficha.status==turno){//2
                    cont++;
                    
                    
                    fichasGanadoras.add(ficha);
                    
                    
                }
                
                else{
                    cont=0;
                    fichasGanadoras.clear();
                    
                }
                
                if(cont == cantFichasParaGanar){
                    for(Ficha fich:fichasGanadoras){
                        fich.status = turno+2;//4
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
    
    public Ficha[][] auxDiagonal(int contGane, int fichasParaGanar, ArrayList<Ficha> listaFichas, Ficha[][] matriz, int turno){
        
        if(contGane == fichasParaGanar){

            for(Ficha fich:listaFichas){
                fich.status = turno+2;//ej si es 1(1+2=3 [gana el jugador 1])
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
        
    public Ficha[][] diagonalSuperior(int fichasParaGanar, Ficha[][] matriz, int turno){
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
                
                if(matriz[auxPosX][posY].status == turno)
                {
                    contFichas++;
                    listaFichas.add(matriz[auxPosX][posY]);
                }
                
                
                else
                {
                    contFichas = 0;
                    listaFichas.clear();
                            
                }
                
                if(contFichas == fichasParaGanar)
                {
                 

                    return auxDiagonal(contFichas, fichasParaGanar, listaFichas, matriz, turno);
                    
                }
                posY++;
                auxPosX--;
                
            }
            posX++;                              
            
        }
        
        return matriz;
        
    }
    
    public Ficha[][] diagonalInferior(int fichasParaGanar, Ficha[][] matriz, int turno){
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
                
                if(matriz[auxPosX][auxPosY].status == turno)//2
                {
                    listaFichas.add(matriz[auxPosX][auxPosY]);
                    contFichas++;
                }
                
                
                else
                {
                    contFichas = 0;
                    listaFichas.clear();
                }
                
                if(contFichas == fichasParaGanar)
                {
                   
                    return auxDiagonal(contFichas, fichasParaGanar, listaFichas, matriz, turno);
                    
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
                if(ficha.status==3){
                    json.gana=1;//esto debe ser una variable y no una constante
                    return json;

                }
                else if(ficha.status == 4){
                    json.gana = 2;
                    return json;
                
                }
            }
        }
        return json;
    
    }
    
   

}
