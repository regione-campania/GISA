/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.bdn.webservices;

public class WsAziendeQrySoapProxy implements it.izs.bdn.webservices.WsAziendeQrySoap {
  private String _endpoint = null;
  private it.izs.bdn.webservices.WsAziendeQrySoap wsAziendeQrySoap = null;
  
  public WsAziendeQrySoapProxy() {
    _initWsAziendeQrySoapProxy();
  }
  
  public WsAziendeQrySoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initWsAziendeQrySoapProxy();
  }
  
  private void _initWsAziendeQrySoapProxy() {
    try {
      wsAziendeQrySoap = (new it.izs.bdn.webservices.WsAziendeQryLocator()).getwsAziendeQrySoap();
      if (wsAziendeQrySoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wsAziendeQrySoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wsAziendeQrySoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wsAziendeQrySoap != null)
      ((javax.xml.rpc.Stub)wsAziendeQrySoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.izs.bdn.webservices.WsAziendeQrySoap getWsAziendeQrySoap() {
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap;
  }
  
  public it.izs.bdn.webservices.GetAziendaResponseGetAziendaResult getAzienda(java.lang.String p_azienda_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getAzienda(p_azienda_codice);
  }
  
  public java.lang.String getAzienda_STR(java.lang.String p_azienda_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getAzienda_STR(p_azienda_codice);
  }
  
  public it.izs.bdn.webservices.GetPersonaResponseGetPersonaResult getPersona(java.lang.String p_persona_idfiscale) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getPersona(p_persona_idfiscale);
  }
  
  public java.lang.String getPersona_STR(java.lang.String p_persona_idfiscale) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getPersona_STR(p_persona_idfiscale);
  }
  
  public it.izs.bdn.webservices.GetInfoSanitarieResponseGetInfoSanitarieResult getInfoSanitarie(java.lang.String p_azienda_codice, java.lang.String p_malattia_codice, java.lang.String p_dt_rilevazione) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getInfoSanitarie(p_azienda_codice, p_malattia_codice, p_dt_rilevazione);
  }
  
  public java.lang.String getInfoSanitarie_STR(java.lang.String p_azienda_codice, java.lang.String p_malattia_codice, java.lang.String p_dt_rilevazione) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getInfoSanitarie_STR(p_azienda_codice, p_malattia_codice, p_dt_rilevazione);
  }
  
  public it.izs.bdn.webservices.GetSoccidariResponseGetSoccidariResult getSoccidari(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getSoccidari(p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico);
  }
  
  public java.lang.String getSoccidari_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getSoccidari_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico);
  }
  
  public it.izs.bdn.webservices.GetInfoAllevamentoResponseGetInfoAllevamentoResult getInfoAllevamento(java.lang.String p_allev_id) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getInfoAllevamento(p_allev_id);
  }
  
  public java.lang.String getInfoAllevamento_STR(java.lang.String p_allev_id) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getInfoAllevamento_STR(p_allev_id);
  }
  
  public it.izs.bdn.webservices.GetInfoAziendaResponseGetInfoAziendaResult getInfoAzienda(java.lang.String p_azienda_id) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getInfoAzienda(p_azienda_id);
  }
  
  public java.lang.String getInfoAzienda_STR(java.lang.String p_azienda_id) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getInfoAzienda_STR(p_azienda_id);
  }
  
  public it.izs.bdn.webservices.GetAllevamentoResponseGetAllevamentoResult getAllevamento(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getAllevamento(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public it.izs.bdn.webservices.GetAllevamento_LatteResponseGetAllevamento_LatteResult getAllevamento_Latte(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getAllevamento_Latte(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public java.lang.String getAllevamento_Latte_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getAllevamento_Latte_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public java.lang.String getAllevamento_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getAllevamento_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public it.izs.bdn.webservices.FindOviTipologieProduttiveResponseFindOviTipologieProduttiveResult findOviTipologieProduttive(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findOviTipologieProduttive(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public java.lang.String findOviTipologieProduttive_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findOviTipologieProduttive_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public it.izs.bdn.webservices.FindSuiTipologieProduttiveResponseFindSuiTipologieProduttiveResult findSuiTipologieProduttive(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findSuiTipologieProduttive(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public java.lang.String findSuiTipologieProduttive_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findSuiTipologieProduttive_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public it.izs.bdn.webservices.FindOviCensimentiResponseFindOviCensimentiResult findOviCensimenti(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findOviCensimenti(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public java.lang.String findOviCensimenti_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findOviCensimenti_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public it.izs.bdn.webservices.FindSuiCensimentiResponseFindSuiCensimentiResult findSuiCensimenti(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findSuiCensimenti(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public java.lang.String findSuiCensimenti_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findSuiCensimenti_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public it.izs.bdn.webservices.FindAviCensimentiResponseFindAviCensimentiResult findAviCensimenti(java.lang.String p_allev_id_fiscale, java.lang.String p_azienda_codice, java.lang.String p_specie_codice, java.lang.String p_detent_id_fiscale, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findAviCensimenti(p_allev_id_fiscale, p_azienda_codice, p_specie_codice, p_detent_id_fiscale, p_tipo_prod, p_orientamento, p_tipo_allev);
  }
  
  public java.lang.String findAviCensimenti_STR(java.lang.String p_allev_id_fiscale, java.lang.String p_azienda_codice, java.lang.String p_specie_codice, java.lang.String p_detent_id_fiscale, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findAviCensimenti_STR(p_allev_id_fiscale, p_azienda_codice, p_specie_codice, p_detent_id_fiscale, p_tipo_prod, p_orientamento, p_tipo_allev);
  }
  
  public it.izs.bdn.webservices.GetAllevamento_AVIResponseGetAllevamento_AVIResult getAllevamento_AVI(java.lang.String p_azienda_codice, java.lang.String p_prop_id_fiscale, java.lang.String p_det_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getAllevamento_AVI(p_azienda_codice, p_prop_id_fiscale, p_det_id_fiscale, p_spe_codice, p_tipo_prod, p_orientamento, p_tipo_allev);
  }
  
  public java.lang.String getAllevamento_AVI_STR(java.lang.String p_azienda_codice, java.lang.String p_prop_id_fiscale, java.lang.String p_det_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getAllevamento_AVI_STR(p_azienda_codice, p_prop_id_fiscale, p_det_id_fiscale, p_spe_codice, p_tipo_prod, p_orientamento, p_tipo_allev);
  }
  
  public it.izs.bdn.webservices.FindAllevamento_AVIResponseFindAllevamento_AVIResult findAllevamento_AVI(java.lang.String p_azienda_codice, java.lang.String p_prop_id_fiscale, java.lang.String p_det_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_tipo_prod, java.lang.String p_orientamento, java.lang.String p_tipo_allev) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findAllevamento_AVI(p_azienda_codice, p_prop_id_fiscale, p_det_id_fiscale, p_spe_codice, p_tipo_prod, p_orientamento, p_tipo_allev);
  }
  
  public it.izs.bdn.webservices.Get_ListaAllevamentiResponseGet_ListaAllevamentiResult get_ListaAllevamenti(java.lang.String p_codice_capo) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.get_ListaAllevamenti(p_codice_capo);
  }
  
  public java.lang.String get_ListaAllevamenti_STR(java.lang.String p_codice_capo) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.get_ListaAllevamenti_STR(p_codice_capo);
  }
  
  public it.izs.bdn.webservices.GetAllevamento_A_LibroResponseGetAllevamento_A_LibroResult getAllevamento_A_Libro(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getAllevamento_A_Libro(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public java.lang.String getAllevamento_A_Libro_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.getAllevamento_A_Libro_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice);
  }
  
  public it.izs.bdn.webservices.FindAllevamentoNelRaggioResponseFindAllevamentoNelRaggioResult findAllevamentoNelRaggio(java.lang.String p_specie_codice, java.lang.String p_latitudine, java.lang.String p_longitudine, java.lang.String p_raggio) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findAllevamentoNelRaggio(p_specie_codice, p_latitudine, p_longitudine, p_raggio);
  }
  
  public java.lang.String findAllevamentoNelRaggio_STR(java.lang.String p_specie_codice, java.lang.String p_latitudine, java.lang.String p_longitudine, java.lang.String p_raggio) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findAllevamentoNelRaggio_STR(p_specie_codice, p_latitudine, p_longitudine, p_raggio);
  }
  
  public it.izs.bdn.webservices.FindAllevamentoResponseFindAllevamentoResult findAllevamento(java.lang.String p_azienda_codice, java.lang.String p_denominazione, java.lang.String p_specie_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findAllevamento(p_azienda_codice, p_denominazione, p_specie_codice);
  }
  
  public it.izs.bdn.webservices.FindAllev_Tipologie_ProdResponseFindAllev_Tipologie_ProdResult findAllev_Tipologie_Prod(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_specie_codice, java.lang.String p_cod_tipo_tipologia_prod) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findAllev_Tipologie_Prod(p_azienda_codice, p_allev_id_fiscale, p_specie_codice, p_cod_tipo_tipologia_prod);
  }
  
  public java.lang.String findAllev_Tipologie_Prod_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_specie_codice, java.lang.String p_cod_tipo_tipologia_prod) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findAllev_Tipologie_Prod_STR(p_azienda_codice, p_allev_id_fiscale, p_specie_codice, p_cod_tipo_tipologia_prod);
  }
  
  public java.lang.String findAllevamento_STR(java.lang.String p_azienda_codice, java.lang.String p_denominazione, java.lang.String p_specie_codice) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findAllevamento_STR(p_azienda_codice, p_denominazione, p_specie_codice);
  }
  
  public it.izs.bdn.webservices.Estrazione_AllevamentiResponseEstrazione_AllevamentiResult estrazione_Allevamenti(java.lang.String strDelega) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.estrazione_Allevamenti(strDelega);
  }
  
  public java.lang.String estrazione_Allevamenti_STR(java.lang.String strDelega) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.estrazione_Allevamenti_STR(strDelega);
  }
  
  public java.lang.String findControlli_Allevamento_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_dt_controllo) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findControlli_Allevamento_STR(p_azienda_codice, p_allev_id_fiscale, p_spe_codice, p_dt_controllo);
  }
  
  public it.izs.bdn.webservices.FindControlli_AllevamentoResponseFindControlli_AllevamentoResult findControlli_Allevamento(java.lang.String p_azienda_codice, java.lang.String p_allev_id_fiscale, java.lang.String p_spe_codice, java.lang.String p_dt_controllo) throws java.rmi.RemoteException{
    if (wsAziendeQrySoap == null)
      _initWsAziendeQrySoapProxy();
    return wsAziendeQrySoap.findControlli_Allevamento(p_azienda_codice, p_allev_id_fiscale, p_spe_codice, p_dt_controllo);
  }
  
  
}