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
    form.className = 'mx-auto p-5 w-50 position-relative float-end';
    const containerDiv = document.createElement('div');
    const containerChangePassword = document.createElement('div');

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

    const changePasswordButton = document.createElement('button');
    changePasswordButton.textContent = 'Modifier mot de passe';
    changePasswordButton.className = 'position-absolute bottom-0 start-50 translate-middle-x btn btn-primary';

    containerDiv.appendChild(form);
    form.appendChild(changePasswordButton);
    main.appendChild(containerDiv);
    main.appendChild(containerChangePassword);

    const cancelChangePassword = document.createElement('button');
    cancelChangePassword.textContent = 'annuler';
    cancelChangePassword.className = 'position-absolute bottom-0 start-0 translate-middle-x btn btn-primary';

    const saveChangePassword = document.createElement('button');
    saveChangePassword.textContent = 'sauver';
    saveChangePassword.className = 'position-absolute bottom-0 start-50 translate-middle-x btn btn-primary';

    changePasswordButton.addEventListener('click', () => {


        const changePasswordForm = document.createElement('form');
        changePasswordForm.id = 'changePasswordForm';
        changePasswordForm.className = 'mx-auto p-5 w-50 position-relative float-end';

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
        containerChangePassword.appendChild(changePasswordForm);


        cancelChangePassword.addEventListener('click', () => {
            containerChangePassword.removeChild(changePasswordForm);
            form.appendChild(changePasswordButton);
        });


        saveChangePassword.addEventListener('click', onChangePassword);

        changePasswordForm.appendChild(cancelChangePassword);
        changePasswordForm.appendChild(saveChangePassword);
        form.removeChild(changePasswordButton);
        containerChangePassword.appendChild(changePasswordForm);
    });
}

async function onChangePassword(e){
    e.preventDefault();
    const authenticatedUser = getAuthenticatedUser();
    const oldPassword = document.querySelector('#oldPassword').value;
    const newPassword = document.querySelector('#newPassword').value;
    const confirmationPassword = document.querySelector('#confirmationPassword').value;
    const erreur = document.createElement('p');

    const options = {
        method: 'PUT',
        body: JSON.stringify({
            oldPassword,
            newPassword,
            confirmationPassword
        }),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': authenticatedUser.token
        },
    };

    const form1 = document.querySelector('#changePasswordForm');
    const lastError = form1.querySelector('.text-danger');

    if(newPassword === confirmationPassword) {
        // call the api to change the password
        const response = await fetch(`http://localhost:3000/auths/passwordChange`, options);
        if (response.status === 401) {
            // clear the last error message
            if (lastError) {
                form1.removeChild(lastError);
            }
            erreur.textContent = 'Le mot de passe actuel est incorrect';
            erreur.className = 'text-danger';
            const form = document.querySelector('#changePasswordForm');
            form.appendChild(erreur);
        }
    } else {
        if (lastError) {
            form1.removeChild(lastError);
        }
        erreur.textContent = 'Les mots de passe ne correspondent pas à la confirmation';
        erreur.className = 'text-danger';
        const form = document.querySelector('#changePasswordForm');
        form.appendChild(erreur);
    }
}

export default ProfilePage;