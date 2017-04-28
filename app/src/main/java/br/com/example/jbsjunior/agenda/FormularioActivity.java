package br.com.example.jbsjunior.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.File;

import br.com.example.jbsjunior.agenda.dao.AlunoDAO;
import br.com.example.jbsjunior.agenda.model.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 567;
    ViewHolder mViewHolder;
    Aluno mAluno;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewHolder = new ViewHolder(this);

        Intent intent = getIntent();
        mAluno = (Aluno) intent.getParcelableExtra("aluno");
        if (mAluno != null) {
            mViewHolder.setFieldsAluno(mAluno);
        }

        FloatingActionButton botaoFoto = (FloatingActionButton) findViewById(R.id.formulario_fb_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                //intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                //startActivityForResult(intentCamera, CAMERA_REQUEST_CODE);

                // essa parte muda no Android 7, estamos construindo uma URI
                // para acessar a foto utilizando o FileProvider
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(FormularioActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider", arquivoFoto));


                startActivityForResult(intentCamera, CAMERA_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mViewHolder.setCampoFoto(caminhoFoto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_formulario_activity, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_action_save:
                Aluno aluno = mViewHolder.getAluno();
                AlunoDAO dao = new AlunoDAO(this);
                if (aluno.getId() != null) {
                    dao.update(aluno);
                } else {
                    dao.insert(aluno);
                }
                dao.close();
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getName() + " Salvo!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Defines a class that hold resource IDs of each item layout
     * row to prevent having to look them up each time data is
     * bound to a row.
     */
    private static class ViewHolder {
        EditText editName;
        EditText editAddress;
        EditText editEmail;
        EditText editPhone;
        RatingBar ratingBar;
        ImageView campoFoto;

        Aluno aluno;

        public ViewHolder (FormularioActivity fa) {
            editName = (EditText) fa.findViewById(R.id.edt_name);
            editAddress = (EditText) fa.findViewById(R.id.edt_address);
            editEmail = (EditText) fa.findViewById(R.id.edt_email);
            editPhone = (EditText) fa.findViewById(R.id.edt_phone);
            ratingBar = (RatingBar) fa.findViewById(R.id.rtg_bar);
            campoFoto = (ImageView) fa.findViewById(R.id.formulario_foto);
            aluno = new Aluno();
        }

        public Aluno getAluno() {
            aluno.setName(editName.getText().toString());
            aluno.setAddress(editAddress.getText().toString());
            aluno.setEmail(editEmail.getText().toString());
            aluno.setPhone(editPhone.getText().toString());
            aluno.setNota(Double.valueOf(ratingBar.getProgress()));
            aluno.setCaminhoFoto((String) campoFoto.getTag());
            return aluno;
        }

        public void setFieldsAluno(Aluno aluno) {
            editName.setText(aluno.getName());
            editPhone.setText(aluno.getPhone());
            editAddress.setText(aluno.getAddress());
            editEmail.setText(aluno.getEmail());
            ratingBar.setProgress(aluno.getNota().intValue());
            setCampoFoto(aluno.getCaminhoFoto());
            this.aluno = aluno;
        }

        public void setCampoFoto(String caminhoFoto) {

            if (caminhoFoto != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                campoFoto.setImageBitmap(bitmapReduzido);
                campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
                campoFoto.setTag(caminhoFoto);
            }
        }
    }
}
