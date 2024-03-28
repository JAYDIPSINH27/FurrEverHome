import React from 'react'
import verifyAuthentication from '../hooks/verifyAuthentication'
import { Outlet, Navigate } from 'react-router-dom'

const PublicRoutes = ({ children }) => {
    const userToken = verifyAuthentication()

    return userToken ? (userToken.userRole = "Shelter") ? <Navigate to="/shelter/home" /> : <Navigate to="/shelter/home" /> : <Outlet />
}

export default PublicRoutes