package tn.esprit;
import org.apache.jena.datatypes.xsd.XSDDatatype;
//import org.apache.jena.vocabulary.*;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;


import tn.esprit.tools.JenaEngine;
public class mainOWL {

	public static void main(String[] args) {

		String ns= "http://reseau-social.com/";		
		
		OntModel model = ModelFactory.createOntologyModel();
		model.setNsPrefix("",ns);
		
		OntClass Groupe = model.createClass(ns + "Groupe");
		OntClass Discussion = model.createClass(ns + "Discussion");
		OntClass GroupeEducation = model.createClass(ns + "GroupeEducation");
		OntClass GroupePrive = model.createClass(ns + "GroupePrive");
		OntClass GroupePublic = model.createClass(ns + "GroupePublic");
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
			
		GroupeEducation.addSuperClass(Groupe);
		GroupePrive.addSuperClass(Groupe);
		GroupePublic.addSuperClass(Groupe);
		EvenementCulturel.addSuperClass(Evenement);
		EvenementSocial.addSuperClass(Evenement);
		EvenementSportif.addSuperClass(Evenement);

		SimpleUtilisateur.addSuperClass(Utilisateur);
		UtilisateurPrivilege.addSuperClass(Utilisateur);
		Membre.addSuperClass(SimpleUtilisateur);
		SuperAdministrateur.addSuperClass(UtilisateurPrivilege);
			
//		ObjectProperty reagitA = model.createObjectProperty(ns + "reagitA");
		ObjectProperty reagitA = model.createObjectProperty(ns + "reagitA");
		ObjectProperty aime = model.createObjectProperty(ns + "aime");
		ObjectProperty partage = model.createObjectProperty(ns + "partage");
		ObjectProperty estAuteurDe = model.createObjectProperty(ns + "estAuteurDe");
		ObjectProperty gere = model.createObjectProperty(ns + "gere");
		ObjectProperty estAssocieA = model.createObjectProperty(ns + "estAssocieA");
		ObjectProperty partagemembre = model.createObjectProperty(ns + "partagemembre");

//		aime.addSuperProperty(reagitA);
			
		Individual groupe1 = model.createIndividual(ns + "groupe1", Groupe);
		Individual groupe2 = model.createIndividual(ns + "groupe2", GroupePrive);

		Individual discussion = model.createIndividual(ns + "discussion", Discussion);
			
		aime.addSuperProperty(reagitA);
			
		Individual evenement1 = model.createIndividual(ns + "evenement1", Evenement);
		Individual evenement2 = model.createIndividual(ns + "evenement2", EvenementSportif);

		Individual invitation = model.createIndividual(ns + "invitation", InvitationClass);
		
		Individual privilegeUser = model.createIndividual(ns + "privilegeUser", UtilisateurPrivilege);
		Individual membre = model.createIndividual(ns + "membre", Membre);
		Individual superAdmin = model.createIndividual(ns + "superAdmin", SuperAdministrateur);
					
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
			groupe1.addProperty(nomGroupe, "Groupe 1", XSDDatatype.XSDstring);

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