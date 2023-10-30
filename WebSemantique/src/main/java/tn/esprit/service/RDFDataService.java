package tn.esprit.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import tn.esprit.tools.JenaEngine;

@Service
public class RDFDataService {
    private Model model;
    
    @PostConstruct
    public void init() {
        /*model = ModelFactory.createDefaultModel();
        model.read("data/evenement.owl");*/
    	
		model = JenaEngine.readModel("data/evenement.owl");

    }
    
    public Model getModel() {
        return model;
    }
    
    public String executeSampleSparqlQuery() {
    	
    	String NS = "";
    	if (model != null) {
    		//lire le Namespace de l’ontologie
    		NS = model.getNsPrefixURI("");
    		// apply our rules on the owlInferencedModel
    		Model inferedModel =

    		JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
    		// query on the model after inference
    		return JenaEngine.executeQueryFile(inferedModel,"data/query.txt");
    		/*System.out.println(JenaEngine.executeQueryFile(inferedModel,

    		"data/query.txt"));*/

    		} else {
    		System.out.println("Error when reading model from ontology");
    		}
    	return null;
    		/*String sparqlQuery = "PREFIX ns: <http://reseau-social.com/>"
        			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
        			+ "SELECT ?hashtag"
        			+ "WHERE {"
        			+ "?hashtag rdf:type ns:Hashtag ."
        			+ "?hashtag ns:estAssocieA ns:pub1 ."
        			+ "}";*/
    		/*"PREFIX ns: <http://reseau-social.com/>"
    		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
    		+ "SELECT ?evenement"
    		+ "WHERE {"
    		+ "	?evenement rdf:type ns:Evenement ."
    		+ "}";*/
            /*Query query = QueryFactory.create(sparqlQuery);
            List<QuerySolution> results = new ArrayList();
            try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = queryExecution.execSelect();
                while (resultSet.hasNext()) {
                    results.add(resultSet.nextSolution());
                }
            }
            return results;*/
            
        /*String queryString = 
        		"PREFIX ns: <http://reseau-social.com/>"
        		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
        		+ "SELECT ?evenement ?nomEvenement"
        		+ "WHERE {"
        		+ "	?evenement rdf:type ns:Evenement ."
        		+ "	?evenement ns:nomEvenement ?nomEvenement ."
        		+ "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {
            return queryExecution.execSelect();
        }*/
    }
}