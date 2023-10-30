package tn.esprit.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import tn.esprit.tools.JenaEngine;

@Service
public class RDFDataService {
    private Model model;
    
    @PostConstruct
    public void init() {
    	
		model = JenaEngine.readModel("data/Page_Evenement_Commentaire_Group.owl");

    }
    
    public Model getModel() {
        return model;
    }
    public String PageSparqlQuery() {
    	
    	String NS = model.getNsPrefixURI("");
        String qexec = "PREFIX ns: <http://reseau-social.com/>"
        		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
        		+ "SELECT (str(?idPage) as ?idP) ?descriptionPage ?badge ?type"
        		+ "WHERE {"
        		+ "{"
        		+ "	?page rdf:type ns:PagePriveeClass ."
        		+ "	?page ns:idPage ?idPage ."
        		+ "OPTIONAL { ?page ns:descriptionPage ?descriptionPage }"
        		+ "OPTIONAL { ?page ns:badge ?badge }"
        		+ "BIND('prive' as ?type)"
        		+ "}"
        		+ "UNION"
        		+ "{"
        		+ "?page rdf:type ns:PagePublicClass ."
        		+ "?page ns:idPage ?idPage ."
        		+ "OPTIONAL { ?page ns:descriptionPage ?descriptionPage }"
        		+ "OPTIONAL { ?page ns:badge ?badge }"
        		+ "BIND('public' as ?type)"
        		+ "}"
        		+ "}"
        		;

        Model model = JenaEngine.readModel("data/Page_Evenement_Commentaire_Group.owl");

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

    
    
    public String CommentSparqlQuery() {
    	
    	String NS = model.getNsPrefixURI("");
        
        String qexec = "PREFIX ns: <http://reseau-social.com/>"
        		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
        		+ "SELECT (str(?id) as ?idc)"
        		+ "		   (str(?date) as ?Date_creation)"
        		+ "        ?contenu"
        		+ "        ?emojis"
        		+ "        ?Type_Contenu"
        		+ " WHERE {"
        		+ "   ?commentaire rdf:type ns:Commentaire ."
        		+ "   ?commentaire ns:id ?id ."
        		+ "   ?commentaire ns:Date_creation ?date ."
        		+ "   OPTIONAL { ?commentaire ns:contenu ?contenu }"
        		+ "   OPTIONAL { ?commentaire ns:emojis ?emojis }"
        		+ "   OPTIONAL { ?commentaire ns:Type_Contenu ?Type_Contenu }"
        		+ " }"
        		;

        Model model = JenaEngine.readModel("data/Page_Evenement_Commentaire_Group.owl");

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
        		+ "SELECT ?group ?nomGroupe "
        		+ "WHERE {"
        		+ "	?group rdf:type ns:Groupe ."
        		+ "	?group ns:nomGroupe ?nomGroupe ."
        		+ "}"
        		;

        Model model = JenaEngine.readModel("data/Page_Evenement_Commentaire_Group.owl");

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

    
    
    public String executeSampleSparqlQuery() {
    	
    	String NS = model.getNsPrefixURI("");
        String qexec = "PREFIX ns: <http://reseau-social.com/>"
        		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
        		+ "SELECT ?evenement ?nomEvenement"
        		+ "WHERE {"
        		+ "	?evenement rdf:type ns:Evenement ."
        		+ "	?evenement ns:nomEvenement ?nomEvenement ."
        		+ "}";

        Model model = JenaEngine.readModel("data/Page_Evenement_Commentaire_Group.owl");

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
}