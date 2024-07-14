import React, { useEffect, useState } from 'react';

import PetsTable from '../components/Shelter/PetsTable';
import { readLocalStorage, saveLocalStorage } from '../utils/helper';

import axios from 'axios';

const ShelterHome = ({ children }) => {

  const [search, setSearch] = useState('');

  const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`;
  const [change, setChange] = useState(false)
  const token = readLocalStorage("token")
  const [pets, setPets] = useState([])
  const sid = readLocalStorage("shelterID");
  const id = readLocalStorage("id");


  const getPet = () => {
    axios.get(`${baseurl}/shelter/${sid}/pets`, {
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
  }

  useEffect(() => {

    axios.get(`${baseurl}/shelters/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        saveLocalStorage("User", JSON.stringify(response.data));
      })
      .catch(error => {
        console.log(error);
      })

    getPet()

  }, [change])



  return (
    <section>

      <div className='lg:flex '>

        {/* <div className='lg:w-[20%]'>
          <Sidebar />
        </div> */}



        <div className=' sm:w-full'>

          <PetsTable pets={pets} setChange={setChange}/>

        </div>

      </div>
    </section>
  )
}

export default ShelterHome
