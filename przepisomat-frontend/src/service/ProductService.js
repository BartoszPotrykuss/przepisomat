import axios from "axios";

const PRODUCT_API_BASE_URL = "http://localhost:8080/api/product";

class ProductService {

    addProduct(product) {
        const token = localStorage.getItem('token'); 
        return axios.post(PRODUCT_API_BASE_URL, product, {
            headers: {
                Authorization: `Bearer ${token}` 
            }
        });
    }

    getProducts() {
        const token = localStorage.getItem('token'); 
        return axios.get(PRODUCT_API_BASE_URL + "?username=admin", {
            headers: {
                Authorization: `Bearer ${token}` 
            }
        });
    }

    deleteProductById(id) {
        const token = localStorage.getItem('token');
        return axios.delete(`${PRODUCT_API_BASE_URL}/${id}`, {
            headers: {
                Authorization: `Bearer ${token}` 
            }
        });
    }

    updateProductAmount(id, newAmount) {
        const token = localStorage.getItem('token');
        return axios.put(`${PRODUCT_API_BASE_URL}/${id}`, {amount: newAmount}, {
            headers: {
                Authorization: `Bearer ${token}` 
            }
        });
    }
}

export default new ProductService();
