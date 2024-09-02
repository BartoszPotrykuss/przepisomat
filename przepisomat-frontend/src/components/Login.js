import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from '../service/AuthService';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();

        AuthService.login(username, password)
            .then((response) => {
                console.log('Logged in:', response.data);
                localStorage.setItem('token', response.data); // Zapisz token JWT
                navigate('/'); // Przekierowanie do głównej strony aplikacji po udanym logowaniu
                window.location.reload();
            })
            .catch((error) => {
                setError('Logowanie nie powiodło się. Sprawdź dane i spróbuj ponownie.');
                console.error(error);
            });
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center mb-4">Zaloguj się</h2>
            <form onSubmit={handleSubmit} className="w-50 mx-auto">
                <div className="form-group mb-3">
                    <label>Username:</label>
                    <input 
                        type="text" 
                        className="form-control" 
                        value={username} 
                        onChange={(e) => setUsername(e.target.value)} 
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
                <button type="submit" className="btn btn-primary w-100">Login</button>
            </form>
            {error && <p className="text-danger text-center mt-3">{error}</p>}
        </div>
    );
};

export default Login;
