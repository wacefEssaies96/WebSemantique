import { Box, Button, Card, Container, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, Icon, styled, useTheme, TextField, Tooltip } from '@mui/material';
import { Breadcrumb } from 'app/components';
import useMediaQuery from '@mui/material/useMediaQuery';
import React, { useEffect, useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css'
import { Small } from 'app/components/Typography';
// import AccountCircleTwoToneIcon from '@mui/icons-material/AccountCircleTwoTone';
import { Formik } from 'formik';
import { LoadingButton } from '@mui/lab';

const PublicationUser = () => {

    const [open, setOpen] = React.useState(false);
    const theme = useTheme();
    const fullScreen = useMediaQuery(theme.breakpoints.down('sm'));
    const [pubContent, setPubContent] = useState('');
    const [pubList, setPubList] = useState([])
    const [pub, setPub] = useState({
        idPub: "",
        contenu: pubContent,
        visibilite: "",
        status: "",
        typeOfPub: ""
    })
    const [openEdit, setOpenEdit] = useState(false)

    const getPubs = async () => {
        const res = await fetch("http://localhost:8082/websemantique/publications/getAllPub")
        const data = await res.json()
        setPubList(data);
    }

    useEffect(() => {
        let d = async () => await getPubs()

        d()
    }, [])

    var toolbarOptions = [
        ['bold', 'italic', 'underline'],        // toggled buttons

        [{ 'header': 1 }, { 'header': 2 }],               // custom button values
        [{ 'list': 'ordered' }, { 'list': 'bullet' }],
        [{ 'indent': '-1' }, { 'indent': '+1' }],          // outdent/indent
        [{ 'direction': 'rtl' }],                         // text direction

        [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
        [{ 'header': [1, 2, 3, 4, 5, 6, false] }],

        [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
        [{ 'font': [] }],
        [{ 'align': [] }],
        ['image'],

        ['clean']                                         // remove formatting button
    ];

    function handleClickOpen() {
        setOpen(true);
    }

    function handleClickOpenEdit(pub) {
        setPub(pub);
        setOpenEdit(true);
    }

    function handleClose() {
        setOpen(false);
    }

    function handleCloseEdit() {
        setOpenEdit(false);
    }

    const module = {
        toolbar: toolbarOptions
    }

    const ContentBox = styled('div')(() => ({
        display: 'flex',
        flexWrap: 'wrap',
        alignItems: 'center',
    }));

    const StyledCard = styled(Card)(({ theme }) => ({
        display: 'flex',
        flexWrap: 'wrap',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: '24px !important',
        background: theme.palette.background.paper,
        [theme.breakpoints.down('sm')]: { padding: '16px !important' },
    }));

    const Heading = styled('h6')(({ theme }) => ({
        margin: 0,
        marginTop: '4px',
        fontSize: '14px',
        fontWeight: '500',
        color: theme.palette.primary.main,
    }));

    const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
        hour12: false,
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setPub({ ...pub, [name]: value, contenu: pubContent });
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(pub);
        fetch('http://localhost:8082/websemantique/publications/addPub', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(pub),
        })
            .then((response) => console.log(response))
            .then(async (data) => {
                console.log('Response from server:', data);
                const res = await fetch("http://localhost:8082/websemantique/publications/getAllPub")
                const pubs = await res.json()
                setPubList(pubs);
                handleClose()
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }

    function deleteItem(id) {
        const shouldDelete = window.confirm("Are you sure you want to delete this Publication ?");
        if (shouldDelete) {
            fetch(`http://localhost:8082/websemantique/publications/deletePub/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                }
            })
                .then(async (response) => {
                    if (response.ok) {
                        console.log('Publication deleted successfully');
                        const res = await fetch("http://localhost:8082/websemantique/publications/getAllPub")
                        const data = await res.json()
                        setPubList(data);
                    } else {
                        console.error('Failed to delete Publication');
                    }
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        } else {
            console.log("Deletion canceled");
        }
    }

    const handleSubmitEdit = (e) => {
        e.preventDefault();
        console.log(pub);
        fetch('http://localhost:8082/websemantique/publications/editPub', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(pub),
        })
            .then((response) => response.json())
            .then(async (data) => {
                console.log('Response from server:', data);
                const res = await fetch("http://localhost:8082/websemantique/publications/getAllPub")
                const pubs = await res.json()
                setPubList(pubs);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }

    return (
        <Container>
            <Box className="breadcrumb" style={{ marginTop: "3%" }}>
                <Breadcrumb routeSegments={[{ name: "Material", path: "/material" }, { name: "Publication" }]} style={{ marginTop: "5%" }} />
                <Box>
                    <Button variant="outlined" color="primary" onClick={handleClickOpen} style={{ marginTop: "1%" }}>
                        Add a new publication
                    </Button>

                    <Dialog
                        fullScreen={fullScreen}
                        open={open}
                        onClose={handleClose}
                        aria-labelledby="responsive-dialog-title"
                    >
                        <DialogTitle id="responsive-dialog-title">Add New Publication</DialogTitle>

                        <DialogContent>
                            <DialogContentText>
                                <Card className="card">
                                    <Grid container>
                                        <Grid item sm={12} xs={12}>
                                            <Box p={4} height="100%">
                                                <label style={{ color: "#004aad" }}>Publication content with text editor</label>
                                                <ReactQuill modules={module} theme="snow" value={pubContent} onChange={setPubContent} />
                                                <Formik>
                                                    <form onSubmit={handleSubmit}>
                                                        <label style={{ color: "#004aad" }}>Publication ID</label>
                                                        <TextField
                                                            fullWidth
                                                            size="small"
                                                            name="idPub"
                                                            type="number"
                                                            label="ID Publication"
                                                            variant="outlined"
                                                            value={pub.idPub}
                                                            onChange={handleChange}
                                                            sx={{ mb: 2 }}
                                                        />
                                                        <label style={{ color: "#004aad" }}>Publication Type</label>
                                                        <select
                                                            style={{ width: "473px", height: "40px", marginBottom: "4%" }}
                                                            fullWidth
                                                            size="small"
                                                            name="typeOfPub"
                                                            label="Type Publication"
                                                            variant="outlined"
                                                            value={pub.typeOfPub}
                                                            onChange={handleChange}
                                                            sx={{ mb: 2 }}
                                                        >
                                                            <option value="PublicationTextuelle">Publication Text</option>
                                                            <option value="PublicationImage">Publication Image</option>
                                                            <option value="PublicationVideo">Publication Video</option>
                                                        </select>
                                                        <label style={{ color: "#004aad" }}>Click on this input to get content</label>
                                                        <TextField
                                                            fullWidth
                                                            size="small"
                                                            type="text"
                                                            name="contenu"
                                                            label="Content"
                                                            variant="outlined"
                                                            value={pub.contenu}
                                                            onChange={handleChange}
                                                            sx={{ mb: 3 }}
                                                        />
                                                        <label style={{ color: "#004aad" }}>Publication visibility</label>
                                                        <select
                                                            style={{ width: "473px", height: "40px", marginBottom: "4%" }}
                                                            fullWidth
                                                            size="small"
                                                            name="visibilite"
                                                            label="Visibility"
                                                            variant="outlined"
                                                            value={pub.visibilite}
                                                            onChange={handleChange}
                                                            sx={{ mb: 3 }}
                                                        >
                                                            <option value="Public">Public</option>
                                                            <option value="Private">Private</option>
                                                            <option value="Specific">Specific</option>
                                                        </select>
                                                        <label style={{ color: "#004aad" }}>Publication status</label>
                                                        <select
                                                            style={{ width: "473px", height: "40px", marginBottom: "4%" }}
                                                            fullWidth
                                                            size="small"
                                                            name="status"
                                                            label="Satuts"
                                                            variant="outlined"
                                                            value={pub.status}
                                                            onChange={handleChange}
                                                            sx={{ mb: 2 }}
                                                        >
                                                            <option value="Active">Active</option>
                                                            <option value="Archive">Archive</option>
                                                        </select>
                                                        <LoadingButton
                                                            type="submit"
                                                            color="primary"
                                                            variant="contained"
                                                            sx={{ mb: 2, mt: 3 }}
                                                        >
                                                            Create Publication
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

            <Grid container spacing={3} sx={{ mb: '24px' }} style={{ marginTop: "1%", marginLeft: "8%" }}>
                {pubList.length > 0 && pubList.map((p, index) => (
                    <Grid xs={12} md={10} key={index} style={{ marginTop: "2%" }}>
                        <StyledCard elevation={6}>
                            <ContentBox>
                                <div className="d-flex flex-row">
                                    {/* <Icon className="icon" style={{ marginTop: "-1%" }}><AccountCircleTwoToneIcon></AccountCircleTwoToneIcon></Icon> */}
                                    <p>Created at {new Date(p.dateCreationPub.value).toLocaleDateString("en-US", options)}</p>
                                    <Box ml="12px">
                                        <Small>Publication with a {p.visibilite.value} visibility</Small><br />
                                        <Small>Publication with ID {p.idPub.value}</Small>
                                        <Heading>Content <br /></Heading>
                                        <div dangerouslySetInnerHTML={{ __html: p.contenu.value }}></div>
                                    </Box>
                                </div>
                            </ContentBox>
                            <div className='d-flex justify-content-center'>
                                <Button onClick={() => handleClickOpenEdit(p)} color="primary">
                                    Edit
                                </Button>
                                <Button
                                    onClick={() => deleteItem(p.idPub.value)}
                                >
                                    Delete
                                </Button>
                            </div>

                            <Dialog
                                fullScreen={fullScreen}
                                open={openEdit}
                                onClose={handleCloseEdit}
                                aria-labelledby="responsive-dialog-title"
                            >
                                <DialogTitle id="responsive-dialog-title">Edit Publication</DialogTitle>
                                <DialogContent>
                                    <DialogContentText>
                                        <Card className="card">
                                            <Grid container>
                                                <Grid item sm={12} xs={12}>
                                                    <Box p={4} height="100%">
                                                        <label style={{ color: "#004aad" }}>Publication content with text editor</label>
                                                        <ReactQuill modules={module} theme="snow" value={pubContent} onChange={setPubContent} />
                                                        <Formik>
                                                            <form onSubmit={handleSubmitEdit}>
                                                                <label style={{ color: "#004aad" }}>Publication ID</label>
                                                                <TextField
                                                                    fullWidth
                                                                    size="small"
                                                                    name="idPub"
                                                                    type="number"
                                                                    label="ID Publication"
                                                                    variant="outlined"
                                                                    value={pub.idPub}
                                                                    onChange={handleChange}
                                                                    sx={{ mb: 2 }}
                                                                />
                                                                <label style={{ color: "#004aad" }}>Publication Type</label>
                                                                <select
                                                                    style={{ width: "473px", height: "40px", marginBottom: "4%" }}
                                                                    fullWidth
                                                                    size="small"
                                                                    name="typeOfPub"
                                                                    label="Type Publication"
                                                                    variant="outlined"
                                                                    value={pub.typeOfPub}
                                                                    onChange={handleChange}
                                                                    sx={{ mb: 2 }}
                                                                >
                                                                    <option value="PublicationTextuelle">Publication Text</option>
                                                                    <option value="PublicationImage">Publication Image</option>
                                                                    <option value="PublicationVideo">Publication Video</option>
                                                                </select>
                                                                <label style={{ color: "#004aad" }}>Click on this input to get content</label>
                                                                <TextField
                                                                    fullWidth
                                                                    size="small"
                                                                    type="text"
                                                                    name="contenu"
                                                                    label="Content"
                                                                    variant="outlined"
                                                                    value={pub.contenu}
                                                                    onChange={handleChange}
                                                                    sx={{ mb: 3 }}
                                                                />
                                                                <label style={{ color: "#004aad" }}>Publication visibility</label>
                                                                <select
                                                                    style={{ width: "473px", height: "40px", marginBottom: "4%" }}
                                                                    fullWidth
                                                                    size="small"
                                                                    name="visibilite"
                                                                    label="Visibility"
                                                                    variant="outlined"
                                                                    value={pub.visibilite}
                                                                    onChange={handleChange}
                                                                    sx={{ mb: 3 }}
                                                                >
                                                                    <option value="Public">Public</option>
                                                                    <option value="Private">Private</option>
                                                                    <option value="Specific">Specific</option>
                                                                </select>
                                                                <label style={{ color: "#004aad" }}>Publication status</label>
                                                                <select
                                                                    style={{ width: "473px", height: "40px", marginBottom: "4%" }}
                                                                    fullWidth
                                                                    size="small"
                                                                    name="status"
                                                                    label="Satuts"
                                                                    variant="outlined"
                                                                    value={pub.status}
                                                                    onChange={handleChange}
                                                                    sx={{ mb: 2 }}
                                                                >
                                                                    <option value="Active">Active</option>
                                                                    <option value="Archive">Archive</option>
                                                                </select>
                                                                <LoadingButton
                                                                    type="submit"
                                                                    color="primary"
                                                                    variant="contained"
                                                                    sx={{ mb: 2, mt: 3 }}
                                                                >
                                                                    Create Publication
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
                                    <Button onClick={handleCloseEdit} color="primary">
                                        Cancel
                                    </Button>
                                </DialogActions>
                            </Dialog>
                        </StyledCard>
                    </Grid>
                ))}
            </Grid>
        </Container>

    );
};

export default PublicationUser;
