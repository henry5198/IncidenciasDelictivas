package com.example.IncidenciasDelictivas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Noticias {
    private String titulo;
    private String subtitulo;
    private String url;
    private String fecha;
    public Noticias(JSONObject a) throws JSONException {
        titulo =  a.getString("titulo");
        subtitulo =   a.getString("intro");
        //url = "http://www.uteq.edu.ec/images/noticias/" + a.getString("406") + "/" + a.getString("portada_406") + ".jpeg";
        //url = "http://www.uteq.edu.ec/images/noticias/406/portada_406.jpeg";
        url = "http://www.uteq.edu.ec/"+a.getString("url");
        fecha=a.getString("publicacion");
    }
    public Noticias(String tit, String sub){
        titulo = tit;
        subtitulo = sub;
    }
    public String getTitulo(){
        return titulo;
    }
    public String getSubtitulo(){
        return subtitulo;
    }
    public String getUrl() { return url; }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public static ArrayList<Noticias> jsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<Noticias> noticias = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
                noticias.add(new Noticias(datos.getJSONObject(i)));
            }       return noticias;
    }
}
