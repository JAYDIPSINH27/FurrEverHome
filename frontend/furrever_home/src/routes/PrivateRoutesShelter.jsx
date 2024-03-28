import React from 'react'
import verifyAuthentication from '../hooks/verifyAuthentication'
import { Outlet, Navigate } from 'react-router-dom'

const PrivateRoutesShelter = () => {
    const userToken = verifyAuthentication()

    return userToken.userRole === "SHELTER" ? <Outlet /> : <Navigate to='/' />
}

export default PrivateRoutesShelter