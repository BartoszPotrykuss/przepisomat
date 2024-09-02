import axios from 'axios';
import React, { useEffect, useState } from 'react';
import ProductService from '../service/ProductService';
import Swal from 'sweetalert2';

const ListOfProducts = () => {
    const [products, setProducts] = useState(null);
    const [update, setUpdate] = useState(null); // Zmieniono na `null`, aby przechowywać id produktu do edycji
    const [newAmount, setNewAmount] = useState(''); // Stan dla nowej ilości produktu

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await ProductService.getProducts();
                setProducts(response.data);
            } catch (error) {
                console.log(error);
            }
        };

        fetchData();
    }, []);

    const handleDelete = (id) => {
        Swal.fire({
            title: 'Czy na pewno chcesz usunąć ten produkt?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Tak, usuń!',
            cancelButtonText: 'Anuluj',
        }).then((result) => {
            if (result.isConfirmed) {
                ProductService.deleteProductById(id).then(() => {
                    window.location.reload(); // Przeładuj stronę po usunięciu produktu
                });
            }
        });
    };

    const handleUpdate = (product) => {
        setUpdate(product.id); // Ustawienie aktualizowanego produktu
        setNewAmount(product.amount); // Ustawienie aktualnej ilości w formularzu
    };

    const handleSaveUpdate = (id) => {
        ProductService.updateProductAmount(id, newAmount)
            .then(() => {
                setUpdate(null); // Zamknięcie formularza po zapisaniu
                window.location.reload(); // Przeładuj stronę po aktualizacji
            })
            .catch((error) => {
                console.error('Error updating product:', error);
            });
    };

    

    return (
        <div className='container'>
            <h3>TWOJE PRODUKTY</h3>
            <ul className='list-group list-group-item-info'>
                {products ? (
                    products.map((product) => (
                        <li key={product.id} className='list-group-item p-2'>
                            {product.amount}g {product.name} 
                            <button className='btn btn-danger ms-4 float-end' onClick={() => handleDelete(product.id)}>Usuń produkt</button>
                            <button className='btn btn-warning ms-4 float-end' onClick={() => handleUpdate(product)}>Edytuj ilość</button>
                            {update === product.id && (
                                <div className='mt-3'>
                                    <form className="d-flex align-items-center">
                                        <input
                                            type="number"
                                            className="form-control me-2"
                                            value={newAmount}
                                            onChange={(e) => setNewAmount(e.target.value)}
                                            placeholder="Nowa ilość w gramach"
                                        />
                                        <button
                                            type="button"
                                            className="btn btn-primary"
                                            onClick={() => handleSaveUpdate(product.id)}
                                        >
                                            Zapisz
                                        </button>
                                    </form>
                                </div>
                            )}
                        </li>
                    ))
                ) : (
                    <p>Ładowanie produktów...</p>
                )}
            </ul>
        </div>
    );
};

export default ListOfProducts;
