import React from 'react';


import ShelterTable from '../components/Admin/ShelterTable';


const AdminHome = ({ children }) => {



  return (
    <section>

      <div className='lg:flex '>




        <div className=' sm:w-full'>

          <ShelterTable />

        </div>

      </div>
    </section>
  )
}

export default AdminHome
