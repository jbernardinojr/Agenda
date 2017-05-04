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

public class WebClient extends AsyncTask<Void,Void,String>{

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final String URL_SITE = "https:www.caelum.com.br/mobile";

    OkHttpClient client = new OkHttpClient();
    private ProgressDialog mDialog;
    private Context mContext;

    public WebClient(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        mDialog = ProgressDialog.show(mContext, "Enviando Dados", "Aguarde", true);
    }

    @Override
    protected String doInBackground(Void... params) {

        AlunoDAO dao = new AlunoDAO(mContext);
        List<Aluno> alunos = dao.getAlunos();
        dao.close();
        AlunoConverter converter = new AlunoConverter();
        String json = converter.convertToJson(alunos);

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(URL_SITE)
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

    @Override
    protected void onPostExecute(String response) {

        if (response != null) {
            Log.d("Bernardino", response);
        } else {
            Log.d("Bernardino", "Response is null");
        }

        mDialog.dismiss();
    }
}