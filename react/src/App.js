import { UserProvider } from './context/UserContext';
import ErrorBoundary from './ErrorBoundary';
import Welcome from './pages/Welcome/Welcome';
import Register from './pages/Register/Register';
import Login from './pages/Login/Login';
import Home from './pages/Home/Home';
import RestaurantDetailPage from './pages/RestaurantDetail/RestaurantDetailPage';
import LikedRestaurant from './components/ui/restaurant/LikedRestaurant';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';


const MyApp = () => {

  return (
    <div className="wrapper">
      <ErrorBoundary fallback={<p>Something went wrong</p>}>
        <UserProvider>
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Welcome />} />
              <Route path="/Home" element={<Home />} />
              <Route path="/Register" element={<Register />} />
              <Route path="/Login" element={<Login />} />
              <Route path="/:restaurantId" element={<RestaurantDetailPage />} />
              <Route path="/LikedRestaurant" element={<LikedRestaurant />} />
            </Routes>
          </BrowserRouter>
        </UserProvider>
      </ErrorBoundary>
    </div>
  );
}

export default MyApp;