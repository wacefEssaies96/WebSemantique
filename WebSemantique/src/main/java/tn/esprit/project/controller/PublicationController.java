package tn.esprit.project.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.classe.Hashtag;
import tn.esprit.classe.Publication;
import tn.esprit.service.PublicationService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/publications")
public class PublicationController {

	@Autowired
    private PublicationService pubSer;

    @GetMapping("/getAllPub")
    public String getAllPubs() {
        return pubSer.getAllPublications();
    }
    
    @GetMapping("/pub/{idPub}")
    public String getById(@PathVariable("idPub") int idPub) {
        return pubSer.getPubById(idPub);
    }
    
    @PostMapping("/addPub")
    public String addPub(@RequestBody Publication p) throws FileNotFoundException {
        return pubSer.addNewPub(p);
    }
    
    @DeleteMapping("/deletePub/{idPub}")
    public String addPub(@PathVariable("idPub") int idPub) {
        return pubSer.deletePubById(idPub);
    }
    
    @PutMapping("/editPub")
    public String editPub(@RequestBody Publication p) throws FileNotFoundException {
        return pubSer.updatePub(p);
    }
}
