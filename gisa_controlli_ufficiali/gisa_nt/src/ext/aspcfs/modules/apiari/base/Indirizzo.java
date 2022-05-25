/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Indirizzo extends GenericBean {

    public static final int TIPO_SEDE_LEGALE = 1 ;
    public static final int TIPO_SEDE_OPERATIVA = 5 ;
    public static final int TIPO_SEDE_MOBILE = 7 ;



    private int idIndirizzo = -1;
    private String cap;
    private int comune = -1;
    private String descrizioneComune ;
    private String provincia;
    private String via;
    private String nazione;
    private double latitudine;
    private double longitudine;
    private int enteredBy = -1;
    private int modifiedBy = -1;
    private String ipEnteredBy;
    private String ipModifiedBy;
    private int idProvincia = -1 ;
    private int tipologiaSede = -1;
    private String descrizione_provincia;
    private int idAsl =-1;
    private String descrizioneAsl ="";
    private Timestamp modified;
    private String comuneTesto ;
    private String codiceIstatComune ;
    private String siglaProvincia ;
    private String codiceAsl ;
    private String civico ;

   
   
   

    public String getCivico() {
        return civico;
    }


    public void setCivico(String civico) {
        this.civico = civico;
    }


    public String getCodiceAsl() {
        return codiceAsl;
    }


    public void setCodiceAsl(String codiceAsl) {
        this.codiceAsl = codiceAsl;
    }


    public String getComuneTesto() {
        if(comuneTesto!=null)
        return comuneTesto.trim();
        return "" ;
    }

   
    public String getCodiceIstatComune() {
        return codiceIstatComune;
    }


    public void setCodiceIstatComune(String codiceIstatComune) {
        this.codiceIstatComune = codiceIstatComune;
    }


    public String getSiglaProvincia() {
        return siglaProvincia;
    }


    public void setSiglaProvincia(String siglaProvincia) {
        this.siglaProvincia = siglaProvincia;
    }


    public void setComuneTesto(String comuneTesto) {
        this.comuneTesto = comuneTesto;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

   
    public Indirizzo(){

    }


    public void setInfoComune(String comune,Connection db) throws SQLException
    {

        PreparedStatement pst = db.prepareStatement("select  id,nome from comuni1 where nome ilike ? ");
        pst.setString(1, comune);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            this.setComune(rs.getInt(1));
            this.setDescrizioneComune(rs.getString(2));
        }
    }
   
    public void getIstatComune (Connection db) throws SQLException
    {
       

        PreparedStatement pst = db.prepareStatement("Select comuni1.cod_comune,lp.cod_provincia  " +
                "FROM comuni1  " +
                "left join lookup_province lp on lp.code = comuni1.cod_provincia::int " +
                " where id = ? ");
        pst.setInt(1, comune);
                ResultSet rs = pst.executeQuery();
       
        if (rs.next())
        {
            codiceIstatComune = rs.getString(1);
            siglaProvincia= rs.getString(2);
        }
           
       
    }
   
    public Indirizzo(Connection db, int idIndirizzo) throws SQLException{
        /*    if (idIndirizzo == -1){
                throw new SQLException("Invalid Indirizzo");
            }*/

        PreparedStatement pst = db.prepareStatement("Select i.*,asl.code , asl.description ,comuni1.cod_provincia,coalesce (comuni1.nome,comune_testo) as descrizione_comune,lp.description as descrizione_provincia,"
                + " 'R'||comuni1.codiceasl_bdn as codice_asl,comuni1.cod_comune as istat_comune,lp.cod_provincia as sigla_provincia   "
                + "from opu_indirizzo i " +
                "left join comuni1 on (comuni1.id = i.comune) " +
                "left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true "+
                "left join lookup_province lp on lp.code = comuni1.cod_provincia::int " +
                " where i.id = ? ");
       

        pst.setInt(1, idIndirizzo);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            buildRecord(rs);
            this.idAsl = rs.getInt("code");
            this.descrizioneAsl = rs.getString("description");
        }
        rs.close();
        pst.close();
    }

    private int opu_evita_indirizzi_duplicati(Connection db ) throws SQLException
    {
        int idIndirizzo = -1 ;

        String sql = "select id from opu_indirizzo where comune = ? and trim(lower(provincia)) = trim(lower(?)) and trim(lower(via)) = trim(lower(?)) and latitudine = ? and longitudine = ? " ;
        try
        {
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setInt(1, this.comune);
            pst.setString(2, this.provincia);
            pst.setString(3, this.via);
            pst.setDouble(4, this.latitudine);
            pst.setDouble(5, this.longitudine);
            ResultSet rs = pst.executeQuery();
            if (rs.next())
                idIndirizzo = rs.getInt(1);


        }
        catch(SQLException e)
        {
            throw e ;
        }
        return idIndirizzo ;

    }


    public Indirizzo(HttpServletRequest request,Connection db,ActionContext context) throws SQLException{

        UserBean user = (UserBean) request.getSession().getAttribute("User");
       
        if ((String)request.getParameter("searchcodeIdprovincia") != null){
            if (request.getParameter("searchcodeIdprovincia")!= null && !"".equalsIgnoreCase(request.getParameter("searchcodeIdprovincia")) && new Integer ((String)request.getParameter("searchcodeIdprovincia")) > -1)
            {
                this.setProvincia(request.getParameter("searchcodeIdprovincia"));
                this.setIdProvincia(Integer.parseInt(request.getParameter("searchcodeIdprovincia")));
            }
            else
                this.setProvincia(request.getParameter("searchcodeIdprovinciaTesto"));
        }else if ((String)request.getParameter("searchcodeIdprovinciaAsl") != null){
            this.setProvincia((String)request.getParameter("searchcodeIdprovinciaAsl"));
        }
        try
        {
        this.setComune(request.getParameter("searchcodeIdComune"));
        }
        catch(NumberFormatException ex)
        {
        	if(request.getParameter("searchcodeIdComune")!=null && !request.getParameter("searchcodeIdComune").equals("") && !request.getParameter("searchcodeIdComune").equals("null"))
        		this.setInfoComune(request.getParameter("searchcodeIdComune"), db);
        }
        this.setVia(request.getParameter("viaTesto"));

        this.setDescrizioneComune(request.getParameter("searchcodeIdComuneinput"));
        this.setDescrizione_provincia(request.getParameter("searchcodeIdprovinciaTesto"));



        this.setLatitudine(request.getParameter("latitudine"));
        this.setLongitudine(request.getParameter("longitudine"));
        this.setCap(request.getParameter("cap"));
        if(request.getParameter("presso")!=null && !request.getParameter("presso").equals(""))
        	this.setCap(request.getParameter("presso"));
        this.setEnteredBy(user.getUserId());
        this.setModifiedBy(user.getUserId());
        String ip = user.getUserRecord().getIp();
        this.setIpEnteredBy(ip);
        this.setIpModifiedBy(ip);
        this.setIdAsl(db);
        this.getIstatComune(db);
        this.insert(db,context);


    }



    public Indirizzo(HttpServletRequest request,LookupList nazioniList,Connection db,ActionContext context) throws SQLException{

        UserBean user = (UserBean) request.getSession().getAttribute("User");

        this.setNazione(nazioniList.getSelectedValue(Integer.parseInt(request.getParameter("nazioneSedeLegale"))));
       
        if ("106".equals(request.getParameter("nazioneSedeLegale")))
        {

            if ((String)request.getParameter("searchcodeIdprovincia") != null){
                if (! "".equals(request.getParameter("searchcodeIdprovincia"))  && new Integer ((String)request.getParameter("searchcodeIdprovincia")) > 0)
                {
                    this.setProvincia(request.getParameter("searchcodeIdprovincia"));
                    this.setIdProvincia(Integer.parseInt(request.getParameter("searchcodeIdprovincia")));
                    this.setDescrizione_provincia(request.getParameter("searchcodeIdprovinciainput"));
                }
               
            }else if ((String)request.getParameter("searchcodeIdprovinciaAsl") != null){
                this.setProvincia((String)request.getParameter("searchcodeIdprovinciaAsl"));
            }
            this.setComune(request.getParameter("searchcodeIdComune"));
            this.setDescrizioneComune(request.getParameter("searchcodeIdComuneinput"));
            this.setVia(request.getParameter("viaTesto"));


        }
        else
        {
            this.setComuneTesto(request.getParameter("searchcodeIdComuneinput"));
            this.setComune(-1);
            this.setVia(request.getParameter("viainput"));
   
        }


        this.setCap(request.getParameter("cap"));
        if(request.getParameter("presso")!=null && !request.getParameter("presso").equals(""))
        	this.setCap(request.getParameter("presso"));
        this.setEnteredBy(user.getUserId());
        this.setModifiedBy(user.getUserId());
        String ip = user.getUserRecord().getIp();
        this.setIpEnteredBy(ip);
        this.setIpModifiedBy(ip);
        this.setIdAsl(db);
        this.insert(db,context);


    }
   
   



   




    public int getIdAsl() {
        return idAsl;
    }

    public void setIdAsl(int idAsl) {
        this.idAsl = idAsl;
    }
   
    public void setIdAsl(Connection db) throws SQLException {
       
        PreparedStatement pst = db.prepareStatement("select code from lookup_site_id where codiceistat in (select codiceistatasl from comuni1 where id =?) ");
        pst.setInt(1, this.comune);
        ResultSet rs = pst.executeQuery();
        if (rs.next())
            this.idAsl=rs.getInt("code");
        if (this.idAsl <=0)
        {
            this.idAsl = 16;
        }
    }


    public String getDescrizioneAsl() {
        return descrizioneAsl;
    }

    public void setDescrizioneAsl(String descrizioneAsl) {
        this.descrizioneAsl = descrizioneAsl;
    }

    public String getDescrizione_provincia() {
        return (descrizione_provincia!=null) ? descrizione_provincia.trim() : "";
    }

    public void setDescrizione_provincia(String descrizione_provincia) {
        this.descrizione_provincia = descrizione_provincia;
    }

    public String getDescrizioneComune() {
        return (descrizioneComune!=null) ? descrizioneComune.trim() : "";
    }

    public void setDescrizioneComune(String descrizioneComune) {
        this.descrizioneComune = descrizioneComune;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public int getIdIndirizzo() {
        return idIndirizzo;
    }


    public void setIdIndirizzo(int idIndirizzo) {
        this.idIndirizzo = idIndirizzo;
    }



    public String getCap() {
        return (cap!=null) ? cap.trim() : "";
    }


    public void setCap(String cap) {
        this.cap = cap;
    }



    public int getComune() {
       
        return comune;
    }


    public void setComune(String comune) {
        if (comune != null && ! comune.equals(""))
            this.comune = new Integer(comune).intValue();
    }




    public void setComune(int idComune){
        this.comune =idComune;
    }




    public String getProvincia() {
        return (provincia!=null) ? provincia.trim() : "" ;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }


    public String getVia() {
        return (via!=null) ? via.trim() : "" ;
    }


    public void setVia(String via) {
        this.via = via;
    }


    public String getNazione() {
        if(nazione!=null)
            return nazione.trim();
        return "";
    }





    public void setNazione(String nazione) {
        this.nazione = nazione;
    }






    public double getLatitudine() {
        return latitudine;
    }





    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }





    public double getLongitudine() {
        return longitudine;
    }





    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }



    public void setLatitudine(String latitude) {
        try {
            this.latitudine = Double.parseDouble(latitude.replace(',', '.'));
        } catch (Exception e) {
            this.latitudine = 0;
        }
    }


    public void setLongitudine(String longitude) {
        try {
            this.longitudine = Double.parseDouble(longitude.replace(',', '.'));
        } catch (Exception e) {
            this.longitudine = 0;
        }
    }





    public int getTipologiaSede() {
        return tipologiaSede;
    }





    public void setTipologiaSede(int tipologiaSede) {
        this.tipologiaSede = tipologiaSede;
    }


    public void setTipologiaSede(String tipologiaSede) {
        this.tipologiaSede = new Integer(tipologiaSede).intValue();
    }


    public int getEnteredBy() {
        return enteredBy;
    }


    public void setEnteredBy(int enteredBy) {
        this.enteredBy = enteredBy;
    }


    public int getModifiedBy() {
        return modifiedBy;
    }


    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    public String getIpEnteredBy() {
        return ipEnteredBy;
    }


    public void setIpEnteredBy(String ipEnteredBy) {
        this.ipEnteredBy = ipEnteredBy;
    }


    public String getIpModifiedBy() {
        return ipModifiedBy;
    }


    public void setIpModifiedBy(String ipModifiedBy) {
        this.ipModifiedBy = ipModifiedBy;
    }

    public boolean insert(Connection db,ServletContext context) throws SQLException{
        StringBuffer sql = new StringBuffer();
        try{

        	if(cap==null || cap.trim().equals("") || cap.trim().equals("null"))
        		cap = ComuniAnagrafica.getCap(db, this.comune);

            idIndirizzo = this.opu_evita_indirizzi_duplicati(db);

            if (idIndirizzo <=0)
            {
                //Controllare se c'e' gia' soggetto fisico, se no inserirlo
                idIndirizzo = DatabaseUtils.getNextSeq(db, "opu_indirizzo_id_seq");

                sql.append("INSERT INTO opu_indirizzo (");

                if (idIndirizzo > -1)
                    sql.append("id,");

                sql.append("via, cap, comune, provincia, nazione");


                sql.append(", latitudine");



                sql.append(", longitudine");

                sql.append(", comune_testo");


                sql.append(")");

                sql.append("VALUES (?,?,?,?,?,?");


                if (idIndirizzo > -1) {
                    sql.append(",?");
                }


                sql.append(", ?");



                sql.append(", ?");



                sql.append(")");

                int i = 0;

                PreparedStatement pst = db.prepareStatement(sql.toString());

                if (idIndirizzo > -1) {
                    pst.setInt(++i, idIndirizzo);
                }


                pst.setString(++i, this.via);
                pst.setString(++i, this.cap);
                pst.setInt(++i, this.comune);
                pst.setString(++i, this.provincia);
                pst.setString(++i, this.nazione);
                pst.setDouble(++i, this.latitudine);
                pst.setDouble(++i, this.longitudine);
                pst.setString(++i, this.comuneTesto);


                pst.execute();
                pst.close();

            }
            //    JOptionPane.showMessageDialog(null,pst.toString()+"\nINSERT INTO opu_indirizzo SET via="+this.via+" COMUNE= "+this.comune+" PROVINCIA= "+this.provincia+" CAP= "+this.cap+" ID="+idIndirizzo+"\n Stringhe: Provincia: "+this.descrizione_provincia+" Comune: "+this.descrizioneComune);



        }catch (SQLException e) {

            throw new SQLException(e.getMessage());
        } finally {

        }

        return true;

    }


    public boolean insert(Connection db,ActionContext context) throws SQLException{
        StringBuffer sql = new StringBuffer();
        try{
        	if(cap==null || cap.trim().equals("") || cap.trim().equals("null"))
        		cap = ComuniAnagrafica.getCap(db, this.comune);

            idIndirizzo = this.opu_evita_indirizzi_duplicati(db);

            if (idIndirizzo <=0)
            {
                //Controllare se c'e' gia' soggetto fisico, se no inserirlo
                idIndirizzo = DatabaseUtils.getNextSeq(db, "opu_indirizzo_id_seq");

                sql.append("INSERT INTO opu_indirizzo (");

                if (idIndirizzo > -1)
                    sql.append("id,");

                sql.append("via, cap, comune, provincia, nazione");


                sql.append(", latitudine");



                sql.append(", longitudine");

                sql.append(", comune_testo");


                sql.append(")");

                sql.append("VALUES (?,?,?,?,?,?");


                if (idIndirizzo > -1) {
                    sql.append(",?");
                }


                sql.append(", ?");



                sql.append(", ?");



                sql.append(")");

                int i = 0;

                PreparedStatement pst = db.prepareStatement(sql.toString());

                if (idIndirizzo > -1) {
                    pst.setInt(++i, idIndirizzo);
                }


                pst.setString(++i, this.via);
                pst.setString(++i, this.cap);
                pst.setInt(++i, this.comune);
                pst.setString(++i, this.provincia);
                pst.setString(++i, this.nazione);
                pst.setDouble(++i, this.latitudine);
                pst.setDouble(++i, this.longitudine);
                pst.setString(++i, this.comuneTesto);


                pst.execute();
                pst.close();

            }
            else
            {
                int i = 0 ;
                PreparedStatement pst = db.prepareStatement("update opu_indirizzo set latitudine=? , longitudine=? where id =?");
                pst.setDouble(++i, this.latitudine);
                pst.setDouble(++i, this.longitudine);
                pst.setInt(++i, this.idIndirizzo);
                pst.execute();


            }
            //    JOptionPane.showMessageDialog(null,pst.toString()+"\nINSERT INTO opu_indirizzo SET via="+this.via+" COMUNE= "+this.comune+" PROVINCIA= "+this.provincia+" CAP= "+this.cap+" ID="+idIndirizzo+"\n Stringhe: Provincia: "+this.descrizione_provincia+" Comune: "+this.descrizioneComune);



        }catch (SQLException e) {

            throw new SQLException(e.getMessage());
        } finally {

        }

        return true;

    }







    public Indirizzo (ResultSet rs) throws SQLException {

        buildRecord(rs);

    }




    protected void buildRecord(ResultSet rs) throws SQLException {

        this.descrizione_provincia = rs.getString("descrizione_provincia");
        this.idIndirizzo = rs.getInt("id");
        this.comune = rs.getInt("comune");
        if(rs.getString("cod_provincia")!=null)
            this.idProvincia = Integer.parseInt(rs.getString("cod_provincia"));
        this.provincia = rs.getString("provincia");
        this.cap = rs.getString("cap");
        this.descrizioneComune = rs.getString("descrizione_comune");
        this.via = rs.getString("via");
        this.nazione = rs.getString("nazione");
        this.latitudine = rs.getDouble("latitudine");
        this.longitudine = rs.getDouble("longitudine");
        try{
            this.tipologiaSede = rs.getInt("tipologia_sede");
        }catch (org.postgresql.util.PSQLException e){

        }
       
        try{
            this.civico = rs.getString("civico");
        }catch (org.postgresql.util.PSQLException e){

        }
       
        try{
            this.comuneTesto = rs.getString("comune_testo");
        }catch (org.postgresql.util.PSQLException e){

        }
       
       
        try{
            this.descrizione_provincia = rs.getString("descrizione_provincia");
        }catch (org.postgresql.util.PSQLException e){

        }
       
       
       
       
        codiceIstatComune = rs.getString("istat_comune");
        siglaProvincia = rs.getString("sigla_provincia");
        codiceAsl = rs.getString("codice_asl");


    }



    public HashMap<String, Object> getHashmap() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {

        HashMap<String, Object> map = new HashMap<String, Object>();
        Field[] campi = this.getClass().getDeclaredFields();
        Method[] metodi = this.getClass().getMethods();
        for (int i = 0 ; i <campi.length; i++)
        {
            String nomeCampo = campi[i].getName();

            for (int j=0; j<metodi.length; j++ )
            {
                if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
                {
                    if(nomeCampo.equalsIgnoreCase("via"))
                    {
                        //map.put("descrizione", metodi[j].invoke(this));
                        map.put("descrizionevia", metodi[j].invoke(this));
                        map.put("value", metodi[j].invoke(this));
                        map.put("label", metodi[j].invoke(this));
                    }
                    else{
                        if(nomeCampo.equalsIgnoreCase("idIndirizzo"))
                        {
                            //map.put("codice", metodi[j].invoke(this));
                            map.put("codicevia", metodi[j].invoke(this));
                            map.put("idindirizzo", metodi[j].invoke(this));
                        }
                        else
                        {
                            map.put(nomeCampo, (""+metodi[j].invoke(this)).trim());
                        }
                    }
                }

            }

        }

        return map ;

    }   
   
   
   
   
   
    public HashMap<String, Object> getHashmapIndirizzo() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {

        HashMap<String, Object> map = new HashMap<String, Object>();
        Field[] campi = this.getClass().getDeclaredFields();
        Method[] metodi = this.getClass().getMethods();
        for (int i = 0 ; i <campi.length; i++)
        {
            String nomeCampo = campi[i].getName();

            for (int j=0; j<metodi.length; j++ )
            {
                if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
                {
               
                        //map.put("descrizione", metodi[j].invoke(this));
                        map.put(nomeCampo,new String (""+metodi[j].invoke(this)+""));
                       
                   
               
                }

            }

        }

        return map ;

    }   

   

    public String toString()
    {
        String descrizione = "" ;

        if(via!=null)
            descrizione = via;
        if (cap != null)
            descrizione += ", " + cap;
        if (descrizioneComune!=null)
            descrizione += " " + descrizioneComune ;
        if(descrizione_provincia!=null )
            descrizione+=" , "+descrizione_provincia ;

        return descrizione ;
    }

    //IN CASO DI MODIFICA INDIRIZZO, CREA NUOVA ENTRY PER TENERE TRACCIA DEL CAMBIO
    public boolean insertModificaIndirizzo(Connection db, int operatoreId, int oldIndirizzoId, int newIndirizzoId, int userId, int tipoSede) throws SQLException{
        StringBuffer sql = new StringBuffer();
        java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        java.sql.Timestamp data_date = new Timestamp(timeNow.getTime());
        try{

            sql.append("INSERT INTO opu_indirizzo_history (id_operatore, id_vecchio_indirizzo, id_nuovo_indirizzo, utente_modifica, data_modifica, tipo_sede");
            sql.append(")");

            sql.append("VALUES (?,?,?, ?, ?, ?");
            sql.append(")");

            int i = 0;

            PreparedStatement pst = db.prepareStatement(sql.toString());
            pst.setInt(++i, operatoreId);
            pst.setInt(++i, oldIndirizzoId);
            pst.setInt(++i, newIndirizzoId);
            pst.setInt(++i, userId);
            pst.setTimestamp(++i, data_date);
            pst.setInt(++i, tipoSede); //1: LEGALE 2: OPERATIVA 3: RESPONSABILE


            pst.execute();
            pst.close();



        }catch (SQLException e) {

            throw new SQLException(e.getMessage());
        } finally {

        }

        return true;

    }
   
   
   
    public int compareTo(Indirizzo otherIndirizzo) {
          
        String nazione = otherIndirizzo.getNazione();
        String provincia = otherIndirizzo.getDescrizione_provincia()+"";
        String comune = otherIndirizzo.getDescrizioneComune();
        String comuneTesto = otherIndirizzo.getComuneTesto();
        String via = otherIndirizzo.getVia();
               
        if (this.getNazione().equalsIgnoreCase(nazione)         &&
            (this.getDescrizione_provincia().equalsIgnoreCase(provincia))        &&
            (this.getDescrizioneComune().equalsIgnoreCase(comune) || this.getComuneTesto().equalsIgnoreCase(comuneTesto)) &&
           
            this.getVia().equalsIgnoreCase(via)
            )
            {
                return 0;
            }
               
               
                return 1;
       
       

    }
   
    public void updateCoordinate(Connection db)
    {
       
        PreparedStatement pst=null;
        try {
            pst = db.prepareStatement("update opu_indirizzo set latitudine = ? , longitudine = ?  where id = ?");
            pst.setDouble(1, latitudine);
            pst.setDouble(2, longitudine);
            pst.setInt(3, idIndirizzo);
            pst.execute();
       
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
           
       
   
   
   
   

}
}