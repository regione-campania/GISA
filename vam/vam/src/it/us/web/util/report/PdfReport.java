/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.report;

import it.us.web.util.DateUtils;
import it.us.web.util.PdfUtil;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Permette di generare un report composto di due parti, entrambe opzionali (frontespizio ed elenco items).<br/>
 * Il frontespizio viene generato se viene settato almeno un filtro ( addFiltro ) o almeno un elemento di riepilogo ( addRiepilogoItem ).<br/>
 * Le pagine con l'elenco di item vengono generate se questi (gli item) vengono setatti ( setItems ).<br/>
 * Per generare il report invocare il metodo render.<br/>
 * E' possibile generare solo il frontespizio con il metodo "renderFrontespizio".<br/>
 * E' possibile generare solo l'elenco di item con il metodo "renderItems".
 */
public class PdfReport
{
	public static final boolean VERTICALE	= true;
	public static final boolean ORIZZONTALE	= false;
	
	private boolean orientamento = VERTICALE;
	
	private List<Object>				items		= null;
	private ArrayList<Colonna>			colonne		= new ArrayList<Colonna>();
	private ArrayList<String>			filtri		= new ArrayList<String>();
	private ArrayList<RiepilogoItem>	riepilogo	= new ArrayList<RiepilogoItem>();
	
	private int elementiPerPagina	    = 15;
	private boolean elementiContigui	= true;
	
	private float margineTop	= 30f;
	private float margineLeft	= 30f;
	private float margineBottom	= 30f;
	private float margineRight	= 30f;
	
	private Date				data		= new Date();
	private String				dataFormato	= "dd/MM/yyyy";
	private SimpleDateFormat 	sdfToPrint	= new SimpleDateFormat( dataFormato );
	
	private String booleanTrueValue		= "SI";
	private String booleanFalseValue	= "NO";
	
	private String intestazione	= "";
	private String footerRight	= "";
	private String footerLeft	= "";
	private String footerMiddle	= "";
	
	private boolean printNumeroPagina	= true;
	private boolean printDataReport		= true;
	
	private Rectangle pageSize = PageSize.A4;
	
	private Color coloreIntestazione	= new Color( 114, 159, 207 );
	private Color coloreFooter			= Color.black;
	private Color coloreTesto			= Color.black;

	private Color		coloreSfondoIntestazioneTabella	= new Color( 114, 159, 207 );
	private BaseColor	colorePari						= new BaseColor( 226, 226, 226 );
	private BaseColor	coloreDispari					= BaseColor.WHITE;
	
	private BaseFont fontIntestazione	= null;
	private BaseFont fontFooter			= null;
	
	private Font fontTabellaIntestazione	= null;
	private Font fontTabellaBody			= null;
	
	private int cellaTabAllineamentoOrizzontale	= Element.ALIGN_CENTER;
	
