import { getAuthenticatedUser } from '../../../utils/auths';
import { clearPage, renderPageTitle } from '../../../utils/render';
import Navigate from '../../Router/Navigate';

const MeetContactPage = () => {
  const authenticatedUser = getAuthenticatedUser();
  if (!authenticatedUser) {
    Navigate('/login');
    window.location.reload();
  } else {
    clearPage();
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
}

export default MeetContactPage;
