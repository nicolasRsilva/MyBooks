package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;

import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.Livro;

public class CadastroActivity extends AppCompatActivity {

    Bitmap livroCapa;
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricao;

    //variavel final é variavel onde não pode mudar o valor dela
    private final int COD_REQ_GALERIA = 101;

    //variavel de acesso ao banco
    private MyBooksDataBase myBooksDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);

        //instancia do banco de dados
        myBooksDB = Room.databaseBuilder(getApplicationContext(), MyBooksDataBase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

    }

    public void abrirGaleria(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        //PEGAR APENAS IMG, É NÃO TODOS OS DOCUMENTOS
        intent.setType("image/*");

        //VOLTAR UM RESULTADO
        //VARIAVEL COM NUMERO INTEIRO, para saber oq retorna (qual image ele escolheu)
        startActivityForResult(intent.createChooser(intent, "Selecione uma imagem"), COD_REQ_GALERIA);

    }

    //onActivityResult - ELE vai mandar a variavel startActivityForResult para o onActivityResult, consigo verificar através da varialvel
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //se os números for iguais & o usuario selecionou alguma coisa
        if(COD_REQ_GALERIA == requestCode && resultCode == Activity.RESULT_OK){

            try{

                //variavel do tipo Input, pega oque tiver em data
                InputStream input = getContentResolver().openInputStream(data.getData());

                //converteu para bitmap - oque estamos usando no banco
                livroCapa = BitmapFactory.decodeStream(input);

                //passando a o bitmap para img - onde vai receber a foto
                //exibindo na tela
                imgLivroCapa.setImageBitmap(livroCapa);

            }catch(Exception ex){
                ex.printStackTrace();
            }

        }

    }

    public void salvarLivro(View view) {

        if(livroCapa == null || txtTitulo.getText().toString().equals("") || txtDescricao.getText().toString().equals("")){
            alert("ERRO", "Informe todos os dados");
        }else{
            alert("Cadastro", "Cadastro efetuado com sucesso");

            byte[]capa = Utils.toByteArray(livroCapa);

            String titulo = txtTitulo.getText().toString();

            String descricao = txtDescricao.getText().toString();

            Livro livro = new Livro(capa, titulo, descricao);

            //inserindo em livro que onde vai ser exibido
            myBooksDB.daoLivro().inserir(livro);
        }

    }

    public void alert(String titulo, String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(msg);

        alert.setPositiveButton("ok", new Dialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        alert.create().show();
    }

}
