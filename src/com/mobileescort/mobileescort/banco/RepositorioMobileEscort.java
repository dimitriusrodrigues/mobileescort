package com.mobileescort.mobileescort.banco;

import java.util.ArrayList;
import java.util.List;

import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.model.Viagem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Repositório para rotas que utiliza o SQLite internamente
 *
 * @author Dimitrius
 *
 */
public class RepositorioMobileEscort {

	private static final String TAG = "RepositorioMobileEscort";

	// Nome do banco
	private static final String NOME_BANCO = "mobileescort";

	// Nome da tabela
	public static final String TABELA_USUARIO = "usuario";
	public static final String TABELA_ROTA = "rota";
	public static final String TABELA_ROTA_USUARIO = "rota_usuario";
	public static final String TABELA_VIAGEM = "viagem";
	
	protected SQLiteDatabase db;

	public RepositorioMobileEscort(Context ctx) {
		// Abre o banco de dados já existente
		db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
	}

	protected RepositorioMobileEscort() {
		// Apenas para criar uma subclasse...
	}

	// Salva o usuario, insere um novo ou atualiza
	public long salvarUsuario(Usuario usuario) {
		int idUsuario = usuario.getId_usuario();
		long id = 0;
		if (buscarUsuarioPorId(idUsuario)) {
			atualizarUsuario(usuario);
		} else {
			// Insere novo
			id = inserirUsuario(usuario);
		}

		return id;
	}
	
	// Salva a rota, insere uma nova ou atualiza
	public long salvarRota(Rota rota) {
		int idRota = rota.getId_rota();
		long id = 0 ;
		if (buscarRotaPorId(idRota)) {
			atualizarRota(rota);
		} else {
			// Insere novo
			id = inserirRota(rota);
		}
		return id;
	}

	// Salva a rota, insere uma nova ou atualiza
	public int salvarViagem(Viagem viagem) {
		int idViagem = viagem.getId_viagem();
		if (idViagem != 0) {
			atualizarViagem(viagem);
		} else {
			// Insere novo
			idViagem = inserirViagem(viagem);
		}
		return idViagem;
	}
	

	// Insere um novo usuario
	private long inserirUsuario(Usuario usuario) {
		ContentValues values = new ContentValues();

		values.put(Usuario.KEY_ID , usuario.getId_usuario() );
		values.put(Usuario.KEY_REGISTRO, usuario.getRegistro());
		values.put(Usuario.KEY_NAME, usuario.getNome());
		values.put(Usuario.KEY_EMAIL, usuario.getEmail());
		values.put(Usuario.KEY_CELULAR, usuario.getCelular());
		values.put(Usuario.KEY_PASSWORD, usuario.getPassword());
		values.put(Usuario.KEY_PERFIL, usuario.getPerfil());
		values.put(Usuario.KEY_CIDADE, usuario.getCidade());
		values.put(Usuario.KEY_ENDERECO, usuario.getEndereco());
		values.put(Usuario.KEY_LATITUDE, usuario.getLatitude());
		values.put(Usuario.KEY_LONGITUDE, usuario.getLongitude());

		long id = inserirUsuario(values);
		return id;
	}

	// Insere um novo usuario
	private long inserirUsuario(ContentValues valores) {
		long id = db.insert(TABELA_USUARIO , "", valores);
		return id;
	}
	
	// Insere uma viagem
	private int inserirViagem(Viagem viagem) {
		ContentValues values = new ContentValues();

		values.put(Viagem.KEY_ID_ROTA , viagem.getId_rota());
		values.put(Viagem.KEY_STATUS, viagem.getId_status());
		values.put(Viagem.KEY_LATITUDE, viagem.getLatitude());
		values.put(Viagem.KEY_LONGITUDE, viagem.getLongitude());

		int id = inserirViagem(values);
		return id;
	}
	
