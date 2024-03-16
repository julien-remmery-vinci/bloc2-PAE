import {clearPage} from "../../../utils/render";
import {getAuthenticatedUser} from "../../../utils/auths";
import Navigate from "../../Router/Navigate";

const AddContactPage = () => {
    const authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser) {
        Navigate('/login');
        window.location.reload();
    } else {
        clearPage();
        buildPage();
    }
}

async function buildPage(){
    const companyList = await getCompanies();
    const main = document.querySelector('main');
    const title = document.createElement('h3');
    title.textContent = 'Ajouter un nouveau contact';
    title.style.textAlign = 'center';
    title.style.marginBottom = '5%';
    main.appendChild(title);
    const company = document.createElement('label');
    company.textContent = 'Entreprise';
    company.style.marginLeft = '15%';
    main.appendChild(company);
    const companies = document.createElement('select');
    
    companies.className = 'form-control';
    companies.style.width = '25%';
    companies.style.marginLeft = '15%';

    companyList.forEach((e) => {
        const option = document.createElement('option');
        option.value = e.id;
        option.text = e.tradeName;
        companies.appendChild(option);
    });

    main.appendChild(companies);

    const designation = document.createElement('label');
    designation.textContent = 'Appellation';
    designation.style.marginLeft = '15%';
    main.appendChild(designation);
    const designations = document.createElement('select');
    designations.className = 'form-control';
    designations.style.width = '25%';
    designations.style.marginLeft = '15%';
    const option3 = document.createElement('option');
    option3.value = 'option3';
    option3.text = 'Option 3';
    designations.appendChild(option3);
    const option4 = document.createElement('option');
    option4.value = 'option4';
    option4.text = 'Option 4';
    designations.appendChild(option4);
    main.appendChild(designations);
    
}

// fetch function to get all entreprises
async function getCompanies() {
    const response = await fetch('http://localhost:3000/company', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getAuthenticatedUser().token,
        },
    });
    if (response.status === 200) {
        return response.json();
    }
    return undefined;
}

export default AddContactPage;

