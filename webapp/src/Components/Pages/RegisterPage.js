import { clearPage, renderPageTitle } from '../../utils/render';
import Navigate from '../Router/Navigate';

const RegisterPage = () => {
  clearPage();
  renderPageTitle("S'enregistrer");
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
  lastname.required = true;
  lastname.className = 'form-control mb-3';
  const titleFirstName = document.createElement('h6');
  titleFirstName.textContent = 'Prénom';
  const firstname = document.createElement('input');
  firstname.type = 'text';
  firstname.id = 'firstname';
  firstname.required = true;
  firstname.className = 'form-control mb-3';
  const titleEmail = document.createElement('h6');
  titleEmail.textContent = 'Email';
  const email = document.createElement('input');
  email.type = 'email';
  email.id = 'email';
  email.required = true;
  email.className = 'form-control mb-3';
  const titlePhoneNumber = document.createElement('h6');
  titlePhoneNumber.textContent = 'Numéro de téléphone';
  const phoneNumber = document.createElement('input');
  phoneNumber.type = 'tel';
  phoneNumber.id = 'phoneNumber';
  phoneNumber.required = true;
  phoneNumber.className = 'form-control mb-3';
  const titlePassword = document.createElement('h6');
  titlePassword.textContent = 'Password';
  const password = document.createElement('input');
  password.type = 'password';
  password.id = 'password';
  password.required = true;
  password.className = 'form-control mb-3';
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
  prof.className = 'form-check';
  prof.required = !div.hidden;
  const admin = document.createElement('input');
  admin.type = 'radio';
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
  submit.className = 'btn btn-danger';
  form.appendChild(titleFirstName);
  form.appendChild(firstname);
  form.appendChild(titleLastName);
  form.appendChild(lastname);
  form.appendChild(titleEmail);
  form.appendChild(email);
  form.appendChild(titlePhoneNumber);
  form.appendChild(phoneNumber);
  form.appendChild(titlePassword);
  form.appendChild(password);
  email.addEventListener('change', () => {
    div.hidden = !useRegex(email.value);
    prof.required = !div.hidden;
  });
  form.appendChild(div);
  form.appendChild(submit);
  main.appendChild(form);
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
  const email = document.querySelector('#email').value;
  const phoneNumber = document.querySelector('#phoneNumber').value;
  const password = document.querySelector('#password').value;

  const options = {
    method: 'POST',
    body: JSON.stringify({
      firstname,
      lastname,
      email,
      phoneNumber,
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