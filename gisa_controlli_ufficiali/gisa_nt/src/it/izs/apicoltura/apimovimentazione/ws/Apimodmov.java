/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.apicoltura.apimovimentazione.ws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per apimodmov complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="apimodmov">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.apimovimentazione.apicoltura.izs.it/}entity">
 *       &lt;sequence>
 *         &lt;element name="apimodmovId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apiProgressivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="apiattDenominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apiattAziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numModello" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtModello" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dtUscita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="apimotuscCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apimotuscDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destApiattDenominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destApiattAziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attDestPropIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attDestPropCognNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attestazioneSanitaria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statoRichiestaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statoRichiesta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtStatoRichiesta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="xmlListaDettaglioModello" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listaDettaglioModello" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Apidetmod" type="{http://ws.apimovimentazione.apicoltura.izs.it/}apidetmod" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "apimodmov", propOrder = {
    "apimodmovId",
    "apiProgressivo",
    "apiattDenominazione",
    "apiattAziendaCodice",
    "numModello",
    "dtModello",
    "dtUscita",
    "apimotuscCodice",
    "apimotuscDescrizione",
    "destApiattDenominazione",
    "destApiattAziendaCodice",
    "attDestPropIdFiscale",
    "attDestPropCognNome",
    "attestazioneSanitaria",
    "statoRichiestaCodice",
    "statoRichiesta",
    "dtStatoRichiesta",
    "numSciami",
    "numAlveari",
    "numPacchiDapi",
    "numApiRegine",
    "destAziendaComIstat",
    "destAziendaComProSigla",
    "destAziendaIndirizzo",
    "destAziendaIdFiscale",
    "destAziendaDenominazione",
    "recuperoMaterialeBiologico",
    "xmlListaDettaglioModello",
    "listaDettaglioModello"
})
public class Apimodmov
    extends Entity
{

    protected Integer apimodmovId;
    protected Integer apiProgressivo;
    protected String apiattDenominazione;
    protected String apiattAziendaCodice;
    protected String numModello;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtModello;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtUscita;
    protected String apimotuscCodice;
    protected String apimotuscDescrizione;
    protected String destApiattDenominazione;
    protected String destApiattAziendaCodice;
    protected String attDestPropIdFiscale;
    protected String attDestPropCognNome;
    protected String attestazioneSanitaria;
    protected String statoRichiestaCodice;
    protected String statoRichiesta;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtStatoRichiesta;
    protected Integer numSciami;
    protected Integer numAlveari;
    protected Integer numPacchiDapi;
    protected Integer numApiRegine;
    protected String xmlListaDettaglioModello;
    protected Apimodmov.ListaDettaglioModello listaDettaglioModello;
    protected String destAziendaComProSigla;
    protected String destAziendaComIstat;
    protected String destAziendaIndirizzo;
    protected String destAziendaIdFiscale;
    protected String destAziendaDenominazione;
    protected boolean recuperoMaterialeBiologico;
    
    /**
     * Recupera il valore della proprieta apimodmovId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApimodmovId() {
        return apimodmovId;
    }

    /**
     * Imposta il valore della proprieta apimodmovId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApimodmovId(Integer value) {
        this.apimodmovId = value;
    }

    /**
     * Recupera il valore della proprieta apiProgressivo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApiProgressivo() {
        return apiProgressivo;
    }

    /**
     * Imposta il valore della proprieta apiProgressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApiProgressivo(Integer value) {
        this.apiProgressivo = value;
    }
    
    
    public String getDestAziendaComIstat() {
        return destAziendaComIstat;
    }

    /**
     * Imposta il valore della proprieta destApiComIstat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestAziendaComIstat(String value) {
        this.destAziendaComIstat = value;
    }

    
    public String getDestAziendaIndirizzo() {
        return destAziendaIndirizzo;
    }

    /**
     * Imposta il valore della proprieta destApiIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestAziendaIndirizzo(String value) {
        this.destAziendaIndirizzo = value;
    }
    
    
    /**
     * Recupera il valore della proprieta apiattDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiattDenominazione() {
        return apiattDenominazione;
    }
    
    public String getDestAziendaComProSigla() {
		return destAziendaComProSigla;
	}

	public void setDestAziendaComProSigla(String destAziendaComProSigla) {
		this.destAziendaComProSigla = destAziendaComProSigla;
	}
	
	public String getDestAziendaIdFiscale() {
		return destAziendaIdFiscale;
	}

	public void setDestAziendaIdFiscale(String destAziendaIdFiscale) {
		this.destAziendaIdFiscale = destAziendaIdFiscale;
	}
	
	public String getDestAziendaDenominazione() {
		return destAziendaDenominazione;
	}

	public void setDestAziendaDenominazione(String destAziendaDenominazione) {
		this.destAziendaDenominazione = destAziendaDenominazione;
	}
	
	public boolean getRecuperoMaterialeBiologico() {
		return recuperoMaterialeBiologico;
	}

	public void setRecuperoMaterialeBiologico(boolean recuperoMaterialeBiologico) {
		this.recuperoMaterialeBiologico = recuperoMaterialeBiologico;
	}
	
	

    /**
     * Imposta il valore della proprieta apiattDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiattDenominazione(String value) {
        this.apiattDenominazione = value;
    }

    /**
     * Recupera il valore della proprieta apiattAziendaCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiattAziendaCodice() {
        return apiattAziendaCodice;
    }

    /**
     * Imposta il valore della proprieta apiattAziendaCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiattAziendaCodice(String value) {
        this.apiattAziendaCodice = value;
    }

    /**
     * Recupera il valore della proprieta numModello.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumModello() {
        return numModello;
    }

    /**
     * Imposta il valore della proprieta numModello.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumModello(String value) {
        this.numModello = value;
    }

    /**
     * Recupera il valore della proprieta dtModello.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtModello() {
        return dtModello;
    }

    /**
     * Imposta il valore della proprieta dtModello.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtModello(XMLGregorianCalendar value) {
        this.dtModello = value;
    }

    /**
     * Recupera il valore della proprieta dtUscita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtUscita() {
        return dtUscita;
    }

    /**
     * Imposta il valore della proprieta dtUscita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtUscita(XMLGregorianCalendar value) {
        this.dtUscita = value;
    }

    /**
     * Recupera il valore della proprieta apimotuscCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApimotuscCodice() {
        return apimotuscCodice;
    }

    /**
     * Imposta il valore della proprieta apimotuscCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApimotuscCodice(String value) {
        this.apimotuscCodice = value;
    }
    
    public Integer getNumSciami() {
        return numSciami;
    }

    /**
     * Imposta il valore della proprieta numSciami.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumSciami(Integer value) {
        this.numSciami = value;
    }

    /**
     * Recupera il valore della proprieta numAlveari.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumAlveari() {
        return numAlveari;
    }

    /**
     * Imposta il valore della proprieta numAlveari.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumAlveari(Integer value) {
        this.numAlveari = value;
    }

    /**
     * Recupera il valore della proprieta numPacchiDapi.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumPacchiDapi() {
        return numPacchiDapi;
    }

    /**
     * Imposta il valore della proprieta numPacchiDapi.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumPacchiDapi(Integer value) {
        this.numPacchiDapi = value;
    }

    /**
     * Recupera il valore della proprieta numApiRegine.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumApiRegine() {
        return numApiRegine;
    }

    /**
     * Imposta il valore della proprieta numApiRegine.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumApiRegine(Integer value) {
        this.numApiRegine = value;
    }


    /**
     * Recupera il valore della proprieta apimotuscDescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApimotuscDescrizione() {
        return apimotuscDescrizione;
    }

    /**
     * Imposta il valore della proprieta apimotuscDescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApimotuscDescrizione(String value) {
        this.apimotuscDescrizione = value;
    }

    /**
     * Recupera il valore della proprieta destApiattDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestApiattDenominazione() {
        return destApiattDenominazione;
    }

    /**
     * Imposta il valore della proprieta destApiattDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestApiattDenominazione(String value) {
        this.destApiattDenominazione = value;
    }

    /**
     * Recupera il valore della proprieta destApiattAziendaCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestApiattAziendaCodice() {
        return destApiattAziendaCodice;
    }

    /**
     * Imposta il valore della proprieta destApiattAziendaCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestApiattAziendaCodice(String value) {
        this.destApiattAziendaCodice = value;
    }

    /**
     * Recupera il valore della proprieta attDestPropIdFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttDestPropIdFiscale() {
        return attDestPropIdFiscale;
    }

    /**
     * Imposta il valore della proprieta attDestPropIdFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttDestPropIdFiscale(String value) {
        this.attDestPropIdFiscale = value;
    }

    /**
     * Recupera il valore della proprieta attDestPropCognNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttDestPropCognNome() {
        return attDestPropCognNome;
    }

    /**
     * Imposta il valore della proprieta attDestPropCognNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttDestPropCognNome(String value) {
        this.attDestPropCognNome = value;
    }

    /**
     * Recupera il valore della proprieta attestazioneSanitaria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttestazioneSanitaria() {
        return attestazioneSanitaria;
    }

    /**
     * Imposta il valore della proprieta attestazioneSanitaria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttestazioneSanitaria(String value) {
        this.attestazioneSanitaria = value;
    }

    /**
     * Recupera il valore della proprieta statoRichiestaCodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoRichiestaCodice() {
        return statoRichiestaCodice;
    }

    /**
     * Imposta il valore della proprieta statoRichiestaCodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoRichiestaCodice(String value) {
        this.statoRichiestaCodice = value;
    }

    /**
     * Recupera il valore della proprieta statoRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoRichiesta() {
        return statoRichiesta;
    }

    /**
     * Imposta il valore della proprieta statoRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoRichiesta(String value) {
        this.statoRichiesta = value;
    }

    /**
     * Recupera il valore della proprieta dtStatoRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtStatoRichiesta() {
        return dtStatoRichiesta;
    }

    /**
     * Imposta il valore della proprieta dtStatoRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtStatoRichiesta(XMLGregorianCalendar value) {
        this.dtStatoRichiesta = value;
    }

    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Apidetmod" type="{http://ws.apimovimentazione.apicoltura.izs.it/}apidetmod" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    
    /**
     * Recupera il valore della proprieta xmlListaDettaglioModello.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlListaDettaglioModello() {
        return xmlListaDettaglioModello;
    }

    /**
     * Imposta il valore della proprieta xmlListaDettaglioModello.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlListaDettaglioModello(String value) {
        this.xmlListaDettaglioModello = value;
    }

    /**
     * Recupera il valore della proprieta listaDettaglioModello.
     * 
     * @return
     *     possible object is
     *     {@link Apimodmov.ListaDettaglioModello }
     *     
     */
    public Apimodmov.ListaDettaglioModello getListaDettaglioModello() {
        return listaDettaglioModello;
    }

    /**
     * Imposta il valore della proprieta listaDettaglioModello.
     * 
     * @param value
     *     allowed object is
     *     {@link Apimodmov.ListaDettaglioModello }
     *     
     */
    public void setListaDettaglioModello(Apimodmov.ListaDettaglioModello value) {
        this.listaDettaglioModello = value;
    }
    
    
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "apidetmod"
    })
    public static class ListaDettaglioModello {

    	
    	@XmlElement(name = "Apidetmod")
		public List<Apidetmod> apidetmod;

        /**
         * Gets the value of the apidetmod property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the apidetmod property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getApidetmod().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Apidetmod }
         * 
         * 
         */
        public List<Apidetmod> getApidetmod() {
            if (apidetmod == null) {
                apidetmod = new ArrayList<Apidetmod>();
            }
            return this.apidetmod;
        }
        
        public void setApidetmod(List<Apidetmod> apidetmod) {
            this.apidetmod = apidetmod;
        }

    }
    
    
}
