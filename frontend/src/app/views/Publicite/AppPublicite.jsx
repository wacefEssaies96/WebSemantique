import { Fragment, useEffect, useState } from 'react';
import { Formik } from 'formik';

import { LoadingButton } from '@mui/lab';
import { Breadcrumb } from 'app/components';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { Grid, Button, Box, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Card, IconButton, MenuItem, Select, 
  styled, Table, TextField, TableBody,TableCell, TableHead,Icon,TableRow, useTheme, Tooltip, Avatar,
} from '@mui/material';

import { Paragraph,Small } from 'app/components/Typography';
import { deleteData } from 'services/mix';
import DeleteModal from 'app/components/layouts/DeleteModal';

const StyledCard = styled(Card)(({ theme }) => ({
  display: 'flex',
  flexWrap: 'wrap',
  alignItems: 'center',
  justifyContent: 'space-between',
  padding: '24px !important',
  background: theme.palette.background.paper,
  [theme.breakpoints.down('sm')]: { padding: '16px !important' },
}));

const ContentBox = styled(Box)(({ theme }) => ({
  display: 'flex',
  flexWrap: 'wrap',
  alignItems: 'center',
  '& small': { color: theme.palette.text.secondary },
  '& .icon': { opacity: 0.6, fontSize: '44px', color: theme.palette.primary.main },
}));

const Heading = styled('h6')(({ theme }) => ({
  margin: 0,
  marginTop: '4px',
  fontSize: '14px',
  fontWeight: '500',
  color: theme.palette.primary.main,
}));


const Title = styled('span')(() => ({
  fontSize: '1rem',
  fontWeight: '500',
  marginRight: '.5rem',
  textTransform: 'capitalize',
}));

const SubTitle = styled('span')(({ theme }) => ({
  fontSize: '0.875rem',
  color: theme.palette.text.secondary,
}));

const H4 = styled('h4')(({ theme }) => ({
  fontSize: '1rem',
  fontWeight: '500',
  marginBottom: '16px',
  textTransform: 'capitalize',
  color: theme.palette.text.secondary,
}));

