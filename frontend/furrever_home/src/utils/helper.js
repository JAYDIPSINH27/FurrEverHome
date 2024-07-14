
export const saveLocalStorage=(key,value) =>{
    localStorage.setItem(key,value);
}

export const deleteLocalStorage=(key) =>{
    localStorage.removeItem(key);
}

export const readLocalStorage=(key)=>{
    return localStorage.getItem(key);
}

export const validatePassword = (password) => {

    const uppercaseRegex = /[A-Z]/;
    const digitRegex = /\d/;
    const specialCharRegex = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/;
    const numericalSequenceRegex = /(012|123|234|345|456|567|678|789)/;
    const alphabeticalSequenceRegex = /(abc|bcd|cde|def|efg|fgh|ghi|hij|ijk|jkl|klm|lmn|mno|nop|opq|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz)/;
    const qwertySequenceRegex = /(qwerty|asdfgh|zxcvbn)/;
    const whitespaceRegex = /\s/;

    const errors = [];

    if (password.length < 8 || password.length > 30) {
        errors.push("* Password must be between 8 and 30 characters long.");
    }
    if (!uppercaseRegex.test(password)) {
        errors.push("* Password must contain at least one uppercase letter.");
    }
    if (!digitRegex.test(password)) {
         errors.push("* Password must contain at least one digit.");
    }
    if (!specialCharRegex.test(password)) {
        errors.push("* Password must contain at least one special character.");
    }
    if (numericalSequenceRegex.test(password)) {
        errors.push("* Password must not contain numerical sequences.");
    }
    if (alphabeticalSequenceRegex.test(password)) {
        errors.push("* Password must not contain alphabetical sequences.");
    }
    if (qwertySequenceRegex.test(password)) {
        errors.push("* Password must not contain QWERTY sequences.");
    }
    if (whitespaceRegex.test(password)) {
        errors.push("* Password must not contain whitespace characters.");
    }
    return errors;
}