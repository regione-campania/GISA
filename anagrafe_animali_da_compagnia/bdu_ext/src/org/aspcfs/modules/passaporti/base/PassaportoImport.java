/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.passaporti.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.apps.transfer.reader.mapreader.Property;
import org.aspcfs.apps.transfer.reader.mapreader.PropertyMap;
import org.aspcfs.controller.ImportManager;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Import;
import org.aspcfs.utils.CFSFileReader;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.formatter.ContactNameFormatter;

import com.zeroio.iteam.base.FileItem;

public class PassaportoImport extends Import implements Runnable {
	

	  /**
		 * 
		 */

	 
	  private static final long serialVersionUID = 829996519812894123L;
	  public final static String fs = System.getProperty("file.separator");
	  private PropertyMap propertyMap = null;
	  private String filePath = null;
	  private int owner = -1;
	  private String ownerName = null;
	  private int userId = -1;
	  private int accessTypeId = -1;
	  private boolean lookupAccount = false;
	  private ImportManager manager = null;
	  private Connection db = null;
	  //private ConnectionElement connectionElement = null;
	  private File errorFile = null;
	  private BufferedWriter fos = null;
	  private FileItem fileItem = null;
	  private Thread importThread = null;
	  private boolean lead = false;
	  private int leadStatus = -1;
	  private int ownerSiteId = -1;
	  private static Logger logger = Logger.getLogger(org.aspcfs.modules.accounts.base.Organization.class);
	  static {
	    if (System.getProperty("DEBUG") != null) {
	      logger.setLevel(Level.DEBUG);
	    }
	  }
	  /**
	   * Constructor for the ContactImport object
	   */
	  public PassaportoImport() {
	  }

	  /**
	   * Constructor for the ContactImport object
	   * 
	   * @param db
	   *          Description of the Parameter
	   * @param importId
	   *          Description of the Parameter
	   * @throws SQLException
	   *           Description of the Exception
	   */
	  public PassaportoImport(Connection db, int importId) throws SQLException {
	    super(db, importId);
	  }

	  /**
	   * Sets the properties attribute of the ContactImport object
	   * 
	   * @param request
	   *          The new properties value
	   */
	  public void setProperties(HttpServletRequest request) {
	    if (request.getParameter("owner") != null) {
	      setOwner(request.getParameter("owner"));
	    }
	    if (request.getParameter("accessType") != null) {
	      setAccessTypeId(request.getParameter("accessType"));
	    }
	  }

	  
	  


	  /**
	   * Sets the owner attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new owner value
	   */
	  public void setOwner(int tmp) {
	    this.owner = tmp;
	  }

	  /**
	   * Sets the owner attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new owner value
	   */
	  public void setOwner(String tmp) {
	    this.owner = Integer.parseInt(tmp);
	  }

	  /**
	   * Gets the ownerName attribute of the ContactImport.java object
	   * 
	   * @return the ownerName
	   */
	  public String getOwnerName() {
	    return ownerName;
	  }

	  /**
	   * Sets the properties attribute of the ContactImport.java object
	   * 
	   * @param ownerName
	   *          the ownerName to set
	   */
	  public void setOwnerName(String ownerName) {
	    this.ownerName = ownerName;
	  }

	  /**
	   * Sets the accessTypeId attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new accessTypeId value
	   */
	  public void setAccessTypeId(int tmp) {
	    this.accessTypeId = tmp;
	  }

	  /**
	   * Sets the accessTypeId attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new accessTypeId value
	   */
	  public void setAccessTypeId(String tmp) {
	    this.accessTypeId = Integer.parseInt(tmp);
	  }

	  /**
	   * Sets the userId attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new userId value
	   */
	  public void setUserId(int tmp) {
	    this.userId = tmp;
	  }

	  /**
	   * Sets the userId attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new userId value
	   */
	  public void setUserId(String tmp) {
	    this.userId = Integer.parseInt(tmp);
	  }

	  /**
	   * Sets the lookupAccount attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new lookupAccount value
	   */
	  public void setLookupAccount(boolean tmp) {
	    this.lookupAccount = tmp;
	  }

	  /**
	   * Sets the lookupAccount attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new lookupAccount value
	   */
	  public void setLookupAccount(String tmp) {
	    this.lookupAccount = DatabaseUtils.parseBoolean(tmp);
	  }

