CREATE OR REPLACE VIEW m_registro_macellazioni AS
SELECT 
  c.id_macello,
  c.cd_mod4 AS mod4,
  date_trunc( 'day', c.entered ) AS data_arrivo,
  specie.description AS specie,
  c.cd_codice_azienda AS codice_azienda,
  c.cd_speditore AS speditore,
  c.cd_matricola AS matricola,
  CASE WHEN c.cd_maschio THEN 'M' ELSE 'F' END AS sesso,
  c.cd_data_nascita AS data_nascita,
  c.vpm_data AS data_macellazione,
  esito_vpm.description AS esito_vpm,
  c.destinatario_1_nome || ' ' || c.destinatario_2_nome AS destinatario_carni,
  CASE 
    WHEN (c.cd_provenienza_stato = 113)
    THEN regioni.description || ' ' || c.cd_provenienza_comune 
    ELSE stati.description END AS provenienza
FROM 
  m_capi c 
  LEFT JOIN m_lookup_specie specie ON ( c.cd_specie = specie.code )
  LEFT JOIN m_lookup_esiti_vpm esito_vpm ON ( c.vpm_esito = esito_vpm.code )
  LEFT JOIN m_lookup_stati stati ON ( c.cd_provenienza_stato = stati.code )
  LEFT JOIN m_lookup_regioni regioni ON ( c.cd_provenienza_regione = regioni.code )
WHERE
  c.trashed_date IS NULL
;

CREATE OR REPLACE VIEW m_aziende_macellazioni AS
SELECT
  c.id_macello,
  c.cd_codice_azienda AS codice_azienda,
  count(*) AS numero_capi,
  c.vpm_data AS data_macellazione
FROM
  m_capi c
WHERE
  c.trashed_date IS NULL
GROUP BY
  c.id_macello, c.cd_codice_azienda, c.vpm_data
;

CREATE OR REPLACE VIEW m_esercenti_macellazioni AS
SELECT *, count(*) AS numero_capi FROM
(
SELECT
  c.id_macello,
  c.destinatario_1_id AS id_esercente,
  c.destinatario_1_nome AS esercente,
  --count(*) AS numero_capi,
  c.vpm_data AS data_macellazione
FROM
  m_capi c
WHERE
  c.destinatario_1_id > 0
  AND c.trashed_date IS NULL
  AND c.vpm_data IS NOT NULL

UNION

SELECT
  c.id_macello,
  c.destinatario_2_id AS id_esercente,
  c.destinatario_2_nome AS esercente,
  --count(*) AS numero_capi,
  c.vpm_data AS data_macellazione
FROM
  m_capi c
WHERE
  c.destinatario_2_id > 0
  AND c.trashed_date IS NULL
  AND c.vpm_data IS NOT NULL
)c

GROUP BY
  id_macello, id_esercente, esercente, data_macellazione
;

CREATE OR REPLACE VIEW m_speditori_morti_stalla AS
SELECT
  c.id_macello,
  c.cd_speditore AS speditore,
  count(*) AS numero_capi,
  c.mavam_data AS data
FROM
  m_capi c
WHERE
  c.trashed_date IS NULL
  AND c.vpm_esito = 2 --esclusione dal consumo con distruzione carcassa
GROUP BY
  c.id_macello, c.cd_speditore, c.mavam_data
;

CREATE OR REPLACE VIEW m_modulo_bse AS
SELECT
  c.id,
  c.id_macello,
  c.bse_data_prelievo AS data_prelievo,
  c.cd_matricola AS matricola,
  c.cd_codice_azienda AS codice_azienda,
  cd_provenienza_comune AS comune,
  ln.description AS nazione_provenienza,
  lb.description AS motivo_prelievo,
  c.entered - interval '4 years' >= c.cd_data_nascita AS quattro_anni,
  ls.description AS specie,
  cd_data_nascita AS data_nascita,
  CASE WHEN cd_maschio THEN 'M' ELSE 'F' END AS sesso
FROM 
  m_capi c 
  LEFT JOIN m_lookup_nazioni ln ON ln.code = c.cd_provenienza_stato
  LEFT JOIN m_lookup_bse lb ON lb.code = c.cd_bse
  LEFT JOIN m_lookup_specie ls ON ls.code = c.cd_specie
WHERE
  c.bse_data_prelievo IS NOT NULL AND
  c.trashed_date IS NULL
ORDER BY
  codice_azienda, nazione_provenienza, motivo_prelievo, quattro_anni, matricola
;

CREATE OR REPLACE VIEW m_speditori_abbattimento AS
SELECT
  c.id_macello,
  upper( trim(c.cd_speditore) ) AS speditore,
  count(*) AS numero_capi,
  c.abb_data AS data_abbattimento
FROM
  m_capi c
