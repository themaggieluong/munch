import Layout from "../components/ui/Layout";
import { addUserRestaurant, getNotLikedOrRejectedRestaurant, getRestaurantByCategory } from "../api/RestaurantApi";
import { useEffect, useState } from "react";
import Card from 'react-bootstrap/Card';
import { LinkContainer } from "react-router-bootstrap";
import Button from 'react-bootstrap/Button'
import CategoryForm from "../components/form/CategoryForm";
import Container from 'react-bootstrap/Container';
import Image from 'react-bootstrap/Image';
import { UserContext } from "../context/UserContext";
import { useContext } from "react";
import TinderCard from 'react-tinder-card'
import '../../App.css';

const Home = () => {
    const userContext = useContext(UserContext);
    const currentUser = userContext.currentUser;
    const [restaurants, setRestaurants] = useState([]);
    const [form, setForm] = useState(false);
    const [clicked, setClicked] = useState(false);
    const [first, setFirst] = useState(false);
    const [restCategories, setRestCategories] = useState([]);
    useEffect(() =>{
        if(currentUser !== null && !first){
            const getRestaurantList = async() => {
                const data = await getNotLikedOrRejectedRestaurant(currentUser.id);
                setRestaurants(data);
            }
            getRestaurantList();
            setFirst(true);
        }
    }, [clicked, currentUser, first, restaurants]);

    const resetUpdateModal = event => {
        setForm(false);
    }

    const filteredRestaurants = (restaurants) => {
        setRestaurants(restaurants);
    }

    const convertToUserRestaurant = (restaurant, liked) => {
        const userRestaurant = {
            userId: currentUser.id,
            restaurantId: restaurant.restaurantId,
            liked: liked,
            rejected: !liked,
            visitCount: 0
        };
        return userRestaurant;
    }

    const handleLike = (restaurant, event) => {
            const addLikeRestaurant = async() =>{
                const userRestaurantObj = convertToUserRestaurant(restaurant, true);
                await addUserRestaurant(userRestaurantObj);
                filterLikedOrRejectedRestaurants();
                setClicked(!clicked);
            }
            addLikeRestaurant();
    };

    const restCategory = (categories) => [
        setRestCategories(categories)
    ]

    const filterLikedOrRejectedRestaurants = () => {
        const filterCategories = async() => {
            let data;
            if(restCategories.length !== 0){
                data = await getRestaurantByCategory(restCategories, currentUser.id);
            }
            else{
                data = await getNotLikedOrRejectedRestaurant(currentUser.id);
            }
            setRestaurants(data);
        }
        filterCategories();
    };

    const handleReject = (restaurant, event) => {
        const rejectRestaurant = async() =>{
            const userRestaurantObj = convertToUserRestaurant(restaurant, false);
            await addUserRestaurant(userRestaurantObj);
            filterLikedOrRejectedRestaurants();
            setClicked(!clicked);
        }
        rejectRestaurant();
    };




    return (
        <Layout>
            <Container>
                <div className="d-table w-100">
                    <p className="d-table-cell">{restaurants.length} results</p>
                        <div className="d-table-cell tar">
                            <Button variant="btn btn-danger" 
                            onClick={e => {setForm(true)}} 
                            className="mt-5"> 
                            Filter 
                            <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" className="bi bi-filter" viewBox="0 0 16 16">
                                <path d="M6 10.5a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5z"/>
                            </svg>
                            </Button>
                        </div>
                    </div>
                {form &&
                    <CategoryForm resetUpdateModal={resetUpdateModal} filteredRestaurants={filteredRestaurants} restCategory={restCategory}> </CategoryForm>
                }
            <div className="tinderCards__cardContainer">
                {restaurants.length === 0 &&
                    <p className="mt-5"> No restaurants available. </p>
                }
                {restaurants.map((restaurant) => (
                <TinderCard
                    className="swipe"
                    key={restaurant.restaurantId}>
                    <div className="card">
                        <Card.Body>
                            <Card.Img
                            src={`../../../images/${restaurant.imgSrc}.png`}
                            alt={restaurant.restaurantName}
                            width="10%" />
                            <Card.Title className="mt-3 fs-1 fw-bold">{restaurant.restaurantName}</Card.Title>
                            <Card.Text className="mb-2 fs-5">{restaurant.description}</Card.Text>
                            <Card.Text className="mb-2 fs-5">⭐ Rating: {restaurant.rating}</Card.Text>
                            <div className="text-center">
                                <LinkContainer to={{ pathname: `/${restaurant.restaurantId}` }} >
                                    <Button className="mt-3 d-grid gap-2" size="lg" variant="outline-primary">See More</Button>
                                </LinkContainer>
                        <Card.Text className="mt-3 mx-auto">
                            <Button variant="outline-secondary" className="rounded-circle" height="50" width="50"><Image src="images/Dislike.png" width="40" onClick={(event) => handleReject(restaurant, event)}/></Button>
                            <Button variant="outline-danger" className="me-3 rounded-circle" height="50" width="50"><Image src="images/Like.png" width="40" onClick={(event) => handleLike(restaurant, event)}/></Button>
                        </Card.Text>
                            </div>
                        </Card.Body>
                    </div>
                </TinderCard>
            ))}
            </div>
            </Container>
        </Layout>
    );
}
export default Home;