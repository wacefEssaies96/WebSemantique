package tn.esprit.project.controller;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.function.library.date;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.classe.Evenement;
import tn.esprit.service.RDFDataService;
import tn.esprit.tools.JenaEngine;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/controller")
public class RDFController {
    @Autowired
    private RDFDataService rdfDataService;
    
    
    @GetMapping("/get-all-events")
    public String executeSparqlQuery() {
        return rdfDataService.getAllEvenement();
    }
    
    @PostMapping("/add-event")
    public String addPub(@RequestBody Evenement e) throws FileNotFoundException {
        return rdfDataService.addNewEvent(e);
    }
    
    @DeleteMapping("/delete-event/{idEvenement}")
    public String addPub(@PathVariable("idEvenement") int idEvenement) {
        return rdfDataService.deleteEventById(idEvenement);
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
    @GetMapping("/groupe")
    public String GroupSparqlQuery() {
        return rdfDataService.GroupSparqlQuery();
    }
    @GetMapping("/groupefilter/{type}")
    public String GroupSparqlQueryFilter(@PathVariable(value = "type") String type) {
          return rdfDataService.GroupSparqlQueryFilter(type); 
    }

    @GetMapping("/grpdiscussion/{nomGroupe}")
    public String getdiscussion(@PathVariable(value = "nomGroupe") String nomGroupe) {
        return rdfDataService.getdiscussion(nomGroupe); 

    	
    }
    @GetMapping("/grpuser/{user}")

    public String getuser( @PathVariable(value = "user") String user) {
    	return rdfDataService.getuser(user);
    }
    
    @GetMapping("/grpgruser/{user}")
     public String getgroupegereparuser( @PathVariable(value = "user") String user) {
    	return rdfDataService.getgroupegereparuser(user);

    }
    
    @GetMapping("/grppuser/{user}")
    public String getgroupepartageparuser ( @PathVariable(value = "user") String user) {
   	return rdfDataService.getgroupepartageparuser(user);

   }

    @PostMapping("/insertgrp")
    public ResponseEntity<String> insertGroup(@RequestParam String nomGroupe, @RequestParam String regles, @RequestParam String dateCreationGroupe, @RequestParam String descriptionGroupe, @RequestParam int idGroupe) {
        String result = rdfDataService.insertGroup(nomGroupe, regles, dateCreationGroupe, descriptionGroupe, idGroupe);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/grpmembre/{groupe}")

    public String getmemebregroupes(@PathVariable(value = "user") String user) {
       	return rdfDataService.getmemebregroupes(user);
    }
    @GetMapping("/users")
    public String UserSparqlQuery() {
    	return rdfDataService.UserSparqlQuery();

} 
   

}

