import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api'

class APIService{
    getUsers(){
        return axios.get(BASE_URL + "/users");
    }

    getTransactions(){
        return axios.get(BASE_URL + "/orders")
    }

    getStoreItems(){
        return axios.get(BASE_URL + "/storeItems")
    }
}

export default new APIService()