	// Insere uma viagem
	private int inserirViagem(ContentValues valores) {
		long id = db.insert(TABELA_VIAGEM , "", valores);
		return (int) id;
			
	}
	// Insere uma nova rota
	private long inserirRota(Rota rota) {
		ContentValues valuesRota = new ContentValues();

		valuesRota.put(Rota.KEY_ID , rota.getId_rota() );
		valuesRota.put(Rota.KEY_DESCRICAO, rota.getDescricao());
		valuesRota.put(Rota.KEY_MOTORISTA, rota.getMotorista().getId_usuario());
		deletarRota(rota.getId_rota());
		long id = inserirRota(valuesRota);
		
		if (id > 0 ) {
			
			for (int i = 0; i < rota.getUsuarios().size(); i++) {
				Usuario usuario = new Usuario();
				usuario = rota.getUsuarios().get(i);
				salvarUsuario(usuario);
				ContentValues valuesRotaUsuario = new ContentValues();
				valuesRotaUsuario.put(Rota.KEY_ID , rota.getId_rota() );
				valuesRotaUsuario.put(Usuario.KEY_ID, usuario.getId_usuario());
				
				inserirRotaUsuario(valuesRotaUsuario);
			}
		}
		return id;
	}

	// Insere uma nova rota
	private long inserirRota(ContentValues valores) {
		long id = db.insert(TABELA_ROTA , "", valores);
		return id;
	}
	
	// Insere os usuarios de uma rota 
	private long inserirRotaUsuario(ContentValues valores) {
		long id = db.insert(TABELA_ROTA_USUARIO, "", valores);
		return id;
	}

	// Atualiza o usuario no banco. O id do usuario é utilizado.
	private long atualizarUsuario(Usuario usuario) {
		ContentValues values = new ContentValues();

		values.put(Usuario.KEY_ID , usuario.getId_usuario() );
		values.put(Usuario.KEY_REGISTRO, usuario.getRegistro());
		values.put(Usuario.KEY_NAME, usuario.getNome());
		values.put(Usuario.KEY_EMAIL, usuario.getEmail());
		values.put(Usuario.KEY_CELULAR, usuario.getCelular());
		values.put(Usuario.KEY_PASSWORD, usuario.getPassword());
		values.put(Usuario.KEY_PERFIL, usuario.getPerfil());
		values.put(Usuario.KEY_CIDADE, usuario.getCidade());
		values.put(Usuario.KEY_ENDERECO, usuario.getEndereco());
		values.put(Usuario.KEY_LATITUDE, usuario.getLatitude());
		values.put(Usuario.KEY_LONGITUDE, usuario.getLongitude());


		String _id = String.valueOf(usuario.getId_usuario());

		String where = Usuario.KEY_ID + "=?";
		String[] whereArgs = new String[] { _id };

		long count = atualizarUsuario(values, where, whereArgs);

		return count;
	}

	// Atualiza o usuario com os valores abaixo
	// A cláusula where é utilizada para identificar o usuario a ser atualizado
	private long atualizarUsuario(ContentValues valores, String where, String[] whereArgs) {
		long count = db.update(TABELA_USUARIO, valores, where, whereArgs);
		Log.i(TAG, "Atualizou [" + count + "] registros");
		return count;
	}


	// Atualiza viagem. 
	private long atualizarViagem(Viagem viagem) {
		ContentValues values = new ContentValues();

		values.put(Viagem.KEY_ID , viagem.getId_viagem());
		values.put(Viagem.KEY_ID_ROTA , viagem.getId_rota() );
		values.put(Viagem.KEY_STATUS, viagem.getId_status());
		values.put(Viagem.KEY_LATITUDE, viagem.getLatitude());
		values.put(Viagem.KEY_LONGITUDE, viagem.getLongitude());


		String _id = String.valueOf(viagem.getId_viagem());

		String where = Viagem.KEY_ID + "=?";
		String[] whereArgs = new String[] { _id };

		long count = atualizarViagem(values, where, whereArgs);

		return count;
	}

