import React, { useEffect, useState } from 'react';
import Recipe from './Recipe';
import RecipeService from '../service/RecipeService';
import Swal from 'sweetalert2';

const Recipes = () => {
    const [recipes, setRecipes] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await RecipeService.getRecipes();
                setRecipes(response.data);
                console.log(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    const handleDelete = (id) => {
        Swal.fire({
            title: 'Czy na pewno chcesz usunąć ten przepis?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Tak, usuń!',
            cancelButtonText: 'Anuluj',
        }).then((result) => {
            if (result.isConfirmed) {
                RecipeService.deleteRecipeById(id).then(() => {
                    window.location.reload();
                });
            }
        });
    }

    return (
        <div className="accordion" id="accordionPanelsStayOpenExample">
            <h2 className='text-center text-info'>TWOJE PRZEPISY</h2>
            {recipes ? (
                recipes.map((recipe) => (
                    <div className="accordion-item" key={recipe.id}>
                        <div className="d-flex justify-content-between align-items-center">
                            <h2 className="accordion-header flex-grow-1">
                                <button className="accordion-button text-primary" type="button" data-bs-toggle="collapse" data-bs-target={`#panelsStayOpen-collapse${recipe.id}`} aria-expanded="true" aria-controls={`panelsStayOpen-collapse${recipe.id}`}>
                                    {recipe.title}
                                </button>
                            </h2>
                            <button type='button' className='btn btn-outline-danger ms-3' onClick={() => handleDelete(recipe.id)}>Usuń przepis</button>
                        </div>
                        <div id={`panelsStayOpen-collapse${recipe.id}`} className="accordion-collapse collapse">
                            <div className="accordion-body">
                                <Recipe recipe={recipe}/>
                            </div>
                        </div>
                    </div>
                ))
            ) : (
                <p>Ładowanie przepisów...</p>
            )}
        </div>
    );
}

export default Recipes;