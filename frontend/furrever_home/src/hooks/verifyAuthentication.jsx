
const verifyAuthentication = () => {

    const user = localStorage.getItem('token')
    const userRole = localStorage.getItem('role')

    if (user) {
        return { user, userRole }
    } else {
        return false
    }
}

export default verifyAuthentication