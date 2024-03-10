const SearchPage = () => {
  renderSearchPage();
};

function renderSearchPage() {
  const main = document.querySelector('main');
  main.innerHTML = `
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
