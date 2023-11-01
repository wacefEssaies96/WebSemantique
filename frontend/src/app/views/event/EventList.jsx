import { Box, Button, Card, Container } from '@mui/material';
import { Breadcrumb } from 'app/components';
import React, { useEffect, useState } from 'react';
import 'react-quill/dist/quill.snow.css'
import { CardBody, CardHeader } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

const EventList = () => {

    const [table, setTable] = useState([]);
    const navigate = useNavigate()

    const getAllEvents = async () => {
        const res = await fetch("http://localhost:8082/websemantique/controller/get-all-events")
        const data = await res.json()
        setTable(data);
    }
    const deleteEvent = async (idEvenement) => {
        await fetch(`http://localhost:8082/websemantique/controller/delete-event/${idEvenement}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: {},
        })
        await getAllEvents()
    }

    useEffect(() => {
        let d = async () => await getAllEvents()
        d()
    }, [])

    return (
        <Container>
            <Box className="breadcrumb">
                <Breadcrumb routeSegments={[{ name: "Material", path: "/material" }, { name: "Events" }]} style={{ marginTop: "5%" }} />
            </Box>
            <Card style={{ padding: '20px' }}>
                <CardHeader>

                    <h3>Events</h3>
                    <Button variant="outlined" color="primary" onClick={() => { navigate('/events/add') }}>
                        Add new event
                    </Button>

                </CardHeader>
                <CardBody>
                    <TableContainer component={Paper}>
                        <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>ID</TableCell>
                                    <TableCell align="right">Name</TableCell>
                                    <TableCell align="right">Description</TableCell>
                                    <TableCell align="right">Place</TableCell>
                                    <TableCell align="right">Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {table.map((e, i) => (
                                    <TableRow
                                        key={i}
                                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                    >
                                        <TableCell component="th" scope="row">
                                            {e.idEvenement.value}
                                        </TableCell>
                                        <TableCell align="right">{e.nomEvenement.value}</TableCell>
                                        <TableCell align="right">{e.descriptionEvenement.value}</TableCell>
                                        <TableCell align="right">{e.lieu.value}</TableCell>
                                        <TableCell align="right">
                                            <Button onClick={() => { deleteEvent(e.idEvenement.value) }}>
                                                Delete
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    {/* <Table>

                        <TableHead>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Place</th>
                            <th>Actions</th>
                        </TableHead>
                        <TableBody>
                            {table.length > 0 && table.map((e, i) => <tr key={i}>
                                <td key={e.idEvenement.value}>{e.idEvenement.value}</td>
                                <td key={e.nomEvenement.value}>{e.nomEvenement.value}</td>
                                <td key={e.descriptionEvenement.value}>{e.descriptionEvenement.value}</td>
                                <td key={e.lieu.value + e.idEvenement.value}>{e.lieu.value}</td>
                                <td key={e.idEvenement.value + "123"}>
                                    <Button onClick={() => { deleteEvent(e.idEvenement.value) }}>
                                        Delete
                                    </Button>
                                </td>
                            </tr>)}
                        </TableBody>

                    </Table> */}
                </CardBody>
            </Card>

        </Container>

    );
};

export default EventList;
