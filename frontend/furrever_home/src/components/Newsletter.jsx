import React from 'react';

import { HiOutlineArrowNarrowRight } from 'react-icons/hi';
import DogImage from '/img/newsletter/dog.png';
const Newsletter = () => {
  return (
    <section>
      <div className='h-[800px] flex flex-1 flex-col lg:flex-row lg:h-[324px]'>
        <div className='bg-newsletterOrange bg-center bg-cover bg-no-repeat flex-1 flex-col justify-center items-center px-8 lg:px-0 h-full'>
          <div className='flex relative flex-col items-center justify-center align-bottom p-32'>
            <h2>Checkout our Pet Care Blogs</h2>

            <div className=''>

              <button className='btn btn-primary'>Blogs<HiOutlineArrowNarrowRight /></button>
            </div>
          </div>
        </div>
        <div className='bg-newsletterYellow bg-center bg-no-repeat bg-cover flex-1 flex justify-center items-end h-full'>
          <img src={DogImage} alt="" />
        </div>

      </div>
    </section>
  )
};

export default Newsletter;
