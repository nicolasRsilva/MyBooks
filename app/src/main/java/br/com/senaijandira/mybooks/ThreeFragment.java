package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.adapter.LivroLerAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.LivroLer;

public class ThreeFragment extends Fragment{

    public static LivroLer[] livros;

    private MyBooksDataBase myBooksDataBase;
    ListView livroLer;

    LivroLerAdapter adapter;

    public ThreeFragment() {
        // contrutor vazio
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myBooksDataBase = Room.databaseBuilder(getContext(),MyBooksDataBase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        View v = inflater.inflate(R.layout.fragment_three, container, false);
        livroLer = v.findViewById(R.id.viewLivroLer);

        adapter = new LivroLerAdapter(getContext(), myBooksDataBase);

        livroLer.setAdapter(adapter);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

        livros = myBooksDataBase.daoLivroLer().livrosLeer();

        adapter.clear();

        adapter.addAll(livros);
    }
}
