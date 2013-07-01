/**
 * 
 */
package com.mobileescort.mobileescort.model;

import java.io.Serializable;

/**
 * @author dimitrius
 *
 */
public class Viagem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static final String KEY_ID = "id_viagem";
	public static final String KEY_ID_ROTA = "id_rota";
	public static final String KEY_STATUS = "id_status";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";

	public static String[] colunas = new String[] { KEY_ID, KEY_ID_ROTA, KEY_STATUS, KEY_LATITUDE, KEY_LONGITUDE };

	private int id_viagem;
	private int id_rota;
	private String id_status;
	private double latitude;
	private double longitude;
	
	/**
	 * @return the id_rota
	 */
	public int getId_viagem() {
		return id_viagem;
	}


	/**
	 * @param id_rota the id_rota to set
	 */
	public void setId_viagem(int id_viagem) {
		this.id_viagem = id_viagem;
	}
	
	/**
	 * @return the id_rota
	 */
	public int getId_rota() {
		return id_rota;
	}


	/**
	 * @param id_rota the id_rota to set
	 */
	public void setId_rota(int id_rota) {
		this.id_rota = id_rota;
	}


	/**
	 * @return the id_status
	 */
	public String getId_status() {
		return id_status;
	}


	/**
	 * @param id_status the id_status to set
	 */
	public void setId_status(String id_status) {
		this.id_status = id_status;
	}


	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}


	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}


	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public Viagem() {
		// TODO Auto-generated constructor stub
	}

}
