import { clearPage } from "../../utils/render";
import { getAuthenticatedUser } from "../../utils/auths";
import Navigate from "../Router/Navigate";

const HomePage = () => {
    const authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser) {
        Navigate('/login');
        window.location.reload();
    } else {
        clearPage();
        renderHomePage();
    }
};

function renderHomePage() {
    const main = document.querySelector('main');
    const h1 = document.createElement('h1');
    h1.textContent = 'Bienvenue sur VinciOBS !';
    main.appendChild(h1);
};

export default HomePage;