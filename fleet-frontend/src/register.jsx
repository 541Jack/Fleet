import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { registerUser } from './services/api';

function Register() {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        
        try {
            await registerUser({
                username,
                email,
                password
            });
            navigate('/login');
        } catch (error) {
            setError(error.message);
            console.error('Registration failed:', error);
        }
    }

    return (
        <div style={{ 
            padding: '20px',
            maxWidth: '400px',
            margin: '40px auto'
        }}>
            <h1>Register</h1>
            {error && (
                <div style={{ color: 'red', marginBottom: '20px' }}>
                    {error}
                </div>
            )}
            <form onSubmit={handleSubmit}>
                <div style={{marginBottom:'20px'}}>
                    <TextField
                        fullWidth
                        label="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div style={{marginBottom:'20px'}}>
                    <TextField
                        fullWidth
                        label="Email"
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div style={{marginBottom:'20px'}}>
                    <TextField
                        fullWidth
                        label="Password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <Button 
                    type="submit"
                    variant="contained"
                    fullWidth
                >
                    Register
                </Button>
                <Button
                    variant="text"
                    fullWidth
                    onClick={() => navigate('/login')}
                    style={{marginTop: '10px'}}
                >
                    Already have an account? Login
                </Button>
            </form>
        </div>
    )
}

export default Register;