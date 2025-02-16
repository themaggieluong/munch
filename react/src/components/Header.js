import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { UserContext } from "../../context/UserContext";
import { useContext, useState } from "react";
import Image from 'react-bootstrap/Image';
import { NavbarText } from 'reactstrap';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import { useNavigate } from "react-router-dom";
import { LinkContainer } from 'react-router-bootstrap';

const Header = () => {
	const navigate = useNavigate();
    const userContext = useContext(UserContext);
	const { setCurrentUser } = useContext(UserContext);
    const currentUser = userContext.currentUser;
	const [show, setShow] = useState(false);
	const handleClose = () => setShow(false);
	const handleShow = () => setShow(true);

	const handleLogout = () => {
		localStorage.removeItem('token');
		localStorage.removeItem('user');
		setCurrentUser(null);
		navigate("/");
	  }

	return (
		<React.Fragment>
			<header>
			<Navbar sticky="top" bg="light" data-bs-theme="light">
				<Container>
				{currentUser == null &&
				<Navbar.Brand href="/">
				<Image src="../../../images/MunchLogo_Full.png" width={100} alt="Munch logo" fluid />
				</Navbar.Brand>
				}

				{currentUser !== null &&
					<Navbar>
						<LinkContainer to="/Home" className="mr-5">
							<Nav.Link>
								<Image src="../../../images/MunchLogo_Full.png" width={100} alt="Munch logo" fluid="true" />
							</Nav.Link>
						</LinkContainer>
						<NavbarText className="me-auto justify-content-beg">
							<span className="navbar-brand mb-0 h1">
								Hello {currentUser.firstName}!
							</span>
						</NavbarText>
					</Navbar>
				}
				{currentUser !== null &&
					<Navbar.Collapse className="me-auto justify-content-end">
						<LinkContainer to="/LikedRestaurant" className="mr-5">
							<Nav.Link>
								<span className="navbar-brand mb-0">Matches</span>
							</Nav.Link>
						</LinkContainer>
						<Nav.Item>
							<Nav.Link onClick={handleShow} className="navbar-brand">Logout</Nav.Link>
						</Nav.Item>
					</Navbar.Collapse>
				}
				<Modal show={show} onHide={handleClose}>
					<Modal.Header closeButton>
					<Modal.Title>Logout</Modal.Title>
					</Modal.Header>
					<Modal.Body>Are you sure you want to logout?</Modal.Body>
					<Modal.Footer>
					<Button variant="secondary" onClick={handleClose}>
						No, I'm hungry
					</Button>
					<Button variant="primary" onClick={handleLogout}>
						Logout
					</Button>
					</Modal.Footer>
				</Modal>
				</Container>
			</Navbar>
			</header>
		</React.Fragment>	
	);
};

export default Header;
