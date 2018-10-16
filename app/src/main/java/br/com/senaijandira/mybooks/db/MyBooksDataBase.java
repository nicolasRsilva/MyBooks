package br.com.senaijandira.mybooks.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.senaijandira.mybooks.dao.LivroDao;
import br.com.senaijandira.mybooks.dao.LivroLerDao;
import br.com.senaijandira.mybooks.dao.LivroLidoDao;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivroLer;
import br.com.senaijandira.mybooks.model.LivroLido;

//quando tiver outra versão, tabelas, é só mudar a versão e ele faz tudo
@Database(entities = {Livro.class, LivroLido.class, LivroLer.class} , version = 13)
public abstract class MyBooksDataBase extends RoomDatabase{

    public abstract LivroDao daoLivro();

    public abstract LivroLidoDao daoLivrosLidos();

    public abstract LivroLerDao daoLivroLer();

}
