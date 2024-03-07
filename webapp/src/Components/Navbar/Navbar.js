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
    </nav>
  `;

  const loggedNavbar = `
  <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
    <a class="navbar-brand" href="#">VinciOBS</a>
    <div class="ms-auto">
      <button class="btn btn-primary rounded" type="button" id="button1">
        Profil
      </button>
        <button class="btn btn-primary rounded" type="button" id="button2">Déconnexion</button>
    </div>
  </nav>
  `;
  const navbar = document.querySelector('#navbarWrapper');
  if (getUserToken() === undefined) {
    navbar.innerHTML = notLoggedNavbar;
  } else {
    navbar.innerHTML = loggedNavbar;
    const logOutButton = document.querySelector('#button2');
    logOutButton.addEventListener('click', () => {
      clearAuthenticatedUser();
      Navigate('/login');
      window.location.reload();
    });
  }
}

export default Navbar;
