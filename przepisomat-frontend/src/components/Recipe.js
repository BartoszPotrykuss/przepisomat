import React from 'react';

const Recipe = ({ recipe }) => {  // Destrukturyzacja propsów, teraz "recipe" jest obiektem
    return (
        <div className='container'>
            <div className='col'>
                <div className='row text-center'>
                    <h2 className='text-primary'>{recipe.title}</h2>  {/* Teraz poprawnie odnosimy się do "recipe" */}
                </div>
                <div className='row text-center'>
                    <div className='col text-center'>
                        <h2>Składniki</h2>
                        <ul className='list-group list-group-item-info'>
                            <li className='list-group-item p-2'>{recipe.ingredients}</li>
                        </ul>
                    </div>
                    <div className='col text-center'>
                        <h2>Opis</h2>
                        <ul className='list-group list-group-item-primary'>
                            <li className='list-group-item p-2'>{recipe.listOfSteps}</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Recipe;
