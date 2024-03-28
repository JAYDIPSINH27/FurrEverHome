import { Spinner } from "@material-tailwind/react";
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import PetCard from '../../components/Card/PetCard';
import { readLocalStorage } from '../../utils/helper';


const Shelter = () => {

    const { id } = useParams()
    const { state } = useLocation()

    const [shelter, setShelter] = useState({})
    const [pets, setPets] = useState({})
    const [loading, setLoading] = useState(false)
    const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`
    const token = readLocalStorage("token")
    const navigate = useNavigate()

    const handlePetClick = (petId) => {
        navigate("/adopter/pet", {
            state: {
                id: petId
            }
        })
    }

    useEffect(() => {

        axios.get(`${baseurl}/shelters/${id}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
            .then(() => {
                setLoading(true)
                setShelter(response.data)
            }
            )
            .catch(error => {
                console.log(error);
            })

        axios.get(`${baseurl}/petadopter/${state.userId}/pets`, {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
            .then(response => {
                setPets(response.data)
            })
            .catch(error => {
                console.log(error);
            })

    }, []);






    return (

        <div className="">
            <div className="container mx-auto py-8">
                <div className="grid grid-cols-4 sm:grid-cols-12 gap-6 px-4">
                    {loading ?
                        <>
                            <div className="col-span-4 sm:col-span-3">

                                <div className="bg-white shadow rounded-lg p-6">

                                    <div className="flex flex-col items-center">

                                        <img src={state.shelter.image} className="w-32 h-32 bg-gray-300 rounded-full mb-4 shrink-0">

                                        </img>
                                        <h1 className="text-xl font-bold">{state.shelter.name}</h1>
                                        {/* <p className="text-gray-700">Capacity: {user.capacity}</p> */}
                                        <div className="mt-6 flex flex-wrap gap-4 justify-center">
                                            <Link to={`/chat/${id}`}>
                                                <button className='btn btn-primary'>Chat</button>
                                            </Link>
                                        </div>
                                    </div>
                                    <hr className="my-6 border-t border-gray-300" />
                                    <div className="flex flex-col">
                                        <span className="text-gray-700 uppercase tracking-wider mb-2">Address</span>
                                        <ul>
                                            <li className="mb-2">Address: {state.shelter.address}</li>
                                            <li className="mb-2">City: {state.shelter.city}</li>
                                            <li className="mb-2">Country: {state.shelter.country}</li>
                                            <li className="mb-2">Zipcode: {state.shelter.zipcode}</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div className="col-span-4 sm:col-span-9">
                                <div className="bg-white shadow rounded-lg p-6">
                                    {/* <h2 className="text-xl font-bold mb-4">Available Pets</h2> */}
                                    <div className="grid gap-8 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 p-3 sm:p-8">

                                        {
                                            pets.length > 0
                                                ?
                                                pets.map((pet) => {
                                                    return (
                                                        <PetCard
                                                            key={pet.petID}
                                                            className="bg-[#f3faff]"
                                                            type={pet.type}
                                                            breed={pet.breed}
                                                            age={pet.age}
                                                            thumbnailSrc={pet.petImage}
                                                            shelterName={pet.shelter.name}
                                                            shelterCity={pet.shelter.city}
                                                            shelterContact={pet.shelter.contact}
                                                            petId={pet.petId}
                                                            handleClick={() => navigate("/adopter/pet", {
                                                                state: {
                                                                    id: pet.petID
                                                                }
                                                            })}
                                                        />)


                                                })
                                                : <></>

                                        }
                                    </div>


                                </div>
                            </div>
                        </>
                        :
                        <Spinner color="green" className="flex justify-center align-middle" />
                    }

                </div>
            </div>
        </div>

    )
}

export default Shelter