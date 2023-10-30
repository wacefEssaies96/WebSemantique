import { Fragment } from 'react';
// import Campaigns from './shared/Campaigns';
// import DoughnutChart from './shared/Doughnut';
import RowCards from '../dashboard/shared/RowCards';
// import StatCards from './shared/StatCards';
// import StatCards2 from './shared/StatCards2';
// import TopSellingTable from './shared/TopSellingTable';
// import UpgradeCard from './shared/UpgradeCard';
import React from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { DatePicker } from "@mui/lab";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import LocalizationProvider from "@mui/lab/LocalizationProvider";
import {
  Button,
  Checkbox,
  FormControlLabel,
  Grid,
  Icon,
  Radio,
  RadioGroup,
  TextareaAutosize,
  styled,
  useTheme,

  
} from "@mui/material";
import DoughnutChart from "../charts/echarts/Doughnut";
import { Span } from "app/components/Typography";
import { useEffect, useState } from "react";
import { TextValidator, ValidatorForm } from "react-material-ui-form-validator";

const TextField = styled(TextValidator)(() => ({
  width: "100%",
  marginBottom: "16px",
}));
const TextArea = styled(TextValidator)(() => ({
  width: "100%",
  marginBottom: "16px"
  /* Autres styles CSS personnalisés */
}));
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

