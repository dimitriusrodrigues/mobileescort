package com.mobileescort.mobileescort.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

public class Rota implements Serializable {
	   
	private Integer id_rota;
	
	private String descricao;
	
	private Integer id_motorista;
	
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

	public Integer getId_motorista() {
		return id_motorista;
	}

	public void setId_motorista(Integer id_motorista) {
		this.id_motorista = id_motorista;
	}

	public Rota() {
		super();
	}
	
	
   
}
