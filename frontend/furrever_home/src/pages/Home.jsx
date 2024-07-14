import React from 'react'
import Adoption from '../components/Adoption'
import Hero from '../components/Hero'
import Newsletter from '../components/Newsletter'
import Pets from '../components/Pets'
import Services from '../components/Services'
import Header from '../components/Header'
import Footer from '../components/Footer'

const Home = () => {
    return (
        <>
        <Header/>
            <div className='mx-auto overflow-hidden'>
                <Hero />
                <Pets />
                <Services />
                <Adoption />
                <Newsletter />
            </div>
            <Footer/>
        </>
    )
}

export default Home