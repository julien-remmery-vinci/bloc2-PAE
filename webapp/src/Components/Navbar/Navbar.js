import {
  getAuthenticatedUser, isAuthenticated,
} from "../../utils/auths";
import logo2 from "../../img/logo2.png";

const Navbar = () => {
  renderNavbar();
};

async function renderNavbar() {
  const defaultNavbar = `
    <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
        <div class="container-fluid">
            <a class="navbar-brand" href="#" id="logo"></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/login">Se connecter</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/register">S'enregistrer</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
`;

  const studentNavbar = `
    <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
        <div class="container-fluid">
            <a class="navbar-brand" href="#" id="logo"></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/profile">Profil</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/stage">Stage</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/contact">Contacts</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/logout">Déconnexion</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
  `;

  const teacherNavbar = `
    <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
        <div class="container-fluid">
            <a class="navbar-brand" href="#" id="logo"></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/profile">Profil</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/search">Recherches</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/intership-supervisor">Responsables</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/dashboard">Statistiques</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/logout">Déconnexion</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
`;

  const adminNavbar = `
    <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
        <div class="container-fluid">
            <a class="navbar-brand" href="#" id="logo"></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/profile">Profil</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/search">Recherches</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-primary me-2 text-white" aria-current="page" href="#" data-uri="/logout">Déconnexion</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
`;

  const navbar = document.querySelector('#navbarWrapper');
  if(!isAuthenticated()) navbar.innerHTML = defaultNavbar;
  else {
    const userRole = getAuthenticatedUser().role;
    if(userRole === 'étudiant') navbar.innerHTML = studentNavbar;
    else if(userRole === 'professeur') navbar.innerHTML = teacherNavbar;
    else if(userRole === 'administratif') navbar.innerHTML = adminNavbar;
  }

    const logo = document.querySelector('#logo');
    logo.innerHTML = `<img src="${logo2}" alt="logo" width=30% height=30%>`;
}

export default Navbar;
