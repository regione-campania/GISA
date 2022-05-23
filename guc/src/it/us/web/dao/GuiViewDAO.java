/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao;

import it.us.web.bean.BFunzione;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BSubfunzione;
import it.us.web.constants.Sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuiViewDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( GuiViewDAO.class );
	
	public static Vector<BGuiView> getByFunction(String funzione)
	{
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		BGuiView			bgv		= null;
		Vector<BGuiView>	ret		= new Vector<BGuiView>();
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_GUI_VIEW_FUNZ_BY_ID );
			stat.setInt(1, Integer.parseInt(funzione));
			
			rs = stat.executeQuery();
			
			while(rs.next())
			{
				bgv = loadResultSet(rs);
				ret.addElement( bgv );
			}
		}
		catch (SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return ret ;
	}
	
	public static Vector<BGuiView> getAll()
	{
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		BGuiView			bgv		= null;
		Vector<BGuiView>	ret		= new Vector<BGuiView>();
		
		try
		{
			conn	= retrieveConnection();
			stat	= conn.prepareStatement( Sql.GET_GUI_VIEW_FUNZ_ALL );
			rs		= stat.executeQuery();

			
			while( rs.next() )
			{
				bgv = loadResultSet(rs);
				ret.addElement(bgv);
			}
		}
		catch (SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return ret ;
	}
	
	public static BGuiView getById(int id){
		
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		BGuiView			bgv		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_GUI_VIEW_BY_ID );
			stat.setInt(1, id);
			
			rs = stat.executeQuery();
			
			if( rs.next() )
			{
				bgv = loadResultSet(rs);
			}
		}
		catch (SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return bgv ;
	}
			
	public static BGuiView getView(String funzione , String subfunzione , String gui)
	{
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		BGuiView			bgv		= null;
		
		try
		{
			conn = retrieveConnection();

			if( !( (funzione == null) || funzione.equals("") ) )
			{
				if( (subfunzione == null) || subfunzione.equals("") )
				{
					stat = conn.prepareStatement( Sql.GET_GUI_VIEW_FUNZ );
					stat.setString( 1, funzione );
				}
				else
				{
					if( (gui == null) || gui.equals("") )
					{
						stat = conn.prepareStatement( Sql.GET_GUI_VIEW_FUNZ_SUBFUNZ );
						stat.setString(1, funzione);
						stat.setString(2, subfunzione);	
					}
					else
					{
						stat = conn.prepareStatement( Sql.GET_GUI_VIEW_FUNZ_SUBFUNZ_GUI );
						stat.setString( 1, funzione );
						stat.setString( 2, subfunzione );
						stat.setString( 3, gui );
					}
				}
			}
			else
			{
				return null;
			}
			
			rs = stat.executeQuery();
			
			if( rs.next() )
			{
				bgv = loadResultSet(rs);
				bgv.setKey(bgv.getNome_funzione()+"->" +bgv.getNome_subfunzione()+"->"+bgv.getNome_gui());
			}
		}
		catch (SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return bgv ;
		
	}
	public static BGuiView createGui( String f, String sf, String og )
	{
		Connection			conn = null;
		PreparedStatement	stat = null;
		
		BFunzione			bfu = null;
		BSubfunzione		bsf = null;
		
		bfu = FunzioneDAO.getFunzione( f );
		if( bfu != null )
		{
			bsf = SubfunzioneDAO.ottieniSubfunzione( bfu, sf );
		}
		
		try
		{
			conn = retrieveConnection();
			conn.setAutoCommit( false );
						
			if( bfu == null )
			{
				stat = conn.prepareStatement( Sql.INSERT_NEW_FUNZIONE );
				stat.setString( 1, f );
				stat.execute();
				stat.close();
				stat = null;
				conn.commit();
				bfu = FunzioneDAO.getFunzione( f );
			}
			
			if( bsf == null )
			{
				stat = conn.prepareStatement( Sql.INSERT_NEW_SUBFUNZIONE );
				stat.setInt( 1, bfu.getId() );
				stat.setString( 2, sf );
				stat.execute();
				stat.close();
				stat = null;
				conn.commit();
				bsf = SubfunzioneDAO.ottieniSubfunzione( bfu, sf );
			}
			
			stat = conn.prepareStatement( Sql.INSERT_NEW_GUIOBJECT );
			
			stat.setInt( 1, bsf.getId() );
			stat.setString( 2, og );			
			stat.execute();
			conn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			logger.error( "", e );
		}
		finally
		{
			close( stat, conn );
		}
		
		return getView( f, sf, og );
	}

	private static BGuiView loadResultSet(ResultSet rs) throws SQLException
	{
		BGuiView bgv = new BGuiView();
		
		bgv.setDescrizione_funzione		( rs.getString("DESCRIZIONE_FUNZIONE") );
		bgv.setDescrizione_gui			( rs.getString("DESCRIZIONE_GUI") );
		bgv.setDescrizione_subfunzione	( rs.getString("DESCRIZIONE_SUBFUNZIONE") );
		bgv.setId_funzione				( rs.getInt("ID_FUNZIONE") );
		bgv.setId_gui					( rs.getInt("ID_GUI") );
		bgv.setId_subfunzione			( rs.getInt("ID_SUBFUNZIONE") );
		bgv.setNome_funzione			( rs.getString("NOME_FUNZIONE") );
		bgv.setNome_gui					( rs.getString("NOME_GUI") );
		bgv.setNome_subfunzione			( rs.getString("NOME_SUBFUNZIONE") );
		
		return bgv;
	}
}
