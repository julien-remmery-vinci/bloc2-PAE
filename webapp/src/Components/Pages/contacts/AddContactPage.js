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
    const entrepriseList = await getEntreprises();
    const main = document.querySelector('main');
    const title = document.createElement('h3');
    title.textContent = 'Ajouter un nouveau contact';
    title.style.textAlign = 'center';
    title.style.marginBottom = '5%';
    main.appendChild(title);
    const entreprise = document.createElement('label');
    entreprise.textContent = 'Entreprise';
    entreprise.style.marginLeft = '15%';
    main.appendChild(entreprise);
    const entreprises = document.createElement('select');
    
    entreprises.className = 'form-control';
    entreprises.style.width = '25%';
    entreprises.style.marginLeft = '15%';

    entrepriseList.forEach((e) => {
        const option = document.createElement('option');
        option.value = e.id;
        option.text = e.tradeName;
        entreprises.appendChild(option);
    });

    main.appendChild(entreprises);

    const appellation = document.createElement('label');
    appellation.textContent = 'Appellation';
    appellation.style.marginLeft = '15%';
    main.appendChild(appellation);
    const appelations = document.createElement('select');
    appelations.className = 'form-control';
    appelations.style.width = '25%';
    appelations.style.marginLeft = '15%';
    const option3 = document.createElement('option');
    option3.value = 'option3';
    option3.text = 'Option 3';
    appelations.appendChild(option3);
    const option4 = document.createElement('option');
    option4.value = 'option4';
    option4.text = 'Option 4';
    appelations.appendChild(option4);
    main.appendChild(appelations);
    
}

// fetch function to get all entreprises
async function getEntreprises() {
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

