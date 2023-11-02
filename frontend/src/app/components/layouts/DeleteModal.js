import React from 'react'
import {Dialog, Button, DialogActions, DialogContent, DialogTitle} from "@mui/material";

export default function DeleteModal ({ showModal,hideModal, confirmModal, id, message }) {

  return (
    <Dialog open={showModal}  onClose={hideModal}>

        <DialogTitle>Delete Confirmation</DialogTitle>
        <i onClick={hideModal} style={{ fontSize: "25px", color: '#ff0000', display: "flex", justifyContent: "flex-end" }} className='feather icon-x'></i>
      <DialogContent><div className="alert alert-danger">{message}</div></DialogContent>
      <DialogActions>
        <Button style={{backgroundColor:"blue",  color: '#ffffff'}} variant="light" onClick={hideModal}>
          Cancel
        </Button>
        <Button style={{backgroundColor:"red",  color: '#ffffff'}} variant="danger" onClick={() => confirmModal(id)}>
          Delete
        </Button> 
      </DialogActions>
    </Dialog>
  )
}