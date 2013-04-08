package com.mobileescort.mobileescort.clientWS;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mobileescort.mobileescort.model.Usuario;

public class UsuarioREST {

    private static final String URL_WS = "http://10.51.6.30:8080/Test/api/";

    public Usuario getUsuario(String nome, String password) throws Exception {

     String[] resposta = new WebServiceClient().get(URL_WS + "usuarios/buscarUsuario/" + password);
     
     if (resposta[0].equals("200")) {
         Gson gson = new Gson();
         Usuario Usuario = gson.fromJson(resposta[1], Usuario.class);
         return Usuario;
     } else {
         throw new Exception(resposta[1]);
     }
    }
    
    public List<Usuario> getListaUsuario() throws Exception {

     String[] resposta = new WebServiceClient().get(URL_WS + "usuarios");
//       String[] resposta = new WebServiceUsuario().get(URL_WS + "buscarTodos");
     
     if (resposta[0].equals("200")) {
         Gson gson = new Gson();
         ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
         JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(resposta[1]).getAsJsonArray();
         
        for (int i = 0; i < array.size(); i++) {
             listaUsuario.add(gson.fromJson(array.get(i), Usuario.class));
         }
         return listaUsuario;
     } else {
         throw new Exception(resposta[1]);
     }
    }
    
    public String inserirUsuario(Usuario Usuario) throws Exception {
     
     Gson gson = new Gson();
     String UsuarioJSON = gson.toJson(Usuario);
     String[] resposta = new WebServiceClient().post(URL_WS + "usuarios", UsuarioJSON);
     if (resposta[0].equals("200")) {
         return resposta[1];
     } else {
         throw new Exception(resposta[1]);
     }
    }
    
    public Usuario consultarUsuario(int id) throws Exception {   
     String[] resposta = new WebServiceClient().get(URL_WS + "usuarios/" + id);
     if (resposta[0].equals("200")) {
         Gson gson = new Gson();
         Usuario Usuario = gson.fromJson(resposta[1], Usuario.class);
         return Usuario;
     } else {
         throw new Exception(resposta[1]);
     }
     
    }
}
 

