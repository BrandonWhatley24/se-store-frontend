<template>
  <v-app>

    <!--Bar at the top-->
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <v-img alt="Vuetify Logo" class="shrink mr-2" contain
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png" transition="scale-transition" width="40" @click="openMainPage"/>

        <v-img alt="Vuetify Name" class="shrink mt-1 hidden-sm-and-down" contain min-width="100"
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-name-dark.png" width="100" @click="openMainPage" />
      </div>

      <v-spacer></v-spacer>

      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn v-bind="attrs" fab color="black" small v-on="on" v-show="showMainPage || showTransactions">
            {{ buttonInitials }}
          </v-btn>
        </template>
        <v-list>
          <v-list-item>
          <v-btn block @click="openTransactions" >View Transactions</v-btn>
          </v-list-item>

          <v-list-item>
          <v-btn block @click="signOut">Sign Out</v-btn>
        </v-list-item>
        </v-list>
      </v-menu>


    </v-app-bar>

    <v-main>

      <!--Login Page-->
      <div v-show="showLoginPage">
        <v-row>
          <v-col cols="3">
          </v-col>
          <v-col cols="6" align="center" justify="center" class="mt-3">
            <v-card elevation="3" outlined width="50%">
              <v-card-title class="justify-center">Login</v-card-title>
              <v-col cols="1"></v-col>
              <v-col cols="10">
                <v-text-field v-model="username" label="Username" filled></v-text-field>
                <v-text-field v-model="password" type="password" label="Password" filled></v-text-field>
                <v-btn block @click="loginFunction">
                  Login
                </v-btn>
              </v-col>
              <v-col cols="1"></v-col>
            </v-card>
          </v-col>
          <v-col cols="3">
          </v-col>
        </v-row>

        <v-snackbar v-model="showSnackbar" :timeout="snackbarTimeout" color="error"> {{ snackbarText }}</v-snackbar>
      </div>

      <!--Main Page-->
      <div v-show="showMainPage">
        <v-row v-show="showCurrentCart">
          <v-card color="blue" width="100%" class="mt-4 ml-3 mr-3 ">
            <v-card-title>
              Current Cart
            </v-card-title>
          </v-card>
        </v-row>

        <v-row v-show="showCurrentCart" width="100%" class="ml-2 mr-2 mb-4 mt-4">
          <v-card width="100%" class="mt-4 ml-3 mr-3 pa-4">
            <v-row>
              <v-col cols="2">
                <h4 class="ma-3">
                  {{ currentItemInCart.title }}
                </h4>
              </v-col>
              <v-col cols="3">
                <h4 class="ma-3">
                  {{ currentItemInCart.category }}
                </h4>
              </v-col>
              <v-col cols="3">
                <h4 class="ma-3">
                  {{ currentItemInCart.specs }}
                </h4>
              </v-col>
              <v-col cols="2">
                <h4 class="ma-3">
                  {{ currentItemInCart.price }}
                </h4>
              </v-col>
              <v-col cols="2">
                <v-btn class="ma-1" color="" @click="removeFromCart">Remove</v-btn>
                <v-btn class="ma-1" color="">Checkout</v-btn>
              </v-col>
            </v-row>
          </v-card>
        </v-row>
        <v-row>
          <v-card color="blue" width="100%" class="mt-3 ml-3 mr-3 mb-3">
            <v-card-title>
              Current Items For Sale
            </v-card-title>
          </v-card>
        </v-row>

        <v-row>
          <v-card v-for="item in itemsForSale" :key="item.id" width="32%" height="400px" class="ml-3 mr-3 mb-3 mt-3">
            <v-card-title>{{ item.item }}</v-card-title>
            <v-btn @click="addItemToCart(item)">Add Item to Cart</v-btn>
          </v-card>
        </v-row>
      </div>

      <!--Transaction Page-->
      <div v-show="showTransactions">
        <v-card color="blue" class="ml-2 mr-2 mb-4 mt-4">
          <v-card-title>
            Transactions for {{ currentUser }}
          </v-card-title>
        </v-card>

        <v-data-table :headers="headers" :items="transactions" :items-per-page="15" class="elevation-1 ml-2 mr-2"
          width="99%"></v-data-table>

        <v-btn class="ma-4" @click="openMainPage"> Main Page </v-btn>
      </div>

    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App',


  data: () => ({
    //All Pages
    showLoginPage: true,
    showMainPage: false,
    showTransactions: false,
    currentUser: "",
    buttonInitials: "",

    //Login Page
    username: "",
    password: "",
    savedUsers: [
      { username: "TestUser", password: "userTestPass", firstName: "Test", lastName: "User" }
    ],
    showSnackbar: false,
    snackbarText: "Your username or password is incorrect. Please try again",
    snackbarTimeout: 2000,

    //Main Page
    showCurrentCart: false,
    currentItemInCart: { id: 0, item: '', category: '', specs: '', price: '' },
    itemsForSale: [
      { id: 0, item: 'Blue Shirt', category: 'Clothing', specs: 'Blue', price: '29.99' },
      { id: 1, item: 'Red Shirt', category: 'Clothing', specs: 'Red', price: '29.99' },
      { id: 2, item: 'Purple Shirt', category: 'Clothing', specs: 'Purple', price: '29.99' },
      { id: 3, item: 'Yellow Shirt', category: 'Clothing', specs: 'Yellow', price: '29.99' },
    ],

    //Transactions Page

    headers: [
      { text: "Item Title", align: 'start', sortable: false, value: 'title' },
      { text: "Item Category", value: 'category', },
      { text: "Item Specifications", value: 'specs', },
      { text: "Purchase Date", value: 'date', },
      { text: "Purchase Time", value: 'time', },
      { text: "Item Price", value: 'price', },
    ],

    transactions: [
      { title: "T-Shirt", category: "Clothing", specs: "Blue Shirt", date: "September 18th, 2023", time: "9:00PM", price: "29.99" },
      { title: "T-Shirt", category: "Clothing", specs: "Blue Shirt", date: "September 18th, 2023", time: "9:00PM", price: "29.99" }
    ]
  }),
  methods: {
    loginFunction() {

      window.HELLO.test("Webview");
      let username = this.username;
      let password = this.password;
      let userFound = false;
      let correctIndex = -1;

      for (let i = 0; i < this.savedUsers.length; i++) {
        if (this.savedUsers[i].username.toUpperCase === username.toUpperCase && password === this.savedUsers[i].password) {
          userFound = true;
          correctIndex = i;
        }
      }

      if (userFound) {
        this.showLoginPage = false;
        this.showMainPage = true;
        this.showSnackbar = false;

        this.currentUser = this.savedUsers[correctIndex].firstName + " " + this.savedUsers[correctIndex].lastName;
        this.buttonInitials = this.savedUsers[correctIndex].firstName.charAt(0) + this.savedUsers[correctIndex].lastName.charAt(0);
      } else {
        this.showSnackbar = true;
      }
    },
    addItemToCart(item) {
      this.showCurrentCart = true;

      this.currentItemInCart.title = item.item;
      this.currentItemInCart.id = item.id;
      this.currentItemInCart.category = item.category;
      this.currentItemInCart.specs = item.specs;
      this.currentItemInCart.price = item.price;
    },
    removeFromCart() {
      this.showCurrentCart = false

      this.currentItemInCart.title = "";
      this.currentItemInCart.id = 0;
      this.currentItemInCart.category = "";
      this.currentItemInCart.specs = "";
      this.currentItemInCart.price = "";
    },
    openTransactions(){
      this.showLoginPage = false;
      this.showMainPage = false;
      this.showTransactions = true;
    },
    openMainPage(){
      this.showLoginPage = false;
      this.showMainPage = true;
      this.showTransactions = false;
    },
    signOut(){
      this.showLoginPage = true;
      this.showMainPage = false;
      this.showTransactions = false;

      this.username = "";
      this.password = "";
      this.currentUser = "";
      this.currentItemInCart = { id: 0, item: '', category: '', specs: '', price: '' };
    }
  }
};
</script>
