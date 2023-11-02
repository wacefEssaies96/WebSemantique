package tn.esprit.project.controller;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.UpdateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.classe.Commentaire;
import tn.esprit.service.RDFDataServicecommentaire;
import tn.esprit.service.RDFDataServiceemoji;
import tn.esprit.tools.JenaEngine;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/controlleremoji")
public class RDFControlleremoji {
    @Autowired
    private RDFDataServiceemoji rdfDataService;
   
    @GetMapping("/test")
    public void test() {
    	System.out.println("tese");
    }
   
    @GetMapping("/sparqlemoji")
    public String executeSparqlQueryemoji() {
        return rdfDataService.emojiSparqlQuery();
    }
   

    
    
  
    
    
    
    

}








