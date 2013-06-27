package com.mobileescort.mobileescort.banco;

import android.content.Context;

/**
 * Repositório para Rotas que utiliza o SQLite internamente
 *
 *
 * @author Dimitrius
 *
 */
public class RepositorioMobileEscortScript extends RepositorioMobileEscort {

	// Script para fazer drop na tabela
	private static final String[] SCRIPT_DATABASE_DELETE = new String[] {"DROP TABLE IF EXISTS rota_usuario;",
			"DROP TABLE IF EXISTS rota;",
			"DROP TABLE IF EXISTS usuario;",
			"DROP TABLE IF EXISTS viagem;"};

	// Cria a tabela com o "_id" sequencial
	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
			"create table usuario ( id_usuario integer primary key, celular text, cidade text, email text, endereco text, nome text, latitude double, longitude double, password text, perfil text, registro text );",
			"create table rota ( id_rota integer primary key, descricao text, id_usuario integer);",
			"create table rota_usuario ( id_rota integer , id_usuario integer, PRIMARY KEY(id_rota, id_usuario), FOREIGN KEY (id_rota) REFERENCES rota(id_rota),FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) );",
			"create table viagem ( id_viagem integer primary key autoincrement, id_rota integer not null, id_status text not null,latitude double, longitude double);"};

	// Nome do banco
	private static final String NOME_BANCO = "mobileescort";

	// Controle de versão
	private static final int VERSAO_BANCO = 2;

	// Nome da tabela
	public static final String TABELA_USUARIO = "usuario";
	public static final String TABELA_ROTA = "rota";
	public static final String TABELA_ROTA_USUARIO = "rota_usuario";
	public static final String TABELA_VIAGEM = "viagem";

	// Classe utilitária para abrir, criar, e atualizar o banco de dados
	private SQLiteHelper dbHelper;

	// Cria o banco de dados com um script SQL
	public RepositorioMobileEscortScript(Context ctx) {
		// Criar utilizando um script SQL
		dbHelper = new SQLiteHelper(ctx, RepositorioMobileEscortScript.NOME_BANCO, RepositorioMobileEscortScript.VERSAO_BANCO,
				RepositorioMobileEscortScript.SCRIPT_DATABASE_CREATE, RepositorioMobileEscortScript.SCRIPT_DATABASE_DELETE);

		// abre o banco no modo escrita para poder alterar também
		db = dbHelper.getWritableDatabase();
	}

	// Fecha o banco
	@Override
	public void fechar() {
		super.fechar();
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
}
