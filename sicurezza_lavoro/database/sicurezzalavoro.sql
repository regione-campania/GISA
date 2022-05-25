--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: gds; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA gds;


ALTER SCHEMA gds OWNER TO postgres;

--
-- Name: gds_log; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA gds_log;


ALTER SCHEMA gds_log OWNER TO postgres;

--
-- Name: gds_macchine; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA gds_macchine;


ALTER SCHEMA gds_macchine OWNER TO postgres;

--
-- Name: gds_mgmt; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA gds_mgmt;


ALTER SCHEMA gds_mgmt OWNER TO postgres;

--
-- Name: gds_notifiche; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA gds_notifiche;


ALTER SCHEMA gds_notifiche OWNER TO postgres;

--
-- Name: gds_srv; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA gds_srv;


ALTER SCHEMA gds_srv OWNER TO postgres;

--
-- Name: gds_types; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA gds_types;


ALTER SCHEMA gds_types OWNER TO postgres;

--
-- Name: gds_ui; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA gds_ui;


ALTER SCHEMA gds_ui OWNER TO postgres;

--
-- Name: get_dati(character varying, character varying, bigint); Type: PROCEDURE; Schema: gds; Owner: postgres
--

CREATE PROCEDURE gds.get_dati(IN operazione character varying, IN v character varying, IN idutente bigint, OUT joutput json)
    LANGUAGE plpgsql
    AS $$

declare 	
	idtransazione bigint;
	proc_name varchar;
	id_op bigint;  
	ret gds_types.result_type;
	ret_ispezione_fasi gds_types.result_type;
	ret_nucleo_ispettori gds_types.result_type;
	ret_ispezione_imprese gds_types.result_type;
	ret_imprese_selezionabili gds_types.result_type;
	ret_ispezione_stati_ispezione_successivi gds_types.result_type;
	ret_cantiere  gds_types.result_type;
	ret_fase_persone gds_types.result_type;
	ret_fase_verbali gds_types.result_type;
	ret_cantieri gds_types.result_type;
	ret_cantiere_persona_ruoli gds_types.result_type;
	ret_cantiere_imprese gds_types.result_type;
	ret_stati_successivi gds_types.result_type;
	ret_notifiche_prec gds_types.result_type;
	retj json;
	text_msg1 varchar;	
	text_msg2 varchar;	
	text_msg3 varchar;	
	text_msg4 varchar;
	ts timestamp;
	fallito bool;
	_id_ispezione bigint;
	id_ispezione bigint;
	id_ispezione_fase bigint;
	id_notifica bigint;
	cantiere varchar;

begin 
	ts:=CLOCK_TIMESTAMP();
	proc_name:='gds.get_dati';
	idtransazione:= gds_log.get_id_transazione(idutente,proc_name);
   	COMMIT;
 
	begin 
	fallito:=false;

	case operazione
	when 'get_ispezione' then
		id_ispezione:=(v::json->>'id')::bigint;
		ret:=gds.get_ispezione(id_ispezione, idtransazione);
		if ret.esito then
			ret_ispezione_fasi:=gds.get_ispezione_fasi(id_ispezione, idtransazione);
		
			cantiere:=ret.info;
			ret.info:='{"ispezione":' || ret.info;

			--cantiere uguale a ispezione
			ret.info:= ret.info || ',"cantiere":' || cantiere;
					
			--fasi
			ret_ispezione_fasi:=gds.get_ispezione_fasi(id_ispezione, idtransazione);
			ret.info:= ret.info || ',"fasi":';
			if ret_ispezione_fasi.esito then 
				ret.info:= ret.info || ret_ispezione_fasi.info;
			else
				ret.info:= ret.info || '[]';
			end if;
		
			--nucleo ispettori
			ret_nucleo_ispettori:=gds.get_nucleo_ispettori(id_ispezione, idtransazione);
			ret.info:= ret.info || ',"nucleo_ispettivo":';
			if ret_nucleo_ispettori.esito then 
				ret.info:= ret.info || ret_nucleo_ispettori.info;
			else 
				ret.info:= ret.info || '[]';
			end if;
			
			--ispezione imprese
			ret_ispezione_imprese:=gds.get_ispezione_imprese(id_ispezione, idtransazione);
			ret.info:= ret.info || ',"imprese":';
			if ret_ispezione_imprese.esito then 
				ret.info:= ret.info || ret_ispezione_imprese.info;
			else 
				ret.info:= ret.info || '[]';
			end if;
		
			--ispezione_stati_ispezione_successivi
			ret_ispezione_stati_ispezione_successivi:=gds.get_ispezione_stati_ispezione_successivi(id_ispezione, idtransazione);
			ret.info:= ret.info || ',"stati_ispezione_successivi":';
			if ret_ispezione_stati_ispezione_successivi.esito then 
				ret.info:= ret.info || ret_ispezione_stati_ispezione_successivi.info;
			else 
				ret.info:= ret.info || '[]';
			end if;

			ret.info:=ret.info || '}';
		
			raise notice 'ret.info %',ret.info;
		end if;
	when 'get_notifica' then
		id_notifica:=(v::json->>'id')::bigint;
		ret:=gds_notifiche.get_cantieri(id_notifica, idtransazione);

		if ret.esito then
			ret.info:='{"cantiere":' || ret.info;
		
			--cantieri
			/*
			ret_cantieri:=gds_notifiche.get_cantieri(id_notifica, idtransazione);
			ret.info:= ret.info || ',"cantieri":';
			if ret_cantieri.esito then 
				ret.info:= ret.info || ret_cantieri.info;
			else
				ret.info:= ret.info || '[]';
			end if;
			*/
		
			--cantiere_persona_ruoli
			ret_cantiere_persona_ruoli:=gds_notifiche.get_cantiere_persona_ruoli(id_notifica, idtransazione);
			ret.info:= ret.info || ',"persona_ruoli":';
			if ret_cantiere_persona_ruoli.esito then 
				ret.info:= ret.info || ret_cantiere_persona_ruoli.info;
			else
				ret.info:= ret.info || '[]';
			end if;
			
			--cantiere_imprese
			ret_cantiere_imprese:=gds_notifiche.get_cantiere_imprese(id_notifica, idtransazione);
			ret.info:= ret.info || ',"cantiere_imprese":';
			if ret_cantiere_imprese.esito then 
				ret.info:= ret.info || ret_cantiere_imprese.info;
			else 
				ret.info:= ret.info || '[]';
			end if;
		
			--stati successivi
			ret_stati_successivi:=gds_notifiche.get_notifica_stati_successivi(id_notifica, idtransazione);
			ret.info:= ret.info || ',"stati_successivi":';
			if ret_stati_successivi.esito then 
				ret.info:= ret.info || ret_stati_successivi.info;
			else 
				ret.info:= ret.info || '[]';
			end if;
		
			--notifiche_prec
			ret_notifiche_prec:=gds_notifiche.get_notifiche_prec(id_notifica, idtransazione);
			ret.info:= ret.info || ',"notifiche_prec":';
			if ret_notifiche_prec.esito then 
				ret.info:= ret.info || ret_notifiche_prec.info;
			else 
				ret.info:= ret.info || '[]';
			end if;
		
			ret.info:=ret.info || '}';
		end if;
	when 'get_ispezione_fase' then 
		id_ispezione_fase:=(v::json->>'id')::bigint;
		ret:=gds.get_ispezione_fase(id_ispezione_fase, idtransazione);
		
		if ret.esito then
			ret.info:='{"fase":' || ret.info;
		
			--persone 
			ret_fase_persone:=gds.get_fase_persone(id_ispezione_fase, idtransazione);
			ret.info:= ret.info || ',"persone_sanzionate":';
			if ret_fase_persone.esito then 
				ret.info:= ret.info || ret_fase_persone.info;
			else
				ret.info:= ret.info || '[]';
			end if;
		
			--verbali
			ret_fase_verbali:=gds.get_fase_verbali(id_ispezione_fase, idtransazione);
			ret.info:= ret.info || ',"verbali":';
			if ret_fase_verbali.esito then 
				ret.info:= ret.info || ret_fase_verbali.info;
			else
				ret.info:= ret.info || '[]';
			end if;
		
			select i.id_ispezione into _id_ispezione from gds.ispezione_fasi i where i.id = id_ispezione_fase;
			RAISE notice 'cerco impese selezionabili per isp: : %', _id_ispezione;
			--imprese_selezionabili
			ret_imprese_selezionabili:=gds.get_ispezione_imprese(_id_ispezione, idtransazione);
			ret.info:= ret.info || ',"imprese_selezionabili":';
			if ret_imprese_selezionabili.esito then 
				ret.info:= ret.info || ret_imprese_selezionabili.info;
			else 
				ret.info:= ret.info || '[]';
			end if;
		
			ret.info:=ret.info || '}';
		end if;
	
		
	end case;

	RAISE notice 'gds.get_dati ret.esito: %', ret.esito;
	   
	if ret.esito=false then 
		RAISE notice 'gds.get_dati if su ret.esito=false';
		fallito=true;
		RAISE notice 'gds.get_dati ROLLBACK a seguito di ret.esito=false';
	end if;

	exception when others then
		fallito:=true;
		RAISE notice 'gds.get_dati ROLLBACK a seguito di exception';
		GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
		                  			text_msg2 = PG_EXCEPTION_DETAIL,
		                  			text_msg3 = PG_EXCEPTION_HINT,
		                 			text_msg4 = PG_EXCEPTION_CONTEXT;
		ret.esito:=false;
		ret.valore:= null;
		ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
   end;
  
  	if fallito=true then 
		rollback;
	end if;

	joutput:=row_to_json(ret);
	id_op:=gds_log.op(proc_name,idtransazione,v,joutput,ts,-1,operazione);
end;
$$;


ALTER PROCEDURE gds.get_dati(IN operazione character varying, IN v character varying, IN idutente bigint, OUT joutput json) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: result_type; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.result_type (
    esito boolean,
    valore bigint,
    msg character varying,
    info character varying
);


ALTER TABLE gds_types.result_type OWNER TO postgres;

--
-- Name: get_fase_persone(bigint, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.get_fase_persone(idfaseperona bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	rt json;

	begin
		proc_name:='gds.get_fase_persone';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select json_agg(row_to_json(vw_fase_persone)) into rt from gds.vw_fase_persone where id_ispezione_fase=idfaseperona;

		ret.valore:= idfaseperona;
	
		raise notice '%',json_array_length(rt);
	
		if json_array_length(rt) is null then
			ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
		end if;

	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds.get_fase_persone(idfaseperona bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_fase_verbali(bigint, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.get_fase_verbali(idispezionefase bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	rt json;

	begin
		proc_name:='gds.get_fase_verbali';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select json_agg(row_to_json(vw_fase_verbali)) into rt from gds.vw_fase_verbali where id_ispezione_fase=idispezionefase;

		ret.valore:= idispezionefase;
	
		raise notice '%',json_array_length(rt);
	
		if json_array_length(rt) is null then
			ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
		end if;
		
	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds.get_fase_verbali(idispezionefase bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_ispezione(bigint, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.get_ispezione(id_record bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	R_ISP gds.vw_ispezioni%ROWTYPE;
	rt json;

	begin
		proc_name:='gds.get_ispezione';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select * into R_ISP from gds.vw_ispezioni where id=id_record;
		rt:=row_to_json(R_ISP);
	
		ret.valore:= id_record;
	
	    if rt->>'id' is null then 
    		ret.esito:=false;	
    		ret.msg:='id non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
	   	end if;

	    --ret:=gds_ui.build_ret(ret,proc_name,R_ISP.id);
	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds.get_ispezione(id_record bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_ispezione_fase(bigint, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.get_ispezione_fase(id_record bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
		declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	R_ISPFAS gds.vw_ispezione_fasi%ROWTYPE;
	rt json;

	begin
		proc_name:='gds.get_ispezione';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select * into R_ISPFAS from gds.vw_ispezione_fasi where id=id_record;
		rt:=row_to_json(R_ISPFAS);
	
		ret.valore:= id_record;
	
	    if rt->>'id' is null then 
    		ret.esito:=false;	
    		ret.msg:='id non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
	   	end if;

	    --ret:=gds_ui.build_ret(ret,proc_name,R_ISP.id);
	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds.get_ispezione_fase(id_record bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_ispezione_fasi(bigint, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.get_ispezione_fasi(idispezione bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	rt json;

	begin
		proc_name:='gds.get_ispezione_fasi';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select json_agg(row_to_json(vw_ispezione_fasi_visibili)) into rt from gds.vw_ispezione_fasi_visibili where id_ispezione=idispezione 
				and id_fase_esito is not null; --Gianluca, per non visualizzare fasi vuote (fasi create e mai salvate)

		ret.valore:= idispezione;
	
		raise notice '%',json_array_length(rt);
	
		if json_array_length(rt) is null then
			ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
		end if;
	
		
	 	
	    --ret:=gds_ui.build_ret(ret,proc_name,R_ISP.id);
	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds.get_ispezione_fasi(idispezione bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_ispezione_imprese(bigint, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.get_ispezione_imprese(idispezione bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	rt json;

	begin
		proc_name:='gds.get_ispezione_imprese';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select json_agg(row_to_json(vw_ispezione_imprese)) into rt from gds.vw_ispezione_imprese where id_ispezione=idispezione;

		ret.valore:= idispezione;
	
		raise notice '%',json_array_length(rt);
	
		if json_array_length(rt) is null then
			ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
		end if;

	    --ret:=gds_ui.build_ret(ret,proc_name,R_ISP.id);
	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds.get_ispezione_imprese(idispezione bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_ispezione_stati_ispezione_successivi(bigint, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.get_ispezione_stati_ispezione_successivi(idispezione bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	rt json;

	begin
		proc_name:='gds.get_ispezione_stati_ispezione_successivi';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select json_agg(row_to_json(vw_ispezione_stati_ispezione_successivi)) into rt from gds.vw_ispezione_stati_ispezione_successivi where id_ispezione=idispezione;

		ret.valore:= idispezione;
	
		raise notice '%',json_array_length(rt);
	
		if json_array_length(rt) is null then
			ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
		end if;

	    --ret:=gds_ui.build_ret(ret,proc_name,R_ISP.id);
	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds.get_ispezione_stati_ispezione_successivi(idispezione bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_nucleo_ispettori(bigint, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.get_nucleo_ispettori(idispezione bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	rt json;

	begin
		proc_name:='gds.get_nucleo_ispettori';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select json_agg(row_to_json(vw_nucleo_ispettori)) into rt from gds.vw_nucleo_ispettori where id_ispezione=idispezione;

		ret.valore:= idispezione;
	
		raise notice '%',json_array_length(rt);
	
		if json_array_length(rt) is null then
			ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
		end if;

	    --ret:=gds_ui.build_ret(ret,proc_name,R_ISP.id);
	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds.get_nucleo_ispettori(idispezione bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_verbale(bigint, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.get_verbale(id_record bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	R_V gds.vw_verbali%ROWTYPE;
	rt json;

	begin
		proc_name:='gds.get_verbale';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select * into R_V from gds.vw_verbali where id=id_record;
		rt:=row_to_json(R_V);
	
		ret.valore:= id_record;
	
	    if rt->>'id' is null then 
    		ret.esito:=false;	
    		ret.msg:='id non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
	   	end if;

	    --ret:=gds_ui.build_ret(ret,proc_name,R_ISP.id);
	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds.get_verbale(id_record bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: ins_fase_verbale(character varying, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.ins_fase_verbale(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; -- start
	ret_fase_verbale gds_types.result_type;
	ret_fase_verbali gds_types.result_type;
	id_op bigint;   
	proc_name varchar; -- END
	R_FV gds.fase_verbali%ROWTYPE; 
	R_FV_APP gds.vw_fase_verbali%ROWTYPE; 
	n integer;
	id_fase_esito bigint;

	begin
	proc_name:='gds.ins_fase_verbale';
	R_FV_APP:=  json_populate_record(null::gds.vw_fase_verbali, v::json);	

	R_FV.id:=nextval('gds.fase_verbali_id_seq');
	--R_IF.data_creazione:=clock_timestamp();
	--R_IF.data_modifica:=clock_timestamp();
	R_FV.id_verbale:=R_FV_APP.id_verbale;
	R_FV.id_ispezione_fase:=R_FV_APP.id_ispezione_fase;
	R_FV.data:=clock_timestamp();
	 
	 --Gianluca, elimino verbali precedenti
	delete from gds.fase_verbali where id_ispezione_fase = R_FV_APP.id_ispezione_fase;

	insert into gds.fase_verbali values (R_FV.*);

	n:=gds_log.upd_record('gds.ins_fase_verbale',idtransazione,R_FV,'I');

    ret.esito:=true;	
	ret:=gds_ui.build_ret(ret,proc_name,R_FV.id);
	--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
	return ret;
	end;
END
$$;


ALTER FUNCTION gds.ins_fase_verbale(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: ins_ispezione(character varying, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.ins_ispezione(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; -- START
	id_op bigint;   
	proc_name varchar; -- END
	R_I gds.ispezioni%ROWTYPE; 
	R_I_APP gds.vw_ispezioni%ROWTYPE; 
	R_S gds_types.vw_stati_ispezione%ROWTYPE; 
	n integer;
	begin
	proc_name:='gds.ins_ispezione';
	--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
	select * into R_S from gds_types.vw_stati_ispezione where descr='INIZIALE';
	raise notice 'ID_stato %', R_S.id;
	R_I_APP:=  json_populate_record(null::gds.vw_ispezioni, v::json);	

	R_I.id:=nextval('gds.ispezioni_id_seq');
	R_I.id_stato_ispezione:=R_S.id;

	R_I.data_inserimento:=clock_timestamp();
	R_I.data_modifica:=clock_timestamp();
	R_I.altro := ''; 
	R_I.descr := ''; 
	R_I.note := ''; 
	R_I.codice_ispezione := LPAD(R_I.id::varchar, 6, '0'); 
    insert into gds.ispezioni values (R_I.*);
    --insert into gds._h_ispezioni values (nextval('gds."_h_ispezioni_id_seq"'),R_I.*,tsrange(clock_timestamp()::timestamp,null,'[)'),idtransazione,clock_timestamp(),current_timestamp);
	n:=gds_log.upd_record('gds.ispezioni',idtransazione,R_I,'I');

    ret.esito:=true;	
	ret:=gds_ui.build_ret(ret,proc_name,R_I.id);
	--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
	return ret;
	end;
END
$$;


ALTER FUNCTION gds.ins_ispezione(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: ins_ispezione_fase(character varying, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.ins_ispezione_fase(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; -- start
	ret_ispezione_imprese gds_types.result_type;
	ret_ispezione_fasi gds_types.result_type;
	id_op bigint;   
	proc_name varchar; -- END
	R_IF gds.ispezione_fasi%ROWTYPE; 
	R_IF_APP gds.vw_ispezione_fasi%ROWTYPE; 
	R_S gds_types.vw_stati%ROWTYPE; 
	n integer;
	id_fase_esito bigint;

	begin
	proc_name:='gds.ins_ispezione_fase';
	--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
	/* AF --------------
	 * select * into R_S from gds_types.vw_stati_ispezione where descr='INIZIALE';
	 * raise notice 'ID_stato %', R_S.id;
	 */
	R_IF_APP:=  json_populate_record(null::gds.vw_ispezione_fasi, v::json);	

	R_IF.id:=nextval('gds.ispezione_fasi_id_seq');
	/* AF --------------
	 *  R_IF.id_stato:=R_S.id;
	 */

	R_IF.data_creazione:=clock_timestamp();
	R_IF.data_modifica:=clock_timestamp();
	R_IF.id_ispezione:=R_IF_APP.id_ispezione;
	 
	-------------------ALEX
	R_IF.id_impresa_sede:=null;
	R_IF.id_fase_esito:=null;
	R_IF.data_fase:=null;
	R_IF.note:=null;

	ret_ispezione_imprese:=gds.get_ispezione_imprese(R_IF_APP.id_ispezione,idtransazione);
	raise notice 'ret_ispezione_imprese.info: %',ret_ispezione_imprese.info;
	raise notice 'ret_ispezione_imprese esito %',ret_ispezione_imprese.esito;
	
	if ret_ispezione_imprese.esito then
		raise notice 'length ret_ispezione_imprese.info %',json_array_length(ret_ispezione_imprese.info::json);
		if json_array_length(ret_ispezione_imprese.info::json)=1 then 
			raise notice 'id_impresa_sede %',(((ret_ispezione_imprese.info::json)->>0)::json)->>'id_impresa_sede';
			R_IF.id_impresa_sede:=(((ret_ispezione_imprese.info::json)->>0)::json)->>'id_impresa_sede';
		end if;
	end if;

	ret_ispezione_fasi:=gds.get_ispezione_fasi(R_IF_APP.id_ispezione,idtransazione);
	raise notice 'ret_ispezione_fasi esito %',ret_ispezione_fasi.esito;
	if ret_ispezione_fasi.esito=false then
		raise notice 'non ci sono fasi per ispezione %',R_IF_APP.id_ispezione;
		select id into id_fase_esito from gds_types.vw_fasi_esiti where descr_fase='PRIMO SOPRALLUOGO' and descr_esito_per_fase ='---';
		--R_IF.id_fase_esito:=id_fase_esito; --Gianluca non inserisco primo sopralluogo a primo inserimento
	end if;
	-------------------ALEX

	insert into gds.ispezione_fasi values (R_IF.*);
	
	/*
	insert
		into
		gds._h_ispezione_fasi
	values (nextval('gds."_h_ispezione_fasi_id_seq"'),
			R_IF.*,
			tsrange(clock_timestamp()::timestamp,null,'[)'),
			idtransazione,
			clock_timestamp(),
			current_timestamp);
   */
	n:=gds_log.upd_record('gds.ispezione_fasi',idtransazione,R_IF,'I');

    ret.esito:=true;	
	ret:=gds_ui.build_ret(ret,proc_name,R_IF.id);
	--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
	return ret;
	end;
END
$$;


ALTER FUNCTION gds.ins_ispezione_fase(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: ins_verbale(character varying, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.ins_verbale(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; -- START
	id_op bigint;   
	proc_name varchar; -- END
	R_V gds.verbali%ROWTYPE; 
	R_V_APP gds.vw_verbali%ROWTYPE; 
	n integer;
	
	begin
		proc_name:='gds.ins_verbale';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');

		R_V_APP:=  json_populate_record(null::gds.vw_verbali, v::json);	
	
		R_V.id:=nextval('gds.verbali_id_seq');
		--R_V.data_inserimento:=clock_timestamp();
		--R_V.data_modifica:=clock_timestamp();
		R_V.id_modulo := R_V_APP.id_modulo; 
		R_V.data_verbale := R_V_APP.data_verbale; 
		
	    insert into gds.verbali values (R_V.*);
	  	n:=gds_log.upd_record('gds.verbali',idtransazione,R_V,'I');
	
	    ret.esito:=true;	
		ret:=gds_ui.build_ret(ret,proc_name,R_V.id);
		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
		return ret;
	end;
END
$$;


ALTER FUNCTION gds.ins_verbale(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: load_imprese(); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.load_imprese() RETURNS text
    LANGUAGE plpgsql
    AS $$ 
	begin
		declare n integer;
			k integer;
	    	idindirizzo bigint;
	    	idtiposede bigint;
	   		R_I Record;    
	begin
		--n:=setval('gds.imprese_id_seq', 10000);
		--n:=setval('gds.impresa_sedi_id_seq', 10000);
		delete from gds.impresa_sedi;
		delete from gds.imprese;
		for R_I in select i.*,c.id as id_comune,cs.id as id_comune_sede,ng.id as id_natura_giuridica from gesdasic_3_2021.imp_imprese i
							join public.comuni1 c on c.istat = i.codiceistatsedelegale left join public.comuni1 cs on i.codiceistatpat = cs.istat
							left join gds_types.natura_giuridica ng on ng.codice=i.naturagiuridica  order by i.id loop
			n:=R_I.id+10000;
			idindirizzo:=public.insert_opu_noscia_indirizzo( 106,null,R_I.id_comune,null,null,R_I.indirizzosedelegale,
						case when R_I.capsedelegale='80100' then '80145' else R_I.capsedelegale end ,R_I.numerocivicosedelegale,R_I.latitudinesedelegale ,R_I.longitudinesedelegale);
			insert into gds.imprese (id,codice_inail,codice_fiscale,partita_iva,id_natura_giuridica,nome_azienda,
									 codice_ateco,tipo_ateco,anno_competenza,numero_dipendenti,numero_artigiani,addetti_speciali,note,
									 data_inserimento,operatore_inserimento,data_modifica,operatore_modifica)values
									(n,R_I.codiceinail,R_I.codicefiscale,R_I.partitaiva,R_I.id_natura_giuridica,R_I.nomeazienda ,R_I.codiceateco ,
									R_I.tipoateco ,R_I.annocompetenza,R_I.numerodipendenti,R_I.numeroartigiani,R_I.addettispeciali,R_I.note,R_I.data_inserimento,R_I.operatore_inserimento,
									R_I.data_modifica,R_I.operatore_modifica);
			select id into idtiposede from gds_types.tipi_sede where descrizione ='Sede legale';
			insert into gds.impresa_sedi (id,id_impresa,id_tipo_sede,codice_pat,datainizio,datafine,id_indirizzo)
				values (n*2,n,idtiposede,R_I.codicepat,
							(case when R_I.datainiziopat like '000%' then null else R_I.datainiziopat end)::timestamp,
							(case when R_I.datafinepat   like '000%' then null else R_I.datafinepat   end)::timestamp,idindirizzo);
						
			idindirizzo:=public.insert_opu_noscia_indirizzo( 106,null,R_I.id_comune_sede,null,null,R_I.indirizzopat,case when R_I.cappat='80100' then '80145' else R_I.cappat end ,
									R_I.numerocivicopat,R_I.latitudinepat ,R_I.longitudinepat);
			select id into idtiposede from gds_types.tipi_sede where descrizione ='Stabilimento';
			insert into gds.impresa_sedi (id,id_impresa,id_tipo_sede,codice_pat,datainizio,datafine,id_indirizzo)
				values (n*2+1,n,idtiposede,R_I.codicepat,
							(case when R_I.datainiziopat like '000%' then null else R_I.datainiziopat end)::timestamp,
							(case when R_I.datafinepat   like '000%' then null else R_I.datafinepat   end)::timestamp,idindirizzo);

		end loop;
		insert into gds.ispezione_imprese select nextval('gds.ispezione_imprese_id_seq'),idispezione,(idimpresa+10000)*2 from gesdasic_3_2021.isp_su_imprese;
		k:=setval('gds.imprese_id_seq', n+1);
		k:=setval('gds.impresa_sedi_id_seq', n+1);
	return 'OK';
   end;
END
$$;


ALTER FUNCTION gds.load_imprese() OWNER TO postgres;

--
-- Name: load_tables_ispezioni(); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.load_tables_ispezioni() RETURNS text
    LANGUAGE plpgsql
    AS $$ 
     begin
	     declare n integer;
begin
truncate table gds.ispezioni cascade;
truncate table gds_types.fasi_esiti cascade;
truncate table gds_types.esiti_per_fase cascade;
truncate table gds_types.fasi cascade;
truncate table gds.ispezione_fasi cascade;
truncate table gds.fase_persone cascade;
delete from public.role where role_id < 300;
delete from public.opu_soggetto_fisico where id between 20000 and 30000 and id not in (select id_soggetto_fisico from gds_notifiche.cantiere_persona_ruoli);
delete from public.contact_ where user_id between 20000 and 30000 ;--and user_id not in (select id_soggetto_fisico from gds_notifiche.cantiere_persona_ruoli);
delete from public.access_ where user_id between 20000 and 30000;-- and user_id not in (select id_soggetto_fisico from gds_notifiche.cantiere_persona_ruoli);

delete from gds.nucleo_ispettori;


insert into gds.ispezioni (id,id_cantiere,id_motivo_isp,altro,
id_ente_uo,descr,id_stato_ispezione,per_conto_di,data_inserimento,
data_modifica,note,data_ispezione,codice_ispezione)
select id,ic.idcantiere ,idmotivo,null,identeunitaoperativa,descrizione,
idstato,replace(codiceasl ,'15','2')::integer,i.data_inserimento ,i.data_modifica ,
note,"data", to_char(id,'000000') from gesdasic_3_2021.isp_ispezioni i left join gesdasic_3_2021.isp_su_cantieri ic on ic.idispezione =i.id ;
 n:=setval('gds.ispezioni_id_seq', (SELECT MAX(id) + 1 FROM gds.ispezioni)); 




insert into gds_types.fasi select * from gesdasic_3_2021.fas_tipi;

insert into gds_types.esiti_per_fase
select distinct id,esito,riferimento from gesdasic_3_2021.fas_esiti where id not in (9,25);

insert into gds_types.fasi_esiti
select min(id),idtipo,case when idesito=9 then 5 when idesito=25 then 6 else idesito end 
from gesdasic_3_2021.fas_stati fs2 
group by 2,3;


insert into gds.ispezione_fasi  (id,id_ispezione,data_creazione,data_modifica,
id_impresa_sede,id_fase_esito,data_fase,note)
select id, idispezione,data_inserimento ,data_modifica, (idimpresa+10000)*2+1 ,idstato,"data",note
from gesdasic_3_2021.fas_fasi;

raise notice 'DOPO gds.ispezione_fasi';
insert into public."role" (role_id,role,description,entered,enteredby,modified,modifiedby)
select id,ruolo,'Profilo '||ruolo,case when data_inserimento::varchar like '0000%' then '2000-01-01 00:00:00'::timestamp else data_inserimento::timestamp  end ,operatore_inserimento,
case when data_modifica::varchar like '0000%' then '2000-01-01 00:00:00'::timestamp else data_modifica::timestamp end ,operatore_modifica
from gesdasic_3_2021.ope_ruoli;

raise notice 'DOPO public.role';
insert into public.opu_soggetto_fisico (nome,cognome,codice_fiscale,id)
select nome,cognome,codicefiscale,id+20000 from gesdasic_3_2021.ope_operatori
where  id not in (select id_soggetto_fisico-20000 from gds_notifiche.cantiere_persona_ruoli) ;

insert into public.access_
	(user_id,contact_id,username,password,role_id,enabled,entered,last_login,modified,last_interaction_time,
	in_dpat,in_nucleo_ispettivo,in_access,enteredby,modifiedby,allow_webdav_access,allow_httpapi_access)
select id+20000, id+20000,username,password,
idQualifica,case when idStato=1 then true else false end,
case when data_inserimento::varchar like '0000%' then '2000-01-01 00:00:00'::timestamp else data_inserimento::timestamp  end,
case when data_ultimo_accesso::varchar like '0000%' then '2000-01-01 00:00:00'::timestamp else data_ultimo_accesso::timestamp  end,
case when data_modifica::varchar like '0000%' then '2000-01-01 00:00:00'::timestamp else data_modifica::timestamp end ,
case when data_ultimo_accesso::varchar like '0000%' then '2000-01-01 00:00:00'::timestamp else data_ultimo_accesso::timestamp  end,
false,false,true,operatore_inserimento+20000 ,operatore_modifica+20000 ,true,true from gesdasic_3_2021.ope_operatori;
--where  id not in (select id_soggetto_fisico-20000 from gds_notifiche.cantiere_persona_ruoli) ;


insert into public.contact_ (user_id,contact_id,namefirst,namelast,notes,
         enabled,entered,modified, enteredby, modifiedby,site_id,employee)
select  id+20000, id+20000,nome,cognome,note,case when idStato=1 then true else false end,
case when data_inserimento::varchar like '0000%' then '2000-01-01 00:00:00'::timestamp else data_inserimento::timestamp  end,
case when data_modifica::varchar like '0000%' then '2000-01-01 00:00:00'::timestamp else data_modifica::timestamp end ,
operatore_inserimento+20000 ,operatore_modifica+20000,-1,false from gesdasic_3_2021.ope_operatori ;
--where  id not in (select id_soggetto_fisico-20000 from gds_notifiche.cantiere_persona_ruoli) ;

insert into gds.nucleo_ispettori (id,id_ispezione,id_access,progressivo,data_inserimento)
select nextval('gds.nucleo_ispettori_id_seq'),idIspezione,idOperatore+20000,
row_number() over(partition by idIspezione order by data_inserimento),data_inserimento
from gesdasic_3_2021.ope_su_ispezioni;

insert into gds.fase_persone 
select row_number () over (order by idfase,idpersona),idpersona+20000 ,idfase, '2000-01-01 00:00:00', row_number () over (partition by idfase)
from gesdasic_3_2021.fas_su_persone fsp ;
	RETURN 'OK';
   end;
END
$$;


ALTER FUNCTION gds.load_tables_ispezioni() OWNER TO postgres;

--
-- Name: upd_dati(character varying, character varying, bigint); Type: PROCEDURE; Schema: gds; Owner: postgres
--

CREATE PROCEDURE gds.upd_dati(IN operazione character varying, IN v character varying, IN idutente bigint, OUT joutput json)
    LANGUAGE plpgsql
    AS $$
declare 
	idtransazione bigint;
	proc_name varchar;
	id_op bigint;  
	ret gds_types.result_type;
	--retj json;
	text_msg1 varchar;	
	text_msg2 varchar;	
	text_msg3 varchar;	
	text_msg4 varchar;
	ts timestamp;
	fallito bool;

begin
	ts:=CLOCK_TIMESTAMP();
	proc_name:='gds.upd_dati';
	idtransazione:= gds_log.get_id_transazione(idutente,proc_name);
   	COMMIT;
  
   	begin
	   	fallito:=false;
	   	case operazione
	   		when 'upd_ispezione' then
	   			ret:=gds.upd_ispezione(v::json->>'ispezione', idtransazione);
	   			/*if ret.esito then
		   			ret:=gds.upd_nucleo_ispettori(v::json->>'nucleo_ispettivo', idtransazione);
		   		end if;*/
				raise notice 'ret upd_ispezione %',ret;
	   			if ret.esito then
		   			ret:=gds.upd_ispezione_imprese(v::json->>'imprese', idtransazione);
		   		end if;
	   		when 'upd_notifica' then
	   			ret:=gds_notifiche.upd_cantiere_persona_ruoli(v::json->>'persona_ruoli',idtransazione);
	   		   			
	   			if ret.esito then
		   			ret:=gds_notifiche.upd_cantiere_imprese(v::json->>'cantiere_imprese',idtransazione);
		   			
		   			if ret.esito then
		   				ret:=gds_notifiche.upd_notifica(v::json->>'cantiere',idtransazione);
		   			end if;
		   		end if;

				raise notice '%',ret;
			when 'upd_ispezione_fase' then
	   			ret:=gds.upd_ispezione_fase(v::json->>'fase',idtransazione);
	   		   			
	   			/*if ret.esito then
		   			ret:=gds.(v::json->>'persone_sanzionate',idtransazione);
		   			
		   			if ret.esito then
		   				ret:=gds.(v::json->>'verbali',idtransazione);
		   			end if;
		   		end if;*/

				--raise notice '%',ret;
			when 'ins_ispezione' then
				 ret:=gds.ins_ispezione(v, idtransazione);
			when 'ins_ispezione_fase' then 
				ret:=gds.ins_ispezione_fase(v,idtransazione);
			when 'ins_notifica' then
	   			ret:=gds_notifiche.ins_notifica(v,idtransazione);
			when 'ins_verbale' then
				ret:=gds_notifiche.ins_verbale(v,idtransazione);
			else
	   			ret:=null;
	   	end case;
		
	    RAISE notice 'gds.upd_dati ret.esito: %', ret.esito;
	   
   		if ret.esito=false then 
   			RAISE notice 'gds.upd_dati if su ret.esito=false';
   			fallito=true;
	   		RAISE notice 'gds.upd_dati ROLLBACK a seguito di ret.esito=false';
   		end if;
		
	exception when others then
		fallito:=true;
		RAISE notice 'gds.upd_dati ROLLBACK a seguito di exception';
		GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			RAISE notice '%', ret;
	end;
	
	if fallito=true then 
		rollback;
	end if;

	joutput:=row_to_json(ret);
	id_op:=gds_log.op(proc_name,idtransazione,v,joutput,ts,-1,operazione);

end;
$$;


ALTER PROCEDURE gds.upd_dati(IN operazione character varying, IN v character varying, IN idutente bigint, OUT joutput json) OWNER TO postgres;

--
-- Name: upd_ispezione(character varying, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.upd_ispezione(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare 
        ret             gds_types.result_type; -- startCall FVG-BDN
        R_ispezione     gds.vw_ispezioni%ROWTYPE;
        R_ispezione_old gds.vw_ispezioni%ROWTYPE;
        R_isp           gds.ispezioni%ROWTYPE;
        R_stato         gds_types.stati_ispezione%ROWTYPE;
        R_stato_new     gds_types.stati_ispezione%ROWTYPE;
        proc_name varchar; -- end
        n int;
	begin
		proc_name:='gds.upd_ispezione';
        --id_op:=gds_log.start_op(proc_name,idtransazione ,v);

	    R_ispezione:=  (json_populate_record(null::gds.vw_ispezioni,v::json));
		R_ispezione.id:=R_ispezione.id_ispezione;
        raise notice 'R_ispezione.id %',R_ispezione.id;
        
        select * into R_ispezione_old from gds.vw_ispezioni where id = R_ispezione.id;      
        raise notice 'R_ispezione_old.id_stato_ispezione %',R_ispezione_old.id_stato_ispezione;
       
       	select * into R_stato     from gds_types.stati_ispezione s where id= R_ispezione_old.id_stato_ispezione;
        select * into R_stato_new from gds_types.stati_ispezione s where id= R_ispezione.id_stato_ispezione;
        raise notice 'R_stato %',R_stato.id;
       
       	select * into R_isp from gds.ispezioni n where id=R_ispezione.id;
       
       RAISE notice 'OLD DESCR: %', R_ispezione_old.descr;
       RAISE notice 'NEW DESCR: %', R_ispezione.descr;
     
       	IF R_ispezione.id_stato_ispezione != R_ispezione_old.id_stato_ispezione then
        	select count(*) into n from gds_types.stati_ispezione_successivi ss
			where ss.id_stato_ispezione_attuale = R_ispezione_old.id_stato_ispezione and ss.id_stato_ispezione_prossimo =R_ispezione.id_stato_ispezione;
		    raise notice 'CAMBIAMENTO STATO N %',n;
		    raise notice 'CAMBIAMENTO STATO OLD % NEW %',R_stato.descr,R_stato_new.descr;

			if n < 1 or n> 1then
			      ret.esito:=false;	
				  ret:=gds_ui.build_ret(ret,proc_name,-2);
				  --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                  return ret;
			end if;
        end if;
       
        if R_ispezione_old.modificabile = false and false then 
    		ret.esito:=false;	
            ret:=gds_ui.build_ret(ret,proc_name,-4);
            --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
            return ret;
	    end if;
	   	if R_ispezione.altro            is null then R_ispezione.altro := '';            end if;	   
		if R_ispezione.descr            is null then R_ispezione.descr := '';        end if;	 
		if R_ispezione.note             is null then R_ispezione.note := '';             end if;
		if R_ispezione.codice_ispezione is null then R_ispezione.codice_ispezione := ''; end if;
	   
	   
		if R_ispezione.id_cantiere   != R_ispezione_old.id_cantiere or
		   R_ispezione.id_motivo_isp != R_ispezione_old.id_motivo_isp or
		   R_ispezione.altro != R_ispezione_old.altro or
		   R_ispezione.id_ente_uo != R_ispezione_old.id_ente_uo or
		   R_ispezione.descr != R_ispezione_old.descr or
		   R_ispezione.id_stato_ispezione != R_ispezione_old.id_stato_ispezione or
		   R_ispezione.per_conto_di != R_ispezione_old.per_conto_di or
		   R_ispezione.note != R_ispezione_old.note or
		   R_ispezione.codice_ispezione != R_ispezione_old.codice_ispezione then
		   
		   RAISE notice 'DENTRO IF NEW DESCR: %', R_ispezione.descr;
		  
		   update gds.ispezioni set (id_cantiere, id_motivo_isp,altro,
		   id_ente_uo, descr,  id_stato_ispezione,
		   per_conto_di,note,codice_ispezione,data_ispezione) =
		   (R_ispezione.id_cantiere,  R_ispezione.id_motivo_isp,  R_ispezione.altro,  R_ispezione.id_ente_uo,
		   R_ispezione.descr_isp, R_ispezione.id_stato_ispezione, R_ispezione.per_conto_di,
		   R_ispezione.note,   R_ispezione.codice_ispezione, R_ispezione.data_ispezione) 
		   where id=R_ispezione.id;
		end if;
	    update gds.ispezioni set data_modifica = CLOCK_TIMESTAMP() where id=R_isp.id; 
	   	n:=gds_log.upd_record('gds.ispezioni',idtransazione,R_isp,'U');
	    ret.esito:=true;	
        ret:=gds_ui.build_ret(ret,proc_name, 0);
        --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
        return ret;
	end;
END
$$;


ALTER FUNCTION gds.upd_ispezione(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_ispezione_fase(character varying, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.upd_ispezione_fase(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare 
        ret             	 gds_types.result_type; -- start
        R_ispezione_fase     gds.vw_ispezione_fasi%ROWTYPE;
        R_ispezione_fase_old gds.vw_ispezione_fasi%ROWTYPE;
        R_isp_fase           gds.ispezione_fasi%ROWTYPE;
        R_stato         	 gds_types.stati_ispezione%ROWTYPE;
        R_fase_esito		 gds_types.vw_fasi_esiti%ROWTYPE;
        proc_name varchar; -- end
        n int;
	begin
		proc_name:='gds.upd_ispezione_fase';
        --id_op:=gds_log.start_op(proc_name,idtransazione ,v);
		R_ispezione_fase :=  (json_populate_record(null::gds.vw_ispezione_fasi,v::json));
		R_ispezione_fase.id:=R_ispezione_fase.id_ispezione_fase;

        raise notice 'R_ispezione_fase.id %',R_ispezione_fase.id;
       raise notice 'R_ispezione_fase.data_fase %',R_ispezione_fase.data_fase;
        select * into R_ispezione_fase_old from gds.vw_ispezioni where id = R_ispezione_fase.id;
       
        /*if R_ispezione_fase_old.modificabile = false then 
    		ret.esito:=false;	
            ret:=gds_ui.build_ret(ret,proc_name,-4);
            --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
            return ret;
	    end if;*/
       
       
       if (R_ispezione_fase.id_fase_esito = 1 or R_ispezione_fase.id_fase_esito = 2) and --altro
      	 	(trim(R_ispezione_fase.altro_esito) = '' or R_ispezione_fase.altro_esito is null)
       			then 
    		ret.esito:=false;	
            ret:=gds_ui.build_ret(ret,proc_name,-4);
            --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
            return ret;
	    end if;
	   
	   	if R_ispezione_fase.note is null then R_ispezione_fase.note := ''; end if;	      
	   
	   if R_ispezione_fase.id_fase_esito is null then
	        ret.esito:=false;	
            ret:=gds_ui.build_ret(ret,proc_name,-5);
                       return ret;
	   end if;
	   select count(*) into n from gds.vw_ispezione_fasi_visibili where id_ispezione =R_ispezione_fase.id_ispezione;
	   if n = 0 then
	   	select * into R_fase_esito from gds_types.vw_fasi_esiti vfe where id =R_ispezione_fase.id_fase_esito;
	   		if R_fase_esito != 'PRIMO SOPRALLUOGO' then
	   			ret.esito:=false;	
            	ret:=gds_ui.build_ret(ret,proc_name,-6);
                return ret;
	   		end if;
	   end if;
		if R_ispezione_fase.id_impresa_sede  != R_ispezione_fase_old.id_impresa_sede  or (R_ispezione_fase_old.id_impresa_sede is null and  R_ispezione_fase.id_impresa_sede is not null) or
		   R_ispezione_fase.id_fase_esito != R_ispezione_fase_old.id_fase_esito or (R_ispezione_fase_old.id_fase_esito is null and  R_ispezione_fase.id_fase_esito is not null) or
		   R_ispezione_fase.note != R_ispezione_fase_old.note or (R_ispezione_fase_old.note is null and  R_ispezione_fase.note is not null) or
  		   R_ispezione_fase.data_fase != R_ispezione_fase_old.data_fase or (R_ispezione_fase_old.data_fase is null and  R_ispezione_fase.data_fase is not null) or
  		   R_ispezione_fase.altro_esito != R_ispezione_fase_old.altro_esito or (R_ispezione_fase_old.altro_esito is null and  R_ispezione_fase.altro_esito is not null)

		   then
		   update gds.ispezione_fasi set (id_impresa_sede, id_fase_esito,note, data_fase, altro_esito) =
		   (R_ispezione_fase.id_impresa_sede,  R_ispezione_fase.id_fase_esito,  R_ispezione_fase.note, R_ispezione_fase.data_fase, R_ispezione_fase.altro_esito)
		   where id=R_ispezione_fase.id;
		end if;	   
	   
	   

	    update gds.ispezione_fasi set data_modifica = CLOCK_TIMESTAMP() where id=R_ispezione_fase.id; 
	   	n:=gds_log.upd_record('gds.ispezione_fasi',idtransazione,R_ispezione_fase,'U');
	    ret.esito:=true;	
        ret:=gds_ui.build_ret(ret,proc_name, 0);
        --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
        return ret;
	end;
END
$$;


ALTER FUNCTION gds.upd_ispezione_fase(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_ispezione_fasi_old(character varying, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.upd_ispezione_fasi_old(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare 
        ret             	 gds_types.result_type; -- start
        R_ispezione_fase     gds.vw_ispezione_fasi%ROWTYPE;
        R_ispezione_fase_old gds.vw_ispezione_fasi%ROWTYPE;
        R_isp_fase           gds.ispezione_fasi%ROWTYPE;
        R_stato         	 gds_types.stati_ispezione%ROWTYPE;
        proc_name varchar; -- end
        n int;
	begin
		proc_name:='gds.upd_ispezione_fase';
        --id_op:=gds_log.start_op(proc_name,idtransazione ,v);
		R_ispezione_fase :=  (json_populate_record(null::gds_notifiche.vw_ispezione_fasi,v::json));
		R_ispezione_fase.id:=R_ispezione_fase.id_ispezione_fase;

        raise notice 'R_ispezione_fase.id %',R_ispezione_fase.id;
        select * into R_ispezione_fase_old from gds.vw_ispezione where id = R_ispezione.id;
       
        if R_ispezione_fase.modificabile = false then 
    		ret.esito:=false;	
            ret:=gds_ui.build_ret(ret,proc_name,-4);
            --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
            return ret;
	    end if;
	   
	   	if R_ispezione_fase.note is null then R_ispezione_fase.note := ''; end if;	      
	   
		if R_ispezione_fase.id_impresa_sede   != R_ispezione_fase_old.id_impresa_sede or
		   R_ispezione_fase.id_fase_esito != R_ispezione_fase.id_fase_esito or
		   R_ispezione_fase.note != R_ispezione_fase.note then
		   update gds.ispezione_fase set (id_impresa_sede, id_fase_esito,note) =
		   (R_ispezione_fase.id_impresa_sede,  R_ispezione_fase.id_fase_esito,  R_ispezione_fase.note)
		   where id=R_ispezione_fase.id;
		end if;	   
	   
	   

	    update gds.ispezione_fase set data_modifica = CLOCK_TIMESTAMP() where id=R_isp_fase.id; 
	   	n:=gds_log.upd_record('gds.ispezione_fasi',idtransazione,R_isp_fase,'U');
	    ret.esito:=true;	
        ret:=gds_ui.build_ret(ret,proc_name, 0);
        --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
        return ret;
	end;
END
$$;


ALTER FUNCTION gds.upd_ispezione_fasi_old(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_ispezione_imprese(character varying, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.upd_ispezione_imprese(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
		ret gds_types.result_type; 
		id_op bigint;   
		proc_name varchar;
		R_ispezione_imprese gds.vw_ispezione_imprese%ROWTYPE;
		R_ispezione_imprese_old gds.ispezione_imprese%ROWTYPE;
		list bigint[];
		n bigint;
   	    R_II record;
   	   
	begin
		proc_name:='gds.upd_ispezione_imprese';
	    --id_op:=gds_log.start_op(proc_name,idtransazione ,v);
		raise notice 'gds.upd_ispezione_imprese solo % ' ,v;
		select array_append(list,-1) into list;
	
		for R_ispezione_imprese in select * from json_populate_recordset(null::gds.vw_ispezione_imprese, v::json) loop
			raise notice 'gds.upd_ispezione_imprese loop 1 start' ;
			/* verifica che tutti le imprese abbiano la stessa ispezione */  
			IF n is not null and R_ispezione_imprese.id_ispezione is not null and n != R_ispezione_imprese.id_ispezione then 		
       	    	ret.esito:=false;	
				ret:=gds_ui.build_ret(ret,proc_name,-1);
				--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret));
		    	return ret;         
       		end if;
       		n:=R_ispezione_imprese.id_ispezione;
       		raise notice 'gds.upd_ispezione_imprese loop 1 end' ;
       	end loop;
       
       	n:=1;
       
        for R_ispezione_imprese in select * from json_populate_recordset(null::gds.vw_ispezione_imprese, v::json) loop
	       	raise notice 'gds.upd_ispezione_imprese loop 2 start';
    	       
	       /* if (R_ispezione_imprese.partita_iva  is null or R_ispezione_imprese.partita_iva ='') then
				continue;
			end if;*/
				       
			n:=n+1;
			
			raise notice 'gds.upd_ispezione_imprese Prima di select su old'; 
			select * into R_ispezione_imprese_old from gds.ispezione_imprese where id_ispezione = R_ispezione_imprese.id_ispezione and id_impresa_sede=R_ispezione_imprese.id_impresa_sede;
       		raise notice 'gds.upd_ispezione_imprese R_ispezione_imprese_old.id: %',R_ispezione_imprese_old.id; 
	       	
       		if R_ispezione_imprese_old.id is null then
	       		raise notice 'gds.upd_ispezione_imprese loop 2 CASE INSERT';
		       	R_ispezione_imprese_old.id:=nextval('gds.ispezione_imprese_id_seq'); 
				R_ispezione_imprese_old.id_ispezione:=R_ispezione_imprese.id_ispezione;
				R_ispezione_imprese_old.id_impresa_sede :=R_ispezione_imprese.id_impresa_sede;
				R_ispezione_imprese_old.progressivo:=R_ispezione_imprese.progressivo;
				
				insert into gds.ispezione_imprese (id,id_ispezione,id_impresa_sede ,progressivo) 
				values (R_ispezione_imprese_old.id,R_ispezione_imprese_old.id_ispezione,R_ispezione_imprese_old.id_impresa_sede,R_ispezione_imprese_old.progressivo);
				
				n:=gds_log.upd_record('gds.ispezione_imprese',idtransazione,R_ispezione_imprese_old,'I');
				raise notice 'gds.upd_ispezione_imprese loop INSERT % % %',currval('gds.ispezione_imprese_id_seq'),R_ispezione_imprese.id_ispezione_impresa,R_ispezione_imprese.id_ispezione;
	       	else
	       		raise notice 'gds.upd_ispezione_imprese loop 2 CASE UPDATE';
	    		if R_ispezione_imprese_old.progressivo != n then
		       		update gds.ispezione_imprese 
		    		set 
		    			progressivo=n 
		    		where 
		    			id_ispezione = R_ispezione_imprese.id_ispezione and id_impresa_sede=R_ispezione_imprese.id_impresa_sede;
	       			
		    		n:=gds_log.upd_record('gds.ispezione_imprese',idtransazione,R_ispezione_imprese_old,'U');
		    		raise notice 'gds.upd_ispezione_imprese loop UPDATE % %',R_ispezione_imprese.id_ispezione_impresa,R_ispezione_imprese.id_ispezione;
	       		end if;
	       	end if;
       
       		select array_append(list,R_ispezione_imprese.id_impresa_sede) into list;
       	
       		raise notice 'gds.upd_ispezione_imprese loop 2 end';
       	end loop;
       
        raise notice 'gds.upd_ispezione_imprese prima di delete List %',list;
              	
        for R_II in select * from gds.ispezione_imprese where id_ispezione = R_ispezione_imprese.id_ispezione and id_impresa_sede <> ALL (list) loop
         	delete from gds.ispezione_imprese where id=R_II.id;
         	n:=gds_log.upd_record('gds.ispezione_imprese',idtransazione,R_II,'D');
        	raise notice 'gds.upd_ispezione_imprese DELETE id %',R_II.id ;
        end loop;
        
       
        ret.esito:=true;	
		ret:=gds_ui.build_ret(ret,proc_name,0);
		return ret;
	end;
end;
$$;


ALTER FUNCTION gds.upd_ispezione_imprese(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_nucleo_ispettori(character varying, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.upd_nucleo_ispettori(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
		ret gds_types.result_type; 
		id_op bigint;   
		proc_name varchar;
		R_nucleo_ispettori gds.vw_nucleo_ispettori%ROWTYPE;
		R_nucleo_ispettori_old gds.nucleo_ispettori%ROWTYPE;
		--R_ispezione gds.imprese%ROWTYPE;
		list bigint[];
		n bigint;
   	    R_NI record;
	
	begin 
		proc_name:='gds.upd_nucleo_ispettori';
	    --id_op:=gds_log.start_op(proc_name,idtransazione ,v);
		raise notice 'gds.upd_nucleo_ispettori solo % ' ,v;
		select array_append(list,-1) into list;
	
		for R_nucleo_ispettori in select * from json_populate_recordset(null::gds.vw_nucleo_ispettori, v::json) loop
			raise notice 'gds.upd_nucleo_ispettori loop 1 start' ;
			/* verifica che tutti i nuclei ispettivi appartengano allo stessa ispezione */  
			IF n is not null and R_nucleo_ispettori.id_ispezione is not null and n != R_nucleo_ispettori.id_ispezione then 		
       	    	ret.esito:=false;	
				ret:=gds_ui.build_ret(ret,proc_name,-1);
				--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret));
		    	return ret;         
       		end if;
       		n:=R_nucleo_ispettori.id_ispezione;
       		raise notice 'gds.upd_nucleo_ispettori loop 1 end' ;
       	end loop;
       
       	n:=0;
       
       	for R_nucleo_ispettori in select * from json_populate_recordset(null::gds.vw_nucleo_ispettori, v::json) loop
	       	raise notice 'gds.upd_nucleo_ispettori loop 2 start';
    	       
	        if (R_nucleo_ispettori.codice_fiscale  is null or R_nucleo_ispettori.codice_fiscale ='') then
				continue;
			end if;
				       
			n:=n+1;
			
			raise notice 'gds.upd_nucleo_ispettori Prima di select su old'; 
			select * into R_nucleo_ispettori_old from gds.nucleo_ispettori where id_ispezione = R_nucleo_ispettori.id_ispezione and id_access=R_nucleo_ispettori.id_soggetto_fisico;
       		raise notice 'gds.upd_nucleo_ispettori R_nucleo_ispettori_old.id: %',R_nucleo_ispettori_old.id; 
	       	
       		if R_nucleo_ispettori_old.id is null then
	       		raise notice 'gds.upd_nucleo_ispettori loop 2 CASE INSERT';
		       	R_nucleo_ispettori_old.id:=nextval('gds.nucleo_ispettori_id_seq'); --verificare sulla tabella nucleo_ispettori se la sequence è giusta
				R_nucleo_ispettori_old.id_ispezione:=R_nucleo_ispettori.id_ispezione;
				R_nucleo_ispettori_old.id_access:=R_nucleo_ispettori.id_soggetto_fisico;
				R_nucleo_ispettori_old.progressivo:=R_nucleo_ispettori.progressivo;
				R_nucleo_ispettori_old.data_inserimento=R_nucleo_ispettori.data_inserimento;
				
				insert into gds.nucleo_ispettori (id,id_ispezione,id_access,progressivo,data_inserimento) 
				values (R_nucleo_ispettori_old.id,R_nucleo_ispettori_old.id_ispezione,R_nucleo_ispettori_old.id_access,R_nucleo_ispettori_old.progressivo,R_nucleo_ispettori_old.data_inserimento);
				
				n:=gds_log.upd_record('gds.nucleo_ispettori',idtransazione,R_nucleo_ispettori_old,'I');
				raise notice 'gds.upd_nucleo_ispettori loop INSERT % % %',currval('gds.nucleo_ispettori_id_seq'),R_nucleo_ispettori.id_nucleo_ispettore,R_nucleo_ispettori.id_ispezione;
	       	else
	       		raise notice 'gds.upd_nucleo_ispettori loop 2 CASE UPDATE';
	    		if R_nucleo_ispettori_old.progressivo != n then
		       		update gds.nucleo_ispettori 
		    		set 
		    			progressivo=n 
		    		where 
		    			id_ispezione = R_nucleo_ispettori.id_ispezione and id_access=R_nucleo_ispettori.id_soggetto_fisico;
	       			
		    		n:=gds_log.upd_record('gds.nucleo_ispettori',idtransazione,R_nucleo_ispettori_old,'U');
		    		raise notice 'gds.upd_nucleo_ispettori loop UPDATE % %',R_nucleo_ispettori.id_nucleo_ispettore,R_nucleo_ispettori.id_ispezione;
	       		end if;
	       	end if;
	       
	       	raise notice 'gds.upd_nucleo_ispettori loop end' ;
       		select array_append(list,R_nucleo_ispettori.id_soggetto_fisico) into list;
       	
       		raise notice 'gds.upd_nucleo_ispettori loop 2 end';
       	end loop;
       
        raise notice 'gds.upd_nucleo_ispettori prima di delete List %',list;
       
       	
        for R_NI in select * from gds.nucleo_ispettori where id_ispezione = R_nucleo_ispettori.id_ispezione and id_access <> ALL (list) loop
         	delete from gds.nucleo_ispettori where id=R_NI.id;
         	n:=gds_log.upd_record('gds.nucleo_ispettori',idtransazione,R_NI,'D');
        	raise notice 'gds.upd_nucleo_ispettori DELETE id %',R_NI.id ;
        end loop;
        
       
        ret.esito:=true;	
		ret:=gds_ui.build_ret(ret,proc_name,0);
		return ret;
	end;
end
$$;


ALTER FUNCTION gds.upd_nucleo_ispettori(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_verbale_valore(bigint, bigint, character varying, character varying, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.upd_verbale_valore(idverbale bigint, idmodulocampo bigint, val_old character varying, val_new character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	proc_name varchar;
	id_op bigint;
	n integer;
	r gds_types.result_type;
	begin
		proc_name:='gds.upd_verbale_valore';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		IF val_old IS NOT NULL AND val_new IS NULL then
			delete from gds.verbale_valori where id_verbale=idverbale and id_modulo_campo=idmodulocampo;
		ELSIF val_old IS NULL AND val_new IS NOT NULL then
			insert into gds.verbale_valori values (nextval('gds.verbale_valori_id_seq'),idverbale,idmodulocampo,val_new);
		ElSif val_old != val_new then
			update gds.verbale_valori set val=val_new where  id_verbale=idverbale and id_modulo_campo=idmodulocampo;
		end if;
		r:=gds_ui.build_ret_ok(0);
		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(r));
		return r;
	end;

END
$$;


ALTER FUNCTION gds.upd_verbale_valore(idverbale bigint, idmodulocampo bigint, val_old character varying, val_new character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_verbale_valori(character varying, bigint); Type: FUNCTION; Schema: gds; Owner: postgres
--

CREATE FUNCTION gds.upd_verbale_valori(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
		ret gds_types.result_type;
		id_op bigint;
		proc_name varchar;
        R record;
        idverbale bigint;
        idmodulo bigint;
        n bigint;
        list bigint [];
        R_C_D record;
	begin
		proc_name:='gds.upd_verbale_valori';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select replace(json_extract_path(v::json,'id_verbale')::varchar,'"','')::bigint into idverbale;
		select replace(json_extract_path(v::json,'id_modulo')::varchar,'"','')::bigint into idmodulo;
		if idverbale is null then
			if idmodulo is null then
				ret.esito:=false;	
				ret:=gds_ui.build_ret(ret,proc_name,-2);
				--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret));
				return ret;
			end if;
			idverbale:=nextval('gds.verbali_id_seq');
			insert into gds.verbali values (idverbale,idmodulo,CLOCK_TIMESTAMP());
			--insert into gds.verbale_valori select nextval('gds.verbale_valori_id_seq'),idverbale,id,null from gds_types.modulo_campi where id_modulo=idmodulo;
		end if;
		select array_append(list,-1) into list;
		for R in select j.key,j.value::text,c.id_modulo_campo,c.nome_campo ,v.val from json_each(v::json) j
 					join gds_types.vw_modulo_campi c on c.nome_campo=j.key and c.id_modulo=idmodulo
 					left join gds.vw_verbale_valori v on v.id_modulo_campo=c.id_modulo_campo and v.id_verbale=idverbale
			order by id_modulo_campo loop
			raise notice 'gds.upd_verbale_valori % % % % % % %', idverbale,R.id_modulo_campo ,R.val,R.value,R.nome_campo,R.key,R.value;
			
			if R.value='""' then 
				R.value:=null; 
			end if;
		    if left(R.value,1)='"' then
		    	R.value:=substring(R.value,2);
		    end if;
		    if right(R.value,1)='"' then
		    	R.value:=left(R.value,length(R.value)-1);
		    end if;
			raise notice 'gds.upd_verbale_valori % % % %', idverbale,R.id_modulo_campo ,R.val::varchar , R.value;
			if (R.val::varchar != R.value or (R.val::varchar is null and R.value is not null) or (R.val::varchar is not null and R.value is null)) then
				ret:=gds.upd_verbale_valore(idverbale,R.id_modulo_campo ,R.val::varchar , R.value,idtransazione);
			end if;
		    select array_append(list,R.id_modulo_campo) into list;
		end loop;
	    for R_C_D in select * from gds.verbale_valori where id_verbale = idverbale and id_modulo_campo <> ALL (list) loop
         	delete from gds.verbale_valori where id=R_C_D.id;
         	n:=gds_log.upd_record('gds.verbale_valori',idtransazione,R_C_D,'D');
        end loop;
		ret:=gds_ui.build_ret_ok(0);
		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret));
		return ret;
	end;
END
$$;


ALTER FUNCTION gds.upd_verbale_valori(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: end_op(character varying, bigint, character varying); Type: FUNCTION; Schema: gds_log; Owner: postgres
--

CREATE FUNCTION gds_log.end_op(pname character varying, idtransazione bigint, v character varying) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
begin
	declare 
	begin
		insert into gds_log.call_logs   values (nextval('gds_log.call_logs_id_seq'),idtransazione,pname,'END',CLOCK_TIMESTAMP(),v);
		if length(v)>0 then
			update gds_log.operazioni set ret=v,ts_end=CLOCK_TIMESTAMP()
			where id_transazione=idtransazione and ret is null and procedura=pname;
		else
			update gds_log.operazioni set ret=v,ts_end=CLOCK_TIMESTAMP()
			where id_transazione=idtransazione and ret is null and procedura=pname;
		end if;
		return(currval('gds_log.operazioni_id_seq'));
	end;
END
$$;


ALTER FUNCTION gds_log.end_op(pname character varying, idtransazione bigint, v character varying) OWNER TO postgres;

--
-- Name: get_id_transazione(bigint); Type: FUNCTION; Schema: gds_log; Owner: postgres
--

CREATE FUNCTION gds_log.get_id_transazione(iduser bigint) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
begin
	declare R_T gds_log.transazioni%ROWTYPE;
	begin
		R_T.id=nextval('gds_log.transazioni_id_seq');
		R_T.id_user=iduser;
		R_T.ts:=current_timestamp;
		insert into gds_log.transazioni values (R_T.*);
		return R_T.id;
	end;
END
$$;


ALTER FUNCTION gds_log.get_id_transazione(iduser bigint) OWNER TO postgres;

--
-- Name: get_id_transazione(bigint, character varying); Type: FUNCTION; Schema: gds_log; Owner: postgres
--

CREATE FUNCTION gds_log.get_id_transazione(iduser bigint, titolo character varying) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
begin
	declare R_T gds_log.transazioni%ROWTYPE;
	begin
		R_T.id=nextval('gds_log.transazioni_id_seq');
		R_T.id_user=iduser;
		R_T.ts:=current_timestamp;
		R_T.descr:=titolo;
		insert into gds_log.transazioni values (R_T.*);
		return R_T.id;
	end;
END
$$;


ALTER FUNCTION gds_log.get_id_transazione(iduser bigint, titolo character varying) OWNER TO postgres;

--
-- Name: op(character varying, bigint, character varying, json, timestamp without time zone, bigint, character varying); Type: FUNCTION; Schema: gds_log; Owner: postgres
--

CREATE FUNCTION gds_log.op(pname character varying, idtransazione bigint, param character varying, v json, tsstart timestamp without time zone, idtrattato bigint DEFAULT '-1'::integer, tipooperazione character varying DEFAULT ''::character varying) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
begin
	declare 
	begin
		--insert into gds_log.call_logs   values (nextval('gds_log.call_logs_id_seq'),idtransazione,pname,'START',CLOCK_TIMESTAMP(),param);
		insert into gds_log.operazioni  
			(id,id_transazione,procedura,fase,ts_start,ts_end,ts_transazione,val,id_trattato,ret) 
		values 
			(nextval('gds_log.operazioni_id_seq'),idtransazione,pname,tipooperazione,tsstart,CLOCK_TIMESTAMP(),current_timestamp,param,idtrattato,
    		v::varchar);

    	return(currval('gds_log.operazioni_id_seq'));
	end;
END
$$;


ALTER FUNCTION gds_log.op(pname character varying, idtransazione bigint, param character varying, v json, tsstart timestamp without time zone, idtrattato bigint, tipooperazione character varying) OWNER TO postgres;

--
-- Name: start_op(character varying, bigint, character varying, bigint); Type: FUNCTION; Schema: gds_log; Owner: postgres
--

CREATE FUNCTION gds_log.start_op(pname character varying, idtransazione bigint, param character varying, idtrattato bigint DEFAULT '-1'::integer) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
begin
	declare 
	begin
		insert into gds_log.call_logs   values (nextval('gds_log.call_logs_id_seq'),idtransazione,pname,'START',CLOCK_TIMESTAMP(),param);
		insert into gds_log.operazioni  (id,id_transazione,procedura,fase,ts_start,ts_transazione,val,id_trattato) values 
	(nextval('gds_log.operazioni_id_seq'),idtransazione,pname,'START',CLOCK_TIMESTAMP(),current_timestamp,param,idtrattato);
		return(currval('gds_log.operazioni_id_seq'));
	end;
END
$$;


ALTER FUNCTION gds_log.start_op(pname character varying, idtransazione bigint, param character varying, idtrattato bigint) OWNER TO postgres;

--
-- Name: upd_record(character varying, bigint, record, character varying); Type: FUNCTION; Schema: gds_log; Owner: postgres
--

CREATE FUNCTION gds_log.upd_record(tbl character varying, idtransazione bigint, r record, act character varying) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
begin
	declare 
	r_new record;
	

	begin
		act = upper(act);
		raise notice 'TBL=% ACT=%',tbl,act;
		if act = 'D' or act = 'U' then
			if    tbl = 'gds_notifiche.notifiche'              then
				update gds_notifiche."_h_notifiche"              set validita=tsrange(lower(validita),current_timestamp::timestamp,'[)')
					where id_notifica=R.id and upper_inf(validita);
			elsif tbl = 'gds_notifiche.cantieri'               then
				update gds_notifiche."_h_cantieri"               set validita=tsrange(lower(validita),current_timestamp::timestamp,'[)')
					where id_cantiere =R.id and upper_inf(validita);
			elsif tbl = 'gds_notifiche.cantiere_imprese'       then
				update gds_notifiche."_h_cantiere_imprese"       set validita=tsrange(lower(validita),current_timestamp::timestamp,'[)')
					where id_cantiere_impresa =R.id and upper_inf(validita);
			elsif tbl = 'gds_notifiche.cantiere_persona_ruoli' then
				update gds_notifiche."_h_cantiere_persona_ruoli" set validita=tsrange(lower(validita),current_timestamp::timestamp,'[)')
					where id_cantiere_persona_ruolo =R.id and upper_inf(validita);
			elsif tbl = 'gds_notifiche.imprese'                then
				update gds_notifiche."_h_imprese"                set validita=tsrange(lower(validita),current_timestamp::timestamp,'[)')
					where id_impresa =R.id and upper_inf(validita);
			elsif tbl = 'public.opu_soggetto_fisico' then
				update gds_notifiche."_h_soggetti_fisici"        set validita=tsrange(lower(validita),current_timestamp::timestamp,'[)')
					where id_soggetto_fisico =R.id and upper_inf(validita);
			elsif tbl = 'gds.ispezioni' then 
				update gds."_h_ispezioni"        				 set validita=tsrange(lower(validita),current_timestamp::timestamp,'[)')
					where id_ispezione =R.id and upper_inf(validita);
			elsif tbl = 'gds.ispezione_imprese' then 
				update gds."_h_ispezione_imprese"        			 set validita=tsrange(lower(validita),current_timestamp::timestamp,'[)')
					where id_ispezione_impresa =R.id and upper_inf(validita);
			elsif tbl = 'gds.ispezione_fasi' then 
				update gds."_h_ispezione_fasi"        			 set validita=tsrange(lower(validita),current_timestamp::timestamp,'[)')
					where id_ispezione_fase =R.id and upper_inf(validita);
			elsif tbl = 'gds.nucleo_ispettori' then 
				update gds."_h_nucleo_ispettori"        			 set validita=tsrange(lower(validita),current_timestamp::timestamp,'[)')
					where id_nucleo_ispettore =R.id and upper_inf(validita);
			else
				return -1;
			end if;
		end if;
		if act = 'U' or act = 'I' then
			if    tbl = 'gds_notifiche.notifiche'              then
			 insert into gds_notifiche._h_notifiche 
			 select nextval('gds_notifiche._h_notifiche_id_seq'), i.*, tsrange(current_timestamp::timestamp,null,'[)'),idtransazione,clock_timestamp(),current_timestamp           
				from gds_notifiche.notifiche i  where id=r.id;
			elsif tbl = 'gds_notifiche.cantieri'               then
				insert into gds_notifiche._h_cantieri 
			 	select nextval('gds_notifiche._h_cantieri_id_seq'), i.*, tsrange(current_timestamp::timestamp,null,'[)'),idtransazione,clock_timestamp(),current_timestamp           
					from gds_notifiche.cantieri i where id=r.id;	
			elsif tbl = 'gds_notifiche.cantiere_imprese'       then
				insert into gds_notifiche._h_cantiere_imprese 
			 	select nextval('gds_notifiche._h_cantiere_imprese_id_seq'), i.*, tsrange(current_timestamp::timestamp,null,'[)'),idtransazione,clock_timestamp(),current_timestamp           
					from gds_notifiche.cantiere_imprese i where id=r.id;	
			elsif tbl = 'gds_notifiche.cantiere_persona_ruoli' then
				insert into gds_notifiche._h_cantiere_persona_ruoli 
			 	select nextval('gds_notifiche._h_cantiere_persona_ruoli_id_seq'), i.*, tsrange(current_timestamp::timestamp,null,'[)'),idtransazione,clock_timestamp(),current_timestamp           
					from gds_notifiche.cantiere_persona_ruoli i where id=r.id;	
			elsif tbl = 'gds_notifiche.imprese'                then
				insert into gds_notifiche._h_imprese 
			 	select nextval('gds_notifiche._h_imprese_id_seq'), i.*, tsrange(current_timestamp::timestamp,null,'[)'),idtransazione,clock_timestamp(),current_timestamp           
					from gds_notifiche.imprese i where id=r.id;	
			elsif tbl = 'public.opu_soggetto_fisico'        then
				insert into gds_notifiche._h_soggetti_fisici 
			 	select nextval('gds_notifiche._h_soggetti_fisici_id_seq'), i.*, tsrange(current_timestamp::timestamp,null,'[)'),idtransazione,clock_timestamp(),current_timestamp           
					from public.opu_soggetto_fisico i where id=r.id;	
			elsif tbl = 'gds.ispezioni' then 
				insert into gds._h_ispezioni 
			 	select nextval('gds._h_ispezioni_id_seq'), i.*, tsrange(current_timestamp::timestamp,null,'[)'),idtransazione,clock_timestamp(),current_timestamp           
					from gds.ispezioni i where id=r.id;		
			elsif tbl = 'gds.ispezione_imprese' then
				insert into gds._h_ispezione_imprese 
			 	select nextval('gds._h_ispezione_imprese_id_seq'), i.*, tsrange(current_timestamp::timestamp,null,'[)'),idtransazione,clock_timestamp(),current_timestamp           
					from gds.ispezione_imprese i where id=r.id;		
			elsif tbl = 'gds.ispezione_fasi' then
				insert into gds._h_ispezione_fasi 
			 	select nextval('gds._h_ispezione_fasi_id_seq'), i.*, tsrange(current_timestamp::timestamp,null,'[)'),idtransazione,clock_timestamp(),current_timestamp           
					from gds.ispezione_fasi i where id=r.id;		
			elsif tbl = 'gds.nucleo_ispettori' then 
				insert into gds._h_nucleo_ispettori 
			 	select nextval('gds._h_nucleo_ispettori_id_seq'), i.*, tsrange(current_timestamp::timestamp,null,'[)'),idtransazione,clock_timestamp(),current_timestamp           
					from gds.nucleo_ispettori i where id=r.id;	
			else
				return -1;
			end if;
		end if;
		return 0;
	end;
END
$$;


ALTER FUNCTION gds_log.upd_record(tbl character varying, idtransazione bigint, r record, act character varying) OWNER TO postgres;

--
-- Name: upd_costruttori(character varying, bigint); Type: FUNCTION; Schema: gds_macchine; Owner: postgres
--

CREATE FUNCTION gds_macchine.upd_costruttori(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare 
	
            ret gds_types.result_type; -- START
		    id_op bigint;   
		    proc_name varchar; -- END
	        R_costruttore     gds_macchine.costruttori%ROWTYPE;
	       	R_costruttore_old gds_macchine.costruttori%ROWTYPE;
	       	R_costruttore_new gds_macchine.costruttori%ROWTYPE;
            n bigint;
    begin
		
		proc_name:='gds_macchine.upd_costruttori';
	    id_op:=gds_log.start_op(proc_name,idtransazione ,v);
        R_costruttore.descr:=  trim(json_extract_path(v::json,'descr_costruttore'));
        if R_costruttore.descr is null or R_costruttore.descr = '' then
        	ret.valore:=null;
        	ret.esito=false;
        	ret.msg:='';
        	return ret;
        end if;
       	select * into R_costruttore_old where descr=R_costruttore.descr;
        if R_costruttore_old.id is  null then
            insert into gds_types.tipi_macchina (                                       id,  descr)
       									 values (nextval('gds_types.costruttori_id_seq'),R_costruttore.descr);
       		select * into R_costruttore_new from gds_macchine.macchine
       	    where id = currval('gds_types.costruttori_id_seq');
       		n:=gds_log.upd_record(proc_name,idtransazione,R_costruttore_new,'I');   
        end if; 
	    ret.esito:=true;	
		ret:=gds_ui.build_ret(ret,proc_name,R_costruttore_new.id);
		id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
		return ret;
	end;
END
$$;


ALTER FUNCTION gds_macchine.upd_costruttori(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_macchine(character varying, bigint); Type: FUNCTION; Schema: gds_macchine; Owner: postgres
--

CREATE FUNCTION gds_macchine.upd_macchine(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare 
	
            ret gds_types.result_type; -- START
		    id_op bigint;   
		    proc_name varchar; -- END
	        R_macchina     gds_macchine.vw_macchine%ROWTYPE;
            R_macchina_old gds_macchine.macchine%ROWTYPE;
            R_macchina_new gds_macchine.macchine%ROWTYPE;
            n bigint;
           
	begin
		
		proc_name:='gds_macchine.upd_macchine';
	    id_op:=gds_log.start_op(proc_name,idtransazione ,v);
        R_macchina:=  json_populate_record(null::gds_notifiche.vw_macchine,v::json);
        R_macchina.id:=R_cantieri.id_macchina;
        R_macchina.descr_tipo_macchina:= trim(R_macchina.descr_tipo_macchina);
        R_macchina.descr_costruttore:= trim(R_macchina.descr_costruttore);
       
        --select * into R_macchina_old from gds_macchine.macchine m where id =R_macchina.id;
        -- aggiungere controlli prt tipo_macchina e costruttore (nel caso fare insert)

        ret := gds_macchine.upd_tipi_macchina(v,id_transazione);
        R_macchina.id_tipo_macchina:=ret.valore;
        ret := gds_macchine.upd_costruttori(v,id_transazione);
        R_macchina.id_costruttore:=ret.valore;
        /*select * into R_tipo_macchina where descr=R_macchina.descr_tipo_macchina;
        if R_tipo_macchina is null then
        	insert into gds_types.tipi_macchina values(nextval('gds_types.tipi_macchina_id_seq',R_macchina.descr_tipo_macchina);
            n:=gds_log.upd_record(proc_name,idtransazione,R_macchine_new,'I'); 
        end if;
		*/
       if R_macchina.id is null then
        	insert into gds_macchine.macchine   (                                     id,           id_tipo_macchina,           id_costruttore,           modello,    data_modifica, data_inserimento)
       									 values (nextval('gds_macchine.macchine_id_seq'),R_macchina.id_tipo_macchina,R_macchina.id_costruttore,R_macchina.modello,CLOCK_TIMESTAMP(),CLOCK_TIMESTAMP());
       		select * into R_macchina_new from gds_macchine.macchine  where id = R_macchina.id;
       		n:=gds_log.upd_record(proc_name,idtransazione,R_macchine_new,'I');    	
        end if;
        select * into R_macchina_old from gds_macchine.vw_macchine where id = R_macchina.id;
   
    	IF R_macchina.id_tipo_macchina != R_macchina_old.id_tipo_macchina or (R_macchina.id_tipo_macchina is not null and R_macchina_old.id_tipo_macchina is  null)
    	    or R_macchina.id_costruttore != R_macchina_old.id_costruttore or (R_macchina.id_costruttore is not null and R_macchina_old.id_costruttore is  null)
    	    or R_macchina.modello != R_macchina_old.modello or (R_macchina.modello is not null and R_macchina_old.modello is  null)
       		then
       		raise notice 'sono nell update %',proc_name;

        	update gds_macchine.macchine  set (           id_tipo_macchina,           id_costruttore,           modello,data_modifica) =
       									      (R_macchina.id_tipo_macchina,R_macchina.id_costruttore,R_macchina.modello,CLOCK_TIMESTAMP())
       		where id = R_macchine.id;
       		select * into R_macchina_new from gds_macchine.macchine  where id = R_macchina.id;
       		n:=gds_log.upd_record(proc_name,idtransazione,R_macchine_new,'U');    								
    	end if;
  
	    ret.esito:=true;	
		ret:=gds_ui.build_ret(ret,proc_name,0);
		id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
		return ret;
	end;
END
$$;


ALTER FUNCTION gds_macchine.upd_macchine(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: del_cantiere(bigint); Type: FUNCTION; Schema: gds_mgmt; Owner: postgres
--

CREATE FUNCTION gds_mgmt.del_cantiere(idcantiere bigint) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
begin
	declare idnotifica bigint;
begin
	delete from gds_notifiche.cantiere_imprese where id_cantiere=idcantiere;
	delete from gds_notifiche.cantiere_persona_ruoli where id_cantiere=idcantiere;
	select id_notifica  into idnotifica from gds_notifiche.cantieri where id=idcantiere;
	if idnotifica is not null then
		delete from gds_notifiche.notifiche where id=idnotifica;
	end if;
	delete from gds_notifiche.cantieri where id=idcantiere;
	return 0;
end;
END
$$;


ALTER FUNCTION gds_mgmt.del_cantiere(idcantiere bigint) OWNER TO postgres;

--
-- Name: check_cf(character varying); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.check_cf(cf character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $_$
begin
	declare 
        Dispari hstore;
		Pari    hstore;
		Resto   hstore;
        n int;
        
    begin
	    Pari   :='0=>0, 1=>1, 2=>2, 3=>3, 4=>4, 5=>5, 6=>6, 7=>7, 8=>8, 9=>9, A=>0, B=>1, C=>2, D=>3, E=>4, F=>5, G=>6, H=>7, I=>8, J=>9, K=>10, L=>11, M=>12, N=>13, O=>14, P=>15, Q=>16, R=>17, S=>18, T=>19, U=>20, V=>21, W=>22, X=>23, Y=>24, Z=>25';
		Dispari:='0=>1, 1=>0, 2=>5, 3=>7, 4=>9, 5=>13, 6=>15, 7=>17, 8=>19, 9=>21, A=>1, B=>0, C=>5, D=>7, E=>9, F=>13, G=>15, H=>17, I=>19, J=>21, K=>2, L=>4, M=>18, N=>20, O=>11, P=>3, Q=>6, R=>8, S=>12, T=>14, U=>16, V=>10, W=>22, X=>25, Y=>24, Z=>23';
		Resto  :='0=>A, 1=>B, 2=>C, 3=>D, 4=>E, 5=>F, 6=>G, 7=>H, 8=>I, 9=>J, 10=>K, 11=>L, 12=>M, 13=>N, 14=>O, 15=>P, 16=>Q, 17=>R, 18=>S, 19=>T, 20=>U, 21=>V, 22=>W, 23=>X, 24=>Y, 25=>Z';

		cf:=upper(replace(cf,' ',''));
	    --l:= length(cf);
	    --'^(?:[A-Z][AEIOU][AEIOUX]|[AEIOU]X{2}|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[15MR][\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T][26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$'
	    --if cf !~ '^[A-Z]{6}[0-9L-NP-V]{2}[A-EHLMPRST][0-7L-NP-T][0-9L-NP-V][A-Z][0-9L-NP-V]{3}[A-Z]$' then
	    if cf !~ '^(?:[A-Z][AEIOU][AEIOUX]|[AEIOU]X{2}|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[15MR][\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T][26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$' then
	    	return false;
	    end if;
	   	n:=0;
	    for I in 1 .. 15 by 2 loop
	    	--raise notice 'C=% %',substring( cf,I,1),(Dispari-> substring( cf,I,1))::integer;
	    	n:= n + (Dispari-> substring( cf,I,1))::integer;
	    end loop;
	   	for I in 2 .. 15 by 2 loop
	    	--raise notice 'C=% %',substring( cf,I,1),(Pari-> substring( cf,I,1))::integer;
	    	n:= n + (Pari-> substring( cf,I,1))::integer;
	    end loop;
	   	n:= n % 26;
	    --l:=l+1;
	    --raise notice 'N=% Resto->n=% sub=%',n,Resto->(n::varchar),substring( cf,16,1);
	    return Resto->(n::varchar) = substring( cf,16,1);
	end;
END
$_$;


ALTER FUNCTION gds_notifiche.check_cf(cf character varying) OWNER TO postgres;

--
-- Name: check_piva(character varying); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.check_piva(piva character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $_$
begin
	declare 
        x int;y int;z int;k int;t int;i int;      
    begin
		piva:=upper(replace(piva,' ',''));
		if piva !~ '^[A-Z][A-Z]' then
			return false;
		end if;
	    if piva ~ '^IT' THEN
	    	IF piva !~ '^IT[0-9]{11}$' then
	    		return false;
	    	END IF;
	   
	   		x:=0;y:=0;z:=0;
	    	for I in 3 .. 12 by 2 loop
	    		x:= x + substring( piva,I,1)::integer;
	    	end loop;
	   		for I in 4 .. 12 by 2 loop
	   			k:=substring( piva,I,1)::integer;
	    		y:= y + k *2;
	    		IF (k>= 5) THEN z:=z+1; END IF;
	    	end loop;
	   		t:= (x+y+z) % 10;
	    	return substring( piva,13,1)::integer = (10-t) % 10;
	    end if;
	    RETURN TRUE;
	end;
END
$_$;


ALTER FUNCTION gds_notifiche.check_piva(piva character varying) OWNER TO postgres;

--
-- Name: crea_codice(character varying, character varying); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.crea_codice(pre character varying, tipo character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare cod varchar;
			crc integer;
			c_dispari integer[];
			i integer;
			n integer;
			a integer;
			R_C gds_notifiche.codici%ROWTYPE;
	begin
		c_dispari := '{1,0,5,7,9,13,15,17,19,21}'::integer[];
		a:=left(pre,4)::integer;
		update gds_notifiche.codici set val=val+1 where codice=tipo and anno=a
		returning * into R_C;
		if R_C.id is null then
			R_C.id := nextval('gds_notifiche.codici_id_seq');
			R_C.codice:=tipo;
			R_C.anno:=a::integer;
			R_C.val:=1;
			insert into gds_notifiche.codici values (R_C.*);
		end if;
		cod = replace(pre,'-','') ||lpad(R_C.val::varchar,5,'0');

		crc:=0;
		raise notice 'CRC = %', crc;
		n:=length(cod);
		for i in 1..n loop
		raise notice 'substring(cod,i,1)::integer %',substring(cod,i,1)::integer;
			if i %2 = 0 then
				crc:=crc+substring(cod,i,1)::integer;
			else
				crc := crc + c_dispari[substring(cod,i,1)::integer+1];
			end if;
			raise notice 'I=% CRC = %', i,crc;
		end loop;
		crc := crc  %10;
		cod:=pre ||'-'||lpad(R_C.val::varchar,5,'0')||(crc%10);
		raise notice 'CODICE %',  cod;
		return cod;
	end;
END
$$;


ALTER FUNCTION gds_notifiche.crea_codice(pre character varying, tipo character varying) OWNER TO postgres;

--
-- Name: create_insert_verbale(); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.create_insert_verbale() RETURNS text
    LANGUAGE plpgsql
    AS $$
	BEGIN
          truncate table gds_types.modulo_campi  cascade;
         
         
         --PRIMO VERBALE 
 insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1,'numero_verbale', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1,'ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1,'ora_sopralluogo', 4);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1,'nome_persona_presente', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1,'comune_nascita', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'data_nascita', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'comune_residenza', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'via_residenza', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'identificato_con', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'qualita_di', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'data_ispezione', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'is_ditta_o_cantiere', 9);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'nome_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'comune_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'via_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'comune_sede_legale_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'via_sede_legale_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'comune_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'via_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'nome_ditta_in_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'sede_legale_ditta_in_canitere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'via_sede_legale_ditta_in_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'nome_rappresentante_legale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'qualita_di_rappresentante_legale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'comune_nascita_rappresentante_legale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'data_nascita_rappresentante_legale', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'comune_residenza_rappresentante_legale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'via_residenza_rappresentante_legale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'nome_datore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'comune_nascita_datore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'data_nascita_datore', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'comune_residenza_datore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'via_residenza_datore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'dato_atto', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'descrizione_luoghi', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'esito_sopralluogo_rilievi_negativi', 9);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'esito_sopralluogo', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'attivita_effettuate', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'rilievi_fotografici', 10);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'acquisizione_documentale', 10);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),1 ,'altro', 5);

--SECONDO VERBALE   

insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'numero_verbale', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'nome_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'data_ispezione', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'nome_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'comune_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'via_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'comune_sede_legale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'via_sede_legale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'comune_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'via_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'sede_legale_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'via_sede_legale_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'nome_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'ruolo_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'comune_nascita_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'data_nascita_rappresentante', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'via_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'nome_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'comune_nascita_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'data_nascita_datore_lavoro', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'residenza_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'via_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'verifica_attrezzature', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'elenco_ditte', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'nome_ispettore_presente', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'comune_ispettore_presente', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'data_ispettore_presente', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'residenza_ispettore_presente', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'via_residenza', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'ruolo_ispettore_presente', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'data_presentazione', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'ora_presentazione', 4);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),2 ,'luogo_presentazione', 3);

--TERZO VERBALE 


insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'numero_verbale', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'nome_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'ora_odierna', 4);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'comune_nascita_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'data_nascita_ispettore', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'residenza_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'via_residenza_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'identificativo', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'ruolo', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'nome_azienda_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'comune_azienda_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'via_azienda_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'comune_sede_legale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'nome_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'ruolo_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'comune_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'data_nascita_rappresentante', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'via_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'nome_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'ruolo_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'comune_nascita_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'data_nascita_datore_lavoro', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'residenza_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'via_residenza_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'atto', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'numero_articolo', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'numero_articolo_sanzione', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'anno_articolo', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'sanzione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'motivo_sanzione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'motivo_violazione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'prescrizione_uno', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'prescrizione_due', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'prescrizione_tre', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'prescrizione_quattro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'giorni_non_oltre', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'numero_verbale_prescrizione', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'data_verbale_prescrizione', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'numero_prescrizione', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'data_prescrizione', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'ammontare', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'nome_contravventore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'comune_nascita_contravventore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'data_nascita_contravventore', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'residenza_contravventore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'via_residenza_contravventore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'firma_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'firma_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),3 ,'firma_verbalizzanti', 3);


--4 VERBALE 
 
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'numero_verbale', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'ora_odierna', 4);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'nome_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'data_nascita_ispettore', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'comune_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'via_residenza_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'identificativo', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'data_ispezione', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'nome_comune_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'via_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'numero_civico_via', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'nome_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'comune_sede_legale_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'via_sede_legale_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'numero_civico_via_sede_legale_ditta', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'nome_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'comune_nascita_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'data_nascita_rappresentante', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'via_residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'anno_residenza', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'atto', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'data_violazione', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'nome_coordinatore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'data_nascita_coordinatore', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'residenza_coordinatore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'via_residenza_coordinatore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'numero_civico_residenza_coordinatore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'articolo_numero_uno', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'anno_articolo_uno', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'numero_articolo_sanzione_uno', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'prima_sanzione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'motivo_prima_sanzione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'articolo_numero_due', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'anno_articolo_due', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'numero_articolo_sanzione_due', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'seconda_sanzione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'motivo_seconda_sanzione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'articolo_numero_tre', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'anno_articolo_tre', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'numero_articolo_sanzione_tre', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'terza_sanzione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'motivo_terza_sanzione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'articolo_numero_quattro', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'anno_articolo_quattro', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'numero_articolo_sanzione_quattro', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'quarta_sanzione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'motivo_quarta_sanzione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'motivo_violazione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'nome_contravventore_a', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'ruolo_contravventore_a', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'comune_nascita_contravventore_a', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'data_nascita_contravventore_a', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'residenza_contravventore_a', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'via_residenza_contravventore_a', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'numero_civico_residenza_contravventore_a', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'prima_prescrizione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'seconda_prescrizione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'terza_prescrizione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'quarta_prescrizione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'giorni_non_oltre', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'nome_contravventore_b', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'comune_nascita_contravventore_b', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'data_nascita_contravventore_b', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'residenza_contravventore_b', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'via_contravventore_b', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'numero_prescrizione', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'anno_prescrizione', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'ammontare', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'nome_contravventore_c', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'comune_nascita_contravventore_c', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'data_nascita_contravventore_c', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'residenza_contravventore_c', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'via_residenza_contravventore_c', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'firma_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'firma_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),4 ,'firma_verbalizzanti', 3);

--VERBALE 5

insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'numero_verbale', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'anno_verbale', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'nome_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'comune_nascita_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'data_nascita_soggetto', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'residenza_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'via_residenza_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'identificativo_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'numero_identificativo_soggetto', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'data_rilascio', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'nome_soggetto_rilascio', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'ruolo_soggetto_rilascio', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'ora_odierna', 4);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'nome_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'comune_nascita_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'data_nascita_ispettore', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'residenza_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'via_residenza_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'identificativo_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'numero_identificativo_ispettore', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'data_rilascio', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'nome_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'comune_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'via_piazza_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'comune_sede_legale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'via_residenza_sede_legale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'comune_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'via_piazza_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'nome_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'nome_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'ruolo_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'comune_nascita_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'data_nascita_rappresentante', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'via_residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'elenco_beni', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'numero_cartelle', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'numero_sigilli', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'numero_articolo', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'motivazione_articolo', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'nome_custode_giudiziario', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'comune_nascita_custode_giudiziario', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'numero_verbale_sequestro', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'anno_verbale_sequestro', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'data_nascita_custode_giudiziario', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'residenza_custode_giudiziario', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'via_residenza_custode_giudiziario', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'indirizzo_notifica', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'nome_avvocato', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'nome_comune_avvocato', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'nome_ricevente', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'nome_comune', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'firma_custode_giudiziario', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),5 ,'firma_verbalizzanti', 3);



--VERBALE 6

insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'numero_verbale', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'nome_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'comune_nascita_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'data_nascita_soggetto', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'residenza_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'via_residenza_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'identificativo_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'numero_identificativo_soggetto', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'data_rilascio', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'nome_soggetto_rilascio', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'ruolo_soggetto_rilascio', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'ora_odierna', 4);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'nome_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'comune_nascita_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'data_nascita_ispettore', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'residenza_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'via_residenza_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'identificativo_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'numero_identificativo_ispettore', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'data_rilascio', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'nome_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'comune_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'via_piazza_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'comune_sede_legale_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'via_residenza_sede_legale_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'comune_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'via_piazza_cantiere', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'nome_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'ruolo_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'comune_nascita_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'data_nascita_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'via_residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'elenco_beni', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'numero_cartelle', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'numero_sigilli', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'motivazione_durata_sequestro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'nome_custode_giudiziario', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'comune_nascita_custode_giudiziario', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'data_nascita_custode_giudiziario', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'residenza_custode_giudiziario', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'via_residenza_custode_giudiziario', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'numero_verbale_sequestro', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'anno_verbale_sequestro', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'indirizzo_notifica', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'nome_ricevente', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'nome_comune_tribunale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'firma_custode_giudiziario', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),6 ,'firma_verbalizzanti', 3);




--VERBALE 7

insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'data_verbale', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'nome_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'nome_testimone', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'comune_nascita_testimone', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'data_nascita_testimone', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'residenza_testimone', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'numero_testimone', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'nome_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'documento_testimone', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'data_stabilita', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'anno_stabilito', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'ora_stabilita', 4);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'nome_dipartimento', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'nome_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'dichiarazione', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'note', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'firma_testimone', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),7 ,'firma_dipartimento', 3);

    --VERBALE 8

insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'numero_verbale', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'data_verbale', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'ragione_sociale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'sede_sociale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'cantiere_bonifica', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'attività', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'data_nascita_rappresentante', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'comune_nascita_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'nome_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'data_nascita_datore_lavoro', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'comune_nascita_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'residenza_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'nome_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'data_nascita_soggetto', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'comune_nascita_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'residenza_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'via_residenza_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'ruolo_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'nome_dipartimento', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'data', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'ora', 4);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'altro', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'firma_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),8 ,'firma_verbalizzanti', 3);   

--VERBALE 9

insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'numero_verbale', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'data_verbale', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'ragione_sociale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'sede_sociale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'sede_unità_locale', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'settore_attività', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'nome_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'data_nascita_rappresentante', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'comune_nascita_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'nome_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'data_nascita_datore_lavoro', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'comune_nascita_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'residenza_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'nome_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'data_nascita_soggetto', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'comune_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'residenza_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'via', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'ruolo_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'numero_telefono', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'anno_verbale', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'nome_dipartimento', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'ora', 4);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'altro', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'firma_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),9 ,'firma_verbalizzanti', 3);


--VERBALE 10

insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'numero_verbale', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'anno_verbale', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'nome', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'ora', 4);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'nome_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'comune_nascita', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'data_nascita', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'residenza_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'via_residenza_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'identificativo', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'ruolo_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'nome_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'comune_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'via_piazza_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'comune_sede_legale_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'via_sede_legale_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'nome_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'ruolo_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'comune_nascita_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'data_nascita_rappresentante', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'via_residenza_rappresentante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'nome_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'comune_nascita_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'data_nascita_datore_lavoro', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'residenza_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'via_residenza_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'data', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'note', 5);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'nome_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'data_nascita_soggetto', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'comune_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'residenza_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'via_residenza_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'ruolo_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'firma_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'firma_ispettore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),10 ,'firma_verbalizzanti', 3);
         

-- VERBALE 11

insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'numero_verbale', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'anno_verbale', 6);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'giorno', 7);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'mese', 8);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'nome_verbalizzante', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'nome_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'comune_nascita_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'data_nascita_datore_lavoro', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'residenza_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'via_residenza_datore_lavoro', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'nome_ditta', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'nome_contravventore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'ammontare', 1);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'firma_contravventore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),11 ,'firma_verbalizzanti', 3);



--VERBALE 12

insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'comune', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'nome_sostituto_procuratore', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'modello_certificazione', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'numero_verbale', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'nome_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'comune_nascita_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'data_nascita_soggetto', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'residenza', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'via_residenza', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'nome_società', 3);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'numero_prescrizione', 2);
insert into gds_types.modulo_campi values (nextval('gds_types.modulo_campi_id_seq'),12 ,'nome_soggetto', 3);

        RETURN 'OK';
	END;
$$;


ALTER FUNCTION gds_notifiche.create_insert_verbale() OWNER TO postgres;

--
-- Name: create_sequence(); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.create_sequence() RETURNS text
    LANGUAGE plpgsql
    AS $$
	begin
		declare n integer;
		begin 
			
		
         
 drop  SEQUENCE gds_notifiche.cantiere_imprese_id_seq;
 create SEQUENCE gds_notifiche.cantiere_imprese_id_seq
	start 1
	CACHE 1
	NO CYCLE;

   n:=setval('gds_notifiche.cantiere_imprese_id_seq', (SELECT MAX(id) + 1 FROM gds_notifiche.cantiere_imprese));



drop SEQUENCE gds_notifiche.cantiere_persona_ruoli_id_seq;  
create SEQUENCE gds_notifiche.cantiere_persona_ruoli_id_seq
	START 1
	CACHE 1
	NO CYCLE;
 n:=setval('gds_notifiche.cantiere_persona_ruoli_id_seq', (SELECT MAX(id) + 1 FROM gds_notifiche.cantiere_persona_ruoli));

drop SEQUENCE gds_notifiche.cantieri_id_seq;
create SEQUENCE gds_notifiche.cantieri_id_seq
	START 1
	CACHE 1
	NO CYCLE;
 n:=setval('gds_notifiche.cantieri_id_seq', (SELECT MAX(id) + 1 FROM gds_notifiche.cantieri));

drop SEQUENCE gds_notifiche.imprese_id_seq; 
create SEQUENCE gds_notifiche.imprese_id_seq
	start 1
	CACHE 1
	NO CYCLE;
 n:=setval('gds_notifiche.imprese_id_seq', (SELECT MAX(id) + 1 FROM gds_notifiche.imprese));

drop SEQUENCE gds_notifiche.notifiche_id_seq;
create  SEQUENCE gds_notifiche.notifiche_id_seq
	START 1
	CACHE 1
	NO CYCLE;
 n:=setval('gds_notifiche.notifiche_id_seq', (SELECT MAX(id) + 1 FROM gds_notifiche.notifiche));


drop SEQUENCE gds_types.nature_opera_id_seq;   
create SEQUENCE gds_types.nature_opera_id_seq
	START 1
	CACHE 1
	NO CYCLE;
 n:=setval('gds_types.nature_opera_id_seq', (SELECT MAX(id) + 1 FROM gds_types.nature_opera));

drop SEQUENCE gds_types.ruoli_id_seq;
create SEQUENCE gds_types.ruoli_id_seq
	START 1
	CACHE 1
	NO CYCLE;
 n:=setval('gds_types.ruoli_id_seq', (SELECT MAX(id) + 1 FROM gds_types.ruoli));


drop SEQUENCE gds_types.stati_id_seq;
create  SEQUENCE gds_types.stati_id_seq
	START 1
	CACHE 1
	NO CYCLE;
 n:=setval('gds_types.stati_id_seq', (SELECT MAX(id) + 1 FROM gds_types.stati));

drop SEQUENCE gds_types.stati_successivi_id_seq;  
create SEQUENCE gds_types.stati_successivi_id_seq
	START 1
	CACHE 1
	NO CYCLE;
 n:=setval('gds_types.stati_successivi_id_seq', (SELECT MAX(id) + 1 FROM gds_types.stati_successivi));


 drop  SEQUENCE gds_notifiche.moduli_id_seq;
 create SEQUENCE gds_notifiche.moduli_id_seq
	start 1
	CACHE 1
	NO CYCLE;
  
   
   
 drop  SEQUENCE  gds_types.tipi_campo_id_seq;
 create SEQUENCE  gds_types.tipi_campo_id_seq
	start 1
	CACHE 1
	NO CYCLE;

   
   
 drop  SEQUENCE gds_notifiche.modulo_campi_id_seq;
 create SEQUENCE gds_notifiche.modulo_campi_id_seq
	start 1
	CACHE 1
	NO CYCLE;

 drop  SEQUENCE gds_ui.ui_messaggi_id_seq;
 create SEQUENCE gds_ui.ui_messaggi_id_seq
	start 1
	CACHE 1
	NO CYCLE;


   RETURN 'OK';
	   end;
	END;
$$;


ALTER FUNCTION gds_notifiche.create_sequence() OWNER TO postgres;

--
-- Name: create_view(); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.create_view() RETURNS text
    LANGUAGE plpgsql
    AS $$
begin

drop view if exists gds_notifiche.vw_soggetto cascade;
drop view  if exists gds_notifiche.vw_opu_soggetti_fisici cascade;
drop view  if exists gds_notifiche.vw_soggetti_fisici cascade;
drop view if exists gds_notifiche.vw_opu_indirizzi cascade;
drop view  if exists gds_notifiche.vw_notifiche cascade;
drop view  if exists gds_notifiche.vw_imprese cascade;
drop view  if exists gds_notifiche.vw_cantieri cascade;
drop view  if exists gds_notifiche.vw_cantiere_persona_ruoli cascade;
drop view  if exists gds_notifiche.vw_cantiere_imprese cascade;	
drop view  if exists gds_notifiche.vw_documenti cascade;

drop view  if exists gds_types.vw_stati cascade; 
drop view  if exists gds_types.vw_ruoli cascade;
drop view  if exists gds_types.vw_moduli cascade;
drop view  if exists gds_types.vw_tipo_campo cascade; 
drop view  if exists gds_types.vw_modulo_campi cascade;
drop view  if exists gds_types.vw_nature_opera cascade; 
drop view if exists gds_types.vw_fasi cascade;
drop view if exists gds_types.vw_motivi cascade;

drop view if exists gds_srv.vw_ruoli cascade;

drop view if exists gds_ui.vw_ruoli_ui cascade;
drop view if exists gds_ui.vw_nature_opera_ui cascade;
drop view if exists gds_ui.vw_stati_ui cascade;

drop view if exists gds_log.vw_call_log cascade;
drop view if exists gds_log.vw_transazioni cascade;
drop view if exists gds_log.vw_operazioni cascade;

drop view if exists gds.vw_verbali  cascade;
drop view if exists gds.vw_verbale_valori cascade;



CREATE OR REPLACE VIEW gesdasic2021.vw_imprese_valide
AS SELECT a.ragione_sociale,
    a.id,

    a.partita_iva::text ||
        CASE
            WHEN b.cnt <= 1 THEN ''::text
            ELSE '-'::text || "left"(a.ragione_sociale, 1)
        END AS partita_iva,    a.codice_fiscale
   FROM ( SELECT replace(upper(replace(replace(i.ragione_sociale::text, ' '::text, ''::text), '.'::text, ''::text)), 'EDILUXDIGILGIOFRANCACLAUDIA'::text, 'EDILUX'::text) AS ragione_sociale,
                CASE
                    WHEN i.partita_iva IS NULL OR i.partita_iva::text = ''::text THEN i.codice_fiscale
                    ELSE i.partita_iva
                END AS partita_iva,
            min(i.id) AS id,min(i.codice_fiscale) as codice_fiscale
           FROM gesdasic2021.impresa_cantiere ic
             JOIN gesdasic2021.imprese i ON i.id = ic.idimpresa
          GROUP BY (replace(upper(replace(replace(i.ragione_sociale::text, ' '::text, ''::text), '.'::text, ''::text)), 'EDILUXDIGILGIOFRANCACLAUDIA'::text, 'EDILUX'::text)), (
                CASE
                    WHEN i.partita_iva IS NULL OR i.partita_iva::text = ''::text THEN i.codice_fiscale
                    ELSE i.partita_iva
                END)) a
     JOIN ( SELECT a_1.partita_iva,
            min(a_1.ragione_sociale) AS min,
            max(a_1.ragione_sociale) AS max,
            min(a_1.id) AS min,
            count(*) AS cnt
           FROM ( SELECT replace(upper(replace(replace(i.ragione_sociale::text, ' '::text, ''::text), '.'::text, ''::text)), 'EDILUXDIGILGIOFRANCACLAUDIA'::text, 'EDILUX'::text) AS ragione_sociale,
                        CASE
                            WHEN i.partita_iva IS NULL OR i.partita_iva::text = ''::text THEN i.codice_fiscale
                            ELSE i.partita_iva
                        END AS partita_iva,
                    min(i.id) AS id
                   FROM gesdasic2021.impresa_cantiere ic
                     JOIN gesdasic2021.imprese i ON i.id = ic.idimpresa
                  GROUP BY (replace(upper(replace(replace(i.ragione_sociale::text, ' '::text, ''::text), '.'::text, ''::text)), 'EDILUXDIGILGIOFRANCACLAUDIA'::text, 'EDILUX'::text)), (
                        CASE
                            WHEN i.partita_iva IS NULL OR i.partita_iva::text = ''::text THEN i.codice_fiscale
                            ELSE i.partita_iva
                        END)) a_1
          GROUP BY a_1.partita_iva) b(partita_iva, min, max, min_1, cnt) ON a.partita_iva::text = b.partita_iva::text;
         

CREATE OR REPLACE VIEW gds_types.vw_moduli
AS SELECT 
    m.id,
    m.id as id_modulo,
    m.descr,
    m.nome_file
   FROM gds_types.moduli m ;
  
  
  CREATE OR REPLACE VIEW gds_types.vw_tipi_campo
AS SELECT  tc.id, 
   tc.id as id_tipo_campo,
   tc.descr,
   tc.codice
   FROM gds_types.tipi_campo tc ;
  
  
 CREATE OR REPLACE VIEW gds_types.vw_modulo_campi
AS SELECT  mc.id,
   mc.id as id_modulo_campo,
   mc.id_modulo,
   mc.nome_campo,
   mc.tipo_campo,
   m.descr as descrizione_modulo,
   m.nome_file, 
   tc.id as id_tipo_campo,
   tc.descr as descrizione_tipo_modulo,
   tc.codice
 FROM gds_types.modulo_campi mc 
 join gds_types.moduli m on m.id = mc.id
 join gds_types.tipi_campo tc on m.id = tc.id ;
  
  
CREATE OR REPLACE VIEW gds_types.vw_ruoli
AS SELECT r.id,    r.id AS id_ruolo,r.descr,r.descr AS ruolo                                  
   FROM gds_types.ruoli r;
  
  CREATE OR REPLACE VIEW gds_types.vw_stati
AS SELECT st.id,
    st.id AS id_stato,
    st.descr,
    st.descr AS stato,
    st.modificabile
   FROM gds_types.stati st;
   
  CREATE OR REPLACE VIEW gds_notifiche.vw_imprese
AS SELECT i.id,
    i.id AS id_impresa,
    i.id_gisa,
    i.ragione_sociale,
    i.partita_iva,
    i.codice_fiscale
   FROM gds_notifiche.imprese i;
  
  CREATE OR REPLACE VIEW gds_notifiche.vw_opu_indirizzi
AS SELECT opu_indirizzo.id,
    opu_indirizzo.id AS id_indirizzo,
    opu_indirizzo.via,
    opu_indirizzo.comune id_comune,
    c.nome comune,
    TRIM(opu_indirizzo.cap) cap,
    l.cod_provincia,
    opu_indirizzo.latitudine AS lat,
    opu_indirizzo.longitudine AS lng
   FROM opu_indirizzo  left join public.comuni1 c on opu_indirizzo.comune =c.id
   left join public.lookup_province l on c.cod_provincia::integer = l.code;
  
  CREATE OR REPLACE VIEW gds_notifiche.vw_comuni
AS SELECT c.id,
    c.nome comune,
    l.cod_provincia
   FROM public.comuni1 c
  join public.lookup_province l on c.cod_provincia::integer = l.code;
  
 
CREATE OR REPLACE VIEW gds_types.vw_nature_opera
AS SELECT nt.id,
    nt.id AS id_natura_opera,
    nt.descr,
    nt.descr AS natura_opera
   FROM gds_types.nature_opera nt;
  
  
  CREATE OR REPLACE VIEW gds_types.vw_fasi
AS SELECT f.id, f.id as id_fase,
    f.primo_sopralluogo,
	f.sopralluogo_prossimo,
	f.attività_prescrizione,
	f.attività_accertamenti,
	f.attività_sospensione,
	f.atti_indagine,
	f.documetazione
   FROM gds_types.fasi f;
  
  
    CREATE OR REPLACE VIEW gds_types.vw_motivi
AS SELECT m.id, m.id as id_motivo,
    m.programma_vigilanza,
	m.richiesta_vigilanza,
	m.iniziativa_vigilanza,
	m.indagine_infortunio,
	m.indagine_malattia,
	m.emissione_parere
   FROM gds_types.motivi m;
 
CREATE OR REPLACE VIEW gds_notifiche.vw_opu_soggetti_fisici
AS SELECT opu_soggetto_fisico.id,
    opu_soggetto_fisico.id AS id_soggetto_fisico,
    opu_soggetto_fisico.nome,
    opu_soggetto_fisico.cognome,
    opu_soggetto_fisico.indirizzo_id,
    opu_soggetto_fisico.codice_fiscale,
    opu_soggetto_fisico.email
   FROM public.opu_soggetto_fisico; 
  
        
CREATE OR REPLACE VIEW gds_notifiche.vw_soggetti_fisici
AS SELECT s.id,
    s.id AS id_soggetto_fisico,
    s.nome,
    s.cognome,
    s.codice_fiscale,
    s.email,
    s.indirizzo_id as id_indirizzo,
    i.via,
    i.comune,
    i.cap,
    i.id_comune,
    i.cod_provincia
   FROM gds_notifiche.vw_opu_soggetti_fisici s
   join gds_notifiche.vw_opu_indirizzi i  on  s.indirizzo_id = i.id;
  
CREATE OR REPLACE VIEW gds_notifiche.vw_notifiche
AS SELECT n.id AS id_notifica,
    n.id,
    n.id_soggetto_fisico,
    n.data_notifica,
    n.id_soggetto_fisico AS id_soggetto_notificante,
    s.nome AS nome_notificante,
    s.cognome AS cognome_notificante,
    s.codice_fiscale AS cf_notificante,
    n.id_stato,
    st.stato,
    n.data_notifica as data_modifica,
    c.denominazione,
    st.modificabile,
    s.via,
    s.comune,
    s.cod_provincia,
    s.id_comune,
    s.cap,
    s.id_indirizzo
    FROM gds_notifiche.notifiche n
     LEFT JOIN gds_notifiche.vw_soggetti_fisici s ON n.id_soggetto_fisico = s.id_soggetto_fisico
     JOIN gds_types.vw_stati st ON st.id_stato = n.id_stato
    left join gds_notifiche.cantieri c on c.id_notifica = n.id;
    
    
    
 CREATE OR REPLACE VIEW gds_notifiche.vw_cantieri
AS SELECT c.id AS id_cantiere,
    c.id,
    c.id_natura_opera,
    c.id_notifica,
    c.denominazione,
    c.data_presunta,
    c.durata_presunta,
    c.numero_imprese,
    c.numero_lavoratori,
    c.ammontare,
    c.altro,
    c.id_indirizzo,
    n.natura_opera,
    nt.id_soggetto_notificante,
    nt.data_notifica,
    nt.nome_notificante,
    nt.cognome_notificante,
    nt.cf_notificante,
    nt.via as via_notificante,
    nt.comune as comune_notificante,
    nt.id_comune as id_comune_notificante,
    nt.cod_provincia as cod_provincia_notificante,
    i.via,
    i.comune,
    i.id_comune,
    i.cap,
    i.cod_provincia,
    i.lat,
    i.lng,
    nt.id_stato,
    nt.stato,
    nt.cap AS cap_notificante,
    nt.id_indirizzo AS id_indirizzo_notificante
   FROM gds_notifiche.cantieri c
     JOIN gds_notifiche.vw_notifiche nt ON c.id_notifica = nt.id_notifica
     left JOIN gds_types.vw_nature_opera n ON n.id_natura_opera = c.id_natura_opera
     left JOIN gds_notifiche.vw_opu_indirizzi i ON i.id_indirizzo = c.id_indirizzo;  
    
  
CREATE VIEW gds_notifiche.vw_cantiere_imprese AS
 SELECT ci.id AS id_cantiere_impresa,
    ci.id_cantiere,
    ci.id_impresa,
    ci.id,
    i.id_gisa,
    i.ragione_sociale,
    i.partita_iva,
    i.codice_fiscale,
    c.id_natura_opera,
    c.id_notifica,
    c.denominazione,
    c.data_presunta,
    c.durata_presunta,
    c.numero_imprese,
    c.numero_lavoratori,
    c.ammontare,
    c.altro,
    c.natura_opera,
    c.id_soggetto_notificante,
    c.data_notifica,
    c.nome_notificante,
    c.cognome_notificante,
    c.cf_notificante,
    c.via,
    c.comune,
    c.cap,
    c.lat,
    c.lng,
    c.cod_provincia
   FROM ((gds_notifiche.cantiere_imprese ci
     RIGHT JOIN gds_notifiche.vw_cantieri c ON ((c.id_cantiere = ci.id_cantiere)))
     LEFT JOIN gds_notifiche.vw_imprese i ON ((i.id_impresa = ci.id_impresa)));
    

  
CREATE VIEW gds_notifiche.vw_cantiere_persona_ruoli AS
 SELECT c.id_cantiere,
    c.id_natura_opera,
    c.id_notifica,
    c.denominazione,
    c.data_presunta,
    c.durata_presunta,
    c.numero_imprese,
    c.numero_lavoratori,
    c.ammontare,
    c.altro,
    c.natura_opera,
    c.id_soggetto_notificante,
    c.data_notifica,
    c.nome_notificante,
    c.cognome_notificante,
    c.cf_notificante,
    s.id_soggetto_fisico,
    s.nome,
    s.cognome,
    s.codice_fiscale,
    s.via,
    s.cap,
    s.comune,
    s.cod_provincia,
    r.id AS id_ruolo,
    r.descr,
    r.descr AS ruolo,
    s.email AS pec,
    s.id_indirizzo,
    cpr.id
   FROM (((( SELECT c_1.id AS id_cantiere,
            r_1.id,
            r_1.descr,
            r_1.richiesta_pec
           FROM (gds_notifiche.cantieri c_1
             JOIN gds_types.ruoli r_1 ON ((1 = 1)))) r
     LEFT JOIN gds_notifiche.cantiere_persona_ruoli cpr ON (((cpr.id_cantiere = r.id_cantiere) AND (r.id = cpr.id_ruolo))))
     JOIN gds_notifiche.vw_cantieri c ON ((c.id_cantiere = r.id_cantiere)))
     LEFT JOIN gds_notifiche.vw_soggetti_fisici s ON ((s.id_soggetto_fisico = cpr.id_soggetto_fisico)));



 
  CREATE OR REPLACE VIEW gds_types.vw_stati_successivi
AS SELECT s.id,  s.id_stato_attuale , s.id_stato_prossimo,
		sa.descr as descr_stato_attuale,
          sp.descr as descr_stato_prossimo
   from gds_types.stati_successivi s
      join  gds_types.stati sa on s.id_stato_attuale = sa.id
      join gds_types.stati sp  on s.id_stato_prossimo = sp.id;
       
      
  
  CREATE OR REPLACE VIEW gds_notifiche.vw_notifica_stati_successivi
AS SELECT n.id_notifica,n.id_soggetto_fisico,n.data_notifica,n.id_soggetto_notificante,n.nome_notificante,n.cognome_notificante,
          n.cf_notificante,n.id_stato,n.stato,
          s.id id_stato_successivo,s.id_stato_prossimo,s.descr_stato_attuale,s.descr_stato_prossimo
   from gds_notifiche.vw_notifiche n 
      join  gds_types.vw_stati_successivi s  on s.id_stato_attuale  = n.id_stato;
       
    
  
  CREATE OR REPLACE VIEW gds_ui.vw_ruoli_ui
AS SELECT ru.id, ru.id as id_ruolo,ru.ordinamento,r.descr
   FROM gds_ui.ruoli_ui ru join gds_types.ruoli r on r.id=ru.id;

  
    CREATE OR REPLACE VIEW gds_ui.vw_nature_opera_ui
AS SELECT nu.id, nu.id as id_natura_opera,nu.ordinamento,n.descr
   FROM gds_ui.natura_opere nu 
   join gds_types.nature_opera n on n.id=nu.id; 
  
  
      CREATE OR REPLACE VIEW gds_ui.vw_stati_ui
AS SELECT su.id as id_stato, su.label_bottone, su.colore
   FROM gds_ui.stati_ui su
   join gds_types.stati s on su.id = s.id;
  
  
     CREATE OR REPLACE VIEW gds_ui.vw_messaggi_ui
AS SELECT mu.id , mu.id as id_messaggio, mu.procedura, mu.valore, mu.msg
   FROM gds_ui.messaggi_ui mu;
  
   CREATE OR REPLACE VIEW gds_ui.vw_verbali_ui
AS SELECT vu.id,
     vu.id as id_verbale_ui,
    vu.descr,
	vu.filename 
   FROM gds_ui.verbali_ui vu;
  
  
    CREATE OR REPLACE VIEW gds_srv.vw_ruoli
AS SELECT vw_ruoli_ui.id,            
    vw_ruoli_ui.ordinamento
   FROM gds_ui.vw_ruoli_ui;

CREATE OR REPLACE VIEW gds_srv.vw_notifiche as select * from gds_notifiche.vw_notifiche;
CREATE OR REPLACE VIEW gds_srv.vw_cantieri as select * from gds_notifiche.vw_cantieri;



 CREATE OR REPLACE VIEW gds_srv.vw_notifica_stati_successivi
AS SELECT vw_notifica_stati_successivi.id_notifica,
    vw_notifica_stati_successivi.id_soggetto_fisico,
    vw_notifica_stati_successivi.data_notifica,
    vw_notifica_stati_successivi.id_soggetto_notificante,
    vw_notifica_stati_successivi.nome_notificante,
    vw_notifica_stati_successivi.cognome_notificante,
    vw_notifica_stati_successivi.cf_notificante,
    vw_notifica_stati_successivi.id_stato,
    vw_notifica_stati_successivi.stato,
    vw_notifica_stati_successivi.id_stato_successivo,
    vw_notifica_stati_successivi.id_stato_prossimo,
    vw_notifica_stati_successivi.descr_stato_attuale,
    vw_notifica_stati_successivi.descr_stato_prossimo,
    su.label_bottone
   FROM gds_notifiche.vw_notifica_stati_successivi
   LEFT JOIN gds_ui.stati_ui su  ON su.id = vw_notifica_stati_successivi.id_stato_prossimo;
    
CREATE OR REPLACE VIEW gds_srv.vw_cantiere_persona_ruoli as select * from gds_notifiche.vw_cantiere_persona_ruoli;
CREATE OR REPLACE VIEW gds_srv.vw_cantiere_imprese as select * from  gds_notifiche.vw_cantiere_imprese;
CREATE OR REPLACE VIEW gds_srv.vw_comuni as select * from  gds_notifiche.vw_comuni;

CREATE OR REPLACE VIEW  gds_srv.vw_nature_opera as select * from gds_ui.vw_nature_opera_ui;

CREATE OR REPLACE VIEW gds_srv.vw_imprese as select * from  gds_notifiche.vw_imprese;
CREATE OR REPLACE VIEW gds_srv.vw_soggetti_fisici as select * from  gds_notifiche.vw_soggetti_fisici;

/*create view gds_types.vw_modulo_campi as select mc.*,tc.descr,tc.codice 
from gds_types.modulo_campi mc join  gds_types.tipi_campo tc on mc.tipo_campo = tc.id;*/



CREATE OR REPLACE VIEW gds.vw_verbale_valori
AS SELECT v.id AS id_verbale,
    vv.id AS id_valore,
    vv.val,
    mc.nome_campo,
    mc.id as id_modulo_campo
   FROM gds.verbali v
     LEFT JOIN gds.verbale_valori vv ON v.id = vv.id_verbale
     JOIN gds_types.vw_modulo_campi mc ON mc.id_modulo = v.id_modulo;

CREATE OR REPLACE VIEW  gds_srv.vw_verbale_valori as select * from gds.vw_verbale_valori;
--CREATE OR REPLACE VIEW gds_srv.vw_ruoli        as select * from gds_ui.vw_ruoli_ui; i 

 CREATE OR REPLACE VIEW gds_log.vw_call_logs
AS SELECT cl.id as id_call_log,cl.id_transazione AS id_call, cl.procedura, cl.fase, cl.ts, cl.val
from gds_log.call_logs cl; 


CREATE OR REPLACE VIEW gds_log.vw_transazioni
AS SELECT 
     t.id,
     t.id as id_transazione,
     t.id_user,
     t.ts,
     t.descr as descrizione_transazione
   FROM gds_log.transazioni t;
  
  
  CREATE OR REPLACE VIEW gds_log.vw_operazioni
AS SELECT 
    o.id,
    o.id as id_operazione,
    o.id_transazione,
    o.procedura,
    o.fase,
    o.ts_start,
    o.ts_transazione,
    o.val,
    o.ts_end,
    o.ret
   FROM gds_log.operazioni o 
   join gds_log.transazioni t on o.id = t.id;
  
    
    CREATE OR REPLACE VIEW gds.vw_verbali
AS SELECT v.id,
    v.id AS id_verbale,
    v.id_modulo,
    v.ts
   FROM gds.verbali v;
  
  
  CREATE OR REPLACE VIEW gds.vw_verbale_valori
AS SELECT v.id AS id_verbale,
    vv.id AS id_valore,
    vv.val,
    mc.nome_campo,
    mc.id AS id_modulo_campo
   FROM gds.verbali v
     LEFT JOIN gds.verbale_valori vv ON v.id = vv.id_verbale
     JOIN gds_types.vw_modulo_campi mc ON mc.id_modulo = v.id_modulo;
  
    
CREATE OR REPLACE VIEW gds_notifiche.vw_documenti
AS SELECT  d.id,
   d.id as id_documento,
   d.tipo,
   d.id_interno,
   d.cod_documento,
   d.titolo,
   d.dati
   FROM gds_notifiche.documenti d ;
  
  
RETURN 'OK';
END
$$;


ALTER FUNCTION gds_notifiche.create_view() OWNER TO postgres;

--
-- Name: create_view_h_(); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.create_view_h_() RETURNS text
    LANGUAGE plpgsql
    AS $$
	begin
		
	  
	  drop view  if exists gds.vw_h_allegati  cascade;
	  drop view  if exists gds.vw_h_fase_persone  cascade;
	  drop view  if exists gds.vw_h_fase_verbali  cascade;
	  drop view  if exists gds.vw_h_impresa_sedi  cascade;
	  drop view  if exists gds.vw_h_imprese  cascade;
	  drop view  if exists gds.vw_h_ispezione_fasi  cascade;
	  drop view  if exists gds.vw_h_ispezioni_imprese  cascade;
	  drop view  if exists gds.vw_h_ispezioni  cascade;
      drop view  if exists gds.vw_h_nucleo_ispettori  cascade;
      drop view if exists gds.vw_h_verbale_valori  cascade;
      drop view  if exists gds.vw_h_verbali  cascade;
    
     
     
     
      drop view  if exists gds_macchine.vw_h_macchine cascade;
      drop view  if exists gds_macchine.vw_h_costruttori cascade;

     
      drop view if exists gds_notifiche.vw_h_cantiere_imprese  cascade;
      drop view  if exists gds_notifiche.vw_h_cantiere_persona_ruoli  cascade;
      drop view if exists gds_notifiche.vw_h_cantieri  cascade;
      drop view if exists gds_notifiche.vw_h_email_asl  cascade;
      drop view  if exists gds_notifiche.vw_h_cantieri  cascade;
      drop view if exists gds_notifiche.vw_h_imprese  cascade;
      drop view  if exists gds_notifiche.vw_h_notifiche  cascade;
      drop view  if exists gds_notifiche.vw_h_documenti  cascade;
      drop view  if exists gds_notifiche.vw_h_soggetti_fisici  cascade;
     
     
      drop view if exists gds_types.vw_h_fasi  cascade;
      drop view if exists gds_types.vw_h_moduli  cascade;
      drop view if exists gds_types.vw_h_modulo_campi  cascade;
      drop view if exists gds_types.vw_h_motivi  cascade;
      drop view if exists gds_types.vw_h_motivi_isp  cascade;
      drop view if exists gds_types.vw_h_nature_opera  cascade;
      drop view if exists gds_types.vw_h_ruoli  cascade;
      drop view if exists gds_types.vw_h_stati  cascade;
      drop view if exists gds_types.vw_h_stati_successivi cascade;
      drop view if exists gds_types.vw_h_tipi_campo  cascade;
      drop view if exists gds_types.vw_h_tipi_macchina  cascade;
      drop view if exists gds_types.vw_h_ente_uo  cascade;
      drop view if exists gds_types.vw_h_enti  cascade;
      drop view if exists gds_types.vw_h_esiti_per_fase cascade;
      drop view if exists gds_types.vw_h_uo cascade;
      drop view if exists gds_types.vw_h_fasi_ cascade;
      drop view if exists gds_types.vw_h_fasi_esiti cascade;
      drop view if exists gds_types.vw_h_natura_giuridica   cascade;
      drop view if exists gds_types.vw_h_stati_ispezione  cascade;
      drop view if exists gds_types.vw_h_stati_ispezioni_successivi  cascade;
      drop view if exists gds_types.vw_h_tipi_sede  cascade;
     
      drop view if exists gds_ui.vw_h_messaggi_ui  cascade;
      drop view if exists gds_ui.vw_h_nature_opera_ui cascade;
      drop view if exists gds_ui.vw_h_ruoli_ui  cascade;
      drop view if exists gds_ui.vw_h_stati_ui  cascade;
      drop view if exists gds_ui.vw_h_stati_notifica_ui  cascade;
      drop view if exists gds_ui.vw_h_stati_successivi_ui  cascade;
      drop view if exists gds_uivw._h_verbali_ui  cascade;
      drop view if exists gds_ui.vw_h_stati_ispezione_ui  cascade;



--gds

CREATE VIEW gds.vw_h_allegati AS
 SELECT ha.id,
    ha.id AS id_allegato,
    ha.descrizione,
    ha.nome,
    ha.file,
    ha.preview,
    ha.data_inserimento,
    ha.id_operatore,
    ha.validita,
    ha.id_transazione,
    ha.ts,
    ha.ts_transazione
   FROM gds._h_allegati ha;
  
  
  CREATE VIEW gds.vw_h_fase_persone AS
 SELECT hfp.id,
    hfp.id AS id_fase_persona,
    hfp.id_soggetto_fisico,
    hfp.id_ispezione_fase,
    hfp.data_inserimento,
    hfp.ordine,
    hfp.validita,
    hfp.id_transazione,
    hfp.ts,
    hfp.ts_transazione
   FROM gds._h_fase_persone hfp;
   
 
CREATE VIEW gds.vw_h_fase_verbali AS
 SELECT hfv.id,
    hfv.id AS id_fase_verbale,
    hfv.id_ispezione_fase,
    hfv.id_verbale,
    hfv.data,
    hfv.validita,
    hfv.id_transazione,
    hfv.ts,
    hfv.ts_transazione
   FROM gds._h_fase_verbali hfv;
  
  
CREATE VIEW gds.vw_h_impresa_sedi AS
 SELECT his.id,
    his.id AS id_impresa_sede,
    his.id_impresa,
    his.id_tipo_sede,
    his.codice_pat,
    his.datainizio,
    his.datafine,
    his.id_indirizzo,
    his.validita,
    his.id_transazione,
    his.ts,
    his.ts_transazione
   FROM gds._h_impresa_sedi his;
  
  
  CREATE VIEW gds.vw_h_imprese AS
 SELECT hi.id,
    hi.id AS id_impresa,
    hi.codice_inail,
    hi.codice_fiscale,
    hi.partita_iva,
    hi.id_natura_giuridica,
    hi.nome_azienda,
    hi.codice_ateco,
    hi.tipo_ateco,
    hi.anno_competenza,
    hi.numero_dipendenti,
    hi.numero_artigiani,
    hi.addetti_speciali,
    hi.note,
    hi.data_inserimento,
    hi.operatore_inserimento,
    hi.data_modifica,
    hi.operatore_modifica,
    hi.validita,
    hi.id_transazione,
    hi.ts,
    hi.ts_transazione
   FROM gds._h_imprese hi;
  
  
  CREATE VIEW gds.vw_h_ispezione_fasi AS
 SELECT hif.id,
    hif.id AS id_ispezione_fase,
    hif.id_ispezione,
    hif.data_creazione,
    hif.data_modifica,
    hif.id_impresa_sede,
    hif.id_fase_esito,
    hif.data_fase,
    hif.note,
    hif.validita,
    hif.id_transazione,
    hif.ts,
    hif.ts_transazione
   FROM gds._h_ispezione_fasi hif;
  
  
  
CREATE VIEW gds.vw_h_ispezione_imprese AS
 SELECT hii.id,
    hii.id AS id_ispezione_impresa,
    hii.id_ispezione,
    hii.id_impresa_sede,
    hii.progressivo,
    hii.validita,
    hii.id_transazione,
    hii.ts,
    hii.ts_transazione
   FROM gds._h_ispezione_imprese hii;

  
  
  CREATE VIEW gds.vw_h_ispezioni AS
 SELECT hi.id,
    hi.id AS id_ispezione,
    hi.id_cantiere,
    hi.id_motivo_isp,
    hi.altro,
    hi.id_ente_uo,
    hi.descr,
    hi.id_stato_ispezione,
    hi.per_conto_di,
    hi.data_inserimento,
    hi.data_modifica,
    hi.note,
    hi.data_ispezione,
    hi.codice_ispezione,
    hi.validita,
    hi.id_transazione,
    hi.ts,
    hi.ts_transazione
   FROM gds._h_ispezioni hi;
   
   
   CREATE VIEW gds.vw_h_nucleo_ispettori AS
 SELECT hni.id,
    hni.id AS id_nucleo_ispettore,
    hni.id_ispezione,
    hni.id_soggetto_fisico,
    hni.progressivo,
    hni.validita,
    hni.id_transazione,
    hni.ts,
    hni.ts_transazione
   FROM gds._h_nucleo_ispettori hni;

  
  CREATE VIEW gds.vw_h_verbale_valori AS
 SELECT hvv.id,
    hvv.id AS id_verbale_valore,
    hvv.id_verbale,
    hvv.id_modulo_campo,
    hvv.val,
    hvv.validita,
    hvv.id_transazione,
    hvv.ts,
    hvv.ts_transazione
   FROM gds._h_verbale_valori hvv;
  
  
  
  CREATE OR REPLACE VIEW gds.vw_h_verbali
AS SELECT hv.id,
    hv.id AS id_verbale,
    hv.id_modulo,
    hv.id_transazione,
    hv.validita,
    hv.ts,
    hv.ts_transazione
   FROM gds._h_verbali hv;
  
  --gds_macchine
  
  CREATE OR REPLACE VIEW gds_macchine.vw_h_macchine
AS SELECT hm.id,
  hm.id as id_macchina,
  hm.id_tipo_macchina,
  hm.id_costruttore,
  hm.modello,
  hm.data_modifica,
  hm.data_inserimento,
  hm.validita,
  hm.id_transazione,
  hm.ts,
  hm.ts_transazione
   FROM gds_macchine."_h_macchine" hm;
  
     
CREATE OR REPLACE VIEW gds_macchine.vw_h_costruttori
AS SELECT hc.id,
  hc.id as id_costruttore,
  hc.descr,
  hc.validita,
  hc.id_transazione,
  hc.ts,
  hc.ts_transazione
   FROM gds_macchine."_h_costruttori" hc ;
     
CREATE OR REPLACE VIEW gds_notifiche.vw_h_cantiere_imprese
AS SELECT hci.id,
    hci.id AS id_cantiere_impresa,
    hci.id_cantiere,
    hci.id_impresa,
    hci.ordine,
    hci.validita,
    hci.id_transazione,
    hci.ts,
    hci.ts_transazione
   FROM gds_notifiche._h_cantiere_imprese hci;
  
  --gds_notifiche
  
  CREATE OR REPLACE VIEW gds_notifiche.vw_h_cantiere_persona_ruoli
AS SELECT hcpr.id,
    hcpr.id AS id_cantiere_persona_ruolo,
    hcpr.id_cantiere,
    hcpr.id_soggetto_fisico,
    hcpr.id_ruolo,
    hcpr.validita,
    hcpr.id_transazione,
    hcpr.ts,
    hcpr.ts_transazione
   FROM gds_notifiche._h_cantiere_persona_ruoli hcpr;
  
CREATE OR REPLACE VIEW gds_notifiche.vw_h_cantieri
AS SELECT c.id AS id_cantiere,
    c.id,
    c.id_natura_opera,
    c.id_notifica,
    c.denominazione,
    c.data_presunta,
    c.durata_presunta,
    c.numero_imprese,
    c.numero_lavoratori,
    c.ammontare,
    c.altro,
    c.id_indirizzo,
    n.natura_opera,
    nt.id_soggetto_notificante,
    nt.data_notifica,
    nt.nome_notificante,
    nt.cognome_notificante,
    nt.cf_notificante,
    nt.via AS via_notificante,
    nt.comune AS comune_notificante,
    nt.id_comune AS id_comune_notificante,
    nt.cod_provincia AS cod_provincia_notificante,
    i.via,
    i.comune,
    i.id_comune,
    i.cap,
    i.cod_provincia,
    i.lat,
    i.lng,
    nt.id_stato,
    nt.stato,
    nt.cap AS cap_notificante,
    nt.id_indirizzo AS id_indirizzo_notificante,
    c.cuc,
    nt.cun,
    i.civico,
    nt.civico AS civico_notificante,
    i.codiceistatasl,
    i.codiceistatcomune,
    ea.address AS indirizzo_mail,
    nt.scaricabile,
    nt.id_notifica_succ,
    nt.attivo,
    c.ts,
    c.ts_transazione,
    c.validita * n.validita * ea.validita * nt.validita AS validita,
        CASE
            WHEN lower(c.validita) >= lower(n.validita) AND lower(c.validita) >= lower(ea.validita) AND lower(c.validita) >= lower(nt.validita) THEN c.id_transazione
            WHEN lower(n.validita) >= lower(c.validita) AND lower(n.validita) >= lower(ea.validita) AND lower(n.validita) >= lower(nt.validita) THEN n.id_transazione
            WHEN lower(ea.validita) >= lower(c.validita) AND lower(ea.validita) >= lower(n.validita) AND lower(c.validita) >= lower(nt.validita) THEN ea.id_transazione
            WHEN lower(nt.validita) >= lower(c.validita) AND lower(nt.validita) >= lower(n.validita) AND lower(nt.validita) >= lower(ea.validita) THEN nt.id_transazione
            ELSE NULL::bigint
        END AS id_transazione
   FROM gds_notifiche._h_cantieri c
     JOIN gds_notifiche.vw_h_notifiche nt ON c.id_notifica = nt.id_notifica
     LEFT JOIN gds_types.vw_h_nature_opera n ON n.id_natura_opera = c.id_natura_opera AND c.validita && n.validita
     LEFT JOIN gds_notifiche.vw_opu_indirizzi i ON i.id_indirizzo = c.id_indirizzo
     LEFT JOIN gds_notifiche._h_email_asl ea ON ea.id_asl::text = i.codiceistatasl::text AND c.validita && ea.validita;
    
  
   CREATE OR REPLACE VIEW gds_notifiche.vw_h_email_asl
AS select hea.id,
   hea.id as id_email_asl,
   hea.id_asl,
   hea.address,
   hea.validita,
   hea.id_transazione,
   hea.ts,
   hea.ts_transazione
   FROM gds_notifiche."_h_email_asl" hea;
  
  
  CREATE OR REPLACE VIEW gds_notifiche.vw_h_imprese
AS SELECT hi.id,
    hi.id AS id_impresa,
    hi.id_gisa,
    hi.ragione_sociale,
    hi.partita_iva,
    hi.codice_fiscale,
    hi.validita,
    hi.id_transazione,
    hi.ts,
    hi.ts_transazione
   FROM gds_notifiche._h_imprese hi;

CREATE OR REPLACE VIEW gds_notifiche.vw_h_notifiche
AS SELECT n.id_notifica,
    n.id,
    n.id_soggetto_fisico,
    n.data_notifica,
    n.id_soggetto_fisico AS id_soggetto_notificante,
    s.nome AS nome_notificante,
    s.cognome AS cognome_notificante,
    s.codice_fiscale AS cf_notificante,
    n.id_stato,
    st.descr AS stato,
    n.data_modifica,
    c.denominazione,
    st.modificabile,
    s.via,
    s.comune,
    s.cod_provincia,
    s.id_comune,
    s.cap,
    s.id_indirizzo,
    n.cun,
    c.id AS id_cantiere,
    st.modificabile_cantiere,
    c.cuc,
    s.civico,
    st.scaricabile,
    n.id_notifica_succ,
    st.attivo,
    n.ts,
    n.ts_transazione,
    n.validita * c.validita * st.validita * s.validita AS validita,
        CASE
            WHEN lower(n.validita) >= lower(c.validita) AND lower(n.validita) >= lower(st.validita) AND lower(n.validita) >= lower(s.validita) THEN n.id_transazione
            WHEN lower(c.validita) >= lower(n.validita) AND lower(c.validita) >= lower(st.validita) AND lower(c.validita) >= lower(s.validita) THEN c.id_transazione
            WHEN lower(st.validita) >= lower(c.validita) AND lower(st.validita) >= lower(n.validita) AND lower(st.validita) >= lower(s.validita) THEN st.id_transazione
            WHEN lower(s.validita) >= lower(c.validita) AND lower(s.validita) >= lower(st.validita) AND lower(s.validita) >= lower(n.validita) THEN s.id_transazione
            ELSE NULL::bigint
        END AS id_transazione
   FROM gds_notifiche._h_notifiche n
     LEFT JOIN gds_notifiche.vw_h_soggetti_fisici s ON n.id_soggetto_fisico = s.id_soggetto_fisico AND n.validita && s.validita
     JOIN gds_types.vw_h_stati st ON st.id_stato = n.id_stato AND st.validita && n.validita
     LEFT JOIN gds_notifiche._h_cantieri c ON c.id_notifica = n.id_notifica AND c.validita && n.validita;
    
  
  create or replace view gds_notifiche.vw_h_documenti
  as select hd.id,
   hd.id as id_documento,
   hd.tipo,
   hd.id_interno,
   hd.cod_documento,
   hd.titolo,
   hd.dati,
   hd.validita,
   hd.id_transazione,
	hd.ts,
	hd.ts_transazione
   from gds_notifiche."_h_documenti" hd;
  
  
 
CREATE OR REPLACE VIEW gds_notifiche.vw_h_soggetti_fisici
AS SELECT s.id,
    s.id AS id_soggetto_fisico,
    s.titolo,
    s.nome,
    s.cognome,
    s.comune_nascita,
    s.codice_fiscale,
    s.email,
    s.indirizzo_id AS id_indirizzo,
    i.via,
    i.comune,
    i.cap,
    i.id_comune,
    i.cod_provincia,
    i.civico,
    s.validita,
    s.validita AS tsrange,
    s.id_transazione,
    s.ts,
    s.ts_transazione
   FROM gds_notifiche._h_soggetti_fisici s
     LEFT JOIN gds_notifiche.vw_opu_indirizzi i ON s.indirizzo_id = i.id;
 
  --gds_types
  
 CREATE OR REPLACE VIEW gds_types.vw_h_fasi
AS SELECT hf.id,
    hf.id AS id_fase,
    hf.descr,
    hf.prop,
    hf.cnt,
	hf.cnt_prop,
	hf.cnt_prop_nec,
	hf.cnt_nec,
    hf.validita,
    hf.id_transazione,
    hf.ts,
    hf.ts_transazione
   FROM gds_types._h_fasi hf;
  CREATE OR REPLACE VIEW gds_types.vw_h_moduli
AS SELECT hm.id,
    hm.id AS id_modulo,
    hm.id_transazione,
    hm.descr,
    hm.nome_file,
    hm.validita,
    hm.ts,
    hm.ts_transazione
   FROM gds_types._h_moduli hm;
  
  CREATE OR REPLACE VIEW gds_types.vw_h_modulo_campi
AS SELECT hmc.id,
    hmc.id AS id_modulo_campo,
    hmc.id_modulo,
    hmc.id_transazione,
    hmc.nome_campo,
    hmc.tipo_campo,
    hmc.validita,
    hmc.ts,
    hmc.ts_transazione
   FROM gds_types._h_modulo_campi hmc;
  
  CREATE OR REPLACE VIEW gds_types.vw_h_motivi
AS SELECT hm.id,
    hm.id AS id_motivo,
    hm.programma_vigilanza,
    hm.richiesta_vigilanza,
    hm.iniziativa_vigilanza,
    hm.indagine_infortunio,
    hm.indagine_malattia,
    hm.emissione_parere,
    hm.validita,
    hm.id_transazione,
    hm.ts,
    hm.ts_transazione
   FROM gds_types._h_motivi hm;
  
  CREATE OR REPLACE VIEW gds_types.vw_h_motivi_isp
AS SELECT hmi.id,
	hmi.id as id_motivo_isp,
	hmi.descr,
	hmi.descr_interna,
	hmi.validita ,
	hmi.id_transazione,
	hmi.ts,
	hmi.ts_transazione
  FROM gds_types."_h_motivi_isp" hmi ; 
  
  CREATE OR REPLACE VIEW gds_types.vw_h_nature_opera
AS SELECT hnt.id,
    hnt.id AS id_natura_opera,
    hnt.descr,
    hnt.descr AS natura_opera,
    hnt.validita,
    hnt.ts,
    hnt.ts_transazione,
    hnt.id_transazione
   FROM gds_types._h_nature_opera hnt;
  
  CREATE OR REPLACE VIEW gds_types.vw_h_ruoli
AS SELECT hr.id,
    hr.id AS id_ruolo,
    hr.descr,
    hr.richiesta_pec,
    hr.obbligatorio,
    hr.validita,
    hr.ts,
    hr.id_transazione,
    hr.ts_transazione
   FROM gds_types._h_ruoli hr;
  
 CREATE OR REPLACE VIEW gds_types.vw_h_stati
AS SELECT hst.id,
    hst.id AS id_stato,
    hst.descr,
    hst.descr AS stato,
    hst.modificabile,
    hst.modificabile_cantiere,
    hst.scaricabile,
    hst.visibile,
    hst.genera_pdf,
    hst.controlli,
    hst.attivo,
    hst.validita,
    hst.ts,
    hst.id_transazione,
    hst.ts_transazione
   FROM gds_types._h_stati hst;
  
  CREATE OR REPLACE VIEW gds_types.vw_h_stati_successivi
AS SELECT hss.id,
   hss.id as id_stato_successivo,
   hss.id_stato_attuale,
   hss.id_stato_prossimo,
   hss.validita,
   hss.id_transazione,
   hss.ts,
   hss.ts_transazione
   FROM gds_types."_h_stati_successivi" hss ;
  
  
  CREATE OR REPLACE VIEW gds_types.vw_h_tipi_campo
AS SELECT htc.id,
    htc.id AS id_tipo_campo,
    htc.descr,
    htc.codice,
    htc.validita,
    htc.ts,
    htc.ts_transazione,
    htc.id_transazione
   FROM gds_types._h_tipi_campo htc;
  
  CREATE OR REPLACE VIEW gds_types.vw_h_tipi_macchina
AS SELECT htm.id,
	htm.id as id_tipo_macchina,
	htm.descr,
	htm.validita,
	htm.id_transazione,
	htm.ts,
	htm.ts_transazione
  FROM gds_types."_h_tipi_macchina" htm;
 
 
 CREATE OR REPLACE VIEW gds_types.vw_h_ente_uo
AS SELECT heu.id,
  heu.id as id_ente_uo,
  heu.id_ente,
  heu.id_uo,
  heu.validita,
  heu.id_transazione,
  heu.ts,
  heu.ts_transazione
   FROM gds_types._h_ente_uo heu ;
  
  
  CREATE OR REPLACE VIEW gds_types.vw_h_enti
AS SELECT he.id,
  he.id as id_ente,
  he.descr_ente ,
  he.id_asl,
  he.validita,
  he.id_transazione,
  he.ts,
  he.ts_transazione
   FROM gds_types."_h_enti" he;
  
  CREATE OR REPLACE VIEW gds_types.vw_h_esiti_per_fase 
AS SELECT hef.id,
  hef.id as id_esito_per_fase,
  hef.descr_esito_per_fase,
  hef.riferimento_fase_esito,
  hef.validita,
  hef.id_transazione,
  hef.ts,
  hef.ts_transazione
   FROM gds_types._h_esiti_per_fase hef;
  
  CREATE OR REPLACE VIEW gds_types.vw_h_uo
AS SELECT hu.id,
  hu.id as id_uo,
  hu.descr_uo,
  hu.validita,
  hu.id_transazione,
  hu.ts,
  hu.ts_transazione
   FROM gds_types."_h_uo" hu ;
  
  
  CREATE OR REPLACE VIEW gds_types.vw_h_fasi_
AS SELECT hf.id,
  hf.id as id_fase_,
  hf.primo_sopralluogo ,
  hf.sopralluogo_prossimo ,
  hf.attività_prescrizione ,
  hf.attività_accertamenti ,
  hf.attività_sospensione,
  hf.atti_indagine,
  hf.documetazione , 
  hf.validita,
  hf.id_transazione,
  hf.ts,
  hf.ts_transazione
   FROM gds_types."_h_fasi_" hf ;
  
  
  CREATE OR REPLACE VIEW gds_types.vw_h_fasi_esiti
AS SELECT hfe.id,
  hfe.id as id_fase_esito,
  hfe.id_fase,
  hfe.id_esito_per_fase,
  hfe.validita,
  hfe.id_transazione,
  hfe.ts,
  hfe.ts_transazione
   FROM gds_types."_h_fasi_esiti" hfe  ;
  
  
  CREATE OR REPLACE VIEW gds_types.vw_h_natura_giuridica
AS SELECT hng.id,
  hng.id as id_natura_giuridica,
  hng.codice,
  hng.abb,
  hng.descrizione,
  hng.validita,
  hng.id_transazione,
  hng.ts,
  hng.ts_transazione
   FROM gds_types."_h_natura_giuridica" hng  ;
  
  
  CREATE OR REPLACE VIEW gds_types.vw_h_stati_ispezione
AS SELECT hsi.id,
  hsi.id as id_stato_ispezione,
  hsi.descr,
  hsi.modificabile,
  hsi.validita,
  hsi.id_transazione,
  hsi.ts,
  hsi.ts_transazione
   FROM gds_types."_h_stati_ispezione" hsi  ;
  
  
  
   CREATE OR REPLACE VIEW gds_types.vw_h_stati_ispezione_successivi
AS SELECT hsis.id,
  hsis.id as id_stato_ispezione_successivo,
  hsis.id_stato_ispezione_attuale,
  hsis.id_stato_ispezione_prossimo,
  hsis.validita,
  hsis.id_transazione,
  hsis.ts,
  hsis.ts_transazione
   FROM gds_types."_h_stati_ispezione_successivi" hsis  ;
  
  
   CREATE OR REPLACE VIEW gds_types.vw_h_tipi_sede
AS SELECT hts.id,
  hts.id as id_tipo_sede,
  hts.codice,
  hts.descrizione,
  hts.validita,
  hts.id_transazione,
  hts.ts,
  hts.ts_transazione
   FROM gds_types."_h_tipi_sede" hts  ; 
  
  
  --gds_ui
  
  CREATE OR REPLACE VIEW gds_ui.vw_h_messaggi_ui
AS SELECT hmu.id,
    hmu.id AS id_messaggio,
    hmu.procedura,
    hmu.valore,
    hmu.msg,
    hmu.validita,
    hmu.ts,
    hmu.ts_transazione,
    hmu.id_transazione
   FROM gds_ui._h_messaggi_ui hmu;
  
  CREATE OR REPLACE VIEW gds_ui.vw_h_nature_opera_ui
AS SELECT hnu.id,
    hnu.id AS id_natura_opera,
    hnu.ordinamento,
    hnu.validita,
    hnu.ts,
    hnu.ts_transazione,
    hnu.id_transazione
   FROM gds_ui._h_natura_opere hnu;
  
  CREATE OR REPLACE VIEW gds_ui.vw_h_ruoli_ui
AS SELECT hru.id,
    hru.id AS id_ruolo,
    hru.ordinamento,
    hru.validita,
    hru.id_transazione,
    hru.ts,
    hru.ts_transazione
   FROM gds_ui._h_ruoli_ui hru;
  
  CREATE OR REPLACE VIEW gds_ui.vw_h_stati_ui
AS SELECT hsu.id,
    hsu.id AS id_stato,
    hsu.label_bottone,
    hsu.colore,
    hsu.validita,
    hsu.ts,
    hsu.ts_transazione,
    hsu.id_transazione
   FROM gds_ui._h_stati_ui hsu;
  
  
    CREATE OR REPLACE VIEW gds_ui.vw_h_stati_notifica_ui
AS SELECT snu.id,
    snu.id as id_stato_notifica_ui,
    snu.label_bottone,
    snu.colore,
    snu.msg,
    snu.ordine,
    snu.validita,
    snu.id_transazione,
    snu.ts,
    snu.ts_transazione
   FROM gds_ui._h_stati_notifica_ui snu;
  
  
  
   CREATE OR REPLACE VIEW gds_ui.vw_h_stati_successivi_ui
AS SELECT ssu.id,
   ssu.id as id_stato_successivo_ui,
   ssu.label_bottone,
   ssu.colore,
   ssu.msg,
   ssu.ordine,
   ssu.validita,
   ssu.id_transazione,
   ssu.ts,
   ssu.ts_transazione
   FROM gds_ui._h_stati_successivi_ui ssu;
  
  
  CREATE OR REPLACE VIEW gds_ui.vw_h_verbali_ui
AS SELECT vu.id,
    vu.id AS id_verbale,
    vu.descr AS verbale,
    vu.filename,
    vu.validita,
    vu.ts_transazione,
    vu.id_transazione,
    vu.ts
   FROM gds_ui._h_verbali vu;
  
  
  
  CREATE OR REPLACE VIEW gds_ui.vw_h_stati_ispezione_ui
AS SELECT hsiu.id,
  hsiu.id as id_ispezione_ui,
  hsiu.label_bottone,
  hsiu.colore,
  hsiu.msg ,
  hsiu.ordine,
  hsiu.validita,
  hsiu.id_transazione,
  hsiu.ts,
  hsiu.ts_transazione
   FROM gds_ui."_h_stati_ispezione_ui" hsiu;
  
     return 'OK';
	END;
$$;


ALTER FUNCTION gds_notifiche.create_view_h_() OWNER TO postgres;

--
-- Name: create_view_ph_(); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.create_view_ph_() RETURNS text
    LANGUAGE plpgsql
    AS $$
	begin
	
		 drop view  if exists gds_types.vw_ph_stati cascade;
		 drop view  if exists gds_notifiche.vw_ph_notifiche cascade;
		 drop view  if exists gds_types.vw_ph_nature_opere cascade;
	     drop view  if exists gds_types.vw_ph_email_asl cascade;
         drop view  if exists gds_notifiche.vw_ph_cantieri cascade;
         drop view  if exists gds_notifiche.vw_ph_soggetti_fisici cascade;
		
--Creazione ph
 
CREATE OR REPLACE VIEW gds_types.vw_ph_stati
AS SELECT 
    hs.id,
    hs.id AS id_stato,
    hs.descr,
    hs.descr AS stato,
    hs.modificabile,
    hs.modificabile_cantiere,
    hs.validita,
    hs.id_transazione,
    hs.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_types."_h_stati" hs 
  join gds_log.transazioni t on t.ts <@ hs.validita;
 
 
 
 
 CREATE OR REPLACE VIEW gds_notifiche.vw_ph_notifiche
AS SELECT 
   hn.id,
    hn.id AS id_notifica,
    hn.id_soggetto_fisico,
    hn.data_notifica,
    hn.id_stato,
    hn.cun,
    hn.id_notifica_succ,
    hn.data_modifica,
    hn.validita,
    hn.id_transazione,
    hn.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_notifiche."_h_notifiche" hn  
  join gds_log.transazioni t on t.ts <@ hn.validita
  left JOIN gds_notifiche.vw_ph_soggetti_fisici psf on psf.ts = hn.ts 
  join gds_types.vw_ph_stati vps on vps.ts = hn.ts
  left join gds_notifiche."_h_cantieri" hc on hc.ts = hn.ts;
 
 
 
 CREATE OR REPLACE VIEW gds_types.vw_ph_nature_opere
AS SELECT 
    hnt.id,
    hnt.id AS id_natura_opera,
    hnt.descr,
    hnt.descr AS natura_opera,
    hnt.validita,
    hnt.ts_transazione,
    hnt.id_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_types."_h_nature_opera" hnt 
  join gds_log.transazioni t on t.ts <@ hnt.validita;
 
 
  CREATE OR REPLACE VIEW gds_types.vw_ph_email_asl
AS SELECT 
    hea.id,
    hea.id AS id_email_asl,
    hea.id_asl,
    hea.address,
    hea.validita,
    hea.id_transazione,
    hea.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_notifiche."_h_email_asl" hea 
  join gds_log.transazioni t on t.ts <@ hea.validita;
 
 
   CREATE OR REPLACE VIEW gds_notifiche.vw_ph_cantieri
AS SELECT 
   hc.id,
    hc.id AS id_cantiere,
    hc.id_natura_opera,
    hc.id_notifica,
    hc.denominazione,
    hc.data_presunta,
    hc.durata_presunta,
    hc.numero_imprese,
    hc.numero_lavoratori,
    hc.ammontare,
    hc.altro,
    hc.id_indirizzo,
    hc.cuc,
    hc.validita,
    hc.id_transazione,
    hc.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_notifiche."_h_cantieri" hc 
  join gds_log.transazioni t on t.ts <@ hc.validita;
 
 
  CREATE OR REPLACE VIEW gds_notifiche.vw_ph_soggetti_fisici
AS SELECT 
    hsf.id,
    hsf.id AS id_soggetto_fisico,
    hsf.titolo,
    hsf.cognome,
    hsf.nome,
    hsf.comune_nascita,
    hsf.codice_fiscale,
    hsf.enteredby,
    hsf.modifiedby,
    hsf.ipenteredby,
    hsf.ipmodifiedby,
    hsf.sesso,
    hsf.telefono,
    hsf.fax,
    hsf.email,
    hsf.telefono1,
    hsf.data_nascita,
    hsf.documento_identita,
    hsf.indirizzo_id,
    hsf.provenienza_estera,
    hsf.riferimento_org_id,
    hsf.provincia_nascita,
    hsf.id_bdn,
    hsf.id_operatore_temp,
    hsf.trashed_date,
    hsf.note_hd,
    hsf.id_soggetto_precedente,
    hsf.id_nazione_nascita,
    hsf.id_comune_nascita,
    hsf.validita,
    hsf.id_transazione,
    hsf.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_notifiche."_h_soggetti_fisici" hsf 
  join gds_log.transazioni t on t.ts <@ hsf.validita;
 
 
 
 
 return 'OK';
 

	END;
$$;


ALTER FUNCTION gds_notifiche.create_view_ph_() OWNER TO postgres;

--
-- Name: create_view_ph_()(); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche."create_view_ph_()"() RETURNS text
    LANGUAGE plpgsql
    AS $$
	BEGIN
              --Creazione ph
 
CREATE OR REPLACE VIEW gds_types.vw_ph_stati
AS SELECT 
    hs.id,
    hs.id AS id_stato,
    hs.descr,
    hs.descr AS stato,
    hs.modificabile,
    hs.modificabile_cantiere,
    hs.validita,
    hs.id_transazione,
    hs.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_types."_h_stati" hs 
  join gds_log.transazioni t on t.ts <@ hs.validita;
 
 
 
 
 CREATE OR REPLACE VIEW gds_types.vw_ph_notifiche
AS SELECT 
   hn.id,
    hn.id AS id_notifica,
    hn.id_soggetto_fisico,
    hn.data_notifica,
    hn.id_stato,
    hn.cun,
    hn.id_notifica_succ,
    hn.data_modifica,
    hn.validita,
    hn.id_transazione,
    hn.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_notifiche."_h_notifiche" hn  
  join gds_log.transazioni t on t.ts <@ hn.validita;
 
 
 
 CREATE OR REPLACE VIEW gds_types.vw_ph_nature_opere
AS SELECT 
    hnt.id,
    hnt.id AS id_natura_opera,
    hnt.descr,
    hnt.descr AS natura_opera,
    hnt.validita,
    hnt.ts_transazione,
    hnt.id_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_types."_h_nature_opera" hnt 
  join gds_log.transazioni t on t.ts <@ hnt.validita;
 
 
  CREATE OR REPLACE VIEW gds_types.vw_ph_email_asl
AS SELECT 
    hea.id,
    hea.id AS id_email_asl,
    hea.id_asl,
    hea.address,
    hea.validita,
    hea.id_transazione,
    hea.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_notifiche."_h_email_asl" hea 
  join gds_log.transazioni t on t.ts <@ hea.validita;
 
 
   CREATE OR REPLACE VIEW gds_types.vw_ph_cantieri
AS SELECT 
   hc.id,
    hc.id AS id_cantiere,
    hc.id_natura_opera,
    hc.id_notifica,
    hc.denominazione,
    hc.data_presunta,
    hc.durata_presunta,
    hc.numero_imprese,
    hc.numero_lavoratori,
    hc.ammontare,
    hc.altro,
    hc.id_indirizzo,
    hc.cuc,
    hc.validita,
    hc.id_transazione,
    hc.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_notifiche."_h_cantieri" hc 
  join gds_log.transazioni t on t.ts <@ hc.validita;
 
 
  CREATE OR REPLACE VIEW gds_types.vw_ph_soggetti_fisici
AS SELECT 
    hsf.id,
    hsf.id AS id_soggetto_fisico,
    hsf.titolo,
    hsf.cognome,
    hsf.nome,
    hsf.comune_nascita,
    hsf.codice_fiscale,
    hsf.enteredby,
    hsf.modifiedby,
    hsf.ipenteredby,
    hsf.ipmodifiedby,
    hsf.sesso,
    hsf.telefono,
    hsf.fax,
    hsf.email,
    hsf.telefono1,
    hsf.data_nascita,
    hsf.documento_identita,
    hsf.indirizzo_id,
    hsf.provenienza_estera,
    hsf.riferimento_org_id,
    hsf.provincia_nascita,
    hsf.id_bdn,
    hsf.id_operatore_temp,
    hsf.trashed_date,
    hsf.note_hd,
    hsf.id_soggetto_precedente,
    hsf.id_nazione_nascita,
    hsf.id_comune_nascita,
    hsf.validita,
    hsf.id_transazione,
    hsf.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
  from gds_notifiche."_h_soggetti_fisici" hsf 
  join gds_log.transazioni t on t.ts <@ hsf.validita;
 
 
 
 
 return 'OK';

	END;
$$;


ALTER FUNCTION gds_notifiche."create_view_ph_()"() OWNER TO postgres;

--
-- Name: delete_notifica(bigint, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.delete_notifica(idnotifica bigint, idtransazione bigint) RETURNS integer
    LANGUAGE plpgsql
    AS $$
begin
	declare i integer;
			n integer;
			R_N gds_notifiche.notifiche%ROWTYPE;
			R_C gds_notifiche.cantieri%ROWTYPE;
			rec record;
	begin
		select * into R_N from gds_notifiche.notifiche where id=idnotifica;
		if R_N.id is null then
			return 0;
		end if;
				
		delete from gds_notifiche.notifiche where id=idnotifica;
		n:=gds_log.upd_record('gds_notifiche.notifiche',idtransazione,R_N,'D');
	
		select * into R_C from gds_notifiche.cantieri where id_notifica=idnotifica;
		if R_C.id is null then
			return 0;
		end if;

		FOR rec IN
	        SELECT * from gds_notifiche.cantiere_imprese where id_cantiere=R_C.id
	    LOOP
	    	delete from gds_notifiche.cantiere_imprese where id=rec.id;
 	       	n:=gds_log.upd_record('gds_notifiche.cantiere_imprese',idtransazione,rec,'D');
	    END LOOP;
		
		FOR rec in
			select * from gds_notifiche.cantiere_persona_ruoli where id_cantiere=R_C.id
		LOOP
			delete from gds_notifiche.cantiere_persona_ruoli where id=rec.id;
			n:=gds_log.upd_record('gds_notifiche.cantiere_persona_ruoli',idtransazione,rec,'D');
		END LOOP;
	
		delete from gds_notifiche.cantieri where id=R_C.id;
		n:=gds_log.upd_record('gds_notifiche.cantieri',idtransazione,R_C,'D');
		return 0;
	end;
END
$$;


ALTER FUNCTION gds_notifiche.delete_notifica(idnotifica bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: delete_notifiche_zombie(bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.delete_notifiche_zombie(idtransazione bigint) RETURNS integer
    LANGUAGE plpgsql
    AS $$
begin
	declare i integer;
			n integer;
			R_N gds_notifiche.notifiche%ROWTYPE;
			R_F gds.ispezione_fasi%ROWTYPE;
	begin
		n:=0;
		for R_N in (select *  from gds_notifiche.notifiche where id_stato= -1 and data_modifica < CURRENT_TIMESTAMP - interval '120 minutes') loop
			i:=gds_notifiche.delete_notifica (R_N.id,idtransazione);
		n:=n+1;
		end loop;
		delete from gds.ispezione_fasi where id_fase_esito is null and data_modifica < CURRENT_TIMESTAMP - interval '120 minutes';
		return n;
	end;
END
$$;


ALTER FUNCTION gds_notifiche.delete_notifiche_zombie(idtransazione bigint) OWNER TO postgres;

--
-- Name: duplica_notifica(bigint, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.duplica_notifica(idnotifica bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
		ret gds_types.result_type;
		n integer;
		id_op bigint;
	idcantiere bigint;
		proc_name varchar;
		R_notifica gds_notifiche.notifiche%ROWTYPE;
		R_cantiere gds_notifiche.cantieri%ROWTYPE;	
		R_c_i gds_notifiche.cantiere_imprese%ROWTYPE;
		R_c_r gds_notifiche.cantiere_persona_ruoli%ROWTYPE;
	begin
	proc_name:='gds.duplica_notifica';
	--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
	select * into R_notifica from gds_notifiche.notifiche where id=idnotifica;
	if R_notifica.id is null then
		ret.esito:=false;	
		ret:=gds_ui.build_ret(ret,proc_name,-1);
		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
		return ret;
	end if;
	R_notifica.id:=nextval('gds_notifiche.notifiche_id_seq');
	R_notifica.cun:= null;
 	R_notifica.data_notifica:=clock_timestamp();
	R_notifica.data_modifica:=clock_timestamp();
	select id into R_notifica.id_stato from gds_types.stati where descr='BOZZA-MODIFICA';
	insert into gds_notifiche.notifiche values(R_notifica.*);
    --insert into gds_notifiche."_h_notifiche" values (nextval('gds_notifiche."_h_notifiche_id_seq"'), R_notifica.*,tsrange(clock_timestamp()::timestamp,'infinity','[)'),idtransazione,clock_timestamp(),current_timestamp);
	n:=gds_log.upd_record('gds_notifiche.notifiche',idtransazione,R_notifica,'I');
	select * into R_cantiere from gds_notifiche.cantieri where id_notifica=idnotifica;
	if R_cantiere.id is not null then
		idcantiere:=R_cantiere.id;
		R_cantiere.id:=nextval('gds_notifiche.cantieri_id_seq');
		--R_cantiere.cuc:= null;
 		R_cantiere.id_notifica :=R_notifica.id;

 		insert into gds_notifiche.cantieri values(R_cantiere.*);
 		n:=gds_log.upd_record('gds_notifiche.cantieri',idtransazione,R_cantiere,'I');
 	    --insert into gds_notifiche."_h_cantieri" values (nextval('gds_notifiche."_h_cantieri_id_seq"'), R_cantieri,tsrange(clock_timestamp()::timestamp,'infinity','[)'),idtransazione,clock_timestamp(),current_timestamp);
 		for R_c_i in select * from gds_notifiche.cantiere_imprese ci where id_cantiere=idcantiere loop
 			R_c_i.id_cantiere:=R_cantiere.id;
 			R_c_i.id:=nextval('gds_notifiche.cantiere_imprese_id_seq');
 			insert into gds_notifiche.cantiere_imprese values (R_c_i.*);
 		end loop;
 	 	for R_c_r in select * from gds_notifiche.cantiere_persona_ruoli cpr where id_cantiere=idcantiere loop
 			R_c_r.id_cantiere:=R_cantiere.id;
 			R_c_r.id:=nextval('gds_notifiche.cantiere_persona_ruoli_id_seq');
 			insert into gds_notifiche.cantiere_persona_ruoli values (R_c_r.*);
 		end loop;
 	end if;

 	ret:=gds_ui.build_ret_ok(R_notifica.id);
 	--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
 raise notice 'DUPLICA NEW ID =%', ret.valore;
	return ret;

end;
end;
$$;


ALTER FUNCTION gds_notifiche.duplica_notifica(idnotifica bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_cantiere_imprese(bigint, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.get_cantiere_imprese(idnotifica bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	rt json;

	begin
		proc_name:='gds_notifiche.get_cantiere_imprese';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select json_agg(row_to_json(vw_cantiere_imprese)) into rt from gds_notifiche.vw_cantiere_imprese where id_notifica=idnotifica;

		ret.valore:= idnotifica;
	
		raise notice '%',json_array_length(rt);
	
		if json_array_length(rt) is null then
			ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
		end if;
	
	
	    --ret:=gds_ui.build_ret(ret,proc_name,R_ISP.id);
	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds_notifiche.get_cantiere_imprese(idnotifica bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_cantiere_persona_ruoli(bigint, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.get_cantiere_persona_ruoli(idnotifica bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	rt json;

	begin
		proc_name:='gds_notifiche.get_cantiere_persona_ruoli';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select json_agg(row_to_json(vw_cantiere_persona_ruoli)) into rt from gds_srv.vw_cantiere_persona_ruoli where id_notifica=idnotifica;

		ret.valore:= idnotifica;
	
		raise notice '%',json_array_length(rt);
	
		if json_array_length(rt) is null then
			ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
		end if;

	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds_notifiche.get_cantiere_persona_ruoli(idnotifica bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_cantieri(bigint, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.get_cantieri(idnotifica bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	rt json;

	begin
		proc_name:='gds_notifiche.get_cantieri';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select json_agg(row_to_json(vw_cantieri)) into rt from gds_notifiche.vw_cantieri where id_notifica=idnotifica;

		ret.valore:= idnotifica;
	
		raise notice '%',json_array_length(rt);
	
		if json_array_length(rt) is null then
			ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt->>0; --nel caso dei cantieri viene ritornato sempre solo un record
		end if;
	
		/*
	    if rt->>'id' is null then 
    		ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
	   	end if;
		*/
	
	   	--ret.esito:=true;
 		--ret.msg:=null;
	 	--ret.info:=rt;
	 	
	    --ret:=gds_ui.build_ret(ret,proc_name,R_ISP.id);
	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds_notifiche.get_cantieri(idnotifica bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_id_notificante(character varying, character varying, character varying, boolean, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.get_id_notificante(cf character varying, nome character varying, cognome character varying, ins boolean, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	DECLARE
	ret gds_types.result_type; -- START
		id_op bigint;
		n bigint;
		proc_name varchar; -- END
	R_S public.opu_soggetto_fisico%ROWTYPE; 
	BEGIN
	proc_name:='gds_notifiche.get_id_notificante';
	--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
	select * into R_S.id from gds_notifiche.vw_opu_soggetti_fisici where upper(codice_fiscale)=upper(cf);
	if R_S.id is null THEN
		IF NOT ins THEN
			ret.esito:=false;	
			ret:=gds_ui.build_ret(ret,proc_name,-3);
			--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
			return ret;
		END IF;
		if nome is null or cognome is null or nome='' or cognome ='' then
			ret.esito:=false;	
			ret:=gds_ui.build_ret(ret,proc_name,-1);
			--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
			return ret;
		end if;
		R_S.codice_fiscale := upper(replace(cf,' ',''));
		if length(R_S.codice_fiscale) != 16 then
			ret.esito:=false;	
			ret:=gds_ui.build_ret(ret,proc_name,-2);
			--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
			return ret;
		end if;
		R_S.nome:=upper(trim(nome));
		R_S.cognome:=upper(trim(cognome));

		R_S.id:=nextval('opu_soggetto_fisico_id_seq'::regclass);
		insert into public.opu_soggetto_fisico values (R_S.*);
		n:=gds_log.upd_record('public.opu_soggetto_fisico',idtransazione,R_S,'I');
	end if;
	ret.esito:=true;
	ret.valore:=R_S.id;
	ret:=gds_ui.build_ret(ret,proc_name,R_S.id);
	--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
	return ret;
	end;
END
$$;


ALTER FUNCTION gds_notifiche.get_id_notificante(cf character varying, nome character varying, cognome character varying, ins boolean, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_id_notificante_new(character varying, character varying, character varying, boolean, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.get_id_notificante_new(cf character varying, nome character varying, cognome character varying, ins boolean, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
    DECLARE
    ret gds_types.result_type; -- START
        id_op bigint;
        n bigint;
        proc_name varchar; -- END
        R_S gds.vw_access_notificante%ROWTYPE;
        R_C contact_%ROWTYPE;
        R_A access_%ROWTYPE;
        R_SF opu_soggetto_fisico%ROWTYPE;
    BEGIN
    proc_name:='gds_notifiche.get_id_notificante';
    --id_op:=gds_log.start_op(proc_name,idtransazione ,'');
    --select * into R_S.id from gds_notifiche.vw_opu_soggetti_fisici where upper(codice_fiscale)=upper(cf);
    select * into R_S from gds.vw_access_notificante where upper(codice_fiscale)=upper(cf);

    if R_S.contact_id is null THEN
        IF NOT ins THEN
            ret.esito:=false;   
            ret:=gds_ui.build_ret(ret,proc_name,-3);
            --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
            return ret;
        END IF;
        if nome is null or cognome is null or nome='' or cognome ='' then
            ret.esito:=false;   
            ret:=gds_ui.build_ret(ret,proc_name,-1);
            --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
            return ret;
        end if;
        R_C.codice_fiscale := upper(replace(cf,' ',''));
        if length(R_S.codice_fiscale) != 16 then
            ret.esito:=false;   
            ret:=gds_ui.build_ret(ret,proc_name,-2);
            --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
            return ret;
        end if;
        select * into R_SF from public.opu_soggetto_fisico osf where codice_fiscale=R_C.codice_fiscale;
       	if R_SF.id is null then
       		R_SF.nome:=upper(trim(nome));
			R_SF.cognome:=upper(trim(cognome));
			R_SF.codice_fiscale:=upper(trim(cf));
			R_SF.id:=nextval('opu_soggetto_fisico_id_seq'::regclass);
			R_S.id:=R_SF.id;
			insert into public.opu_soggetto_fisico values (R_SF.*);
			n:=gds_log.upd_record('public.opu_soggetto_fisico',idtransazione,R_SF,'I');
		else
			R_S.id:=R_SF.id;
		end if;
		R_S.contact_id := nextval('contact_contact_id_seq'::regclass);
        R_A.user_id:=nextval('access_user_id_seq'::regclass);
        R_A.username:= 'Notificante_user _'||R_A.user_id;
        R_A.password:= 'Notificante_psw _'||R_A.user_id;
        R_A.contact_id:= R_S.contact_id ;
        R_S.description:='Profilo Notificatore';
        R_A.role_id = (select role_id from public.role where role = R_S.description);
        R_A.enabled   := true;
        R_A.entered   := current_timestamp;
        R_A.last_login:= current_timestamp;
        R_A.modified:= current_timestamp;
        R_A.last_interaction_time := current_timestamp;
        R_A.in_dpat   := false;
        R_A.in_nucleo_ispettivo   := false;
        R_A.in_access   := true;
        R_A.contact_id        :=R_S.contact_id;
        R_A.enteredby:=  R_A.contact_id;
        R_A.modifiedby:=  R_A.contact_id;
        R_A.allow_webdav_access :=true;
        R_A.allow_httpapi_access :=true;
        insert into public.access_ values (R_A.*);
        n:=gds_log.upd_record('public.access_',idtransazione,R_C,'I');       
       
        R_C.namefirst:=upper(trim(nome));
        R_C.namelast :=upper(trim(cognome));
        R_C.contact_id        :=R_A.contact_id;
        R_C.notes     := 'Utente registrato come notificatore tramite SPID';
        R_C.enabled   := true;
        R_C.entered   := current_timestamp;
        R_C.modified   := current_timestamp;
        R_C.enteredby:=  R_A.user_id;
        R_C.modifiedby:=  R_A.user_id;
        R_C.site_id   := -1;
        R_C.employee := false;

        --insert into public.opu_soggetto_fisico values (R_S.*);
        insert into public.contact_ values (R_C.*);
        n:=gds_log.upd_record('public.contact_',idtransazione,R_C,'I');
    end if;
    ret.esito:=true;
    ret.valore:=R_S.id;
    ret.msg:='{"ruolo":"'||R_S.description||'","site_id":"'||coalesce(R_S.site_id::text,'')||'"}';
    ret.info:=ret.msg;
    ret:=gds_ui.build_ret(ret,proc_name,R_S.id);
    --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
   	n:=gds_notifiche.delete_notifiche_zombie(idtransazione);
    return ret;
    end;
END
$$;


ALTER FUNCTION gds_notifiche.get_id_notificante_new(cf character varying, nome character varying, cognome character varying, ins boolean, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_notifica(bigint, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.get_notifica(id_record bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	R_NOT gds_notifiche.vw_notifiche%ROWTYPE;
	rt json;

	begin
		proc_name:='gds.get_notifica';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select * into R_NOT from gds_notifiche.vw_notifiche where id=id_record;
		rt:=row_to_json(R_NOT);
	
		ret.valore:= id_record;
	
	    if rt->>'id' is null then 
    		ret.esito:=false;	
    		ret.msg:='id non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
	   	end if;

	    --ret:=gds_ui.build_ret(ret,proc_name,R_NOT.id);
	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds_notifiche.get_notifica(id_record bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_notifica_stati_successivi(bigint, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.get_notifica_stati_successivi(idnotifica bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	rt json;

	begin
		proc_name:='gds_notifiche.get_notifica_stati_successivi';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select json_agg(row_to_json(vw_notifica_stati_successivi)) into rt from gds_srv.vw_notifica_stati_successivi where id_notifica=idnotifica;

		ret.valore:= idnotifica;
	
		raise notice '%',json_array_length(rt);
	
		if json_array_length(rt) is null then
			ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
		end if;

	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds_notifiche.get_notifica_stati_successivi(idnotifica bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_notifiche_prec(bigint, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.get_notifiche_prec(idnotifica bigint, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	ret gds_types.result_type; 
	id_op bigint;   
	proc_name varchar;
	rt json;

	begin
		proc_name:='gds_notifiche.get_notifiche_prec';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		select json_agg(row_to_json(vw_notifiche_prec)) into rt from gds_notifiche.vw_notifiche_prec
	    where id_notifica=idnotifica and stato!='ELIMINATA';

		ret.valore:= idnotifica;
	
		raise notice '%',json_array_length(rt);
	
		if json_array_length(rt) is null then
			ret.esito:=false;	
    		ret.msg:='id ispezione non trovato';
    		ret.info:=null; 
    	else
    		ret.esito:=true;
 			ret.msg:=null;
	 		ret.info:=rt;
		end if;

	 	return ret;
	end;
	
end;
$$;


ALTER FUNCTION gds_notifiche.get_notifiche_prec(idnotifica bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_proc_msg(character varying, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.get_proc_msg(proc character varying, cod bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare n integer;
	R_msg gds_ui.messaggi_ui%ROWTYPE;
begin
	select * into R_msg from gds_ui.messaggi_ui where procedura=proc and valore=cod;
	if R_msg.id is not null then
		return coalesce(R_msg.msg,'');
	end if;
	if cod < 0 then
		return 'ERRORE GENERICO ';
	end if;

	if cod = 0 then
		return 'DATI SALVATI';
	end if;
	return 'ERRORE ' ;
end;
end
$$;


ALTER FUNCTION gds_notifiche.get_proc_msg(proc character varying, cod bigint) OWNER TO postgres;

--
-- Name: ins_notifica(character varying, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.ins_notifica(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare
	   ret gds_types.result_type; -- START
		id_op bigint;   
		proc_name varchar; -- END
	R_C gds_notifiche.cantieri%ROWTYPE; 
	R_N gds_notifiche.notifiche%ROWTYPE; 
	R_N_APP gds_notifiche.vw_notifiche%ROWTYPE; 
	R_S gds_types.vw_stati%ROWTYPE; 
	n integer;
	begin
	proc_name:='gds_notifiche.ins_notifica';
	--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
	select * into R_S from gds_types.vw_stati where descr='INIZIALE';
	raise notice 'ID_stato %', R_S.id;
	R_N_APP:=  json_populate_record(null::gds_notifiche.vw_notifiche, v::json);	
	/*raise notice 'ID %', R_N_APP.id_soggetto_notificante;
	select count(*) into n from gds_notifiche.vw_notifiche where id_stato=R_S.id and id_soggetto_notificante=R_N_APP.id_soggetto_notificante;
	if n>= 1 then
	   ret.esito:=false;	
	   ret:=gds_ui.build_ret(ret,proc_name,-1);
	   --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
	   return ret;
	end if;
	raise notice 'N %', N;*/

	R_N.id:=nextval('gds_notifiche.notifiche_id_seq');
	R_N.id_stato:=R_S.id;
	R_N.id_soggetto_fisico:=	R_N_APP.id_soggetto_notificante ;
	raise notice 'R_N.id_soggetto_fisico %',R_N.id_soggetto_fisico;
	raise notice 'select count(*) into n from gds_notifiche.vw_soggetti_fisici vsf where id_soggetto_fisico =%',R_N.id_soggetto_fisico;
	select count(*) into n from gds_notifiche.vw_soggetti_fisici vsf where id_soggetto_fisico =R_N.id_soggetto_fisico;
	raise notice 'N %',n;
	if n <=0 then
	    ret.esito:=false;	
		ret:=gds_ui.build_ret(ret,proc_name,-2);
		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
		return ret;
	end if;
	R_N.data_notifica:=clock_timestamp();
	R_N.data_modifica:=clock_timestamp();
    insert into gds_notifiche.notifiche values (R_N.*);
    n:=gds_log.upd_record('gds_notifiche.notifiche',idtransazione,R_N,'I');
    
    R_C.id:=nextval('gds_notifiche.cantieri_id_seq');
    R_C.id_notifica:=R_N.id;
    
    insert into gds_notifiche.cantieri values (R_C.*);
	n:=gds_log.upd_record('gds_notifiche.cantieri',idtransazione,R_C,'I');

    ret.esito:=true;	
	ret:=gds_ui.build_ret(ret,proc_name,R_N.id);
	--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
	return ret;
	end;
END
$$;


ALTER FUNCTION gds_notifiche.ins_notifica(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: load_tables(); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.load_tables() RETURNS text
    LANGUAGE plpgsql
    AS $$ 
     begin
	     declare n integer;
begin
truncate table apicoltura_relazione_imprese_sede_legale cascade;
truncate table public.opu_operatore,public.opu_indirizzo,public.opu_soggetto_fisico,public.apicoltura_apiari,
				public.opu_stabilimento,public.opu_rel_operatore_soggetto_fisico cascade;
truncate table gds_types.nature_opera cascade;
truncate table gds_notifiche.notifiche,gds_notifiche.cantiere_persona_ruoli,gds_notifiche.imprese, gds_notifiche.cantieri,
               gds_notifiche.cantiere_imprese cascade;
              
delete from gds.verbale_valori;
delete from gds.verbali;

insert into gds_types.nature_opera select id,natura_opera from gesdasic2021.can_nature_opera ;
 n:=setval('gds_types.nature_opera_id_seq', (SELECT MAX(id) + 1 FROM gds_types.nature_opera)); 

CREATE OR REPLACE VIEW gesdasic2021.vw_imprese_valide
AS SELECT a.ragione_sociale,
    a.id,

    a.partita_iva::text ||
        CASE
            WHEN b.cnt <= 1 THEN ''::text
            ELSE '-'::text || "left"(a.ragione_sociale, 1)
        END AS partita_iva,    a.codice_fiscale
   FROM ( SELECT replace(upper(replace(replace(i.ragione_sociale::text, ' '::text, ''::text), '.'::text, ''::text)), 'EDILUXDIGILGIOFRANCACLAUDIA'::text, 'EDILUX'::text) AS ragione_sociale,
                CASE
                    WHEN i.partita_iva IS NULL OR i.partita_iva::text = ''::text THEN i.codice_fiscale
                    ELSE i.partita_iva
                END AS partita_iva,
            min(i.id) AS id,min(i.codice_fiscale) as codice_fiscale
           FROM gesdasic2021.impresa_cantiere ic
             JOIN gesdasic2021.imprese i ON i.id = ic.idimpresa
          GROUP BY (replace(upper(replace(replace(i.ragione_sociale::text, ' '::text, ''::text), '.'::text, ''::text)), 'EDILUXDIGILGIOFRANCACLAUDIA'::text, 'EDILUX'::text)), (
                CASE
                    WHEN i.partita_iva IS NULL OR i.partita_iva::text = ''::text THEN i.codice_fiscale
                    ELSE i.partita_iva
                END)) a
     JOIN ( SELECT a_1.partita_iva,
            min(a_1.ragione_sociale) AS min,
            max(a_1.ragione_sociale) AS max,
            min(a_1.id) AS min,
            count(*) AS cnt
           FROM ( SELECT replace(upper(replace(replace(i.ragione_sociale::text, ' '::text, ''::text), '.'::text, ''::text)), 'EDILUXDIGILGIOFRANCACLAUDIA'::text, 'EDILUX'::text) AS ragione_sociale,
                        CASE
                            WHEN i.partita_iva IS NULL OR i.partita_iva::text = ''::text THEN i.codice_fiscale
                            ELSE i.partita_iva
                        END AS partita_iva,
                    min(i.id) AS id
                   FROM gesdasic2021.impresa_cantiere ic
                     JOIN gesdasic2021.imprese i ON i.id = ic.idimpresa
                  GROUP BY (replace(upper(replace(replace(i.ragione_sociale::text, ' '::text, ''::text), '.'::text, ''::text)), 'EDILUXDIGILGIOFRANCACLAUDIA'::text, 'EDILUX'::text)), (
                        CASE
                            WHEN i.partita_iva IS NULL OR i.partita_iva::text = ''::text THEN i.codice_fiscale
                            ELSE i.partita_iva
                        END)) a_1
          GROUP BY a_1.partita_iva) b(partita_iva, min, max, min_1, cnt) ON a.partita_iva::text = b.partita_iva::text;



create or replace view gesdasic2021.vw_persone_valide as
select upper(codicefiscale)codicefiscale,max(upper(nome)) nome,max(upper(cognome)) cognome ,max(id) id from gesdasic2021.persone group by 1;

create or replace view gesdasic2021.vw_persone_valide_all as
					select codicefiscale ,
					case when codicefiscale='MNCGNR87D29E791J' then 'GENNARO' 
					else  replace(replace(replace(replace(replace(replace(replace(trim(upper(nome)) ,'ANTONO','ANTONIO'),'FELIE','FELICE'),'CRISTIANO CERA','CRISTIANO'),'ALESSANDRA CARBONE','ALESSANDRA'),
					'PAOLO GERVASIO','PAOLO'),'MICHELE LUIGI SANTORO','MICHELE'),'SARCHI LORENZO','LORENZO') end nome,
					case  when codicefiscale='CMPMLE77H21E472E' then 'CIAMPA' else 
					replace(replace(replace(trim(upper(cognome)),'’',''''),'\',''),'SARCHI LORENZO','SARCHI') end cognome
					
					from gesdasic2021.vw_persone_valide vpv -- 287
					union
										SELECT distinct  trim(upper(u.user_login)),
										case when u.user_login='GRNFNC50C12F839V' then 'FRANCESCO' 
										     when u.user_login='PCUMSM71R03F839I' then 'MASSIMO'
										     when u.user_login='BRRNLC85M48C722F' then 'ANGELA CARLA'
										else replace(trim(upper(n.meta_value)) ,'FRANCESCOFRANCESCO','FRANCESCO') end,
										case when u.user_login='GRNFNC50C12F839V' then 'GRANDE'
											 when u.user_login='PCUMSM71R03F839I' then 'PUCA'
											 when u.user_login='BRRNLC85M48C722F' then 'BORRELLI'
										else trim(upper(c.meta_value) ) end
                    FROM gesdasic2021.cantieri cantieri
                    LEFT JOIN gesdasic2021.wp_users u    ON u.ID=cantieri.operatore_inserimento
					LEFT JOIN gesdasic2021.wp_usermeta n ON u.ID=n.user_id and n.meta_key ='first_name'
					LEFT JOIN gesdasic2021.wp_usermeta c ON u.ID=c.user_id and c.meta_key ='last_name';

				
insert into  public.opu_indirizzo  (id,via,civico,cap,comune)
select p.id,p.indirizzo ,p.numerocivico ,p.cap,c.id from gesdasic2021.persone p join gesdasic2021.vw_persone_valide pv on p.id=pv.id 
join public.comuni1 c on p.comune = upper(c.nome) ;


  
insert into public.opu_soggetto_fisico   (id,nome,cognome,codice_fiscale,telefono,fax,email,indirizzo_id)
select p.id,p.nome,p.cognome,p.codicefiscale,telefono,fax,email,i.id
from gesdasic2021.persone p join gesdasic2021.vw_persone_valide pv on p.id=pv.id 
left join public.opu_indirizzo i on p.id=i.id;




insert into public.opu_soggetto_fisico   (id,nome,cognome,codice_fiscale,telefono,fax,email,indirizzo_id)
select row_number() over() +1000,pv.nome,pv.cognome,pv.codicefiscale,null,null,null,null
from gesdasic2021.vw_persone_valide_all pv where pv.codicefiscale not in (select pf.codice_fiscale from public.opu_soggetto_fisico pf); 

 
 
insert into public.opu_indirizzo  (id,via,civico,cap,comune)
select ca.id+1000,ca.indirizzo ,ca.numero_civico ,case when  ca.cap='80100' then null else ca.cap end cap,c.id from gesdasic2021.cantieri ca
join public.comuni1 c on ca.codiceistat  = upper(c.istat);

 

/*insert into gds_notifiche.imprese  (id,ragione_sociale ,partita_iva,codice_fiscale)
select i.id,ragione_sociale,left(trim(partita_iva),11),codice_fiscale  from gesdasic2021.imprese i;  */

insert into gds_notifiche.imprese  (id,ragione_sociale ,partita_iva,codice_fiscale)
select id,ragione_sociale,partita_iva ,codice_fiscale  from gesdasic2021.vw_imprese_valide;


 n:=setval('gds_notifiche.imprese_id_seq', (SELECT MAX(id) + 1 FROM gds_notifiche.imprese));


insert into gds_notifiche.notifiche
select distinct a.id,pv.id,a.dt,b.id_stato from 
(

select     c.id,
s.id op,
case when length(data_notifica) = 0 then null else replace(data_notifica,'0000-00-00 00:00:00','2000-01-01 00:00:00')::timestamp end dt
 from gesdasic2021.cantieri c              
LEFT JOIN gesdasic2021.wp_users u    ON u.ID=c.operatore_inserimento
left join public.opu_soggetto_fisico s on s.codice_fiscale=u.user_login   
 
 
 ) a 
 left join gesdasic2021.persone p on a.op=p.id 
 left join gesdasic2021.vw_persone_valide pv on upper(p.codicefiscale) =upper(pv.codicefiscale ) 
 join 
(select s.id_cantiere , s.id_stato  from gesdasic2021.stato_cantieri s  join
(select id_cantiere ,max(data_modifica) data_modifica from gesdasic2021.stato_cantieri sc group by 1) a
on s.id_cantiere =a.id_cantiere and s.data_modifica =a.data_modifica) b on b.id_cantiere = a.id;
 n:=setval('gds_notifiche.notifiche_id_seq', (SELECT MAX(id) + 1 FROM gds_notifiche.notifiche));


insert into gds_notifiche.cantieri
select     c.id,idnaturaopera,c.id,denominazione,
(data_presunta||' 00:00:00')::timestamp,durata_presunta,numero_imprese,numero_lavoratori,ammontare,null,c.id+1000
from gesdasic2021.cantieri c;
--left join public.opu_indirizzo i on c.id+1000=i.id;
 n:=setval('gds_notifiche.cantieri_id_seq', (SELECT MAX(id) + 1 FROM gds_notifiche.cantieri));

/*insert into gds_notifiche.cantiere_imprese  select idcantiere,idimpresa,row_number() over() from gesdasic2021.impresa_cantiere ;*/


insert into gds_notifiche.cantiere_imprese 
select idcantiere ,idimpresa ,row_number() over() from gesdasic2021.impresa_cantiere ic join gesdasic2021.imprese i on i.id= ic.idimpresa
join gesdasic2021.vw_imprese_valide iv on case when i.partita_iva is null or i.partita_iva ='' then i.codice_fiscale else i.partita_iva end  = iv.partita_iva or
case when i.partita_iva is null or i.partita_iva ='' then i.codice_fiscale else i.partita_iva end ||'-'|| upper(left(i.ragione_sociale,1)) =iv.partita_iva;
 n:=setval('gds_notifiche.cantiere_imprese_id_seq', (SELECT MAX(id) + 1 FROM gds_notifiche.cantiere_imprese));

insert into gds_notifiche.cantiere_persona_ruoli (id_cantiere ,id_soggetto_fisico ,id_ruolo, id )
select idcantiere,idpersona ,idruolo, row_number() over()  from gesdasic2021.persona_ruolo_cantiere
join gesdasic2021.vw_persone_valide pv on idpersona=pv.id;
 n:=setval('gds_notifiche.cantiere_persona_ruoli_id_seq', (SELECT MAX(id) + 1 FROM gds_notifiche.cantiere_persona_ruoli));




/*INSERT INTO gds.verbali VALUES (1, 1, '0022-01-01 00:00:00');

INSERT INTO gds.verbale_valori VALUES (2, 1, 1112, '77788');
INSERT INTO gds.verbale_valori VALUES (3, 1, 1113, 'ISPETTORE');

n:=setval('gds.verbale_valori_id_seq', (SELECT MAX(id) + 1 FROM gds.verbale_valori));*/
truncate table gds_types. tipi_macchina ,gds_macchine.costruttori, gds_macchine.macchine;
insert into gds_types. tipi_macchina 
select row_number () over (),tipologia from (
select distinct tipologia from  gesdasic2021.mac_macchine mm) a;
 n:=setval('gds_types.tipi_macchina_id_seq', (SELECT MAX(id) + 1 FROM gds_types.tipi_macchina));

insert into gds_macchine.costruttori 
select row_number () over (),costruttore from (
select distinct costruttore from  gesdasic2021.mac_macchine mm) a;
n:=setval('gds_macchine.costruttori_id_seq', (SELECT MAX(id) + 1 FROM gds_macchine.costruttori));


insert into gds_macchine.macchine
select row_number () over (),* from (
select tm.id,c.id,modello,max(m.data_modifica::timestamp), min(m.data_inserimento::timestamp)
from gesdasic2021.mac_macchine m join gds_types.tipi_macchina tm on tm.descr =m.tipologia join gds_macchine.costruttori c on c.descr =m.costruttore 
group by 1,2,3) a;
n:=setval('gds_macchine.macchine_id_seq', (SELECT MAX(id) + 1 FROM gds_macchine.macchine));

	RETURN 'OK';
   end;
END
$$;


ALTER FUNCTION gds_notifiche.load_tables() OWNER TO postgres;

--
-- Name: load_tables_types(); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.load_tables_types() RETURNS text
    LANGUAGE plpgsql
    AS $$
	begin
		  declare n integer;
      begin 
	      
    	INSERT INTO gds_types.ruoli values (1,'Committente',false);
        INSERT INTO gds_types.ruoli values (2,'Responsabile dei lavori',true);
        INSERT INTO gds_types.ruoli values (3,'Coordinatore per la sicurezza durante la progettazione',false);
        INSERT INTO gds_types.ruoli values (4,'Coordinatore per la sicurezza durante la realizzazione',false);
        n:=setval('gds_types.ruoli_id_seq', (SELECT MAX(id) + 1 FROM gds_types.ruoli));


        INSERT INTO gds_types.stati values (1,'BOZZA',true);
		INSERT INTO gds_types.stati values (2,'SALVATA - INOLTRATA - ATTIVA',false);
		INSERT INTO gds_types.stati values (3,'ELIMINATA',true);
		INSERT INTO gds_types.stati values (4,'SALVATA - INOLTRATA - DISATTIVA',false);
		INSERT INTO gds_types.stati values (5,'BOZZA-MODIFICA',true);
		n:=setval('gds_types.stati_id_seq', (SELECT MAX(id) + 1 FROM gds_types.stati));

		delete from gds_types.stati_successivi;
		insert into gds_types.stati_successivi values(1,1,1);
		insert into gds_types.stati_successivi values(2,1,2);
		insert into gds_types.stati_successivi values(3,1,3);
		insert into gds_types.stati_successivi values(4,2,4);
		insert into gds_types.stati_successivi values(6,2,5);
		insert into gds_types.stati_successivi values(7,5,2);
		insert into gds_types.stati_successivi values(8,5,3);
		insert into gds_types.stati_successivi values(9,5,5);
		 n:=setval('gds_types.stati_successivi_id_seq', (SELECT MAX(id) + 1 FROM gds_types.stati_successivi));
		
		INSERT INTO gds_ui.stati_ui VALUES (1, 'Salva bozza', '#fae852');
		INSERT INTO gds_ui.stati_ui VALUES (2, 'Salva e invia', '#b3fd9e');
		INSERT INTO gds_ui.stati_ui VALUES (3, 'Elimina', '#ff8a8a');
		INSERT INTO gds_ui.stati_ui VALUES (4, 'Disattiva', '#ff8a8a' );
		INSERT INTO gds_ui.stati_ui VALUES (5, 'Integra/modifica', '#fae852');
		n:=setval('gds_ui.stati_ui_id_seq', (SELECT MAX(id) + 1 FROM gds_ui.stati_ui));
	
	
	insert into gds_types.fasi values (1,'PRIMO SOPRALLUOGO',null);
insert into gds_types.fasi values (2,'ALTRO',1);
insert into gds_types.fasi values (3,'SOPRALLUOGO SUCCESSIVO AL PRIMO',1);
insert into gds_types.fasi values (4,'ATTIVITÀ CONSEGUENTI LA PRESCRIZIONE',1);
insert into gds_types.fasi values (5,'ATTIVITÀ CONSEGUENTI L''ACCERTAMENTO',4);
insert into gds_types.fasi values (6,'DISSEQUESTRO PER LAVORI',1);
insert into gds_types.fasi values (7,'ATTIVITÀ CONSEGUENTI LA SOSPENSIONE',1);
insert into gds_types.fasi values (8,'ALTRI ATTI DA INDAGINE',1);
insert into gds_types.fasi values (9,'RICHIESTA DOCUMENTAZIONE',1);

insert into gds_types.motivi_isp values (1,'VIGILANZA SU PROGRAMMA', '(Pianificazione di servizio)' );
insert into gds_types.motivi_isp values (2,'VIGILANZA SU RICHIESTA', '(Esposto, Sindacati, Carabinieri, Magistratura)' );
insert into gds_types.motivi_isp values (3,'VIGILANZA DI INIZIATIVA','(Attività non legata a progetti di vigilanza)' );
insert into gds_types.motivi_isp values (4,'INDAGINE INFORTUNIO','' );
insert into gds_types.motivi_isp values (5,'INDAGINE MALATTIA PROFESSIONALE','' );
insert into gds_types.motivi_isp values (6,'EMISSIONE PARERE','' );

insert into gds_types.stati_notifica values(1,'IN CORSO',true);
insert into gds_types.stati_notifica values(2,'CHIUSA',false);
insert into gds_types.stati_notifica values(3,'TRASFERITA',false);

insert into gds_types.stati_notifica_successivi values(1,1,2);
insert into gds_types.stati_notifica_successivi values(2,1,3);

INSERT INTO gds_ui.stati_ui (id,label_bottone,colore,msg,ordine) VALUES
	 (1,'SALVA','#fae852',NULL,1),
	 (3,'TERMINA','#ff8a8a',NULL,2),
	 (4,'TRASFERISCI','#ff8a8a',NULL,3);
	
	
	
	
insert into gds_types.enti values 
(1,	'ARPAC'),
(2,	'DPL'),
(3,	'INAIL'),
(4,	'ASL Napoli 1 Centro'),
(5,	'ASL Napoli 2 Nord'),
(6,	'ASL Napoli 3 Sud'),
(7,	'ASL Avellino'),
(8,	'ASL Caserta'),
(9,	'ASL Benevento'),
(10,'ASL Salerno'),
(11,	'VV.F.'),
(12,	'VV.UU.');

insert into gds_types.uo values
(0	,'Altro'),
(1	,'Sede Regionale'),
(2	,'Sede Provinciale di Napoli'),
(3	,'Sede Provinciale di Avellino'),
(4	,'Sede Provinciale di Caserta'),
(5	,'Sede Provinciale di Benevento'),
(6	,'Sede Provinciale di Salerno'),
(7	,'Sede di Roma'),
(8	,'Sede di Napoli'),
(9	,'Sede di Avellino'),
(10	,'Servizio SPSAL'),
(11	,'Servizio SIMDL'),
(12	,'Unità Operativa'),
(13	,'Comando Regionale'),
(14	,'Comando di Napoli'),
(15	,'Comando di Caserta'),
(16	,'Comando di Benevento'),
(17	,'Comando di Avellino'),
(18	,'Comando di Salerno'),
(19	,'Comando Comunale');

insert into gds_types.ente_uo values
(1,	1,	0),
(2,	1,	1),
(3,	1,	2),
(4,	1,	3),
(5,	1,	4),
(6,	1,	5),
(7,	1,	6),
(8,	2,	0),
(9,	2,	1),
(10,	2,	2),
(11,	2,	3),
(12,	2,	4),
(13,	2,	5),
(14,	2,	6),
(15,	3,	0),
(16,	3,	1),
(17,	3,	2),
(18,	3,	3),
(19,	3,	4),
(20,	3,	5),
(21,	3,	6),
(22,	4,	10),
(23,	4,	11),
(24,	4,	12),
(25,	5,	10),
(26,	5,	11),
(27,	5,	12),
(28,	6,	10),
(29,	6,	11),
(30,	6,	12),
(31,	7,	10),
(32,	7,	11),
(33,	7,	12),
(34,	8,	10),
(35,	8,	11),
(36,	8,	12),
(37,	9,	10),
(38,	9,	11),
(39,	9,	12),
(40,	10,	10),
(41,	10,	11),
(42,	10,	12),
(43,	11,	13),
(44,	11,	14),
(45,	11,	15),
(46,	11,	16),
(47,	11,	17),
(48,	11,	18),
(49,	12,	19);

insert into gds_types.fase_esiti values 
(0	,'ALTRO','---'),
(1	,'---','---'),
(2	,'POSITIVO','art. 13 d.lgs. 81/2008 e s.m.i'),
(3	,'POSITIVO','art. 301 bis d.lgs. 81/2008 e s.m.i.'),
(4	,'NEGATIVO','art. 301 bis d.lgs. 81/2008 e s.m.i.'),
(5	,'L''INFRAZIONE NON COSTITUISCE REATO','art. 302 bis d.lgs. 81/2008 e s.m.i.'),
(6	,'SOSPENSIONE DELL''ATTIVITÀ IMPRENDITORIALE','art. 14 bis d.lgs. 81/2008 e s.m.i.'),
(7	,'PRESCRIZIONE','d.lgs. 758/1994 e s.m.i.'),
(8	,'SEQUESTRO','art. 321 del c.p.p'),
(9	,'L''INFRAZIONE NON COSTITUISCE REATO (BIS)','art. 302 bis d.lgs. 81/2008 e s.m.i.'),
(10	,'COMUNICAZIONE AL MAGISTRATO','d.lgs. 758/1994'),
(11	,'PROROGA ACCORDATA','d.lgs. 758/1994'),
(12	,'PROROGA RESPINTA','d.lgs. 758/1994'),
(13	,'NEGATIVO','d.lgs. 758/1994'),
(14	,'PAGAMENTO EFFETTUATO','d.lgs. 758/1994'),
(15	,'MANCATO ADEMPIMENTO ALLE PRESCRIZIONI	d.lgs.','758/1994'),
(17	,'DISSEQUESTRO DEFINITIVO','C.p.p.'),
(18	,'DISSEQUESTRO TEMPORANEO','85 disp. att. c.p.p.'),
(19	,'COMUNICAZIONE AD AUTORITÀ COMPETENTI','art. 14 d.lgs. 81/2008 e s.m.i.'),
(20	,'REVOCA DELLA SOSPENSIONE','art. 14 d.lgs. 81/2008 e s.m.i.'),
(21	,'COMUNICAZIONI ALL''AG','c.p.p.'),
(22	,'RICHIESTA DOCUMENTAZIONE','---'),
(23	,'ACCERTAMENTO',''),
(25	,'SOSPENSIONE DELL''ATTIVITÀ IMPRENDITORIALE (CANTIERE)','art. 14 bis d.lgs. 81/2008 e s.m.i.');



insert into gds_types.natura_giuridica values 
(nextval('gds_types.natura_giuridica_id_seq'),'01','SAA,','Società in accomandita per azioni'),
(nextval('gds_types.natura_giuridica_id_seq'),'02','SRL','Società a responsabilità limitata'),
(nextval('gds_types.natura_giuridica_id_seq'),'03','SPA','Società per azioni'),
(nextval('gds_types.natura_giuridica_id_seq'),'04','SCA','Società cooperative e loro consorzi iscritti nei registri prefettizi e nello schedario generale della cooperazione'),
(nextval('gds_types.natura_giuridica_id_seq'),'05','ASC','Altre società cooperative'),
(nextval('gds_types.natura_giuridica_id_seq'),'06','MA','Mutue assicuratrici'),
(nextval('gds_types.natura_giuridica_id_seq'),'07','CPG','Consorzi con personalità giuridica'),
(nextval('gds_types.natura_giuridica_id_seq'),'08','A','Associazioni riconosciute'),
(nextval('gds_types.natura_giuridica_id_seq'),'09','F','Fondazioni'),
(nextval('gds_types.natura_giuridica_id_seq'),'10','AEI','Altri enti ed istituti con personalità giuridica'),
(nextval('gds_types.natura_giuridica_id_seq'),'11','CSPG','Consorzi senza personalità giuridica'),
(nextval('gds_types.natura_giuridica_id_seq'),'12','ANR','Associazioni non riconosciute e comitati'),
(nextval('gds_types.natura_giuridica_id_seq'),'13','AOP','Altre organizzazioni di persone o di beni senza personalità giuridica'),
(nextval('gds_types.natura_giuridica_id_seq'),'14','EN','Enti pubblici economici'),
(nextval('gds_types.natura_giuridica_id_seq'),'15','ET','Enti pubblici non economici'),
(nextval('gds_types.natura_giuridica_id_seq'),'16','CMF', 'Casse mutue e fondi di previdenza, assistenza, pensioni o simili con o senza personalità'),
(nextval('gds_types.natura_giuridica_id_seq'),'17','OPM', 'Opere pie e società di mutuo soccorso'),
(nextval('gds_types.natura_giuridica_id_seq'),'18','EO', 'Enti ospedalieri'),
(nextval('gds_types.natura_giuridica_id_seq'),'19','IPA', 'Enti ed istituti di previdenza e di assistenza sociale'),
(nextval('gds_types.natura_giuridica_id_seq'),'20','ASF', 'Aziende autonome di cura, soggiorno e turismo'),
(nextval('gds_types.natura_giuridica_id_seq'),'21','ARPC', 'Aziende regionali, provinciali, comunali e loro consorzi'),
(nextval('gds_types.natura_giuridica_id_seq'),'22','S','Società, organizzazioni ed enti costituiti all''estero non altrimenti classificabili con sede'),
(nextval('gds_types.natura_giuridica_id_seq'),'23','SCR', 'Società semplici, irregolari e di fatto'),
(nextval('gds_types.natura_giuridica_id_seq'),'24','SNC', 'Società in nome collettivo'),
(nextval('gds_types.natura_giuridica_id_seq'),'25','SAS', 'Società in accomandita semplice'),
(nextval('gds_types.natura_giuridica_id_seq'),'26','SDA', 'Società di armamento'),
(nextval('gds_types.natura_giuridica_id_seq'),'27','AAP', 'Associazioni tra artisti e professionisti'),
(nextval('gds_types.natura_giuridica_id_seq'),'28','ACG', 'Aziende coniugali gestite in forma di società'),
(nextval('gds_types.natura_giuridica_id_seq'),'29','GEIE', 'GEIE - Gruppi europei di interesse economico'),
(nextval('gds_types.natura_giuridica_id_seq'),'30', 'NSDF', 'Soggetti non residenti - Società semplici, irregolari e di fatto'),
(nextval('gds_types.natura_giuridica_id_seq'),'31', 'NSNC', 'Soggetti non residenti - Società in nome collettivo'),
(nextval('gds_types.natura_giuridica_id_seq'),'32', 'NSAS', 'Soggetti non residenti - Società in accomandita semplice'),
(nextval('gds_types.natura_giuridica_id_seq'),'33', 'NSA', 'Soggetti non residenti - Società di armamento'),
(nextval('gds_types.natura_giuridica_id_seq'),'34', 'NAAP', 'Soggetti non residenti - Associazione tra professionisti'),
(nextval('gds_types.natura_giuridica_id_seq'),'35', 'NSAA', 'Soggetti non residenti - Società in accomandita per azioni'),
(nextval('gds_types.natura_giuridica_id_seq'),'36', 'NSRL', 'Soggetti non residenti - Società a responsabilità limitata'),
(nextval('gds_types.natura_giuridica_id_seq'),'37', 'NSPA', 'Soggetti non residenti - Società per azioni'),
(nextval('gds_types.natura_giuridica_id_seq'),'38', 'NC', 'Soggetti non residenti - Consorzi'),
(nextval('gds_types.natura_giuridica_id_seq'),'39', 'NAEI', 'Soggetti non residenti - Altri enti ed istituti'),
(nextval('gds_types.natura_giuridica_id_seq'),'40', 'NADF', 'Soggetti non residenti - Associazioni riconosciute e non riconoscite e di fatto'),
(nextval('gds_types.natura_giuridica_id_seq'),'41', 'NF', 'Soggetti non residenti - Fondazioni'),
(nextval('gds_types.natura_giuridica_id_seq'),'42', 'NOPM', 'Soggetti non residenti - Opere pie e società di mutuo soccorso'),
(nextval('gds_types.natura_giuridica_id_seq'),'43', 'NAAP', 'Soggetti non residenti - Altre organizzazioni di persone o di beni'),
(nextval('gds_types.natura_giuridica_id_seq'),'50', 'SAA' ,'Società per azioni, aziende speciali e consorzi di cui agli artt. 23, 25, 60 della L. 142/90'),
(nextval('gds_types.natura_giuridica_id_seq'),'51', 'CON' ,'Condomini'),
(nextval('gds_types.natura_giuridica_id_seq'),'52', 'I' ,'Imprese individuali artigiane'),
(nextval('gds_types.natura_giuridica_id_seq'),'53', 'IIC' ,'Imprese individuali commerciali'),
(nextval('gds_types.natura_giuridica_id_seq'),'54', 'EC' ,'Enti ecclesiastici'),
(nextval('gds_types.natura_giuridica_id_seq'),'55', 'LA' ,'Lavoratori autonomi - Professionisti'),
(nextval('gds_types.natura_giuridica_id_seq'),'56', 'ALA' ,'Altri lavoratori autonomi'),
(nextval('gds_types.natura_giuridica_id_seq'),'90', 'OAS' ,'Organi ed amministrazioni dello stato, comuni, province, regioni, consorzi tra enti locali, gestori di demani collettivi, comunità montane')
;


delete from gds_types.tipi_sede;
n:=setval('gds_types.tipi_sede_id_seq', 1);
insert into gds_types.tipi_sede values
                   ( nextval('gds_types.tipi_sede_id_seq'), 0  , 'Sede legale'),
                   ( nextval('gds_types.tipi_sede_id_seq'), 1  , 'Filiale'),
                   ( nextval('gds_types.tipi_sede_id_seq'),  2  , 'Succursale'),
                   ( nextval('gds_types.tipi_sede_id_seq'),  3  , 'Magazzino'),
                   ( nextval('gds_types.tipi_sede_id_seq'),  4  , 'Stabilimento'),
                   ( nextval('gds_types.tipi_sede_id_seq'), 5  , 'Ufficio'),
                   ( nextval('gds_types.tipi_sede_id_seq'),  6  , 'Negozio'),
                   ( nextval('gds_types.tipi_sede_id_seq'), 7  , 'Deposito'),
                   ( nextval('gds_types.tipi_sede_id_seq'), 8  , 'Laboratorio'),
                   ( nextval('gds_types.tipi_sede_id_seq'), 9  , 'Altri');

	RETURN 'OK';
        end;
	END;
$$;


ALTER FUNCTION gds_notifiche.load_tables_types() OWNER TO postgres;

--
-- Name: prova(); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.prova() RETURNS bigint
    LANGUAGE plpgsql
    AS $$
begin
	declare R gds_types.stati%ROWTYPE;
n int8;
	begin
	n:=json_populate_record(R, '{"id":1,"descr":2}');
	return R.id;
	end;
END
$$;


ALTER FUNCTION gds_notifiche.prova() OWNER TO postgres;

--
-- Name: upd_cantiere(character varying, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.upd_cantiere(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare 
	
            ret gds_types.result_type; -- START
		    id_op bigint;   
		    proc_name varchar; -- END
	        R_cantieri gds_notifiche.vw_cantieri%ROWTYPE;
            R_cantieri_old gds_notifiche.vw_cantieri%ROWTYPE;
            R_cantieri_new gds_notifiche.cantieri%ROWTYPE;
            n bigint;
           
	begin
		
		proc_name:='gds_notifiche.upd_cantiere';
	    --id_op:=gds_log.start_op(proc_name,idtransazione ,v);
        R_cantieri:=  (json_populate_record(null::gds_notifiche.vw_cantieri,v::json));
        R_cantieri.id:=R_cantieri.id_cantiere;
        R_cantieri.denominazione := upper(trim(R_cantieri.denominazione));
   		R_cantieri.civico        := upper(trim(R_cantieri.civico));
   		R_cantieri.via           := upper(trim(R_cantieri.via));
   		R_cantieri.comune        := upper(trim(R_cantieri.comune));
   		R_cantieri.altro         := upper(trim(R_cantieri.altro));
   	   	R_cantieri.natura_opera         := upper(trim(R_cantieri.natura_opera));
   		if R_cantieri.denominazione = '' then R_cantieri.denominazione := null; end if;
   		if R_cantieri.via =           '' then R_cantieri.via :=           null; end if;
   		if R_cantieri.comune =        '' then R_cantieri.comune :=        null; end if;
  		if R_cantieri.civico =        '' then R_cantieri.cicivo :=        null; end if;
 
        select * into R_cantieri_old from gds_notifiche.vw_cantieri where id = R_cantieri.id;
        if R_cantieri.numero_lavoratori is null or R_cantieri.numero_lavoratori < 3 then
        	ret.esito := false;
			ret:=gds_ui.build_ret(ret,proc_name,-2);
		    return ret;
		end if;
		ret:= gds_notifiche.upd_indirizzo(v,idtransazione);
        raise notice 'N:= gds_notifiche.upd_indirizzo(v) =%', ret;
   		if ret.esito = false then
			ret:=gds_ui.build_ret(ret,proc_name,-1);
			--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
		    return ret;
   		      
   		end if;
   		select count(*) into n from gds_notifiche.vw_cantieri c where
   			    (id != R_cantieri.id or R_cantieri.id is null)
   			and denominazione=R_cantieri.denominazione
   			and civico=R_cantieri.civico
   			and via =R_cantieri.via
   			and comune = R_cantieri.comune
   			and cap = R_cantieri.cap
   			and natura_opera  =R_cantieri.natura_opera
   			and data_presunta  = R_cantieri.data_presunta 
   			and id_notifica_succ is null and attivo=true;
  raise notice 'CANTIERE >%< >%< >%< >%< N=%', 	R_cantieri.denominazione,		R_cantieri.civico,R_cantieri.via,R_cantieri.comune,n;
   	    if n>0  then
   	    	ret.msg:='COUNT = '||n;
   	        ret.esito := false;
			ret:=gds_ui.build_ret(ret,proc_name,-3);
		    return ret;
		end if;
   
   		R_cantieri.id_indirizzo:=ret.valore;
    	IF R_cantieri.id_natura_opera != R_cantieri_old.id_natura_opera or (R_cantieri.id_natura_opera is not null and R_cantieri_old.id_natura_opera is  null)or (R_cantieri.id_natura_opera is null and R_cantieri_old.id_natura_opera is not null)
    	    or R_cantieri.id_notifica != R_cantieri_old.id_notifica or (R_cantieri.id_notifica is not null and R_cantieri_old.id_notifica is  null) or (R_cantieri.id_notifica is null and R_cantieri_old.id_notifica is not null)
    	    or R_cantieri.denominazione != R_cantieri_old.denominazione or (R_cantieri.denominazione is not null and R_cantieri_old.denominazione is  null) or (R_cantieri.denominazione is null and R_cantieri_old.denominazione is not null)
       		or R_cantieri.data_presunta != R_cantieri_old.data_presunta or (R_cantieri.data_presunta is not null and R_cantieri_old.data_presunta is  null)or (R_cantieri.data_presunta is null and R_cantieri_old.data_presunta is not null)
       		or R_cantieri.durata_presunta != R_cantieri_old.durata_presunta or (R_cantieri.durata_presunta is not null and R_cantieri_old.durata_presunta is  null) or (R_cantieri.durata_presunta is null and R_cantieri_old.durata_presunta is not null)
       		or R_cantieri.numero_imprese != R_cantieri_old.numero_imprese or (R_cantieri.numero_imprese is not null and R_cantieri_old.numero_imprese is  null)or (R_cantieri.numero_imprese is null and R_cantieri_old.numero_imprese is not null)
       		or R_cantieri.numero_lavoratori != R_cantieri_old.numero_lavoratori  or (R_cantieri.numero_lavoratori is not null and R_cantieri_old.numero_lavoratori is  null) or (R_cantieri.numero_lavoratori is null and R_cantieri_old.numero_lavoratori is not null)
       		or R_cantieri.ammontare != R_cantieri_old.ammontare or (R_cantieri.ammontare is not null and R_cantieri_old.ammontare is  null)or (R_cantieri.ammontare is null and R_cantieri_old.ammontare is not null)
       		or R_cantieri.altro != R_cantieri_old.altro or (R_cantieri.altro is not null and R_cantieri_old.altro is  null)or (R_cantieri.altro is null and R_cantieri_old.altro is not null)
       		or R_cantieri.id_indirizzo != R_cantieri_old.id_indirizzo or (R_cantieri.id_indirizzo is not null and R_cantieri_old.id_indirizzo is  null) or (R_cantieri.id_indirizzo is null and R_cantieri_old.id_indirizzo is not null) then
       		raise notice 'sono nell update';
       		raise notice 'sono nell update ID=%',R_cantieri.id;
       		raise notice 'sono nell update denominazione=%',R_cantieri.denominazione;
        	update gds_notifiche.cantieri set (        id_natura_opera,           id_notifica,           denominazione,           data_presunta,
        										       durata_presunta,          numero_imprese,
       								                   numero_lavoratori,         ammontare,             altro,                  id_indirizzo) =
       									    (R_cantieri.id_natura_opera,R_cantieri.id_notifica,R_cantieri.denominazione,R_cantieri.data_presunta,
       										R_cantieri.durata_presunta,R_cantieri.numero_imprese,
       								        R_cantieri.numero_lavoratori,R_cantieri.ammontare,R_cantieri.altro,        R_cantieri.id_indirizzo)
       		where id = R_cantieri.id;
       		select * into R_cantieri_new from gds_notifiche.cantieri c where id = R_cantieri.id;
       		n:=gds_log.upd_record('gds_notifiche.cantieri',idtransazione,R_cantieri_new,'U');
       		--update      gds_notifiche."_h_cantieri" set validita=tsrange(lower(validita),clock_timestamp()::timestamp,'[)') where id_cantiere=R_cantieri.id and upper_inf(validita);
       	    --insert into gds_notifiche."_h_cantieri" values (nextval('gds_notifiche."_h_cantieri_id_seq"'), R_cantieri.*,tsrange(clock_timestamp()::timestamp,'infinity','[)'),idtransazione,clock_timestamp(),current_timestamp);

       								
    	end if;
  
	    ret.esito:=true;
	   	ret.valore:=R_cantieri.id;
		ret:=gds_ui.build_ret(ret,proc_name,0);
		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
		return ret;
	end;
END
$$;


ALTER FUNCTION gds_notifiche.upd_cantiere(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_cantiere_imprese(character varying, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.upd_cantiere_imprese(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare 
             ret gds_types.result_type; -- START
		    id_op bigint;   
		    proc_name varchar; -- END
	        R_cantiere_imprese gds_notifiche.vw_cantiere_imprese%ROWTYPE;
            R_cantiere_imprese_old gds_notifiche.cantiere_imprese%ROWTYPE;
	        R_cantiere_imprese_prev gds_notifiche.vw_cantiere_imprese%ROWTYPE;
            R_impresa gds_notifiche.imprese%ROWTYPE;
            n bigint;
            --n_imprese integer;
            R_CI record;
            list bigint [];
	begin	
		proc_name:='gds_notifiche.upd_cantiere_imprese';
	    --id_op:=gds_log.start_op(proc_name,idtransazione ,v);
		raise notice 'gds_notifiche.upd_cantiere_imprese solo % ' ,v;
		select array_append(list,-1) into list;
		--n_imprese := null;
		for R_cantiere_imprese in
			select * from json_populate_recordset(null::gds_notifiche.vw_cantiere_imprese, v::json) loop
			raise notice 'gds_notifiche.upd_cantiere_imprese loop 1 start' ;
			/* verifica che tutte le imprese abbiano lo stesso cantiere */  
			IF n is not null and R_cantiere_imprese.id_cantiere is not null and n != R_cantiere_imprese.id_cantiere then 		
       	    	ret.esito:=false;	
				ret:=gds_ui.build_ret(ret,proc_name,-1);
				--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret));
		    	return ret;         
       		end if;
       	
       		n:=R_cantiere_imprese.id_cantiere;
       		--if n_imprese is null and R_cantiere_imprese.numero_imprese is not null then n_imprese := R_cantiere_imprese.numero_imprese; end if;
       	
       		if R_cantiere_imprese_prev.partita_iva is not null and upper(trim(R_cantiere_imprese.partita_iva)) = upper(trim(R_cantiere_imprese_prev.partita_iva)) then
       			if upper(trim(R_cantiere_imprese.ragione_sociale)) != upper(trim(R_cantiere_imprese_prev.ragione_sociale)) or (R_cantiere_imprese.ragione_sociale is null and R_cantiere_imprese_prev.ragione_sociale is not null) or (R_cantiere_imprese.ragione_sociale is not null and R_cantiere_imprese_prev.ragione_sociale is  null) or
       			   upper(trim(R_cantiere_imprese.codice_fiscale))  != upper(trim(R_cantiere_imprese_prev.codice_fiscale))  or (R_cantiere_imprese.codice_fiscale  is null and R_cantiere_imprese_prev.codice_fiscale  is not null) or (R_cantiere_imprese.codice_fiscale  is not null and R_cantiere_imprese_prev.codice_fiscale  is  null)
       			   then
       			        ret.esito:=false;
       			       	ret.msg:=R_cantiere_imprese.partita_iva;
       					ret:=gds_ui.build_ret(ret,proc_name, -5);
        				--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
        				return ret; 
        		end if;
			end if;
       		R_cantiere_imprese_prev :=R_cantiere_imprese;
	       	raise notice 'gds_notifiche.upd_cantiere_imprese loop 1 end' ;
       	end loop;
        --if n_imprese is null then n_imprese:=0; end if;
        n:=0;
		for R_cantiere_imprese in
			select * from json_populate_recordset(null::gds_notifiche.vw_cantiere_imprese, v::json) loop
			R_impresa.id:=R_cantiere_imprese.id_impresa ;
			R_impresa.partita_iva:=upper(trim(R_cantiere_imprese.partita_iva)) ;
			R_impresa.ragione_sociale:=upper(trim(R_cantiere_imprese.ragione_sociale)) ;
			R_impresa.codice_fiscale:=upper(trim(R_cantiere_imprese.codice_fiscale)) ;
			if  (R_cantiere_imprese.partita_iva     is null or R_cantiere_imprese.partita_iva    ='') and 
				(R_cantiere_imprese.ragione_sociale is null or R_cantiere_imprese.ragione_sociale='') and
				(R_cantiere_imprese.codice_fiscale  is null or R_cantiere_imprese.codice_fiscale ='')
			then
					continue;
			end if;
			n:=n+1;
								
			ret:= gds_notifiche.upd_imprese('{"partita_iva":"'||R_impresa.partita_iva||'","codice_fiscale":"'||coalesce(R_impresa.codice_fiscale,'')||'","ragione_sociale":"'||coalesce(R_impresa.ragione_sociale,'')||'"}',idtransazione);
			IF not ret.esito then   		
       	    	ret.esito:=false;	
				ret:=gds_ui.build_ret(ret,proc_name,-2);
		    	return ret;         
       		end if;	
			IF  ret.valore is null then   		
       	    	ret.esito:=false;	
				ret:=gds_ui.build_ret(ret,proc_name,-3);
		    	return ret;         
       		end if;			
		
			R_cantiere_imprese.id_impresa :=ret.valore;
        	select * into R_cantiere_imprese_old from gds_notifiche.cantiere_imprese where id_cantiere = R_cantiere_imprese.id_cantiere and id_impresa = R_cantiere_imprese.id_impresa;
       		raise notice 'gds_notifiche.upd_cantiere_imprese loop start';
        	IF R_cantiere_imprese_old.id is null then
        		raise notice 'gds_notifiche.upd_cantiere_imprese loop INSERT';
        		R_cantiere_imprese_old.id:=nextval('gds_notifiche.cantiere_imprese_id_seq');
        		R_cantiere_imprese_old.id_cantiere:=R_cantiere_imprese.id_cantiere;
        		R_cantiere_imprese_old.id_impresa :=R_cantiere_imprese.id_impresa;
				insert into gds_notifiche.cantiere_imprese (id,id_cantiere,id_impresa,ordine) values (R_cantiere_imprese_old.id,R_cantiere_imprese_old.id_cantiere,R_cantiere_imprese_old.id_impresa,n);
				n:=gds_log.upd_record('gds_notifiche.cantiere_imprese',idtransazione,R_cantiere_imprese_old,'I');
				--insert into gds_notifiche._h_cantiere_imprese
				--values (nextval('gds_notifiche._h_cantiere_persona_ruoli_id_seq'),currval('gds_notifiche.cantiere_imprese_id_seq'),R_cantiere_imprese.id_cantiere,R_cantiere_imprese.id_impresa,
				--		tsrange(clock_timestamp()::timestamp,'infinity','[)'),idtransazione,clock_timestamp(),current_timestamp);			
				raise notice 'gds_notifiche.upd_cantiere_imprese loop INSERT % % %',currval('gds_notifiche.cantiere_imprese_id_seq'),R_cantiere_imprese.id_cantiere,R_cantiere_imprese.id_impresa;
			/*elsif R_cantiere_imprese.id_impresa != R_cantiere_imprese_old.id_impresa then
				raise notice 'gds_notifiche.upd_cantiere_imprese loop UPDATE';	
				update  gds_notifiche.cantiere_imprese set (id_cantiere,id_impresa)=(R_cantiere_imprese.id_cantiere,R_cantiere_imprese.id_impresa)
				where id= R_cantiere_imprese.id;
			
				update      gds_notifiche."_h_cantiere_imprese" set validita=tsrange(lower(validita),clock_timestamp()::timestamp,'[)') where id_cantiere_impresa =R_cantiere_imprese.id and upper_inf(validita);
				insert into gds_notifiche._h_cantiere_imprese
				values (nextval('gds_notifiche._h_cantiere_persona_ruoli_id_seq'),currval('gds_notifiche.cantiere_imprese_id_seq'),R_cantiere_imprese.id_cantiere,R_cantiere_imprese.id_impresa,
						tsrange(clock_timestamp()::timestamp,'infinity','[)'),idtransazione,clock_timestamp(),current_timestamp);			*/	
       		else
       			if R_cantiere_imprese_old.ordine != n then
       				update gds_notifiche.cantiere_imprese set ordine =n where id_cantiere = R_cantiere_imprese.id_cantiere and id_impresa = R_cantiere_imprese.id_impresa;
       				n:=gds_log.upd_record('gds_notifiche.cantiere_imprese',idtransazione,R_cantiere_imprese_old,'U');

	       		end if;
       		end if;
       		raise notice 'gds_notifiche.upd_cantiere_imprese loop end' ;
       		select array_append(list,R_cantiere_imprese.id_impresa) into list;
       	end loop;
       /*if n > n_imprese then
            ret.esito:=false;	
            ret.msg:= 'N='||n||' > N_IMPRESE'||coalesce(n_imprese,-1);
			ret:=gds_ui.build_ret(ret,proc_name,-4);
		    return ret;  
       end if;*/
        raise notice 'gds_notifiche.upd_cantiere_imprese prima di delete List %',list;
        for R_CI in select * from gds_notifiche.cantiere_imprese where id_cantiere = R_cantiere_imprese.id_cantiere and id_impresa <> ALL (list) loop
         	delete from gds_notifiche.cantiere_imprese where id=R_CI.id;
         	n:=gds_log.upd_record('gds_notifiche.cantiere_imprese',idtransazione,R_CI,'D');
        end loop;
        /*delete from gds_notifiche.cantiere_imprese where id_cantiere = R_cantiere_imprese.id_cantiere and id_impresa <> ALL (list);
       	update      gds_notifiche."_h_cantiere_imprese" set validita=tsrange(lower(validita),clock_timestamp()::timestamp,'[)') where  id_cantiere = R_cantiere_imprese.id_cantiere and id_impresa <> ALL (list) and upper_inf(validita);
*/
	          
	    ret.esito:=true;	
		ret:=gds_ui.build_ret(ret,proc_name,0);
		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
		return ret;
	end;
END
$$;


ALTER FUNCTION gds_notifiche.upd_cantiere_imprese(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_cantiere_persona_ruoli(character varying, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.upd_cantiere_persona_ruoli(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare R_cantiere_persona_ruoli gds_notifiche.vw_cantiere_persona_ruoli%ROWTYPE;
			R_cantiere_persona_ruoli_prev gds_notifiche.vw_cantiere_persona_ruoli%ROWTYPE;
            R_cantiere_persona_ruoli_old gds_notifiche.cantiere_persona_ruoli%ROWTYPE;
            n bigint;
            r_ruolo gds_types.ruoli%ROWTYPE;
          	idcantiere bigint;
            list bigint [];
           	ret gds_types.result_type;
			id_op bigint;
			proc_name varchar;
			R_CPR record;
	begin
		proc_name:='gds_notifiche.upd_cantiere_persona_ruoli';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,v);
		for R_cantiere_persona_ruoli in /* Verifica stesso cantiere */
			select * from json_populate_recordset(null::gds_notifiche.vw_cantiere_persona_ruoli, v::json) order by codice_fiscale loop		
			if R_cantiere_persona_ruoli.id_soggetto_fisico is null or R_cantiere_persona_ruoli.id_ruolo is null or R_cantiere_persona_ruoli.codice_fiscale is null or 
		       trim(R_cantiere_persona_ruoli.codice_fiscale) ='' then continue; end if;
			IF idcantiere is not null and idcantiere != R_cantiere_persona_ruoli.id_cantiere then
       	    	ret.esito:=false;	
				ret:=gds_ui.build_ret(ret,proc_name,-1);
				--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret));
		    	return ret;     
       		end if;
       		select * into R_ruolo from gds_types.ruoli where id=R_cantiere_persona_ruoli.id_ruolo;
       		if R_ruolo.id is null then
       			ret.esito:=false;
       			ret:=gds_ui.build_ret(ret,proc_name, -3);
        		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
        		return ret; 
       		end if;
       	    if R_ruolo.richiesta_pec and R_cantiere_persona_ruoli.id_soggetto_fisico is not null and R_cantiere_persona_ruoli.codice_fiscale is not null and 
       	       trim(R_cantiere_persona_ruoli.codice_fiscale) !='' and 
       	       (R_cantiere_persona_ruoli.pec is null or  trim(R_cantiere_persona_ruoli.pec) = '') then
       			ret.esito:=false;
       			ret:=gds_ui.build_ret(ret,proc_name, -4);
        		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
        		return ret; 
       		end if;
       		idcantiere:=R_cantiere_persona_ruoli.id_cantiere;
       		if R_cantiere_persona_ruoli_prev.codice_fiscale is not null and upper(trim(R_cantiere_persona_ruoli.codice_fiscale)) = upper(trim(R_cantiere_persona_ruoli_prev.codice_fiscale)) then
       			if upper(trim(R_cantiere_persona_ruoli.nome))      != upper(trim(R_cantiere_persona_ruoli_prev.nome))    or (R_cantiere_persona_ruoli.nome    is null and R_cantiere_persona_ruoli_prev.nome    is not null) or (R_cantiere_persona_ruoli.nome    is not null and R_cantiere_persona_ruoli_prev.nome    is  null) or
       			   upper(trim(R_cantiere_persona_ruoli.cognome))   != upper(trim(R_cantiere_persona_ruoli_prev.cognome)) or (R_cantiere_persona_ruoli.cognome is null and R_cantiere_persona_ruoli_prev.cognome is not null) or (R_cantiere_persona_ruoli.cognome is not null and R_cantiere_persona_ruoli_prev.cognome is  null) or
       			   upper(trim(R_cantiere_persona_ruoli.pec))       != upper(trim(R_cantiere_persona_ruoli_prev.pec))     or (R_cantiere_persona_ruoli.pec     is null and R_cantiere_persona_ruoli_prev.pec     is not null) or (R_cantiere_persona_ruoli.pec     is not null and R_cantiere_persona_ruoli_prev.pec     is  null) or
       			   upper(trim(R_cantiere_persona_ruoli.via))       != upper(trim(R_cantiere_persona_ruoli_prev.via))     or (R_cantiere_persona_ruoli.via     is null and R_cantiere_persona_ruoli_prev.via     is not null) or (R_cantiere_persona_ruoli.via     is not null and R_cantiere_persona_ruoli_prev.via     is  null) or
       			   upper(trim(R_cantiere_persona_ruoli.comune))    != upper(trim(R_cantiere_persona_ruoli_prev.comune))  or (R_cantiere_persona_ruoli.comune  is null and R_cantiere_persona_ruoli_prev.comune  is not null) or (R_cantiere_persona_ruoli.comune  is not null and R_cantiere_persona_ruoli_prev.comune  is  null) or
       			   upper(trim(R_cantiere_persona_ruoli.cap))       != upper(trim(R_cantiere_persona_ruoli_prev.cap))     or (R_cantiere_persona_ruoli.cap     is null and R_cantiere_persona_ruoli_prev.cap     is not null) or (R_cantiere_persona_ruoli.cap     is not null and R_cantiere_persona_ruoli_prev.cap     is  null) then
       			        ret.esito:=false;
       			       	ret.msg:=R_cantiere_persona_ruoli.codice_fiscale;
       					ret:=gds_ui.build_ret(ret,proc_name, -5);
        		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
        				return ret; 
        		end if;
			end if;
       		R_cantiere_persona_ruoli_prev  :=R_cantiere_persona_ruoli;
       	end loop;
       
        select array_append(list,-1) into list;      
       
		for R_cantiere_persona_ruoli in select * from json_populate_recordset(null::gds_notifiche.vw_cantiere_persona_ruoli, v::json) loop
		    raise notice 'inizio loop R_cantiere_persona_ruoli.id_soggetto_fisico %', R_cantiere_persona_ruoli.id_soggetto_fisico;
		   	if  R_cantiere_persona_ruoli.codice_fiscale is null or trim(R_cantiere_persona_ruoli.codice_fiscale) ='' then 
		   		continue;
		    end if;

		   	--if R_cantiere_persona_ruoli.id_soggetto_fisico is null then continue; end if;
        	select * into R_cantiere_persona_ruoli_old from gds_notifiche.cantiere_persona_ruoli
        	where id_cantiere = R_cantiere_persona_ruoli.id_cantiere and id_ruolo = R_cantiere_persona_ruoli.id_ruolo;
            if R_cantiere_persona_ruoli.codice_fiscale is not null and R_cantiere_persona_ruoli.codice_fiscale !='' then     
                raise notice 'R_cantiere_persona_ruoli.id_soggetto_fisico upd_soggetto_fisico %', R_cantiere_persona_ruoli.id_soggetto_fisico;
			    ret:=gds_notifiche.upd_soggetto_fisico(row_to_json(R_cantiere_persona_ruoli)::varchar,idtransazione);
			   if ret.esito = false then
		    		ret:=gds_ui.build_ret(ret,proc_name, -2);
        			--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
            		return ret;
			   end if;
			   R_cantiere_persona_ruoli.id_soggetto_fisico:=ret.valore;
		    end if;
		    raise notice 'prima dell if';
        	IF R_cantiere_persona_ruoli_old.id_soggetto_fisico is null then
        		R_cantiere_persona_ruoli_old.id:=nextval('gds_notifiche.cantiere_persona_ruoli_id_seq');
        		R_cantiere_persona_ruoli_old.id_cantiere:=R_cantiere_persona_ruoli.id_cantiere;
        		R_cantiere_persona_ruoli_old.id_soggetto_fisico:=R_cantiere_persona_ruoli.id_soggetto_fisico;
        		R_cantiere_persona_ruoli_old.id_ruolo:=R_cantiere_persona_ruoli.id_ruolo;
				insert into gds_notifiche.cantiere_persona_ruoli 
				values (R_cantiere_persona_ruoli_old.id_cantiere,R_cantiere_persona_ruoli_old.id_soggetto_fisico,R_cantiere_persona_ruoli_old.id_ruolo,R_cantiere_persona_ruoli_old.id);
				n:=gds_log.upd_record('gds_notifiche.cantiere_persona_ruoli',idtransazione,R_cantiere_persona_ruoli_old,'I');
				--insert into gds_notifiche._h_cantiere_persona_ruoli 
				--values (nextval('gds_notifiche._h_cantiere_persona_ruoli_id_seq'),R_cantiere_persona_ruoli.id_cantiere,R_cantiere_persona_ruoli.id_soggetto_fisico,R_cantiere_persona_ruoli.id_ruolo,
							--currval('gds_notifiche.cantiere_persona_ruoli_id_seq'),tsrange(clock_timestamp()::timestamp,'infinity','[)'),idtransazione,clock_timestamp(),current_timestamp);
       		elsif R_cantiere_persona_ruoli.id_soggetto_fisico != R_cantiere_persona_ruoli_old.id_soggetto_fisico then
       			R_cantiere_persona_ruoli_old.id_soggetto_fisico :=R_cantiere_persona_ruoli.id_soggetto_fisico;
				update  gds_notifiche.cantiere_persona_ruoli set id_soggetto_fisico=R_cantiere_persona_ruoli_old.id_soggetto_fisico
				where id_ruolo=R_cantiere_persona_ruoli.id_ruolo and id_cantiere =R_cantiere_persona_ruoli.id_cantiere;
				n:=gds_log.upd_record('gds_notifiche.cantiere_persona_ruoli',idtransazione,R_cantiere_persona_ruoli_old,'U');
			    
				--update      gds_notifiche."_h_cantiere_persona_ruoli" set validita=tsrange(lower(validita),clock_timestamp()::timestamp,'[)') where id_ruolo=R_cantiere_persona_ruoli.id_ruolo and id_cantiere =R_cantiere_persona_ruoli.id_cantiere and upper_inf(validita);
				--insert into gds_notifiche._h_cantiere_persona_ruoli 
				--values (nextval('gds_notifiche._h_cantiere_persona_ruoli_id_seq'),R_cantiere_persona_ruoli.id_cantiere,R_cantiere_persona_ruoli.id_soggetto_fisico,R_cantiere_persona_ruoli.id_ruolo,
				--			R_cantiere_persona_ruoli_old.id,tsrange(clock_timestamp()::timestamp,'infinity','[)'),idtransazione,clock_timestamp(),current_timestamp);

       		end if;
       		raise notice 'R_cantiere_persona_ruoli %', R_cantiere_persona_ruoli;
        	raise notice 'R_cantiere_persona_ruoli.id_soggetto_fisico %', R_cantiere_persona_ruoli.id_soggetto_fisico;
            raise notice 'R_cantiere_persona_ruoli_old.id_soggetto_fisico %', R_cantiere_persona_ruoli_old.id_soggetto_fisico;

       		if R_cantiere_persona_ruoli.id_soggetto_fisico is not null then 
       			select array_append(list,R_cantiere_persona_ruoli.id_ruolo) into list;
      		end if;
       		raise notice 'array_append % %',R_cantiere_persona_ruoli.id_ruolo,list;
       	end loop;
  
       	for R_CPR in select * from gds_notifiche.cantiere_persona_ruoli where id_cantiere = R_cantiere_persona_ruoli.id_cantiere and id_ruolo <> all (list) loop
       	    delete from gds_notifiche.cantiere_persona_ruoli where id=R_CPR.id;
         	n:=gds_log.upd_record('gds_notifiche.cantiere_persona_ruoli',idtransazione,R_CPR,'D');
       	end loop;
        --delete from gds_notifiche.cantiere_persona_ruoli where id_cantiere = R_cantiere_persona_ruoli.id_cantiere and id_ruolo <> all (list);
       	--update      gds_notifiche."_h_cantiere_persona_ruoli" set validita=tsrange(lower(validita),clock_timestamp()::timestamp,'[)') where id_cantiere = R_cantiere_persona_ruoli.id_cantiere and id_ruolo <> all (list) and upper_inf(validita);

	    raise notice 'delete%',list;       
 		ret:=gds_ui.build_ret_ok(0);
 		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
		return ret;
	end;
END
$$;


ALTER FUNCTION gds_notifiche.upd_cantiere_persona_ruoli(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_imprese(character varying, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.upd_imprese(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare 
	
            ret gds_types.result_type; -- START
		    id_op bigint;   
		    proc_name varchar; -- END
	        R_impresa     gds.imprese%ROWTYPE;
	       	R_impresa_old gds.imprese%ROWTYPE;
	       	R_impresa_new gds.imprese%ROWTYPE;
            n bigint;
    begin
		
		proc_name:='gds_notifiche.upd_imprese';
	    id_op:=gds_log.start_op(proc_name,idtransazione ,v);
	    
        R_impresa:=  (json_populate_record(null::gds.imprese,v::json));
        R_impresa.partita_iva     :=trim(R_impresa.partita_iva);
        R_impresa.ragione_sociale :=trim(upper(R_impresa.ragione_sociale));
        R_impresa.codice_fiscale  :=trim(upper(R_impresa.codice_fiscale));
        if R_impresa.partita_iva = ''     then R_impresa.partita_iva := null;     end if;
        if R_impresa.ragione_sociale = '' then R_impresa.ragione_sociale := null; end if;
        if R_impresa.codice_fiscale = ''  then R_impresa.codice_fiscale := null;  end if;
       
        if R_impresa.partita_iva  is null or  R_impresa.partita_iva = '' then	
       	    	ret.esito:=false;	
       	    	ret.msg:=coalesce(R_impresa.ragione_sociale,'') || ' - ' ||coalesce(R_impresa.codice_fiscale,'');
				ret:=gds_ui.build_ret(ret,proc_name,-1);
		    	return ret;         
        end if;
       
        if not gds_notifiche.check_piva(R_impresa.partita_iva) then
        	ret.esito:=false;
            ret.msg:= R_impresa.partita_iva;
			ret:=gds_ui.build_ret(ret,proc_name, -2);
            return ret;        	
        end if;
       
       	select * into R_impresa_old from gds.imprese where partita_iva=R_impresa.partita_iva;
       	
        if R_impresa_old.id is  null then
            insert into gds.imprese  (                                       id,  ragione_sociale,partita_iva,codice_fiscale)
       									 values (nextval('gds.imprese_id_seq'),R_impresa.ragione_sociale,R_impresa.partita_iva,R_impresa.codice_fiscale);
       		insert into gds.impresa_sedi  ( id_impresa,id_tipo_sede)
       									 values (currval('gds.imprese_id_seq'),0);
       		select * into R_impresa_new from gds.imprese
       	    where id = currval('gds.imprese_id_seq');
       		n:=gds_log.upd_record(proc_name,idtransazione,R_impresa_new,'I');   
       	elsif ((R_impresa_old.ragione_sociale != R_impresa.ragione_sociale)
       			or (R_impresa_old.ragione_sociale is     null and  R_impresa.ragione_sociale is not null) 
       			or (R_impresa_old.ragione_sociale is not null and  R_impresa.ragione_sociale is null)) or 
       		  ((R_impresa_old.codice_fiscale  != R_impresa.codice_fiscale)
       			or (R_impresa_old.codice_fiscale is     null and  R_impresa.codice_fiscale is not null) 
       			or (R_impresa_old.codice_fiscale is not null and  R_impresa.codice_fiscale is null)) then
       		update gds.imprese set ragione_sociale = R_impresa.ragione_sociale,codice_fiscale =R_impresa.codice_fiscale 
       		where partita_iva =R_impresa.partita_iva;
       	    select * into R_impresa_new from gds.imprese  where partita_iva =R_impresa.partita_iva;
       		n:=gds_log.upd_record(proc_name,idtransazione,R_impresa_new,'U'); 
       	else 
       		R_impresa_new.id:=R_impresa_old.id;
        end if; 
	    ret.esito:=true;	
		ret:=gds_ui.build_ret(ret,proc_name,R_impresa_new.id);
		id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
		return ret;
	end;
END
$$;


ALTER FUNCTION gds_notifiche.upd_imprese(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_indirizzo(character varying, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.upd_indirizzo(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $_$
begin
	declare 
            
	        ret gds_types.result_type; -- START
            id_op bigint;   
            proc_name varchar; -- END
	        R_indirizzo     gds_notifiche.vw_opu_indirizzi%ROWTYPE;
            R_indirizzo_old gds_notifiche.vw_opu_indirizzi%ROWTYPE;
           v_old varchar;
          v_new varchar;
            n int;
           str varchar;
          caps varchar;
         n_fields int;
	begin
		
		proc_name:='gds_notifiche.upd_indirizzo';
        --id_op:=gds_log.start_op(proc_name,idtransazione ,v);
		insert into gds_log.call_logs   values (nextval('gds_log.call_logs_id_seq'),idtransazione,proc_name,'START ->',CLOCK_TIMESTAMP(),v);
        R_indirizzo:=  (json_populate_record(null::gds_notifiche.vw_opu_indirizzi,v::json));
       	/*if R_indirizzo.id_indirizzo is null then
       	    ret.esito:=false;	
            ret:=gds_ui.build_ret(ret,proc_name,-1);
            id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                   raise notice '*** UPD INDIRIZZO ID NON PRESENTE';
            return ret;
       	end if;*/
        R_indirizzo.id:=R_indirizzo.id_indirizzo;
        R_indirizzo.via    := upper(trim(R_indirizzo.via));
    	R_indirizzo.civico := upper(trim(R_indirizzo.civico));
       	R_indirizzo.comune := upper(trim(R_indirizzo.comune));
        R_indirizzo.cap := upper(trim(R_indirizzo.cap));
        n_fields:=0;
        if length(R_indirizzo.via)    <= 0 or R_indirizzo.via is null then R_indirizzo.via       :=null; n_fields:=n_fields+1; end if;
        if length(R_indirizzo.civico) <= 0 or R_indirizzo.civico is null then R_indirizzo.civico :=null; end if;
        if length(R_indirizzo.comune) <= 0 or R_indirizzo.comune is null then R_indirizzo.comune :=null; n_fields:=n_fields+1; end if;
        if length(R_indirizzo.cap)    <= 0 or R_indirizzo.cap is null then R_indirizzo.cap       :=null; n_fields:=n_fields+1; end if;
       raise notice 'N_FIELDS %',n_fields;
        if n_fields >0 and n_fields< 3 then
            ret.msg:='Via ' || coalesce(R_indirizzo.via,'')  || ' - CAP '|| coalesce(R_indirizzo.cap,'') || ' - comune '|| coalesce(R_indirizzo.comune,'');
       		ret.esito:=false;
       		ret:=gds_ui.build_ret(ret,proc_name,-4);
		    return ret;
        end if;
        raise notice 'gds_notifiche.upd_indirizzo(v=) =%', v;
        raise notice 'R_indirizzo.id_indirizzo=%', R_indirizzo.id_indirizzo;
       	if  R_indirizzo.id_indirizzo is not null then
        	select * into R_indirizzo_old from gds_notifiche.vw_opu_indirizzi where id = R_indirizzo.id_indirizzo;
 
         	raise notice 'gds_notifiche.upd_indirizzo(OLD) =%', R_indirizzo_old;
         
        	raise notice 'gds_notifiche.upd_indirizzo(NEW) =%', R_indirizzo;
        end if;
        
        v_old:=Row_to_json(R_indirizzo_old);
        v_new:=Row_to_json(R_indirizzo);
       
         raise notice 'gds_notifiche.upd_indirizzo(OLD) =%', v_old;
         
         raise notice 'gds_notifiche.upd_indirizzo(NEW) =%', v_new;
        
         raise notice 'gds_notifiche.upd_indirizzo(comune) =%', R_indirizzo.comune;
         raise notice 'gds_notifiche.upd_indirizzo(comune) =%', R_indirizzo_old.comune;
        if R_indirizzo.via is null and  R_indirizzo.civico is null and R_indirizzo.comune is null and R_indirizzo.cap is null then
        	R_indirizzo.id:= null;  
    	ELSIF ((R_indirizzo_old.id is null) and 
    		(R_indirizzo.via is not null or R_indirizzo.civico is not null or R_indirizzo.cap is not null is not null or R_indirizzo.comune is not null) )
    	    or (R_indirizzo.via != R_indirizzo_old.via and R_indirizzo.via is not null and R_indirizzo_old.via is not null)
    	    or (R_indirizzo.via is not null and R_indirizzo_old.via is  null) or (R_indirizzo.via is  null and R_indirizzo_old.via is not null)
    	    or (R_indirizzo.civico != R_indirizzo_old.civico and R_indirizzo.civico is not null and R_indirizzo_old.civico is not null)
    	    or (R_indirizzo.civico is not null and R_indirizzo_old.civico is null) or (R_indirizzo.civico is null and R_indirizzo_old.civico is not null)
    	    or (R_indirizzo.cap != R_indirizzo_old.cap and R_indirizzo.cap is not null and R_indirizzo_old.cap is not null)
    	    or (R_indirizzo.cap is not null and R_indirizzo_old.cap is null) or (R_indirizzo.cap is null and R_indirizzo_old.cap is not null)
    	    --or (R_indirizzo.id_comune != R_indirizzo_old.id_comune and R_indirizzo.id_comune is not null and R_indirizzo_old.id_comune is not null)
    	    or (R_indirizzo.comune != R_indirizzo_old.comune and R_indirizzo.comune is not null and R_indirizzo_old.comune is not null) 
    	    or (R_indirizzo.comune is not null and R_indirizzo_old.comune is null)  or (R_indirizzo.comune is null and R_indirizzo_old.comune is not null) 
    	    or (R_indirizzo.lat != R_indirizzo_old.lat and R_indirizzo.lat is not null and R_indirizzo_old.lat is not null) 
    	    or (R_indirizzo.lat is not null and R_indirizzo_old.lat is null)  or (R_indirizzo.lat is null and R_indirizzo_old.lat is not null) 
    	        	    or (R_indirizzo.lng != R_indirizzo_old.lng and R_indirizzo.lng is not null and R_indirizzo_old.lng is not null) 
    	    or (R_indirizzo.lng is not null and R_indirizzo_old.lng is null)  or (R_indirizzo.lng is null and R_indirizzo_old.lng is not null) 
    	    then
       		raise notice 'if 1 %',R_indirizzo.comune;
       		if length(trim(R_indirizzo.comune)) > 0 /*and R_indirizzo.id_comune is null */then
       			select id into R_indirizzo.id_comune from public.comuni1 where upper(nome) = upper(trim(R_indirizzo.comune)) and (notused is null or notused =false);
       			raise notice 'R_indirizzo.id_comune % %',R_indirizzo.comune,R_indirizzo.id_comune;
       		else
       			R_indirizzo.id_comune := null;
			end if;
       		if R_indirizzo.id_comune is null then 
       			ret.msg:=R_indirizzo.comune;
       			ret.esito:=false;
       			ret:=gds_ui.build_ret(ret,proc_name,-1);
		    	return ret;
       		end if;
       	    raise notice 'if 2';
       	    if R_indirizzo.cap !~ '^[0-9]{5}$' then 
       			ret.esito:=false;
       			ret.msg := 'CAP '''||R_indirizzo.cap ||'''';
       			ret:=gds_ui.build_ret(ret,proc_name,-2);
		    	return ret;
       		end if;
       	    raise notice 'if 3';
       		str:='%'||R_indirizzo.cap||'%';
       		select reverse_cap into caps  from public.comuni1 where id=R_indirizzo.id_comune ;
       	    if caps not like str then 
       			ret.esito:=false;
       			ret.msg:='CAP '||R_indirizzo.cap ||' errato, CAP possibili per il comune specificato ('||caps||')';
       			raise notice 'MSG %',ret.msg;
       			ret:=gds_ui.build_ret(ret,proc_name,-3);
       		raise notice 'MSG %',ret;
		    	return ret;
       		end if;
       		raise notice 'if 4';
 		    insert into gds_log.call_logs   values (nextval('gds_log.call_logs_id_seq'),idtransazione,proc_name,'INSERT NUOVO ADDRESS ->',CLOCK_TIMESTAMP(),R_indirizzo);      		
    		R_indirizzo.id:=public.insert_opu_noscia_indirizzo( 106,trim(R_indirizzo.cod_provincia) ,R_indirizzo.id_comune  , null::text,
    					null::int4,    trim(R_indirizzo.via)  ,    trim(R_indirizzo.cap)  ,    trim(R_indirizzo.civico),
    				R_indirizzo.lat ,    R_indirizzo.lng );
    		raise notice 'gds_notifiche.upd_indirizzo(NEW ID) =%', R_indirizzo.id;
    		ret.esito:=true;	
            ret:=gds_ui.build_ret(ret,proc_name,R_indirizzo.id);
            --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
            raise notice ' RET = %',row_to_json(ret)::varchar;
            return ret;
                				
    	end if;
      
    	ret.esito:=true;	
        ret:=gds_ui.build_ret(ret,proc_name,R_indirizzo.id);
        --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
        return ret;
	end;
END
$_$;


ALTER FUNCTION gds_notifiche.upd_indirizzo(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_notifica(character varying, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.upd_notifica(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare 
            ret gds_types.result_type; -- start
            ret_dup gds_types.result_type; -- start
            ret_soggetto gds_types.result_type; -- START
            id_op bigint;   
            proc_name varchar; -- end
            R_not gds_notifiche.notifiche%ROWTYPE;
            R_notifica_prec gds_notifiche.notifiche%ROWTYPE;
	        R_notifiche gds_notifiche.vw_notifiche%ROWTYPE;
            R_notifiche_old gds_notifiche.vw_notifiche%ROWTYPE;
            R_notifica gds_notifiche.vw_notifiche%ROWTYPE;
            R_cantiere gds_notifiche.vw_cantieri%ROWTYPE;
            R_cantiere_new gds_notifiche.cantieri%ROWTYPE;
           	R_soggetto_fisico gds_notifiche.vw_soggetti_fisici%ROWTYPE;
            R_stato gds_types.stati%ROWTYPE;
            R_stato_new gds_types.stati%ROWTYPE;
            ruolo_mancante varchar;
            str_app varchar;
           	nuovo_id bigint; 
            n int;
	begin
		proc_name:='gds_notifiche.upd_notifica';
        --id_op:=gds_log.start_op(proc_name,idtransazione ,v);
	
		R_notifiche.id_stato:=v::json->>'id_stato';
		R_notifiche.id:=v::json->>'id_notifica';


        raise notice 'R_notifiche.id %',R_notifiche.id;
        select * into R_notifiche_old from gds_notifiche.vw_notifiche  where id = R_notifiche.id;
        raise notice 'R_notifiche_old.id_stato %',R_notifiche_old.id_stato;
       	select * into R_stato from gds_types.stati s where id= R_notifiche_old.id_stato;
        select * into R_stato_new from gds_types.stati s where id= R_notifiche.id_stato;
        raise notice 'R_stato %',R_stato.id;
       
       	select * into R_not from gds_notifiche.notifiche n where id=R_notifiche.id;
       	IF R_notifiche.id_stato != R_notifiche_old.id_stato then
        	select count(*) into n from gds_types.stati_successivi ss
			where ss.id_stato_attuale = R_notifiche_old.id_stato and ss.id_stato_prossimo =R_notifiche.id_stato;
		    raise notice 'CAMBIAMENTO STATO N %',n;
		    raise notice 'CAMBIAMENTO STATO OLD % NEW %',R_stato.descr,R_stato_new.descr;

			if n < 1 or n> 1then
			      ret.esito:=false;	
				  ret:=gds_ui.build_ret(ret,proc_name,-2);
				  --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                  return ret;
			end if;
			--return -3;
			if n = 1 then /* cambiamento di stato */
				if  R_stato_new.descr='ELIMINATA'  and R_stato.descr='INIZIALE' then
					ret.esito:=true;
					ret.valore:= gds_notifiche.delete_notifica(R_notifiche.id);
                	ret:=gds_ui.build_ret(ret,proc_name,ret.valore);
                    id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);              	
                	return ret;
				end if;
				if  R_stato_new.descr='ELIMINATA'  or R_stato_new.descr='SALVATA - INOLTRATA - DISATTIVA' then
					select * into R_notifica_prec from gds_notifiche.notifiche n where id_notifica_succ =R_notifiche.id;
					if R_notifica_prec.id is not null then
						update gds_notifiche.notifiche set id_stato=(select id from gds_types.stati where descr='SALVATA - INOLTRATA - ATTIVA'),
						data_notifica=current_timestamp,id_notifica_succ =null
						where id=R_notifica_prec.id;
						update gds_notifiche.notifiche set 
						data_notifica=current_timestamp,id_notifica_succ =R_notifica_prec.id
						where id=R_notifiche.id;
						--n:=gds_log.upd_record('gds_notifiche.notifiche',idtransazione,R_notifica_prec,'U');
					end if;
				update gds_notifiche.notifiche set id_stato=R_stato_new.id,data_notifica=current_timestamp 
				where id=R_notifiche.id;
				raise notice 'STATO NUOVO = %',R_stato_new.id;
				ret.esito:=true;	
                ret:=gds_ui.build_ret(ret,proc_name,R_notifiche.id);
                        --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                n:=gds_log.upd_record('gds_notifiche.notifiche',idtransazione,R_not,'U');
                return ret;
			end if;
				if R_notifiche.id_stato != R_notifiche_old.id_stato and R_stato.descr='SALVATA - INOLTRATA - ATTIVA' then
				/* DUPLICA NOTIFICA */
					if R_not.id_notifica_succ is null then
						ret_dup:=gds_notifiche.duplica_notifica(R_not.id,idtransazione);
						if not ret_dup.esito then
							ret.esito:=false;	
				  			ret:=gds_ui.build_ret(ret,proc_name,-7);
				  			--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                  			return ret;
						end if;
					raise notice 'DUPLICA NOTIFICA %',ret_dup.valore;
						nuovo_id=ret_dup.valore;
						R_not.id_notifica_succ := nuovo_id;
						update gds_notifiche.notifiche set id_notifica_succ =nuovo_id, 
						id_stato=(select id from gds_types.stati where descr='SALVATA - INOLTRATA - DISATTIVA'),
						data_notifica=current_timestamp
						where id=R_not.id;
						n:=gds_log.upd_record('gds_notifiche.notifiche',idtransazione,R_not,'U');
					    ret.esito:=true;	
				        ret:=gds_ui.build_ret(ret,proc_name,nuovo_id);
				        raise notice 'DUPLICA NOTIFICA %',ret.valore;
				        ret.msg:='NUOVO ID';
				        --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                  		return ret;
					else
					    ret.esito:=false;	
                        ret:=gds_ui.build_ret(ret,proc_name,-3);
                        --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                        return ret;
					end if;
				end if;
				update gds_notifiche.notifiche set id_stato = R_notifiche.id_stato,data_notifica=current_timestamp
				where id=R_notifiche.id;
				raise notice 'STATO NUOVO PUNTO 2= %',R_notifiche.id_stato;
			end if;
        end if;
       if R_notifiche_old.modificabile = false then
       	          
    		     ret.esito:=false;	
                 ret:=gds_ui.build_ret(ret,proc_name,-4);
                 --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                 return ret;
	   end if;
	    str_app:=((v::json)->'ammontare')::text;
	    if str_app = '' or str_app='""' then
        	v:=(v::jsonb - 'ammontare'::text )::text;
        	v:=(v::jsonb|| '{"ammontare":null}'::jsonb)::text;
        end if; 
       	str_app:=((v::json)->'numero_lavoratori')::text;
	    if str_app = '' or str_app='""' then
        	v:=(v::jsonb - 'numero_lavoratori'::text )::text;
        	v:=(v::jsonb|| '{"numero_lavoratori":null}'::jsonb)::text;
        end if;
        str_app:=((v::json)->'numero_imprese')::text;
	    if str_app = '' or str_app='""' then
        	v:=(v::jsonb - 'numero_imprese'::text )::text;
        	v:=(v::jsonb|| '{"numero_imprese":null}'::jsonb)::text;
        end if;
        str_app:=((v::json)->'durata_presunta')::text;
	    if str_app = '' or str_app='""' then
        	v:=(v::jsonb - 'durata_presunta'::text )::text;
        	v:=(v::jsonb|| '{"durata_presunta":null}'::jsonb)::text;
        end if;  
	    R_notifiche:=  json_populate_record(null::gds_notifiche.vw_notifiche,v::json);
       	R_notifiche.id:=R_notifiche.id_notifica;

        R_cantiere:=  json_populate_record(null::gds_notifiche.vw_cantieri,  v::json);
        R_cantiere.id := R_cantiere.id_cantiere;
	  
	  
	  
	   raise notice 'in update %', v;
	   R_cantiere.via:=upper(trim( R_cantiere.via));
	   R_cantiere.cap:=trim( R_cantiere.cap);
	   R_cantiere.denominazione:=upper(trim( R_cantiere.denominazione));
	   R_cantiere.comune:=upper(trim( R_cantiere.comune));
	   R_cantiere.civico:=upper(trim( R_cantiere.civico));

	   if R_cantiere.via is null or R_cantiere.via='' or R_cantiere.id_comune is null or R_cantiere.cap is null or R_cantiere.cap='' then
	       		 ret.esito:=false;	
                 ret:=gds_ui.build_ret(ret,proc_name,-6);
                 --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                 return ret;
	   end if;
	   if R_cantiere.denominazione is null or trim(R_cantiere.denominazione) = ''then
	       		 ret.esito:=false;	
                 ret:=gds_ui.build_ret(ret,proc_name,-9);
                 --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                 return ret;
	   end if;
	  	if R_cantiere.denominazione is null or R_cantiere.via is null or R_cantiere.cap is null or
			   R_cantiere.comune is null or R_cantiere.civico is null or R_cantiere.id_natura_opera is null or R_cantiere.data_presunta  is null or
			   R_cantiere.denominazione ='' or R_cantiere.via ='' or R_cantiere.cap ='' or
			   R_cantiere.comune ='' or R_cantiere.civico is null then
			   	ret.esito:=false;	
				ret:=gds_ui.build_ret(ret,proc_name,-12);
				return ret;
		end if;
	  
	   select count(*) into n from gds_notifiche.vw_cantieri c
	  		where denominazione = R_cantiere.denominazione and
	  				        via = R_cantiere.via and
	  				        cap = trim( R_cantiere.cap) and
	  				     comune = R_cantiere.comune and
	  				     civico = R_cantiere.civico and
	  		      cod_provincia = R_cantiere.cod_provincia and 
	  		          data_presunta = R_cantiere.data_presunta and 
	  		     id_cantiere != R_cantiere.id_cantiere and attivo=true
	  		    and id_notifica_succ is null;
	   if n >=  1 then
			      ret.esito:=false;	
				  ret:=gds_ui.build_ret(ret,proc_name,-8);
				  --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                  return ret;
		end if;
        ret:= gds_notifiche.upd_cantiere(v, idtransazione);
                raise notice 'gds_notifiche.upd_cantiere %', n;
        if ret.esito =false then
		          
      		     --ret.esito:=false;	
                 ret:=gds_ui.build_ret(ret,proc_name,-1);
                 --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
                 return ret;
		end if;
		
		select count(*) into n from gds_notifiche.cantiere_imprese ci where id_cantiere =R_cantiere.id;
		if n > coalesce(R_cantiere.numero_imprese,0) then 		       	          
    		 ret.esito:=false;	
             ret:=gds_ui.build_ret(ret,proc_name,-13);
                 --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
             return ret;
		end if;
		
		R_soggetto_fisico.via:=trim(R_cantiere.via_notificante);
		R_soggetto_fisico.cap:=trim(R_cantiere.cap_notificante);
		R_soggetto_fisico.civico:=trim(R_cantiere.civico_notificante);
		R_soggetto_fisico.comune:=trim(R_cantiere.comune_notificante);
		R_soggetto_fisico.nome:=trim(R_cantiere.nome_notificante);
		R_soggetto_fisico.cognome:=trim(R_cantiere.cognome_notificante);
	    if R_soggetto_fisico.comune is null or R_soggetto_fisico.comune ='' then
	    	R_soggetto_fisico.id_comune:=null;
	    else
	        R_soggetto_fisico.id_comune:=R_cantiere.id_comune_notificante;
	    end if;

		R_soggetto_fisico.cod_provincia:=R_cantiere.cod_provincia_notificante;
		R_soggetto_fisico.id_indirizzo:=R_cantiere.id_indirizzo_notificante;
		R_soggetto_fisico.id:=R_cantiere.id_soggetto_notificante;
		R_soggetto_fisico.codice_fiscale:=R_cantiere.cf_notificante;
		raise notice 'R_cantiere.id_indirizzo_notificante %',R_cantiere.id_indirizzo_notificante;
		ret_soggetto:= gds_notifiche.upd_soggetto_fisico(row_to_json(R_soggetto_fisico)::varchar,idtransazione);
	    /*  n:= gds_notifiche.upd_indirizzo(v);
        if n != 0 then
			return -1;
		end if; */

		if not ret_soggetto.esito then
		    ret:=gds_ui.build_ret(ret_soggetto,proc_name, -5);
        	--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
            return ret;
		end if;
		if R_notifiche.id_stato != R_notifiche_old.id_stato and R_stato_new.descr='SALVATA - INOLTRATA - ATTIVA' then
			select * into R_cantiere_new from gds_notifiche.cantieri   where id=R_notifiche.id_cantiere;
			select * into R_notifica from gds_notifiche.vw_notifiche  where id=R_notifiche.id_notifica;
			if R_cantiere_new.id_natura_opera is null or R_cantiere_new.data_presunta is null or R_cantiere_new.durata_presunta is null or
			   R_cantiere_new.numero_imprese is null or R_cantiere_new.numero_lavoratori is null or R_cantiere_new.ammontare is null then
			   	ret.esito:=false;	
				ret:=gds_ui.build_ret(ret,proc_name,-10);
				return ret;
			end if;
			ret.msg:='';
			for ruolo_mancante in select cpr.descr  from gds_notifiche.vw_cantiere_persona_ruoli cpr where cpr.id_cantiere = R_notifiche.id_cantiere and cpr.obbligatorio and cpr.id is null loop
				ret.msg := ret.msg || ruolo_mancante || ' ';
			end loop;
			if ret.msg != '' then
			   	ret.esito:=false;	
				ret:=gds_ui.build_ret(ret,proc_name,-11);
			 	return ret;
			end if;
			if R_not.cun is null or R_not.cun = '' then
				R_not.cun:=gds_notifiche.crea_codice(extract (year from coalesce(R_notifica.data_notifica,CLOCK_TIMESTAMP()) )::varchar ,'CUN'::varchar);
				raise notice 'CUN %',R_not.cun;
				update gds_notifiche.notifiche set cun =  R_not.cun,data_notifica=current_timestamp where id=R_not.id;
			end if;
			if R_cantiere_new.cuc is null or  R_cantiere_new.cuc = '' then
				R_cantiere_new.cuc:=gds_notifiche.crea_codice(R_not.cun ,'CUC'::varchar);
				update gds_notifiche.cantieri set cuc = R_cantiere_new.cuc where id=R_cantiere.id;
				--select * into R_cantiere_new from gds_notifiche.cantieri c where id=R_cantiere.id;
				n:=gds_log.upd_record('gds_notifiche.cantieri',idtransazione,R_cantiere_new,'U');
				raise notice 'CUC %',R_cantiere.cuc;
			end if;

		end if;	
	    update gds_notifiche.notifiche set data_modifica = CLOCK_TIMESTAMP() where id=R_not.id; 
	   	n:=gds_log.upd_record('gds_notifiche.notifiche',idtransazione,R_not,'U');
	    ret.esito:=true;	
        ret:=gds_ui.build_ret(ret,proc_name, 0);
        --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
        return ret;
	end;
END
$$;


ALTER FUNCTION gds_notifiche.upd_notifica(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_soggetto_fisico(character varying, bigint); Type: FUNCTION; Schema: gds_notifiche; Owner: postgres
--

CREATE FUNCTION gds_notifiche.upd_soggetto_fisico(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $_$
begin
	declare 
             ret gds_types.result_type; -- START
			 id_op bigint;   
 			 proc_name varchar; -- END
	        R_soggetto_fisico gds_notifiche.vw_soggetti_fisici%ROWTYPE;
            R_soggetto_fisico_old public.opu_soggetto_fisico%ROWTYPE;
            n bigint;
	begin
		proc_name:='gds_notifiche.upd_soggetto_fisico';
        --id_op:=gds_log.start_op(proc_name,idtransazione ,v);
       
		raise notice 'gds_notifiche.upd_soggetto_fisico %',v;
		R_soggetto_fisico:=  (json_populate_record(null::gds_notifiche.vw_soggetti_fisici,v::json));
		R_soggetto_fisico.email:=json_extract_path_text(v::json,'pec');
		R_soggetto_fisico.id:= R_soggetto_fisico.id_soggetto_fisico;

	    R_soggetto_fisico.nome:=upper(trim(R_soggetto_fisico.nome));
        R_soggetto_fisico.cognome:=upper(trim(R_soggetto_fisico.cognome));
        R_soggetto_fisico.codice_fiscale:=upper(replace(R_soggetto_fisico.codice_fiscale,' ',''));
        R_soggetto_fisico.email:=replace(R_soggetto_fisico.email,' ','');
       
       
        if length(R_soggetto_fisico.nome) =0 or R_soggetto_fisico.nome is null then
            ret.esito:=false;
            ret.msg:= coalesce(R_soggetto_fisico.codice_fiscale,' ') || ' - '||coalesce(R_soggetto_fisico.cognome,' ') ;
			ret:=gds_ui.build_ret(ret,proc_name, -4);
            return ret;  
        end if;
        if length(R_soggetto_fisico.cognome) =0 or R_soggetto_fisico.cognome is null then
            ret.esito:=false;
            ret.msg:= coalesce(R_soggetto_fisico.codice_fiscale, ' ') || ' - '||coalesce(R_soggetto_fisico.nome,' ') ;
			ret:=gds_ui.build_ret(ret,proc_name, -5);
            return ret;  
        end if;
        if length(R_soggetto_fisico.codice_fiscale) =0 or R_soggetto_fisico.codice_fiscale is null then
            ret.esito:=false;
            ret.msg:= coalesce(R_soggetto_fisico.cognome,' ') || ' - '||coalesce(R_soggetto_fisico.nome,' ') ;
			ret:=gds_ui.build_ret(ret,proc_name, -6);
            return ret;  
        end if;
        if not gds_notifiche.check_cf(R_soggetto_fisico.codice_fiscale) then
        	ret.esito:=false;
            ret.msg:= '"'||R_soggetto_fisico.codice_fiscale||'"';
			  ret:=gds_ui.build_ret(ret,proc_name, -2);
              return ret;        	
        end if;
       
       --'[A-z0-9\.\+_-]+@[A-z0-9\._-]+\.[A-z]{2,6}''
        if length(R_soggetto_fisico.email) > 0 and R_soggetto_fisico.email !~ '^[A-z0-9\.\+_-]+@[A-z0-9\._-]+\.[A-z]{2,6}$' then
        	ret.esito=false;
        	ret.msg:= R_soggetto_fisico.email;
			ret:=gds_ui.build_ret(ret,proc_name, -3);
            return ret;        	
        end if;
        if length(R_soggetto_fisico.email) > 0 and lower(R_soggetto_fisico.email) !~ '.*@.*(pec|legal|cert)' then
        	ret.esito=false;
        	ret.msg:= R_soggetto_fisico.email;
			ret:=gds_ui.build_ret(ret,proc_name, -7);
            return ret;        	
        end if;	
    	raise notice 'R_soggetto_fisico %',v;   	
		select * into R_soggetto_fisico_old from public.opu_soggetto_fisico where codice_fiscale =R_soggetto_fisico.codice_fiscale;
		raise notice 'SELECT'; 
	    ret:=gds_notifiche.upd_indirizzo(v,idtransazione);
        if (not ret.esito) then

			  ret:=gds_ui.build_ret(ret,proc_name, -1);
			  --id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
              return ret;
        end if;
		R_soggetto_fisico.id_indirizzo:=ret.valore;
       	raise notice 'SOGGETTO: UPD INDIRIZZO ReT.valore %',ret.valore; 
        if R_soggetto_fisico_old.id is null  then
        	R_soggetto_fisico_old.id := nextval('public.opu_soggetto_fisico_id_seq');
        	R_soggetto_fisico.id:=R_soggetto_fisico_old.id;
        	R_soggetto_fisico_old.nome:=trim(R_soggetto_fisico.nome);
        	R_soggetto_fisico_old.cognome:=trim(R_soggetto_fisico.cognome);
        	R_soggetto_fisico_old.codice_fiscale:=trim(R_soggetto_fisico.codice_fiscale);
        	R_soggetto_fisico_old.email:=trim(R_soggetto_fisico.email);
        	R_soggetto_fisico_old.indirizzo_id:=ret.valore;
        	insert into public.opu_soggetto_fisico    (id,nome,cognome,codice_fiscale,email,indirizzo_id) values
        	(R_soggetto_fisico_old.id,R_soggetto_fisico_old.nome,R_soggetto_fisico_old.cognome,R_soggetto_fisico_old.codice_fiscale,R_soggetto_fisico_old.email,R_soggetto_fisico_old.indirizzo_id);
        	n:=gds_log.upd_record('public.opu_soggetto_fisico',idtransazione,R_soggetto_fisico_old,'I');
        	raise notice 'SOGGETTO: INSERT';
       else
       		raise notice 'SOGGETTO: PRIMA DI UPDATE % %',coalesce(ret.valore::varchar,'RET VALORE NULL'), coalesce(R_soggetto_fisico_old.indirizzo_id::varchar,'ID INDIRIZZO NULL');
       		raise notice 'SOGGETTO (OLD) %',R_soggetto_fisico_old;
       		raise notice 'SOGGETTO (NEW) %',R_soggetto_fisico;
       		R_soggetto_fisico.id:=R_soggetto_fisico_old.id;
      		R_soggetto_fisico.id_indirizzo:=ret.valore;
       		if ((R_soggetto_fisico_old.indirizzo_id != R_soggetto_fisico.id_indirizzo) or (R_soggetto_fisico_old.indirizzo_id is null and R_soggetto_fisico.id_indirizzo is not null)  or (R_soggetto_fisico_old.indirizzo_id is not null and R_soggetto_fisico.id_indirizzo is  null))  then
       			raise notice 'SOGGETTO: UPDATE % IND=%',R_soggetto_fisico.codice_fiscale,ret.valore;
				update  public.opu_soggetto_fisico  set indirizzo_id =R_soggetto_fisico.id_indirizzo
				where codice_fiscale =R_soggetto_fisico.codice_fiscale;
				n:=gds_log.upd_record('public.opu_soggetto_fisico',idtransazione,R_soggetto_fisico_old,'U');
       		end if;
       	    if  ((R_soggetto_fisico_old.email        != R_soggetto_fisico.email)        or (R_soggetto_fisico_old.email        is null and R_soggetto_fisico.email        is not null)  or (R_soggetto_fisico_old.email        is not null and R_soggetto_fisico.email        is  null)) then
       			raise notice 'SOGGETTO: UPDATE % IND=%',R_soggetto_fisico.codice_fiscale,ret.valore;
				update  public.opu_soggetto_fisico  set email = R_soggetto_fisico.email
				where codice_fiscale =R_soggetto_fisico.codice_fiscale ;
				n:=gds_log.upd_record('public.opu_soggetto_fisico',idtransazione,R_soggetto_fisico_old,'U');
       		end if;
       	    if  ((R_soggetto_fisico_old.nome        != R_soggetto_fisico.nome or R_soggetto_fisico_old.nome is null)            and R_soggetto_fisico.nome    is not null and R_soggetto_fisico.nome    !='') then
       			raise notice 'SOGGETTO: UPDATE % IND=%',R_soggetto_fisico.codice_fiscale,ret.valore;
				update  public.opu_soggetto_fisico  set nome = R_soggetto_fisico.nome
				where codice_fiscale =R_soggetto_fisico.codice_fiscale ;
				n:=gds_log.upd_record('public.opu_soggetto_fisico',idtransazione,R_soggetto_fisico_old,'U');
       		end if;
       	    if  ((R_soggetto_fisico_old.cognome        != R_soggetto_fisico.cognome or R_soggetto_fisico_old.cognome is null)   and    R_soggetto_fisico.cognome is not null and R_soggetto_fisico.cognome !='') then
       			raise notice 'SOGGETTO: UPDATE % IND=%',R_soggetto_fisico.codice_fiscale,ret.valore;
				update  public.opu_soggetto_fisico  set cognome = R_soggetto_fisico.cognome
				where codice_fiscale =R_soggetto_fisico.codice_fiscale ;
				n:=gds_log.upd_record('public.opu_soggetto_fisico',idtransazione,R_soggetto_fisico_old,'U');
       		end if;
       end if;
      	ret.valore:=R_soggetto_fisico.id;
        ret.esito:=true;	
		ret:=gds_ui.build_ret(ret,proc_name, R_soggetto_fisico.id);
		--id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
        return ret;
	end;
end;
$_$;


ALTER FUNCTION gds_notifiche.upd_soggetto_fisico(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_dati(character varying, character varying, bigint); Type: PROCEDURE; Schema: gds_srv; Owner: postgres
--

CREATE PROCEDURE gds_srv.get_dati(IN operazione character varying, IN v character varying, IN idutente bigint, OUT joutput json)
    LANGUAGE plpgsql
    AS $$
begin
	declare
		proc_name varchar;
	begin
		proc_name:='gds_srv.get_dati';
		call gds.get_dati(operazione,v,idutente,joutput);
	end;
END
$$;


ALTER PROCEDURE gds_srv.get_dati(IN operazione character varying, IN v character varying, IN idutente bigint, OUT joutput json) OWNER TO postgres;

--
-- Name: get_dati_gian(anyelement, bigint, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.get_dati_gian(operazione anyelement, id_record bigint, idutente bigint) RETURNS anyelement
    LANGUAGE plpgsql
    AS $$
declare 
	idtransazione bigint;
	proc_name varchar;
	id_op bigint;  
	ret gds_types.result_type;
	text_msg1 varchar;	
	text_msg2 varchar;	
	text_msg3 varchar;	
	text_msg4 varchar;
	ts timestamp;
	not_operazione bool;
	joutput json;
	rt record;
	
	R_ISP gds.vw_ispezioni%ROWTYPE;
	R_NOT gds_srv.vw_notifiche%ROWTYPE;
	

begin
	ts:=CLOCK_TIMESTAMP();
	idtransazione:= gds_log.get_id_transazione(idutente,'get_dati');
	proc_name:='gds_srv.get_dati';
   	not_operazione:=false;
  
   	begin

	   	case operazione
	   		when 'get_ispezione' then
	   			select * into R_ISP from gds.vw_ispezioni where id=id_record;
	   			rt:=(R_ISP);
			when 'get_notifica' then
	   			select * into R_NOT from gds_srv.vw_notifiche where id=id_record;
				rt:=(R_NOT);
			else
	   			not_operazione:=true;
	   	end case;
		
  	    ret.info:='operzione ' || operazione  || ' - ' || 'idutente ' || idutente; 
	   	
	    if not_operazione=false then
		    ret.esito:=true;
 		   	ret.msg:=null;
	    	if rt->>'id' is null then 
	    		ret.esito:=false;	
	    		ret.msg:='id non trovato';
	    	end if;
	 		ret.valore:= id_record;
		else 
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:='operazione ' || operazione || ' non configurata';
		end if;

	exception when others then
		GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
	end;

	joutput:=row_to_json(ret);
	id_op:=gds_log.op(proc_name,idtransazione,rt::varchar,joutput,ts,id_record);

	return rt;
end;
$$;


ALTER FUNCTION gds_srv.get_dati_gian(operazione anyelement, id_record bigint, idutente bigint) OWNER TO postgres;

--
-- Name: get_id_notificante(character varying, character varying, character varying, boolean, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.get_id_notificante(cf character varying, nome character varying, cognome character varying, ins boolean, idtransazione bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare ret gds_types.result_type%ROWTYPE;
		proc_name varchar;
		rt varchar;
		id_op bigint;
		text_msg1 varchar;	
		text_msg2 varchar;	
		text_msg3 varchar;	
		text_msg4 varchar;
	begin
		proc_name:='gds_srv.get_id_notificante';
		id_op:=gds_log.start_op(proc_name,idtransazione ,cf||nome||cognome);
		ret:=gds_notifiche.get_id_notificante(cf , nome , cognome,ins,idtransazione ) ;
		--ret:=gds_ui.build_ret(ret,proc_name,-1);
		rt:=row_to_json(ret);
		id_op:=gds_log.end_op(proc_name,idtransazione ,rt::varchar);
		return rt;
			exception when others then
			GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			rt:=row_to_json(ret);
			id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
			return rt;
	end;
END
$$;


ALTER FUNCTION gds_srv.get_id_notificante(cf character varying, nome character varying, cognome character varying, ins boolean, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_id_notificante_new(character varying, character varying, character varying, boolean, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.get_id_notificante_new(cf character varying, nome character varying, cognome character varying, ins boolean, idtransazione bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare ret gds_types.result_type%ROWTYPE;
		proc_name varchar;
		rt varchar;
		id_op bigint;
		text_msg1 varchar;	
		text_msg2 varchar;	
		text_msg3 varchar;	
		text_msg4 varchar;
	begin
		proc_name:='gds_srv.get_id_notificante';
		id_op:=gds_log.start_op(proc_name,idtransazione ,cf||nome||cognome);
		ret:=gds_notifiche.get_id_notificante_new(cf , nome , cognome,ins,idtransazione ) ;
		--ret:=gds_ui.build_ret(ret,proc_name,-1);
		rt:=row_to_json(ret);
		id_op:=gds_log.end_op(proc_name,idtransazione ,rt::varchar);
		return rt;
			exception when others then
			GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			rt:=row_to_json(ret);
			id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
			return rt;
	end;
END
$$;


ALTER FUNCTION gds_srv.get_id_notificante_new(cf character varying, nome character varying, cognome character varying, ins boolean, idtransazione bigint) OWNER TO postgres;

--
-- Name: get_id_transazione(bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.get_id_transazione(iduser bigint) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
begin
	declare R_T gds_log.transazioni%ROWTYPE;
	proc_name varchar;
	id_op bigint;

	begin
		proc_name:='gds_srv.get_id_transazione';
	    R_T.id:=gds_log.get_id_transazione(iduser);
		id_op:=gds_log.start_op(proc_name, R_T.id ,'');
		id_op:=gds_log.end_op(proc_name, R_T.id , '');
		return R_T.id;

	end;
END
$$;


ALTER FUNCTION gds_srv.get_id_transazione(iduser bigint) OWNER TO postgres;

--
-- Name: get_verbale_valori(bigint, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.get_verbale_valori(idverbale bigint, idtransazione bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare
		str varchar;
		R record;
		ret gds_types.result_type;
		proc_name varchar;
		id_op bigint;
		id_mod bigint;
		text_msg1 varchar;	
		text_msg2 varchar;	
		text_msg3 varchar;	
		text_msg4 varchar;
	begin
		proc_name:='gds_srv.get_verbale_valori';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,coalesce(idverbale,-1));
		str:='{"esito":true,"valore":'||idverbale||',"msg":""';
		str:=str || ',"id_verbale":"'||idverbale||'"';
	
	    select id_modulo into ID_MOD from gds.vw_verbali vv where id_verbale = idverbale;
		str:=str || ',"id_modulo":"'||ID_MOD||'"';
	
		for R in SELECT vv.id_verbale,
		    vv.id AS id_valore,
		    vv.val,
		    mc.nome_campo,
		    mc.id AS id_modulo_campo,
		    mc.id_modulo
		    from gds_types.vw_modulo_campi mc
		    left JOIN gds.verbale_valori vv ON  vv.id_verbale = idverbale and mc.id = vv.id_modulo_campo
		    where mc.id_modulo  = ID_MOD loop
			str := str ||',"'|| R.nome_campo||'" : "'|| coalesce(R.val,'')||'"';
		end loop;
		str := str ||'}';
		--id_op:=gds_log.end_op(proc_name,idtransazione ,str);
		return str;
		exception when others then
			GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			str:=row_to_json(ret);
			--id_op:=gds_log.end_op(proc_name,idtransazione ,str);
			return str;
	end;
END
$$;


ALTER FUNCTION gds_srv.get_verbale_valori(idverbale bigint, idtransazione bigint) OWNER TO postgres;

--
-- Name: ins_ispezione(character varying, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.ins_ispezione(v character varying, idtransazione bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare
	proc_name varchar;
	ret gds_types.result_type%ROWTYPE;
	id_op bigint;
	rt character varying;
		text_msg1 varchar;	
		text_msg2 varchar;	
		text_msg3 varchar;	
		text_msg4 varchar;
begin
	proc_name:='gds_srv.ins_ispezione';
	id_op:=gds_log.start_op(proc_name,idtransazione ,v);
	ret:= gds.ins_ispezione(v,idtransazione) ;
	ret:=gds_ui.build_ret(ret,proc_name, ret.valore);
	rt:=row_to_json(ret);
	id_op:=gds_log.end_op(proc_name,idtransazione ,rt::varchar);
	return rt;
		exception when others then
			GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			rt:=row_to_json(ret);
			id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
			return rt;
end;
end
$$;


ALTER FUNCTION gds_srv.ins_ispezione(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: ins_notifica(character varying, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.ins_notifica(v character varying, idtransazione bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare
	proc_name varchar;
	ret gds_types.result_type%ROWTYPE;
	id_op bigint;
	rt character varying;
		text_msg1 varchar;	
		text_msg2 varchar;	
		text_msg3 varchar;	
		text_msg4 varchar;
begin
	proc_name:='gds_srv.ins_notifica';
	id_op:=gds_log.start_op(proc_name,idtransazione ,v);
	ret:= gds_notifiche.ins_notifica(v,idtransazione) ;
	ret:=gds_ui.build_ret(ret,proc_name, ret.valore);
	rt:=row_to_json(ret);
	id_op:=gds_log.end_op(proc_name,idtransazione ,rt::varchar);
	return rt;
		exception when others then
			GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			rt:=row_to_json(ret);
			id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
			return rt;
end;
end
$$;


ALTER FUNCTION gds_srv.ins_notifica(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: test(); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.test() RETURNS SETOF record
    LANGUAGE plpgsql
    AS $$
DECLARE
 rec record;
BEGIN
  select 1,2 into rec;
  return next rec;

  select 3,4 into rec;
  return next rec;
END $$;


ALTER FUNCTION gds_srv.test() OWNER TO postgres;

--
-- Name: upd_cantiere_imprese(character varying, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.upd_cantiere_imprese(v character varying, idtransazione bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare ret gds_types.result_type;
	proc_name varchar;
	rt varchar;
	id_op bigint;
		text_msg1 varchar;	
		text_msg2 varchar;	
		text_msg3 varchar;	
		text_msg4 varchar;
begin
	proc_name:='gds_srv.upd_cantiere_imprese';
	id_op:=gds_log.start_op(proc_name,idtransazione ,v,((v::json)->>'id_notifica')::int8);
	ret:=gds_notifiche.upd_cantiere_imprese(v,idtransazione) ;
	rt:=row_to_json(ret);
	id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
	return rt;
		exception when others then
			GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			rt:=row_to_json(ret);
			id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
			return rt;
end;
END
$$;


ALTER FUNCTION gds_srv.upd_cantiere_imprese(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_cantiere_persona_ruoli(character varying, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.upd_cantiere_persona_ruoli(v character varying, idtransazione bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare 
		ret gds_types.result_type;
		id_op bigint;
		proc_name varchar;
		rt varchar;
			text_msg1 varchar;	
		text_msg2 varchar;	
		text_msg3 varchar;	
		text_msg4 varchar;
	begin
		proc_name:='gds_srv.upd_cantiere_persona_ruoli';
		id_op:=gds_log.start_op(proc_name,idtransazione ,v,((v::json)->>'id_notifica')::int8);
		ret:= gds_notifiche.upd_cantiere_persona_ruoli(v,idtransazione) ;
		--ret:=gds_ui.build_ret(ret,proc_name,-1);
		rt:=row_to_json(ret);
		id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
		return rt;
			exception when others then
			GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			rt:=row_to_json(ret);
			id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
			return rt;
	end;
END
$$;


ALTER FUNCTION gds_srv.upd_cantiere_persona_ruoli(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_dati(character varying, character varying, bigint); Type: PROCEDURE; Schema: gds_srv; Owner: postgres
--

CREATE PROCEDURE gds_srv.upd_dati(IN operazione character varying, IN v character varying, IN idutente bigint, OUT joutput json)
    LANGUAGE plpgsql
    AS $$
begin
	declare
		proc_name varchar;
	begin
		proc_name:='gds_srv.upd_dati';
		call gds.upd_dati(operazione,v,idutente,joutput);
	end;
END
$$;


ALTER PROCEDURE gds_srv.upd_dati(IN operazione character varying, IN v character varying, IN idutente bigint, OUT joutput json) OWNER TO postgres;

--
-- Name: upd_ispezione(character varying, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.upd_ispezione(v character varying, idtransazione bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare ret gds_types.result_type;
		id_op bigint;
		proc_name varchar;
		rt varchar;
		text_msg1 varchar;	
		text_msg2 varchar;	
		text_msg3 varchar;	
		text_msg4 varchar;

	begin
		proc_name:='gds_srv.upd_ispezione';
		id_op:=gds_log.start_op(proc_name,idtransazione ,v);
		ret:= gds.upd_ispezione(v,idtransazione) ;
		--ret:= gds_ui.build_ret(ret,proc_name,-1);
		rt:=row_to_json(ret);
		id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
		return rt;
		exception when others then
			GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			rt:=row_to_json(ret);
			id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
			return rt;
	end;
END
$$;


ALTER FUNCTION gds_srv.upd_ispezione(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_ispezione_fase(character varying, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.upd_ispezione_fase(v character varying, idtransazione bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare ret gds_types.result_type;
		id_op bigint;
		proc_name varchar;
		rt varchar;
		text_msg1 varchar;	
		text_msg2 varchar;	
		text_msg3 varchar;	
		text_msg4 varchar;

	begin
		proc_name:='gds_srv.upd_ispezione_fase';
		id_op:=gds_log.start_op(proc_name,idtransazione ,v);
		ret:= gds.upd_ispezione_fase(v,idtransazione) ;
		--ret:= gds_ui.build_ret(ret,proc_name,-1);
		rt:=row_to_json(ret);
		id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
		return rt;
		exception when others then
			GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			rt:=row_to_json(ret);
			id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
			return rt;
	end;
END
$$;


ALTER FUNCTION gds_srv.upd_ispezione_fase(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_notifica(character varying, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.upd_notifica(v character varying, idtransazione bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare ret gds_types.result_type;
		id_op bigint;
		proc_name varchar;
		rt varchar;
		text_msg1 varchar;	
		text_msg2 varchar;	
		text_msg3 varchar;	
		text_msg4 varchar;

	begin
		proc_name:='gds_srv.upd_notifica';
		id_op:=gds_log.start_op(proc_name,idtransazione ,v);
		ret:= gds_notifiche.upd_notifica(v,idtransazione) ;
		--ret:= gds_ui.build_ret(ret,proc_name,-1);
		rt:=row_to_json(ret);
		id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
		return rt;
		exception when others then
			GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			rt:=row_to_json(ret);
			id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
			return rt;
	end;
END
$$;


ALTER FUNCTION gds_srv.upd_notifica(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_verbale_valori(character varying, bigint); Type: FUNCTION; Schema: gds_srv; Owner: postgres
--

CREATE FUNCTION gds_srv.upd_verbale_valori(v character varying, idtransazione bigint) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
begin
	declare ret gds_types.result_type;
		id_op bigint;
		proc_name varchar;
		rt varchar;
			text_msg1 varchar;	
		text_msg2 varchar;	
		text_msg3 varchar;	
		text_msg4 varchar;
	begin
		proc_name:='gds_srv.upd_verbale_valori';
		--id_op:=gds_log.start_op(proc_name,idtransazione ,'');
		ret:= gds.upd_verbale_valori(v, idtransazione) ;
		ret:= gds_ui.build_ret(ret,proc_name,-1);
		rt:=row_to_json(ret);
		--id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
		return rt;
			exception when others then
			GET STACKED DIAGNOSTICS text_msg1 = MESSAGE_TEXT,
                          			text_msg2 = PG_EXCEPTION_DETAIL,
                          			text_msg3 = PG_EXCEPTION_HINT,
                         			text_msg4 = PG_EXCEPTION_CONTEXT;
			ret.esito:=false;
			ret.valore:= null;
			ret.msg:=coalesce(text_msg1,'')|| chr(10) ||coalesce(text_msg2,'')|| chr(10)  ||coalesce(text_msg3,'')|| chr(10)  ||coalesce(text_msg4,'');
			rt:=row_to_json(ret);
			--id_op:=gds_log.end_op(proc_name,idtransazione ,rt);
			return rt;
	end;
END
$$;


ALTER FUNCTION gds_srv.upd_verbale_valori(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: upd_tipi_macchina(character varying, bigint); Type: FUNCTION; Schema: gds_types; Owner: postgres
--

CREATE FUNCTION gds_types.upd_tipi_macchina(v character varying, idtransazione bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare 
	
            ret gds_types.result_type; -- START
		    id_op bigint;   
		    proc_name varchar; -- END
	        R_tipo_macchina     gds_types.tipi_macchina%ROWTYPE;
	       	R_tipo_macchina_old gds_types.tipi_macchina%ROWTYPE;
	       	R_tipo_macchina_new gds_types.tipi_macchina%ROWTYPE;
            n bigint;
    begin
		
		proc_name:='gds_types.upd_tipi_macchina';
	    id_op:=gds_log.start_op(proc_name,idtransazione ,v);
        R_tipo_macchina.descr:=  trim(json_extract_path(v::json,'descr_tipo_macchina'));
        if R_tipo_macchina.descr is null or R_tipo_macchina.descr = '' then
        	ret.valore:=null;
        	ret.esito=false;
        	ret.msg:='';
        	return ret;
        end if;
       	select * into R_tipo_macchina_old where descr=R_tipo_macchina.descr;
        if R_tipo_macchina_old.id is  null then
            insert into gds_types.tipi_macchina (                                       id,  descr)
       									 values (nextval('gds_types.tipi_macchina_id_seq'),R_tipo_macchina.descr);
       		select * into R_tipo_macchina_new from gds_macchine.macchine
       	    where id = currval('gds_types.tipi_macchina_id_seq');
       		n:=gds_log.upd_record(proc_name,idtransazione,R_tipo_macchina_new,'I');   
        end if; 
	    ret.esito:=true;	
		ret:=gds_ui.build_ret(ret,proc_name,R_tipo_macchina_new.id);
		id_op:=gds_log.end_op(proc_name,idtransazione ,row_to_json(ret)::varchar);
		return ret;
	end;
END
$$;


ALTER FUNCTION gds_types.upd_tipi_macchina(v character varying, idtransazione bigint) OWNER TO postgres;

--
-- Name: build_ret(gds_types.result_type, character varying, bigint); Type: FUNCTION; Schema: gds_ui; Owner: postgres
--

CREATE FUNCTION gds_ui.build_ret(r gds_types.result_type, pname character varying, val bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare 
	begin
		r.valore:= val;
		if not r.esito then
			--r.msg:=coalesce(r.msg,'') || ' ('|| coalesce(r.valore,-1)||')' ;
			r.msg:=coalesce(r.msg,'')||' '||CHR(13)||gds_notifiche.get_proc_msg(pname,r.valore)||' ['||coalesce(pname,'PROC UNDEFINED')||' ('||val||') ] ';
		end if;
		return r;
	end;
END
$$;


ALTER FUNCTION gds_ui.build_ret(r gds_types.result_type, pname character varying, val bigint) OWNER TO postgres;

--
-- Name: build_ret_ok(bigint); Type: FUNCTION; Schema: gds_ui; Owner: postgres
--

CREATE FUNCTION gds_ui.build_ret_ok(val bigint) RETURNS gds_types.result_type
    LANGUAGE plpgsql
    AS $$
begin
	declare r gds_types.result_type;
	begin
		r.esito:=true;
		r.valore :=val;
		r.msg:='';
		return r;
	end;
END
$$;


ALTER FUNCTION gds_ui.build_ret_ok(val bigint) OWNER TO postgres;

--
-- Name: ispezioni_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.ispezioni_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.ispezioni_id_seq OWNER TO postgres;

--
-- Name: ispezioni; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.ispezioni (
    id bigint DEFAULT nextval('gds.ispezioni_id_seq'::regclass) NOT NULL,
    id_cantiere bigint,
    id_motivo_isp bigint,
    altro character varying,
    id_ente_uo bigint,
    descr character varying,
    id_stato_ispezione bigint,
    per_conto_di bigint,
    data_inserimento timestamp without time zone,
    data_modifica timestamp without time zone,
    note character varying(500),
    data_ispezione timestamp without time zone,
    codice_ispezione character varying
);


ALTER TABLE gds.ispezioni OWNER TO postgres;

--
-- Name: cantieri; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche.cantieri (
    id bigint NOT NULL,
    id_natura_opera bigint,
    id_notifica bigint,
    denominazione character varying(255),
    data_presunta timestamp without time zone,
    durata_presunta integer,
    numero_imprese integer,
    numero_lavoratori integer,
    ammontare double precision,
    altro character varying,
    id_indirizzo bigint,
    cuc character varying
);


ALTER TABLE gds_notifiche.cantieri OWNER TO postgres;

--
-- Name: email_asl; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche.email_asl (
    id integer NOT NULL,
    id_asl integer,
    address text
);


ALTER TABLE gds_notifiche.email_asl OWNER TO postgres;

--
-- Name: notifiche; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche.notifiche (
    id bigint NOT NULL,
    id_soggetto_fisico bigint,
    data_notifica timestamp without time zone,
    id_stato bigint,
    cun character varying,
    id_notifica_succ bigint,
    data_modifica timestamp without time zone
);


ALTER TABLE gds_notifiche.notifiche OWNER TO postgres;

--
-- Name: vw_opu_indirizzi; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_opu_indirizzi AS
 SELECT opu_indirizzo.id,
    opu_indirizzo.id AS id_indirizzo,
    opu_indirizzo.via,
    opu_indirizzo.comune AS id_comune,
    c.nome AS comune,
    TRIM(BOTH FROM opu_indirizzo.cap) AS cap,
    l.cod_provincia,
    opu_indirizzo.latitudine AS lat,
    opu_indirizzo.longitudine AS lng,
    opu_indirizzo.civico,
    c.codiceistatasl,
    c.istat AS codiceistatcomune
   FROM ((public.opu_indirizzo
     LEFT JOIN public.comuni1 c ON ((opu_indirizzo.comune = c.id)))
     LEFT JOIN public.lookup_province l ON (((c.cod_provincia)::integer = l.code)));


ALTER TABLE gds_notifiche.vw_opu_indirizzi OWNER TO postgres;

--
-- Name: vw_opu_soggetti_fisici; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_opu_soggetti_fisici AS
 SELECT opu_soggetto_fisico.id,
    opu_soggetto_fisico.id AS id_soggetto_fisico,
    opu_soggetto_fisico.nome,
    opu_soggetto_fisico.cognome,
    opu_soggetto_fisico.indirizzo_id,
    opu_soggetto_fisico.codice_fiscale,
    opu_soggetto_fisico.email
   FROM public.opu_soggetto_fisico;


ALTER TABLE gds_notifiche.vw_opu_soggetti_fisici OWNER TO postgres;

--
-- Name: vw_soggetti_fisici; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_soggetti_fisici AS
 SELECT s.id,
    s.id AS id_soggetto_fisico,
    s.nome,
    s.cognome,
    s.codice_fiscale,
    s.email,
    s.indirizzo_id AS id_indirizzo,
    i.via,
    i.comune,
    i.cap,
    i.id_comune,
    i.cod_provincia,
    i.civico
   FROM (gds_notifiche.vw_opu_soggetti_fisici s
     LEFT JOIN gds_notifiche.vw_opu_indirizzi i ON ((s.indirizzo_id = i.id)));


ALTER TABLE gds_notifiche.vw_soggetti_fisici OWNER TO postgres;

--
-- Name: stati; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.stati (
    id bigint NOT NULL,
    descr character varying,
    modificabile boolean,
    modificabile_cantiere boolean,
    scaricabile boolean,
    visibile boolean,
    genera_pdf boolean,
    controlli boolean,
    attivo boolean
);


ALTER TABLE gds_types.stati OWNER TO postgres;

--
-- Name: vw_stati; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_stati AS
 SELECT st.id,
    st.id AS id_stato,
    st.descr,
    st.descr AS stato,
    st.modificabile,
    st.modificabile_cantiere,
    st.scaricabile,
    st.genera_pdf,
    st.controlli,
    st.attivo
   FROM gds_types.stati st;


ALTER TABLE gds_types.vw_stati OWNER TO postgres;

--
-- Name: vw_notifiche; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_notifiche AS
 SELECT n.id AS id_notifica,
    n.id,
    n.id_soggetto_fisico,
    n.data_notifica,
    n.id_soggetto_fisico AS id_soggetto_notificante,
    s.nome AS nome_notificante,
    s.cognome AS cognome_notificante,
    s.codice_fiscale AS cf_notificante,
    n.id_stato,
    st.stato,
    n.data_modifica,
    c.denominazione,
    st.modificabile,
    s.via,
    s.comune,
    s.cod_provincia,
    s.id_comune,
    s.cap,
    s.id_indirizzo,
    n.cun,
    c.id AS id_cantiere,
    st.modificabile_cantiere,
    c.cuc,
    s.civico,
    st.scaricabile,
    n.id_notifica_succ,
    st.attivo
   FROM (((gds_notifiche.notifiche n
     LEFT JOIN gds_notifiche.vw_soggetti_fisici s ON ((n.id_soggetto_fisico = s.id_soggetto_fisico)))
     JOIN gds_types.vw_stati st ON ((st.id_stato = n.id_stato)))
     LEFT JOIN gds_notifiche.cantieri c ON ((c.id_notifica = n.id)));


ALTER TABLE gds_notifiche.vw_notifiche OWNER TO postgres;

--
-- Name: nature_opera; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.nature_opera (
    id bigint NOT NULL,
    descr character varying
);


ALTER TABLE gds_types.nature_opera OWNER TO postgres;

--
-- Name: vw_nature_opera; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_nature_opera AS
 SELECT nt.id,
    nt.id AS id_natura_opera,
    nt.descr,
    nt.descr AS natura_opera
   FROM gds_types.nature_opera nt;


ALTER TABLE gds_types.vw_nature_opera OWNER TO postgres;

--
-- Name: vw_cantieri; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_cantieri AS
 SELECT c.id AS id_cantiere,
    c.id,
    c.id_natura_opera,
    c.id_notifica,
    c.denominazione,
    c.data_presunta,
    c.durata_presunta,
    c.numero_imprese,
    c.numero_lavoratori,
    c.ammontare,
    c.altro,
    c.id_indirizzo,
    n.natura_opera,
    nt.id_soggetto_notificante,
    nt.data_notifica,
    nt.nome_notificante,
    nt.cognome_notificante,
    nt.cf_notificante,
    nt.via AS via_notificante,
    nt.comune AS comune_notificante,
    nt.id_comune AS id_comune_notificante,
    nt.cod_provincia AS cod_provincia_notificante,
    i.via,
    i.comune,
    i.id_comune,
    i.cap,
    i.cod_provincia,
    i.lat,
    i.lng,
    nt.id_stato,
    nt.stato,
    nt.cap AS cap_notificante,
    nt.id_indirizzo AS id_indirizzo_notificante,
    c.cuc,
    nt.cun,
    i.civico,
    nt.civico AS civico_notificante,
    i.codiceistatasl,
    i.codiceistatcomune,
    ea.address AS indirizzo_mail,
    nt.scaricabile,
    nt.id_notifica_succ,
    lsi.description AS descr_asl,
    lsi.short_description AS short_descr_asl,
    nt.attivo
   FROM (((((gds_notifiche.cantieri c
     JOIN gds_notifiche.vw_notifiche nt ON ((c.id_notifica = nt.id_notifica)))
     LEFT JOIN gds_types.vw_nature_opera n ON ((n.id_natura_opera = c.id_natura_opera)))
     LEFT JOIN gds_notifiche.vw_opu_indirizzi i ON ((i.id_indirizzo = c.id_indirizzo)))
     LEFT JOIN gds_notifiche.email_asl ea ON (((ea.id_asl)::text = (i.codiceistatasl)::text)))
     LEFT JOIN public.lookup_site_id lsi ON (((lsi.codiceistat)::text = (i.codiceistatasl)::text)));


ALTER TABLE gds_notifiche.vw_cantieri OWNER TO postgres;

--
-- Name: ente_uo_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.ente_uo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.ente_uo_id_seq OWNER TO postgres;

--
-- Name: ente_uo; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.ente_uo (
    id bigint DEFAULT nextval('gds_types.ente_uo_id_seq'::regclass) NOT NULL,
    id_ente bigint,
    id_uo bigint
);


ALTER TABLE gds_types.ente_uo OWNER TO postgres;

--
-- Name: enti_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.enti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.enti_id_seq OWNER TO postgres;

--
-- Name: enti; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.enti (
    id bigint DEFAULT nextval('gds_types.enti_id_seq'::regclass) NOT NULL,
    descr_ente character varying,
    id_asl integer
);


ALTER TABLE gds_types.enti OWNER TO postgres;

--
-- Name: motivi_isp_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.motivi_isp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.motivi_isp_id_seq OWNER TO postgres;

--
-- Name: motivi_isp; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.motivi_isp (
    id bigint DEFAULT nextval('gds_types.motivi_isp_id_seq'::regclass) NOT NULL,
    descr character varying NOT NULL,
    descr_interna character varying
);


ALTER TABLE gds_types.motivi_isp OWNER TO postgres;

--
-- Name: stati_ispezione; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.stati_ispezione (
    id bigint NOT NULL,
    descr character varying,
    modificabile boolean
);


ALTER TABLE gds_types.stati_ispezione OWNER TO postgres;

--
-- Name: uo_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.uo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.uo_id_seq OWNER TO postgres;

--
-- Name: uo; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.uo (
    id bigint DEFAULT nextval('gds_types.uo_id_seq'::regclass) NOT NULL,
    descr_uo character varying
);


ALTER TABLE gds_types.uo OWNER TO postgres;

--
-- Name: vw_enti; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_enti AS
 SELECT enti.id AS id_ente,
    enti.id,
    enti.descr_ente,
    enti.id_asl
   FROM gds_types.enti;


ALTER TABLE gds_types.vw_enti OWNER TO postgres;

--
-- Name: vw_uo; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_uo AS
 SELECT uo.id AS id_uo,
    uo.id,
    uo.descr_uo
   FROM gds_types.uo;


ALTER TABLE gds_types.vw_uo OWNER TO postgres;

--
-- Name: vw_ente_uo; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_ente_uo AS
 SELECT eu.id,
    eu.id AS id_ente_uo,
    eu.id_ente,
    eu.id_uo,
    e.descr_ente,
    u.descr_uo,
    (((e.descr_ente)::text || ' - '::text) || (u.descr_uo)::text) AS descr_ente_uo,
    e.id_asl
   FROM ((gds_types.ente_uo eu
     JOIN gds_types.vw_enti e ON ((e.id_ente = eu.id_ente)))
     JOIN gds_types.vw_uo u ON ((u.id_uo = eu.id_uo)));


ALTER TABLE gds_types.vw_ente_uo OWNER TO postgres;

--
-- Name: vw_motivi_isp; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_motivi_isp AS
 SELECT motivi_isp.id AS id_motivo_isp,
    motivi_isp.id,
    motivi_isp.descr,
    motivi_isp.descr_interna
   FROM gds_types.motivi_isp;


ALTER TABLE gds_types.vw_motivi_isp OWNER TO postgres;

--
-- Name: vw_stati_ispezione; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_stati_ispezione AS
 SELECT stati_ispezione.id AS id_stato_ispezione,
    stati_ispezione.id,
    stati_ispezione.descr,
    stati_ispezione.modificabile,
    stati_ispezione.descr AS descr_stato_ispezione
   FROM gds_types.stati_ispezione;


ALTER TABLE gds_types.vw_stati_ispezione OWNER TO postgres;

--
-- Name: vw_ispezioni; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_ispezioni AS
 SELECT i.id,
    i.id AS id_ispezione,
    i.id_cantiere,
    i.id_motivo_isp,
    mi.descr AS descr_motivo_isp,
    c.denominazione,
    i.altro,
    i.id_ente_uo,
    eu.descr_ente_uo,
    i.id_stato_ispezione,
    si.descr_stato_ispezione,
    i.per_conto_di,
    (('ASL '::text || (s.description)::text))::character varying AS descr_ente,
    i.data_inserimento,
    i.data_modifica,
    i.data_ispezione,
    eu.descr_ente AS descr_ente_isp,
    eu.descr_uo AS descr_uo_isp,
        CASE
            WHEN (i.id_cantiere IS NULL) THEN 'Impresa'::text
            ELSE 'Cantiere'::text
        END AS cantiere_o_impresa,
    s.short_description,
    c.data_notifica,
    c.comune,
    c.cod_provincia,
    c.via,
    c.civico,
    c.natura_opera,
    i.note,
    i.codice_ispezione,
    si.modificabile,
    i.descr AS descr_isp,
    i.descr,
    c.cun,
    c.cuc,
    c.id_notifica
   FROM (((((gds.ispezioni i
     LEFT JOIN gds_notifiche.vw_cantieri c ON ((c.id = i.id_cantiere)))
     LEFT JOIN gds_types.vw_motivi_isp mi ON ((mi.id = i.id_motivo_isp)))
     LEFT JOIN gds_types.vw_ente_uo eu ON ((eu.id_ente_uo = i.id_ente_uo)))
     LEFT JOIN gds_types.vw_stati_ispezione si ON ((si.id_stato_ispezione = i.id_stato_ispezione)))
     LEFT JOIN public.lookup_site_id s ON ((s.code = i.per_conto_di)));


ALTER TABLE gds.vw_ispezioni OWNER TO postgres;

--
-- Name: _h_allegati; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds._h_allegati (
    id bigint NOT NULL,
    id_allegato bigint,
    descrizione character varying,
    nome character varying NOT NULL,
    file character varying,
    preview character varying,
    data_inserimento timestamp without time zone,
    id_operatore bigint,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds._h_allegati OWNER TO postgres;

--
-- Name: _h_allegati_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds._h_allegati_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds._h_allegati_id_seq OWNER TO postgres;

--
-- Name: _h_fase_persone; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds._h_fase_persone (
    id bigint NOT NULL,
    id_fase_persona bigint,
    id_soggetto_fisico bigint NOT NULL,
    id_ispezione_fase bigint NOT NULL,
    data_inserimento timestamp without time zone NOT NULL,
    ordine integer,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds._h_fase_persone OWNER TO postgres;

--
-- Name: _h_fase_persone_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds._h_fase_persone_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds._h_fase_persone_id_seq OWNER TO postgres;

--
-- Name: _h_fase_verbali; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds._h_fase_verbali (
    id bigint NOT NULL,
    id_fase_verbale bigint,
    id_ispezione_fase bigint NOT NULL,
    id_verbale bigint,
    data timestamp without time zone NOT NULL,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds._h_fase_verbali OWNER TO postgres;

--
-- Name: _h_fase_verbali_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds._h_fase_verbali_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds._h_fase_verbali_id_seq OWNER TO postgres;

--
-- Name: _h_impresa_sedi; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds._h_impresa_sedi (
    id bigint NOT NULL,
    id_impresa_sede bigint,
    id_impresa bigint,
    id_tipo_sede bigint,
    codice_pat character varying,
    datainizio timestamp without time zone,
    datafine timestamp without time zone,
    id_indirizzo bigint,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds._h_impresa_sedi OWNER TO postgres;

--
-- Name: _h_impresa_sedi_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds._h_impresa_sedi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds._h_impresa_sedi_id_seq OWNER TO postgres;

--
-- Name: _h_imprese; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds._h_imprese (
    id bigint NOT NULL,
    id_impresa bigint,
    codice_inail character varying,
    codice_fiscale character varying,
    partita_iva character varying,
    id_natura_giuridica bigint,
    nome_azienda character varying,
    codice_ateco character varying,
    tipo_ateco character varying,
    anno_competenza integer,
    numero_dipendenti integer,
    numero_artigiani integer,
    addetti_speciali integer,
    note character varying,
    data_inserimento timestamp without time zone,
    operatore_inserimento bigint,
    data_modifica timestamp without time zone,
    operatore_modifica bigint,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds._h_imprese OWNER TO postgres;

--
-- Name: _h_imprese_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds._h_imprese_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds._h_imprese_id_seq OWNER TO postgres;

--
-- Name: _h_ispezione_fasi; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds._h_ispezione_fasi (
    id bigint NOT NULL,
    id_ispezione_fase bigint NOT NULL,
    id_ispezione bigint NOT NULL,
    data_creazione timestamp without time zone NOT NULL,
    data_modifica timestamp without time zone NOT NULL,
    id_impresa_sede bigint,
    id_fase_esito bigint,
    data_fase timestamp without time zone,
    note character varying(512),
    altro_esito character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds._h_ispezione_fasi OWNER TO postgres;

--
-- Name: _h_ispezione_fasi_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds._h_ispezione_fasi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds._h_ispezione_fasi_id_seq OWNER TO postgres;

--
-- Name: _h_ispezione_imprese; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds._h_ispezione_imprese (
    id bigint NOT NULL,
    id_ispezione_impresa bigint,
    id_ispezione bigint,
    id_impresa_sede bigint,
    progressivo integer,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds._h_ispezione_imprese OWNER TO postgres;

--
-- Name: _h_ispezione_imprese_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds._h_ispezione_imprese_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds._h_ispezione_imprese_id_seq OWNER TO postgres;

--
-- Name: _h_ispezioni; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds._h_ispezioni (
    id bigint NOT NULL,
    id_ispezione bigint,
    id_cantiere bigint,
    id_motivo_isp bigint,
    altro character varying,
    id_ente_uo bigint,
    descr character varying,
    id_stato_ispezione bigint,
    per_conto_di bigint,
    data_inserimento timestamp without time zone,
    data_modifica timestamp without time zone,
    note character varying(500),
    data_ispezione timestamp without time zone,
    codice_ispezione character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds._h_ispezioni OWNER TO postgres;

--
-- Name: _h_ispezioni_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds._h_ispezioni_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds._h_ispezioni_id_seq OWNER TO postgres;

--
-- Name: _h_nucleo_ispettori; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds._h_nucleo_ispettori (
    id bigint NOT NULL,
    id_nucleo_ispettore bigint,
    id_ispezione bigint,
    id_soggetto_fisico bigint NOT NULL,
    progressivo bigint NOT NULL,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds._h_nucleo_ispettori OWNER TO postgres;

--
-- Name: _h_nucleo_ispettori_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds._h_nucleo_ispettori_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds._h_nucleo_ispettori_id_seq OWNER TO postgres;

--
-- Name: _h_verbale_valori; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds._h_verbale_valori (
    id bigint NOT NULL,
    id_verbale_valore bigint,
    id_verbale bigint,
    id_modulo_campo bigint,
    val character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds._h_verbale_valori OWNER TO postgres;

--
-- Name: _h_verbale_valori_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds._h_verbale_valori_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds._h_verbale_valori_id_seq OWNER TO postgres;

--
-- Name: _h_verbali; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds._h_verbali (
    id bigint NOT NULL,
    id_verbale bigint,
    id_modulo bigint,
    ts timestamp without time zone,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds._h_verbali OWNER TO postgres;

--
-- Name: _h_verbali_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds._h_verbali_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds._h_verbali_id_seq OWNER TO postgres;

--
-- Name: allegati; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.allegati (
    id bigint NOT NULL,
    descrizione character varying,
    nome character varying NOT NULL,
    file character varying,
    preview character varying,
    data_inserimento timestamp without time zone,
    id_operatore bigint
);


ALTER TABLE gds.allegati OWNER TO postgres;

--
-- Name: allegati_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.allegati_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.allegati_id_seq OWNER TO postgres;

--
-- Name: allegati_id_seq; Type: SEQUENCE OWNED BY; Schema: gds; Owner: postgres
--

ALTER SEQUENCE gds.allegati_id_seq OWNED BY gds.allegati.id;


--
-- Name: fase_persone; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.fase_persone (
    id bigint NOT NULL,
    id_soggetto_fisico bigint NOT NULL,
    id_ispezione_fase bigint NOT NULL,
    data_inserimento timestamp without time zone NOT NULL,
    ordine integer
);


ALTER TABLE gds.fase_persone OWNER TO postgres;

--
-- Name: fase_persone_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.fase_persone_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.fase_persone_id_seq OWNER TO postgres;

--
-- Name: fase_persone_id_seq; Type: SEQUENCE OWNED BY; Schema: gds; Owner: postgres
--

ALTER SEQUENCE gds.fase_persone_id_seq OWNED BY gds.fase_persone.id;


--
-- Name: fase_verbali; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.fase_verbali (
    id bigint NOT NULL,
    id_ispezione_fase bigint NOT NULL,
    id_verbale bigint,
    data timestamp without time zone NOT NULL
);


ALTER TABLE gds.fase_verbali OWNER TO postgres;

--
-- Name: fase_verbali_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.fase_verbali_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.fase_verbali_id_seq OWNER TO postgres;

--
-- Name: fase_verbali_id_seq; Type: SEQUENCE OWNED BY; Schema: gds; Owner: postgres
--

ALTER SEQUENCE gds.fase_verbali_id_seq OWNED BY gds.fase_verbali.id;


--
-- Name: impresa_sedi; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.impresa_sedi (
    id bigint NOT NULL,
    id_impresa bigint,
    id_tipo_sede bigint,
    codice_pat character varying,
    datainizio timestamp without time zone,
    datafine timestamp without time zone,
    id_indirizzo bigint
);


ALTER TABLE gds.impresa_sedi OWNER TO postgres;

--
-- Name: impresa_sedi_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.impresa_sedi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.impresa_sedi_id_seq OWNER TO postgres;

--
-- Name: impresa_sedi_id_seq; Type: SEQUENCE OWNED BY; Schema: gds; Owner: postgres
--

ALTER SEQUENCE gds.impresa_sedi_id_seq OWNED BY gds.impresa_sedi.id;


--
-- Name: imprese; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.imprese (
    id bigint,
    codice_inail character varying,
    codice_fiscale character varying,
    partita_iva character varying,
    id_natura_giuridica bigint,
    ragione_sociale character varying,
    codice_ateco character varying,
    tipo_ateco character varying,
    anno_competenza integer,
    numero_dipendenti integer,
    numero_artigiani integer,
    addetti_speciali integer,
    note character varying,
    data_inserimento timestamp without time zone,
    operatore_inserimento bigint,
    data_modifica timestamp without time zone,
    operatore_modifica bigint
);


ALTER TABLE gds.imprese OWNER TO postgres;

--
-- Name: imprese_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.imprese_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.imprese_id_seq OWNER TO postgres;

--
-- Name: ispezione_allegati; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.ispezione_allegati (
    id bigint NOT NULL,
    id_allegato bigint
);


ALTER TABLE gds.ispezione_allegati OWNER TO postgres;

--
-- Name: ispezione_allegati_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.ispezione_allegati_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.ispezione_allegati_id_seq OWNER TO postgres;

--
-- Name: ispezione_allegati_id_seq; Type: SEQUENCE OWNED BY; Schema: gds; Owner: postgres
--

ALTER SEQUENCE gds.ispezione_allegati_id_seq OWNED BY gds.ispezione_allegati.id;


--
-- Name: ispezione_fasi_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.ispezione_fasi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.ispezione_fasi_id_seq OWNER TO postgres;

--
-- Name: ispezione_fasi; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.ispezione_fasi (
    id bigint DEFAULT nextval('gds.ispezione_fasi_id_seq'::regclass) NOT NULL,
    id_ispezione bigint NOT NULL,
    data_creazione timestamp without time zone NOT NULL,
    data_modifica timestamp without time zone NOT NULL,
    id_impresa_sede bigint,
    id_fase_esito bigint,
    data_fase timestamp without time zone,
    note character varying(512),
    altro_esito character varying
);


ALTER TABLE gds.ispezione_fasi OWNER TO postgres;

--
-- Name: ispezione_imprese; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.ispezione_imprese (
    id bigint NOT NULL,
    id_ispezione bigint NOT NULL,
    id_impresa_sede bigint NOT NULL,
    progressivo integer
);


ALTER TABLE gds.ispezione_imprese OWNER TO postgres;

--
-- Name: ispezione_imprese_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.ispezione_imprese_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.ispezione_imprese_id_seq OWNER TO postgres;

--
-- Name: ispezione_imprese_id_seq; Type: SEQUENCE OWNED BY; Schema: gds; Owner: postgres
--

ALTER SEQUENCE gds.ispezione_imprese_id_seq OWNED BY gds.ispezione_imprese.id;


--
-- Name: nucleo_ispettori_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.nucleo_ispettori_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.nucleo_ispettori_id_seq OWNER TO postgres;

--
-- Name: nucleo_ispettori; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.nucleo_ispettori (
    id bigint DEFAULT nextval('gds.nucleo_ispettori_id_seq'::regclass) NOT NULL,
    id_ispezione bigint,
    id_access bigint NOT NULL,
    progressivo bigint NOT NULL,
    data_inserimento timestamp without time zone
);


ALTER TABLE gds.nucleo_ispettori OWNER TO postgres;

--
-- Name: rt; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.rt (
    json_agg json
);


ALTER TABLE gds.rt OWNER TO postgres;

--
-- Name: verbale_valori; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.verbale_valori (
    id bigint NOT NULL,
    id_verbale bigint,
    id_modulo_campo bigint,
    val character varying
);


ALTER TABLE gds.verbale_valori OWNER TO postgres;

--
-- Name: verbale_valori_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.verbale_valori_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.verbale_valori_id_seq OWNER TO postgres;

--
-- Name: verbale_valori_id_seq; Type: SEQUENCE OWNED BY; Schema: gds; Owner: postgres
--

ALTER SEQUENCE gds.verbale_valori_id_seq OWNED BY gds.verbale_valori.id;


--
-- Name: verbali; Type: TABLE; Schema: gds; Owner: postgres
--

CREATE TABLE gds.verbali (
    id bigint NOT NULL,
    id_modulo bigint,
    data_verbale timestamp without time zone
);


ALTER TABLE gds.verbali OWNER TO postgres;

--
-- Name: verbali_id_seq; Type: SEQUENCE; Schema: gds; Owner: postgres
--

CREATE SEQUENCE gds.verbali_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds.verbali_id_seq OWNER TO postgres;

--
-- Name: verbali_id_seq; Type: SEQUENCE OWNED BY; Schema: gds; Owner: postgres
--

ALTER SEQUENCE gds.verbali_id_seq OWNED BY gds.verbali.id;


--
-- Name: vw_access; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_access AS
 SELECT c.contact_id,
    c.namefirst,
    c.namelast,
    c.codice_fiscale,
    r.description,
    s.id
   FROM (((public.access_ a
     JOIN public.contact_ c ON ((a.contact_id = c.contact_id)))
     LEFT JOIN public.role r ON ((a.role_id = r.role_id)))
     LEFT JOIN public.opu_soggetto_fisico s ON (((s.codice_fiscale)::text = (c.codice_fiscale)::text)));


ALTER TABLE gds.vw_access OWNER TO postgres;

--
-- Name: vw_access_notificante; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_access_notificante AS
 SELECT c.contact_id,
    c.namefirst,
    c.namelast,
    c.codice_fiscale,
    r.description,
    s.id,
    a.site_id
   FROM (((public.access_ a
     JOIN public.contact_ c ON ((a.contact_id = c.contact_id)))
     JOIN public.role r ON ((a.role_id = r.role_id)))
     LEFT JOIN public.opu_soggetto_fisico s ON (((s.codice_fiscale)::text = (c.codice_fiscale)::text)))
  WHERE r.enabled;


ALTER TABLE gds.vw_access_notificante OWNER TO postgres;

--
-- Name: natura_giuridica; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.natura_giuridica (
    id bigint NOT NULL,
    codice character varying,
    abb character varying,
    descrizione character varying
);


ALTER TABLE gds_types.natura_giuridica OWNER TO postgres;

--
-- Name: vw_natura_giuridica; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_natura_giuridica AS
 SELECT ng.id,
    ng.id AS id_natura_giuridica,
    ng.codice AS codice_natura_giuridica,
    ng.abb AS abb_natura_giuridica,
    ng.descrizione AS descrizione_natura_giuridica
   FROM gds_types.natura_giuridica ng;


ALTER TABLE gds_types.vw_natura_giuridica OWNER TO postgres;

--
-- Name: vw_imprese; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_imprese AS
 SELECT i.id,
    i.id AS id_impresa,
    i.ragione_sociale AS nome_azienda,
    i.partita_iva,
    ng.abb_natura_giuridica,
    ng.descrizione_natura_giuridica,
    i.codice_fiscale
   FROM (gds.imprese i
     LEFT JOIN gds_types.vw_natura_giuridica ng ON ((ng.id_natura_giuridica = i.id_natura_giuridica)));


ALTER TABLE gds.vw_imprese OWNER TO postgres;

--
-- Name: tipi_sede; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.tipi_sede (
    id bigint NOT NULL,
    codice integer,
    descrizione character varying
);


ALTER TABLE gds_types.tipi_sede OWNER TO postgres;

--
-- Name: vw_impresa_sedi; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_impresa_sedi AS
 SELECT s.id,
    s.id AS id_impresa_sede,
    i.id_impresa,
    i.nome_azienda,
    i.partita_iva,
    i.abb_natura_giuridica,
    i.descrizione_natura_giuridica,
    s.id_tipo_sede,
    s.id_indirizzo,
    ts.descrizione,
    oi.via,
    oi.id_comune AS comune,
    oi.comune AS descr_comune,
    i.codice_fiscale
   FROM (((gds.impresa_sedi s
     JOIN gds.vw_imprese i ON ((i.id = s.id_impresa)))
     LEFT JOIN gds_types.tipi_sede ts ON ((ts.id = s.id_tipo_sede)))
     LEFT JOIN gds_notifiche.vw_opu_indirizzi oi ON ((oi.id = s.id_indirizzo)));


ALTER TABLE gds.vw_impresa_sedi OWNER TO postgres;

--
-- Name: cantiere_imprese; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche.cantiere_imprese (
    id_cantiere bigint NOT NULL,
    id_impresa bigint NOT NULL,
    id bigint NOT NULL,
    ordine integer
);


ALTER TABLE gds_notifiche.cantiere_imprese OWNER TO postgres;

--
-- Name: vw_cantiere_imprese; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_cantiere_imprese AS
 SELECT ci.id AS id_cantiere_impresa,
    c.id AS id_cantiere,
    ci.id_impresa,
    ci.id,
    i.id AS id_gisa,
    i.nome_azienda AS ragione_sociale,
    i.partita_iva,
    i.codice_fiscale,
    c.id_natura_opera,
    c.id_notifica,
    c.denominazione,
    c.data_presunta,
    c.durata_presunta,
    c.numero_imprese,
    c.numero_lavoratori,
    c.ammontare,
    c.altro,
    c.natura_opera,
    c.id_soggetto_notificante,
    c.data_notifica,
    c.nome_notificante,
    c.cognome_notificante,
    c.cf_notificante,
    c.via,
    c.comune,
    c.cap,
    c.lat,
    c.lng,
    c.cod_provincia,
    ci.ordine,
    i.id_impresa_sede,
    i.nome_azienda
   FROM ((gds_notifiche.cantiere_imprese ci
     RIGHT JOIN gds_notifiche.vw_cantieri c ON ((c.id_cantiere = ci.id_cantiere)))
     LEFT JOIN gds.vw_impresa_sedi i ON (((i.id_impresa = ci.id_impresa) AND (i.id_tipo_sede = 0))));


ALTER TABLE gds_notifiche.vw_cantiere_imprese OWNER TO postgres;

--
-- Name: vw_cantiere_impresa_sedi; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_cantiere_impresa_sedi AS
 SELECT s.id,
    s.id_impresa,
    s.id_tipo_sede,
    s.codice_pat,
    s.datainizio,
    s.datafine,
    s.id_indirizzo,
    ci.id_cantiere,
    ci.id_cantiere_impresa,
    ci.ragione_sociale,
    ci.partita_iva,
    ci.codice_fiscale
   FROM (gds.impresa_sedi s
     JOIN gds_notifiche.vw_cantiere_imprese ci ON ((ci.id_impresa = s.id_impresa)))
  WHERE (s.id_tipo_sede = 0);


ALTER TABLE gds.vw_cantiere_impresa_sedi OWNER TO postgres;

--
-- Name: moduli; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.moduli (
    id bigint NOT NULL,
    descr text,
    nome_file text,
    url character varying
);


ALTER TABLE gds_types.moduli OWNER TO postgres;

--
-- Name: vw_moduli; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_moduli AS
 SELECT m.id,
    m.id AS id_modulo,
    m.descr,
    m.nome_file
   FROM gds_types.moduli m;


ALTER TABLE gds_types.vw_moduli OWNER TO postgres;

--
-- Name: vw_verbali; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_verbali AS
 SELECT v.id,
    v.id AS id_verbale,
    v.id_modulo,
    v.data_verbale AS ts,
    m.descr,
    m.descr AS descr_modulo,
    v.data_verbale
   FROM (gds.verbali v
     JOIN gds_types.vw_moduli m ON ((m.id = v.id_modulo)));


ALTER TABLE gds.vw_verbali OWNER TO postgres;

--
-- Name: vw_fase_verbali; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_fase_verbali AS
 SELECT fv.id,
    fv.id_ispezione_fase,
    fv.id_verbale,
    fv.data,
    fv.id AS id_fase_verbale,
    v.id_modulo,
    v.descr_modulo
   FROM (gds.fase_verbali fv
     JOIN gds.vw_verbali v ON ((v.id = fv.id_verbale)));


ALTER TABLE gds.vw_fase_verbali OWNER TO postgres;

--
-- Name: esiti_per_fase; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.esiti_per_fase (
    id bigint DEFAULT nextval('gds_types.enti_id_seq'::regclass) NOT NULL,
    descr_esito_per_fase character varying,
    riferimento_fase_esito character varying
);


ALTER TABLE gds_types.esiti_per_fase OWNER TO postgres;

--
-- Name: fase_esiti_possibili_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.fase_esiti_possibili_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.fase_esiti_possibili_id_seq OWNER TO postgres;

--
-- Name: fasi_; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.fasi_ (
    id bigint NOT NULL,
    primo_sopralluogo character varying,
    sopralluogo_prossimo character varying,
    "attività_prescrizione" character varying,
    "attività_accertamenti" character varying,
    "attività_sospensione" character varying,
    atti_indagine character varying,
    documetazione character varying
);


ALTER TABLE gds_types.fasi_ OWNER TO postgres;

--
-- Name: fasi_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.fasi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.fasi_id_seq OWNER TO postgres;

--
-- Name: fasi_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.fasi_id_seq OWNED BY gds_types.fasi_.id;


--
-- Name: fasi; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.fasi (
    id bigint DEFAULT nextval('gds_types.fasi_id_seq'::regclass) NOT NULL,
    descr character varying NOT NULL,
    prop bigint,
    cnt integer,
    cnt_prop integer,
    cnt_prop_nec integer,
    cnt_nec integer,
    cnt_max integer,
    cnt_prop_max integer
);


ALTER TABLE gds_types.fasi OWNER TO postgres;

--
-- Name: fasi_esiti; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.fasi_esiti (
    id bigint DEFAULT nextval('gds_types.fase_esiti_possibili_id_seq'::regclass) NOT NULL,
    id_fase bigint,
    id_esito_per_fase bigint
);


ALTER TABLE gds_types.fasi_esiti OWNER TO postgres;

--
-- Name: vw_esiti_per_fase; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_esiti_per_fase AS
 SELECT epf.id AS id_esito_per_fase,
    epf.id,
    epf.descr_esito_per_fase,
    epf.riferimento_fase_esito
   FROM gds_types.esiti_per_fase epf;


ALTER TABLE gds_types.vw_esiti_per_fase OWNER TO postgres;

--
-- Name: vw_fasi; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_fasi AS
 SELECT fasi.id AS id_fase,
    fasi.id,
    fasi.descr,
    fasi.prop,
    fasi.cnt,
    fasi.cnt_prop,
    fasi.cnt_prop_nec,
    fasi.cnt_nec,
    fasi.cnt_prop_max,
    fasi.cnt_max
   FROM gds_types.fasi;


ALTER TABLE gds_types.vw_fasi OWNER TO postgres;

--
-- Name: vw_fasi_esiti; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_fasi_esiti AS
 SELECT fe.id AS id_fase_esito_possibile,
    fe.id,
    fe.id_fase,
    fe.id_esito_per_fase,
    f.descr AS descr_fase,
    ef.descr_esito_per_fase,
    ef.riferimento_fase_esito,
    f.cnt,
    f.cnt_prop,
    f.cnt_prop_nec,
    f.cnt_nec,
    f.cnt_prop_max,
    f.cnt_max
   FROM ((gds_types.fasi_esiti fe
     LEFT JOIN gds_types.vw_fasi f ON ((fe.id_fase = f.id)))
     LEFT JOIN gds_types.vw_esiti_per_fase ef ON ((ef.id = fe.id_esito_per_fase)));


ALTER TABLE gds_types.vw_fasi_esiti OWNER TO postgres;

--
-- Name: vw_ispezione_fasi; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_ispezione_fasi AS
 SELECT f.id,
    f.id AS id_ispezione_fase,
    f.id_ispezione,
    f.data_creazione,
    f.data_modifica,
    fe.descr_fase,
    fe.descr_esito_per_fase,
    fe.riferimento_fase_esito,
    f.note,
    f.data_fase,
    f.id_fase_esito,
    f.id_impresa_sede,
    s.nome_azienda,
    s.partita_iva,
    s.abb_natura_giuridica,
    i.descr_motivo_isp,
    i.descr_ente,
    i.data_ispezione,
    fe.cnt,
    fe.cnt_prop,
    fe.cnt_nec,
    fe.cnt_prop_nec,
    f.altro_esito,
    fv.id_verbale,
    fv.id_modulo
   FROM ((((gds.ispezione_fasi f
     JOIN gds.vw_ispezioni i ON ((i.id_ispezione = f.id_ispezione)))
     LEFT JOIN gds_types.vw_fasi_esiti fe ON ((fe.id = f.id_fase_esito)))
     LEFT JOIN gds.vw_impresa_sedi s ON ((s.id = f.id_impresa_sede)))
     LEFT JOIN gds.vw_fase_verbali fv ON ((fv.id_ispezione_fase = f.id)));


ALTER TABLE gds.vw_ispezione_fasi OWNER TO postgres;

--
-- Name: vw_fase_persone; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_fase_persone AS
 SELECT p.id,
    p.id_soggetto_fisico,
    p.id_ispezione_fase,
    p.data_inserimento,
    p.ordine,
    p.id AS id_fase_persona,
    s.nome,
    s.cognome,
    s.codice_fiscale,
    f.nome_azienda,
    f.descr_motivo_isp,
    f.descr_ente,
    f.data_ispezione,
    f.id_ispezione
   FROM ((gds.fase_persone p
     JOIN gds.vw_ispezione_fasi f ON ((f.id = p.id_ispezione_fase)))
     JOIN public.opu_soggetto_fisico s ON ((s.id = p.id_soggetto_fisico)));


ALTER TABLE gds.vw_fase_persone OWNER TO postgres;

--
-- Name: vw_ispezione_fasi_visibili; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_ispezione_fasi_visibili AS
 SELECT vw_ispezione_fasi.id,
    vw_ispezione_fasi.id_ispezione_fase,
    vw_ispezione_fasi.id_ispezione,
    vw_ispezione_fasi.data_creazione,
    vw_ispezione_fasi.data_modifica,
    vw_ispezione_fasi.descr_fase,
    vw_ispezione_fasi.descr_esito_per_fase,
    vw_ispezione_fasi.riferimento_fase_esito,
    vw_ispezione_fasi.note,
    vw_ispezione_fasi.data_fase,
    vw_ispezione_fasi.id_fase_esito,
    vw_ispezione_fasi.id_impresa_sede,
    vw_ispezione_fasi.nome_azienda,
    vw_ispezione_fasi.partita_iva,
    vw_ispezione_fasi.abb_natura_giuridica,
    vw_ispezione_fasi.descr_motivo_isp,
    vw_ispezione_fasi.descr_ente,
    vw_ispezione_fasi.data_ispezione,
    vw_ispezione_fasi.cnt,
    vw_ispezione_fasi.cnt_prop,
    vw_ispezione_fasi.cnt_nec,
    vw_ispezione_fasi.cnt_prop_nec,
    vw_ispezione_fasi.altro_esito
   FROM gds.vw_ispezione_fasi;


ALTER TABLE gds.vw_ispezione_fasi_visibili OWNER TO postgres;

--
-- Name: vw_fasi_esiti_per_ispezione; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_fasi_esiti_per_ispezione AS
 SELECT a.id_ispezione,
    fe.id_fase_esito_possibile,
    fe.id,
    fe.id_fase,
    fe.id_esito_per_fase,
    fe.descr_fase,
    fe.descr_esito_per_fase,
    fe.riferimento_fase_esito,
    fe.cnt,
    fe.cnt_prop,
    fe.cnt_prop_nec,
    fe.cnt_nec
   FROM (gds_types.vw_fasi_esiti fe
     LEFT JOIN ( SELECT vif.id_ispezione,
            sum(vif.cnt) AS cnt,
            sum(vif.cnt_prop) AS cnt_prop
           FROM gds.vw_ispezione_fasi_visibili vif
          GROUP BY vif.id_ispezione) a ON ((((COALESCE(a.cnt, (0)::bigint) >= fe.cnt_nec) AND (COALESCE(a.cnt, (0)::bigint) <= fe.cnt_max)) AND ((COALESCE(a.cnt_prop, (0)::bigint) >= fe.cnt_prop_nec) AND (COALESCE(a.cnt_prop, (0)::bigint) <= fe.cnt_prop_max)))));


ALTER TABLE gds.vw_fasi_esiti_per_ispezione OWNER TO postgres;

--
-- Name: vw_h_allegati; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_h_allegati AS
 SELECT ha.id,
    ha.id AS id_allegato,
    ha.descrizione,
    ha.nome,
    ha.file,
    ha.preview,
    ha.data_inserimento,
    ha.id_operatore,
    ha.validita,
    ha.id_transazione,
    ha.ts,
    ha.ts_transazione
   FROM gds._h_allegati ha;


ALTER TABLE gds.vw_h_allegati OWNER TO postgres;

--
-- Name: vw_h_fase_persone; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_h_fase_persone AS
 SELECT hfp.id,
    hfp.id AS id_fase_persona,
    hfp.id_soggetto_fisico,
    hfp.id_ispezione_fase,
    hfp.data_inserimento,
    hfp.ordine,
    hfp.validita,
    hfp.id_transazione,
    hfp.ts,
    hfp.ts_transazione
   FROM gds._h_fase_persone hfp;


ALTER TABLE gds.vw_h_fase_persone OWNER TO postgres;

--
-- Name: vw_h_fase_verbali; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_h_fase_verbali AS
 SELECT hfv.id,
    hfv.id AS id_fase_verbale,
    hfv.id_ispezione_fase,
    hfv.id_verbale,
    hfv.data,
    hfv.validita,
    hfv.id_transazione,
    hfv.ts,
    hfv.ts_transazione
   FROM gds._h_fase_verbali hfv;


ALTER TABLE gds.vw_h_fase_verbali OWNER TO postgres;

--
-- Name: vw_h_impresa_sedi; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_h_impresa_sedi AS
 SELECT his.id,
    his.id AS id_impresa_sede,
    his.id_impresa,
    his.id_tipo_sede,
    his.codice_pat,
    his.datainizio,
    his.datafine,
    his.id_indirizzo,
    his.validita,
    his.id_transazione,
    his.ts,
    his.ts_transazione
   FROM gds._h_impresa_sedi his;


ALTER TABLE gds.vw_h_impresa_sedi OWNER TO postgres;

--
-- Name: vw_h_imprese; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_h_imprese AS
 SELECT hi.id,
    hi.id AS id_impresa,
    hi.codice_inail,
    hi.codice_fiscale,
    hi.partita_iva,
    hi.id_natura_giuridica,
    hi.nome_azienda,
    hi.codice_ateco,
    hi.tipo_ateco,
    hi.anno_competenza,
    hi.numero_dipendenti,
    hi.numero_artigiani,
    hi.addetti_speciali,
    hi.note,
    hi.data_inserimento,
    hi.operatore_inserimento,
    hi.data_modifica,
    hi.operatore_modifica,
    hi.validita,
    hi.id_transazione,
    hi.ts,
    hi.ts_transazione
   FROM gds._h_imprese hi;


ALTER TABLE gds.vw_h_imprese OWNER TO postgres;

--
-- Name: vw_h_ispezione_fasi; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_h_ispezione_fasi AS
 SELECT hif.id,
    hif.id AS id_ispezione_fase,
    hif.id_ispezione,
    hif.data_creazione,
    hif.data_modifica,
    hif.id_impresa_sede,
    hif.id_fase_esito,
    hif.data_fase,
    hif.note,
    hif.altro_esito,
    hif.validita,
    hif.id_transazione,
    hif.ts,
    hif.ts_transazione
   FROM gds._h_ispezione_fasi hif;


ALTER TABLE gds.vw_h_ispezione_fasi OWNER TO postgres;

--
-- Name: vw_h_ispezione_imprese; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_h_ispezione_imprese AS
 SELECT hii.id,
    hii.id AS id_ispezione_impresa,
    hii.id_ispezione,
    hii.id_impresa_sede,
    hii.progressivo,
    hii.validita,
    hii.id_transazione,
    hii.ts,
    hii.ts_transazione
   FROM gds._h_ispezione_imprese hii;


ALTER TABLE gds.vw_h_ispezione_imprese OWNER TO postgres;

--
-- Name: vw_h_ispezioni; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_h_ispezioni AS
 SELECT hi.id,
    hi.id AS id_ispezione,
    hi.id_cantiere,
    hi.id_motivo_isp,
    hi.altro,
    hi.id_ente_uo,
    hi.descr,
    hi.id_stato_ispezione,
    hi.per_conto_di,
    hi.data_inserimento,
    hi.data_modifica,
    hi.note,
    hi.data_ispezione,
    hi.codice_ispezione,
    hi.validita,
    hi.id_transazione,
    hi.ts,
    hi.ts_transazione
   FROM gds._h_ispezioni hi;


ALTER TABLE gds.vw_h_ispezioni OWNER TO postgres;

--
-- Name: vw_h_nucleo_ispettori; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_h_nucleo_ispettori AS
 SELECT hni.id,
    hni.id AS id_nucleo_ispettore,
    hni.id_ispezione,
    hni.id_soggetto_fisico,
    hni.progressivo,
    hni.validita,
    hni.id_transazione,
    hni.ts,
    hni.ts_transazione
   FROM gds._h_nucleo_ispettori hni;


ALTER TABLE gds.vw_h_nucleo_ispettori OWNER TO postgres;

--
-- Name: vw_h_verbale_valori; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_h_verbale_valori AS
 SELECT hvv.id,
    hvv.id AS id_verbale_valore,
    hvv.id_verbale,
    hvv.id_modulo_campo,
    hvv.val,
    hvv.validita,
    hvv.id_transazione,
    hvv.ts,
    hvv.ts_transazione
   FROM gds._h_verbale_valori hvv;


ALTER TABLE gds.vw_h_verbale_valori OWNER TO postgres;

--
-- Name: vw_h_verbali; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_h_verbali AS
 SELECT hv.id,
    hv.id AS id_verbale,
    hv.id_modulo,
    hv.id_transazione,
    hv.validita,
    hv.ts,
    hv.ts_transazione
   FROM gds._h_verbali hv;


ALTER TABLE gds.vw_h_verbali OWNER TO postgres;

--
-- Name: vw_ispezione_imprese; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_ispezione_imprese AS
 SELECT ii.id,
    ii.id AS id_ispezione_impresa,
    i.id_ispezione,
    ii.id_impresa_sede,
    s.nome_azienda,
    s.partita_iva,
    s.abb_natura_giuridica,
    ii.progressivo,
    s.codice_fiscale
   FROM ((gds.ispezione_imprese ii
     RIGHT JOIN gds.vw_ispezioni i ON ((i.id_ispezione = ii.id_ispezione)))
     LEFT JOIN gds.vw_impresa_sedi s ON ((s.id = ii.id_impresa_sede)));


ALTER TABLE gds.vw_ispezione_imprese OWNER TO postgres;

--
-- Name: stati_ispezione_successivi; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.stati_ispezione_successivi (
    id bigint NOT NULL,
    id_stato_ispezione_attuale bigint,
    id_stato_ispezione_prossimo bigint
);


ALTER TABLE gds_types.stati_ispezione_successivi OWNER TO postgres;

--
-- Name: vw_stati_ispezione_successivi; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_stati_ispezione_successivi AS
 SELECT s.id,
    s.id_stato_ispezione_attuale,
    s.id_stato_ispezione_prossimo,
    sa.descr AS descr_stato_ispezione_attuale,
    sp.descr AS descr_stato_ispezione_prossimo
   FROM ((gds_types.stati_ispezione_successivi s
     JOIN gds_types.stati_ispezione sa ON ((s.id_stato_ispezione_attuale = sa.id)))
     JOIN gds_types.stati_ispezione sp ON ((s.id_stato_ispezione_prossimo = sp.id)));


ALTER TABLE gds_types.vw_stati_ispezione_successivi OWNER TO postgres;

--
-- Name: vw_ispezione_stati_ispezione_successivi; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_ispezione_stati_ispezione_successivi AS
 SELECT n.id_ispezione,
    n.data_ispezione,
    n.id_stato_ispezione,
    n.descr_stato_ispezione,
    s.id,
    s.id_stato_ispezione_attuale,
    s.id_stato_ispezione_prossimo,
    s.descr_stato_ispezione_attuale,
    s.descr_stato_ispezione_prossimo
   FROM (gds.vw_ispezioni n
     JOIN gds_types.vw_stati_ispezione_successivi s ON ((s.id_stato_ispezione_attuale = n.id_stato_ispezione)));


ALTER TABLE gds.vw_ispezione_stati_ispezione_successivi OWNER TO postgres;

--
-- Name: vw_moduli; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_moduli AS
 SELECT m.id,
    m.descr,
    m.nome_file,
    m.url
   FROM gds_types.moduli m
  ORDER BY m.id;


ALTER TABLE gds.vw_moduli OWNER TO postgres;

--
-- Name: vw_nucleo_ispettori; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_nucleo_ispettori AS
 SELECT ni.id,
    ni.id AS id_nucleo_ispettore,
    vi.id AS id_ispezione,
    ni.id_access AS id_soggetto_fisico,
    ni.progressivo,
    vi.id_cantiere,
    vi.id_motivo_isp,
    vi.descr_motivo_isp,
    vi.denominazione,
    vi.altro,
    vi.id_ente_uo,
    vi.descr_ente_uo,
    vi.id_stato_ispezione,
    vi.descr_stato_ispezione,
    vi.per_conto_di,
    vi.descr_ente,
    vi.data_inserimento,
    vi.data_modifica,
    vi.data_ispezione,
    vi.descr_ente_isp,
    vi.descr_uo_isp,
    vi.cantiere_o_impresa,
    vi.short_description,
    a.namefirst AS nome,
    a.namelast AS cognome,
    a.codice_fiscale,
    vi.codice_ispezione,
    vi.descr_isp
   FROM ((gds.nucleo_ispettori ni
     RIGHT JOIN gds.vw_ispezioni vi ON ((vi.id = ni.id_ispezione)))
     LEFT JOIN gds.vw_access a ON ((a.contact_id = ni.id_access)));


ALTER TABLE gds.vw_nucleo_ispettori OWNER TO postgres;

--
-- Name: modulo_campi; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.modulo_campi (
    id bigint NOT NULL,
    id_modulo bigint,
    nome_campo text,
    tipo_campo bigint
);


ALTER TABLE gds_types.modulo_campi OWNER TO postgres;

--
-- Name: tipi_campo; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.tipi_campo (
    id bigint NOT NULL,
    descr text,
    codice text
);


ALTER TABLE gds_types.tipi_campo OWNER TO postgres;

--
-- Name: vw_modulo_campi; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_modulo_campi AS
 SELECT mc.id,
    mc.id AS id_modulo_campo,
    mc.id_modulo,
    mc.nome_campo,
    mc.tipo_campo,
    m.descr AS descrizione_modulo,
    m.nome_file,
    tc.id AS id_tipo_campo,
    tc.descr AS descrizione_tipo_modulo,
    tc.codice
   FROM ((gds_types.modulo_campi mc
     JOIN gds_types.moduli m ON ((m.id = mc.id_modulo)))
     JOIN gds_types.tipi_campo tc ON ((m.id = tc.id)));


ALTER TABLE gds_types.vw_modulo_campi OWNER TO postgres;

--
-- Name: vw_verbale_valori; Type: VIEW; Schema: gds; Owner: postgres
--

CREATE VIEW gds.vw_verbale_valori AS
 SELECT v.id AS id_verbale,
    vv.id AS id_valore,
    vv.val,
    mc.nome_campo,
    mc.id AS id_modulo_campo
   FROM ((gds_types.vw_modulo_campi mc
     JOIN gds.verbali v ON ((mc.id_modulo = v.id_modulo)))
     LEFT JOIN gds.verbale_valori vv ON (((v.id = vv.id_verbale) AND (vv.id_modulo_campo = mc.id_modulo_campo))));


ALTER TABLE gds.vw_verbale_valori OWNER TO postgres;

--
-- Name: call_logs; Type: TABLE; Schema: gds_log; Owner: postgres
--

CREATE TABLE gds_log.call_logs (
    id bigint NOT NULL,
    id_transazione bigint,
    procedura character varying,
    fase character varying,
    ts timestamp without time zone,
    val character varying
);


ALTER TABLE gds_log.call_logs OWNER TO postgres;

--
-- Name: call_logs_id_seq; Type: SEQUENCE; Schema: gds_log; Owner: postgres
--

CREATE SEQUENCE gds_log.call_logs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_log.call_logs_id_seq OWNER TO postgres;

--
-- Name: call_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_log; Owner: postgres
--

ALTER SEQUENCE gds_log.call_logs_id_seq OWNED BY gds_log.call_logs.id;


--
-- Name: operazioni; Type: TABLE; Schema: gds_log; Owner: postgres
--

CREATE TABLE gds_log.operazioni (
    id bigint NOT NULL,
    id_transazione bigint,
    procedura character varying,
    fase character varying,
    ts_start timestamp without time zone,
    ts_transazione timestamp without time zone,
    val character varying,
    ts_end timestamp without time zone,
    ret character varying,
    id_trattato bigint
);


ALTER TABLE gds_log.operazioni OWNER TO postgres;

--
-- Name: operazioni_id_seq; Type: SEQUENCE; Schema: gds_log; Owner: postgres
--

CREATE SEQUENCE gds_log.operazioni_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_log.operazioni_id_seq OWNER TO postgres;

--
-- Name: operazioni_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_log; Owner: postgres
--

ALTER SEQUENCE gds_log.operazioni_id_seq OWNED BY gds_log.operazioni.id;


--
-- Name: transazioni; Type: TABLE; Schema: gds_log; Owner: postgres
--

CREATE TABLE gds_log.transazioni (
    id bigint NOT NULL,
    id_user bigint NOT NULL,
    ts timestamp without time zone NOT NULL,
    descr character varying
);


ALTER TABLE gds_log.transazioni OWNER TO postgres;

--
-- Name: transazioni_id_seq; Type: SEQUENCE; Schema: gds_log; Owner: postgres
--

CREATE SEQUENCE gds_log.transazioni_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_log.transazioni_id_seq OWNER TO postgres;

--
-- Name: transazioni_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_log; Owner: postgres
--

ALTER SEQUENCE gds_log.transazioni_id_seq OWNED BY gds_log.transazioni.id;


--
-- Name: vw_call_logs; Type: VIEW; Schema: gds_log; Owner: postgres
--

CREATE VIEW gds_log.vw_call_logs AS
 SELECT cl.id AS id_call_log,
    cl.id_transazione AS id_call,
    cl.procedura,
    cl.fase,
    cl.ts,
    cl.val
   FROM gds_log.call_logs cl;


ALTER TABLE gds_log.vw_call_logs OWNER TO postgres;

--
-- Name: vw_operazioni; Type: VIEW; Schema: gds_log; Owner: postgres
--

CREATE VIEW gds_log.vw_operazioni AS
 SELECT o.id,
    o.id AS id_operazione,
    o.id_transazione,
    o.procedura,
    o.fase,
    o.ts_start,
    o.ts_transazione,
    o.val,
    o.ts_end,
    o.ret
   FROM (gds_log.operazioni o
     JOIN gds_log.transazioni t ON ((o.id_transazione = t.id)));


ALTER TABLE gds_log.vw_operazioni OWNER TO postgres;

--
-- Name: vw_transazioni; Type: VIEW; Schema: gds_log; Owner: postgres
--

CREATE VIEW gds_log.vw_transazioni AS
 SELECT t.id,
    t.id AS id_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
   FROM gds_log.transazioni t;


ALTER TABLE gds_log.vw_transazioni OWNER TO postgres;

--
-- Name: z_util_log_operazioni_id_seq; Type: SEQUENCE; Schema: gds_log; Owner: postgres
--

CREATE SEQUENCE gds_log.z_util_log_operazioni_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_log.z_util_log_operazioni_id_seq OWNER TO postgres;

--
-- Name: _h_costruttori; Type: TABLE; Schema: gds_macchine; Owner: postgres
--

CREATE TABLE gds_macchine._h_costruttori (
    id bigint NOT NULL,
    id_costruttore bigint,
    descr character varying NOT NULL,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_macchine._h_costruttori OWNER TO postgres;

--
-- Name: _h_costruttori_id_seq; Type: SEQUENCE; Schema: gds_macchine; Owner: postgres
--

CREATE SEQUENCE gds_macchine._h_costruttori_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_macchine._h_costruttori_id_seq OWNER TO postgres;

--
-- Name: _h_costruttori_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_macchine; Owner: postgres
--

ALTER SEQUENCE gds_macchine._h_costruttori_id_seq OWNED BY gds_macchine._h_costruttori.id;


--
-- Name: _h_macchine; Type: TABLE; Schema: gds_macchine; Owner: postgres
--

CREATE TABLE gds_macchine._h_macchine (
    id bigint NOT NULL,
    id_macchina bigint,
    id_tipo_macchina bigint NOT NULL,
    id_costruttore bigint NOT NULL,
    modello character varying NOT NULL,
    data_modifica timestamp without time zone NOT NULL,
    data_inserimento timestamp without time zone NOT NULL,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_macchine._h_macchine OWNER TO postgres;

--
-- Name: _h_macchine_id_seq; Type: SEQUENCE; Schema: gds_macchine; Owner: postgres
--

CREATE SEQUENCE gds_macchine._h_macchine_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_macchine._h_macchine_id_seq OWNER TO postgres;

--
-- Name: _h_macchine_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_macchine; Owner: postgres
--

ALTER SEQUENCE gds_macchine._h_macchine_id_seq OWNED BY gds_macchine._h_macchine.id;


--
-- Name: costruttori; Type: TABLE; Schema: gds_macchine; Owner: postgres
--

CREATE TABLE gds_macchine.costruttori (
    id bigint NOT NULL,
    descr character varying NOT NULL
);


ALTER TABLE gds_macchine.costruttori OWNER TO postgres;

--
-- Name: costruttori_id_seq; Type: SEQUENCE; Schema: gds_macchine; Owner: postgres
--

CREATE SEQUENCE gds_macchine.costruttori_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_macchine.costruttori_id_seq OWNER TO postgres;

--
-- Name: costruttori_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_macchine; Owner: postgres
--

ALTER SEQUENCE gds_macchine.costruttori_id_seq OWNED BY gds_macchine.costruttori.id;


--
-- Name: macchine; Type: TABLE; Schema: gds_macchine; Owner: postgres
--

CREATE TABLE gds_macchine.macchine (
    id bigint NOT NULL,
    id_tipo_macchina bigint NOT NULL,
    id_costruttore bigint NOT NULL,
    modello character varying NOT NULL,
    data_modifica timestamp without time zone NOT NULL,
    data_inserimento timestamp without time zone NOT NULL
);


ALTER TABLE gds_macchine.macchine OWNER TO postgres;

--
-- Name: macchine_id_seq; Type: SEQUENCE; Schema: gds_macchine; Owner: postgres
--

CREATE SEQUENCE gds_macchine.macchine_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_macchine.macchine_id_seq OWNER TO postgres;

--
-- Name: macchine_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_macchine; Owner: postgres
--

ALTER SEQUENCE gds_macchine.macchine_id_seq OWNED BY gds_macchine.macchine.id;


--
-- Name: vw_costruttori; Type: VIEW; Schema: gds_macchine; Owner: postgres
--

CREATE VIEW gds_macchine.vw_costruttori AS
 SELECT costruttori.id,
    costruttori.id AS id_costruttore,
    costruttori.descr,
    costruttori.descr AS descr_costruttore
   FROM gds_macchine.costruttori;


ALTER TABLE gds_macchine.vw_costruttori OWNER TO postgres;

--
-- Name: vw_h_costruttori; Type: VIEW; Schema: gds_macchine; Owner: postgres
--

CREATE VIEW gds_macchine.vw_h_costruttori AS
 SELECT hc.id,
    hc.id AS id_costruttore,
    hc.descr,
    hc.validita,
    hc.id_transazione,
    hc.ts,
    hc.ts_transazione
   FROM gds_macchine._h_costruttori hc;


ALTER TABLE gds_macchine.vw_h_costruttori OWNER TO postgres;

--
-- Name: vw_h_macchine; Type: VIEW; Schema: gds_macchine; Owner: postgres
--

CREATE VIEW gds_macchine.vw_h_macchine AS
 SELECT hm.id,
    hm.id AS id_macchina,
    hm.id_tipo_macchina,
    hm.id_costruttore,
    hm.modello,
    hm.data_modifica,
    hm.data_inserimento,
    hm.validita,
    hm.id_transazione,
    hm.ts,
    hm.ts_transazione
   FROM gds_macchine._h_macchine hm;


ALTER TABLE gds_macchine.vw_h_macchine OWNER TO postgres;

--
-- Name: tipi_macchina; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.tipi_macchina (
    id bigint NOT NULL,
    descr character varying NOT NULL
);


ALTER TABLE gds_types.tipi_macchina OWNER TO postgres;

--
-- Name: vw_tipi_macchina; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_tipi_macchina AS
 SELECT tipi_macchina.id,
    tipi_macchina.id AS id_tipo_macchina,
    tipi_macchina.descr,
    tipi_macchina.descr AS descr_tipo_macchina
   FROM gds_types.tipi_macchina;


ALTER TABLE gds_types.vw_tipi_macchina OWNER TO postgres;

--
-- Name: vw_macchine; Type: VIEW; Schema: gds_macchine; Owner: postgres
--

CREATE VIEW gds_macchine.vw_macchine AS
 SELECT m.id,
    m.id AS id_macchina,
    m.modello,
    m.id_tipo_macchina,
    m.id_costruttore,
    tm.descr_tipo_macchina,
    c.descr_costruttore,
    m.data_modifica,
    m.data_inserimento
   FROM ((gds_macchine.macchine m
     JOIN gds_types.vw_tipi_macchina tm ON ((m.id_tipo_macchina = tm.id_tipo_macchina)))
     JOIN gds_macchine.vw_costruttori c ON ((c.id = m.id_costruttore)));


ALTER TABLE gds_macchine.vw_macchine OWNER TO postgres;

--
-- Name: _h_cantiere_imprese; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche._h_cantiere_imprese (
    id bigint,
    id_cantiere bigint,
    id_impresa bigint,
    id_cantiere_impresa bigint,
    ordine integer,
    validita tsrange,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_notifiche._h_cantiere_imprese OWNER TO postgres;

--
-- Name: _h_cantiere_imprese_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche._h_cantiere_imprese_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche._h_cantiere_imprese_id_seq OWNER TO postgres;

--
-- Name: _h_cantiere_persona_ruoli; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche._h_cantiere_persona_ruoli (
    id bigint NOT NULL,
    id_cantiere bigint,
    id_soggetto_fisico bigint,
    id_ruolo bigint,
    id_cantiere_persona_ruolo bigint,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_notifiche._h_cantiere_persona_ruoli OWNER TO postgres;

--
-- Name: _h_cantiere_persona_ruoli_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche._h_cantiere_persona_ruoli_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche._h_cantiere_persona_ruoli_id_seq OWNER TO postgres;

--
-- Name: _h_cantiere_persona_ruoli_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche._h_cantiere_persona_ruoli_id_seq OWNED BY gds_notifiche._h_cantiere_persona_ruoli.id;


--
-- Name: _h_cantieri; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche._h_cantieri (
    id bigint NOT NULL,
    id_cantiere bigint,
    id_natura_opera bigint,
    id_notifica bigint,
    denominazione character varying(255),
    data_presunta timestamp without time zone,
    durata_presunta integer,
    numero_imprese integer,
    numero_lavoratori integer,
    ammontare double precision,
    altro character varying,
    id_indirizzo bigint,
    cuc character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_notifiche._h_cantieri OWNER TO postgres;

--
-- Name: _h_cantieri_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche._h_cantieri_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche._h_cantieri_id_seq OWNER TO postgres;

--
-- Name: _h_cantieri_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche._h_cantieri_id_seq OWNED BY gds_notifiche._h_cantieri.id;


--
-- Name: _h_documenti; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche._h_documenti (
    id bigint NOT NULL,
    id_documento bigint,
    tipo character varying NOT NULL,
    id_interno bigint,
    cod_documento character varying,
    titolo character varying,
    dati bytea,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_notifiche._h_documenti OWNER TO postgres;

--
-- Name: _h_documenti_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche._h_documenti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche._h_documenti_id_seq OWNER TO postgres;

--
-- Name: _h_documenti_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche._h_documenti_id_seq OWNED BY gds_notifiche._h_documenti.id;


--
-- Name: _h_email_asl; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche._h_email_asl (
    id bigint NOT NULL,
    id_email_asl bigint,
    id_asl bigint,
    address character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_notifiche._h_email_asl OWNER TO postgres;

--
-- Name: _h_email_asl_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche._h_email_asl_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche._h_email_asl_id_seq OWNER TO postgres;

--
-- Name: _h_email_asl_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche._h_email_asl_id_seq OWNED BY gds_notifiche._h_email_asl.id;


--
-- Name: _h_imprese; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche._h_imprese (
    id bigint NOT NULL,
    id_impresa bigint,
    id_gisa bigint,
    ragione_sociale character varying,
    partita_iva character varying,
    codice_fiscale character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_notifiche._h_imprese OWNER TO postgres;

--
-- Name: _h_imprese_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche._h_imprese_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche._h_imprese_id_seq OWNER TO postgres;

--
-- Name: _h_imprese_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche._h_imprese_id_seq OWNED BY gds_notifiche._h_imprese.id;


--
-- Name: _h_notifiche; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche._h_notifiche (
    id bigint NOT NULL,
    id_notifica bigint,
    id_soggetto_fisico bigint,
    data_notifica timestamp without time zone,
    id_stato bigint,
    cun character varying,
    id_notifica_succ bigint,
    data_modifica timestamp without time zone,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_notifiche._h_notifiche OWNER TO postgres;

--
-- Name: _h_notifiche_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche._h_notifiche_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche._h_notifiche_id_seq OWNER TO postgres;

--
-- Name: _h_notifiche_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche._h_notifiche_id_seq OWNED BY gds_notifiche._h_notifiche.id;


--
-- Name: _h_soggetti_fisici; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche._h_soggetti_fisici (
    id bigint NOT NULL,
    id_soggetto_fisico bigint,
    titolo character(20),
    cognome text,
    nome text,
    comune_nascita text,
    codice_fiscale character varying,
    enteredby integer,
    modifiedby integer,
    ipenteredby character(100),
    ipmodifiedby character(100),
    sesso character(1),
    telefono character(50),
    fax character(50),
    email character varying(100),
    telefono1 character(50),
    data_nascita timestamp without time zone,
    documento_identita text,
    indirizzo_id integer,
    provenienza_estera boolean DEFAULT false,
    riferimento_org_id integer,
    provincia_nascita text,
    id_bdn integer,
    id_operatore_temp integer,
    trashed_date timestamp without time zone,
    note_hd text,
    id_soggetto_precedente integer,
    id_nazione_nascita integer,
    id_comune_nascita integer,
    validita tsrange,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_notifiche._h_soggetti_fisici OWNER TO postgres;

--
-- Name: _h_soggetti_fisici_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche._h_soggetti_fisici_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche._h_soggetti_fisici_id_seq OWNER TO postgres;

--
-- Name: _h_soggetti_fisici_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche._h_soggetti_fisici_id_seq OWNED BY gds_notifiche._h_soggetti_fisici.id;


--
-- Name: cantiere_imprese_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche.cantiere_imprese_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche.cantiere_imprese_id_seq OWNER TO postgres;

--
-- Name: cantiere_imprese_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche.cantiere_imprese_id_seq OWNED BY gds_notifiche.cantiere_imprese.id;


--
-- Name: cantiere_persona_ruoli; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche.cantiere_persona_ruoli (
    id_cantiere bigint NOT NULL,
    id_soggetto_fisico bigint NOT NULL,
    id_ruolo bigint NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE gds_notifiche.cantiere_persona_ruoli OWNER TO postgres;

--
-- Name: cantiere_persona_ruoli_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche.cantiere_persona_ruoli_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche.cantiere_persona_ruoli_id_seq OWNER TO postgres;

--
-- Name: cantiere_persona_ruoli_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche.cantiere_persona_ruoli_id_seq OWNED BY gds_notifiche.cantiere_persona_ruoli.id;


--
-- Name: cantieri_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche.cantieri_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche.cantieri_id_seq OWNER TO postgres;

--
-- Name: cantieri_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche.cantieri_id_seq OWNED BY gds_notifiche.cantieri.id;


--
-- Name: codici; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche.codici (
    id bigint NOT NULL,
    codice character varying NOT NULL,
    anno integer,
    val integer
);


ALTER TABLE gds_notifiche.codici OWNER TO postgres;

--
-- Name: codici_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche.codici_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche.codici_id_seq OWNER TO postgres;

--
-- Name: codici_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche.codici_id_seq OWNED BY gds_notifiche.codici.id;


--
-- Name: cuc_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche.cuc_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche.cuc_id_seq OWNER TO postgres;

--
-- Name: cun_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche.cun_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche.cun_id_seq OWNER TO postgres;

--
-- Name: documenti; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche.documenti (
    id bigint NOT NULL,
    tipo character varying NOT NULL,
    id_interno bigint,
    cod_documento character varying,
    titolo character varying,
    dati bytea
);


ALTER TABLE gds_notifiche.documenti OWNER TO postgres;

--
-- Name: documenti_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche.documenti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche.documenti_id_seq OWNER TO postgres;

--
-- Name: documenti_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche.documenti_id_seq OWNED BY gds_notifiche.documenti.id;


--
-- Name: email_asl_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche.email_asl_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche.email_asl_id_seq OWNER TO postgres;

--
-- Name: email_asl_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche.email_asl_id_seq OWNED BY gds_notifiche.email_asl.id;


--
-- Name: imprese; Type: TABLE; Schema: gds_notifiche; Owner: postgres
--

CREATE TABLE gds_notifiche.imprese (
    id bigint NOT NULL,
    id_gisa bigint,
    ragione_sociale character varying,
    partita_iva character varying,
    codice_fiscale character varying
);


ALTER TABLE gds_notifiche.imprese OWNER TO postgres;

--
-- Name: imprese_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche.imprese_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche.imprese_id_seq OWNER TO postgres;

--
-- Name: imprese_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche.imprese_id_seq OWNED BY gds_notifiche.imprese.id;


--
-- Name: notifiche_id_seq; Type: SEQUENCE; Schema: gds_notifiche; Owner: postgres
--

CREATE SEQUENCE gds_notifiche.notifiche_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_notifiche.notifiche_id_seq OWNER TO postgres;

--
-- Name: notifiche_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_notifiche; Owner: postgres
--

ALTER SEQUENCE gds_notifiche.notifiche_id_seq OWNED BY gds_notifiche.notifiche.id;


--
-- Name: ruoli; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.ruoli (
    id bigint NOT NULL,
    descr character varying,
    richiesta_pec boolean,
    obbligatorio boolean
);


ALTER TABLE gds_types.ruoli OWNER TO postgres;

--
-- Name: vw_cantiere_persona_ruoli; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_cantiere_persona_ruoli AS
 SELECT c.id_cantiere,
    c.id_natura_opera,
    c.id_notifica,
    c.denominazione,
    c.data_presunta,
    c.durata_presunta,
    c.numero_imprese,
    c.numero_lavoratori,
    c.ammontare,
    c.altro,
    c.natura_opera,
    c.id_soggetto_notificante,
    c.data_notifica,
    c.nome_notificante,
    c.cognome_notificante,
    c.cf_notificante,
    s.id_soggetto_fisico,
    s.nome,
    s.cognome,
    s.codice_fiscale,
    s.via,
    s.cap,
    s.comune,
    s.cod_provincia,
    r.id AS id_ruolo,
    r.descr,
    r.descr AS ruolo,
    s.email AS pec,
    s.id_indirizzo,
    cpr.id,
    s.id_comune,
    r.obbligatorio
   FROM (((( SELECT c_1.id AS id_cantiere,
            r_1.id,
            r_1.descr,
            r_1.richiesta_pec,
            r_1.obbligatorio
           FROM (gds_notifiche.cantieri c_1
             JOIN gds_types.ruoli r_1 ON ((1 = 1)))) r
     LEFT JOIN gds_notifiche.cantiere_persona_ruoli cpr ON (((cpr.id_cantiere = r.id_cantiere) AND (r.id = cpr.id_ruolo))))
     JOIN gds_notifiche.vw_cantieri c ON ((c.id_cantiere = r.id_cantiere)))
     LEFT JOIN gds_notifiche.vw_soggetti_fisici s ON ((s.id_soggetto_fisico = cpr.id_soggetto_fisico)));


ALTER TABLE gds_notifiche.vw_cantiere_persona_ruoli OWNER TO postgres;

--
-- Name: vw_cantieri_attivi; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_cantieri_attivi AS
 SELECT vc.id_cantiere,
    vc.id,
    vc.id_natura_opera,
    vc.id_notifica,
    vc.denominazione,
    vc.data_presunta,
    vc.durata_presunta,
    vc.numero_imprese,
    vc.numero_lavoratori,
    vc.ammontare,
    vc.altro,
    vc.id_indirizzo,
    vc.natura_opera,
    vc.id_soggetto_notificante,
    vc.data_notifica,
    vc.nome_notificante,
    vc.cognome_notificante,
    vc.cf_notificante,
    vc.via_notificante,
    vc.comune_notificante,
    vc.id_comune_notificante,
    vc.cod_provincia_notificante,
    vc.via,
    vc.comune,
    vc.id_comune,
    vc.cap,
    vc.cod_provincia,
    vc.lat,
    vc.lng,
    vc.id_stato,
    vc.stato,
    vc.cap_notificante,
    vc.id_indirizzo_notificante,
    vc.cuc,
    vc.cun,
    vc.civico,
    vc.civico_notificante,
    vc.codiceistatasl,
    vc.codiceistatcomune,
    vc.indirizzo_mail,
    vc.scaricabile,
    vc.id_notifica_succ,
    vc.descr_asl,
    vc.short_descr_asl,
    vc.attivo
   FROM gds_notifiche.vw_cantieri vc
  WHERE (vc.id_stato = 2);


ALTER TABLE gds_notifiche.vw_cantieri_attivi OWNER TO postgres;

--
-- Name: vw_codici; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_codici AS
 SELECT c.id,
    c.id AS id_codice,
    c.codice,
    c.anno,
    c.val
   FROM gds_notifiche.codici c;


ALTER TABLE gds_notifiche.vw_codici OWNER TO postgres;

--
-- Name: vw_comuni; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_comuni AS
 SELECT c.id,
    c.nome AS comune,
    l.cod_provincia,
    c.codiceistatasl,
    c.notused
   FROM (public.comuni1 c
     JOIN public.lookup_province l ON (((c.cod_provincia)::integer = l.code)))
  WHERE (c.notused IS NULL);


ALTER TABLE gds_notifiche.vw_comuni OWNER TO postgres;

--
-- Name: vw_comuni_cantiere; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_comuni_cantiere AS
 SELECT vw_comuni.id,
    vw_comuni.comune,
    vw_comuni.cod_provincia,
    vw_comuni.codiceistatasl
   FROM gds_notifiche.vw_comuni
  WHERE (((vw_comuni.codiceistatasl)::integer >= 201) AND ((vw_comuni.codiceistatasl)::integer <= 207) AND (vw_comuni.notused IS NULL));


ALTER TABLE gds_notifiche.vw_comuni_cantiere OWNER TO postgres;

--
-- Name: vw_documenti; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_documenti AS
 SELECT d.id,
    d.id AS id_documento,
    d.tipo,
    d.id_interno,
    d.cod_documento,
    d.titolo,
    d.dati
   FROM gds_notifiche.documenti d;


ALTER TABLE gds_notifiche.vw_documenti OWNER TO postgres;

--
-- Name: vw_email_asl; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_email_asl AS
 SELECT ea.id,
    ea.id AS id_email_asl_ui,
    ea.id_asl,
    ea.address
   FROM gds_notifiche.email_asl ea;


ALTER TABLE gds_notifiche.vw_email_asl OWNER TO postgres;

--
-- Name: vw_h_cantiere_imprese; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_h_cantiere_imprese AS
 SELECT hci.id,
    hci.id AS id_cantiere_impresa,
    hci.id_cantiere,
    hci.id_impresa,
    hci.ordine,
    hci.validita,
    hci.id_transazione,
    hci.ts,
    hci.ts_transazione
   FROM gds_notifiche._h_cantiere_imprese hci;


ALTER TABLE gds_notifiche.vw_h_cantiere_imprese OWNER TO postgres;

--
-- Name: vw_h_cantiere_persona_ruoli; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_h_cantiere_persona_ruoli AS
 SELECT hcpr.id,
    hcpr.id AS id_cantiere_persona_ruolo,
    hcpr.id_cantiere,
    hcpr.id_soggetto_fisico,
    hcpr.id_ruolo,
    hcpr.validita,
    hcpr.id_transazione,
    hcpr.ts,
    hcpr.ts_transazione
   FROM gds_notifiche._h_cantiere_persona_ruoli hcpr;


ALTER TABLE gds_notifiche.vw_h_cantiere_persona_ruoli OWNER TO postgres;

--
-- Name: vw_h_soggetti_fisici; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_h_soggetti_fisici AS
 SELECT s.id,
    s.id AS id_soggetto_fisico,
    s.titolo,
    s.nome,
    s.cognome,
    s.comune_nascita,
    s.codice_fiscale,
    s.email,
    s.indirizzo_id AS id_indirizzo,
    i.via,
    i.comune,
    i.cap,
    i.id_comune,
    i.cod_provincia,
    i.civico,
    s.validita,
    s.validita AS tsrange,
    s.id_transazione,
    s.ts,
    s.ts_transazione
   FROM (gds_notifiche._h_soggetti_fisici s
     LEFT JOIN gds_notifiche.vw_opu_indirizzi i ON ((s.indirizzo_id = i.id)));


ALTER TABLE gds_notifiche.vw_h_soggetti_fisici OWNER TO postgres;

--
-- Name: _h_stati; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_stati (
    id bigint NOT NULL,
    id_stato bigint,
    descr character varying,
    modificabile boolean,
    modificabile_cantiere boolean,
    scaricabile boolean,
    visibile boolean,
    genera_pdf boolean,
    controlli boolean,
    attivo boolean,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_stati OWNER TO postgres;

--
-- Name: vw_h_stati; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_stati AS
 SELECT hst.id,
    hst.id_stato,
    hst.descr,
    hst.descr AS stato,
    hst.modificabile,
    hst.modificabile_cantiere,
    hst.scaricabile,
    hst.visibile,
    hst.genera_pdf,
    hst.controlli,
    hst.attivo,
    hst.validita,
    hst.ts,
    hst.id_transazione,
    hst.ts_transazione
   FROM gds_types._h_stati hst;


ALTER TABLE gds_types.vw_h_stati OWNER TO postgres;

--
-- Name: vw_h_notifiche; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_h_notifiche AS
 SELECT n.id_notifica,
    n.id,
    n.id_soggetto_fisico,
    n.data_notifica,
    n.id_soggetto_fisico AS id_soggetto_notificante,
    s.nome AS nome_notificante,
    s.cognome AS cognome_notificante,
    s.codice_fiscale AS cf_notificante,
    n.id_stato,
    st.descr AS stato,
    n.data_modifica,
    c.denominazione,
    st.modificabile,
    s.via,
    s.comune,
    s.cod_provincia,
    s.id_comune,
    s.cap,
    s.id_indirizzo,
    n.cun,
    c.id AS id_cantiere,
    st.modificabile_cantiere,
    c.cuc,
    s.civico,
    st.scaricabile,
    n.id_notifica_succ,
    st.attivo,
    n.ts,
    n.ts_transazione,
    (((n.validita * c.validita) * st.validita) * s.validita) AS validita,
        CASE
            WHEN ((lower(n.validita) >= lower(c.validita)) AND (lower(n.validita) >= lower(st.validita)) AND (lower(n.validita) >= lower(s.validita))) THEN n.id_transazione
            WHEN ((lower(c.validita) >= lower(n.validita)) AND (lower(c.validita) >= lower(st.validita)) AND (lower(c.validita) >= lower(s.validita))) THEN c.id_transazione
            WHEN ((lower(st.validita) >= lower(c.validita)) AND (lower(st.validita) >= lower(n.validita)) AND (lower(st.validita) >= lower(s.validita))) THEN st.id_transazione
            WHEN ((lower(s.validita) >= lower(c.validita)) AND (lower(s.validita) >= lower(st.validita)) AND (lower(s.validita) >= lower(n.validita))) THEN s.id_transazione
            ELSE NULL::bigint
        END AS id_transazione
   FROM (((gds_notifiche._h_notifiche n
     LEFT JOIN gds_notifiche.vw_h_soggetti_fisici s ON (((n.id_soggetto_fisico = s.id_soggetto_fisico) AND (n.validita && s.validita))))
     JOIN gds_types.vw_h_stati st ON (((st.id_stato = n.id_stato) AND (st.validita && n.validita))))
     LEFT JOIN gds_notifiche._h_cantieri c ON (((c.id_notifica = n.id_notifica) AND (c.validita && n.validita))));


ALTER TABLE gds_notifiche.vw_h_notifiche OWNER TO postgres;

--
-- Name: _h_nature_opera; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_nature_opera (
    id bigint NOT NULL,
    id_natura_opera bigint,
    descr character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_nature_opera OWNER TO postgres;

--
-- Name: vw_h_nature_opera; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_nature_opera AS
 SELECT hnt.id,
    hnt.id AS id_natura_opera,
    hnt.descr,
    hnt.descr AS natura_opera,
    hnt.validita,
    hnt.ts,
    hnt.ts_transazione,
    hnt.id_transazione
   FROM gds_types._h_nature_opera hnt;


ALTER TABLE gds_types.vw_h_nature_opera OWNER TO postgres;

--
-- Name: vw_h_cantieri; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_h_cantieri AS
 SELECT c.id AS id_cantiere,
    c.id,
    c.id_natura_opera,
    c.id_notifica,
    c.denominazione,
    c.data_presunta,
    c.durata_presunta,
    c.numero_imprese,
    c.numero_lavoratori,
    c.ammontare,
    c.altro,
    c.id_indirizzo,
    n.natura_opera,
    nt.id_soggetto_notificante,
    nt.data_notifica,
    nt.nome_notificante,
    nt.cognome_notificante,
    nt.cf_notificante,
    nt.via AS via_notificante,
    nt.comune AS comune_notificante,
    nt.id_comune AS id_comune_notificante,
    nt.cod_provincia AS cod_provincia_notificante,
    i.via,
    i.comune,
    i.id_comune,
    i.cap,
    i.cod_provincia,
    i.lat,
    i.lng,
    nt.id_stato,
    nt.stato,
    nt.cap AS cap_notificante,
    nt.id_indirizzo AS id_indirizzo_notificante,
    c.cuc,
    nt.cun,
    i.civico,
    nt.civico AS civico_notificante,
    i.codiceistatasl,
    i.codiceistatcomune,
    ea.address AS indirizzo_mail,
    nt.scaricabile,
    nt.id_notifica_succ,
    nt.attivo,
    c.ts,
    c.ts_transazione,
    (((c.validita * n.validita) * ea.validita) * nt.validita) AS validita,
        CASE
            WHEN ((lower(c.validita) >= lower(n.validita)) AND (lower(c.validita) >= lower(ea.validita)) AND (lower(c.validita) >= lower(nt.validita))) THEN c.id_transazione
            WHEN ((lower(n.validita) >= lower(c.validita)) AND (lower(n.validita) >= lower(ea.validita)) AND (lower(n.validita) >= lower(nt.validita))) THEN n.id_transazione
            WHEN ((lower(ea.validita) >= lower(c.validita)) AND (lower(ea.validita) >= lower(n.validita)) AND (lower(c.validita) >= lower(nt.validita))) THEN ea.id_transazione
            WHEN ((lower(nt.validita) >= lower(c.validita)) AND (lower(nt.validita) >= lower(n.validita)) AND (lower(nt.validita) >= lower(ea.validita))) THEN nt.id_transazione
            ELSE NULL::bigint
        END AS id_transazione
   FROM ((((gds_notifiche._h_cantieri c
     JOIN gds_notifiche.vw_h_notifiche nt ON ((c.id_notifica = nt.id_notifica)))
     LEFT JOIN gds_types.vw_h_nature_opera n ON (((n.id_natura_opera = c.id_natura_opera) AND (c.validita && n.validita))))
     LEFT JOIN gds_notifiche.vw_opu_indirizzi i ON ((i.id_indirizzo = c.id_indirizzo)))
     LEFT JOIN gds_notifiche._h_email_asl ea ON ((((ea.id_asl)::text = (i.codiceistatasl)::text) AND (c.validita && ea.validita))));


ALTER TABLE gds_notifiche.vw_h_cantieri OWNER TO postgres;

--
-- Name: vw_h_documenti; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_h_documenti AS
 SELECT hd.id,
    hd.id AS id_documento,
    hd.tipo,
    hd.id_interno,
    hd.cod_documento,
    hd.titolo,
    hd.dati,
    hd.validita,
    hd.id_transazione,
    hd.ts,
    hd.ts_transazione
   FROM gds_notifiche._h_documenti hd;


ALTER TABLE gds_notifiche.vw_h_documenti OWNER TO postgres;

--
-- Name: vw_h_email_asl; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_h_email_asl AS
 SELECT hea.id,
    hea.id AS id_email_asl,
    hea.id_asl,
    hea.address,
    hea.validita,
    hea.id_transazione,
    hea.ts,
    hea.ts_transazione
   FROM gds_notifiche._h_email_asl hea;


ALTER TABLE gds_notifiche.vw_h_email_asl OWNER TO postgres;

--
-- Name: vw_imprese; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_imprese AS
 SELECT i.id,
    i.id AS id_impresa,
    i.id_gisa,
    i.ragione_sociale,
    i.partita_iva,
    i.codice_fiscale
   FROM gds_notifiche.imprese i;


ALTER TABLE gds_notifiche.vw_imprese OWNER TO postgres;

--
-- Name: stati_successivi; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.stati_successivi (
    id bigint NOT NULL,
    id_stato_attuale bigint,
    id_stato_prossimo bigint
);


ALTER TABLE gds_types.stati_successivi OWNER TO postgres;

--
-- Name: vw_stati_successivi; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_stati_successivi AS
 SELECT s.id,
    s.id_stato_attuale,
    s.id_stato_prossimo,
    sa.descr AS descr_stato_attuale,
    sp.descr AS descr_stato_prossimo,
    sp.modificabile AS modificabile_stato_prossimo,
    sp.modificabile_cantiere AS modificabile_cantiere_stato_prossimo,
    sp.scaricabile AS scaricabile_stato_prossimo,
    sp.visibile AS visibile_stato_prossimo,
    sp.genera_pdf AS genera_pdf_stato_prossimo,
    sp.controlli AS controlli_stato_prossimo,
    sa.modificabile AS modificabile_stato_attuale,
    sa.modificabile_cantiere AS modificabile_cantiere_stato_attuale,
    sa.scaricabile AS scaricabile_stato_attuale,
    sa.visibile AS visibile_stato_attuale,
    sa.genera_pdf AS genera_pdf_stato_attuale,
    sa.controlli AS controlli_stato_attuale
   FROM ((gds_types.stati_successivi s
     JOIN gds_types.stati sa ON ((s.id_stato_attuale = sa.id)))
     JOIN gds_types.stati sp ON ((s.id_stato_prossimo = sp.id)));


ALTER TABLE gds_types.vw_stati_successivi OWNER TO postgres;

--
-- Name: vw_notifica_stati_successivi; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_notifica_stati_successivi AS
 SELECT n.id_notifica,
    n.id_soggetto_fisico,
    n.data_notifica,
    n.id_soggetto_notificante,
    n.nome_notificante,
    n.cognome_notificante,
    n.cf_notificante,
    n.id_stato,
    n.stato,
    s.id AS id_stato_successivo,
    s.id_stato_prossimo,
    s.descr_stato_attuale,
    s.descr_stato_prossimo,
    s.modificabile_stato_prossimo,
    s.modificabile_cantiere_stato_prossimo,
    s.scaricabile_stato_prossimo,
    s.visibile_stato_prossimo,
    s.genera_pdf_stato_prossimo,
    s.controlli_stato_prossimo,
    s.modificabile_stato_attuale,
    s.modificabile_cantiere_stato_attuale,
    s.scaricabile_stato_attuale,
    s.visibile_stato_attuale,
    s.genera_pdf_stato_attuale,
    s.controlli_stato_attuale
   FROM (gds_notifiche.vw_notifiche n
     JOIN gds_types.vw_stati_successivi s ON ((s.id_stato_attuale = n.id_stato)));


ALTER TABLE gds_notifiche.vw_notifica_stati_successivi OWNER TO postgres;

--
-- Name: vw_notifiche_rel_prec; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_notifiche_rel_prec AS
 WITH RECURSIVE notifiche_prec AS (
         SELECT n.id_notifica_succ,
            n.id AS id_notifica_prec
           FROM gds_notifiche.notifiche n
          WHERE (n.id_notifica_succ IS NOT NULL)
        UNION
         SELECT n.id_notifica_succ,
            n1.id
           FROM (notifiche_prec n
             JOIN gds_notifiche.notifiche n1 ON ((n.id_notifica_prec = n1.id_notifica_succ)))
        )
 SELECT notifiche_prec.id_notifica_succ AS id_notifica,
    notifiche_prec.id_notifica_prec
   FROM notifiche_prec;


ALTER TABLE gds_notifiche.vw_notifiche_rel_prec OWNER TO postgres;

--
-- Name: vw_notifiche_prec; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_notifiche_prec AS
 SELECT np.id_notifica,
    np.id_notifica_prec,
    n.cun,
    n.data_notifica,
    n.data_modifica,
    n.denominazione,
    n.stato
   FROM (gds_notifiche.vw_notifiche n
     JOIN gds_notifiche.vw_notifiche_rel_prec np ON ((np.id_notifica_prec = n.id)));


ALTER TABLE gds_notifiche.vw_notifiche_prec OWNER TO postgres;

--
-- Name: vw_stati_visibili; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_stati_visibili AS
 SELECT st.id,
    st.id AS id_stato,
    st.descr,
    st.descr AS stato,
    st.modificabile,
    st.modificabile_cantiere,
    st.scaricabile
   FROM gds_types.stati st
  WHERE (st.visibile = true);


ALTER TABLE gds_types.vw_stati_visibili OWNER TO postgres;

--
-- Name: vw_notifiche_visibili; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_notifiche_visibili AS
 SELECT n.id AS id_notifica,
    n.id,
    n.id_soggetto_fisico,
    n.data_notifica,
    n.id_soggetto_fisico AS id_soggetto_notificante,
    s.nome AS nome_notificante,
    s.cognome AS cognome_notificante,
    s.codice_fiscale AS cf_notificante,
    n.id_stato,
    st.stato,
    n.data_modifica,
    c.denominazione,
    st.modificabile,
    s.via,
    s.comune,
    s.cod_provincia,
    s.id_comune,
    s.cap,
    s.id_indirizzo,
    n.cun,
    c.id AS id_cantiere,
    st.modificabile_cantiere,
    c.cuc,
    s.civico,
    st.scaricabile,
    n.id_notifica_succ,
    EXTRACT(year FROM n.data_notifica) AS anno,
    c.descr_asl,
    c.short_descr_asl,
    c.codiceistatasl
   FROM (((gds_notifiche.notifiche n
     LEFT JOIN gds_notifiche.vw_soggetti_fisici s ON ((n.id_soggetto_fisico = s.id_soggetto_fisico)))
     JOIN gds_types.vw_stati_visibili st ON ((st.id_stato = n.id_stato)))
     LEFT JOIN gds_notifiche.vw_cantieri c ON ((c.id_notifica = n.id)));


ALTER TABLE gds_notifiche.vw_notifiche_visibili OWNER TO postgres;

--
-- Name: vw_notifiche_visibili_regione; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_notifiche_visibili_regione AS
 SELECT vw_notifiche_visibili.id_notifica,
    vw_notifiche_visibili.id,
    vw_notifiche_visibili.id_soggetto_fisico,
    vw_notifiche_visibili.data_notifica,
    vw_notifiche_visibili.id_soggetto_notificante,
    vw_notifiche_visibili.nome_notificante,
    vw_notifiche_visibili.cognome_notificante,
    vw_notifiche_visibili.cf_notificante,
    vw_notifiche_visibili.id_stato,
    vw_notifiche_visibili.stato,
    vw_notifiche_visibili.data_modifica,
    vw_notifiche_visibili.denominazione,
    vw_notifiche_visibili.modificabile,
    vw_notifiche_visibili.via,
    vw_notifiche_visibili.comune,
    vw_notifiche_visibili.cod_provincia,
    vw_notifiche_visibili.id_comune,
    vw_notifiche_visibili.cap,
    vw_notifiche_visibili.id_indirizzo,
    vw_notifiche_visibili.cun,
    vw_notifiche_visibili.id_cantiere,
    vw_notifiche_visibili.modificabile_cantiere,
    vw_notifiche_visibili.cuc,
    vw_notifiche_visibili.civico,
    vw_notifiche_visibili.scaricabile,
    vw_notifiche_visibili.id_notifica_succ,
    vw_notifiche_visibili.anno,
    vw_notifiche_visibili.descr_asl,
    vw_notifiche_visibili.short_descr_asl,
    vw_notifiche_visibili.codiceistatasl
   FROM gds_notifiche.vw_notifiche_visibili
  WHERE (vw_notifiche_visibili.id_stato <> ALL (ARRAY[(1)::bigint, (5)::bigint]));


ALTER TABLE gds_notifiche.vw_notifiche_visibili_regione OWNER TO postgres;

--
-- Name: vw_ph_cantieri; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_ph_cantieri AS
 SELECT hc.id,
    hc.id AS id_cantiere,
    hc.id_natura_opera,
    hc.id_notifica,
    hc.denominazione,
    hc.data_presunta,
    hc.durata_presunta,
    hc.numero_imprese,
    hc.numero_lavoratori,
    hc.ammontare,
    hc.altro,
    hc.id_indirizzo,
    hc.cuc,
    hc.validita,
    t.id AS id_transazione,
    hc.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
   FROM (gds_notifiche._h_cantieri hc
     JOIN gds_log.transazioni t ON ((t.ts <@ hc.validita)));


ALTER TABLE gds_notifiche.vw_ph_cantieri OWNER TO postgres;

--
-- Name: vw_ph_email_asl; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_ph_email_asl AS
 SELECT hea.id,
    hea.id AS id_email_asl,
    hea.id_asl,
    hea.address,
    hea.validita,
    t.id AS id_transazione,
    hea.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
   FROM (gds_notifiche._h_email_asl hea
     JOIN gds_log.transazioni t ON ((t.ts <@ hea.validita)));


ALTER TABLE gds_notifiche.vw_ph_email_asl OWNER TO postgres;

--
-- Name: vw_ph_soggetti_fisici; Type: VIEW; Schema: gds_notifiche; Owner: postgres
--

CREATE VIEW gds_notifiche.vw_ph_soggetti_fisici AS
 SELECT hsf.id,
    hsf.id AS id_soggetto_fisico,
    hsf.titolo,
    hsf.cognome,
    hsf.nome,
    hsf.comune_nascita,
    hsf.codice_fiscale,
    hsf.enteredby,
    hsf.modifiedby,
    hsf.ipenteredby,
    hsf.ipmodifiedby,
    hsf.sesso,
    hsf.telefono,
    hsf.fax,
    hsf.email,
    hsf.telefono1,
    hsf.data_nascita,
    hsf.documento_identita,
    hsf.indirizzo_id,
    hsf.provenienza_estera,
    hsf.riferimento_org_id,
    hsf.provincia_nascita,
    hsf.id_bdn,
    hsf.id_operatore_temp,
    hsf.trashed_date,
    hsf.note_hd,
    hsf.id_soggetto_precedente,
    hsf.id_nazione_nascita,
    hsf.id_comune_nascita,
    hsf.validita,
    t.id AS id_transazione,
    hsf.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
   FROM (gds_notifiche._h_soggetti_fisici hsf
     JOIN gds_log.transazioni t ON ((t.ts <@ hsf.validita)));


ALTER TABLE gds_notifiche.vw_ph_soggetti_fisici OWNER TO postgres;

--
-- Name: vw_cantiere_impresa_sedi; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_cantiere_impresa_sedi AS
 SELECT vw_cantiere_impresa_sedi.id,
    vw_cantiere_impresa_sedi.id_impresa,
    vw_cantiere_impresa_sedi.id_tipo_sede,
    vw_cantiere_impresa_sedi.codice_pat,
    vw_cantiere_impresa_sedi.datainizio,
    vw_cantiere_impresa_sedi.datafine,
    vw_cantiere_impresa_sedi.id_indirizzo,
    vw_cantiere_impresa_sedi.id_cantiere,
    vw_cantiere_impresa_sedi.id_cantiere_impresa,
    vw_cantiere_impresa_sedi.ragione_sociale,
    vw_cantiere_impresa_sedi.partita_iva,
    vw_cantiere_impresa_sedi.codice_fiscale
   FROM gds.vw_cantiere_impresa_sedi;


ALTER TABLE gds_srv.vw_cantiere_impresa_sedi OWNER TO postgres;

--
-- Name: vw_cantiere_imprese; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_cantiere_imprese AS
 SELECT vw_cantiere_imprese.id_cantiere_impresa,
    vw_cantiere_imprese.id_cantiere,
    vw_cantiere_imprese.id_impresa,
    vw_cantiere_imprese.id,
    vw_cantiere_imprese.id_gisa,
    vw_cantiere_imprese.ragione_sociale,
    vw_cantiere_imprese.partita_iva,
    vw_cantiere_imprese.codice_fiscale,
    vw_cantiere_imprese.id_natura_opera,
    vw_cantiere_imprese.id_notifica,
    vw_cantiere_imprese.denominazione,
    vw_cantiere_imprese.durata_presunta,
    vw_cantiere_imprese.numero_imprese,
    vw_cantiere_imprese.numero_lavoratori,
    vw_cantiere_imprese.ammontare,
    vw_cantiere_imprese.altro,
    vw_cantiere_imprese.natura_opera,
    vw_cantiere_imprese.id_soggetto_notificante,
    vw_cantiere_imprese.data_notifica,
    vw_cantiere_imprese.nome_notificante,
    vw_cantiere_imprese.cognome_notificante,
    vw_cantiere_imprese.cf_notificante,
    vw_cantiere_imprese.via,
    vw_cantiere_imprese.comune,
    vw_cantiere_imprese.cap,
    vw_cantiere_imprese.lat,
    vw_cantiere_imprese.lng,
    vw_cantiere_imprese.cod_provincia,
    vw_cantiere_imprese.ordine
   FROM gds_notifiche.vw_cantiere_imprese
  ORDER BY vw_cantiere_imprese.id_cantiere, vw_cantiere_imprese.ordine;


ALTER TABLE gds_srv.vw_cantiere_imprese OWNER TO postgres;

--
-- Name: ruoli_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui.ruoli_ui (
    id bigint NOT NULL,
    ordinamento integer
);


ALTER TABLE gds_ui.ruoli_ui OWNER TO postgres;

--
-- Name: vw_cantiere_persona_ruoli; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_cantiere_persona_ruoli AS
 SELECT vw_cantiere_persona_ruoli.id_cantiere,
    vw_cantiere_persona_ruoli.id_natura_opera,
    vw_cantiere_persona_ruoli.id_notifica,
    vw_cantiere_persona_ruoli.denominazione,
    vw_cantiere_persona_ruoli.durata_presunta,
    vw_cantiere_persona_ruoli.numero_imprese,
    vw_cantiere_persona_ruoli.numero_lavoratori,
    vw_cantiere_persona_ruoli.ammontare,
    vw_cantiere_persona_ruoli.altro,
    vw_cantiere_persona_ruoli.natura_opera,
    vw_cantiere_persona_ruoli.id_soggetto_notificante,
    vw_cantiere_persona_ruoli.data_notifica,
    vw_cantiere_persona_ruoli.nome_notificante,
    vw_cantiere_persona_ruoli.cognome_notificante,
    vw_cantiere_persona_ruoli.cf_notificante,
    vw_cantiere_persona_ruoli.id_soggetto_fisico,
    vw_cantiere_persona_ruoli.nome,
    vw_cantiere_persona_ruoli.cognome,
    vw_cantiere_persona_ruoli.codice_fiscale,
    vw_cantiere_persona_ruoli.via,
    vw_cantiere_persona_ruoli.cap,
    vw_cantiere_persona_ruoli.comune,
    vw_cantiere_persona_ruoli.cod_provincia,
    vw_cantiere_persona_ruoli.id_ruolo,
    vw_cantiere_persona_ruoli.descr,
    vw_cantiere_persona_ruoli.ruolo,
    vw_cantiere_persona_ruoli.pec,
    vw_cantiere_persona_ruoli.id_indirizzo,
    vw_cantiere_persona_ruoli.id,
    vw_cantiere_persona_ruoli.id_comune,
    vw_cantiere_persona_ruoli.obbligatorio
   FROM (gds_notifiche.vw_cantiere_persona_ruoli
     JOIN gds_ui.ruoli_ui ru ON ((ru.id = vw_cantiere_persona_ruoli.id_ruolo)))
  ORDER BY ru.ordinamento;


ALTER TABLE gds_srv.vw_cantiere_persona_ruoli OWNER TO postgres;

--
-- Name: vw_cantieri; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_cantieri AS
 SELECT vw_cantieri.id_cantiere,
    vw_cantieri.id,
    vw_cantieri.id_natura_opera,
    vw_cantieri.id_notifica,
    vw_cantieri.denominazione,
    vw_cantieri.data_presunta,
    vw_cantieri.durata_presunta,
    vw_cantieri.numero_imprese,
    vw_cantieri.numero_lavoratori,
    vw_cantieri.ammontare,
    vw_cantieri.altro,
    vw_cantieri.id_indirizzo,
    vw_cantieri.natura_opera,
    vw_cantieri.id_soggetto_notificante,
    vw_cantieri.data_notifica,
    vw_cantieri.nome_notificante,
    vw_cantieri.cognome_notificante,
    vw_cantieri.cf_notificante,
    vw_cantieri.via_notificante,
    vw_cantieri.comune_notificante,
    vw_cantieri.id_comune_notificante,
    vw_cantieri.cod_provincia_notificante,
    vw_cantieri.via,
    vw_cantieri.comune,
    vw_cantieri.id_comune,
    vw_cantieri.cap,
    vw_cantieri.cod_provincia,
    vw_cantieri.lat,
    vw_cantieri.lng,
    vw_cantieri.id_stato,
    vw_cantieri.stato,
    vw_cantieri.cap_notificante,
    vw_cantieri.id_indirizzo_notificante,
    vw_cantieri.cun,
    vw_cantieri.cuc,
    vw_cantieri.civico,
    vw_cantieri.civico_notificante,
    vw_cantieri.codiceistatasl,
    vw_cantieri.codiceistatcomune,
    vw_cantieri.indirizzo_mail,
    vw_cantieri.scaricabile
   FROM gds_notifiche.vw_cantieri;


ALTER TABLE gds_srv.vw_cantieri OWNER TO postgres;

--
-- Name: vw_cantieri_attivi; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_cantieri_attivi AS
 SELECT vca.id_cantiere,
    vca.id,
    vca.id_natura_opera,
    vca.id_notifica,
    vca.denominazione,
    vca.data_presunta,
    vca.durata_presunta,
    vca.numero_imprese,
    vca.numero_lavoratori,
    vca.ammontare,
    vca.altro,
    vca.id_indirizzo,
    vca.natura_opera,
    vca.id_soggetto_notificante,
    vca.data_notifica,
    vca.nome_notificante,
    vca.cognome_notificante,
    vca.cf_notificante,
    vca.via_notificante,
    vca.comune_notificante,
    vca.id_comune_notificante,
    vca.cod_provincia_notificante,
    vca.via,
    vca.comune,
    vca.id_comune,
    vca.cap,
    vca.cod_provincia,
    vca.lat,
    vca.lng,
    vca.id_stato,
    vca.stato,
    vca.cap_notificante,
    vca.id_indirizzo_notificante,
    vca.cuc,
    vca.cun,
    vca.civico,
    vca.civico_notificante,
    vca.codiceistatasl,
    vca.codiceistatcomune,
    vca.indirizzo_mail,
    vca.scaricabile,
    vca.id_notifica_succ,
    vca.descr_asl,
    vca.short_descr_asl,
    vca.attivo
   FROM gds_notifiche.vw_cantieri_attivi vca;


ALTER TABLE gds_srv.vw_cantieri_attivi OWNER TO postgres;

--
-- Name: vw_comuni; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_comuni AS
 SELECT vw_comuni.id,
    vw_comuni.comune,
    vw_comuni.cod_provincia,
    vw_comuni.codiceistatasl
   FROM gds_notifiche.vw_comuni;


ALTER TABLE gds_srv.vw_comuni OWNER TO postgres;

--
-- Name: vw_comuni_cantiere; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_comuni_cantiere AS
 SELECT vw_comuni_cantiere.id,
    vw_comuni_cantiere.comune,
    vw_comuni_cantiere.cod_provincia,
    vw_comuni_cantiere.codiceistatasl
   FROM gds_notifiche.vw_comuni_cantiere;


ALTER TABLE gds_srv.vw_comuni_cantiere OWNER TO postgres;

--
-- Name: vw_costruttori; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_costruttori AS
 SELECT c.id,
    c.id_costruttore,
    c.descr,
    c.descr_costruttore
   FROM gds_macchine.vw_costruttori c;


ALTER TABLE gds_srv.vw_costruttori OWNER TO postgres;

--
-- Name: vw_fase_persone; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_fase_persone AS
 SELECT vw_fase_persone.id,
    vw_fase_persone.id_soggetto_fisico,
    vw_fase_persone.id_ispezione_fase,
    vw_fase_persone.data_inserimento,
    vw_fase_persone.ordine,
    vw_fase_persone.id_fase_persona,
    vw_fase_persone.nome,
    vw_fase_persone.cognome,
    vw_fase_persone.codice_fiscale,
    vw_fase_persone.descr_motivo_isp,
    vw_fase_persone.descr_ente,
    vw_fase_persone.data_ispezione,
    vw_fase_persone.id_ispezione
   FROM gds.vw_fase_persone;


ALTER TABLE gds_srv.vw_fase_persone OWNER TO postgres;

--
-- Name: vw_fase_verbali; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_fase_verbali AS
 SELECT vfv.id,
    vfv.id_ispezione_fase,
    vfv.id_verbale,
    vfv.data,
    vfv.id_fase_verbale,
    vfv.id_modulo,
    vfv.descr_modulo
   FROM gds.vw_fase_verbali vfv;


ALTER TABLE gds_srv.vw_fase_verbali OWNER TO postgres;

--
-- Name: vw_impresa_sedi; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_impresa_sedi AS
 SELECT vw_impresa_sedi.id,
    vw_impresa_sedi.id AS id_impresa_sede,
    vw_impresa_sedi.id_impresa,
    vw_impresa_sedi.nome_azienda AS ragione_sociale,
    vw_impresa_sedi.partita_iva,
    vw_impresa_sedi.abb_natura_giuridica,
    vw_impresa_sedi.descrizione_natura_giuridica,
    vw_impresa_sedi.id_tipo_sede,
    vw_impresa_sedi.id_indirizzo,
    vw_impresa_sedi.descrizione,
    vw_impresa_sedi.via,
    vw_impresa_sedi.comune,
    vw_impresa_sedi.descr_comune,
    vw_impresa_sedi.codice_fiscale
   FROM gds.vw_impresa_sedi;


ALTER TABLE gds_srv.vw_impresa_sedi OWNER TO postgres;

--
-- Name: vw_imprese; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_imprese AS
 SELECT vw_imprese.id,
    vw_imprese.id_impresa,
    vw_imprese.id_gisa,
    vw_imprese.ragione_sociale,
    vw_imprese.partita_iva,
    vw_imprese.codice_fiscale
   FROM gds_notifiche.vw_imprese;


ALTER TABLE gds_srv.vw_imprese OWNER TO postgres;

--
-- Name: vw_ispezione_fasi; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_ispezione_fasi AS
 SELECT vw_ispezione_fasi.id,
    vw_ispezione_fasi.id_ispezione_fase,
    vw_ispezione_fasi.id_ispezione,
    vw_ispezione_fasi.data_creazione,
    vw_ispezione_fasi.data_modifica,
    vw_ispezione_fasi.descr_fase,
    vw_ispezione_fasi.descr_esito_per_fase,
    vw_ispezione_fasi.riferimento_fase_esito,
    vw_ispezione_fasi.note,
    vw_ispezione_fasi.data_fase,
    vw_ispezione_fasi.id_fase_esito,
    vw_ispezione_fasi.id_impresa_sede,
    vw_ispezione_fasi.nome_azienda,
    vw_ispezione_fasi.partita_iva,
    vw_ispezione_fasi.abb_natura_giuridica
   FROM gds.vw_ispezione_fasi;


ALTER TABLE gds_srv.vw_ispezione_fasi OWNER TO postgres;

--
-- Name: vw_ispezione_imprese; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_ispezione_imprese AS
 SELECT vw_ispezione_imprese.id,
    vw_ispezione_imprese.id_ispezione_impresa,
    vw_ispezione_imprese.id_ispezione,
    vw_ispezione_imprese.id_impresa_sede,
    vw_ispezione_imprese.nome_azienda,
    vw_ispezione_imprese.partita_iva,
    vw_ispezione_imprese.abb_natura_giuridica
   FROM gds.vw_ispezione_imprese;


ALTER TABLE gds_srv.vw_ispezione_imprese OWNER TO postgres;

--
-- Name: vw_ispezione_stati_ispezione_successivi; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_ispezione_stati_ispezione_successivi AS
 SELECT vw_ispezione_stati_ispezione_successivi.id_ispezione,
    vw_ispezione_stati_ispezione_successivi.data_ispezione,
    vw_ispezione_stati_ispezione_successivi.id_stato_ispezione,
    vw_ispezione_stati_ispezione_successivi.descr_stato_ispezione,
    vw_ispezione_stati_ispezione_successivi.id,
    vw_ispezione_stati_ispezione_successivi.id_stato_ispezione_attuale,
    vw_ispezione_stati_ispezione_successivi.id_stato_ispezione_prossimo,
    vw_ispezione_stati_ispezione_successivi.descr_stato_ispezione_attuale,
    vw_ispezione_stati_ispezione_successivi.descr_stato_ispezione_prossimo
   FROM gds.vw_ispezione_stati_ispezione_successivi;


ALTER TABLE gds_srv.vw_ispezione_stati_ispezione_successivi OWNER TO postgres;

--
-- Name: vw_ispezioni; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_ispezioni AS
 SELECT i.id,
    i.id_ispezione,
    i.id_cantiere,
    i.id_motivo_isp,
    i.descr_motivo_isp,
    i.denominazione,
    i.altro,
    i.id_ente_uo,
    i.descr_ente_uo,
    i.id_stato_ispezione,
    i.descr_stato_ispezione,
    i.per_conto_di,
    i.descr_ente,
    i.data_inserimento,
    i.data_modifica,
    i.data_ispezione,
    i.descr_ente_isp,
    i.descr_uo_isp,
    i.cantiere_o_impresa,
    i.short_description,
    i.note,
    i.data_notifica,
    i.comune,
    i.cod_provincia,
    i.via,
    i.civico,
    i.natura_opera,
    i.codice_ispezione,
    i.modificabile,
    i.descr_isp
   FROM gds.vw_ispezioni i;


ALTER TABLE gds_srv.vw_ispezioni OWNER TO postgres;

--
-- Name: vw_macchine; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_macchine AS
 SELECT m.id,
    m.id_macchina,
    m.modello,
    m.id_tipo_macchina,
    m.id_costruttore,
    m.descr_tipo_macchina,
    m.descr_costruttore,
    m.data_modifica,
    m.data_inserimento
   FROM gds_macchine.vw_macchine m;


ALTER TABLE gds_srv.vw_macchine OWNER TO postgres;

--
-- Name: vw_moduli; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_moduli AS
 SELECT m.id,
    m.descr,
    m.nome_file,
    m.url
   FROM gds.vw_moduli m;


ALTER TABLE gds_srv.vw_moduli OWNER TO postgres;

--
-- Name: natura_opere; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui.natura_opere (
    id bigint NOT NULL,
    ordinamento integer
);


ALTER TABLE gds_ui.natura_opere OWNER TO postgres;

--
-- Name: vw_nature_opera_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_nature_opera_ui AS
 SELECT nu.id,
    nu.id AS id_natura_opera,
    nu.ordinamento,
    n.descr
   FROM (gds_ui.natura_opere nu
     JOIN gds_types.nature_opera n ON ((n.id = nu.id)));


ALTER TABLE gds_ui.vw_nature_opera_ui OWNER TO postgres;

--
-- Name: vw_nature_opera; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_nature_opera AS
 SELECT vw_nature_opera_ui.id,
    vw_nature_opera_ui.id_natura_opera,
    vw_nature_opera_ui.ordinamento,
    vw_nature_opera_ui.descr
   FROM gds_ui.vw_nature_opera_ui;


ALTER TABLE gds_srv.vw_nature_opera OWNER TO postgres;

--
-- Name: stati_successivi_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui.stati_successivi_ui (
    id bigint NOT NULL,
    label_bottone character varying,
    colore character varying,
    msg character varying,
    ordine integer
);


ALTER TABLE gds_ui.stati_successivi_ui OWNER TO postgres;

--
-- Name: vw_stati_successivi_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_stati_successivi_ui AS
 SELECT su.id AS id_stato_successivo,
    su.label_bottone,
    su.colore,
    su.msg,
    su.ordine
   FROM (gds_ui.stati_successivi_ui su
     JOIN gds_types.stati_successivi s ON ((su.id = s.id)));


ALTER TABLE gds_ui.vw_stati_successivi_ui OWNER TO postgres;

--
-- Name: vw_notifica_stati_successivi; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_notifica_stati_successivi AS
 SELECT s.id_notifica,
    s.id_soggetto_fisico,
    s.data_notifica,
    s.id_soggetto_notificante,
    s.nome_notificante,
    s.cognome_notificante,
    s.cf_notificante,
    s.id_stato,
    s.stato,
    s.id_stato_successivo,
    s.id_stato_prossimo,
    s.descr_stato_attuale,
    s.descr_stato_prossimo,
    su.label_bottone,
    su.msg,
    s.modificabile_stato_prossimo,
    s.modificabile_cantiere_stato_prossimo,
    s.scaricabile_stato_prossimo,
    s.visibile_stato_prossimo,
    s.genera_pdf_stato_prossimo,
    s.controlli_stato_prossimo,
    s.modificabile_stato_attuale,
    s.modificabile_cantiere_stato_attuale,
    s.scaricabile_stato_attuale,
    s.visibile_stato_attuale,
    s.genera_pdf_stato_attuale,
    s.controlli_stato_attuale
   FROM (gds_notifiche.vw_notifica_stati_successivi s
     LEFT JOIN gds_ui.vw_stati_successivi_ui su ON ((su.id_stato_successivo = s.id_stato_successivo)))
  ORDER BY su.ordine;


ALTER TABLE gds_srv.vw_notifica_stati_successivi OWNER TO postgres;

--
-- Name: stati_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui.stati_ui (
    id bigint NOT NULL,
    label_bottone character varying,
    colore character varying,
    msg character varying,
    ordine integer
);


ALTER TABLE gds_ui.stati_ui OWNER TO postgres;

--
-- Name: vw_stati_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_stati_ui AS
 SELECT su.id AS id_stato,
    su.label_bottone,
    su.colore,
    su.msg,
    su.ordine
   FROM (gds_ui.stati_ui su
     JOIN gds_types.stati s ON ((su.id = s.id)));


ALTER TABLE gds_ui.vw_stati_ui OWNER TO postgres;

--
-- Name: vw_notifiche; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_notifiche AS
 SELECT vw_notifiche.id_notifica,
    vw_notifiche.id,
    vw_notifiche.id_soggetto_fisico,
    vw_notifiche.data_notifica,
    vw_notifiche.id_soggetto_notificante,
    vw_notifiche.nome_notificante,
    vw_notifiche.cognome_notificante,
    vw_notifiche.cf_notificante,
    vw_notifiche.id_stato,
    vw_notifiche.stato,
    vw_notifiche.data_modifica,
    vw_notifiche.denominazione,
    vw_notifiche.modificabile,
    vw_notifiche.via,
    vw_notifiche.comune,
    vw_notifiche.cod_provincia,
    vw_notifiche.id_comune,
    vw_notifiche.cap,
    vw_notifiche.id_indirizzo,
    vw_notifiche.modificabile_cantiere,
    vw_notifiche.cun,
    vw_notifiche.cuc
   FROM (gds_notifiche.vw_notifiche
     LEFT JOIN gds_ui.vw_stati_ui s ON ((vw_notifiche.id_stato = s.id_stato)))
  ORDER BY s.ordine, vw_notifiche.data_modifica DESC, vw_notifiche.data_notifica DESC;


ALTER TABLE gds_srv.vw_notifiche OWNER TO postgres;

--
-- Name: vw_notifiche_prec; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_notifiche_prec AS
 SELECT vw_notifiche_prec.id_notifica,
    vw_notifiche_prec.id_notifica_prec,
    vw_notifiche_prec.cun,
    vw_notifiche_prec.data_notifica,
    vw_notifiche_prec.data_modifica,
    vw_notifiche_prec.denominazione
   FROM gds_notifiche.vw_notifiche_prec;


ALTER TABLE gds_srv.vw_notifiche_prec OWNER TO postgres;

--
-- Name: vw_notifiche_visibili; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_notifiche_visibili AS
 SELECT n.id_notifica,
    n.id,
    n.id_soggetto_fisico,
    n.data_notifica,
    n.id_soggetto_notificante,
    n.nome_notificante,
    n.cognome_notificante,
    n.cf_notificante,
    n.id_stato,
    n.stato,
    n.data_modifica,
    n.denominazione,
    n.modificabile,
    n.via,
    n.comune,
    n.cod_provincia,
    n.id_comune,
    n.cap,
    n.id_indirizzo,
    n.modificabile_cantiere,
    n.cun,
    n.cuc,
    n.anno,
    n.descr_asl,
    n.short_descr_asl
   FROM (gds_notifiche.vw_notifiche_visibili n
     LEFT JOIN gds_ui.vw_stati_ui s ON ((n.id_stato = s.id_stato)))
  ORDER BY s.ordine, n.data_modifica DESC, n.data_notifica DESC;


ALTER TABLE gds_srv.vw_notifiche_visibili OWNER TO postgres;

--
-- Name: vw_notifiche_visibili_regione; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_notifiche_visibili_regione AS
 SELECT vw_notifiche_visibili_regione.id_notifica,
    vw_notifiche_visibili_regione.id,
    vw_notifiche_visibili_regione.id_soggetto_fisico,
    vw_notifiche_visibili_regione.data_notifica,
    vw_notifiche_visibili_regione.id_soggetto_notificante,
    vw_notifiche_visibili_regione.nome_notificante,
    vw_notifiche_visibili_regione.cognome_notificante,
    vw_notifiche_visibili_regione.cf_notificante,
    vw_notifiche_visibili_regione.id_stato,
    vw_notifiche_visibili_regione.stato,
    vw_notifiche_visibili_regione.data_modifica,
    vw_notifiche_visibili_regione.denominazione,
    vw_notifiche_visibili_regione.modificabile,
    vw_notifiche_visibili_regione.via,
    vw_notifiche_visibili_regione.comune,
    vw_notifiche_visibili_regione.cod_provincia,
    vw_notifiche_visibili_regione.id_comune,
    vw_notifiche_visibili_regione.cap,
    vw_notifiche_visibili_regione.id_indirizzo,
    vw_notifiche_visibili_regione.cun,
    vw_notifiche_visibili_regione.id_cantiere,
    vw_notifiche_visibili_regione.modificabile_cantiere,
    vw_notifiche_visibili_regione.cuc,
    vw_notifiche_visibili_regione.civico,
    vw_notifiche_visibili_regione.scaricabile,
    vw_notifiche_visibili_regione.id_notifica_succ,
    vw_notifiche_visibili_regione.anno,
    vw_notifiche_visibili_regione.descr_asl,
    vw_notifiche_visibili_regione.short_descr_asl,
    vw_notifiche_visibili_regione.codiceistatasl
   FROM (gds_notifiche.vw_notifiche_visibili_regione
     LEFT JOIN gds_ui.vw_stati_ui s ON ((vw_notifiche_visibili_regione.id_stato = s.id_stato)))
  ORDER BY s.ordine, vw_notifiche_visibili_regione.data_modifica DESC, vw_notifiche_visibili_regione.data_notifica DESC;


ALTER TABLE gds_srv.vw_notifiche_visibili_regione OWNER TO postgres;

--
-- Name: stati_ispezione_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui.stati_ispezione_ui (
    id bigint NOT NULL,
    label_bottone character varying,
    colore character varying,
    msg character varying,
    ordine integer
);


ALTER TABLE gds_ui.stati_ispezione_ui OWNER TO postgres;

--
-- Name: vw_stati_ispezione_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_stati_ispezione_ui AS
 SELECT stati_ispezione_ui.id,
    stati_ispezione_ui.label_bottone,
    stati_ispezione_ui.colore,
    stati_ispezione_ui.msg,
    stati_ispezione_ui.ordine
   FROM gds_ui.stati_ispezione_ui;


ALTER TABLE gds_ui.vw_stati_ispezione_ui OWNER TO postgres;

--
-- Name: vw_nucleo_ispettori; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_nucleo_ispettori AS
 SELECT vw_nucleo_ispettori.id,
    vw_nucleo_ispettori.id_nucleo_ispettore,
    vw_nucleo_ispettori.id_ispezione,
    vw_nucleo_ispettori.id_soggetto_fisico,
    vw_nucleo_ispettori.progressivo,
    vw_nucleo_ispettori.id_cantiere,
    vw_nucleo_ispettori.id_motivo_isp,
    vw_nucleo_ispettori.descr_motivo_isp,
    vw_nucleo_ispettori.denominazione,
    vw_nucleo_ispettori.altro,
    vw_nucleo_ispettori.id_ente_uo,
    vw_nucleo_ispettori.descr_ente_uo,
    vw_nucleo_ispettori.id_stato_ispezione,
    vw_nucleo_ispettori.descr_stato_ispezione,
    vw_nucleo_ispettori.per_conto_di,
    vw_nucleo_ispettori.descr_ente,
    vw_nucleo_ispettori.data_inserimento,
    vw_nucleo_ispettori.data_modifica,
    vw_nucleo_ispettori.data_ispezione,
    vw_nucleo_ispettori.descr_ente_isp,
    vw_nucleo_ispettori.descr_uo_isp,
    vw_nucleo_ispettori.cantiere_o_impresa,
    vw_nucleo_ispettori.short_description,
    vw_nucleo_ispettori.nome,
    vw_nucleo_ispettori.cognome,
    vw_nucleo_ispettori.codice_fiscale,
    vw_nucleo_ispettori.codice_ispezione,
    vw_nucleo_ispettori.descr_isp
   FROM (gds.vw_nucleo_ispettori
     JOIN gds_ui.vw_stati_ispezione_ui s ON ((s.id = vw_nucleo_ispettori.id_stato_ispezione)))
  WHERE (vw_nucleo_ispettori.id_stato_ispezione <> '-1'::integer)
  ORDER BY s.ordine, vw_nucleo_ispettori.data_ispezione;


ALTER TABLE gds_srv.vw_nucleo_ispettori OWNER TO postgres;

--
-- Name: vw_ruoli_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_ruoli_ui AS
 SELECT ru.id,
    ru.id AS id_ruolo,
    ru.ordinamento,
    r.descr
   FROM (gds_ui.ruoli_ui ru
     JOIN gds_types.ruoli r ON ((r.id = ru.id)));


ALTER TABLE gds_ui.vw_ruoli_ui OWNER TO postgres;

--
-- Name: vw_ruoli; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_ruoli AS
 SELECT vw_ruoli_ui.id,
    vw_ruoli_ui.ordinamento
   FROM gds_ui.vw_ruoli_ui;


ALTER TABLE gds_srv.vw_ruoli OWNER TO postgres;

--
-- Name: vw_soggetti_fisici; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_soggetti_fisici AS
 SELECT vw_soggetti_fisici.id,
    vw_soggetti_fisici.id_soggetto_fisico,
    vw_soggetti_fisici.nome,
    vw_soggetti_fisici.cognome,
    vw_soggetti_fisici.codice_fiscale,
    vw_soggetti_fisici.email,
    vw_soggetti_fisici.id_indirizzo,
    vw_soggetti_fisici.via,
    vw_soggetti_fisici.comune,
    vw_soggetti_fisici.cap,
    vw_soggetti_fisici.id_comune,
    vw_soggetti_fisici.cod_provincia,
    vw_soggetti_fisici.email AS pec
   FROM gds_notifiche.vw_soggetti_fisici;


ALTER TABLE gds_srv.vw_soggetti_fisici OWNER TO postgres;

--
-- Name: vw_tipi_macchina; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_tipi_macchina AS
 SELECT tm.id,
    tm.id_tipo_macchina,
    tm.descr,
    tm.descr_tipo_macchina
   FROM gds_types.vw_tipi_macchina tm;


ALTER TABLE gds_srv.vw_tipi_macchina OWNER TO postgres;

--
-- Name: vw_verbale_valori; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_verbale_valori AS
 SELECT vw_verbale_valori.id_verbale,
    vw_verbale_valori.id_valore,
    vw_verbale_valori.val,
    vw_verbale_valori.nome_campo,
    vw_verbale_valori.id_modulo_campo
   FROM gds.vw_verbale_valori;


ALTER TABLE gds_srv.vw_verbale_valori OWNER TO postgres;

--
-- Name: vw_verbali; Type: VIEW; Schema: gds_srv; Owner: postgres
--

CREATE VIEW gds_srv.vw_verbali AS
 SELECT vw_verbali.id,
    vw_verbali.id_verbale,
    vw_verbali.id_modulo,
    vw_verbali.ts,
    vw_verbali.descr,
    vw_verbali.descr_modulo,
    vw_verbali.data_verbale
   FROM gds.vw_verbali;


ALTER TABLE gds_srv.vw_verbali OWNER TO postgres;

--
-- Name: _h_ente_uo; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_ente_uo (
    id bigint NOT NULL,
    id_ente_uo bigint,
    id_ente bigint,
    id_uo bigint,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_ente_uo OWNER TO postgres;

--
-- Name: _h_ente_uo_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_ente_uo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_ente_uo_id_seq OWNER TO postgres;

--
-- Name: _h_ente_uo_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_ente_uo_id_seq OWNED BY gds_types._h_ente_uo.id;


--
-- Name: _h_enti; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_enti (
    id bigint NOT NULL,
    id_ente bigint,
    descr_ente character varying,
    id_asl integer,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_enti OWNER TO postgres;

--
-- Name: _h_enti_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_enti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_enti_id_seq OWNER TO postgres;

--
-- Name: _h_enti_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_enti_id_seq OWNED BY gds_types._h_enti.id;


--
-- Name: _h_esiti_per_fase; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_esiti_per_fase (
    id bigint NOT NULL,
    id_esito_per_fase bigint,
    descr_esito_per_fase character varying,
    riferimento_fase_esito character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_esiti_per_fase OWNER TO postgres;

--
-- Name: _h_esiti_per_fase_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_esiti_per_fase_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_esiti_per_fase_id_seq OWNER TO postgres;

--
-- Name: _h_esiti_per_fase_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_esiti_per_fase_id_seq OWNED BY gds_types._h_esiti_per_fase.id;


--
-- Name: _h_fasi; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_fasi (
    id bigint NOT NULL,
    id_fase bigint,
    descr character varying NOT NULL,
    prop bigint,
    cnt integer,
    cnt_prop integer,
    cnt_prop_nec integer,
    cnt_nec integer,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_fasi OWNER TO postgres;

--
-- Name: _h_fasi_; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_fasi_ (
    id bigint NOT NULL,
    id_fase_ bigint,
    primo_sopralluogo character varying,
    sopralluogo_prossimo character varying,
    "attività_prescrizione" character varying,
    "attività_accertamenti" character varying,
    "attività_sospensione" character varying,
    atti_indagine character varying,
    documetazione character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_fasi_ OWNER TO postgres;

--
-- Name: _h_fasi__id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_fasi__id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_fasi__id_seq OWNER TO postgres;

--
-- Name: _h_fasi__id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_fasi__id_seq OWNED BY gds_types._h_fasi_.id;


--
-- Name: _h_fasi_esiti; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_fasi_esiti (
    id bigint NOT NULL,
    id_fase_esito bigint,
    id_fase bigint,
    id_esito_per_fase bigint,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_fasi_esiti OWNER TO postgres;

--
-- Name: _h_fasi_esiti_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_fasi_esiti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_fasi_esiti_id_seq OWNER TO postgres;

--
-- Name: _h_fasi_esiti_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_fasi_esiti_id_seq OWNED BY gds_types._h_fasi_esiti.id;


--
-- Name: _h_fasi_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_fasi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_fasi_id_seq OWNER TO postgres;

--
-- Name: _h_fasi_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_fasi_id_seq OWNED BY gds_types._h_fasi.id;


--
-- Name: _h_moduli; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_moduli (
    id bigint NOT NULL,
    id_modulo bigint,
    descr text,
    nome_file text,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_moduli OWNER TO postgres;

--
-- Name: _h_moduli_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_moduli_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_moduli_id_seq OWNER TO postgres;

--
-- Name: _h_moduli_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_moduli_id_seq OWNED BY gds_types._h_moduli.id;


--
-- Name: _h_modulo_campi; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_modulo_campi (
    id bigint NOT NULL,
    id_modulo bigint,
    nome_campo text,
    tipo_campo bigint,
    id_modulo_campo bigint,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_modulo_campi OWNER TO postgres;

--
-- Name: _h_modulo_campi_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_modulo_campi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_modulo_campi_id_seq OWNER TO postgres;

--
-- Name: _h_modulo_campi_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_modulo_campi_id_seq OWNED BY gds_types._h_modulo_campi.id;


--
-- Name: _h_motivi; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_motivi (
    id bigint NOT NULL,
    id_motivo bigint,
    programma_vigilanza character varying,
    richiesta_vigilanza character varying,
    iniziativa_vigilanza character varying,
    indagine_infortunio character varying,
    indagine_malattia character varying,
    emissione_parere character varying,
    validita tsrange,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_motivi OWNER TO postgres;

--
-- Name: _h_motivi_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_motivi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_motivi_id_seq OWNER TO postgres;

--
-- Name: _h_motivi_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_motivi_id_seq OWNED BY gds_types._h_motivi.id;


--
-- Name: _h_motivi_isp; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_motivi_isp (
    id bigint NOT NULL,
    id_motivo_isp bigint,
    descr character varying NOT NULL,
    descr_interna character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_motivi_isp OWNER TO postgres;

--
-- Name: _h_motivi_isp_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_motivi_isp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_motivi_isp_id_seq OWNER TO postgres;

--
-- Name: _h_motivi_isp_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_motivi_isp_id_seq OWNED BY gds_types._h_motivi_isp.id;


--
-- Name: _h_natura_giuridica; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_natura_giuridica (
    id bigint NOT NULL,
    id_natura_giuridica bigint,
    codice character varying,
    abb character varying,
    descrizione character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_natura_giuridica OWNER TO postgres;

--
-- Name: _h_natura_giuridica_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_natura_giuridica_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_natura_giuridica_id_seq OWNER TO postgres;

--
-- Name: _h_natura_giuridica_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_natura_giuridica_id_seq OWNED BY gds_types._h_natura_giuridica.id;


--
-- Name: _h_nature_opera_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_nature_opera_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_nature_opera_id_seq OWNER TO postgres;

--
-- Name: _h_nature_opera_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_nature_opera_id_seq OWNED BY gds_types._h_nature_opera.id;


--
-- Name: _h_ruoli; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_ruoli (
    id bigint NOT NULL,
    id_ruolo bigint,
    descr character varying,
    richiesta_pec boolean,
    obbligatorio boolean,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_ruoli OWNER TO postgres;

--
-- Name: _h_ruoli_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_ruoli_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_ruoli_id_seq OWNER TO postgres;

--
-- Name: _h_ruoli_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_ruoli_id_seq OWNED BY gds_types._h_ruoli.id;


--
-- Name: _h_stati_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_stati_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_stati_id_seq OWNER TO postgres;

--
-- Name: _h_stati_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_stati_id_seq OWNED BY gds_types._h_stati.id;


--
-- Name: _h_stati_ispezione; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_stati_ispezione (
    id bigint NOT NULL,
    id_stato_ispezione bigint,
    descr character varying,
    modificabile boolean,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_stati_ispezione OWNER TO postgres;

--
-- Name: _h_stati_ispezione_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_stati_ispezione_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_stati_ispezione_id_seq OWNER TO postgres;

--
-- Name: _h_stati_ispezione_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_stati_ispezione_id_seq OWNED BY gds_types._h_stati_ispezione.id;


--
-- Name: _h_stati_ispezione_successivi; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_stati_ispezione_successivi (
    id bigint NOT NULL,
    id_stato_ispezione_successivo bigint,
    id_stato_ispezione_attuale bigint,
    id_stato_ispezione_prossimo bigint,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_stati_ispezione_successivi OWNER TO postgres;

--
-- Name: _h_stati_ispezione_successivi_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_stati_ispezione_successivi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_stati_ispezione_successivi_id_seq OWNER TO postgres;

--
-- Name: _h_stati_ispezione_successivi_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_stati_ispezione_successivi_id_seq OWNED BY gds_types._h_stati_ispezione_successivi.id;


--
-- Name: _h_stati_successivi; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_stati_successivi (
    id bigint NOT NULL,
    id_stato_successivo bigint,
    id_stato_attuale bigint,
    id_stato_prossimo bigint,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_stati_successivi OWNER TO postgres;

--
-- Name: _h_stati_successivi_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_stati_successivi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_stati_successivi_id_seq OWNER TO postgres;

--
-- Name: _h_stati_successivi_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_stati_successivi_id_seq OWNED BY gds_types._h_stati_successivi.id;


--
-- Name: _h_tipi_campo; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_tipi_campo (
    id bigint NOT NULL,
    id_tipo_campo bigint,
    descr text,
    codice text,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_tipi_campo OWNER TO postgres;

--
-- Name: _h_tipi_campo_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_tipi_campo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_tipi_campo_id_seq OWNER TO postgres;

--
-- Name: _h_tipi_campo_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_tipi_campo_id_seq OWNED BY gds_types._h_tipi_campo.id;


--
-- Name: _h_tipi_macchina; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_tipi_macchina (
    id bigint NOT NULL,
    id_tipo_macchina bigint,
    descr character varying NOT NULL,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_tipi_macchina OWNER TO postgres;

--
-- Name: _h_tipi_macchina_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_tipi_macchina_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_tipi_macchina_id_seq OWNER TO postgres;

--
-- Name: _h_tipi_macchina_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_tipi_macchina_id_seq OWNED BY gds_types._h_tipi_macchina.id;


--
-- Name: _h_tipi_sede; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_tipi_sede (
    id bigint NOT NULL,
    id_tipo_sede bigint,
    codice integer,
    descrizione character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_tipi_sede OWNER TO postgres;

--
-- Name: _h_tipi_sede_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_tipi_sede_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_tipi_sede_id_seq OWNER TO postgres;

--
-- Name: _h_tipi_sede_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_tipi_sede_id_seq OWNED BY gds_types._h_tipi_sede.id;


--
-- Name: _h_uo; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types._h_uo (
    id bigint NOT NULL,
    id_uo bigint,
    descr_uo character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_types._h_uo OWNER TO postgres;

--
-- Name: _h_uo_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types._h_uo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types._h_uo_id_seq OWNER TO postgres;

--
-- Name: _h_uo_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types._h_uo_id_seq OWNED BY gds_types._h_uo.id;


--
-- Name: fase_esiti_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.fase_esiti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.fase_esiti_id_seq OWNER TO postgres;

--
-- Name: moduli_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.moduli_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.moduli_id_seq OWNER TO postgres;

--
-- Name: moduli_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.moduli_id_seq OWNED BY gds_types.moduli.id;


--
-- Name: modulo_campi_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.modulo_campi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.modulo_campi_id_seq OWNER TO postgres;

--
-- Name: modulo_campi_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.modulo_campi_id_seq OWNED BY gds_types.modulo_campi.id;


--
-- Name: motivi; Type: TABLE; Schema: gds_types; Owner: postgres
--

CREATE TABLE gds_types.motivi (
    id bigint NOT NULL,
    programma_vigilanza character varying,
    richiesta_vigilanza character varying,
    iniziativa_vigilanza character varying,
    indagine_infortunio character varying,
    indagine_malattia character varying,
    emissione_parere character varying
);


ALTER TABLE gds_types.motivi OWNER TO postgres;

--
-- Name: motivi_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.motivi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.motivi_id_seq OWNER TO postgres;

--
-- Name: motivi_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.motivi_id_seq OWNED BY gds_types.motivi.id;


--
-- Name: natura_giuridica_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.natura_giuridica_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.natura_giuridica_id_seq OWNER TO postgres;

--
-- Name: natura_giuridica_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.natura_giuridica_id_seq OWNED BY gds_types.natura_giuridica.id;


--
-- Name: nature_opera_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.nature_opera_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.nature_opera_id_seq OWNER TO postgres;

--
-- Name: nature_opera_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.nature_opera_id_seq OWNED BY gds_types.nature_opera.id;


--
-- Name: ruoli_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.ruoli_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.ruoli_id_seq OWNER TO postgres;

--
-- Name: ruoli_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.ruoli_id_seq OWNED BY gds_types.ruoli.id;


--
-- Name: stati_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.stati_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.stati_id_seq OWNER TO postgres;

--
-- Name: stati_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.stati_id_seq OWNED BY gds_types.stati.id;


--
-- Name: stati_notifica_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.stati_notifica_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.stati_notifica_id_seq OWNER TO postgres;

--
-- Name: stati_notifica_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.stati_notifica_id_seq OWNED BY gds_types.stati_ispezione.id;


--
-- Name: stati_notifica_successivi_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.stati_notifica_successivi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.stati_notifica_successivi_id_seq OWNER TO postgres;

--
-- Name: stati_notifica_successivi_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.stati_notifica_successivi_id_seq OWNED BY gds_types.stati_ispezione_successivi.id;


--
-- Name: stati_successivi_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.stati_successivi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.stati_successivi_id_seq OWNER TO postgres;

--
-- Name: stati_successivi_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.stati_successivi_id_seq OWNED BY gds_types.stati_successivi.id;


--
-- Name: tipi_campo_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.tipi_campo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.tipi_campo_id_seq OWNER TO postgres;

--
-- Name: tipi_campo_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.tipi_campo_id_seq OWNED BY gds_types.tipi_campo.id;


--
-- Name: tipi_macchina_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.tipi_macchina_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.tipi_macchina_id_seq OWNER TO postgres;

--
-- Name: tipi_macchina_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.tipi_macchina_id_seq OWNED BY gds_types.tipi_macchina.id;


--
-- Name: tipi_sede_id_seq; Type: SEQUENCE; Schema: gds_types; Owner: postgres
--

CREATE SEQUENCE gds_types.tipi_sede_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_types.tipi_sede_id_seq OWNER TO postgres;

--
-- Name: tipi_sede_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_types; Owner: postgres
--

ALTER SEQUENCE gds_types.tipi_sede_id_seq OWNED BY gds_types.tipi_sede.id;


--
-- Name: vw__fasi; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw__fasi AS
 SELECT f.id,
    f.id AS id_fase,
    f.primo_sopralluogo,
    f.sopralluogo_prossimo,
    f."attività_prescrizione",
    f."attività_accertamenti",
    f."attività_sospensione",
    f.atti_indagine,
    f.documetazione
   FROM gds_types.fasi_ f;


ALTER TABLE gds_types.vw__fasi OWNER TO postgres;

--
-- Name: vw_h_ente_uo; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_ente_uo AS
 SELECT heu.id,
    heu.id AS id_ente_uo,
    heu.id_ente,
    heu.id_uo,
    heu.validita,
    heu.id_transazione,
    heu.ts,
    heu.ts_transazione
   FROM gds_types._h_ente_uo heu;


ALTER TABLE gds_types.vw_h_ente_uo OWNER TO postgres;

--
-- Name: vw_h_enti; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_enti AS
 SELECT he.id,
    he.id AS id_ente,
    he.descr_ente,
    he.id_asl,
    he.validita,
    he.id_transazione,
    he.ts,
    he.ts_transazione
   FROM gds_types._h_enti he;


ALTER TABLE gds_types.vw_h_enti OWNER TO postgres;

--
-- Name: vw_h_esiti_per_fase; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_esiti_per_fase AS
 SELECT hef.id,
    hef.id AS id_esito_per_fase,
    hef.descr_esito_per_fase,
    hef.riferimento_fase_esito,
    hef.validita,
    hef.id_transazione,
    hef.ts,
    hef.ts_transazione
   FROM gds_types._h_esiti_per_fase hef;


ALTER TABLE gds_types.vw_h_esiti_per_fase OWNER TO postgres;

--
-- Name: vw_h_fasi; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_fasi AS
 SELECT hf.id,
    hf.id AS id_fase,
    hf.descr,
    hf.prop,
    hf.cnt,
    hf.cnt_prop,
    hf.cnt_prop_nec,
    hf.cnt_nec,
    hf.validita,
    hf.id_transazione,
    hf.ts,
    hf.ts_transazione
   FROM gds_types._h_fasi hf;


ALTER TABLE gds_types.vw_h_fasi OWNER TO postgres;

--
-- Name: vw_h_fasi_; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_fasi_ AS
 SELECT hf.id,
    hf.id AS id_fase_,
    hf.primo_sopralluogo,
    hf.sopralluogo_prossimo,
    hf."attività_prescrizione",
    hf."attività_accertamenti",
    hf."attività_sospensione",
    hf.atti_indagine,
    hf.documetazione,
    hf.validita,
    hf.id_transazione,
    hf.ts,
    hf.ts_transazione
   FROM gds_types._h_fasi_ hf;


ALTER TABLE gds_types.vw_h_fasi_ OWNER TO postgres;

--
-- Name: vw_h_fasi_esiti; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_fasi_esiti AS
 SELECT hfe.id,
    hfe.id AS id_fase_esito,
    hfe.id_fase,
    hfe.id_esito_per_fase,
    hfe.validita,
    hfe.id_transazione,
    hfe.ts,
    hfe.ts_transazione
   FROM gds_types._h_fasi_esiti hfe;


ALTER TABLE gds_types.vw_h_fasi_esiti OWNER TO postgres;

--
-- Name: vw_h_moduli; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_moduli AS
 SELECT hm.id,
    hm.id AS id_modulo,
    hm.id_transazione,
    hm.descr,
    hm.nome_file,
    hm.validita,
    hm.ts,
    hm.ts_transazione
   FROM gds_types._h_moduli hm;


ALTER TABLE gds_types.vw_h_moduli OWNER TO postgres;

--
-- Name: vw_h_modulo_campi; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_modulo_campi AS
 SELECT hmc.id,
    hmc.id AS id_modulo_campo,
    hmc.id_modulo,
    hmc.id_transazione,
    hmc.nome_campo,
    hmc.tipo_campo,
    hmc.validita,
    hmc.ts,
    hmc.ts_transazione
   FROM gds_types._h_modulo_campi hmc;


ALTER TABLE gds_types.vw_h_modulo_campi OWNER TO postgres;

--
-- Name: vw_h_motivi; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_motivi AS
 SELECT hm.id,
    hm.id AS id_motivo,
    hm.programma_vigilanza,
    hm.richiesta_vigilanza,
    hm.iniziativa_vigilanza,
    hm.indagine_infortunio,
    hm.indagine_malattia,
    hm.emissione_parere,
    hm.validita,
    hm.id_transazione,
    hm.ts,
    hm.ts_transazione
   FROM gds_types._h_motivi hm;


ALTER TABLE gds_types.vw_h_motivi OWNER TO postgres;

--
-- Name: vw_h_motivi_isp; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_motivi_isp AS
 SELECT hmi.id,
    hmi.id AS id_motivo_isp,
    hmi.descr,
    hmi.descr_interna,
    hmi.validita,
    hmi.id_transazione,
    hmi.ts,
    hmi.ts_transazione
   FROM gds_types._h_motivi_isp hmi;


ALTER TABLE gds_types.vw_h_motivi_isp OWNER TO postgres;

--
-- Name: vw_h_natura_giuridica; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_natura_giuridica AS
 SELECT hng.id,
    hng.id AS id_natura_giuridica,
    hng.codice,
    hng.abb,
    hng.descrizione,
    hng.validita,
    hng.id_transazione,
    hng.ts,
    hng.ts_transazione
   FROM gds_types._h_natura_giuridica hng;


ALTER TABLE gds_types.vw_h_natura_giuridica OWNER TO postgres;

--
-- Name: vw_h_ruoli; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_ruoli AS
 SELECT hr.id,
    hr.id AS id_ruolo,
    hr.descr,
    hr.richiesta_pec,
    hr.obbligatorio,
    hr.validita,
    hr.ts,
    hr.id_transazione,
    hr.ts_transazione
   FROM gds_types._h_ruoli hr;


ALTER TABLE gds_types.vw_h_ruoli OWNER TO postgres;

--
-- Name: vw_h_stati_ispezione; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_stati_ispezione AS
 SELECT hsi.id,
    hsi.id AS id_stato_ispezione,
    hsi.descr,
    hsi.modificabile,
    hsi.validita,
    hsi.id_transazione,
    hsi.ts,
    hsi.ts_transazione
   FROM gds_types._h_stati_ispezione hsi;


ALTER TABLE gds_types.vw_h_stati_ispezione OWNER TO postgres;

--
-- Name: vw_h_stati_ispezione_successivi; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_stati_ispezione_successivi AS
 SELECT hsis.id,
    hsis.id AS id_stato_ispezione_successivo,
    hsis.id_stato_ispezione_attuale,
    hsis.id_stato_ispezione_prossimo,
    hsis.validita,
    hsis.id_transazione,
    hsis.ts,
    hsis.ts_transazione
   FROM gds_types._h_stati_ispezione_successivi hsis;


ALTER TABLE gds_types.vw_h_stati_ispezione_successivi OWNER TO postgres;

--
-- Name: vw_h_stati_successivi; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_stati_successivi AS
 SELECT hss.id,
    hss.id AS id_stato_successivo,
    hss.id_stato_attuale,
    hss.id_stato_prossimo,
    hss.validita,
    hss.id_transazione,
    hss.ts,
    hss.ts_transazione
   FROM gds_types._h_stati_successivi hss;


ALTER TABLE gds_types.vw_h_stati_successivi OWNER TO postgres;

--
-- Name: vw_h_tipi_campo; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_tipi_campo AS
 SELECT htc.id,
    htc.id AS id_tipo_campo,
    htc.descr,
    htc.codice,
    htc.validita,
    htc.ts,
    htc.ts_transazione,
    htc.id_transazione
   FROM gds_types._h_tipi_campo htc;


ALTER TABLE gds_types.vw_h_tipi_campo OWNER TO postgres;

--
-- Name: vw_h_tipi_macchina; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_tipi_macchina AS
 SELECT htm.id,
    htm.id AS id_tipo_macchina,
    htm.descr,
    htm.validita,
    htm.id_transazione,
    htm.ts,
    htm.ts_transazione
   FROM gds_types._h_tipi_macchina htm;


ALTER TABLE gds_types.vw_h_tipi_macchina OWNER TO postgres;

--
-- Name: vw_h_tipi_sede; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_tipi_sede AS
 SELECT hts.id,
    hts.id AS id_tipo_sede,
    hts.codice,
    hts.descrizione,
    hts.validita,
    hts.id_transazione,
    hts.ts,
    hts.ts_transazione
   FROM gds_types._h_tipi_sede hts;


ALTER TABLE gds_types.vw_h_tipi_sede OWNER TO postgres;

--
-- Name: vw_h_uo; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_h_uo AS
 SELECT hu.id,
    hu.id AS id_uo,
    hu.descr_uo,
    hu.validita,
    hu.id_transazione,
    hu.ts,
    hu.ts_transazione
   FROM gds_types._h_uo hu;


ALTER TABLE gds_types.vw_h_uo OWNER TO postgres;

--
-- Name: vw_motivi; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_motivi AS
 SELECT m.id,
    m.id AS id_motivo,
    m.programma_vigilanza,
    m.richiesta_vigilanza,
    m.iniziativa_vigilanza,
    m.indagine_infortunio,
    m.indagine_malattia,
    m.emissione_parere
   FROM gds_types.motivi m;


ALTER TABLE gds_types.vw_motivi OWNER TO postgres;

--
-- Name: vw_ph_nature_opere; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_ph_nature_opere AS
 SELECT hnt.id,
    hnt.id AS id_natura_opera,
    hnt.descr,
    hnt.descr AS natura_opera,
    hnt.validita,
    hnt.ts_transazione,
    t.id AS id_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
   FROM (gds_types._h_nature_opera hnt
     JOIN gds_log.transazioni t ON ((t.ts <@ hnt.validita)));


ALTER TABLE gds_types.vw_ph_nature_opere OWNER TO postgres;

--
-- Name: vw_ph_stati; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_ph_stati AS
 SELECT hs.id,
    hs.id AS id_stato,
    hs.descr,
    hs.descr AS stato,
    hs.modificabile,
    hs.modificabile_cantiere,
    hs.validita,
    t.id AS id_transazione,
    hs.ts_transazione,
    t.id_user,
    t.ts,
    t.descr AS descrizione_transazione
   FROM (gds_types._h_stati hs
     JOIN gds_log.transazioni t ON ((t.ts <@ hs.validita)));


ALTER TABLE gds_types.vw_ph_stati OWNER TO postgres;

--
-- Name: vw_ruoli; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_ruoli AS
 SELECT r.id,
    r.id AS id_ruolo,
    r.descr,
    r.descr AS ruolo,
    r.obbligatorio
   FROM gds_types.ruoli r;


ALTER TABLE gds_types.vw_ruoli OWNER TO postgres;

--
-- Name: vw_tipi_campo; Type: VIEW; Schema: gds_types; Owner: postgres
--

CREATE VIEW gds_types.vw_tipi_campo AS
 SELECT tc.id,
    tc.id AS id_tipo_campo,
    tc.descr,
    tc.codice
   FROM gds_types.tipi_campo tc;


ALTER TABLE gds_types.vw_tipi_campo OWNER TO postgres;

--
-- Name: _h_messaggi_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui._h_messaggi_ui (
    id bigint NOT NULL,
    id_messaggio_ui bigint,
    procedura character varying,
    valore bigint,
    msg character varying,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_ui._h_messaggi_ui OWNER TO postgres;

--
-- Name: _h_messaggi_ui_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui._h_messaggi_ui_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui._h_messaggi_ui_id_seq OWNER TO postgres;

--
-- Name: _h_messaggi_ui_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_ui; Owner: postgres
--

ALTER SEQUENCE gds_ui._h_messaggi_ui_id_seq OWNED BY gds_ui._h_messaggi_ui.id;


--
-- Name: _h_natura_opere_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui._h_natura_opere_ui (
    id bigint NOT NULL,
    id_natura_opera_ui bigint,
    ordinamento integer,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_ui._h_natura_opere_ui OWNER TO postgres;

--
-- Name: _h_natura_opere_ui_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui._h_natura_opere_ui_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui._h_natura_opere_ui_id_seq OWNER TO postgres;

--
-- Name: _h_natura_opere_ui_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_ui; Owner: postgres
--

ALTER SEQUENCE gds_ui._h_natura_opere_ui_id_seq OWNED BY gds_ui._h_natura_opere_ui.id;


--
-- Name: _h_ruoli_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui._h_ruoli_ui (
    id bigint NOT NULL,
    id_ruolo_ui bigint,
    ordinamento integer,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_ui._h_ruoli_ui OWNER TO postgres;

--
-- Name: _h_ruoli_ui_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui._h_ruoli_ui_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui._h_ruoli_ui_id_seq OWNER TO postgres;

--
-- Name: _h_ruoli_ui_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_ui; Owner: postgres
--

ALTER SEQUENCE gds_ui._h_ruoli_ui_id_seq OWNED BY gds_ui._h_ruoli_ui.id;


--
-- Name: _h_stati_ispezione_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui._h_stati_ispezione_ui (
    id bigint NOT NULL,
    id_stato_ispezione_ui bigint,
    label_bottone character varying,
    colore character varying,
    msg character varying,
    ordine integer,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_ui._h_stati_ispezione_ui OWNER TO postgres;

--
-- Name: _h_stati_ispezione_ui_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui._h_stati_ispezione_ui_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui._h_stati_ispezione_ui_id_seq OWNER TO postgres;

--
-- Name: _h_stati_ispezione_ui_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_ui; Owner: postgres
--

ALTER SEQUENCE gds_ui._h_stati_ispezione_ui_id_seq OWNED BY gds_ui._h_stati_ispezione_ui.id;


--
-- Name: _h_stati_notifica_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui._h_stati_notifica_ui (
    id bigint NOT NULL,
    id_stato_notifica_ui bigint,
    label_bottone character varying,
    colore character varying,
    msg character varying,
    ordine integer,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_ui._h_stati_notifica_ui OWNER TO postgres;

--
-- Name: _h_stati_notifica_ui_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui._h_stati_notifica_ui_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui._h_stati_notifica_ui_id_seq OWNER TO postgres;

--
-- Name: _h_stati_notifica_ui_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_ui; Owner: postgres
--

ALTER SEQUENCE gds_ui._h_stati_notifica_ui_id_seq OWNED BY gds_ui._h_stati_notifica_ui.id;


--
-- Name: _h_stati_successivi_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui._h_stati_successivi_ui (
    id bigint NOT NULL,
    id_stato_successivo_ui bigint,
    label_bottone character varying,
    colore character varying,
    msg character varying,
    ordine integer,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_ui._h_stati_successivi_ui OWNER TO postgres;

--
-- Name: _h_stati_successivi_ui_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui._h_stati_successivi_ui_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui._h_stati_successivi_ui_id_seq OWNER TO postgres;

--
-- Name: _h_stati_successivi_ui_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_ui; Owner: postgres
--

ALTER SEQUENCE gds_ui._h_stati_successivi_ui_id_seq OWNED BY gds_ui._h_stati_successivi_ui.id;


--
-- Name: _h_stati_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui._h_stati_ui (
    id bigint NOT NULL,
    id_stato_ui bigint,
    label_bottone character varying,
    colore character varying,
    msg character varying,
    "validità" tsrange,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_ui._h_stati_ui OWNER TO postgres;

--
-- Name: _h_stati_ui_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui._h_stati_ui_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui._h_stati_ui_id_seq OWNER TO postgres;

--
-- Name: _h_stati_ui_id_seq1; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui._h_stati_ui_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui._h_stati_ui_id_seq1 OWNER TO postgres;

--
-- Name: _h_stati_ui_id_seq1; Type: SEQUENCE OWNED BY; Schema: gds_ui; Owner: postgres
--

ALTER SEQUENCE gds_ui._h_stati_ui_id_seq1 OWNED BY gds_ui._h_stati_ui.id;


--
-- Name: _h_verbali_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui._h_verbali_ui (
    id bigint NOT NULL,
    id_verbale_ui bigint,
    descr text,
    filename text,
    validita tsrange NOT NULL,
    id_transazione bigint,
    ts timestamp without time zone,
    ts_transazione timestamp without time zone
);


ALTER TABLE gds_ui._h_verbali_ui OWNER TO postgres;

--
-- Name: _h_verbali_ui_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui._h_verbali_ui_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui._h_verbali_ui_id_seq OWNER TO postgres;

--
-- Name: _h_verbali_ui_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_ui; Owner: postgres
--

ALTER SEQUENCE gds_ui._h_verbali_ui_id_seq OWNED BY gds_ui._h_verbali_ui.id;


--
-- Name: messaggi_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui.messaggi_ui (
    id bigint NOT NULL,
    procedura character varying,
    valore bigint,
    msg character varying
);


ALTER TABLE gds_ui.messaggi_ui OWNER TO postgres;

--
-- Name: natura_opere_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui.natura_opere_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui.natura_opere_id_seq OWNER TO postgres;

--
-- Name: natura_opere_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_ui; Owner: postgres
--

ALTER SEQUENCE gds_ui.natura_opere_id_seq OWNED BY gds_ui.natura_opere.id;


--
-- Name: ruoli_ui_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui.ruoli_ui_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui.ruoli_ui_id_seq OWNER TO postgres;

--
-- Name: ruoli_ui_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_ui; Owner: postgres
--

ALTER SEQUENCE gds_ui.ruoli_ui_id_seq OWNED BY gds_ui.ruoli_ui.id;


--
-- Name: stati_notifica_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui.stati_notifica_ui (
    id bigint NOT NULL,
    label_bottone character varying,
    colore character varying,
    msg character varying,
    ordine integer
);


ALTER TABLE gds_ui.stati_notifica_ui OWNER TO postgres;

--
-- Name: stati_ui_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui.stati_ui_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui.stati_ui_id_seq OWNER TO postgres;

--
-- Name: verbali_ui; Type: TABLE; Schema: gds_ui; Owner: postgres
--

CREATE TABLE gds_ui.verbali_ui (
    id bigint NOT NULL,
    descr text,
    filename text
);


ALTER TABLE gds_ui.verbali_ui OWNER TO postgres;

--
-- Name: verbali_id_seq; Type: SEQUENCE; Schema: gds_ui; Owner: postgres
--

CREATE SEQUENCE gds_ui.verbali_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gds_ui.verbali_id_seq OWNER TO postgres;

--
-- Name: verbali_id_seq; Type: SEQUENCE OWNED BY; Schema: gds_ui; Owner: postgres
--

ALTER SEQUENCE gds_ui.verbali_id_seq OWNED BY gds_ui.verbali_ui.id;


--
-- Name: vw_h_messaggi_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_h_messaggi_ui AS
 SELECT hmu.id,
    hmu.id AS id_messaggio,
    hmu.procedura,
    hmu.valore,
    hmu.msg,
    hmu.validita,
    hmu.ts,
    hmu.ts_transazione,
    hmu.id_transazione
   FROM gds_ui._h_messaggi_ui hmu;


ALTER TABLE gds_ui.vw_h_messaggi_ui OWNER TO postgres;

--
-- Name: vw_h_nature_opera_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_h_nature_opera_ui AS
 SELECT hnu.id,
    hnu.id AS id_natura_opera,
    hnu.ordinamento,
    hnu.validita,
    hnu.ts,
    hnu.ts_transazione,
    hnu.id_transazione
   FROM gds_ui._h_natura_opere_ui hnu;


ALTER TABLE gds_ui.vw_h_nature_opera_ui OWNER TO postgres;

--
-- Name: vw_h_ruoli_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_h_ruoli_ui AS
 SELECT hru.id,
    hru.id AS id_ruolo,
    hru.ordinamento,
    hru.validita,
    hru.id_transazione,
    hru.ts,
    hru.ts_transazione
   FROM gds_ui._h_ruoli_ui hru;


ALTER TABLE gds_ui.vw_h_ruoli_ui OWNER TO postgres;

--
-- Name: vw_h_stati_ispezione_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_h_stati_ispezione_ui AS
 SELECT hsiu.id,
    hsiu.id AS id_ispezione_ui,
    hsiu.label_bottone,
    hsiu.colore,
    hsiu.msg,
    hsiu.ordine,
    hsiu.validita,
    hsiu.id_transazione,
    hsiu.ts,
    hsiu.ts_transazione
   FROM gds_ui._h_stati_ispezione_ui hsiu;


ALTER TABLE gds_ui.vw_h_stati_ispezione_ui OWNER TO postgres;

--
-- Name: vw_h_stati_notifica_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_h_stati_notifica_ui AS
 SELECT snu.id,
    snu.id AS id_stato_notifica_ui,
    snu.label_bottone,
    snu.colore,
    snu.msg,
    snu.ordine,
    snu.validita,
    snu.id_transazione,
    snu.ts,
    snu.ts_transazione
   FROM gds_ui._h_stati_notifica_ui snu;


ALTER TABLE gds_ui.vw_h_stati_notifica_ui OWNER TO postgres;

--
-- Name: vw_h_stati_successivi_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_h_stati_successivi_ui AS
 SELECT ssu.id,
    ssu.id AS id_stato_successivo_ui,
    ssu.label_bottone,
    ssu.colore,
    ssu.msg,
    ssu.ordine,
    ssu.validita,
    ssu.id_transazione,
    ssu.ts,
    ssu.ts_transazione
   FROM gds_ui._h_stati_successivi_ui ssu;


ALTER TABLE gds_ui.vw_h_stati_successivi_ui OWNER TO postgres;

--
-- Name: vw_h_stati_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_h_stati_ui AS
 SELECT hsu.id,
    hsu.id AS id_stato,
    hsu.label_bottone,
    hsu.colore,
    hsu.validita,
    hsu.ts,
    hsu.ts_transazione,
    hsu.id_transazione
   FROM gds_ui._h_stati_ui hsu;


ALTER TABLE gds_ui.vw_h_stati_ui OWNER TO postgres;

--
-- Name: vw_h_verbali_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_h_verbali_ui AS
 SELECT vu.id,
    vu.id AS id_verbale,
    vu.descr AS verbale,
    vu.filename,
    vu.validita,
    vu.ts_transazione,
    vu.id_transazione,
    vu.ts
   FROM gds_ui._h_verbali_ui vu;


ALTER TABLE gds_ui.vw_h_verbali_ui OWNER TO postgres;

--
-- Name: vw_messaggi_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_messaggi_ui AS
 SELECT mu.id,
    mu.id AS id_messaggio,
    mu.procedura,
    mu.valore,
    mu.msg
   FROM gds_ui.messaggi_ui mu;


ALTER TABLE gds_ui.vw_messaggi_ui OWNER TO postgres;

--
-- Name: vw_stati_notifica_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_stati_notifica_ui AS
 SELECT su.id AS id_stato,
    su.label_bottone,
    su.colore,
    su.msg,
    su.ordine
   FROM (gds_ui.stati_notifica_ui su
     JOIN gds_types.stati_ispezione s ON ((su.id = s.id)));


ALTER TABLE gds_ui.vw_stati_notifica_ui OWNER TO postgres;

--
-- Name: vw_verbali_ui; Type: VIEW; Schema: gds_ui; Owner: postgres
--

CREATE VIEW gds_ui.vw_verbali_ui AS
 SELECT vu.id,
    vu.id AS id_verbale_ui,
    vu.descr,
    vu.filename
   FROM gds_ui.verbali_ui vu;


ALTER TABLE gds_ui.vw_verbali_ui OWNER TO postgres;

--
-- Name: allegati id; Type: DEFAULT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.allegati ALTER COLUMN id SET DEFAULT nextval('gds.allegati_id_seq'::regclass);


--
-- Name: fase_persone id; Type: DEFAULT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.fase_persone ALTER COLUMN id SET DEFAULT nextval('gds.fase_persone_id_seq'::regclass);


--
-- Name: fase_verbali id; Type: DEFAULT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.fase_verbali ALTER COLUMN id SET DEFAULT nextval('gds.fase_verbali_id_seq'::regclass);


--
-- Name: impresa_sedi id; Type: DEFAULT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.impresa_sedi ALTER COLUMN id SET DEFAULT nextval('gds.impresa_sedi_id_seq'::regclass);


--
-- Name: ispezione_allegati id; Type: DEFAULT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.ispezione_allegati ALTER COLUMN id SET DEFAULT nextval('gds.ispezione_allegati_id_seq'::regclass);


--
-- Name: ispezione_imprese id; Type: DEFAULT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.ispezione_imprese ALTER COLUMN id SET DEFAULT nextval('gds.ispezione_imprese_id_seq'::regclass);


--
-- Name: verbale_valori id; Type: DEFAULT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.verbale_valori ALTER COLUMN id SET DEFAULT nextval('gds.verbale_valori_id_seq'::regclass);


--
-- Name: verbali id; Type: DEFAULT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.verbali ALTER COLUMN id SET DEFAULT nextval('gds.verbali_id_seq'::regclass);


--
-- Name: call_logs id; Type: DEFAULT; Schema: gds_log; Owner: postgres
--

ALTER TABLE ONLY gds_log.call_logs ALTER COLUMN id SET DEFAULT nextval('gds_log.call_logs_id_seq'::regclass);


--
-- Name: operazioni id; Type: DEFAULT; Schema: gds_log; Owner: postgres
--

ALTER TABLE ONLY gds_log.operazioni ALTER COLUMN id SET DEFAULT nextval('gds_log.operazioni_id_seq'::regclass);


--
-- Name: transazioni id; Type: DEFAULT; Schema: gds_log; Owner: postgres
--

ALTER TABLE ONLY gds_log.transazioni ALTER COLUMN id SET DEFAULT nextval('gds_log.transazioni_id_seq'::regclass);


--
-- Name: _h_costruttori id; Type: DEFAULT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine._h_costruttori ALTER COLUMN id SET DEFAULT nextval('gds_macchine._h_costruttori_id_seq'::regclass);


--
-- Name: _h_macchine id; Type: DEFAULT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine._h_macchine ALTER COLUMN id SET DEFAULT nextval('gds_macchine._h_macchine_id_seq'::regclass);


--
-- Name: costruttori id; Type: DEFAULT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine.costruttori ALTER COLUMN id SET DEFAULT nextval('gds_macchine.costruttori_id_seq'::regclass);


--
-- Name: macchine id; Type: DEFAULT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine.macchine ALTER COLUMN id SET DEFAULT nextval('gds_macchine.macchine_id_seq'::regclass);


--
-- Name: _h_cantiere_persona_ruoli id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_cantiere_persona_ruoli ALTER COLUMN id SET DEFAULT nextval('gds_notifiche._h_cantiere_persona_ruoli_id_seq'::regclass);


--
-- Name: _h_cantieri id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_cantieri ALTER COLUMN id SET DEFAULT nextval('gds_notifiche._h_cantieri_id_seq'::regclass);


--
-- Name: _h_documenti id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_documenti ALTER COLUMN id SET DEFAULT nextval('gds_notifiche._h_documenti_id_seq'::regclass);


--
-- Name: _h_email_asl id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_email_asl ALTER COLUMN id SET DEFAULT nextval('gds_notifiche._h_email_asl_id_seq'::regclass);


--
-- Name: _h_imprese id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_imprese ALTER COLUMN id SET DEFAULT nextval('gds_notifiche._h_imprese_id_seq'::regclass);


--
-- Name: _h_notifiche id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_notifiche ALTER COLUMN id SET DEFAULT nextval('gds_notifiche._h_notifiche_id_seq'::regclass);


--
-- Name: _h_soggetti_fisici id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_soggetti_fisici ALTER COLUMN id SET DEFAULT nextval('gds_notifiche._h_soggetti_fisici_id_seq'::regclass);


--
-- Name: cantiere_imprese id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantiere_imprese ALTER COLUMN id SET DEFAULT nextval('gds_notifiche.cantiere_imprese_id_seq'::regclass);


--
-- Name: cantiere_persona_ruoli id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantiere_persona_ruoli ALTER COLUMN id SET DEFAULT nextval('gds_notifiche.cantiere_persona_ruoli_id_seq'::regclass);


--
-- Name: cantieri id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantieri ALTER COLUMN id SET DEFAULT nextval('gds_notifiche.cantieri_id_seq'::regclass);


--
-- Name: codici id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.codici ALTER COLUMN id SET DEFAULT nextval('gds_notifiche.codici_id_seq'::regclass);


--
-- Name: documenti id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.documenti ALTER COLUMN id SET DEFAULT nextval('gds_notifiche.documenti_id_seq'::regclass);


--
-- Name: email_asl id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.email_asl ALTER COLUMN id SET DEFAULT nextval('gds_notifiche.email_asl_id_seq'::regclass);


--
-- Name: imprese id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.imprese ALTER COLUMN id SET DEFAULT nextval('gds_notifiche.imprese_id_seq'::regclass);


--
-- Name: notifiche id; Type: DEFAULT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.notifiche ALTER COLUMN id SET DEFAULT nextval('gds_notifiche.notifiche_id_seq'::regclass);


--
-- Name: _h_ente_uo id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_ente_uo ALTER COLUMN id SET DEFAULT nextval('gds_types._h_ente_uo_id_seq'::regclass);


--
-- Name: _h_enti id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_enti ALTER COLUMN id SET DEFAULT nextval('gds_types._h_enti_id_seq'::regclass);


--
-- Name: _h_esiti_per_fase id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_esiti_per_fase ALTER COLUMN id SET DEFAULT nextval('gds_types._h_esiti_per_fase_id_seq'::regclass);


--
-- Name: _h_fasi id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_fasi ALTER COLUMN id SET DEFAULT nextval('gds_types._h_fasi_id_seq'::regclass);


--
-- Name: _h_fasi_ id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_fasi_ ALTER COLUMN id SET DEFAULT nextval('gds_types._h_fasi__id_seq'::regclass);


--
-- Name: _h_fasi_esiti id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_fasi_esiti ALTER COLUMN id SET DEFAULT nextval('gds_types._h_fasi_esiti_id_seq'::regclass);


--
-- Name: _h_moduli id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_moduli ALTER COLUMN id SET DEFAULT nextval('gds_types._h_moduli_id_seq'::regclass);


--
-- Name: _h_modulo_campi id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_modulo_campi ALTER COLUMN id SET DEFAULT nextval('gds_types._h_modulo_campi_id_seq'::regclass);


--
-- Name: _h_motivi id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_motivi ALTER COLUMN id SET DEFAULT nextval('gds_types._h_motivi_id_seq'::regclass);


--
-- Name: _h_motivi_isp id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_motivi_isp ALTER COLUMN id SET DEFAULT nextval('gds_types._h_motivi_isp_id_seq'::regclass);


--
-- Name: _h_natura_giuridica id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_natura_giuridica ALTER COLUMN id SET DEFAULT nextval('gds_types._h_natura_giuridica_id_seq'::regclass);


--
-- Name: _h_nature_opera id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_nature_opera ALTER COLUMN id SET DEFAULT nextval('gds_types._h_nature_opera_id_seq'::regclass);


--
-- Name: _h_ruoli id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_ruoli ALTER COLUMN id SET DEFAULT nextval('gds_types._h_ruoli_id_seq'::regclass);


--
-- Name: _h_stati id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati ALTER COLUMN id SET DEFAULT nextval('gds_types._h_stati_id_seq'::regclass);


--
-- Name: _h_stati_ispezione id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati_ispezione ALTER COLUMN id SET DEFAULT nextval('gds_types._h_stati_ispezione_id_seq'::regclass);


--
-- Name: _h_stati_ispezione_successivi id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati_ispezione_successivi ALTER COLUMN id SET DEFAULT nextval('gds_types._h_stati_ispezione_successivi_id_seq'::regclass);


--
-- Name: _h_stati_successivi id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati_successivi ALTER COLUMN id SET DEFAULT nextval('gds_types._h_stati_successivi_id_seq'::regclass);


--
-- Name: _h_tipi_campo id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_tipi_campo ALTER COLUMN id SET DEFAULT nextval('gds_types._h_tipi_campo_id_seq'::regclass);


--
-- Name: _h_tipi_macchina id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_tipi_macchina ALTER COLUMN id SET DEFAULT nextval('gds_types._h_tipi_macchina_id_seq'::regclass);


--
-- Name: _h_tipi_sede id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_tipi_sede ALTER COLUMN id SET DEFAULT nextval('gds_types._h_tipi_sede_id_seq'::regclass);


--
-- Name: _h_uo id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_uo ALTER COLUMN id SET DEFAULT nextval('gds_types._h_uo_id_seq'::regclass);


--
-- Name: fasi_ id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.fasi_ ALTER COLUMN id SET DEFAULT nextval('gds_types.fasi_id_seq'::regclass);


--
-- Name: moduli id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.moduli ALTER COLUMN id SET DEFAULT nextval('gds_types.moduli_id_seq'::regclass);


--
-- Name: modulo_campi id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.modulo_campi ALTER COLUMN id SET DEFAULT nextval('gds_types.modulo_campi_id_seq'::regclass);


--
-- Name: motivi id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.motivi ALTER COLUMN id SET DEFAULT nextval('gds_types.motivi_id_seq'::regclass);


--
-- Name: natura_giuridica id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.natura_giuridica ALTER COLUMN id SET DEFAULT nextval('gds_types.natura_giuridica_id_seq'::regclass);


--
-- Name: nature_opera id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.nature_opera ALTER COLUMN id SET DEFAULT nextval('gds_types.nature_opera_id_seq'::regclass);


--
-- Name: ruoli id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.ruoli ALTER COLUMN id SET DEFAULT nextval('gds_types.ruoli_id_seq'::regclass);


--
-- Name: stati id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.stati ALTER COLUMN id SET DEFAULT nextval('gds_types.stati_id_seq'::regclass);


--
-- Name: stati_ispezione id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.stati_ispezione ALTER COLUMN id SET DEFAULT nextval('gds_types.stati_notifica_id_seq'::regclass);


--
-- Name: stati_ispezione_successivi id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.stati_ispezione_successivi ALTER COLUMN id SET DEFAULT nextval('gds_types.stati_notifica_successivi_id_seq'::regclass);


--
-- Name: stati_successivi id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.stati_successivi ALTER COLUMN id SET DEFAULT nextval('gds_types.stati_successivi_id_seq'::regclass);


--
-- Name: tipi_campo id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.tipi_campo ALTER COLUMN id SET DEFAULT nextval('gds_types.tipi_campo_id_seq'::regclass);


--
-- Name: tipi_macchina id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.tipi_macchina ALTER COLUMN id SET DEFAULT nextval('gds_types.tipi_macchina_id_seq'::regclass);


--
-- Name: tipi_sede id; Type: DEFAULT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.tipi_sede ALTER COLUMN id SET DEFAULT nextval('gds_types.tipi_sede_id_seq'::regclass);


--
-- Name: _h_messaggi_ui id; Type: DEFAULT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_messaggi_ui ALTER COLUMN id SET DEFAULT nextval('gds_ui._h_messaggi_ui_id_seq'::regclass);


--
-- Name: _h_natura_opere_ui id; Type: DEFAULT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_natura_opere_ui ALTER COLUMN id SET DEFAULT nextval('gds_ui._h_natura_opere_ui_id_seq'::regclass);


--
-- Name: _h_ruoli_ui id; Type: DEFAULT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_ruoli_ui ALTER COLUMN id SET DEFAULT nextval('gds_ui._h_ruoli_ui_id_seq'::regclass);


--
-- Name: _h_stati_ispezione_ui id; Type: DEFAULT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_ispezione_ui ALTER COLUMN id SET DEFAULT nextval('gds_ui._h_stati_ispezione_ui_id_seq'::regclass);


--
-- Name: _h_stati_notifica_ui id; Type: DEFAULT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_notifica_ui ALTER COLUMN id SET DEFAULT nextval('gds_ui._h_stati_notifica_ui_id_seq'::regclass);


--
-- Name: _h_stati_successivi_ui id; Type: DEFAULT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_successivi_ui ALTER COLUMN id SET DEFAULT nextval('gds_ui._h_stati_successivi_ui_id_seq'::regclass);


--
-- Name: _h_stati_ui id; Type: DEFAULT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_ui ALTER COLUMN id SET DEFAULT nextval('gds_ui._h_stati_ui_id_seq1'::regclass);


--
-- Name: _h_verbali_ui id; Type: DEFAULT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_verbali_ui ALTER COLUMN id SET DEFAULT nextval('gds_ui._h_verbali_ui_id_seq'::regclass);


--
-- Name: natura_opere id; Type: DEFAULT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui.natura_opere ALTER COLUMN id SET DEFAULT nextval('gds_ui.natura_opere_id_seq'::regclass);


--
-- Name: ruoli_ui id; Type: DEFAULT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui.ruoli_ui ALTER COLUMN id SET DEFAULT nextval('gds_ui.ruoli_ui_id_seq'::regclass);


--
-- Name: verbali_ui id; Type: DEFAULT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui.verbali_ui ALTER COLUMN id SET DEFAULT nextval('gds_ui.verbali_id_seq'::regclass);


--
-- Name: _h_allegati _h_allegati_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_allegati
    ADD CONSTRAINT _h_allegati_pk PRIMARY KEY (id);


--
-- Name: _h_allegati _h_allegati_uk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_allegati
    ADD CONSTRAINT _h_allegati_uk UNIQUE (id_allegato, validita);


--
-- Name: _h_fase_persone _h_fase_persone_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_fase_persone
    ADD CONSTRAINT _h_fase_persone_pk PRIMARY KEY (id);


--
-- Name: _h_fase_persone _h_fase_persone_uk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_fase_persone
    ADD CONSTRAINT _h_fase_persone_uk UNIQUE (id_fase_persona, validita);


--
-- Name: _h_fase_verbali _h_fase_verbale_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_fase_verbali
    ADD CONSTRAINT _h_fase_verbale_pk PRIMARY KEY (id);


--
-- Name: _h_fase_verbali _h_fase_verbale_uk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_fase_verbali
    ADD CONSTRAINT _h_fase_verbale_uk UNIQUE (id_fase_verbale, validita);


--
-- Name: _h_impresa_sedi _h_impresa_sedi_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_impresa_sedi
    ADD CONSTRAINT _h_impresa_sedi_pk PRIMARY KEY (id);


--
-- Name: _h_impresa_sedi _h_impresa_sedi_uk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_impresa_sedi
    ADD CONSTRAINT _h_impresa_sedi_uk UNIQUE (id_impresa_sede, validita);


--
-- Name: _h_imprese _h_imprese_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_imprese
    ADD CONSTRAINT _h_imprese_pk PRIMARY KEY (id);


--
-- Name: _h_imprese _h_imprese_uk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_imprese
    ADD CONSTRAINT _h_imprese_uk UNIQUE (id_impresa, validita);


--
-- Name: _h_ispezione_fasi _h_ispezione_fasi_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_ispezione_fasi
    ADD CONSTRAINT _h_ispezione_fasi_pk PRIMARY KEY (id);


--
-- Name: _h_ispezione_fasi _h_ispezione_fasi_uk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_ispezione_fasi
    ADD CONSTRAINT _h_ispezione_fasi_uk UNIQUE (id_ispezione_fase, validita);


--
-- Name: _h_ispezione_imprese _h_ispezione_imprese_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_ispezione_imprese
    ADD CONSTRAINT _h_ispezione_imprese_pk PRIMARY KEY (id);


--
-- Name: _h_ispezione_imprese _h_ispezione_imprese_uk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_ispezione_imprese
    ADD CONSTRAINT _h_ispezione_imprese_uk UNIQUE (id_ispezione_impresa, validita);


--
-- Name: _h_ispezioni _h_ispezioni_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_ispezioni
    ADD CONSTRAINT _h_ispezioni_pk PRIMARY KEY (id);


--
-- Name: _h_ispezioni _h_ispezioni_uk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_ispezioni
    ADD CONSTRAINT _h_ispezioni_uk UNIQUE (id_ispezione, validita);


--
-- Name: _h_nucleo_ispettori _h_nucleo_ispettori_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_nucleo_ispettori
    ADD CONSTRAINT _h_nucleo_ispettori_pk PRIMARY KEY (id);


--
-- Name: _h_nucleo_ispettori _h_nucleo_ispettori_uk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_nucleo_ispettori
    ADD CONSTRAINT _h_nucleo_ispettori_uk UNIQUE (id_nucleo_ispettore, validita);


--
-- Name: _h_verbale_valori _h_verbale_valori_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_verbale_valori
    ADD CONSTRAINT _h_verbale_valori_pk PRIMARY KEY (id);


--
-- Name: _h_verbale_valori _h_verbale_valori_uk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_verbale_valori
    ADD CONSTRAINT _h_verbale_valori_uk UNIQUE (id_verbale_valore, validita);


--
-- Name: _h_verbali _h_verbali_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_verbali
    ADD CONSTRAINT _h_verbali_pk PRIMARY KEY (id);


--
-- Name: _h_verbali _h_verbali_uk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds._h_verbali
    ADD CONSTRAINT _h_verbali_uk UNIQUE (id_verbale, validita);


--
-- Name: impresa_sedi impresa_sedi_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.impresa_sedi
    ADD CONSTRAINT impresa_sedi_pk PRIMARY KEY (id);


--
-- Name: ispezione_imprese ispezione_imprese_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.ispezione_imprese
    ADD CONSTRAINT ispezione_imprese_pk PRIMARY KEY (id);


--
-- Name: ispezione_imprese ispezione_imprese_un; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.ispezione_imprese
    ADD CONSTRAINT ispezione_imprese_un UNIQUE (id_ispezione, id_impresa_sede);


--
-- Name: ispezioni ispezioni_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.ispezioni
    ADD CONSTRAINT ispezioni_pk PRIMARY KEY (id);


--
-- Name: nucleo_ispettori nucleo_ispettori_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.nucleo_ispettori
    ADD CONSTRAINT nucleo_ispettori_pk PRIMARY KEY (id);


--
-- Name: verbale_valori verbale_valori_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.verbale_valori
    ADD CONSTRAINT verbale_valori_pk PRIMARY KEY (id);


--
-- Name: verbale_valori verbale_valori_unique; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.verbale_valori
    ADD CONSTRAINT verbale_valori_unique UNIQUE (id_verbale, id_modulo_campo);


--
-- Name: verbali verbali_pk; Type: CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.verbali
    ADD CONSTRAINT verbali_pk PRIMARY KEY (id);


--
-- Name: call_logs call_logs_pk; Type: CONSTRAINT; Schema: gds_log; Owner: postgres
--

ALTER TABLE ONLY gds_log.call_logs
    ADD CONSTRAINT call_logs_pk PRIMARY KEY (id);


--
-- Name: operazioni operazioni_pk; Type: CONSTRAINT; Schema: gds_log; Owner: postgres
--

ALTER TABLE ONLY gds_log.operazioni
    ADD CONSTRAINT operazioni_pk PRIMARY KEY (id);


--
-- Name: _h_costruttori _h_costruttori_pk; Type: CONSTRAINT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine._h_costruttori
    ADD CONSTRAINT _h_costruttori_pk PRIMARY KEY (id);


--
-- Name: _h_costruttori _h_costruttori_uk; Type: CONSTRAINT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine._h_costruttori
    ADD CONSTRAINT _h_costruttori_uk UNIQUE (id_costruttore, validita);


--
-- Name: _h_macchine _h_macchine_pk; Type: CONSTRAINT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine._h_macchine
    ADD CONSTRAINT _h_macchine_pk PRIMARY KEY (id);


--
-- Name: _h_macchine _h_macchine_uk; Type: CONSTRAINT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine._h_macchine
    ADD CONSTRAINT _h_macchine_uk UNIQUE (id_macchina, validita);


--
-- Name: costruttori costruttori_unique; Type: CONSTRAINT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine.costruttori
    ADD CONSTRAINT costruttori_unique UNIQUE (descr);


--
-- Name: costruttori costruttoricostruttori_pk; Type: CONSTRAINT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine.costruttori
    ADD CONSTRAINT costruttoricostruttori_pk PRIMARY KEY (id);


--
-- Name: macchine macchine_pk; Type: CONSTRAINT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine.macchine
    ADD CONSTRAINT macchine_pk PRIMARY KEY (id);


--
-- Name: macchine macchine_unique; Type: CONSTRAINT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine.macchine
    ADD CONSTRAINT macchine_unique UNIQUE (modello);


--
-- Name: _h_cantiere_persona_ruoli _h_cantiere_persona_ruoli_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_cantiere_persona_ruoli
    ADD CONSTRAINT _h_cantiere_persona_ruoli_pk PRIMARY KEY (id);


--
-- Name: _h_cantiere_persona_ruoli _h_cantiere_persona_ruoli_uk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_cantiere_persona_ruoli
    ADD CONSTRAINT _h_cantiere_persona_ruoli_uk UNIQUE (id_cantiere_persona_ruolo, validita);


--
-- Name: _h_cantieri _h_cantieri_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_cantieri
    ADD CONSTRAINT _h_cantieri_pk PRIMARY KEY (id);


--
-- Name: _h_cantieri _h_cantieri_uk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_cantieri
    ADD CONSTRAINT _h_cantieri_uk UNIQUE (id_cantiere, validita);


--
-- Name: _h_documenti _h_documenti_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_documenti
    ADD CONSTRAINT _h_documenti_pk PRIMARY KEY (id);


--
-- Name: _h_documenti _h_documenti_uk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_documenti
    ADD CONSTRAINT _h_documenti_uk UNIQUE (id_documento, validita);


--
-- Name: _h_email_asl _h_email_asl_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_email_asl
    ADD CONSTRAINT _h_email_asl_pk PRIMARY KEY (id);


--
-- Name: _h_email_asl _h_email_asl_uk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_email_asl
    ADD CONSTRAINT _h_email_asl_uk UNIQUE (id_email_asl, validita);


--
-- Name: _h_imprese _h_imprese_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_imprese
    ADD CONSTRAINT _h_imprese_pk PRIMARY KEY (id);


--
-- Name: _h_imprese _h_imprese_uk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_imprese
    ADD CONSTRAINT _h_imprese_uk UNIQUE (id_impresa, validita);


--
-- Name: _h_notifiche _h_notifiche_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_notifiche
    ADD CONSTRAINT _h_notifiche_pk PRIMARY KEY (id);


--
-- Name: _h_notifiche _h_notifiche_uk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche._h_notifiche
    ADD CONSTRAINT _h_notifiche_uk UNIQUE (id_notifica, validita);


--
-- Name: cantiere_imprese cantiere_imprese_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantiere_imprese
    ADD CONSTRAINT cantiere_imprese_pk PRIMARY KEY (id);


--
-- Name: cantiere_imprese cantiere_imprese_unique; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantiere_imprese
    ADD CONSTRAINT cantiere_imprese_unique UNIQUE (id_cantiere, id_impresa);


--
-- Name: cantiere_persona_ruoli cantiere_persona_ruoli_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantiere_persona_ruoli
    ADD CONSTRAINT cantiere_persona_ruoli_pk PRIMARY KEY (id);


--
-- Name: cantieri cantieri_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantieri
    ADD CONSTRAINT cantieri_pk PRIMARY KEY (id);


--
-- Name: cantieri cantieri_unique; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantieri
    ADD CONSTRAINT cantieri_unique UNIQUE (id_notifica, denominazione);


--
-- Name: documenti documenti_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.documenti
    ADD CONSTRAINT documenti_pk PRIMARY KEY (id);


--
-- Name: imprese imprese_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.imprese
    ADD CONSTRAINT imprese_pk PRIMARY KEY (id);


--
-- Name: imprese imprese_un; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.imprese
    ADD CONSTRAINT imprese_un UNIQUE (partita_iva);


--
-- Name: notifiche notifiche_pk; Type: CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.notifiche
    ADD CONSTRAINT notifiche_pk PRIMARY KEY (id);


--
-- Name: _h_ente_uo _h_ente_uo_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_ente_uo
    ADD CONSTRAINT _h_ente_uo_pk PRIMARY KEY (id);


--
-- Name: _h_ente_uo _h_ente_uo_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_ente_uo
    ADD CONSTRAINT _h_ente_uo_uk UNIQUE (id_ente_uo, validita);


--
-- Name: _h_enti _h_enti_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_enti
    ADD CONSTRAINT _h_enti_pk PRIMARY KEY (id);


--
-- Name: _h_enti _h_enti_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_enti
    ADD CONSTRAINT _h_enti_uk UNIQUE (id_ente, validita);


--
-- Name: _h_esiti_per_fase _h_esiti_per_fase_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_esiti_per_fase
    ADD CONSTRAINT _h_esiti_per_fase_pk PRIMARY KEY (id);


--
-- Name: _h_esiti_per_fase _h_esiti_per_fase_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_esiti_per_fase
    ADD CONSTRAINT _h_esiti_per_fase_uk UNIQUE (id_esito_per_fase, validita);


--
-- Name: _h_fasi_ _h_fasi__pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_fasi_
    ADD CONSTRAINT _h_fasi__pk PRIMARY KEY (id);


--
-- Name: _h_fasi_ _h_fasi__uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_fasi_
    ADD CONSTRAINT _h_fasi__uk UNIQUE (id_fase_, validita);


--
-- Name: _h_fasi_esiti _h_fasi_esiti_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_fasi_esiti
    ADD CONSTRAINT _h_fasi_esiti_pk PRIMARY KEY (id);


--
-- Name: _h_fasi_esiti _h_fasi_esiti_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_fasi_esiti
    ADD CONSTRAINT _h_fasi_esiti_uk UNIQUE (id_fase_esito, validita);


--
-- Name: _h_fasi _h_fasi_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_fasi
    ADD CONSTRAINT _h_fasi_pk PRIMARY KEY (id);


--
-- Name: _h_fasi _h_fasi_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_fasi
    ADD CONSTRAINT _h_fasi_uk UNIQUE (id_fase, validita);


--
-- Name: _h_modulo_campi _h_moduli_campi_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_modulo_campi
    ADD CONSTRAINT _h_moduli_campi_pk PRIMARY KEY (id);


--
-- Name: _h_modulo_campi _h_moduli_campi_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_modulo_campi
    ADD CONSTRAINT _h_moduli_campi_uk UNIQUE (id_modulo_campo, validita);


--
-- Name: _h_moduli _h_moduli_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_moduli
    ADD CONSTRAINT _h_moduli_pk PRIMARY KEY (id);


--
-- Name: _h_moduli _h_moduli_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_moduli
    ADD CONSTRAINT _h_moduli_uk UNIQUE (id_modulo, validita);


--
-- Name: _h_motivi_isp _h_motivi_isp_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_motivi_isp
    ADD CONSTRAINT _h_motivi_isp_pk PRIMARY KEY (id);


--
-- Name: _h_motivi_isp _h_motivi_isp_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_motivi_isp
    ADD CONSTRAINT _h_motivi_isp_uk UNIQUE (id_motivo_isp, validita);


--
-- Name: _h_motivi _h_motivi_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_motivi
    ADD CONSTRAINT _h_motivi_pk PRIMARY KEY (id);


--
-- Name: _h_motivi _h_motivi_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_motivi
    ADD CONSTRAINT _h_motivi_uk UNIQUE (id_motivo, validita);


--
-- Name: _h_natura_giuridica _h_natura_giuridica_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_natura_giuridica
    ADD CONSTRAINT _h_natura_giuridica_pk PRIMARY KEY (id);


--
-- Name: _h_natura_giuridica _h_natura_giuridica_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_natura_giuridica
    ADD CONSTRAINT _h_natura_giuridica_uk UNIQUE (id_natura_giuridica, validita);


--
-- Name: _h_nature_opera _h_nature_opera_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_nature_opera
    ADD CONSTRAINT _h_nature_opera_pk PRIMARY KEY (id);


--
-- Name: _h_nature_opera _h_nature_opera_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_nature_opera
    ADD CONSTRAINT _h_nature_opera_uk UNIQUE (id_natura_opera, validita);


--
-- Name: _h_ruoli _h_ruoli_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_ruoli
    ADD CONSTRAINT _h_ruoli_pk PRIMARY KEY (id);


--
-- Name: _h_ruoli _h_ruoli_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_ruoli
    ADD CONSTRAINT _h_ruoli_uk UNIQUE (id_ruolo, validita);


--
-- Name: _h_stati_ispezione _h_stati_ispezione_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati_ispezione
    ADD CONSTRAINT _h_stati_ispezione_pk PRIMARY KEY (id);


--
-- Name: _h_stati_ispezione_successivi _h_stati_ispezione_successivi_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati_ispezione_successivi
    ADD CONSTRAINT _h_stati_ispezione_successivi_pk PRIMARY KEY (id);


--
-- Name: _h_stati_ispezione_successivi _h_stati_ispezione_successivi_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati_ispezione_successivi
    ADD CONSTRAINT _h_stati_ispezione_successivi_uk UNIQUE (id_stato_ispezione_successivo, validita);


--
-- Name: _h_stati_ispezione _h_stati_ispezione_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati_ispezione
    ADD CONSTRAINT _h_stati_ispezione_uk UNIQUE (id_stato_ispezione, validita);


--
-- Name: _h_stati _h_stati_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati
    ADD CONSTRAINT _h_stati_pk PRIMARY KEY (id);


--
-- Name: _h_stati_successivi _h_stati_successivi_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati_successivi
    ADD CONSTRAINT _h_stati_successivi_pk PRIMARY KEY (id);


--
-- Name: _h_stati_successivi _h_stati_successivi_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati_successivi
    ADD CONSTRAINT _h_stati_successivi_uk UNIQUE (id_stato_successivo, validita);


--
-- Name: _h_stati _h_stati_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_stati
    ADD CONSTRAINT _h_stati_uk UNIQUE (id_stato, validita);


--
-- Name: _h_tipi_campo _h_tipi_campo_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_tipi_campo
    ADD CONSTRAINT _h_tipi_campo_pk PRIMARY KEY (id);


--
-- Name: _h_tipi_campo _h_tipi_campo_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_tipi_campo
    ADD CONSTRAINT _h_tipi_campo_uk UNIQUE (id_tipo_campo, validita);


--
-- Name: _h_tipi_macchina _h_tipi_macchina_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_tipi_macchina
    ADD CONSTRAINT _h_tipi_macchina_pk PRIMARY KEY (id);


--
-- Name: _h_tipi_macchina _h_tipi_macchina_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_tipi_macchina
    ADD CONSTRAINT _h_tipi_macchina_unique UNIQUE (id_tipo_macchina, validita);


--
-- Name: _h_tipi_sede _h_tipi_sede_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_tipi_sede
    ADD CONSTRAINT _h_tipi_sede_pk PRIMARY KEY (id);


--
-- Name: _h_tipi_sede _h_tipi_sede_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_tipi_sede
    ADD CONSTRAINT _h_tipi_sede_uk UNIQUE (id_tipo_sede, validita);


--
-- Name: _h_uo _h_uo_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_uo
    ADD CONSTRAINT _h_uo_pk PRIMARY KEY (id);


--
-- Name: _h_uo _h_uo_uk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types._h_uo
    ADD CONSTRAINT _h_uo_uk UNIQUE (id_uo, validita);


--
-- Name: ente_uo ente_uo_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.ente_uo
    ADD CONSTRAINT ente_uo_pk PRIMARY KEY (id);


--
-- Name: ente_uo ente_uo_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.ente_uo
    ADD CONSTRAINT ente_uo_unique UNIQUE (id_ente, id_uo);


--
-- Name: enti enti_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.enti
    ADD CONSTRAINT enti_pk PRIMARY KEY (id);


--
-- Name: enti enti_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.enti
    ADD CONSTRAINT enti_unique UNIQUE (descr_ente);


--
-- Name: esiti_per_fase esiti_per_fase_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.esiti_per_fase
    ADD CONSTRAINT esiti_per_fase_pk PRIMARY KEY (id);


--
-- Name: esiti_per_fase esiti_per_fase_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.esiti_per_fase
    ADD CONSTRAINT esiti_per_fase_unique UNIQUE (descr_esito_per_fase, riferimento_fase_esito);


--
-- Name: fasi_esiti fase_esiti_possibili_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.fasi_esiti
    ADD CONSTRAINT fase_esiti_possibili_pk PRIMARY KEY (id);


--
-- Name: fasi_esiti fase_esiti_possibili_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.fasi_esiti
    ADD CONSTRAINT fase_esiti_possibili_unique UNIQUE (id_fase, id_esito_per_fase);


--
-- Name: fasi_ fasi__pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.fasi_
    ADD CONSTRAINT fasi__pk PRIMARY KEY (id);


--
-- Name: fasi fasi_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.fasi
    ADD CONSTRAINT fasi_pk PRIMARY KEY (id);


--
-- Name: fasi fasi_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.fasi
    ADD CONSTRAINT fasi_unique UNIQUE (descr);


--
-- Name: moduli moduli_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.moduli
    ADD CONSTRAINT moduli_pk PRIMARY KEY (id);


--
-- Name: moduli moduli_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.moduli
    ADD CONSTRAINT moduli_unique UNIQUE (descr);


--
-- Name: modulo_campi modulo_campi_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.modulo_campi
    ADD CONSTRAINT modulo_campi_pk PRIMARY KEY (id);


--
-- Name: motivi_isp motivi_isp_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.motivi_isp
    ADD CONSTRAINT motivi_isp_pk PRIMARY KEY (id);


--
-- Name: motivi motivi_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.motivi
    ADD CONSTRAINT motivi_pk PRIMARY KEY (id);


--
-- Name: motivi_isp motivi_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.motivi_isp
    ADD CONSTRAINT motivi_unique UNIQUE (descr);


--
-- Name: nature_opera nature_opera_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.nature_opera
    ADD CONSTRAINT nature_opera_pk PRIMARY KEY (id);


--
-- Name: nature_opera nature_opera_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.nature_opera
    ADD CONSTRAINT nature_opera_unique UNIQUE (descr);


--
-- Name: ruoli ruoli_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.ruoli
    ADD CONSTRAINT ruoli_pk PRIMARY KEY (id);


--
-- Name: ruoli ruoli_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.ruoli
    ADD CONSTRAINT ruoli_unique UNIQUE (descr);


--
-- Name: stati_ispezione stati_notifica_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.stati_ispezione
    ADD CONSTRAINT stati_notifica_pk PRIMARY KEY (id);


--
-- Name: stati_ispezione_successivi stati_notifica_successivi_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.stati_ispezione_successivi
    ADD CONSTRAINT stati_notifica_successivi_pk PRIMARY KEY (id);


--
-- Name: stati_ispezione stati_notifica_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.stati_ispezione
    ADD CONSTRAINT stati_notifica_unique UNIQUE (descr);


--
-- Name: stati stati_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.stati
    ADD CONSTRAINT stati_pk PRIMARY KEY (id);


--
-- Name: stati_successivi stati_successivi_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.stati_successivi
    ADD CONSTRAINT stati_successivi_pk PRIMARY KEY (id);


--
-- Name: stati stati_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.stati
    ADD CONSTRAINT stati_unique UNIQUE (descr);


--
-- Name: tipi_campo tipi_campo_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.tipi_campo
    ADD CONSTRAINT tipi_campo_pk PRIMARY KEY (id);


--
-- Name: tipi_macchina tipi_macchina_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.tipi_macchina
    ADD CONSTRAINT tipi_macchina_pk PRIMARY KEY (id);


--
-- Name: tipi_macchina tipi_macchina_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.tipi_macchina
    ADD CONSTRAINT tipi_macchina_unique UNIQUE (descr);


--
-- Name: uo uo_pk; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.uo
    ADD CONSTRAINT uo_pk PRIMARY KEY (id);


--
-- Name: uo uo_unique; Type: CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.uo
    ADD CONSTRAINT uo_unique UNIQUE (descr_uo);


--
-- Name: _h_messaggi_ui _h_messaggi_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_messaggi_ui
    ADD CONSTRAINT _h_messaggi_ui_pk PRIMARY KEY (id);


--
-- Name: _h_messaggi_ui _h_messaggi_ui_uk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_messaggi_ui
    ADD CONSTRAINT _h_messaggi_ui_uk UNIQUE (id_messaggio_ui, validita);


--
-- Name: _h_natura_opere_ui _h_natura_opere_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_natura_opere_ui
    ADD CONSTRAINT _h_natura_opere_pk PRIMARY KEY (id);


--
-- Name: _h_natura_opere_ui _h_natura_opere_uk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_natura_opere_ui
    ADD CONSTRAINT _h_natura_opere_uk UNIQUE (id_natura_opera_ui, validita);


--
-- Name: _h_ruoli_ui _h_ruoli_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_ruoli_ui
    ADD CONSTRAINT _h_ruoli_ui_pk PRIMARY KEY (id);


--
-- Name: _h_ruoli_ui _h_ruoli_ui_uk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_ruoli_ui
    ADD CONSTRAINT _h_ruoli_ui_uk UNIQUE (id_ruolo_ui, validita);


--
-- Name: _h_stati_ispezione_ui _h_stati_ispezione_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_ispezione_ui
    ADD CONSTRAINT _h_stati_ispezione_ui_pk PRIMARY KEY (id);


--
-- Name: _h_stati_ispezione_ui _h_stati_ispezione_ui_uk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_ispezione_ui
    ADD CONSTRAINT _h_stati_ispezione_ui_uk UNIQUE (id_stato_ispezione_ui, validita);


--
-- Name: _h_stati_notifica_ui _h_stati_notifica_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_notifica_ui
    ADD CONSTRAINT _h_stati_notifica_ui_pk PRIMARY KEY (id);


--
-- Name: _h_stati_notifica_ui _h_stati_notifica_ui_uk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_notifica_ui
    ADD CONSTRAINT _h_stati_notifica_ui_uk UNIQUE (id_stato_notifica_ui, validita);


--
-- Name: _h_stati_successivi_ui _h_stati_successivi_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_successivi_ui
    ADD CONSTRAINT _h_stati_successivi_ui_pk PRIMARY KEY (id);


--
-- Name: _h_stati_successivi_ui _h_stati_successivi_ui_uk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_successivi_ui
    ADD CONSTRAINT _h_stati_successivi_ui_uk UNIQUE (id_stato_successivo_ui, validita);


--
-- Name: _h_stati_ui _h_stati_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_ui
    ADD CONSTRAINT _h_stati_ui_pk PRIMARY KEY (id);


--
-- Name: _h_stati_ui _h_stati_ui_uk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_stati_ui
    ADD CONSTRAINT _h_stati_ui_uk UNIQUE (id_stato_ui, validita);


--
-- Name: _h_verbali_ui _h_verbali_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_verbali_ui
    ADD CONSTRAINT _h_verbali_ui_pk PRIMARY KEY (id);


--
-- Name: _h_verbali_ui _h_verbali_ui_uk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui._h_verbali_ui
    ADD CONSTRAINT _h_verbali_ui_uk UNIQUE (id_verbale_ui, validita);


--
-- Name: messaggi_ui messaggi_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui.messaggi_ui
    ADD CONSTRAINT messaggi_ui_pk PRIMARY KEY (id);


--
-- Name: natura_opere natura_opere_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui.natura_opere
    ADD CONSTRAINT natura_opere_pk PRIMARY KEY (id);


--
-- Name: ruoli_ui ruoli_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui.ruoli_ui
    ADD CONSTRAINT ruoli_ui_pk PRIMARY KEY (id);


--
-- Name: stati_ispezione_ui stati_ispezione_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui.stati_ispezione_ui
    ADD CONSTRAINT stati_ispezione_ui_pk PRIMARY KEY (id);


--
-- Name: stati_notifica_ui stati_notifica_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui.stati_notifica_ui
    ADD CONSTRAINT stati_notifica_ui_pk PRIMARY KEY (id);


--
-- Name: stati_successivi_ui stati_successivi_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui.stati_successivi_ui
    ADD CONSTRAINT stati_successivi_ui_pk PRIMARY KEY (id);


--
-- Name: stati_ui stati_ui_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui.stati_ui
    ADD CONSTRAINT stati_ui_pk PRIMARY KEY (id);


--
-- Name: verbali_ui verbali_pk; Type: CONSTRAINT; Schema: gds_ui; Owner: postgres
--

ALTER TABLE ONLY gds_ui.verbali_ui
    ADD CONSTRAINT verbali_pk PRIMARY KEY (id);


--
-- Name: fase_verbali fase_verbali_fk; Type: FK CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.fase_verbali
    ADD CONSTRAINT fase_verbali_fk FOREIGN KEY (id_verbale) REFERENCES gds.verbali(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: ispezione_fasi ispezione_fasi_ispezioni_fk; Type: FK CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.ispezione_fasi
    ADD CONSTRAINT ispezione_fasi_ispezioni_fk FOREIGN KEY (id_ispezione) REFERENCES gds.ispezioni(id);


--
-- Name: ispezione_imprese ispezione_imprese_impresa_sede_fk; Type: FK CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.ispezione_imprese
    ADD CONSTRAINT ispezione_imprese_impresa_sede_fk FOREIGN KEY (id_impresa_sede) REFERENCES gds.impresa_sedi(id);


--
-- Name: ispezione_imprese ispezione_imprese_ispezioni_fk; Type: FK CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.ispezione_imprese
    ADD CONSTRAINT ispezione_imprese_ispezioni_fk FOREIGN KEY (id_ispezione) REFERENCES gds.ispezioni(id);


--
-- Name: nucleo_ispettori nucleo_ispettori_ispezioni_fk; Type: FK CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.nucleo_ispettori
    ADD CONSTRAINT nucleo_ispettori_ispezioni_fk FOREIGN KEY (id_ispezione) REFERENCES gds.ispezioni(id);


--
-- Name: verbale_valori verbale_valori_fk; Type: FK CONSTRAINT; Schema: gds; Owner: postgres
--

ALTER TABLE ONLY gds.verbale_valori
    ADD CONSTRAINT verbale_valori_fk FOREIGN KEY (id_verbale) REFERENCES gds.verbali(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: macchine macchine_costruttore_fk; Type: FK CONSTRAINT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine.macchine
    ADD CONSTRAINT macchine_costruttore_fk FOREIGN KEY (id_costruttore) REFERENCES gds_macchine.costruttori(id);


--
-- Name: macchine macchine_tipo_macchina_fk; Type: FK CONSTRAINT; Schema: gds_macchine; Owner: postgres
--

ALTER TABLE ONLY gds_macchine.macchine
    ADD CONSTRAINT macchine_tipo_macchina_fk FOREIGN KEY (id_tipo_macchina) REFERENCES gds_types.tipi_macchina(id);


--
-- Name: cantiere_imprese cantiere_imprese_cantieri_fk; Type: FK CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantiere_imprese
    ADD CONSTRAINT cantiere_imprese_cantieri_fk FOREIGN KEY (id_cantiere) REFERENCES gds_notifiche.cantieri(id);


--
-- Name: cantiere_persona_ruoli cantiere_persona_ruoli_cantieri_fk; Type: FK CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantiere_persona_ruoli
    ADD CONSTRAINT cantiere_persona_ruoli_cantieri_fk FOREIGN KEY (id_cantiere) REFERENCES gds_notifiche.cantieri(id);


--
-- Name: cantiere_persona_ruoli cantiere_persona_ruoli_opu_soggetto_fisico_fk; Type: FK CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantiere_persona_ruoli
    ADD CONSTRAINT cantiere_persona_ruoli_opu_soggetto_fisico_fk FOREIGN KEY (id_soggetto_fisico) REFERENCES public.opu_soggetto_fisico(id);


--
-- Name: cantiere_persona_ruoli cantiere_persona_ruoli_ruoli_fk; Type: FK CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantiere_persona_ruoli
    ADD CONSTRAINT cantiere_persona_ruoli_ruoli_fk FOREIGN KEY (id_ruolo) REFERENCES gds_types.ruoli(id);


--
-- Name: cantieri cantieri_nature_opera_fk; Type: FK CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.cantieri
    ADD CONSTRAINT cantieri_nature_opera_fk FOREIGN KEY (id_natura_opera) REFERENCES gds_types.nature_opera(id);


--
-- Name: notifiche notifiche_opu_soggetto_fisico_fk; Type: FK CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.notifiche
    ADD CONSTRAINT notifiche_opu_soggetto_fisico_fk FOREIGN KEY (id_soggetto_fisico) REFERENCES public.opu_soggetto_fisico(id);


--
-- Name: notifiche notifiche_stati_fk; Type: FK CONSTRAINT; Schema: gds_notifiche; Owner: postgres
--

ALTER TABLE ONLY gds_notifiche.notifiche
    ADD CONSTRAINT notifiche_stati_fk FOREIGN KEY (id_stato) REFERENCES gds_types.stati(id);


--
-- Name: fasi_esiti fase_esiti_possibili_fase_esit_fk; Type: FK CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.fasi_esiti
    ADD CONSTRAINT fase_esiti_possibili_fase_esit_fk FOREIGN KEY (id_esito_per_fase) REFERENCES gds_types.esiti_per_fase(id);


--
-- Name: fasi_esiti fase_esiti_possibili_fase_fk; Type: FK CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.fasi_esiti
    ADD CONSTRAINT fase_esiti_possibili_fase_fk FOREIGN KEY (id_fase) REFERENCES gds_types.esiti_per_fase(id);


--
-- Name: modulo_campi moduli_moduli_fk; Type: FK CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.modulo_campi
    ADD CONSTRAINT moduli_moduli_fk FOREIGN KEY (id_modulo) REFERENCES gds_types.moduli(id);


--
-- Name: modulo_campi tipi_campo_campo_fk; Type: FK CONSTRAINT; Schema: gds_types; Owner: postgres
--

ALTER TABLE ONLY gds_types.modulo_campi
    ADD CONSTRAINT tipi_campo_campo_fk FOREIGN KEY (tipo_campo) REFERENCES gds_types.tipi_campo(id);


--
-- PostgreSQL database dump complete
--

