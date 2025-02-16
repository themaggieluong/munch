import React, { useState, useEffect } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { getRestaurantDetail } from "../../../api/RestaurantApi";
import Loading from '../Loading';

const RestaurantDetail = (props) => {
  const [restaurantDetail, setRestaurantDetail] = useState({});
  const [callingDetail, setCalling] = useState(false);

  useEffect(() => {
    const getDetail = () => {
      const restaurantDetail = async () => {
        if (!callingDetail) {
          setCalling(true);
          const localRestaurant = await getRestaurantDetail(props.restaurantId);
          setCalling(false);
          setRestaurantDetail(localRestaurant);
        }
      }
      restaurantDetail();
    };
    getDetail();

  }, []);

  if (callingDetail) return <Loading />;

  return (
    <React.Fragment>
      {restaurantDetail &&
        <Row>
          <Col key="titleCard">
            
          <Card className="my-2"
              style={{width: '50%', margin: 'auto'}}>
            
              <Card.Img variant="top" src={`../../../images/${restaurantDetail.imgSrc}.png`} alt={restaurantDetail.restaurantName} fluid="true" />
              <Card.Body>
                <Card.Title>{restaurantDetail.restaurantName}</Card.Title>
                <Card.Subtitle className="mb-2 text-muted moveSubtitle"
                  tag="h6">{restaurantDetail?.description}</Card.Subtitle>
                <Card.Text>
                  {restaurantDetail?.overview}
                </Card.Text>
                <Card.Text>
                  Address: {restaurantDetail.address}, {restaurantDetail.city}, {restaurantDetail.state}, {restaurantDetail.zipCode}
                </Card.Text>
                <Card.Text>
                  Phone Number: {restaurantDetail.phoneNumber}
                </Card.Text>
                <Card.Text>
                  Rating: {restaurantDetail.rating}
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      }
      </React.Fragment>
  );
}

export default RestaurantDetail;