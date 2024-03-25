import {clearPage} from "../../../utils/render";
import {getToken, isAuthenticated} from "../../../utils/auths";
import Navigate from "../../Router/Navigate";

const StagePage = () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    clearPage();
    renderStagePage();
    document.title = "Stage";
  }
}

async function renderStagePage() {
  const stage = await getStage();
  const main = document.querySelector('main');
  main.className = 'd-flex flex-row justify-content-around align-items-center vh-100';

  // Créer les éléments HTML
  const stageSection = document.createElement('section');
  const responsibleSection = document.createElement('section');

  // Ajouter du contenu aux sections
  stageSection.innerHTML = `
    <h2>Mon stage</h2>
    <form class="p-5 w-150 bg-light rounded shadow col-md-8">
        <label for="subject" class="fw-bold mb-1">Sujet du stage:</label><br>
        <textarea id="subject" name="subject" class="form-control mb-3" rows="2">Suivre un processus de sortie d'une fonctionalité</textarea><br>
        <label for="date" class="fw-bold mb-1">Date de signature:</label><br>
        <input type="date" id="date" name="date" value="2024-03-10" class="form-control mb-3">
        <label for="supervisor" class="fw-bold mb-1">Responsable</label><br>
        <select id="supervisor" name="supervisor" class="form-control mb-3">
            <option value="responsable1" selected>Romain</option>
            <option value="responsable2" >Responsable 2</option>
            <option value="responsable3">Responsable 3</option>
        </select>
        <button type="submit" class="btn btn-primary mt-3">Sauver</button>
    </form>
`;

  responsibleSection.innerHTML = `
        <h2>Inscrire un responsable</h2>
        <form class="p-5 w-150 bg-light rounded shadow col-md-8">
            <label for="name" class="fw-bold mb-1">Nom:</label><br>
            <input type="text" id="name" name="name" class="form-control mb-3"><br>
            <label for="name" class="fw-bold mb-1">Prénom:</label><br>
            <input type="text" id="firstname" name="firstname" class="form-control mb-3"><br>
            <label for="phone" class="fw-bold mb-1">Téléphone:</label><br>
            <input type="tel" id="phone" name="phone" class="form-control mb-3"><br>
            <label for="email" class="fw-bold mb-1">Email:</label><br>
            <input type="email" id="email" name="email" class="form-control mb-3">
            <button type="submit" class="btn btn-primary mt-3">Sauver</button>
        </form>
    `;

  // Ajouter les sections à la page principale
  main.appendChild(stageSection);
  main.appendChild(responsibleSection);

}

async function getStage() {
  const response = await fetch('http://localhost:3000/internships', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getToken()
    }
  });
  return response.json();
}

export default StagePage;