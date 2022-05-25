drop FUNCTION public_functions.dbi_bdu_registrazioni_data_evento(
    IN tipospecie integer,
    IN asl integer,
    IN tiporegistrazione integer,
    IN anno_evento integer);
    
CREATE OR REPLACE FUNCTION public_functions.dbi_bdu_registrazioni_data_evento(
    IN tipospecie integer,
    IN asl integer,
    IN tiporegistrazione integer,
    IN anno_evento integer)
  RETURNS TABLE(id_animale integer, data_furto timestamp without time zone, luogo_furto text, dati_denuncia_furto text, importo_smarrimento double precision, luogo_smarrimento text, data_smarrimento timestamp without time zone, data_decesso timestamp without time zone, tipo_decesso text, luogo_decesso text, data_ritrovamento timestamp without time zone, ritrovamento_non_denunciato text, luogo_ritrovamento text, comune_ritrovamento text, nuovo_proprietario_ritrovamento text, asl_proprietario_sterilizzazione text, comune_cattura text, data_sterilizzazione timestamp without time zone, sterilizzazione_con_contributo text, sterilizzazione_progetto_canili text, ente_sterilizzazione text, veterinario_1_sterilizzazione text, veterinario_2_sterilizzazione text, evento_pregresso text, data_cattura timestamp without time zone, verbale_cattura text, data_rilascio_passaporto timestamp without time zone, data_scadenza_passaporto timestamp without time zone, numero_passaporto text, rinnovo_passaporto text, data_trasferimento timestamp without time zone, nuovo_proprietario_trasferimento text, vecchio_proprietario_trasferimento text, vecchio_detentore_trasferimento text,data_trasferimento_fuoriregione timestamp without time zone, regione_destinazione_fuoriregione text, data_cessione timestamp without time zone, vecchio_proprietario_cessione text, asl_vecchio_proprietario_cessione text, asl_nuovo_proprietario_cessione text, data_adozione_colonia timestamp without time zone, vecchio_proprietario_adozione_colonia text, vecchio_detentore_adozione_colonia text, nuovo_proprietario_adozione_colonia text, data_adozione_canile timestamp without time zone, vecchio_proprietario_adozione_canile text, vecchio_detentore_adozione_canile text, tipo_adozione text, nuovo_proprietario_adozione_canile text, data_presa_in_carico timestamp without time zone, nuovo_proprietario_presa_in_carico text, data_reimmissione timestamp without time zone, comune_reimmissione text, data_rientro_fuoriregione timestamp without time zone, regione_rientro_fuori_regione text, luogo_rientro_fuori_regione text, data_prelievo_leishmania timestamp without time zone, veterinario_prelievo_leishmania text, asl_evento text, id_asl integer, inserita_da text, ruolo_inserita_da text, specie text, microchip text, tatuaggio text, id_tipologia_registrazione integer, descrizione_tipologia_registrazione text, id_evento integer, data_operazione_sistema timestamp without time zone, data_evento timestamp without time zone, proprietario_corrente text, detentore_corrente text, tipologia_proprietario_corrente text, tipologia_detentore_corrente text, nuovo_proprietario_cessione text, nuovo_proprietario_presa_cessione text, nuovo_proprietario_presa_in_carico_adozione_fa text, vecchio_proprietario_trasferimento_fuori_regione text, nuovo_proprietario_trasferimento_fuori_regione text, nuovo_proprietario_trasferimento_canile text, vecchio_detentore_trasferimento_canile text,nuovo_proprietario_trasf_sindaco text, vecchio_proprietario_trasferimento_fuori_stato text, nuovo_proprietario_trasferimento_fuori_stato text) AS
$BODY$
BEGIN FOR 
   id_animale ,
   data_furto , 
   luogo_furto , 
   dati_denuncia_furto ,
   importo_smarrimento ,
   luogo_smarrimento ,
   data_smarrimento ,
   data_decesso,
   tipo_decesso ,
   luogo_decesso ,
   data_ritrovamento , 
   ritrovamento_non_denunciato , 
   luogo_ritrovamento  ,
   comune_ritrovamento , 
   nuovo_proprietario_ritrovamento ,
   asl_proprietario_sterilizzazione ,
   comune_cattura  ,
   data_sterilizzazione , 
   sterilizzazione_con_contributo  ,
   sterilizzazione_progetto_canili  ,
   ente_sterilizzazione , 
   veterinario_1_sterilizzazione , 
   veterinario_2_sterilizzazione ,
   evento_pregresso   ,
   data_cattura ,
   verbale_cattura ,
   data_rilascio_passaporto , 
   data_scadenza_passaporto  ,
   numero_passaporto ,
   rinnovo_passaporto , 
   data_trasferimento  ,
   nuovo_proprietario_trasferimento  ,
   vecchio_proprietario_trasferimento , 
   vecchio_detentore_trasferimento , 
   data_trasferimento_fuoriregione  ,
   regione_destinazione_fuoriregione , 
   data_cessione ,
   vecchio_proprietario_cessione , 
   asl_vecchio_proprietario_cessione ,
   asl_nuovo_proprietario_cessione  ,
   data_adozione_colonia  ,
   vecchio_proprietario_adozione_colonia  ,
   vecchio_detentore_adozione_colonia  ,
   nuovo_proprietario_adozione_colonia , 
   data_adozione_canile  ,
   vecchio_proprietario_adozione_canile , 
   vecchio_detentore_adozione_canile  ,
   --nuovo_proprietario_adozione_canile  ,
   tipo_adozione ,
   --data_adozione  ,
  -- vecchio_detentore , 
   nuovo_proprietario_adozione_canile ,
   data_presa_in_carico  ,
 nuovo_proprietario_presa_in_carico , 
