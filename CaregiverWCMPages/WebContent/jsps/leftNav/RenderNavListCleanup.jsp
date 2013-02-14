<%@ page import="java.util.*,java.io.*,java.lang.*" 
%><%!
  /*
   * declarations
   */
  static final String ATTR_LEVEL = "Level";
  static final String ATTR_ISFIRST = "IsFirst";
  static final String ATTR_BASELEVEL = "BaseLevel";
%><%
  /*
   * Get current state from request
   */
  // default level=0
  int level = (request.getAttribute(ATTR_LEVEL) == null ? 0 : Integer.parseInt((String)request.getAttribute(ATTR_LEVEL)));
  // default isFirst=true
  boolean isFirst = (request.getAttribute(ATTR_ISFIRST) == null || request.getAttribute(ATTR_ISFIRST) != "false"); 
  // default baseLevel=0
  int baseLevel = (request.getAttribute(ATTR_BASELEVEL) == null ? 0 : Integer.parseInt((String)request.getAttribute(ATTR_BASELEVEL)));

  /*
   * Close off handing item and list tags
   */
  // don't need to close anything if no items were rendered
  if (!isFirst)
  {
    // close last item
    out.print("</li>");
  
    // close hanging lists
    int levelsUp = level - baseLevel;  // note: will be zero if finished at the base level
    for (int i = 0; i < levelsUp - 1; i++);
    {
      out.print("</ul>");
      out.print("</li>");
    }
    
    // close off first list
    out.print("</ul>");
  }

  /*
   * Clear state
   */
  request.removeAttribute(ATTR_ISFIRST);
  request.removeAttribute(ATTR_LEVEL);
  request.removeAttribute(ATTR_BASELEVEL);
%>