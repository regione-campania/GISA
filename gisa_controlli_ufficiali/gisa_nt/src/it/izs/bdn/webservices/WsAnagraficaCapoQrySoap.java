/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * WsAnagraficaCapoQrySoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.izs.bdn.webservices;

public interface WsAnagraficaCapoQrySoap extends java.rmi.Remote {

    /**
     * Estrae i dati di un capo bovino a partire dall'identificativo
     * elettronico.
     */
    public it.izs.bdn.webservices.GetCapoTAGResponseGetCapoTAGResult getCapoTAG(java.lang.String p_TAG) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo bovino.
     */
    public it.izs.bdn.webservices.GetCapoResponseGetCapoResult getCapo(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo bovino.
     */
    public java.lang.String getCapo_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo bovino a partire dall'identificativo
     * elettronico.Restituisce un tipo stringa.
     */
    public java.lang.String getCapoTAG_STR(java.lang.String p_TAG) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo ovino.
     */
    public it.izs.bdn.webservices.GetCapoOvinoResponseGetCapoOvinoResult getCapoOvino(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo bovino.
     */
    public java.lang.String getCapoOvino_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi a un capo bovino iscritto a libro. Se
     * il capo non risulta iscritto non restituisce alcun dato.
     */
    public it.izs.bdn.webservices.GetCapoALibroResponseGetCapoALibroResult getCapoALibro(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi a un capo bovino iscritto a libro. Se
     * il capo non risulta iscritto non restituisce alcun dato.
     */
    public java.lang.String getCapoALibro_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo bovino macellato.
     */
    public it.izs.bdn.webservices.GetCapoMacellatoResponseGetCapoMacellatoResult getCapoMacellato(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo bovino macellato.
     */
    public java.lang.String getCapoMacellato_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo ovino macellato.
     */
    public it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult getCapoOvinoMacellato(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo ovino macellato.
     */
    public java.lang.String getCapoOvinoMacellato_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo presente nell'allevamento.
     */
    public it.izs.bdn.webservices.Get_Capi_AllevamentoResponseGet_Capi_AllevamentoResult get_Capi_Allevamento(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo presente nell'allevamento.
     */
    public java.lang.String get_Capi_Allevamento_STR(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo presente nell'allevamento.
     */
    public it.izs.bdn.webservices.Get_Capi_Allevamento_PeriodoResponseGet_Capi_Allevamento_PeriodoResult get_Capi_Allevamento_Periodo(java.lang.String p_allev_id, java.lang.String p_cod_capo, java.lang.String p_data_dal, java.lang.String p_data_al) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo presente nell'allevamento.
     */
    public java.lang.String get_Capi_Allevamento_Periodo_STR(java.lang.String p_allev_id, java.lang.String p_cod_capo, java.lang.String p_data_dal, java.lang.String p_data_al) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo presente nell'allevamento.
     */
    public it.izs.bdn.webservices.GetCapiOviniAllevamentoResponseGetCapiOviniAllevamentoResult getCapiOviniAllevamento(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo presente nell'allevamento.
     */
    public java.lang.String getCapiOviniAllevamento_STR(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo presente nell'allevamento.
     */
    public it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult get_Capi_Allevamento_Cod(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo presente nell'allevamento.
     */
    public java.lang.String get_Capi_Allevamento_Cod_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo, java.lang.String p_data_dal, java.lang.String p_data_al) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo presente nell'allevamento.
     */
    public it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult getCapiOviniAllevamento_Cod(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo presente nell'allevamento.
     */
    public java.lang.String getCapiOviniAllevamento_Cod_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi ad i capi presenti nell'allevamento
     * nel mese corrente.
     */
    public it.izs.bdn.webservices.GetInfoCapiAllevamentoResponseGetInfoCapiAllevamentoResult getInfoCapiAllevamento(java.lang.String p_allev_id) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi ad i capi presenti nell'allevamento
     * nel mese corrente.
     */
    public java.lang.String getInfoCapiAllevamento_STR(java.lang.String p_allev_id) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi ad i capi presenti nell'allevamento
     * nel mese corrente.
     */
    public it.izs.bdn.webservices.GetInfoCapiOviniAllevamentoResponseGetInfoCapiOviniAllevamentoResult getInfoCapiOviniAllevamento(java.lang.String p_allev_id) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi ad i capi presenti nell'allevamento
     * nel mese corrente.
     */
    public java.lang.String getInfoCapiOviniAllevamento_STR(java.lang.String p_allev_id) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi a i capi che ad una certa data hanno
     * le caratteristiche per diventare madre.
     */
    public it.izs.bdn.webservices.GetMadriResponseGetMadriResult getMadri(java.lang.String p_allev_id, java.lang.String p_data) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi a i capi che ad una certa data hanno
     * le caratteristiche per diventare madre.
     */
    public java.lang.String getMadri_STR(java.lang.String p_allev_id, java.lang.String p_data) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi ai decessi di un allevamento.
     */
    public it.izs.bdn.webservices.GetDecessoResponseGetDecessoResult getDecesso(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi ai decessi di un allevamento.
     */
    public java.lang.String getDecesso_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi al decesso di un ovino.
     */
    public it.izs.bdn.webservices.GetDecessoOvinoResponseGetDecessoOvinoResult getDecessoOvino(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi al decesso di un ovino.
     */
    public java.lang.String getDecessoOvino_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un furto bovino.
     */
    public it.izs.bdn.webservices.GetFurtoResponseGetFurtoResult getFurto(java.lang.String p_codice_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un furto bovino.
     */
    public java.lang.String getFurto_STR(java.lang.String p_codice_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un furto ovino.
     */
    public it.izs.bdn.webservices.GetFurtoOvinoResponseGetFurtoOvinoResult getFurtoOvino(java.lang.String p_codice_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un furto ovino.
     */
    public java.lang.String getFurtoOvino_STR(java.lang.String p_codice_capo) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo macellato.
     */
    public it.izs.bdn.webservices.FindCapoMacellatoResponseFindCapoMacellatoResult findCapoMacellato(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo macellato.
     */
    public java.lang.String findCapoMacellato_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi alla macellazione di capi ovini.
     */
    public it.izs.bdn.webservices.FindCapiOviniMacellatiResponseFindCapiOviniMacellatiResult findCapiOviniMacellati(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati relativi alla macellazione di capi ovini.
     */
    public java.lang.String findCapiOviniMacellati_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di una cancellazione amministrativa per un capo
     * bovino.
     */
    public it.izs.bdn.webservices.GetCancellazione_AmministrativaResponseGetCancellazione_AmministrativaResult getCancellazione_Amministrativa(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di una cancellazione amministrativa per un capo
     * bovino. Restituisce una stringa
     */
    public java.lang.String getCancellazione_Amministrativa_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException;

    /**
     * Estrae i dati riguardanti un capo sentinella.
     */
    public it.izs.bdn.webservices.Get_Capo_SentinellaResponseGet_Capo_SentinellaResult get_Capo_Sentinella(java.lang.String p_codice_elettronico, java.lang.String p_ident_nome, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException;

    /**
     * Estrae i dati riguardanti un capo sentinella. Restituisce una
     * stringa
     */
    public java.lang.String get_Capo_Sentinella_STR(java.lang.String p_codice_elettronico, java.lang.String p_ident_nome, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException;

    /**
     * Estrae i dati riguardanti un capo equino macellato.
     */
    public it.izs.bdn.webservices.Get_Capo_Equino_MacellatoResponseGet_Capo_Equino_MacellatoResult get_Capo_Equino_Macellato(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException;

    /**
     * Estrae i dati riguardanti un capo equino macellato. Restituisce
     * una stringa
     */
    public java.lang.String get_Capo_Equino_Macellato_STR(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException;

    /**
     * Estrae i dati riguardanti un capo equino macellato.
     */
    public it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult find_Capi_Equini_Macellati(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException;

    /**
     * Estrae i dati riguardanti un capo equino macellato. Restituisce
     * una stringa
     */
    public java.lang.String find_Capi_Equini_Macellati_STR(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException;

    /**
     * Estrae i dati di un capo ovino.
     */
    public it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult getCanc_Amm_Ovini(java.lang.String p_capo_codice) throws java.rmi.RemoteException;
}