data_reimmissione  ,
comune_reimmissione ,  
data_rientro_fuoriregione  ,
regione_rientro_fuori_regione  ,
luogo_rientro_fuori_regione  ,
data_prelievo_leishmania,
veterinario_prelievo_leishmania,
  asl_evento , 
  id_asl ,
   inserita_da , 
   ruolo_inserita_da , 
   specie , 
   microchip , 
   tatuaggio , 
   id_tipologia_registrazione, 
   descrizione_tipologia_registrazione, 
   id_evento ,
   data_operazione_sistema  , 
   data_evento, 
   --entered,
   proprietario_corrente, 
   detentore_corrente, 
   tipologia_proprietario_corrente, 
   tipologia_detentore_corrente,
   nuovo_proprietario_cessione,
   nuovo_proprietario_presa_cessione,
   nuovo_proprietario_presa_in_carico_adozione_fa,
   --codice_fiscale_nuovo_proprietario_presa_in_carico_adozione_fa,
   vecchio_proprietario_trasferimento_fuori_regione,
   nuovo_proprietario_trasferimento_fuori_regione,
   nuovo_proprietario_trasferimento_canile,
   vecchio_detentore_trasferimento_canile,
   nuovo_proprietario_trasf_sindaco,
   vecchio_proprietario_trasferimento_fuori_stato,
   nuovo_proprietario_trasferimento_fuori_stato

		in
 SELECT 
 animale.id as id_animale,
 --FURTO
furto.data_furto as data_furto,
furto.luogo_furto as luogo_furto ,
furto.dati_denuncia as dati_denuncia_furto,
--SMARRIMENTO
smar.importo_smarrimento as importo_smarrimento,
smar.luogo_smarrimento as luogo_smarrimento,
smar.data_smarrimento as data_smarrimento,
--DECESSO
decesso.data_decesso as data_decesso,
listadecessi.description as tipo_decesso,
decesso.luogo_decesso as luogo_decesso, 
--RITROVAMENTO E RITROVAMENTO ND  
CASE when reg.id_tipologia_evento = 12 then ritro.data_ritrovamento when reg.id_tipologia_evento = 41 then ritro_nd.data_ritrovamento_nd end as data_ritrovamento,
  CASE when reg.id_tipologia_evento = 12 then 'NO' when reg.id_tipologia_evento = 41 then 'SI' end as ritrovamento_non_denunciato,
  CASE when reg.id_tipologia_evento = 12 then ritro.luogo_ritrovamento when reg.id_tipologia_evento = 41 then ritro_nd.luogo_ritrovamento_nd end as luogo_ritrovamento,
    CASE when reg.id_tipologia_evento = 12 then comuni.nome when reg.id_tipologia_evento = 41 then comuni_nd.nome end as comune_ritrovamento,
CASE when reg.id_tipologia_evento = 12 then den.ragione_sociale when reg.id_tipologia_evento = 41 then den_nd.ragione_sociale end as nuovo_proprietario,
listaasl_ster.description as asl_proprietario_sterilizzazione,
COALESCE(comunicane.nome, com1.nome, comunigatto.nome) AS comune_cattura_sterilizzazione,
ster.data_sterilizzazione, 
CASE WHEN (ster.flag_richiesta_contributo_regionale and ster.id_progetto_di_sterilizzazione_richiesto > 0) THEN 'SI'::text ELSE 'NO'::text END AS sterilizzazione_con_contributo,
CASE WHEN ster.id_progetto_di_sterilizzazione_richiesto in (select id_pratica from pratiche_contributi_canili) then 'SI'::text ELSE 'NO'::text END as sterilizzazione_progetto_canili,
CASE WHEN ster.tipologia_soggetto_sterilizzante=1 THEN 'ASL'::text WHEN ster.tipologia_soggetto_sterilizzante=2 THEN 'LLPP' WHEN ster.tipologia_soggetto_sterilizzante is null THEN 'ASL' END as ente_sterilizzazione,
concat_ws( vet_1_ster_contact.namelast,  vet_1_ster_contact.namefirst, ' ') as  veterinario_1_sterilizzazione,
concat_ws( vet_2_ster_contact.namelast,  vet_2_ster_contact.namefirst, ' ') as  veterinario_2_sterilizzazione,
CASE WHEN reg.inserimento_registrazione_forzato THEN 'SI'::text ELSE 'NO'::text END AS evento_pregresso,
cattura.data_cattura ,
--COALESCE(comuni.nome, 'N.D.') as comune_cattura,
cattura.verbale_cattura,
ril_passaporto.data_rilascio_passaporto, 
ril_passaporto.data_scadenza_passaporto ,
ril_passaporto.numero_passaporto,
case when ril_passaporto.flag_rinnovo = true then 'SI'::text when ril_passaporto.flag_rinnovo =false then 'NO'::text when ril_passaporto.flag_rinnovo is null then 'NO'::text end AS rinnovo_passaporto, 
trasf.data_trasferimento ,
rel_nuovo.ragione_sociale as nuovo_proprietario_trasferimento,
rel_vecchio.ragione_sociale as vecchio_proprietario_trasferimento, 
rel_vecchio_det.ragione_sociale as vecchio_detentore_trasferimento,
trasf_fr.data_trasferimento_fuori_regione ,
regioni.description as regione_destinazione_fuoriregione,   
cessione.data_cessione,
vecchio_proprietario.ragione_sociale as vecchio_proprietario, 
listaasl_vecchio.description as asl_vecchio_proprietario_cessione, 
listaasl_nuovo.description as asl_nuovo_proprietario_cessione,
ado_colonia.data_adozione_colonia ,
 vecchio_colonia_prop.ragione_sociale AS vecchio_proprietario_adozione_colonia,
