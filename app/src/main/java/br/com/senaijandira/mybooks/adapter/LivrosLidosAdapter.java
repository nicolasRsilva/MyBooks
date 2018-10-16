package br.com.senaijandira.mybooks.adapter;

import android.content.Context;
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

import java.util.ArrayList;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.LivroLido;

public class LivrosLidosAdapter extends ArrayAdapter<LivroLido>{

    ImageView menu;

    //banco de dados
    private MyBooksDataBase myBooksDataBase;

    //colocar livro no array
    public LivrosLidosAdapter(Context ctx,MyBooksDataBase myBooksDataBase){
        super(ctx,0,new ArrayList<LivroLido>());

        //banco
        this.myBooksDataBase = myBooksDataBase;
    }

    //metodo para deletar livro
    private  void deletarLivro(LivroLido livrolido){

        //deletar livro
        myBooksDataBase.daoLivrosLidos().deletar(livrolido);

        //remover livro da lista
        remove(livrolido);
    }


    public void menuPopup(final View v, final LivroLido livro, final int position){

        final PopupMenu popupMenu = new PopupMenu(v.getContext(), v);

        popupMenu.getMenuInflater().inflate(R.menu.poupup_menu_lidos, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.menuEditar){

                }else if(item.getItemId() == R.id.menuExcluir){
                    deletarLivro(livro);
                }

                return false;
            }
        });

        popupMenu.show();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            //pega os dados que será carregado e coloca dentro da variavel v.
            v = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout,parent,false);
        }
        final LivroLido livro = getItem(position);

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

}
