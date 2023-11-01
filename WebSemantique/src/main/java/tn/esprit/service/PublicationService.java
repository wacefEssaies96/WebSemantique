package tn.esprit.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import tn.esprit.classe.Publication;
import tn.esprit.tools.JenaEngine;

@Service
public class PublicationService {

	private Model model;
    
    @PostConstruct
    public void init() {
		model = JenaEngine.readModel("data/ReseauxSocial.owl");
    }
    
    public Model getModel() {
        return model;
    }
    
    public String getAllPublications() {
    	
    	String qexec = "PREFIX ns: <http://reseau-social.com/>"
    	        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
    	        + "SELECT ?idPub ?contenu ?dateCreationPub ?status "
    	        + "?visibilite "
    	        + " WHERE {"
    	        + "?Publication ns:idPub ?idPub ;"
    	        + " ns:contenu ?contenu ;"
    	        + " ns:dateCreationPub ?dateCreationPub ;"
    	        + " ns:status ?status ;"
    	        + " ns:visibilite ?visibilite ;"
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
    
    public static String formatToISOString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return sdf.format(date);
    }
    
    public String addNewPub(Publication p) throws FileNotFoundException {
    	
    	if (p == null) {
            return "Publication object is null.";
        }
    	
        Model modelPub = JenaEngine.readModel("data/ReseauxSocial.owl");
    	String NS = modelPub.getNsPrefixURI("");

        int idPub = p.idPub;
        Date dateCreationPub = new Date();
        String visibilite = p.visibilite;
        String contenu = p.contenu;
        String status = p.status;
        String typeOfPub = p.typeOfPub;

        String newPublicationURI = NS + "pub_with_id_" + idPub;
        
        String formattedDate = formatToISOString(dateCreationPub);
        
        String sparqlInsert = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX ns: <http://reseau-social.com/>" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                "INSERT DATA {" +
                " <"+ newPublicationURI +"> rdf:type ns:"+typeOfPub +" ;" +
                " ns:idPub '" + idPub + "' ;" +
                " ns:dateCreationPub '" + formattedDate + "'^^xsd:dateTime ;" +
                " ns:contenu '" + contenu + "' ;" +
                " ns:visibilite '" + visibilite + "' ;" +
                " ns:status '" + status + "' ." +
                "}";
        
        try {
            UpdateRequest updateRequest = UpdateFactory.create(sparqlInsert);
            UpdateAction.execute(updateRequest, modelPub);
            modelPub.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
        } catch (Exception e) {
            e.printStackTrace();
            return "Publication could not be added.";
        }
        
        String response = "Publication added successfully!";
            
    	return response;
    }
    
    public String getPubById(int idPub) {
    	String qexec = "PREFIX ns: <http://reseau-social.com/>"
    	        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
    	        + "SELECT ?publication ?contenu ?dateCreationPub ?status ?visibilite "
    	        + " WHERE {"
    	        + "?Publication ns:idPub '"+idPub+"' ;"
    	        + " ns:contenu ?contenu ;"
    	        + " ns:dateCreationPub ?dateCreationPub ;"
    	        + " ns:status ?status ;"
    	        + " ns:visibilite ?visibilite ;"
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
    
    public String deletePubById(int idPub) {
    	String pub = getPubById(idPub);
    	if(pub!=null) {
    		String deleteSparql = "PREFIX ns: <http://reseau-social.com/>" +
    			    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
    			    "DELETE WHERE {"
    			    + " ?pub ns:idPub '" + idPub + "' ."
    			    + " ?pub ns:contenu ?contenu ."
				    + " ?pub ns:dateCreationPub ?dateCreationPub ."
				    + " ?pub ns:status ?status ."
				    + " ?pub ns:visibilite ?visibilite ."
    			    + "}";

    		    Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

    		    UpdateRequest updateRequest = UpdateFactory.create(deleteSparql);

    		    try {
    		        UpdateAction.execute(updateRequest, model);
    		        model.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
    		        return "Publication deleted successfully!";
    		    } catch (Exception e) {
    		        e.printStackTrace();
    		        return "Error deleting publication.";
    		    }
    	}
    	return "Publication not found!";
    }
    
    public String updatePub(Publication p) {
    	if(p!=null) {
    		String deleteSparql = "PREFIX ns: <http://reseau-social.com/>" +
    			    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
    			    "DELETE WHERE {"
    			    + " ?pub rdf:type ns:"+p.typeOfPub +" ."
    			    + " ?pub ns:idPub '" + p.idPub + "' ."
    			    + " ?pub ns:contenu ?contenu ."
				    + " ?pub ns:dateCreationPub ?dateCreationPub ."
				    + " ?pub ns:status ?status ."
				    + " ?pub ns:visibilite ?visibilite ."
    			    + "}";

    		    Model model = JenaEngine.readModel("data/ReseauxSocial.owl");

    		    UpdateRequest deleteRequest = UpdateFactory.create(deleteSparql);
    		    
    	    	String NS = model.getNsPrefixURI("");

    	        int idPub = p.idPub;
    	        Date dateCreationPub = new Date();
    	        String visibilite = p.visibilite;
    	        String contenu = p.contenu;
    	        String status = p.status;
    	        String typeOfPub = p.typeOfPub;

    	        String newPublicationURI = NS + "pub_with_id_" + idPub;
    	        
    	        String formattedDate = formatToISOString(dateCreationPub);
    	        
    	        String sparqlInsert = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
    	                "PREFIX ns: <http://reseau-social.com/>" +
    	                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
    	                "INSERT DATA {" +
    	                " <"+ newPublicationURI +"> rdf:type ns:"+typeOfPub +" ;" +
    	                " ns:idPub '" + idPub + "' ;" +
    	                " ns:dateCreationPub '" + formattedDate + "'^^xsd:dateTime ;" +
    	                " ns:contenu '" + contenu + "' ;" +
    	                " ns:visibilite '" + visibilite + "' ;" +
    	                " ns:status '" + status + "' ." +
    	                "}";

        		UpdateRequest updateRequest = UpdateFactory.create(sparqlInsert);

    		    try {
    		        UpdateAction.execute(deleteRequest, model);
    		        model.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
    		        UpdateAction.execute(updateRequest, model);
    		        model.write(new FileOutputStream("data/ReseauxSocial.owl"), "RDF/XML");
    		        return "Publication updated successfully !";
    		    } catch (Exception e) {
    		        e.printStackTrace();
    		        return "Error updating publication !";
    		    }
    	}
    	return "Publication not found !";
    }
}
