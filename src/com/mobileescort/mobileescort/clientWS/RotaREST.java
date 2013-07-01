package com.mobileescort.mobileescort.clientWS;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.utils.SessionManager;

public class RotaREST {

    private static final String URL_WS = SessionManager.URL_WS;

    public Rota getRota(Integer id) throws Exception {

     String[] resposta = new WebServiceClient().get(URL_WS + "rotas/buscar/" + id);
     
     if (resposta[0].equals("200")) {
         Gson gson = new Gson();
         Rota Rota = gson.fromJson(resposta[1], Rota.class);
         return Rota;
     } else {
         throw new Exception(resposta[1]);
     }
    }
    
    public List<Rota> getListaRota(Integer id_motorista) throws Exception {

     String[] resposta = new WebServiceClient().get(URL_WS + "rotas/buscarRotas/" + id_motorista);
     
     if (resposta[0].equals("200")) {
         Gson gson = new Gson();
         ArrayList<Rota> listaRota = new ArrayList<Rota>();
         JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(resposta[1]).getAsJsonArray();
         
        for (int i = 0; i < array.size(); i++) {
             listaRota.add(gson.fromJson(array.get(i), Rota.class));
         }
         return listaRota;
     } else {
         throw new Exception(resposta[1]);
     }
    }
    
    public String inserirRota(Rota Rota) throws Exception {
     
     Gson gson = new Gson();
     String RotaJSON = gson.toJson(Rota);
     String[] resposta = new WebServiceClient().post(URL_WS + "rotas", RotaJSON);
     if (resposta[0].equals("200")) {
         return "OK";
     } else {
         throw new Exception(resposta[1]);
     }
    }
    
    public String deletarRota(int id) {   
     String[] resposta = new WebServiceClient().get(URL_WS + "rotas/" + id);
     return resposta[1];
    }
    
    public String deletarUsuarioRota(int id_rota, int id_usuario) {   
        String[] resposta = new WebServiceClient().delete(URL_WS + "rotas/delete/" + id_rota + "/" + id_usuario);
        return resposta[1];
    }
    
    public String enviarMensagem(Integer id_rota, String msg) throws Exception {
    	
    	String url = URLEncoder.encode(msg, "UTF-8");
    	
        String[] resposta = new WebServiceClient().get(URL_WS + "rotas/enviarNotificacao/" + id_rota + "/"+ url);
        if (resposta[0].equals("200")) {
            return "OK";
        } else {
            throw new Exception(resposta[1]);
        }
    }
    
    public String enviarMensagemUsuario(Integer id_rota, Integer id_usuario, String msg) throws Exception {
    	
    	String url = URLEncoder.encode(msg, "UTF-8");
    	
        String[] resposta = new WebServiceClient().get(URL_WS + "rotas/enviarNotificacaoUsuario/" + id_rota + "/" + id_usuario + "/" + url);
        if (resposta[0].equals("200")) {
            return "OK";
        } else {
            throw new Exception(resposta[1]);
        }
    }
}
 