const Groupe = () => {
    const [state, setState] = useState({ date: new Date() });
    const [showChart, setShowChart] = useState(false);

  const handleShowChart = () => {
    setShowChart(true);
  };

  const handleHideChart = () => {
    setShowChart(false);
  };
  useEffect(() => {
    ValidatorForm.addValidationRule("isPasswordMatch", (value) => {
      if (value !== state.password) return false;

      return true;
    });
    return () => ValidatorForm.removeValidationRule("isPasswordMatch");
  }, [state.password]);

  const handleSubmit = (event) => {
    // console.log("submitted");
    // console.log(event);
  };

  const handleChange = (event) => {
    event.persist();
    setState({ ...state, [event.target.name]: event.target.value });
  };

  const handleDateChange = (date) => setState({ ...state, date });

  const {
    username,
    firstName,
    mobile,
    password,
    confirmPassword,
    email,
    description,
    type

  } = state;

    const [open, setOpen] = React.useState(false);

    function handleClickOpen() {
      setOpen(true);
    }
  
    function handleClose() {
      setOpen(false);
    }
  const { palette } = useTheme();
  const StyledButton = styled(Button)(({ theme }) => ({
    margin: theme.spacing(1),
  }));
  return (
    <Fragment>
                <StyledButton variant="outlined" onClick={handleClickOpen}>Add a new group</StyledButton>
                <div>
      <Button onClick={handleShowChart}>Afficher le graphique</Button>
      {showChart && (
        <div>
          <Button onClick={handleHideChart}>Masquer le graphique</Button>
          <DoughnutChart height="300px" color={['#FF5733', '#337DFF', '#33FF57']} />
        </div>
      )}
    </div>
      <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">Add your group</DialogTitle>
        <DialogContent>
          <DialogContentText>
            To add  a group, please enter the information in this form. We will send updates
            occasionally.
          </DialogContentText>
          <div>
      <ValidatorForm onSubmit={handleSubmit} onError={() => null}>
        <Grid container spacing={6}>
          <Grid item lg={6} md={6} sm={12} xs={12} sx={{ mt: 2 }}>
            <TextField
              type="text"
              name="Group name"
              id="standard-basic"
              value={username || ""}
              onChange={handleChange}
              errorMessages={["this field is required"]}
              label="Group (Min length 4, Max length 9)"
              validators={["required", "minStringLength: 4", "maxStringLength: 9"]}
            />

            <TextField
              type="text"
              name="firstName"
              label="First Name"
              onChange={handleChange}
              value={firstName || ""}
              validators={["required"]}
              errorMessages={["this field is required"]}
            />

            <TextField
              type="email"
              name="email"
              label="Email"
              value={email || ""}
              onChange={handleChange}
              validators={["required", "isEmail"]}
              errorMessages={["this field is required", "email is not valid"]}
            />
             <TextArea
              type="text"
              name="Description"
              label="Description"
              value={description || ""}
              onChange={handleChange}
              validators={["required"]}
              errorMessages={["this field is required"]}
            />
{/* 
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <DatePicker
                value={date}
                onChange={handleDateChange}
                renderInput={(props) => (
                  <TextField
                    {...props}
                    label="Date picker"
                    id="mui-pickers-date"
                    sx={{ mb: 2, width: "100%" }}
                  />
                )}
              />
            </LocalizationProvider>

            <TextField
              sx={{ mb: 4 }}
              type="number"
              name="creditCard"
              label="Credit Card"
              onChange={handleChange}
              value={creditCard || ""}
              errorMessages={["this field is required"]}
              validators={["required", "minStringLength:16", "maxStringLength: 16"]}
            /> */}
          </Grid>

          <Grid item lg={6} md={6} sm={12} xs={12} sx={{ mt: 2 }}>
            <TextField
              type="text"
              name="mobile"
              value={mobile || ""}
              label="Mobile Nubmer"
              onChange={handleChange}
              validators={["required"]}
              errorMessages={["this field is required"]}
            />
            <TextField
              name="password"
              type="password"
              label="Password"
              value={password || ""}
              onChange={handleChange}
              validators={["required"]}
              errorMessages={["this field is required"]}
            />
            <TextField
              type="password"
              name="confirmPassword"
              onChange={handleChange}
              label="Confirm Password"
              value={confirmPassword || ""}
              validators={["required", "isPasswordMatch"]}
              errorMessages={["this field is required", "password didn't match"]}
            />
            <RadioGroup
              row
              name="type"
              sx={{ mb: 2 }}
              value={type || ""}
              onChange={handleChange}
            >
              <FormControlLabel
                value="Private Group"
                label="Private Group"
                labelPlacement="end"
                control={<Radio color="secondary" />}
              />

              <FormControlLabel
                value="Public Group"
                label="Public Group"
                labelPlacement="end"
                control={<Radio color="secondary" />}
              />

              <FormControlLabel
                value="Educational Group"
                label="Educational Group"
                labelPlacement="end"
                control={<Radio color="secondary" />}
              />
            </RadioGroup>

            <FormControlLabel
              control={<Checkbox />}
              label="I have read and agree to the terms of service."
            />
          </Grid>
        </Grid>

      
      </ValidatorForm>
    </div>
        </DialogContent>
        <DialogActions>
          <Button variant="outlined" color="secondary" onClick={handleClose}>
            Cancel
          </Button>
          <Button color="primary" variant="contained" type="submit" onClick={handleClose}>
          <Icon>send</Icon>
          <Span sx={{ pl: 1, textTransform: "capitalize" }}>Submit</Span>
        </Button>
        </DialogActions>
      </Dialog>

      <ContentBox className="analytics">
      <Grid container spacing={3}>
            <Grid item lg={8} md={8} sm={12} xs={12}>
             {/*<StatCards />
            <TopSellingTable />
            <StatCards2 />*/}

            <H4>Available groups</H4>
            <RowCards />
          </Grid> 

          {/* <Grid item lg={4} md={4} sm={12} xs={12}>
            <Card sx={{ px: 3, py: 2, mb: 3 }}>
              <Title>Traffic Sources</Title>
              <SubTitle>Last 30 days</SubTitle>

              <DoughnutChart
                height="300px"
                color={[palette.primary.dark, palette.primary.main, palette.primary.light]}
              />
            </Card>

            <UpgradeCard />
            <Campaigns />
          </Grid> */}
        </Grid>
      </ContentBox>
  

    </Fragment>
  );
};

export default Groupe;
