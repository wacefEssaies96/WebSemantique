import {
    Avatar,
    Box,
    Card,
    Grid,
    Select,
    MenuItem,
    CardHeader,
    Typography,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
  } from '@mui/material';
  import { Button } from '@mui/material';
  import React, { useEffect, useState } from 'react';
  
  import { Span } from 'app/components/Typography';
  import { Fragment } from 'react';
  import Axios from 'axios';
  import { styled } from '@mui/system';
  
  const ProjectName = styled(Span)(({ theme }) => ({
    marginLeft: 24,
    fontWeight: '500',
    [theme.breakpoints.down('sm')]: { marginLeft: 4 },
  }));
  
  const StyledAvatar = styled(Avatar)(() => ({
    width: '32px !important',
    height: '32px !important',
  }));
  
  const Tableau = ({ nomGroupe }) => {
    const [groups, setGroups] = useState([]);
    const [selectedType, setSelectedType] = useState('Groupe');
    const [selectedGroupe, setSelectedGroupe] = useState(null);
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const textMuted = 'rgba(0, 0, 0, 0.54)';
    const [discussions, setDiscussions] = useState([]);
  
    const handleShowDiscussion = (nomGroupe) => {
      setSelectedGroupe(nomGroupe);
    };
  
    const handleCloseDialog = () => {
      setIsDialogOpen(false);
    };
  
    useEffect(() => {
      Axios.get('http://localhost:8082/websemantique/controller/groupe')
        .then((response) => {
          setGroups(response.data);
        })
        .catch((error) => {
          console.error('Error fetching data:', error);
        });
    }, []);
  
    useEffect(() => {
      // Fetch discussions for the selected group
      if (selectedGroupe) {
        Axios.get(`http://localhost:8082/websemantique/controller/grpdiscussion/${selectedGroupe}`)
          .then((response) => {
            setDiscussions(response.data);
            setIsDialogOpen(true); // Open the dialog when discussions are fetched
          })
          .catch((error) => {
            console.error('Error fetching discussions:', error);
          });
      }
    }, [selectedGroupe]);
  
    const fetchGroupsByType = (type) => {
      Axios.get(`http://localhost:8082/websemantique/controller/groupefilter/${type}`)
        .then((response) => {
          setGroups(response.data);
        })
        .catch((error) => {
          console.error('Error fetching data:', error);
        });
    };
  
    const handleTypeChange = (event) => {
      const newType = event.target.value;
      setSelectedType(newType);
      fetchGroupsByType(newType);
    };
  
    return (
      <Fragment>
        <CardHeader
          title={
            <Typography variant="h5" component="div">
              Groups
            </Typography>
          }
          action={
            <Select size="small" value={selectedType} onChange={handleTypeChange}>
              <MenuItem value="Groupe">Groupe</MenuItem>
              <MenuItem value="GroupePrive">GroupePrive</MenuItem>
            </Select>
          }
        />
        {groups.map((group) => (
          <Card sx={{ py: 1, px: 2 }} className="project-card" key={group.idGroupe.value}>
            <Grid container alignItems="center">
              <Grid item xs={12} md={3}>
                <Box display="flex" alignItems="center">
                  <ProjectName>{group.nomGroupe.value}</ProjectName>
                </Box>
              </Grid>
              <Grid item xs={12} md={2}>
                <Box color={textMuted}>{group.date_CreationGroupe.value}</Box>
              </Grid>
              <Grid item xs={12} md={2}>
                <Box color={textMuted}>{group.descriptionGroupe.value}</Box>
              </Grid>
              <Grid item xs={12} md={3}>
                <Box color={textMuted}>Rules: {group.Regles.value}</Box>
              </Grid>
              <Grid item xs={12} md={2}>
                <Box display="flex" justifyContent="flex-end">
                  <Button onClick={() => handleShowDiscussion(group.nomGroupe.value)}>
                    Afficher la discussion
                  </Button>
                </Box>
              </Grid>
            </Grid>
            <Box p={2}></Box>
          </Card>
        ))}
        <Dialog open={isDialogOpen} onClose={handleCloseDialog}>
          {discussions.map((discussion) => (
            <Fragment>
              <DialogTitle>Discussion Title: {discussion.titre.value}</DialogTitle>
              <DialogContent dividers>
                <Typography gutterBottom>Content: {discussion.contenu.value}</Typography>
              </DialogContent>
              <DialogActions>
                <Button onClick={handleCloseDialog} color="primary">
                  Close
                </Button>
              </DialogActions>
            </Fragment>
          ))}
        </Dialog>
        <Box py={1} />
      </Fragment>
    );
  };
  
  export default Tableau;
  