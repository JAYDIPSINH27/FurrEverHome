import React from 'react'
import AddVaccine from './AddVaccine'
import { readLocalStorage } from '../../utils/helper';
import { button } from '@material-tailwind/react';

const ShelterDetail = ({
  shelter,
  vaccine,
  petId
}) => {

  const role = readLocalStorage("role")

  return (
    <div className="bg-white relative group overflow-hidden rounded-lg shadow-lg hover:shadow-xl transition-transform duration-300 ease-in-out hover:-translate-y-2">

      <div className="p-3 dark:bg-gray-950">
        <h1 className="text-1xl font-bold">Shelter Details</h1>
        <h2 className="text-lg font-normal">Shelter Name : {shelter.name}</h2>
        <div className="grid gap-2 text-lg py-2">
          <p className="flex items-center gap-1">Address : {shelter.address} </p>
          <p className="flex items-center gap-1">{shelter.city} , {shelter.country}</p>
          <p className="flex items-center gap-1">{shelter.contact}</p>
        </div>
      </div>
      <h1 className="text-1xl font-bold p-3">Vaccinations Given</h1>

      {
        vaccine.length > 0 ?

          vaccine.map((vac, index) => {
            return (<p key={index} className="flex items-center gap-1 p-3">{vac} </p>)
          })
          :
          <p className="flex items-center gap-1 p-3">No Vaccines are given to this pet</p>
      }

      {
        role === "SHELTER" &&
        <AddVaccine petId={petId} />
      }

    </div>
  )
}

export default ShelterDetail
