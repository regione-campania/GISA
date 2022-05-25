/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per checklistbenessereTO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="checklistbenessereTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allevIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aslCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aziendaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="caId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="clbId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dataScadPrescrizioni" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataVerificaBa" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="detenIdFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtControllo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flagBattGabbieMod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagBattGabbieNoMod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagCapMaxAut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagCee" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagEsitoBa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagEsitoIr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagExtrapiano" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagPrescrizioneEsitoBa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagVitelli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numAnimaliMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numAnimaliPresenti" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numBattGabbieMod" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numBattGabbieNoMod" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numBox" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numBoxAttivi" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numCapannoni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numCapannoniAttivi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numIngrassoMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numIngrassoPresenti" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numLattanzoliMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numLattanzoliPresenti" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numOvaioleMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numOvaiolePresenti" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numScrofeMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numScrofePresenti" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numSuinettiMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numSuinettiPresenti" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numVerriMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numVerriPresenti" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numVitelli8Sett" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numVitelliMax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numVitelliPresenti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prescrizioni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requisitiXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondoControllore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoAllevCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoProdCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="critCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="criterioControlloAltro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagIntenzionalita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomeRappresentante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomeRappresentanteVer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parametro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="veterinario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numClb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rScrofeMorteAnno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rSuinettiSvezzatiAnno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sSuiniPresenti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sSuiniMorti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sFlagTuttoPieno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sNumCicli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sNumCapiCiclo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sBoxTipo1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sBoxTipo2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sBoxTipo3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sBoxTipo4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sBoxTipo5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iSuiniPresenti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iSuiniMorti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iFlagTuttoPieno " type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iNumCicli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iNumCapiCiclo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iBoxTipo1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iBoxTipo2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iBoxTipo3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iBoxTipo4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iBoxTipo5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="capiCodaTagliata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gruppiCodaTagliata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="produzioniTipiche" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usoAnestetici" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presenzaManuale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numUovaAnno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagSelImbal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzoSelImbal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mutaInAllev" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numCapiLattazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numCapiAsciutta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numManze" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numIngrasso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numTori" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kgLatte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagStabulazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checklistbenessereTO", propOrder = {
    "allevIdFiscale",
    "aslCodice",
    "aziendaCodice",
    "caId",
    "clbId",
    "dataScadPrescrizioni",
    "dataVerificaBa",
    "detenIdFiscale",
    "dtControllo",
    "flagBattGabbieMod",
    "flagBattGabbieNoMod",
    "flagCapMaxAut",
    "flagCee",
    "flagEsitoBa",
    "flagEsitoIr",
    "flagExtrapiano",
    "flagPrescrizioneEsitoBa",
    "flagVitelli",
    "numAnimaliMax",
    "numAnimaliPresenti",
    "numBattGabbieMod",
    "numBattGabbieNoMod",
    "numBox",
    "numBoxAttivi",
    "numCapannoni",
    "numCapannoniAttivi",
    "numIngrassoMax",
    "numIngrassoPresenti",
    "numLattanzoliMax",
    "numLattanzoliPresenti",
    "numOvaioleMax",
    "numOvaiolePresenti",
    "numScrofeMax",
    "numScrofePresenti",
    "numSuinettiMax",
    "numSuinettiPresenti",
    "numVerriMax",
    "numVerriPresenti",
    "numVitelli8Sett",
    "numVitelliMax",
    "numVitelliPresenti",
    "prescrizioni",
    "requisitiXml",
    "secondoControllore",
    "tipoAllegato",
    "tipoAllevCodice",
    "tipoProdCodice",
    "critCodice",
    "criterioControlloAltro",
    "flagIntenzionalita",
    "nomeRappresentante",
    "nomeRappresentanteVer",
    "parametro",
    "veterinario",
    "numClb",
    "rScrofeMorteAnno",
    "rSuinettiSvezzatiAnno",
    "sSuiniPresenti",
    "sSuiniMorti",
    "sFlagTuttoPieno",
    "sNumCicli",
    "sNumCapiCiclo",
    "sBoxTipo1",
    "sBoxTipo2",
    "sBoxTipo3",
    "sBoxTipo4",
    "sBoxTipo5",
    "iSuiniPresenti",
    "iSuiniMorti",
    "iFlagTuttoPieno",
    "iNumCicli",
    "iNumCapiCiclo",
    "iBoxTipo1",
    "iBoxTipo2",
    "iBoxTipo3",
    "iBoxTipo4",
    "iBoxTipo5",
    "capiCodaTagliata",
    "gruppiCodaTagliata",
    "produzioniTipiche",
    "usoAnestetici",
    "presenzaManuale",
   	"numUovaAnno",
   	"flagSelImbal",
   	"indirizzoSelImbal",
   	"mutaInAllev",
   	"numCapiLattazione",
   	"numCapiAsciutta",
   	"numManze",
   	"numIngrasso",
   	"numTori",
   	"kgLatte",
   	"flagStabulazione"
})
@XmlSeeAlso({
    ChecklistbenessereWsTO.class
})
public class ChecklistbenessereTO {

