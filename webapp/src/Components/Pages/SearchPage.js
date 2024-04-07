import { clearPage, renderPageTitle } from '../../utils/render';
import { isAuthenticated } from '../../utils/auths';
import Navigate from '../Router/Navigate';

const fetchUsers = async () => {
  fetch('http://localhost:3000/users')
    .then((response) => response.json())
    .then((data) => {
      renderUsers(data);
    })
    .catch((error) => console.error(error));
};

const SearchPage = () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    renderPageTitle('Recherche');
    document.title = 'Recherche';
    clearPage();
    fetchUsers();
    renderSearchPage();
  }
};

function renderSearchPage() {
  const main = document.querySelector('main');
  main.innerHTML = `
<div class="container">
<div class="row">
  <div class="col">
    <h1>Recherche</h1>
</div>
<div class="row">
<div class="col-md-4">
  <div class="search-container d-flex justify-content-between">
  <div class="filter-container">
    <h3>Filtres</h3>
    <label>
      <input type="checkbox" id="studentFilter" name="filter" value="etudiant">
      Que les étudiants
    </label>
  </div>
</div>
</div>
<div class="col-md-4 ms-auto">
  <div class="search-bar">
    <input class="form-control rounded rounded-pill" type="search" value="Rechercher">
  </div>
</div>
<br>
</div>
</div>
<br>
<br>
    <div class="table-responsive d-flex justify-content-center align-items-center">
    <table class="table table-bordered table-hover caption-top">
    <caption>Liste des utilisateurs</caption>
    <thead class="table-light">
        <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Rôle</th>
            <th>Année académique</th>
            <th>Stage accepté</th>
        </tr>
    </thead>
    </table>
    </div>
`;
}

function renderUsers(users) {
  const table = document.querySelector('table');
  const tbody = document.createElement('tbody');
  users.forEach((userMap) => {
    const { user, accepted_contact: acceptedContact } = userMap;
    const tr = document.createElement('tr');
    if (user.role === 'étudiant') {
      tr.innerHTML = `
      <td>${user.lastname}</td>
      <td>${user.firstname}</td>
      <td>${user.role}</td>
      <td>${user.academicYear}</td>
      <td>${acceptedContact ? 'Oui' : 'Non'}</td>
    `;
    } else {
      tr.innerHTML = `
        <td>${user.lastname}</td>
        <td>${user.firstname}</td>
        <td>${user.role}</td>
        <td class="table-secondary">N/A</td>
        <td class="table-secondary">N/A</td>
      `;
    }

    tr.addEventListener('click', () => {
      if (user.role === 'étudiant') {
        Navigate(`/student-info?id=${user.idUser}`);
      }
    });

    tbody.appendChild(tr);
  });
  table.appendChild(tbody);
}

export default SearchPage;
