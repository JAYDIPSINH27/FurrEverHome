import React, { useEffect, useState } from 'react'
import { readLocalStorage } from '../../utils/helper'
import { toast } from 'react-toastify'
import UpdatePetIndividualDetails from '../Shelter/UpdatePetIndividualDetails'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'



const PetDetail = ({
  pet,
  petId
}) => {

  const role = readLocalStorage("role")
  const userId = readLocalStorage("petadopterID")
  const token = readLocalStorage("token")
  const [reqExist,setReqExist] = useState(false)
  const sid = readLocalStorage("shelterID")

  console.log(pet)

  const [isAdopted,setIsAdopted] = useState(false)


  const navigate = useNavigate()
 

  if(role === "PETADOPTER"){
    useEffect(()=> {

    axios.get(`${import.meta.env.VITE_BACKEND_BASE_URL}/petadopter/pet/adopt/requestexists`,{
      params:{
        petID: petId,
        petAdopterID:userId
      },
      headers : {
        Authorization :`Bearer ${token}`
      }
    })
    .then(response => {
      setReqExist(true)
    })
    .catch(error => {
      console.log(error)
    })
    },[])
  }

  const handleAdoptionRequest = () => {
    axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/petadopter/pet/adopt`,{
      petID : petId,
      petAdopterID : userId
    },{
      headers : {
        Authorization :`Bearer ${token}`
      }
    })
    .then(response => {
      setReqExist(true)
      toast.success("Adoption request sent")
    })
    .catch(error => {
      console.log(error)
      toast.error("Cannot send request, Please Try again later")
    })
  }

  const getPetAdoptionRequests=() => {
    navigate("/shelter/pet/petadoptionrequest",{
      state:{
        id:petId,
      }
    })
  }
  return (
        <div className="bg-white w- relative group overflow-hidden rounded-lg shadow-lg hover:shadow-xl transition-transform duration-300 ease-in-out hover:-translate-y-2">
            <img
              alt="Rusty"
              className="w-[50%] cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 h-auto mx-auto"
              src={pet.petImage}
            />
            <div className="p-4 dark:bg-gray-950">

            <h2 className="text-2xl font-bold">Type : {pet.type}</h2>
            <div className="grid gap-2 text-lg font-normal py-2">
              <p className="flex items-center gap-1">Birth date : {pet.birthdate}</p>
              <p className="flex items-center gap-1">Gender : {pet.gender}</p>
              <p className="flex items-center gap-1">Breed : {pet.breed}</p>
              <p className="flex items-center gap-1">Medical History : {pet.petMedicalHistory == null ? "none" : pet.petMedicalHistory}</p>
              <p className="flex items-center gap-1">Color : {pet.colour}</p>          
            </div>
            <div>

            { role === "PETADOPTER" ?
                pet.adopted ? <p className="flex items-center fonr-bold text-red-500 gap-1">This pet is already adopted</p> :
                reqExist ? <p className="flex items-center fonr-bold text-green-500 gap-1">You have sent request for this pet </p> : <button type="button" onClick={handleAdoptionRequest} className="btn btn-orange mx-auto lg:h-10 sm:h-15">Adopt</button> 
                : 
                pet.adopted ? <p className="flex items-center fonr-bold text-red-500 gap-1">This pet is already adopted</p> :
                  

                <>
                  <div className="flex justify-center"> 
                    <span className="text-lg font-normal py-2 mr-3">Want to edit Pet details ? </span>
                    <UpdatePetIndividualDetails pets={pet} sid={sid} />
                  </div>
                  <div className="flex justify-center"> 
                    <button type="button" onClick={getPetAdoptionRequests} className="btn btn-orange mx-auto lg:h-10 sm:h-15">See Pet Adoption Requests</button>             
                  </div>
                
                </>
                
                
            }

            </div>
              
            </div>
        </div>
  )
}


export default PetDetail