	// Atualiza a viagem
	// A cláusula where é utilizada para identificar o usuario a ser atualizado
	private long atualizarViagem(ContentValues valores, String where, String[] whereArgs) {
		long count = db.update(TABELA_VIAGEM, valores, where, whereArgs);
		Log.i(TAG, "Atualizou [" + count + "] registros");
		return count;
	}
	
	// Atualiza a rota no banco. O id da rota é utilizado.
	private long atualizarRota(Rota rota) {
		ContentValues values = new ContentValues();

		values.put(Rota.KEY_ID , rota.getId_rota() );
		values.put(Rota.KEY_DESCRICAO, rota.getDescricao());
		values.put(Rota.KEY_MOTORISTA, rota.getMotorista().getId_usuario());

		String _id = String.valueOf(rota.getId_rota());

		String where = Rota.KEY_ID + "=?";
		String[] whereArgs = new String[] { _id };
		//deletarRota(rota.getId_rota());
		long count = atualizarRota(values, where, whereArgs);
		
		if (count > 0 ) {
			int id_usuarioRota;
			for (int i = 0; i < rota.getUsuarios().size(); i++) {
				salvarUsuario(rota.getUsuarios().get(i));
				id_usuarioRota = rota.getUsuarios().get(i).getId_usuario();
				
				ContentValues valuesRotaUsuario = new ContentValues();
				valuesRotaUsuario.put(Rota.KEY_ID , rota.getId_rota());
				valuesRotaUsuario.put(Usuario.KEY_ID, id_usuarioRota);
				
				if (!buscarRotaUsuario(rota.getId_rota(), id_usuarioRota)) {
					inserirRotaUsuario(valuesRotaUsuario);	
				} else {
					String _idRota = String.valueOf(rota.getId_rota());
					String _idUsuario = String.valueOf(id_usuarioRota);
					String whereRotaUsuario = Rota.KEY_ID + "=?" + " and " + Usuario.KEY_ID + "=?";
					String[] whereArgsRotaUsuario = new String[] { _idRota, _idUsuario };
					atualizarRotaUsuario(valuesRotaUsuario, whereRotaUsuario , whereArgsRotaUsuario);
				}
				
			}
		}

		
		return count;
	}

	// Atualiza a rota com os valores abaixo
	// A cláusula where é utilizada para identificar a rota a ser atualizado
	private long atualizarRota(ContentValues valores, String where, String[] whereArgs) {
		long count = db.update(TABELA_ROTA, valores, where, whereArgs);
		Log.i(TAG, "Atualizou [" + count + "] registros");
		return count;
	}

	// Atualiza a rota com os valores abaixo
	// A cláusula where é utilizada para identificar a rota a ser atualizado
	private long atualizarRotaUsuario(ContentValues valores, String where, String[] whereArgs) {
		long count = db.update(TABELA_ROTA_USUARIO, valores, where, whereArgs);
		Log.i(TAG, "Atualizou [" + count + "] registros");
		return count;
	}
	
	
	// Deleta o usuario com o id fornecido
	public long deletarUsuario(int id) {
		String where = Usuario.KEY_ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };

		long count = deletarUsuario(where, whereArgs);

