import { clearPage, renderPageTitle } from "../../utils/render";
import Navigate from "../Router/Navigate";
import {isAuthenticated, getToken} from "../../utils/auths";

const fetchUserById = async (id) => {
  try {
    fetch(`http://localhost:3000/users/${id}`)
      .then(response => response.json())
      .then(data => {
        renderUser(data);
      })
      .catch((error) => console.error(error));
  } catch (error) {
    console.error(error);
  }
};

async function getContacts() {
  const response = await fetch('http://localhost:3000/contacts', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getToken()
    }
  });
  return response.json();
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
      renderUser();
      getContacts().then(contacts => renderContacts(contacts));
  }
};


function renderUser(user) {
  const main = document.querySelector('main');
  main.innerHTML = `
  <div class="container">
    <div class="row">
      <div class="col">
        <h1>Informations de l'étudiant</h1>
        <p>Voici les informations de l'étudiant</p>
        <p>Nom: ${user?.lastname}</p>
        <p>Prénom: ${user?.firstname}</p>
        <p>Email: ${user?.email}</p>
        <p>Numéro de téléphone: ${user?.phoneNumber}</p>
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
};

function renderContacts(contacts) {
  const tbody = document.querySelector('tbody');
  contacts.forEach(contact => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${contact.company.tradeName} - ${contact.company.designation || 'Pas de désignation'}</td>
      <td>${contact.state}</td>
    `;
    tbody.appendChild(tr);
  });
}

export default StudentInfoPage;
