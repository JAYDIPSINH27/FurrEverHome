import React, { useState,useEffect } from 'react'
import Footer from '../components/Footer'
import Header from '../components/Header'
import { readLocalStorage, saveLocalStorage,deleteLocalStorage } from '../utils/helper';
import axios from 'axios';
import { Spinner } from '@material-tailwind/react';
import { useNavigate } from 'react-router';

const Layout = ({ children }) => {

    const userid = readLocalStorage("id")
    const token = readLocalStorage("token")
    const [user,setUser] = useState({})
    const [loading, setLoading] = useState(false)
    
    // const User = JSON.parse(readLocalStorage("User"))

    const parseJwt = (token) => {        
        const decode = JSON.parse(atob(token.split('.')[1]));

        if (decode.exp * 1000 < new Date().getTime()) {
            handleLogout();
            console.log('Time Expired');
        }
    };

    const handleLogout = () => {
        deleteLocalStorage("token");
        deleteLocalStorage("User");
        deleteLocalStorage("role");
        deleteLocalStorage("id");
        navigate("/login");
      }
    parseJwt(token)
    
    const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`

    const getShelter= ()=>{
        axios.get(`${baseurl}/shelter/single/${userid}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            }
          })
            .then(response => {

              saveLocalStorage("User", JSON.stringify(response.data));
              setUser(response.data)
              setLoading(true)
              
            })
            .catch(error => {
              console.log(error);
            })
    }

    const getAdopter= ()=>{
        axios.get(`${baseurl}/petadopter/${userid}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            }
          })
            .then(response => {

              saveLocalStorage("User", JSON.stringify(response.data));
              setUser(response.data)
              setLoading(true)
            })
            .catch(error => {
              console.log(error);
            })
    }
    useEffect(() => {
        
        if(readLocalStorage("role")==="SHELTER"){
            getShelter()
        }else if(readLocalStorage("role")==="PETADOPTER"){
            getAdopter()
        }else{
          setLoading(true)
        }
    }, [])
    return (
        loading?
        <>
        
            <Header user={user}/>
            {children}
            <Footer />

        </>
        :
        <Spinner color="green" className="flex justify-center align-middle" />
    )
}

export default Layout