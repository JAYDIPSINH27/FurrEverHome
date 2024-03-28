import React, { useEffect } from 'react';

import {
  Avatar, Menu,
  MenuHandler,
  MenuItem,
  MenuList
} from "@material-tailwind/react";
import InitialsAvatar from 'react-initials-avatar';
import 'react-initials-avatar/lib/ReactInitialsAvatar.css';
import { Link, useNavigate } from 'react-router-dom';
import verifyAuthentication from '../hooks/verifyAuthentication';
import { deleteLocalStorage, readLocalStorage } from '../utils/helper';
import Modal from './Modal/Modal';
import ImgLogo from '/img/logo/LogoWithText_NoBG.png';

const Header = ({user}) => {
  const userToken = verifyAuthentication()

  const navigate = useNavigate();


  const handleLogout = () => {
    deleteLocalStorage("token");
    deleteLocalStorage("User");
    deleteLocalStorage("role");
    deleteLocalStorage("id");
    navigate("/login");
  }
  // backdrop-blur-sm

  return (

    <header className='lg:w-full lg:left-0 top-0 shadow-md bg-cream/75 backdrop-blur-sm  border-b-[1px]  p-1 sticky z-10'>
      <div className='container mx-auto flex flex-col gap-y-6 lg:flex-row h-full justify-between items-center relative'>



        {/* Todo: Add Link Component*/}



        {
          (userToken.userRole === 'SHELTER')
            ?
            (
              (
                <Link to='/shelter/home'>
                  <img className=' h-12' src={ImgLogo} />
                </Link>
              )
            )
            :
            (
              (userToken.userRole === 'PETADOPTER')
                ?
                (
                  <>
                    <Link to='/adopter/home'>
                      <img className=' h-12' src={ImgLogo} />
                    </Link>
                    <nav className='text-xl flex gap-x-4 lg:gap-x-12'>
                      <Link to='/adopter/home' >
                        Shelters
                      </Link>
                      {/* <Link to='/'>
                        Pets
                      </Link> */}
                      {/* <Link to='/'>
                        Adopt
                      </Link> */}
                      <Link to='/lost-found'>
                        Lost&Found
                      </Link>
                    </nav>
                  </>
                )

                :
                (
                  <Link to='/'>
                    <img className=' h-12' src={ImgLogo} />
                  </Link>
                )

            )
        }

        {
          userToken ?
            (
              <div className='flex gap-2'>


                {
                  userToken.userRole === "SHELTER"
                    ?
                    (
                      <Menu>
                        <MenuHandler>
                          <button>
                            <img
                              alt="user 5"
                              src={user.image}
                              class="relative inline-block h-12 w-12 rounded-full border-2 border-white object-cover object-center hover:z-10 focus:z-10"
                            />
                          </button>
                        </MenuHandler>
                        <MenuList>
                          <MenuItem>
                            <Link to="/shelter/profile">
                              Profile
                            </Link>
                          </MenuItem>
                          <MenuItem>
                            <Link to="/shelter/home">
                              Dashboard
                            </Link>
                          </MenuItem>
                          <button type="submit"
                            className="btn btn-outline align-middle justify-center"
                            onClick={handleLogout}
                          >
                            Sign Out
                          </button>
                        </MenuList>
                      </Menu>
                    )
                    :
                    userToken.userRole === "PETADOPTER"
                      ?
                      (
                        <Menu>
                          <MenuHandler>
                            <button>
                              <InitialsAvatar name={`${user.firstname} ${user.lastname}`} />
                            </button>
                          </MenuHandler>
                          <MenuList>
                            <MenuItem>
                              <Link to="/adopter/profile">
                                Profile
                              </Link>
                            </MenuItem>
                            <MenuItem>
                              <Link to="/adopter/home">
                                Dashboard
                              </Link>
                            </MenuItem>
                            <button type="submit"
                              className="btn btn-outline align-middle justify-center"
                              onClick={handleLogout}
                            >
                              Sign Out
                            </button>
                          </MenuList>
                        </Menu>
                      )
                      :
                      (
                        <Menu>
                          <MenuHandler>
                            <button>
                              <InitialsAvatar name={`ADMIN`} />
                            </button>
                          </MenuHandler>
                          <MenuList>
                            <MenuItem>
                              <Link to="/adopter/profile">
                                Profile
                              </Link>
                            </MenuItem>
                            <MenuItem>
                              <Link to="/adopter/home">
                                Dashboard
                              </Link>
                            </MenuItem>
                            <button type="submit"
                              className="btn btn-outline align-middle justify-center"
                              onClick={handleLogout}
                            >
                              Sign Out
                            </button>
                          </MenuList>
                        </Menu>
                      )
                }


              </div>

            ) :
            (
              <div className='flex gap-4 p-2 pl-5'>
                <Link to='/login'>
                  <button className='btn btn-outline'>Login</button>
                </Link>

                <Modal />


              </div>)
        }



      </div>

    </header >

  )
};

export default Header;