vecchio_colonia_det.ragione_sociale AS vecchio_detentore_adozione_colonia,
nuovo_prop.ragione_sociale AS nuovo_proprietario_adozione_colonia,
 ado_canile.data_adozione ,
 vecchio_canile_prop.ragione_sociale AS vecchio_proprietario_adozione_canile,
vecchio_canile_det.ragione_sociale AS vecchio_detentore_adozione_canile,
--nuovo_prop.ragione_sociale AS nuovo_proprietario_adozione_canile,
 tipo_adozione ,
 --case when reg.id_tipologia_evento = 13 then ado_canile.data_adozione when reg.id_tipologia_evento = 19 then ado_colonia.data_adozione_colonia when reg.id_tipologia_evento = 46 then ado_fa.data_adozione_fa end as data_adozione
--, case when reg.id_tipologia_evento = 13 then den_canile_origine.ragione_sociale when reg.id_tipologia_evento = 19 then den_colonia_origine.ragione_sociale when reg.id_tipologia_evento = 46 then den_fa_origine.ragione_sociale end as vecchio_detentore
--,
 case when reg.id_tipologia_evento = 13 then den_canile.ragione_sociale when reg.id_tipologia_evento = 19 then den_colonia.ragione_sociale when reg.id_tipologia_evento = 46 then den_fa.ragione_sociale end as nuovo_proprietario ,
 presa.data_presa_in_carico , 
 nuovo_proprietario.ragione_sociale as nuovo_proprietario, 
reimmissione.data_reimmissione ,
comuni_reimmissione.nome as comune_reimmissione,  
rientro_fr.data_rientro_fr ,
regione_rientro_fuori_regione ,
rientro_fr.luogo ,
prelievo_leish.data_prelievo_leish as data_prelievo_leishmania,
case when (prelievo_leish.id_veterinario_llpp is not null and cc.namelast is not null) then concat(cc.namelast ,',', cc.namefirst) 
when (prelievo_leish.id_veterinario_llpp is null and cc.namelast is not null) then concat(cc.namelast ,',', cc.namefirst)   end as veterinario_prelievo_leishmaniosi,

 listaasl.description AS asl_evento, 
 reg.id_asl as id_asl,
 pg_catalog.concat(contatto.namefirst, ' ', contatto.namelast) AS inserita_da, 
 listaruoli.role AS ruolo_inserita_da, 
 CASE       WHEN animale.id_specie = 1 THEN 'CANE'::text
            WHEN animale.id_specie = 2 THEN 'GATTO'::text
            WHEN animale.id_specie = 3 THEN 'FURETTO'::text
            ELSE NULL::text
        END AS specie, 
 CASE
            WHEN animale.microchip::text = ''::text THEN 'N.D.'::text::character varying
            ELSE animale.microchip
	END AS microchip, 
 CASE
            WHEN animale.tatuaggio::text = ''::text THEN 'N.D.'::text::character varying
            ELSE animale.tatuaggio
        END AS tatuaggio, 
 listaregistrazioni.code AS id_tipologia_registrazione,
 listaregistrazioni.description AS descrizione_tipologia_registrazione, 
 reg.id_evento as id_evento,
