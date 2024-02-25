import { getAuthenticatedUser } from '../../utils/auths';

const Navbar = () => {
  renderNavbar();
};

function renderNavbar() {
  const authenticatedUser = getAuthenticatedUser();

  const userNavbar = `<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">VinciOBS</a>
    <div class="ml-auto">
    <div class="dropdown">
      <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        ${authenticatedUser?.username}
      </button>
      <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
        <button class="dropdown-item" type="button">Déconnexion</button>
      </div>
      </div>
    </div>
  </nav>
  `;

  const navbar = document.querySelector('#navbarWrapper');
  navbar.innerHTML = userNavbar;
}

export default Navbar;