	  /**
	   * Sets the propertyMap attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new propertyMap value
	   */
	  public void setPropertyMap(PropertyMap tmp) {
	    this.propertyMap = tmp;
	  }

	  /**
	   * Sets the filePath attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new filePath value
	   */
	  public void setFilePath(String tmp) {
	    this.filePath = tmp;
	  }

	  /**
	   * Sets the db attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new db value
	   */
	  public void setDb(Connection tmp) {
	    this.db = tmp;
	  }

	  /**
	   * Sets the manager attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new manager value
	   */
	  public void setManager(ImportManager tmp) {
	    this.manager = tmp;
	  }

	  /**
	   * Sets the errorFile attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new errorFile value
	   */
	  public void setErrorFile(File tmp) {
	    this.errorFile = tmp;
	  }

	  /**
	   * Sets the fileItem attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new fileItem value
	   */
	  public void setFileItem(FileItem tmp) {
	    this.fileItem = tmp;
	  }

	  /**
	   * Sets the importThread attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new importThread value
	   */
	  public void setImportThread(Thread tmp) {
	    this.importThread = tmp;
	  }

	  /**
	   * Gets the importThread attribute of the ContactImport object
	   * 
	   * @return The importThread value
	   */
	  public Thread getImportThread() {
	    return importThread;
	  }

	  /**
	   * Gets the fileItem attribute of the ContactImport object
	   * 
	   * @return The fileItem value
	   */
	  public FileItem getFileItem() {
	    return fileItem;
	  }

	  /**
	   * Gets the errorFile attribute of the ContactImport object
	   * 
	   * @return The errorFile value
	   */
	  public File getErrorFile() {
	    return errorFile;
	  }

	  /**
	   * Gets the manager attribute of the ContactImport object
	   * 
	   * @return The manager value
	   */
	  public ImportManager getManager() {
	    return manager;
	  }

	  /**
	   * Gets the db attribute of the ContactImport object
	   * 
	   * @return The db value
	   */
	  public Connection getDb() {
	    return db;
	  }

	  /**
	   * Gets the filePath attribute of the ContactImport object
	   * 
	   * @return The filePath value
	   */
	  public String getFilePath() {
	    return filePath;
	  }

	  /**
	   * Gets the propertyMap attribute of the ContactImport object
	   * 
	   * @return The propertyMap value
	   */
	  public PropertyMap getPropertyMap() {
	    return propertyMap;
	  }

	  /**
	   * Gets the lookupAccount attribute of the ContactImport object
	   * 
	   * @return The lookupAccount value
	   */
	  public boolean getLookupAccount() {
	    return lookupAccount;
	  }

	  /**
	   * Gets the userId attribute of the ContactImport object
	   * 
	   * @return The userId value
	   */
	  public int getUserId() {
	    return userId;
	  }

	  /**
	   * Gets the owner attribute of the ContactImport object
	   * 
	   * @return The owner value
	   */
	  public int getOwner() {
	    return owner;
	  }

	  /**
	   * Gets the accessTypeId attribute of the ContactImport object
	   * 
	   * @return The accessTypeId value
	   */
	  public int getAccessTypeId() {
	    return accessTypeId;
	  }

	//  /**
	//   * Sets the connectionElement attribute of the ContactImport object
	//   * 
	//   * @param tmp
	//   *          The new connectionElement value
	//   */
	//  public void setConnectionElement(ConnectionElement tmp) {
//	    this.connectionElement = tmp;
	//  }
	//
	//  /**
	//   * Gets the connectionElement attribute of the ContactImport object
	//   * 
	//   * @return The connectionElement value
	//   */
	//  public ConnectionElement getConnectionElement() {
//	    return connectionElement;
	//  }

	  /**
	   * Gets the lead attribute of the ContactImport object
	   * 
	   * @return The lead value
	   */
	  public boolean getLead() {
	    return lead;
	  }

	  /**
	   * Sets the lead attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new lead value
	   */
	  public void setLead(boolean tmp) {
	    this.lead = tmp;
	  }

	  /**
	   * Sets the lead attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new lead value
	   */
	  public void setLead(String tmp) {
	    this.lead = DatabaseUtils.parseBoolean(tmp);
	  }

	  /**
	   * Gets the leadStatus attribute of the ContactImport object
	   * 
	   * @return The leadStatus value
	   */
	  public int getLeadStatus() {
	    return leadStatus;
	  }

