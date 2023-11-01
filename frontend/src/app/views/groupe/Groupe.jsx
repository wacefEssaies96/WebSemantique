import { Fragment } from 'react';
import React, { useState, useEffect } from 'react';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  FormControlLabel,
  Grid,
  Icon,
  Radio,
  RadioGroup,
  styled,
  useTheme,
  TextareaAutosize,
} from '@mui/material';
import { Span } from 'app/components/Typography';
import UsersTable from '../dashboard/UsersTable';
import Tableau from './Tableau';

const ContentBox = styled('div')(({ theme }) => ({
  margin: '30px',
  [theme.breakpoints.down('sm')]: { margin: '16px' },
}));

const Title = styled('span')(() => ({
  fontSize: '1rem',
  fontWeight: '500',
  marginRight: '.5rem',
  textTransform: 'capitalize',
}));

const H4 = styled('h4')(({ theme }) => ({
  fontSize: '1rem',
  fontWeight: '500',
  marginBottom: '16px',
  textTransform: 'capitalize',
  color: theme.palette.text.secondary,
}));

const Groupe = () => {
  const [openn, setOpenn] = useState(false);

  const handleOpenn = () => {
    setOpenn(true);
  };

  const handleClosee = () => {
    setOpenn(false);
  };

  return (
    <Fragment>
      <Button variant="outlined" onClick={handleOpenn}>
        Show Users
      </Button>
      <Dialog open={openn} onClose={handleClosee} aria-labelledby="modal-title" aria-describedby="modal-description">
        <DialogTitle id="modal-title">List of Users</DialogTitle>
        <DialogContent>
          <DialogContentText id="modal-description">
            Below is the list of users:
          </DialogContentText>
          <div style={{ maxHeight: '400px', overflow: 'auto' }}>
            <UsersTable  />
          </div>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClosee} color="primary">
            Close
          </Button>
        </DialogActions>
      </Dialog>

      <ContentBox className="analytics">
        <Grid container spacing={3}>
          <Grid item lg={8} md={8} sm={12} xs={12}>
            <H4>Available groups</H4>
            <Tableau />
          </Grid>
        </Grid>
      </ContentBox>
    </Fragment>
  );
};

export default Groupe;
