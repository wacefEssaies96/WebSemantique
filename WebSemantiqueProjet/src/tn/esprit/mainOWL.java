package tn.esprit;
import org.apache.jena.datatypes.xsd.XSDDatatype;
//import org.apache.jena.vocabulary.*;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.vocabulary.*;

import tn.esprit.tools.JenaEngine;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;


import tn.esprit.tools.JenaEngine;
public class mainOWL {
	/**
	* @param args
	* the command line arguments
	*/
	public static void main(String[] args) {/*
	
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
	*/
		String NS = "";
		
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/reseausocial.owl");
		if (model != null) {
		//lire le Namespace de l’ontologie
		NS = model.getNsPrefixURI("");
		// apply our rules on the owlInferencedModel
		Model inferedModel =
		
		JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
		// query on the model after inference
		System.out.println(JenaEngine.executeQueryFile(inferedModel,"data/query2.txt"));
		
		} else {
		System.out.println("Error when reading model from ontology");
		}
	}
}

	public static void main(String[] args) {

		String ns= "http://reseau-social.com/";		
		
		OntModel model = ModelFactory.createOntologyModel();
		model.setNsPrefix("",ns);
		
		OntClass Evenement = model.createClass(ns + "Evenement");
		OntClass InvitationClass = model.createClass(ns + "Invitation");
		OntClass UtilisateurClass = model.createClass(ns + "Utilisateur");
		OntClass EvenementCulturel = model.createClass(ns + "EvenementCulturel");
		OntClass EvenementSocial = model.createClass(ns + "EvenementSocial");
		OntClass EvenementSportif = model.createClass(ns + "EvenementSportif");
	
		OntClass Utilisateur = model.createClass(ns + "Utilisateur");
		OntClass SimpleUtilisateur = model.createClass(ns + "SimpleUtilisateur");
		OntClass UtilisateurPrivilege = model.createClass(ns + "UtilisateurPrivilege");
		OntClass SuperAdministrateur = model.createClass(ns + "SuperAdministrateur");
		OntClass Membre = model.createClass(ns + "Membre");
			
		EvenementCulturel.addSuperClass(Evenement);
		EvenementSocial.addSuperClass(Evenement);
		EvenementSportif.addSuperClass(Evenement);

		SimpleUtilisateur.addSuperClass(Utilisateur);
		UtilisateurPrivilege.addSuperClass(Utilisateur);
		Membre.addSuperClass(SimpleUtilisateur);
		SuperAdministrateur.addSuperClass(UtilisateurPrivilege);
			
		ObjectProperty reagitA = model.createObjectProperty(ns + "reagitA");
		ObjectProperty aime = model.createObjectProperty(ns + "aime");
		ObjectProperty partage = model.createObjectProperty(ns + "partage");
		ObjectProperty estAuteurDe = model.createObjectProperty(ns + "estAuteurDe");
		ObjectProperty gere = model.createObjectProperty(ns + "gere");
			
		aime.addSuperProperty(reagitA);
			
		Individual evenement1 = model.createIndividual(ns + "evenement1", Evenement);
		Individual evenement2 = model.createIndividual(ns + "evenement2", EvenementSportif);

		Individual invitation = model.createIndividual(ns + "invitation", InvitationClass);
		
		Individual privilegeUser = model.createIndividual(ns + "privilegeUser", UtilisateurPrivilege);
		Individual membre = model.createIndividual(ns + "membre", Membre);
		Individual superAdmin = model.createIndividual(ns + "superAdmin", SuperAdministrateur);
					
		superAdmin.addProperty(gere, evenement1);
		superAdmin.addProperty(gere, invitation);
		privilegeUser.addProperty(estAuteurDe, evenement2);
		membre.addProperty(partage, evenement2);
		
		DatatypeProperty idEvenement = model.createDatatypeProperty(ns + "idEvenement");
		DatatypeProperty dateCreationEvenement = model.createDatatypeProperty(ns + "dateCreationEvenement");
		DatatypeProperty dateModificationEvenement = model.createDatatypeProperty(ns + "dateModificationEvenement");
		DatatypeProperty lieu = model.createDatatypeProperty(ns + "lieu");
		DatatypeProperty nomEvenement = model.createDatatypeProperty(ns + "nomEvenement");
		DatatypeProperty descriptionEvenement = model.createDatatypeProperty(ns + "descriptionEvenement");
		
		DatatypeProperty idInvitation = model.createDatatypeProperty(ns + "idInvitation");
		DatatypeProperty dateCreationInvitation = model.createDatatypeProperty(ns + "dateCreationInvitation");
		DatatypeProperty status = model.createDatatypeProperty(ns + "status");
		
		idEvenement.addDomain(Evenement);
		idEvenement.addRange(XSD.integer);
		dateCreationEvenement.addDomain(Evenement);
		dateCreationEvenement.addRange(XSD.dateTime);
		dateModificationEvenement.addDomain(Evenement);
		dateModificationEvenement.addRange(XSD.dateTime);
		lieu.addDomain(Evenement);
		lieu.addRange(XSD.xstring);
		nomEvenement.addDomain(Evenement);
		nomEvenement.addRange(XSD.xstring);
		descriptionEvenement.addDomain(Evenement);
		descriptionEvenement.addRange(XSD.xstring);
		
		idInvitation.addDomain(InvitationClass);
		idInvitation.addRange(XSD.integer);
		dateCreationInvitation.addDomain(InvitationClass);
		dateCreationInvitation.addRange(XSD.dateTime);
		status.addDomain(InvitationClass);
		status.addRange(XSD.xstring);
		
		evenement1.addProperty(nomEvenement, "Evenement 1", XSDDatatype.XSDstring);
		
		System.out.print("Ontologie de l'evenement est : ");
		model.write(System.out,"RDF/XML");
		String NS = "";
		Model modelEvenement = JenaEngine.readModel("data/evenement.owl");
		if (model != null) {
		//lire le Namespace de l’ontologie
		NS = modelEvenement.getNsPrefixURI("");
		// apply our rules on the owlInferencedModel
		Model inferedModel =

		JenaEngine.readInferencedModelFromRuleFile(modelEvenement, "data/rules.txt");
		// query on the model after inference
		System.out.println(JenaEngine.executeQueryFile(inferedModel,

		"data/query.txt"));

		} else {
		System.out.println("Error when reading model from ontology");
		}
	
	}

}
