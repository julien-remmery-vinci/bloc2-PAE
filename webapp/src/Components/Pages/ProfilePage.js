import {clearPage} from "../../utils/render";
import {
  getAuthenticatedUser,
  getToken,
  isAuthenticated
} from "../../utils/auths";
import Navigate from "../Router/Navigate";

const ProfilePage = async () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    clearPage();
    document.title = "Profil";
    renderProfilPage();
  }
}

function renderProfilPage() {
  const main = document.querySelector('main');
  const DocumentTitle = document.createElement('h3');
  DocumentTitle.textContent = 'Profil';
  DocumentTitle.style.textAlign = 'center';
  main.appendChild(DocumentTitle);
  main.className = 'd-flex flex-column justify-content-center align-items-center vh-100';
  const form = document.createElement('form');
  const authenticatedUser = getAuthenticatedUser();
  form.className = 'p-5 w-50 bg-light rounded shadow';
  form.className = 'mx-auto p-5 w-50 position-relative float-end bg-light rounded shadow';
  const fields = ['lastname', 'firstname', 'email', 'phoneNumber'];
  const labels = ['Lastname', 'Firstname', 'Email', 'Numéro de téléphone'];

  fields.forEach((field, index) => {
    const title = document.createElement('h6');
    title.textContent = labels[index];
    title.className = 'fw-bold mb-1';

    const input = document.createElement('input');
    input.id = field;
    input.value = `${authenticatedUser[field]}`;
    input.className = 'form-control mb-3';

    input.addEventListener('input', () => {
      const existingButton = Array.from(
          form.getElementsByTagName('button')).find(
          button => button.textContent === 'Sauver');
      if (!existingButton) {
        const sauver = document.createElement('button');
        sauver.textContent = 'Sauver';
        sauver.className = 'btn btn-success mt-2';
        sauver.addEventListener('click', onSaveProfile);
        form.appendChild(sauver);
      }
    });

    form.appendChild(title);
    form.appendChild(input);
  });

  const changePasswordButton = document.createElement('button');
  changePasswordButton.textContent = 'Modifier mot de passe';
  changePasswordButton.className = 'position-absolute bottom-0 start-50 translate-middle-x btn btn-primary';
  form.appendChild(changePasswordButton);
  main.appendChild(form);

  changePasswordButton.addEventListener('click', event => {
    event.preventDefault();

    changePasswordButton.style.display = 'none';

    const passwordForm = document.createElement('form');
    passwordForm.className = 'p-5 w-50 bg-light rounded shadow';
    passwordForm.className = 'mx-auto p-5 w-50 position-relative float-end bg-light rounded shadow';

    const oldPasswordField = document.createElement('input');
    oldPasswordField.type = 'password';
    oldPasswordField.placeholder = 'Ancien mot de passe';
    oldPasswordField.className = 'form-control mb-3';
    passwordForm.appendChild(oldPasswordField);

    const passwordField = document.createElement('input');
    passwordField.type = 'password';
    passwordField.placeholder = 'Nouveau mot de passe';
    passwordField.className = 'form-control mb-3';
    passwordForm.appendChild(passwordField);

    const confirmPasswordField = document.createElement('input');
    confirmPasswordField.type = 'password';
    confirmPasswordField.placeholder = 'Confirmer le nouveau mot de passe';
    confirmPasswordField.className = 'form-control mb-3';
    passwordForm.appendChild(confirmPasswordField);

    const submitButton = document.createElement('button');
    submitButton.type = 'submit';
    submitButton.textContent = 'Sauver';
    submitButton.className = 'btn btn-primary';
    submitButton.addEventListener('click', onSavePassword);
    passwordForm.appendChild(submitButton);

    const cancelButton = document.createElement('button');
    cancelButton.type = 'button';
    cancelButton.textContent = 'Annuler';
    cancelButton.className = 'btn btn-secondary ms-2';
    cancelButton.addEventListener('click', () => {
      passwordForm.remove();  // Supprime le formulaire de modification de mot de passe
      changePasswordButton.style.display = 'block';  // Affiche à nouveau le bouton "Modifier mot de passe"
    });
    passwordForm.appendChild(cancelButton);

    main.appendChild(passwordForm);
  });
}

async function onSaveProfile(e) {
  e.preventDefault();
  const firstname = document.querySelector('#firstname').value;
  const lastname = document.querySelector('#lastname').value;
  const email = document.querySelector('#email').value.toLowerCase();
  const phoneNumber = document.querySelector('#phoneNumber').value;

  const options = {
    method: 'POST',
    body: JSON.stringify({
      firstname,
      lastname,
      email,
      phoneNumber,

    }),
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getToken()
    },
  };

  const response = await fetch('http://localhost:3000/auths/user/update', options);

  if (response.status !== 200) {
    const error = document.querySelector('#error');
    error.textContent = 'Erreur lors de la modification du profil';
    error.hidden = false;
  }
}

async function onSavePassword(e) {
  e.preventDefault();
  const oldPassword = document.querySelector('#oldPasswordField').value;
  const newPassword = document.querySelector('#passwordField').value;
  const confirmationPassword = document.querySelector('#confirmPasswordField').value;


  const options = {
    method: 'POST',
    body: JSON.stringify({
      oldPassword,
      newPassword,
      confirmationPassword,
    }),
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getToken()
    },
  };

  const response = await fetch('http://localhost:3000/auths/user/changepassword', options);

  if (response.status !== 200) {
    const error = document.querySelector('#error');
    error.textContent = 'Erreur lors du changement de mot de passe';
    error.hidden = false;
  }
}

export default ProfilePage;