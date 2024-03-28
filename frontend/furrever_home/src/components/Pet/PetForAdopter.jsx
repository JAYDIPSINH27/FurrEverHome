import { useState , React, useEffect} from 'react'
import { useLocation } from 'react-router-dom'
import { readLocalStorage } from '../../utils/helper'
import PetDetail from './PetDetail'
import ShelterDetail from './ShelterDetail'
import axios from 'axios'
import { ToastContainer, toast } from "react-toastify";

const PetForAdopter = () => {
    
    const location = useLocation();

    const petId = location.state.id;
    const [pet,setPet] = useState({
      type:"",
      breed:"",
      birthdate:"",
      gender:"",
      colour:"",
      petMedicalHistory:"",
      petImage:"",
      petID:"",
      adopted:""
    })
    const [shelter,setShelter] = useState({
      name:"",
      address:"",
      city:"",
      country:"",
      contact:"",
    })
    const [vaccine,setVaccine] = useState([])
    const token = readLocalStorage("token");
    
    useEffect(() =>{

      axios.get(`${import.meta.env.VITE_BACKEND_BASE_URL}/petadopter/pets/${petId}`,{
        headers : {
          Authorization : `Bearer ${token}`,
        }
      })
      .then((response) => {
        

        const DOB = response.data.birthdate.substring(0,10)
        setPet({
          type:response.data.type,
          breed:response.data.breed,
          birthdate:DOB,
          gender:response.data.gender,
          colour:response.data.colour,
          petMedicalHistory:response.data.petMedicalHistory,
          petImage:response.data.petImage,
          petID:response.data.petID,
          adopted:response.data.adopted
        })

        const res = response.data.shelter


        setShelter({
          name:res.name,
          address:res.address,
          city:res.city,
          country:res.country,
          contact:res.contact
        })
        setVaccine(response.data.vaccineNameList)

      })
      .catch(error => {
        toast.error("Cannot get pet details")
        console.log(error)
      })
    },[])



  return (

    <div className="w-full py-6 space-y-6 bg-gray-100">
      <div className="container mx-auto py-8">
        <div className="grid gap-6 grid-cols-3 mx-auto" >
            <PetDetail pet={pet} petId={petId} />
            <ShelterDetail shelter={shelter} vaccine={vaccine} petId={petId}/>     
        </div>
      </div>
    </div>
  )
}

export default PetForAdopter
