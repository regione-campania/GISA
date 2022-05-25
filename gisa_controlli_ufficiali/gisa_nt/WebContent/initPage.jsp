<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ page import="java.util.*,java.text.*,org.aspcfs.controller.SystemStatus,com.darkhorseventures.database.ConnectionElement,org.aspcfs.utils.*" %>
<%@ page import="org.aspcfs.modules.admin.base.*, org.aspcfs.modules.login.beans.UserBean,org.aspcfs.modules.quotes.base.*,java.sql.*" %>
<%@ page import="org.aspcfs.controller.ApplicationPrefs" %>
<%! 
	
	public static String asteriscoSeDiverso(String x, String y) {
		String ritorno=null;
		if (x==null) { 
			ritorno = "<b>"+ " (Effettuata Modifica)" + "</b>";
		} else if (y==null) { 
			ritorno="";
		} else if (x.equals(y)) {
			ritorno = "";
		}
		else {
			ritorno  = "<b>"+ " (Effettuata Modifica)" + "</b>";
		}
		
		return ritorno;
	}
	

public String getMeseFromData(String mese) {
	try {Integer.parseInt(mese);}
	catch (Exception e){
		return "";
	}
	
	switch (Integer.parseInt(mese)) {
	case 01:
		mese = "Gennaio";
		break;
	case 02:
		mese = "Febbraio";
		break;
	case 03:
		mese = "Marzo";
		break;
	case 04:
		mese = "Aprile";
		break;
	case 05:
		mese = "Maggio";
		break;
	case 06:
		mese = "Giugno";
		break;
	case 07:
		mese = "Luglio";
		break;
	// case 08 : mese = "Agosto" ; break;
	// case 09 : mese = "Settembre" ; break;
	case 10:
		mese = "Ottobre";
		break;
	case 11:
		mese = "Novembre";
		break;
	case 12:
		mese = "Dicembre";
		break;
	}
	if (mese.equals("08")) {
		mese = "Agosto";
	}
	if (mese.equals("09")) {
		mese = "Settembre";
	}

	return mese;
}

public static String toDateasString(Timestamp time)
{
	  String toRet = "";
	  try
	  { 
		  if (time != null){
		  	java.sql.Date d = new java.sql.Date(time.getTime());
		  
		  	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		  	toRet=sdf.format(d);
	  }
	  }
	  catch(Exception e)
	  {
		  System.out.println(e);
	  }
	  return toRet;
}

public static String toDateasStringWitTime(Timestamp time)
{
	  String toRet = "";
	  try
	  { 
		  if (time != null){
		  	java.sql.Date d = new java.sql.Date(time.getTime());
		  
		  	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		  	toRet=sdf.format(d);
	  }
	  }
	  catch(Exception e)
	  {
		  System.out.println(e);
	  }
	  return toRet;
}

public static String toDateasStringWithFormat(Timestamp time, String format)
{
	  String toRet = "";
	  try
	  { 
		  if (time != null){
		  	java.sql.Date d = new java.sql.Date(time.getTime());
		  
		  	SimpleDateFormat sdf = new SimpleDateFormat(format);
		  	toRet=sdf.format(d);
	  }
	  }
	  catch(Exception e)
	  {
		  System.out.println(e);
	  }
	  return toRet;
}

	public static String grassettoSeDiverso(Integer x, Integer y) {
		String ritorno=null;
		if (y.intValue()==-1) { 
			ritorno="";
		} else if (x.equals(y)) {
			ritorno = ""+y.intValue();
		}
		else {
			ritorno = "<b>"+ y.intValue() + "</b>";
		}
		
		return ritorno;
	}

	public static String grassettoSeDiverso(String x, String y) {
		String ritorno=null;
		if (x==null) { 
			ritorno = "<b>"+ y + "</b>";
		} else if (y==null) { 
			ritorno="";
		} else if (x.equals(y)) {
			ritorno = y;
		}
		else {
			ritorno = "<b>"+ y + "</b>";
		}
		
		return ritorno;
	}

	public static String grassettoSeDiverso(Timestamp x, Timestamp y) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String ritorno=null;

		if (x==null) { 
			ritorno="<b>"+ formatter.format((java.util.Date) y) + "</b>";
		} else if (y==null) { 
			ritorno="";
		} else if (x.equals(y)) {
			ritorno = formatter.format((java.util.Date) y);
		}
		else {
			ritorno = "<b>"+ formatter.format((java.util.Date) y) + "</b>";
		}
		
		return ritorno;
	}
	
	public static String grassettoSeDiversoConMinSec(Timestamp x, Timestamp y) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh.mm");
		String ritorno=null;

		if (x==null) { 
			ritorno="<b>"+ formatter.format((java.util.Date) y) + "</b>";
		} else if (y==null) { 
			ritorno="";
		} else if (x.equals(y)) {
			ritorno = formatter.format((java.util.Date) y);
		}
		else {
			ritorno = "<b>"+ formatter.format((java.util.Date) y) + "</b>";
		}
		
		return ritorno;
	}
	
	
    
