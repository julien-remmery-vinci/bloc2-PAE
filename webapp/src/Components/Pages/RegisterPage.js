import { clearPage, renderPageTitle } from '../../utils/render';
import Navigate from '../Router/Navigate';

const RegisterPage = () => {
  clearPage();
  renderPageTitle('Register');
  renderRegisterForm();
};

function renderRegisterForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.className = 'p-5';
  const titleLastName = document.createElement('h6');
  titleLastName.textContent = 'Nom';
  const lastname = document.createElement('input');
  lastname.type = 'text';
  lastname.id = 'lastname';
  lastname.placeholder = 'lastname';
  lastname.required = true;
  lastname.className = 'form-control mb-3';
  const titleFirstName = document.createElement('h6');
  titleFirstName.textContent = 'Prénom';
  const firstname = document.createElement('input');
  firstname.type = 'text';
  firstname.id = 'firstname';
  firstname.placeholder = 'firstname';
  firstname.required = true;
  firstname.className = 'form-control mb-3';
  const titleEmail = document.createElement('h6');
  titleEmail.textContent = 'Email';
  const email = document.createElement('input');
  email.type = 'email';
  email.id = 'email';
  email.placeholder = 'email';
  email.required = true;
  email.className = 'form-control mb-3';
  const titlePhoneNumber = document.createElement('h6');
  titlePhoneNumber.textContent = 'Numéro de téléphone';
  const phoneNumber = document.createElement('input');
  phoneNumber.type = 'tel';
  phoneNumber.id = 'phoneNumber';
  phoneNumber.placeholder = 'phoneNumber';
  phoneNumber.required = true;
  phoneNumber.className = 'form-control mb-3';
  const titlePassword = document.createElement('h6');
  titlePassword.textContent = 'Password';
  const password = document.createElement('input');
  password.type = 'password';
  password.id = 'password';
  password.required = true;
  password.placeholder = 'password';
  password.className = 'form-control mb-3';
  const submit = document.createElement('input');
  submit.value = "S'inscrire";
  submit.type = 'submit';
  submit.className = 'btn btn-danger';
  form.appendChild(username);
  form.appendChild(password);
  form.appendChild(submit);
  main.appendChild(form);
  form.addEventListener('submit', onRegister);
}

async function onRegister(e) {
  e.preventDefault();

  const username = document.querySelector('#username').value;
  const password = document.querySelector('#password').value;

  const options = {
    method: 'POST',
    body: JSON.stringify({
      username,
      password,
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch('/api/auths/register', options);

  if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`);

  const authenticatedUser = await response.json();

  console.log('Newly registered & authenticated user : ', authenticatedUser);

  Navigate('/');
}

export default RegisterPage;