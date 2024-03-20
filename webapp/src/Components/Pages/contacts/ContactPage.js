import {clearPage} from "../../../utils/render";
import {getAuthenticatedUser, getUserToken} from "../../../utils/auths";
import Navigate from "../../Router/Navigate";

const ContactPage = () => {
  const authenticatedUser = getAuthenticatedUser();
  if (!authenticatedUser) {
    Navigate('/login');
    window.location.reload();
  } else {
    clearPage();
    buildPage();
  }
}

function buildPage() {
  const tableBody = document.createElement('tbody');
  const contacts = getContacts();
  contacts.forEach(contact => {
    console.log(contact);
    const row = document.createElement('tr');
    const companyCell = document.createElement('td');
    companyCell.textContent = contact.company;
    const stateCell = document.createElement('td');
    stateCell.textContent = contact.state;
    const notFollowCell = document.createElement('td');
    const notFollowButton = document.createElement('button');
    notFollowButton.textContent = 'Ne plus suivre';

    row.appendChild(companyCell);
    row.appendChild(stateCell);
    row.appendChild(notFollowCell);
    tableBody.appendChild(row);
  });
}

async function getContacts() {
  const response = await fetch('http://localhost:3000/contact', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getUserToken()
    }
  });
  return response.json();
}

export default ContactPage;