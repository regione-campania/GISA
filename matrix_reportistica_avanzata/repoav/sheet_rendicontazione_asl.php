<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
ini_set('max_execution_time', '0'); // for infinite time of execution 
ini_set('memory_limit', '512M');
header('Cache-Control: max-age=0');

require_once("../dal_include.php");
require_once("../dal_connessione.php");

$ANNO = $_REQUEST['anno'];

$query = "select riga as riga ,id_r, unaccent(colonna)||case when ha_campioni then ' [Campioni]' else '' end as colonna, id_c, ordinamento,  sum(coalesce(valore,0)) as valore, sum(coalesce(target,0)) as target, sum(coalesce(target_ups,0)) as target_ups, sum(coalesce(target_uba,0)) as target_uba, sum(eseguiti_ups) as eseguiti_ups, sum(eseguiti_uba) as eseguiti_uba  , ha_campioni 
		, colonna_padre, count(*) over (partition by colonna_padre)	as count_padre  
		from ( 	 	
			select replace(a.descrizione_breve, '.', '') as riga, a.id as id_r, replace(upper(p.alias||' '||p.descrizione), '.', '') as colonna, p.id as id_c, p.path_ord as ordinamento,  coalesce(t.sum, 0) as target, coalesce(target_ups,0) as target_ups , coalesce(t.target_uba,0) as target_uba, sum(f_ups*eseguiti) as eseguiti_ups, sum(f_uba*eseguiti) as eseguiti_uba,  sum(eseguiti) as valore  , ha_campioni  
			,replace(upper(p.p_alias||' '||p.p_descrizione), '.', '') as colonna_padre 
			from   matrix.vw_tree_nodes_piani_descr p 	
			 join  matrix.vw_tree_nodes_asl_descr a  on  p.id not in (select id from matrix.struttura_piani where id_gisa in (select r.id_piano  from \\\"Analisi_dev\\\".piani_no_rend r)) 
			 and p.p_id in (select id from matrix.vw_tree_nodes_piani_descr where p_id = ID_PIANO) and  a.p_id = ID_ASL
			left join matrix.vw_struttura_piano_target_tree_no_rend t 
			on  t.id_node_ref = p.id and a.id = t.id_struttura  
				left join ( 
					select cu2.id_motivo,	
					cu2.a_id_ref as id_unita_operativa,	
					date_part('year'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_y,	
					date_part('month'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_m,	
					'Trim '::text || btrim(to_char(date_part('quarter'::text,  data_inizio_controllo::timestamp without time zone)::integer, 'RN'::text)) AS data_inizio_controllo_q,	
					date_part('day'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_d,	
					to_char(to_timestamp(date_part('month'::text, data_inizio_controllo::timestamp without time zone)::text, 'MM'::text), 'TMMonth'::text) AS data_inizio_controllo_m_text,	
					sum(eseguiti) as eseguiti, 
					f_ups,  
					f_uba, 
					p_id_ref, 
					a_id_ref, 
					m_id_ref, 
					data_inizio_controllo 
				from (	
				SELECT distinct g.id_controllo, g.id_motivo,	
				case when g.fonte not in ('camp',  'isp semp', 'a1', 'd1', 'audit') then a.id_asl else g.id_unita_operativa end as id_unita_operativa,	
				date_part('year'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_y,  	
				date_part('month'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_m, 	
				'Trim '::text || btrim(to_char(date_part('quarter'::text,  data_inizio_controllo::timestamp without time zone)::integer, 'RN'::text)) AS data_inizio_controllo_q,	
				date_part('day'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_d,		
				to_char(to_timestamp(date_part('month'::text, data_inizio_controllo::timestamp without time zone)::text, 'MM'::text), 'TMMonth'::text) AS data_inizio_controllo_m_text,	
				case when g.fonte = 'sorveglianze' then 1 else eseguiti end as eseguiti, 	
				coalesce(fattore_ups,0) as f_ups, 	
				coalesce(fattore_uba,0) as f_uba,	
					p.id_ref as p_id_ref,	
					a.id_ref as a_id_ref,	
					mr.id_ref as m_id_ref,	
					data_inizio_controllo	
					 FROM ra.vw_gisa_controlli_ufficiali g	
					 left  join \\\"Analisi_dev\\\".mvw_macroarea_ref mr on mr.codice = split_part(g.codice_linea,'-', 3) and mr.id_ref   = -1	
					left join \\\"Analisi_dev\\\".mvw_macroarea_ref mr2 on mr2.id_gisa =  mr.parent and mr2.codice = split_part(g.codice_linea,'-', 2) 
					left join \\\"Analisi_dev\\\".mvw_macroarea_ref mr3 on mr3.id_gisa =  mr2.parent and mr3.codice = split_part(g.codice_linea,'-', 1) 
					join \\\"Analisi_dev\\\".vw_struttura_piani_ref_no_rend_new p on p.id_gisa = g.id_motivo and p.id_ref  in (select id from matrix.vw_tree_nodes_piani_descr pd where pd.p_id in (select id from matrix.vw_tree_nodes_piani_descr where p_id = ID_PIANO))			 
					 left  join matrix.struttura_asl_ref_new a on  g.id_unita_operativa = a.id_gisa and a.id_ref  in (select id from matrix.vw_tree_nodes_asl_descr where p_id = ID_ASL)
					where data_inizio_controllo >= '$ANNO-01-01' 				) cu2 GROUP BY 1,2,3,4,5,6,7,9,10,11,12,13,14	
				) cu on  p.id = cu.p_id_ref  and  a.id = cu.a_id_ref 	
				where  1=1 
				group by 1,2,3,4,5,6,7, 8, ha_campioni, colonna_padre 
		) a where colonna is not null and riga is not null group by 1,2,a.colonna,4 , 5, ha_campioni, colonna_padre order by  1,coalesce(ordinamento,a.colonna)";

$json->anno = $ANNO;

$res = pg_query("select * from matrix.struttura_asl sa where anno = $ANNO and n_livello = 0;");
while($row = pg_fetch_assoc($res)){
	$query = str_replace("ID_ASL", $row["id"], $query);
	$json->id_asl = $row['id'];
	$json->descr_asl = $row['descrizione'];

}

$piani = array();
$res = pg_query("select * from matrix.vw_tree_nodes_up_piani where livello = 2 and anno = $ANNO order by path_ord");
while($row = pg_fetch_assoc($res)){
	$obj = new stdClass();
	$obj->id = $row["id"];
	$obj->descr = $row["path_descr"];
	$obj->query = str_replace("ID_PIANO", $row["id"], $query);
	array_push($piani, $obj);
}

$json->piani = $piani;

echo json_encode($json);


