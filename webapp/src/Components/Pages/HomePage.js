import {clearPage, renderBreadcrumb} from "../../utils/render";
import {getAuthenticatedUser, isAuthenticated} from "../../utils/auths";
import Navigate from "../Router/Navigate";

const HomePage = () => {
    if (!isAuthenticated()) {
        Navigate('/login');
    } else {
        clearPage();
        document.title = "Home";
        renderBreadcrumb({"Accueil": "/"})
        if (getAuthenticatedUser().role === 'étudiant') {
            studentHomePage();
        } else if (getAuthenticatedUser().role === 'professeur') {
            teacherHomePage();
        } else if (getAuthenticatedUser().role === 'administratif') {
            adminHomePage();
        }
    }
};

/**
 * Render the student home page which contains buttons to access to the internship page or the contacts page
 */
function studentHomePage() {
    const main = document.querySelector('main');

    const title = document.createElement('h1');
    title.innerText = "Bienvenue sur votre espace étudiant";
    title.className = "text-center";
    main.appendChild(title);

    const internship = document.createElement('div');
    internship.className = 'card';
    const text = document.createElement('div');
    text.className = 'card-body';
    text.textContent = 'Vous voulez consulter votre stage ?';
    internship.appendChild(text);
    const button = document.createElement('button');
    button.className = 'btn btn-primary';
    button.textContent = 'Accéder à votre stage';
    internship.appendChild(button);
    internship.style.width = '18rem';
    main.appendChild(internship);
    internship.addEventListener('click', () => {
        Navigate('/stage');
    });

}

function teacherHomePage() {
    const main = document.querySelector('main');
    main.innerHTML += `
    <div class="search-container d-flex justify-content-between">
    <div class="filter-container">
        <h3>Filtres</h3>
        <label>
            <input type="checkbox" name="filter" value="etudiant">
            Que les étudiants
        </label>
        <br>
        <label>
            Année académique
            <select>
                <option value="all">Toutes les années</option>
            </select>
        </label>
    </div>

    <div class="search-bar">
        <input class="form-control border-end-0 border rounded-pill" type="search" placeholder="Rechercher">
    </div>`;
}

function adminHomePage() {
    const main = document.querySelector('main');
    main.innerHTML += `
    <div class="search-container d-flex justify-content-between">
    <div class="filter-container">
        <h3>Filtres</h3>
        <label>
            <input type="checkbox" name="filter" value="etudiant">
            Que les étudiants
        </label>
        <br>
        <label>
            Année académique
            <select>
                <option value="all">Toutes les années</option>
            </select>
        </label>
    </div>

    <div class="search-bar">
        <input class="form-control border-end-0 border rounded-pill" type="search" placeholder="Rechercher">
    </div>`;
}

export default HomePage;