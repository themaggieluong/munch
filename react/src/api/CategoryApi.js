import { utilFetchWrapper } from "../services/utilFetchWrapper";
const fetchWrapper = utilFetchWrapper();
const baseUrl = '/category';

export const getCategories = () => {
    return fetchWrapper.get(baseUrl);
}