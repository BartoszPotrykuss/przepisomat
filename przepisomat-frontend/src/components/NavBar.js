import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from '../service/AuthService';

const NavBar = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        // Sprawdź, czy token JWT jest zapisany w localStorage
        const token = localStorage.getItem('token');
        setIsLoggedIn(!!token); // Jeśli token istnieje, ustaw isLoggedIn na true
    }, []);

    const handleLogout = () => {
        AuthService.logout();
        setIsLoggedIn(false);
        navigate('/login');
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light py-3">
            <div className="container">
                <a className="navbar-brand text-primary fw-bold" href="/">
                    PRZEPISOMAT
                </a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item">
                            <a className="nav-link text-primary" href="/createRecipe">Utwórz przepis</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link text-primary" href="/recipes">Twoje przepisy</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link text-primary" href="/addProduct">Dodaj produkt</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link text-primary" href="/products">Twoje produkty</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link text-primary" href="/">O aplikacji</a>
                        </li>
                        {!isLoggedIn ? (
                            <>
                                <li className="nav-item">
                                    <a className="nav-link text-warning" href="/login">Zaloguj się</a>
                                </li>
                                <li className="nav-item">
                                    <a className="nav-link text-warning" href="/register">Zarejestruj się</a>
                                </li>
                            </>
                        ) : (
                            <li className="nav-item">
                                <button className="btn btn-danger ms-3" onClick={handleLogout}>Wyloguj się</button>
                            </li>
                        )}
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default NavBar;
