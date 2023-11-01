package tn.esprit.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import tn.esprit.classe.Hashtag;
import tn.esprit.tools.JenaEngine;

@Service
public class HashtagService {

	private Model model;
	
	@PostConstruct
    public void init() {
		model = JenaEngine.readModel("data/ReseauxSocial.owl");
    }
    
    public Model getModel() {
        return model;
    }
    
    public String getAllHashs() {
    	
    	String qexec = "PREFIX ns: <http://reseau-social.com/>"
    	        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
    	        + "SELECT ?idHash ?description ?categorie ?nom ?popularite "
    	        + " WHERE {"
    	        + "?Hashtag ns:idHash ?idHash ;"
    	        + " ns:description ?description ;"
    	        + " ns:categorie ?categorie ;"
    	        + " ns:nom ?nom ;"
    	        + " ns:popularite ?popularite ;"
    	        + "}";

        Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        String json = new String(outputStream.toByteArray());

        JSONObject j = new JSONObject(json);
        
        System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

        return j.getJSONObject("results").getJSONArray("bindings").toString();
    }
    
    public String addNewHahs(Hashtag h) throws FileNotFoundException {
    	
    	if (h == null) {
            return "Hashtag object is null.";
        }
    	
        Model modelPub = JenaEngine.readModel("data/ReseauxSocial.owl");
    	String NS = modelPub.getNsPrefixURI("");

    	int idHash = h.idHash;
    	String description = h.description;
    	String categorie = h.categorie;
    	String nom = h.nom;
    	int popularite = h.popularite;

        String newHashURI = NS + "hash_with_id_" + idHash;
                
        String sparqlInsert = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX ns: <http://reseau-social.com/>" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                "INSERT DATA {" +
                " <"+ newHashURI +"> rdf:type ns:Hashtag ;" +
                " ns:idHash '" + idHash + "' ;" +
                " ns:description '" + description + "' ;" +
                " ns:categorie '" + categorie + "' ;" +
                " ns:nom '" + nom + "' ;" +
                " ns:popularite '" + popularite + "' ." +
                "}";
        
        try {
            UpdateRequest updateRequest = UpdateFactory.create(sparqlInsert);
            UpdateAction.execute(updateRequest, modelPub);
            modelPub.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
        } catch (Exception e) {
            e.printStackTrace();
            return "Hahstag could not be added.";
        }
        
        String response = "Hahstag added successfully!";
            
    	return response;
    }
    
    public String getHashByName(String nom) {
    	String qexec = "PREFIX ns: <http://reseau-social.com/>"+
    			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" 
    			+ "SELECT ?hash "
    			+ "WHERE {"
    			+ "?hash ns:nom '"+nom+"' ."
    			+ "}";

    	Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

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
    
    public String deletePubByName(String nom) {
    	String h = getHashByName(nom);
    	if(h!=null) {
    		String deleteSparql = "PREFIX ns: <http://reseau-social.com/>" +
    			    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
    			    "DELETE WHERE {"
    			    + " ?h rdf:type ns:Hashtag ."
    			    + " ?h ns:nom '" + nom + "' ."
    			    + " ?h ns:idHash ?idHash ."
				    + " ?h ns:description ?description ."
				    + " ?h ns:categorie ?categorie ."
				    + " ?h ns:popularite ?popularite ."
    			    + "}";

    		    Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

    		    UpdateRequest updateRequest = UpdateFactory.create(deleteSparql);

    		    try {
    		        UpdateAction.execute(updateRequest, model);
    		        model.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
    		        return "Hashtag deleted successfully!";
    		    } catch (Exception e) {
    		        e.printStackTrace();
    		        return "Error deleting hashtag.";
    		    }
    	}
    	return "Hashtag not found!";
    }
    
    public String updateHash(Hashtag h) {
    	if(h!=null) {
    		String deleteSparqlById = "PREFIX ns: <http://reseau-social.com/>" +
    			    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
    			    "DELETE WHERE {"
    			    + " ?h rdf:type ns:Hashtag ."
    			    + " ?h ns:nom ?nom ."
    			    + " ?h ns:idHash "+ h.idHash +" ."
				    + " ?h ns:description ?description ."
				    + " ?h ns:categorie ?categorie ."
				    + " ?h ns:popularite ?popularite ."
    			    + "}";

    		    Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

    		    UpdateRequest deleteRequest = UpdateFactory.create(deleteSparqlById);
    		    
    		    String NS = model.getNsPrefixURI("");

    	    	int idHash = h.idHash;
    	    	String description = h.description;
    	    	String categorie = h.categorie;
    	    	String nom = h.nom;
    	    	int popularite = h.popularite;

    	        String newHashURI = NS + "hash_with_id_" + idHash;

    		    String updateSparql = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
    	                "PREFIX ns: <http://reseau-social.com/>" +
    	                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
    	                "INSERT DATA {" +
    	                " <"+ newHashURI +"> rdf:type ns:Hashtag ;" +
    	                " ns:idHash '" + idHash + "' ;" +
    	                " ns:description '" + description + "' ;" +
    	                " ns:categorie '" + categorie + "' ;" +
    	                " ns:nom '" + nom + "' ;" +
    	                " ns:popularite '" + popularite + "' ." +
    	                "}";

        		    UpdateRequest updateRequest = UpdateFactory.create(updateSparql);

    		    try {
    		        UpdateAction.execute(deleteRequest, model);
    		        model.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
    		        UpdateAction.execute(updateRequest, model);
    		        model.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
    		        return "Hashtag updated successfully !";
    		    } catch (Exception e) {
    		        e.printStackTrace();
    		        return "Error updating hashtag !";
    		    }
    	}
    	return "Hashtag not found !";
    }
    
    public String getAllHashsPub() {
    	
    	String qexec = "PREFIX ns: <http://reseau-social.com/>"
    	        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
    	        + "SELECT ?idHash ?description ?categorie ?nom ?popularite ?pub "
    	        + " WHERE {"
    	        + "?Hashtag ns:idHash ?idHash ;"
    	        + " ns:description ?description ;"
    	        + " ns:categorie ?categorie ;"
    	        + " ns:nom ?nom ;"
    	        + " ns:popularite ?popularite ;"
    	        + " ns:est_associe_a ?pub ;" 
    	        + "}";

        Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        String json = new String(outputStream.toByteArray());

        JSONObject j = new JSONObject(json);
        
        System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

        return j.getJSONObject("results").getJSONArray("bindings").toString();
    }
}
