package tn.esprit.service;

import java.io.ByteArrayOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import tn.esprit.tools.JenaEngine;

@Service
public class RDFDataService {
    private Model model;
    
    @PostConstruct
    public void init() {
		model = JenaEngine.readModel("data/commentaire.owl");
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

public String CommentSparqlQuery() {
    String NS = model.getNsPrefixURI("");

    String qexec = "PREFIX ns: <http://reseau-social.com/>"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
            + "SELECT (str(?id) as ?idc)"
            + "       (str(?date) as ?Date_creation)"
            + "       ?contenu"
            + "       ?emojis"
            + "       ?Type_Contenu"
            + " WHERE {"
            + "   ?commentaire rdf:type ns:Commentaire ."
            + "   ?commentaire ns:id ?id ."
            + "   ?commentaire ns:Date_creation ?date ."
            + "   OPTIONAL { ?commentaire ns:contenu ?contenu }"
            + "   OPTIONAL { ?commentaire ns:emojis ?emojis }"
            + "   OPTIONAL { ?commentaire ns:Type_Contenu ?Type_Contenu }"
            + "}";

    Model model = JenaEngine.readModel("data/commentaire.owl");

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