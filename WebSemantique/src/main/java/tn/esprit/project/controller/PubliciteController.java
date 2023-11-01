package tn.esprit.project.controller;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.classe.Publicite;
import tn.esprit.service.PubliciteService;
import tn.esprit.service.RDFDataService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/Publicites")
public class PubliciteController {
	@Autowired
    private PubliciteService publiciteServ;

    @GetMapping("/getAll")
    public String getAllPublicites() {
        return publiciteServ.getAllPublicites();
    }

    @GetMapping("/publicite/{id}")
    public String getById(@PathVariable("id") int id) {
        return publiciteServ.getPubliciteById(id);
    }

    @PostMapping("/addPublicite")
    public String addPublicite(@RequestBody Publicite p) throws FileNotFoundException {
        return publiciteServ.addNewPublicite(p);
    }

    @DeleteMapping("/delete/Publicite/{id}")
    public String deletePublicite(@PathVariable("id") int id) {
        return publiciteServ.deletePubliciteById(id);
    }

    @PutMapping("/editPublicite")
    public String editPublicite(@RequestBody Publicite p) throws FileNotFoundException {
        return publiciteServ.updatePublicite(p);
    }
}

