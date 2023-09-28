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
        <v-snackbar
          v-model="showSnackbar"
          :timeout="snackbarTimeout"
          :color="snackbarColor"
        >
          {{ snackbarText }}</v-snackbar
        >

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
            <v-card width="100%" color="#F6F4EB" class="mt-4 ml-3 mr-3 pa-2">
              <v-row>
                <v-col cols="2">
                  <h4 class="ma-1">
                    {{ this.currentItemInCart.itemTitle }}
                  </h4>
                </v-col>
                <v-col cols="3">
                  <h4 class="ma-1"></h4>
                </v-col>
                <v-col cols="3">
                  <h4 class="ma-1"></h4>
                </v-col>
                <v-col cols="2">
                  <h4 class="ma-1">
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
              height=""
              color="#F6F4EB"
              class="ml-5 mr-4 mb-3 mt-3"
            >
              <v-row>
                <v-col cols="12"
                  ><v-card-title>{{ item.itemTitle }}</v-card-title></v-col
                > </v-row
              ><v-row>
                <v-col cols="12"
                  ><v-card-title>{{ item.itemPrice }}</v-card-title></v-col
                >
              </v-row>
              <v-row class="ma-2">
                <v-col cols="8"
                  ><v-btn
                    @click="addItemToCart(item)"
                    color="#B0D9B1"
                    width="90%"
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
    snackbarColor: "#F78CA2",
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

          APIService.getTransactionsByUser(this.currentUserObj.username).then(
            (response) => {
              this.transactions = response;
            }
          );

          this.showLoginPage = false;
          this.showMainPage = true;
        } else {
          this.showSnackbar = true;
          this.snackbarText =
            "Username is not in a our system and/or doesn't match the password";
          this.snackbarColor = "#F78CA2";
        }
      });
    },
    addItemToCart(item) {
      this.showCurrentCart = true;

      this.currentItemInCart.itemTitle = item.itemTitle;
      this.currentItemInCart.itemId = item.itemId;
      this.currentItemInCart.itemPrice = item.itemPrice;
    },
    removeFromCart() {
      this.showCurrentCart = false;

      this.currentItemInCart.itemTitle = "";
      this.currentItemInCart.itemId = 0;
      this.currentItemInCart.itemPrice = "";
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
        itemId: 0,
        itemTitle: "",
        itemPrice: "",
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
      let currDate = "";

      if (item.itemPrice > 500) {
        this.showSnackbar = true;
        this.snackbarText = "Purchase price cannot be over $500";
        this.snackbarColor = "#F78CA2";
        isAccepted = false;
        return;
      } else {
        let balance = 0;
        APIService.checkBalance(this.currentUserObj.username).then(
          (response) => {
            balance = response;

            if (balance >= item.itemPrice) {
              const date = new Date();

              let month = date.getMonth() + 1;
              let year = date.getFullYear();

              let itemQty = 0;

              APIService.checkItemQty(item.itemId).then((response) => {
                itemQty = response;

                if (itemQty >= 1) {
                  let day = date.getDate() + 1;
                  currDate = `${month}-${day}-${year}`;
                } else {
                  let day = date.getDate() + 5;
                  currDate = `${month}-${day}-${year}`;

                  this.reorderCertainItem(item);
                }
              });
            } else {
              this.showSnackbar = true;
              this.snackbarText = "insufficient funds for the purchase price";
              this.snackbarColor = "#F78CA2";
              isAccepted = false;
              return;
            }
          }
        )
      }

      let transaction = {
        user: this.currentUserObj.username,
        itemTitle: this.currentItemInCart.itemTitle,
        itemId: this.currentItemInCart.itemId,
        itemPrice: this.currentItemInCart.price,
        date: currDate,
        result: isAccepted,
      };

      let result = false;
      APIService.insertTransaction(transaction).then((response) => {
        result = response;

        if (result) {
          this.currentItemInCart = {};
          this.showCurrentCart = false;
          this.showSnackbar = true;

          this.snackbarColor = "#B0D9B1";
        } else {
          this.showSnackbar = true;
          this.snackbarText = "Purchase of the item was unsuccessful";
          this.snackbarColor = "#F78CA2";
        }
      });
    },
    reorderCertainItem(item) {
      APIService.reorderItem(item.itemId);
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