public static String replace(String str, String o, String n) {
      boolean all = true;
      if (str != null && o != null && o.length() > 0 && n != null) { 
        StringBuffer result = null;
        int oldpos = 0;
        do {
            int pos = str.indexOf(o, oldpos);
            if (pos < 0)
                break;
            if (result == null)
                result = new StringBuffer();
            result.append(str.substring(oldpos, pos));
            result.append(n);
            pos += o.length();
            oldpos = pos;
        } while (all);
        if (oldpos == 0) {
            return str;
        } else {
            result.append(str.substring(oldpos));
            return new String(result);
        }
      } else {
        return str;
      }
    }
    
  public static String toString(String s) {
    if (s != null) {
      return(s.trim());
    } else {
      return("");
    }
  }
  
  public static String toStringSpace(String s) {
	    if (s != null && !s.equals("") ) {
	      return(s.trim());
	    } else {
	      return(" ");
	    }
	  }
  
  public static String toHtml(String s) {
    return StringUtils.toHtml(s);
  }
  
  public static String toHtml2(String s) {
	  
	  String toReturn = StringUtils.toHtml2(s).trim().replaceAll("'","").toUpperCase();
	  if(toReturn!=null && toReturn.equals("NULL"))
		  return "";
	  return toReturn;
	  }
  
  public static String toHtml4(String s) {
	  
	    return StringUtils.toHtml4(s).trim().toUpperCase().replaceAll("NULL", "");
	  }
  
  public static String toHtmlLight(String s) {
	    return StringUtils.toHtmlLight(s).trim().replaceAll("'","").toUpperCase();
	  }
  
  
  
  public static String toHtml3(String s) {
	    return StringUtils.toHtml2(s).trim().replaceAll("'","'--'");
	  }
    
  public static String toHtmlValue(String s) {
    return StringUtils.toHtmlValue(s);
  }
    
  public static String toJavaScript(String s) {
      return StringUtils.jsStringEscape(s);
  }
  
  public static String toHtmlValue(int tmp) {
    return (toHtmlValue(String.valueOf(tmp)));
  }

  public static String toHtmlFill(String s, int size) {
	  String output="";
	  if (s==null || s.equals("")) {
		  for (int i = 0; i<size;i++)
			  output+="&nbsp;";
	  }
	  else
		  output = StringUtils.toHtml(s);
	  return output;
	  }
  
  public static String toDateString(java.sql.Timestamp inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT,Locale.ITALIAN).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }
  
  public static String toDateString(java.sql.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT,Locale.ITALIAN).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }
  
  public static String toLongDateString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }
  
  public static String toFullDateString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }
  
  public static String toMediumDateString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }
  
  public static String sqlReplace(String s) {
    //s = replace(s, "<br>", "\r");
      String newString = "";
      char[] input = s.toCharArray();
      int arraySize = input.length;
      for (int i=0; i < arraySize; i++) {
        if (input[i] == '\'') {
          newString += "\\\'";
        } else if (input[i] == '\\') {
          newString += "\\\\";
        } else {
          newString += input[i];
        }
      }
      return newString;
    }
    
    private static String getLongTimestamp(java.sql.Timestamp tmp) {
      SimpleDateFormat formatter = new SimpleDateFormat ("MMMMM d, yyyy 'at' hh:mm a");
      return(formatter.format(tmp));
    }
    
    private static String getShortDate(java.sql.Date tmp) {
      SimpleDateFormat formatter = new SimpleDateFormat ("MM/dd/yy");
      return(formatter.format(tmp));
    }
    
    public static String getLongDate(java.sql.Timestamp tmpTimestamp) {
      SimpleDateFormat formatter1 = new SimpleDateFormat ("dd/MM/yyyy");
      return(formatter1.format(tmpTimestamp));
    }
    
    private static String getShortDateLong(java.sql.Timestamp tmpTimestamp) {
        SimpleDateFormat formatter1 = new SimpleDateFormat ("dd/MM/yyyy");
        return(formatter1.format(tmpTimestamp));
      }
    
    public static String showAttribute(HttpServletRequest request, String errorEntry) {
      return "<font color='#006699'>" + toHtml((String)request.getAttribute(errorEntry)) + "</font>";
    }
    
    public static String showError(HttpServletRequest request, String errorEntry) {
      return showError(request, errorEntry, true);
    }

    public static String showWarning(HttpServletRequest request, String warningEntry) {
      if (request.getAttribute(warningEntry) != null) {
        return "&nbsp;<br /><img src=\"images/box-hold.gif\" border=\"0\" align=\"absmiddle\"/> <font color='#FF9933'>" + toHtml((String)request.getAttribute(warningEntry)) + "</font><br />&nbsp;<br />";
      } else if(request.getParameter(warningEntry) != null){
        return "&nbsp;<br /><img src=\"images/box-hold.gif\" border=\"0\" align=\"absmiddle\"/> <font color='#FF9933'>" + toHtml((String)request.getParameter(warningEntry)) + "</font><br />&nbsp;<br />";
      } else{
        return "&nbsp;";
      }
    }

    public static String showWarningAttribute(HttpServletRequest request, String warningEntry) {
      if (request.getAttribute(warningEntry) != null) {
        return "<font color='#FF9933'>" + toHtml((String)request.getAttribute(warningEntry)) + "</font>";
      } else if(request.getParameter(warningEntry) != null){
        return "<font color='#FF9933'>" + toHtml((String)request.getParameter(warningEntry)) + "</font>";
      } else{
        return "";
      }
    }
    
    public static String showError(HttpServletRequest request, String errorEntry, boolean showSpace) {
      if (request.getAttribute(errorEntry) != null) {
        return (showSpace ? "&nbsp;<br />" : "") + "<img src=\"images/error.gif\" border=\"0\" align=\"absmiddle\"/> <font color='red'>" + toHtml((String)request.getAttribute(errorEntry)) + "</font><br />&nbsp;<br />";
      } else if (request.getParameter(errorEntry) != null) {
        return (showSpace ? "&nbsp;<br />" : "") + "<img src=\"images/error.gif\" border=\"0\" align=\"absmiddle\"/> <font color='red'>" + toHtml((String)request.getParameter(errorEntry)) + "</font><br />&nbsp;<br />";
      } else {
        return (showSpace ? "&nbsp;" : "");
      }
    }
    
    public static boolean hasText(String in) {
      return (in != null && !("".equals(in.trim())) && !(" ".equals(in.trim())));
    }

    public static boolean hasText(Object object, String in) {
      StringTokenizer st = new StringTokenizer(in, ",");
      while (st.hasMoreTokens()) {
        String token = st.nextToken();
        if (hasText(ObjectUtils.getParam(object, token.trim()))) {
          return true;
        }
      }
      return false;
    }
    
    public static String toDateTimeString(java.sql.Timestamp inDate) {
      return toDateTimeString(inDate, "");
    }
    
    public static String toDateTimeString(java.sql.Timestamp inDate, String defaultText) {
      try {
        return java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.LONG).format(inDate);
      } catch (NullPointerException e) {
      }
      return defaultText;
    }

  public static String addHiddenParams(HttpServletRequest request, String tmp){
    StringBuffer sb = new StringBuffer();
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while (tokens.hasMoreTokens()){
       String param = tokens.nextToken();
       if (request.getParameter(param) != null){
        sb.append("<input type=\"hidden\" name=\"" + param + "\" value=\"" + request.getParameter(param) + "\">");
       }
    }
    return sb.toString();
  }
  
  public static String addLinkParams(HttpServletRequest request, String tmp){
    StringBuffer sb = new StringBuffer();
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while(tokens.hasMoreTokens()){
     String param = tokens.nextToken();
     if (request.getParameter(param) != null){
       sb.append("&" + param + "=" + request.getParameter(param));
     }
    }
    return sb.toString();
  }
    
  public static boolean isPopup(HttpServletRequest request){
      return (request.getParameter("popup") != null && "true".equals(request.getParameter("popup")));
    }

  public static boolean isInLinePopup(HttpServletRequest request){
    return "inline".equals(request.getParameter("popupType"));
  }
  
  public static String addContainerParams(HttpServletRequest request, String tmp){
    StringBuffer sb = new StringBuffer();
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while(tokens.hasMoreTokens()){
     String param = tokens.nextToken();
     if (request.getParameter(param) != null){
       sb.append("|" + param + "=" + request.getParameter(param));
     }
    }
    return sb.toString();
  }
    
    public static String getServerPort(HttpServletRequest request) {
      int port = request.getServerPort();
      if (port != 80 && port != 443) {
        return ":" + String.valueOf(port);
      }
      return "";
    }
    
    public static String getServerUrl(HttpServletRequest request) {
      return request.getServerName() + getServerPort(request) + request.getContextPath();
    }
  
  public static String getPref(ServletContext context, String param) {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getAttribute("applicationPrefs");
    if (prefs != null) {
      return prefs.get(param);
    } else {
      return null;
    }
  }
  
  public boolean allowMultipleComponents(javax.servlet.jsp.PageContext context, String config, String param) {
    java.util.Hashtable globalStatus = (java.util.Hashtable)context.getServletConfig().getServletContext().getAttribute("SystemStatus");
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((java.util.Hashtable)context.getServletConfig().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    String multiple =  systemStatus.getValue(config, param);
    if(multiple != null && "false".equals(multiple)){
      return false;
    }else{
      return true;
    }
  }
  public boolean allowMultipleQuote(javax.servlet.jsp.PageContext context) {
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((java.util.Hashtable)context.getServletConfig().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    String multiple =  systemStatus.getValue(Quote.QUOTE_CONFIG_NAME, Quote.MULTIPLE_QUOTE_CONFIG_PARAM);
    if(multiple != null && "false".equals(multiple)){
      return false;
    }else{
      return true;
    }
  }
  public boolean allowMultipleVersion(javax.servlet.jsp.PageContext context) {
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((java.util.Hashtable)context.getServletConfig().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    String multiple =  systemStatus.getValue(Quote.QUOTE_CONFIG_NAME, Quote.MULTIPLE_VERSION_CONFIG_PARAM);
    if(multiple != null && "false".equals(multiple)){
      return false;
    }else{
      return true;
    }
  }

  public String getTime(javax.servlet.jsp.PageContext context, Timestamp timestamp, String timeZone, int dateFormat, boolean showTimeZone, boolean userTimeZone, boolean dateOnly, String defaultValue) {
    String result = null;
    int timeFormat = DateFormat.SHORT;
    if (timestamp != null && !"".equals(timestamp)) {
      Locale locale = null;
      // Retrieve the user's timezone from their session
      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
      if (thisUser != null) {
        if (userTimeZone) {
          if (timeZone == null) {
            timeZone = thisUser.getUserRecord().getTimeZone();
          }
        }
        // Still use the user's locale
        locale = thisUser.getUserRecord().getLocale();
      }
      if (locale == null) {
        locale = Locale.getDefault();
      }

      //Format the specified timestamp with the retrieved timezone
      SimpleDateFormat formatter = null;
      if (dateOnly) {
        formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(dateFormat, locale);
      } else {
        formatter = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(
            dateFormat, timeFormat, locale);
      }

      //set the pattern
      if (dateOnly) {
        if ((showTimeZone) && (timeZone != null)) {
          formatter.applyPattern(DateUtils.get4DigitYearDateFormat(formatter.toPattern()) + " z");
        } else {
          formatter.applyPattern(DateUtils.get4DigitYearDateFormat(formatter.toPattern()));
        }
      } else {
        SimpleDateFormat dateFormatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
            dateFormat, locale);
        dateFormatter.applyPattern(DateUtils.get4DigitYearDateFormat(dateFormatter.toPattern()));

        SimpleDateFormat timeFormatter = (SimpleDateFormat) SimpleDateFormat.getTimeInstance(
            timeFormat, locale);

        if ((showTimeZone) && (timeZone != null)) {
          formatter.applyPattern(dateFormatter.toPattern() + " " + timeFormatter.toPattern() + " z");
        } else {
          formatter.applyPattern(dateFormatter.toPattern() + " " + timeFormatter.toPattern());
        }
      }
      //set the timezone
      if (timeZone != null) {
        java.util.TimeZone tz = java.util.TimeZone.getTimeZone(timeZone);
        formatter.setTimeZone(tz);
      }
      result = formatter.format(timestamp);
    } else {
      //no date found, output default
      if (defaultValue == null || "".equals(defaultValue)) {
        defaultValue="&nbsp;";
      }
      result = defaultValue;
    }
    return result;
  }

  public String getUsername(javax.servlet.jsp.PageContext context, int userId, boolean lastFirst, boolean firstInitialLast, String defaultText) {
    String result = null;
    Hashtable globalStatus = (Hashtable)context.getServletConfig().getServletContext().getAttribute("SystemStatus");
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable)context.getServletConfig().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    org.aspcfs.modules.admin.base.User thisUser = systemStatus.getUser(userId);
    if (thisUser != null) 
    {
      org.aspcfs.modules.contacts.base.Contact thisContact = thisUser.getContact();
      if (thisContact != null) {
        if (lastFirst) {
          result = StringUtils.toHtml(thisContact.getNameLastFirst());
        } else if (firstInitialLast) {
          result = StringUtils.toHtml(thisContact.getNameFirstInitialLast());
        } else {
          result = StringUtils.toHtml(thisContact.getNameFirstLast());
        }
      }
    } else {
      //NOTE: the default text will already be in the output format
      if (defaultText == null || "".equals(defaultText)) {
        return "&nbsp;";
      }
      String temp = systemStatus.getLabel(defaultText);
      if ( temp != null || !"".equals(temp)) {
        result = StringUtils.toHtml(systemStatus.getLabel(defaultText));
      } else {
        result = defaultText;
      }
    }
    return result;
  }
  
  public boolean hasAuthority(javax.servlet.jsp.PageContext context, String owner) {
    boolean result = false;
    Hashtable globalStatus = (Hashtable)context.getServletConfig().getServletContext().getAttribute("SystemStatus");
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable)context.getServletConfig().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    int userId = ((UserBean) context.getSession().getAttribute("User")).getUserId();
    String[] owners = owner.split(",");
    for (int i = 0; i < owners.length; i++) {
      String oneOwner = owners[i];
      if (userId == Integer.parseInt(oneOwner)) {
        result = true;
        break;
      }
      org.aspcfs.modules.admin.base.User userRecord = systemStatus.getUser(userId);
      org.aspcfs.modules.admin.base.User childRecord = null;
      if (oneOwner != null && !"".equals(oneOwner.trim())) {
        childRecord = userRecord.getChild(Integer.parseInt(oneOwner));
      }
      if (childRecord != null) {
        result = true;
        break;
      }
    }
    return result;
  }
  
  private static String toDateWithTimeasString(java.sql.Timestamp tmp) {
      SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
      return(formatter.format(tmp));
    }
  
  private static String toDateasStringfromString(String tmp) {
	  if (tmp.length()<10)
     		return tmp;
	  else
		  return (tmp.substring(8,10) + "/" + tmp.substring(5,7) + "/" + tmp.substring(0,4)); 
    }
  
  
 private static String toDateasStringFromString(String timestring)
  {
	  String toRet = "";
	  if (timestring == null || timestring == "" ||  timestring.length()<10)
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  toRet =giorno+"/"+mese+"/"+anno;
	  return toRet;
	  
  }
 
 private static String toDateasStringFromStringWithTime(String timestring)
 {
	  String toRet = "";
	  if (timestring == null || timestring == "" ||  timestring.length()<10)
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  String ore = timestring.substring(11,13);
	  String minuti = timestring.substring(14,16);

	  toRet =giorno+"/"+mese+"/"+anno+ " " + ore+":"+minuti;
	  return toRet;
	  
 }
 
 public static String nullablePrint(String toPrint)
 {
		return (toPrint == null || toPrint.trim().length() == 0) ? "" : toPrint; 
	
 }
 
 public Timestamp toAddDays(Timestamp date, int days){
	 
	 if (date== null)
		 return date;
	 
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.DAY_OF_MONTH, days);
	Timestamp dateNew = new Timestamp(cal.getTime().getTime());
	return dateNew;
 }
 
%>




