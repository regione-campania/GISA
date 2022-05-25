/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per controlliallevamentiWS complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="controlliallevamentiWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allevIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aslCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="caId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dataPreavviso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="detenIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="distrettoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtControllo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flagCee" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagEsito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagExtrapiano" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagPreavviso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagVitelli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ocCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orientamentoCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagFaseProduttiva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primoControllore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="regCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="speCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoAllevCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoControllo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoProdCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagCopiaCheckList" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sanzBloccoMov" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sanzAmministrativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sanzSequestro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sanzAbbattimentoCapi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sanzInformativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sanzAltro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sanzAltroDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noteControllore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noteDetentore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagEvidenze" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="evidenzaIr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="evidenzaSv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="evidenzaSa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataChiusura" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flagCondizionalita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
  *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "controlliallevamentiWS", propOrder = {
    "allevIdFiscale",
    "aslCodice",
    "aziendaCodice",
    "caId",
    "dataPreavviso",
    "detenIdFiscale",
    "propIdFiscale",
    "distrettoCodice",
    "dtControllo",
    "flagCee",
    "flagEsito",
    "flagExtrapiano",
    "flagPreavviso",
    "flagVitelli",
    "ocCodice",
    "orientamentoCodice",
    "flagFaseProduttiva",
    "primoControllore",
    "regCodice",
    "speCodice",
    "tipoAllevCodice",
    "tipoControllo",
    "tipoProdCodice",
    "flagCopiaCheckList",
    "sanzBloccoMov",
    "sanzAmministrativa",
    "sanzSequestro",
    "sanzAbbattimentoCapi",
    "sanzInformativa",
    "sanzAltro",
    "sanzAltroDesc",
    "noteControllore",
    "noteDetentore",
    "flagEvidenze",
    "evidenzaIr",
    "evidenzaSv",
    "evidenzaSa",
    "dataChiusura",
    "flagCondizionalita"
})

public class ControlliallevamentiWS {

