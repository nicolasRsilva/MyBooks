package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.senaijandira.mybooks.model.LivroLer;

@Dao
public interface LivroLerDao {

    @Insert
    void inserir(LivroLer l);

    @Update
    void atualizar(LivroLer l);

    @Delete
    void deletar(LivroLer l);

    @Query("SELECT * FROM livroler")
    LivroLer[] selecionarLivrosLer();

    //select para pegar os id da tabela
    @Query("SELECT livroler.idLivro FROM livroler, livro where livroler.idLivro = :id")
    int idLivros(int id);

    //pega um livroler de acordo com id
    @Query("SELECT * FROM livroler WHERE idLivro = :id")
    LivroLer pegarLivroLer(int id);

    //select para transferir livro
    @Query("SELECT Id.*, l.titulo,l.descricao, l.capa from LivroLer as Id, livro as l WHERE Id.idLivro = l.Id")
    LivroLer[] livrosLeer();

}
