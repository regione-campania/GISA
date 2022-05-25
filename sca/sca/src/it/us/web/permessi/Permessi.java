/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.permessi;

import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.dao.GuiViewDAO;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.talos.Talos;
import org.talos.TalosFactory;
import org.talos.exception.EntityNotFoundException;
import org.talos.model.Category;

public class Permessi {
	static String[]	permessi			= { "r", "w" };
	static String[]	gruppoPermessi		= { "rw" };
	static boolean	DEFAULT_POLICY		= false;
	static boolean	CREATE_GUI_ENTRIES	= true;
	static int		LETTURA				= 1;
	static int		NULLA				= 0;
	static int		SCRITTURA			= 2;

	private static TalosFactory tf = null;
	
	static
	{
		try
		{
			tf = TalosFactory.createDefault();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		loadPermissions();
	}
	
	/**
	 * Testa se un utente ha i permessi su di un'azione.
	 * @param utente
	 * @param gui Azione sulla quale si testano i permessi
	 * @param permessi r(read), w(write)
	 * @return
	 */
	public static boolean can( BUtente utente, BGuiView gui, String permessi )
	{
		boolean ret = false;
		Talos t = tf.open();
		
		try
		{
			String[] subjects = { gui.getKey() };
			String[] objects = { utente.getId() + "" };
			String[] op = { permessi };
			ret = t.withSubjects( subjects ).andObject( objects ).isAllowed( op );
			t.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ret = DEFAULT_POLICY;
			t.rollback();
		}
		
		return ret;
	}
	
	/**
	 * questo metodo ritorna un interno che identifica i diritti su una specifica GUI dell'utente
	 * @param utente
	 * @param f
	 * @param sf
	 * @param og
	 * @return
	 */
	public static int can( BUtente utente, String f, String sf, String og)
	{
		
		BGuiView b = GuiViewDAO.getView(f, sf, og);
		
		if( b == null && CREATE_GUI_ENTRIES )
		{
			b = GuiViewDAO.createGui( f, sf, og );
			loadSubject( b );
		}
		
		if( b!=null )
		{
			if( can(utente,b,permessi[1]) )
			{
				return SCRITTURA;
			}
			else if(can(utente,b,permessi[0]))
			{
				return LETTURA;
			}
			else
			{
				return NULLA;
			}
		}
		else
		{
			return NULLA;
		}
	}
		
	private static void loadSubject( BGuiView bg ) 
	{
		Talos t = tf.open();
		//BGuiView bg = GuiViewDAO.getView( "", "", "" );
		String key = bg.getKey();
		try
		{
			t.createSubject( key );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			t.rollback();
			t = tf.open();
		}
		
		t.commit();
	}
	
	
	private static void loadPermissions()
	{
		Talos t = null;
		
		try
		{
			t = tf.open();
			if( t.withPermissions( gruppoPermessi ).list().isEmpty())
			{
				t.createGroupPermissions( gruppoPermessi );
			}
			if( t.withPermissions( permessi ).list().isEmpty())
			{
				t.createSimplePermissions(permessi);
			}
			
			t.commit();
		}
		catch (Exception e)
		{
			try
			{
				t.rollback();
			}
			catch (Exception ex)
			{
				e.printStackTrace();
			}
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Associa un utente ad un gruppo.
	 * Provvede a rimuovere l'utente dal'eventuale gruppo precedentemente assegnato.
	 * 
	 * @param gruppo
	 * @param utente
	 */
	public static void add2category( String gruppo, BUtente utente )
	{
		Talos t = null;
		String[] categorie = { gruppo };
		String[] obj = { utente.getId() + "" };
		
		try
		{
			t = tf.open();
			t.createObject( obj[0] );
			t.commit();
		}
		catch (Exception e)
		{
			t.rollback();
			e.printStackTrace();
		}
		
		try
		{
			t = tf.open();
			t.withObjects( obj ).removeCategories( t.allCategories().list() );
			t.withCategories( categorie ).addObjects( obj );
			t.commit();
		}
		catch (Exception e)
		{
			t.rollback();
			e.printStackTrace();
		}
	}
	
	/**
	 * Rimuove un utente da ogni gruppo.
	 * 
	 * @param utente
	 */
	public static void removeFromAllCategory( BUtente utente )
	{
		Talos t = null;
		String[] obj = { utente.getId() + "" };
		
//		try
//		{
//			t = tf.open();
//			t.createObject( obj[0] );
//			t.commit();
//		}
//		catch (Exception e)
//		{
//			t.rollback();
//			e.printStackTrace();
//		}
		
		try
		{
			t = tf.open();
			t.withObjects( obj ).removeCategories( t.allCategories().list() );
			t.commit();
		}
		catch (Exception e)
		{
			t.rollback();
			e.printStackTrace();
		}
	}
	
	/**
	 * Crea in Talos il secureObject relativo all'utente
	 */
	public static void startupUser( BUtente utente )
	{
		Talos t = null;
		String[] obj = { utente.getId() + "" };
		
		try
		{
			t = tf.open();
			t.createObject( obj[0] );
			t.commit();
		}
		catch (Exception e)
		{
			t.rollback();
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Aggiunge un utente in Talos
	 * 
	 * @param utente
	 */
	public static void addUser( BUtente utente )
	{
		Talos t = null;
		String[] obj = { utente.getId() + "" };
		
		try
		{
			t = tf.open();
			t.createObject( obj[0] );
			t.commit();
		}
		catch (Exception e)
		{
			t.rollback();
			e.printStackTrace();
		}
		
	}
	
	public static void removeFromCategory( String gruppo, BUtente utente )
	{
		Talos t = tf.open();
		
		try
		{
			String[] categorie = { gruppo };
			String[] obj = { utente.getId() + "" };
			t.withCategories( categorie ).removeObjects( obj );
			t.commit();
		}
		catch (Exception e)
		{
			t.rollback();
			e.printStackTrace();
		}
	}
	
	/**
	 * Assegna i permessi ad un gruppo per un'azione.
	 * 
	 * N.B. L'assegnazione dei permessi ad un'azione implica la rimozione dei permessi precedentemente assegnati.
	 * @param gui Azione sulla quale assegnare i permessi
	 * @param gruppo Categoria di utenti alla quale assegnare i pernessi su "gui"
	 * @param permessi Permessi da assegnare (r, w, rw)
	 */
	public static void grant2category( BGuiView gui, String gruppo, String permessi )
	{
		Talos t = tf.open();
		
		try
		{
			String[] soggetti = { gui.getKey() };
			String[] categorie = { gruppo };
			String[] per = { permessi };
			t.withSubjects( soggetti ).andCategory( categorie ).revokeAll();
			if( permessi != null )
			{
				t.withSubjects( soggetti ).andCategory( categorie ).grant( per );
			}
			t.commit();
		}
		catch (Exception e)
		{
			t.rollback();
			e.printStackTrace();
		}
		
	}
	public static void grant2category( BGuiView gui, String gruppo, String permessi, Talos t )
	{
		try
		{
			String[] soggetti = { gui.getKey() };
			String[] categorie = { gruppo };
			String[] per = { permessi };
			t.withSubjects( soggetti ).andCategory( categorie ).revokeAll();
			if( permessi != null )
			{
				t.withSubjects( soggetti ).andCategory( categorie ).grant( per );
			}
		}
		catch (Exception e)
		{
			t.rollback();
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Revoca tutti i diritti ad una categoria (di utenti).
	 */
	public static void revokeAll2category( BGuiView gui, String gruppo )
	{
		Talos t = tf.open();
		
		try
		{
			String[] soggetti = { gui.getKey() };
			String[] categorie = { gruppo };
			t.withSubjects( soggetti ).andCategory( categorie ).revokeAll();
			t.commit();
		}
		catch (Exception e)
		{
			t.rollback();
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Crea una nuova categoria (di utenti).
	 */
	public static void createCategory( String ruolo, boolean clone, String ruoloDaClonare )
	{
		Talos t = tf.open();
		
		try
		{
			t.createCategory( ruolo );
			t.commit();
			
			//Se si devono clonare i permessi da un ruolo gia esistente
			if(clone)
			{
				String permesso = "";
				Talos t2 = tf.open();
				
				//Recupero di tutte le gui del sistema				
				Vector<BGuiView> gui = GuiViewDAO.getAll();
				int i = 0;
				
				//Loop su tutte le gui e assegnazione 
				//dello stesso permesso del ruolo da clonare
				while(i<gui.size())
				{
					permesso = getPermissions( ruoloDaClonare , gui.elementAt(i) , t2 );
					grant2category(gui.elementAt(i) , ruolo, permesso, t2 );
					i++;
				}
				t2.commit();
			}
			
			
		}
		catch (Exception e)
		{
			try
			{
				t.rollback();
			}
			catch (Exception ex)
			{
				e.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	
	public static String getPermissions( String ruolo, BGuiView gui )
	{
		String permessi = "no";
		Talos t = tf.open();
		Set<String> s = null;
		
		try
		{
			t.withCategories( ruolo );
		}
		catch (EntityNotFoundException e)
		{
			createCategory( ruolo, false, "" );
		}
		
		s = t.withSubjects( gui.getKey() ).andCategory( ruolo ).listNames();
		
		Iterator<String> i = s.iterator();
		while( i.hasNext() )
		{
			String perm = i.next();
			if( permessi == null || ( perm != null && permessi != null ))
			{
				if(perm.equalsIgnoreCase("w"))
					permessi = "rw";
				else if(perm.equalsIgnoreCase("r"))
					permessi = "ro";
			}
		}
		t.commit();
//		tf.close();
		return permessi;
	}
	
	public static String getPermissions( String ruolo, BGuiView gui, Talos t )
	{
		String permessi = null;
		Set<String> s = null;
		
		try
		{
			t.withCategories( ruolo );
		}
		catch (EntityNotFoundException e)
		{
			createCategory( ruolo , false, ""  );
		}
		
		s = t.withSubjects( gui.getKey() ).andCategory( ruolo ).listNames();
		
		Iterator<String> i = s.iterator();
		while( i.hasNext() )
		{
			String perm = i.next();
			if( permessi == null || ( permessi.equalsIgnoreCase( "r" ) && (perm != null) && perm.equalsIgnoreCase( "w" )  ))
			{
				permessi = perm;
			}
		}
		return permessi;
	}
	
	public static Vector<BGuiView> getPermissionsOnRuolo( String ruolo , String funzione )
	{
		Talos					t 	= tf.open();
		Vector<BGuiView>		v	= GuiViewDAO.getByFunction(funzione);
		Vector<BGuiView>		v2	= new Vector<BGuiView>();
		Enumeration<BGuiView>	e	= v.elements();
		
		while( e.hasMoreElements() )
		{
			BGuiView bgv	= e.nextElement();
			String p		= getPermissions( ruolo, bgv, t );
			bgv.setDiritti( p );
			v2.addElement( bgv );
		}
		t.commit();
		return v2;
	}
	
	public static Vector<BGuiView> getPermissionsOnRuolo( String ruolo )
	{
		Talos					t 	= tf.open();
		Vector<BGuiView>		v	= GuiViewDAO.getAll();
		Vector<BGuiView>		v2	= new Vector<BGuiView>();
		Enumeration<BGuiView>	e	= v.elements();
		
		while( e.hasMoreElements() )
		{
			BGuiView bgv	= e.nextElement();
			String p		= getPermissions( ruolo, bgv, t );
			bgv.setDiritti( p );
			v2.addElement( bgv );
		}
		t.commit();
		return v2;
	}

	public static String getRuolo(BUtente utente) {

		//t.withObjects( utente.get ).listCategories();
		String ruolo = null;
		Talos t = null;
		Collection<Category> ruoli = null;
		String[] obj = { utente.getId() + "" };
		
		try
		{
			t = tf.open();
			ruoli = t.withObjects( obj[0] ).listCategories();
			t.commit();
		}
		catch (Exception e)
		{
			t.rollback();
			e.printStackTrace();
		}
		
		if( ruoli != null )
		{
			Iterator<Category> i = ruoli.iterator();
			if( i.hasNext() )
			{
				ruolo = i.next().getName();
			}
		}
		
		return ruolo;
	}

	@SuppressWarnings("unchecked")
	public static boolean isRuoloAssegnato(String ruolo)
	{
		boolean ret = true;
		Talos t = null;
		try
		{
			t = tf.open();
			Collection c = t.withCategories( ruolo ).listObjects();
			if( c.isEmpty() )
			{
				ret = false;
			}
			t.commit();
		}
		catch (Exception e)
		{
			try
			{
				e.printStackTrace();
				t.rollback();
				ret = false;
			}
			catch (Exception e1)
			{
				e.printStackTrace();
				ret = true;
			}
		}
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static int getNumeroUtentiAssegnatiRuolo(String ruolo)
	{
		int ret = 0;
		Talos t = null;
		try
		{
			t = tf.open();
			Collection c = t.withCategories( ruolo ).listObjects();
			if( !c.isEmpty() )
			{
				ret = c.size();
			}
			t.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public static void rimuoviRuolo(String nome_ruolo)
	{
		Talos t = null;
		t = tf.open();
		t.withCategories( nome_ruolo ).remove();
		t.commit();
	}
	
}
