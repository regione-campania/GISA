
    CREATE OR REPLACE FUNCTION public.dpat_duplica_strumento_calcolo(
    anno_i integer,
    anno_target_i integer)
  RETURNS void AS
$BODY$
declare
rec_strumento_calcolo_old record;
rec_struttura_semplice_old record;
rec_struttura_complessa_old record;
rec_dpat_old record;
rec_nominativo_old record;
id_strumento_calcolo_inserito integer;
id_struttura_semplice_inserita integer;
id_struttura_complessa_inserita integer;
id_nominativo_inserito integer;
id_dpat_istanza_inserito integer;
next_id integer;
begin

--Azzero l'anno di destinazione
raise info 'Cancellazione dpat in corso';
delete from dpat where anno = anno_target_i;
raise info 'Cancellazione dpat_istanza in corso';
delete from dpat_istanza where anno = anno_target_i;
raise info 'Cancellazione dpat_strumento_calcolo in corso';
delete from dpat_strumento_calcolo where anno = anno_target_i;
raise info 'Cancellazione dpat_strumento_calcolo_nominativi_ in corso';
delete from dpat_strumento_calcolo_nominativi_ where id_dpat_strumento_calcolo_strutture in (select id from strutture_asl where anno = anno_target_i) and id_dpat_strumento_calcolo_strutture in (select codice_interno_fk from strutture_asl where anno = anno_target_i);
raise info 'Cancellazione strutture_asl in corso';
delete from strutture_asl where anno = anno_target_i;
   
insert into dpat_istanza(anno,entered,enteredby,stato,flag_import_piani) values(anno_target_i,current_timestamp,291,1,false) returning id into id_dpat_istanza_inserito;

for rec_dpat_old in 
select * from dpat where anno = anno_i 
loop
	INSERT INTO dpat(
            anno, id_asl,entered, entered_by, 
            modified, modified_by, enabled, completo, id_dpat_istanza, 
            congelato, data_congelamento)
    VALUES (anno_target_i, rec_dpat_old.id_asl, current_timestamp, 291,  
            current_timestamp, 291, rec_dpat_old.enabled, rec_dpat_old.completo, id_dpat_istanza_inserito, 
            false, null);
end loop;