reg.entered AS data_operazione_sistema, 
        coalesce(
CASE
            WHEN reg.id_tipologia_evento = 1 THEN registrazione.data_registrazione
            WHEN reg.id_tipologia_evento = 2 THEN ster.data_sterilizzazione
            WHEN reg.id_tipologia_evento = 3 THEN ins_microchip.data_inserimento_microchip
            WHEN reg.id_tipologia_evento = 4 THEN furto.data_furto
            WHEN reg.id_tipologia_evento = 5 THEN cattura.data_cattura
            WHEN reg.id_tipologia_evento = 6 THEN ril_passaporto.data_rilascio_passaporto
            WHEN reg.id_tipologia_evento = 7 THEN cessione.data_cessione
            WHEN reg.id_tipologia_evento = 8 THEN trasf_fr.data_trasferimento_fuori_regione
            WHEN reg.id_tipologia_evento = 9 THEN decesso.data_decesso
            WHEN reg.id_tipologia_evento = 11 THEN smar.data_smarrimento
            WHEN reg.id_tipologia_evento = 12 THEN ritro.data_ritrovamento
            WHEN reg.id_tipologia_evento = 13 THEN ado_canile.data_adozione
            WHEN reg.id_tipologia_evento = 14 THEN rest_canile.data_restituzione_canile
            WHEN reg.id_tipologia_evento = 15 THEN presa.data_presa_in_carico
            WHEN reg.id_tipologia_evento = 16 THEN trasf.data_trasferimento
            WHEN reg.id_tipologia_evento = 17 THEN rientro_fr.data_rientro_fr
            WHEN reg.id_tipologia_evento = 18 THEN cambio.data_cambio_detentore
            WHEN reg.id_tipologia_evento = 19 THEN ado_colonia.data_adozione_colonia
            WHEN reg.id_tipologia_evento = 21 THEN morsicatura.data_morso
            WHEN reg.id_tipologia_evento = 23 THEN reimmissione.data_reimmissione
            WHEN reg.id_tipologia_evento = 24 THEN cattura.data_cattura
            WHEN reg.id_tipologia_evento = 26 THEN controlli.data_registrazione_esiti -------------------------------
            WHEN reg.id_tipologia_evento = 31 THEN trasf_canile.data_trasferimento_canile
             WHEN reg.id_tipologia_evento = 33 THEN reg.modified ------------------------------
            WHEN reg.id_tipologia_evento = 36 THEN ins_vaccino.data_inserimento_vaccinazione
            WHEN reg.id_tipologia_evento = 38 THEN ins_microchip.data_inserimento_microchip
            WHEN reg.id_tipologia_evento = 39 THEN trasf_fs.data_trasferimento_fuori_stato
            --WHEN reg.id_tipologia_evento = 40 THEN trasf_fr_sp.data_trasferimento_fuori_regione_solo_proprietario
            WHEN reg.id_tipologia_evento = 42 THEN rientro_fs.data_rientro_fuori_stato
            WHEN reg.id_tipologia_evento = 43 THEN residenza.data_modifica_residenza
            WHEN reg.id_tipologia_evento = 50 THEN dna.data_prelievo
            WHEN reg.id_tipologia_evento = 45 THEN rest.data_restituzione
            WHEN reg.id_tipologia_evento = 46 then ado_fa.data_adozione_fa
            WHEN reg.id_tipologia_evento = 47 then presa_ado_fa.data_presa_in_carico_adozione_fa
            WHEN reg.id_tipologia_evento = 41 then ritro_nd.data_ritrovamento_nd
            WHEN reg.id_tipologia_evento = 54 then prelievo_leish.data_prelievo_leish
            WHEN reg.id_tipologia_evento = 55 then trasf_sindaco.data_trasferimento
            ELSE null::timestamp without time zone
        END,reg.entered) AS data_evento,
               -- reg.entered,
        propc.ragione_sociale as proprietario_corrente, 
        detc.ragione_sociale as detentore_corrente, 
	propcdesc.description as tipologia_proprietario_corrente, 
	detcdesc.description as tipologia_detentore_corrente,
	nuovo_proprietario_cessione.ragione_sociale as nuovo_proprietario_cessione,
	nuovo_proprietario.ragione_sociale as nuovo_proprietario_presa_cessione,
	/* NUOVO PROPRIETARIO PRESA IN CARICO ADOZIONE FUORI AMBITO ASL */
        nuovo_proprietario_presa_ado_fa.ragione_sociale as nuovo_proprietario_presa_in_carico_adozione_fa,
