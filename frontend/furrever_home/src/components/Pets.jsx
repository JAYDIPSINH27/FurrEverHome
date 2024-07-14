import React, { useState } from 'react';

import Pet1 from '/img/pets/1.png';
import Pet2 from '/img/pets/2.png';
import Pet3 from '/img/pets/3.png';
import Pet4 from '/img/pets/4.png';
import Pet5 from '/img/pets/5.png';
import Pet6 from '/img/pets/6.png';
import Pet7 from '/img/pets/7.png';
import Pet8 from '/img/pets/8.png';
import Pet9 from '/img/pets/9.png';
import Pet10 from '/img/pets/10.png';
import Pet11 from '/img/pets/11.png';
import Pet12 from '/img/pets/12.png';
import Badge from '/img/pets/badge.svg';

const pets = [
  {
    id: 1,
    category: 'cat',
    name: 'koki',
    image: Pet1
  },
  {
    id: 2,
    category: 'bird',
    name: 'chirpy',
    image: Pet2
  },
  {
    id: 3,
    category: 'fox',
    name: 'froxy',
    image: Pet3
  },
  {
    id: 4,
    category: 'cat',
    name: 'kristy',
    image: Pet4
  },
  {
    id: 5,
    category: 'hamster',
    name: 'molu',
    image: Pet5
  },
  {
    id: 6,
    category: 'parrot',
    name: 'pelu',
    image: Pet6
  },
  {
    id: 7,
    category: 'parrot',
    name: 'pomo',
    image: Pet7
  },
  {
    id: 8,
    category: 'cat',
    name: 'yoshi',
    image: Pet8
  },
  {
    id: 9,
    category: 'lion',
    name: 'musa',
    image: Pet9
  },
  {
    id: 10,
    category: 'bird',
    name: 'dunki',
    image: Pet10
  },
  {
    id: 11,
    category: 'dog',
    name: 'jossy',
    image: Pet11
  },
  {
    id: 12,
    category: 'hamster',
    name: 'hammy',
    image: Pet12
  }

]
const Pets = () => {

  const [petDetails, setPetDetails] = useState(pets[10]);
  const [petIndex, setPetIndex] = useState(10);

  const getPetDetails = (id) => {
    const pet = pets.find((pet) => {
      return pet.id === id;
    })
    setPetDetails(pet);

  }

  return (
    <section className='bg-pets bg-center py-6 overflow-hidden'>
      <div className='flex flex-col lg:flex-row'>
        <div className='hidden xl:flex xl:w-[30%] xl: pl-[160px]'>
          <img src={Badge} width={230} height={227} alt="" />
        </div>
        <div className='flex-1 flex flex-col lg:flex-row'>

          <div className='lg:w-[30%] flex flex-col justify-center items-end pb-6 lg:py-2 mx-auto lg:mx-0 '>
            <div className='text-center text-white'>
              <div className='text-[32px] capitalize'>
                {petDetails.category}
              </div>
              <div className='uppercase text-[17px] mb-1'>
                ({petDetails.name})
              </div>
              <div className='w-[150px] h-[150px] mx-auto lg:mx-0 border-4 border-white rounded-full'>
                <img height={150} width={150} src={petDetails.image} />
              </div>
            </div>
          </div>
          <div className='relative lg:w-[60%] flex-1 flex  items-center '>
            <div className='flex flex-wrap gap-4 justify-center lg:justify-end lg:-mr-6 '>
              {pets.map((pet, index) => {

                return (
                  <div onClick={
                    () => {

                      getPetDetails(pet.id)
                      setPetIndex(index)
                    }

                  } className='cursor-pointer relative' key={index}>
                    {/* Overlay */}
                    <div className={`w-full h-full absolute rounded-full ${petIndex === index ? 'border-2 border-white' : 'bg-black/40 '}`}></div>
                    <img src={pet.image} width={95} height={95} draggable='false'></img>
                  </div>
                )
              })}
            </div>
          </div>
        </div>
      </div>

    </section>
  )
};

export default Pets;
