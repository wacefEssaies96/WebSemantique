import React, { useEffect, useState } from 'react';
import * as rdflib from 'rdflib';

const RDFData = `
@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.
@prefix owl: <http://www.w3.org/2002/07/owl#>.
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.
@prefix : <http://reseau-social.com/>.
@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.

:Membre rdf:type owl:Class;
    rdfs:subClassOf :simpleUtilisateur.

:Emoji rdf:type owl:Class.

:simpleUtilisateur rdf:type owl:Class;
    rdfs:subClassOf :Utilisateur.

:SuperAdministrateur rdf:type owl:Class;
    rdfs:subClassOf :Utilisateur.

:Commentaire rdf:type owl:Class.

:UtilisateurPrivilegie rdf:type owl:Class;
    rdfs:subClassOf :Utilisateur.

:Emoji rdf:type owl:Class.

:geres rdf:type owl:ObjectProperty.

:est_associe_a rdf:type owl:ObjectProperty.

:est_auteurDe rdf:type owl:ObjectProperty.

:repond_a rdf:type owl:ObjectProperty.

:Date_creation rdf:type owl:DatatypeProperty;
    rdfs:domain :Commentaire.

:Type_Contenu rdf:type owl:DatatypeProperty;
    rdfs:domain :Commentaire.

:contenu rdf:type owl:DatatypeProperty;
    rdfs:domain :Commentaire.

:emojis rdf:type owl:DatatypeProperty;
    rdfs:domain :Commentaire.

:id rdf:type owl:DatatypeProperty;
    rdfs:domain :Commentaire.

<http://reseau-social.com/emoji1> rdf:type :Emoji;
    :est_associe_a [
        rdf:type :Commentaire;
        :Type_Contenu "Type de contenu 1";
        :emojis "😊,👍,❤";
        :id "1"^^xsd:integer;
        :Date_creation "2023-10-25T09:00:00"^^xsd:dateTime;
        :contenu "Contenu du commentaire 1"
    ].

<http://reseau-social.com/membre1> rdf:type :Membre;
    :repond_a <http://reseau-social.com/commentaire1>;
    :est_auteurDe <http://reseau-social.com/commentaire2>.

<http://reseau-social.com/superAdministrateur1> rdf:type :SuperAdministrateur;
    :gere <http://reseau-social.com/commentaire1>.

<http://reseau-social.com/utilisateurPrivilegie1> rdf:type :UtilisateurPrivilegie;
    :repond_a <http://reseau-social.com/commentaire2>;
    :est_auteurDe <http://reseau-social.com/commentaire1>.

`;

function PublicationUser() {
    const [data, setData] = useState([]);
    const [commentInput, setCommentInput] = useState('');
   
    var store = rdflib.graph();
    useEffect(() => {
    
  
      // Parse the Turtle RDF data
      rdflib.parse(RDFData, store, 'http://reseau-social.com/', 'text/turtle');
  
      // Query the RDF data to retrieve specific information
      const emojis = store.each(undefined, rdflib.namedNode('http://reseau-social.com/emojis'), undefined);
      const commentaires = store.each(undefined, rdflib.namedNode('http://reseau-social.com/repond_a'), undefined);
  
      // Construct an array of data to display
      const parsedData = commentaires.map((commentaire) => {
        const id = commentaire.uri;
        const emojisValue = emojis.find((emoji) => emoji.object && emoji.subject.equals(commentaire.object));
        return {
          id: id,
          emojis: emojisValue ? emojisValue.object.value : 'N/A',
        };
      });
  
      setData(parsedData);
    }, []);
  
    const handleCommentSubmit = (e) => {
        e.preventDefault();
    
        // Create a new RDF statement for the comment
        const commentURI = 'http://reseau-social.com/new_comment'; // Generate a unique URI
        const commentSubject = rdflib.sym(commentURI);
        const commentPredicate = rdflib.sym('http://reseau-social.com/est_associe_a');
        const commentObject = rdflib.literal(commentInput, 'http://www.w3.org/2001/XMLSchema#string');
    
        // Add the new comment to the RDF graph
        store.add(commentSubject, commentPredicate, commentObject);
    
        // Update the data state with the new comment
        const newComment = {
          id: commentURI,
          emojis: 'N/A', // You can set the initial value of emojis as needed
        };
        setData([...data, newComment]);
    
        // Reset the comment input field
        setCommentInput('');
      };
    
      return (
        <div>
          <h1>RDF Data</h1>
          <form onSubmit={handleCommentSubmit}>
            <input
              type="text"
              placeholder="Add a comment"
              value={commentInput}
              onChange={(e) => setCommentInput(e.target.value)}
            />
            <button type="submit">Submit</button>
          </form>
          <ul>
            {data.map((item) => (
              <li key={item.id}>
                Commentaire ID: {item.id}, Emojis: {item.emojis}
              </li>
            ))}
          </ul>
        </div>
      );
    }
    
    export default PublicationUser;