--        nuovo_proprietario_presa_ado_fa.codice_fiscale as codice_fiscale_nuovo_proprietario_presa_in_carico_adozione_fa,
        trasf_fr_vecchio.ragione_sociale as vecchio_proprietario_trasferimento_fuori_regione,
        trasf_fr.dati_proprietario_fuori_regione as nuovo_proprietario_trasferimento_fuori_regione,
        nuovo_propr_trasf_canile.ragione_sociale as nuovo_proprietario_trasferimento_canile,
        vecchio_det_trasf_canile.ragione_sociale as vecchio_detentore_trasferimento_canile,
        nuovo_propr_trasf_sindaco.ragione_sociale as nuovo_proprietario_trasf_sindaco,
        vecchio_propr_trasf_fuori_stato.ragione_sociale as vecchio_proprietario_trasferimento_fuori_stato,
        trasf_fs.dati_proprietario_fuori_stato as nuovo_proprietario_trasferimento_fuori_stato
        
        

   FROM evento reg

     LEFT JOIN animale ON animale.id = reg.id_animale

   /*FURTO*/
   LEFT JOIN evento_furto furto ON furto.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=4) or tiporegistrazione=-1)
  /*SMARRIMENTO*/
  LEFT JOIN evento_smarrimento smar ON smar.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=11) or tiporegistrazione=-1)

 /*DECESSO*/
    LEFT JOIN evento_decesso decesso ON decesso.id_evento = reg.id_evento 
    left join lookup_tipologia_decesso listadecessi on decesso.id_causa_decesso = listadecessi.code 
    and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=9) or tiporegistrazione=-1)

 /*RITROVAMENTO*/
   LEFT JOIN evento_ritrovamento ritro ON ritro.id_evento = reg.id_evento                                                     
   left join opu_operatori_denormalizzati den on ritro.id_proprietario_dopo_ritrovamento = den.id_rel_stab_lp and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=12) or tiporegistrazione=-1)

 /*RITROVAMENTO ND*/

   left join evento_ritrovamento_non_denunciato ritro_nd on ritro_nd.id_evento = reg.id_evento
   left join opu_operatori_denormalizzati den_nd on ritro_nd.id_detentore_dopo_ritrovamento_nd = den_nd.id_rel_stab_lp 
   left join comuni1 comuni_nd on comuni_nd.id = ritro_nd.comune_ritrovamento_nd 
   and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=41) or tiporegistrazione=-1)

/*STERILIZZAZIONE*/

  LEFT JOIN evento_sterilizzazione ster ON ster.id_evento = reg.id_evento
  left join lookup_asl_rif listaasl_ster on listaasl_ster.code = ster.id_asl_proprietario
  left join lookup_asl_rif listaenti on listaenti.code = ster.id_soggetto_sterilizzante and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=2) or tiporegistrazione=-1)
  left join access_ vet_1_ster_acc on vet_1_ster_acc.user_id = ster.veterinario_asl_1
  left join contact_ vet_1_ster_contact on vet_1_ster_contact.user_id = vet_1_ster_acc.user_id
  left join access_ vet_2_ster_acc on vet_2_ster_acc.user_id = ster.veterinario_asl_2
  left join contact_ vet_2_ster_contact on vet_2_ster_contact.user_id = vet_2_ster_acc.user_id
--left join lookup_asl_rif listaaslevento on listaaslevento.code = reg.id_asl

/*CATTURA*/
   LEFT JOIN evento_cattura cattura ON cattura.id_evento = reg.id_evento
   LEFT JOIN comuni1 comuni ON comuni.id = cattura.id_comune_cattura 
   LEFT JOIN cane on cane.id_animale = animale.id
   LEFT JOIN gatto on gatto.id_animale = animale.id
   LEFT JOIN comuni1 comunicane on comunicane.id = cane.id_comune_cattura 
   LEFT JOIN comuni1 comunigatto on comunigatto.id = gatto.id_comune_cattura 
   left join comuni1 com1 on com1.id = animale.id_comune_cattura 
    and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=5) or tiporegistrazione=-1)

 /*RILASCIO PASSAPORTO*/

   LEFT JOIN evento_rilascio_passaporto ril_passaporto ON ril_passaporto.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=6) or tiporegistrazione=-1)


 /*TRASFERIMENTO*/
   LEFT JOIN evento_trasferimento trasf ON trasf.id_evento = reg.id_evento 
   left join opu_operatori_denormalizzati rel_nuovo on trasf.id_nuovo_proprietario = rel_nuovo.id_rel_stab_lp 
   left join opu_operatori_denormalizzati rel_vecchio on trasf.id_vecchio_proprietario = rel_vecchio.id_rel_stab_lp and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=16) or tiporegistrazione=-1)
   left join opu_operatori_denormalizzati rel_vecchio_det on trasf.id_vecchio_detentore = rel_vecchio_det.id_rel_stab_lp and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=16) or tiporegistrazione=-1)



 /*TRASF FUORI REGIONE*/
    LEFT JOIN evento_trasferimento_fuori_regione trasf_fr ON trasf_fr.id_evento = reg.id_evento 
    left join lookup_regione regioni on regioni.code = trasf_fr.id_regione_a 
    left join opu_operatori_denormalizzati trasf_fr_vecchio on trasf_fr.id_vecchio_proprietario_fuori_regione = trasf_fr_vecchio.id_rel_stab_lp and 
 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=8) or tiporegistrazione=-1)

    /* TRASFERIMENTO DA CANILE */   
   LEFT JOIN evento_trasferimento_canile trasf_canile ON trasf_canile.id_evento = reg.id_evento
  left join opu_operatori_denormalizzati nuovo_propr_trasf_canile on trasf_canile.id_proprietario_trasferimento_canile = nuovo_propr_trasf_canile.id_rel_stab_lp and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=31) or tiporegistrazione=-1)
  left join opu_operatori_denormalizzati vecchio_det_trasf_canile on trasf_canile.id_canile_old_trasferimento_canile = vecchio_det_trasf_canile.id_rel_stab_lp and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=31) or tiporegistrazione=-1)

    
       /* TRASFERIMENTO VERSO SINDACO */   
   LEFT JOIN evento_trasferimento_sindaco trasf_sindaco ON trasf_sindaco.id_evento = reg.id_evento
  left join opu_operatori_denormalizzati nuovo_propr_trasf_sindaco on trasf_sindaco.id_nuovo_proprietario = nuovo_propr_trasf_sindaco.id_rel_stab_lp and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=55) or tiporegistrazione=-1)

