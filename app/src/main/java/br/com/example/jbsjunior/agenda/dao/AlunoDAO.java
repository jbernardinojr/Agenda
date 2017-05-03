package br.com.example.jbsjunior.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.example.jbsjunior.agenda.model.Aluno;

/**
 * Created by JBSJunior on 12/04/2017.
 */

public class AlunoDAO extends SQLiteOpenHelper{

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "agenda.db";


    public AlunoDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_ALUNO_TABLE = "CREATE TABLE Aluno (id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "address TEXT, " +
                "email TEXT, " +
                "phone TEXT, " +
                "nota REAL, " +
                "caminhoFoto TEXT);";

        db.execSQL(SQL_CREATE_ALUNO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "";

        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Aluno ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql); //update to version 2
        }
    }

    public long insert (Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = getAlunoData(aluno);

        return db.insert("Aluno", null, cv);
    }

    @NonNull
    private ContentValues getAlunoData(Aluno aluno) {
        ContentValues cv = new ContentValues();
        cv.put("name", aluno.getName());
        cv.put("address", aluno.getAddress());
        cv.put("email", aluno.getEmail());
        cv.put("phone", aluno.getPhone());
        cv.put("nota", aluno.getNota());
        cv.put("caminhoFoto", aluno.getCaminhoFoto());
        return cv;
    }

    public List<Aluno> getAlunos() {

        String sql = "SELECT * FROM Aluno;";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);
        Aluno aluno;
        List<Aluno> alunos = new ArrayList<>();
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                aluno = new Aluno();
                aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
                aluno.setName(cursor.getString(cursor.getColumnIndex("name")));
                aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
                aluno.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                aluno.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));
                alunos.add(aluno);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return alunos;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        String [] params = {aluno.getId().toString()};

        db.delete("Aluno", " id = ? ",  params);
    }

    public void update(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = getAlunoData(aluno);
        String [] params = {aluno.getId().toString()};

        db.update("Aluno", cv, " id = ? ", params);
    }

    public boolean isAluno(String tel) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from Aluno where phone = ?", new String[]{tel});
        boolean ret = cursor.getCount() > 0;
        cursor.close();

        return ret;
    }
}
