<%@ page import="java.util.*,java.io.*,java.lang.*,com.ibm.workplace.wcm.api.*" 
%><%@taglib uri="/WEB-INF/tld/wcm.tld" prefix="wcm" 
%><%!
  /*
   * declarations
   */
  static final String ATTR_LEVEL = "Level"; 
  static final String ATTR_ISFIRST = "IsFirst";
  static final String ATTR_BASELEVEL = "BaseLevel";
  static final String PARAM_HIGHLIGHT = "highlight";
%><wcm:initworkspace user="<%= request.getUserPrincipal() %>">login fail</wcm:initworkspace><%
  /*
   * Get current state from request
   */
  // default level=0
  int level = (request.getAttribute(ATTR_LEVEL) == null ? 0 : Integer.parseInt((String)request.getAttribute(ATTR_LEVEL)));
  // default isFirst=true
  boolean isFirst = (request.getAttribute(ATTR_ISFIRST) == null || request.getAttribute(ATTR_ISFIRST) != "false"); 

  /*
   * Find current level  
   */
  RenderingContext rc = (RenderingContext) request.getAttribute(Workspace.WCM_RENDERINGCONTEXT_KEY);
  Workspace ws = rc.getContent().getSourceWorkspace();
  DocumentId docId = rc.getCurrentResultId();
  String path = ws.getPathById(docId, false, false);
  int currLevel = path.split("/").length;

  /*
   * Render open/close list tags based on current level vs. previous level
   */
  // boolean isFirstForLevel = false;
   
  // if going down to a deeper level or is first item, start a new list
  if (currLevel > level || isFirst)
  {
    out.print("<ul>");
    //isFirstForLevel = true;
  }
  // if coming up from a deeper level, close off hanging list and containing item
  else if (currLevel < level)
  {
    // close last item on previous level
    out.print("</li>");
    
    // close all the hanging lists/items
    int levelsUp = level - currLevel;
    for (int i = 0; i < levelsUp; i++)
    {
      out.print("</ul>");
      out.print("</li>");
    }
  }
  // if staying at same level, and not first item, close previous item
  else if (currLevel == level && !isFirst)
  {
      out.print("</li>");
  }

  /*
   * Render item tag for current item
   */
  // check if highlighting current content or site area
  String highlightStr = request.getParameter(PARAM_HIGHLIGHT);
  boolean highlightContent = (highlightStr != null && highlightStr.equals("content"));

  // check if this is an item on the current path
  String currPath = ws.getPathById(rc.getContent().getId(), false, false);
  boolean isSelected = (highlightContent && path.equals(currPath)) || (!highlightContent && path.equals(currPath.substring(0, currPath.lastIndexOf('/'))));
  boolean isSelectedPath = currPath.startsWith(path);
 if(docId.isOfType(DocumentTypes.Content)){
   isSelectedPath=false;
 }
  // render list item tag, marking with styles for "first" and "selected"
  out.print("<li class=\"item" + (isSelected ? " selected" : (isSelectedPath ? " selectedPath" : "")) + (docId.isOfType(DocumentTypes.Content) ? " content" : "") + "\">");

  /*
   * Save current state
   */
  if (isFirst)
  {
    request.setAttribute(ATTR_BASELEVEL, Integer.toString(currLevel));
  }
  request.setAttribute(ATTR_ISFIRST, "false");
  request.setAttribute(ATTR_LEVEL, Integer.toString(currLevel));
%>