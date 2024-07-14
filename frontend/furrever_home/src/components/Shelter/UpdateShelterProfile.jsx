import {
    Card,
    CardBody,
    Dialog,
    Typography
} from "@material-tailwind/react";
import axios from 'axios';
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { deleteLocalStorage, readLocalStorage, saveLocalStorage } from '../../utils/helper';

const UpdateShelterProfile = ({ shelter }) => {
    const [open, setOpen] = React.useState(false);
    const [response, setResponse] = useState({})
    const [loading, setLoading] = useState(true)
    const handleOpen = () => setOpen((cur) => !cur);
    const navigate = useNavigate();
    const id = readLocalStorage("shelterID");


    const [formData, setFormData] = useState({
        name: shelter.name,
        address: shelter.address,
        capacity: shelter.capacity,
        city: shelter.city,
        country: shelter.country,
        zipcode: shelter.zipcode,
        contact: shelter.contact,
        imageBase64: "",
        shelter: id
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
            newData.imageBase64 = e.target.result
            setFormData(newData)

        };

        reader.onerror = function () {
            console.log(reader.error);
        };
    }

    const handleSubmit = (event) => {

        event.preventDefault();
        const userId = readLocalStorage("id")
        const token = readLocalStorage("token")

        axios.put(`${import.meta.env.VITE_BACKEND_BASE_URL}/shelters/${userId}`, formData, {
            headers: {
                Authorization: `Bearer ${token} `,
            }
        })
            .then((res) => {
                setLoading(true)
                deleteLocalStorage("User")
                saveLocalStorage("User", JSON.stringify(res.data))
                toast.success("Successfully Updated Shelter info");
                navigate(0)
                handleOpen();
                setFormData({
                    name: "",
                    address: "",
                    capacity: "",
                    city: "",
                    country: "",
                    zipcode: "",
                    contact: "",
                    shelter: id
                })
            })
            .catch((err) => {
                console.log(err)
                toast.error(err.message)
                handleOpen();
                setFormData({
                    name: "",
                    address: "",
                    capacity: "",
                    city: "",
                    country: "",
                    zipcode: "",
                    contact: "",
                    shelter: id
                })
            })
    }



    return (
        <>
            <button className="btn btn-orange m-5 " onClick={handleOpen}>Update Profile</button>
            <Dialog
                size="lg"
                open={open}
                handler={handleOpen}
                className="bg-transparent shadow-none"
            >
                <Card className="mx-auto w-full max-w-[30rem] ">
                    <CardBody className="flex flex-col gap-4">
                        <Typography variant="h4" color="blue-gray">
                            Update Profile
                        </Typography>


                        <form onSubmit={handleSubmit}>



                            <div>

                                <label htmlFor="lastName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Name
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="name"
                                        name="name"
                                        type="text"
                                        value={formData.name}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Last name'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="phoneNumber" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Phone number
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="phoneNumber"
                                        name="phoneNumber"
                                        type="text"
                                        value={formData.contact}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Phone number'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="address" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Address
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="address"
                                        name="address"
                                        type="text"
                                        value={formData.address}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Address'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="city" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    City
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="city"
                                        name="city"
                                        type="text"
                                        value={formData.city}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter City'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="country" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Country
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="country"
                                        name="country"
                                        type="text"
                                        value={formData.country}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter country'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="zipcode" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Zip Code
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="zipcode"
                                        name="zipcode"
                                        type="text"
                                        value={formData.zipcode}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Zipcode'
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
                                    Update
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

export default UpdateShelterProfile