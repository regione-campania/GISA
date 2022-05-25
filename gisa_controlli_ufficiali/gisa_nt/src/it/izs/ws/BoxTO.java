/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per boxTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="boxTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clBoxId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clbId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="boxNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="larghezza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lunghezza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroAnimali" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="peso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="categoria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagPavimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="travetti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fessure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="regolare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "boxTO", propOrder = {
		"clBoxId",
		"clbId",
		"boxNum",
		"larghezza",
		"lunghezza",
		"numeroAnimali",
		"peso",
		"categoria",
		"flagPavimento",
		"travetti",
		"fessure",
		"regolare"
		
    })
@XmlSeeAlso({
    BoxWsTO.class
})
public class BoxTO {

	protected String clBoxId;
	protected String clbId;
	protected String boxNum;
	protected String larghezza;
	protected String lunghezza;
	protected String numeroAnimali;
	protected String peso;
	protected String categoria;
	protected String flagPavimento;
	protected String travetti;
	protected String fessure;
	protected String regolare;
	public String getClBoxId() {
		return clBoxId;
	}
	public void setClBoxId(String clBoxId) {
		this.clBoxId = clBoxId;
	}
	public String getClbId() {
		return clbId;
	}
	public void setClbId(String clbId) {
		this.clbId = clbId;
	}
	public String getBoxNum() {
		return boxNum;
	}
	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}
	public String getLarghezza() {
		return larghezza;
	}
	public void setLarghezza(String larghezza) {
		this.larghezza = larghezza;
	}
	public String getLunghezza() {
		return lunghezza;
	}
	public void setLunghezza(String lunghezza) {
		this.lunghezza = lunghezza;
	}
	public String getNumeroAnimali() {
		return numeroAnimali;
	}
	public void setNumeroAnimali(String numeroAnimali) {
		this.numeroAnimali = numeroAnimali;
	}
	public String getPeso() {
		return peso;
	}
	public void setPeso(String peso) {
		this.peso = peso;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getFlagPavimento() {
		return flagPavimento;
	}
	public void setFlagPavimento(String flagPavimento) {
		this.flagPavimento = flagPavimento;
	}
	public String getTravetti() {
		return travetti;
	}
	public void setTravetti(String travetti) {
		this.travetti = travetti;
	}
	public String getFessure() {
		return fessure;
	}
	public void setFessure(String fessure) {
		this.fessure = fessure;
	}
	public String getRegolare() {
		return regolare;
	}
	public void setRegolare(String regolare) {
		this.regolare = regolare;
	}
	
	public boolean isVuoto() {
		boolean vuoto = false;
		if ((boxNum==null || boxNum.equals("")) &&
				(larghezza==null || larghezza.equals("")) &&
				(lunghezza==null || lunghezza.equals("")) &&
				(numeroAnimali==null || numeroAnimali.equals("")) &&
				(peso==null || peso.equals("")) &&
				(categoria==null || categoria.equals("")) &&
				(flagPavimento==null || flagPavimento.equals("")) &&
				(travetti==null || travetti.equals("")) &&
				(fessure==null || fessure.equals("")) &&
				(regolare==null || regolare.equals(""))) {
			vuoto = true; 
		}
		return vuoto;
	}

}

    
    