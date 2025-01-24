// Base URL for all API requests
const API_BASE_URL = 'http://localhost:8080';

// Function to register a new user
// Takes userData object containing registration details
export const registerUser = async (userData) => {
  try {
    // Make POST request to registration endpoint
    // Use fetch to make the request
    const response = await fetch(`${API_BASE_URL}/users/register`, {
      method: 'POST', // Using POST method for creating new resource
      headers: {
        'Content-Type': 'application/json', // Specify JSON content type
      },
      body: JSON.stringify(userData), // Convert userData object to JSON string
    });

    // Check if request was successful
    if (!response.ok) {
      throw new Error('Registration failed');
    }

    // Parse and return response data
    return await response.json();
  } catch (error) {
    // If any error occurs, throw error with message
    throw new Error(error.message || 'Registration failed');
  }
}; 