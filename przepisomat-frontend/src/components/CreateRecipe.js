import React, { useState } from 'react';
import ListOfProducts from './ListOfProducts';
import Recipe from './Recipe';
import RecipeService from '../service/RecipeService';

const CreateRecipe = () => {
    const [recipe, setRecipe] = useState(null);
    const [loading, setLoading] = useState(false); // Nowy stan dla zarządzania ładowaniem

    const handleClick = (event) => {
        event.preventDefault();
        setLoading(true); // Ustawiamy stan ładowania na true
        RecipeService.createRecipe().then((response) => {
            setRecipe(response.data);
            setLoading(false); // Po otrzymaniu odpowiedzi ustawiamy stan ładowania na false
        }).catch((error) => {
            console.log(error);
            setLoading(false); // W razie błędu także ustawiamy stan ładowania na false
        });
    };

    return (
        <div className='container'>
            <div className='row mt-5 d-flex justify-content-center align-items-center'>
                <button type="button" onClick={handleClick} className='btn btn-lg btn-outline-primary w-50 h-50 fs-1'>
                    UTWÓRZ PRZEPIS
                </button>
            </div>

            {loading && (
                <div className='row mt-5 d-flex justify-content-center align-items-center'>
                    <div className="spinner-border" role="status">
                        <span className="sr-only"></span>
                    </div>
                </div>
            )}

            {recipe && !loading && (
                <div className='row mt-5'>
                    <Recipe recipe={recipe} />
                </div>
            )}
        </div>
    );
};

export default CreateRecipe;
