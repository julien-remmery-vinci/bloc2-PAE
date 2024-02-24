import { renderPageTitle } from "../../utils/render";
import { getAuthenticatedUser } from "../../utils/auths";

const Header = () => {
    // eslint-disable-next-line no-unused-vars
    const authenticatedUser = getAuthenticatedUser();
    
    renderPageTitle(`Bienvenue chez VinciOBS, ${authenticatedUser?.username}`);
};

export default Header;

