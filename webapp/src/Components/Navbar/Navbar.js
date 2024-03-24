import {
  clearAuthenticatedUser,
  getUserToken
} from "../../utils/auths";
import Navigate from "../Router/Navigate";

const Navbar = () => {
  renderNavbar();
};

async function renderNavbar() {
  const notLoggedNavbar = `
    <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
      <a class="navbar-brand" href="#">VinciOBS</a>
      <div class="ms-auto">
      <button class="btn btn-primary rounded" type="button" id="login">
        Connexion
      </button>
        <button class="btn btn-primary rounded" type="button" id="register">Inscription</button>
    </div>
    </nav>
  `;

  const loggedNavbarStudent = `
  <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
    <a class="navbar-brand" href="#">VinciOBS</a>
    <div class="ms-auto">
      <button class="btn btn-primary rounded" type="button" id="button1">
        Profil
      </button>
      <button class="btn btn-primary rounded" type="button" id="contacts">
        Contacts
      </button>
        <button class="btn btn-primary rounded" type="button" id="button2">Déconnexion</button>
    </div>
  </nav>
  `;

  const loggedNavbarTeacher = `
  <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
    <a class="navbar-brand" href="#">VinciOBS</a>
    <div class="ms-auto">
      <button class="btn btn-primary rounded" type="button" id="button1">
        Profil
      </button>
      <button class="btn btn-primary rounded" type="button" id="recherches">
        Recherches
      </button>
       <button class="btn btn-primary rounded" type="button" id="responsables">
        Responsables
       </button>
       <button class="btn btn-primary rounded" type="button" id="stats">
        Statistiques
       </button>
        <button class="btn btn-primary rounded" type="button" id="button2">Déconnexion</button>
    </div>
  </nav>
  `;

  const navbar = document.querySelector('#navbarWrapper');
  if (getUserToken() === undefined) {
    navbar.innerHTML = notLoggedNavbar;
    const loginButton = document.querySelector('#login');
    const registerButton = document.querySelector('#register');
    loginButton.addEventListener('click', () => {
      Navigate('/login');
    });
    registerButton.addEventListener('click', () => {
      Navigate('/register');
    });
  } else if (getUserToken().role === 'student') {
    navbar.innerHTML = loggedNavbarStudent;
    const logOutButton = document.querySelector('#button2');
    const profileButton = document.querySelector('#button1');
    const contactsButton = document.querySelector('#contacts');
    logOutButton.addEventListener('click', () => {
      clearAuthenticatedUser();
      Navigate('/login');
      window.location.reload();
    });
    profileButton.addEventListener('click', () => {
      Navigate('/profile');
    });
    contactsButton.addEventListener('click', () => {
      Navigate('/contact');
    });
  } else if (getUserToken().role === 'teacher') {
    navbar.innerHTML = loggedNavbarTeacher;
    const logOutButton = document.querySelector('#button2');
    const profileButton = document.querySelector('#button1');
    const researchesButton = document.querySelector('#recherches');
    const responsablesButton = document.querySelector('#responsables');
    const statsButton = document.querySelector('#stats');
    logOutButton.addEventListener('click', () => {
      clearAuthenticatedUser();
      Navigate('/login');
      window.location.reload();
    });
    profileButton.addEventListener('click', () => {
      Navigate('/profile');
    });
    researchesButton.addEventListener('click', () => {
      Navigate('/research');
    });
    responsablesButton.addEventListener('click', () => {
      Navigate('/');
    });
    statsButton.addEventListener('click', () => {
      Navigate('/');
    });
  }
}

export default Navbar;
