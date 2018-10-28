package com.example.natanael.appgmmultimarcas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.natanael.appgmmultimarcas.model.Pessoa;

public class PessoaDAO extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "DB_GMmultimarcas.db";
    private static final int VERSION = 1;
    private static final String TABELA = "PESSOA";
    private static final String PESSOA_ID = "ID";
    private static final String PESSOA_NOME = "NOME";
    private static final String PESSOA_IDADE = "IDADE";
    private static final String PESSOA_ENDERECO = "ENDERECO";
    private static final String PESSOA_TELEFONE = "TELEFONE";

    public PessoaDAO(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override //CRIA TABELA NO BANCO
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE " + TABELA + " (\n");
        sql.append(PESSOA_ID + " INTEGER PRIMARY KEY AUTOINCREMENTO,\n");
        sql.append(PESSOA_NOME + " TEXT,\n");
        sql.append(PESSOA_IDADE + " INTEGER,\n");
        sql.append(PESSOA_ENDERECO + " TEXT,\n");
        sql.append(PESSOA_TELEFONE + " TEXT\n");
        sql.append(");");

        db.execSQL(String.valueOf(sql));
    }

    @Override //VERIFICA SE A TABELA JÁ ESTÁ CRIADA
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = String.format("DROP TABLE IS EXISTS %s", TABELA);
        db.execSQL(sql);
        onCreate(db);
    }

    public long salvarPessoa(Pessoa pessoa){
        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(PESSOA_NOME,pessoa.getNome());
        values.put(PESSOA_IDADE,pessoa.getIdade());
        values.put(PESSOA_ENDERECO,pessoa.getEndereco());
        values.put(PESSOA_TELEFONE,pessoa.getTelefone());

        retornoDB = getWritableDatabase().insert(TABELA, null, values);

        return retornoDB;
    }
}
