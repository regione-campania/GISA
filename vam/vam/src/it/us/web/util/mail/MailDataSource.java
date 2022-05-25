/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.mail;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class MailDataSource implements DataSource {

	byte[] bytes;

	String contentType = null;
	String name;
	
	public MailDataSource( byte[] bytes, String contentType, String name)
	{
		this.bytes = bytes;
		if (contentType == null)
		{
			this.contentType = "application/octet-stream";
		}
		else
		{
			this.contentType = contentType;
		}
		this.name = name;
	} 
	
	public String getContentType() {
		return contentType;
	}

	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(bytes, 0, bytes.length - 2);
	}

	public String getName() {
		return name;
	}

	public OutputStream getOutputStream() throws IOException {
		throw new FileNotFoundException(); 
	}

}