for rec_strumento_calcolo_old in 
select * from dpat_strumento_calcolo where anno = anno_i 
loop
	insert INTO dpat_strumento_calcolo (anno,         completo,entered,          enteredby,id_asl,                                    modified,modifiedby,riaperto,stato) 
	                             values(anno_target_i,false,   current_timestamp,291,      rec_strumento_calcolo_old.id_asl, current_timestamp,291,       false,   1)
	returning id
        into id_strumento_calcolo_inserito;

        for rec_struttura_complessa_old in 
		select strutt.*
		from strutture_asl strutt
		LEFT JOIN lookup_tipologia_nodo_oia tipooia ON strutt.tipologia_struttura = tipooia.code 
		where tipologia_struttura in( 13,14) 
			and strutt.trashed_date is null
			and strutt.id_strumento_calcolo = rec_strumento_calcolo_old.id
			and (strutt.data_scadenza is null or strutt.data_scadenza> current_timestamp)
			and strutt.enabled 
		order by codice_interno_fk 
		loop
			raise info 'id struttra complessa old %', rec_struttura_complessa_old.id;
			next_id := (select max(id) +1 from strutture_asl where id <10000000);
			insert into strutture_asl(id,anno,       stato,  id_padre, id_asl, descrizione_lunga, n_livello, entered, entered_by, modified, modified_by, tipologia_struttura,
						  comune, enabled, obsoleto, confermato, id_strumento_calcolo, codice_interno_fk, nome,id_utente,mail,indirizzo,delegato,descrizione_comune,id_oia_nodo_temp,
                                                  descrizione_area_struttura_complessa,id_lookup_area_struttura_asl,ui_struttura_foglio_att_iniziale,ui_struttura_foglio_att_finale,    
                                                  id_utente_edit, percentuale_area_a,stato_all2,stato_all6,codice_interno_univoco,id_struttura_anno_prec) 
		        values(next_id,anno_target_i,       1,  rec_struttura_complessa_old.id_padre, rec_struttura_complessa_old.id_asl, rec_struttura_complessa_old.descrizione_lunga, rec_struttura_complessa_old.n_livello, current_timestamp, 291, current_timestamp, 291, rec_struttura_complessa_old.tipologia_struttura,
                               rec_struttura_complessa_old.comune, rec_struttura_complessa_old.enabled, rec_struttura_complessa_old.obsoleto, rec_struttura_complessa_old.confermato, id_strumento_calcolo_inserito, next_id, rec_struttura_complessa_old.nome,rec_struttura_complessa_old.id_utente,rec_struttura_complessa_old.mail,rec_struttura_complessa_old.indirizzo,rec_struttura_complessa_old.delegato,rec_struttura_complessa_old.descrizione_comune,rec_struttura_complessa_old.id_oia_nodo_temp,
                               rec_struttura_complessa_old.descrizione_area_struttura_complessa,rec_struttura_complessa_old.id_lookup_area_struttura_asl,rec_struttura_complessa_old.ui_struttura_foglio_att_iniziale,rec_struttura_complessa_old.ui_struttura_foglio_att_finale,    
                               rec_struttura_complessa_old.id_utente_edit, rec_struttura_complessa_old.percentuale_area_a,rec_struttura_complessa_old.stato_all2,rec_struttura_complessa_old.stato_all6,rec_struttura_complessa_old.codice_interno_univoco,rec_struttura_complessa_old.id_struttura_anno_prec)
                        returning id into id_struttura_complessa_inserita;
			raise info 'id struttra complessa new %', id_struttura_complessa_inserita;
 
                        for rec_struttura_semplice_old in 
			select *
			from strutture_asl 
			where id_padre = rec_struttura_complessa_old.id
			      and stato != 1 
			      and trashed_date is null
			      and enabled
			      and (data_scadenza is null or data_scadenza> current_timestamp)
			order by codice_interno_fk
			      loop
			        raise info 'id struttra semplice old %', rec_struttura_semplice_old.id;
			        next_id := (select max(id) +1 from strutture_asl where id <10000000);
				insert into strutture_asl(id,anno,       stato,  id_padre, id_asl, descrizione_lunga, n_livello, entered, entered_by, modified, modified_by, tipologia_struttura,
						  comune, enabled, obsoleto, confermato, id_strumento_calcolo, codice_interno_fk, nome,id_utente,mail,indirizzo,delegato,descrizione_comune,id_oia_nodo_temp,
                                                  descrizione_area_struttura_complessa,id_lookup_area_struttura_asl,ui_struttura_foglio_att_iniziale,ui_struttura_foglio_att_finale,    
                                                  id_utente_edit, percentuale_area_a,stato_all2,stato_all6,codice_interno_univoco,id_struttura_anno_prec) 
				values(next_id,anno_target_i,       1,  id_struttura_complessa_inserita, rec_struttura_semplice_old.id_asl, rec_struttura_semplice_old.descrizione_lunga, rec_struttura_semplice_old.n_livello, current_timestamp, 291, current_timestamp, 291, rec_struttura_semplice_old.tipologia_struttura,
					rec_struttura_semplice_old.comune, rec_struttura_semplice_old.enabled, rec_struttura_semplice_old.obsoleto, rec_struttura_semplice_old.confermato, id_strumento_calcolo_inserito, next_id, rec_struttura_semplice_old.nome,291,rec_struttura_semplice_old.mail,rec_struttura_semplice_old.indirizzo,rec_struttura_semplice_old.delegato,rec_struttura_semplice_old.descrizione_comune,rec_struttura_semplice_old.id_oia_nodo_temp,
                               rec_struttura_semplice_old.descrizione_area_struttura_complessa,rec_struttura_semplice_old.id_lookup_area_struttura_asl,rec_struttura_semplice_old.ui_struttura_foglio_att_iniziale,rec_struttura_semplice_old.ui_struttura_foglio_att_finale,    
                               291, rec_struttura_semplice_old.percentuale_area_a,rec_struttura_semplice_old.stato_all2,rec_struttura_semplice_old.stato_all6,rec_struttura_semplice_old.codice_interno_univoco,rec_struttura_semplice_old.id_struttura_anno_prec)
                               returning id into id_struttura_semplice_inserita;
		               raise info 'id struttra semplice new %', id_struttura_semplice_inserita;

				for rec_nominativo_old in 
					select *
					from
					  dpat_strumento_calcolo_nominativi_
					where (id_dpat_strumento_calcolo_strutture = rec_struttura_semplice_old.id or id_dpat_strumento_calcolo_strutture = rec_struttura_semplice_old.codice_interno_fk)
					  and trashed_date is null
					  and (data_scadenza is null or data_scadenza> current_timestamp)
					order by codice_interno_fk
				      loop
				        raise info 'id nominativo old %', rec_nominativo_old.id;
				        next_id := (select max(id) +1 from dpat_strumento_calcolo_nominativi_ where id <10000000);
					insert into dpat_strumento_calcolo_nominativi_(id,stato,id_anagrafica_nominativo,id_lookup_qualifica,id_dpat_strumento_calcolo_strutture, id_old_anagrafica_nominativo,
                                                 note_hd ,confermato   , confermato_da,data_conferma, data_scadenza , codice_interno_fk, modified_by, modified ,
                                                 entered ,entered_by) 
					values(next_id,1,rec_nominativo_old.id_anagrafica_nominativo, rec_nominativo_old.id_lookup_qualifica, id_struttura_semplice_inserita, rec_nominativo_old.id_old_anagrafica_nominativo, 
						rec_nominativo_old.note_hd , rec_nominativo_old.confermato   , rec_nominativo_old.confermato_da, rec_nominativo_old.data_conferma, rec_nominativo_old.data_scadenza , next_id, 291, current_timestamp , 
						current_timestamp , 291) returning id into  id_nominativo_inserito ;
					raise info 'id nominativo new %', id_nominativo_inserito;

				end loop;

			end loop;

		

		end loop;


