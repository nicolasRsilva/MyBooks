package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.adapter.LivroAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.Livro;


public class OneFragment extends Fragment{

    ListView lstViewLivros;

    public static Livro[] livros;

    //variavel de acesso ao banco
    private MyBooksDataBase myBooksDB;

    //Adapter para criar a lista de livros
    LivroAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //instancia do banco de dados
        myBooksDB = Room.databaseBuilder(getContext(),MyBooksDataBase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        View v = inflater.inflate(R.layout.fragment_one, container, false);
        lstViewLivros = v.findViewById(R.id.lstViewLivros);

        //criar o adapter
        adapter = new LivroAdapter(getContext(),myBooksDB);

        //setando o adapter
        lstViewLivros.setAdapter(adapter);

        return v;
    }

    public OneFragment() {

    }


    @Override
    public void onResume() {
        super.onResume();

        //Fazer o select no banco
        // ele vai fazer o select no banco, pega o banco + selecionarTodos que é o nosso select
        //livro pega tudo do banco
        livros = myBooksDB.daoLivro().selecionarTodos();

        //limpamdo a listView
        adapter.clear();

        //Adicionando os livros a lista
        adapter.addAll(livros);

    }


}
