import Layout from "../Layout";
import { useEffect, useState } from "react";
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { LinkContainer } from "react-router-bootstrap";
import Button from 'react-bootstrap/Button'
import Container from 'react-bootstrap/Container';
import { UserContext } from "../../../context/UserContext";
import { useContext } from "react";
import { getLikedRestaurant } from "../../../api/RestaurantApi";

const LikedRestaurant = () => {
    const userContext = useContext(UserContext);
    const currentUser = userContext.currentUser;
    const [restaurants, setRestaurants] = useState([]);

    useEffect(() => {
        if(currentUser !== null){
            const getRestaurants = async() =>{
                const data = await getLikedRestaurant(currentUser.id);
                setRestaurants(data);
            }
            getRestaurants();
        }
    }, [currentUser]
    );
    
    return(
        <Layout>
            <Container className="mt-5">
                {
                    currentUser !== null &&
                    <h1> {currentUser.username}'s liked restaurants</h1>
                }
                <Container className="mt-5">
                    <Row>
                        {restaurants.map(restaurant => (
                            <Col key={restaurant.restaurantId}>
                            <Card className="my-2"
                                style={{
                                width: '18rem'
                                }}>
                                <Card.Img variant="top" src={`../../../images/${restaurant.imgSrc}.png`} alt={restaurant.restaurantName} width="10%" />
                                <Card.Body>
                                    <Card.Title>{restaurant.restaurantName}</Card.Title>
                                    <Card.Text className="mb-2 text-muted moveSubtitle"
                                        tag="h6">{restaurant.description}</Card.Text>
                                    <Card.Text className="mb-2 text-muted moveSubtitle"
                                        tag="h6">⭐ Rating: {restaurant.rating}
                                    </Card.Text>
                                    <LinkContainer to={{ pathname: `/${restaurant.restaurantId}` }} >
                                        <Button variant="outline-primary">See More</Button>
                                    </LinkContainer>
                                </Card.Body>
                            </Card>
                            </Col>
                        ))}
                        </Row>
                </Container>
            </Container>
        </Layout>
    )
}

export default LikedRestaurant;