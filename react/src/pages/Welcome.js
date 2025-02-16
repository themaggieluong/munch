import Layout from "../components/ui/Layout";
import Image from 'react-bootstrap/Image';
import Container from 'react-bootstrap/Container';
import { Button } from 'react-bootstrap';

const Welcome = () => {
    return (
        <Layout>
            <Container>
                <div className="text-center card-body mb-10">
                <Image src="../../../images/Munch2.png" width={600} alt="Munch logo" fluid />
                <h3>Helping small businesses one bite at a time!</h3>
                <br></br>
                </div>
                <div className="d-grid gap-3 col-8 mx-auto">
                    <Button href="/Register" variant="primary" size="lg">
                        Register
                    </Button>
                    <Button href="/Login" variant="secondary" size="lg">
                        Login
                    </Button>
                </div>

            </Container>
        </Layout>
    );
}
export default Welcome;