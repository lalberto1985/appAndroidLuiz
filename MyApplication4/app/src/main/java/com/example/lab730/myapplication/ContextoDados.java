//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.lab730.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class ContextoDados extends SQLiteOpenHelper {
    private static final String NOME_BD = "Agenda";
    private static final int VERSAO_BD = 2;
    private static final String LOG_TAG = "Agenda";
    private final Context contexto;

    public ContextoDados(Context context) {
        super(context, "Agenda", (CursorFactory)null, 2);
        this.contexto = context;
    }

    public void onCreate(SQLiteDatabase db) {
        String[] sql = this.contexto.getString(R.string.ContextoDados_onCreate).split("\n");
        db.beginTransaction();

        try {
            this.ExecutarComandosSQL(db, sql);
            db.setTransactionSuccessful();
        } catch (SQLException var7) {
            Log.e("Erro", var7.toString());
        } finally {
            db.endTransaction();
        }

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("Agenda", "Atualizando a base de dados da versÃ£o " + oldVersion + " para " + newVersion + ", que destruirÃ¡ todos os dados antigos");
        String[] sql = this.contexto.getString(R.string.ContextoDados_onUpgrade).split("\n");
        db.beginTransaction();

        try {
            this.ExecutarComandosSQL(db, sql);
            db.setTransactionSuccessful();
        } catch (SQLException var9) {
            Log.e("Erro", var9.toString());
            throw var9;
        } finally {
            db.endTransaction();
        }

        this.onCreate(db);
    }

    private void ExecutarComandosSQL(SQLiteDatabase db, String[] sql) {
        String[] var6 = sql;
        int var5 = sql.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String s = var6[var4];
            if(s.trim().length() > 0) {
                db.execSQL(s);
            }
        }

    }

    public ContextoDados.ContatosCursor RetornarContatos(ContextoDados.ContatosCursor.OrdenarPor ordenarPor) {
        String sql = "SELECT * FROM Contatos ORDER BY Nome " + (ordenarPor == ContextoDados.ContatosCursor.OrdenarPor.NomeCrescente?"ASC":"DESC");
        SQLiteDatabase bd = this.getReadableDatabase();
        ContextoDados.ContatosCursor cc = (ContatosCursor) bd.rawQueryWithFactory(new ContatosCursor.Factory(), sql, null, null);
        cc.moveToFirst();
        return cc;
    }

    public long InserirContato(String nome, String telefone, String endereco) {
        SQLiteDatabase db = this.getReadableDatabase();

        long var7;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("Nome", nome);
            initialValues.put("Telefone", telefone);
            initialValues.put("Endereco", endereco);
            var7 = db.insert("Contatos", (String)null, initialValues);
        } finally {
            db.close();
        }

        return var7;
    }

    public static class ContatosCursor extends SQLiteCursor {
        private static final String CONSULTA = "SELECT * FROM Contatos ORDER BY Nome ";

        private ContatosCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
            super(db, driver, editTable, query);
        }

        public long getID() {
            return this.getLong(this.getColumnIndexOrThrow("ID"));
        }

        public String getNome() {
            return this.getString(this.getColumnIndexOrThrow("Nome"));
        }

        public String getEndereco() {
            return this.getString(this.getColumnIndexOrThrow("Endereco"));
        }

        public String getTelefone() {
            return this.getString(this.getColumnIndexOrThrow("Telefone"));
        }

        private static class Factory implements CursorFactory {
            private Factory() {
            }

            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
                return new ContatosCursor(db, driver, editTable, query);

            }
        }

        public static enum OrdenarPor {
            NomeCrescente,
            NomeDecrescente;

            private OrdenarPor() {
            }
        }
    }
}
