package br.com.example.jbsjunior.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.example.jbsjunior.agenda.R;
import br.com.example.jbsjunior.agenda.model.Aluno;

/**
 * Created by junior on 28/04/17.
 */

public class AlunosAdapter extends BaseAdapter {

    private final List<Aluno> mAlunos;
    private final Context mContext;
    private ImageView mCampoFoto;
    private TextView mNomeAluno;
    private TextView mTelefone;
    private TextView mEmailSite;
    private TextView mAddress;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.mContext = context;
        this.mAlunos = alunos;
    }

    @Override
    public int getCount() {
        return mAlunos.size();
    }

    @Override
    public Object getItem(int position) {
        return mAlunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mAlunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = mAlunos.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        mNomeAluno = (TextView) view.findViewById(R.id.item_nome);
        mNomeAluno.setText(aluno.getName());

        mTelefone = (TextView) view.findViewById(R.id.item_telefone);
        mTelefone.setText(aluno.getPhone());

        mAddress = (TextView) view.findViewById(R.id.item_address);
        if (mAddress != null) {
            mAddress.setText(aluno.getAddress());
        }

        mEmailSite = (TextView) view.findViewById(R.id.item_email);
        if(mEmailSite != null) {
            mEmailSite.setText(aluno.getEmail());
        }

        mCampoFoto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            mCampoFoto.setImageBitmap(bitmapReduzido);
            mCampoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
