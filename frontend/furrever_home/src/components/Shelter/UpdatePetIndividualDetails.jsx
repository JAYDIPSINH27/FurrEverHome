import { PencilIcon } from "@heroicons/react/24/solid";
import {
    Card,
    CardBody,
    Dialog,
    Typography
} from "@material-tailwind/react";
import axios from 'axios';
import React, { useState,useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { readLocalStorage } from '../../utils/helper';

const UpdatePetDetails = ({ pets, sid }) => {
    const [open, setOpen] = React.useState(false);
    const [response, setResponse] = useState({})
    const [loading, setLoading] = useState(true)
    const handleOpen = () => setOpen((cur) => !cur);
    const navigate = useNavigate();
    const token = readLocalStorage("token")


    useEffect(() => {
        setFormData({

            type: pets.type,
            breed: pets.breed,
            colour: pets.colour,
            gender: pets.gender,
            birthdate: pets.birthdate,
            petMedicalHistory:pets.petMedicalHistory,
            petImage: pets.petImage,
            shelter: sid,
            adopted: pets.adopted
        })

    },[pets])

    const [formData, setFormData] = useState({

        type: pets.type,
        breed: pets.breed,
        colour: pets.colour,
        gender: pets.gender,
        birthdate: pets.birthdate,
        petMedicalHistory:pets.petMedicalHistory,
        petImage: pets.petImage,
        shelter: sid,
        adopted: pets.adopted
    });


    const handleChange = (event) => {

        const newData = { ...formData }
        newData[event.target.id] = event.target.value

        setFormData(newData)
    }

    const handleImage = (image) => {

        const reader = new FileReader();
        reader.readAsDataURL(image);
        reader.onload = function (e) {

            const newData = { ...formData }
            newData.petImage = e.target.result
            setFormData(newData)

        };

        reader.onerror = function () {
            console.log(reader.error);
        };
    }



    const handleSubmit = (event) => {

        event.preventDefault();

        axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/shelter/editPet/${pets.petID}`, formData, {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
            .then((res) => {
                setResponse(res)
                setLoading(true)
                toast.success("Pet Updated!");
                navigate(0)
                // setChange(true)
                handleOpen();
                // setFormData({
                //     type: "",
                //     breed: "",
                //     colour: "",
                //     gender: "",
                //     birthdate: "",
                //     petImage: "",
                //     adopted: false
                // })

            })
            .catch((err) => {
                toast.error(err.message)
                handleOpen();
                // setFormData({
                //     type: "",
                //     breed: "",
                //     colour: "",
                //     gender: "",
                //     birthdate: "",
                //     petImage: "",
                //     adopted: false
                // })
            })
    }



    return (
        <>
            <button onClick={handleOpen}><PencilIcon className="h-4 w-4" /></button>
            <Dialog
                size="lg"
                open={open}
                handler={handleOpen}
                className="bg-transparent shadow-none"
            >
                <Card className="mx-auto w-full max-w-[30rem] ">
                    <CardBody className="flex flex-col gap-4">
                        <Typography variant="h4" color="blue-gray">
                            Edit Pet Details
                        </Typography>

                        <form method="POST" onSubmit={handleSubmit}>


                            <div>

                                <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Type
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="type"
                                        name="type"
                                        type="text"
                                        value={formData.type}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Pet Type'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Status
                                </label>
                                <div className="mt-1">
                                    <select name="adopted" id="adopted" value={formData.adopted} onChange={handleChange}>
                                        <option value="true">Adopted</option>
                                        <option value="false">Not Adopted</option>
                                    </select>
                                </div>
                            </div>

                            <div>

                                <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Breed
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="breed"
                                        name="breed"
                                        type="text"
                                        value={formData.breed}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Pet Breed'
                                    />
                                </div>
                            </div>

                            

                            <div>

                                <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Medical History
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="petMedicalHistory"
                                        name="petMedicalHistory"
                                        type="text"
                                        value={formData.petMedicalHistory}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        size="40"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Pet Medical History'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Colour
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="colour"
                                        name="colour"
                                        type="text"
                                        value={formData.colour}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Pet Colour'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Gender
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="gender"
                                        name="gender"
                                        type="text"
                                        value={formData.gender}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Pet Gender'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Birth Date
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="birthdate"
                                        name="birthdate"
                                        type="date"
                                        value={formData.birthdate}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                      
                                    />
                                </div>
                            </div>

                            <div className="">
                                <label className="block text-sm font-medium leading-6 text-gray-900">Upload Image</label>
                                <input type="file"
                                    name='petImage'
                                    required
                                    onChange={(event) => { handleImage(event.target.files[0]) }}
                                    className="w-full text-black text-sm bg-white border file:cursor-pointer cursor-pointer file:border-0 file:py-2.5 file:px-4 file:bg-gray-100 file:hover:bg-gray-200 file:text-black rounded" />
                                <p className="text-xs text-gray-400 mt-2">PNG, JPG are Allowed.</p>
                            </div>

                            <div className="pt-0 flex gap-4">
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

export default UpdatePetDetails