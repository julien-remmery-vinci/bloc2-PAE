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

function buildPage(){
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

    const option1 = document.createElement('option');
    option1.value = 'option1';
    option1.text = 'Option 1';
    entreprises.appendChild(option1);

    const option2 = document.createElement('option');
    option2.value = 'option2';
    option2.text = 'Option 2';
    entreprises.appendChild(option2);

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

export default AddContactPage;