    protected String allevIdFiscale;
    protected String aslCodice;
    protected String aziendaCodice;
    protected Integer caId;
    protected Integer clbId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadPrescrizioni;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVerificaBa;
    protected String detenIdFiscale;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtControllo;
    protected String flagBattGabbieMod;
    protected String flagBattGabbieNoMod;
    protected String flagCapMaxAut;
    protected String flagCee;
    protected String flagEsitoBa;
    protected String flagEsitoIr;
    protected String flagExtrapiano;
    protected String flagPrescrizioneEsitoBa;
    protected String flagVitelli;
    protected Integer numAnimaliMax;
    protected Integer numAnimaliPresenti;
    protected Integer numBattGabbieMod;
    protected Integer numBattGabbieNoMod;
    protected Integer numBox;
    protected Integer numBoxAttivi;
    protected String numCapannoni;
    protected String numCapannoniAttivi;
    protected Integer numIngrassoMax;
    protected Integer numIngrassoPresenti;
    protected Integer numLattanzoliMax;
    protected Integer numLattanzoliPresenti;
    protected Integer numOvaioleMax;
    protected Integer numOvaiolePresenti;
    protected Integer numScrofeMax;
    protected Integer numScrofePresenti;
    protected Integer numSuinettiMax;
    protected Integer numSuinettiPresenti;
    protected Integer numVerriMax;
    protected Integer numVerriPresenti;
    protected String numVitelli8Sett;
    protected String numVitelliMax;
    protected String numVitelliPresenti;
    protected String prescrizioni;
    protected String requisitiXml;
    protected String secondoControllore;
    protected String tipoAllegato;
    protected String tipoAllevCodice;
    protected String tipoProdCodice;

    
    protected String critCodice;
   	protected String criterioControlloAltro;
   	protected String flagIntenzionalita;
   	protected String nomeRappresentante;
   	protected String nomeRappresentanteVer;
   	protected String parametro;
   	protected String veterinario;
   	protected String numClb;
   	protected String rScrofeMorteAnno;
   	protected String rSuinettiSvezzatiAnno;
   	protected String sSuiniPresenti;
   	protected String sSuiniMorti;
   	protected String sFlagTuttoPieno;
   	protected String sNumCicli;
   	protected String sNumCapiCiclo;
   	protected String sBoxTipo1;
   	protected String sBoxTipo2;
   	protected String sBoxTipo3;
   	protected String sBoxTipo4;
   	protected String sBoxTipo5;
   	protected String iSuiniPresenti;
   	protected String iSuiniMorti;
   	protected String iFlagTuttoPieno ;
   	protected String iNumCicli;
   	protected String iNumCapiCiclo;
   	protected String iBoxTipo1;
   	protected String iBoxTipo2;
   	protected String iBoxTipo3;
   	protected String iBoxTipo4;
   	protected String iBoxTipo5;
   	
