package com.mobileescort.mobileescort.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.List;

public class Rota implements Serializable {
	   
	private Integer id_rota;
	
	private String descricao;
	
	private Usuario motorista;

	private List<Usuario> usuarios;
	
	private static final long serialVersionUID = 1L;

	public Integer getId_rota() {
		return id_rota;
	}

	public void setId_rota(Integer id_rota) {
		this.id_rota = id_rota;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getMotorista() {
		return motorista;
	}

	public void setMotorista(Usuario motorista) {
		this.motorista = motorista;
	}

	/**
	 * @return the usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Rota() {
		super();
	}
   
}