		return count;
	}

	// Deleta o usuario com os argumentos fornecidos
	private long deletarUsuario(String where, String[] whereArgs) {
		long count = db.delete(TABELA_USUARIO, where, whereArgs);
		Log.i(TAG, "Deletou [" + count + "] registros");
		return count;
	}

	// Deleta a viagem com o id fornecido
	public int deletarViagem(int id) {
		String where = Viagem.KEY_ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };

		int count = deletarViagem(where, whereArgs);

		return count;
	}

	// Deleta a viagem com os argumentos fornecidos
	private int deletarViagem(String where, String[] whereArgs) {
		long count = db.delete(TABELA_VIAGEM, where, whereArgs);
		Log.i(TAG, "Deletou [" + count + "] registros");
		return (int)count;
	}
	
	// Deleta a rota com o id fornecido
	public long deletarRota(int id) {
		String where = Rota.KEY_ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };

		long count = deletarRota(where, whereArgs);

		return count;
	}

	// Deleta a rota com os argumentos fornecidos
	private long deletarRota(String where, String[] whereArgs) {
		
		long count = db.delete(TABELA_ROTA_USUARIO, where, whereArgs);
		count += db.delete(TABELA_ROTA, where, whereArgs);
		Log.i(TAG, "Deletou [" + count + "] registros");
		return count;
	}

	// Busca o usuario pelo id
	public Usuario buscarUsuario(int id) {
		// select * from usuario where _id=?
		Cursor mCursor = db.query(true, TABELA_USUARIO, Usuario.colunas, Usuario.KEY_ID + "=" + id, null, null, null, null, null);

		if (mCursor.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			mCursor.moveToFirst();

			Usuario usuario = new Usuario();

			// Lê os dados
			usuario.setId_usuario( mCursor.getInt(mCursor.getColumnIndex(Usuario.KEY_ID)) );
			usuario.setRegistro(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_REGISTRO)));
			usuario.setNome(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_NAME)) );
			usuario.setEmail(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_EMAIL)));
			usuario.setCelular(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_CELULAR)));
			usuario.setPassword(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_PASSWORD)));
			usuario.setPerfil(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_PERFIL)));
			usuario.setCidade(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_CIDADE)));
			usuario.setEndereco(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_ENDERECO)));
			usuario.setLatitude(mCursor.getDouble(mCursor.getColumnIndex(Usuario.KEY_LATITUDE)));
			usuario.setLongitude(mCursor.getDouble(mCursor.getColumnIndex(Usuario.KEY_LONGITUDE)));

			return usuario;
		}

		return null;
	}

	// Busca Viagem pela rota
	public Viagem buscarViagem(int id) {
		// select * from usuario where _id=?
		Cursor mCursor = db.query(true, TABELA_VIAGEM, Viagem.colunas, Viagem.KEY_ID_ROTA + "=" + id, null, null, null, null, null);

		if (mCursor.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			mCursor.moveToFirst();

			Viagem viagem = new Viagem();

			// Lê os dados
			viagem.setId_viagem(mCursor.getInt(mCursor.getColumnIndex(Viagem.KEY_ID)) );
			viagem.setId_rota(mCursor.getInt(mCursor.getColumnIndex(Viagem.KEY_ID_ROTA)));
			viagem.setId_status(mCursor.getString(mCursor.getColumnIndex(Viagem.KEY_STATUS)) );
			viagem.setLatitude(mCursor.getDouble(mCursor.getColumnIndex(Viagem.KEY_LATITUDE)));
			viagem.setLongitude(mCursor.getDouble(mCursor.getColumnIndex(Viagem.KEY_LONGITUDE)));

			return viagem;
		}

		return null;
	}

	// Busca o usuario pelo id
	public Rota buscarRota(int id) {
		
		Cursor mCursor = db.query(true, TABELA_ROTA, Rota.colunas, Rota.KEY_ID + "=" + id, null, null, null, null, null);

		if (mCursor.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			mCursor.moveToFirst();

			Rota rota = new Rota();

			// Lê os dados
			rota.setId_rota(mCursor.getInt(mCursor.getColumnIndex(Rota.KEY_ID)) );
			rota.setDescricao(mCursor.getString(mCursor.getColumnIndex(Rota.KEY_DESCRICAO)));
			int id_motorista = mCursor.getInt(mCursor.getColumnIndex(Rota.KEY_MOTORISTA));
			Usuario motorista = new Usuario();
			motorista = buscarUsuario(id_motorista);
			rota.setMotorista(motorista);
			
			Cursor mCursorRotaUsuario = db.query(true, TABELA_ROTA_USUARIO, Rota.colunasRotaUsuario, Rota.KEY_ID + "=" + id, null, null, null, null, null);
			List<Usuario> usuarios = new ArrayList<Usuario>();
			if (mCursorRotaUsuario.moveToFirst()) {
				// Loop até o final
				do {
					usuarios.add(buscarUsuario(mCursorRotaUsuario.getInt(mCursorRotaUsuario.getColumnIndex(Usuario.KEY_ID))));
				} while (mCursorRotaUsuario.moveToNext());
			}
			rota.setUsuarios(usuarios);
			
			return rota;
		}

		return null;
	}

	// Retorna um cursor com todos os usuario
	public Cursor getCursorUsuario() {
		try {
			// select * from usuario
			return db.query(TABELA_USUARIO, Usuario.colunas, null, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao buscar os usuarios: " + e.toString());
			return null;
		}
	}

	// Retorna uma lista com todos os usuarios
	public List<Usuario> listarUsuarios() {
		Cursor mCursor = getCursorUsuario();

		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (mCursor.moveToFirst()) {

			// Loop até o final
			do {
				Usuario usuario = new Usuario();

				// recupera os atributos do usuario
				usuario.setId_usuario( mCursor.getInt(mCursor.getColumnIndex(Usuario.KEY_ID)) );
				usuario.setRegistro(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_REGISTRO)));
				usuario.setNome(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_NAME)) );
				usuario.setEmail(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_EMAIL)));
				usuario.setCelular(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_CELULAR)));
				usuario.setPassword(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_PASSWORD)));
				usuario.setPerfil(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_PERFIL)));
				usuario.setCidade(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_CIDADE)));
				usuario.setEndereco(mCursor.getString(mCursor.getColumnIndex(Usuario.KEY_ENDERECO)));
				usuario.setLatitude(mCursor.getDouble(mCursor.getColumnIndex(Usuario.KEY_LATITUDE)));
				usuario.setLongitude(mCursor.getDouble(mCursor.getColumnIndex(Usuario.KEY_LONGITUDE)));
				usuarios.add(usuario);

			} while (mCursor.moveToNext());
		}

		return usuarios;
	}

	// Busca o usuario pelo id "select * from usuario where id_usurio=?"
	public boolean buscarUsuarioPorId(int id) {
		try {
			// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
			Cursor mCursor = db.query(TABELA_USUARIO, Usuario.colunas, Usuario.KEY_ID + "=" + id , null, null, null, null);

			// Se encontrou...
			if (mCursor.moveToNext()) {
				return true;

			}
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao buscar o usuario pelo id: " + e.toString());
			return false;
		}

		return false;
	}
	
	// Busca a viagem pelo id 
	public boolean buscarViagemPorId(int id) {
		try {
			
			Cursor mCursor = db.query(TABELA_VIAGEM, Viagem.colunas, Viagem.KEY_ID + "=" + id , null, null, null, null);

			// Se encontrou...
			if (mCursor.moveToNext()) {
				return true;

			}
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao buscar a viagem pelo id: " + e.toString());
			return false;
		}

		return false;
	}



	// Busca a rota pelo id
	public boolean buscarRotaPorId(int id) {
		try {
			
			Cursor mCursor = db.query(true, TABELA_ROTA, Rota.colunas, Rota.KEY_ID + "=" + id, null, null, null, null, null);
		
			if (mCursor.getCount() > 0) {
				return true;
			}	
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao buscar o usuario pelo id: " + e.toString());
			return false;
		}
		
		return false;
	}
	
	// Busca a rota pelo id da rota e pelo passageiro
	public boolean buscarRotaUsuario(int id_rota, int id_usuario) {
		try {
			Cursor mCursor = db.query(true, TABELA_ROTA_USUARIO, Rota.colunasRotaUsuario, Rota.KEY_ID + "=" + id_rota + " and " + Usuario.KEY_ID + "=" + id_usuario , null, null, null, null, null);
		
			if (mCursor.getCount() > 0) {
				return true;
			}	
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao buscar o usuario pelo id: " + e.toString());
			return false;
		}
		
		return false;
	}

	// Fecha o banco
	public void fechar() {
		// fecha o banco de dados
		if (db != null) {
			db.close();
		}
	}
}