--   LEFT JOIN evento_trasferimento_fuori_regione_solo_proprietario trasf_fr_sp ON trasf_fr_sp.id_evento = reg.id_evento and 
 --                                                    ((tiporegistrazione>-1 and tiporegistrazione=40) or tiporegistrazione=-1)
   LEFT JOIN evento_trasferimento_fuori_stato trasf_fs ON trasf_fs.id_evento = reg.id_evento 
   left join opu_operatori_denormalizzati vecchio_propr_trasf_fuori_stato on trasf_fs.id_vecchio_proprietario_fuori_stato = vecchio_propr_trasf_fuori_stato.id_rel_stab_lp and 
 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=39) or tiporegistrazione=-1)



 /*CESSIONE*/

    LEFT JOIN evento_cessione cessione ON cessione.id_evento = reg.id_evento 
   left join lookup_asl_rif listaasl_vecchio on cessione.id_asl_vecchio_proprietario_cessione = listaasl_vecchio.code
   left join lookup_asl_rif listaasl_nuovo on cessione.id_asl_nuovo_proprietario_cessione = listaasl_nuovo.code 
   left join opu_operatori_denormalizzati vecchio_proprietario on cessione.id_vecchio_proprietario_cessione = vecchio_proprietario.id_rel_stab_lp     and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=7) or tiporegistrazione=-1)
   left join opu_operatori_denormalizzati nuovo_proprietario_cessione on cessione.id_nuovo_proprietario_cessione = nuovo_proprietario_cessione.id_rel_stab_lp     and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=7) or tiporegistrazione=-1)

  /*PRESA CESSIONE*/
   LEFT JOIN evento_presa_in_carico_cessione presa ON presa.id_evento = reg.id_evento 
   left join opu_operatori_denormalizzati nuovo_proprietario on presa.id_nuovo_proprietario_presa_cessione = nuovo_proprietario.id_rel_stab_lp and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=15) or tiporegistrazione=-1)


/*ADOZIONE DA CANILE*/   
   LEFT JOIN evento_adozione_da_canile ado_canile ON ado_canile.id_evento = reg.id_evento 
   left join opu_operatori_denormalizzati den_canile on den_canile.id_rel_stab_lp = ado_canile.id_proprietario_adozione 
   left join opu_operatori_denormalizzati vecchio_canile_prop on ado_canile.id_vecchio_proprietario_adozione = vecchio_canile_prop.id_rel_stab_lp 
   left join opu_operatori_denormalizzati vecchio_canile_det on ado_canile.id_vecchio_detentore_adozione = vecchio_canile_det.id_rel_stab_lp  and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione =13) or tiporegistrazione=-1) 

/*ADOZIONE DA COLONIA*/
   LEFT JOIN evento_adozione_da_colonia ado_colonia ON ado_colonia.id_evento = reg.id_evento    
   left join opu_operatori_denormalizzati vecchio_colonia_prop on ado_colonia.id_vecchio_proprietario_adozione_colonia = vecchio_colonia_prop.id_rel_stab_lp 
   left join opu_operatori_denormalizzati vecchio_colonia_det on ado_colonia.id_vecchio_detentore_adozione_colonia = vecchio_colonia_det.id_rel_stab_lp 
   left join opu_operatori_denormalizzati nuovo_prop on ado_colonia.id_proprietario_adozione_colonia = nuovo_prop.id_rel_stab_lp 
   left join opu_operatori_denormalizzati den_colonia_origine on den_colonia_origine.id_rel_stab_lp = ado_colonia.id_vecchio_detentore_adozione_colonia
   left join opu_operatori_denormalizzati den_colonia on den_colonia.id_rel_stab_lp = ado_colonia.id_proprietario_adozione_colonia and 
                                                        ((tiporegistrazione>-1 and tiporegistrazione=19) or tiporegistrazione=-1)

   /*ADOZIONE FUORI ASL*/

   left join evento_adozione_fuori_asl ado_fa on ado_fa.id_evento = reg.id_evento  
   left join opu_operatori_denormalizzati den_canile_origine on den_canile_origine.id_rel_stab_lp = ado_fa.id_vecchio_detentore_adozione_fa 
   left join opu_operatori_denormalizzati den_fa_origine on den_fa_origine.id_rel_stab_lp = ado_fa.id_vecchio_detentore_adozione_fa 
   left join opu_operatori_denormalizzati den_fa on den_fa.id_rel_stab_lp = ado_fa.id_nuovo_proprietario_adozione_fa   and   
                                                     ((tiporegistrazione>-1 and tiporegistrazione=46) or tiporegistrazione=-1)


