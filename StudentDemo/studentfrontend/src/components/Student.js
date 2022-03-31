import * as React from "react";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import { Container, Button, Paper } from "@material-ui/core";
import { useState } from "react";
import { useEffect } from "react";
import { makeStyles } from "@material-ui/core";


const useStyles = makeStyles((theme) => ({
  root: {
    "& > *": {
      margin: theme.spacing(1),
    },
  },
}));

export default function Student() {
  const [name, setName] = useState("");
  const [address, setAddress] = useState("");
  const [students, setStudents] = useState([]);
  // const classes = useStyles()
  const handleClick = (e) => {
    e.preventDefault();
    const student = { name, address };
    console.log(student);
    fetch("http://localhost:8080/student/add", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(student),
    }).then(() => {
      console.log("New Student Added");
    });
  };

  useEffect(() => {
    fetch("http://localhost:8080/student/getAll")
      .then((res) => res.json())
      .then((result) => {
        setStudents(result);
      });
  }, []);
  return (
    <Container>
      <h1> Add Student</h1>
      <Box
        component="form"
        sx={{
          "& > :not(style)": { m: 1, width: "25ch" },
        }}
        noValidate
        autoComplete="off"
      >
        <TextField
          id="standard-basic"
          label="Student Name"
          variant="standard"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <TextField
          id="standard-basic"
          label="Student Address"
          variant="standard"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
        />
      </Box>
      <Button color="secondary" onClick={handleClick}>
        Submit
      </Button>
      <h1>Students</h1>
      <Paper elevation={3} >
        {students.map((student) => (
          <Paper elevation={6} style={{margin: "10px", padding:"15px", textAlign:"left"}} key={student.id}>
            Id: {student.id}
            Name: {student.name}
            Address: {student.address}
          </Paper>
        ))}
      </Paper>
    </Container>
  );
}
