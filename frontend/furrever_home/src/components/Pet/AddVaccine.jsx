import React from 'react'
import { useState } from 'react';
import {
    Button,
    Dialog,
    Card,
    CardHeader,
    CardBody,
    CardFooter,
    Typography,
    Input,
    Checkbox,

} from "@material-tailwind/react";
import axios from 'axios';
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { readLocalStorage } from '../../utils/helper';

const AddVaccine = ({petId}) => {
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen((cur) => !cur);
    const navigate = useNavigate();
    const token = readLocalStorage("token")


    const [formData, setFormData] = useState({
        vaccineName: "",
        vaccineGiven:true,
        date:""
    });

    const handleChange = (event) => {

        const newData = { ...formData }
        newData[event.target.id] = event.target.value

        if(event.target.id === "vaccineGiven" && event.target.value === "true"){
            newData[event.target.id] = true
        }else if(event.target.id === "vaccineGiven" && event.target.value === "false"){
            newData[event.target.id] = false
        }

        console.log(newData)
        setFormData(newData)
    }

    const handleSubmit = (event) => {

        event.preventDefault();
        
        axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/shelter/${petId}/addvaccine`, formData, {
            headers: {
                Authorization: `Bearer ${token} `,
            }
        })
            .then((res) => {
                console.log(res)
                toast.success("Successfully Added Pet Vaccine info");
                navigate(0)
                handleOpen();
                setFormData({
                    vaccineName:"",
                    vaccineGiven:true,
                    date:""
                })
            })
            .catch((err) => {
                console.log(err)
                toast.error(err.message)
                handleOpen();
                setFormData({
                    vaccineName:"",
                    vaccineGiven:true,
                    date:""
                })
            })
    }



    return (
        <>
            <button className="btn btn-orange m-5 lg:h-10 sm:h-15" onClick={handleOpen}>Add Vaccine</button>
            <Dialog
                size="lg"
                open={open}
                handler={handleOpen}
                className="bg-transparent shadow-none"
            >
                <Card className="mx-auto w-full max-w-[30rem] ">
                    <CardBody className="flex flex-col gap-4">
                        <Typography variant="h4" color="blue-gray">
                            Add Vaccine
                        </Typography>


                        <form onSubmit={handleSubmit}>

                            <div>
                                <label htmlFor="lastName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                   Vaccine Name
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="vaccineName"
                                        name="name"
                                        type="text"
                                        value={formData.vaccineName}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Vaccine Name'
                                    />
                                </div>
                            </div>

                            <div>
                                <label htmlFor="lastName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                   Is Vaccine given or not ?
                                </label>
                                <div className="mt-1">
                                    <select name="vaccineGiven" id="vaccineGiven" value={formData.vaccineGiven} onChange={handleChange}>
                                        <option value="true">Yes</option>
                                        <option value="false">No</option>
                                    </select>
                                </div>
                            </div>

                            <div>
                            <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                Date on which vaccine should be given
                            </label>
                            <div className="mt-1">
                                <input
                                    id="date"
                                    name="date"
                                    type="date"
                                    value={formData.date}
                                    onChange={handleChange}
                                    autoComplete="text"
                                    required
                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                    placeholder='Enter Date'
                                />
                            </div>
                            </div>

                            <div className="pt-0 flex gap-4 mt-4">
                                <button type="submit" className="btn btn-orange" variant="gradient" fullWidth>
                                    Add
                                </button>
                                <button className="btn btn-orange" variant="gradient" onClick={handleOpen} fullWidth>
                                    Close
                                </button>
                            </div>
                        </form>
                    </CardBody>
                </Card>
            </Dialog>
        </>
    );
}

export default AddVaccine
