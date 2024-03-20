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

    const defaultOption = document.createElement('option');
    defaultOption.text = 'Choisissez votre entreprise';
    defaultOption.value = '';
    companies.appendChild(defaultOption);

    const companyNames = [...new Set(companyList.map(e => e.tradeName))]; // Suppression des doublons
    companyNames.forEach(name => {
        const option = document.createElement('option');
        option.value = name;
        option.text = name;
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

    main.appendChild(designations);

    companies.addEventListener('change', (e) => {
        while (designations.firstChild) {
            designations.removeChild(designations.firstChild);
        }

        const selectedCompany = companyList.find((c) => c.tradeName === e.target.value);
        
        if (e.target.value === '') {
            const defaultOptionDesignation = document.createElement('option');
            defaultOptionDesignation.text = "Choisissez d'abord votre entreprise";
            defaultOptionDesignation.value = '';
            designations.appendChild(defaultOptionDesignation);
        }
        else
        if (selectedCompany && selectedCompany.designation!==null) {
            const designationList = companyList.filter((c) => c.tradeName === e.target.value);
            designationList.forEach(name => {
                const option = document.createElement('option');
                option.value = name;
                option.text = name.designation;
                designations.appendChild(option);
            });
        }
        else if (selectedCompany.designation===null) {
            const noDesignation = document.createElement('option');
            noDesignation.textContent = 'Aucune appellation';
            designations.appendChild(noDesignation);
        }

    });

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
}

export default AddContactPage;

