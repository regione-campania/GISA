/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import org.apache.lucene.index.Term;

/**
 * Class for working with the Lucene search engine
 *
 * @author
 * @version $Id: FileFolderIndexer.java 12404 2005-08-05 17:37:07Z mrajkowski $
 * @created
 */
public class FileFolderIndexer implements Indexer {

  /**
   * Gets the searchTerm attribute of the FileFolderIndexer class
   *
   * @param fileFolder Description of the Parameter
   * @return The searchTerm value
   */
  public static Term getSearchTerm(FileFolder fileFolder) {
    Term searchTerm = new Term("folderId", String.valueOf(fileFolder.getId()));
    return searchTerm;
  }


  /**
   * Gets the deleteTerm attribute of the FileFolderIndexer class
   *
   * @param fileFolder Description of the Parameter
   * @return The deleteTerm value
   */
  public static Term getDeleteTerm(FileFolder fileFolder) {
    Term searchTerm = new Term("folderId", String.valueOf(fileFolder.getId()));
    return searchTerm;
  }
}

