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

import tn.esprit.models.Commentaire;
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
    @PutMapping("/editc/{commentId}")
    public String editCommentaire(
            @PathVariable("commentId") int commentId,
            @RequestBody Commentaire commentaireRequest
    ) {
    
    	return rdfDataService.updateCommentaire(commentaireRequest);
		
        
    }

    
    
  
    
    
    
    @PostMapping("/addcomment")
    public ResponseEntity<String> ajouterCommentaire(@RequestBody Commentaire commentaire) {
        try {
            String typeContenu = commentaire.getTypeContenu();
            List<String> emojis = commentaire.getEmojis();
            int id = commentaire.getId();
            Date dateCreation = commentaire.getDateCreation();
            String contenu = commentaire.getContenu();

            if (typeContenu == null || contenu == null) {
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("Des informations obligatoires sont manquantes.");
            }

            // Obtenez ou créez un modèle
            Model model = rdfDataService.createOrGetModel();

            // Utilisez le service pour ajouter le commentaire au modèle
            rdfDataService.addCommentToModel(model, typeContenu, emojis, id, contenu);

            // Écrire le modèle mis à jour dans le fichier RDF
            JenaEngine.writeModel(model, "data/commentaire.owl");

            return ResponseEntity.status(HttpStatus.SC_CREATED).body("Commentaire ajouté avec succès");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Une erreur s'est produite : " + ex.getMessage());
        }
    }
 
 
 
    @GetMapping("/comment/{idc}")
    public String getById(@PathVariable("idc") int idc) {
        return rdfDataService.getCommentaireById(idc);
    }
    

    @DeleteMapping("/deletecomment/{idc}")
    public String addPub(@PathVariable("idc") int idc) {
        return rdfDataService.deletecommentById(idc);
    }
   

}








