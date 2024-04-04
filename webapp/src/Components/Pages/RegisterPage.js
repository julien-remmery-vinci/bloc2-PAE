import { clearPage, renderPageTitle } from '../../utils/render';
import Navigate from '../Router/Navigate';
import {isAuthenticated, setAuthenticatedUser, setToken} from "../../utils/auths";
import Navbar from "../Navbar/Navbar";

const RegisterPage = () => {
  if (isAuthenticated()) {
    Navigate('/');
  } else {
    clearPage();
    renderPageTitle("S'enregistrer");
    document.title = "S'enregistrer";
    renderRegisterForm();
  }
};

function renderRegisterForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.className = 'p-5';
  const titleLastName = document.createElement('h6');
  titleLastName.textContent = 'Nom';
  const lastname = document.createElement('input');
  lastname.type = 'text';
  lastname.placeholder = 'ex: Dupont';
  lastname.id = 'lastname';
  lastname.required = true;
  lastname.className = 'form-control mb-3';
  const titleFirstName = document.createElement('h6');
  titleFirstName.textContent = 'Prénom';
  const firstname = document.createElement('input');
  firstname.type = 'text';
  firstname.placeholder = 'ex: Jean';
  firstname.id = 'firstname';
  firstname.required = true;
  firstname.className = 'form-control mb-3';
  const titleEmail = document.createElement('h6');
  titleEmail.textContent = 'Email';
  const email = document.createElement('input');
  email.type = 'email';
  email.value.toLowerCase();
  email.placeholder = 'ex: luc.jean@student.vinci.be';
  email.id = 'email';
  email.required = true;
  email.className = 'form-control mb-3';
  const titlePhoneNumber = document.createElement('h6');
  titlePhoneNumber.textContent = 'Numéro de téléphone';
  const phoneNumber = document.createElement('input');
  phoneNumber.type = 'tel';
  phoneNumber.id = 'phoneNumber';
  phoneNumber.placeholder = 'ex: 0499673562';
  phoneNumber.required = true;
  phoneNumber.className = 'form-control mb-3';
  const passwordDiv = document.createElement('div');
  const passwordHtml = `
    <div class="input-group">
      <input id="password" type="password" class="form-control mb-3" name="password" required>
      <button class="btn btn-outline-secondary mb-3" type="button" id="hidePassword">
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
  const titlePassword = document.createElement('h6');
  titlePassword.textContent = 'Mot de passe';
  passwordDiv.appendChild(titlePassword);
  passwordDiv.innerHTML += passwordHtml;
  const div = document.createElement('div');
  div.hidden = true;
  const div1 = document.createElement('div');
  const div2 = document.createElement('div');
  const profLabel = document.createElement('label');
  const adminLabel = document.createElement('label');
  profLabel.textContent = 'Professeur';
  adminLabel.textContent = 'Admin';
  const prof = document.createElement('input');
  prof.type = 'radio';
  prof.name = 'role';
  prof.value = 'professeur';
  prof.className = 'form-check';
  prof.required = !div.hidden;
  const admin = document.createElement('input');
  admin.type = 'radio';
  admin.value = 'administratif';
  admin.name = 'role';
  admin.className = 'form-check';
  div1.appendChild(prof);
  div1.appendChild(profLabel);
  div2.appendChild(admin);
  div2.appendChild(adminLabel);
  div.appendChild(div1);
  div.appendChild(div2);
  div.style.display = 'flex';
  div.style.justifyContent = 'space-around';
  const submit = document.createElement('input');
  submit.value = "S'inscrire";
  submit.type = 'submit';
  submit.className = 'btn btn-primary';
  const login = document.createElement('p');
  login.textContent = 'Vous avez déjà un compte ? ';
  login.className = 'd-block text-center mt-3';
  const link = document.createElement('a');
  link.textContent = 'Connectez-vous';
  link.href = '/login';

  form.appendChild(titleFirstName);
  form.appendChild(firstname);
  form.appendChild(titleLastName);
  form.appendChild(lastname);
  form.appendChild(titleEmail);
  form.appendChild(email);
  form.appendChild(titlePhoneNumber);
  form.appendChild(phoneNumber);
  form.appendChild(passwordDiv);
  email.addEventListener('change', () => {
    div.hidden = !useRegex(email.value);
    prof.required = !div.hidden;
  });
  form.appendChild(div);
  form.appendChild(submit);
  const error = document.createElement('p');
  error.id = 'error';
  error.className = 'text-danger text-center';
  error.hidden = true;
  form.appendChild(error);
  main.appendChild(form);
  const hidePassword = document.querySelector('#hidePassword');
  hidePassword.addEventListener('click', () => {
    const password = document.querySelector('#password');
    if (password.type === 'password') {
      password.type = 'text';
    } else {
      password.type = 'password';
    }
  });
  main.appendChild(login);
  login.appendChild(link);
  form.addEventListener('submit', onRegister);
}

function useRegex(input) {
  const regex = /[A-Za-z]+.[A-Za-z]+@vinci.be/i;
  return regex.test(input);
}

async function onRegister(e) {
  e.preventDefault();

  const firstname = document.querySelector('#firstname').value;
  const lastname = document.querySelector('#lastname').value;
  const email = document.querySelector('#email').value.toLowerCase();
  const phoneNumber = document.querySelector('#phoneNumber').value;
  const password = document.querySelector('#password').value;
  let role;
  if(document.querySelector('input[name="role"]:checked')) {
    role = document.querySelector('input[name="role"]:checked').value;
  }

  const options = {
    method: 'POST',
    body: JSON.stringify({
      firstname,
      lastname,
      email,
      phoneNumber,
      password,
      role
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch('http://localhost:3000/auths/register', options);

  if (response.status !== 200) {
    const error = document.querySelector('#error');
    error.textContent = 'Erreur lors de l\'inscription';
    error.hidden = false;
    return;
  }

  const authenticatedUser = await response.json();
  setToken(authenticatedUser.token);
  setAuthenticatedUser(authenticatedUser.user);

  console.log('Newly registered & authenticated user : ', authenticatedUser);

  Navbar();
  Navigate('/');
}

export default RegisterPage;