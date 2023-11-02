import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {
  Button,
  Grid,
  Icon,
  TextField,
  Card,
  CardContent,
  Typography,
} from "@mui/material";
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import { addComment, deleteComment, fetchComments } from '../../../redux/actions/CommentAction';
import { Picker } from 'emoji-mart';

function CommentComponent() {
  const [contenu, setContenu] = useState('');
  const [selectedEmoji, setSelectedEmoji] = useState('');
  const [showEmojiPicker, setShowEmojiPicker] = useState(false);

  const comments = useSelector((state) => state.commentReducer.comments);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchComments());
  }, [dispatch]);

  const handleCommentChange = (event) => {
    setContenu(event.target.value);
  };

  const toggleEmojiPicker = () => {
    setShowEmojiPicker(!showEmojiPicker);
  };

  const handleEmojiSelect = (emoji) => {
    setSelectedEmoji(emoji.native);
    toggleEmojiPicker();
  };
  function getRandomInt(min, max) {
    // Use Math.floor() to round down and get an integer
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }
  
  // Example usage to generate a random integer between 1 and 10 (inclusive)

  
  const handleSubmit = async (event) => {
    event.preventDefault();
    const commentWithEmoji = {
      typeContenu: contenu,
      id:  getRandomInt(1, 100).toString() , // Convert to string
    
      contenu: contenu,
      dateCreation: Date.now() ,
    };
    
    
    dispatch(addComment(commentWithEmoji));
    setContenu('');
    setSelectedEmoji('');
    window.location.reload();
  };

  const handleDeleteClick = (commentId) => {
    dispatch(deleteComment(commentId.value));
    window.location.reload();

  };

  return (
    <div>
      <ValidatorForm onSubmit={handleSubmit} onError={() => null}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextValidator
              label="Commentaire"
              multiline
              rows={4}
              variant="outlined"
              fullWidth
              value={contenu}
              onChange={handleCommentChange}
            />
            <Button onClick={toggleEmojiPicker}>Ajouter un emoji</Button>
            {showEmojiPicker && (
              <Picker onSelect={handleEmojiSelect} />
            )}
          </Grid>
        </Grid>

        <Button color="primary" variant="contained" type="submit">
          <Icon>send</Icon>
          Ajouter un commentaire
        </Button>
      </ValidatorForm>

      <div>
        {comments.map((comment) => (
          <Card key={comment.idc.value} style={{ marginBottom: '10px' }}>
            <CardContent>
              <Typography variant="body1">{comment.contenu.value}</Typography>
              <Typography variant="caption" color="textSecondary">
                Date de création : {comment.Date_creation.value}
              </Typography>
              <Typography variant="caption" color="textSecondary">
                Type de contenu : {comment.Type_Contenu.value}
              </Typography>
             
              <Button onClick={() => handleDeleteClick(comment.idc)}>Supprimer</Button>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}

export default CommentComponent;