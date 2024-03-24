import {
  getAuthenticatedUser, isAuthenticated,
} from "../../utils/auths";

const Navbar = () => {
  renderNavbar();
};

async function renderNavbar() {
  const defaultNavbar = `
    <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
      <a class="navbar-brand" href="#">VinciOBS</a>
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/login">Login</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/register">Register</a>
        </li>           
      </ul>
    </nav>
  `;

  const studentNavbar = `
    <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
      <a class="navbar-brand" href="#">VinciOBS</a>
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/profile">Profil</a>
        </li>      
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/contact">Contacts</a>
        </li>    
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/logout">Déconnexion</a>
        </li>       
      </ul>
    </nav>
  `;

  const teacherNavbar = `
    <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
      <a class="navbar-brand" href="#">VinciOBS</a>
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/profile">Profil</a>
        </li>      
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/contact">Recherches</a>
        </li>    
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/logout">Responsables</a>
        </li>     
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/logout">Statistiques</a>
        </li>     
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/logout">Déconnexion</a>
        </li>    
      </ul>
    </nav>
  `;

  const adminNavbar = `
    <nav class="navbar navbar-expand-lg navbar-light bg-primary-subtle">
      <a class="navbar-brand" href="#">VinciOBS</a>
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/">Home</a>
        </li>  
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#" data-uri="/logout">Déconnexion</a>
        </li>    
      </ul>
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
}

export default Navbar;
