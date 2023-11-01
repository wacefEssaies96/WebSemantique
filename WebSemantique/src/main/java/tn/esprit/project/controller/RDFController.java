package tn.esprit.project.controller;

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
}

