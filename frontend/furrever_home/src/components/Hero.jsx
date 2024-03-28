import React from 'react';

import pretitleImg from '/img/hero/pretitle-img.svg'
import ArrowsRight from '/img/hero/stroke-twist.svg'
import Header from './Header'

const Hero = () => {
  return (

    <section className='bg-cream lg:bg-hero lg:bg-cover lg:bg-no-repeat min-h-[400px] lg:min-h-[805px]'>
      {/* <Header/> */}
      <div className='container mx-auto flex justify-start items-center min-h-[400px] lg:h-[605px]'>
        <div className='lg:max-w-[650px] text-center mx-auto lg:text-left lg:mx-0'>

          <div className='hidden xl:flex mb-6 ml-5'>
            {/* Image */}
            <img src={ArrowsRight} className='w-[40%] h-auto rotate-90' alt="pretitleimage" />
          </div>

          <h1 className='text-5xl lg:text-8xl uppercase font-bold -tracking-tighter-[0.05em] mb-10'>Where pets<br />
            <span>are</span><br /><span className='text-orange'>Family</span> </h1>
          <button className='btn btn-orange mx-auto'>
            About Us
          </button>
        </div>
      </div>
    </section>
  );
};

export default Hero;
