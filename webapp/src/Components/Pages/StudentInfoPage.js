import { clearPage, renderPageTitle, displayToast } from '../../utils/render';
import Navigate from '../Router/Navigate';
import { isAuthenticated, getToken } from '../../utils/auths';

const fetchUserById = async (id) => {
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
};

const fetchContacts = async (id) => {
  fetch(`http://localhost:3000/contacts/${id}`, {
    method: 'GET',
    headers: {
      Authorization: getToken(),
    },
  })
    .then((response) => response.json())
    .then((data) => {
      renderContacts(data);
    })
};

const fetchInternshipByStudentId = async (id) => {
  fetch(`http://localhost:3000/internships/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: getToken(),
    },
  })
    .then((response) => {
      if (response.status === 404) return renderInternship(null);
      return response.json();
    })
    .then((data) => {
      renderInternship(data);
    })
};

const StudentInfoPage = () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    renderPageTitle("Informations de l'étudiant");
    document.title = "Informations de l'étudiant";
    clearPage();
    renderBreadcrumb({
      'Accueil': '/',
      'Liste des étudiants': '/students',
      "Informations de l'étudiant": "/student-info",
    });
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    fetchUserById(id);
    fetchContacts(id);
    fetchInternshipByStudentId(id);
    renderUser();
    renderContacts();
    renderInternship();
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

function renderInternship(internship) {
  const main = document.querySelector('main');
  const row = document.createElement('div');
  row.className = 'row';
  const div = document.createElement('div');
  div.className = 'd-flex flex-row justify-content-around align-items-center vh-90';
  div.appendChild(row);
  main.appendChild(div);

  const existingSection = document.querySelector('section');
  if (existingSection !== null) main.removeChild(existingSection);
  const stageSection = document.createElement('section');
  stageSection.className = 'text-center';

  if (!internship) {
    stageSection.innerHTML = `
    <h2>Stage de l'étudiant</h2>
    <p>L'étudiant n'a pas encore de stage pour cette année</p>
    `;
  } else {
    const subjectValue =
      internship && internship.internshipProject ? internship.internshipProject : 'Pas de sujet';
    stageSection.innerHTML = `
    <h2>Stage de l'étudiant</h2>
    <div class="p-5 w-150 bg-light rounded shadow col-md-8" style="max-width: 80%;">
      <p class="fw-bold mb-1">Sujet du stage:</p>
      <p class="form-control mb-3">${subjectValue}</p>
      <p class="fw-bold mb-1">Date de signature:</p>
      <p class="form-control mb-3">${new Date(internship.signatureDate).toLocaleDateString()}</p>
      <p class="fw-bold mb-1">Responsable:</p>
      <p class="form-control mb-3">${internship.internshipSupervisor.firstName} ${
      internship.internshipSupervisor.lastName
    }</p>
    </div>
    `;
  }
  main.appendChild(stageSection);
}

export default StudentInfoPage;
