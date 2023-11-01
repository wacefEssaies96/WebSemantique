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
import tn.esprit.classe.Page;
import tn.esprit.tools.JenaEngine;

@Service
public class PageService {

	private Model model;

	@PostConstruct
    public void init() {
		model = JenaEngine.readModel("data/ReseauxSocial.owl");
    }

    public Model getModel() {
        return model;
    }

    public String getAllPages() {
    	String qexec = "PREFIX ns: <http://reseau-social.com/> "
    		    + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
    		    + "SELECT (str(?idPage) as ?idP) ?descriptionPage ?badge ?type "
    		    + "WHERE { "
    		    + "  { "
    		    + "    ?page rdf:type ns:PagePriveeClass . "
    		    + "    ?page ns:idPage ?idPage . "
    		    + "    OPTIONAL { ?page ns:descriptionPage ?descriptionPage } "
    		    + "    OPTIONAL { ?page ns:badge ?badge } "
    		    + "    BIND('prive' as ?type) "
    		    + "  } "
    		    + "  UNION "
    		    + "  { "
    		    + "    ?page rdf:type ns:PagePublicClass . "
    		    + "    ?page ns:idPage ?idPage . "
    		    + "    OPTIONAL { ?page ns:descriptionPage ?descriptionPage } "
    		    + "    OPTIONAL { ?page ns:badge ?badge } "
    		    + "    BIND('public' as ?type) "
    		    + "  } "
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

    public String addNewPage(String type,Page p) throws FileNotFoundException {

    	if (p == null) {
            return "Page object is null.";
        }

        Model model = JenaEngine.readModel("data/ReseauxSocial.owl");
    	String NS = model.getNsPrefixURI("");
    	
    	String pageType;
    	if ("prive".equals(type)) {
    	    pageType = "ns:PagePriveeClass";
    	} else {
    	    pageType = "ns:PagePublicClass";
    	}
    	
    	int idPage = p.idPage;
    	String descriptionPage = p.descriptionPage;
    	boolean badge = p.badge;

        String newPageURI = NS + "page_with_id_" + idPage;

        String sparqlInsert = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX ns: <http://reseau-social.com/>" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                "INSERT DATA {" +
                " <"+ newPageURI +"> rdf:type " + pageType + " ;" +
                " ns:idPage '" + idPage + "' ;" +
                " ns:descriptionPage '" + descriptionPage + "' ;" +
                " ns:badge '" + badge + "' ." +
                "}";

        try {
            UpdateRequest updateRequest = UpdateFactory.create(sparqlInsert);
            UpdateAction.execute(updateRequest, model);
            model.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
        } catch (Exception e) {
            e.printStackTrace();
            return "Page could not be added.";
        }

        String response = "Page added successfully!";

    	return response;
    }

    public String getPageById(int id) {
    	String qexec = "PREFIX ns: <http://reseau-social.com/> " +
    	    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
    	    "SELECT ?idPage ?descriptionPage ?badge ?type " +
    	    "WHERE { " +
    	    "    ?page rdf:type ?typec . " +
    	    "    ?page ns:idPage '" + id + "' . " +
			"    OPTIONAL { ?page ns:descriptionPage ?descriptionPage } " +
    	    "    OPTIONAL { ?page ns:badge ?badge } " +
    	    "    BIND(IF(?typec = ns:PagePriveeClass, 'prive', 'public') AS ?type) " +
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

    public boolean verifPageById(int id) {
        boolean result = false;
        String queryVerif = "PREFIX ns: <http://reseau-social.com/> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "ASK { " +
            "?resource rdf:type ?type . " + // Specify the variable name ?type
            "?resource ns:idPage '" + id + "' . " +
            "FILTER (?type = ns:PagePriveeClass || ?type = ns:PagePublicClass) " +
            "}";

        Model model = JenaEngine.readModel("data/ReseauxSocial.owl");
        try (QueryExecution qexec = QueryExecutionFactory.create(queryVerif, model)) {
            result = qexec.execAsk();
        }
        return result;
    }
    public String deletePageById(int id) {
    	if(verifPageById(id)) {
        String deleteSparql = "PREFIX ns: <http://reseau-social.com/> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "DELETE { " +
            "  ?resource ?p ?o . " +
            "} " +
            "WHERE { " +
            "  ?resource rdf:type ?type . " +
            "  ?resource ns:idPage '" + id + "' . " +
            "  FILTER (?type = ns:PagePriveeClass || ?type = ns:PagePublicClass) " +
            "  ?resource ?p ?o . " +
            "}";

    		    Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

    		    UpdateRequest updateRequest = UpdateFactory.create(deleteSparql);
    		    try {
    		        UpdateAction.execute(updateRequest, model);
    		        model.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
    		        return "Page deleted successfully!";
    		    } catch (Exception e) {
    		        e.printStackTrace();
    		        return "Error deleting Page.";
    		    }
    	}
    	return "Page not found!";
    }
    public String updatePage(String type,Page p) {
    	if(p!=null) {
			try {
				deletePageById(p.idPage);
				addNewPage(type,p);
				return "Page updated successfully !";
			} catch (Exception e) {
				e.printStackTrace();
				return "Error updating Page !";
			}
    	}
    	return "Page not found !";
    }
}