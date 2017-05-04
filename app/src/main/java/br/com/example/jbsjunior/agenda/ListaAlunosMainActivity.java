package br.com.example.jbsjunior.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.Collections;
import java.util.List;

import br.com.example.jbsjunior.agenda.adapter.AlunosAdapter;
import br.com.example.jbsjunior.agenda.converter.AlunoConverter;
import br.com.example.jbsjunior.agenda.dao.AlunoDAO;
import br.com.example.jbsjunior.agenda.model.Aluno;

public class ListaAlunosMainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_SMS = 456;
    List<Aluno> mAlunos;
    ListView mListaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.fab);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAlunosMainActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        mListaAlunos = (ListView) findViewById(R.id.lista_alunos);
        registerForContextMenu(mListaAlunos);

        mListaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) mListaAlunos.getItemAtPosition(position);

                Intent intent = new Intent(ListaAlunosMainActivity.this, FormularioActivity.class);
                intent.putExtra("aluno", aluno);
                startActivity(intent);
            }
        });

        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        REQUEST_CODE_SMS);
        }
    }

    private void loadListAlunos() {
        AlunoDAO dao = new AlunoDAO(this);
        mAlunos = dao.getAlunos();
        dao.close();
        Collections.sort(mAlunos);

        AlunosAdapter adapter = new AlunosAdapter(this, mAlunos);
        mListaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListAlunos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_alunos_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) mListaAlunos.getItemAtPosition(info.position);

        String site = aluno.getEmail();

        if (!(site.startsWith("http://") || site.startsWith("https://"))) {
            site = "http://" + site;
        }

        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (ActivityCompat.checkSelfPermission(ListaAlunosMainActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(ListaAlunosMainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getPhone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });

        MenuItem itemSms = menu.add("Enviar SMS");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:" + aluno.getPhone()));
        itemSms.setIntent(intentSms);

        MenuItem itemVisita = menu.add("Visitar Site");
        Intent visitaSiteIntent = new Intent(Intent.ACTION_VIEW);
        visitaSiteIntent.setData(Uri.parse(site));
        itemVisita.setIntent(visitaSiteIntent);

        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?z=14&q=" + aluno.getAddress()));
        itemMapa.setIntent(intentMapa);

        MenuItem delete = menu.add("Deletar");
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AlunoDAO alunoDAO = new AlunoDAO(ListaAlunosMainActivity.this);
                alunoDAO.deleta(aluno);
                alunoDAO.close();

                loadListAlunos();

                Toast.makeText(ListaAlunosMainActivity.this, "Deletar o aluno " + aluno.getName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_enviar_notas:
                WebClient post = new WebClient(this);
                post.execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
