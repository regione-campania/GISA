-- Chi: Bartolo Sansone
-- Cosa: Flusso 257 - Invio PNAA
-- Quando: 23/07/21

insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level)
select category_id, 'izsm-pnaa', true, true, true, true, 'Gestione INVIO PRELIEVI PNAA', 2 from permission where permission = 'izsm-molluschi';

insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete)
select 1, (select permission_id from permission where permission = 'izsm-pnaa'), true, true, true, true;

insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete)
select 31, (select permission_id from permission where permission = 'izsm-pnaa'), true, true, true, true;

insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete)
select 32, (select permission_id from permission where permission = 'izsm-pnaa'), true, true, true, true;




-- DBI

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_prelievo_pnaa(
    _username text,
    _password text,
    _id integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat(


concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://sinsa.izs.it/services">
<soapenv:Header><ser:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></ser:SOAPAutorizzazione><ser:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></ser:SOAPAutenticazione></soapenv:Header>
<soapenv:Body>'), 
concat('<ser:insertPrelievo>
<prelievo>
                <assistCodFiscale>', assist_cod_fiscale, '</assistCodFiscale>
                <campioniPrelevati>'), 
		concat('<altreInformazioni>
                        <informazioni>
                            <entry>
                                <key>emailResponsabileCampionamento</key>
                                <value>', email_responsabile_campionamento, '</value>
                            </entry>
                            <entry>
                                <key>destCampPrelevato</key>
                                <value>', dest_camp_prelevato, '</value>
                            </entry>
                            <entry>
                                <key>confezionamentoPNAA2021v1</key>
                                <value>', confezionamento_pnaa_2021_v1, '</value>
                            </entry>
                            <entry>
                                <key>ragSocDittaProduttrice</key>
                                <value>', rag_soc_ditta_produttrice, '</value>
                            </entry>
                            <entry>
                                <key>indirizzoDittaProduttrice</key>
                                <value>', indirizzo_ditta_produttrice, '</value>
                            </entry>
                            <entry>
                                <key>spCatDestAlim2021v1</key>
                                <value>', sp_cat_dest_alim_2021_v1, '</value>
                            </entry>
                            <entry>
                                <key>metProd2021v1</key>
                                <value>', met_prod2021v1, '</value>
                            </entry>
                            <entry>
                                <key>nomeCommercialeMangime</key>
                                <value>', nome_commerciale_mangime, '</value>
                            </entry>
                            <entry>
                                <key>stProd2021v1</key>
                                <value>', st_prod_2021_v1, '</value>
                            </entry>
                            <entry>
                                <key>stProd2021Altro</key>
                                <value>', st_prod_2021_altro, '</value>
                            </entry>
                            <entry>
                                <key>ragSocRespEtichettatura</key>
                                <value>', rag_soc_resp_etichettatura, '</value>
                            </entry>
                            <entry>
                                <key>indirizzoRespEtichettatura</key>
                                <value>', indirizzo_resp_etichettatura, '</value>
                            </entry>
                            <entry>
                                <key>stProduzione</key>
                                <value>', st_produzione, '</value>
                            </entry>
                            <entry>
                                <key>dtProduzione</key>
                                <value>', dt_produzione, '</value>
                            </entry>
                            <entry>
                                <key>dtScadenza</key>
                                <value>', dt_scadenza, '</value>
                            </entry>
                            <entry>
                                <key>numLottoProd</key>
                                <value>', num_lotto_prod, '</value>
                            </entry>
                            <entry>
                                <key>dimPartita</key>
                                <value>', dim_partita, '</value>
                            </entry>
                            <entry>
                                <key>ingredientiReg63</key>
                                <value>', ingredienti_reg_63, '</value>
                            </entry>
                            <entry>
                                <key>vopeCartellino</key>
                                <value>', vope_cartellino, '</value>
                            </entry>
                            <entry>
                                <key>vopeModEsecCamp</key>
                                <value>', vope_mod_esec_camp, '</value>
                            </entry>
                            <entry>
                                <key>vopeNumPuntiSacchi</key>
                                <value>', vope_num_punti_sacchi, '</value>
                            </entry>
                            <entry>
                                <key>vopeNumCampioniEffettivi</key>
                                <value>', vope_num_campioni_effettivi, '</value>
                            </entry>
                            <entry>
                                <key>vopePesoVolumePuntiSacchi</key>
                                <value>', vope_peso_volume_punti_sacchi, '</value>
                            </entry>
                            <entry>
                                <key>vopeOperazioniCampioneGlobale</key>
                                <value>', vope_operazioni_campione_globale, '</value>
                            </entry>
                            <entry>
                                <key>vopePesoVolumeCampioneGlobale</key>
                                <value>', vope_peso_volume_campione_globale, '</value>
                            </entry>
                            <entry>
                                <key>vopeCampioneGlobaleRidotto</key>
                                <value>', vope_campione_globale_ridotto, '</value>
                            </entry>
                            <entry>
                                <key>vopePesoVolumeCampioneRidotto</key>
                                <value>', vope_peso_volume_campione_ridotto, '</value>
                            </entry>
                            <entry>
                                <key>vopeOperazioniCampioniFinali</key>
                                <value>', vope_operazioni_campioni_finali, '</value>
                            </entry>
                            <entry>
                                <key>vopeNumCampioniFinali</key>
                                <value>', vope_num_campioni_finali, '</value>
                            </entry>
                            <entry>
                                <key>vopePesoVolumeMinimoCampioniFinali</key>
                                <value>', vope_peso_volume_minimo_campioni_finali, '</value>
                            </entry>
                            <entry>
                                <key>vopeDichiarazioniPropDet</key>
                                <value>', vope_dichiarazioni_prop_det, '</value>
                            </entry>
                            <entry>
                                <key>vopeNumCopieVerbaleInviate</key>
                                <value>', vope_num_copie_verbale_inviate, '</value>
                            </entry>
                            <entry>
                                <key>vopeDataInvioVerbali</key>
                                <value>', vope_data_invio_verbali, '</value>
                            </entry>
                            <entry>
                                <key>vopeNumCopieVerbaleCustodite</key>
                                <value>', vope_num_copie_verbale_custodite, '</value>
                            </entry>
                            <entry>
                                <key>vopeNumCampioniFinaliCustoditi</key>
                                <value>', vope_num_campioni_finali_custoditi, '</value>
                            </entry>
                            <entry>
                                <key>vopeNomeCognomeCustode</key>
                                <value>', vope_nome_cognome_custode, '</value>
                            </entry>
                            <entry>
                                <key>vopeTipoCustodiaCampioneFinale</key>
                                <value>', vope_tipo_custodia_campione_finale, '</value>
                            </entry>
                            <entry>
                                <key>vopeRinunciaCampioniFinali</key>
                                <value>', vope_rinuncia_campioni_finali, '</value>
                            </entry>
                            <entry>
                                <key>vopeSequestoPartitaLotto</key>
                                <value>', vope_sequesto_partita_lotto, '</value>
                            </entry>
                        </informazioni>
                    </altreInformazioni>'), 
               concat('<foodexCodice>', foodex_codice, '</foodexCodice>
                    <numAliquote>', num_aliquote, '</numAliquote>
                    <numUnitaCampionarie>', num_unita_campionarie, '</numUnitaCampionarie>
                    <progressivoCampione>', progressivo_campione, '</progressivoCampione>
                </campioniPrelevati>
                <contaminanti>
                    <contaminanteCodice>', contaminante_codice, '</contaminanteCodice>
                    <note />
                </contaminanti>
                <cun>', cun, '</cun>
                <dataPrelievo>', data_prelievo, '</dataPrelievo>
                <dataVerbale>', data_verbale, '</dataVerbale>
                <laboratorioCodice>',laboratorio_codice , '</laboratorioCodice>
                <luogoPrelievoCodice>', luogo_prelievo_codice, '</luogoPrelievoCodice>
                <metodoCampionamentoCodice>', metodo_campionamento_codice, '</metodoCampionamentoCodice>
                <motivoCodice>', motivo_codice, '</motivoCodice>
                <note />
                <numeroScheda>', numero_scheda, '</numeroScheda>
                <numeroVerbale>', numero_verbale, '</numeroVerbale>
                <pianoCodice>', piano_codice, '</pianoCodice>
                <prelevatori>
                    <prelCodFiscale>', prel_cod_fiscale, '</prelCodFiscale>
                </prelevatori>
                <tipoImpresa>', tipo_impresa, '</tipoImpresa>
            </prelievo>
</ser:insertPrelievo>'), 
concat('</soapenv:Body>
</soapenv:Envelope>')) into ret
from import_pnaa where id = _id;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


  
 
 CREATE TABLE import_pnaa(
id serial primary key, id_import integer, esito text, enteredby integer, entered timestamp without time zone default now(),
assist_cod_fiscale text, email_responsabile_campionamento text, dest_camp_prelevato text, confezionamento_pnaa_2021_v1 text, rag_soc_ditta_produttrice text, 
indirizzo_ditta_produttrice text, sp_cat_dest_alim_2021_v1 text, met_prod2021v1 text, nome_commerciale_mangime text, st_prod_2021_v1 text, 
st_prod_2021_altro text, rag_soc_resp_etichettatura text, indirizzo_resp_etichettatura text, st_produzione text, dt_produzione text, 
dt_scadenza text, num_lotto_prod text, dim_partita text, ingredienti_reg_63 text, vope_cartellino text, 
vope_mod_esec_camp text, vope_num_punti_sacchi text, vope_num_campioni_effettivi text, vope_peso_volume_punti_sacchi text, vope_operazioni_campione_globale text, 
vope_peso_volume_campione_globale text, vope_campione_globale_ridotto text, vope_peso_volume_campione_ridotto text, vope_operazioni_campioni_finali text, vope_num_campioni_finali text, 
vope_peso_volume_minimo_campioni_finali text, vope_dichiarazioni_prop_det text, vope_num_copie_verbale_inviate text, vope_data_invio_verbali text, vope_num_copie_verbale_custodite text, 
vope_num_campioni_finali_custoditi text, vope_nome_cognome_custode text, vope_tipo_custodia_campione_finale text, vope_rinuncia_campioni_finali text, vope_sequesto_partita_lotto text, 
foodex_codice text, num_aliquote text, num_unita_campionarie text, progressivo_campione text, contaminante_codice text, 
cun text, data_prelievo text, data_verbale text, laboratorio_codice text, luogo_prelievo_codice text, 
metodo_campionamento_codice text, motivo_codice text, numero_scheda text, numero_verbale text, piano_codice text, 
prel_cod_fiscale text, tipo_impresa text
);