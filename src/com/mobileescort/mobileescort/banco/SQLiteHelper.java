package com.mobileescort.mobileescort.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Implementacao de SQLiteOpenHelper
 * 
 * Classe utilit�ria para abrir, criar, e atualizar o banco de dados
 * 
 * @author Dimitrius 
 */
class SQLiteHelper extends SQLiteOpenHelper {

	private static final String TAG = "SQLiteHelper";

	private String[] scriptSQLCreate;
	private String[] scriptSQLDelete;

	/**
	 * Cria uma inst�ncia de SQLiteHelper
	 * 
	 * @param context
	 * @param nomeBanco nome do banco de dados
	 * @param versaoBanco vers�o do banco de dados (se for diferente � para atualizar)
	 * @param scriptSQLCreate SQL com o create table..
	 * @param scriptSQLDelete SQL com o drop table...
	 */
	SQLiteHelper(Context context, String nomeBanco, int versaoBanco, String[] scriptSQLCreate, String[] scriptSQLDelete) {
		super(context, nomeBanco, null, versaoBanco);
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
	}

	@Override
	// Criar novo banco...
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "Criando banco com sql");
		int qtdeScripts = scriptSQLCreate.length;

		// Executa cada sql passado como par�metro
		for (int i = 0; i < qtdeScripts; i++) {
			String sql = scriptSQLCreate[i];
			Log.i(TAG, sql);
			// Cria o banco de dados executando o script de cria��o
			db.execSQL(sql);
		}
	}

	@Override
	// Mudou a vers�o...
	public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
		Log.w(TAG, "Atualizando da vers�o " + versaoAntiga + " para " + novaVersao + ". Todos os registros ser�o deletados.");
		int qtdeScripts = scriptSQLDelete.length;

		// Executa cada sql passado como par�metro
		for (int i = 0; i < qtdeScripts; i++) {
			String sql = scriptSQLDelete[i];
			Log.i(TAG, sql);
			// Deleta as tabelas...
			db.execSQL(sql);
		}
		// Cria novamente...
		onCreate(db);
	}
}