import { useState } from 'react';
import Layout from "../components/ui/Layout";
import Container from 'react-bootstrap/Container';

import Form from 'react-bootstrap/Form';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { Button } from 'react-bootstrap';
import { registerUser } from '../api/UserApi'
import { useNavigate } from "react-router-dom";


const Register = () => {
    const navigate = useNavigate();
    const [validated, setValidated] = useState(null);

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('')
    const [confirmPassword, setconfirmPassword] = useState('')
    const [firstName, setfirstName] = useState('')
    const [lastName, setlastName] = useState('')
    const [role, setRole] = useState("USER");
    const [zipCode, setZipCode] = useState('');
    const [error, setError] = useState(false);
    const [pwError, setPwError] = useState(false);


    const userInfo = {
        username : username,
        password : password,
        confirmPassword : confirmPassword,
        firstName : firstName,
        lastName : lastName,
        role : role,
        zipCode: zipCode
    };

    const handleCreateAccount = async (e) => {
        e.preventDefault();
        const form = e.currentTarget;
        if(form.checkValidity() === false) {
            e.stopPropagation();
        }
        setValidated(true);
        registerTheUser();
    };

    const registerTheUser = async () => {
        try{
            if(userInfo.password !== userInfo.confirmPassword){
                setPwError(true);
            }
            else{
                setPwError(false);
                const registeredUser = await registerUser(userInfo);
                if(registeredUser === true) {
                    navigate("/Login")
                }
            }
        }
        catch(e){
            setError(true);
        }
    };

    const handleCancel = () => {
        navigate("/");
    };


    return (
        <Layout>
            <Container style={{padding: "1.25rem"}}>
            <div className="text-center">
                <h1>Create an account!</h1>
            </div>
            <Form noValidate validated={validated}>
            <Form.Group as={Col} md="12" className="mb-3">
                {error&&
                    <div className="alert alert-danger" role="alert">
                        Username already exists.
                  </div>
                }
                {
                        pwError &&
                        <div className="alert alert-danger" role="alert">
                            Passwords do not match.
                        </div>
                    }
                <Row>
                    <Col>
                    <Form.Group controld="formUsername">
                    <Form.Label>Username</Form.Label>
                    <Form.Control required type="text" placeholder="Enter username" onChange={e => {setUsername(e.target.value)}} />
                    <Form.Control.Feedback type="invalid">Please choose a username.</Form.Control.Feedback>
                    </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col>
                    <Form.Label>Password</Form.Label>
                    <Form.Control required type="text" placeholder="Enter password" onChange={e => {setPassword(e.target.value)}} />
                    <Form.Control.Feedback type="invalid">Please create a password.</Form.Control.Feedback>
                    </Col>
                    <Col>
                    <Form.Label>Confirm Password</Form.Label>
                    <Form.Control required type="text" placeholder="Confirm password" onChange={e => {setconfirmPassword(e.target.value)}}/>
                    <Form.Control.Feedback type="invalid">Please re-enter your password.</Form.Control.Feedback>
                    </Col>
                </Row>
                <Row>
                    <Col>
                    <Form.Label>First Name</Form.Label>
                    <Form.Control required type="text" placeholder="Enter first name" onChange={e => {setfirstName(e.target.value)}}/>
                    <Form.Control.Feedback type="invalid">Please enter your first name.</Form.Control.Feedback>
                    </Col>

                    <Col>
                    <Form.Label>Last Name</Form.Label>
                    <Form.Control required type="text" placeholder="Enter last name" onChange={e => {setlastName(e.target.value)}} />
                    <Form.Control.Feedback type="invalid">Please enter your last name.</Form.Control.Feedback>
                    </Col>

                    <Col>
                    <Form.Label>Zip Code</Form.Label>
                    <Form.Control required type="number" placeholder="Enter zip code" onChange={e => {setZipCode(e.target.value)}} />
                    <Form.Control.Feedback type="invalid">Please enter your zip code.</Form.Control.Feedback>
                    </Col>
                </Row>
            </Form.Group>
            <div className="d-grid gap-3 col-12 mx-auto">
                <Button variant="primary" onClick={handleCreateAccount} type="submit" size="lg">Create Account</Button>
                <Button variant="secondary" onClick={handleCancel} size="lg">Cancel</Button>
            </div>
            </Form>
            </Container>
        </Layout>
    );
}
export default Register;