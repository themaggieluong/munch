import { useState, useContext } from 'react';
import { Button } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';
import Layout from "../components/ui/Layout";
import { UserContext } from "../context/UserContext";
import Container from 'react-bootstrap/Container';
import { useNavigate } from "react-router-dom";
import { loginUser }    from '../api/UserApi'
import Col from 'react-bootstrap/Col';

const Login = ({ children }) => {
    const navigate = useNavigate();        
    const { setCurrentUser } = useContext(UserContext);
    const userContext = useContext(UserContext);
    const currentUser = userContext.currentUser;
  
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(false);

    const doSetUsername = (event) => {
        setUsername(event.target.value);
    };
    const doSetPassword = (event) => {
        setPassword(event.target.value);
    };

    const handleCancel = () => {
        setUsername('');
        setPassword('');
        navigate("/");

    };

    const handleLogin = (event) => {
        if (event !== undefined) event.preventDefault();
        const loginCurrentUser = async () => {
            const userLogin = { 'username': username, 'password': password };
            try{
                const loggedInUser = await loginUser(userLogin);
                if (loggedInUser) {                  
                    loggedInUser.user.isAuthenticated = true;
                    localStorage.setItem('user', JSON.stringify(loggedInUser.user));
                    localStorage.setItem('token', loggedInUser.token);        
                    setCurrentUser(loggedInUser.user);
                    navigate("/Home");
                }
            }
            catch (e){
                setError(true);
            }
        };
        loginCurrentUser();
    };
    
    return (
        <Layout>
            <Container style={{padding: "1.25rem"}}>
            {(!currentUser || !currentUser.isAuthenticated) &&
                <Form onSubmit={handleLogin}>
                    <div className="text-center mt-5 mb-5">
                        <h1>Login to your account</h1>
                    </div>
                    <Form.Group as={Col} md="12" className="mb-3" controlId="formBasicEmail">
                    {
                        error &&
                        <div className="alert alert-danger" role="alert">
                            Wrong Credentials. Try again.
                        </div>
                    }
                        <Form.Label>Username</Form.Label>
                        <Form.Control type="email" placeholder="Enter username"
                            value={username} onChange={doSetUsername} />
                    </Form.Group>
                    <Form.Group as={Col} md="12" className="mb-3" controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control type="password" placeholder="Password"
                            value={password} onChange={doSetPassword} />
                    </Form.Group>

                <div className="d-grid gap-3 col-12 mx-auto">
                        <Button variant="primary" onClick={handleLogin} type="submit" size="lg">
                            Login
                        </Button>
                        <Button variant="secondary" onClick={handleCancel} size="lg">
                            Cancel
                        </Button>
                </div>
                <div className="text-center mt-5 mb-5">
                        <p> Not a member? <a href="/Register" style={{textDecoration: "none"}}>Register</a></p>
                    </div>
                </Form>
            }
            {children}
            </Container>
        </Layout>
    );
}
export default Login;