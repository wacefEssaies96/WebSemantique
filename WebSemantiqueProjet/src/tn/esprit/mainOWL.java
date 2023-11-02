package tn.esprit;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.vocabulary.*;
import org.apache.jena.vocabulary.XSD;

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


import tn.esprit.tools.JenaEngine;
import org.apache.jena.rdf.model.*;
public class mainOWL {
	/**
	* @param args
	* the command line arguments
	*/
	public static void main(String[] args) {
		String ns= "http://reseau-social.com/";

        OntModel model = ModelFactory.createOntologyModel();
        model.setNsPrefix("", ns);

        OntClass utilisateurClass = model.createClass(ns + "Utilisateur");
        OntClass utilisateurPrivilegieClass = model.createClass(ns + "UtilisateurPrivilegie");
        utilisateurPrivilegieClass.addSuperClass(utilisateurClass);
        
        
        OntClass simpleUtilisateurClass = model.createClass(ns + "simpleUtilisateur");
        simpleUtilisateurClass.addSuperClass(utilisateurClass);
        
        
        OntClass SuperAdministrateurClass = model.createClass(ns + "SuperAdministrateur");
        SuperAdministrateurClass.addSuperClass(utilisateurClass);
        OntClass MembreClass = model.createClass(ns + "Membre");
        MembreClass.addSuperClass(simpleUtilisateurClass);

        OntClass CommentaireClass = model.createClass(ns + "Commentaire");
        OntClass EmojiClass = model.createClass(ns + "Emoji");

        // Créez les DatatypeProperties
        DatatypeProperty contenuProperty = model.createDatatypeProperty(ns + "contenu");
        DatatypeProperty dateCreationProperty = model.createDatatypeProperty(ns + "Date_creation");
        DatatypeProperty idProperty = model.createDatatypeProperty(ns + "id");
        DatatypeProperty emojisProperty = model.createDatatypeProperty(ns + "emojis");
        DatatypeProperty typeContenuProperty = model.createDatatypeProperty(ns + "Type_Contenu");

        // Associez les DatatypeProperties à la classe Commentaire
        contenuProperty.addDomain(CommentaireClass);
        dateCreationProperty.addDomain(CommentaireClass);
        idProperty.addDomain(CommentaireClass);
        emojisProperty.addDomain(CommentaireClass);
        typeContenuProperty.addDomain(CommentaireClass);

        Individual commentaire1 = model.createIndividual(ns + "commentaire1", CommentaireClass);
        Individual commentaire2 = model.createIndividual(ns + "commentaire2", CommentaireClass);

        // Associez les valeurs aux propriétés de données pour les individus
        commentaire1.addProperty(contenuProperty, "Contenu du commentaire 1");
       Literal dateCreation1 = model.createTypedLiteral("2023-10-25T09:00:00", XSDDatatype.XSDdateTime);
        commentaire1.addProperty(dateCreationProperty, dateCreation1);
        commentaire1.addProperty(idProperty, "1",XSDDatatype.XSDinteger);
        Literal emojis1 = model.createTypedLiteral("😊,👍,❤", XSDDatatype.XSDstring);
        commentaire1.addProperty(emojisProperty, emojis1);
        commentaire1.addProperty(typeContenuProperty, "Type de contenu 1");

        ObjectProperty est_auteurDe = model.createObjectProperty(ns + "est_auteurDe");
        ObjectProperty est_associe_a = model.createObjectProperty(ns + "est_associe_a");
        ObjectProperty gere = model.createObjectProperty(ns + "gere");
        ObjectProperty repond_a = model.createObjectProperty(ns + "repond_a");

        Individual membre1 = model.createIndividual(ns + "membre1", MembreClass);
        Individual utilisateurprivilegie1 = model.createIndividual(ns + "utilisateurPrivilegie1", utilisateurPrivilegieClass);
        Individual superadmin1 = model.createIndividual(ns + "superAdministrateur1", SuperAdministrateurClass);
        Individual emoji1 = model.createIndividual(ns + "emoji1", EmojiClass);

        superadmin1.addProperty(gere, commentaire1);
        utilisateurprivilegie1.addProperty(est_auteurDe, commentaire1);
        membre1.addProperty(est_auteurDe, commentaire2);
        membre1.addProperty(repond_a, commentaire1);
        utilisateurprivilegie1.addProperty(repond_a, commentaire2);
        emoji1.addProperty(est_associe_a, commentaire1);

        System.out.println("Ontologie du reseau social");
        model.write(System.out, "RDF/XML");

	    
		String ns = "http://reseau-social.com/";
		OntModel model = ModelFactory.createOntologyModel();
		model.setNsPrefix("",ns);
		
		OntClass Groupe = model.createClass(ns + "Groupe");
		OntClass Discussion = model.createClass(ns + "Discussion");
		OntClass GroupeEducation = model.createClass(ns + "GroupeEducation");
		OntClass GroupePrive = model.createClass(ns + "GroupePrive");
		OntClass GroupePublic = model.createClass(ns + "GroupePublic");
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

		OntClass Evenement = model.createClass(ns + "Evenement");
		OntClass InvitationClass = model.createClass(ns + "Invitation");
		OntClass EvenementCulturel = model.createClass(ns + "EvenementCulturel");
		OntClass EvenementSocial = model.createClass(ns + "EvenementSocial");
		OntClass EvenementSportif = model.createClass(ns + "EvenementSportif");
	
		OntClass Utilisateur = model.createClass(ns + "Utilisateur");
		OntClass SimpleUtilisateur = model.createClass(ns + "SimpleUtilisateur");
		OntClass UtilisateurPrivilege = model.createClass(ns + "UtilisateurPrivilege");
		OntClass SuperAdministrateur = model.createClass(ns + "SuperAdministrateur");
		OntClass Membre = model.createClass(ns + "Membre");
			
		GroupeEducation.addSuperClass(Groupe);
		GroupePrive.addSuperClass(Groupe);
		GroupePublic.addSuperClass(Groupe);

		SimpleUtilisateur.addSuperClass(Utilisateur);
		UtilisateurPrivilege.addSuperClass(Utilisateur);
		Membre.addSuperClass(SimpleUtilisateur);
		SuperAdministrateur.addSuperClass(UtilisateurPrivilege);
			
//		ObjectProperty reagitA = model.createObjectProperty(ns + "reagitA");
		ObjectProperty aime = model.createObjectProperty(ns + "aime");
		ObjectProperty estAuteurDe = model.createObjectProperty(ns + "estAuteurDe");
		ObjectProperty gere = model.createObjectProperty(ns + "gere");
		ObjectProperty estAssocieA = model.createObjectProperty(ns + "estAssocieA");
		ObjectProperty partagemembre = model.createObjectProperty(ns + "partagemembre");

//		aime.addSuperProperty(reagitA);
			
		Individual groupe1 = model.createIndividual(ns + "groupe1", Groupe);
		Individual groupe2 = model.createIndividual(ns + "groupe2", GroupePrive);

		Individual discussion = model.createIndividual(ns + "discussion", Discussion);
		
		Individual privilegeUser = model.createIndividual(ns + "privilegeUser", UtilisateurPrivilege);
		Individual membre = model.createIndividual(ns + "membre", Membre);
					
		superAdmin.addProperty(gere, groupe1);
		superAdmin.addProperty(gere, discussion);
		privilegeUser.addProperty(estAuteurDe, groupe2);
		membre.addProperty(partage, groupe1);
		groupe1.addProperty(partagemembre, groupe2);
		 groupe1.addProperty(estAssocieA, discussion);

		
		DatatypeProperty idGroupe = model.createDatatypeProperty(ns + "idGroupe");
		DatatypeProperty date_CreationGroupe = model.createDatatypeProperty(ns + "date_CreationGroupe");
		DatatypeProperty Regles = model.createDatatypeProperty(ns + "Regles");
		DatatypeProperty nomGroupe = model.createDatatypeProperty(ns + "nomGroupe");
		DatatypeProperty descriptionGroupe = model.createDatatypeProperty(ns + "descriptionGroupe");
		
		DatatypeProperty idDiscussion = model.createDatatypeProperty(ns + "idDiscussion");
		DatatypeProperty titreDiscussion = model.createDatatypeProperty(ns + "titreDiscussion");
		DatatypeProperty date_CreationDiscussion = model.createDatatypeProperty(ns + "date_CreationDiscussion");
		DatatypeProperty contenuDiscussion = model.createDatatypeProperty(ns + "contenuDiscussion");

		idGroupe.addDomain(Groupe);
		idGroupe.addRange(XSD.integer);
		date_CreationGroupe.addDomain(Groupe);
		date_CreationGroupe.addRange(XSD.dateTime);
		Regles.addDomain(Groupe);
		Regles.addRange(XSD.xstring);
		nomGroupe.addDomain(Groupe);
		nomGroupe.addRange(XSD.xstring);
		descriptionGroupe.addDomain(Groupe);
		descriptionGroupe.addRange(XSD.xstring);
		
		
		idDiscussion.addDomain(Discussion);
		idDiscussion.addRange(XSD.integer);
		titreDiscussion.addDomain(Discussion);
		titreDiscussion.addRange(XSD.xstring);
		date_CreationDiscussion.addDomain(Discussion);

		date_CreationDiscussion.addRange(XSD.dateTime);
		contenuDiscussion.addDomain(Discussion);
		contenuDiscussion.addRange(XSD.xstring);
		


	        groupe1.addProperty(partagemembre, groupe2);		
		System.out.print("Ontologie de l'evenement est : ");
		model.write(System.out,"RDF/XML");
		String NS = "";
		Model modelEvenement = JenaEngine.readModel("data/evenement.owl");
		if (model != null) {
	System.out.print("Ontologie du reseau social partie page et publication");
	model.write(System.out,"RDF/XML");
	


	RDFDataMgr.write(System.out, model, Lang.NTRIPLES);
    FileOutputStream fichierSortie = null;

    try {
		fichierSortie = new FileOutputStream (new File ("data/Page_Evenement.owl"));
    }
	catch (FileNotFoundException ex) {
		System.out.println("err create file");
    }

    model.write (fichierSortie);
    
	String NS = "";
	
	// lire le model a partir d'une ontologie

	Model model2 = JenaEngine.readModel("data/Page_Evenement.owl");
	if (model2 != null) {
		//lire le Namespace de l’ontologie
		NS = model2.getNsPrefixURI("");
		// apply our rules on the owlInferencedModel
		Model inferedModel =
		
		JenaEngine.readInferencedModelFromRuleFile(model2, "data/rules.txt");
		// query on the model after inference
		System.out.println(JenaEngine.executeQueryFile(inferedModel,"data/query3.txt"));
	
	} else {
		System.out.println("Error when reading model from ontology");
	}
	
		
		

	
	}
}

