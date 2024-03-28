import axios from 'axios';
import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import Logo from '../components/Logo';
import { validatePassword } from '../utils/helper';


const ResetPassword = () => {

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const token = queryParams.get("token");
  const navigate = useNavigate();

  const [newPassword, setNewPassword] = useState('');
  const [verifyNewPassword, setVerifyNewPassword] = useState('');
  const [isError, setIsError] = useState();

  let errors = [];

  const handleNewPasswordChange = (event) => {
    setNewPassword(event.target.value);
  }

  const handleVerifyNewPasswordChange = (event) => {
    setVerifyNewPassword(event.target.value);
  }

  const handleSubmit = (event) => {
    event.preventDefault();

    errors = validatePassword(newPassword);

    if (errors.length === 0) {
      if (newPassword == verifyNewPassword) {
        axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/auth/resetPassword`, { newPassword, verifyNewPassword, token })
          .then(response => {
            toast.success("Password Successfully Reset")
            navigate('/login');
          })
          .catch(error => {
            toast.error("Cannot Reset Password ! Please try again")
          })
      }
      else {
        toast.error("Password and Confirm Password does not match")
      }
    }
    else {
      toast.error("Invalid Password")
      setIsError(true);
    }
  }

  return (
    <>
      <div className="flex min-h-full flex-1 flex-col m-8 justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <Logo />
          <h2 className="mt-10 font-primary text-center text-2xl font-bold leading-9 tracking-tight text-teal">
            Reset your Password
          </h2>
        </div>

        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form className="space-y-6" action="#" method="POST" onSubmit={handleSubmit}>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                  New Password
                </label>
              </div>
              <div className="mt-2">
                <input
                  id="newPassword"
                  name="newPassword"
                  type="password"
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Enter Your Password'
                  onChange={handleNewPasswordChange}
                />
              </div>
            </div>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                  Repeat Password
                </label>
              </div>
              <div className="mt-2">
                <input
                  id="verifyNewPassword"
                  name="verifyNewPassword"
                  type="password"
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Confirm New Password'
                  onChange={handleVerifyNewPasswordChange}
                />
              </div>
              <div className='text-red-500 text-sm'>
                {isError && <p>
                  * Your Password must be 8 characters long,should contain a digit, Uppercase Letter, Special and should not contain numerical sequence, alphabetical sequence,keyboard sequence and empty space.
                </p>
                }
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-orange px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              >
                Reset
              </button>
            </div>
          </form>

        </div>
      </div>
      {/* </div> */}
    </>
  )
}

export default ResetPassword