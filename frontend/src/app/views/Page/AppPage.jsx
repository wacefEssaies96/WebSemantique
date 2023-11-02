import { Fragment, useEffect, useState } from 'react';
import { Formik } from 'formik';

import { LoadingButton } from '@mui/lab';
import { Breadcrumb } from 'app/components';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { Grid, Button, Box, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Card, IconButton, MenuItem, Select, 
  styled, Table, TextField, TableBody,TableCell, TableHead, TableRow, useTheme, Tooltip,
} from '@mui/material';
import { Paragraph } from 'app/components/Typography';
import { deleteData } from 'services/mix';
import DeleteModal from 'app/components/layouts/DeleteModal';

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



const AppPage = () => {

  const [mode, setMode] = useState("Create ");
  const [open, setOpen] = useState(false);

  const [filterValue, setFilterValue] = useState('All');
  const [typePage, setTypePage] = useState('public');

  var [listPage, setListPage] = useState([]);
  var [filteredList, setFilteredList] = useState([])
  const [id, setId] = useState(null)

  const [displaydeleteModal, setDisplaydeleteModal] = useState(false)
  const [deleteMessage, setDeleteMessage] = useState(null)


  const [pg, setPg] = useState({
    idPage: "",
    descriptionPage: "",
    badge: ""
  })

  const { palette } = useTheme();
  const bgError = palette.error.main;
  const bgPrimary = palette.primary.main;


  const deleteOnePage = async (id) => {
    try {
      await deleteData(`http://localhost:8082/websemantique/Pages/delete/Page/${id}`);
      setDisplaydeleteModal(false);
      refresh();
    } catch (error) {
      console.error('Error deleting Page:', error);
    }
  };

  const hideDELELTEModal = () => {
    setDisplaydeleteModal(false)
  };
  const showDELELTEModal = (id) => {
    setId(id)
    setDeleteMessage(`Are you sure you want to delete the page : '${id}'?`)
    setDisplaydeleteModal(true)
  };
  const refresh = async () => {
    try {
      const response = await fetch('http://localhost:8082/websemantique/Pages/getAll');
      const data = await response.json()
      setListPage(data);
      setFilteredList(data)
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

  filteredList = listPage.filter((page) => {
    if (filterValue === 'All') {
      return true;
    } else {
      return page.type.value === filterValue;
    }
  });

  function handleClickOpen(pid) {

    setMode("Create ");
    setPg({
      idPage: "",
      descriptionPage: "",
      badge: ""
    });
    if (pid !== -1) {
      setMode("Update ");
      const l = listPage.find((x) => x.idP.value === pid)
      if (l) {
        Object.keys(l).map((key) => {
          var keyp = null;
          if (key === "idP") {
            keyp = "idPage";
          } else {
            keyp = key;
          }
          setPg((prevPg) => ({
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
    if (name === "typeOfPage") {
      setTypePage(value)
    } else {
      setPg({ ...pg, [name]: value });
    }
    refresh();
  }
  const handleSubmit = (e) => {
    e.preventDefault();
    if (mode === "Create ") {
      fetch(`http://localhost:8082/websemantique/Pages/addPage?type=${typePage}`, {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(pg),
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
      fetch(`http://localhost:8082/websemantique/Pages/editPage?type=${typePage}`, {
        method: "PUT",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(pg),
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
    setListPage([]);
    refresh();

  }
  return (
    <Fragment>
      <DeleteModal showModal={displaydeleteModal} confirmModal={deleteOnePage} hideModal={hideDELELTEModal} id={id} message={deleteMessage} />

      <Box className="breadcrumb" style={{ marginTop: "3%" }}>
        <Breadcrumb routeSegments={[{ name: "Pages", path: "/Page" }]} style={{ marginTop: "5%" }} />
        <Box>

          <Dialog
            open={open}
            onClose={handleClose}
            aria-labelledby="responsive-dialog-title"
          >
            <DialogTitle id="responsive-dialog-title">Add New Page</DialogTitle>

            <DialogContent>
              <DialogContentText>
                <Card className="card">
                  <Grid container>
                    <Grid item sm={12} xs={12}>
                      <Box p={4} height="100%">
                        <Formik>
                          <form onSubmit={handleSubmit}>
                            <label style={{ color: "#004aad" }}>Page ID</label>
                            <TextField
                              fullWidth
                              size="small"
                              name="idPage"
                              type="number"
                              variant="outlined"
                              min="1"
                              value={pg.idPage}
                              onChange={handleChange}
                              sx={{ mb: 2 }}
                              required
                            />
                            <label style={{ color: "#004aad" }}>Page Type</label>
                            <select
                              style={{ width: "473px", height: "40px", marginBottom: "4%" }}
                              fullWidth
                              size="small"
                              name="typeOfPage"
                              variant="outlined"
                              value={typePage}
                              onChange={handleChange}
                              sx={{ mb: 2 }}
                              required
                            >
                              <option value="prive">private</option>
                              <option value="public">Public</option>
                            </select>
                            <label style={{ color: "#004aad" }}>Description</label>
                            <TextField
                              fullWidth
                              required
                              size="small"
                              type="text"
                              name="descriptionPage"
                              label="descriptionPage"
                              variant="outlined"
                              value={pg.descriptionPage}
                              onChange={handleChange}
                              sx={{ mb: 3 }}
                            />
                            <label style={{ color: "#004aad" }}>Page Badge</label>
                            <select
                              style={{ width: "473px", height: "40px", marginBottom: "4%" }}
                              fullWidth
                              size="small"
                              name="badge"
                              label="badge"
                              variant="outlined"
                              value={pg.badge}
                              onChange={handleChange}
                              sx={{ mb: 3 }}
                              required
                            >
                              <option value="true">true</option>
                              <option value="false">false</option>
                            </select>
                            <LoadingButton
                              type="submit"
                              color="primary"
                              variant="contained"
                              sx={{ mb: 2, mt: 3 }}
                            >
                              {mode} Page
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
                <Title>Pages</Title>
                <Select
                  size="small"
                  value={filterValue}
                  onChange={(e) => setFilterValue(e.target.value)}
                  defaultValue="All">
                  <MenuItem value="All">All</MenuItem>
                  <MenuItem value="public">public</MenuItem>
                  <MenuItem value="prive">private</MenuItem>
                </Select>
                <Button variant="outlined" color="primary" onClick={() => handleClickOpen(-1)} style={{ marginTop: "1%" }}>
                  Add a new page
                </Button>
              </CardHeader>

              <Box overflow="auto">
                <ProductTable>
                  <TableHead>
                    <TableRow>
                      <TableCell sx={{ px: 3 }} colSpan={4}>
                        Id
                      </TableCell>
                      <TableCell sx={{ px: 3 }} colSpan={4}>
                        Description
                        {/*  listPage } */}
                      </TableCell>
                      <TableCell sx={{ px: 0 }} colSpan={2}>
                        Badge
                      </TableCell>
                      <TableCell sx={{ px: 0 }} colSpan={2}>
                        Visibility
                      </TableCell>
                      <TableCell sx={{ px: 0 }} colSpan={1}>
                        Action
                      </TableCell>
                    </TableRow>
                  </TableHead>

                  <TableBody>
                    {filteredList.length > 0 && filteredList.map((page, index) => (
                      <TableRow key={index} hover>
                        {page.idP ?
                          (
                            <>
                              <TableCell colSpan={4} align="left" >
                                <Box display="flex" alignItems="center">

                                  <Paragraph sx={{ m: 0, ml: 4 }}>{page.idP.value}</Paragraph>

                                </Box>
                              </TableCell>
                            </>
                          ) : <TableCell>null</TableCell>}
                        {page.descriptionPage ?
                          (
                            <>
                              <TableCell colSpan={4} align="left" sx={{ px: 0, textTransform: 'capitalize' }}>
                                <Box display="flex" alignItems="center">

                                  <Paragraph sx={{ m: 0, ml: 4 }}>{page.descriptionPage.value}</Paragraph>

                                </Box>
                              </TableCell>
                            </>
                          ) : <TableCell>null</TableCell>}
                        {page.badge ?
                          (
                            <><TableCell align="left" colSpan={2} sx={{ px: 0, textTransform: 'capitalize' }}>
                              {page.badge.value}
                            </TableCell>
                            </>
                          ) : <TableCell>null</TableCell>}
                        {page.type ?
                          (
                            <>
                              <TableCell sx={{ px: 0 }} align="left" colSpan={2}>
                                {page.type.value && page.type.value === "prive" ?
                                  <Small bgcolor={bgError}>{page.type.value}</Small>
                                  :
                                  <Small bgcolor={bgPrimary}>{page.type.value}</Small>
                                }
                              </TableCell>
                            </>
                          ) : <TableCell>null</TableCell>}

                        <TableCell sx={{ px: 0 }} colSpan={1}>

                          <Tooltip title="Edit">
                            <IconButton
                              onClick={() => handleClickOpen(page.idP.value)}
                              style={{ marginTop: '-10px' }}>
                              <EditIcon style={{ color: '#3E6C37' }} />
                            </IconButton>
                          </Tooltip>
                          <Tooltip title="Delete">
                            <IconButton
                              onClick={() => showDELELTEModal(page.idP.value)}
                              style={{ marginTop: '-10px' }}>
                              <DeleteIcon style={{ color: '#CD2122' }} />
                            </IconButton>
                          </Tooltip>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </ProductTable>
              </Box>
            </Card>
          </Grid>
        </Grid>
      </ContentBox>
    </Fragment>
  );
};

export default AppPage;
