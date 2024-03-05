import HomePage from "../Pages/HomePage";
import LoginPage from "../Pages/LoginPage";
import AcceptRefusePage from "../Pages/contacts/AcceptRefusePage";

// Define routes
const routes = {
    '/':HomePage,
    '/login':LoginPage,
    '/contact/refusal': AcceptRefusePage
};

export default routes;