import { getRememberMe, setAuthenticatedUser, setRememberMe } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navigate from '../Router/Navigate';

// Define LoginPage component
const LoginPage = () => {
  clearPage();
  renderPageTitle('Connexion');
  renderRegisterForm();
};

// Function to render the registration form
function renderRegisterForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.className = 'p-5';
  const titleEmail = document.createElement('h6');
  titleEmail.textContent = 'Email';
  const username = document.createElement('input');
  username.type = 'text';
  username.id = 'username';
  username.required = true;
  username.className = 'form-control mb-3';
  const titlePassword = document.createElement('h6');
  titlePassword.textContent = 'Mot de passe';
  const password = document.createElement('input');
  password.type = 'password';
  password.id = 'password';
  password.required = true;
  password.className = 'form-control mb-3';
  const submit = document.createElement('input');
  submit.value = 'Se connecter';
  submit.type = 'submit';
  submit.className = 'btn btn-primary';

  const formCheckWrapper = document.createElement('div');
  formCheckWrapper.className = 'mb-3 form-check';

  const rememberme = document.createElement('input');
  rememberme.type = 'checkbox';
  rememberme.className = 'form-check-input';
  rememberme.id = 'rememberme';
  const remembered = getRememberMe();
  rememberme.checked = remembered;
  rememberme.addEventListener('click', onCheckboxClicked);

  const checkLabel = document.createElement('label');
  checkLabel.htmlFor = 'rememberme';
  checkLabel.className = 'form-check-label';
  checkLabel.textContent = 'Se souvenir de moi';

  formCheckWrapper.appendChild(rememberme);
  formCheckWrapper.appendChild(checkLabel);

  form.appendChild(titleEmail);
  form.appendChild(username);
  form.appendChild(titlePassword);
  form.appendChild(password);
  form.appendChild(formCheckWrapper);
  form.appendChild(submit);
  main.appendChild(form);
  form.addEventListener('submit', onLogin);
}

// Function to handle checkbox click event
function onCheckboxClicked(e) {
  setRememberMe(e.target.checked);
}

// Async function to handle form submission
async function onLogin(e) {
  e.preventDefault();

  const email = document.querySelector('#username').value;
  const password = document.querySelector('#password').value;

  const options = {
    node: 'no-cors',
    Credentials: 'true',
    method: 'POST',
    body: JSON.stringify({
      email,
      password,
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch(`/api/auths/login`, options);

  if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`);

  // Get the authenticated user
  const authenticatedUser = await response.json();

  console.log('Authenticated user : ', authenticatedUser);

  setAuthenticatedUser(authenticatedUser);

  // Navigate to the home page if the user is authenticated, otherwise navigate to the login page
  if (authenticatedUser) {
    Navigate('/');
  } else {
    Navigate('/login');
  }
};

export default LoginPage;