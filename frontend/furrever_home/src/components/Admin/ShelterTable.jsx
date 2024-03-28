import { MagnifyingGlassIcon } from "@heroicons/react/24/outline";
import {
    Card,
    CardHeader, Chip, Input,
    Typography
} from "@material-tailwind/react";
import { DataGrid } from '@mui/x-data-grid';
import axios from "axios";
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { readLocalStorage } from '../../utils/helper';

const ShelterTable = () => {


    const [shelters, setShelters] = useState([])
    const [loading, setLoading] = useState(false)
    const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}/admin`;
    const token = readLocalStorage("token")
    const id = readLocalStorage("id");

    const navigate = useNavigate();

    const [search, setSearch] = useState('');
    const handleChange = (e) => {
        setSearch(e.target.value)
    }

    const columns = [
        { field: 'id', headerName: 'ShelterID', width: 90 },
        {
            field: 'name',
            headerName: 'name',
            width: 150,
            editable: true,
        },
        {
            field: 'rejected',
            headerName: 'Status',
            width: 150,
            editable: true,
            renderCell: (param) => {
                return (
                    (!param.row.accepted && !param.value) ? <Chip color="blue" value="Pending" /> : (param.row.accepted) ? <Chip color="green" value="Verified" /> : <Chip color="red" value="Rejected" />
                )

            }
        },
        {
            field: 'contact',
            headerName: 'Contact',
            width: 150,
            editable: true,
        },
        {
            field: 'email',
            headerName: 'Email',
            width: 110,
            editable: true,
        },
        {
            field: 'image',
            headerName: 'Image',
            width: 110,
            renderCell: (param) => {
                return (
                    <img src={param.value} width="50px" height="50px" />
                )
            }
        },
        {
            field: 'license',
            headerName: 'Licence',
            width: 110,
            renderCell: (param) => {
                return (
                    <img src={param.value} width="50px" height="50px" />
                )
            }
        },
        {
            field: 'city',
            headerName: 'City',
            width: 110,
            editable: true,
        },
        {
            field: 'accepted',
            headerName: 'Action',
            width: 400,
            renderCell: (prop) => {
                return (

                    (!prop.row.rejected && !prop.row.accepted) ?


                        <div className="flex gap-2">
                            <button className='btn btn-primary' onClick={() => { approve(prop.row.id) }}>Approve</button>
                            <button className='btn btn-outline' onClick={() => { reject(prop.row.id) }}>Reject</button>
                        </div>

                        :
                        <></>

                )

            }
        },
    ];


    const approve = (id) => {
        axios.get(`${baseurl}/shelter/${shelters[id - 1].email}/Approve`, {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
            .then(response => {
                toast.info("Status Approved")
                navigate(0)
            })
            .catch(error => {
                console.log(error);
            })
    }

    const reject = (id) => {
        axios.get(`${baseurl}/shelter/${shelters[id - 1].email}/Reject}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
            .then(response => {
                toast.info("Status Approved")
                navigate(0)
            })
            .catch(error => {
                console.log(error);
            })
    }

    useEffect(() => {

        axios.get(`${baseurl}/shelters`, {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
            .then(response => {
                setShelters(response.data)
                setLoading(true)
            })
            .catch(error => {
                console.log(error);
            })

    }, [shelters.length])

    return (
        <Card className=" h-[calc(100vh-2rem)]  mx-5 my-2">
            <CardHeader floated={false} shadow={false} className="rounded-none my-4">
                <div className="mb-8 flex items-center justify-between gap-8">
                    <div>
                        <Typography variant="h5" color="blue-gray">
                            Shelter Requests
                        </Typography>
                    </div>
                    <div className="flex shrink-0 flex-col gap-2 sm:flex-row">

                    </div>
                </div>
                <div className="flex flex-col items-center justify-between gap-4 md:flex-row">
                    <div className="w-full md:w-72">
                        <Input
                            label="Search"
                            icon={<MagnifyingGlassIcon className="h-5 w-5" />}
                            value={search}
                            onChange={handleChange}
                        />
                    </div>
                </div>
            </CardHeader>

            {/* <Card className=""> */}

            <DataGrid
                rows={shelters}
                columns={columns}
                initialState={{
                    pagination: {
                        paginationModel: {
                            pageSize: 10,
                        },
                    },
                }}
                pageSizeOptions={[10]}
                checkboxSelection
                disableRowSelectionOnClick
            />



        </Card>
    );
}

export default ShelterTable