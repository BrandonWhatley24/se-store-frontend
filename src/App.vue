<template>
  <v-app style="background-color: #91c8e4">
    <!--Bar at the top-->
    <v-app-bar app color="#4682A9" dark>
      <div class="d-flex align-center">
        <v-img src="./Tux_Logo.png" width="50px" @click="logoClick"></v-img>
        <v-img src="./text.png" width="300px" @click="logoClick"></v-img>
      </div>

      <v-spacer></v-spacer>

      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            v-bind="attrs"
            fab
            color="black"
            small
            v-on="on"
            v-show="showMainPage || showTransactions"
          >
            {{ buttonInitials }}
          </v-btn>
        </template>
        <v-list>
          <v-list-item>
            <v-btn block @click="openTransactions">View Transactions</v-btn>
          </v-list-item>

          <v-list-item>
            <v-btn block @click="signOut">Sign Out</v-btn>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
    <div class="background">
      <v-main>
        <!--Login Page-->
        <div v-show="showLoginPage">
          <v-row>
            <v-col cols="3"> </v-col>
            <v-col cols="6" align="center" justify="center" class="mt-3">
              <v-card
                elevation="3"
                outlined
                width="50%"
                style="background-color: #f6f4eb"
              >
                <v-card-title class="justify-center">Login</v-card-title>
                <v-col cols="1"></v-col>
                <v-col cols="10">
                  <v-text-field
                    v-model="username"
                    label="Username"
                    filled
                  ></v-text-field>
                  <v-text-field
                    v-model="password"
                    type="password"
                    label="Password"
                    filled
                  ></v-text-field>
                  <v-btn block @click="loginFunction" color="#B0D9B1">
                    Login
                  </v-btn>
                </v-col>
                <v-col cols="1"></v-col>
              </v-card>
            </v-col>
            <v-col cols="3"> </v-col>
          </v-row>

          <v-snackbar
            v-model="showSnackbar"
            :timeout="snackbarTimeout"
            color="#F78CA2"
          >
            {{ snackbarText }}</v-snackbar
          >
        </div>

        <!--Main Page-->
        <div v-show="showMainPage">
          <v-row
            v-show="showCurrentCart"
            width="100%"
            class="ml-2 mr-2 mb-4 mt-4"
          >
            <v-card color="#749BC2" width="100%" class="mt-4 ml-3 mr-3">
              <v-card-title> Current Cart </v-card-title>
            </v-card>
          </v-row>

          <v-row
            v-show="showCurrentCart"
            width="100%"
            class="ml-2 mr-2 mb-4 mt-4"
          >
            <v-card width="100%" color="#F6F4EB" class="mt-4 ml-3 mr-3 pa-4">
              <v-row>
                <v-col cols="2">
                  <h4 class="ma-3">
                    {{ currentItemInCart.itemTitle }}
                  </h4>
                </v-col>
                <v-col cols="3">
                  <h4 class="ma-3">
                    {{ currentItemInCart.itemCat }}
                  </h4>
                </v-col>
                <v-col cols="3">
                  <h4 class="ma-3">
                    {{ currentItemInCart.itemSpecs }}
                  </h4>
                </v-col>
                <v-col cols="2">
                  <h4 class="ma-3">
                    {{ currentItemInCart.itemPrice }}
                  </h4>
                </v-col>
                <v-col cols="2">
                  <v-btn class="ma-1" color="#F78CA2" @click="removeFromCart"
                    >Remove</v-btn
                  >
                  <v-btn class="ma-1" color="#B0D9B1" @click="checkoutItem"
                    >Checkout</v-btn
                  >
                </v-col>
              </v-row>
            </v-card>
          </v-row>
          <v-row width="100%" class="ml-2 mr-2 mb-4 mt-4">
            <v-card color="#749BC2" width="100%" class="mt-3 ml-3 mr-3 mb-3">
              <v-card-title> Current Items For Sale </v-card-title>
            </v-card>
          </v-row>

          <v-row width="100%" class="ml-2 mr-2 mb-4 mt-4">
            <v-card
              v-for="item in itemsForSale"
              :key="item.itemId"
              width="23%"
              height="200px"
              color="#F6F4EB"
              class="ml-5 mr-4 mb-3 mt-3"
            >
              <v-row>
                <v-col cols="9"
                  ><v-card-title>{{ item.itemTitle }}</v-card-title></v-col
                >
                <v-col cols="3"
                  ><v-card-title>{{ item.itemPrice }}</v-card-title></v-col
                >
              </v-row>
              <v-row class="ma-0">
                <v-col cols="7"
                  ><v-card-text>{{ item.itemCat }}</v-card-text></v-col
                >
              </v-row>
              <v-row class="ma-0">
                <v-col cols="6"
                  ><v-card-text>{{ item.itemSpecs }}</v-card-text></v-col
                >
                <v-col cols="6"
                  ><v-btn @click="addItemToCart(item)" color="#B0D9B1"
                    >Add Item to Cart</v-btn
                  ></v-col
                >
              </v-row>
            </v-card>
          </v-row>
        </div>

        <!--Transaction Page-->
        <div v-show="showTransactions">
          <v-card color="#749BC2" class="ml-2 mr-2 mb-4 mt-4">
            <v-card-title> Transactions for {{ currentUser }} </v-card-title>
          </v-card>

          <v-data-table
            :headers="headers"
            :items="transactions"
            :items-per-page="15"
            class="elevation-1 ml-2 mr-2"
            color="#F6F4EB"
            width="99%"
          ></v-data-table>

          <v-btn class="ma-4" @click="openMainPage" color="#B0D9B1">
            Main Page
          </v-btn>
        </div>
      </v-main>
    </div>
  </v-app>
