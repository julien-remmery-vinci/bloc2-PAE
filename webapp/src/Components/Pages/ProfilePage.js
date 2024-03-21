import {clearPage} from "../../utils/render";
import {getAuthenticatedUser} from "../../utils/auths";
import Navigate from "../Router/Navigate";

const ProfilePage = async () => {
  const authenticatedUser = getAuthenticatedUser();
  if (!authenticatedUser) {
    Navigate('/login');
    window.location.reload();
  } else {
    clearPage();
    document.title = "Profil";
    renderProfilPage();
  }
}

function renderProfilPage() {
  // Votre code existant
  const main = document.querySelector('main');
  const DocumentTitle = document.createElement('h3');
  DocumentTitle.textContent = 'Profil';
  DocumentTitle.style.textAlign = 'center';
  main.appendChild(DocumentTitle);
  main.className = 'd-flex flex-column justify-content-center align-items-center vh-100';
  const form = document.createElement('form');
  const authenticatedUser = getAuthenticatedUser();
  form.className = 'p-5 w-50 bg-light rounded shadow';
  form.className = 'mx-auto p-5 w-50 position-relative float-end bg-light rounded shadow';
  const fields = ['lastname', 'firstname', 'email', 'phoneNumber'];
  const labels = ['Lastname', 'Firstname', 'Email', 'Numéro de téléphone'];

  fields.forEach((field, index) => {
    const title = document.createElement('h6');
    title.textContent = labels[index];
    title.className = 'fw-bold mb-1';

    const input = document.createElement('input');
    input.readOnly = true;
    input.id = field;
    input.value = `${authenticatedUser[field]}`;
    input.className = 'form-control mb-3';

    form.appendChild(title);
    form.appendChild(input);
  });

  const changePasswordButton = document.createElement('button');
  changePasswordButton.textContent = 'Modifier mot de passe';
  changePasswordButton.className = 'position-absolute bottom-0 start-50 translate-middle-x btn btn-primary';
  form.appendChild(changePasswordButton);
  main.appendChild(form);

// Ajout de l'écouteur d'événements
  changePasswordButton.addEventListener('click', event => {
    event.preventDefault();  // Empêche l'action par défaut du bouton

    // Cache le bouton "Modifier mot de passe"
    changePasswordButton.style.display = 'none';

    // Création du formulaire de modification de mot de passe
    const passwordForm = document.createElement('form');
    passwordForm.className = 'p-5 w-50 bg-light rounded shadow';
    passwordForm.className = 'mx-auto p-5 w-50 position-relative float-end bg-light rounded shadow';


    // Ajout des champs du formulaire
    const oldPasswordField = document.createElement('input');
    oldPasswordField.type = 'password';
    oldPasswordField.placeholder = 'Ancien mot de passe';
    oldPasswordField.className = 'form-control mb-3';
    passwordForm.appendChild(oldPasswordField);

    // Ajout des champs du formulaire
    const passwordField = document.createElement('input');
    passwordField.type = 'password';
    passwordField.placeholder = 'Nouveau mot de passe';
    passwordField.className = 'form-control mb-3';
    passwordForm.appendChild(passwordField);

    const confirmPasswordField = document.createElement('input');
    confirmPasswordField.type = 'password';
    confirmPasswordField.placeholder = 'Confirmer le nouveau mot de passe';
    confirmPasswordField.className = 'form-control mb-3';
    passwordForm.appendChild(confirmPasswordField);

    // Ajout du bouton de soumission du formulaire
    const submitButton = document.createElement('button');
    submitButton.type = 'submit';
    submitButton.textContent = 'Sauver';
    submitButton.className = 'btn btn-primary';
    passwordForm.appendChild(submitButton);

    // Ajout du bouton d'annulation
    const cancelButton = document.createElement('button');
    cancelButton.type = 'button';
    cancelButton.textContent = 'Annuler';
    cancelButton.className = 'btn btn-secondary ms-2';
    cancelButton.addEventListener('click', () => {
      passwordForm.remove();  // Supprime le formulaire de modification de mot de passe
      changePasswordButton.style.display = 'block';  // Affiche à nouveau le bouton "Modifier mot de passe"
    });
    passwordForm.appendChild(cancelButton);

    // Ajout du formulaire à la page
    main.appendChild(passwordForm);
  });
}

export default ProfilePage;