package com.aurora.org.connector;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aurora.seedlistservice.remoterestclient.SeedListObjectsService;
import com.aurora.xml.GSAFeed;
import com.aurora.xml.Meta;
import com.aurora.xml.Record;
import com.google.enterprise.connector.spi.AuthenticationIdentity;
import com.google.enterprise.connector.spi.AuthenticationManager;
import com.google.enterprise.connector.spi.AuthenticationResponse;
import com.google.enterprise.connector.spi.AuthorizationManager;
import com.google.enterprise.connector.spi.AuthorizationResponse;
import com.google.enterprise.connector.spi.Connector;
import com.google.enterprise.connector.spi.Document;
import com.google.enterprise.connector.spi.DocumentList;
import com.google.enterprise.connector.spi.Property;
import com.google.enterprise.connector.spi.RepositoryException;
import com.google.enterprise.connector.spi.Session;
import com.google.enterprise.connector.spi.SimpleDocument;
import com.google.enterprise.connector.spi.SpiConstants;
import com.google.enterprise.connector.spi.TraversalManager;
import com.google.enterprise.connector.spi.Value;

public class WCMConnector implements Connector {
	private static final String XSL_ATOMFEED = "xsl/seedlist.xsl";

	
	
  public Session login() {
    return new WCMSession();
  }

  public class WCMSession implements Session {
    public AuthenticationManager getAuthenticationManager() {
      return new WCMAuthenticationManager();
    }

    public AuthorizationManager getAuthorizationManager() {
      return new WCMAuthorizationManager();
    }

    public TraversalManager getTraversalManager() {
      return new WCMTraversalManager();
    }
  }

  public class WCMTraversalManager implements TraversalManager {
    private static final int MAX_DOCID = 1000;
    private int batchHint = 10;

    public void setBatchHint(int hint) {
      batchHint = hint;
    }

    public DocumentList startTraversal() {
      return traverse("0");
    }

    public DocumentList resumeTraversal(String checkpoint) {
      return traverse(checkpoint);
    }

    /**
     * Utility method to produce a {@code DocumentList} containing
     * the next batch of {@code Document} from the checkpoint.
     *
     * @param checkpoint a String representing the last document
     *        number processed.
     */
    private DocumentList traverse(String checkpoint) {
      int startDocId = Integer.parseInt(checkpoint) + 1;
      if (startDocId > MAX_DOCID) {
        return null;  // No more documents.
      }

      Calendar cal = Calendar.getInstance();

      List<Document> docList = new ArrayList<Document>(batchHint);
      GSAFeed gFeed = SeedListObjectsService.getInstance().getSeedListXSL(XSL_ATOMFEED);
      ArrayList<Record> records = gFeed.getGroup();
    
      for (int i = 0;i<records.size();i++){
    	  cal.setTimeInMillis(10 * 1000); // Each doc has the curretn timestamp
    	  Map<String, List<Value>>properties;
    	  properties = new HashMap<String, List<Value>>();
    	  Record temp = records.get(i);
    	  properties.put(SpiConstants.PROPNAME_LASTMODIFIED,asList(Value.getDateValue(cal)));
    	  properties.put(SpiConstants.PROPNAME_DISPLAYURL, asList(Value.getStringValue(temp.getDisplayurl())));
    	  properties.put(SpiConstants.PROPNAME_SEARCHURL, asList(Value.getStringValue(temp.getUrl())));
    	  properties.put(SpiConstants.PROPNAME_MIMETYPE, asList(Value.getStringValue(temp.getMimetype())));
    	  
    	  /*
    	   * Add Meta Data
    	   */
    	  ArrayList<Meta> meta = temp.getMetadata();
    	  for (int x = 0;x<meta.size();x++){
    		  Meta tag = meta.get(x);
    		  properties.put(tag.getName(), asList(Value.getStringValue(tag.getContent())));
    	  }
    	  docList.add(new SimpleDocument(properties));
      }
//      int endDocId = Math.min(startDocId + batchHint - 1, MAX_DOCID);
//
//      for (int i = startDocId; i <= endDocId; i++) {
//        cal.setTimeInMillis(10 * 1000); // Each doc has the curretn timestamp
//        Map<String, List<Value>>properties;
//        properties = new HashMap<String, List<Value>>();
//       
//        properties.put(SpiConstants.PROPNAME_DOCID,
//            asList(Value.getStringValue(Integer.toString(i))));
//
//        properties.put(SpiConstants.PROPNAME_LASTMODIFIED,
//            asList(Value.getDateValue(cal)));
//
//        properties.put(SpiConstants.PROPNAME_DISPLAYURL,
//            asList(Value.getStringValue("http://www.example.com/?docid=" + i)));
//
//        properties.put(SpiConstants.PROPNAME_CONTENT,
//            asList(Value.getBinaryValue("WCM World!".getBytes())));
//
//        docList.add(new SimpleDocument(properties));
//      }

      return new WCMDocumentList(docList);
    }

    private List<Value> asList(Value value) {
      List<Value> list = new LinkedList<Value>();
      list.add(value);
      return list;
    }
  }

  class WCMDocumentList implements DocumentList {
    private Iterator<Document> iterator;
    private Document document;

    public WCMDocumentList(List<Document> documents) {
      this.iterator = documents.iterator();
      this.document = null;
    }

    public Document nextDocument() {
      if (iterator.hasNext()) {
        document = iterator.next();
        return document;
      }
      return null;
    }

    public String checkpoint() throws RepositoryException {
      if (document != null) {
        Property docId =
            document.findProperty(SpiConstants.PROPNAME_DOCID);
        return docId.nextValue().toString();
      }
      return null;
    }
  }

  class WCMAuthenticationManager implements AuthenticationManager {
    public AuthenticationResponse authenticate(AuthenticationIdentity id) {
      return new AuthenticationResponse(true, null);
    }
  }

  class WCMAuthorizationManager implements AuthorizationManager {
    public Collection<AuthorizationResponse> authorizeDocids(
         Collection<String> docIds, AuthenticationIdentity id) {
      ArrayList<AuthorizationResponse> authorized =
          new ArrayList<AuthorizationResponse>(docIds.size());
      for (String docId : docIds) {
        authorized.add(new AuthorizationResponse(true, docId));
      }
      return authorized;
    }
  }
}