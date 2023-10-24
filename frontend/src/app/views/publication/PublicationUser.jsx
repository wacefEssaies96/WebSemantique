import { Box, Button, Card, Container, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, Fab, Icon, lighten, styled, useTheme, Checkbox, Hidden, IconButton, Avatar } from '@mui/material';
import { Breadcrumb } from 'app/components';
import useMediaQuery from '@mui/material/useMediaQuery';
import React, { Fragment, useEffect, useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css'
import { DateRange, StarOutline } from '@mui/icons-material';
import { format } from 'date-fns';
import axios from 'axios';
import { Table } from 'react-bootstrap';

const PublicationUser = () => {

    const [open, setOpen] = React.useState(false);
    const theme = useTheme();
    const fullScreen = useMediaQuery(theme.breakpoints.down('sm'));
    const [value, setValue] = useState('');
    const [table, setTable] = useState();
    var toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
        ['blockquote', 'code-block'],

        [{ 'header': 1 }, { 'header': 2 }],               // custom button values
        [{ 'list': 'ordered' }, { 'list': 'bullet' }],
        [{ 'script': 'sub' }, { 'script': 'super' }],      // superscript/subscript
        [{ 'indent': '-1' }, { 'indent': '+1' }],          // outdent/indent
        [{ 'direction': 'rtl' }],                         // text direction

        [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
        [{ 'header': [1, 2, 3, 4, 5, 6, false] }],

        [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
        [{ 'font': [] }],
        [{ 'align': [] }],
        ['link', 'image'],

        ['clean']                                         // remove formatting button
    ];

    function handleClickOpen() {
        setOpen(true);
    }

    function handleClose() {
        setOpen(false);
    }

    const module = {
        toolbar: toolbarOptions
    }

    const ContentBox = styled('div')(() => ({
        display: 'flex',
        flexWrap: 'wrap',
        alignItems: 'center',
    }));

    const FabIcon = styled(Fab)(() => ({
        width: '44px !important',
        height: '44px !important',
        boxShadow: 'none !important',
    }));

    const H3 = styled('h3')(({ textcolor }) => ({
        margin: 0,
        color: textcolor,
        fontWeight: '500',
        marginLeft: '12px',
    }));

    const H1 = styled('h1')(({ theme }) => ({
        margin: 0,
        flexGrow: 1,
        color: theme.palette.text.secondary,
    }));

    const Span = styled('span')(({ textcolor }) => ({
        fontSize: '13px',
        color: textcolor,
        marginLeft: '4px',
    }));

    const IconBox = styled('div')(() => ({
        width: 16,
        height: 16,
        color: '#fff',
        display: 'flex',
        overflow: 'hidden',
        borderRadius: '300px ',
        justifyContent: 'center',
        '& .icon': { fontSize: '14px' },
    }));

    const { palette } = useTheme();
    const textError = palette.error.main;
    const bgError = lighten(palette.error.main, 0.85);

    const StyledAvatar = styled(Avatar)(() => ({
        width: '32px !important',
        height: '32px !important',
    }));

    const textMuted = palette.text.secondary;

    const ProjectName = styled(Span)(({ theme }) => ({
        marginLeft: 24,
        fontWeight: '500',
        [theme.breakpoints.down('sm')]: { marginLeft: 4 },
    }));

    const getData = async () => {
        try {
            const response = await fetch('http://localhost:8081/publicationMS/publication');
            if (!response.ok) {
                setTable(response)
                throw new Error(`HTTP error! Status: ${response.status}`);

            }
            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            await getData();
        };

        fetchData();
    }, []);

    return (
        <Container>
            <Box className="breadcrumb">
                <Breadcrumb routeSegments={[{ name: "Material", path: "/material" }, { name: "Publication" }]} style={{ marginTop: "5%" }} />
                <Box>
                    <Button variant="outlined" color="primary" onClick={handleClickOpen}>
                        Add a new publication
                    </Button>
                    <table>
                        {table && table.length > 0 && table.map((e, i) => <tr key={i}>
                            <td>{e}</td>
                        </tr>)}
                    </table>

                    <Dialog
                        fullScreen={fullScreen}
                        open={open}
                        onClose={handleClose}
                        aria-labelledby="responsive-dialog-title"
                    >
                        <DialogTitle id="responsive-dialog-title">Publication content</DialogTitle>

                        <DialogContent>
                            <DialogContentText>
                                <ReactQuill modules={module} theme="snow" value={value} onChange={setValue} />
                            </DialogContentText>
                        </DialogContent>

                        <DialogActions>
                            <Button onClick={handleClose} color="primary">
                                Cancel
                            </Button>

                            <Button onClick={handleClose} color="primary" autoFocus>
                                Add
                            </Button>
                        </DialogActions>
                    </Dialog>
                </Box>
            </Box>
            <Grid item xs={12} md={6}>
                <Card elevation={3} sx={{ p: 2 }}>
                    <Hidden smDown>
                        <Grid item xs={3}>
                            <Box display="flex" position="relative" marginLeft="-0.875rem !important">
                                <StyledAvatar src="/assets/images/face-4.jpg" />
                            </Box>
                        </Grid>
                    </Hidden>

                    <ContentBox>
                        <FabIcon size="medium" sx={{ background: bgError, overflow: 'hidden' }}>
                            <Icon sx={{ color: textError }}>favorite_border</Icon>
                        </FabIcon>
                    </ContentBox>

                    <ContentBox sx={{ pt: 2 }}>
                        <H1>Publication</H1>
                        <ProjectName>Pub content</ProjectName>
                    </ContentBox>
                </Card>
            </Grid>

            <Fragment>
                <Card sx={{ py: 1, px: 2 }} className="project-card">
                    <Grid container alignItems="center">
                        <Grid item md={5} xs={7}>
                            <Box display="flex" alignItems="center">
                                <Checkbox />
                                <Hidden smDown>
                                    <DateRange size="small">
                                        <Icon>date_range</Icon>
                                    </DateRange>
                                </Hidden>

                            </Box>
                        </Grid>

                        <Grid item md={3} xs={4}>
                            <Box color={textMuted}>{format(new Date().getTime(), 'MM/dd/yyyy hh:mma')}</Box>
                        </Grid>



                        <Grid item xs={1}>
                            <Box display="flex" justifyContent="flex-end">
                                <IconButton>
                                    <Icon>more_vert</Icon>
                                </IconButton>
                            </Box>
                        </Grid>
                    </Grid>
                </Card>
                <Box py={1} />
            </Fragment>

        </Container>

    );
};

export default PublicationUser;
