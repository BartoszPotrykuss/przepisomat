import axios from 'axios';
import React, { useState } from 'react';
import ProductService from '../service/ProductService';

const AddProduct = () => {
    const [product, setProduct] = useState({
        name: "",
        amount: ""
    });

    const [alert, setAlert] = useState("");

    const handleSubmit = (event) => {
        event.preventDefault();
        ProductService.addProduct(product).then((response) => {
            console.log(response);
            setAlert("Dodano produkt!");

            // Ukrycie alertu po 3 sekundach
            setTimeout(() => {
                setAlert("");
            }, 3000);
        }).catch((error) => {
            console.log(error);
        })
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProduct(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    return (
        <div className="container mt-5">
                        {alert && (
                <div className="alert alert-primary mb-2" role="alert">
                    {alert}
                </div>
            )}
            <h2>Dodaj Produkt</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="productName">Nazwa Produktu</label>
                    <input
                        type="text"
                        className="form-control"
                        id="productName"
                        name="name"
                        placeholder="Wpisz nazwę produktu"
                        value={product.name}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="productAmount">Waga (g)</label>
                    <input
                        type="number"
                        className="form-control"
                        id="productAmount"
                        name="amount"
                        placeholder="Wpisz wagę produktu"
                        value={product.amount}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-primary mt-3">Dodaj Produkt</button>
            </form>
        </div>
    );
};

export default AddProduct;
