import axios from "axios";

const RECIPE_API_BASE_URL = "http://localhost:8080/api/recipe";

class RecipeService {
    getRecipes() {
        const token = localStorage.getItem('token'); 
        return axios.get(RECIPE_API_BASE_URL, {
            headers: {
                Authorization: `Bearer ${token}` 
            }
        });
    }


    createRecipe() {
        const token = localStorage.getItem('token'); 
        return axios.post(RECIPE_API_BASE_URL, null, {
            headers: {
                Authorization: `Bearer ${token}` 
            }
        });
    }

    deleteRecipeById(id) {
        const token = localStorage.getItem('token');
        return axios.delete(`${RECIPE_API_BASE_URL}/${id}`, {
            headers: {
                Authorization: `Bearer ${token}` 
            }
        })
    }
}

export default new RecipeService();
