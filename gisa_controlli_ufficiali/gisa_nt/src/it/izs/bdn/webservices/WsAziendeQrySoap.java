/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * WsAziendeQrySoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public interface WsAziendeQrySoap extends java.rmi.Remote {

    /**
     * Restituisce il dettaglio delle aziende.
     */
    public it.izs.bdn.webservices.GetAziendaResponseGetAziendaResult getAzienda(java.lang.String p_azienda_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce il dettaglio delle aziende.
     */
    public java.lang.String getAzienda_STR(java.lang.String p_azienda_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce le informazioni relative ad una persona.
     */
    public it.izs.bdn.webservices.GetPersonaResponseGetPersonaResult getPersona(java.lang.String p_persona_idfiscale) throws java.rmi.RemoteException;

    /**
     * Restituisce le informazioni relative ad una persona.
     */
    public java.lang.String getPersona_STR(java.lang.String p_persona_idfiscale) throws java.rmi.RemoteException;

    /**
     * Restituisce le informazioni sanitarie relative ad un'azienda.
     */
    public it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult getInfoSanitarie(java.lang.String p_azienda_codice, java.lang.String p_malattia_codice, java.lang.String p_dt_rilevazione) throws java.rmi.RemoteException;

    /**
     * Restituisce le informazioni sanitarie relative ad un'azienda.
     */
    public java.lang.String getInfoSanitarie_STR(java.lang.String p_azienda_codice, java.lang.String p_malattia_codice, java.lang.String p_dt_rilevazione) throws java.rmi.RemoteException;

    /**
     * Restituisce le informazioni di un soccidario.
     */
    public it.izs.bdn.webservices.GetSoccidariResponseGetSoccidariResult getSoccidari(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico) throws java.rmi.RemoteException;

    /**
     * Restituisce le informazioni di un soccidario.
     */
    public java.lang.String getSoccidari_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico) throws java.rmi.RemoteException;

    /**
     * Restituisce il dettaglio di un allevamento.
     */
    public it.izs.bdn.webservices.GetInfoAllevamentoResponseGetInfoAllevamentoResult getInfoAllevamento(java.lang.String p_allev_id) throws java.rmi.RemoteException;

    /**
     * Restituisce il dettaglio di un allevamento.
     */
    public java.lang.String getInfoAllevamento_STR(java.lang.String p_allev_id) throws java.rmi.RemoteException;

    /**
     * Restituisce il dettaglio di un'azienda.
     */
    public it.izs.bdn.webservices.GetInfoAziendaResponseGetInfoAziendaResult getInfoAzienda(java.lang.String p_azienda_id) throws java.rmi.RemoteException;

    /**
     * Restituisce il dettaglio di un'azienda.
     */
    public java.lang.String getInfoAzienda_STR(java.lang.String p_azienda_id) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati relativi ad un allevamento.
     */
    public it.izs.bdn.webservices.GetAllevamentoResponseGetAllevamentoResult getAllevamento(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati di un allevamento se autorizzato alla produzione
     * di latte alimentare.
     */
    public it.izs.bdn.webservices.GetAllevamento_LatteResponseGetAllevamento_LatteResult getAllevamento_Latte(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati di un allevamento se autorizzato alla produzione
     * di latte alimentare.
     */
    public java.lang.String getAllevamento_Latte_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati relativi ad un allevamento.
     */
    public java.lang.String getAllevamento_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce la tipologia produttiva associata ad un allevamento
     * ovicaprino.
     */
    public it.izs.bdn.webservices.FindOviTipologieProduttiveResponseFindOviTipologieProduttiveResult findOviTipologieProduttive(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce la tipologia produttiva associata ad un allevamento
     * ovicaprino.
     */
    public java.lang.String findOviTipologieProduttive_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce la tipologia produttiva associata ad un allevamento
     * suino.
     */
    public it.izs.bdn.webservices.FindSuiTipologieProduttiveResponseFindSuiTipologieProduttiveResult findSuiTipologieProduttive(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce la tipologia produttiva associata ad un allevamento
     * suino.
     */
    public java.lang.String findSuiTipologieProduttive_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati del censimento di un allevamento ovicaprino.
     */
    public it.izs.bdn.webservices.FindOviCensimentiResponseFindOviCensimentiResult findOviCensimenti(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati del censimento di un allevamento ovicaprino.
     */
    public java.lang.String findOviCensimenti_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati del censimento di un allevamento suino.
     */
    public it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult findSuiCensimenti(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati del censimento di un allevamento suino.
     */
    public java.lang.String findSuiCensimenti_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati del censimento di un allevamento avicolo.
     */
    public it.izs.bdn.webservices.FindAviCensimentiResponseFindAviCensimentiResult findAviCensimenti(java.lang.String p_allev_id_fiscale, java.lang.String p_azienda_codice, java.lang.String p_specie_codice, java.lang.String p_detent_id_fiscale, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati del censimento di un allevamento avicolo.
     */
    public java.lang.String findAviCensimenti_STR(java.lang.String p_allev_id_fiscale, java.lang.String p_azienda_codice, java.lang.String p_specie_codice, java.lang.String p_detent_id_fiscale, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati di un allevamento avicolo.
     */
    public it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult getAllevamento_AVI(java.lang.String p_azienda_codice, java.lang.String p_prop_id_fiscale, java.lang.String p_det_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati di un allevamento avicolo.
     */
    public java.lang.String getAllevamento_AVI_STR(java.lang.String p_azienda_codice, java.lang.String p_prop_id_fiscale, java.lang.String p_det_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati di un allevamento avicolo.
     */
    public it.izs.bdn.webservices.FindAllevamento_AVIResponseFindAllevamento_AVIResult findAllevamento_AVI(java.lang.String p_azienda_codice, java.lang.String p_prop_id_fiscale, java.lang.String p_det_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException;

    /**
     * Restituisce la lista degli allevamenti.
     */
    public it.izs.bdn.webservices.Get_ListaAllevamentiResponseGet_ListaAllevamentiResult get_ListaAllevamenti(java.lang.String p_codice_capo) throws java.rmi.RemoteException;

    /**
     * Restituisce la lista degli allevamenti.
     */
    public java.lang.String get_ListaAllevamenti_STR(java.lang.String p_codice_capo) throws java.rmi.RemoteException;

    /**
     * Estrae tutti i dati relativi ad un allevamento iscritto a libro
     * genealogico. Se l’ allevamento non risulta iscritto, il metodo non
     * restituisce nulla. L’allevamento viene ricercato tramite chiave logica
     * composta da codice azienda, codice specie, codice fiscale dell’allevamento.
     */
    public it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult getAllevamento_A_Libro(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce la lista degli allevamenti.
     */
    public java.lang.String getAllevamento_A_Libro_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException;

    /**
     * dato un punto geografico individuato da latitudine e longitudine,
     * dato un raggio non superiore a 10 km, recupera gli allevamenti di
     * una certa specie situati all'interno della circonferenza con centro
     * nel punto e raggio il raggio inserito.
     */
    public it.izs.bdn.webservices.FindAllevamentoNelRaggioResponseFindAllevamentoNelRaggioResult findAllevamentoNelRaggio(java.lang.String p_specie_codice, java.lang.String p_latitudine, java.lang.String p_longitudine, java.lang.String p_raggio) throws java.rmi.RemoteException;

    /**
     * dato un punto geografico individuato da latitudine e longitudine,
     * dato un raggio non superiore a 10 km, recupera gli allevamenti di
     * una certa specie situati all'interno della circonferenza con centro
     * nel punto e raggio il raggio inserito
     */
    public java.lang.String findAllevamentoNelRaggio_STR(java.lang.String p_specie_codice, java.lang.String p_latitudine, java.lang.String p_longitudine, java.lang.String p_raggio) throws java.rmi.RemoteException;

    /**
     * Restituisce il dettaglio di un allevamento.
     */
    public it.izs.bdn.webservices.FindAllevamentoResponseFindAllevamentoResult findAllevamento(java.lang.String p_azienda_codice, java.lang.String p_denominazione, java.lang.String p_specie_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce il dettaglio di un allevamento.
     */
    public it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult findAllev_Tipologie_Prod(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_specie_codice, java.lang.String p_cod_tipo_tipologia_prod) throws java.rmi.RemoteException;

    /**
     * Restituisce il dettaglio di un allevamento.
     */
    public java.lang.String findAllev_Tipologie_Prod_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_specie_codice, java.lang.String p_cod_tipo_tipologia_prod) throws java.rmi.RemoteException;

    /**
     * Restituisce il dettaglio di un allevamento.
     */
    public java.lang.String findAllevamento_STR(java.lang.String p_azienda_codice, java.lang.String p_denominazione, java.lang.String p_specie_codice) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati relativi all'azienda, all'allevamento ed
     * i capi in esso presenti.
     */
    public it.izs.bdn.webservices.Estrazione_AllevamentiResponseEstrazione_AllevamentiResult estrazione_Allevamenti(java.lang.String strDelega) throws java.rmi.RemoteException;

    /**
     * Restituisce i dati relativi all'azienda, all'allevamento ed
     * i capi in esso presenti.
     */
    public java.lang.String estrazione_Allevamenti_STR(java.lang.String strDelega) throws java.rmi.RemoteException;

    /**
     * Estrae i controlli effettuati presso un allevamento. Restituisce
     * una stringa
     */
    public java.lang.String findControlli_Allevamento_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_dt_controllo) throws java.rmi.RemoteException;

    /**
     * Estrae i controlli effettuati presso un allevamento.
     */
    public it.izs.bdn.webservices.FindControlli_AllevamentoResponseFindControlli_AllevamentoResult findControlli_Allevamento(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_dt_controllo) throws java.rmi.RemoteException;
}
