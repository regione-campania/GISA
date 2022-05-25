/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.documentale;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import it.us.web.action.GenericAction;
import it.us.web.bean.vam.Immagine;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.properties.Application;

public class DownloadImg extends GenericAction
{
	
	public DownloadImg() {		
	}
	
	@Override
	public void can() throws AuthorizationException
	{
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	@Override
	public void execute() throws Exception
	{
    	
		String codDocumento = stringaFromRequest("codDocumento");
		String estensione = stringaFromRequest("estensione");
		String nome = stringaFromRequest("nomeDocumento");
		
    	boolean		thumb	= booleanoFromRequest( "thumb" );
    	boolean		inline	= booleanoFromRequest( "inline" );
    	
    	File		f		= null;
    	f = new File( Application.get( "UPLOAD_TMP_FOLDER" ) + codDocumento + ".thumb" );
		String download_url = "http://" + ((HashMap<String, InetAddress>)req.getServletContext().getAttribute("hosts")).get("srvDOCUMENTALEL").getHostAddress() + ":" + Application.get("DOCUMENTALE_PORT") + "/" + Application.get("DOCUMENTALE_APPLICATION_NAME") + "/DownloadService?codDocumento="+codDocumento;
		URL url = new URL(download_url);
		FileUtils.copyURLToFile(url, f);
    	
		
		Immagine	img		= new Immagine();
		
		img.setContentType(estensione);
		img.setDisplayName(nome);
		
		
    	if( thumb )//se è richiesta una miniatura
    	{
    		//f = new File( Application.get( "UPLOAD_TMP_FOLDER" ) + img.getPathName() + ".thumb" );
    		if( !f.exists() )//se la miniatura non esiste
    		{
    			//crea miniatura
    			creaMiniatura( img );
    		//	f = new File( Application.get( "UPLOAD_TMP_FOLDER" ) + img.getPathName() + ".thumb" );
    		}
    	}
    	else
    	{
    	//	f = new File( Application.get( "UPLOAD_TMP_FOLDER" ) + img.getPathName() );
    	}
    	
    	InputStream	is		= new FileInputStream( f );
    	byte[]		bytes	= new byte[ (int)f.length() ];
        
    	int offset	= 0;
        int numRead	= 0;
        while( offset < bytes.length && ( numRead = is.read(bytes, offset, bytes.length-offset) ) >= 0)
        {
            offset += numRead;
        }
    	is.close();
    	
        res.setContentType( img.getContentType() );
        if( inline )
        {
        	res.setHeader( "Content-Disposition","inline; filename=\"" + img.getDisplayName() + "\";" );
        }
        else
        {
        	res.setHeader( "Content-Disposition","attachment; filename=\"" + img.getDisplayName() + "\";" );
        }
		
		OutputStream out = res.getOutputStream();
		out.write( bytes );
        out.flush();
        out.close();

        bytes = null;
        System.gc();
	}

	private static void creaMiniatura(Immagine img) throws ImageFormatException, IOException, InterruptedException
	{
		int largestDimension = 80;
		double scale;
		int sizeDifference;
		int originalImageLargestDim;

		Component component = new JPanel();
		MediaTracker mediaTracker = new MediaTracker(component);
		
		Image original = Toolkit.getDefaultToolkit().getImage( Application.get( "UPLOAD_TMP_FOLDER" ) + img.getPathName() );
		mediaTracker.addImage(original, 0);
		mediaTracker.waitForAll();
		
		if( original.getWidth(null) > original.getHeight(null) )
		{
			scale					= (double)largestDimension/(double)original.getWidth(null);
			sizeDifference			= original.getWidth(null) - largestDimension;
			originalImageLargestDim	= original.getWidth(null);
		}
		else
		{
			scale					= (double)largestDimension/(double)original.getHeight(null);
			sizeDifference			= original.getHeight(null) - largestDimension;
			originalImageLargestDim	= original.getHeight(null);
		}
		
		BufferedImage thumb = null;//new BufferedImage( largestDimension, largestDimension, BufferedImage.TYPE_INT_RGB );
		Graphics2D g2d;
		AffineTransform tx;
		if( scale < 1.0d ) //only scale if desired size is smaller than original
		{
			int numSteps = sizeDifference / 100;
			int stepSize = sizeDifference / numSteps;
			int stepWeight = stepSize/2;
			int heavierStepSize = stepSize + stepWeight;
			int lighterStepSize = stepSize - stepWeight;
			int currentStepSize, centerStep;
			double scaledW = original.getWidth(null);
			double scaledH = original.getHeight(null);
			if( numSteps % 2 == 1 ) //if there's an odd number of steps
				centerStep = (int)Math.ceil((double)numSteps / 2d); //find the center step
			else
				centerStep = -1; //set it to -1 so it's ignored later
			Integer intermediateSize = originalImageLargestDim, previousIntermediateSize = originalImageLargestDim;
			for(Integer i=0; i<numSteps; i++)
			{
				if(i+1 != centerStep) //if this isn't the center step
				{
					if(i == numSteps-1) //if this is the last step
					{
						//fix the stepsize to account for decimal place errors previously
						currentStepSize = previousIntermediateSize - largestDimension;
					}
					else
					{
						if(numSteps - i > numSteps/2) //if we're in the first half of the reductions
							currentStepSize = heavierStepSize;
						else
							currentStepSize = lighterStepSize;
					}
				}
				else //center step, use natural step size
				{                        
					currentStepSize = stepSize;
				}
				intermediateSize = previousIntermediateSize - currentStepSize;
				scale = (double)intermediateSize/(double)previousIntermediateSize;
				scaledW = (int)scaledW*scale;
				scaledH = (int)scaledH*scale;
				thumb = new BufferedImage((int)scaledW, (int)scaledH, BufferedImage.TYPE_INT_RGB);
				g2d = thumb.createGraphics();
				g2d.setBackground(Color.WHITE);
				g2d.clearRect(0, 0, thumb.getWidth(), thumb.getHeight());
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				tx = new AffineTransform();
				tx.scale(scale, scale);
				g2d.drawImage(original, tx, null);
				g2d.dispose();
				original = new ImageIcon(thumb).getImage();
				previousIntermediateSize = intermediateSize;
			}                
		}
		else
		{
			//just copy the original
			thumb = new BufferedImage(original.getWidth(null), original.getHeight(null), BufferedImage.TYPE_INT_RGB);
			g2d = thumb.createGraphics();
			g2d.setBackground(Color.WHITE);
			g2d.clearRect(0, 0, thumb.getWidth(), thumb.getHeight());
			tx = new AffineTransform();
			tx.setToIdentity(); //use identity matrix so image is copied exactly
			g2d.drawImage(original, tx, null);
			g2d.dispose();
		}
		//JPEG-encode the image and write to file.
		OutputStream os = new FileOutputStream( Application.get( "UPLOAD_TMP_FOLDER" ) + img.getPathName() + ".thumb" );
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
		encoder.encode( thumb );
		os.flush();
		os.close();
		
	}

}
