import React, { useState, } from 'react'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'
import { Link } from 'react-router-dom'
import Logo from '../components/Logo'

const Login = () => {

  const [email,setEmail] = useState('');
  const [password,setPassword] = useState('');

  const navigate = useNavigate();

  const handleEmailChange = (event) => {
      setEmail(event.target.value);
  }

  const handlePasswordChange =(event) =>{
    setPassword(event.target.value);
  }
  
  const handleSubmit = async(event) => {
    event.preventDefault();

    axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/auth/signin`,{email,password})
      .then(response=>{

        console.log(response);
          saveLocalStorage("token",response.data.token);
          saveLocalStorage("role",response.data.userRole);

            if(response.data.verified == false)
                alert("User is not verified")

            else if(response.data.userRole == "PETADOPTER")
                navigate("/PetAdopterHome");

            else
                navigate("/ShelterHome");
        
      })
      .catch(error =>{
        alert("Incorrect Email or Paassword");
      })
      
  }

  return (
    <>
      <div className="flex min-h-full flex-1 flex-col m-8 justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <Logo />
          <h2 className="mt-10 font-primary text-center text-2xl font-bold leading-9 tracking-tight text-teal">
            Welcome Back to FurreverHome
          </h2>
        </div>

        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form className="space-y-6" action="#" method="POST" onSubmit={handleSubmit}>
            <div>
            
              <label htmlFor="email" className="text-sm font-medium leading-6 text-gray-900 flex">
                 Email address
              </label>
              <div className="mt-2">
                <input
                  id="email"
                  name="email"
                  type="email"
                  autoComplete="email"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Your Email Address'
                  onChange={handleEmailChange}
                />
              </div>
            </div>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                  Password
                </label>
                <div className="text-sm">
                  <Link to="/forgot-password" className="font-semibold text-indigo-600 hover:text-indigo-500">
                    Forgot password?
                  </Link>
                </div>
              </div>
              <div className="mt-2">
                <input
                  id="password"
                  name="password"
                  type="password"
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Your Password'
                  onChange={handlePasswordChange}
                />
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-orange px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              >
                Sign in
              </button>
            </div>
          </form>

          <p className="mt-10 text-center text-sm text-gray-500">
            Want to register as a PetAdopter?{' '}
            <Link to="/register/adopter" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
              Click here
            </Link>
          </p>
          <p className="mt-2 text-center text-sm text-gray-500">
            Want to register as a Shelter?{' '}
            <Link to="/register/shelter" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
              Click here
            </Link>
          </p>
        </div>
      </div>
    </>
  )
 

}

export default Login