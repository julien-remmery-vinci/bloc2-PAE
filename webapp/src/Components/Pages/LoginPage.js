import { getRememberMe, isAuthenticated, setAuthenticatedUser, setRememberMe, setToken } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

// Define LoginPage component
const LoginPage = () => {
  if (isAuthenticated()) {
    Navigate('/');
  } else {
    clearPage();
    renderPageTitle('Connexion');
    document.title = "Connexion";
    renderLoginForm();
  }
};

// Function to render the login form
function renderLoginForm() {
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
  const passwordDiv = document.createElement('div');
  const passwordHtml = `
    <div class="input-group">
      <input id="password" type="password" class="form-control mb-3" name="password" required>
      <button class="btn btn-outline-secondary mb-3" type="button" id="hidePassword">
         <i class="bi bi-eye"></i>
      </button>
    </div>
  `;
  passwordDiv.appendChild(titlePassword);
  passwordDiv.innerHTML += passwordHtml;
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

  const register = document.createElement('p');
  register.textContent = 'Pas encore de compte ? ';
  register.className = 'd-block text-center mt-3';
  const link = document.createElement('a');
  link.textContent = 'Inscrivez-vous';
  link.href = '/register';

  formCheckWrapper.appendChild(rememberme);
  formCheckWrapper.appendChild(checkLabel);

  form.appendChild(titleEmail);
  form.appendChild(username);
  form.appendChild(passwordDiv);
  form.appendChild(formCheckWrapper);
  form.appendChild(submit);
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
  main.appendChild(register);
  register.appendChild(link);
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
    method: 'POST',
    body: JSON.stringify({
      email,
      password,
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  // verify if email is valid
  const emailRegex = /^[a-zA-Z0-9._%+-]+\.[a-zA-Z0-9._%+-]+@(vinci\.be|student\.vinci\.be)$/;
  if (!emailRegex.test(email)) {
    // clear the last error message
    const form1 = document.querySelector('form');
    const lastError = form1.querySelector('.text-danger');
    if (lastError) {
      form1.removeChild(lastError);
    }
    const erreur = document.createElement('p');
    erreur.textContent = 'Email invalide';
    erreur.className = 'text-danger';
    const form = document.querySelector('form');
    form.appendChild(erreur);
    return;
  }

  const response = await fetch(`http://localhost:3000/auths/login`, options);

  if (response.status === 401) {
    // clear the last error message
    const form1 = document.querySelector('form');
    const lastError = form1.querySelector('.text-danger');
    if (lastError) {
      form1.removeChild(lastError);
    }
    const erreur = document.createElement('p');
    erreur.textContent = 'Email ou mot de passe invalide';
    erreur.className = 'text-danger';
    const form = document.querySelector('form');
    form.appendChild(erreur);
    return;
  }

  if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText} `);

  // Get the authenticated user
  const authenticatedUser = await response.json();

  console.log('Authenticated user : ', authenticatedUser);

  setToken(authenticatedUser.token);
  setAuthenticatedUser(authenticatedUser.user);

  // Navigate to the home page if the user is authenticated, otherwise navigate to the login page
  if (authenticatedUser) {
    Navbar();
    Navigate('/');
  } else {
    Navigate('/login');
  }
};

export default LoginPage;