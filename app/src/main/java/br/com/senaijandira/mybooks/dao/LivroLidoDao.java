package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.senaijandira.mybooks.model.LivroLido;

@Dao
public interface LivroLidoDao {

    @Insert
    void inserir(LivroLido l);

    @Update
    void atualizar(LivroLido l);

    @Delete
    void deletar(LivroLido l);

    @Query("SELECT * FROM livrolido")
    LivroLido[] selecionarTodosLidos();

    //select para pegar os id da tabela
    @Query("SELECT livrolido.idLivro FROM livrolido, livro where livrolido.idLivro = :id")
    int idLivros(int id);

    //pega um livrolidos de acordo com id
    @Query("SELECT * FROM livrolido WHERE idLivro = :id")
    LivroLido pegarLivroLidos(int id);

    @Query("SELECT Id.*, l.titulo,l.descricao, l.capa from livrolido as Id, livro as l WHERE Id.idLivro = l.Id")
    LivroLido[]livrosLidos();
}


