import HomePage from "../Pages/HomePage";
import LoginPage from "../Pages/LoginPage";
import RegisterPage from "../Pages/RegisterPage";
import AcceptRefusePage from "../Pages/contacts/AcceptRefusePage";
import AddContactPage from "../Pages/contacts/AddContactPage";
import SearchPage from "../Pages/SearchPage";
import ProfilePage from "../Pages/ProfilePage";

// Define routes
const routes = {
    '/':HomePage,
    '/login':LoginPage,
    '/contact/refusal': AcceptRefusePage,
    '/register':RegisterPage,
    '/contact/add': AddContactPage,
    '/search': SearchPage,
    '/profile': ProfilePage
};

export default routes;