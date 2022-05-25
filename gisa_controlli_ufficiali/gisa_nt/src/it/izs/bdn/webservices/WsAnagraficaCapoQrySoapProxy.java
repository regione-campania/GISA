/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.bdn.webservices;

public class WsAnagraficaCapoQrySoapProxy implements it.izs.bdn.webservices.WsAnagraficaCapoQrySoap {
  private String _endpoint = null;
  private it.izs.bdn.webservices.WsAnagraficaCapoQrySoap wsAnagraficaCapoQrySoap = null;
  
  public WsAnagraficaCapoQrySoapProxy() {
    _initWsAnagraficaCapoQrySoapProxy();
  }
  
  public WsAnagraficaCapoQrySoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initWsAnagraficaCapoQrySoapProxy();
  }
  
  private void _initWsAnagraficaCapoQrySoapProxy() {
    try {
      wsAnagraficaCapoQrySoap = (new it.izs.bdn.webservices.WsAnagraficaCapoQryLocator()).getwsAnagraficaCapoQrySoap();
      if (wsAnagraficaCapoQrySoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wsAnagraficaCapoQrySoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wsAnagraficaCapoQrySoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wsAnagraficaCapoQrySoap != null)
      ((javax.xml.rpc.Stub)wsAnagraficaCapoQrySoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.izs.bdn.webservices.WsAnagraficaCapoQrySoap getWsAnagraficaCapoQrySoap() {
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap;
  }
  
  public it.izs.bdn.webservices.GetCapoTAGResponseGetCapoTAGResult getCapoTAG(java.lang.String p_TAG) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapoTAG(p_TAG);
  }
  
  public it.izs.bdn.webservices.GetCapoResponseGetCapoResult getCapo(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapo(p_capo_codice);
  }
  
  public java.lang.String getCapo_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapo_STR(p_capo_codice);
  }
  
  public java.lang.String getCapoTAG_STR(java.lang.String p_TAG) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapoTAG_STR(p_TAG);
  }
  
  public it.izs.bdn.webservices.GetCapoOvinoResponseGetCapoOvinoResult getCapoOvino(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapoOvino(p_capo_codice);
  }
  
  public java.lang.String getCapoOvino_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapoOvino_STR(p_capo_codice);
  }
  
  public it.izs.bdn.webservices.GetCapoALibroResponseGetCapoALibroResult getCapoALibro(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapoALibro(p_capo_codice);
  }
  
  public java.lang.String getCapoALibro_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapoALibro_STR(p_capo_codice);
  }
  
  public it.izs.bdn.webservices.GetCapoMacellatoResponseGetCapoMacellatoResult getCapoMacellato(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapoMacellato(p_capo_codice);
  }
  
  public java.lang.String getCapoMacellato_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapoMacellato_STR(p_capo_codice);
  }
  
  public it.izs.bdn.webservices.GetCapoOvinoMacellatoResponseGetCapoOvinoMacellatoResult getCapoOvinoMacellato(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapoOvinoMacellato(p_capo_codice);
  }
  
  public java.lang.String getCapoOvinoMacellato_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapoOvinoMacellato_STR(p_capo_codice);
  }
  
  public it.izs.bdn.webservices.Get_Capi_AllevamentoResponseGet_Capi_AllevamentoResult get_Capi_Allevamento(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.get_Capi_Allevamento(p_allev_id, p_storico, p_cod_capo);
  }
  
  public java.lang.String get_Capi_Allevamento_STR(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.get_Capi_Allevamento_STR(p_allev_id, p_storico, p_cod_capo);
  }
  
  public it.izs.bdn.webservices.Get_Capi_Allevamento_PeriodoResponseGet_Capi_Allevamento_PeriodoResult get_Capi_Allevamento_Periodo(java.lang.String p_allev_id, java.lang.String p_cod_capo, java.lang.String p_data_dal, java.lang.String p_data_al) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.get_Capi_Allevamento_Periodo(p_allev_id, p_cod_capo, p_data_dal, p_data_al);
  }
  
  public java.lang.String get_Capi_Allevamento_Periodo_STR(java.lang.String p_allev_id, java.lang.String p_cod_capo, java.lang.String p_data_dal, java.lang.String p_data_al) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.get_Capi_Allevamento_Periodo_STR(p_allev_id, p_cod_capo, p_data_dal, p_data_al);
  }
  
  public it.izs.bdn.webservices.GetCapiOviniAllevamentoResponseGetCapiOviniAllevamentoResult getCapiOviniAllevamento(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapiOviniAllevamento(p_allev_id, p_storico, p_cod_capo);
  }
  
  public java.lang.String getCapiOviniAllevamento_STR(java.lang.String p_allev_id, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapiOviniAllevamento_STR(p_allev_id, p_storico, p_cod_capo);
  }
  
  public it.izs.bdn.webservices.Get_Capi_Allevamento_CodResponseGet_Capi_Allevamento_CodResult get_Capi_Allevamento_Cod(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.get_Capi_Allevamento_Cod(p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico, p_cod_capo);
  }
  
  public java.lang.String get_Capi_Allevamento_Cod_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo, java.lang.String p_data_dal, java.lang.String p_data_al) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.get_Capi_Allevamento_Cod_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico, p_cod_capo, p_data_dal, p_data_al);
  }
  
  public it.izs.bdn.webservices.GetCapiOviniAllevamento_CodResponseGetCapiOviniAllevamento_CodResult getCapiOviniAllevamento_Cod(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapiOviniAllevamento_Cod(p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico, p_cod_capo);
  }
  
  public java.lang.String getCapiOviniAllevamento_Cod_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_storico, java.lang.String p_cod_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCapiOviniAllevamento_Cod_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_storico, p_cod_capo);
  }
  
  public it.izs.bdn.webservices.GetInfoCapiAllevamentoResponseGetInfoCapiAllevamentoResult getInfoCapiAllevamento(java.lang.String p_allev_id) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getInfoCapiAllevamento(p_allev_id);
  }
  
  public java.lang.String getInfoCapiAllevamento_STR(java.lang.String p_allev_id) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getInfoCapiAllevamento_STR(p_allev_id);
  }
  
  public it.izs.bdn.webservices.GetInfoCapiOviniAllevamentoResponseGetInfoCapiOviniAllevamentoResult getInfoCapiOviniAllevamento(java.lang.String p_allev_id) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getInfoCapiOviniAllevamento(p_allev_id);
  }
  
  public java.lang.String getInfoCapiOviniAllevamento_STR(java.lang.String p_allev_id) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getInfoCapiOviniAllevamento_STR(p_allev_id);
  }
  
  public it.izs.bdn.webservices.GetMadriResponseGetMadriResult getMadri(java.lang.String p_allev_id, java.lang.String p_data) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getMadri(p_allev_id, p_data);
  }
  
  public java.lang.String getMadri_STR(java.lang.String p_allev_id, java.lang.String p_data) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getMadri_STR(p_allev_id, p_data);
  }
  
  public it.izs.bdn.webservices.GetDecessoResponseGetDecessoResult getDecesso(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getDecesso(p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_codice_capo);
  }
  
  public java.lang.String getDecesso_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getDecesso_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_codice_capo);
  }
  
  public it.izs.bdn.webservices.GetDecessoOvinoResponseGetDecessoOvinoResult getDecessoOvino(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getDecessoOvino(p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_codice_capo);
  }
  
  public java.lang.String getDecessoOvino_STR(java.lang.String p_azienda_codice, java.lang.String p_allev_idfiscale, java.lang.String p_spe_codice, java.lang.String p_codice_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getDecessoOvino_STR(p_azienda_codice, p_allev_idfiscale, p_spe_codice, p_codice_capo);
  }
  
  public it.izs.bdn.webservices.GetFurtoResponseGetFurtoResult getFurto(java.lang.String p_codice_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getFurto(p_codice_capo);
  }
  
  public java.lang.String getFurto_STR(java.lang.String p_codice_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getFurto_STR(p_codice_capo);
  }
  
  public it.izs.bdn.webservices.GetFurtoOvinoResponseGetFurtoOvinoResult getFurtoOvino(java.lang.String p_codice_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getFurtoOvino(p_codice_capo);
  }
  
  public java.lang.String getFurtoOvino_STR(java.lang.String p_codice_capo) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getFurtoOvino_STR(p_codice_capo);
  }
  
  public it.izs.bdn.webservices.FindCapoMacellatoResponseFindCapoMacellatoResult findCapoMacellato(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.findCapoMacellato(p_capo_codice);
  }
  
  public java.lang.String findCapoMacellato_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.findCapoMacellato_STR(p_capo_codice);
  }
  
  public it.izs.bdn.webservices.FindCapiOviniMacellatiResponseFindCapiOviniMacellatiResult findCapiOviniMacellati(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.findCapiOviniMacellati(p_capo_codice);
  }
  
  public java.lang.String findCapiOviniMacellati_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.findCapiOviniMacellati_STR(p_capo_codice);
  }
  
  public it.izs.bdn.webservices.GetCancellazione_AmministrativaResponseGetCancellazione_AmministrativaResult getCancellazione_Amministrativa(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCancellazione_Amministrativa(p_capo_codice);
  }
  
  public java.lang.String getCancellazione_Amministrativa_STR(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCancellazione_Amministrativa_STR(p_capo_codice);
  }
  
  public it.izs.bdn.webservices.Get_Capo_SentinellaResponseGet_Capo_SentinellaResult get_Capo_Sentinella(java.lang.String p_codice_elettronico, java.lang.String p_ident_nome, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.get_Capo_Sentinella(p_codice_elettronico, p_ident_nome, p_passaporto, p_codice_ueln);
  }
  
  public java.lang.String get_Capo_Sentinella_STR(java.lang.String p_codice_elettronico, java.lang.String p_ident_nome, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.get_Capo_Sentinella_STR(p_codice_elettronico, p_ident_nome, p_passaporto, p_codice_ueln);
  }
  
  public it.izs.bdn.webservices.Get_Capo_Equino_MacellatoResponseGet_Capo_Equino_MacellatoResult get_Capo_Equino_Macellato(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.get_Capo_Equino_Macellato(p_codice_elettronico, p_passaporto, p_codice_ueln);
  }
  
  public java.lang.String get_Capo_Equino_Macellato_STR(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.get_Capo_Equino_Macellato_STR(p_codice_elettronico, p_passaporto, p_codice_ueln);
  }
  
  public it.izs.bdn.webservices.Find_Capi_Equini_MacellatiResponseFind_Capi_Equini_MacellatiResult find_Capi_Equini_Macellati(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.find_Capi_Equini_Macellati(p_codice_elettronico, p_passaporto, p_codice_ueln);
  }
  
  public java.lang.String find_Capi_Equini_Macellati_STR(java.lang.String p_codice_elettronico, java.lang.String p_passaporto, java.lang.String p_codice_ueln) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.find_Capi_Equini_Macellati_STR(p_codice_elettronico, p_passaporto, p_codice_ueln);
  }
  
  public it.izs.bdn.webservices.GetCanc_Amm_OviniResponseGetCanc_Amm_OviniResult getCanc_Amm_Ovini(java.lang.String p_capo_codice) throws java.rmi.RemoteException{
    if (wsAnagraficaCapoQrySoap == null)
      _initWsAnagraficaCapoQrySoapProxy();
    return wsAnagraficaCapoQrySoap.getCanc_Amm_Ovini(p_capo_codice);
  }
  
  
}