end loop;

end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_duplica_strumento_calcolo(integer, integer)
  OWNER TO postgres;








  CREATE OR REPLACE FUNCTION public.dpat_update_struttura(    id_input integer,    data_scadenza_input timestamp without time zone,    id_asl_input integer,    tipo_struttura_input integer, descrizione_lunga_input text,id_utente_input integer,id_padre_input integer)
  RETURNS integer AS
$BODY$
DECLARE
  anno_old integer;
  codice_interno_univoco_old integer;
  id_inserito integer;
  rec_nominativo_old_struttura record;
  next_id integer;
  codice_interno_fk_inserito integer;
  stato_to_pass integer;
  data_congelamento_to_pass timestamp without time zone;
BEGIN
	select anno into anno_old from strutture_asl where id = id_input;

	select codice_interno_univoco into codice_interno_univoco_old from strutture_asl where id = id_input;
	
	select stato into stato_to_pass from strutture_asl where id = id_input;
	
	select data_congelamento into data_congelamento_to_pass from strutture_asl where id = id_input;
	
	
	perform dpat_disabilita_struttura(id_input, data_scadenza_input, id_asl_input, tipo_struttura_input, descrizione_lunga_input, id_utente_input, id_padre_input);
  
	id_inserito := (select * from dpat_insert_struttura(anno_old, id_asl_input, tipo_struttura_input, descrizione_lunga_input, id_utente_input,id_padre_input,stato_to_pass,data_congelamento_to_pass,codice_interno_univoco_old));

	codice_interno_fk_inserito := (select codice_interno_fk from strutture_asl where id = id_inserito );

	for rec_nominativo_old_struttura in 
		select *
		from
		  dpat_strumento_calcolo_nominativi_
		where (id_dpat_strumento_calcolo_strutture = (select codice_interno_fk  from strutture_asl where id = id_input) or id_dpat_strumento_calcolo_strutture = codice_interno_fk_inserito)
		  and trashed_date is null
		  and (data_scadenza is null or data_scadenza> current_timestamp)
		order by codice_interno_fk
	      loop
		next_id := (select max(id) +1 from dpat_strumento_calcolo_nominativi_ where id <10000000);
		insert into dpat_strumento_calcolo_nominativi_(id,stato,id_anagrafica_nominativo,id_lookup_qualifica,id_dpat_strumento_calcolo_strutture, id_old_anagrafica_nominativo,
			 confermato   , confermato_da,data_conferma, data_scadenza , codice_interno_fk, modified_by, modified ,
			 entered ,entered_by) 
		values(next_id,rec_nominativo_old_struttura.stato,rec_nominativo_old_struttura.id_anagrafica_nominativo, rec_nominativo_old_struttura.id_lookup_qualifica, id_inserito, rec_nominativo_old_struttura.id_old_anagrafica_nominativo, 
			 rec_nominativo_old_struttura.confermato   , rec_nominativo_old_struttura.confermato_da, rec_nominativo_old_struttura.data_conferma, rec_nominativo_old_struttura.data_scadenza , next_id, 291, current_timestamp , 
			current_timestamp , 291)  ;
		
	end loop;
    return id_inserito;		 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_update_struttura(integer, timestamp without time zone, integer, integer, text, integer, integer)
  OWNER TO postgres;







  drop FUNCTION public.dpat_insert_struttura(integer,  integer, integer,  text, integer,integer,integer,timestamp without time zone );
  
  CREATE OR REPLACE FUNCTION public.dpat_insert_struttura(anno_input integer, id_asl_input integer,tipo_struttura_input integer, descrizione_lunga_input text, id_utente_input integer, id_padre_input integer,stato_input integer, data_congelamento_input timestamp without time zone, codice_interno_univoco_input integer default null)
  RETURNS integer AS
