import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from '../service/AuthService';

const Register = () => {
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();

        AuthService.register(name, password)
            .then((response) => {
                console.log('User registered:', response.data);
                navigate('/login'); // Przekierowanie do strony logowania po udanej rejestracji
            })
            .catch((error) => {
                setError('Rejestracja nie powiodła się. Spróbuj ponownie.');
                console.error(error);
            });
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center mb-4">Zarejestruj się</h2>
            <form onSubmit={handleSubmit} className="w-50 mx-auto">
                <div className="form-group mb-3">
                    <label>Username:</label>
                    <input 
                        type="text" 
                        className="form-control" 
                        value={name} 
                        onChange={(e) => setName(e.target.value)} 
                        required 
                    />
                </div>
                <div className="form-group mb-3">
                    <label>Password:</label>
                    <input 
                        type="password" 
                        className="form-control" 
                        value={password} 
                        onChange={(e) => setPassword(e.target.value)} 
                        required 
                    />
                </div>
                <button type="submit" className="btn btn-primary w-100">Register</button>
            </form>
            {error && <p className="text-danger text-center mt-3">{error}</p>}
        </div>
    );
};

export default Register;
