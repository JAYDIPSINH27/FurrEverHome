import React from 'react'
import verifyAuthentication from '../hooks/verifyAuthentication'
import { Outlet, Navigate } from 'react-router-dom'

const PrivateRoutes = () => {
    const userToken = verifyAuthentication()

    return userToken ? <Outlet /> : <Navigate to='/login' />
}

export default PrivateRoutes