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
 * <p>Classe Java per capannoniTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="capannoniTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clcapId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clbId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="capacita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="animaliPresenti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroBox" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroBoxAttivi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="categoriaAnimali" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagIspezionato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capannoniTO", propOrder = {
		"clcapId",
		"clbId",
		"identificativo",
		"capacita",
		"animaliPresenti",
		"numeroBox",
		"numeroBoxAttivi",
		"categoriaAnimali",
		"flagIspezionato"		
    })
@XmlSeeAlso({
    CapannoniWsTO.class
})
public class CapannoniTO {

	protected String clcapId;
	protected String clbId;
	protected String identificativo;
	protected String capacita;
	protected String animaliPresenti;
	protected String numeroBox;
	protected String numeroBoxAttivi;
	protected String categoriaAnimali;
	protected String flagIspezionato;
	
	
	public String getClcapId() {
		return clcapId;
	}
	public void setClcapId(String clcapId) {
		this.clcapId = clcapId;
	}
	public String getClbId() {
		return clbId;
	}
	public void setClbId(String clbId) {
		this.clbId = clbId;
	}
	public String getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	public String getCapacita() {
		return capacita;
	}
	public void setCapacita(String capacita) {
		this.capacita = capacita;
	}
	public String getAnimaliPresenti() {
		return animaliPresenti;
	}
	public void setAnimaliPresenti(String animaliPresenti) {
		this.animaliPresenti = animaliPresenti;
	}
	public String getNumeroBox() {
		return numeroBox;
	}
	public void setNumeroBox(String numeroBox) {
		this.numeroBox = numeroBox;
	}
	public String getNumeroBoxAttivi() {
		return numeroBoxAttivi;
	}
	public void setNumeroBoxAttivi(String numeroBoxAttivi) {
		this.numeroBoxAttivi = numeroBoxAttivi;
	}
	public String getCategoriaAnimali() {
		return categoriaAnimali;
	}
	public void setCategoriaAnimali(String categoriaAnimali) {
		this.categoriaAnimali = categoriaAnimali;
	}
	public String getFlagIspezionato() {
		return flagIspezionato;
	}
	public void setFlagIspezionato(String flagIspezionato) {
		this.flagIspezionato = flagIspezionato;
	}

	
}
    
    