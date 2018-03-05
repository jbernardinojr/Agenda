package br.com.example.jbsjunior.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.example.jbsjunior.agenda.converter.AlunoConverter;
import br.com.example.jbsjunior.agenda.dao.AlunoDAO;
import br.com.example.jbsjunior.agenda.model.Aluno;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by junior on 03/05/17.
 */

public class WebClient {

    public static final MediaType JSON
            = MediaType.parse("application/json");

    public String URL = "https:www.caelum.com.br/mobile";

    OkHttpClient client = new OkHttpClient();
    private ProgressDialog mDialog;
    private Context mContext;

    public WebClient(Context context) {
        mContext = context;
    }


    public String post (String json) {
        URL = "https:www.caelum.com.br/mobile";
        return realizaConexao(json, URL);
    }

    public void insere(String jsonCompleto){
        URL = "http://192.168.98.177:8080/api/aluno";
        realizaConexao(jsonCompleto, URL);
    }
    
    public String realizaConexao(String json, String endereco) {

        AlunoDAO dao = new AlunoDAO(mContext);
        List<Aluno> alunos = dao.getAlunos();
        dao.close();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(endereco)
                .post(body)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}