$BODY$
DECLARE
next_id integer;
id_strumento_calcolo_to_insert integer;
id_padre_to_insert integer;
n_livello_to_insert integer;
	 	
BEGIN
	next_id := (select max(id) +1 from strutture_asl where id <10000000);
	id_strumento_calcolo_to_insert := (select id from dpat_strumento_calcolo where anno = anno_input and id_asl = id_asl_input);

	--STRUTTURA COMPLESSA o STRUTTURA SEMPLICE DIPARTIMENTALE
	if(tipo_struttura_input =13 or tipo_struttura_input=14) then
		id_padre_to_insert := (select id from strutture_asl where id_padre = 8 and id_asl =  id_asl_input);
	--STRUTTURA SEMPLICE
	else  
		id_padre_to_insert := id_padre_input;
	end if;

	--STRUTTURA COMPLESSA o STRUTTURA SEMPLICE DIPARTIMENTALE
	if(tipo_struttura_input =13 or tipo_struttura_input=14) then
		n_livello_to_insert := 2;
	--STRUTTURA SEMPLICE
	else  
		n_livello_to_insert := 3;
	end if;

	if(codice_interno_univoco_input is null) then
		codice_interno_univoco_input := next_id;
	end if;
	
        insert into strutture_asl(id,           id_padre,       id_asl,       descrizione_lunga,           n_livello,           entered,      entered_by,          modified,     modified_by, enabled,  tipologia_struttura, obsoleto, confermato,           id_strumento_calcolo, codice_interno_fk,                  stato,       anno, stato_all2, stato_all6,data_congelamento,codice_interno_univoco) 
        values (             next_id, id_padre_to_insert, id_asl_input, descrizione_lunga_input, n_livello_to_insert, current_timestamp, id_utente_input, current_timestamp, id_utente_input,    true, tipo_struttura_input,    false,       true, id_strumento_calcolo_to_insert,           next_id,     stato_input, anno_input,          0,          0,data_congelamento_input,codice_interno_univoco_input);

	return next_id;	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.dpat_insert_struttura(integer,  integer, integer,  text, integer,integer,integer,timestamp without time zone,integer )
  OWNER TO postgres;