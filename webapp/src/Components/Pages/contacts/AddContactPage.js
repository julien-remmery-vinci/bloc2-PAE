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
    
}

export default AddContactPage;

