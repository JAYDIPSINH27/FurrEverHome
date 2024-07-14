import React, { useEffect } from 'react';
import { useState } from 'react';
import {
    Card,
    CardHeader,
    Typography,
    Avatar,
} from "@material-tailwind/react";
import { DataGrid } from '@mui/x-data-grid';
import { readLocalStorage } from '../../utils/helper'
import { useLocation } from 'react-router-dom'
import { toast } from 'react-toastify'
import axios from 'axios'

const PetAdoptionRequests = () => {

    const token=readLocalStorage("token")
    const location = useLocation();

    const petId = location.state.id;
    const [adopters,setAdopters] = useState([])


    useEffect(()=>{
        axios.get(`${import.meta.env.VITE_BACKEND_BASE_URL}/shelter/${petId}/adoptionrequests`,{
            headers : {
                Authorization : `Bearer ${token}`,
            }
        })
        .then(response=>{


            setAdopters(response.data.petAdopters)
        })
        .catch(error=>{
            toast.error("Cannot get Adoption Requests, Please try again later")
            console.log(error)
        })
    },[])

    const handleChatClick = (adopterId) => {
        toast.info("Redirect to chat feature")
    }

    const columns = [
        { field: 'id', headerName: 'Adopter ID', width: 120 },
        { field: 'firstname', headerName: 'First Name', width: 150 },
        { field: 'lastname', headerName: 'Last Name', width: 150 },
        { field: 'phone_number', headerName: 'Contact', width: 150 },
        { field: 'address', headerName: 'Address', width: 200 },
        { field: 'city', headerName: 'City', width: 150 },
        // {
        //     field: 'chat',
        //     headerName: 'Chat',
        //     width: 100,
        //     renderCell: (params) => (
        //         <Avatar
        //             onClick={() => handleChatClick(params.row.adopterId)}
        //             alt="Chat"
        //         />
        //     )
        // }
    ];

    return (
        <Card className="h-[calc(100vh-2rem)] mx-5 my-2">
            <CardHeader floated={false} shadow={false} className="rounded-none my-4">
                <div className="mb-8 flex items-center justify-between gap-8">
                    <div>
                        <Typography variant="h5" color="blue-gray">
                            Pet Adoption Requests
                        </Typography>
                        <Typography color="gray" className="mt-1 font-normal">
                            See information about all adoption requests
                        </Typography>
                    </div>
                </div>
            </CardHeader>

            <DataGrid
                rows={adopters}
                getRowId={(row) => row.id}
                columns={columns}
                pageSize={10}
                disableRowSelectionOnClick
            />
        </Card>
    );
}

export default PetAdoptionRequests;
