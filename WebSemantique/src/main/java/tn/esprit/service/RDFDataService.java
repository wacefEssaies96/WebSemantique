package tn.esprit.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import tn.esprit.classe.Evenement;
import tn.esprit.tools.JenaEngine;

@Service
public class RDFDataService {
    private Model model;
    
    @PostConstruct
    public void init() {
		model = JenaEngine.readModel("data/evenement.owl");
    }
    
    public Model getModel() {
        return model;
    }
    
    public String getAllEvenement() {
    	
        String NS = model.getNsPrefixURI("");
        String qexec = "PREFIX ns: <http://reseau-social.com/>"
    	        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
    	        + "SELECT ?idEvenement ?nomEvenement ?descriptionEvenement ?lieu "
    	        + " WHERE {"
    	        + "?Publication ns:idEvenement ?idEvenement ;"
    	        + " ns:nomEvenement ?nomEvenement ;"
    	        + " ns:descriptionEvenement ?descriptionEvenement ;"
    	        + " ns:lieu ?lieu ;"
    	        + "}";
        Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

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
    
    public String addNewEvent(Evenement e) throws FileNotFoundException {
    	
    	if (e == null) {
            return "Publication object is null.";
        }
    	
        //Model modelPub = JenaEngine.readModel("data/publication.owl");
    	String NS = model.getNsPrefixURI("");

        int idEvenement = e.idEvenement;
        String lieu = e.lieu;
        String nomEvenement = e.nomEvenement;
        String descriptionEvenement = e.descriptionEvenement;
        String typeEvenement = e.typeEvenement;

        String newEvenementURI = NS + "evenement_id_" + idEvenement;
                
        String sparqlInsert = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX ns: <http://reseau-social.com/>" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                "INSERT DATA {" +
                " <"+ newEvenementURI +"> rdf:type ns:"+typeEvenement +" ;" +
                " ns:idEvenement '" + idEvenement + "' ;" +
                " ns:lieu '" + lieu + "' ;" +
                " ns:nomEvenement '" + nomEvenement + "' ;" +
                " ns:descriptionEvenement '" + descriptionEvenement + "' ." +
                "}";
        
        try {
            UpdateRequest updateRequest = UpdateFactory.create(sparqlInsert);
            UpdateAction.execute(updateRequest, model);
            model.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Event could not be added.";
        }
        
        String response = "Event added successfully!";
            
    	return response;
    }
    
    public String getAllInvitation() {
    	
        String NS = model.getNsPrefixURI("");
        String qexec = "PREFIX ns: <http://reseau-social.com/>"
        		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
        		+ "SELECT ?invitation"
        		+ "WHERE {"
        		+ "	?invitation rdf:type invitation ."
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


}