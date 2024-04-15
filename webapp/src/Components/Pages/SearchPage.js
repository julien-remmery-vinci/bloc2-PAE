import {getToken, isAuthenticated} from '../../utils/auths';
import {clearPage, renderBreadcrumb, renderPageTitle} from '../../utils/render';
import Navigate from '../Router/Navigate';

const fetchUsers = async () => {
  fetch('http://localhost:3000/users', {
    method: 'GET',
    headers: {
      Authorization: getToken(),
    },

  })
    .then((response) => response.json())
    .then((data) => {
      renderUsers(data);
    })
    .catch((error) => console.error(error));
};

const SearchPage = async () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    renderPageTitle('Recherche');
    document.title = 'Recherche';
    clearPage();
    renderBreadcrumb(
        {"Accueil": "/", "Liste des utilisateurs": "/search"});
    await fetchUsers();
    renderSearchPage();
  }
};

function renderSearchPage() {
  const main = document.querySelector('main');
  main.innerHTML += `
  <div class="search-container d-flex justify-content-between">
  <div class="filter-container">
    <h3>Filtres</h3>
    <label>
      <input type="checkbox" name="filter" value="etudiant">
      Que les étudiants
    </label>
  </div>

  <div class="search-bar">
    <input class="form-control border-end-0 border rounded-pill" type="search" placeholder="Rechercher">
  </div>
</div>
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
  // filter the users to only show the students
  const filter = document.querySelector('.filter-container input');
  filter.addEventListener('change', () => {
    const table = document.querySelector('table');
    const tbody = table.querySelector('tbody');
    const rows = tbody.querySelectorAll('tr');
    rows.forEach((row) => {
      const cells = row.querySelectorAll('td');
      if (filter.checked) {
        if (cells[2].textContent !== 'étudiant') {
          row.style.display = 'none';
        } else {
          row.style.display = '';
        }
      } else {
        row.style.display = '';
      }
    });
  });
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
