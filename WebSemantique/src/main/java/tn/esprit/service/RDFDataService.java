package tn.esprit.service;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.function.library.date;
import org.apache.jena.update.UpdateAction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.annotation.PostConstruct;
import tn.esprit.tools.JenaEngine;

@Service
public class RDFDataService {
    private Model model;

    
    @PostConstruct
    public void init() {
		model = JenaEngine.readModel("data/groupe.owl");
    }

    
    public Model getModel() {
        return model;
    }
  
    
    public String executeSampleSparqlQuery() {
    	
        String NS = model.getNsPrefixURI("");
        String qexec = "PREFIX ns: <http://reseau-social.com/>"
        		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
        		+ "SELECT ?evenement ?nomEvenement"
        		+ "WHERE {"
        		+ "	?evenement rdf:type ns:Evenement ."
        		+ "	?evenement ns:nomEvenement ?nomEvenement ."
        		+ "}";
        Model model = JenaEngine.readModel("data/evenement.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        String json = new String(outputStream.toByteArray());

        JSONObject j = new JSONObject(json);
        System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

        JSONArray res = j.getJSONObject("results").getJSONArray("bindings");

        return j.getJSONObject("results").getJSONArray("bindings").toString();

    }
  public String GroupSparqlQuery() {
    	
    	String NS = model.getNsPrefixURI("");
        
    	String qexec = "PREFIX ns: <http://reseau-social.com/>"
    		    + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
    		    + "SELECT ?group ?idGroupe ?nomGroupe ?date_CreationGroupe ?descriptionGroupe ?Regles "
    		    + "WHERE {"
    		    + "  {?group rdf:type ns:Groupe .}"
    		    + "  UNION"
    		    + "  {?group rdf:type ns:GroupePrive .}"
    		    + "  ?group ns:idGroupe ?idGroupe ."
    		    + "  ?group ns:nomGroupe ?nomGroupe ."
    		    + "  ?group ns:date_CreationGroupe ?date_CreationGroupe ."
    		    + "  ?group ns:descriptionGroupe ?descriptionGroupe ."
    		    + "  ?group ns:Regles ?Regles ."
    		    + "}";

        Model model = JenaEngine.readModel("data/groupe.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        String json = new String(outputStream.toByteArray());

        JSONObject j = new JSONObject(json);
        System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

        JSONArray res = j.getJSONObject("results").getJSONArray("bindings");

        return j.getJSONObject("results").getJSONArray("bindings").toString();

    
    }
  public String UserSparqlQuery() {
  	
  	String NS = model.getNsPrefixURI("");
      
  	String qexec = "PREFIX ns: <http://reseau-social.com/>"
  		    + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
  		    + "SELECT ?user ?nom "
  		    + "WHERE {"
  		    + "  {?user rdf:type ns:Utilisateur .}"
  		  + "  UNION"
		    + "  {?user rdf:type ns:Membre .}"
		    + "  UNION"
		    + "  {?user rdf:type ns:SuperAdministrateurClass .}"
		    + "  UNION"
		    + "  {?user rdf:type ns:UtilisateurPrivilege .}"
  		    
  		    + "  ?user ns:nom ?nom ."
  	
  		    + "}";

      Model model = JenaEngine.readModel("data/groupe.owl");

      QueryExecution qe = QueryExecutionFactory.create(qexec, model);
      ResultSet results = qe.execSelect();

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      ResultSetFormatter.outputAsJSON(outputStream, results);

      String json = new String(outputStream.toByteArray());

      JSONObject j = new JSONObject(json);
      System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

      JSONArray res = j.getJSONObject("results").getJSONArray("bindings");

      return j.getJSONObject("results").getJSONArray("bindings").toString();

  
  }
  public String GroupSparqlQueryFilter(String type) {
  	
  	String NS = model.getNsPrefixURI("");
      
  	String qexec = "PREFIX ns: <http://reseau-social.com/>" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "SELECT ?group ?idGroupe ?nomGroupe ?date_CreationGroupe ?descriptionGroupe ?Regles " +
            "WHERE {" +
            "  ?group rdf:type ?type." +
            "  ?group ns:idGroupe ?idGroupe ." +
            "  ?group ns:nomGroupe ?nomGroupe ." +
            "  ?group ns:date_CreationGroupe ?date_CreationGroupe ." +
            "  ?group ns:descriptionGroupe ?descriptionGroupe ." +
            "  ?group ns:Regles ?Regles ." +
            "  FILTER (?type = ns:" + type + " )" +
            "}";

    Model model = JenaEngine.readModel("data/groupe.owl");

    QueryExecution qe = QueryExecutionFactory.create(qexec, model);
    ResultSet results = qe.execSelect();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    ResultSetFormatter.outputAsJSON(outputStream, results);

    String json = new String(outputStream.toByteArray());

    JSONObject j = new JSONObject(json);
    System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

    JSONArray res = j.getJSONObject("results").getJSONArray("bindings");

    return j.getJSONObject("results").getJSONArray("bindings").toString();
  
  }
  public String insertGroup(String nomGroupe, String regles, String dateCreationGroupe, String descriptionGroupe, int idGroupe) {
	    // Chargez votre modèle RDF
	    Model model = JenaEngine.readModel("data/groupe.owl");

	    // Définissez l'espace de noms
	    String NS = "http://reseau-social.com/";

	    // Écrivez la requête SPARQL INSERT en utilisant les attributs fournis
	    String queryString = "PREFIX ab: <http://reseau-social.com/>"
	            + "PREFIX dc: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
	            + "INSERT DATA {"
	            + "rdf:type ns:Groupe ;"
	            + "ns:nomGroupe \"" + nomGroupe + "\" ;"
	            + "ns:Regles \"" + regles + "\" ;"
	            + "ns:date_CreationGroupe \"" + dateCreationGroupe + "\" ;"
	            + "ns:descriptionGroupe \"" + descriptionGroupe + "\" ;"
	            + "ns:idGroupe " + idGroupe + " ."
	            + "}";


	    UpdateAction.parseExecute(queryString, model);

	    return "Groupe inséré avec succès.";
	}

  public String getdiscussion( String nomGroupe) {
		
		
	  String qexec = "PREFIX ns: <http://reseau-social.com/>" +
  		    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
  		    "SELECT ?discussion ?contenu ?dateCreation ?idDiscussion ?titre " +
  		    "WHERE {" +
  		    "  ?nomGroupe ns:estAssocieA ?discussion ." +
  		    "  ?nomGroupe ns:nomGroupe '" + nomGroupe + "' ." +
  		    "  ?discussion ns:contenuDiscussion ?contenu ." +
  		    "  ?discussion ns:date_CreationDiscussion ?dateCreation ." +
  		    "  ?discussion ns:idDiscussion ?idDiscussion ." +
  		    "  ?discussion ns:titreDiscussion ?titre ." +
  		    "}";


      
      Model model = JenaEngine.readModel("data/groupe.owl");

      QueryExecution qe = QueryExecutionFactory.create(qexec, model);
      ResultSet results = qe.execSelect();

      // write to a ByteArrayOutputStream
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      ResultSetFormatter.outputAsJSON(outputStream, results);

      // and turn that into a String
      String json = new String(outputStream.toByteArray());

      JSONObject j = new JSONObject(json);
      System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

      JSONArray res = j.getJSONObject("results").getJSONArray("bindings");


      return j.getJSONObject("results").getJSONArray("bindings").toString();

  }
  
  public String getuser( String user) {
		
		
	  String qexec = "PREFIX ns: <http://reseau-social.com/>" +
	  		    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
	  		    "SELECT ?groupe ?nomGroupe ?Regles ?date_CreationGroupe ?descriptionGroupe ?idGroupe " +
	  		    "WHERE {" +
	  		    "  ?nom ns:estAuteurDe ?groupe ." +
	  		    "  ?nom ns:nom '" + user + "' ." +
	  		    "  ?groupe ns:nomGroupe ?nomGroupe ." +
	  		    "  ?groupe ns:Regles ?Regles ." +
	  		    "  ?groupe ns:date_CreationGroupe ?date_CreationGroupe ." +
	  		    "  ?groupe ns:descriptionGroupe ?descriptionGroupe ." +
	  		    "  ?groupe ns:idGroupe ?idGroupe ." +

	  		    "}";


      
      Model model = JenaEngine.readModel("data/groupe.owl");

      QueryExecution qe = QueryExecutionFactory.create(qexec, model);
      ResultSet results = qe.execSelect();

      // write to a ByteArrayOutputStream
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      ResultSetFormatter.outputAsJSON(outputStream, results);

      // and turn that into a String
      String json = new String(outputStream.toByteArray());

      JSONObject j = new JSONObject(json);
      System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

      JSONArray res = j.getJSONObject("results").getJSONArray("bindings");


      return j.getJSONObject("results").getJSONArray("bindings").toString();

  }
  
  public String getgroupegereparuser( String user) {
		
		
	  String qexec = "PREFIX ns: <http://reseau-social.com/>" +
	  		    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
	  		    "SELECT ?groupe ?nomGroupe ?Regles ?date_CreationGroupe ?descriptionGroupe ?idGroupe " +
	  		    "WHERE {" +
	  		    "  ?nom ns:gere ?groupe ." +
	  		    "  ?nom ns:nom '" + user + "' ." +
	  		    "  ?groupe ns:nomGroupe ?nomGroupe ." +
	  		    "  ?groupe ns:Regles ?Regles ." +
	  		    "  ?groupe ns:date_CreationGroupe ?date_CreationGroupe ." +
	  		    "  ?groupe ns:descriptionGroupe ?descriptionGroupe ." +
	  		    "  ?groupe ns:idGroupe ?idGroupe ." +

	  		    "}";


      
      Model model = JenaEngine.readModel("data/groupe.owl");

      QueryExecution qe = QueryExecutionFactory.create(qexec, model);
      ResultSet results = qe.execSelect();

      // write to a ByteArrayOutputStream
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      ResultSetFormatter.outputAsJSON(outputStream, results);

      // and turn that into a String
      String json = new String(outputStream.toByteArray());

      JSONObject j = new JSONObject(json);
      System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

      JSONArray res = j.getJSONObject("results").getJSONArray("bindings");


      return j.getJSONObject("results").getJSONArray("bindings").toString();

  }
	
  public String getgroupepartageparuser( String user) {
		
		
	  String qexec = "PREFIX ns: <http://reseau-social.com/>" +
	  		    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
	  		    "SELECT ?groupe ?nomGroupe ?Regles ?date_CreationGroupe ?descriptionGroupe ?idGroupe " +
	  		    "WHERE {" +
	  		    "  ?nom ns:partage ?groupe ." +
	  		    "  ?nom ns:nom '" + user + "' ." +
	  		    "  ?groupe ns:nomGroupe ?nomGroupe ." +
	  		    "  ?groupe ns:Regles ?Regles ." +
	  		    "  ?groupe ns:date_CreationGroupe ?date_CreationGroupe ." +
	  		    "  ?groupe ns:descriptionGroupe ?descriptionGroupe ." +
	  		    "  ?groupe ns:idGroupe ?idGroupe ." +

	  		    "}";


      
      Model model = JenaEngine.readModel("data/groupe.owl");

      QueryExecution qe = QueryExecutionFactory.create(qexec, model);
      ResultSet results = qe.execSelect();

      // write to a ByteArrayOutputStream
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      ResultSetFormatter.outputAsJSON(outputStream, results);

      // and turn that into a String
      String json = new String(outputStream.toByteArray());

      JSONObject j = new JSONObject(json);
      System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

      JSONArray res = j.getJSONObject("results").getJSONArray("bindings");


      return j.getJSONObject("results").getJSONArray("bindings").toString();

  }
	
  public String getmemebregroupes( String user) {
		
		
	  String qexec = "PREFIX ns: <http://reseau-social.com/>" +
	  		    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
	  		    "SELECT ?groupe ?nomGroupe ?Regles ?date_CreationGroupe ?descriptionGroupe ?idGroupe " +
	  		    "WHERE {" +
	  		    "  ?nom ns:estAssocieA ?groupe ." +
	  		    "  ?nom ns:nom '" + user + "' ." +
	  		    "  ?groupe ns:nomGroupe ?nomGroupe ." +
	  		    "  ?groupe ns:Regles ?Regles ." +
	  		    "  ?groupe ns:date_CreationGroupe ?date_CreationGroupe ." +
	  		    "  ?groupe ns:descriptionGroupe ?descriptionGroupe ." +
	  		    "  ?groupe ns:idGroupe ?idGroupe ." +

	  		    "}";


      
      Model model = JenaEngine.readModel("data/groupe.owl");

      QueryExecution qe = QueryExecutionFactory.create(qexec, model);
      ResultSet results = qe.execSelect();

      // write to a ByteArrayOutputStream
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      ResultSetFormatter.outputAsJSON(outputStream, results);

      // and turn that into a String
      String json = new String(outputStream.toByteArray());

      JSONObject j = new JSONObject(json);
      System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

      JSONArray res = j.getJSONObject("results").getJSONArray("bindings");


      return j.getJSONObject("results").getJSONArray("bindings").toString();

  }
  
  
 

}