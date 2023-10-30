package tn.esprit.project.controller;
import java.util.List;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.service.RDFDataService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/controller")
public class RDFController {
    @Autowired
    private RDFDataService rdfDataService;
    
    @GetMapping("/test")
    public void test() {
    	System.out.println("tese");
    }
    @GetMapping("/sparql")
    public String executeSparqlQuery() {
        return rdfDataService.executeSampleSparqlQuery();
    }
    @GetMapping("/sparqlPage")
    public String PageSparqlQuery() {
        return rdfDataService.PageSparqlQuery();
    }
    @GetMapping("/sparqlComment")
    public String CommentSparqlQuery() {
        return rdfDataService.CommentSparqlQuery();
    }
    @GetMapping("/sparqlGroup")
    public String GroupSparqlQuery() {
        return rdfDataService.GroupSparqlQuery();
    }
}