WHERE
  c.trashed_date IS NULL
  AND c.abb_data IS NOT NULL
GROUP BY
  c.id_macello, speditore, c.abb_data
;

CREATE OR REPLACE VIEW m_modulo_abbattimento AS
SELECT
  c.id,
  c.id_macello,
  c.abb_data AS data_abbattimento,
  c.abb_motivo AS motivo,
  c.cd_matricola AS matricola,
  c.cd_codice_azienda AS codice_azienda,
  c.cd_speditore AS speditore,
  ls.description AS specie,
  lp.description AS destinazione,
  cd_data_nascita AS data_nascita,
  CASE WHEN cd_maschio THEN 'M' ELSE 'F' END AS sesso
FROM 
  m_capi c 
  LEFT JOIN m_lookup_specie ls ON ls.code = c.cd_specie
  LEFT JOIN m_lookup_provvedimenti_vam lp ON lp.code = c.vam_provvedimenti
WHERE
  c.abb_data IS NOT NULL AND
  c.trashed_date IS NULL
ORDER BY
  codice_azienda, matricola
;

CREATE OR REPLACE VIEW m_modulo_art17 AS
SELECT
  c.id,
  c.id_macello,
  c.cd_matricola AS matricola,
  (date_part('Year', age( c.vpm_data, c.cd_data_nascita )) * 12) + date_part('Month', age( c.vpm_data, c.cd_data_nascita )) AS mesi,
  c.cd_codice_azienda AS codice_azienda,
  c.cd_speditore AS speditore,
  c.destinatario_1_id AS id_esercente,
  c.destinatario_1_nome AS esercente,
  ls.description AS specie,
  le.description AS esito,
  c.vpm_data AS data_macellazione,
  c.cd_data_nascita AS data_nascita,
  c.cd_mod4 AS mod4,
  CASE WHEN cd_maschio THEN 'M' ELSE 'F' END AS sesso,
  CASE WHEN c.destinatario_2_id > 0 THEN '1/2' ELSE '1' END AS num_capi
FROM 
  m_capi c 
  LEFT JOIN m_lookup_specie ls ON ls.code = c.cd_specie
  LEFT JOIN m_lookup_esiti_vpm le ON le.code = c.vpm_esito
WHERE
  c.destinatario_1_id > 0
  AND c.vpm_data IS NOT NULL AND
  c.trashed_date IS NULL

UNION

SELECT
  c.id,
  c.id_macello,
  c.cd_matricola AS matricola,
  (date_part('Year', age( c.vpm_data, c.cd_data_nascita )) * 12) + date_part('Month', age( c.vpm_data, c.cd_data_nascita )) AS mesi,
  c.cd_codice_azienda AS codice_azienda,
  c.cd_speditore AS speditore,
  c.destinatario_2_id AS id_esercente,
  c.destinatario_2_nome AS esercente,
  ls.description AS specie,
  le.description AS esito,
  c.vpm_data AS data_macellazione,
  c.cd_data_nascita AS data_nascita,
  c.cd_mod4 AS mod4,
  CASE WHEN cd_maschio THEN 'M' ELSE 'F' END AS sesso,
  CASE WHEN c.destinatario_1_id > 0 THEN '1/2' ELSE '1' END AS num_capi
FROM 
  m_capi c 
  LEFT JOIN m_lookup_specie ls ON ls.code = c.cd_specie
  LEFT JOIN m_lookup_esiti_vpm le ON le.code = c.vpm_esito
WHERE
  c.destinatario_2_id > 0
  AND c.vpm_data IS NOT NULL AND
  c.trashed_date IS NULL

ORDER BY
  codice_azienda, matricola
;

CREATE OR REPLACE VIEW m_modulo_morti_stalla AS
SELECT
  c.id,
  c.id_macello,
  c.cd_matricola AS matricola,
  (date_part('Year', age( c.vpm_data, c.cd_data_nascita )) * 12) + date_part('Month', age( c.vpm_data, c.cd_data_nascita )) AS mesi,
  c.cd_codice_azienda AS codice_azienda,
  c.cd_speditore AS speditore,
  c.mavam_impianto_termodistruzione AS impianto,
  ls.description AS specie,
  le.description AS esito,
  c.mavam_data AS data,
  c.cd_data_nascita AS data_nascita,
  c.cd_mod4 AS mod4,
  CASE WHEN cd_maschio THEN 'M' ELSE 'F' END AS sesso
FROM 
  m_capi c 
  LEFT JOIN m_lookup_specie ls ON ls.code = c.cd_specie
  LEFT JOIN m_lookup_esiti_vpm le ON le.code = c.vpm_esito
WHERE
  c.mavam_data IS NOT NULL
  AND c.vpm_esito = 2
  AND c.trashed_date IS NULL
;
