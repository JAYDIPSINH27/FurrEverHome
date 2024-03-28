import {
    Dialog,
    DialogBody,
    DialogHeader
} from "@material-tailwind/react";
import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Modal = () => {
    const [open, setOpen] = useState(false);

    const handleOpen = () => setOpen(!open);

    return (
        <>
            <button onClick={handleOpen} className='btn btn-outline'>Register</button>
            <Dialog
                open={open}
                handler={handleOpen}
                animate={{
                    mount: { scale: 1, y: 0 },
                    unmount: { scale: 0.9, y: -100 },
                }}
                className="bg-cream"
            >
                <DialogHeader className='justify-center align-middle'>Register As:</DialogHeader>
                <DialogBody>

                    <div className='flex gap-7 align-middle justify-center'>

                        <div>
                            <Link to='/register/adopter'>
                                <button className='btn btn-orange'>Pet Adopter</button>
                            </Link>
                        </div>

                        <div>
                            <Link to='/register/shelter'>
                                <button className='btn btn-primary'>Shelter</button>
                            </Link>
                        </div>

                    </div>

                </DialogBody>
            </Dialog>
        </>
    );
}

export default Modal



