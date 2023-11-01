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
import tn.esprit.service.HashtagService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/hashtags")
public class HashtagController {

	@Autowired
    private HashtagService hashSer;

    @GetMapping("/getAllHash")
    public String getAllHashs() {
        return hashSer.getAllHashs();
    }
    
    @GetMapping("/hash/{name}")
    public String getByName(@PathVariable("name") String name) {
        return hashSer.getHashByName(name);
    }
    
    @PostMapping("/addHash")
    public String addHash(@RequestBody Hashtag h) throws FileNotFoundException {
        return hashSer.addNewHahs(h);
    }
    
    @DeleteMapping("/deleteHash/{name}")
    public String deleteHash(@PathVariable("name") String name) {
        return hashSer.deletePubByName(name);
    }
    
    @PutMapping("/editHash")
    public String editHash(@RequestBody Hashtag h) throws FileNotFoundException {
        return hashSer.updateHash(h);
    }
    
    @GetMapping("/getAllHashsPub")
    public String getAllHashsPub() {
        return hashSer.getAllHashsPub();
    }
}
