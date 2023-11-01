package tn.esprit.project;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tn.esprit.tools.JenaEngine;

import org.apache.jena.rdf.model.ModelFactory;
@SpringBootApplication
@ComponentScan(basePackages = "tn.esprit")

public class WebSemantiqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSemantiqueApplication.class, args);
		
		
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
        DatatypeProperty emojiIdProperty = model.createDatatypeProperty(ns + "emoji_id");
        DatatypeProperty emojiSymboleProperty = model.createDatatypeProperty(ns + "emoji_symbole");
        DatatypeProperty typeContenuProperty = model.createDatatypeProperty(ns + "Type_Contenu");

        // Associez les DatatypeProperties à la classe Commentaire
        contenuProperty.addDomain(CommentaireClass);
        dateCreationProperty.addDomain(CommentaireClass);
        idProperty.addDomain(CommentaireClass);
        emojiIdProperty.addDomain(EmojiClass);
        emojiSymboleProperty.addDomain(EmojiClass);
        typeContenuProperty.addDomain(CommentaireClass);

        Individual commentaire1 = model.createIndividual(ns + "commentaire1", CommentaireClass);
        Individual commentaire2 = model.createIndividual(ns + "commentaire2", CommentaireClass);

        // Associez les valeurs aux propriétés de données pour les individus
        commentaire1.addProperty(contenuProperty, "Contenu du commentaire 1");
       Literal dateCreation1 = model.createTypedLiteral("2023-10-25T09:00:00", XSDDatatype.XSDdateTime);
        commentaire1.addProperty(dateCreationProperty, dateCreation1);
        commentaire1.addProperty(idProperty, "1",XSDDatatype.XSDinteger);
        Literal emojis1 = model.createTypedLiteral("❤", XSDDatatype.XSDstring);
        commentaire1.addProperty(emojiSymboleProperty, emojis1);
        commentaire1.addProperty(typeContenuProperty, "Type de contenu 1");

        ObjectProperty est_auteurDe = model.createObjectProperty(ns + "est_auteurDe");
        ObjectProperty est_associe_a = model.createObjectProperty(ns + "est_associe_a");
        ObjectProperty gere = model.createObjectProperty(ns + "gere");
        ObjectProperty repond_a = model.createObjectProperty(ns + "repond_a");

        Individual membre1 = model.createIndividual(ns + "membre1", MembreClass);
        Individual utilisateurprivilegie1 = model.createIndividual(ns + "utilisateurPrivilegie1", utilisateurPrivilegieClass);
        Individual superadmin1 = model.createIndividual(ns + "superAdministrateur1", SuperAdministrateurClass);
        Individual emoji1 = model.createIndividual(ns + "emoji1", EmojiClass);
        emoji1.addProperty(emojiIdProperty, "1", XSDDatatype.XSDinteger);
        emoji1.addProperty(emojiSymboleProperty, "❤", XSDDatatype.XSDstring);

        superadmin1.addProperty(gere, commentaire1);
        utilisateurprivilegie1.addProperty(est_auteurDe, commentaire1);
        membre1.addProperty(est_auteurDe, commentaire2);
        membre1.addProperty(repond_a, commentaire1);
        utilisateurprivilegie1.addProperty(repond_a, commentaire2);
        emoji1.addProperty(est_associe_a, commentaire1);

        System.out.println("Ontologie du reseau social");
        model.write(System.out, "RDF/XML");
        String NS = "";
    	
    	// lire le model a partir d'une ontologie

    	Model model2 = JenaEngine.readModel("data/commentaire.owl");
    	if (model2 != null) {
    		//lire le Namespace de l’ontologie
    		NS = model2.getNsPrefixURI("");
    		// apply our rules on the owlInferencedModel
    		Model inferedModel =
    		
    		JenaEngine.readInferencedModelFromRuleFile(model2, "data/rules.txt");
    		// query on the model after inference
    		System.out.println(JenaEngine.executeQueryFile(inferedModel,"data/querycommentaire.txt"));
    	

    	} else {
    		System.out.println("Error when reading model from ontology");
    	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/commentcontroller/**") // Remplacez avec le chemin approprié
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

}
