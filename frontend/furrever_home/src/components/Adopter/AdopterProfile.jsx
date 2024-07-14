import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { toast } from "react-toastify"
import { readLocalStorage } from '../../utils/helper'
import UpdateAdopterProfile from './UpdateAdopterProfile'
import InitialsAvatar from 'react-initials-avatar';

const AdopterProfile = () => {
    const [user, setUser] = useState({});
    const userId = readLocalStorage("id");
    const token = readLocalStorage("token")

    useEffect(() => {
        axios.get(`${import.meta.env.VITE_BACKEND_BASE_URL}/users/${userId}`, {
            headers: {
                Authorization: `Bearer ${token} `,
            }
        })
            .then(response => {
                setUser(response.data)
            })
            .catch(error => {
                toast.error("Cannot get User details");
            })
    }, [])


    return (
        <div className="bg-gray-100">
            {/* <div className="container mx-auto py-8"> */}
            <div className="container mx-auto w-[30rem] py-8">
                {/* <div className="grid grid-cols-4 sm:grid-cols-12 gap-6 px-4"> */}
                    <div className="col-span-4 sm:col-span-3">
                        <div className="bg-white shadow rounded-lg p-6">
                            <div className="flex flex-col items-center">
                                {/* <img src={user.imageBase64} className="w-32 h-32 bg-gray-300 rounded-full mb-4 shrink-0"> */}
                                <InitialsAvatar name={`${user.firstName} ${user.lastName}`} />
                                {/* </img> */}
                                <h1 className="text-xl font-bold">{user.firstName} {user.lastName}</h1>
                                {/* <p className="text-gray-700">Capacity: {user.capacity}</p> */}
                                <div className="mt-6 flex flex-wrap gap-4 justify-center">
                                    <UpdateAdopterProfile user={user}/>
                                </div>
                            </div>
                            <hr className="my-6 border-t border-gray-300" />
                            <div className="flex flex-col">
                                <span className="text-gray-700 uppercase tracking-wider mb-2">Address</span>
                                <ul>
                                    <li className="mb-2">Address: {user.address}</li>
                                    <li className="mb-2">City: {user.city}</li>
                                    <li className="mb-2">Country: {user.country}</li>
                                    <li className="mb-2">Zipcode: {user.zipcode}</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    {/* <div className="col-span-4 sm:col-span-9">
                        <div className="bg-white shadow rounded-lg p-6">
                            <h2 className="text-xl font-bold mb-4">Adopted Pets</h2>
                            <p className="text-gray-700">
                                Pets Details
                            </p>


                        </div>
                    </div> */}
                {/* </div> */}
            </div>
        </div>
    )
}

export default AdopterProfile