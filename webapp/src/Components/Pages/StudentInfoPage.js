import { clearPage, renderPageTitle, renderBreadcrumb } from '../../utils/render';
import Navigate from '../Router/Navigate';
import {getToken, isAuthenticated} from '../../utils/auths';

const fetchContacts = (id) => {
  fetch(`http://localhost:3000/contacts/${id}`, {
    method: 'GET',
    headers: {
      Authorization: getToken(),
    },
  })
    .then((response) => response.json())
    .then(data => displayContacts(data));
};

const fetchInternshipByStudentId = (id) => {
  fetch(`http://localhost:3000/internships/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: getToken(),
    },
  })
    .then((response) => {
      if (response.status === 404) return null;
      return response.json();
    })
    .then(data => displayInternship(data));
};

const StudentInfoPage = (user) => {
  if (!isAuthenticated()) {
    Navigate('/login');
  }
  else if(!user) Navigate('/search')
  else {
    renderPageTitle("Informations de l'étudiant");
    document.title = "Informations de l'étudiant";
    clearPage();
    renderBreadcrumb({
      'Accueil': '/',
      'Liste des étudiants': '/students',
      'Informations de l\'étudiant': "/student-info",
    });
    buildPage(user);
  }
};

function buildPage(user) {
  const main = document.querySelector('main');
  const mainDiv = document.createElement('div');
  mainDiv.style.width = '100%';
  const infosDiv = document.createElement('div');
  infosDiv.style.display = 'flex';
  infosDiv.style.justifyContent = 'center';
  const studentInfos = document.createElement('div');
  studentInfos.id = 'studentInfos';
  studentInfos.className = 'card';
  studentInfos.style.width = '40%';
  const internship = document.createElement('div');
  internship.id = 'internship';
  internship.className = 'card';
  internship.style.width = '40%';
  infosDiv.appendChild(studentInfos);
  infosDiv.appendChild(internship);
  const contactsDiv = document.createElement('div');
  infosDiv.id = 'infosDiv';
  contactsDiv.id = 'contacts';
  contactsDiv.style.width = '100%';
  mainDiv.appendChild(infosDiv);
  mainDiv.appendChild(contactsDiv);
  mainDiv.style.height = '80vh';
  main.appendChild(mainDiv);
  displayInfos(user);
  fetchInternshipByStudentId(user.idUser);
  fetchContacts(user.idUser);
  // document.querySelector('main').innerHTML += `
  // <div class="container">
  //   <div class="row">
  //     <div class="col" id="user" style="max-width: 40%"></div>
  //     <div class="col overflow-auto" id="contactsDiv" style="max-height: 55vh">
  //       <h5>Contacts de l'étudiant</h5>
  //       <table class="table table-bordered">
  //         <thead class="table-light sticky-top">
  //           <tr>
  //             <th>Entreprise</th>
  //             <th>Etat</th>
  //           </tr>
  //         </thead>
  //         <tbody id="contacts"></tbody>
  //       </table>
  //     </div>
  //   </div>
  //   <div id="internship"></div>
  // </div>
  // `;
  // renderUser(user);
  // fetchContacts(user.idUser);
  // fetchInternshipByStudentId(user.idUser);
}

function displayInfos(user) {
  const studentInfos = document.getElementById('studentInfos');

  const title = document.createElement('h5');
  title.textContent = "Informations de l'étudiant";
  title.style.textAlign = 'center';
  studentInfos.appendChild(title);

  const div = document.createElement('div');
  div.style.display = 'flex';
  div.style.justifyContent = 'center';
  div.style.flexDirection = 'column';
  div.style.height = '100%';

  const firstname = document.createElement('p');
  firstname.textContent = user.firstname;
  firstname.style.textAlign = 'center';
  div.appendChild(firstname);

  const lastname = document.createElement('p');
  lastname.textContent = user.lastname;
  lastname.style.textAlign = 'center';
  div.appendChild(lastname);

  const email = document.createElement('p');
  email.textContent = user.email;
  email.style.textAlign = 'center';
  div.appendChild(email);

  const phoneNumber = document.createElement('p');
  phoneNumber.textContent = user.phoneNumber;
  phoneNumber.style.textAlign = 'center';
  div.appendChild(phoneNumber);

  const academicYear = document.createElement('p');
  academicYear.textContent = user.academicYear;
  academicYear.style.textAlign = 'center';
  div.appendChild(academicYear);

  studentInfos.appendChild(div);
}

function displayInternship(internship) {
  const internshipDiv = document.getElementById('internship');
  const title = document.createElement('h5');
  title.textContent = "Stage de l'étudiant";
  title.style.textAlign = 'center';
  internshipDiv.appendChild(title);
  const div = document.createElement('div');
  div.style.display = 'flex';
  div.style.justifyContent = 'center';
  div.style.flexDirection = 'column';
  div.style.height = '100%';
  if(!internship) {
    const noInternship = document.createElement('p');
    noInternship.textContent = "L'étudiant n'a pas encore de stage pour cette année";
    noInternship.style.textAlign = 'center';
    div.appendChild(noInternship);
  } else {
    const internshipProject = document.createElement('p');
    internshipProject.textContent = internship.internshipProject;
    internshipProject.style.textAlign = 'center';
    div.appendChild(internshipProject);

    const signatureDate = document.createElement('p');
    signatureDate.textContent = new Date(internship.signatureDate).toLocaleDateString();
    signatureDate.style.textAlign = 'center';
    div.appendChild(signatureDate);

    const internshipSupervisor = document.createElement('p');
    internshipSupervisor.textContent = `${internship.internshipSupervisor.firstName} ${internship.internshipSupervisor.lastName}`;
    internshipSupervisor.style.textAlign = 'center';
    div.appendChild(internshipSupervisor);
  }
  internshipDiv.appendChild(div);
}

function displayContacts(contacts) {
  const contactsDiv = document.getElementById('contacts');
  contactsDiv.style.overflow = 'auto';
  contactsDiv.style.marginTop = '20px';
  contactsDiv.style.maxHeight = '45vh';
  const table = document.createElement('table');
  table.className = 'table table-bordered';
  table.style.width = '80%';
  const thead = document.createElement('thead');
  thead.className = 'table-light sticky-top';
  const tr = document.createElement('tr');
  const th1 = document.createElement('th');
  th1.textContent = 'Entreprise';
  th1.style.width = '50%';
  const th2 = document.createElement('th');
  th2.textContent = 'Etat';
  tr.appendChild(th1);
  tr.appendChild(th2);
  thead.appendChild(tr);
  table.appendChild(thead);
  const tbody = document.createElement('tbody');
  table.appendChild(tbody);
  contactsDiv.appendChild(table);
  contacts.forEach(contact => {
    const trow = document.createElement('tr');
    const td1 = document.createElement('td');
    td1.textContent = `${contact.company.tradeName} ${contact.company.designation ? `- ${contact.company.designation}` : ''}`
    const td2 = document.createElement('td');
    td2.textContent = contact.state;
    trow.appendChild(td1);
    trow.appendChild(td2);
    tbody.appendChild(trow);
  });
}

// function renderUser(user) {
//   document.querySelector('#user').innerHTML = `
//     <h5>Informations de l'étudiant</h5>
//     <form>
//       <div class="mb-3">
//         <label for="lastname" class="form-label">Nom</label>
//         <input type="text" class="form-control rounded" id="lastname" value="${user?.lastname}" readonly>
//       </div>
//       <div class="mb-3">
//         <label for="firstname" class="form-label">Prénom</label>
//         <input type="text" class="form-control rounded" id="firstname" value="${user?.firstname}" readonly>
//       </div>
//       <div class="mb-3">
//         <label for="email" class="form-label">Email</label>
//         <input type="email" class="form-control rounded" id="email" value="${user?.email}" readonly>
//       </div>
//       <div class="mb-3">
//         <label for="phoneNumber" class="form-label">Numéro de téléphone</label>
//         <input type="tel" class="form-control rounded" id="phoneNumber" value="${user?.phoneNumber}" readonly>
//       </div>
//     </form>
//   `;
//   const lastname = document.getElementById('lastname');
//   lastname.addEventListener('click', () => {
//     displayToast('Vous ne pouvez pas modifier ce champ', 'danger');
//   });
//
//   const firstname = document.getElementById('firstname');
//   firstname.addEventListener('click', () => {
//     displayToast('Vous ne pouvez pas modifier ce champ', 'danger');
//   });
//
//   const email = document.getElementById('email');
//   email.addEventListener('click', () => {
//     displayToast('Vous ne pouvez pas modifier ce champ', 'danger');
//   });
//
//    const phoneNumber = document.getElementById('phoneNumber');
//   phoneNumber.addEventListener('click', () => {
//     displayToast('Vous ne pouvez pas modifier ce champ', 'danger');
//   });
// }

// function renderContacts(contacts) {
//   const tbody = document.querySelector('tbody');
//   // if (!contacts) return;
//   contacts.forEach((contact) => {
//     const tr = document.createElement('tr');
//     tr.innerHTML = `
//       <td>${contact.company.tradeName} - ${contact.company.designation || 'Pas d\'appellation'}</td>
//       <td>${contact.state}</td>
//     `;
//     tbody.appendChild(tr);
//   });
// }

// function renderInternship(internship) {
//   const internshipDiv = document.querySelector('#internship');
//   // const row = document.createElement('div');
//   // row.className = 'row';
//   // const div = document.createElement('div');
//   // div.className = 'd-flex flex-row justify-content-around align-items-center vh-90';
//   // div.appendChild(row);
//   // internshipDiv.appendChild(div);
//
//   const stageSection = document.createElement('section');
//   stageSection.style.marginTop = '2rem';
//   stageSection.className = 'text-center';
//
//   if (!internship) {
//     stageSection.innerHTML = `
//     <h5>Stage de l'étudiant</h5>
//     <p>L'étudiant n'a pas encore de stage pour cette année</p>
//     `;
//   } else {
//     const subjectValue =
//       internship && internship.internshipProject ? internship.internshipProject : 'Pas de sujet';
//     stageSection.innerHTML = `
//     <h5>Stage de l'étudiant</h5>
//     <div class="p-5 w-150 bg-light rounded shadow col-md-8" style="max-width: 80%;">
//       <p class="fw-bold mb-1">Sujet du stage:</p>
//       <p class="form-control mb-3">${subjectValue}</p>
//       <p class="fw-bold mb-1">Date de signature:</p>
//       <p class="form-control mb-3">${new Date(internship.signatureDate).toLocaleDateString()}</p>
//       <p class="fw-bold mb-1">Responsable:</p>
//       <p class="form-control mb-3">${internship.internshipSupervisor.firstName} ${
//       internship.internshipSupervisor.lastName
//     }</p>
//     </div>
//     `;
//   }
//   internshipDiv.appendChild(stageSection);
// }

export default StudentInfoPage;
