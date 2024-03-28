import React from 'react';

import { BsFacebook, BsInstagram, BsTwitterX } from 'react-icons/bs'
import TextLogo from '/img/logo/LogoOnlyText.png'
import ImgLogo from '/img/logo/LogoWithText_NoBG.png'
import { Link } from 'react-router-dom';

const Footer = () => {
  return (
    <footer className='bg-footer bg-cover bg-center bg-no-repeat min-h-[263px] flex items-center py-8 '>
      <div className="container mx-auto">
        <div className='lg:flex lg:justify-between'>
          <div className='flex-1 mx-auto lg:flex items-center justify-center lg:justify-start mb-6'>
            <Link className='flex lg:justify-start justify-center' to='/'>
              <img className='h-16' src={TextLogo} />
            </Link>
          </div>

          <div className='text-white flex-1 '>
            <ul className='flex flex-col gap-y-6 items-center lg:flex-row lg:gap-x-4  text-base font-semibold mb-8'>
              <li><Link>Service</Link></li>
              <li><Link>About</Link></li>
              <li><Link>Blog</Link></li>
              <li><Link>Contact</Link></li>
            </ul>
            <div className='flex justify-center lg:justify-start'>
              <div className='mr-6'>Follow</div>
              <ul className='flex gap-x-4'>
                <li><Link><BsFacebook /></Link></li>
                <li><Link><BsInstagram /></Link></li>
                <li><Link><BsTwitterX /></Link></li>
              </ul>
            </div>
          </div>

        </div>
      </div>
    </footer>
  )
};

export default Footer;
