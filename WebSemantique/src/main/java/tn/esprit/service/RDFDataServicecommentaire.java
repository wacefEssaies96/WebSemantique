package tn.esprit.service;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.ResourceUtils;
import org.apache.jena.vocabulary.RDF;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import tn.esprit.classe.Commentaire;
import tn.esprit.tools.JenaEngine;
import org.apache.jena.update.UpdateAction;

@Service
public class RDFDataServicecommentaire {
    private Model model;
    
    @PostConstruct
    public void init() {
		model = JenaEngine.readModel("data/commentaire.owl");
    }
    
    public Model getModel() {
        return model;
    } public Model createOrGetModel() {
        // Vous pouvez implémenter la logique pour créer ou obtenir un modèle Jena ici
        // Assurez-vous que le modèle est disponible et prêt à être utilisé.
        return JenaEngine.readModel("data/commentaire.owl");
    }
    
    // Function to format Date to XSD dateTime format
    private String formatDateToXSDDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        return sdf.format(new Date());
    }

    
    
    
    public String deletecommentById(int idc) {
        String NS = "http://reseau-social.com/";

        String commentURI = NS + "comment_" + idc; // Construct the comment URI

        String deleteSparql =
            "PREFIX ns: <" + NS + "> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "DELETE WHERE { " +
            "   <" + commentURI + "> rdf:type ns:Commentaire ; " +
            "                ns:id " + idc + " ; " +
            "                ?p ?o . " +  // Match all properties and objects of the comment.
            "}";

        Model model = JenaEngine.readModel("data/commentaire.owl");

        UpdateRequest updateRequest = UpdateFactory.create(deleteSparql);

        try {
            UpdateAction.execute(updateRequest, model);
            model.write(new FileOutputStream("data/commentaire.owl"), "RDF/XML");
            return "Comment deleted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting comment.";
        }
    }
  


public String getCommentaireById(int commentId) {
    String NS = "http://reseau-social.com/";

    String sparqlQuery =
        "PREFIX ns: <" + NS + "> " +
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
        "SELECT ?typeContenu ?emojis ?dateCreation ?contenu " +
        "WHERE { " +
        "   ?comment rdf:type ns:Commentaire ; " +
        "            ns:id " + commentId + " ; " +
        "            ns:Type_Contenu ?typeContenu ; " +
        "            ns:emojis ?emojis ; " +
        "            ns:Date_creation ?dateCreation ; " +
        "            ns:contenu ?contenu . " +
        "}";

    // Read the RDF model
    Model model = FileManager.get().loadModel("data/commentaire.owl");

    // Execute the SPARQL query
    try (QueryExecution qe = QueryExecutionFactory.create(sparqlQuery, model)) {
        ResultSet results = qe.execSelect();

        // Convert the results to a JSON array
        JSONArray jsonArray = new JSONArray();

        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();
            jsonArray.put(soln.toString()); // You may need to format this as JSON objects
        }

        return jsonArray.toString();
    } catch (Exception e) {
        e.printStackTrace();
        return "Error processing the query";
    }
}    
    
    


    
    
    
    
    
    
/*public void addCommentToModel(Model model, String typeContenu, List<String> emojis, int id, Date dateCreation, String contenu) {
    String NS = "http://reseau-social.com/";

    // Création de la ressource de commentaire
    Resource newComment = model.createResource(NS + "comment_" + id);

    // Ajout des propriétés du commentaire
    newComment.addProperty(RDF.type, model.createResource(NS + "Commentaire"));
    newComment.addLiteral(model.createProperty(NS + "id"), id);
    newComment.addLiteral(model.createProperty(NS + "Date_creation"), model.createTypedLiteral(dateCreation, XSDDatatype.XSDdateTime));
    newComment.addLiteral(model.createProperty(NS + "contenu"), contenu);
    newComment.addLiteral(model.createProperty(NS + "Type_Contenu"), typeContenu);
    
    // Ajout des emojis sous forme de liste
    if (emojis != null) {
        emojis.forEach(emoji -> newComment.addLiteral(model.createProperty(NS + "emojis"), emoji));
    }
}*/
public void addCommentToModel(Model model, String typeContenu, List<String> emojis, int id, String contenu) {
    String NS = "http://reseau-social.com/";

    // Création de la ressource de commentaire
    Resource newComment = model.createResource(NS + "comment_" + id);
    Date currentDate = new Date();

    // Ajout des propriétés du commentaire
    newComment.addProperty(RDF.type, model.createResource(NS + "Commentaire"));
    newComment.addLiteral(model.createProperty(NS + "id"), id);
    newComment.addLiteral(model.createProperty(NS + "Date_creation"), model.createTypedLiteral(currentDate, XSDDatatype.XSDdateTime));
    newComment.addLiteral(model.createProperty(NS + "contenu"), contenu);
    newComment.addLiteral(model.createProperty(NS + "Type_Contenu"), typeContenu);
    
    // Ajout des emojis sous forme de liste
    if (emojis != null) {
        emojis.forEach(emoji -> newComment.addLiteral(model.createProperty(NS + "emojis"), emoji));
    }
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

        String qexec = "PREFIX ns: <" + NS + ">\n"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "SELECT (str(?id) as ?idc)"
                + "       (str(?date) as ?Date_creation)"
                + "       ?contenu"
                + "       ?emoji_symbole"
                + "       ?Type_Contenu"
                + " WHERE {"
                + "   ?commentaire rdf:type ns:Commentaire ."
                + "   ?commentaire ns:id ?id ."
                + "   ?commentaire ns:Date_creation ?date ."
                + "   OPTIONAL { ?commentaire ns:contenu ?contenu }"
                + "   OPTIONAL {"
                + "     ?emoji rdf:type ns:Emoji ."
                + "     ?emoji ns:est_associe_a ?commentaire ."
                + "     ?emoji ns:emoji_symbole ?emoji_symbole"
                + "   }"
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

/*public String CommentSparqlQuery() {
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
}*/
}