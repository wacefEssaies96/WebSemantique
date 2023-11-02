import React, { useState, useEffect } from 'react';
import Axios from 'axios';
import {
  Box,
  Card,
  CardHeader,
  Icon,
  IconButton,
  MenuItem,
  Select,
  styled,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography,
  useTheme,
} from '@mui/material';
import { Paragraph } from 'app/components/Typography';

const CardHeaderWrapper = styled(CardHeader)(({ theme }) => ({
  display: 'flex',
  paddingLeft: theme.spacing(3),
  paddingRight: theme.spacing(3),
  marginBottom: theme.spacing(2),
  alignItems: 'center',
  justifyContent: 'space-between',
}));

const Title = styled('span')(() => ({
  fontSize: '1rem',
  fontWeight: '500',
  textTransform: 'capitalize',
}));

const ProductTable = styled(Table)(({ theme }) => ({
  minWidth: 400,
  whiteSpace: 'pre',
  '& small': {
    width: 50,
    height: 15,
    borderRadius: 500,
    boxShadow: theme.shadows[1],
  },
  '& td': { borderBottom: 'none' },
  '& td:first-of-type': { paddingLeft: theme.spacing(2) },
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

const UsersTable = () => {
  const { palette } = useTheme();
  const bgError = palette.error.main;
  const bgPrimary = palette.primary.main;
  const bgSecondary = palette.secondary.main;
  const [users, setUsers] = useState([]);
  const [userGroups, setUserGroups] = useState([]);
  const [managedGroups, setManagedGroups] = useState([]);
  const [sharedGroups, setSharedGroups] = useState([]);

  useEffect(() => {
    Axios.get('http://localhost:8082/websemantique/controller/users')
      .then((response) => {
        setUsers(response.data);
      })
      .catch((error) => {
        console.error('Error fetching users:', error);
      });
  }, []);

  useEffect(() => {
    // Function to fetch user groups
    const fetchUserGroups = async (userName) => {
      try {
        const response = await Axios.get(`http://localhost:8082/websemantique/controller/grpuser/${userName}`);
        const groups = response.data.map(group => group.nomGroupe.value).join(', ');

        setUserGroups((prevUserGroups) => ({
          ...prevUserGroups,
          [userName]: groups || 'Aucun groupe',
        }));
      } catch (error) {
        console.error('Error fetching user groups:', error);
        setUserGroups((prevUserGroups) => ({
          ...prevUserGroups,
          [userName]: 'Aucun groupe',
        }));
      }
    };

    // Function to fetch managed groups for a user
    const fetchManagedGroups = async (userName) => {
      try {
        const response = await Axios.get(`http://localhost:8082/websemantique/controller/grpgruser/${userName}`);
        const managedGroups = response.data.map(group => group.nomGroupe.value).join(', ');

        setManagedGroups((prevManagedGroups) => ({
          ...prevManagedGroups,
          [userName]: managedGroups || 'Aucun groupe',
        }));
      } catch (error) {
        console.error('Error fetching managed groups:', error);
        setManagedGroups((prevManagedGroups) => ({
          ...prevManagedGroups,
          [userName]: 'Aucun groupe',
        }));
      }
    };

    // Function to fetch shared groups for a user
    const fetchSharedGroups = async (userName) => {
      try {
        const response = await Axios.get(`http://localhost:8082/websemantique/controller/grppuser/${userName}`);
        const sharedGroups = response.data.map(group => group.nomGroupe.value).join(', ');

        setSharedGroups((prevSharedGroups) => ({
          ...prevSharedGroups,
          [userName]: sharedGroups || 'Aucun groupe',
        }));
      } catch (error) {
        console.error('Error fetching shared groups:', error);
        setSharedGroups((prevSharedGroups) => ({
          ...prevSharedGroups,
          [userName]: 'Aucun groupe',
        }));
      }
    };

    // Fetch user groups, managed groups, and shared groups for each user
    users.forEach((u) => {
      fetchUserGroups(u.nom.value);
      fetchManagedGroups(u.nom.value);
      fetchSharedGroups(u.nom.value);
    });
  }, [users]);

  return (
    <Card elevation={3} sx={{ pt: '20px', mb: 3 }}>
      <CardHeaderWrapper>
        <Title>Top Selling Products</Title>
        <Select size="small" defaultValue="this_month">
          <MenuItem value="this_month">This Month</MenuItem>
          <MenuItem value="last_month">Last Month</MenuItem>
        </Select>
      </CardHeaderWrapper>

      <Box overflow="auto">
        <ProductTable>
          <TableHead>
            <TableRow>
              <TableCell sx={{ px: 3 }} colSpan={4}>
                Name
              </TableCell>
              <TableCell sx={{ px: 3 }} colSpan={4}>
                Group
              </TableCell>
              <TableCell sx={{ px: 3 }} colSpan={4}>
                Managed Groups
              </TableCell>
              <TableCell sx={{ px: 3 }} colSpan={4}>
                Shared Groups
              </TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {users.map((u) => (
              <TableRow key={u.nom.value}>
                <TableCell colSpan={4} align="left" sx={{ px: 0, textTransform: 'capitalize' }}>
                  <Box display="flex" alignItems="center">
                    <Paragraph sx={{ m: 0, ml: 4 }}>{u.nom.value}</Paragraph>
                  </Box>
                </TableCell>
                <TableCell colSpan={4} align="left" sx={{ px: 0, textTransform: 'capitalize' }}>
                  <Box display="flex" alignItems="center">
                    <Paragraph sx={{ m: 0, ml: 4, whiteSpace: 'nowrap' }}>
                      {userGroups[u.nom.value]}
                    </Paragraph>
                  </Box>
                </TableCell>
                <TableCell colSpan={4} align="left" sx={{ px: 0, textTransform: 'capitalize' }}>
                  <Box display="flex" alignItems="center">
                    <Paragraph sx={{ m: 0, ml: 4, whiteSpace: 'nowrap' }}>
                      {managedGroups[u.nom.value]}
                    </Paragraph>
                  </Box>
                </TableCell>
                <TableCell colSpan={4} align="left" sx={{ px: 0, textTransform: 'capitalize' }}>
                  <Box display="flex" alignItems="center">
                    <Paragraph sx={{ m: 0, ml: 4, whiteSpace: 'nowrap' }}>
                      {sharedGroups[u.nom.value]}
                    </Paragraph>
                  </Box>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </ProductTable>
      </Box>
    </Card>
  );
};

export default UsersTable;
