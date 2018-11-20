package com.example.natanael.appgmmultimarcas.activitie;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.natanael.appgmmultimarcas.R;
import com.example.natanael.appgmmultimarcas.dao.PessoaDAO;
import com.example.natanael.appgmmultimarcas.model.Pessoa;

import java.util.ArrayList;

public class ClienteActivity extends AppCompatActivity {

    ListView listVisivel;
    FloatingActionButton btnNovoCadastro;
    Pessoa pessoa;
    PessoaDAO pessoaDAO;
    ArrayList<Pessoa> arrayListPessoa;
    ArrayAdapter<Pessoa> arrayAdapterPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        listVisivel = (ListView) findViewById(R.id.listPessoas);
        btnNovoCadastro = findViewById(R.id.btnNovoCad);
        registerForContextMenu(listVisivel);

        //AÇÃO BOTÃO NOVO CADASTRO '+' VAI PARA TELA FORMPESSOA PARA ADICIONAR UM NOVO USUARIO
        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ClienteActivity.this, FormPessoa.class);
                startActivity(intent);
            }
        });


        //ALTERAR UM USUARIO QUANDO CLICK CURTO -> ENVIA PARA A ACTIVITY FormPessoa (EDUTAR)
        listVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pessoa pessoaEnviada = (Pessoa) arrayAdapterPessoa.getItem(position);

                Intent intent = new Intent(ClienteActivity.this, FormPessoa.class);
                intent.putExtra("pessoa-enviada", pessoaEnviada);
                startActivity(intent);
            }
        });

        //EXCLUI UM USUARIO QUANDO CLICK LONGO NA LISTA --> EXCLUIR
        listVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pessoa = arrayAdapterPessoa.getItem(position);
                return false;
            }
        });
    }

    //----------------------------------------------------------------------------------------------
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
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_lista_cliente, menu);

        MenuItem item = menu.findItem(R.id.acao_pesquisar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(buscarTexto());

        return super.onCreateOptionsMenu(menu);
    }

    private SearchView.OnQueryTextListener buscarTexto() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(ClienteActivity.this,
                        ""+query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                carregaDadosLike();
                return false;
            }
        };
    }

    private  void carregaDadosLike(String nome){
        PessoaDAO pessoaDAO = new PessoaDAO(this);
        pessoa = pessoaDAO.listarProdutosLike(nome);
        //Log.i("teste", "tamanho: "+produtos.size());
        listView.setAdapter(new ProdutoBaseAdapter(this, produtos));
    }*/

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
