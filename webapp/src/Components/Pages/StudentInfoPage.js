import { clearPage, renderPageTitle } from "../../utils/render";
import Navigate from "../Router/Navigate";
import isAuthenticated from "../../utils/auths"; // Import the 'isAuthenticated' function



const StudentInfoPage = () => {
    if (!isAuthenticated()) {
        Navigate('/login');
    } else {
        renderPageTitle("Informations de l'étudiant");
        document.title = "Informations de l'étudiant";
        clearPage();
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
