package br.com.example.jbsjunior.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import br.com.example.jbsjunior.agenda.dao.AlunoDAO;
import br.com.example.jbsjunior.agenda.model.Aluno;

public class FormularioActivity extends AppCompatActivity {

    ViewHolder mViewHolder;
    Aluno mAluno;
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

        Aluno aluno;

        public ViewHolder (FormularioActivity fa) {
            editName = (EditText) fa.findViewById(R.id.edt_name);
            editAddress = (EditText) fa.findViewById(R.id.edt_address);
            editEmail = (EditText) fa.findViewById(R.id.edt_email);
            editPhone = (EditText) fa.findViewById(R.id.edt_phone);
            ratingBar = (RatingBar) fa.findViewById(R.id.rtg_bar);
        }

        public Aluno getAluno() {
            aluno.setName(editName.getText().toString());
            aluno.setAddress(editAddress.getText().toString());
            aluno.setEmail(editEmail.getText().toString());
            aluno.setPhone(editPhone.getText().toString());
            aluno.setNota(Double.valueOf(ratingBar.getProgress()));
            return aluno;
        }

        public void setFieldsAluno(Aluno aluno) {
            editName.setText(aluno.getName());
            editPhone.setText(aluno.getPhone());
            editAddress.setText(aluno.getAddress());
            editEmail.setText(aluno.getEmail());
            ratingBar.setProgress(aluno.getNota().intValue());
            this.aluno = aluno;
        }
    }
}
