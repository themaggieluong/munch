
import { utilFetchWrapper } from "../services/utilFetchWrapper";
const fetchWrapper = utilFetchWrapper();

export function getRestaurants(){
    return fetchWrapper.get('/');
}

export function getRestaurantDetail(restaurantId){    
    return fetchWrapper.get(`/${restaurantId}`);
}

export function getRestaurantByCategory(categoryIds, userId){
    let url = `/restaurant/categories/${userId}`;
    return fetchWrapper.post(url, categoryIds);
}

export function addUserRestaurant(userRestaurant){
    let url = '/restaurant/users';
    return fetchWrapper.post(url, userRestaurant);
}

export function getLikedRestaurant(userId){
    let url = `/restaurant/users/liked/${userId}`;
    return fetchWrapper.get(url);
}

export function getNotLikedOrRejectedRestaurant(userId){
    let url = `/restaurant/updated/${userId}`;
    return fetchWrapper.get(url);
}