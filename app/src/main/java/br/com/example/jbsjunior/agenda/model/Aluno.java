package br.com.example.jbsjunior.agenda.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by JBSJunior on 12/04/2017.
 */

public class Aluno implements Comparable, Parcelable {

    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private Double nota;
    private String caminhoFoto;

    public Aluno(){}

    public Aluno(Parcel in) {
        id = in.readLong();
        name = in.readString();
        address = in.readString();
        email = in.readString();
        phone = in.readString();
        nota = in.readDouble();
        caminhoFoto = in.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }


    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }


    @Override
    public String toString() {
        return getId() + " - " + getName();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Aluno otherAluno = (Aluno)o;
        return this.getName().compareTo(otherAluno.getName());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeDouble(nota);
        dest.writeString(caminhoFoto);
    }

    public static final Creator<Aluno> CREATOR = new Creator<Aluno>() {
        @Override
        public Aluno[] newArray(int size) {
            return new Aluno[size];
        }

        @Override
        public Aluno createFromParcel(Parcel in) {
            return new Aluno(in);
        }
    };
}
