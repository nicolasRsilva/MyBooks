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
import android.widget.Toast;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.LivroLer;
import br.com.senaijandira.mybooks.model.LivroLido;

public class LivroLerAdapter extends ArrayAdapter<LivroLer> {

    ImageView menu;

    //banco de dados
    private MyBooksDataBase myBooksDataBase;

    public LivroLerAdapter(Context ctx,MyBooksDataBase myBooksDataBase){
        super(ctx, 0, new ArrayList<LivroLer>());

        //BANCO
        this.myBooksDataBase = myBooksDataBase;

    }

    //metodo para deletar livro
    public void deletarLivro(LivroLer livroler){

        //deletar livro
        myBooksDataBase.daoLivroLer().deletar(livroler);

        //remover livro da lista
        remove(livroler);
    }

    //metodo para enviar livro para livrolidos
    private void livrosLidos(LivroLer livro){

        LivroLido livrol = new LivroLido();

        //setando o valor do id livro lid
        livrol.setIdLivro(livro.getIdLivro());

        myBooksDataBase.daoLivrosLidos().inserir(livrol);
        Toast.makeText(getContext(),"O livro enviado com sucesso", Toast.LENGTH_SHORT).show();
        myBooksDataBase.daoLivroLer().deletar(livro);

    }


    public void menuPopup(final View v, final LivroLer livro, final int position){

        final PopupMenu popupMenu = new PopupMenu(v.getContext(), v);

        popupMenu.getMenuInflater().inflate(R.menu.poupup_menu_ler, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.menulivrosLidos){
                    livrosLidos(livro);
                }
                else if(item.getItemId() == R.id.menuExcluir){
                    deletarLivro(livro);
                }

                return false;
            }
        });

        popupMenu.show();
    }



    @NonNull
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout,parent,false);
        }
        final LivroLer livro = getItem(position);

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
