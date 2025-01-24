// login.jsx

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const loginUser = async (email, password) => {
    // TODO: Replace with actual API call
    return new Promise((resolve) => {
      setTimeout(() => {
        // Simulate successful login
        resolve({ token: 'dummy-token-123' });
      }, 1000);
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await loginUser(email, password);
      localStorage.setItem('token', response.token);
      navigate('/dashboard');
    } catch (error) {
      console.error('Login failed:', error);
    }
  };

  return (
    <div style={{ 
      padding: '20px',
      maxWidth: '400px',
      margin: '40px auto'
    }}>
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '20px' }}>
          <TextField
            fullWidth
            label="Email Address"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div style={{ marginBottom: '20px' }}>
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
          Sign In
        </Button>
        {/* button to register  */}
        <Button
          variant="contained"
          fullWidth
          onClick={() => navigate('/register')}
          style={{marginTop: '10px'}}>
            Register
        </Button>
      </form>
    </div>
  );
}

export default Login;
// index.js