const AppPublicite = () => {
  const { palette } = useTheme();
  const cardList = [
    { name: 'New Leads', amount: 3050, icon: 'group' },
    { name: 'This week Sales', amount: '$80,500', icon: 'attach_money' },
    { name: 'Inventory Status', amount: '8.5% Stock Surplus', icon: 'store' },
    { name: 'Orders to deliver', amount: '305 Orders', icon: 'shopping_cart' },
  ];

  const ContentBox = styled('div')(({ theme }) => ({
    margin: '30px',
    [theme.breakpoints.down('sm')]: { margin: '16px' },
  }));
  
  
  const CardHeader = styled(Box)(() => ({
    display: 'flex',
    paddingLeft: '24px',
    paddingRight: '24px',
    marginBottom: '12px',
    alignItems: 'center',
    justifyContent: 'space-between',
  }));
  
  const Title = styled('span')(() => ({
    fontSize: '1rem',
    fontWeight: '500',
    textTransform: 'capitalize',
  }));
  
  const ProductTable = styled(Table)(() => ({
    minWidth: 400,
    whiteSpace: 'pre',
    '& small': {
      width: 50,
      height: 15,
      borderRadius: 500,
      boxShadow: '0 0 2px 0 rgba(0, 0, 0, 0.12), 0 2px 2px 0 rgba(0, 0, 0, 0.24)',
    },
    '& td': { borderBottom: 'none' },
    '& td:first-of-type': { paddingLeft: '16px !important' },
  }));
  
  const Small = styled('small')(({ bgcolor }) => ({
    width: 50,
    height: 15,
    color: '#fff',
    padding: '2px 8px',
    borderRadius: '4px',
    overflow: 'hidden',
    background: bgcolor,
    boxShadow: '0 0 2px 0 rgba(0, 0, 0, 0.12), 0 2px 2px 0 rgba(0, 0, 0, 0.24)',
  }));
  
  
  
  
    const [mode, setMode] = useState("Create ");
    const [open, setOpen] = useState(false);
  
  
    var [listPublicite, setListPublicite] = useState([]);
    const [id, setId] = useState(null)
  
    const [displaydeleteModal, setDisplaydeleteModal] = useState(false)
    const [deleteMessage, setDeleteMessage] = useState(null)
  
  
    const [pb, setPb] = useState({
      idPublicite: "",
      descriptionPublicite: "",
      ImagePublicite: ""
    })
  
    const bgError = palette.error.main;
    const bgPrimary = palette.primary.main;
  
  
    const deleteOnePublicite = async (id) => {
      try {
        await deleteData(`http://localhost:8082/websemantique/Publicites/delete/Publicite/${id}`);
        setDisplaydeleteModal(false);
        refresh();
      } catch (error) {
        console.error('Error deleting Publicite:', error);
      }
    };
  
    const hideDELELTEModal = () => {
      setDisplaydeleteModal(false)
    };
    const showDELELTEModal = (id) => {
      setId(id)
      setDeleteMessage(`Are you sure you want to delete the publicite : '${id}'?`)
      setDisplaydeleteModal(true)
    };
    const refresh = async () => {
      try {
        const response = await fetch('http://localhost:8082/websemantique/Publicites/getAll');
        const data = await response.json()
        setListPublicite(data);
        console.log(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    }
    useEffect(() => {
      const fetchData = async () => {
        await refresh();
      };
  
      fetchData();
    }, []);
  
  
    function handleClickOpen(pid) {
  
      setMode("Create ");
      setPb({
        idPublicite: "",
        descriptionPublicite: "",
        ImagePublicite: ""
      });
      if (pid !== -1) {
        setMode("Update ");
        const l = listPublicite.find((x) => x.idP.value === pid)
        if (l) {
          Object.keys(l).map((key) => {
            var keyp = null;
            if (key === "idP") {
              keyp = "idPublicite";
            } else {
              keyp = key;
            }
            setPb((prevPg) => ({
              ...prevPg,
              [keyp]: l[key].value,
            }));
            return null;
          });
        }
      }
      setOpen(true);
    }
  
    function handleClose() {
      setOpen(false);
    }
  
    const handleChange = (e) => {
      const { name, value } = e.target;
      setPb({ ...pb, [name]: value });
      refresh();
    }
    const handleSubmit = (e) => {
      e.preventDefault();
      if (mode === "Create ") {
        fetch(`http://localhost:8082/websemantique/Publicites/addPublicite`, {
          method: "POST",
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(pb),
        })
          .then((response) => console.log(response))
          .then((data) => {
            console.log('Response from server:', data);
            setOpen(false);
          })
          .catch((error) => {
            console.log('Error:', error);
          });
      } else {
        fetch(`http://localhost:8082/websemantique/Publicites/editPublicite`, {
          method: "PUT",
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(pb),
        })
          .then((response) => console.log(response))
          .then((data) => {
            console.log('Response from server:', data);
            setOpen(false);
          })
          .catch((error) => {
            console.log('Error:', error);
          });
      }
      setListPublicite([]);
      refresh();
  
    }
  return (
    <Fragment>
      <DeleteModal showModal={displaydeleteModal} confirmModal={deleteOnePublicite} hideModal={hideDELELTEModal} id={id} message={deleteMessage} />

      <Box className="breadcrumb" style={{ marginTop: "3%" }}>
        <Breadcrumb routeSegments={[{ name: "Publicites", path: "/Publicite" }]} style={{ marginTop: "5%" }} />
        <Box>

          <Dialog
            open={open}
            onClose={handleClose}
            aria-labelledby="responsive-dialog-title"
          >
            <DialogTitle id="responsive-dialog-title">Add New Publicite</DialogTitle>

            <DialogContent>
              <DialogContentText>
                <Card className="card">
                  <Grid container>
                    <Grid item sm={12} xs={12}>
                      <Box p={4} height="100%">
                        <Formik>
                          <form onSubmit={handleSubmit}>
                            <label style={{ color: "#004aad" }}>Publicite ID</label>
                            <TextField
                              fullWidth
                              size="small"
                              name="idPublicite"
                              type="number"
                              variant="outlined"
                              min="1"
                              value={pb.idPublicite}
                              onChange={handleChange}
                              sx={{ mb: 2 }}
                              required
                            />
                            <label style={{ color: "#004aad" }}>Description</label>
                            <TextField
                              fullWidth
                              required
                              size="small"
                              type="text"
                              name="descriptionPublicite"
                              label="descriptionPublicite"
                              variant="outlined"
                              value={pb.descriptionPublicite}
                              onChange={handleChange}
                              sx={{ mb: 3 }}
                            />
                            <label style={{ color: "#004aad" }}>Image</label>
                            <TextField
                              fullWidth
                              required
                              size="small"
                              type="text"
                              name="ImagePublicite"
                              label="ImagePublicite"
                              variant="outlined"
                              value={pb.ImagePublicite}
                              onChange={handleChange}
                              sx={{ mb: 3 }}
                            />
                            <LoadingButton
                              type="submit"
                              color="primary"
                              variant="contained"
                              sx={{ mb: 2, mt: 3 }}
                            >
                              {mode} Publicite
                            </LoadingButton>
                          </form>
                        </Formik>
                      </Box>
                    </Grid>
                  </Grid>
                </Card>
              </DialogContentText>
            </DialogContent>

            <DialogActions>
              <Button onClick={handleClose} color="primary">
                Cancel
              </Button>
            </DialogActions>
          </Dialog>
        </Box>
      </Box>
      <ContentBox className="analytics">
        <Grid container spacing={3}>
          <Grid item lg={10} md={10} sm={12} xs={12}>

            <Card elevation={3} sx={{ pt: '20px', mb: 3 }}>
              <CardHeader>
                <Title>Publicites</Title>
                <Button variant="outlined" color="primary" onClick={() => handleClickOpen(-1)} style={{ marginTop: "1%" }}>
                  Add a new Publicite
                </Button>
              </CardHeader>

              
              <Box overflow="auto">
              <Grid container spacing={3} sx={{ mb: '24px' }}>
                    {listPublicite.length > 0 && listPublicite.map((publicite, index) => (
                        <Grid item xs={12} md={3} key={index}>
                          <StyledCard elevation={6}>
                            <ContentBox>
                              {publicite.ImagePublicite ?
                              (
                                <>
                                  <Avatar src={publicite.ImagePublicite.value} />

                                
                                  <Icon className="icon"></Icon>
                                  </>
                              ):<>null</>}
                              
                              {publicite.idP ?
                              (<>
                                  <Tooltip title="Edit">
                                    <IconButton
                                      onClick={() => handleClickOpen(publicite.idP.value)}
                                      style={{ marginTop: '-10px' }}>
                                      <EditIcon style={{ color: '#3E6C37' }} />
                                    </IconButton>
                                  </Tooltip>
                                  <Tooltip title="Delete">
                                    <IconButton
                                      onClick={() => showDELELTEModal(publicite.idP.value)}
                                      style={{ marginTop: '-10px' }}>
                                      <DeleteIcon style={{ color: '#CD2122' }} />
                                    </IconButton>
                                  </Tooltip>
                                </>
                          ):<>null</>}
                              <Box ml="12px">
                                
                           {publicite.descriptionPublicite ?
                              (<>
                              
                                <Heading>{publicite.descriptionPublicite.value}</Heading>
                                </>
                          ):<Heading>null</Heading>} 
                              </Box>
                            </ContentBox>
      
                            {/* <Tooltip title="View Details" placement="top">
                              <IconButton>
                                <Icon>arrow_right_alt</Icon>
                              </IconButton>
                            </Tooltip> */}
                          </StyledCard>
                        </Grid>
                      ))}
                    </Grid>
              </Box>
            </Card>
          </Grid>
        </Grid>
      </ContentBox>
    </Fragment>
  );
};

export default AppPublicite;
