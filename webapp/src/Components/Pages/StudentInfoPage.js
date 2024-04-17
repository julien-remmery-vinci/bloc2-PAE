import { clearPage, renderPageTitle, displayToast } from '../../utils/render';
import Navigate from '../Router/Navigate';
import { isAuthenticated, getToken } from '../../utils/auths';

const fetchUserById = async (id) => {
  try {
    fetch(`http://localhost:3000/users/${id}`, {
      method: 'GET',
      headers: {
        Authorization: getToken(),
      },
    })
      .then((response) => response.json())
      .then((data) => {
        renderUser(data);
      })
      .catch((error) => console.error(error));
  } catch (error) {
    console.error(error);
  }
};

const fetchContacts = async (id) => {
  fetch(`http://localhost:3000/contacts/${id}`, {
    method: 'GET',
    headers: {
      Authorization: getToken(),
    },
  })
  .then(response => response.json())
  .then(data => {
    renderContacts(data);
  })
  .catch((error) => console.error(error));
};

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
    fetchContacts(id);
    renderUser();
    renderContacts();
  }
};

function renderUser(user) {
  const main = document.querySelector('main');
  main.innerHTML = `
  <div class="container">
  <div class="row">
  <div class="col">
    <h1>Informations de l'étudiant</h1>
    <form>
      <div class="mb-3">
        <label for="lastname" class="form-label">Nom</label>
        <input type="text" class="form-control rounded" id="lastname" value="${user?.lastname}" readonly>
      </div>
      <div class="mb-3">
        <label for="firstname" class="form-label">Prénom</label>
        <input type="text" class="form-control rounded" id="firstname" value="${user?.firstname}" readonly>
      </div>
      <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control rounded" id="email" value="${user?.email}" readonly>
      </div>
      <div class="mb-3">
        <label for="phoneNumber" class="form-label">Numéro de téléphone</label>
        <input type="tel" class="form-control rounded" id="phoneNumber" value="${user?.phoneNumber}" readonly>
      </div>
    </form>
      </div>
      <div class="col">
        <h1>Contacts de l'étudiant</h1>
        <table class="table table-bordered">
        <thead class="table-light">
          <tr>
            <th>Entreprise</th>
            <th>Etat</th>
          </tr>
        </thead>
        <tbody>
    </div>
  </div>
  `;
  const lastname = document.getElementById('lastname');
  lastname.addEventListener('click', () => {
    displayToast('Vous ne pouvez pas modifier ce champ', 'danger');
  });

  const firstname = document.getElementById('firstname');
  firstname.addEventListener('click', () => {
    displayToast('Vous ne pouvez pas modifier ce champ', 'danger');
  });

  const email = document.getElementById('email');
  email.addEventListener('click', () => {
    displayToast('Vous ne pouvez pas modifier ce champ', 'danger');
  });

   const phoneNumber = document.getElementById('phoneNumber');
  phoneNumber.addEventListener('click', () => {
    displayToast('Vous ne pouvez pas modifier ce champ', 'danger');
  });
}

function renderContacts(contacts) {
  const tbody = document.querySelector('tbody');
  if (!contacts) return;
  contacts.forEach((contact) => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${contact.company.tradeName} - ${contact.company.designation || 'Pas de désignation'}</td>
      <td>${contact.state}</td>
    `;
    tbody.appendChild(tr);
  });
}

export default StudentInfoPage;
