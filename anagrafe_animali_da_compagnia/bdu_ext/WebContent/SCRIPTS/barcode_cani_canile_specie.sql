-- Function: public.get_barcode_cani_canile_leish(integer)

-- DROP FUNCTION public.get_barcode_cani_canile_leish(integer);

CREATE OR REPLACE FUNCTION public.get_barcode_cani_canile_leish(IN idcanile integer)
  RETURNS TABLE(id integer, specie text, proprietario text, detentore text, microchip text, tatuaggio text, sesso text, mantello text, data_prelievo_leish timestamp without time zone, data_esito_leish timestamp without time zone, esito_leish text) AS
$BODY$

 BEGIN

 FOR

id, specie, proprietario, detentore, microchip, tatuaggio, sesso, mantello, data_prelievo_leish, data_esito_leish, 

esito_leish
in
SELECT  a1.id, a1.specie, a1.proprietario, a1.detentore, a1.microchip, a1.tatuaggio, a1.sesso, a1.mantello, a2.data_prelievo_leish, a2.data_esito, a2. esito

 from

(SELECT 
 a.id,
 s.description as specie,
p.ragione_sociale as proprietario,
d.ragione_sociale as detentore,
a.microchip, 
a.tatuaggio, 
a.sesso,
m.description as mantello

from animale a
left join opu_operatori_denormalizzati p on p.id_rel_stab_lp = a.id_proprietario
left join opu_operatori_denormalizzati d on d.id_rel_stab_lp = a.id_detentore
left join lookup_mantello m on m.code = a.id_tipo_mantello 
left join lookup_specie s on s.code = a.id_specie

where a.id_detentore =  idcanile
and (a.flag_decesso is false or a.flag_decesso is null)
and (a.flag_smarrimento is null or a.flag_smarrimento is false)
and (a.flag_furto is null       or a.flag_furto is false)
AND a.data_cancellazione is NULL and a.trashed_date is NULL 
and p.id_linea_produttiva in (3,5) and date_part('year',  age(a.data_nascita)) >=1  and date_part('year',  age(a.data_nascita)) <=8
order by a.id) a1

join

(SELECT
distinct on (a.id) a.id,
l.data_prelievo_leish,
esiti.data_esito,
esiti.esito || ' ' || esito_car as esito

from animale a
LEFT JOIN evento e on e.id_animale = a.id and e.id_tipologia_evento = 54 and e.trashed_date is null and e.data_cancellazione is null
LEFT JOIN evento_prelievo_leish l on l.id_evento = e.id_evento
LEFT JOIN esiti_leishmaniosi esiti on esiti.id_animale = a.id and date_part('year',esiti.data_prelievo) = (select date_part('year', now())) 
where a.id_detentore =  idcanile AND a.data_cancellazione is NULL and a.trashed_date is NULL 
order by a.id) a2
on a1.id = a2.id


 
LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_barcode_cani_canile_leish(integer)
  OWNER TO postgres;
COMMENT ON FUNCTION public.get_barcode_cani_canile_leish(integer) IS 'Siccome la condizione sul tipo linea E i join su evento rendevano la funzione lentissima, la funzione si basa su una JOIN tra due sottoquery, una estrae i dati animale, l''altra estrae i dati evento, e vanno in join su id_animale';


-- Function: public.get_barcode_cani_canile(integer)

-- DROP FUNCTION public.get_barcode_cani_canile(integer);

CREATE OR REPLACE FUNCTION public.get_barcode_cani_canile(IN idcanile integer)
  RETURNS TABLE(id integer, specie text, proprietario text, detentore text, microchip text, tatuaggio text, sesso text, mantello text, data_prelievo_leish timestamp without time zone, data_esito_leish timestamp without time zone, esito_leish text) AS
$BODY$

 BEGIN

 FOR

id, specie, proprietario, detentore, microchip, tatuaggio, sesso, mantello, data_prelievo_leish, data_esito_leish, 

esito_leish
in
 SELECT 
 a.id,
 s.description,
p.ragione_sociale,
d.ragione_sociale,
a.microchip, 
a.tatuaggio, 
a.sesso,
m.description,
l.data_prelievo_leish,
esiti.data_esito,
esiti.esito || ' ' || esito_car as esito

from animale a
left join opu_operatori_denormalizzati p on p.id_rel_stab_lp = a.id_proprietario
left join opu_operatori_denormalizzati d on d.id_rel_stab_lp = a.id_detentore
left join lookup_mantello m on m.code = a.id_tipo_mantello 
left join lookup_specie s on s.code = a.id_specie 

LEFT JOIN evento e on e.id_animale = a.id and e.id_tipologia_evento = 54 and e.trashed_date is null and 

e.data_cancellazione is null
LEFT JOIN evento_prelievo_leish l on l.id_evento = e.id_evento

LEFT JOIN esiti_leishmaniosi esiti on esiti.id_animale = a.id and date_part('year',esiti.data_prelievo) = (select date_part('year', now())) 

where a.id_detentore =  idcanile
and (a.flag_decesso is false or a.flag_decesso is null)
and (a.flag_smarrimento is null or a.flag_smarrimento is false)
and (a.flag_furto is null       or a.flag_furto is false)
AND a.data_cancellazione is NULL and a.trashed_date is NULL 

order by a.microchip desc
 
LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_barcode_cani_canile(integer)
  OWNER TO postgres;


