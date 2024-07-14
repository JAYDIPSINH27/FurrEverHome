import React from 'react'
import ImgLogo from '/img/logo/LogoWithText_NoBG.png'
import { Link } from 'react-router-dom'

const Logo = () => {
    return (
        <Link to='/'>
            <img
                className="mx-auto h-20 w-auto"
                src={ImgLogo}
                alt="Your Company"
            />
        </Link>
    )
}

export default Logo