package com.example.natanael.appgmmultimarcas.activitie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.natanael.appgmmultimarcas.R;
import com.example.natanael.appgmmultimarcas.dao.PessoaDAO;
import com.example.natanael.appgmmultimarcas.model.Pessoa;

import java.util.ArrayList;

public class ClienteActivity extends AppCompatActivity {

    ListView listVisivel;
    Button btnNovoCadastro;
    Pessoa pessoa;
    PessoaDAO pessoaDAO;
    ArrayList<Pessoa> arrayListPessoa;
    ArrayAdapter<Pessoa> arrayAdapterPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        listVisivel = (ListView) findViewById(R.id.listPessoas);
        btnNovoCadastro = (Button) findViewById(R.id.btnNovoCadastro);
        registerForContextMenu(listVisivel);

        //VAI PARA TELA FORMPESSOA PARA ADICIONAR UM NOVO USUARIO
        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteActivity.this, FormPessoa.class);
                startActivity(intent);
            }
        });

        //ALTERAR UM USUARIO QUANDO SELECIONADO -> ENVIA PARA O FORMPESSOA
        listVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pessoa pessoaEnviada = (Pessoa) arrayAdapterPessoa.getItem(position);

                Intent intent = new Intent(ClienteActivity.this, FormPessoa.class);
                intent.putExtra("pessoa-enviada", pessoaEnviada);
                startActivity(intent);
            }
        });

        //EXCLUI UM USUARIO QUANDO PRECIONADO NA LISTA --> EXCLUIR
        listVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pessoa = arrayAdapterPessoa.getItem(position);
                return false;
            }
        });
    }

    //METODO PARA PREENCHER(ATUALIZAR) A LISTA
    public void populaLista(){
        pessoaDAO = new PessoaDAO(ClienteActivity.this);

        arrayListPessoa = pessoaDAO.selectAllPessoas();
        pessoaDAO.close();

        if (listVisivel != null){
            arrayAdapterPessoa = new ArrayAdapter<Pessoa>(ClienteActivity.this,
                    android.R.layout.simple_list_item_1, arrayListPessoa);

            listVisivel.setAdapter(arrayAdapterPessoa);

        }
    }

    protected void onResume(){
        super.onResume();
        populaLista();
    }

    //Exclui Usuario do Sistema
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Delete Registro");

        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long retornoDB;
                pessoaDAO = new PessoaDAO(ClienteActivity.this);
                retornoDB = pessoaDAO.excluirPessoaDAO(pessoa);
                pessoaDAO.close();

                if (retornoDB == -1){
                    alert("Erro ao Excluir");
                }else {
                    alert("Cliente Excluido com sucesso!");
                }

                populaLista();

                return false;
            }
        });
    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
