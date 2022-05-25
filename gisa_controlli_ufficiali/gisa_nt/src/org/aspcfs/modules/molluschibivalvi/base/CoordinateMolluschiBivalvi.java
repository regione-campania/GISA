/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.molluschibivalvi.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CoordinateMolluschiBivalvi implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 96910740548595074L;
	private int id ;
	private int addressId ;
	private double latitude = 0;
	private double longitude = 0;
	private int tipologia = 1;
	private String identificativo = "";
	
	public CoordinateMolluschiBivalvi(ResultSet rs) throws SQLException
	{
		buildRecord(rs);
		
	}
	public CoordinateMolluschiBivalvi(){}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	} 
	  
	  public void setLatitude(String latitude) {
	    try {
	      this.latitude = Double.parseDouble(latitude.replace(',', '.'));
	    } catch (Exception e) {
	      this.latitude = 0;
	    }
	  }


	  /**
	   * Sets the longitude attribute of the Address object
	   *
	   * @param longitude the longitude to set
	   */
	  public void setLongitude(String longitude) {
	    try {
	      this.longitude = Double.parseDouble(longitude.replace(',', '.'));
	    } catch (Exception e) {
	      this.longitude = 0;
	    }
	  }
	
	public void store (Connection db) {
		
		String insert = "insert into coordinate_zone_produzione (address_id,latitude,longitude, tipologia, identificativo) values (?,?,?, ?, ?)" ;
		try
		{
			java.sql.PreparedStatement pst =  db.prepareStatement(insert);
			pst.setInt(1, addressId) ;
			pst.setDouble(2, latitude);
			pst.setDouble(3, longitude);
			pst.setInt(4, tipologia);
			pst.setString(5, identificativo);
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
public void update (Connection db) {
		
		String insert = "update coordinate_zone_produzione set latitude = ?, longitude = ? where id = ?" ;
		try
		{
			java.sql.PreparedStatement pst =  db.prepareStatement(insert);
			pst.setDouble(1, latitude);
			pst.setDouble(2, longitude);
			pst.setInt(3, id);
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}

public void delete (Connection db) {
		
		String insert = "delete from coordinate_zone_produzione where id = ?" ;
		try
		{
			java.sql.PreparedStatement pst =  db.prepareStatement(insert);
			pst.setInt(1, id) ;
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
public int getTipologia() {
	return tipologia;
}
public void setTipologia(int tipologia) {
	this.tipologia = tipologia;
}

private void buildRecord(ResultSet rs) throws SQLException{
	this.id=rs.getInt("id");
	this.addressId = rs.getInt("address_id");
	this.latitude=rs.getDouble("latitude");
	this.longitude=rs.getDouble("longitude");
	this.tipologia=rs.getInt("tipologia");
	this.identificativo=rs.getString("identificativo");

}
public String getIdentificativo() {
	return identificativo;
}
public void setIdentificativo(String identificativo) {
	this.identificativo = identificativo;
}
public CoordinateMolluschiBivalvi (Connection db, int id) throws SQLException{
	PreparedStatement pst = db.prepareStatement("select * from coordinate_zone_produzione where id = "+id);
	ResultSet rs = pst.executeQuery();
	if (rs.next())
		buildRecord(rs);
}

}
