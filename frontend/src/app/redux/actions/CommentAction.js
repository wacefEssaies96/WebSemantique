
import axios from 'axios';
// types.js
export const ADD_COMMENT = 'ADD_COMMENT';
export const UPDATE_COMMENT = 'UPDATE_COMMENT';
export const DELETE_COMMENT = 'DELETE_COMMENT';
export const FETCH_COMMENTS = 'FETCH_COMMENTS';
// actions.js


// Action pour ajouter un commentaire
export const addComment = (comment) => async (dispatch) => {
  try {
    const response = await axios.post('http://localhost:8082/websemantique/commentcontroller/addcomment', comment);
    dispatch({ type: ADD_COMMENT, payload: response.data });
  } catch (error) {
    console.error(error);
  }
};
/*
// Action pour mettre à jour un commentaire
export const updateComment = (comment) => async (dispatch) => {
  try {
    const response = await axios.put(`http://localhost:8082/CommentServices/api/comments/${comment.id}`, comment);
    dispatch({ type: UPDATE_COMMENT, payload: response.data });
  } catch (error) {
    console.error(error);
  }
};*/

// Action pour supprimer un commentaire
export const deleteComment = (commentId) => async (dispatch) => {
  try {
    await axios.delete(`http://localhost:8082/websemantique/commentcontroller/deletecomment/${commentId}`);
    dispatch({ type: DELETE_COMMENT, payload: commentId });
  } catch (error) {
    console.error(error);
  }
};

// Action pour récupérer tous les commentaires
export const fetchComments = () => async (dispatch) => {
    try {
      const response = await axios.get('http://localhost:8082/websemantique/commentcontroller/sparqlcommentaire');
      dispatch({ type: FETCH_COMMENTS, payload: response.data });
      console.log(response.data)
    } catch (error) {
      console.error('Error fetching comments:', error);
    }
  };
  
