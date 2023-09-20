<template>
  <v-container fluid>
    <v-row v-show="showCurrentCart">
      <v-card color="blue" width="100%" class="mt-4 ml-3 mr-3">
        <v-card-title>
          Current Cart
        </v-card-title>
      </v-card>
    </v-row>

    <v-row v-show="showCurrentCart">
      <v-card width="100%" class="mt-4 ml-3 mr-3">
        <v-row>
          <v-col cols="2">
            <h4 class="ma-3">
              Title of the item
            </h4>
          </v-col>
          <v-col cols="3">
            <h4 class="ma-3">
              Category of the item
            </h4>
          </v-col>
          <v-col cols="3">
            <h4 class="ma-3">
              Specific of the item
            </h4>
          </v-col>
          <v-col cols="2">
            <h4 class="ma-3">
              Price
            </h4>
          </v-col>
          <v-col cols="2">
            <v-btn class="ma-1" color="">Remove</v-btn>
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
    </v-card>
    </v-row>
  </v-container>
</template>
    
<script>
export default {
  name: 'HelloWorld',

  data: () => ({
    username: "",
    password: "",
    showCurrentCart: true,
    savedUsers: [
      "TestUser"
    ],
    savedPasswords: [
      "userTestPass"
    ],
    itemsForSale: [
      { id: 0, item: 'Blue Shirt' },
      { id: 1, item: 'Red Shirt' },
      { id: 2, item: 'Purple Shirt' },
      { id: 3, item: 'Yellow Shirt' },

    ]
  }),
  methods: {
    loginFunction() {
      console.log("This is a test")
    }
  }
}
</script>
    