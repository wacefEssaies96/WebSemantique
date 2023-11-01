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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.classe.Page;
import tn.esprit.service.PageService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/Pages")
public class PageController {

	@Autowired
    private PageService pageServ;

    @GetMapping("/getAll")
    public String getAllPages() {
        return pageServ.getAllPages();
    }

    @GetMapping("/page/{id}")
    public String getById(@PathVariable("id") int id) {
        return pageServ.getPageById(id);
    }

    @PostMapping("/addPage")
    public String addPage(@RequestParam String type,@RequestBody Page p) throws FileNotFoundException {
        return pageServ.addNewPage(type, p);
    }

    @DeleteMapping("/delete/Page/{id}")
    public String deletePage(@PathVariable("id") int id) {
        return pageServ.deletePageById(id);
    }

    @PutMapping("/editPage")
    public String editPage(@RequestParam String type,@RequestBody Page p) throws FileNotFoundException {
        return pageServ.updatePage(type, p);
    }
}

