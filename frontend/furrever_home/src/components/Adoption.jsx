import React from 'react';

import Img1 from '/img/adoption/img1.png';


const Adoption = () => {
  return (

    <section className='bg-adoption bg-cover bg-no-repeat min-h-[760px] py-8 flex'>

      <div className='container mx-auto flex flex-col lg:flex-row items-center justify-center gap-x-16'>

        <div className='flex mb-6 lg:mb-0'>
          <img src={Img1} width={542} height={560} />
        </div>
        <div className='flex-1 text-cream text-center max-w-md lg:text-left lg:max-w-none'>
          <h2 className='h2 mb-6'>Helping pet's human in every way possible.</h2>
          <p className='mb-6 '>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Accusantium laboriosam iste perspiciatis deserunt tenetur eum quos nostrum suscipit culpa est?
          </p>
          <ul className='mb-[38px] flex flex-col gap-y-4 lg:list-disc lg:pl-4'>
            <li>Adoption</li>
            <li>Frozen Raw</li>
            <li>Next Day Delivery</li>
          </ul>
          <button className='btn btn-primary mx-auto lg:mx-0 '>Explore</button>
        </div>
      </div>
    </section>
  )
};

export default Adoption;
