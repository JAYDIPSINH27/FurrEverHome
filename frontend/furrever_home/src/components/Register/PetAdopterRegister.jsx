import axios from 'axios';
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import { validatePassword } from '../../utils/helper';
import Logo from '../Logo';

const PetAdopterRegister = () => {

  const [response, setResponse] = useState({})
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState({})
  const navigate = useNavigate();
  let errors = []
  const [isError, setIsError] = useState(false);

  const [formData, setFormData] = useState({
    email: "",
    firstName: "",
    lastName: "",
    password: "",
    address: "",
    city: "",
    country: "",
    zipcode: "",
    phone_number: "",
    role: "petadopter",
    checkRole: "1"
  })

  const handleChange = (event) => {

    const newData = { ...formData }
    newData[event.target.id] = event.target.value

    setFormData(newData)
  }

  const handleSubmit = (event) => {

    event.preventDefault();

    errors = validatePassword(formData.password)

    if (errors.length === 0) {

      axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/auth/signup`, {
        ...formData
      })
        .then((res) => {

          setResponse(res)
          setLoading(false)
          toast.info("Verify Your Email!");
          navigate("/login")
        })
        .catch((err) => {
          console.log(err)
          setError(err)
          toast.error(err.message)
        })
    }
    else {
      toast.error("Invalid Password")
      setIsError(true)
    }

  }

  // useEffect(()=>{
  //   fetchData()
  // },[])


  return (
    <>
      <div className="flex min-h-full flex-1 flex-col m-8 justify-center px-6 py-12 lg:px-8">

        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <Logo />
          <h2 className="mt-10 font-primary text-center text-2xl font-bold leading-9 tracking-tight text-teal">
            Create Your New Account
          </h2>
        </div>

        <div className="mt-5 sm:mx-auto sm:w-full sm:max-w-sm">
          <form className="space-y-6" method="POST" onSubmit={handleSubmit}>
            <div>

              <label htmlFor="firstName" className="text-sm font-medium leading-6 text-gray-900 flex">
                First Name
              </label>
              <div className="mt-1">
                <input
                  id="firstName"
                  name="firstName"
                  value={formData.firstName}
                  onChange={handleChange}
                  type="text"
                  autoComplete="text"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Enter Your First Name'
                />
              </div>
            </div>

            <div>

              <label htmlFor="lastName" className="text-sm font-medium leading-6 text-gray-900 flex">
                Last Name
              </label>
              <div className="mt-1">
                <input
                  id="lastName"
                  name="lastName"
                  value={formData.lastName}
                  onChange={handleChange}
                  type="text"
                  autoComplete="text"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Enter Your Last Name'
                />
              </div>
            </div>

            <div>

              <label htmlFor="email" className="text-sm font-medium leading-6 text-gray-900 flex">
                Email address
              </label>
              <div className="mt-1">
                <input
                  id="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  type="email"
                  autoComplete="email"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Your Email Address'
                />
              </div>
            </div>

            <div>

              <label htmlFor="phone" className="text-sm font-medium leading-6 text-gray-900 flex">
                Phone Number
              </label>
              <div className="mt-1">
                <input
                  id="phone_number"
                  name="phone_number"
                  value={formData.phone_number}
                  onChange={handleChange}
                  type="tel"
                  autoComplete="tel"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Your Phone Number'
                />
              </div>
            </div>


            <div>

              <label htmlFor="address" className="text-sm font-medium leading-6 text-gray-900 flex">
                Address
              </label>
              <div className="mt-1">
                <textarea
                  id="address"
                  name="address"
                  value={formData.address}
                  onChange={handleChange}
                  type="text"
                  rows="2"
                  autoComplete="text"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Enter Your Address'
                />
              </div>
            </div>

            <div className='flex gap-2'>
              <div>

                <label htmlFor="city" className="text-sm font-medium leading-6 text-gray-900 flex">
                  City
                </label>
                <div className="mt-1">
                  <input
                    id="city"
                    name="city"
                    value={formData.city}
                    onChange={handleChange}
                    type="text"
                    autoComplete="text"
                    required
                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                    placeholder='City'
                  />
                </div>
              </div>

              <div>

                <label htmlFor="country" className="text-sm font-medium leading-6 text-gray-900 flex">
                  Country
                </label>
                <div className="mt-1">
                  <input
                    id="country"
                    name="country"
                    value={formData.country}
                    onChange={handleChange}
                    type="text"
                    autoComplete="text"
                    required
                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                    placeholder='Country'
                  />
                </div>
              </div>

              <div>

                <label htmlFor="zipcode" className="text-sm font-medium leading-6 text-gray-900 flex">
                  Zipcode
                </label>
                <div className="mt-1">
                  <input
                    id="zipcode"
                    name="zipcode"
                    value={formData.zipcode}
                    onChange={handleChange}
                    type="text"
                    autoComplete="text"
                    required
                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                    placeholder='Zipcode'
                  />
                </div>
              </div>
            </div>



            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                  Password
                </label>
              </div>
              <div className="mt-1">
                <input
                  id="password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                  type="password"
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Enter a Password'
                />
              </div>
              <div className='text-red-500 text-sm'>
                {isError && <p>
                  * Your Password must be 8 characters long,should contain a digit, Uppercase Letter, Special and should not contain numerical sequence, alphabetical sequence,keyboard sequence and empty space.
                </p>
                }
              </div>
            </div>

            {/* <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                  Confirm Password
                </label>
              </div>
              <div className="mt-1">
                <input
                  id="cpassword"
                  name="cpassword"
                  type="cpassword"
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Confirm Your Password'
                />
              </div>
            </div> */}

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-orange px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"

              >
                Register
              </button>
            </div>
          </form>

          <p className="mt-10 text-center text-sm text-gray-500">
            Already a Parent?{' '}
            <Link to="/login" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
              Login Here
            </Link>
          </p>
          <p className="mt-2 text-center text-sm text-gray-500">
            Are you a Shelter Owner?{' '}
            <Link to="/login" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
              Shelter Login
            </Link>
          </p>
        </div>
      </div>
      {/* </div> */}
    </>
  )
}

export default PetAdopterRegister