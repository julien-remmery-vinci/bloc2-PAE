import {clearPage} from "../../utils/render";
import {getAuthenticatedUser} from "../../utils/auths";

const ProfilePage = async () => {
    clearPage();
    renderProfilPage();
}

function renderProfilPage() {
    const main = document.querySelector('main');
    const form = document.createElement('form');
    const authenticatedUser = getAuthenticatedUser();
    form.className = 'mx-auto p-5 w-50 position-relative start-10';
    const containerDiv = document.createElement('div');

    const titleLastName = document.createElement('h6');
    titleLastName.textContent = 'Lastname';
    const lastname = document.createElement('input');
    lastname.readOnly=true;
    lastname.id='lastname';
    lastname.value = `${authenticatedUser.lastname}`;
    lastname.className = 'form-control-sm';
    form.appendChild(titleLastName);
    form.appendChild(lastname);

    const titleFirstName = document.createElement('h6');
    titleFirstName.textContent = 'Firstname ';
    const firstname = document.createElement('input');
    firstname.readOnly=true;
    firstname.id='firstname';
    firstname.value = `${authenticatedUser.firstname}`;
    firstname.className = 'form-control-sm';
    form.appendChild(titleFirstName);
    form.appendChild(firstname);

    const titleEmail = document.createElement('h6');
    titleEmail.textContent = 'Email ';
    const email = document.createElement('input');
    email.readOnly=true;
    email.id='email';
    email.value = `${authenticatedUser.email}`;
    email.className = 'form-control-sm';
    form.appendChild(titleEmail);
    form.appendChild(email);

    const titlePhoneNum = document.createElement('h6');
    titlePhoneNum.textContent = 'Numéro de téléphone ';
    const phoneNumber = document.createElement('input');
    phoneNumber.readOnly=true;
    phoneNumber.id='phoneNumber';
    phoneNumber.value = `${authenticatedUser.phoneNumber}`;
    phoneNumber.className = 'form-control-sm';
    form.appendChild(titlePhoneNum);
    form.appendChild(phoneNumber);

    const titlePassword = document.createElement('h6');
    titlePassword.textContent = 'Mot de passe actuel ';
    const password = document.createElement('input');
    password.readOnly=true;
    password.id='password';
    password.value = '********';
    password.className = 'form-control-sm';
    form.appendChild(titlePassword);
    form.appendChild(password);

    const changePasswordButton = document.createElement('button');
    changePasswordButton.textContent = 'Modifier mot de passe';
    changePasswordButton.className = 'position-absolute bottom-0 start-50 translate-middle-x btn btn-primary';

    containerDiv.appendChild(form);
    main.appendChild(containerDiv);
    main.appendChild(changePasswordButton);

    changePasswordButton.addEventListener('click', () => {


        const changePasswordForm = document.createElement('form');
        changePasswordForm.className = 'mx-auto p-5 w-50 position-relative start-10';

        const titleOldPassword = document.createElement('h6');
        titleOldPassword.textContent = 'ancien mot de passe';
        const oldPassword = document.createElement('input');
        oldPassword.required=true;
        oldPassword.type = 'password';
        oldPassword.id = 'oldPassword';
        oldPassword.className = 'form-control-sm';
        changePasswordForm.appendChild(titleOldPassword);
        changePasswordForm.appendChild(oldPassword);

        const titleNewPassword = document.createElement('h6');
        titleNewPassword.textContent = 'nouveau mot de passe ';
        const newPassword = document.createElement('input');
        newPassword.required=true;
        newPassword.type = 'password';
        newPassword.id = 'newPassword';
        newPassword.className = 'form-control-sm';
        changePasswordForm.appendChild(titleNewPassword);
        changePasswordForm.appendChild(newPassword);

        const titleConfirmationPassword = document.createElement('h6');
        titleConfirmationPassword.textContent = 'confirmer nouveau mot de passe';
        const confirmationPassword = document.createElement('input');
        confirmationPassword.required=true;
        confirmationPassword.type = 'password';
        confirmationPassword.id = 'confirmationPassword';
        confirmationPassword.className = 'form-control-sm';
        changePasswordForm.appendChild(titleConfirmationPassword);
        changePasswordForm.appendChild(confirmationPassword);

        const cancelChangePassword = document.createElement('button');
        cancelChangePassword.textContent = 'annuler';
        cancelChangePassword.className = 'position-absolute bottom-0 start-0 translate-middle-x btn btn-primary';

        cancelChangePassword.addEventListener('click', () => {
            main.removeChild(changePasswordForm);
            main.appendChild(changePasswordButton);
        });

        const saveChangePassword = document.createElement('button');
        saveChangePassword.textContent = 'sauver';
        saveChangePassword.className = 'position-absolute bottom-0 start-50 translate-middle-x btn btn-primary';
        saveChangePassword.addEventListener('click', () => {
            if(newPassword.value === confirmationPassword.value) {
                main.removeChild(changePasswordForm);
                main.appendChild(changePasswordButton);
            } else {
                alert('Les mots de passe ne correspondent pas');
            }
        });

        changePasswordForm.appendChild(cancelChangePassword);
        changePasswordForm.appendChild(saveChangePassword);
        main.removeChild(changePasswordButton);
        main.appendChild(changePasswordForm);
    });
}

export default ProfilePage;