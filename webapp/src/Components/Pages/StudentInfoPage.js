import { clearPage, renderPageTitle } from "../../utils/render";
import Navigate from "../Router/Navigate";
import {isAuthenticated} from "../../utils/auths"; // Import the 'isAuthenticated' function

const renderUser = (user) => {
  const main = document.querySelector('main');
  main.innerHTML = `
  <div class="container">
    <div class="row">
      <div class="col">
        <h1>Informations de l'étudiant</h1>
        <p>Voici les informations de l'étudiant</p>
        <p>Nom: ${user.name}</p>
        <p>Prénom: ${user.firstname}</p>
        <p>Email: ${user.email}</p>
        <p>Role: ${user.role}</p>
      </div>
    </div>
  </div>
  `;
}

const fetchUserById = (id) => {
  fetch(`http://localhost:3000/users${id}`)
  .then(response => response.json())
  .then(user => renderUser(user))
  .catch(error => console.error(error));
}

const StudentInfoPage = () => {
    if (!isAuthenticated()) {
        Navigate('/login');
    } else {
        renderPageTitle("Informations de l'étudiant");
        document.title = "Informations de l'étudiant";
        clearPage();
        const urlParams = new URLSearchParams(window.location.search);
        const id = urlParams.get('id');
        fetchUserById(id);
        renderStudentInfoPage();
    } 
};

function renderStudentInfoPage() {
  const main = document.querySelector('main');
  main.innerHTML = `
  <div class="container">
    <div class="row">
      <div class="col">
        <h1>Informations de l'étudiant</h1>
        <p>Voici les informations de l'étudiant</p>
      </div>
    </div>
  </div>
  `;
}

export default StudentInfoPage;