   	protected String capiCodaTagliata;
   	protected String gruppiCodaTagliata;
   	protected String produzioniTipiche;
   	protected String usoAnestetici;
   	protected String presenzaManuale;
   	
   	protected String numUovaAnno;
   	protected String flagSelImbal;
   	protected String indirizzoSelImbal;
   	protected String mutaInAllev;
   	
   	protected String numCapiLattazione;
   	protected String numCapiAsciutta;
   	protected String numManze;
   	protected String numIngrasso;
   	protected String numTori;
   	protected String kgLatte;
   	protected String flagStabulazione;
   	    
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
     * Recupera il valore della proprieta clbId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClbId() {
        return clbId;
    }

    /**
     * Imposta il valore della proprieta clbId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClbId(Integer value) {
        this.clbId = value;
    }

    /**
     * Recupera il valore della proprieta dataScadPrescrizioni.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadPrescrizioni() {
        return dataScadPrescrizioni;
    }

    /**
     * Imposta il valore della proprieta dataScadPrescrizioni.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadPrescrizioni(XMLGregorianCalendar value) {
        this.dataScadPrescrizioni = value;
    }

    /**
     * Recupera il valore della proprieta dataVerificaBa.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVerificaBa() {
        return dataVerificaBa;
    }

    /**
     * Imposta il valore della proprieta dataVerificaBa.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVerificaBa(XMLGregorianCalendar value) {
        this.dataVerificaBa = value;
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
     * Recupera il valore della proprieta flagBattGabbieMod.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagBattGabbieMod() {
        return flagBattGabbieMod;
    }

    /**
     * Imposta il valore della proprieta flagBattGabbieMod.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagBattGabbieMod(String value) {
        this.flagBattGabbieMod = value;
    }

    /**
     * Recupera il valore della proprieta flagBattGabbieNoMod.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagBattGabbieNoMod() {
        return flagBattGabbieNoMod;
    }

    /**
     * Imposta il valore della proprieta flagBattGabbieNoMod.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagBattGabbieNoMod(String value) {
        this.flagBattGabbieNoMod = value;
    }

    /**
     * Recupera il valore della proprieta flagCapMaxAut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagCapMaxAut() {
        return flagCapMaxAut;
    }

    /**
     * Imposta il valore della proprieta flagCapMaxAut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagCapMaxAut(String value) {
        this.flagCapMaxAut = value;
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
     * Recupera il valore della proprieta flagEsitoBa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagEsitoBa() {
        return flagEsitoBa;
    }

    /**
     * Imposta il valore della proprieta flagEsitoBa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagEsitoBa(String value) {
        this.flagEsitoBa = value;
    }

    /**
     * Recupera il valore della proprieta flagEsitoIr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagEsitoIr() {
        return flagEsitoIr;
    }

    /**
     * Imposta il valore della proprieta flagEsitoIr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagEsitoIr(String value) {
        this.flagEsitoIr = value;
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
     * Recupera il valore della proprieta flagPrescrizioneEsitoBa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagPrescrizioneEsitoBa() {
        return flagPrescrizioneEsitoBa;
    }

    /**
     * Imposta il valore della proprieta flagPrescrizioneEsitoBa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagPrescrizioneEsitoBa(String value) {
        this.flagPrescrizioneEsitoBa = value;
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
     * Recupera il valore della proprieta numAnimaliMax.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumAnimaliMax() {
        return numAnimaliMax;
    }

    /**
     * Imposta il valore della proprieta numAnimaliMax.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumAnimaliMax(Integer value) {
        this.numAnimaliMax = value;
    }

    /**
     * Recupera il valore della proprieta numAnimaliPresenti.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumAnimaliPresenti() {
        return numAnimaliPresenti;
    }

    /**
     * Imposta il valore della proprieta numAnimaliPresenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumAnimaliPresenti(Integer value) {
        this.numAnimaliPresenti = value;
    }

    /**
     * Recupera il valore della proprieta numBattGabbieMod.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumBattGabbieMod() {
        return numBattGabbieMod;
    }

    /**
     * Imposta il valore della proprieta numBattGabbieMod.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumBattGabbieMod(Integer value) {
        this.numBattGabbieMod = value;
    }

    /**
     * Recupera il valore della proprieta numBattGabbieNoMod.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumBattGabbieNoMod() {
        return numBattGabbieNoMod;
    }

    /**
     * Imposta il valore della proprieta numBattGabbieNoMod.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumBattGabbieNoMod(Integer value) {
        this.numBattGabbieNoMod = value;
    }

    /**
     * Recupera il valore della proprieta numBox.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumBox() {
        return numBox;
    }

    /**
     * Imposta il valore della proprieta numBox.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumBox(Integer value) {
        this.numBox = value;
    }

    /**
     * Recupera il valore della proprieta numBoxAttivi.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumBoxAttivi() {
        return numBoxAttivi;
    }

    /**
     * Imposta il valore della proprieta numBoxAttivi.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumBoxAttivi(Integer value) {
        this.numBoxAttivi = value;
    }

    /**
     * Recupera il valore della proprieta numCapannoni.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public String getNumCapannoni() {
        return numCapannoni;
    }

    /**
     * Imposta il valore della proprieta numCapannoni.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumCapannoni(String value) {
        this.numCapannoni = value;
    }

    /**
     * Recupera il valore della proprieta numCapannoniAttivi.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public String getNumCapannoniAttivi() {
        return numCapannoniAttivi;
    }

    /**
     * Imposta il valore della proprieta numCapannoniAttivi.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumCapannoniAttivi(String value) {
        this.numCapannoniAttivi = value;
    }

    /**
     * Recupera il valore della proprieta numIngrassoMax.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumIngrassoMax() {
        return numIngrassoMax;
    }

    /**
     * Imposta il valore della proprieta numIngrassoMax.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumIngrassoMax(Integer value) {
        this.numIngrassoMax = value;
    }

    /**
     * Recupera il valore della proprieta numIngrassoPresenti.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumIngrassoPresenti() {
        return numIngrassoPresenti;
    }

    /**
     * Imposta il valore della proprieta numIngrassoPresenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumIngrassoPresenti(Integer value) {
        this.numIngrassoPresenti = value;
    }

    /**
     * Recupera il valore della proprieta numLattanzoliMax.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumLattanzoliMax() {
        return numLattanzoliMax;
    }

    /**
     * Imposta il valore della proprieta numLattanzoliMax.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumLattanzoliMax(Integer value) {
        this.numLattanzoliMax = value;
    }

    /**
     * Recupera il valore della proprieta numLattanzoliPresenti.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumLattanzoliPresenti() {
        return numLattanzoliPresenti;
    }

    /**
     * Imposta il valore della proprieta numLattanzoliPresenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumLattanzoliPresenti(Integer value) {
        this.numLattanzoliPresenti = value;
    }

    /**
     * Recupera il valore della proprieta numOvaioleMax.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumOvaioleMax() {
        return numOvaioleMax;
    }

    /**
     * Imposta il valore della proprieta numOvaioleMax.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumOvaioleMax(Integer value) {
        this.numOvaioleMax = value;
    }

    /**
     * Recupera il valore della proprieta numOvaiolePresenti.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumOvaiolePresenti() {
        return numOvaiolePresenti;
    }

    /**
     * Imposta il valore della proprieta numOvaiolePresenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumOvaiolePresenti(Integer value) {
        this.numOvaiolePresenti = value;
    }

    /**
     * Recupera il valore della proprieta numScrofeMax.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumScrofeMax() {
        return numScrofeMax;
    }

    /**
     * Imposta il valore della proprieta numScrofeMax.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumScrofeMax(Integer value) {
        this.numScrofeMax = value;
    }

    /**
     * Recupera il valore della proprieta numScrofePresenti.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumScrofePresenti() {
        return numScrofePresenti;
    }

    /**
     * Imposta il valore della proprieta numScrofePresenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumScrofePresenti(Integer value) {
        this.numScrofePresenti = value;
    }

    /**
     * Recupera il valore della proprieta numSuinettiMax.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumSuinettiMax() {
        return numSuinettiMax;
    }

    /**
     * Imposta il valore della proprieta numSuinettiMax.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumSuinettiMax(Integer value) {
        this.numSuinettiMax = value;
    }

    /**
     * Recupera il valore della proprieta numSuinettiPresenti.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumSuinettiPresenti() {
        return numSuinettiPresenti;
    }

    /**
     * Imposta il valore della proprieta numSuinettiPresenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumSuinettiPresenti(Integer value) {
        this.numSuinettiPresenti = value;
    }

    /**
     * Recupera il valore della proprieta numVerriMax.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumVerriMax() {
        return numVerriMax;
    }

    /**
     * Imposta il valore della proprieta numVerriMax.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumVerriMax(Integer value) {
        this.numVerriMax = value;
    }

    /**
     * Recupera il valore della proprieta numVerriPresenti.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumVerriPresenti() {
        return numVerriPresenti;
    }

    /**
     * Imposta il valore della proprieta numVerriPresenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumVerriPresenti(Integer value) {
        this.numVerriPresenti = value;
    }

    /**
     * Recupera il valore della proprieta numVitelli8Sett.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public String getNumVitelli8Sett() {
        return numVitelli8Sett;
    }

    /**
     * Imposta il valore della proprieta numVitelli8Sett.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumVitelli8Sett(String value) {
        this.numVitelli8Sett = value;
    }

    /**
     * Recupera il valore della proprieta numVitelliMax.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public String getNumVitelliMax() {
        return numVitelliMax;
    }

    /**
     * Imposta il valore della proprieta numVitelliMax.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumVitelliMax(String value) {
        this.numVitelliMax = value;
    }

    /**
     * Recupera il valore della proprieta numVitelliPresenti.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public String getNumVitelliPresenti() {
        return numVitelliPresenti;
    }

    /**
     * Imposta il valore della proprieta numVitelliPresenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumVitelliPresenti(String value) {
        this.numVitelliPresenti = value;
    }

    /**
     * Recupera il valore della proprieta prescrizioni.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrescrizioni() {
        return prescrizioni;
    }

    /**
     * Imposta il valore della proprieta prescrizioni.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrescrizioni(String value) {
        this.prescrizioni = value;
    }

    /**
     * Recupera il valore della proprieta requisitiXml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequisitiXml() {
        return requisitiXml;
    }

    /**
     * Imposta il valore della proprieta requisitiXml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequisitiXml(String value) {
        this.requisitiXml = value;
    }

    /**
     * Recupera il valore della proprieta secondoControllore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondoControllore() {
        return secondoControllore;
    }

    /**
     * Imposta il valore della proprieta secondoControllore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondoControllore(String value) {
        this.secondoControllore = value;
    }

    /**
     * Recupera il valore della proprieta tipoAllegato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAllegato() {
        return tipoAllegato;
    }

    /**
     * Imposta il valore della proprieta tipoAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAllegato(String value) {
        this.tipoAllegato = value;
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

    public String getCritCodice() {
		return critCodice;
	}

	public void setCritCodice(String critCodice) {
		this.critCodice = critCodice;
	}

	public String getCriterioControlloAltro() {
		return criterioControlloAltro;
	}

	public void setCriterioControlloAltro(String criterioControlloAltro) {
		this.criterioControlloAltro = criterioControlloAltro;
	}

	public String getFlagIntenzionalita() {
		return flagIntenzionalita;
	}

	public void setFlagIntenzionalita(String flagIntenzionalita) {
		this.flagIntenzionalita = flagIntenzionalita;
	}

	public String getNomeRappresentante() {
		return nomeRappresentante;
	}

	public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	}

	public String getNomeRappresentanteVer() {
		return nomeRappresentanteVer;
	}

	public void setNomeRappresentanteVer(String nomeRappresentanteVer) {
		this.nomeRappresentanteVer = nomeRappresentanteVer;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public String getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(String veterinario) {
		this.veterinario = veterinario;
	}

	public String getNumClb() {
		return numClb;
	}

	public void setNumClb(String numClb) {
		this.numClb = numClb;
	}

	public String getrScrofeMorteAnno() {
		return rScrofeMorteAnno;
	}

	public void setrScrofeMorteAnno(String rScrofeMorteAnno) {
		this.rScrofeMorteAnno = rScrofeMorteAnno;
	}

	public String getrSuinettiSvezzatiAnno() {
		return rSuinettiSvezzatiAnno;
	}

	public void setrSuinettiSvezzatiAnno(String rSuinettiSvezzatiAnno) {
		this.rSuinettiSvezzatiAnno = rSuinettiSvezzatiAnno;
	}

	public String getsSuiniPresenti() {
		return sSuiniPresenti;
	}

	public void setsSuiniPresenti(String sSuiniPresenti) {
		this.sSuiniPresenti = sSuiniPresenti;
	}

	public String getsSuiniMorti() {
		return sSuiniMorti;
	}

	public void setsSuiniMorti(String sSuiniMorti) {
		this.sSuiniMorti = sSuiniMorti;
	}

	public String getsFlagTuttoPieno() {
		return sFlagTuttoPieno;
	}

	public void setsFlagTuttoPieno(String sFlagTuttoPieno) {
		this.sFlagTuttoPieno = sFlagTuttoPieno;
	}

	public String getsNumCicli() {
		return sNumCicli;
	}

	public void setsNumCicli(String sNumCicli) {
		this.sNumCicli = sNumCicli;
	}

	public String getsNumCapiCiclo() {
		return sNumCapiCiclo;
	}

	public void setsNumCapiCiclo(String sNumCapiCiclo) {
		this.sNumCapiCiclo = sNumCapiCiclo;
	}

	public String getsBoxTipo1() {
		return sBoxTipo1;
	}

	public void setsBoxTipo1(String sBoxTipo1) {
		this.sBoxTipo1 = sBoxTipo1;
	}

	public String getsBoxTipo2() {
		return sBoxTipo2;
	}

	public void setsBoxTipo2(String sBoxTipo2) {
		this.sBoxTipo2 = sBoxTipo2;
	}

	public String getsBoxTipo3() {
		return sBoxTipo3;
	}

	public void setsBoxTipo3(String sBoxTipo3) {
		this.sBoxTipo3 = sBoxTipo3;
	}

	public String getsBoxTipo4() {
		return sBoxTipo4;
	}

	public void setsBoxTipo4(String sBoxTipo4) {
		this.sBoxTipo4 = sBoxTipo4;
	}

	public String getsBoxTipo5() {
		return sBoxTipo5;
	}

	public void setsBoxTipo5(String sBoxTipo5) {
		this.sBoxTipo5 = sBoxTipo5;
	}

	public String getiSuiniPresenti() {
		return iSuiniPresenti;
	}

	public void setiSuiniPresenti(String iSuiniPresenti) {
		this.iSuiniPresenti = iSuiniPresenti;
	}

	public String getiSuiniMorti() {
		return iSuiniMorti;
	}

	public void setiSuiniMorti(String iSuiniMorti) {
		this.iSuiniMorti = iSuiniMorti;
	}

	public String getiFlagTuttoPieno() {
		return iFlagTuttoPieno;
	}

	public void setiFlagTuttoPieno(String iFlagTuttoPieno) {
		this.iFlagTuttoPieno = iFlagTuttoPieno;
	}

	public String getiNumCicli() {
		return iNumCicli;
	}

	public void setiNumCicli(String iNumCicli) {
		this.iNumCicli = iNumCicli;
	}

	public String getiNumCapiCiclo() {
		return iNumCapiCiclo;
	}

	public void setiNumCapiCiclo(String iNumCapiCiclo) {
		this.iNumCapiCiclo = iNumCapiCiclo;
	}

	public String getiBoxTipo1() {
		return iBoxTipo1;
	}

	public void setiBoxTipo1(String iBoxTipo1) {
		this.iBoxTipo1 = iBoxTipo1;
	}

	public String getiBoxTipo2() {
		return iBoxTipo2;
	}

	public void setiBoxTipo2(String iBoxTipo2) {
		this.iBoxTipo2 = iBoxTipo2;
	}

	public String getiBoxTipo3() {
		return iBoxTipo3;
	}

	public void setiBoxTipo3(String iBoxTipo3) {
		this.iBoxTipo3 = iBoxTipo3;
	}

	public String getiBoxTipo4() {
		return iBoxTipo4;
	}

	public void setiBoxTipo4(String iBoxTipo4) {
		this.iBoxTipo4 = iBoxTipo4;
	}

	public String getiBoxTipo5() {
		return iBoxTipo5;
	}

	public void setiBoxTipo5(String iBoxTipo5) {
		this.iBoxTipo5 = iBoxTipo5;
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

	public String getCapiCodaTagliata() {
		return capiCodaTagliata;
	}

	public void setCapiCodaTagliata(String capiCodaTagliata) {
		this.capiCodaTagliata = capiCodaTagliata;
	}

	public String getGruppiCodaTagliata() {
		return gruppiCodaTagliata;
	}

	public void setGruppiCodaTagliata(String gruppiCodaTagliata) {
		this.gruppiCodaTagliata = gruppiCodaTagliata;
	}

	public String getProduzioniTipiche() {
		return produzioniTipiche;
	}

	public void setProduzioniTipiche(String produzioniTipiche) {
		this.produzioniTipiche = produzioniTipiche;
	}

	public String getUsoAnestetici() {
		return usoAnestetici;
	}

	public void setUsoAnestetici(String usoAnestetici) {
		this.usoAnestetici = usoAnestetici;
	}

	public String getPresenzaManuale() {
		return presenzaManuale;
	}

	public void setPresenzaManuale(String presenzaManuale) {
		this.presenzaManuale = presenzaManuale;
	}

	public String getNumUovaAnno() {
		return numUovaAnno;
	}

	public void setNumUovaAnno(String numUovaAnno) {
		this.numUovaAnno = numUovaAnno;
	}

	public String getFlagSelImbal() {
		return flagSelImbal;
	}

	public void setFlagSelImbal(String flagSelImbal) {
		this.flagSelImbal = flagSelImbal;
	}

	public String getIndirizzoSelImbal() {
		return indirizzoSelImbal;
	}

	public void setIndirizzoSelImbal(String indirizzoSelImbal) {
		this.indirizzoSelImbal = indirizzoSelImbal;
	}

	public String getMutaInAllev() {
		return mutaInAllev;
	}

	public void setMutaInAllev(String mutaInAllev) {
		this.mutaInAllev = mutaInAllev;
	}

	public String getNumCapiLattazione() {
		return numCapiLattazione;
	}

	public void setNumCapiLattazione(String numCapiLattazione) {
		this.numCapiLattazione = numCapiLattazione;
	}

	public String getNumCapiAsciutta() {
		return numCapiAsciutta;
	}

	public void setNumCapiAsciutta(String numCapiAsciutta) {
		this.numCapiAsciutta = numCapiAsciutta;
	}

	public String getNumManze() {
		return numManze;
	}

	public void setNumManze(String numManze) {
		this.numManze = numManze;
	}

	public String getNumIngrasso() {
		return numIngrasso;
	}

	public void setNumIngrasso(String numIngrasso) {
		this.numIngrasso = numIngrasso;
	}

	public String getNumTori() {
		return numTori;
	}

	public void setNumTori(String numTori) {
		this.numTori = numTori;
	}

	public String getKgLatte() {
		return kgLatte;
	}

	public void setKgLatte(String kgLatte) {
		this.kgLatte = kgLatte;
	}

	public String getFlagStabulazione() {
		return flagStabulazione;
	}

	public void setFlagStabulazione(String flagStabulazione) {
		this.flagStabulazione = flagStabulazione;
	}

}
