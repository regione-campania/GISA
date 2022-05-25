create table master_list_configuratore_livelli_aggiuntivi as
select distinct l.id, l.id_linea_attivita, linea.codice_univoco, l.id_padre, l.nome, v.valore
from master_list_livelli_aggiuntivi l
join master_list_linea_attivita linea on linea.id = l.id_linea_attivita
left join master_list_livelli_aggiuntivi_values v on l.id = v.id_livello_aggiuntivo
order by id_linea_attivita;

create table master_list_configuratore_livelli_aggiuntivi_values (
 id serial,
 id_configuratore_livelli_aggiuntivi integer,
 id_istanza integer,
 checked boolean default false
)

-- View: public.opu_campi_estesi_lda_view

 DROP VIEW public.opu_campi_estesi_lda_view;

CREATE OR REPLACE VIEW public.opu_campi_estesi_lda_view AS 
 SELECT oce.id,
    oce.nome_campo,
    oce.label_campo,
    oce.valore_campo,
    oce.tipo_campo,
    oce.modificabile,
    oce.visibile,
    oce.id_rel_stab_lp,
    lin.path_descrizione
   FROM opu_campi_estesi_lda oce
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = oce.id_rel_stab_lp
     LEFT JOIN ml8_linee_attivita_nuove lin ON rel.id_linea_produttiva = lin.id_nuova_linea_attivita;

ALTER TABLE public.opu_campi_estesi_lda_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.opu_campi_estesi_lda_view TO postgres;
GRANT SELECT ON TABLE public.opu_campi_estesi_lda_view TO report;
