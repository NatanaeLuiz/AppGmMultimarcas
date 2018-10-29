package com.example.natanael.appgmmultimarcas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.natanael.appgmmultimarcas.model.Pessoa;

import java.util.ArrayList;

public class PessoaDAO extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "DB_GMmultimarcas.db";
    private static final int VERSION = 1;

    private static final String PESSOA_TABELA = "PESSOA";
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
        /*
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IS NOT EXISTS " + PESSOA_TABELA + " (\n");
        sql.append(" " + PESSOA_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n");
        sql.append(" " + PESSOA_NOME + " VARCHAR(100),\n");
        sql.append(" " + PESSOA_IDADE + " INTEGER,\n");
        sql.append(" " + PESSOA_ENDERECO + " VARCHAR(100),\n");
        sql.append(" " + PESSOA_TELEFONE + " VARCHAR(20)\n");
        sql.append(");");*/

        String sqlProduto = "CREATE TABLE " + PESSOA_TABELA + " (" +
                " " + PESSOA_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " " + PESSOA_NOME + " VARCHAR(100), " +
                " " + PESSOA_IDADE + " INTEGER, " +
                " " + PESSOA_ENDERECO + " VARCHAR(100), " +
                " " + PESSOA_TELEFONE + " VARCHAR(20) );";

        db.execSQL(sqlProduto);
    }

    @Override //VERIFICA SE A TABELA JÁ ESTÁ CRIADA
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = String.format("DROP TABLE IS EXISTS %s", PESSOA_TABELA);
        db.execSQL(sql);
        onCreate(db);
    }

    public long salvarPessoaDAO(Pessoa pessoa){
        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(PESSOA_NOME,pessoa.getNome());
        values.put(PESSOA_IDADE,pessoa.getIdade());
        values.put(PESSOA_ENDERECO,pessoa.getEndereco());
        values.put(PESSOA_TELEFONE,pessoa.getTelefone());

        retornoDB = getWritableDatabase().insert(PESSOA_TABELA, null, values);

        return retornoDB;
    }

    public ArrayList<Pessoa> selectAllPessoas(){
        String[] coluns = {PESSOA_ID, PESSOA_NOME, PESSOA_IDADE, PESSOA_ENDERECO, PESSOA_TELEFONE};

        Cursor cursor = getWritableDatabase().query(PESSOA_TABELA,coluns,null,null,null,null,"upper(nome)",null);

        ArrayList<Pessoa> listPessoa = new ArrayList<Pessoa>();

        while (cursor.moveToNext()){
            Pessoa pessoa = new Pessoa();

            pessoa.setId(cursor.getInt(0));
            pessoa.setNome(cursor.getString(1));
            pessoa.setIdade(cursor.getInt(2));
            pessoa.setEndereco(cursor.getString(3));
            pessoa.setTelefone(cursor.getString(4));

            listPessoa.add(pessoa);
        }

        return listPessoa;
    }
}
