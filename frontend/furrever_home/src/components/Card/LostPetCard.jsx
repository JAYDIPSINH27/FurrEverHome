import React from 'react'
import UpdateLostPet from "../LostAndFound/UpdateLostPet";
const PetCard = ({
    className,
    type,
    breed,
    colour,
    gender,
    phone,
    email,
    petImage,
    id,
    setChange,
    tab
}) => {

    const pet = {
        type,
        breed,
        colour,
        gender,
        phone,
        email,
        petImage,
        id,

    }

    const thumbnailAlt = "Alternate Image"
    return (
        <div className={`rounded-lg p-6 shadow-sm ${className}`}>
            <div className="overflow-hidden rounded-lg">
                <img
                    className="w-52 h-52 cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 rounded-full"
                    src={petImage}
                    alt={petImage}
                />
            </div>
            <h3 className="pt-5 text-primary block">
                {type}
            </h3>
            <h5 className="w-full text-[14px] cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 rounded-lg h-auto">
                {breed}
            </h5>
            <p className="font-normal text-gray-500 cursor-pointer text-lg duration-300 transition hover:text-[#FA5252] mt-2">
                Email:  <a href={`mailto:${email}`}>{email}</a>
            </p>
            <p className="font-normal text-gray-500 cursor-pointer text-lg duration-300 transition hover:text-[#FA5252] mt-2">
                Phone:  {phone}
            </p>

            {tab === "1" ?  <></>: <UpdateLostPet pets={pet} setChange={setChange} />}
            
        </div>
    )
}

export default PetCard
