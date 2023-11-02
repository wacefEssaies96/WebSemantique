import { Box, Button, Card, Container } from '@mui/material';
import { Breadcrumb } from 'app/components';
import React, { useState } from 'react';
import 'react-quill/dist/quill.snow.css'
import { CardBody, CardHeader, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const AddEvent = () => {
    const router = useNavigate()

    const [evenement, setEvenement] = useState({
        idEvenement: "",
        nomEvenement: "",
        descriptionEvenement: "",
        lieu: "",
        type: ""
    })
    const createEvent = async (event) => {
        const JSONdata = JSON.stringify({
            idEvenement: event.target.idEvenement.value,
            nomEvenement: event.target.nomEvenement.value,
            descriptionEvenement: event.target.descriptionEvenement.value,
            lieu: event.target.lieu.value,
            type: event.target.type.value
        })
        await fetch(`http://localhost:8082/websemantique/controller/add-event`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSONdata,
        })
    }
    const handleSubmit = async (event) => {
        event.preventDefault()
        await createEvent(event)
        router('/events')
    }

    return (
        <Container>
            <Box className="breadcrumb">
                <Breadcrumb routeSegments={[{ name: "Material", path: "/material" }, { name: "Events" }]} style={{ marginTop: "5%" }} />
            </Box>
            <Card className='mt-6'>
                <CardHeader>
                    <h3>Add new Event</h3>
                </CardHeader>
                <CardBody>
                    <Form onSubmit={handleSubmit} encType='multipart/form-data'>
                        <Form.Group>
                            <Form.Label htmlFor="id">ID</Form.Label>
                            <Form.Control defaultValue={evenement.idEvenement} placeholder="ID" type="text" id="title" name="idEvenement" required></Form.Control>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label htmlFor="title">Title</Form.Label>
                            <Form.Control defaultValue={evenement.nomEvenement} placeholder="Title" type="text" id="title" name="nomEvenement" required minLength={4} maxLength={50}></Form.Control>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label htmlFor="description">Description</Form.Label>
                            <Form.Control defaultValue={evenement.descriptionEvenement} placeholder="Description" type="text" id="descriptionEvenement" name="descriptionEvenement" required minLength={4} maxLength={50}></Form.Control>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label htmlFor="place">Place</Form.Label>
                            <Form.Control defaultValue={evenement.lieu} placeholder="Place" type="text" id="lieu" name="lieu" required minLength={4} maxLength={50}></Form.Control>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label htmlFor="type">Type</Form.Label>
                            <Form.Control defaultValue={evenement.type} placeholder="Type" type="text" id="type" name="type" required minLength={4} maxLength={50}></Form.Control>
                        </Form.Group>
                        <Button type='submit'>Submit</Button>
                    </Form>
                </CardBody>
            </Card>

        </Container>

    );
}

export default AddEvent;
