import { clearPage, renderPageTitle } from "../../utils/render";
import {isAuthenticated} from "../../utils/auths";
import Navigate from "../Router/Navigate";

const fetchUsers = async () => {
  try {
    fetch("http://localhost:3000/users")
      .then(response => response.json())
      .then(data => {
        renderUsers(data);
      })
      .catch((error) => console.error(error));
  } catch (error) {
    console.error(error);
  }
};

const SearchPage = () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    renderPageTitle("Recherche");
    document.title = "Recherche";
    clearPage();
    fetchUsers();
    renderSearchPage();
  }
};

function renderSearchPage() {
  const main = document.querySelector('main');
  main.innerHTML = `
  <div class="search-container d-flex justify-content-between">
  <div class="filter-container">
    <h3>Filtres</h3>
    <label>
      <input type="checkbox" name="filter" value="etudiant">
      Que les étudiants
    </label>
  </div>

  <div class="search-bar">
    <input class="form-control border-end-0 border rounded-pill" type="search" value="Rechercher">
  </div>
</div>
<br>
    <div class="table-responsive d-flex justify-content-center align-items-center">
    <table class="table table-bordered table-hover">
    <thead>
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
};

function renderUsers(users) {
  const table = document.querySelector('table');
  const tbody = document.createElement('tbody');
  users.forEach(userMap => {
    const { user, accepted_contact: acceptedContact } = userMap;
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${user.lastname}</td>
      <td>${user.firstname}</td>
      <td>${user.role}</td>
      <td>${user.academicYear}</td>
      <td>${acceptedContact ? 'Oui' : 'Non'}</td>
    `;
    tbody.appendChild(tr);
  });
  table.appendChild(tbody);
};

export default SearchPage;