/*PRESA IN CARICO FUORI AMBITO ASL*/   
   LEFT JOIN evento_presa_in_carico_adozione_fuori_asl presa_ado_fa ON presa_ado_fa.id_evento = reg.id_evento  
   left join opu_operatori_denormalizzati nuovo_proprietario_presa_ado_fa on presa_ado_fa.id_nuovo_proprietario_presa_adozione_fa = nuovo_proprietario_presa_ado_fa.id_rel_stab_lp and
                                                     ((tiporegistrazione>-1 and tiporegistrazione =47) or tiporegistrazione=-1) 


   /*REIMMISSIONE*/
     LEFT JOIN evento_reimmissione reimmissione ON reimmissione.id_evento = reg.id_evento left join comuni1 as comuni_reimmissione on (reimmissione.id_comune_reimmissione = comuni_reimmissione.id) and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=23) or tiporegistrazione=-1)


   /*RIENTRO FR*/
      LEFT JOIN evento_rientro_da_fuori_regione rientro_fr ON rientro_fr.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=17) or tiporegistrazione=-1)

   /*EVENTO CAMBIO DETENTORE*/                                                     
   LEFT JOIN evento_cambio_detentore cambio ON cambio.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=18) or tiporegistrazione=-1)

   /*EVENTO ESITO CONTROLLI*/
   LEFT JOIN evento_esito_controlli esito ON esito.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=22) or tiporegistrazione=-1)
   /*evento_inserimento_esiti_controlli_commerciali*/
   LEFT JOIN evento_inserimento_esiti_controlli_commerciali controlli ON controlli.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=26) or tiporegistrazione=-1)
   /*EVENTO INSERIMENTO MC*/                                                  
   LEFT JOIN evento_inserimento_microchip ins_microchip ON ins_microchip.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=3) or tiporegistrazione=-1)
                                                     
   LEFT JOIN evento_inserimento_vaccino ins_vaccino ON ins_vaccino.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=36) or tiporegistrazione=-1)
                                                     
   LEFT JOIN evento_modifica_residenza residenza ON residenza.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=43) or tiporegistrazione=-1)
                                                     
   LEFT JOIN evento_morsicatura morsicatura ON morsicatura.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=21) or tiporegistrazione=-1)

   LEFT JOIN evento_registrazione_bdu registrazione ON registrazione.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=1) or tiporegistrazione=-1)

   LEFT JOIN evento_restituzione_a_canile rest_canile ON rest_canile.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=14) or tiporegistrazione=-1)

   LEFT JOIN evento_rientro_da_fuori_stato rientro_fs ON rientro_fs.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=42) or tiporegistrazione=-1)


   LEFT JOIN evento_prelievo_dna dna ON dna.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=50) or tiporegistrazione=-1)   
   LEFT JOIN evento_restituzione_a_proprietario rest ON rest.id_evento = reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=45) or tiporegistrazione=-1)

    /*PRELIEVO CAMPIONI LEISHMANIA*/

   LEFT JOIN evento_prelievo_leish prelievo_leish on  prelievo_leish.id_evento =  reg.id_evento and 
                                                     ((tiporegistrazione>-1 and tiporegistrazione=54) or tiporegistrazione=-1)
   LEFT JOIN contact_ cc on (prelievo_leish.id_veterinario_llpp = cc.user_id)      
   LEFT JOIN contact_ cc1 on (prelievo_leish.id_veterinario_asl = cc.user_id)                                              

   LEFT JOIN access_ ON reg.id_utente_inserimento = access_.user_id
   LEFT JOIN contact_ contatto ON access_.contact_id = contatto.contact_id
   LEFT JOIN role listaruoli ON access_.role_id = listaruoli.role_id
   LEFT JOIN lookup_tipologia_registrazione listaregistrazioni ON reg.id_tipologia_evento = listaregistrazioni.code
   LEFT JOIN lookup_asl_rif listaasl ON reg.id_asl = listaasl.code


   /*informazioni proprietario detentore corrente*/

   LEFT JOIN opu_operatori_denormalizzati propc on (reg.id_proprietario_corrente = propc.id_rel_stab_lp)
   LEFT JOIN opu_relazione_attivita_produttive_aggregazioni oo on (oo.id = propc.id_linea_produttiva)
   LEFT JOIN opu_lookup_attivita_linee_produttive_aggregazioni propcdesc  on (oo.id_attivita_aggregazione = propcdesc.code)


   LEFT JOIN opu_operatori_denormalizzati detc on (reg.id_detentore_corrente = detc.id_rel_stab_lp)
   LEFT JOIN opu_relazione_attivita_produttive_aggregazioni od on (od.id = detc.id_linea_produttiva)
   LEFT JOIN opu_lookup_attivita_linee_produttive_aggregazioni detcdesc  on (od.id_attivita_aggregazione = detcdesc.code)


  


  WHERE 
  reg.data_cancellazione IS NULL and reg.trashed_date is null
  AND animale.data_cancellazione IS NULL and animale.trashed_date is null
   and(
  (tipospecie>-1 and animale.id_specie = tipospecie)
 or (tipospecie=-1)
  )
  and (
   (asl>-1 and reg.id_asl = asl)
 or (asl=-1)
  )
    and (
   (tiporegistrazione>-1 and reg.id_tipologia_evento = tiporegistrazione)
 or (tiporegistrazione=-1)
  )
  and((reg.id_tipologia_evento = 1 and date_part('year', registrazione.data_registrazione) = anno_evento)
or(reg.id_tipologia_evento = 2 and date_part('year', ster.data_sterilizzazione) = anno_evento)
or(reg.id_tipologia_evento = 3 and date_part('year', ins_microchip.data_inserimento_microchip) = anno_evento)
or(reg.id_tipologia_evento = 4 and date_part('year', furto.data_furto) = anno_evento)
or(reg.id_tipologia_evento = 5 and date_part('year', cattura.data_cattura) = anno_evento)
or(reg.id_tipologia_evento = 6 and date_part('year', ril_passaporto.data_rilascio_passaporto) = anno_evento)
or(reg.id_tipologia_evento = 7 and date_part('year', cessione.data_cessione) = anno_evento)
or(reg.id_tipologia_evento = 8 and date_part('year', trasf_fr.data_trasferimento_fuori_regione) = anno_evento)
or(reg.id_tipologia_evento = 9 and date_part('year', decesso.data_decesso) = anno_evento)
or(reg.id_tipologia_evento = 11 and date_part('year', smar.data_smarrimento) = anno_evento)
or(reg.id_tipologia_evento = 12 and date_part('year', ritro.data_ritrovamento) = anno_evento)
or(reg.id_tipologia_evento = 41 and date_part('year', ritro_nd.data_ritrovamento_nd) = anno_evento)
or(reg.id_tipologia_evento = 13 and date_part('year', ado_canile.data_adozione) = anno_evento)
or(reg.id_tipologia_evento = 14 and date_part('year', rest_canile.data_restituzione_canile) = anno_evento)
or(reg.id_tipologia_evento = 15 and date_part('year', presa.data_presa_in_carico) = anno_evento)
or(reg.id_tipologia_evento = 16 and date_part('year', trasf.data_trasferimento) = anno_evento)
or(reg.id_tipologia_evento = 17 and date_part('year', rientro_fr.data_rientro_fr) = anno_evento)
or(reg.id_tipologia_evento = 18 and date_part('year', cambio.data_cambio_detentore) = anno_evento)
or(reg.id_tipologia_evento = 19 and date_part('year', ado_colonia.data_adozione_colonia) = anno_evento)
or(reg.id_tipologia_evento = 21 and date_part('year', morsicatura.data_morso) = anno_evento)
or(reg.id_tipologia_evento = 23 and date_part('year', reimmissione.data_reimmissione) = anno_evento)
or(reg.id_tipologia_evento = 24 and date_part('year', cattura.data_cattura) = anno_evento)
or(reg.id_tipologia_evento = 26 and date_part('year', controlli.data_registrazione_esiti) = anno_evento)
or(reg.id_tipologia_evento = 31 and date_part('year', trasf_canile.data_trasferimento_canile) = anno_evento)
or(reg.id_tipologia_evento = 33 and date_part('year', reg.modified) = anno_evento)
or(reg.id_tipologia_evento = 36 and date_part('year', ins_vaccino.data_inserimento_vaccinazione) = anno_evento)
or(reg.id_tipologia_evento = 38 and date_part('year', ins_microchip.data_inserimento_microchip) = anno_evento)
or(reg.id_tipologia_evento = 39 and date_part('year', trasf_fs.data_trasferimento_fuori_stato) = anno_evento)
--or(reg.id_tipologia_evento = 40 and date_part('year', trasf_fr_sp.data_trasferimento_fuori_regione_solo_proprietario) = anno_evento)
or(reg.id_tipologia_evento = 42 and date_part('year', rientro_fs.data_rientro_fuori_stato) = anno_evento)
or(reg.id_tipologia_evento = 43 and date_part('year', residenza.data_modifica_residenza) = anno_evento)
or(reg.id_tipologia_evento = 50 and date_part('year', dna.data_prelievo) = anno_evento)
or(reg.id_tipologia_evento = 45 and date_part('year', rest.data_restituzione) = anno_evento)
or(reg.id_tipologia_evento = 46 and date_part('year', ado_fa.data_adozione_fa) = anno_evento)
or(reg.id_tipologia_evento = 47 and date_part('year', presa_ado_fa.data_presa_in_carico_adozione_fa) = anno_evento)
or(reg.id_tipologia_evento = 54 and date_part('year', prelievo_leish.data_prelievo_leish) = anno_evento)
or(reg.id_tipologia_evento = 55 and date_part('year', trasf_sindaco.data_trasferimento) = anno_evento)
or (anno_evento=-1))
  LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_bdu_registrazioni_data_evento(integer, integer, integer, integer)
  OWNER TO postgres;