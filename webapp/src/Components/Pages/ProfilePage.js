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
  main.className = 'd-flex flex-column justify-content-center align-items-center vh-90';
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

    const popup = document.createElement('div');
    popup.id = 'popup';
    popup.style.display = 'none';
    document.body.appendChild(popup);

    input.addEventListener('input', () => {
      const existingButton = document.querySelector('#sauver');
      if (!existingButton) {
        const sauver = document.createElement('button');
        sauver.textContent = 'Sauver';
        sauver.id = 'sauver';
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
  changePasswordButton.className = 'position-absolute bottom-10 start-50 translate-middle-x btn btn-primary';
  form.appendChild(changePasswordButton);
  main.appendChild(form);

  changePasswordButton.addEventListener('click', event => {
    event.preventDefault();

    changePasswordButton.style.display = 'none';
    const passwordDiv = document.createElement('div');
    form.appendChild(passwordDiv);
    const passwordLabel = document.createElement('label');
    passwordLabel.textContent = 'Modification du mot de passe';
    passwordLabel.className = 'fw-bold mb-1';
    passwordDiv.appendChild(passwordLabel);

    const passwordFields = {
      oldPassword: "Ancien mot de passe",
      newPassword: "Nouveau mot de passe",
      confirmPassword: "Confirmer le nouveau mot de passe"
    };

    Object.entries(passwordFields).forEach(([key, value]) => {
      const div = document.createElement('div');
      const passwordHtml = `
        <div class="input-group">
          <input id="${key}" type="password" placeholder="${value}" class="form-control mb-3" required>
          <button class="btn btn-outline-secondary mb-3" type="button" id="${key}Hide">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-slash" viewBox="0 0 16 16">
                    <path
                      d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7.028 7.028 0 0 0-2.79.588l.77.771A5.944 5.944 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.134 13.134 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755-.165.165-.337.328-.517.486z" />
                    <path
                      d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829" />
                    <path
                      d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708" />
                  </svg>
          </button>
        </div>
      `;
      div.innerHTML += passwordHtml;
      passwordDiv.appendChild(div);
      const button = document.querySelector(`#${key}Hide`);
      button.addEventListener('click', () => {
        const password = document.querySelector(`#${key}`);
        if (password.type === 'password') {
          password.type = 'text';
        } else {
          password.type = 'password';
        }
      });
    });

    const submitButton = document.createElement('button');
    submitButton.textContent = 'Sauver';
    submitButton.className = 'btn btn-primary';
    submitButton.addEventListener('click', onSavePassword);
    passwordDiv.appendChild(submitButton);

    const cancelButton = document.createElement('button');
    cancelButton.textContent = 'Annuler';
    cancelButton.className = 'btn btn-secondary ms-2';
    cancelButton.addEventListener('click', () => {
      passwordDiv.remove();  // Supprime le formulaire de modification de mot de passe
      changePasswordButton.style.display = 'block';  // Affiche à nouveau le bouton "Modifier mot de passe"
    });
    passwordDiv.appendChild(cancelButton);

    const error = document.createElement('div');
    error.id = 'error';
    error.style.color = 'red';
    error.hidden = true;
    passwordDiv.appendChild(error);
  });

}

async function onSaveProfile(e) {
  e.preventDefault();
  const firstname = document.querySelector('#firstname').value;
  const lastname = document.querySelector('#lastname').value;
  const email = document.querySelector('#email').value.toLowerCase();
  const phoneNumber = document.querySelector('#phoneNumber').value;

  const options = {
    method: 'PUT',
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

  const response = await fetch('http://localhost:3000/users/update', options);

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
  const confirmationPassword = document.querySelector(
      '#confirmPasswordField').value;

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

  const response = await fetch('http://localhost:3000/users/changepassword',
      options);

  if (response.status !== 204) {
    const error = document.querySelector('#error');
    error.textContent = 'Erreur lors du changement de mot de passe';
    error.hidden = false;
  }
  if (response.status === 204) {
    const popup = document.querySelector('#popup');
    popup.textContent = 'Mot de passe modifié avec succès';
    popup.style.display = 'block';
    setTimeout(() => {
      popup.style.display = 'none';
    }, 10000);
  }
}

export default ProfilePage;