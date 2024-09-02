import axios from "axios";

const AUTH_BASE_API_URL = "http://localhost:8080/auth";

class AuthService {
    register(name, password) {
        return axios.post(`${AUTH_BASE_API_URL}/register`, { name, password });
    }

    login(username, password) {
        return axios.post(`${AUTH_BASE_API_URL}/token`, { username, password });
    }

    logout() {
        localStorage.removeItem('token');
    }
}

export default new AuthService();