    protected String allevIdFiscale;
    protected String aslCodice;
    protected String aziendaCodice;
    protected Integer caId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPreavviso;
    protected String detenIdFiscale;
    protected String propIdFiscale;
    protected String distrettoCodice;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtControllo;
    protected String flagCee;
    protected String flagEsito;
    protected String flagExtrapiano;
    protected String flagPreavviso;
    protected String flagVitelli;
    protected String ocCodice;
    protected String orientamentoCodice;
    protected String flagFaseProduttiva;
    protected String primoControllore;
    protected String regCodice;
    protected String speCodice;
    protected String tipoAllevCodice;
    protected String tipoControllo;
    protected String tipoProdCodice;
    protected String flagCopiaCheckList;
    protected String sanzBloccoMov;
    protected String sanzAmministrativa;
    protected String sanzSequestro;
    protected String sanzAbbattimentoCapi;
    protected String sanzInformativa;
    protected String sanzAltro;
    protected String sanzAltroDesc;
    protected String noteControllore;
    protected String noteDetentore;
    protected String flagEvidenze;
    protected String evidenzaIr;
    protected String evidenzaSv;
    protected String evidenzaSa;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataChiusura;
    protected String flagCondizionalita;
    /**
     * Recupera il valore della proprieta allevIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllevIdFiscale() {
        return allevIdFiscale;
    }

    /**
     * Imposta il valore della proprieta allevIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllevIdFiscale(String value) {
        this.allevIdFiscale = value;
    }

    /**
     * Recupera il valore della proprieta aslCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAslCodice() {
        return aslCodice;
    }

    /**
     * Imposta il valore della proprieta aslCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAslCodice(String value) {
        this.aslCodice = value;
    }

    /**
     * Recupera il valore della proprieta aziendaCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAziendaCodice() {
        return aziendaCodice;
    }

    /**
     * Imposta il valore della proprieta aziendaCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAziendaCodice(String value) {
        this.aziendaCodice = value;
    }

    /**
     * Recupera il valore della proprieta caId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCaId() {
        return caId;
    }

    /**
     * Imposta il valore della proprieta caId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCaId(Integer value) {
        this.caId = value;
    }

    /**
     * Recupera il valore della proprieta dataPreavviso.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPreavviso() {
        return dataPreavviso;
    }

    /**
     * Imposta il valore della proprieta dataPreavviso.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPreavviso(XMLGregorianCalendar value) {
        this.dataPreavviso = value;
    }

    /**
     * Recupera il valore della proprieta detenIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetenIdFiscale() {
        return detenIdFiscale;
    }

    /**
     * Imposta il valore della proprieta propIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropIdFiscale(String value) {
        this.propIdFiscale = value;
    }

    /**
     * Recupera il valore della proprieta propIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropIdFiscale() {
        return propIdFiscale;
    }

    /**
     * Imposta il valore della proprieta detenIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetenIdFiscale(String value) {
        this.detenIdFiscale = value;
    }
    
    /**
     * Recupera il valore della proprieta distrettoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrettoCodice() {
        return distrettoCodice;
    }

    /**
     * Imposta il valore della proprieta distrettoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrettoCodice(String value) {
        this.distrettoCodice = value;
    }

    /**
     * Recupera il valore della proprieta dtControllo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtControllo() {
        return dtControllo;
    }

    /**
     * Imposta il valore della proprieta dtControllo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtControllo(XMLGregorianCalendar value) {
        this.dtControllo = value;
    }

    /**
     * Recupera il valore della proprieta flagCee.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagCee() {
        return flagCee;
    }

    /**
     * Imposta il valore della proprieta flagCee.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagCee(String value) {
        this.flagCee = value;
    }

    /**
     * Recupera il valore della proprieta flagEsito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagEsito() {
        return flagEsito;
    }

    /**
     * Imposta il valore della proprieta flagEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagEsito(String value) {
        this.flagEsito = value;
    }

    /**
     * Recupera il valore della proprieta flagExtrapiano.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagExtrapiano() {
        return flagExtrapiano;
    }

    /**
     * Imposta il valore della proprieta flagExtrapiano.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagExtrapiano(String value) {
        this.flagExtrapiano = value;
    }

    /**
     * Recupera il valore della proprieta flagPreavviso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagPreavviso() {
        return flagPreavviso;
    }

    /**
     * Imposta il valore della proprieta flagPreavviso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagPreavviso(String value) {
        this.flagPreavviso = value;
    }

    /**
     * Recupera il valore della proprieta flagVitelli.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagVitelli() {
        return flagVitelli;
    }

    /**
     * Imposta il valore della proprieta flagVitelli.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagVitelli(String value) {
        this.flagVitelli = value;
    }

    /**
     * Recupera il valore della proprieta ocCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOcCodice() {
        return ocCodice;
    }

    /**
     * Imposta il valore della proprieta ocCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOcCodice(String value) {
        this.ocCodice = value;
    }

    /**
     * Recupera il valore della proprieta orientamentoCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrientamentoCodice() {
        return orientamentoCodice;
    }

    /**
     * Imposta il valore della proprieta orientamentoCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrientamentoCodice(String value) {
        this.orientamentoCodice = value;
    }

    /**
     * Recupera il valore della proprieta flagFaseProduttiva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagFaseProduttiva() {
        return flagFaseProduttiva;
    }

    /**
     * Imposta il valore della proprieta flagFaseProduttiva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagFaseProduttiva(String value) {
        this.flagFaseProduttiva = value;
    }

    
    /**
     * Recupera il valore della proprieta primoControllore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimoControllore() {
        return primoControllore;
    }

    /**
     * Imposta il valore della proprieta primoControllore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimoControllore(String value) {
        this.primoControllore = value;
    }

    /**
     * Recupera il valore della proprieta regCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegCodice() {
        return regCodice;
    }

    /**
     * Imposta il valore della proprieta regCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegCodice(String value) {
        this.regCodice = value;
    }

    /**
     * Recupera il valore della proprieta speCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeCodice() {
        return speCodice;
    }

    /**
     * Imposta il valore della proprieta speCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeCodice(String value) {
        this.speCodice = value;
    }

    /**
     * Recupera il valore della proprieta tipoAllevCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAllevCodice() {
        return tipoAllevCodice;
    }

    /**
     * Imposta il valore della proprieta tipoAllevCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAllevCodice(String value) {
        this.tipoAllevCodice = value;
    }

    /**
     * Recupera il valore della proprieta tipoControllo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoControllo() {
        return tipoControllo;
    }

    /**
     * Imposta il valore della proprieta tipoControllo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoControllo(String value) {
        this.tipoControllo = value;
    }

    /**
     * Recupera il valore della proprieta tipoProdCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoProdCodice() {
        return tipoProdCodice;
    }

    /**
     * Imposta il valore della proprieta tipoProdCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoProdCodice(String value) {
        this.tipoProdCodice = value;
    }

	public String getFlagCopiaCheckList() {
		return flagCopiaCheckList;
	}

	public void setFlagCopiaCheckList(String flagCopiaCheckList) {
		this.flagCopiaCheckList = flagCopiaCheckList;
	}

	public String getSanzBloccoMov() {
		return sanzBloccoMov;
	}

	public void setSanzBloccoMov(String sanzBloccoMov) {
		this.sanzBloccoMov = sanzBloccoMov;
	}

	public String getSanzAmministrativa() {
		return sanzAmministrativa;
	}

	public void setSanzAmministrativa(String sanzAmministrativa) {
		this.sanzAmministrativa = sanzAmministrativa;
	}

	public String getSanzSequestro() {
		return sanzSequestro;
	}

	public void setSanzSequestro(String sanzSequestro) {
		this.sanzSequestro = sanzSequestro;
	}

	public String getSanzAbbattimentoCapi() {
		return sanzAbbattimentoCapi;
	}

	public void setSanzAbbattimentoCapi(String sanzAbbattimentoCapi) {
		this.sanzAbbattimentoCapi = sanzAbbattimentoCapi;
	}

	public String getSanzInformativa() {
		return sanzInformativa;
	}

	public void setSanzInformativa(String sanzInformativa) {
		this.sanzInformativa = sanzInformativa;
	}

	public String getSanzAltro() {
		return sanzAltro;
	}

	public void setSanzAltro(String sanzAltro) {
		this.sanzAltro = sanzAltro;
	}

	public String getSanzAltroDesc() {
		return sanzAltroDesc;
	}

	public void setSanzAltroDesc(String sanzAltroDesc) {
		this.sanzAltroDesc = sanzAltroDesc;
	}

	public String getNoteControllore() {
		return noteControllore;
	}

	public void setNoteControllore(String noteControllore) {
		this.noteControllore = noteControllore;
	}

	public String getNoteDetentore() {
		return noteDetentore;
	}

	public void setNoteDetentore(String noteDetentore) {
		this.noteDetentore = noteDetentore;
	}

	public String getFlagEvidenze() {
		return flagEvidenze;
	}

	public void setFlagEvidenze(String flagEvidenze) {
		this.flagEvidenze = flagEvidenze;
	}

	public String getEvidenzaIr() {
		return evidenzaIr;
	}

	public void setEvidenzaIr(String evidenzaIr) {
		this.evidenzaIr = evidenzaIr;
	}

	public String getEvidenzaSv() {
		return evidenzaSv;
	}

	public void setEvidenzaSv(String evidenzaSv) {
		this.evidenzaSv = evidenzaSv;
	}

	public String getEvidenzaSa() {
		return evidenzaSa;
	}

	public void setEvidenzaSa(String evidenzaSa) {
		this.evidenzaSa = evidenzaSa;
	}

	public XMLGregorianCalendar getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(XMLGregorianCalendar dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public String getFlagCondizionalita() {
		return flagCondizionalita;
	}

	public void setFlagCondizionalita(String flagCondizionalita) {
		this.flagCondizionalita = flagCondizionalita;
	}

}
