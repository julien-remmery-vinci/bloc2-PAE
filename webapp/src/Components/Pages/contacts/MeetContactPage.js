import {
  getToken,
  isAuthenticated
} from '../../../utils/auths';
import { clearPage, renderPageTitle } from '../../../utils/render';
import Navigate from '../../Router/Navigate';

const MeetContactPage = () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    clearPage();
    document.title = "Rencontrer un contact";
    renderPageTitle('Rencontre avec un contact');
    renderMeetContactPage();
  }
};

function renderMeetContactPage() {
  const main = document.querySelector('main');
  main.innerHTML = `
      <form class="container mt-5">
        <div class="mb-3">
          <label for="entreprise" class="form-label">Entreprise</label>
          <input type="text" class="form-control" id="entreprise" name="entreprise" value="Nom de l'entreprise" readonly>
        </div>
        <div class="mb-3">
          <label for="appellation" class="form-label">Appellation</label>
          <input type="text" class="form-control" id="appellation" name="appellation" value="Appellation par défaut" readonly>
        </div>
        <div class="mb-3">
          <label for="lieu" class="form-label">Lieu de rencontre</label>
          <input type="text" class="form-control" id="lieu" name="lieu" required>
        </div>
        <button type="submit" class="btn btn-primary">Soumettre</button>
      </form>
    `;
    const form = document.querySelector('form');
    form.addEventListener('submit', submitFunc);
}

async function submitFunc (event) {
  event.preventDefault();
  const form = event.target;
  const formData = new FormData(form);
  const lieu = formData.get('lieu');
  const contactId = window.location.pathname.split('/').pop();
  try {
    const response = await fetch(`http://localhost:3000/contact/${contactId}/meet`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: getToken(),
      },
      body: JSON.stringify({ lieu }),
    });
    if (response.ok) {
      Navigate('/contacts');
    } else {
      const error = await response.json();
      console.error(error);
    }
  } catch (error) {
    console.error(error);
  }
}

export default MeetContactPage;
