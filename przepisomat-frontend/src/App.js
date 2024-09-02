import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import NavBar from './components/NavBar';
import CreateRecipe from './components/CreateRecipe';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NoPage from './components/NoPage';
import Recipes from './components/Recipes';
import AddProduct from './components/AddProduct';
import ListOfProducts from './components/ListOfProducts';
import Login from './components/Login';
import Register from './components/Register';
import ProtectedRoute from './components/ProtectedRoute';


function App() {
  return (
    <div className="App">
      <Router>
        <NavBar />
        <Routes>
          {/* Otwarte trasy */}
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/*" element={<NoPage />} />
          
          {/* Zabezpieczone trasy */}
          <Route 
            path="/" 
            element={
              <ProtectedRoute>
                <ListOfProducts />
              </ProtectedRoute>
            } 
          />
          <Route 
            path="/createRecipe" 
            element={
              <ProtectedRoute>
                <CreateRecipe />
              </ProtectedRoute>
            } 
          />
          <Route 
            path="/recipes" 
            element={
              <ProtectedRoute>
                <Recipes />
              </ProtectedRoute>
            } 
          />
          <Route 
            path="/addProduct" 
            element={
              <ProtectedRoute>
                <AddProduct />
              </ProtectedRoute>
            } 
          />
          <Route 
            path="/products" 
            element={
              <ProtectedRoute>
                <ListOfProducts />
              </ProtectedRoute>
            } 
          />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
