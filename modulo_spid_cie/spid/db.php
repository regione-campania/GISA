<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("common/connection.php");
require("common/config.php");
mb_internal_encoding("UTF-8");

$operation = $_REQUEST['operation'];

if($operation == 'get_ruoli'){

    echo $_CONFIG['ruoli'];

}else if($operation == 'save'){
    $tipologia_utente = $_POST['tipologia_utente'];
    $tipo_richiesta = $_POST['tipo_richiesta'];
    $cognome = str_replace("'", "''", $_POST['cognome']);
    $nome = str_replace("'", "''", $_POST['nome']);
    $codice_fiscale = $_POST['codice_fiscale'];
    $email = $_POST['email'];
    $telefono = $_POST['telefono'];
    $id_ruolo_gisa = $_POST['id_ruolo_gisa'];
    $id_ruolo_bdu = $_POST['id_ruolo_bdu'];
    $id_ruolo_vam = $_POST['id_ruolo_vam'];
    $id_clinica_vam = $_POST['id_clinica_vam'];
    $id_ruolo_ext = $_POST['id_ruolo_ext'];
    $identificativo_ente = $_POST['identificativo_ente'];
    $piva_numregistrazione = $_POST['piva_numregistrazione'];
    $comune = $_POST['comune'];
    $nominativo_referente = $_POST['nominativo_referente'];
    $email_referente = $_POST['email_referente'];
    $telefono_referente = $_POST['telefono_referente'];
    $ruolo_referente = $_POST['ruolo_referente'];
    $codice_gisa = $_POST['codice_gisa'];
    $indirizzo = $_POST['indirizzo'];
    $cap = $_POST['cap'];
    $id_gestore_acqua = $_POST['id_gestore_acqua'];
    $pec = $_POST['pec'];
    $giava = $_POST['giava'];
    $digemon = $_POST['digemon'];
    $numero_decreto_prefettizio = $_POST['numero_decreto_prefettizio'];
    $scadenza_decreto_prefettizio = $_POST['scadenza_decreto_prefettizio'];
    $provincia_ordine_vet = $_POST['provincia_ordine_vet'];
    $numero_ordine_vet = $_POST['numero_ordine_vet'];
    $numero_autorizzazione_regionale_vet = $_POST['numero_autorizzazione_regionale_vet'];
    $id_asl = $_POST['asl'];
    $id_tipologia_trasp_dist = $_POST['id_tipologia_trasp_dist'];
    $id_guc_ruoli = $_POST['id_guc_ruoli'];
    $id_ruolo_gesdasic = $_POST['id_ruolo_gesdasic'] == null ? "null" : $_POST['id_ruolo_gesdasic'];

    $userAgent = $_SERVER['HTTP_USER_AGENT'];
    $ip = $_SERVER['REMOTE_ADDR'];

    $conn = getConnection("guc");

    //check se richiesta inserimento già presente
    $query = "
    select count(*), STRING_AGG(r.numero_richiesta ||' DEL '||to_CHAR(data_richiesta, 'DD-MM-YYY'), ', ') as num
        from spid.spid_registrazioni r
        left join spid.spid_registrazioni_esiti e on r.numero_richiesta = e.numero_richiesta
        where
        codice_fiscale = '$codice_fiscale' 
        and id_tipo_richiesta = $tipo_richiesta
        and id_tipologia_utente = $tipologia_utente
        and (id_ruolo_gisa = $id_ruolo_gisa or (id_ruolo_gisa is null and '$id_ruolo_gisa' = 'null'))
        and (id_ruolo_digemon = $digemon or (id_ruolo_digemon is null and '$digemon' = 'null'))
        and id_ruolo_vam = $id_ruolo_vam
        and id_ruolo_bdu = $id_ruolo_bdu
        and (id_ruolo_gisa_ext = $id_ruolo_ext or (id_ruolo_gisa_ext is null and '$id_ruolo_ext' = 'null'))
        and piva_numregistrazione = '$piva_numregistrazione'
        and (id_asl = $id_asl or (id_asl is null and '$id_asl' = 'null'))
        and data_richiesta + interval '7 days' > current_date and 1 = $tipo_richiesta
        and e.stato != 2";
        
    $result = pg_query($conn, $query);
    while ($row = pg_fetch_assoc($result)) {
        if($row['count'] > 0)
            die("KO: ESISTE GIA' LA RICHIESTA ".$row['num']. " CONTENENTE I DATI INSERITI. PER CHIARIMENTI CONTATTARE L'HELP DESK.");
    }

    //check se richiesta modifica/cancellazione già inoltrata
    $query = "
        select count(*) 
        from spid.spid_registrazioni r
        left join spid.spid_registrazioni_esiti e on r.numero_richiesta = e.numero_richiesta
        where codice_fiscale = '$codice_fiscale'
        and data_richiesta + interval '1 days' > current_timestamp 
        and id_tipo_richiesta in (2,3)
        and $tipo_richiesta = id_tipo_richiesta 
        and id_guc_ruoli = $id_guc_ruoli
        and  e.stato != 2";

    $avviso = "";
    $result = pg_query($conn, $query);
    while ($row = pg_fetch_assoc($result)) {
        if($row['count'] > 0)  
            $avviso = "La richiesta potrebbe non essere processata, in quanto è già presente una richiesta di modifica/eliminazione account nella stessa giornata a parità di CF, sistema e ruolo da modificare/eliminare";
    }


    $query = "INSERT INTO spid.spid_registrazioni
    (id_tipologia_utente, id_tipo_richiesta, cognome, nome, codice_fiscale, email, telefono, 
    id_ruolo_gisa, id_ruolo_bdu, id_ruolo_vam, id_clinica_vam, id_ruolo_gisa_ext, identificativo_ente, piva_numregistrazione, 
    comune, nominativo_referente, ruolo_referente, email_referente, telefono_referente, data_richiesta, codice_gisa, indirizzo, cap, id_gestore_acque, pec, id_ruolo_digemon,
    numero_ordine_vet, provincia_ordine_vet, numero_autorizzazione_regionale_vet,
    id_asl, id_tipologia_trasp_dist,
    numero_decreto_prefettizio, scadenza_decreto_prefettizio, id_guc_ruoli,
    ip, user_agent)
    VALUES
    ($tipologia_utente, $tipo_richiesta, '$cognome', '$nome', '$codice_fiscale', '$email', '$telefono', 
    $id_ruolo_gisa, $id_ruolo_bdu, $id_ruolo_vam, '{ $id_clinica_vam }', $id_ruolo_ext, '$identificativo_ente', '$piva_numregistrazione', 
    '$comune', '$nominativo_referente', '$ruolo_referente', '$email_referente', '$telefono_referente', CURRENT_TIMESTAMP, '$codice_gisa', '$indirizzo', '$cap', $id_gestore_acqua, '$pec', $digemon , 
    '$numero_ordine_vet', '$provincia_ordine_vet', '$numero_autorizzazione_regionale_vet',
    $id_asl, $id_tipologia_trasp_dist,
    '$numero_decreto_prefettizio', '$scadenza_decreto_prefettizio', $id_guc_ruoli,
    '$ip', '$userAgent')
    returning id";
    $result = pg_query($conn, $query);
    if(!$result)
        die("KO ".$query);
    while ($row = pg_fetch_assoc($result)) {
        $richiesta = date("Y").'-RIC-'.str_pad($row['id'], 8, "0", STR_PAD_LEFT);
        $result2 = pg_query("UPDATE spid.spid_registrazioni SET numero_richiesta = '$richiesta' where id =" .$row['id']);
        if(!$result2)
            die("KO numero richiesta");

        $HTML = str_replace("'", "''", $_POST['html']);

        $queryHtml = "INSERT INTO spid.html_registrazioni (id_spid_registrazioni, html) values (" .$row['id'] . ", '".$HTML."')";
        $result3 = pg_query($queryHtml);
        if(!$result3)
            die("KO $queryHtml");

        echo $richiesta.'||'.$avviso;
    }
}else if($operation == 'printPdf'){

    $num_richiesta = $_GET['numero_richiesta'];
    $query = "select html from spid.html_registrazioni hr where id_spid_registrazioni = (select id from spid.spid_registrazioni sr where numero_richiesta = '$num_richiesta')";
    $conn = getConnection("guc");
    $result = pg_query($conn, $query);
    echo pg_last_error($conn);
    while ($row = pg_fetch_assoc($result)) {
        session_start();
        $HTML = str_replace('"', '\"', $_POST['html']);
        $_SESSION['html'] = $row['html'];
        $_SESSION['numero_richiesta'] = $num_richiesta;
        header("location: esporta.php");
    }

}

?>
