import CloseIcon from '@mui/icons-material/Close';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import MuiDialogActions from '@mui/material/DialogActions';
import MuiDialogContent from '@mui/material/DialogContent';
import MuiDialogTitle from '@mui/material/DialogTitle';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { styled } from '@mui/system';
import { useState } from 'react';
import Axios from 'axios';
import React, { useEffect, useCallback } from 'react';

const DialogTitleRoot = styled(MuiDialogTitle)(({ theme }) => ({
  margin: 0,
  padding: theme.spacing(2),
  '& .closeButton': {
    position: 'absolute',
    right: theme.spacing(1),
    top: theme.spacing(1),
    color: theme.palette.grey[500],
  },
}));

const DialogTitle = (props) => {
  const { children, onClose } = props;
  return (
    <DialogTitleRoot disableTypography>
      <Typography variant="h6">{children}</Typography>
      {onClose ? (
        <IconButton aria-label="Close" className="closeButton" onClick={onClose}>
          <CloseIcon />
        </IconButton>
      ) : null}
    </DialogTitleRoot>)
};

const DialogContent = styled(MuiDialogContent)(({ theme }) => ({
  '&.root': { padding: theme.spacing(2) },
}));

const DialogActions = styled(MuiDialogActions)(({ theme }) => ({
  '&.root': { margin: 0, padding: theme.spacing(1) },
}));

const CustomizedDialogsr = ({ discussion, nomGroupe }) => {
  const [open, setOpen] = useState(false);
  const [selectedDiscussion, setSelectedDiscussion] = useState(null);
  const [discussions, setDiscussions] = useState([]);

  const handleClickOpen = (discussion) => {
    setSelectedDiscussion(discussion);
    setOpen(true);
  };

  const handleClose = () => setOpen(false);

  useEffect(() => {
    // Fetch all discussions when the component initially loads
    Axios.get(`http://localhost:8082/websemantique/controller/grpdiscussion/${nomGroupe}`)
      .then((response) => {
        setDiscussions(response.data);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, [nomGroupe]);

  return (
    <div>
      <Button variant="outlined" color="secondary" onClick={handleClickOpen}>
        Open dialog
      </Button>
      {discussions.map((d) => (
        <div key={d.id}>
          <Dialog
            onClose={handleClose}
            aria-labelledby="customized-dialog-title"
            open={selectedDiscussion === d}
          >
            <DialogTitle id="customized-dialog-title" onClose={handleClose}>
              Discussion Title: {d.value.titre}
            </DialogTitle>
            <DialogContent dividers>
              <Typography gutterBottom>
                Content: {d.value.contenu}
              </Typography>
              <Typography gutterBottom>
                Other discussion details can go here.
              </Typography>
            </DialogContent>
            <DialogActions>
              <Button onClick={handleClose} color="primary">
                Close
              </Button>
            </DialogActions>
          </Dialog>
        </div>
      ))}
    </div>
  );
};

export default CustomizedDialogsr;
