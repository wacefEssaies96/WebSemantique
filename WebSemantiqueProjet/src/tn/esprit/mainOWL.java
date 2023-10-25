package tn.esprit;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.vocabulary.*;

public class mainOWL {

	public static void main(String[] args) {

	String ns= "http://reseau-social.com/";
	OntModel model = ModelFactory.createOntologyModel();
	
	model.setNsPrefix("",ns);

    OntClass UtilisateurClass = model.createClass(ns + "UtilisateurClass");
    OntClass SimpleUtilisateurClass = model.createClass(ns + "SimpleUtilisateurClass");
    OntClass MembreClass = model.createClass(ns + "MembreClass");

    OntClass UtilisateurPrivilegieClass = model.createClass(ns + "UtilisateurPrivilegieClass");
    OntClass AdministrateurClass = model.createClass(ns + "AdministrateurClass");
    OntClass SuperAdministrateurClass = model.createClass(ns + "SuperAdministrateurClass");
    
	OntClass PageClass = model.createClass(ns + "PageClass");
	OntClass PagePriveeClass = model.createClass(ns + "PagePriveeClass");
	OntClass PagePublicClass = model.createClass(ns + "PagePublicClass");

	OntClass PubliciteClass = model.createClass(ns + "Publicite");
   
	SimpleUtilisateurClass.addSuperClass(UtilisateurClass);
	MembreClass.addSuperClass(SimpleUtilisateurClass);
	UtilisateurPrivilegieClass.addSuperClass(UtilisateurClass);
	AdministrateurClass.addSuperClass(UtilisateurPrivilegieClass);
	SuperAdministrateurClass.addSuperClass(UtilisateurPrivilegieClass);

	PagePriveeClass.addSuperClass(PageClass);	
	PagePublicClass.addSuperClass(PageClass);	


    DatatypeProperty idPage = model.createDatatypeProperty(ns + "idPage");
    DatatypeProperty descriptionPage = model.createDatatypeProperty(ns + "descriptionPage");
    DatatypeProperty badge = model.createDatatypeProperty(ns + "badge");
    
    DatatypeProperty idPublicite = model.createDatatypeProperty(ns + "idPublicite");
    DatatypeProperty descriptionPublicite = model.createDatatypeProperty(ns + "descriptionPublicite");
    DatatypeProperty ImagePublicite = model.createDatatypeProperty(ns + "ImagePublicite");

    idPage.addDomain(PageClass);
    descriptionPage.addDomain(PageClass);
    badge.addDomain(PageClass);

    idPublicite.addDomain(PubliciteClass);
    descriptionPublicite.addDomain(PubliciteClass);
    ImagePublicite.addDomain(PubliciteClass);
    
	ObjectProperty est_auteurDe = model.createObjectProperty(ns + "est_auteurDe");
	ObjectProperty est_associe_a = model.createObjectProperty(ns + "est_associe_a");
	ObjectProperty partage = model.createObjectProperty(ns + "partage");

	Individual membre1 = model.createIndividual(ns,MembreClass);
	Individual superAdmin = model.createIndividual(ns,SuperAdministrateurClass);
	Individual admin = model.createIndividual(ns,AdministrateurClass);

	Individual pub1 = model.createIndividual(ns,PubliciteClass);
	Individual Esprit = model.createIndividual(ns,PagePublicClass);
	Individual _5TWIN4 = model.createIndividual(ns,PagePriveeClass);
	
    Esprit.addProperty(idPage, "1", XSDDatatype.XSDint);
    Esprit.addProperty(descriptionPage, "Esprit", XSDDatatype.XSDstring);
    _5TWIN4.addProperty(idPage, "2", XSDDatatype.XSDint);
    _5TWIN4.addProperty(descriptionPage, "5TWIN4", XSDDatatype.XSDstring);

    pub1.addProperty(idPublicite, "1", XSDDatatype.XSDint);
    pub1.addProperty(descriptionPublicite, "Publication Sport", XSDDatatype.XSDstring);

	membre1.addProperty(est_auteurDe, _5TWIN4);
	superAdmin.addProperty(partage, Esprit);
	pub1.addProperty(est_associe_a, _5TWIN4);
	

	System.out.print("Ontologie du reseau social partie page et publication");
	model.write(System.out,"RDF/XML");
	
	
	}

}
