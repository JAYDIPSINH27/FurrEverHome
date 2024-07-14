import React from 'react';

import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper/modules';
import { Swiper, SwiperSlide } from 'swiper/react';

import Service1 from '/img/services/service-icon1.svg';
import Service2 from '/img/services/service-icon2.svg';
import Service3 from '/img/services/service-icon3.svg';

const services = [
  {
    image: Service1,
    name: 'Vaccination Tracking',
    description: 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which look even slightly believable. ',
    btnText: 'Explore'
  },
  {
    image: Service2,
    name: 'Specific Haircut',
    description: 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which look even slightly believable. ',
    btnText: 'Explore'
  },
  {
    image: Service3,
    name: 'Clothes',
    description: 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which look even slightly believable. ',
    btnText: 'Explore'
  },
]

const ServiceSlider = () => {
  return (
    <Swiper slidePerView={1} spaceBetween={30} navigation={true} modules={[Navigation]} breakpoints={{
      768: {
        slidesPerView: 2,
      }
    }} className='serviceSlider min-h-[680px] '>
      {services.map((service, index) => {
        return (
          <>
            <SwiperSlide className='border border-primary/20 bg-cream min-h-[560px] rounded-[66px] py-16 px-8 ' key={index}>
              <img className='mb-9' src={service.image} />
              <div className='text-[26px] font-medium mb-4'>{service.name}</div>
              <div className='text-[20px] mb-8'>{service.description}</div>
              <button className='btn btn-primary'>{service.btnText}</button>
            </SwiperSlide>
          </>
        )
      })}
    </Swiper>



  )
};

export default ServiceSlider;
