import React from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '@mui/material/Button';

function Dashboard() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>Dashboard</h1>
      <p>Welcome to your dashboard!</p>
      <Button 
        variant="contained" 
        color="secondary" 
        onClick={handleLogout}
        style={{ marginTop: '20px' }}
      >
        Logout
      </Button>
    </div>
  );
}

export default Dashboard; 