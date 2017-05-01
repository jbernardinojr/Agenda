package br.com.example.jbsjunior.agenda;

/**
 * Created by junior on 28/04/17.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.example.jbsjunior.agenda.model.Aluno;

/**
 * Defines a class that hold resource IDs of each item layout
 * row to prevent having to look them up each time data is
 * bound to a row.
 */
public class ViewHolder {
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
