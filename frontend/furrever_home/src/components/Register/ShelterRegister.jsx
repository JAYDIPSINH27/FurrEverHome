import axios from 'axios';
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import { validatePassword } from '../../utils/helper';
import Logo from '../Logo';

const ShelterRegister = () => {


  const [response, setResponse] = useState({})
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState({})
  const [isError, setIsError] = useState(false);

  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    name: "",
    password: "",
    contact: "",
    role: "shelter",
    capacity: "",
    address: "",
    city: "",
    country: "",
    zipcode: "",
    checkRole: "2"
  })

  const [image, setImage] = useState([])
  const [license, setLicense] = useState([])
  let errors = []

  const handleChange = (event) => {

    const newData = { ...formData }
    newData[event.target.id] = event.target.value

    setFormData(newData)
  }

  const handleImage = (image) => {

    const reader = new FileReader();
    reader.readAsDataURL(image);
    reader.onload = function (e) {

      setImage(e.target.result)

    };

    reader.onerror = function () {
      console.log(reader.error);
    };
  }

  const handleLicense = (image) => {



    const reader = new FileReader();
    setLicense(image)
    reader.readAsDataURL(image);
    reader.onload = function (e) {
      setLicense(e.target.result)
    };

    reader.onerror = function () {
      console.log(reader.error);
    };
  }

  const handleSubmit = (event) => {

    event.preventDefault();


    const data = {
      email: formData.email,
      name: formData.name,
      password: formData.password,
      contact: formData.contact,
      role: formData.role,
      capacity: formData.capacity,
      imageBase64: image,
      license: license,
      address: formData.address,
      city: formData.city,
      country: formData.country,
      zipcode: formData.zipcode,
      checkRole: formData.checkRole

    }

    errors = validatePassword(formData.password)

    if (errors.length === 0) {

      axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/auth/signup`, data)
        .then((res) => {

          setResponse(res)
          setLoading(false)
          toast.info("Your Shelter Verification is Pending!");
          navigate("/login")
        })
        .catch((err) => {

          setError(err)
          toast.error(err.message)
        })
    }
    else {
      toast.error("Invalid Password")
      setIsError(true)
    }
  }

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

              <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                Shelter Name
              </label>
              <div className="mt-1">
                <input
                  id="name"
                  name="name"
                  type="text"
                  value={formData.name}
                  onChange={handleChange}
                  autoComplete="text"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Enter Shelter Name'
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
                  type="email"
                  value={formData.email}
                  onChange={handleChange}
                  autoComplete="email"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Shelter Email Address'
                />
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
                  type="password"
                  value={formData.password}
                  onChange={handleChange}
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

            <div>
              <label htmlFor="contact" className="text-sm font-medium leading-6 text-gray-900 flex">
                Contact Number
              </label>
              <div className="mt-1">
                <input
                  id="contact"
                  name="contact"
                  type="tel"
                  value={formData.contact}
                  onChange={handleChange}
                  autoComplete="tel"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Shelter Contact Number'
                />
              </div>
            </div>

            <div>
              <label htmlFor="contact" className="text-sm font-medium leading-6 text-gray-900 flex">
                Shelter Capacity
              </label>
              <div className="mt-1">
                <input
                  id="capacity"
                  name="capacity"
                  type="text"
                  value={formData.capacity}
                  autoComplete="text"
                  onChange={handleChange}
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Shelter Capacity'
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

            <div className="">
              <label className="block text-sm font-medium leading-6 text-gray-900" >Upload Shelter Image</label>
              <input type="file"
                name='image'
                required
                onChange={(event) => { handleImage(event.target.files[0]) }}
                className="w-full text-black text-sm bg-white border file:cursor-pointer cursor-pointer file:border-0 file:py-2.5 file:px-4 file:bg-gray-100 file:hover:bg-gray-200 file:text-black rounded" />
              <p className="text-xs text-gray-400 mt-2">PNG, JPG are Allowed.</p>
            </div>

            <div className="">
              <label className="block text-sm font-medium leading-6 text-gray-900">Upload Valid Proof</label>
              <input type="file"
                name='license'
                required
                onChange={(event) => { handleLicense(event.target.files[0]) }}
                className="w-full text-black text-sm bg-white border file:cursor-pointer cursor-pointer file:border-0 file:py-2.5 file:px-4 file:bg-gray-100 file:hover:bg-gray-200 file:text-black rounded" />
              <p className="text-xs text-gray-400 mt-2">PNG, JPG are Allowed.</p>
            </div>

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
            Already Registered?{' '}
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
    </>
  )
}

export default ShelterRegister