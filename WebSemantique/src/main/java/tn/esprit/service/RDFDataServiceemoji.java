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
import org.apache.jena.query.Query;
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
import org.apache.jena.util.ResourceUtils;
import org.apache.jena.vocabulary.RDF;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import tn.esprit.classe.Commentaire;
import tn.esprit.tools.JenaEngine;
import org.apache.jena.update.UpdateAction;

@Service
public class RDFDataServiceemoji {
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
    

    public String emojiSparqlQuery() {
        String ns = "http://reseau-social.com/";

        Model model = JenaEngine.readModel("data/commentaire.owl");
        model.read("data/commentaire.owl");

        String qexec = "PREFIX ns: <" + ns + ">\n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "SELECT ?commentaire_id ?emoji_symbole\n" +
            "WHERE {\n" +
            "   ?commentaire rdf:type ns:Commentaire .\n" +
            "   ?commentaire ns:id ?commentaire_id .\n" +
            "   ?emoji rdf:type ns:Emoji .\n" +
            "   ?emoji ns:est_associe_a ?commentaire .\n" +
            "   ?emoji ns:emoji_id ?emoji_id .\n" +
            "   ?emoji ns:emoji_symbole ?emoji_symbole .\n" +
            "}";

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