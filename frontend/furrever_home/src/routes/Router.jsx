import React from 'react'
import { Route, Routes } from 'react-router-dom'
import AdopterProfile from '../components/Adopter/AdopterProfile'
import Shelter from '../components/Adopter/Shelter'
import AdopterChat from '../components/Chat/AdopterChat'
import ShelterChat from '../components/Chat/shelterchat'
import LostAndFoundHome from '../components/LostAndFound/LostAndFoundHome'
import PageNotFound from '../components/PageNotFound'
import PetForAdopter from '../components/Pet/PetForAdopter'
import PetForShelter from '../components/Pet/PetForShelter'
import PetAdopterRegister from '../components/Register/PetAdopterRegister'
import ShelterRegister from '../components/Register/ShelterRegister'
import ShelterProfile from '../components/Shelter/ShelterProfile'
import Layout from '../layouts/Layout'
import AdminHome from '../pages/AdminHome'
import ForgotPassword from '../pages/ForgotPassword'
import Home from '../pages/Home'
import Login from '../pages/Login'
import PetAdopterHome from "../pages/PetAdopterHome"
import ResetPassword from '../pages/ResetPassword'
import ShelterHome from "../pages/ShelterHome"
import PrivateRoutes from './PrivateRoutes'
import PrivateRoutesAdmin from './PrivateRoutesAdmin'
import PrivateRoutesAdopter from './PrivateRoutesAdopter'
import PrivateRoutesShelter from './PrivateRoutesShelter'
import PublicRoutes from './PublicRoutes'
import PetAdoptionRequests from '../components/Pet/PetAdoptionRequests'

const Router = () => {
    return (
        <Routes>
            <Route path="/" element={<Home />} />

            <Route path="/login" element={<Login />} />


            <Route element={<PublicRoutes />}>
                <Route path="/register/adopter" element={<PetAdopterRegister />} />
                <Route path="/register/shelter" element={<ShelterRegister />} />
                <Route path="/reset-password" element={<ResetPassword />} />
                <Route path="/forgot-password" element={<ForgotPassword />} />
            </Route>

            <Route element={<PrivateRoutes />}>
                <Route element={<PrivateRoutesAdmin />}>
                    <Route path="/admin/home" element={<Layout><AdminHome /></Layout>} />
                </Route>
                <Route element={<PrivateRoutesAdopter />}>
                    <Route path="/adopter/home" element={<Layout><PetAdopterHome /></Layout>} />
                    <Route path="/adopter/profile" element={<Layout><AdopterProfile /></Layout>} />
                    <Route path="/adopter/pet" element={<Layout><PetForAdopter /></Layout>} />
                    <Route path="/adopter/shelter/:id" element={<Layout><Shelter /></Layout>} />
                    <Route path="/chat/:shelterId" element={<Layout><AdopterChat /></Layout>} />
                    <Route path="/lost-found" element={<Layout><LostAndFoundHome /></Layout>} />
                </Route>
                <Route element={<PrivateRoutesShelter />}>
                    <Route path="/shelter/home" element={<Layout><ShelterHome /></Layout>} />
                    <Route path="/shelter/profile" element={<Layout><ShelterProfile /></Layout>} />
                    <Route path="/shelter/pet" element={<Layout><PetForShelter /></Layout>} />
                    <Route path="/shelter/pet/petadoptionrequest" element={<Layout><PetAdoptionRequests/></Layout>}/>
                    <Route path="/chat/shelter" element={<Layout><ShelterChat /></Layout>} />
                </Route>
            </Route>

            <Route path="*" element={<Layout><PageNotFound /></Layout>} />
        </Routes>
    )
}

export default Router