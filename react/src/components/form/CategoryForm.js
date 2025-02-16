import { useEffect, useState, useContext } from "react";
import { getCategories } from "../../api/CategoryApi";
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Button from 'react-bootstrap/Button';
import { getRestaurantByCategory, getNotLikedOrRejectedRestaurant } from "../../api/RestaurantApi";
import Modal from 'react-bootstrap/Modal';
import { UserContext } from "../../context/UserContext";

const CategoryForm = ({resetUpdateModal, filteredRestaurants, restCategory}) => {
    const userContext = useContext(UserContext);
    const currentUser = userContext.currentUser;
    const [categories, setCategories] = useState([]);
    const [clickedCategories, setClickedCategories] = useState([]);
    const [cardModal, setCardModal] = useState(true);

    useEffect(() =>{
        const getCategoryArr = async() => {
            const data =  await getCategories();
            setCategories(data);
        }
        getCategoryArr();
    }, []); 

    const handleSubmit = (event) => {
        if (event !== undefined) event.preventDefault();
        if(clickedCategories.length !== 0){
            async function getCatRestaurants(){
                restCategory(clickedCategories);
                const data = await getRestaurantByCategory(clickedCategories, currentUser.id);
                filteredRestaurants(data);
            }
            getCatRestaurants();
        }
        else{
            async function getAll(){
                restCategory(clickedCategories);
                const data = await getNotLikedOrRejectedRestaurant(currentUser.id);
                filteredRestaurants(data);
            }
            getAll();
        }
        setCardModal(false);
        resetUpdateModal();
    }

    const handleClick = (id, event) => {
        if (event !== undefined) event.preventDefault();
        setClickedCategories(clickedCategories.includes(id)? clickedCategories.filter(category => category !== id): [...clickedCategories, id]);
    }

    return (
        <main>
            <Modal show={cardModal} size='lg' transition='false'>
                <Modal.Header>
                Profile Questionaire
                </Modal.Header>
                <Container className="mt-3">
                    <Form>
                        <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
                            <Form.Label as="legend" column sm={2}>
                                Food Preferences
                            </Form.Label>
                            <Col sm={10}>
                                {categories.map((category, id) => {
                                    return <div key={id}> 
                                        <Form.Check
                                            inline
                                            label={category.categoryName}
                                            name="group1"
                                            onInput={(event) => handleClick(category.categoryId, event)}
                                        />
                                    </div>
                                })}
                            </Col>
                        </Form.Group>
                        <Form.Group as={Row} className="mb-3">
                            <Col sm={{ span: 10, offset: 2 }}>
                            <Button type="submit" onClick={handleSubmit}>Submit</Button>
                            </Col>
                        </Form.Group>
                    </Form>
                </Container>
            </Modal>
        </main>

    );
}

export default CategoryForm;