package tn.esprit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.vocabulary.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import tn.esprit.tools.JenaEngine;

public class mainOWL {

	public static void main(String[] args) {


	    
		String ns = "http://reseau-social.com/";
		OntModel model = ModelFactory.createOntologyModel();
		model.setNsPrefix("", ns);

		// Define User Classes
		OntClass UtilisateurClass = model.createClass(ns + "UtilisateurClass");
		OntClass SimpleUtilisateurClass = model.createClass(ns + "SimpleUtilisateurClass");
		OntClass MembreClass = model.createClass(ns + "MembreClass");
		OntClass UtilisateurPrivilegieClass = model.createClass(ns + "UtilisateurPrivilegieClass");
		OntClass AdministrateurClass = model.createClass(ns + "AdministrateurClass");
		OntClass SuperAdministrateurClass = model.createClass(ns + "SuperAdministrateurClass");

		// Define Page Classes
		OntClass PageClass = model.createClass(ns + "PageClass");
		OntClass PagePriveeClass = model.createClass(ns + "PagePriveeClass");
		OntClass PagePublicClass = model.createClass(ns + "PagePublicClass");

		// Define Publicite Class
		OntClass PubliciteClass = model.createClass(ns + "Publicite");

		// Create Data Properties
		DatatypeProperty idPage = model.createDatatypeProperty(ns + "idPage");
		DatatypeProperty descriptionPage = model.createDatatypeProperty(ns + "descriptionPage");
		DatatypeProperty badge = model.createDatatypeProperty(ns + "badge");
		DatatypeProperty idPublicite = model.createDatatypeProperty(ns + "idPublicite");
		DatatypeProperty descriptionPublicite = model.createDatatypeProperty(ns + "descriptionPublicite");
		DatatypeProperty ImagePublicite = model.createDatatypeProperty(ns + "ImagePublicite");

		// Set Domain for Data Properties
		idPage.addDomain(PageClass);
		descriptionPage.addDomain(PageClass);
		badge.addDomain(PageClass);
		idPublicite.addDomain(PubliciteClass);
		descriptionPublicite.addDomain(PubliciteClass);
		ImagePublicite.addDomain(PubliciteClass);

		// Create Object Properties
		ObjectProperty est_auteurDe = model.createObjectProperty(ns + "est_auteurDe");
		ObjectProperty est_associe_a = model.createObjectProperty(ns + "est_associe_a");
		ObjectProperty partage = model.createObjectProperty(ns + "partage");

		// Create Individuals
		Individual membre1 = model.createIndividual(ns + "membre1", MembreClass);
		Individual superAdmin = model.createIndividual(ns + "superAdmin", SuperAdministrateurClass);
		Individual admin = model.createIndividual(ns + "admin", AdministrateurClass);
		Individual pub1 = model.createIndividual(ns + "pub1", PubliciteClass);
		Individual Esprit = model.createIndividual(ns + "Esprit", PagePublicClass);
		Individual _5TWIN4 = model.createIndividual(ns + "5TWIN4", PagePriveeClass);

		// Add Properties to Individuals
		Esprit.addProperty(idPage, "1");
		Esprit.addProperty(descriptionPage, "Esprit");
		_5TWIN4.addProperty(idPage, "2");
		_5TWIN4.addProperty(descriptionPage, "5TWIN4");
		pub1.addProperty(idPublicite, "1");
		pub1.addProperty(descriptionPublicite, "Publication Sport");
		membre1.addProperty(est_auteurDe, _5TWIN4);
		superAdmin.addProperty(partage, Esprit);
		pub1.addProperty(est_associe_a, _5TWIN4);

	System.out.print("Ontologie du reseau social partie page et publication");
	model.write(System.out,"RDF/XML");
	


	RDFDataMgr.write(System.out, model, Lang.NTRIPLES);
    FileOutputStream fichierSortie = null;

    try {
		fichierSortie = new FileOutputStream (new File ("data/Page.owl"));
    }
	catch (FileNotFoundException ex) {
		System.out.println("err create file");
    }

    model.write (fichierSortie);
    
	String NS = "";
	
	// lire le model a partir d'une ontologie
	Model model2 = JenaEngine.readModel("data/Page.owl");
	if (model2 != null) {
		//lire le Namespace de l’ontologie
		NS = model2.getNsPrefixURI("");
		// apply our rules on the owlInferencedModel
		Model inferedModel =
		
		JenaEngine.readInferencedModelFromRuleFile(model2, "data/rules.txt");
		// query on the model after inference
		System.out.println(JenaEngine.executeQueryFile(inferedModel,"data/query1.txt"));
	
	} else {
		System.out.println("Error when reading model from ontology");
	}
	
	
	}

}
