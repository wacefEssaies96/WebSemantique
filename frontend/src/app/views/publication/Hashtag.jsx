import {
    Box,
    Card,
    Container,
    Icon,
    IconButton,
    styled,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow} from '@mui/material';
import { Paragraph } from 'app/components/Typography';
import React, { useEffect, useState } from 'react';
import 'react-quill/dist/quill.snow.css'

const Title = styled('span')(() => ({
    fontSize: '1rem',
    fontWeight: '500',
    marginRight: '.5rem',
    textTransform: 'capitalize',
}));

const CardHeader = styled(Box)(() => ({
    display: 'flex',
    paddingLeft: '24px',
    paddingRight: '24px',
    marginBottom: '12px',
    alignItems: 'center',
    justifyContent: 'space-between',
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

const Hashtag = () => {

    const [hashList, setHashList] = useState([])

    const getHashs = async () => {
        const res = await fetch("http://localhost:8082/websemantique/hashtags/getAllHash")
        const data = await res.json()
        setHashList(data);
    }

    useEffect(() => {
        let d = async () => await getHashs()

        d()
    }, [])

    const deleteHashtag = (name) => {
        const shouldDelete = window.confirm("Are you sure you want to delete this hashtag ?");
        if (shouldDelete) {
            fetch(`http://localhost:8082/websemantique/hashtags/deleteHash/${name}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                }
            })
                .then(async (response) => {
                    if (response.ok) {
                        console.log('Hashtag deleted successfully');
                        const res = await fetch("http://localhost:8082/websemantique/hashtags/getAllHash")
                        const data = await res.json()
                        setHashList(data);
                    } else {
                        console.error('Failed to delete Hashtag');
                    }
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        } else {
            console.log("Deletion canceled");
        }
    }
console.log(hashList);
    return (
        <Container>
            <Card elevation={3} sx={{ pt: '20px', mb: 3 }} style={{marginTop: "3%"}}>
                <CardHeader>
                    <Title>List of all Hashtags</Title>
                </CardHeader>

                <Box overflow="auto">
                    <ProductTable>
                        <TableHead>
                            <TableRow>
                                <TableCell sx={{ px: 3 }} colSpan={4}>
                                    IdHash
                                </TableCell>
                                <TableCell sx={{ px: 0 }} colSpan={2}>
                                    Nom
                                </TableCell>
                                <TableCell sx={{ px: 0 }} colSpan={2}>
                                    Popularite
                                </TableCell>
                                <TableCell sx={{ px: 0 }} colSpan={2}>
                                    Description
                                </TableCell>
                                <TableCell sx={{ px: 0 }} colSpan={2}>
                                    Categorie
                                </TableCell>
                                <TableCell sx={{ px: 0 }} colSpan={1}>
                                    Action
                                </TableCell>
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {hashList.length > 0 && hashList.map((h, index) => (
                                <TableRow key={index} hover>
                                    <TableCell colSpan={4} align="left" sx={{ px: 0, textTransform: 'capitalize' }}>
                                        <Box display="flex" alignItems="center">
                                            <Paragraph sx={{ m: 0, ml: 4 }}>{h.idHash.value}</Paragraph>
                                        </Box>
                                    </TableCell>

                                    <TableCell align="left" colSpan={2} sx={{ px: 0, textTransform: 'capitalize' }}>
                                        {h.nom.value}
                                    </TableCell>

                                    <TableCell sx={{ px: 0 }} align="left" colSpan={2}>
                                        {h.popularite.value}
                                    </TableCell>

                                    <TableCell sx={{ px: 0 }} align="left" colSpan={2}>
                                        {h.description.value}
                                    </TableCell>

                                    <TableCell sx={{ px: 0 }} align="left" colSpan={2}>
                                        {h.categorie.value}
                                    </TableCell>

                                    <TableCell sx={{ px: 0 }} colSpan={1}>
                                        <IconButton onClick={()=>{deleteHashtag(h.nom.value)}}>
                                            <Icon color="primary">Delete</Icon>
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </ProductTable>
                </Box>
            </Card>
        </Container>

    );
};

export default Hashtag;