	public byte[] 					bytes	= null;
	private ByteArrayOutputStream	baos	= null;
	private PdfReader				reader	= null;
	private PdfStamper				stamper	= null;
	
	
	/**
	 * Crea un nuovo oggetto PdfReport.<br/>
	 */
	public PdfReport()
	{
		try
		{
			fontIntestazione		= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
			fontFooter				= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
			fontTabellaIntestazione	= new Font( FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.WHITE );
			fontTabellaBody			= new Font( FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	public PdfReport(byte[] bytes)
	{
		try
		{
			fontIntestazione		= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
			fontFooter				= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
			fontTabellaIntestazione	= new Font( FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.WHITE );
			fontTabellaBody			= new Font( FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK );
			this.bytes = bytes;
			baos	= new ByteArrayOutputStream();
			reader	= new PdfReader( this.bytes );
			stamper	= new PdfStamper( reader, baos );
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	
	public byte[] render()
	{
		byte[] frontespizio	= renderFrontespizio();
		byte[] dettaglio	= renderItems();
		
		return PdfUtil.join( frontespizio, dettaglio );
	}
	
	/**
	 * Genera il Pdf contenente l'elenco di item passato col metodo "setItems".<br/>
	 * Se non sono state definite le colonne da rappresentare ( addColonna ) vengono visualizzate le proprietà dei bean.
	 * @return
	 */
	public byte[] renderItems()
	{
		byte[] ret = null;
		
		try
		{
			if( items != null )
			{//generazione pdf con tabella
				setupColonne();
				float[]					sizes	= getColumnSizes();
				ByteArrayOutputStream	baos	= new ByteArrayOutputStream();
				
				Document	document	= new Document( new Rectangle( getDocumentWidth(), getDocumentHeight() ) );
				PdfWriter	writer		= PdfWriter.getInstance( document, baos );
				
				document.open();
				
				PdfContentByte	cb			= writer.getDirectContent();
				PdfPTable		table		= null;
				int				totPagine	= calcolaNumeroPagine( items.size(), elementiPerPagina );
				int				currPag		= 0;
				//Indica se si deve andare su una nuova pagina causa capienza pagina
				boolean			newPage		= true;
				
				for( int i = 0; i < items.size(); i++ )
				{
					//Se siamo arrivati al numero di elementi stabiliti per pagina 
					//oppure si deve andare alla nuova pagina perchè non c'è più spazio nella pagina vecchia
					if( ((i % elementiPerPagina) == 0 && !elementiContigui) || newPage)
					{
						newPage = false;
						document.newPage();
						++currPag;
	
						//intestazione
						cb.beginText();
						cb.setFontAndSize( fontIntestazione, 8 );
						cb.setRGBColorFill( coloreIntestazione.getRed(), coloreIntestazione.getGreen(), coloreIntestazione.getBlue() );
						//in alto a sinistra
						if( printDataReport )
						{
							cb.showTextAligned( PdfContentByte.ALIGN_LEFT, "report generato il " + data(), margineLeft, getDocumentHeight() - margineTop, 0 );
						}
						//in alto al centro
						cb.showTextAligned( PdfContentByte.ALIGN_CENTER, intestazione, getDocumentWidth() / 2, getDocumentHeight() - margineTop, 0 );
						//in alto a destra
						if( printNumeroPagina )
						{
							cb.showTextAligned( PdfContentByte.ALIGN_RIGHT, "Pagina " + currPag + " di " + totPagine, getDocumentWidth() - margineRight, getDocumentHeight() - margineTop, 0 );
						}
						cb.endText();
						
						//footer
						cb.beginText();
						cb.setFontAndSize( fontFooter, 8 );
						cb.setRGBColorFill( coloreFooter.getRed(), coloreFooter.getGreen(), coloreFooter.getBlue() );
						//in basso a sinistra
						cb.showTextAligned( PdfContentByte.ALIGN_LEFT, footerLeft, margineLeft, margineBottom, 0 );
						//in basso al centro
						cb.showTextAligned( PdfContentByte.ALIGN_CENTER, footerMiddle, getDocumentWidth() / 2, margineBottom, 0 );
						//in basso a destra
						cb.showTextAligned( PdfContentByte.ALIGN_RIGHT, footerRight, getDocumentWidth() - margineRight, margineBottom, 0 );
						cb.endText();
						
						//creazione tabella e relativa intestazione
						table = new PdfPTable( sizes );
						table.setTotalWidth( getDocumentWidth() - ( margineLeft + margineRight ) );
				        table.getDefaultCell().setBorderWidth( 0 );
				        table.getDefaultCell().setHorizontalAlignment( cellaTabAllineamentoOrizzontale );
				        table.getDefaultCell().setBackgroundColor( 
				        		new BaseColor( coloreSfondoIntestazioneTabella.getRed(), coloreSfondoIntestazioneTabella.getGreen(), coloreSfondoIntestazioneTabella.getBlue() ) );
				        
				        for( int j = 0; j < colonne.size(); j++  )
				        {
				        	table.addCell( new Phrase( colonne.get( j ).getLabel(), fontTabellaIntestazione ) );
				        }

					}
				        
					if( (i % 2) == 1 ) //riga pari per chi conta iniziando da 1
					{
				        table.getDefaultCell().setBackgroundColor( colorePari );
					}
					else
					{
				        table.getDefaultCell().setBackgroundColor( coloreDispari );
					}
					
					//Creo la riga sulla tabella per ogni colonna
					for( int j = 0; j < colonne.size(); j++  )
					{
						table.addCell( new Phrase( getBeanProperty( items.get( i ), colonne.get( j ) ), fontTabellaBody ) );
					}
					//Se la riga non entra nella pagina allora la cancello e sarà scritta al successivo step del for e creo la nuova pagina
					if(!lastRowInPage(table))
					{	
						table.deleteLastRow();
						table.writeSelectedRows( 0, -1, margineLeft, (getDocumentHeight() - margineTop) * 0.98f, cb );
						table = null;
						//Rifaccio lo step
						i--;
						newPage = true;
					}
						
					//Se è l'ultima riga per questa pagina
					if( ((i + 1) % elementiPerPagina) == 0 && !elementiContigui) 
					{
			        	table.writeSelectedRows( 0, -1, margineLeft, (getDocumentHeight() - margineTop) * 0.98f, cb );
			        	table = null;
					}
				}
				
				 //per l'ultima pagina potrei nn aver scritto ancora la tabella
				if( table != null )
				{
					table.writeSelectedRows( 0, -1, margineLeft, (getDocumentHeight() - margineTop) * 0.98f, cb );
				}
				
				if( items.size() > 0 )
				{
					document.close();
				}
				
				ret = baos.toByteArray();
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
		return ret;
	}

	private String getBeanProperty( Object object, Colonna colonna )
	{
		String ret = "";
		
		try
		{
			Object temp = PropertyUtils.getProperty( object, colonna.getName() );
			if( isData( temp ) )
			{
				ret = sdfToPrint.format( temp );
			}
			else if( isBoolean( temp ) )
			{
				ret = ((Boolean)temp) ? (booleanTrueValue) : (booleanFalseValue);
			}
			else if( temp != null )
			{
				ret = temp.toString();
			}
			
			if( colonna.getMaxlen() > 0 && ret != null && ret.length() > colonna.getMaxlen() )
			{
				ret = ret.substring( 0, colonna.getMaxlen() );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return ret;
	}

	private float[] getColumnSizes()
	{
		float[] ret = new float[ colonne.size() ];
		
		for( int i = 0; i < colonne.size(); i++ )
		{
			ret[ i ] = colonne.get( i ).size;
		}
		
		return ret;
	}

	@SuppressWarnings("unchecked")
	private void setupColonne() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		if( colonne.size() == 0 && items.size() > 0 )
		{
			Object s = items.get( 0 );
			Map<String, Object> map = BeanUtils.describe( s );
			Set<String> chiavi = map.keySet();
			for( String chiave: chiavi )
			{
				this.addColonna( chiave, chiave, 1f, -1 );
			}
		}
	}

	private String data()
	{
		String ret = null;
		
		ret = sdfToPrint.format( data );
		
		return ret;
	}

	/**
	 * Genera Il frontespizio sulla base dei filtri e degli elementi di riepilogo settati.
	 * @return
	 */
	public byte[] renderFrontespizio()
	{
		byte[] ret = null;
		
		if( (filtri.size() > 0) || (riepilogo.size() > 0) )
		{
			//generazione pdf con filtri ricerca e riepilogo risultati
			try
			{
				ByteArrayOutputStream	baos	= new ByteArrayOutputStream();
				
				Document	document	= new Document( new Rectangle( getDocumentWidth(), getDocumentHeight() ) );
				PdfWriter	writer		= PdfWriter.getInstance( document, baos );
				
				document.open();
				
				float riepilogoOffset = 0;
				
				{
					PdfContentByte	cb			= writer.getDirectContent();
					PdfPTable		table		= null;
					
					document.newPage();
					
					//intestazione
					cb.beginText();
					cb.setFontAndSize( fontIntestazione, 8 );
					cb.setRGBColorFill( coloreIntestazione.getRed(), coloreIntestazione.getGreen(), coloreIntestazione.getBlue() );
					//in alto a sinistra
					cb.showTextAligned( PdfContentByte.ALIGN_LEFT, "report generato il " + data(), margineLeft, getDocumentHeight() - margineTop, 0 );
					//in alto al centro (un po più grande ed in basso)
					cb.setFontAndSize( fontIntestazione, 12 );
					cb.setRGBColorFill( coloreIntestazione.getRed(), coloreIntestazione.getGreen(), coloreIntestazione.getBlue() );
					cb.showTextAligned( PdfContentByte.ALIGN_CENTER, intestazione, getDocumentWidth() / 2, getDocumentHeight() - (margineTop * 2), 0 );
					cb.endText();
					
					//footer
					cb.beginText();
					cb.setFontAndSize( fontFooter, 8 );
					cb.setRGBColorFill( coloreFooter.getRed(), coloreFooter.getGreen(), coloreFooter.getBlue() );
					//in basso a sinistra
					cb.showTextAligned( PdfContentByte.ALIGN_LEFT, footerLeft, margineLeft, margineBottom, 0 );
					//in basso al centro
					cb.showTextAligned( PdfContentByte.ALIGN_CENTER, footerMiddle, getDocumentWidth() / 2, margineBottom, 0 );
					//in basso a destra
					cb.showTextAligned( PdfContentByte.ALIGN_RIGHT, footerRight, getDocumentWidth() - margineRight, margineBottom, 0 );
					cb.endText();
					
					//tabella filtri
					if( filtri.size() > 0 )//creazione tabella e relativa intestazione
					{
						float[] filtriSizes = { 1f };
						table = new PdfPTable( filtriSizes );
						table.setTotalWidth( ( getDocumentWidth() - ( margineLeft + margineRight ) ) / 2 );
				        table.getDefaultCell().setBorderWidth( 0 );
				        table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
				        table.getDefaultCell().setBackgroundColor( 
				        		new BaseColor( coloreSfondoIntestazioneTabella.getRed(), coloreSfondoIntestazioneTabella.getGreen(), coloreSfondoIntestazioneTabella.getBlue() ) );
				        table.addCell( new Phrase( "Filtri", fontTabellaIntestazione ) );
				        
				        table.getDefaultCell().setBackgroundColor( coloreDispari );
				        for( String filtro: filtri )
				        {
				        	table.addCell( new Phrase( filtro, fontTabellaBody ) );
				        }
	
			        	table.writeSelectedRows( 0, -1, ( getDocumentWidth() - table.getTotalWidth() )/ 2, (getDocumentHeight() - (3 * margineTop) ) * 0.98f, cb );
			        	riepilogoOffset = table.getTotalHeight() + margineTop;
			        	table = null;
					}

					//tabella risultati di riepilogo
					if( riepilogo.size() > 0 )
					{
						float[] riepilogoSizes = {0.1f, 1f, 0.5f, 1f, 0.1f};
						table = new PdfPTable( riepilogoSizes );
						table.setTotalWidth( ( getDocumentWidth() - ( margineLeft + margineRight ) ) / 2 );
				        table.getDefaultCell().setBorderWidth( 0 );
				        table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
				        table.getDefaultCell().setBackgroundColor( 
				        		new BaseColor( coloreSfondoIntestazioneTabella.getRed(), coloreSfondoIntestazioneTabella.getGreen(), coloreSfondoIntestazioneTabella.getBlue() ) );

				        table.addCell( new Phrase( " ", fontTabellaIntestazione ) );
				        table.addCell( new Phrase( " ", fontTabellaIntestazione ) );
				        table.addCell( new Phrase( "Riepilogo", fontTabellaIntestazione ) );
				        table.addCell( new Phrase( " ", fontTabellaIntestazione ) );
				        table.addCell( new Phrase( " ", fontTabellaIntestazione ) );
				        
				        table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_RIGHT );
				        for( int i = 0; i < riepilogo.size(); i++ )
				        {
				        	RiepilogoItem ri = riepilogo.get( i );
				        	if( (i % 2) == 1 )
				        	{
				        		table.getDefaultCell().setBackgroundColor( coloreDispari );
				        	}
				        	else
				        	{
						        table.getDefaultCell().setBackgroundColor( colorePari );
				        	}
					        
				        	table.addCell( new Phrase( " ", fontTabellaBody ) );
				        	table.addCell( new Phrase( ri.getChiave(), fontTabellaBody ) );
				        	table.addCell( new Phrase( " ", fontTabellaBody ) );
				        	table.addCell( new Phrase( ri.getValore(), fontTabellaBody ) );
				        	table.addCell( new Phrase( " ", fontTabellaBody ) );
				        }
	
			        	table.writeSelectedRows( 0, -1, ( getDocumentWidth() - table.getTotalWidth() )/ 2, (getDocumentHeight() - (3 * margineTop) - riepilogoOffset ) * 0.98f, cb );
			        	table = null;
					}

				}

				document.close();
				
				ret = baos.toByteArray();
				
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			
		}
		
		return ret;
	}

	public Color getColoreIntestazione() {
		return coloreIntestazione;
	}

	public void setColoreIntestazione(Color coloreIntestazione) {
		this.coloreIntestazione = coloreIntestazione;
	}

	public Color getColoreFooter() {
		return coloreFooter;
	}

	public void setColoreFooter(Color coloreFooter) {
		this.coloreFooter = coloreFooter;
	}

	public Color getColoreTesto() {
		return coloreTesto;
	}

	public void setColoreTesto(Color coloreTesto) {
		this.coloreTesto = coloreTesto;
	}

	public Color getColoreSfondoIntestazioneTabella() {
		return coloreSfondoIntestazioneTabella;
	}

	public void setColoreSfondoIntestazioneTabella(
			Color coloreSfondoIntestazioneTabella) {
		this.coloreSfondoIntestazioneTabella = coloreSfondoIntestazioneTabella;
	}

	public BaseColor getColorePari() {
		return colorePari;
	}

	public void setColorePari(BaseColor colorePari) {
		this.colorePari = colorePari;
	}

	public BaseColor getColoreDispari() {
		return coloreDispari;
	}

	public void setColoreDispari(BaseColor coloreDispari) {
		this.coloreDispari = coloreDispari;
	}

	public BaseFont getFontIntestazione() {
		return fontIntestazione;
	}

	public boolean isPrintNumeroPagina() {
		return printNumeroPagina;
	}

	/**
	 * Default: true
	 * @param printNumeroPagina
	 */
	public void setPrintNumeroPagina(boolean printNumeroPagina) {
		this.printNumeroPagina = printNumeroPagina;
	}

	public boolean isPrintDataReport() {
		return printDataReport;
	}

	/**
	 * Default: true
	 * @param printDataReport
	 */
	public void setPrintDataReport(boolean printDataReport) {
		this.printDataReport = printDataReport;
	}

	public void setFontIntestazione(BaseFont fontIntestazione) {
		this.fontIntestazione = fontIntestazione;
	}

	public BaseFont getFontFooter() {
		return fontFooter;
	}

	public void setFontFooter(BaseFont fontFooter) {
		this.fontFooter = fontFooter;
	}

	public Font getFontTabellaIntestazione() {
		return fontTabellaIntestazione;
	}

	public void setFontTabellaIntestazione(Font fontTabellaIntestazione) {
		this.fontTabellaIntestazione = fontTabellaIntestazione;
	}

	public Font getFontTabellaBody() {
		return fontTabellaBody;
	}

	public void setFontTabellaBody(Font fontTabellaBody) {
		this.fontTabellaBody = fontTabellaBody;
	}

	public int getCellaTabAllineamentoOrizzontale() {
		return cellaTabAllineamentoOrizzontale;
	}

	/**
	 * Imposta l'allineamento orizzontale delle celle delle tabelle generate con renderItems.
	 * Default: Element.ALIGN_CENTER
	 * @param cellaTabAllineamentoOrizzontale Esempio: Element.ALIGN_LEFT
	 */
	public void setCellaTabAllineamentoOrizzontale(
			int cellaTabAllineamentoOrizzontale) {
		this.cellaTabAllineamentoOrizzontale = cellaTabAllineamentoOrizzontale;
	}

	/**
	 * 
	 * @param nome Nome della proprietà da rappresentare.
	 * @param label Nome della colonna della tabella.
	 * @param size Dimensione in larghezza della colonna (in proporzione alle altre colonne).
	 * @param maxlen Lunghezza massima (in caratteri) del valore da rappresentare oltre la quale verrà troncato.
	 * -1 equivale a nessuna lunghezza massima.
	 */
	public void addColonna( String nome, String label, float size, int maxlen )
	{
		colonne.add( new Colonna( nome, label, size, maxlen ) );
	}

	/**
	 * Gli elementi aggiunti con questo metodo vengono stampati in una tabella del frontespizio, sotto
	 * l'eventuale tabella dei filtri.
	 * @param chiave Elemento sinistro della tabella.
	 * @param valore Elemento destro della tabella.
	 */
	public void addRiepilogoItem( String chiave, String valore )
	{
		riepilogo.add( new RiepilogoItem( chiave, valore ) );
	}
	
	/**
	 * Gli elementi aggiunti con questo metodo vengono stampati in una tabella del frontespizio,
	 * sopra l'eventuale tabella di riepilogo.
	 * @param filtro
	 */
	public void addFiltro( String filtro )
	{
		filtri.add( filtro );
	}

	public boolean getOrientamento() {
		return orientamento;
	}


	/**
	 * Imposta l'orientamento del documento generato.
	 * valore di default PdfReport.VERTICALE.
	 * @param orientamento Valori ammessi: PdfReport.VERTICALE, PdfReport.ORIZZONTALE.
	 */
	public void setOrientamento(boolean orientamento) {
		this.orientamento = orientamento;
	}


	public List<Object> getItems() {
		return items;
	}

	/**
	 * Imposta la lista di items da rappresentare
	 * @param items La lista di bean da cui prendere le proprietà (anche annidate) da rappresentare. Funziona anche con i bean dinamici (DynaBean). 
	 */
	public void setItems(List<Object> items) {
		this.items = items;
	}


	public float getMargineTop() {
		return margineTop;
	}


	public void setMargineTop(float margineTop) {
		this.margineTop = margineTop;
	}


	public float getMargineLeft() {
		return margineLeft;
	}


	public void setMargineLeft(float margineLeft) {
		this.margineLeft = margineLeft;
	}


	public float getMargineBottom() {
		return margineBottom;
	}


	public void setMargineBottom(float margineBottom) {
		this.margineBottom = margineBottom;
	}


	public float getMargineRight() {
		return margineRight;
	}


	public void setMargineRight(float margineRight) {
		this.margineRight = margineRight;
	}

	public Date getData() {
		return data;
	}

	/**
	 * @param data Data del documento. Di default la data odierna.
	 */
	public void setData(Date data) {
		this.data = data;
	}

	public String getIntestazione() {
		return intestazione;
	}

	/**
	 * @param intestazione Intestazione del documento. Di default "".
	 */
	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}
	
	public String getDataFormato() {
		return dataFormato;
	}

	/**
	 * @param dataFormato Pattern utilizzato per la rappresentazione dele date. Di default "dd/MM/yyyy".
	 */
	public void setDataFormato(String dataFormato) {
		this.dataFormato = dataFormato;
		this.sdfToPrint = new SimpleDateFormat( dataFormato );
	}

	public int getElementiPerPagina() {
		return elementiPerPagina;
	}

	/**
	 * @param elementiPerPagina Numero di item da rappresentare per ogni pagina. Di default 15.
	 */
	public void setElementiPerPagina(int elementiPerPagina) {
		this.elementiPerPagina = elementiPerPagina;
	}
	
	public boolean getElementiContigui() {
		return elementiContigui;
	}

	/**
	 * @param elementiContigui Se elementi devo essere contigui o suddivisi per pagina. Di default true.
	 */
	public void setElementiContigui(boolean elementiContigui) {
		this.elementiContigui = elementiContigui;
	}

	public Rectangle getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize Dimensioni del documento. Di default PageSize.A4. 
	 */
	public void setPageSize(Rectangle pageSize) {
		this.pageSize = pageSize;
	}
	
	public float getDocumentWidth()
	{
		float ret = pageSize.getWidth();
		
		if( orientamento == ORIZZONTALE )
		{
			ret = pageSize.getHeight();
		}
		
		return ret;
	}
	
	public float getDocumentHeight()
	{
		float ret = pageSize.getHeight();
		
		if( orientamento == ORIZZONTALE )
		{
			ret = pageSize.getWidth();
		}
		
		return ret;
	}
	
	public String getFooterRight() {
		return footerRight;
	}

	public void setFooterRight(String footerRight) {
		this.footerRight = footerRight;
	}

	public String getFooterLeft() {
		return footerLeft;
	}

	public void setFooterLeft(String footerLeft) {
		this.footerLeft = footerLeft;
	}

	public String getFooterMiddle() {
		return footerMiddle;
	}

	public void setFooterMiddle(String footerMiddle) {
		this.footerMiddle = footerMiddle;
	}

	public String getBooleanTrueValue() {
		return booleanTrueValue;
	}

	/**
	 * @param booleanTrueValue Valore da utilizzare per rappresentare le proprietà di tipo booleano con valore true.
	 * Di default "SI"
	 */
	public void setBooleanTrueValue(String booleanTrueValue) {
		this.booleanTrueValue = booleanTrueValue;
	}

	public String getBooleanFalseValue() {
		return booleanFalseValue;
	}

	/**
	 * @param booleanFalseValue Valore da utilizzare per rappresentare le proprietà di tipo booleano con valore false.
	 * Di default "NO"
	 */
	public void setBooleanFalseValue(String booleanFalseValue) {
		this.booleanFalseValue = booleanFalseValue;
	}

	private static int calcolaNumeroPagine(int size, int page_elem)
	{
		return (int)Math.ceil( (size * 1.0) / page_elem );
	}
	
	private class RiepilogoItem
	{
		private String chiave;
		private String valore;
		
		public RiepilogoItem()
		{
			
		}
		
		public RiepilogoItem( String chiave, String valore )
		{
			this.chiave = chiave;
			this.valore = valore;
		}
		
		public String getChiave() {
			return chiave;
		}
		public void setChiave(String chiave) {
			this.chiave = chiave;
		}
		public String getValore() {
			return valore;
		}
		public void setValore(String valore) {
			this.valore = valore;
		}
	}

	private class Colonna
	{
		private float	size	= 0f;
		private String	name	= null;
		private String	label	= null;
		private int		maxlen	= -1;
		
		public Colonna( String name, String label, float size, int maxlen )
		{
			this.name	= name;
			this.label	= label;
			this.size	= size;
			
			if( maxlen <= 0 )
			{
				this.maxlen = -1;
			}
			else
			{
				this.maxlen	= maxlen;
			}
		}

		public float getSize() {
			return size;
		}

		public String getName() {
			return name;
		}

		public String getLabel() {
			return label;
		}

		public int getMaxlen() {
			return maxlen;
		}
	}

	/**
	 * Utilizza la tecnica del form filling per compilare un documento Pdf preesistente utilizzato come template<br/>
	 * E' possibile concatenare il risultato di questo metodo ad un altro Pdf (eg. una lista di bean) tramite
	 * il metodo it.us.web.util.PdfUtil.join( byte[] primaParte, byte[] secondaParte ): byte[].<br/>
	 * Le proprieta' di tipo data vengono convertite secondo il pattern "dd/MM/yyyy".<br/>
	 * Le proprieta' di tipo boolean vengono rappresentate con "X" se true con "" se false.<br/>
	 * Le proprieta' di tipo Image vengono posizionate all'interno del campo adattando la dimensione dell'immagine.<br/>
	 * Le proprieta' di tipo Barcode128 vengono posizionate all'interno del campo.<br/>
	 * Tutte gli altri tipi di proprieta' vengono rappresentati tramite il loro metodo "toString".<br/>
	 * Per rappresentazioni alternative di una proprietà utilizzare l'oggetto "mappaProprietaAddizionali" fornendo per la data proprietà
	 * il valore nella forma desiderata.
	 * @param template InputStream del documento originale
	 * @param bean Bean da cui prelevare le proprieta' (anche annidate) da mappare nei "field" del documento template.<br/>
	 * Può essere null.
	 * @param mappaProprietaAddizionali Mappa di proprieta' che integrano (e sovrascrivono) quelle ricavate da "bean"<br/>
	 * Può essere null.
	 * @return Array di byte rappresentante il documento compilato e con i "field" resi non più editabili.
	 */
	public static byte[] fillDocument( InputStream template, Object bean, Map<String, Object> mappaProprietaAddizionali )
	{
		byte[] ret = null;
		
		try
		{
			ByteArrayOutputStream	baos	= new ByteArrayOutputStream();
			PdfReader				reader	= new PdfReader( template );
			PdfStamper				stamper	= new PdfStamper( reader, baos );
			AcroFields				fields	= stamper.getAcroFields();
			
			Set<String> acroFieldsSet = fields.getFields().keySet();
			//prima setto tutti i valori dalla map
			//poi setto tutti i valori dal bean
			
			for( String field: acroFieldsSet )
			{
				Object objectToSet = null;
				
				if( mappaProprietaAddizionali != null )//controllo se c'è la proprietà nella mappa passata come parametro
				{
					objectToSet = mappaProprietaAddizionali.get( field );
				}
				
				if( objectToSet == null && bean != null )//se non presenre nella mappa controllo nel bean
				{
					try
					{
						objectToSet = PropertyUtils.getProperty( bean, field );
					}
					catch ( Exception e )
					{
						//nothing to do
					}
				}
				
				if( objectToSet != null )//se ho trovato la proprietà la vado a settare nel pdf
				{
					String stringToSet = null;
					if( isData( objectToSet ) )
					{
						stringToSet = DateUtils.dataToString( (Date)objectToSet );
					}
					else if( isBoolean( objectToSet ) )
					{
						stringToSet = ((Boolean)objectToSet) ? ("X") : ("");
					}
					else if( objectToSet instanceof Image ) // gestisco le immagini
					{
						PdfContentByte 	canvas 					= stamper.getOverContent(1);
						FieldPosition 	imgFieldPosition 		= fields.getFieldPositions(field).get(0);
						Rectangle 		rect 					= imgFieldPosition.position;
						
						((Image)objectToSet).scaleToFit(rect.getWidth(), rect.getHeight());
						((Image)objectToSet).setAbsolutePosition(rect.getLeft(), rect.getBottom());
						
						canvas.addImage( (Image)objectToSet );
						stringToSet = "";
					}
					else if( objectToSet instanceof Barcode128 ) // gestisco i codici a barre
					{
						PdfContentByte 	canvas 					= stamper.getOverContent(1);
						FieldPosition 	bcFieldPosition 		= fields.getFieldPositions(field).get(0);
						Rectangle 		rect 					= bcFieldPosition.position;
						
						Image barImage = ((Barcode128)objectToSet).createImageWithBarcode(canvas, null, null);
						barImage.scaleToFit(rect.getWidth(), rect.getHeight());
						barImage.setAbsolutePosition(rect.getLeft(), rect.getBottom());
						canvas.addImage( barImage );
						
						stringToSet = "";
					}
					else
					{
						stringToSet = objectToSet.toString();
					}
					
					fields.setField( field, stringToSet );
					fields.setFieldProperty( field, "flags", PdfAnnotation.FLAGS_PRINT, null );
				}
			}
			

		    stamper.setFormFlattening( true );
		    stamper.close();
			
		    ret = baos.toByteArray();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return ret;
	}
	
	
	
	public static Document htmlToPdf( String html) throws DocumentException, IOException
	{
		String fileNameWithPath = "/Users/cecco/Desktop/pdf2.pdf";
	
	    com.itextpdf.text.Document document = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.A4);
	    FileOutputStream fos = new FileOutputStream(fileNameWithPath);
	    com.itextpdf.text.pdf.PdfWriter pdfWriter = com.itextpdf.text.pdf.PdfWriter.getInstance(document, fos);
	
	    document.open();
	
	    document.addAuthor("Myself");
	    document.addSubject("My Subject");
	    document.addCreationDate();
	    document.addTitle("My Title");
	
	    com.itextpdf.text.html.simpleparser.HTMLWorker htmlWorker = new com.itextpdf.text.html.simpleparser.HTMLWorker(document);
	    htmlWorker.parse(new StringReader(html));
	
	    document.close();
	    fos.close();
	    return document;
	}
    
	
	/**
	 * Aggiunge un'immagine a un PDF nella posizione specificata dalle coordinate x,y
	 * @param img l'immagine da inserire nel pdf in formato Image
	 * @param input Array di byte conentente il pdf al quale aggiungere l'immagine
	 * @param x coordinate che definiscono la posizione assoluta dell'immagine
	 * @param y coordinate che definiscono la posizione assoluta dell'immagine
	 * @param width larghezza assoluta dell'immagine inserita
	 * @param height altezza assoluta dell'immagine inserita
	 * @param scale percentuale di scala dell'immagine inserita, sovrascrive i parametri di altezza e larghezza
	 * @return Array di byte rappresentante il documento con l'immagine aggiunta
	 */
	public static byte[] addImageToPdf( Image img, byte[] input, float x, float y, float width, float height, float scale  )
	{
		byte[] ret = input;
		
		try
		{
			
			ByteArrayOutputStream	baos	= new ByteArrayOutputStream();
			PdfReader				reader	= new PdfReader( input );
			PdfStamper				stamper	= new PdfStamper( reader, baos );
			
			PdfContentByte 			canvas 	= stamper.getOverContent(1);
			
			float	finalWidth 	= width;
			float 	finalHeight = height;
			
			if ( scale != 0f )
			{
				finalWidth = scale * (img.getWidth() / 100f);
				finalHeight = scale * (img.getHeight() / 100f);
			}
			
			if ( finalWidth <= 0f ) 
			{
				finalWidth = img.getWidth();
			}
			
			if ( finalHeight <= 0f ) 
			{
				finalHeight = img.getHeight();
			}
			
			canvas.addImage(img, finalWidth, 0.0f, 0.0f, finalHeight, x, y);
			canvas.newlineText();
			canvas.newlineText();
			canvas.newlineText();
			canvas.newlineText();
		    stamper.close();
			
		    ret = baos.toByteArray();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		return ret;
	}
	
	/**
	 * Aggiunge un'immagine a un PDF nella posizione specificata dalle coordinate x,y
	 * @param img l'immagine da inserire nel pdf in formato Image
	 * @param input Array di byte conentente il pdf al quale aggiungere l'immagine
	 * @param x coordinate che definiscono la posizione assoluta dell'immagine
	 * @param y coordinate che definiscono la posizione assoluta dell'immagine
	 * @param width larghezza assoluta dell'immagine inserita
	 * @param height altezza assoluta dell'immagine inserita
	 * @param scale percentuale di scala dell'immagine inserita, sovrascrive i parametri di altezza e larghezza
	 * @return Array di byte rappresentante il documento con l'immagine aggiunta
	 */
	public void addBarcodeToPdf( String text, float x, float y, float width, float height, float scale  )
	{
		
		try
		{
			PdfContentByte 			canvas 	= stamper.getOverContent(2);
			
			float	finalWidth 	= width;
			float 	finalHeight = height;
			
			
			Barcode128 barCode = new Barcode128();
			barCode.setCode(text);
			Image barImage = barCode.createImageWithBarcode(canvas, null, null);
			barImage.scaleToFit(width, height);
			barImage.setAbsolutePosition(x, y);
			canvas.addImage( barImage );
			
			if ( scale != 0f )
			{
				finalWidth = scale * (barImage.getWidth() / 100f);
				finalHeight = scale * (barImage.getHeight() / 100f);
			}
			
			if ( finalWidth <= 0f ) 
			{
				finalWidth = barImage.getWidth();
			}
			
			if ( finalHeight <= 0f ) 
			{
				finalHeight = barImage.getHeight();
			}
			
			canvas.addImage(barImage, finalWidth, 0.0f, 0.0f, finalHeight, x, y);
		    stamper.close();
			
	    	bytes = new byte[baos.toByteArray().length];
	    	bytes = baos.toByteArray();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static boolean isBoolean(Object object)
	{
		return (object != null) && (object instanceof Boolean);
	}

	private static boolean isData( Object object )
	{
		boolean ret = false;
		if( object != null &&
				( object instanceof Timestamp
				|| object instanceof Date
				|| object instanceof java.sql.Date ) )
		{
			ret = true;
		}
		
		return ret;
	}

	/**
	 * Metodo cheritorna l'altezza ancora restante nella pagina per poter scrivere
	 */
	private float getRemainingHeigh(PdfPTable table)
	{
		return getDocumentHeight()-margineLeft-margineBottom-table.getTotalHeight();
	}
	
	/**
	 * Metodo che ritorna se l'ultima riga della tabella entra nella pagina
	 */
	private boolean lastRowInPage(PdfPTable table)
	{
		return table.getRowHeight(table.getRows().size())<getRemainingHeigh(table);
	}
	
	
	
	/**
	 * Metodo per resettare l'oggetto PdfReport prima di fornire un nuovo set di item per eseguire un nuovo report.
	 */
	public void reset()
	{
		orientamento = VERTICALE;
		
		items		= null;
		colonne		= new ArrayList<Colonna>();
		filtri		= new ArrayList<String>();
		riepilogo	= new ArrayList<RiepilogoItem>();
		
		//non resetto le impostazioni di formattazione etc
//		elementiPerPagina	= 15;
//		
//		margineTop		= 30f;
//		margineLeft		= 30f;
//		margineBottom	= 30f;
//		margineRight	= 30f;
//		
//		data		= new Date();
//		dataFormato	= "dd/MM/yyyy";
//		sdfToPrint	= new SimpleDateFormat( dataFormato );
//		
//		booleanTrueValue	= "SI";
//		booleanFalseValue	= "NO";
//		
//		intestazione	= "";
//		footerRight		= "";
//		footerLeft		= "";
//		footerMiddle	= "";
//		
//		pageSize = PageSize.A4;
//		
//		coloreIntestazione	= new Color( 114, 159, 207 );
//		coloreFooter		= Color.black;
//		coloreTesto			= Color.black;
//
//		coloreSfondoIntestazioneTabella	= new Color( 114, 159, 207 );
//		colorePari						= new BaseColor( 226, 226, 226 );
//		coloreDispari					= BaseColor.WHITE;
//		
//		fontIntestazione	= null;
//		fontFooter			= null;
//		
//		fontTabellaIntestazione	= null;
//		fontTabellaBody			= null;
	}
	
	
	public void newPage()
	{
		stamper.insertPage(2, reader.getPageSize(1));
	}
	
	public byte[] getBytes()
	{
		return bytes;
	}
}
