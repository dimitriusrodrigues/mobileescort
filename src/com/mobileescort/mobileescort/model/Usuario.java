package com.mobileescort.mobileescort.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

public class Usuario implements Serializable {
	   
	private Integer id_usuario;
	
	private String registro;
	
	private String nome;
	
	private String email;
	
	private String celular;

	private String password;
	
	private String perfil;
	
	private String cidade;
	
	private String endereco;
	
	private Integer latitude;
	
	private Integer longitude;
	
	
	

	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}
	
	public Integer getId_usuario() {
		return this.id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}   
	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}   
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}   
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the perfil
	 */
	public String getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getLatitude() {
		return latitude;
	}

	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
	}

	public Integer getLongitude() {
		return longitude;
	}

	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
	}
}
