import { clearAuthenticatedUser , getAuthenticatedUser} from "../../utils/auths";
import Navigate from "../Router/Navigate";

const Navbar = () => {
  if (getAuthenticatedUser() === undefined) {
    return;
  }
  renderNavbar();
};

function renderNavbar() {

  const userNavbar = `<nav class="navbar navbar-expand-lg navbar-light bg-light">
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
  navbar.innerHTML = userNavbar;

  const logOutButton = document.querySelector('#button2');
  logOutButton.addEventListener('click', () => {
    clearAuthenticatedUser();
    Navigate('/login');
    window.location.reload();
});
}

export default Navbar;
