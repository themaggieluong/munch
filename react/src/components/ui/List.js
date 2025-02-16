import { useContext } from "react";
import { UserContext } from "../../context/UserContext"

const List = () => {
    const userContext = useContext(UserContext);
    const currentUser = userContext.currentUser;
    return (
        <React.Fragment>
        {
            (currentUser) && 
            <span>{currentUser.name}</span>
        }
        </React.Fragment>
    );
  }

  export default List;