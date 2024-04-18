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

function studentHomePage() {
    const main = document.querySelector('main');

    const title = document.createElement('h1');
    title.innerText = "Bienvenue sur votre espace étudiant";
    title.className = "text-center";
    main.appendChild(title);

    const internship = document.createElement('div');
    internship.className = 'card';
    internship.style.marginLeft = '700px';
    internship.style.marginTop = '100px';
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

    const contacts = document.createElement('div');
    contacts.className = 'card';
    contacts.style.marginLeft = '700px';
    contacts.style.marginTop = '100px';
    const text2 = document.createElement('div');
    text2.className = 'card-body';
    text2.textContent = 'Vous voulez consulter vos contacts ?';
    contacts.appendChild(text2);
    const button2 = document.createElement('button');
    button2.className = 'btn btn-primary';
    button2.textContent = 'Accéder à vos contacts';
    contacts.appendChild(button2);
    contacts.style.width = '18rem';
    main.appendChild(contacts);
    contacts.addEventListener('click', () => {
        Navigate('/contact');
    });

}

/**
 * Function to display the teacher home page with buttons to see stats and students
 */
function teacherHomePage() {
    const main = document.querySelector('main');
    const title = document.createElement('h1');
    title.innerText = "Bienvenue sur votre espace professeur";
    title.className = "text-center";
    main.appendChild(title);

    const stats = document.createElement('div');
    stats.className = 'card';
    stats.style.marginLeft = '700px';
    stats.style.marginTop = '100px';
    const text = document.createElement('div');
    text.className = 'card-body';
    text.textContent = 'Vous voulez consulter les statistiques ?';
    stats.appendChild(text);
    const button = document.createElement('button');
    button.className = 'btn btn-primary';
    button.textContent = 'Accéder aux statistiques';
    stats.appendChild(button);
    stats.style.width = '18rem';
    main.appendChild(stats);
    stats.addEventListener('click', () => {
        Navigate('/dashboard');
    });

    const students = document.createElement('div');
    students.className = 'card';
    students.style.marginLeft = '700px';
    students.style.marginTop = '100px';
    const text2 = document.createElement('div');
    text2.className = 'card-body';
    text2.textContent = 'Vous voulez consulter les étudiants ?';
    students.appendChild(text2);
    const button2 = document.createElement('button');
    button2.className = 'btn btn-primary';
    button2.textContent = 'Accéder aux étudiants';
    students.appendChild(button2);
    students.style.width = '18rem';
    main.appendChild(students);
    students.addEventListener('click', () => {
        Navigate('/student-info');
    });
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