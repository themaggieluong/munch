import { useParams } from "react-router-dom";
import Layout from '../components/ui/Layout';
import RestaurantDetail from '../components/ui/restaurant/RestaurantDetail';


const RestaurantDetailPage = ({ children }) => {
  let { restaurantId } = useParams();  

  return (
    <Layout>
      <RestaurantDetail restaurantId={restaurantId}/>
      {children}
    </Layout>
  );
}
export default RestaurantDetailPage;