</template>

<script>
import APIService from "../src/services/APIService";

export default {
  name: "App",

  data: () => ({
    //All Pages
    showLoginPage: true,
    showMainPage: false,
    showTransactions: false,
    currentUser: "",
    currentUserObj: {},
    buttonInitials: "",

    //Login Page
    username: "",
    password: "",
    savedUsers: [],
    showSnackbar: false,
    snackbarText: "Your username or password is incorrect. Please try again",
    snackbarTimeout: 2000,

    //Main Page
    showCurrentCart: false,
    currentItemInCart: {},
    itemsForSale: [],

    //Transactions Page
    headers: [
      {
        text: "Item Title",
        align: "start",
        sortable: false,
        value: "itemTitle",
      },
      { text: "Item Category", value: "itemCat" },
      { text: "Item Specifications", value: "itemSpecs" },
      { text: "Purchase Date", value: "purchaseDate" },
      { text: "Transaction Result", value: "itemResult" },
      { text: "Item Price", value: "itemPrice" },
    ],
    transactions: [],
  }),
  methods: {
    loginFunction() {
      let username = this.username;
      let password = this.password;
      let isUser = false;

      let hash = this.hashPassword(password);

      APIService.login(username, hash).then((tempUser) => {
        if (tempUser.firstname != "") {
          isUser = true;
        } else {
          isUser = false;
        }

        if (isUser) {
          this.currentUserObj = tempUser;
          this.currentUser = tempUser.firstname + " " + tempUser.lastname;
          this.buttonInitials =
            tempUser.firstname.charAt(0) + tempUser.lastname.charAt(0);

          APIService.getTransactionsByUser(this.currentUserObj.username).then((response) => {
            console.log(response);
            this.transactions = response;
          });

          this.showLoginPage = false;
          this.showMainPage = true;
          this.showSnackbar = false;

          
        } else {
          this.showSnackbar = true;
        }
      });
    },
    addItemToCart(item) {
      this.showCurrentCart = true;

      console.log(item.item);

      this.currentItemInCart.title = item.item;
      this.currentItemInCart.id = item.id;
      this.currentItemInCart.category = item.category;
      this.currentItemInCart.specs = item.specs;
      this.currentItemInCart.price = item.price;
    },
    removeFromCart() {
      this.showCurrentCart = false;

      this.currentItemInCart.title = "";
      this.currentItemInCart.id = 0;
      this.currentItemInCart.category = "";
      this.currentItemInCart.specs = "";
      this.currentItemInCart.price = "";
    },
    openTransactions() {
      this.showLoginPage = false;
      this.showMainPage = false;
      this.showTransactions = true;
    },
    openMainPage() {
      this.showLoginPage = false;
      this.showMainPage = true;
      this.showTransactions = false;
    },
    logoClick() {
      if (this.showTransactions) {
        this.showLoginPage = false;
        this.showMainPage = true;
        this.showTransactions = false;
      }
    },
    signOut() {
      this.showLoginPage = true;
      this.showMainPage = false;
      this.showTransactions = false;

      this.username = "";
      this.password = "";
      this.currentUser = "";
      this.currentItemInCart = {
        id: 0,
        item: "",
        category: "",
        specs: "",
        price: "",
      };
    },
    hashPassword(password) {
      let hash = 0;

      for (let i = 0; i < password.length; i++) {
        let char = password.charCodeAt(i);
        hash = (hash << 5) - hash + char;
        hash = hash & hash;
      }

      return hash;
    },
    checkoutItem() {
      let item = this.currentItemInCart;
      let isAccepted = true;
      let currDate = ""

      if (item.price > 500) {
        this.showSnackbar = true;
        isAccepted = false;
      } else {
        if (item.qty <= 0) {
          this.showSnackbar = true;
          this.reorderCertainItem();
          return;
        } else {
          let balance = APIService.checkBalance(this.currentUser.username)

          if(balance >= item.price){
            const date = new Date();
            
            let month = date.getMonth() + 1;
            let year = date.getFullYear();

            let itemQty = APIService.checkItemQty(item.id);

            if(itemQty >= 1){
              let day = date.getDate() + 1;
              currDate = `${month}-${day}-${year}`;
            }else{
              let day = date.getDate() + 5;
              currDate = `${month}-${day}-${year}`;

              this.reorderCertainItem();
            }

            
          }else{
            this.showSnackbar = true;
            return;
          }
        }
      }

      let transaction = {
        user: this.currentUser,
        itemTitle: this.currentItemInCart.itemTitle,
        itemId: this.currentItemInCart.itemId,
        itemPrice: this.currentItemInCart.price,
        itemCat: this.currentItemInCart.itemCat,
        itemSpecs: this.currentItemInCart.itemSpecs,
        date: currDate,
        result: isAccepted,
      }

      let result = APIService.insertTransaction(transaction);

      if(result){
        this.currentItemInCart = {};
        this.showCurrentCart = false;
      }else{
        this.showSnackbar = true;
      }
    },
    reorderCertainItem() {
      let result = APIService.reorderItem(this.currentItemInCart.itemId);

      result
    },
  },
  created() {
    APIService.getUsers().then((response) => {
      this.savedUsers = response.data;
    });

    APIService.getStoreItems().then((response) => {
      this.itemsForSale = response.data;
    });
  },
};
</script>


<style></style>