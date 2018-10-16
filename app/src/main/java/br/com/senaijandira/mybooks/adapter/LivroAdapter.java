package br.com.senaijandira.mybooks.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.senaijandira.mybooks.AtualizarActivity;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivroLer;
import br.com.senaijandira.mybooks.model.LivroLido;

//chama o construtor que esta lá dentro
public class LivroAdapter extends ArrayAdapter<Livro> {

    ImageView menu;
    private List<Livro> livroAtualizar;

    //banco de dados
    private MyBooksDataBase myBooksDb;

    //colocar livro no array
    public  LivroAdapter(Context ctx, MyBooksDataBase myBooksDb){
        super(ctx,0,new ArrayList<Livro>());

        //banco
        this.myBooksDb = myBooksDb;
    }

    //metodo para deletar livro
    private  void deletarLivro(Livro livro){
        //deletar livro
        myBooksDb.daoLivro().deletar(livro);

        //remover livro da lista
        remove(livro);
    }

    //metodo para enviar livro para livrolidos
    public void livrosLidos(Livro livro){

        Boolean enviar = true;

        LivroLido livrol = new LivroLido();

         //setando o valor do id livro lid
        livrol.setIdLivro(livro.getId());

        //saber se o livro já foi enviado
        if(myBooksDb.daoLivrosLidos().idLivros(livro.getId()) == livro.getId()){
            enviar = false;
            Toast.makeText(getContext(),"O livro já se encontra em livros lidos", Toast.LENGTH_SHORT).show();
        }
        //verificar se o livro esta em outra tela
        else if(myBooksDb.daoLivroLer().idLivros(livro.getId()) == livro.getId()){
            enviar = false;
            tranferirLidos(livro.getId(),livrol);
        }

        if(enviar == true){
            myBooksDb.daoLivrosLidos().inserir(livrol);
        }

    }

    //funão que vai excluir LivroLido e transferir para livros lidos
    private void tranferirLidos(final int livro, final LivroLido livrol){

        String msg = "O livro já se encontra em Livros Lidos";

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Deseja transferir para Meus Livros?");
        alert.setMessage(msg);
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exclui o livro da outra tabela
                int a = myBooksDb.daoLivroLer().idLivros(livro);
                //deleta o livroler que foi pedido.
                myBooksDb.daoLivroLer().deletar(myBooksDb.daoLivroLer().pegarLivroLer(a));
                //insere o livro
                myBooksDb.daoLivrosLidos().inserir(livrol);


            }
        });
        alert.create().show();
    }

    //metodo para enviar livro para livrosLer
    public void livrosLer(Livro livro){

        Boolean enviar = true;

        LivroLer livroLer = new LivroLer();

        //setando o valor do id livroler
        livroLer.setIdLivro(livro.getId());

        //for para saber se o livro já foi enviado para livros lidos
        if(myBooksDb.daoLivroLer().idLivros(livro.getId()) == livro.getId()){
            enviar = false;
            Toast.makeText(getContext(),"O livro já se encontra em meus livros", Toast.LENGTH_SHORT).show();
        }
        //verificar se o livro esta em outra tela
        else if(myBooksDb.daoLivrosLidos().idLivros(livro.getId()) == livro.getId()){
            enviar = false;
            tranferirLer(livro.getId(),livroLer);
        }

        if(enviar == true){
            myBooksDb.daoLivroLer().inserir(livroLer);
        }

    }

    //funão que vai excluir LivroLido e transferir para livros lidos
    private void tranferirLer(final int livro, final LivroLer livrol){

        String msg = "O livro já se encontra em Meus Livros";

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Deseja tranferir para Livros Lidos?");
        alert.setMessage(msg);
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exclui o livro da outra tabela

                int a = myBooksDb.daoLivrosLidos().idLivros(livro);
                //deleta o livroler que foi pedido.
                myBooksDb.daoLivrosLidos().deletar(myBooksDb.daoLivrosLidos().pegarLivroLidos(a));
                //insere o livro
                myBooksDb.daoLivroLer().inserir(livrol);


            }
        });
        alert.create().show();
    }


    private void abrirAtualizar(Livro l,View v, int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("Id", l.getId());

        Intent i = new Intent(v.getContext(), AtualizarActivity.class);
        i.putExtras(bundle);

        getContext().startActivity(i);
    }



    //MENU
    public void menuPopup(final View v, final Livro livro, final int position){

        final PopupMenu popupMenu = new PopupMenu(v.getContext(), v);

        popupMenu.getMenuInflater().inflate(R.menu.poupup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.menulivrosLidos){
                    livrosLidos(livro);
                }
                else if(item.getItemId() == R.id.menuMeusLivros){
                    livrosLer(livro);
                }
                else if(item.getItemId() == R.id.menuEditar){
                    abrirAtualizar(livro,v,position);
                }else if(item.getItemId() == R.id.menuExcluir){
                    deletarLivro(livro);
                }

                return false;
            }
        });

        popupMenu.show();
    }



    //pega os dados que será carregado e coloca dentro da variavel v.
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            //pega os dados que será carregado e coloca dentro da variavel v.
            v = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout,parent,false);
        }

        final Livro livro = getItem(position);

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);

        //Menu
        menu = v.findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuPopup(v, livro, position);
            }
        });

        //Setando a imagem
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));

        //Setando o titulo do livro
        txtLivroTitulo.setText(livro.getTitulo());

        //Setando a descrição
        txtLivroDescricao.setText(livro.getDescricao());

        return v;

    }

    public void alert(String titulo, String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(titulo);
        alert.setMessage(msg);
        alert.setCancelable(false);
        alert.setPositiveButton("Próximo", new Dialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        alert.create().show();

    }
}

