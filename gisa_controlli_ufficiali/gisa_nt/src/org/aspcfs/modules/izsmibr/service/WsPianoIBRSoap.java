/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * WsPianoIBRSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.aspcfs.modules.izsmibr.service;

public interface WsPianoIBRSoap extends java.rmi.Remote {

    /**
     * Inserisce l'esito di un esame per IBR. restituisce una stringa
     */
    public java.lang.String insertEsitoPrelievoIBR_STR(java.lang.String strRecord) throws java.rmi.RemoteException;

    /**
     * Inserisce l'esito di un esame per IBR.
     */
    public org.aspcfs.modules.izsmibr.service.InsertEsitoPrelievoIBRResponseInsertEsitoPrelievoIBRResult insertEsitoPrelievoIBR(java.lang.String strRecord) throws java.rmi.RemoteException;
}
