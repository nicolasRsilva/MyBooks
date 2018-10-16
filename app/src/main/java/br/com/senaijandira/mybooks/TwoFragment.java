package br.com.senaijandira.mybooks;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.adapter.LivrosLidosAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.LivroLido;

public class TwoFragment extends Fragment {

    public static LivroLido[] livros;

    private MyBooksDataBase myBooksDataBase;
    ListView livroLido;

    LivrosLidosAdapter adapter;

    public TwoFragment() {
        //precisa de um construtor vazio
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //instancia do banco de dados
        myBooksDataBase = Room.databaseBuilder(getContext(),MyBooksDataBase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        //abrir tela
        View v  = inflater.inflate(R.layout.fragment_two, container, false);
        livroLido = v.findViewById(R.id.viewLivroLidos);

        adapter = new LivrosLidosAdapter(getContext(),myBooksDataBase);

        livroLido.setAdapter(adapter);


        // Inflate the layout for this fragment
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();

        livros = myBooksDataBase.daoLivrosLidos().livrosLidos();

        adapter.clear();

        adapter.addAll(livros);
    }

}