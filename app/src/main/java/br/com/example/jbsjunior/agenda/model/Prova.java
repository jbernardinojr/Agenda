package br.com.example.jbsjunior.agenda.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junior on 21/05/17.
 */

public class Prova implements Parcelable{

    private String materia;
    private String data;
    private List<String> topicos;

    public Prova(String materia, String data, List<String> topicos) {
        this.materia = materia;
        this.data = data;
        this.topicos = topicos;
    }

    public Prova(Parcel in) {
        materia = in.readString();
        data = in.readString();
        topicos = new ArrayList<>();
        in.readList(topicos, List.class.getClassLoader());
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<String> topicos) {
        this.topicos = topicos;
    }

    @Override
    public String toString() {
        return this.materia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(materia);
        dest.writeString(data);
        dest.writeList(topicos);
    }

    public static final Creator<Prova> CREATOR = new Creator<Prova>() {
        @Override
        public Prova[] newArray(int size) {
            return new Prova[size];
        }

        @Override
        public Prova createFromParcel(Parcel in) {
            return new Prova(in);
        }
    };
}
