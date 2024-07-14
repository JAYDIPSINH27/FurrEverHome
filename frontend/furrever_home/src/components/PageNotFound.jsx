import React from 'react'
// import Error from '/img/error/404.jpg'
import { Link } from 'react-router-dom'

const PageNotFound = () => {
    return (
        <section className="">
            <div className="container min-h-screen px-6 py-12 mx-auto lg:flex lg:items-center lg:gap-12">
                <div classNameName="wf-ull lg:w-1/2">
                    <p className="text-sm font-medium text-orange-500 ">404 error</p>
                    <h1 className="mt-3 text-2xl font-semibold text-teal">Page not found</h1>
                    <p className="mt-4 text-primary">Sorry, the page you are looking for doesn't exist.Here are some helpful links:</p>

                    <div className="flex items-center mt-6 gap-x-3">
                        <Link to="/">
                            <button className="btn btn-primary">
                                Take me home
                            </button>
                        </Link>
                    </div>
                </div>

                {/* <div className="relative w-full mt-8 lg:w-1/2 lg:mt-0">
                    <img className=" w-full lg:h-[32rem] h-80 md:h-96 rounded-lg object-cover " src={Error} alt="" />
                </div> */}
            </div>
        </section>
    )
}

export default PageNotFound