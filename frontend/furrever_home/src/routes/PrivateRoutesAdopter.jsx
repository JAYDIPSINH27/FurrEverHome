import React from 'react'
import verifyAuthentication from '../hooks/verifyAuthentication'
import { Outlet, Navigate } from 'react-router-dom'

const PrivateRoutesAdopter = () => {
    const userToken = verifyAuthentication()

    return userToken.userRole === "PETADOPTER" ? <Outlet /> : <Navigate to='/' />
}

export default PrivateRoutesAdopter