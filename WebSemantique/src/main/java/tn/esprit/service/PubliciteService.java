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
import tn.esprit.classe.Publicite;
import tn.esprit.tools.JenaEngine;

@Service
public class PubliciteService {

	private Model model;

	@PostConstruct
    public void init() {
		model = JenaEngine.readModel("data/ReseauxSocial.owl");
    }

    public Model getModel() {
        return model;
    }

    public String getAllPublicites() {
    	String qexec = "PREFIX ns: <http://reseau-social.com/> "
    		    + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
    		    + "SELECT (str(?idPublicite) as ?idP) ?descriptionPublicite ?ImagePublicite "
    		    + "WHERE { "
    		    + "    ?publicite rdf:type ns:PubliciteClass . "
    		    + "    ?publicite ns:idPublicite ?idPublicite . "
    		    + "    OPTIONAL { ?publicite ns:descriptionPublicite ?descriptionPublicite } "
    		    + "    OPTIONAL { ?publicite ns:ImagePublicite ?ImagePublicite } "
    		    + "}";

        Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        String json = new String(outputStream.toByteArray());

        JSONObject j = new JSONObject(json);

        //System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

        return j.getJSONObject("results").getJSONArray("bindings").toString();
    }

    public String addNewPublicite(Publicite p) throws FileNotFoundException {

    	if (p == null) {
            return "Publicite object is null.";
        }

        Model model = JenaEngine.readModel("data/ReseauxSocial.owl");
    	String NS = model.getNsPrefixURI("");
    	
    	
    	int idPublicite = p.idPublicite;
    	String descriptionPublicite = p.descriptionPublicite;
    	String ImagePublicite = p.ImagePublicite;

        String newPubliciteURI = NS + "publicite_with_id_" + idPublicite;

        String sparqlInsert = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX ns: <http://reseau-social.com/>" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                "INSERT DATA {" +
                " <"+ newPubliciteURI +"> rdf:type  ns:PubliciteClass ;" +
                " ns:idPublicite '" + idPublicite + "' ;" +
                " ns:descriptionPublicite '" + descriptionPublicite + "' ;" +
                " ns:ImagePublicite '" + ImagePublicite + "' ." +
                "}";

        try {
            UpdateRequest updateRequest = UpdateFactory.create(sparqlInsert);
            UpdateAction.execute(updateRequest, model);
            model.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
        } catch (Exception e) {
            e.printStackTrace();
            return "Publicite  could not be added.";
        }

        String response = "Publicite  added successfully!";

    	return response;
    }

    public String getPubliciteById(int id) {
    	String qexec = "PREFIX ns: <http://reseau-social.com/> " +
    	    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		    "SELECT ?idPublicite ?descriptionPublicite ?ImagePublicite "+
    	    "WHERE { " +
    	    "    ?publicite rdf:type ns:PubliciteClass . " +
    	    "    ?publicite ns:idPublicite '" + id + "' . " +
    	    "    OPTIONAL { ?publicite ns:descriptionPublicite ?descriptionPublicite } " +
    	    "    OPTIONAL { ?publicite ns:ImagePublicite ?ImagePublicite } " +
    	    "}";

    	Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        JSONObject j = new JSONObject(json);
        //System.out.println(j.getJSONObject("results").getJSONArray("bindings"));

        JSONArray res = j.getJSONObject("results").getJSONArray("bindings");

        return j.getJSONObject("results").getJSONArray("bindings").toString();
    }

    public boolean verifPubliciteById(int id) {
        boolean result = false;
        String queryVerif = "PREFIX ns: <http://reseau-social.com/> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "ASK { " +
            "?resource rdf:type ns:PubliciteClass . " + // Specify the variable name ?type
            "?resource ns:idPublicite'" + id + "' . " +
            "}";

        Model model = JenaEngine.readModel("data/ReseauxSocial.owl");
        try (QueryExecution qexec = QueryExecutionFactory.create(queryVerif, model)) {
            result = qexec.execAsk();
        }
        return result;
    }
    public String deletePubliciteById(int id) {
    	if(verifPubliciteById(id)) {
        String deleteSparql = "PREFIX ns: <http://reseau-social.com/> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "DELETE { " +
            "  ?resource ?p ?o . " +
            "} " +
            "WHERE { " +
            "  ?resource rdf:type ns:PubliciteClass . " +
            "  ?resource ns:idPublicite '" + id + "' . " +
            "  ?resource ?p ?o . " +
            "}";

    		    Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

    		    UpdateRequest updateRequest = UpdateFactory.create(deleteSparql);
    		    try {
    		        UpdateAction.execute(updateRequest, model);
    		        model.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
    		        return "Publicite deleted successfully!";
    		    } catch (Exception e) {
    		        e.printStackTrace();
    		        return "Error deleting Publicite.";
    		    }
    	}
    	return "Publicite not found!";
    }
    public String updatePublicite(Publicite p) {
    	if(p!=null) {
			try {
				deletePubliciteById(p.idPublicite);
				addNewPublicite(p);
				return "Publicite updated successfully !";
			} catch (Exception e) {
				e.printStackTrace();
				return "Error updating Publicite !";
			}
    	}
    	return "Publicite not found !";
    }
}