import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api'

class APIService {
    getUsers() {
        return axios.get(BASE_URL + "/users");
    }

    async login(username, password) {
        const userLogin = {
            username: username,
            password: password,
        }
        let user = {};
        await axios.post(BASE_URL + "/user/Auth", userLogin).then(tempUser => {

            user = {
                'firstname': tempUser.data.firstName,
                'lastname': tempUser.data.lastName,
                'username': tempUser.data.username
            }

            
        });
        return user;
    }

    getTransactions() {
        return axios.get(BASE_URL + "/orders")
    }

    async getTransactionsByUser(username){
        let transactions = []
        await axios.post(BASE_URL + "/orders/byUser", username).then(response => {
            transactions = response.data;
        });
        return transactions
    }

    getStoreItems() {
        return axios.get(BASE_URL + "/storeItems")
    }

    async checkBalance(username){
        let balance = 0.0;
        await axios.post(BASE_URL + "/user/balance", username).then(response => {
            balance = response.data;
        });

        return balance;
    }

    async checkItemQty(itemID){
        let quantity = 0;
        await axios.post(BASE_URL + "/storeItem/Qty", itemID).then(response =>{
            quantity = response.data;
        })

        return quantity;
    }

    async reorderItem(itemID){
        let result = true;
        await axios.post(BASE_URL + "/reorder/item", itemID).then(response =>{
            result = response.data;
        })

        return result;
    }

    async insertTransaction(transaction){
        let result = false;
        await axios.post(BASE_URL + "/insert/order", transaction).then(response => {
            result = response.data;
        })

        return result
    }
}

export default new APIService()