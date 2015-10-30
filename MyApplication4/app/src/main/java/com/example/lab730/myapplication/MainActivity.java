package com.example.lab730.myapplication;



import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    Button btnSalvar;
    Button btnCancelar;
    Button btnNovo;
    EditText txtNome;
    EditText txtEndereco;
    EditText txtTelefone;

    public MainActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.CarregarInterfaceListagem();
    }

    public void CarregarInterfaceListagem() {
        this.setContentView(R.layout.activity_main);
        this.btnNovo = (Button)this.findViewById(R.id.btnNovo);
        this.btnNovo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.CarregarInterfaceCadastro();
            }
        });
        this.CarregarLista(this);
    }

    public void CarregarInterfaceCadastro() {
        this.setContentView(R.layout.cadastro);
        this.btnCancelar = (Button)this.findViewById(R.id.btnCancelar);
        this.btnCancelar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.CarregarInterfaceListagem();
            }
        });
        this.txtNome = (EditText)this.findViewById(R.id.txtNome);
        this.txtEndereco = (EditText)this.findViewById(R.id.txtEndereco);
        this.txtTelefone = (EditText)this.findViewById(R.id.txtTelefone);
        this.btnSalvar = (Button)this.findViewById(R.id.btnSalvar);
        this.btnSalvar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.SalvarCadastro();
            }
        });
    }

    public void SalvarCadastro() {
        ContextoDados db = new ContextoDados(this);

        db.InserirContato(this.txtNome.getText().toString(), this.txtTelefone.getText().toString(), this.txtEndereco.getText().toString());
        this.setContentView(R.layout.activity_main);
        this.CarregarLista(this);
    }

    public void CarregarLista(Context c) {
        ContextoDados db = new ContextoDados(c);
        ContextoDados.ContatosCursor cursor = db.RetornarContatos(ContextoDados.ContatosCursor.OrdenarPor.NomeCrescente);

        for(int i = 0; i < cursor.getCount(); ++i) {
            cursor.moveToPosition(i);
            this.ImprimirLinha(cursor.getNome(), cursor.getTelefone(), cursor.getEndereco());
        }

    }

    public void ImprimirLinha(String nome, String telefone, String endereco) {
        TextView tv = (TextView)this.findViewById(R.id.listaContatos);
        if(tv.getText().toString().equalsIgnoreCase("Nenhum contato cadastrado.")) {
            tv.setText("");
        }

        tv.setText(tv.getText() + "\r\n" + "Nome: " + nome + "\n " + "Telefone: " + telefone + "\n" + "Endereço: " + endereco);
    }
}