	  /**
	   * Sets the leadStatus attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new leadStatus value
	   */
	  public void setLeadStatus(int tmp) {
	    this.leadStatus = tmp;
	  }

	  /**
	   * Sets the leadStatus attribute of the ContactImport object
	   * 
	   * @param tmp
	   *          The new leadStatus value
	   */
	  public void setLeadStatus(String tmp) {
	    this.leadStatus = Integer.parseInt(tmp);
	  }
	  
	  
	  


	/**
	   * Description of the Method
	   */
	  public void start() {
	    importThread = new Thread(this);
	    importThread.start();
	  }

	  /**
	   * Process the import
	   */
	  public void run() {
	    ArrayList thisRecord = new ArrayList();
	    String line = null;
	    StringBuffer error = new StringBuffer();
	    int recordCount = 1;
	    boolean recordInserted = false;
	    boolean done = false;
	    ArrayList header = null;
	    Thread currentThread = Thread.currentThread();
	    ContactNameFormatter nameFormatter = new ContactNameFormatter();
	    boolean withoutContact = false;

	    try {
	      // get connection from the manager
	      db = manager.getConnection();

	      // create a temporary log file under the $FILELIBRARY/database
	      errorFile = new File(filePath + "_error");
	      fos = new BufferedWriter(new FileWriter(errorFile));

	      if (db == null) {
	        // FATAL
	        throw new Exception("FATAL: Could not get database connection!");
	      }

	      // update status
	      if (updateStatus(db, RUNNING) < 1) {
	        // FATAL
	        throw new Exception("Error updating status to RUNNING");
	      }
	      
	      // Read the file in
	      CFSFileReader fileReader = new CFSFileReader(filePath, this.getFileType());
	      fileReader.setColumnDelimiter(",");

	      // header
	      //MODIFICATO PER SNELLIRE L'IMPORT
	      CFSFileReader.Record record = null; //fileReader.nextLine();
	      
	      //header = record.data;

	      // add the header to the log file
	      recordError(null, "Passaporto", 0);
	      //recordError(null, record.line, 0);
	   
	      UserList userList = new UserList();
	      userList.setBuildContact(true);
	      userList.setSiteId(this.getSiteId());
				if (this.getSiteId() != -1){ 
					userList.setIncludeUsersWithAccessToAllSites(true);
				}
	      userList.buildList(db);
	      
	      // process data
	      while (importThread == currentThread && !done) {
	        if ((record = fileReader.nextLine()) != null) {
	          if (error.length() > 0) {
	            error.delete(0, error.length());
	          }
	          recordInserted = false;
	          withoutContact = false;
	          ++recordCount;

	          try {

	            if (!record.isEmpty()) {
	              // get the record
	              thisRecord = record.data;

	              // get the line and pad it if necessary for missing columns
	              line = fileReader.padLine(record.line, 1  - thisRecord.size());

	              Passaporto p = new Passaporto();
	              p.setIdImport(this.getId());
	              p.setIdStatus(Import.PROCESSED_UNAPPROVED);
	                 
	              String nrPassaporto = thisRecord.get(0).toString().replace("\"", " ").trim();
	              String asl = thisRecord.get(1).toString().replace("\"", "").trim();
	              
	              
	
	              
	              if (!"".equals(StringUtils.toString(nrPassaporto))) {
	                  p.setNrPassaporto(nrPassaporto);
	              }else {
	            	  error.append("; Error valore passaporto  non presente");            	  
	              }           
	              if (!"".equals(StringUtils.toString(asl))) {
	            	  p.setIdAslAppartenenza(Integer.valueOf(asl));
	              }else {
	            	  error.append("; Error asl non presente");
	              }
	              if ( ( nrPassaporto.trim().length() == 13 ) ) {
	            	  if ( !Passaporto.verifyDuplicate(db, nrPassaporto) ){
	            		  error.append("; Passaporto: "+nrPassaporto+" gia presente!");
	            	  }
	              }
	              if ( p.getNrPassaporto() != null ) {
	            	  if ( !(  p.getNrPassaporto().trim().length() == 13 ) ){
	            		  error.append("; Passaporto: "+nrPassaporto+" lunghezza non valida!");
	            	  }
	              }
	              
	             p.setIdUtentePrecaricamento(getUserId());
	             

	              if (error.length() == 0) {
	                p = p.store(db);
	                
	                if (p.getId() > 0) {
	                  incrementTotalImportedRecords();
	                } else {
	                  incrementTotalFailedRecords();
	                }
	                
	              } else {
	                recordError(error.toString(), line, recordCount);
	              }
	              
	            }
	            
	          } catch (Exception unknownException) {
	            unknownException.printStackTrace();
	            if (error.length() == 0) {
	              recordError(unknownException.toString(), line, recordCount);
	            } else {
	              recordError(
	                  error.toString() + "; " + unknownException.toString(), line,
	                  recordCount);
	            }
	          }
	        } else {
	          done = true;
	        }
	      }

	      if (done) {
	        this.setStatusId(Import.PROCESSED_UNAPPROVED);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	      recordError(e.toString(), "", -1);
	      try {
	        // update status
	        this.setStatusId(FAILED);
	      } catch (Exception statusException) {
	        e.printStackTrace();
	        // error updating status
	      }
	    } finally {
	      if (importThread == currentThread) {
	        stop();
	        manager.free(db);
	      }
	    }
	  }


	  /**
	   * Cancel the import
	   */
	  public void cancel() {
	    try {
	      // do not use stop to stop the thread, nullify it
	      importThread = null;

	      // set the status to canceled
	      this.setStatusId(Import.CANCELED);

	      // perform clean up
	      destroy();
	    } catch (Exception e) {
	    }
	  }

	  /**
	   * Stops the thread
	   */
	  public void stop() {
	    // do not use stop() to stop the thread, nullify it
	    importThread = null;

	    // perform clean up
	    destroy();
	  }

	  /**
	   * Description of the Method
	   */
	  public void destroy() {
	    // set status of the import and clean up thread pool
	    if (System.getProperty("DEBUG") != null) {
	    	logger.info("[CANINA] - ContactImport -> Starting cleanup for ImportId: " + this.getId());
	    }

	    try {
	      // update status, total imported/failed records
	      recordResults(db);

	      // flush the log file
	      fos.flush();
	      fos.close();

	      if (this.getTotalFailedRecords() > 0) {
	        // Store the error file as a version
	        fileItem.setSubject("Error file");
	        fileItem.setFilename(fileItem.getFilename() + "_error");
	        fileItem.setClientFilename(this.getId() + "_error.csv");
	        fileItem.setVersion(ERROR_FILE_VERSION);
	        fileItem.setSize((int) errorFile.length());
	        fileItem.insertVersion(db);
	      } else {
	        errorFile.delete();
	      }

	      // report back to manager
	      manager.free(this);
	    } catch (Exception e) {
	      if (System.getProperty("DEBUG") != null) {
	        e.printStackTrace();
	      }
	    } finally {
	      // release the database connection
	      manager.free(db);
	    }
	  }

	  /**
	   * Logs an error when a record fails
	   * 
	   * @param error
	   *          Description of the Parameter
	   * @param line
	   *          Description of the Parameter
	   * @param lineNumber
	   *          Description of the Parameter
	   */
	  private void recordError(String error, String line, int lineNumber) {
	    try {
	      // log errors in the temp file created under $FILELIBRARY/_imports/
	      if (lineNumber == 0) {
	        // header:append error column
	        line += "," + "\"_ERROR\"";
	      } else if (lineNumber == -1) {
	        // general error, mostly before import started
	        line += error;
	      } else if (lineNumber > 0) {
	        // append the error
	        line += ",\"" + error + "\"";

	        // a record has failed, increment the failure count
	        this.incrementTotalFailedRecords();
	      }

	      // add next line character
	      // TODO: Change this to a CUSTOM row delimiter if import type is CUSTOM
	      line += "\n";

	      fos.write(line);
	    } catch (IOException e) {
	      // import should not fail because of logging error
	    }
	  }

	  /**
	   * Gets the value attribute of the ContactImport object
	   * 
	   * @param thisRecord
	   *          Description of the Parameter
	   * @param type
	   *          Description of the Parameter
	   * @return The value value
	   */
	  private String getValue(ArrayList thisRecord, Property type) {
	    String value = null;
	    if (type.getMappedColumn() > -1
	        && thisRecord.size() > type.getMappedColumn()) {
	      value = (String) thisRecord.get(type.getMappedColumn());
	    } else if (!"".equals(StringUtils.toString(type.getDefaultValue()))) {
	      value = type.getDefaultValue();
	    }
	    return value;
	  }





}
