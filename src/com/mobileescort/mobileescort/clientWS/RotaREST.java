package com.mobileescort.mobileescort.clientWS;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mobileescort.mobileescort.model.Rota;

public class RotaREST {

    private static final String URL_WS = "http://10.0.0.101:8080/wsMobileEscort/api/";

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
         return resposta[1];
     } else {
         throw new Exception(resposta[1]);
     }
    }
    
    public String deletarRota(int id) {   
     String[] resposta = new WebServiceClient().get(URL_WS + "rotas/" + id);
     return resposta[1];
    }
}
 

