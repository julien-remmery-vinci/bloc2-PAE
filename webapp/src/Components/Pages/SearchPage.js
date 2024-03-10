import { clearPage, renderPageTitle } from "../../utils/render";

const SearchPage = () => {
  renderPageTitle("Recherche");
  clearPage();
  renderSearchPage();
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
}

export default SearchPage;
