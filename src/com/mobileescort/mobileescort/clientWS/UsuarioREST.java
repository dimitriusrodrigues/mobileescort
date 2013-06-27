package com.mobileescort.mobileescort.clientWS;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.SessionManager;

public class UsuarioREST {

    private static final String URL_WS = SessionManager.URL_WS;

    public Usuario getUsuario(String nome, String password) throws Exception {

    	String[] resposta = new WebServiceClient().get(URL_WS + "usuarios/buscarUsuario/" +nome + "/" + password);
    	
    	if (resposta[0] == null) {
    		return null;
    	} else {
		    if (resposta[0].equals("200")) {
		    	Gson gson = new Gson();
		    	Usuario Usuario = gson.fromJson(resposta[1], Usuario.class);
		    	return Usuario;
		    } 
		    else { 
		    	throw new Exception(resposta[1]); 
		    }
    	}    
    }
    
    public List<Usuario> getListaUsuario() throws Exception {

    	String[] resposta = new WebServiceClient().get(URL_WS + "usuarios");
 
    	if (resposta[0].equals("200")) {
    		Gson gson = new Gson();
    		ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
    		JsonParser parser = new JsonParser();
    		JsonArray array = parser.parse(resposta[1]).getAsJsonArray();
     
    		for (int i = 0; i < array.size(); i++) {
    			listaUsuario.add(gson.fromJson(array.get(i), Usuario.class));
    		}
    		return listaUsuario;
    	}
    	else {
    		throw new Exception(resposta[1]);
    	}
    }

    public List<Usuario> getListaUsuario(Usuario motorista) throws Exception {

        String[] resposta = new WebServiceClient().get(URL_WS + "usuarios/buscarPassageiros/" + motorista.getId_usuario());
        
        if (resposta[0].equals("200")) {
        	Gson gson = new Gson();
            ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(resposta[1]).getAsJsonArray();
            
            for (int i = 0; i < array.size(); i++) {
            	listaUsuario.add(gson.fromJson(array.get(i), Usuario.class));
            }
            return listaUsuario;
        }
        else {
        	throw new Exception(resposta[1]);
        }
    }

    
    public String inserirUsuario(Usuario Usuario) throws Exception {
     
    	Gson gson = new Gson();
    	String UsuarioJSON = gson.toJson(Usuario);
    	String[] resposta = new WebServiceClient().post(URL_WS + "usuarios", UsuarioJSON);
    	if (resposta[0].equals("200")) {
    		Usuario newUser = gson.fromJson(resposta[1], Usuario.class);
    	 
    		if ( newUser.getId_usuario() == 0 ){
    			return "NOK";
    		}
    		else {
    			return "OK";
    		}
    	}
    	else {
    		throw new Exception(resposta[1]);
    	}
    }
    
    public String atualizarUsuario(Usuario usuario) throws Exception {
        
        Gson gson = new Gson();
        String usuarioJSON = gson.toJson(usuario);
        String[] resposta = new WebServiceClient().post(URL_WS + "usuarios/update", usuarioJSON);
        if (resposta[0].equals("200")) {
        	if (resposta[1].equals("0")) {
        		return "NOK";
        	} else {
        		return "OK";
        	}
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
 

